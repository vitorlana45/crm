package com.lanDev.crm.adapter.inbound.web.exceptions.setup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();


	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
}
