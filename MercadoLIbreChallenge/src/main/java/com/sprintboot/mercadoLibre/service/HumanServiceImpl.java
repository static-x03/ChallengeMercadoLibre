package com.sprintboot.mercadoLibre.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sprintboot.mercadoLibre.entity.DNA;
import com.sprintboot.mercadoLibre.entity.Human;
import com.sprintboot.mercadoLibre.entity.StatsPerson;
import com.sprintboot.mercadoLibre.exception.InvalidDnaExeption;
import com.sprintboot.mercadoLibre.exception.RepositoryExeption;
import com.sprintboot.mercadoLibre.exception.ServiceExeption;
import com.sprintboot.mercadoLibre.service.validate.Mutant;
import com.sprintboot.mercadoLibre.service.validate.Stats;

@Service
public class HumanServiceImpl implements HumanService{

	@Autowired
	private Mutant mutant;
	@Autowired
	private Stats stats;
	
	private static final Logger logger = LoggerFactory.getLogger(HumanServiceImpl.class);
	

	@Override
	public ResponseEntity<String> isMutant(DNA dna) throws ServiceExeption, InvalidDnaExeption, RepositoryExeption{
		ResponseEntity<String> response = null;
		if(mutant.isMutant(dna.getDna())) {
			response = new ResponseEntity<>(HttpStatus.OK);
			logger.info("HttpStatus: 200");
		}else {
			logger.info("HttpStatus: 403");
			response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return response;
	}

	@Override
	public StatsPerson getStats() throws RepositoryExeption{
		StatsPerson personStats = stats.getStats();
		return personStats;
	}

	@Override
	public Iterable<Human> getAll() {
		return stats.findAll();
	}

	
	
	

}
