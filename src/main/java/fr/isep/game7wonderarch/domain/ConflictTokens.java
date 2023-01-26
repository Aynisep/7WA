package fr.isep.game7wonderarch.domain;

/**
 * définition des Token de gestion de conflits
 */
public enum ConflictTokens {

    /**
     * Trois tokens pour deux joueurs
     */
    deux_joueurs(2, "Deux joueurs", 3),

    /**
     * Trois tokens pour trois joueurs
     */
    trois_joueurs(3, "Trois joueurs", 3),

    /**
     * quatre tokens pour quatre joueurs
     */
    quatre_joueurs(4, "Quatre joueurs", 4),

    /**
     * cinq tokens pour cinq joueurs
     */
    cinq_joueurs(5, "Cinq joueurs", 5),

    /**
     * Six tokens pour six joueurs
     */
    six_joueurs(6, "Six joueurs", 6),

    /**
     * Six tokens pour sept joueurs
     */
    sept_joueurs(7, "sept joueurs", 6);

    // ------------------------------------------------------------------------

    /**
     * Utiliser pour la sélection du nombre de joueurs
     */
    public final String displayName;

    /**
     * Utiliser pour la sélection du nombre de joueurs
     */
    public final int numberOfPlayers;

    /**
     * la quantité de Token associé dans le jeu
     */
    public final int quantity;

    /**
     * le texte correspondant au nombre de joueurs
     * @return le texte correspondant au nombre de joueurs
     */
    public String getDisplayName() {
        return displayName;
    }

    // ------------------------------------------------------------------------

    /**
     * Constructeur de la classe
     * @param displayName le nombre de joueurs en texte
     * @param quantity  le nombre de Token Conflit à utiliser pendant la partie
     */
    ConflictTokens(int numberOfPlayers, String displayName, int quantity) {
        this.numberOfPlayers = numberOfPlayers;
        this.displayName = displayName;
        this.quantity = quantity;
    }

    /**
     * détail des Tokens en fonction du nombre de Joueurs
     * @return un représensation la mise ne place de Token Conflict en fonction du nombre de joueurs
     */
    public String toString() {
        StringBuffer sb = new StringBuffer(100);

        sb.append("\n  --- Conflict Tokens --- \n");
        sb.append("\tNombre de joueurs : ").append(displayName).append("\n");
        sb.append("\tNombre de joueurs : [").append(numberOfPlayers).append("]\n");
        sb.append("\tNombre de Token Conflict : ").append(quantity).append("\n");
        return sb.toString();
    }

}
