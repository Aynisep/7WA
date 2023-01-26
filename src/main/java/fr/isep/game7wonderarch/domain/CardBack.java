package fr.isep.game7wonderarch.domain;

/**
 * le dos des cartes
 */
public enum CardBack {

	/**
	 * deck central
	 */
	CentralDeck(null), //

	/**
	 * deck alexandrie
	 */
	Alexandrie(Wonder.Alexandrie), //

	/**
	 * deck halicarnasse
	 */
	Halicarnasse(Wonder.Halicarnasse),

	/**
	 * deck ephese
	 */
	Ephese(Wonder.Ephese), //

	/**
	 * deck Olypie
	 */
	Olympie(Wonder.Olympie), //,

	/**
	 * deck babylon
	 */
	Babylone(Wonder.Babylone), //

	/**
	 * deck rhodes
	 */
	Rhodes(Wonder.Rhodes), //

	/**
	 * deck gizeh
	 */
	Gizeh(Wonder.Gizeh); //

	/**
	 * le deck central
	 */
	public final boolean centralDeck;
	// set only when not centralDeck

	/**
	 * le deck des merveilles
	 */
	public final Wonder wonderDeck;

	/**
	 * constructeur
	 * @param wonderDeck la liste des merveilles
	 */
	private CardBack(Wonder wonderDeck) {
		this.centralDeck = (wonderDeck == null);
		this.wonderDeck = wonderDeck;
	}
	
}
