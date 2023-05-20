package com.AppRH.AppRH.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.AppRH.AppRH.models.Acessos;
import com.AppRH.AppRH.models.Funcionario;

public interface AcessosRepository extends CrudRepository<Acessos, Long> {

	Iterable<Acessos> findByFuncionario(Funcionario funcionario);

	// para o método delete por CPF
	Acessos findByCpf(String cpf);
	
	Acessos findById(long id);
	List<Acessos> findByNome(String nome);

	// Query para a busca
	@Query(value = "select u from Acessos u where u.nome like %?1%")
	List<Acessos> findByNomesAcessos(String nome);

}
