package br.com.mc.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.mc.cursomc.dao.ClienteDAO;
import br.com.mc.cursomc.domain.Cliente;
import br.com.mc.cursomc.domain.enums.TipoCliente;
import br.com.mc.cursomc.dto.ClienteNewDTO;
import br.com.mc.cursomc.resources.exception.FieldMessage;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteDAO dao;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> errors = new ArrayList<>();
		
		// inclua os testes aqui, inserindo erros na lista
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCodigo())) {
			System.out.println("Pessoa Física");
			CPFValidator cpfv = new CPFValidator();
			cpfv.initialize(null);
			boolean cpfValido = cpfv.isValid(objDto.getCpfOuCnpj(), context);
			System.out.println("CPF válido: " + cpfValido);
			if (!cpfValido) {
				errors.add(new FieldMessage("cpfOuCnpj", "Campo CPF inválido"));
			}
		}
		
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCodigo())) {
			System.out.println("Pessoa Juridica");
			
			CNPJValidator cnpjv = new CNPJValidator();
			cnpjv.initialize(null);
			boolean cnpjValido = cnpjv.isValid(objDto.getCpfOuCnpj(), context);
			
			System.out.println("CNPJ válido: " + cnpjValido);
			if (!cnpjValido) {
				errors.add(new FieldMessage("cpfOuCnpj", "Campo CNPJ inválido"));
			}
		}
		
		Cliente cemail = dao.findByEmail(objDto.getEmail());
		if (cemail != null) {
			System.out.println("Encontrou cliente por email: " + cemail);
			errors.add(new FieldMessage("email", "Email já cadastrado"));
		}

		for (FieldMessage e : errors) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return errors.isEmpty();
	}
}