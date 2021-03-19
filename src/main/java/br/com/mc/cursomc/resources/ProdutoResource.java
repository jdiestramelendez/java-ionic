package br.com.mc.cursomc.resources;

import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import br.com.mc.cursomc.domain.Categoria;
import br.com.mc.cursomc.domain.Produto;
import br.com.mc.cursomc.domain.ProdutoDTO;
import br.com.mc.cursomc.dto.CategoriaDTO;
import br.com.mc.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService prodserv;

	@GetMapping
	public List<Produto> findAll() {
		System.out.println("Produtos: buscando todos");
		return prodserv.todos();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		System.out.println("Produtos: buscando: " + id);
		Produto cli = prodserv.buscar(id);
		return ResponseEntity.ok(cli);
	}

	@GetMapping("/page")
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(name = "nome", defaultValue = "") String nome, 
			@RequestParam(name = "categorias", defaultValue = "0") String categorias, 
			@RequestParam(name = "page", defaultValue = "0") Integer page, 
			@RequestParam(name = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(name = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(name = "direction", defaultValue = "ASC") String direction) {
		System.out.println("Produtos: buscando pagina");
		List<Integer> catIds = Arrays.asList(categorias.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
		Page<Produto> lista = prodserv.search(UriUtils.decode(nome, "UTF-8"), catIds, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listadto = lista.map(prod -> new ProdutoDTO(prod));
		return ResponseEntity.ok(listadto);
	}
	
	@PostMapping
	public Produto novo(@RequestBody Produto cli) {
		System.out.println("Produto: " + cli);
		return prodserv.criar(cli);
	}
	
}
