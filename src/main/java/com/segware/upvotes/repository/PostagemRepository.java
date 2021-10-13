package com.segware.upvotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.segware.upvotes.model.Postagem;

//Criar um pacote Repository, extender o JpaRepository
@Repository //Inserir a anotação Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {
	public List<Postagem>findAllByTituloContainingIgnoreCase(String titulo);

}
