package com.segware.upvotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.segware.upvotes.model.Postagem;
import com.segware.upvotes.repository.PostagemRepository;
import com.segware.upvotes.service.PostagemService;


@RestController // Para informar que está classe é a controladora
@RequestMapping("/postagens") // Definir a URI que será acessada
@CrossOrigin(origins = "*", allowedHeaders = "*") // Aceitar as requisições de qualqer origem
public class PostagemController {

	@Autowired // Utilizar a anotação de injeção de dependência do Spring
	private PostagemRepository postagemRepository;

	@Autowired
	private PostagemService postagemService;

	// Método Get
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll() {
		return ResponseEntity.ok(postagemRepository.findAll());
	}

	// Método Get Id (HTTP que será enviado para API)
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable long id) {
		return postagemRepository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}

	// Método localizar os títulos
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}

	// Método Post
	@PostMapping
	public ResponseEntity<Postagem> postPostagem(@RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	}

	// Método Put
	@PutMapping
	public ResponseEntity<Postagem> putPostagem(@RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
	}

	// Método Delete
	@DeleteMapping("/{id}")
	public void deletePostagem(@PathVariable long id) {
		postagemRepository.deleteById(id);
	}

	// Método Upvote
	@PutMapping("/upvote/{id}")
	public ResponseEntity<Postagem> putUpvotePostagemId(@PathVariable Long id) {

		return ResponseEntity.status(HttpStatus.OK).body(postagemService.upvote(id));

	}

	// Método Downvote
	@PutMapping("/downvote/{id}")
	public ResponseEntity<Postagem> putDownvotePostagemId(@PathVariable Long id) {

		return ResponseEntity.status(HttpStatus.OK).body(postagemService.downvote(id));

	}

}
