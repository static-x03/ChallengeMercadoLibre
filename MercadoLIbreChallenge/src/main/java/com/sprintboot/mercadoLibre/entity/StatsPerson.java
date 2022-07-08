package com.sprintboot.mercadoLibre.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StatsPerson {

	private double count_mutant_dna;
	private double count_human_dna;
	private double ratio;
	
	public StatsPerson(double count_human_dna, double count_mutant_dna) {
		super();
		this.count_mutant_dna = count_mutant_dna;
		this.count_human_dna = count_human_dna;
		this.ratio = ((count_human_dna != 0) ? (Math.round((count_mutant_dna / count_human_dna) * 10000.0) / 10000.0) : 0);
	}
}
