package com.sprintboot.mercadoLibre.service.validate;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sprintboot.mercadoLibre.entity.Human;
import com.sprintboot.mercadoLibre.exception.InvalidDnaExeption;
import com.sprintboot.mercadoLibre.exception.RepositoryExeption;
import com.sprintboot.mercadoLibre.repository.HumansRepository;

@Component
public class MutantImpl implements Mutant {

	@Autowired
	private HumansRepository humansRepository;

	private static final int MATCHES = 2;
	private static final int MIN_VALUE_DNA = 4;
	private static final int PATTERN_MUTANT_DNA = 4;

	@Override
	public boolean isMutant(String[] dna) throws InvalidDnaExeption, RepositoryExeption {
		int matches = 0;
		boolean isMutant = false;
		// validate dna
		validateDna(dna);
		// horizontal --\
		matches = horizontalAnalyze(matches, dna);
		if (matches < MATCHES) {
			// vertical |
			matches = verticalAnalyze(matches, dna);
			if (matches < MATCHES) {
				// oblique to left \
				matches = obliqueAnalyze((dna.length - MIN_VALUE_DNA), 0, +1, matches, dna);
				if (matches < MATCHES) {
					// oblique to right /
					matches = obliqueAnalyze((dna.length - 1), (dna.length - 1), -1, matches, dna);
				}
			}
		}
		isMutant = matches >= MATCHES;
		saveHuman(String.join("-", dna), isMutant);
		return isMutant;
	}

	private void saveHuman(String dnaString, boolean isMutant) throws RepositoryExeption {
		try {
			Human humanDNA = humansRepository.findByDna(dnaString);
			if (humanDNA == null || dnaString.equalsIgnoreCase(humanDNA.getDna())) {
				humanDNA = new Human(dnaString, isMutant);
				humansRepository.save(humanDNA);
			}

		} catch (Exception e) {
			throw new RepositoryExeption("Fail in Database to save dna. " + e.getMessage());
		}
	}

	private void validateDna(String[] dna) throws InvalidDnaExeption {
		if(dna == null) {
			throw new InvalidDnaExeption("Ilegal Structure in DNA. "
					+ "{"
					+ "dna:[ sequence DNA ]"
					+ "}"
					+ "");
		}
		int nRow = dna.length;
		if (nRow >= MIN_VALUE_DNA) {
			for (int i = 0; i < nRow; i++) {
				// This method validate characters
				validateCharacterDna(dna[i]);
				dna[i] = dna[i].toUpperCase();
				// This method validate structure
				validateStructureDna(dna[i], nRow);

			}
		} else {
			throw new InvalidDnaExeption("Ilegal Structure in DNA. N:" + nRow);
		}
	}

	private void validateStructureDna(String dnaX, int l) throws InvalidDnaExeption {
		if (dnaX.length() != l) {
			// ilegal Structure
			throw new InvalidDnaExeption("Ilegal Structure in DNA");
		}
	}

	private void validateCharacterDna(String dnaX) throws InvalidDnaExeption {
		// Character valid for dna
		Pattern p = Pattern.compile("[ATGC]+", Pattern.CASE_INSENSITIVE);
		// The valid characters are A-T-G-C
		if (!p.matcher(dnaX).matches()) {
			throw new InvalidDnaExeption("Invalid characters in DNA");
		}
	}

	private int verticalAnalyze(int matches, String[] dna) {
		for (int j = 0; j < dna.length; j++) {
			for (int i = 0; i <= (dna.length - MIN_VALUE_DNA); i++) {
				if (verticalPatternAnalyze(i, j, dna)) {
					matches++;
					if (matches >= MATCHES) {
						return matches;
					}
				}
			}
		}
		return matches;
	}

	private boolean verticalPatternAnalyze(int i, int j, String[] dna) {
		// Study four element in vertical
		for (int k = (i + (PATTERN_MUTANT_DNA - 1)); k >= i; k--) {
			if (dna[i].charAt(j) != dna[k].charAt(j)) {
				return false;
			}
		}
		return true;
	}

	private int horizontalAnalyze(int matches, String[] dna) {
		for (int i = 0; i < dna.length; i++) {
			for (int j = 0; j <= (dna.length - MIN_VALUE_DNA); j++) {
				if (horizontalPatternAnalyze(i, j, dna)) {
					matches++;
					if (matches >= MATCHES) {
						return matches;
					}

				}
			}
		}
		return matches;
	}

	private boolean horizontalPatternAnalyze(int i, int j, String[] dna) {
		for (int k = (j + PATTERN_MUTANT_DNA - 1); k >= j; k--) {
			if (dna[i].charAt(j) != dna[i].charAt(k)) {
				return false;
			}
		}
		return true;
	}

	private int obliqueAnalyze(int iEnd, int jPosition, int direction, int matches, String[] dna) {
		int iterateIni = 0;
		for (int i = 0; i <= iEnd; i++) {
			if (i == 0) {
				if (direction == 1) {
					for (int j = (dna.length - MIN_VALUE_DNA); j >= 0; j--) {
						iterateIni++;
						if (patternObliqueAnalyze(i, j, iterateIni, direction, dna)) {
							matches++;
							if (matches >= MATCHES) {
								return matches;
							}
						}
					}
				}
				if (direction == -1) {
					for (int j = (MIN_VALUE_DNA - 1); j <= dna.length - 1; j++) {
						iterateIni++;
						if (patternObliqueAnalyze(i, j, iterateIni, direction, dna)) {
							matches++;
							if (matches >= MATCHES) {
								return matches;
							}
						}
					}
				}
			} else {
				iterateIni--;
				if (iterateIni > 0 && patternObliqueAnalyze(i, jPosition, iterateIni, direction, dna)) {
					matches++;
					if (matches >= MATCHES) {
						return matches;
					}
				}
			}
		}
		return matches;
	}

	private boolean patternObliqueAnalyze(int i, int j, int interate, int direction, String[] dna) {
		for (int q = 0; q < interate; q++) {
			for (int k = (MIN_VALUE_DNA - 1); k > 0; k--) {
				if (dna[j + direction * q].charAt(i + q) != dna[j + direction * q + direction * k].charAt(i + q + k)) {
					break;
				}
				if (k == 1) {
					return true;
				}
			}
		}
		return false;
	}

}
