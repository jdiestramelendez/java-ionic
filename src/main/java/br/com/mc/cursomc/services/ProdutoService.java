package br.com.mc.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.mc.cursomc.dao.CategoriaDAO;
import br.com.mc.cursomc.dao.ProdutoDAO;
import br.com.mc.cursomc.domain.Categoria;
import br.com.mc.cursomc.domain.Produto;
import br.com.mc.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoDAO dao;

	@Autowired
	private CategoriaDAO catdao;
	
	public Produto buscar(Integer id) {
		 Optional<Produto> obj = dao.findById(id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException(
		  "Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	public List<Produto> todos() {
		return dao.findAll();
	}
	
	public Produto criar(Produto ped) {
		return dao.save(ped);
	}
	
	public Page<Produto> search(String nome, List<Integer> idsCat, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pr = PageRequest.of(page, linesPerPage, Direction.fromString(direction), orderBy);
		List<Categoria> categorias = catdao.findAllById(idsCat);
		//return dao.search(nome, categorias, pr);
		return dao.findDistinctByNomeContainingIgnoreCaseAndCategoriasIn(nome, categorias, pr);
	}
}
