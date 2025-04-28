package com.lanDev.crm.adapter.inbound.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lanDev.crm.application.service.auth.UserContextService;
import com.lanDev.crm.domain.domainExceptions.BusinessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class OrganizationFilter extends OncePerRequestFilter {

    private final UserContextService userContextService;
    private final ObjectMapper objectMapper;
    
    // Padrões de URL que contêm IDs de organização
    private static final Pattern ORG_ID_PATTERN = Pattern.compile("/organizations/([a-f0-9-]+)");
    private static final Pattern ORG_USERS_PATTERN = Pattern.compile("/organizations/([a-f0-9-]+)/users");
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain chain) throws ServletException, IOException {
        
        String path = request.getRequestURI();
        
        try {
            // Extrair ID da organização da URL se presente
            UUID orgId = extractOrgIdFromUrl(path);
            
            if (orgId != null && !hasAccessToOrganization(orgId)) {
                sendAccessDeniedResponse(response);
                return;
            }
            
            chain.doFilter(request, response);
            
        } catch (Exception e) {
            chain.doFilter(request, response);
        }
    }
    
    private UUID extractOrgIdFromUrl(String path) {
        // Verificar URLs com ID da organização
        Matcher orgMatcher = ORG_ID_PATTERN.matcher(path);
        if (orgMatcher.find()) {
            return UUID.fromString(orgMatcher.group(1));
        }
        
        // Verificar URLs de usuários por organização
        Matcher usersMatcher = ORG_USERS_PATTERN.matcher(path);
        if (usersMatcher.find()) {
            return UUID.fromString(usersMatcher.group(1));
        }
        
        return null;
    }
    
    private boolean hasAccessToOrganization(UUID orgId) {
        // SUPER_ADMIN tem acesso a todas as organizações
        if (userContextService.isSuperAdmin()) {
            return true;
        }
        
        // Outros papéis só podem acessar sua própria organização
        UUID currentUserOrgId = userContextService.getCurrentUserOrganizationId();
        return currentUserOrgId.equals(orgId);
    }
    
    private void sendAccessDeniedResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", HttpStatus.FORBIDDEN.value());
        errorDetails.put("message", "Sem permissão para acessar recursos desta organização");
        
        objectMapper.writeValue(response.getWriter(), errorDetails);
    }
}