package fr.isep.game7wonderarch.domain;

/**
 * classe qui défnit les tokens de progrès
 */
public enum ProgressToken {

	/**
	 * Token de type Urbanisme
	 */
	Urbanism("urbanism", "urbanisme", //
			"Lorsque vous prenez une carte grise 'bois' ou 'brique', " //
			+ "choisissez 1 carte supplémentaire parmi les 3 disponibles et posez-la devant vous", //
			"token-culture.png"),
	/**
	 * Token de type Artisanat
	 */
	ArtsAndCrafts("Arts and crafts", "artisanat", //
			"Lorsque vous prenez une carte grise 'papier' ou 'verre', " //
			+ "choisissez 1 carte suppl�mentaire parmi les 3 disponibles et posez-la devant vous", //
			"token-artsAndCrafts.png"),

	/**
	 * Token de type Joaillerie
	 */
	Jewelry("jewelry", "joaillerie", //
			"Lorsque vous prenez une carte grise 'pierre' ou une carte jaune, " //
			+ "choisissez 1 carte suppl�mentaire parmi les 3 disponibles et posez-la devant vous", //
			"token-jewelry.png"),

	/**
	 * Token de type Science
	 */
	Science("science", "science", //
			"Lorsque vous prenez une carte verte, " // 
			+ "choisissez 1 carte suppl�mentaire parmi les 3 disponibles et posez-la devant vous", //
			"token-science.png"),

	/**
	 * Token de type Propagande
	 */
	Propaganda("propaganda", "propagande", // 
			"Lorsque vous prenez une carte Rouge avec 1 ou 2 ic�nes 'corne', " //
			+ "choisissez 1 carte supplémentaire parmi les 3 disponibles et posez-la devant vous", //
			"token-propaganda.png"),

	/**
	 * Token de type Architecture
	 */
	Architecture("architecture", "architecture", // 
			"Lorsque vous construisez un étape, " //
			+ "choisissez 1 carte parmi les 3 disponibles et posez-la devant vous", //
			"token-architecture.png"),

	/**
	 * Token de type Economie
	 */
	Economy("economy", "économie", //
			"1 carte jaune en votre possession vous offre 2 pi�ces au lieu d'une seule", //
			"token-economy.png"),

	/**
	 * Token de type Ingénierie
	 */
	Ingeniery("ingeniery", "ingénierie", //
			"Lorsque vous construisez un étape, "
			+ "vous pouvez utiliser n'importe quelle ressource sans tenir compte de la restriction 'identique' ou 'diff�rente'", //
			"token-ingeniery.png"),

	/**
	 * Token de type Tactique
	 */
	Tactic("tactic", "tactique", //
			"Ajoutez 2 Boucliers à votre total de Boucliers", //
			"token-tactic.png"),

	/**
	 * Token de type Décoration
	 */
	Decoration("decoration", "décoration", //
			"A la fin de la partie, " // 
			+ "gagnez 4 Points de victoire si votre Merveille est encore en construction, " //
			+ "ou 6 Points de victoire si vous avez terminé sa construction", //
			"token-decoration.png"),

	/**
	 * Token de type Politique
	 */
	Politic("politic", "politique", //
			"A la fin de la partie, " // 
			+ "gagnez 1 Point de victoire par îcone 'chat', présente sur vos cartes bleues", //
			"token-politic.png"),

	/**
	 * Token de type Stratégie
	 */
	Strategy("strategy", "stratégie", //
			"A la fin de la partie, " //
			+ "gagnez 1 Point de victoire par jeton militaire en votre possession", //
			"token-strategy.png"),

	/**
	 * Token de type Education
	 */
	Education("education", "éducation", //
			"A la fin de la partie, " // 
			+ "gagnez 2 Points de victoire par jeton de Progrès en votre possession (celui-ci y compris)", //
			"token-education.png"),

	/**
	 * Token de type Culture
	 */
	Culture("culture", "culture", //
			"Ce jeton est présent en 2 exemplaires. A la fin de la partie, " //
			+ " gagnez 4 Points de victoire si vous possédez 1, ou 12 Points de victoire si vous possédez les 2", //
			"token-culture.png");
	
	// ------------------------------------------------------------------------

	/**
	 * affiche le Token en Anglais
	 */
	public final String displayName;

	/**
	 * Affiche le Token en français
	 */
	public final String frenchName;

	/**
	 * décrit l'effet du Token
	 */
	public final String effectDescription; // in french

	/**
	 * lien vers l'image associée au Token
	 */
	public final String imageResource;
	
	// ------------------------------------------------------------------------

	/**
	 * Constructeur
	 * @param displayName la nom en anglais
	 * @param frenchName le nom en francais
	 * @param effectDescription description de l'action du Token
	 * @param imageResource le nom de l'image qui représente le Token
	 */
	private ProgressToken(String displayName, String frenchName, String effectDescription, String imageResource) {
		this.displayName = displayName;
		this.frenchName = frenchName;
		this.effectDescription = effectDescription;
		this.imageResource = "images/tokens-progress/" + imageResource;
	}

}
