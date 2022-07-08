package com.sprintboot.mercadoLibre.service.validate;

import com.sprintboot.mercadoLibre.entity.Human;
import com.sprintboot.mercadoLibre.entity.StatsPerson;
import com.sprintboot.mercadoLibre.exception.RepositoryExeption;

public interface Stats {
	
	public StatsPerson getStats()throws RepositoryExeption;
	public Iterable<Human> findAll();
}
