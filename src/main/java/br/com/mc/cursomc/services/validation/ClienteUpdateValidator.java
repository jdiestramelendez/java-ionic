package br.com.mc.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.mc.cursomc.dao.ClienteDAO;
import br.com.mc.cursomc.domain.Cliente;
import br.com.mc.cursomc.dto.ClienteDTO;
import br.com.mc.cursomc.resources.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteDAO dao;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		Map<String,String> mv = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(mv.get("id"));
		
		List<FieldMessage> errors = new ArrayList<>();
		
		Cliente cemail = dao.findByEmail(objDto.getEmail());
		if (cemail != null) {
			System.out.println("Encontrou cliente por email: " + cemail);
			if (!cemail.getId().equals(uriId)) {
				errors.add(new FieldMessage("email", "Email já cadastrado"));
			}
		}

		for (FieldMessage e : errors) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return errors.isEmpty();
	}
}