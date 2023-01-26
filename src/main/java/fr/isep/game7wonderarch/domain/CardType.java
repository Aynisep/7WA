package fr.isep.game7wonderarch.domain;

/**
 * type of cards
 */
public enum CardType {

	// Material Cards
	/**
	 * carte grise : bois
	 */
	CardMaterialWood("material:wood", Material.Wood, "card-material-wood-lumberjack.png"), // du bois
	/**
	 * carte grise : papier
	 */
	CardMaterialPaper("material:paper", Material.Paper, "card-material-paper-women.png"), // du papier
	/**
	 * carte grise : briques
	 */
	CardMaterialBrick("material:brick", Material.Brick, "card-material-brick-women.png"), // une brique
	/**
	 * carte grise : pierre
	 */
	CardMaterialStone("material:stone", Material.Stone, "card-material-stone-stonecutter.png"),
	/**
	 * carte grise : verre
	 */
	CardMaterialGlass("material:glass", Material.Glass, "card-material-glass-women.png"), //
	/**
	 * carte jaune : l'or, peut remplacer n'importe quelle carte grise
	 */
	CardMaterialGold("material:gold", Material.Gold, "card-material-gold-vizir.png"), //
	
	// Science cards
	/**
	 * carte verte : law
	 */
	CardScienceLaw("science:law", ScienceCategory.Law, "card-progress-law.png"), //
	/**
	 * carte verte : Mechanic
	 */
	CardScienceMechanic("science:mechanic", ScienceCategory.Mechanic, "card-progress-mechanic.png"), //
	/**
	 * carte verte : Architect
	 */
	CardScienceArchitect("science:architect", ScienceCategory.Architect, "card-progress-architect.png"), //

	/**
	 * carte rouge : Barbare : un bouclier, une corne
	 */
	CardWar_barbarian("war:barbarian", 1, 1, "card-war-barbarian-1corn.png"), //
	/**
	 * carte rouge : Centurion : un bouclier, zéro corne
	 */
	CardWar_centurion("war:centurion", 1, 0, "card-war-centurion.png"),  //
	/**
	 * carte rouge : archer : un bouclier, deux cornes
	 */
	CardWar_archer("war:archer", 1, 2, "card-war-archer-2corns.png"), //

	// Polics Cards
	/**
	 * carte bleue : Empereur
	 */
	CardPolitic_emperor("politic:emperor", 3, false, "card-politic-emperor-3laurel.png"), //
	/**
	 * carte bleue : chat
	 */
	CardPolitic_cat("politic:cat", 2, true, "card-politic-women-2laurel-cat.png") //
	;

	/**
	 * le nom de la carte
	 */
	public final String cardDisplayName;

	/**
	 * la catégorie de la carte
	 */
	public final CardCategory cardCategory;
	
	/** material of the card, meaningfull only cardCategory==Material */
	public final Material material;

	/** drawing on the card, meaningfull only cardCategory==Science */
	public final ScienceCategory scienceCategory;

	/** number of shields, meaningfull only cardCategory==War */
	public final int shieldCount;
	/** number of corns, meaningfull only cardCategory==War */
	public final int cornCount;
	
	/** number of laurel, meaningfull only cardCategory==Politic */
	public final int laurelCount;
	/** with cat, meaningfull only cardCategory==Politic */
	public final boolean cat;

	/**
	 * lien vers le fichier image
	 */
	public final String imageResource;

	// ------------------------------------------------------------------------

	/**
	 * constructeur de l'énumeration
	 * @param cardDisplayName affichage du nom de la carte
	 * @param cardCategory type de carte
	 * @param material type de materiel
	 * @param scienceCategory type de science
	 * @param shieldCount nombre de bouclier
	 * @param cornCount nombre de corne
	 * @param laurelCount nombre de laurier de victoire
	 * @param cat le chat
	 * @param imageResource l'utl pour la source de la carte
	 */
	private CardType(String cardDisplayName, CardCategory cardCategory, // 
			Material material, // only when cardCategory==Material
			ScienceCategory scienceCategory, // only when cardCategory==Science
			int shieldCount, int cornCount, // only when cardCategory==War
			int laurelCount, boolean cat, // only when cardCategory==Politic
			String imageResource) {
		this.cardDisplayName = cardDisplayName;
		this.cardCategory = cardCategory;
		this.material = material;
		this.scienceCategory = scienceCategory;
		this.shieldCount = shieldCount;
		this.cornCount = cornCount;
		this.laurelCount = laurelCount;
		this.cat = cat;
		this.imageResource = "images/cards/" + imageResource;
	}
	
	// for Material card
	private CardType(String cardDisplayName, Material material, String imageResource) {
		this(cardDisplayName, CardCategory.MaterialCard, material, null, 0, 0, 0, false, imageResource);
	}
	// for Science card
	private CardType(String cardDisplayName, ScienceCategory scienceCategory, String imageResource) {
		this(cardDisplayName, CardCategory.ProgressCard, null, scienceCategory, 0, 0, 0, false, imageResource);
	}
	// for War card
	private CardType(String cardDisplayName, int shieldCount, int cornCount, String imageResource) {
		this(cardDisplayName, CardCategory.WarCard, null, null, shieldCount, cornCount, 0, false, imageResource);
	}
	// for Politic card
	private CardType(String cardDisplayName, int laurelCount, boolean cat, String imageResource) {
		this(cardDisplayName, CardCategory.PoliticCard, null, null, 0, 0, laurelCount, cat, imageResource);
	}

}
