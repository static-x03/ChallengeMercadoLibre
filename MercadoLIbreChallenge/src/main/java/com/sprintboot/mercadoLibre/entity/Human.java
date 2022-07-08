package com.sprintboot.mercadoLibre.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "humans")
@Getter @Setter 
public class Human {
	
	public Human() {
		
	}

	public Human(String dna, boolean isMutant) {
		super();
		this.dna = dna;
		this.isMutant = isMutant;
	}
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	public Integer id;
	
	@Column(name = "dna", unique = true)
	private String dna;
	
	private boolean isMutant;
	
	 public String toString() {
	        return String.format(
	                "Person[id=%s, dna='%s', isMuntat='%s']",
	                id, dna, isMutant);
	    }
	
}
