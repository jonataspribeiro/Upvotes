package com.segware.upvotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.segware.upvotes.model.Tema;


//Relancionamento entre Entidades 
public interface TemaRepository extends JpaRepository<Tema, Long> {
	public List<Tema> findAllByDescricaoContainingIgnoreCase(String descricao);
}
