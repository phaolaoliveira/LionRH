package com.AppRH.AppRH.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.AppRH.AppRH.models.Acessos;
import com.AppRH.AppRH.models.Beneficios;
import com.AppRH.AppRH.models.Funcionario;

public interface BeneficiosRepository extends CrudRepository<Beneficios, Long> {
	
	Iterable<Beneficios> findByFuncionario(Funcionario funcionario);

	// para o método delete por CPF
	Beneficios findByCpf(String cpf);
	
	Acessos findById(long id);
	List<Beneficios> findByNome(String nome);

	// Query para a busca
	@Query(value = "select u from Beneficios u where u.nome like %?1%")
	List<Beneficios> findByNomesBeneficios(String nome);
}
