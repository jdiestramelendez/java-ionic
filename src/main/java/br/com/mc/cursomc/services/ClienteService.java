package br.com.mc.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mc.cursomc.dao.ClienteDAO;
import br.com.mc.cursomc.dao.EnderecoDAO;
import br.com.mc.cursomc.domain.Cidade;
import br.com.mc.cursomc.domain.Cliente;
import br.com.mc.cursomc.domain.Endereco;
import br.com.mc.cursomc.domain.enums.TipoCliente;
import br.com.mc.cursomc.dto.ClienteDTO;
import br.com.mc.cursomc.dto.ClienteNewDTO;
import br.com.mc.cursomc.services.exception.DataIntegrityException;
import br.com.mc.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteDAO dao;
	
	@Autowired
	private EnderecoDAO enddao;
	
	@Autowired
	private BCryptPasswordEncoder passEnc;
	
	public Cliente find(Integer id) {
		 Optional<Cliente> obj = dao.findById(id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException(
		  "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public List<Cliente> findAll() {
		return dao.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pr = PageRequest.of(page, linesPerPage, Direction.fromString(direction), orderBy);
		return dao.findAll(pr);
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = dao.save(obj);
		enddao.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente objdb = find(obj.getId());
		updateData(objdb, obj);
		return dao.save(objdb);
	}
	
	private void updateData(Cliente objdb, Cliente obj) {
		objdb.setNome(obj.getNome());
		objdb.setEmail(obj.getEmail());
	}

	public void delete(Integer id) {
		find(id);
		try {
			dao.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir Cliente com pedidos");
		}
	}
	
	public Cliente fromDTO(ClienteDTO dto) {
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO dto) {
		Cliente cli = new Cliente(null, dto.getNome(), dto.getEmail(), dto.getCpfOuCnpj(), TipoCliente.getTipoCliente(dto.getTipo()), passEnc.encode(dto.getSenha()));
		Endereco end = new Endereco(null, dto.getLogradouro(), dto.getNumero(), dto.getComplemento(), dto.getBairro(), dto.getCep(), new Cidade(dto.getCidadeId(), null, null), cli);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(dto.getTelefone1());
		if (dto.getTelefone2() != null) cli.getTelefones().add(dto.getTelefone2());
		if (dto.getTelefone3() != null) cli.getTelefones().add(dto.getTelefone3());
		return cli;
	}
}
