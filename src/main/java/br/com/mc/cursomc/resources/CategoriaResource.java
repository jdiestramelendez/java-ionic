package br.com.mc.cursomc.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.mc.cursomc.domain.Categoria;
import br.com.mc.cursomc.dto.CategoriaDTO;
import br.com.mc.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService catserv;

	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		System.out.println("Categorias: buscando todas");
		List<Categoria> lista = catserv.findAll();
		//List<CategoriaDTO> listadto = lista.stream().map(cat -> new CategoriaDTO(cat)).collect(Collectors.toList());
		List<CategoriaDTO> listadto = new ArrayList<>();
		for (Categoria cat : lista) {
			listadto.add(new CategoriaDTO(cat));
		}
		return ResponseEntity.ok(listadto);
	}

	@GetMapping("/page")
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(name = "page", defaultValue = "0") Integer page, 
			@RequestParam(name = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(name = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(name = "direction", defaultValue = "ASC") String direction) {
		System.out.println("Categorias: buscando pagina");
		Page<Categoria> lista = catserv.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listadto = lista.map(cat -> new CategoriaDTO(cat));
		return ResponseEntity.ok(listadto);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		System.out.println("Categorias: buscando: " + id);
		Categoria cat = catserv.find(id);
		return ResponseEntity.ok(cat);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO catdto) {
		Categoria cat = catserv.fromDTO(catdto);
		System.out.println("Categorias: Criando: " + cat);
		Categoria catNova = catserv.insert(cat);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(catNova.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO catdto, @PathVariable Integer id) {
		Categoria cat = catserv.fromDTO(catdto);
		System.out.println("Categorias: Atualizando: " + cat);
		cat.setId(id);
		cat = catserv.update(cat);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		System.out.println("Categorias: Removendo: " + id);
		catserv.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
