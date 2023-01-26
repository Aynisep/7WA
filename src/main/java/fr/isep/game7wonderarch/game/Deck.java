package fr.isep.game7wonderarch.game;

import fr.isep.game7wonderarch.domain.CardDecks;
import fr.isep.game7wonderarch.domain.CardType;
import fr.isep.game7wonderarch.domain.Wonder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * un Deck contenant toutes les cartes
 */
public class Deck {


    private static final Logger log = LogManager.getLogger( Deck.class );

    /**
     * l'ensemble du deck avec les cartes groupés
     */
    private List<CardDecks.CardTypeQuantity> cardDeckInit;

    /**
     * le deck partagé, dont seule la première carte sera visible
     */
    private List<CardType> cardDeck;

    /**
     * renvoie toutes les cartes du deck
     * @return une ArrayList contenant l'ensemble des cartes du joueur non encore utilisées
     */
    public List<CardType> getCardDeck() {
        return cardDeck;
    }

    /**créer un deck
     * @param cardDeck nouveau deck, ne pas utiliser sauf pour faire des tests
     */
    public void setDeck(List<CardType> cardDeck) {
        this.cardDeck = cardDeck;
    }

    /**
     * constructeur de la classe. Rempli le deck avec les cartes liées à la merveille et mélange les cartes
     * @param wonder la merveille du joueur, null pour le deck central
     */
    public Deck(Wonder wonder){

        if (wonder==null){
            cardDeckInit = CardDecks.deckCardQuantities_Extra;
        } else {
            // initialise le deck du joueur en fonction de sa merveille
            switch (wonder){
                case Alexandrie  :   // cas impossible, juste pour la consistance, non utilisé
                    this.cardDeckInit =  CardDecks.deckCardQuantities_Alexandrie;
                    break;

                case Halicarnasse  :   // cas impossible, juste pour la consistance, non utilisé
                    this.cardDeckInit =  CardDecks.deckCardQuantities_Halicarnasse;
                    break;

                case Ephese  :   // cas impossible, juste pour la consistance, non utilisé
                    this.cardDeckInit =  CardDecks.deckCardQuantities_Ephese;
                    break;

                case Olympie  :   // cas impossible, juste pour la consistance, non utilisé
                    this.cardDeckInit =  CardDecks.deckCardQuantities_Olympie;
                    break;

                case Babylone  :   // cas impossible, juste pour la consistance, non utilisé
                    this.cardDeckInit =  CardDecks.deckCardQuantities_Babylon;
                    break;

                case Rhodes  :   // cas impossible, juste pour la consistance, non utilisé
                    this.cardDeckInit =  CardDecks.deckCardQuantities_Rhodes;
                    break;

                case Gizeh  :   // cas impossible, juste pour la consistance, non utilisé
                    this.cardDeckInit =  CardDecks.deckCardQuantities_Gizeh;
                    break;
                default:  // c'est la pioche centrale, non associée à une merveille mais impossible
                    cardDeckInit = CardDecks.deckCardQuantities_Extra;
            }
        }

        cardDeck = new ArrayList<>();

        // on créé le deck qui servira véritablement à jour
        for (CardDecks.CardTypeQuantity myItem : cardDeckInit){
            // on ajoute autant de carte que nécessaire
            for (int i=0; i< myItem.quantity; i++){
                this.cardDeck.add(myItem.cardType);
            }
        }

        // on mélange le deck
        Collections.shuffle(cardDeck);
    }

    /**
     * renvoie la premiere carte du deck pour l'affichage et ne l'enlève pas du deck
     * @return renvoie la premiere carte du deck pour l'affichage et ne l'enlève pas du deck
     */
    public CardType readFirstCard(){
        // règle du jeu si une pioche est vide, elle le reste
        if ((cardDeck==null) || (cardDeck.size()==0)) {
            log.info("le Deck est vide, on ne peut plus y piocher");
            return null;
        }
        return cardDeck.get(0);
    }

    /**
     * lit une carte dans une position donnée dans le deck, utilisé pour les effets speciaux des merveilles
     * @param position la position de la carte dans le deck
     * @return la carte que l'on a trouvé
     */
    public CardType readCardAtPosition(int position){
        // règle du jeu si une pioche est vide, elle le reste
        if ((cardDeck==null) || (cardDeck.size()==0)) {
            log.info("le Deck est vide, on ne peut plus y piocher");
            return null;
        }
        return cardDeck.get(position);
    }

    /**
     * prendre la première carte du deck
     * @return renvoie la premiere carte du deck et la supprime du deck
     */
    public CardType takeOneCard(){
        // règle du jeu si une pioche est vide, elle le reste
        CardType ct = null;

        if ((cardDeck!=null) && (cardDeck.size()>0)){
            ct = cardDeck.get(0);
            cardDeck.remove(0);
        }
        return ct;
    }

    /**
     * enlève une carte de la pioche du joueur en donnant le type de carte
     * @param cardType la carte à enlever
     */
    public void takeOneCardPerType(CardType cardType){
        cardDeck.remove(cardType);
        Collections.shuffle(cardDeck);
    }

    /**
     * permet de regarder plusieurs cartes dans le deck sans les supprimer
     * @param nbOfCards nombre de cartes
     * @return retourne un nombre de carte donné en entrée (moins si le deck a moins de cartes
     */
    public ArrayList<CardType> checkMultipleCards(int nbOfCards){
        // règle du jeu si une pioche est vide, elle le reste
        ArrayList<CardType> ct = new ArrayList<>();
        int nbCards = nbOfCards;
        if (cardDeck.size()<nbCards){
            nbCards=cardDeck.size();
        }

        for (int i=0; i<nbCards; i++ ){
            ct.add(cardDeck.get(i));
        }

        return ct;
    }

    /**
     * retourne le nombre de carte dans le deck
     * @return 0 si aucune carte, sinon le nombre de cartes
     */
    public int size(){
        if ((cardDeck!=null) && (cardDeck.size()>0)){
           return cardDeck.size();
        }
        return 0;
    }

    /**
     * détail du deck complet groupé par quantité
     * @return une strong contenant tout le deck non encore joué
     */
    public String cardDeckToString() {
        StringBuffer sb = new StringBuffer(500);
        sb.append("\tContenu du deck : ").append("\n");

        // liste de nos ressources

        for ( CardType list  : cardDeck) {
            sb.append("\tType de Carte = [").append(list.cardDisplayName).append("]\t quantité = [1]\n");
        }

        return sb.toString();
    }

}
