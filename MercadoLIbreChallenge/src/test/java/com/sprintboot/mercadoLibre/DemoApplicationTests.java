package com.sprintboot.mercadoLibre;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.sprintboot.mercadoLibre.entity.Human;
import com.sprintboot.mercadoLibre.entity.StatsPerson;
import com.sprintboot.mercadoLibre.exception.InvalidDnaExeption;
import com.sprintboot.mercadoLibre.exception.ManagerExeption;
import com.sprintboot.mercadoLibre.exception.RepositoryExeption;
import com.sprintboot.mercadoLibre.repository.HumansRepository;
import com.sprintboot.mercadoLibre.service.validate.MutantImpl;
import com.sprintboot.mercadoLibre.service.validate.StatsImpl;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@InjectMocks
	MutantImpl mutantsManagerImpl;
	@InjectMocks
	StatsImpl statsManagerImpl;

	@Mock
	HumansRepository personRepository;

	@Test
	public void testIsMutantInvalidLenght() throws RepositoryExeption {
		// DNA N = 3
		// throw new InvalidDnaExeption("Ilegal Structure in DNA. N:"+nRow);
		String[] dna = { "AAA", "AAA", "AAA" };
		try {
			mutantsManagerImpl.isMutant(dna);
		} catch (InvalidDnaExeption e) {
			assertTrue("Ilegal Structure in DNA. N:3".equals(e.getMessage()));
		}

	}

	@Test
	public void testIsMutantInvalidCharacter() throws RepositoryExeption {
		// DNA {"ATGC","ATGC","ATGC","ATGX"} X is character invalid
		// throw new InvalidDnaExeption("Invalid characters in DNA");
		String[] dna = { "ATGC", "ATGC", "ATGC", "ATGX" };
		try {
			mutantsManagerImpl.isMutant(dna);
		} catch (InvalidDnaExeption e) {
			assertTrue("Invalid characters in DNA".equals(e.getMessage()));
		}

	}

	@Test
	public void testIsMutantInvalidStructureDna() throws RepositoryExeption {
		// DNA {"ATGC","ATGC","ATGC","ATGC","ATGC"} NxN -> 4x5 is invalid
		// throw new InvalidDnaExeption("Ilegal Structure in DNA");
		String[] dna = { "ATGC", "ATGC", "ATGC", "ATGC", "ATGC" };
		try {
			mutantsManagerImpl.isMutant(dna);
		} catch (InvalidDnaExeption e) {
			assertTrue("Ilegal Structure in DNA".equals(e.getMessage()));
		}
	}

	@Test
	public void testIsMutantIsTrueAndWillSaveInBD() throws RepositoryExeption, InvalidDnaExeption {
		// DNA {"AAAA","AAAA","AAAA","AAAA"}
		String[] dna = { "AAAA", "AAAA", "AAAA", "AAAA" };
		when(personRepository.findByDna(Mockito.anyString())).thenReturn(null);
		mutantsManagerImpl.isMutant(dna);

	}

	@Test
	public void testIsMutantIsTrueAndWontSaveInBD() throws RepositoryExeption, InvalidDnaExeption {
		// DNA {"AAAA","AAAA","AAAA","AAAA"}
		String[] dna = { "AAAA", "AAAA", "AAAA", "AAAA" };
		Human personM = new Human(String.join("-", dna), true);
		when(personRepository.findByDna(Mockito.anyString())).thenReturn(personM);
		mutantsManagerImpl.isMutant(dna);
	}

	@Test
	public void testIsMutantIsTrueInHorizontalAnalize() throws RepositoryExeption, InvalidDnaExeption {
		// DNA {"AAAA","AAAA","AAAA","AAAA"}
		String[] dna = { "AAAAGT", "TTGCGG", "ATATAT", "TAGCGA", "ATACGT", "ACCCCT" };
		when(personRepository.findByDna(Mockito.anyString())).thenReturn(null);
		assertTrue(mutantsManagerImpl.isMutant(dna));

	}

	@Test
	public void testIsMutantIsTrueInVerticalAnalize() throws RepositoryExeption, InvalidDnaExeption {
		// DNA {"AAAA","AAAA","AAAA","AAAA"}
		String[] dna = { "AAACGT", "TTGCGG", "ATATGT", "AAGCGA", "ATGCAT", "ATGCGT" };
		when(personRepository.findByDna(Mockito.anyString())).thenReturn(null);
		mutantsManagerImpl.isMutant(dna);
		assertTrue(mutantsManagerImpl.isMutant(dna));
	}

	@Test
	public void testIsMutantIsTrueInObliqueUpAnalize() throws RepositoryExeption, InvalidDnaExeption {
		// DNA {"AAAA","AAAA","AAAA","AAAA"}
		String[] dna = { "AAACCT", "TTGAGG", "ATATAT", "AAGCGA", "CTACAT", "ATGAGT" };
		when(personRepository.findByDna(Mockito.anyString())).thenReturn(null);
		mutantsManagerImpl.isMutant(dna);
		assertTrue(mutantsManagerImpl.isMutant(dna));
	}

	@Test
	public void testIsMutantIsTrueInObliqueDownAnalize() throws RepositoryExeption, InvalidDnaExeption {
		// DNA {"AAAA","AAAA","AAAA","AAAA"}
		String[] dna = { "AAACCT", "TTCTGG", "ACATAC", "CGGCCA", "CTACAT", "ATCAGT" };
		when(personRepository.findByDna(Mockito.anyString())).thenReturn(null);
		mutantsManagerImpl.isMutant(dna);
		assertTrue(mutantsManagerImpl.isMutant(dna));
	}

	@Test
	public void testIsMutantIsFalse() throws RepositoryExeption, InvalidDnaExeption {
		// DNA {"AAAA","AAAA","AAAA","AAAA"}
		String[] dna = { "AAACCT", "TTCTGG", "ACATAC", "CGGCTA", "CTACAT", "ATCAGT" };
		when(personRepository.findByDna(Mockito.anyString())).thenReturn(null);
		mutantsManagerImpl.isMutant(dna);
		assertFalse(mutantsManagerImpl.isMutant(dna));
	}

	@Test
	public void testGetStats() throws ManagerExeption, RepositoryExeption {
		List<Human> personsH = new ArrayList<Human>();
		List<Human> personsM = new ArrayList<Human>();
		String dnaH = "CAGT-CAGT-CAGT-CAGT";
		String dnaM = "CAGT-ATCG-CAGT-ATCG";
		Human personM = new Human(dnaM, true);
		System.out.println(personM.toString());
		Human personH = new Human(dnaH, false);
		System.out.println(personH.toString());
		personsH.add(personH);
		personsM.add(personM);
		StatsPerson personStats = new StatsPerson(personsH.size(), personsM.size());
		when(personRepository.findByIsMutant(true)).thenReturn(personsH);
		when(personRepository.findByIsMutant(false)).thenReturn(personsM);

		assertTrue(personM.getDna().equals(dnaM));
		assertTrue(personH.getDna().equals(dnaH));

		assertTrue(personM.isMutant());
		assertTrue(!personH.isMutant());

		assertTrue(personStats.getCount_human_dna() == statsManagerImpl.getStats().getCount_human_dna());
		assertTrue(personStats.getCount_mutant_dna() == statsManagerImpl.getStats().getCount_mutant_dna());
		assertTrue(personStats.getRatio() == statsManagerImpl.getStats().getRatio());
	}

}
