package com.sprintboot.mercadoLibre.service.validate;

import com.sprintboot.mercadoLibre.exception.InvalidDnaExeption;
import com.sprintboot.mercadoLibre.exception.RepositoryExeption;

public interface Mutant {
	public boolean isMutant(String[] dna) throws InvalidDnaExeption, RepositoryExeption;
}
