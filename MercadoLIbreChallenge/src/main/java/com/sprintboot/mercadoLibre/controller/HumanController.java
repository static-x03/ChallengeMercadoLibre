package com.sprintboot.mercadoLibre.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sprintboot.mercadoLibre.entity.DNA;
import com.sprintboot.mercadoLibre.entity.Human;
import com.sprintboot.mercadoLibre.entity.StatsPerson;
import com.sprintboot.mercadoLibre.exception.InvalidDnaExeption;
import com.sprintboot.mercadoLibre.exception.RepositoryExeption;
import com.sprintboot.mercadoLibre.exception.ServiceExeption;
import com.sprintboot.mercadoLibre.service.HumanService;

@RestController
public class HumanController {

	@Autowired
	private HumanService humanService;
	private static final Logger logger = LoggerFactory.getLogger(HumanController.class);

	@GetMapping("/")
	public ResponseEntity<?> getServiceName() {
		Map<String, Object> body = new HashMap<>();
		body.put("message", "run isMutant service");
		ResponseEntity<?> responseEntity = new ResponseEntity<>(body,HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping(value = "/mutant", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> isMutant(@RequestBody @Valid DNA dna)
			throws ServiceExeption, InvalidDnaExeption, RepositoryExeption {
		ResponseEntity<String> response = null;
		logger.info("run isMutant service");
		response = humanService.isMutant(dna);

		return response;

	}

	@RequestMapping(value = "/stats", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	StatsPerson stats() throws ServiceExeption, RepositoryExeption {
		// "{“count_mutant_dna”:40, “count_human_dna”:100: “ratio”:0.4}"
		StatsPerson personStats = null;
		try {
			logger.info("run getStats service");
			personStats = humanService.getStats();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return personStats;
	}
	
	@GetMapping("/mutants")
	public List<Human> readAll(){
		List<Human> humans = StreamSupport
				.stream(humanService.getAll().spliterator(), false)
				.collect(Collectors.toList());
		return humans;
	}
}
