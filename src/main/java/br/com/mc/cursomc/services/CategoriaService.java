package br.com.mc.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.mc.cursomc.dao.CategoriaDAO;
import br.com.mc.cursomc.domain.Categoria;
import br.com.mc.cursomc.dto.CategoriaDTO;
import br.com.mc.cursomc.services.exception.DataIntegrityException;
import br.com.mc.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaDAO dao;
	
	public Categoria find(Integer id) {
		 Optional<Categoria> obj = dao.findById(id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException(
		  "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public List<Categoria> findAll() {
		return dao.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pr = PageRequest.of(page, linesPerPage, Direction.fromString(direction), orderBy);
		return dao.findAll(pr);
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return dao.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		Categoria objdb = find(obj.getId());
		updateData(objdb, obj);
		return dao.save(objdb);
	}
	
	private void updateData(Categoria objdb, Categoria obj) {
		objdb.setNome(obj.getNome());
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			dao.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir categoria com produtos");
		}
	}
	
	public Categoria fromDTO(CategoriaDTO dto) {
		return new Categoria(dto.getId(), dto.getNome());
	}
}
