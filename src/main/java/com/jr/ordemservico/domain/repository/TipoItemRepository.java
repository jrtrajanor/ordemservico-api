package com.jr.ordemservico.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jr.ordemservico.domain.entity.TipoItem;

@Repository
public interface TipoItemRepository extends JpaRepository<TipoItem, Long> {
	
}
