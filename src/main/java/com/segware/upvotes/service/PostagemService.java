package com.segware.upvotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.segware.upvotes.model.Postagem;
import com.segware.upvotes.repository.PostagemRepository;

@Service
public class PostagemService {

	@Autowired
	private PostagemRepository postagemRepository;

	// Método Upvote -> Soma 1 ao atributo upvote

	public Postagem upvote(Long id) {

		Postagem postagem = findPostagemById(id);

		postagem.setUpvotes(postagem.getUpvotes() + 1);

		return postagemRepository.save(postagem);

	}

	/*
	 * Método Downvote -> Subtrai 1 do atributo upvote enquanto for maior do que
	 * zero. Se for igual a zero, mantém o zero
	 */

	public Postagem downvote(Long id) {

		Postagem postagem = findPostagemById(id);

		if (postagem.getUpvotes() > 0) {

			postagem.setUpvotes(postagem.getUpvotes() - 1);

		} else {

			postagem.setUpvotes(0);

		}

		return postagemRepository.save(postagem);

	}

	/*
	 * Método buscarPostagemPeloId -> Implmenta o método findById checando se a
	 * postagem existe.
	 */

	private Postagem findPostagemById(Long id) {

		Postagem postagemSalva = postagemRepository.findById(id).orElse(null);

		if (postagemSalva == null) {

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Postagem não encontrada!", null);
		}

		return postagemSalva;
	}

}
