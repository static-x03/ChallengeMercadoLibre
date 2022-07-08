package com.sprintboot.mercadoLibre.service.validate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprintboot.mercadoLibre.entity.Human;
import com.sprintboot.mercadoLibre.entity.StatsPerson;
import com.sprintboot.mercadoLibre.exception.RepositoryExeption;
import com.sprintboot.mercadoLibre.repository.HumansRepository;

@Component
public class StatsImpl implements Stats {

	@Autowired
	private HumansRepository humansRepository;

	@Override
	public StatsPerson getStats() throws RepositoryExeption {
		List<Human> humans = new ArrayList<Human>();
		List<Human> mutants = new ArrayList<Human>();
		try {
			humans = humansRepository.findByIsMutant(false);
			mutants = humansRepository.findByIsMutant(true);
		} catch (Exception e) {
			throw new RepositoryExeption("Error to get table person in database");
		}
		StatsPerson statsPerson = new StatsPerson(humans.size(), mutants.size());
		return statsPerson;
	}

	@Override
	public Iterable<Human> findAll() {
		return humansRepository.findAll();
	}

}
