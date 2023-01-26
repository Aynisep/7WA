package fr.isep.game7wonderarch.domain;

/**
 * Classe enumération de l'ensemble des Merveilles
 */
public enum Wonder {

	/**
	 * Le Phare d'Alexandrie
	 */
	Alexandrie("Alexandrie", "Alexandrie", //
			"Prenez la première carte d'une pioche au choix, n'importe où sur la table, et posez-la devant vous",
			"deck-alexandrie.png"),

	/**
	 * Halicarnasse
	 */
	Halicarnasse("Halicarnasse", "Halicarnasse", //
			"Prenez les 5 premières cartes de la pioche à votre gauche ou à votre droite, choisissez-en 1 et posez-la devant vous" //
			+ "Mélangez-les cartes restantes dans leur pioche",
			"deck-halicarnasse.png"),

	/**
	 * Ephèse
	 */
	Ephese("Ephese", "Ephèse", //
			"Prenez la première carte de la pioche centrale et posez-la devant vous",
			"deck-ephese.png"),

	/**
	 * La statue de Zeux
	 */
	Olympie("Olympie", "Olympie", //
			"Prenez la première carte de la pioche à votre gauche et de celle à votre droite, et posez-les devant vous",
			"deck-olympie.png"),

	/**
	 * les jardins suspendus de Babylon
	 */
	Babylone("Babylone", "Babylone", //
			"Choisissez 1 jeton Progrès parmi les 4 disponibles, et posez-le devant vous",
			"deck-babylon.png"),

	/**
	 * Le colosse de Rhodes
	 */
	Rhodes("Rhodes", "Rhodes", //
			"Ajoutez 1 Bouclier à votre total de Boucliers",
			"deck-rhodes.png"),

	/**
	 * La pyramide de Gizeh
	 */
	Gizeh("Gizeh", "Gizeh", //
			"Cette merveille n'a pas d'effet particulier, mais rapporte plus de points de victoire que les autres Merveilles",
			"deck-gizeh.png");
	
	// ------------------------------------------------------------------------

	/**
	 * le nom de la merveille en anglais
	 */
	public final String displayName;

	/**
	 * le nom francophone de ma merveille
	 */
	public final String frenchName;

	/**
	 * description des effets donnés par la merveille
	 */
	public final String effectDescription;

	/**
	 * l'url pour afficher la carte
	 */
	public final String url;

	/**
	 * le nom de la merveille
	 * @return le nom de la merveille en anglais
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * le nom en francais de la merveille
	 * @return le nom de la merveille en francais
	 */
	public String getFrenchName() {
		return frenchName;
	}

	/**
	 * les effets de la merveille
	 * @return la description des effets liées à la merveille
	 */
	public String getEffectDescription() {
		return effectDescription;
	}


	/**
	 * l'url de la représenation image de la merveille
	 * @return l'url d'accès à la carte
	 */
	public String getUrl() {
		return url;
	}
	
	// ------------------------------------------------------------------------

	/**
	 * Constructeur de la classe
	 * @param displayName la description en anglais
	 * @param frenchName  la description en francais
	 * @param effectDescription l'effet octroyé par la merveille
	 * @param url l'url de l'image
	 * */
	 Wonder(String displayName, String frenchName, String effectDescription, String url) {
		this.displayName = displayName;
		this.frenchName = frenchName;
		this.effectDescription = effectDescription;
		this.url = "images/decks/" + url;
	}

	/**
	 * détail de la merveille
	 * @return un représensation sous la forme d'une string de la merveille
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer(100);

		sb.append("\n  --- la merveille --- \n");
		sb.append("\tName : ").append(displayName).append("\n");
		sb.append("\tNom : ").append(frenchName).append("\n");
		sb.append("\tDescription: ").append(effectDescription).append("\n");
		sb.append("\turl: ").append(url).append("\n");
		return sb.toString();
	}


}
