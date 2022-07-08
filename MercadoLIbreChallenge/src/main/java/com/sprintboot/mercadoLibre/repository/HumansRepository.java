package com.sprintboot.mercadoLibre.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sprintboot.mercadoLibre.entity.Human;

@Repository
public interface HumansRepository extends CrudRepository<Human, Integer>{

	
	public Human findByDna(String dna);
	public List<Human> findByIsMutant(boolean isMuntat);
}
