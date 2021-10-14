package com.segware.upvotes.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.segware.upvotes.model.Usuario;
import com.segware.upvotes.model.UsuarioLogin;
import com.segware.upvotes.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Optional<Usuario> CadastrarUsuario(Usuario usuario) {

		/*
		 * Apresenta uma Exception do tipo Response Status Bad Request se o usuário já
		 * existir
		 */

		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);

		return Optional.of(usuarioRepository.save(usuario));
	}

	public Optional<Usuario> atualizarUsuario(Usuario usuario) {

		// Validação pelo Id se o usuário existe

		if (usuarioRepository.findById(usuario.getId()).isPresent()) {

			// Valida se o usuário já existe antes de atualizar

			Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());

			if (buscaUsuario.isPresent()) {

				if (buscaUsuario.get().getId() != usuario.getId())
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
			}

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			String senhaEncoder = encoder.encode(usuario.getSenha());
			usuario.setSenha(senhaEncoder);

			return Optional.of(usuarioRepository.save(usuario));

		} else {

			// Se não existir lança uma Exception do tipo Response Status Not Found

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);

		}

	}

	public boolean excluirUsuario(Long id) {

		if (usuarioRepository.findById(id).isPresent()) {

			usuarioRepository.deleteById(id);
			return true;

		} else {

			return false;

		}
	}

	public Optional<UsuarioLogin> Logar(Optional<UsuarioLogin> usuarioLogin) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

		if (usuario.isPresent()) {

			if (encoder.matches(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

				String auth = usuarioLogin.get().getUsuario() + ":" + usuarioLogin.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);

				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setSenha(usuario.get().getSenha());
				usuarioLogin.get().setTipo(usuario.get().getTipo());
				usuarioLogin.get().setToken(authHeader);
				return usuarioLogin;

			}
		}

		// Lança uma Exception do tipo Response Status Unauthorized

		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos!", null);

	}

}
