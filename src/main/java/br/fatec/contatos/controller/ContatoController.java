package br.fatec.contatos.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.contatos.model.Contato;
import br.fatec.contatos.service.ContatoService;

@RestController
@RequestMapping("/contatos")
public class ContatoController implements ControllerInterface<Contato>{
	@Autowired
	private ContatoService service;
	
	@Override
	@GetMapping
	public ResponseEntity<List<Contato>> getAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@Override
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> get(@PathVariable("id") Long id){
		Contato _contato = service.findById(id);
		if(_contato != null) {
			return ResponseEntity.ok(_contato);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@Override
	@PostMapping
	public ResponseEntity<Contato> post(@RequestBody Contato contato){
		service.create(contato);
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(contato.getId()).toUri();
		return ResponseEntity.created(location).body(contato);
	}
	
	@Override
	@PutMapping
	public ResponseEntity<?> put(@RequestBody Contato contato){
		if(service.update(contato)) {
			return ResponseEntity.ok(contato);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@Override
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		if(service.delete(id)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
