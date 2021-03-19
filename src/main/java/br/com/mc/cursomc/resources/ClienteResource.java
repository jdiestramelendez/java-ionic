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

import br.com.mc.cursomc.domain.Cliente;
import br.com.mc.cursomc.dto.ClienteDTO;
import br.com.mc.cursomc.dto.ClienteNewDTO;
import br.com.mc.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService cliserv;
	
	

	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		System.out.println("Clientes: buscando todas");
		List<Cliente> lista = cliserv.findAll();
		//List<ClienteDTO> listadto = lista.stream().map(cli -> new ClienteDTO(cli)).collect(Collectors.toList());
		List<ClienteDTO> listadto = new ArrayList<>();
		for (Cliente cli : lista) {
			listadto.add(new ClienteDTO(cli));
		}
		return ResponseEntity.ok(listadto);
	}

	@GetMapping("/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(name = "page", defaultValue = "0") Integer page, 
			@RequestParam(name = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(name = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(name = "direction", defaultValue = "ASC") String direction) {
		System.out.println("Clientes: buscando todas");
		Page<Cliente> lista = cliserv.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listadto = lista.map(cli -> new ClienteDTO(cli));
		return ResponseEntity.ok(listadto);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		System.out.println("Clientes: buscando: " + id);
		Cliente cli = cliserv.find(id);
		return ResponseEntity.ok(cli);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO clidto) {
		Cliente cli = cliserv.fromDTO(clidto);
		System.out.println("Clientes: Criando: " + cli);
		Cliente cliNova = cliserv.insert(cli);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliNova.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO clidto, @PathVariable Integer id) {
		Cliente cli = cliserv.fromDTO(clidto);
		System.out.println("Clientes: Atualizando: " + cli);
		cli.setId(id);
		cli = cliserv.update(cli);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		System.out.println("Clientes: Removendo: " + id);
		cliserv.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
