package com.AppRH.AppRH.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.AppRH.AppRH.models.Candidato;
import com.AppRH.AppRH.models.Ferias;
import com.AppRH.AppRH.models.Funcionario;
import com.AppRH.AppRH.models.Vaga;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Long>{
	
	Funcionario findById(long id);
	Funcionario findByNome(String nome);
	
	// Query para a busca
	@Query(value = "select u from Funcionario u where u.nome like %?1%")
	List<Funcionario>findByNomes(String nome);

}
