package com.jr.ordemservico.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jr.ordemservico.domain.entity.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

}
