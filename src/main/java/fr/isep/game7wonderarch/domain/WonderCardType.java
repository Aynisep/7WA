package fr.isep.game7wonderarch.domain;

/**
 * On remprend le modèle des cartes pour afficher les merveilles
 *
 * va chercher les images dans
 * src\\main\\resources\\fr\\isep\\game7wonderarch\\utils\\images
 * ..//..//..//..//
 */
public enum WonderCardType  {

    /****************************************************************************
     * Le phare d'Alexandrie
     ****************************************************************************/

    /**
     * la base d'Alexandrie
     */
    AlexandrieBaseBack("init"                      , 0, "alexandrie/piece-back-alexandrie-1.png"), //
    /**
     * base construite
     */
    AlexandrieBaseFront("base construite"          , 1, "alexandrie/piece-front-alexandrie-1.png"), //
    // etage 1
    /**
     * premier étage à construire
     */
    AlexandrieFirstBack("Attente premier étage"    , 2, "alexandrie/piece-back-alexandrie-2.png"), //
    /**
     * premier étage achevé
     */
    AlexandrieFirstFront("Premier étage achevé"    , 3, "alexandrie/piece-front-alexandrie-2.png"), //
    // etage 2
    /**
     * deuxième étage à construire
     */
    AlexandrieSecontBack("Attente deuxieme étage"  , 4, "alexandrie/piece-back-alexandrie-3.png"), //
    /**
     * deuxième étage achevé
     */
    AlexandrieSecondFront("Deuxième étage achevé"  , 5, "alexandrie/piece-front-alexandrie-3.png"), //
    /**
     * troisième étage à faire
     */
    AlexandrieTroisiemeBack("Attente troisième étage"  , 6, "alexandrie/piece-back-alexandrie-4.png"), //
    /**
     * 3eme etage faite
     */
    AlexandrieTroisiemeFront("Troisième étage achevé"   , 7, "alexandrie/piece-front-alexandrie-4.png"), //
    /**
     * dernier étage à faire
     */
    AlexandrieDernierBack("Attente dernier étage"  , 8, "alexandrie/piece-back-alexandrie-6.png"), //
    /**
     * dernier étage faite
     */
    AlexandrieDernierFront("Dernier étage achevé"   , 9, "alexandrie/piece-front-alexandrie-6.png"), //


    /****************************************************************************
     * Les jardins suspendus
     ****************************************************************************/

    /**
     * la base non constuite
     */
    BabylonBaseBack("init"                      , 0, "babylon/piece-back-babylon-1.png"), //
    /**
     * la base  constuite
     */
    BabylonBaseFront("base construite"          , 1, "babylon/piece-front-babylon-1.png"), //

    /**
     * premier etage non constuit
     */
    BabylonFirstBack("Attente premier étage"    , 2, "babylon/piece-back-babylon-2.png"),
    /**
     * premier etage constuit
     */
    BabylonFirstFront("Premier étage achevé"    , 3, "babylon/piece-front-babylon-2.png"),

    /**
     * dexuième etage non constuit
     */
    BabylonSecontBack("Attente deuxieme étage"  , 4, "babylon/piece-back-babylon-3.png"), //
    /**
     * deuxieme etage constuit
     */

    BabylonSecondFront("Deuxième étage achevé"  , 5, "babylon/piece-front-babylon-3.png"), //

    /**
     * partie gauche du toit non faite
     */
    BabylonTroisiemeBack("Partie gauche du toit non faite"   , 7, "babylon/piece-back-babylon-4.png"), //

    /**
     * partie gauche du toit faite
     */
    BabylonTroisiemeFront("Partie gauche du toit faite"   , 7, "babylon/piece-front-babylon-4.png"), //


    /**
     * partie droit du toit non faite
     */
    BabylonQuatriemeBack("partie droite du toit non faite"  , 6, "babylon/piece-back-babylon-5.png"), //

    /**
     * partie droite du toit faite
     */
    BabylonQuatriemeFront("partie droite du toit faite"   , 7, "babylon/piece-front-babylon-5.png"), //



    /****************************************************************************
     * Ephèse
     ****************************************************************************/

    /**
     * base en construction
     */
    EpheseFirstBack("Attente premier étage"    , 2, "ephese/piece-back-ephese-1.png"), //

    /**
     * base construite
     */
    EpheseFirstFront("Premier étage achevé"    , 3, "ephese/piece-front-ephese-1.png"), //

    /**
     * premier etage en construction
     */
    // etage 2
    EpheseSecontBack("Attente deuxieme étage"  , 4, "ephese/piece-back-ephese-2.png"), //
    /**
     * premier etage construit
     */
    EpheseSecondFront("Deuxième étage achevé"  , 5, "ephese/piece-front-ephese-2.png"), //
    /**
     * deuxième etage en construction
     */
    EpheseTroisiemeBack("Attente troisème étage"  , 6, "ephese/piece-back-ephese-3.png"), //
    /**
     * deuxième etage construit
     */
    EpheseTroisiemeFront("Troisième étage achevé"   , 7, "ephese/piece-front-ephese-3.png"), //
    /**
     * troisieme etage en construction
     */
    EpheseQuatriemeBack("Attente quatrième étage"  , 4, "ephese/piece-back-ephese-4.png"), //
    /**
     * troisième etage construit
     */
    EpheseQuatriemeFront("Quatrième étage achevé"  , 5, "ephese/piece-front-ephese-4.png"), //
    /**
     * dernier etage en construction
     */
    EpheseDernierBack("Attente dernier étage"  , 6, "ephese/piece-back-ephese-6.png"), //
    /**
     * dernier etage construit
     */
    EpheseDernierFront("Dernier étage achevé"   , 7, "ephese/piece-front-ephese-6.png"), //

    /****************************************************************************
     * Gizeh
     ****************************************************************************/
    /**
     * base en construciton
     */
    GizehBaseBack("init"                      , 0, "gizeh/piece-back-gizeh-1.png"), //
    /**
     * base construite
     */
    GizehBaseFront("base construite"          , 1, "gizeh/piece-front-gizeh-1.png"), //
    /**
     * etage 1 en construction
     */
    GizehFirstBack("Attente premier étage"    , 2, "gizeh/piece-back-gizeh-2.png"), //
    /**
     * etage 1 construit
     */
    GizehFirstFront("Premier étage achevé"    , 3, "gizeh/piece-front-gizeh-2.png"), //
    /**
     * etage 2 en construction
     */
    GizehSecondBack("Attente deuxieme étage"  , 4, "gizeh/piece-back-gizeh-3.png"), //
    /**
     * etage 2 construit
     */
    GizehSecondFront("Deuxième étage achevé"  , 5, "gizeh/piece-front-gizeh-3.png"), //
    /**
     * etage 3 en construciotn
     */
    GizehTroisiemeBack("Attente dernier étage"  , 6, "gizeh/piece-back-gizeh-4.png"), //
    /**
     * etage 3 construit fin de la merveille
     */
    GizehTroisiemeFront("Dernierétage achevé"   , 7, "gizeh/piece-front-gizeh-4.png"), //

    /**
     * etage 3 en construciotn
     */
    GizehDernierBack("Attente dernier étage"  , 6, "gizeh/piece-back-gizeh-5.png"), //
    /**
     * etage 3 construit fin de la merveille
     */
    GizehDernierFront("Dernierétage achevé"   , 7, "gizeh/piece-front-gizeh-5.png"), //

    /****************************************************************************
     * Halicarnasse
     ****************************************************************************/
    /**
     * base en construction
     */
    HalicarnasseBaseBack("init"                      , 0, "halicarnasse/piece-back-halicarnasse-1.png"), //
    /**
     * base construite
     */
    HalicarnasseBaseFront("base construite"          , 1, "halicarnasse/piece-front-halicarnasse-1.png"), //
    /**
     * etage 1
     */
    HalicarnasseFirstBack("Attente premier étage"    , 2, "halicarnasse/piece-back-halicarnasse-2.png"), //
    /**
     * etage 1 fait
     */
    HalicarnasseFirstFront("Premier étage achevé"    , 3, "halicarnasse/piece-front-halicarnasse-2.png"), //
    /**
     * etage 2 à faire
     */
    HalicarnasseSecondBack("Attente deuxieme étage"  , 4, "halicarnasse/piece-back-halicarnasse-3.png"), //
    /**
     * etage 2 fait
     */
    HalicarnasseSecondFront("Deuxième étage achevé"  , 5, "halicarnasse/piece-front-halicarnasse-3.png"), //
    /**
     * etage 3 a faire
     */
    HalicarnasseTroisièmeBack("Attente trsoième étage"  , 6, "halicarnasse/piece-back-halicarnasse-4.png"), //
    /**
     * etage 3 fait
     */
    HalicarnasseTroisièmeFront("Troisième étage achevé"   , 7, "halicarnasse/piece-front-halicarnasse-4.png"), //
    /**
     * etage 4 à faire
     */
    HalicarnasseDernierBack("Attente dernier étage"  , 6, "halicarnasse/piece-back-halicarnasse-5.png"), //
    /**
     * etage 4 fait, merveille complète
     */
    HalicarnasseDernierFront("Dernier étage achevé"   , 7, "halicarnasse/piece-front-halicarnasse-5.png"), //

    /****************************************************************************
     * Olympie
     ****************************************************************************/
    /**
     * base faite
     */
    OlympieBase("init"                      , 0, "olympie/piece-front-olympie-1.png"), //
    /**
     * pied gauche fait
     */
    OlympieFirst("Premier étage"    , 2, "olympie/piece-front-olympie-2.png"), //
    /**
     * pied droit fait
     */
    OlympieSecond("Deuxième étage"  , 4, "olympie/piece-front-olympie-3.png"), //
    /**
     * buste fait
     */
    OlympieTroisieme("Troisième étage"  , 6, "olympie/piece-front-olympie-4.png"), //
    /**
     * merveille construite
     */
    OlympieDernier("Dernier étage"  , 6, "olympie/piece-front-olympie-5.png"), //

    /****************************************************************************
     * Rhodes
     ****************************************************************************/
    /**
     * base à faire
     */
    RhodesBaseBack("init"                      , 0, "rhodes/piece-back-rhodes-1.png"), //
    /**
     * pied gauche fait
     */
    RhodesBaseFront("base construite"          , 1, "rhodes/piece-front-rhodes-1.png"), //
    /**
     * pied droit à faire
     */
    RhodesFirstBack("Attente premier étage"    , 2, "rhodes/piece-back-rhodes-2.png"), //
    /**
     * pied droit fait
     */
    RhodesFirstFront("Premier étage achevé"    , 3, "rhodes/piece-front-rhodes-2.png"), //
    /**
     * jambes à faire
     */
    RhodesSecontBack("Attente deuxième étage"  , 4, "rhodes/piece-back-rhodes-3.png"), //
    /**
     * jambes faites
     */
    RhodesSecondFront("Deuxième étage achevé"  , 5, "rhodes/piece-front-rhodes-3.png"), //
    /**
     * buste à faire
     */
    // etage 3
    RhodesTroisiemeBack("Attente troisième étage"  , 6, "rhodes/piece-back-rhodes-4.png"), //
    /**
     * buste fait
     */
    RhodesTroisiemeFront("Troisième étage achevé"   , 7, "rhodes/piece-front-rhodes-4.png"), //
    /**
     * tête à faire
     */
    RhodesDernierBack("Attente dernier étage"  , 6, "rhodes/piece-back-rhodes-5.png"), //
    /**
     * merveille achevée
     */
    RhodesDernierFront("Dernier étage achevé"   , 7, "rhodes/piece-front-rhodes-5.png"), //

    ;

    // ------------------------------------------------------------------------


    /**
     * etape actuel de la construction
     */
    public final String displayConstructionLevel;

    /**
     * indice de la construction
     */
    public final int achievedConstructionLevel;

    /**
     * lien vers l'image de la construction
     */
    public final String imageResource;

    // ------------------------------------------------------------------------

    private WonderCardType(String displayConstructionLevel, int achievedConstructionLevel, String imageResource) {
        this.displayConstructionLevel = displayConstructionLevel;
        this.achievedConstructionLevel = achievedConstructionLevel;
        this.imageResource = "images/wonders/" + imageResource;

    }

}
