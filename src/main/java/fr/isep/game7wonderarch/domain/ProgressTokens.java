package fr.isep.game7wonderarch.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * classe définissant les Token de Progrès
 */
public class ProgressTokens {

	private ProgressTokens(){

	}

	/**
	 * liste de l'ensemble des tokens
	 */
	public static final List<ProgressToken> TOKENS = Collections.unmodifiableList(createTokenList());

	/**
	 * initialisation de la liste des token, 1 de chaque sauf 2 pour culture. les tokens sont mélangés
 	 */
	private static List<ProgressToken> createTokenList() {
		List<ProgressToken> res = new ArrayList<ProgressToken>();
		res.addAll(Arrays.asList(ProgressToken.values()));
		res.add(ProgressToken.Culture);

		// on doit mélanger les Token de Progrès
		Collections.shuffle(res);
		return res;
	}

}
