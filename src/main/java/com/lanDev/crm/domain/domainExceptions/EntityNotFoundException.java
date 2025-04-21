package com.lanDev.crm.domain.domainExceptions;


import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BusinessException {
    private final String entity;
    private final Object key;

    public EntityNotFoundException(String entity, Object key) {
        super(
                HttpStatus.NOT_FOUND,
                String.format("%s %s", entity, key)
        );
        this.entity = entity;
        this.key    = key;
    }

    public String getEntity() { return entity; }

    public Object getKey(){ return key; }

}
