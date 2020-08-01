package com.jr.ordemservico.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jr.ordemservico.domain.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByNome(String username);
	
	Usuario findByUsuario(String username);
	
	List<Usuario> findByNomeOrderByNomeAsc(String username);
	
	Optional<Usuario> findByUsuarioOrEmail(String username, String email);
}
