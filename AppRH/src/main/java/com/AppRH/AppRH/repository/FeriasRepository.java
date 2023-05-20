package com.AppRH.AppRH.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.AppRH.AppRH.models.Ferias;

public interface FeriasRepository extends CrudRepository<Ferias, Long> {
	
	Ferias findByCodigo(long codigo);

	List<Ferias> findByNome(String nome);

	// Query para a busca
	@Query(value = "select u from Ferias u where u.nome like %?1%")
	List<Ferias> findByNomesFerias(String nome);
}
