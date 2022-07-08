package com.sprintboot.mercadoLibre.service;


import org.springframework.http.ResponseEntity;

import com.sprintboot.mercadoLibre.entity.DNA;
import com.sprintboot.mercadoLibre.entity.Human;
import com.sprintboot.mercadoLibre.entity.StatsPerson;
import com.sprintboot.mercadoLibre.exception.InvalidDnaExeption;
import com.sprintboot.mercadoLibre.exception.RepositoryExeption;
import com.sprintboot.mercadoLibre.exception.ServiceExeption;

public interface HumanService {
	
	ResponseEntity<String> isMutant(DNA dna) throws ServiceExeption, InvalidDnaExeption, RepositoryExeption;
	StatsPerson getStats() throws ServiceExeption, RepositoryExeption;
	public Iterable<Human> getAll();
}
