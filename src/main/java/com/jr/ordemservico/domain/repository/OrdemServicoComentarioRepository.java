package com.jr.ordemservico.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jr.ordemservico.domain.entity.OrdemServicoComentario;

@Repository
public interface OrdemServicoComentarioRepository extends JpaRepository<OrdemServicoComentario, Long>{

}
