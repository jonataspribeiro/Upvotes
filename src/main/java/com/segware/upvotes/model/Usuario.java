package com.segware.upvotes.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "tb_usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "O Nome é Obrigatório!")
	private String nome;

	@ApiModelProperty(example = "usuario 001")
	@NotNull(message = "O Usuário é Obrigatório!")
	@Size(min = 4, max = 30, message = "O Usuário deve ter no mínimo 4 caracteres")
	private String usuario;

	@NotNull(message = "A Senha é Obrigatória!")
	@Size(min = 8, message = "A Senha deve ter no mínimo 8 caracteres")
	private String senha;
	
	private String tipo;
	
	@OneToMany (mappedBy = "usuario", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario")
	private List <Postagem> postagem;
	
	public Usuario(long id, String nome, String usuario, String senha, String tipo) {
		
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;	
		this.tipo = tipo;
	}	
	
	public Usuario() {	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public List<Postagem> getPostagem() {
		return postagem;
	}
	
	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}


}
