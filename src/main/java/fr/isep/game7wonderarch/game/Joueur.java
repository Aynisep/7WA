package fr.isep.game7wonderarch.game;

import fr.isep.game7wonderarch.wonders.Gizeh;
import fr.isep.game7wonderarch.wonders.WonderConstructionSteps;
import fr.isep.game7wonderarch.wonders.WonderStructure;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.isep.game7wonderarch.domain.*;

import java.util.*;

/**
 * Classe représentant un Joueur avec sa postion sur le plateau, ses cartes et ses jetons
 * la classe implemente Comparable pour pouvoir trier les joueurs en fonction des scores
 */
public class Joueur implements Comparable<Joueur> {

    private static final Logger log = LogManager.getLogger( Joueur.class );

    /**
     * le nb de shield que le joueur possède à un instant t, utiliser pour les conflits
     */
    private int nbBouclier = 0;

    /**
     * le score final
     * @return le score final
     */
    public int getScoreFinal() {
        return scoreFinal;
    }

    private int scoreFinal = 0;

    /**
     * le nombre de cartes chat que l'on possède, utiliser pour les conditions de victoire
     * @return le nombre de cartes chat que l'on possède
     */
    public int getNbChat() {
        return nbOfCats;
    }

    private int nbOfCats = 0;

    private boolean hasArtsAndCrafts = false;  // Lorsque vous prenez une carte grise 'papier' ou 'verre',
    // choisissez 1 carte supplémentaire parmi les 3 disponibles et posez-la devant vous"

    private boolean hasJewelry = false;  // Lorsque vous prenez une carte grise 'pierre' ou une carte jaune,
    // choisissez 1 carte supplémentaire parmi les 3 disponibles et posez-la devant vous"

    private boolean hasScience = false;  // Lorsque vous prenez une carte verte,
    // choisissez 1 carte supplémentaire parmi les 3 disponibles et posez-la devant vous"

    private boolean hasPropaganda = false;  // Lorsque vous prenez une carte Rouge avec 1 ou 2 icônes 'corne,
    // choisissez 1 carte supplémentaire parmi les 3 disponibles et posez-la devant vous"


    // Action disponible en tirant les cartes
    private boolean hasEconomy = false;      // 1 carte jaune en votre possession vous offre 2 pièces au lieu d'une seule,
    // quand on prend mettre à jour liste des ressources Or puis mettre 2 au lieu de 1 à chaque fois

    /**
     * Action sur les construction
     */
    // Action disponible en tirant les cartes
    private boolean hasIngeniery = false;    // Lorsque vous construisez un étape,
    // vous pouvez utiliser n'importe quelle ressource sans tenir compte de la restriction 'identique' ou 'différente'"

    // Action disponible pendant la construction -> buildOneLevel
    private boolean hasArchitecture = false;  // Lorsque vous construisez un étape,
    // choisissez 1 carte supplémentaire parmi les 3 disponibles et posez-la devant vous"

    /**
     * action sur les guerres
     */
    private boolean hasTactic = false;    // Ajoutez 2 Boucliers à votre total de Boucliers

    /**
     * modification des points de victoire  -> fin de parie
     */
    private boolean hasDecoration = false;    // A la fin de la partie, gagnez 4 Points de victoire si votre Merveille est encore en construction
    // ou 6 Points de victoire si vous avez terminé sa construction

    /**
     * modification des points de victoire  -> fin de parie
     */
    private boolean hasPolitic = false;    // A la fin de la partie, gagnez 1 Point de victoire par îcone 'chat', présente sur vos cartes bleues"

    /**
     * modification des points de victoire  -> fin de parie
     */
    private boolean hasStrategy = false;    // A la fin de la partie, gagnez 1 Point de victoire par jeton militaire en votre possession

    /**
     * modification des points de victoire  -> fin de parie
     */
    private boolean hasEducation = false;    // gagnez 2 Points de victoire par jeton de Progrès en votre possession (celui-ci y compris)

    /**
     * modification des points de victoire  -> fin de parie
     */
    private boolean hasCulture = false;    // Ce jeton est présent en 2 exemplaires. A la fin de la partie,
    // gagnez 4 Points de victoire si vous possédez 1, ou 12 Points de victoire si vous possédez les 2

    /**
     * modification des points de victoire  -> fin de parie
     */
    private boolean hasCultureBis = false;    // Ce jeton est présent en 2 exemplaires. A la fin de la partie,
    // gagnez 4 Points de victoire si vous possédez 1, ou 12 Points de victoire si vous possédez les 2

    /**
     * le joueur a droite du joueur courant
     * @return le joueur a droite du joueur courant
     */
    public Joueur getJoueurDeDroite() {
        return joueurDeDroite;
    }

    /**
     * Pointeur sur le joueur qui précède
     * @param joueurDeDroite le joueur a droite du joueur courant
     */
    public void setJoueurDeDroite(Joueur joueurDeDroite) {
        this.joueurDeDroite = joueurDeDroite;
    }

    private Joueur joueurDeDroite = null;

    /**
     * Calcule les points de victoire du joueur. Permet d'activer la fonction de comparaison du classement
     */
    public void calculateFinalScore(){
        // les points de la merveille
        scoreFinal = wonderStructure.getStructurePoints();

        int nbProgressToken = 0;

        // les points du chat
        if (hasCat()){
            scoreFinal += 2;
        }

        // cartes bleues
        scoreFinal += laurelPoints;

        // jeton de victoire militaire
        scoreFinal += getMilitaryPoints();

        // jeton progres
        // on les comptes et traitement special pour certains

        int valeurJetonProgres = 0;

        if (hasArtsAndCrafts){
            nbProgressToken++;
            scoreFinal += valeurJetonProgres;
            // normalement on ne fait rien
        }
        if (hasJewelry){
            nbProgressToken++;
            scoreFinal += valeurJetonProgres;
            // normalement on ne fait rien
        }
        if (hasScience){
            nbProgressToken++;
            scoreFinal += valeurJetonProgres;
            // normalement on ne fait rien
        }
        if (hasPropaganda){
            nbProgressToken++;
            scoreFinal += valeurJetonProgres;
            // normalement on ne fait rien
        }
        if (hasEconomy){
            nbProgressToken++;
            scoreFinal += valeurJetonProgres;
            // normalement on ne fait rien
        }
        if (hasIngeniery){
            nbProgressToken++;
            scoreFinal += valeurJetonProgres;
            // normalement on ne fait rien
        }
        if (hasArchitecture){
            nbProgressToken++;
            scoreFinal += valeurJetonProgres;
            // normalement on ne fait rien
        }
        if (hasTactic){
            nbProgressToken++;
            scoreFinal += valeurJetonProgres;
            // normalement on ne fait rien
        }

        // A la fin de la partie, gagnez 4 Points de victoire si votre Merveille est encore en construction
        // ou 6 Points de victoire si vous avez terminé sa construction
        if (hasDecoration){
            nbProgressToken++;
            if (getWonderStructure().isWonderAchieved()){
                scoreFinal += 6;
            } else {
                scoreFinal += 4;
            }
        }

        // A la fin de la partie, gagnez 1 Point de victoire par îcone 'chat', présente sur vos cartes bleues"
        if (hasPolitic){
            nbProgressToken++;
            scoreFinal += nbOfCats;
        }

        // A la fin de la partie, gagnez 1 Point de victoire par jeton militaire en votre possession
        if (hasStrategy){
            nbProgressToken++;
            scoreFinal += getMilitaryPoints();  // on avait deja mis avant, ca revient à doubler
        }

        // gagnez  12 Points de victoire si vous possédez les 2
        if (hasCultureBis){
            nbProgressToken++;
            nbProgressToken++;
            scoreFinal += 12;
        } else if (hasCulture){  // 4 points de victoire
            nbProgressToken++;
            scoreFinal += 4;
        }


        // gagnez 2 Points de victoire par jeton de Progrès en votre possession (celui-ci y compris)  -> en dernier
        if (hasEducation){
            nbProgressToken++;
            scoreFinal += (2*nbProgressToken);
        }
    }

    /**
     * notre comparateur pour ordonner les joueurs en fin de partie
     * @param joueur the object to be compared.
     * @return -1, 0 ou 1 en fonction du resultat de la comparaison
     */
    @Override
    public int compareTo(Joueur joueur) {
        //trions les joueurs en fonction du score final
        return compare(joueur.scoreFinal, this.scoreFinal);
    }

    /**
     * notre comparateur
     * @param x premier input
     * @param y deuxième input
     * @return resultat de la comparaison
     */
    public static int compare (int x, int y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

    /**
     * pour le calcul du nombre de cartes bleues avec un chat
     */
    public void addChat() {
        this.nbOfCats++;
    }

    /**
     * le nombre de boucliers en pocession du joueur
     * @return le nombre de boucliers en pocession du joueur
     */
    public int getNbBouclier() {
        return nbBouclier;
    }

    /**
     * ajoute des boucliers au joueur
     * @param nbBouclier le nb de bouclier à ajouter
     */
    public void addBouclier(int nbBouclier){
        this.nbBouclier += nbBouclier;
    }

    /**
     * on enlève tous les boucliers du joueur
     */
    public void resetBouclier(){
        this.nbBouclier=0;
    }

    /**
     * Action permettant de prendre une carte supplémentaire
     */
    private boolean hasUrbanisme = false;  // Lorsque vous prenez une carte grise 'bois' ou 'brique',

    private int numberOfCultureProgressTokens = 0;

    /**
     * permet de savoir si le joueur à le token Urbanisme
     * @return vrai s'il possède le Token
     */
    public boolean isHasUrbanisme() {
        return hasUrbanisme;
    }

    /**
     * Gestion du Token Urbnanisme
     * @param hasUrbanisme vrai si le joueur possède le token Urbanisme
     */
    public void setHasUrbanisme(boolean hasUrbanisme) {
        this.hasUrbanisme = hasUrbanisme;
    }

    /**
     * permet de savoir si le joueur à le token Arts and Crafts
     * @return vrai s'il possède le Token
     */
    public boolean isHasArtsAndCrafts() {
        return hasArtsAndCrafts;
    }

    /**
     * Gestion du Token Arts and Crafts
     * @param hasArtsAndCrafts vrai si le joueur possède le token Arts and Crafts
     */
    public void setHasArtsAndCrafts(boolean hasArtsAndCrafts) {
        this.hasArtsAndCrafts = hasArtsAndCrafts;
    }

    /**
     * permet de savoir si le joueur à le token Jewelry
     * @return vrai s'il possède le Token
     */
    public boolean isHasJewelry() {
        return hasJewelry;
    }

    /**
     * Gestion du Token Jewelry
     * @param hasJewelry vrai si le joueur possède le token Jewelry
     */
    public void setHasJewelry(boolean hasJewelry) {
        this.hasJewelry = hasJewelry;
    }

    /**
     * permet de savoir si le joueur à le token Science
     * @return vrai s'il possède le Token
     */
    public boolean isHasScience() {
        return hasScience;
    }

    /**
     * Gestion du Token Science
     * @param hasScience vrai si le joueur possède le token Science
     */
    public void setHasScience(boolean hasScience) { this.hasScience = hasScience;}

    /**
     * permet de savoir si le joueur a le token Propaganda
     * @return vrai s'il possède le Token
     */
    public boolean isHasPropaganda() {
        return hasPropaganda;
    }

    /**
     * Gestion du Token Propaganda
     * @param hasPropaganda vrai si le joueur possède le token Propaganda
     */
    public void setHasPropaganda(boolean hasPropaganda) {
        this.hasPropaganda = hasPropaganda;
    }

    /**
     * permet de savoir si le joueur a le token Economy
     * @return vrai s'il possède le Token
     */
    public boolean isHasEconomy() {
        return hasEconomy;
    }

    /**
     * Gestion du Token Economy
     * @param hasEconomy vrai si le joueur possède le token Architecture
     */
    public void setHasEconomy(boolean hasEconomy) {
        this.hasEconomy = hasEconomy;
    }

    /**
     * permet de savoir si le joueur a le token Ingeniery
     * @return vrai s'il possède le Token
     */
    public boolean isHasIngeniery() {
        return hasIngeniery;
    }

    /**
     * Gestion du Token Ingénierie
     * @param hasIngeniery vrai si le joueur possède le token Architecture
     */
    public void setHasIngeniery(boolean hasIngeniery) {
        this.hasIngeniery = hasIngeniery;
    }

    /**
     * permet de savoir si le joueur a le token Architecture
     * @return vrai s'il possède le Token
     */
    public boolean isHasArchitecture() {
        return hasArchitecture;
    }

    /**
     * Gestion du Token Architecture
     * @param hasArchitecture vrai si le joueur possède le token Architecture
     */
    public void setHasArchitecture(boolean hasArchitecture) {
        this.hasArchitecture = hasArchitecture;
    }

    /**
     * permet de savoir si le joueur a le token Tactic
     * @return vrai s'il possède le Token
     */
    public boolean isHasTactic() {
        return hasTactic;
    }

    /**
     * Gestion du Token Tactic
     * @param hasTactic vrai si le joueur possède le token Tactic
     */
    public void setHasTactic(boolean hasTactic) {
        this.hasTactic = hasTactic;
    }

    /**
     * permet de savoir si le joueur a le token Decoration
     * @return vrai s'il possède le Token
     */
    public boolean isHasDecoration() {
        return hasDecoration;
    }


    /**
     * Gestion du Token Decoration
     * @param hasDecoration vrai si le joueur possède le token Decoration
     */
    public void setHasDecoration(boolean hasDecoration) {
        this.hasDecoration = hasDecoration;
    }

    /**
     * permet de savoir si le joueur à le token Politique
     * @return vrai s'il possède le Token
     */
    public boolean isHasPolitic() {
        return hasPolitic;
    }

    /**
     * Gestion du Token Politique
     * @param hasPolitic vrai si le joueur possède le token politique
     */
    public void setHasPolitic(boolean hasPolitic) {
        this.hasPolitic = hasPolitic;
    }

    /**
     * permet de savoir si le joueur à la token Strategy
     * @return vrai s'il possèdle le Tokek
     */
    public boolean isHasStrategy() {
        return hasStrategy;
    }

    /**
     * Gestion du Token Strategy
     * @param hasStrategy vrai si le joueur possède le token politique
     */
    public void setHasStrategy(boolean hasStrategy) {
        this.hasStrategy = hasStrategy;
    }

    /**
     * permet de savoir si le joueur à le token Education
     * @return vrai s'il possède le Token
     */
    public boolean isHasEducation() {
        return hasEducation;
    }

    /**
     * Gestion du Token Education
     * @param hasEducation vrai si le joueur possède le token politique
     */
    public void setHasEducation(boolean hasEducation) {
        this.hasEducation = hasEducation;
    }

    /**
     * permet de savoir si le joueur à le token Culture
     * @return vrai s'il possède le Token
     */
    public boolean isHasCulture() {
        return hasCulture;
    }

    /**
     * Gestion du Token Culture
     * @param hasCulture vrai si le joueur possède le token culture
     */
    public void setHasCulture(boolean hasCulture) {
        this.hasCulture = hasCulture;
    }

    /**
     * permet de savoir si le joueur possède les deux tokens Culture
     * @return vrai s'il possède les deux Token
     */
    public boolean isHasCultureBis() {
        return hasCulture;
    }

    /**
     * Gestion du deuxième Token Culture
     * @param hasCultureBis vrai si le joueur possède les deux token culture
     */
    public void setHasCultureBis(boolean hasCultureBis) {
        this.hasCultureBis = (hasCulture && hasCultureBis);
    }

    private ArrayList<ProgressToken> myProgressTokens;

    /**
     * la merveille du joueur
     */
    private Wonder wonder;
    private ImageView ivMiniMerveille = null;

    /**
     * ImageView du chat dans le bandeau des joueurs.
     */
    private ImageView ivMiniChat = null;

    /**
     * le joueur à gauche du joueur
     */
    private Joueur joueurSuivant = null;

    /**
     *  l'ImageView qui permet de mettre à jour la carte représentant la pioche du joueur dans le bandeau des joueurs
     */
    private ImageView ivMiniCard = null;

    /**
     * l'ImageView qui permet de mettre à jour la carte représentant la pioche du joueur dans le bandeau des joueurs
     * @return l'ImageView qui permet de mettre à jour la carte représentant la pioche du joueur dans le bandeau des joueurs
     */
    public ImageView getIvMiniCard() {
        return ivMiniCard;
    }

    /**
     * l'ImageView qui permet de mettre à jour la carte représentant la pioche du joueur dans le bandeau des joueurs
     * @param ivMiniCard l'ImageView qui permet de mettre à jour la carte représentant la pioche du joueur dans le bandeau des joueurs
     */
    public void setIvMiniCard(ImageView ivMiniCard) {
        this.ivMiniCard = ivMiniCard;
    }

    /**
     * l'ImageView représentant le fait que le joueur possède le chat dans le bandeau des joueurs
     * @return l'ImageView représentant le fait que le joueur possède le chat dans le bandeau des joueurs
     */
    public ImageView getIvMiniChat() {
        return ivMiniChat;
    }

    /**
     * l'ImageView du représentant le fait que le joueur possède le chat dans le bandeau des joueurs
     * @param ivMiniChat l'ImageView du représentant le fait que le joueur possède le chat dans le bandeau des joueurs
     */
    public void setIvMiniChat(ImageView ivMiniChat) {
        this.ivMiniChat = ivMiniChat;
    }

    /**
     *  le joueur à gauche du joueur actif
     * @return le joueur à gauche du joueur actif
     */
    public Joueur getJoueurSuivant() {
        return joueurSuivant;
    }

    /**
     * utilisé à l'initialisation pour garder la liste des joueurs et leur position
     * @param joueurSuivant le joueur à gauche
     */
    public void setJoueurSuivant(Joueur joueurSuivant) {
        this.joueurSuivant = joueurSuivant;
    }

    /**
     *l'ImageView contenant la représentation de la mini merveille du joueur
     * @return l'ImageView contenant la représentation de la mini merveille du joueur
     */
    public ImageView getIvMiniMerveille() {
        return ivMiniMerveille;
    }

    /**
     * l'ImageView contenant la représentation de la mini merveille du joueur
     * @param ivMiniMerveille l'ImageView contenant la représentation de la mini merveille du joueur
     */
    public void setIvMiniMerveille(ImageView ivMiniMerveille) {
        this.ivMiniMerveille = ivMiniMerveille;
    }


    private int getMilitaryPoints() {
        return militaryPointsBarbarian + militaryPointsCentury;
    }

    /**
     * Apres une guerre, les points correspond au Barbare disporaissent car on se débarasse des cartes
     */
    public void resetMilitaryPointsBarbarian(){
        militaryPointsBarbarian = 0;
    }

    /**
     * ajoute des points militaires pour un barbare
     * @param militaryPointsBarbarian les points à ajouter
     */
    public void addMilitaryPointsBarbarian(int militaryPointsBarbarian) {
        this.militaryPointsBarbarian = this.militaryPointsBarbarian  + militaryPointsBarbarian;
    }

    /**
     * ajoute des points de victoire militaire pour un centurion
     * @param militaryPointsCentury la nombre de point à ajouter
     */
    public void addMilitaryPointsCentury(int militaryPointsCentury) {
        this.militaryPointsCentury = this.militaryPointsCentury  + militaryPointsCentury;
    }

    /**
     * les points de bataille dus à un barbare
     */
    private int militaryPointsBarbarian = 0;

    /**
     * points de bataille du à un soldat
     */
    private int militaryPointsCentury = 0;


     /**
      * le nombre de point de laurier
     * @return le nombre de points de victoire actuels du joueur
     */
    public int getLaurelPoints() {
        return laurelPoints;
    }

    /**
     * ajoute des points de victoires
     * @param laurelPoints le nombre de point de victoire à ajouter au joueur
     */
    public void addLaurelPoints(int laurelPoints) {
        this.laurelPoints = laurelPoints;
    }

    /**
     * permet de savoir si le joueur est en pocession du chat
     * @return vrai si le joueur est en pocession du chat
     */
    public boolean hasCat() {
        return cat;
    }

    /**
     * prend le chat
     */
    public void takeCat() {
        this.cat = true;
    }

    /**
     * perd le chat
     */
    public void loseCat() {
        this.cat = false;
    }

    /**
     * la pioche du joueur de droite
     */
    private Deck deckJoueurDeDroite = null;

    /**
     * la position du joueur dans le cercle des joueurs
     * @param position la position, 0 pour le premier joueur
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Points de victoire actuellement possédeés
     */
    private int laurelPoints = 0;

    /**
     * vrai si on possède le chat
     */
    private boolean cat = false;

    /**
     * l'ensemble des cartes vertes actuellement en pocession du joueur
     * @return l'ensemble des cartes vertes actuellement en pocession du joueur
     */
    public HashMap<CardType, Integer> getCartesVertes() {
        return cartesVertes;
    }

    /**
     * met à jour la liste des tokens de progrès possédés par le joueur
     * @param pt : le token que l'on vient de piocher
     */
    public void addProgressToken (ProgressToken pt){
        this.myProgressTokens.add(pt);

        if (pt == ProgressToken.Urbanism){
            setHasUrbanisme(true);
        } else if (pt == ProgressToken.ArtsAndCrafts){
            setHasArtsAndCrafts(true);
        }else if (pt == ProgressToken.Jewelry){
            setHasJewelry(true);
        }else if (pt == ProgressToken.Science){
            setHasScience(true);
        }else if (pt == ProgressToken.Propaganda){
            setHasPropaganda(true);
        }else if (pt == ProgressToken.Architecture){
            setHasArchitecture(true);
        }else if (pt == ProgressToken.Economy){
            setHasEconomy(true);
        }else if (pt == ProgressToken.Ingeniery){
            setHasIngeniery(true);
        }else if (pt == ProgressToken.Tactic){
            setHasTactic(true);
        }else if (pt == ProgressToken.Decoration){
            setHasDecoration(true);
        }else if (pt == ProgressToken.Politic){
            setHasPolitic(true);
        }else if (pt == ProgressToken.Strategy){
            setHasStrategy(true);
        }else if (pt == ProgressToken.Education){
            setHasEducation(true);
        }else if (pt == ProgressToken.Culture){
            setHasCultureBis(true);
            setHasCulture(true);
            numberOfCultureProgressTokens++;
        }
    }

    /**
     * Ajoute une carte Verte à la main du joueur
     * @param uneCarteVerte ajout d'une carte verte dans les cartes possédées
     */
    public void ajouteruneCarteVerte(CardType uneCarteVerte) {
        cartesVertes.replace(uneCarteVerte, cartesVertes.get(uneCarteVerte)+1);
    }

    /**
     * regarde si on possède assez de token de progrès pour permettre de prendre un jeton, supprime les token de la liste
     */
    public void doActionCarteVerte(){
        if (testConditionCarteVerte()) {
            if (cartesVertes.get(CardType.CardScienceLaw) >= 2) {
                cartesVertes.replace(CardType.CardScienceLaw, cartesVertes.get(CardType.CardScienceLaw) - 2);
            } else if (cartesVertes.get(CardType.CardScienceArchitect) >= 2) {
                cartesVertes.replace(CardType.CardScienceArchitect, cartesVertes.get(CardType.CardScienceArchitect) - 2);
            } else if (cartesVertes.get(CardType.CardScienceMechanic) >= 2) {
                cartesVertes.replace(CardType.CardScienceMechanic, cartesVertes.get(CardType.CardScienceMechanic) - 2);
            } else {
                cartesVertes.replace(CardType.CardScienceMechanic, cartesVertes.get(CardType.CardScienceMechanic) - 1);
                cartesVertes.replace(CardType.CardScienceArchitect, cartesVertes.get(CardType.CardScienceArchitect) - 1);
                cartesVertes.replace(CardType.CardScienceLaw, cartesVertes.get(CardType.CardScienceLaw) - 1);
            }
        }
    }

    /**
     * test les conditions d'obtention d'un token de progrès
     * @return vrai si on a assez de ressources pour en prendre un
     */
    public boolean testConditionCarteVerte(){
        int numberRessourcesDifferentes = 0;
        int maxRessourceIdentiques =0;

        CardType key;
        int value;

        for(Iterator i = cartesVertes.keySet().iterator(); i.hasNext();){
            key=(CardType)i.next();
            value= Integer.valueOf(cartesVertes.get(key));

            if (value>0) {
                if (maxRessourceIdentiques<value){
                    maxRessourceIdentiques = value;
                }
                numberRessourcesDifferentes++;
            }
        }

        return ((maxRessourceIdentiques==2)||(numberRessourcesDifferentes==3));
    }

    /**
     * Les cartes vertes actuellement possédées
     */
    private HashMap<CardType, Integer> cartesVertes;


    /**
      Nom du Joueur
     */
    private String nom;

    /**
     * le nom du joueur
     * @return retourne le nom du joueur
     */
    public String getNom() {
        return nom;
    }

    /**
     * définit le nom du joueur
     * @param nom le nouveau nom du joueur
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     Position du Joueur
     */
    private int position;

    /**
     * la position du joueur dans le jeu de 0 à nombre de joueur moins 1
     * @return retourne la position du joueur (de 0 à Constants.NB_MAX_JOUEURS -1)
     */
    public int getPosition() {
        return position;
    }

    /**
     * définit la position du joueur
     * @param position du joueur
     */
    public void setNom(int position) {
        this.position = position;
    }

    /**
     * la merveille que la joueur doit construire
     */
    private WonderStructure wonderStructure;

    /**
     * info sur la merveille du joueur, notamment sur son achèvement
     * @return les info sur notre merveille pour les étapes de construction
     */
    public WonderConstructionSteps getWcs() {
        return wcs;
    }

    /**
     * défini notre merveille
     * @param wcs : les infos sur la merveille
     */
    public void setWcs(WonderConstructionSteps wcs) {
        this.wcs = wcs;
    }

    /**
     * info sur la construction
     */
    private WonderConstructionSteps wcs;

    /**
     * permet de récupérer les infos sur la merveille que le joueur possède
     * @return la merveille que possède la joueur
     */
    public WonderStructure getWonderStructure() {
        return wonderStructure;
    }

    /**
     * définit la merveille que possède le joueur
     * @param wonderStructure la merveille que possède le joueur
     */
    public void setWonderStructure(WonderStructure wonderStructure) {
        this.wonderStructure = wonderStructure;
    }

    /**
     * retrourne la quantite de materiaux du joueur
     * @return une hash map contenant les materiaux
     */
    public HashMap<Material, Integer> getMaterial() {
        return material;
    }

    /**
     * la liste des matériaux possédés
     * @param material les matériaux
     */
    public void setMaterial(HashMap<Material, Integer> material) {
        this.material = material;
    }

    /**
     * les ressources du joueur, au début 0 de chaque
     */
    private HashMap<Material,Integer> material;

    /**
     * le deck du joueur, c'est-à-dire sa pioche toujours disponible
     */
    private Deck myDeck = null;

    /**
     * permet d'accéder à la pioche du joueur de droite, c'est utilisé pour les effets spéciaux des merveilles
     * @return la pioche du joueur de droite
     */
    public Deck getDeckJoueurDeDroite() {
        return deckJoueurDeDroite;
    }

    /**
     * la pioche du joueur de droite
     * @param deckJoueurDeDroite la pioche du joueur de droite
     */
    public void setDeckJoueurDeDroite(Deck deckJoueurDeDroite) {
        this.deckJoueurDeDroite = deckJoueurDeDroite;
    }

    /**
     * la merveille du joueur
     * @return la merveille du joueur
     */
    public Wonder getWonder() {
        return wonder;
    }

    /**
     * la merveille du joueur
     * @param wonder la merveille que le joueur devra construire
     */
    public void setWonder(Wonder wonder) {
        this.wonder = wonder;
    }

    /**
     * permet de récupérer l'ensemble de la pioche restante du joueur
     * @return le Deck courant du joueur
     */
    public Deck getMyDeck() {
        return myDeck;
    }

    /**
     * ajouter une quantité à une ressource
     * @param material la ressource que l'on va ajouter
     * @param quantity la quantité à ajouter
     */
    public void ajouterUneQuantiteAUneRessource(Material material, int quantity) {

        int quantiteActuelle = 0;

        if (this.material.containsKey(material)){
            try{
                quantiteActuelle = this.material.get(material).intValue();
            } catch (Exception e) {
                log.error("la ressource demandée n'existe pas, c'est étrange, on ne fait rien");
            }
        }
        this.material.replace(material, Integer.valueOf(quantiteActuelle + quantity) );
    }

    /**
     * ajouter un ensemble de ressources
     * @param newRessources  une HashMap contenant les ressources et les quantités à ajouter
     */
    public void ajouterPlusieursRessources(HashMap<Material,Integer> newRessources) {
        for (Map.Entry<Material,Integer> entry : newRessources.entrySet()) {
            ajouterUneQuantiteAUneRessource(entry.getKey(), entry.getValue());
        }
    }

    /**
     * retourne la quantité actuelle d'une ressource
     * @param material la ressource à regarder
     * @return la quantité trouvée
     */
    public int getQuantitéRessource(Material material) {
        int quantiteActuelle = 0;

        if (this.material.containsKey(material)){
            try{
                quantiteActuelle = this.material.get(material).intValue();
            } catch (Exception e) {
                log.error("la ressource demandée n'existe pas, c'est étrange, on ne fait rien");
            }
        }
        return quantiteActuelle;
    }

    /**
     * revoie le nombre de cartes Law dont le joueur est en pocession
     * @return le nombre de cartes
     */
    public int getLaw() {
        if ((cartesVertes==null) || ( cartesVertes.size()==0)){
            return 0;
        }
        return cartesVertes.get(CardType.CardScienceLaw);
    }

    /**
     * revoie le nombre de cartes Mechanic dont le joueur est en pocession
     * @return le nombre de cartes
     */
    public int getMechanic() {
        if ((cartesVertes==null) || ( cartesVertes.size()==0)){
            return 0;
        }
        return cartesVertes.get(CardType.CardScienceMechanic);
    }

    /**
     * revoie le nombre de cartes Architecte dont le joueur est en pocession
     * @return le nombre de cartes
     */
    public int getArchitect() {
        if ((cartesVertes==null) || ( cartesVertes.size()==0)){
            return 0;
        }
        return cartesVertes.get(CardType.CardScienceArchitect);
    }

    /**
     * constructeur de la classe Joeur
     * @param nom nom du joueur
     * @param position position du joueur sur la table
     * @param wonderStructure les étapes de la merveille à csontruire
     * @param wonder la merveille à csontruire
     * @param ivMiniMerveille un pointeur vers la mini représentaiton de la merveille
     * @param ivMiniCard un pointeur vers la mini représentaiton du deck
     * @param ivMiniChat un pointeur vers la mini représentation du chat
     * @throws IndexOutOfBoundsException si on veut accéder à un joueur qui n'existe pas
     */
    public Joueur(String nom, int position, WonderStructure wonderStructure, Wonder wonder, ImageView ivMiniMerveille, ImageView ivMiniCard, ImageView ivMiniChat)  throws IndexOutOfBoundsException{
        this.nom = nom;

        if ((position<0) || (position>7)){
            new IndexOutOfBoundsException();
        }

        this.nbOfCats = 0;
        this.nbBouclier = 0;
        this.ivMiniMerveille = ivMiniMerveille;
        this.ivMiniCard = ivMiniCard;
        this.ivMiniChat = ivMiniChat;
        this.position = position;
        this.wonder = wonder;
        this.myProgressTokens = new ArrayList<>();
        this.laurelPoints = 0;
        this.militaryPointsBarbarian = 0;
        this.militaryPointsCentury = 0;
        this.cat = false;
        this.wonderStructure = wonderStructure;

        // création des ressources du joueur
        material= new HashMap<Material,Integer>();
        material.putIfAbsent(Material.Wood, 0);
        material.putIfAbsent(Material.Paper, 0);
        material.putIfAbsent(Material.Brick, 0);
        material.putIfAbsent(Material.Stone, 0);
        material.putIfAbsent(Material.Glass, 0);
        material.putIfAbsent(Material.Gold, 0);

        // récupère le deck du joueur en début de partie
        myDeck = new Deck(wonder);

        cartesVertes =new HashMap<CardType, Integer>();
        cartesVertes.putIfAbsent(CardType.CardScienceLaw, 0);
        cartesVertes.putIfAbsent(CardType.CardScienceMechanic, 0);
        cartesVertes.putIfAbsent(CardType.CardScienceArchitect, 0);

        if (wonder == Wonder.Alexandrie){
            wcs = WonderConstructionSteps.alexandrieSteps.get(0);
        } else if (wonder == Wonder.Babylone){
            wcs = WonderConstructionSteps.babylonSteps.get(0);
        } else if (wonder == Wonder.Ephese){
            wcs = WonderConstructionSteps.epheseSteps.get(0);
        }  else if (wonder == Wonder.Halicarnasse){
            wcs = WonderConstructionSteps.halicarnasseSteps.get(0);
        }  else if (wonder == Wonder.Gizeh){
            wcs = WonderConstructionSteps.gizehSteps.get(0);
        }  else if (wonder == Wonder.Olympie){
            wcs = WonderConstructionSteps.olympieSteps.get(0);
        }  else if (wonder == Wonder.Rhodes){
            wcs = WonderConstructionSteps.rhodesSteps.get(0);
        }

    }

    /**
     * détail du deck du joueur
     * @return une strong contenant tout le deck non encore joué
     */
    public String cardDeckToString() {
        StringBuffer sb = new StringBuffer(500);
        sb.append("Joueur = [").append(nom).append("]\t").append("en position = ").append(this.position).append("\n");
        sb.append("\tPossède la merveille : ").append(wonder).append("\n");
        sb.append("\tet le deck associé suivant : ").append("\n").append(this.getMyDeck());

        return sb.toString();
    }

    /**
     * détail la main du joueur
     * @return un représensation sous la forme d'une string de la main du joueur
     */
    public String toString() {
        StringBuffer sb = new StringBuffer(300);
        sb.append("Joueur = [").append(nom).append("]\t").append("en position = ").append(this.position).append("\n");
        sb.append("\tPossède la merveille : ").append(wonder.displayName).append("\n");
        sb.append("a gagné\n");
        sb.append("avec le Nombre de points de victoire =").append(this.scoreFinal);

        /**  uniquement pour débug
        sb.append("\tEtape actuelle : ").append(getWonderStructure().getStageConstructionActuel()).append("\n");
        sb.append("\tPossède les ressources : ").append("\n");
        sb.append("\tPossède le deck: ").append(myDeck).append("\n");
        sb.append("\tdeck joueur de droite: ").append(deckJoueurDeDroite).append("\n");
        sb.append("\tPossède le chat: ").append(cat).append("\n");
        // liste de nos ressources
        if (material!=null) {
            for (Map.Entry<Material, Integer> entry : this.material.entrySet()) {
                sb.append("\t").append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
            }
        }

         if (material!=null) {
         for (Map.Entry<Material, Integer> entry : this.material.entrySet()) {
         sb.append("\t").append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
         }
         }
         */

        return sb.toString();
    }
}
