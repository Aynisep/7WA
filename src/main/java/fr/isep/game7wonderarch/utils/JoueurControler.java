// todo tester la condition de victoire


package fr.isep.game7wonderarch.utils;

import fr.isep.game7wonderarch.domain.*;
import fr.isep.game7wonderarch.game.Deck;
import fr.isep.game7wonderarch.game.Joueur;
import fr.isep.game7wonderarch.wonders.*;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Classe controlant le déroulement du jeu
 */
public class JoueurControler {

    /**
     * constructeur de la classe, ne doit pas être utilisé, c'est pour eviter le message javadoc
     */
    public JoueurControler(){

    }

    // permet de savoir si on a les tous les token war activés
    private int nbTokenWar = 0;

    private boolean bFinDuJeu = false;

    // le nb de conflict token pour cette partie, en fonction du nombre de joueur
    private int nbConflictTokens;

    // contient l'ensemble des merveilles
    private List<ProgressToken> listOfToken;

    // contient l'ensemble des merveilles
    private ArrayList<Wonder> listOfWonders = null;

    // sert a tester les fonctions
    private boolean isTest = true;

    // le log de log4j
    private static final Logger log = LogManager.getLogger(JoueurControler.class);

    // contient l'ensembe des joueurs
    private List<Joueur> listeDeJoueurs = null;

    /**
     * la pioche central
     */
    private Deck deckCentral = null;

    // le joueur actuellement présenté dans l'écran central
    private Joueur currentPlayer = null;

    /**
     * initialisation de l'écran de jeu, on n'affiche que le nombre de Token de Conflict demandés
     */
    private void initIvToken(){

        listIvToken.add(ivToken1);
        listIvToken.add(ivToken2);
        listIvToken.add(ivToken3);

        if (nbConflictTokens>=4){
            listIvToken.add(ivToken4);
            ivToken4.setVisible(true);
        }
        if (nbConflictTokens>=5){
            listIvToken.add(ivToken5);
            ivToken5.setVisible(true);
        }
        if (nbConflictTokens>=6){
            listIvToken.add(ivToken6);
            ivToken6.setVisible(true);
        }
    }

    /**
     * initialise le plateau d'un joueur s'il a donné un nom
     * @param event correspond à l'action sur le bouton
     *
     * @author  A. N.
     * @version 1.0
     */
    @FXML
    protected void initialisationNouveauJoueur(ActionEvent event) {

        if (deckCentral==null){
            deckCentral = new Deck(null); // le central
        }

        listeDeJoueurs = new ArrayList<>();

        listIvToken = new ArrayList<>();

        // on désactive le bouton
        bStartGame.setDisable(true);

        nbTokenWar = 0; // situation de paix

        int nbOfPlayers = 2;
        try {
            nbOfPlayers = ((Integer)cbNumberOfPlayers.getValue()).intValue();
        } catch (Exception e){

        }

        switch (nbOfPlayers) {
            case 2:
                nbConflictTokens = ConflictTokens.deux_joueurs.quantity;
                break;

            case 3:
                nbConflictTokens = ConflictTokens.trois_joueurs.quantity;
                break;

            case 4:
                nbConflictTokens = ConflictTokens.quatre_joueurs.quantity;
                break;

            case 5:
                nbConflictTokens = ConflictTokens.cinq_joueurs.quantity;
                break;

            case 6:
                nbConflictTokens = ConflictTokens.six_joueurs.quantity;
                break;

            case 7:
                nbConflictTokens = ConflictTokens.six_joueurs.quantity;
                break;
        }

        // si le nom est vide
        Alert alert;

        String [] nomJoueurs = new String[7];
        nomJoueurs[0] = playerName1.getText().trim();
        nomJoueurs[1] = playerName2.getText().trim();
        nomJoueurs[2] = playerName3.getText().trim();
        nomJoueurs[3] = playerName4.getText().trim();
        nomJoueurs[4] = playerName5.getText().trim();
        nomJoueurs[5] = playerName6.getText().trim();
        nomJoueurs[6] = playerName7.getText().trim();

        initIvToken();

        if (
                (nomJoueurs[0].length()==0) || (nomJoueurs[1].length()==0)
                        || (((nomJoueurs[2].length()==0) && nbOfPlayers>2)
                        || ((nomJoueurs[3].length()==0) && nbOfPlayers>3)
                        || ((nomJoueurs[4].length()==0) && nbOfPlayers>4)
                        || ((nomJoueurs[5].length()==0) && nbOfPlayers>5)
                        || ((nomJoueurs[6].length()==0) && nbOfPlayers>6)))
        {
            // manque des noms de joueurs
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nom des joueurs");
            alert.setContentText(" Les noms des joueurs ne peuvent être vide, merci de nommer les joueurs ");
            alert.showAndWait().ifPresent(rs -> {

                if (rs == ButtonType.OK) {
                    // on ne fait plus rien
                    bStartGame.setDisable(false);
                }
            });

        } else {
            // on créer tous les joueurs
            Wonder w = null;
            WonderStructure ws = null;
            String url = "";

            for (int i=0; i<nbOfPlayers; i++){
                w = getWonder();

                if (w == Wonder.Alexandrie){
                    ws = new Alexandrie(ivAlexandrie1, ivAlexandrie2, ivAlexandrie3, ivAlexandrie4, ivAlexandrie5);
                    url = WonderConstructionSteps.alexandrieSteps.get(0).url;

                } else if (w == Wonder.Babylone){
                    ws = new Babylon(ivBabylon1, ivBabylon2, ivBabylon3, ivBabylon4, ivBabylon5);
                    url = WonderConstructionSteps.babylonSteps.get(0).url;

                } else if (w == Wonder.Ephese){
                    ws = new Ephese(ivEphese1,ivEphese2, ivEphese3, ivEphese4, ivEphese5);
                    url = WonderConstructionSteps.epheseSteps.get(0).url;
                } else if (w == Wonder.Gizeh){
                    ws = new Gizeh(ivGizeh1, ivGizeh2,ivGizeh3, ivGizeh4, ivGizeh4);
                    url = WonderConstructionSteps.gizehSteps.get(0).url;
                } else if (w == Wonder.Halicarnasse){
                    ws = new Halicarnasse(ivHalicarnasse1,ivHalicarnasse2, ivHalicarnasse3, ivHalicarnasse4, ivHalicarnasse5);
                    url = WonderConstructionSteps.halicarnasseSteps.get(0).url;
                } else if (w == Wonder.Olympie){
                    ws = new Olympie(ivOlympie1, ivOlympie2, ivOlympie3, ivOlympie4,ivOlympie5);
                    url = WonderConstructionSteps.olympieSteps.get(0).url;
                } else if (w == Wonder.Rhodes){
                    ws = new Rhodes(ivRhodes1, ivRhodes2, ivRhodes3, ivRhodes4, ivRhodes5);
                    url = WonderConstructionSteps.rhodesSteps.get(0).url;
                }

                switch (i){
                    case 0:
                        listeDeJoueurs.add(new Joueur(nomJoueurs[i], i, ws, w, ivMerveilleJoueur1, ivJoueur1, ivCardChatJoueur1));
                        drawNewImage(ivMerveilleJoueur1, url);
                        break;
                    case 1:
                        listeDeJoueurs.add(new Joueur(nomJoueurs[i], i, ws, w, ivMerveilleJoueur2, ivJoueur2, ivCardChatJoueur2));
                        drawNewImage(ivMerveilleJoueur2, url);
                        break;
                    case 2:
                        listeDeJoueurs.add(new Joueur(nomJoueurs[i], i, ws, w, ivMerveilleJoueur3, ivJoueur3, ivCardChatJoueur3));
                        drawNewImage(ivMerveilleJoueur3, url);
                        break;
                    case 3:
                        listeDeJoueurs.add(new Joueur(nomJoueurs[i], i, ws, w, ivMerveilleJoueur4, ivJoueur4, ivCardChatJoueur4));
                        drawNewImage(ivMerveilleJoueur4, url);
                        break;
                    case 4:
                        listeDeJoueurs.add(new Joueur(nomJoueurs[i], i, ws, w, ivMerveilleJoueur5, ivJoueur5, ivCardChatJoueur5));
                        drawNewImage(ivMerveilleJoueur5, url);
                        break;
                    case 5:
                        listeDeJoueurs.add(new Joueur(nomJoueurs[i], i, ws, w, ivMerveilleJoueur6, ivJoueur6, ivCardChatJoueur6));
                        drawNewImage(ivMerveilleJoueur6, url);
                        break;
                    case 6:
                        listeDeJoueurs.add(new Joueur(nomJoueurs[i], i, ws, w, ivMerveilleJoueur7, ivJoueur7, ivCardChatJoueur7));
                        drawNewImage(ivMerveilleJoueur7, url);
                        break;
                }
            }

            initScreen.setVisible(false);
            mainScreen.setVisible(true);
            mainScreen.setDisable(false);

            hideProgressTokens();

            // on connecte les decks des differents joueurs
            // comme on tourne dans le sens des aiguilles d'une montre, mon voisin de gauche est le suivant dans la liste des joueurs.
            // Si je suis le dernier de la liste, c'est le joueur en tête de liste
            Object [] joueurs = listeDeJoueurs.toArray();
            Joueur j = null;

            for (int i=0; i<joueurs.length ; i++){

                if (i==(joueurs.length)-1){
                    // car du bord
                    ((Joueur) joueurs[0]).setDeckJoueurDeDroite(((Joueur) joueurs[i]).getMyDeck());
                    ((Joueur) joueurs[0]).setJoueurDeDroite(((Joueur) joueurs[i]));
                    ((Joueur) joueurs[i]).setJoueurSuivant(((Joueur) joueurs[0]));
                } else {
                    ((Joueur) joueurs[i + 1]).setDeckJoueurDeDroite(((Joueur) joueurs[i]).getMyDeck());
                    ((Joueur) joueurs[i + 1]).setJoueurDeDroite(((Joueur) joueurs[i]));
                    ((Joueur) joueurs[i]).setJoueurSuivant(((Joueur) joueurs[i+1]));
                }
            }

            nomJoueur1.setText(nomJoueurs[0]);
            nomJoueur2.setText(nomJoueurs[1]);
            nomJoueur3.setText(nomJoueurs[2]);
            nomJoueur4.setText(nomJoueurs[3]);
            nomJoueur5.setText(nomJoueurs[4]);
            nomJoueur6.setText(nomJoueurs[5]);
            nomJoueur7.setText(nomJoueurs[6]);

            // on affiche la merveille du premier joueur
            displayWonderPlayer(listeDeJoueurs.get(0));

            // on affiche face visible de la première carte du joueur
            displayCardDeckJoueur(listeDeJoueurs.get(0));

            // on met à jour le nom du joueur
            nomDuJoueurEcran.setText(nomJoueurs[0]);

            // on met à jour les chat
            ivChat.setVisible(false);
            bSeeDeck.setDisable(true);

            // on balaye l'écran du haut et on met à jour image / chat  et on cache les colonnes inutiles
            // nbOfPlayers
            nomJoueur3.setVisible(nbOfPlayers>=3);
            nomJoueur4.setVisible(nbOfPlayers>=4);
            nomJoueur5.setVisible(nbOfPlayers>=5);
            nomJoueur6.setVisible(nbOfPlayers>=6);
            nomJoueur7.setVisible(nbOfPlayers>=7);

            ivJoueur3.setVisible(nbOfPlayers>=3);
            ivJoueur4.setVisible(nbOfPlayers>=4);
            ivJoueur5.setVisible(nbOfPlayers>=5);
            ivJoueur6.setVisible(nbOfPlayers>=6);
            ivJoueur7.setVisible(nbOfPlayers>=7);

            ivMerveilleJoueur1.setVisible(true);
            ivMerveilleJoueur2.setVisible(true);
            ivMerveilleJoueur3.setVisible(nbOfPlayers>=3);
            ivMerveilleJoueur3.setVisible(nbOfPlayers>=3);
            ivMerveilleJoueur4.setVisible(nbOfPlayers>=4);
            ivMerveilleJoueur5.setVisible(nbOfPlayers>=5);
            ivMerveilleJoueur6.setVisible(nbOfPlayers>=6);
            ivMerveilleJoueur7.setVisible(nbOfPlayers>=7);

            initialisationProgressTokens();

            ivCardChatJoueur1.setVisible(false);
            ivCardChatJoueur2.setVisible(false);
            ivCardChatJoueur3.setVisible(false);
            ivCardChatJoueur4.setVisible(false);
            ivCardChatJoueur5.setVisible(false);
            ivCardChatJoueur6.setVisible(false);
            ivCardChatJoueur7.setVisible(false);

            // le premier tour peut commencer
            currentPlayer = (Joueur)joueurs[0];

            bPiocheCentrale.setDisable(false);
            bPiocheGauche.setDisable(false);
            bPiocheDroite.setDisable(false);

        }
    }

    /**
     * traite la pioche d'une carte
     * @param ct le type de carte qui a été pioché
     */
    private void handlePioche(CardType ct){
        // on désactive les autres boutons
        bSeeDeck.setDisable(true);
        bPiocheDroite.setDisable(true);
        bPiocheGauche.setDisable(true);
        bPiocheCentrale.setDisable(true);
        bFinDuTour.setDisable(false);

        bFinDuJeu = false;

        boolean bHandleSpecialConstruction = false;

        lastCardTaken = ct;

        if (ct.cardCategory == CardCategory.MaterialCard){
            // carte de ressources
            currentPlayer.ajouterUneQuantiteAUneRessource(ct.material, 1);
        } else if (ct.cardCategory == CardCategory.PoliticCard){
            // carte de points de victoire, on regarde si on a le chat
            if (ct==CardType.CardPolitic_emperor){
                currentPlayer.addLaurelPoints(ct.laurelCount);
            } else if (ct==CardType.CardPolitic_cat){
                // on libere le chat de tous les joueurs et on le positionne sur le joueur courant
                currentPlayer.addChat();
                for (Joueur tmpJoueur : listeDeJoueurs){
                    tmpJoueur.loseCat();
                }
                currentPlayer.takeCat();
                ivChat.setVisible(true);
                displayChatJoueurs();

                // met à jour les affichages de chats
            }
            currentPlayer.addLaurelPoints(ct.laurelCount);


        }  else if (ct.cardCategory == CardCategory.ProgressCard){
            // carte de type science, droit ou constructeur
            currentPlayer.ajouteruneCarteVerte(ct);
            if (currentPlayer.testConditionCarteVerte()){
                //on supprime les cartes en fonction de la version possible et on applique l'effet

                // on active les Token Progress, on supprime les cartes qui ont permis la condition, on bloque le bouton fin de partie
                this.bFinDuTour.setDisable(true);

                currentPlayer.doActionCarteVerte();

                bProgressToken1.setDisable(!bProgressToken1.isVisible());
                bProgressToken2.setDisable(!bProgressToken2.isVisible());
                bProgressToken3.setDisable(!bProgressToken3.isVisible());
                bProgressToken4.setDisable(!bProgressToken4.isVisible());
            }
        } else if (ct.cardCategory == CardCategory.WarCard){
            // les cartes de combats
            // on regarde si la carte à des

            // CardWar_barbarian  shield 1 corn 1
            // CardWar_centurion  shield 1 corn 0
            // CardWar_archer  shield 1 corn 2

            currentPlayer.addBouclier(1);

            if (ct == CardType.CardWar_archer){
                // on retourne deux jetons
                // avec nbTokenWar on sait quel jeton il faut changer
                nbTokenWar += 2;
            } else if (ct == CardType.CardWar_barbarian){
                // on retourne un jetons
                // avec nbTokenWar on sait quel jeton il faut changer
                nbTokenWar += 1;
            }

            int counter = 0;
            for(ImageView iv : listIvToken){
                if (counter<nbTokenWar){
                    drawNewImage(iv, "/images/tokens/token-conflict-war.png");
                }
                counter++;
            }
        }

        Wonder w = currentPlayer.getWonder();

        // pour les tests des merveilles, on enlève les commentaires en dessous
        //      currentPlayer.ajouterUneQuantiteAUneRessource(Material.Gold, 50);
        //// fin de la zone de test
            if (currentPlayer.getWonderStructure().isConstructionPossible(currentPlayer.getMaterial())){
            if (w == Wonder.Alexandrie){  // test ok merveille et mini merveille
                updateAlexandrie();
            } else if (w == Wonder.Babylone){ // test ok merveille et mini merveille
                updateBabylon();
            } else if (w == Wonder.Ephese){ // test ok merveille et mini merveille
                updateEphese();
            } else if (w == Wonder.Gizeh){ // test ok merveille et mini merveille
                updateGizeh();
            } else if (w == Wonder.Halicarnasse){ // test ok merveille et mini merveille
                updateHalicarnasse();
            } else if (w == Wonder.Olympie){
                updateOlympie();
            }  else if (w == Wonder.Rhodes){ // test ok merveille et mini merveille
                updateRhodes();
            }


            bFinDuJeu = currentPlayer.getWonderStructure().isWonderAchieved();
            bFinDuTour.setDisable(bFinDuJeu );

            if ((!bFinDuJeu) && (! bProgressTokenArchitecture.isDisable())) {
                handleTokenArchitecture();
            }

            int niveauConstruction = currentPlayer.getWonderStructure().getStageConstructionActuel();

            if (!bFinDuJeu){
                // si Alexandrie step 2 alors on prend une carte n'importe où  -> on prend sur les decks en haut
                if (w == Wonder.Alexandrie){

                    if ((niveauConstruction==2) || (niveauConstruction==4)){
                        // prendre une carte sur la table dans la pioche qu'on veut
                        bHandleSpecialConstruction=true;
                        handlePiocheAlexandrie();
                    }
                } else if (w == Wonder.Babylone){ // test ok merveille et mini merveille
                    if ((niveauConstruction==2) || (niveauConstruction==4)){
                        // prendre un jeton progres
                        bHandleSpecialConstruction=true;
                        handlePrendreJetonProgres();
                    }
                } else if (w == Wonder.Ephese){ // test ok merveille et mini merveille
                    if ((niveauConstruction>=2) || (niveauConstruction<=8)){
                        // prendre un jeton progres
                        bHandleSpecialConstruction=true;
                        handlePrendrePremierCartePiocheCentrale();
                    }
                } else if (w == Wonder.Gizeh){ // test ok merveille et mini merveille
                    // aucune action speciale
                } else if (w == Wonder.Halicarnasse){ // test ok merveille et mini merveille
                    if ((niveauConstruction==2) || (niveauConstruction==3)){
                        // prendre un jeton progres
                        bHandleSpecialConstruction=true;
                        handlePrendreUneCarteParmi5GaucheOuDroite();
                    }
                } else if (w == Wonder.Olympie){
                    if ((niveauConstruction==2) || (niveauConstruction==5)){
                        // prendre un jeton progres
                        bHandleSpecialConstruction=true;
                        handlePrendreUneCarteGauchePuisDroite();
                    }
                }  else if (w == Wonder.Rhodes){ // test ok merveille et mini merveille
                    if ((niveauConstruction==1) || (niveauConstruction==2)|| (niveauConstruction==5)){
                        // prendre un jeton progres
                        currentPlayer.addBouclier(1);
                    }
                }
            }
        }

        if (!bFinDuJeu)
        {
            displayMaterialPlayer(currentPlayer);

            displayCardDeckJoueur(currentPlayer);
        } else if (! bHandleSpecialConstruction){
            handleFinDuJeu();
        }

    }

    /**
     * redessine les écrans met à jour les decks
     * @param event action sur le bouton fin du tour
     */
    public void handleFinDuTour(Event event){
        // on interdit de ne rien faire
        bFinDuTour.setDisable(true);

        // gestion de la guerre
        if (nbTokenWar >=  listIvToken.size()){
            handleWar();
        }

        // on masque l'écran du joueur pour afficher le suivant
        currentPlayer = currentPlayer.getJoueurSuivant();

        // on affiche sa merveille
        // on affiche la merveille du premier joueur
        displayWonderPlayer(currentPlayer);

        // on met à jour le nom du joueur
        nomDuJoueurEcran.setText(currentPlayer.getNom());

        // on met à jour les chat
        ivChat.setVisible(currentPlayer.hasCat());

        // on dessine les deck
        displayCardDeckJoueur(currentPlayer);


        // on affiche les ressources
        displayMaterialPlayer(currentPlayer);

        bSeeDeck.setDisable((! currentPlayer.hasCat()) || (this.deckCentral.size()==0));

        bPiocheDroite.setDisable(currentPlayer.getDeckJoueurDeDroite().size()==0);
        bPiocheGauche.setDisable(currentPlayer.getMyDeck().size()==0);
        bPiocheCentrale.setDisable(this.deckCentral.size()==0);

        bProgressTokenUrbanism.setVisible(currentPlayer.isHasUrbanisme());
        bProgressTokenUrbanism.setDisable(! (currentPlayer.isHasUrbanisme()));

        bProgressTokenArtsAndCrafts.setVisible(currentPlayer.isHasArtsAndCrafts());
        bProgressTokenArtsAndCrafts.setDisable(! (currentPlayer.isHasArtsAndCrafts()));

        bProgressTokenJewelry.setVisible(currentPlayer.isHasJewelry());
        bProgressTokenJewelry.setDisable(! (currentPlayer.isHasJewelry()));

        bProgressTokenScience.setVisible(currentPlayer.isHasScience());
        bProgressTokenScience.setDisable(! (currentPlayer.isHasScience()));

        bProgressTokenPropaganda.setVisible(currentPlayer.isHasPropaganda());
        bProgressTokenPropaganda.setDisable(! (currentPlayer.isHasPropaganda()));

        bProgressTokenArchitecture.setVisible(currentPlayer.isHasArchitecture());
        bProgressTokenArchitecture.setDisable(! (currentPlayer.isHasArchitecture()));

        bProgressTokenEconomy.setVisible(currentPlayer.isHasEconomy());
        bProgressTokenEconomy.setDisable(! (currentPlayer.isHasEconomy()));

        bProgressTokenIngeniery.setVisible(currentPlayer.isHasIngeniery());
        bProgressTokenIngeniery.setDisable(! (currentPlayer.isHasIngeniery()));

        bProgressTokenTactic.setVisible(currentPlayer.isHasTactic());
        bProgressTokenTactic.setDisable(! (currentPlayer.isHasTactic()));

        bProgressTokenDecoration.setVisible(currentPlayer.isHasDecoration());
        bProgressTokenPolitic.setVisible(currentPlayer.isHasPolitic());
        bProgressTokenStrategy.setVisible(currentPlayer.isHasStrategy());
        bProgressTokenEducation.setVisible(currentPlayer.isHasEducation());
        bProgressTokenCulture.setVisible(currentPlayer.isHasCulture());
        bProgressCultureBis.setVisible(currentPlayer.isHasCultureBis());

        lastCardTaken = null;
    }

    private void updateDeckHalicarnasse(Deck deck, GridPane gp, int row){
        // on affiche les 5 premieres cartes

        ArrayList<CardType> ct = deck.checkMultipleCards(5);

        ImageView iv = null;

        int numberOfCards = ct.size();

        Pane paneTmp;

        if (numberOfCards>=1){
            paneTmp = (Pane)getNodeFromGridPane(gp, 0, row);
            iv = (ImageView) paneTmp.getChildren().get(0);
            drawNewImage(iv, ct.get(0).imageResource);
        }
        if (numberOfCards>=2){
            paneTmp = (Pane)getNodeFromGridPane(gp, 2, row);
            iv = (ImageView) paneTmp.getChildren().get(0);
            drawNewImage(iv, ct.get(1).imageResource);
        }
        if (numberOfCards>=3){
            paneTmp = (Pane)getNodeFromGridPane(gp, 4, row);
            iv = (ImageView) paneTmp.getChildren().get(0);
            drawNewImage(iv, ct.get(2).imageResource);
        }
        if (numberOfCards>=4){
            paneTmp = (Pane)getNodeFromGridPane(gp, 6, row);
            iv = (ImageView) paneTmp.getChildren().get(0);
            drawNewImage(iv, ct.get(3).imageResource);
        }
        if (numberOfCards>=5){
            paneTmp = (Pane)getNodeFromGridPane(gp, 8, row);
            iv = (ImageView) paneTmp.getChildren().get(0);
            drawNewImage(iv, ct.get(4).imageResource);
        }



    }
    @FXML
    private void handleSpecialHalicarnasseGauche(MouseEvent mouseEvent){
        // on disable les boutons et on enable les cartes du haut
        Button button = (Button)mouseEvent.getSource();
        button.setDisable(true);

        Pane pane = (Pane) button.getParent(); // = pane specialEffectHalicarnasse

        button = (Button) pane.lookup("#bSpecialHalicarnasseDroit");
        button.setDisable(true);

        GridPane gp = (GridPane)pane.lookup("#specialEffectHalicarnasseGridPane");

        // on efface les rows du bas
        Pane paneTmp;

        for (int i=0; i<9; i=i+2){
            paneTmp = (Pane)getNodeFromGridPane(gp, i, 0);
            paneTmp.getChildren().get(0).setDisable(false);

            paneTmp = (Pane)getNodeFromGridPane(gp, i, 2);
            paneTmp.getChildren().get(0).setDisable(true);
        }

        updateDeckHalicarnasse(currentPlayer.getMyDeck(), gp, 0);
    }

    @FXML
    private void handleSpecialHalicarnasseDroit(MouseEvent mouseEvent){
        // on disable les boutons et on enable les cartes du bas
        Button button = (Button)mouseEvent.getSource();
        button.setDisable(true);

        Pane pane = (Pane) button.getParent(); // = pane specialEffectHalicarnasse

        button = (Button) pane.lookup("#bSpecialHalicarnasseGauche");
        button.setDisable(true);

        GridPane gp = (GridPane)pane.lookup("#specialEffectHalicarnasseGridPane");

        // on efface les rows du bas
        Pane paneTmp;

        // on efface les rows du bas
        for (int i=0; i<9; i=i+2){
            paneTmp = (Pane)getNodeFromGridPane(gp, i, 0);
            paneTmp.getChildren().get(0).setDisable(true);

            paneTmp = (Pane)getNodeFromGridPane(gp, i, 2);
            paneTmp.getChildren().get(0).setDisable(false);
        }

        updateDeckHalicarnasse(currentPlayer.getDeckJoueurDeDroite(), gp, 2);
   }

    /**
     * retourne le Node d'un grid pane à l'emplement col / row
     * @param gridPane : le gridpane a être parcouru
     * @param col : la colonne
     * @param row : la ligne
     * @return le Node trouvé, null si le node n'existe pas

     *
     * @author  A. N.
     * @version 1.0
     */
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {


        for (Node node : gridPane.getChildren()) {
            try {
                if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {

                    return node;
                }
            } catch (Exception e) {
                //  LOGGER.error("Exception " + e);
            }

        }
        return null;
    }

    @FXML
    private void takeOneCardAlexandrie(MouseEvent mouseEvent){
        try {

            // on recheche sur quelle image, la personne a cliqué puis on prend la carte correspondante
            // on cherche la colonne
            ImageView iv = ((ImageView) mouseEvent.getTarget());

            Node node = iv.getParent();

            int column = GridPane.getColumnIndex(node);
            int row = GridPane.getRowIndex(node);

            CardType ct = null;

            if (row==0){
                ct = listeDeJoueurs.get(column/2).getMyDeck().takeOneCard();
            } else {
                // deck central
                ct = deckCentral.takeOneCard();
            }

            // on affiche les écrans
            mainScreen.setVisible(true);
            mainScreen.setDisable(false);

            specialEffectAlexandrie.setDisable(true);
            specialEffectAlexandrie.setVisible(false);

            // on met a jour
            handlePioche(ct);

        }
        catch (Exception e){
            log.error("Impossible de récupérer l'image sur laquelle on a cliqué");
        }
    }

    @FXML
    private void takeOneCard(MouseEvent mouseEvent){
      try {

          // on recheche sur quelle image, la personne a cliqué puis on prend la carte correspondante
          // on cherche la colonne
          ImageView iv = ((ImageView) mouseEvent.getTarget());

          Node node = iv.getParent();

          int column = GridPane.getColumnIndex(node);
          int row = GridPane.getRowIndex(node);

          CardType ct = null;

          if (row==0){
            ct = currentPlayer.getMyDeck().readCardAtPosition(column/2);
              currentPlayer.getMyDeck().takeOneCardPerType(ct);
          } else {
              ct = currentPlayer.getDeckJoueurDeDroite().readCardAtPosition(column/2);
              currentPlayer.getDeckJoueurDeDroite().takeOneCardPerType(ct);
          }

          // on affiche les écrans
          this.mainScreen.setVisible(true);
          mainScreen.setDisable(false);

          specialEffectHalicarnasse.setDisable(true);
          specialEffectHalicarnasse.setVisible(false);

          // on met a jour
          handlePioche(ct);

      }
      catch (Exception e){
          log.error("Impossible de récupérer l'image sur laquelle on a cliqué");
      }

    }

    private CardType whichCardIsThis(String url){
        CardType ct = null;
        if (url.equals(CardType.CardMaterialWood.imageResource)){
            ct = CardType.CardMaterialWood;
        } else if (url.equals(CardType.CardMaterialPaper.imageResource)){
            ct = CardType.CardMaterialPaper;
        } else if (url.equals(CardType.CardMaterialBrick.imageResource)){
            ct = CardType.CardMaterialBrick;
        } else if (url.equals(CardType.CardMaterialStone.imageResource)){
            ct = CardType.CardMaterialStone;
        } else if (url.equals(CardType.CardMaterialGlass.imageResource)){
            ct = CardType.CardMaterialGlass;
        }  else if (url.equals(CardType.CardMaterialGold.imageResource)){
            ct = CardType.CardMaterialGold;
        } else if (url.equals(CardType.CardScienceLaw.imageResource)){
            ct = CardType.CardScienceLaw;
        } else if (url.equals(CardType.CardScienceMechanic.imageResource)){
            ct = CardType.CardScienceMechanic;
        } else if (url.equals(CardType.CardScienceArchitect.imageResource)){
            ct = CardType.CardScienceArchitect;
        } else if (url.equals(CardType.CardWar_barbarian.imageResource)){
            ct = CardType.CardWar_barbarian;
        } else if (url.equals(CardType.CardWar_centurion.imageResource)){
            ct = CardType.CardWar_centurion;
        } else if (url.equals(CardType.CardWar_archer.imageResource)){
            ct = CardType.CardWar_archer;
        } else if (url.equals(CardType.CardPolitic_emperor.imageResource)){
            ct = CardType.CardPolitic_emperor;
        } else if (url.equals(CardType.CardPolitic_cat.imageResource)){
            ct = CardType.CardPolitic_cat;
        }
        return ct;
    }

    /**
     * creation du deck central, partagé entre tous les joueurs
     * test ok
     */
    public void createDeckCentral(){
        Deck deckCentral = new Deck(null);
    }

    /**
     * permet de sélectionner la merveille que le joueur devra construire, choix fait au hasard
     * @return une wonder au hasard
     */
    private Wonder getWonder(){
        if (listOfWonders==null){
            listOfWonders = new ArrayList();

            listOfWonders.add(Wonder.Alexandrie);
            listOfWonders.add(Wonder.Halicarnasse);
            listOfWonders.add(Wonder.Ephese);
            listOfWonders.add(Wonder.Olympie);
            listOfWonders.add(Wonder.Babylone);
            listOfWonders.add(Wonder.Rhodes);
            listOfWonders.add(Wonder.Gizeh);

            Collections.shuffle(listOfWonders);
        }

        Wonder w = listOfWonders.get(0);
        listOfWonders.remove(0);
        return w;
    }

    /**
     * on pioche dans la deck central
     * @param event clique sur la carte
     */
    public void handlePiocheCentral (Event event){
        handlePioche(deckCentral.takeOneCard());
     }

    /**
     * on pioche dans le deck de son voisin de droite
     * @param event clique sur la carte
     */
    public void handlePiocheDroite (Event event){
        handlePioche(currentPlayer.getDeckJoueurDeDroite().takeOneCard());
    }

    /**
     * on pioche dans son deck
     * @param event clique sur la carte
     */
    public void handlePiocheGauche (Event event){
        handlePioche(currentPlayer.getMyDeck().takeOneCard());
    }

    /**
     * on a le chat et on regarde la première carte du deck central
     * @param event clique sur la carte
     */
    public void handleSeeDeck(Event event){
        CardType c = deckCentral.readFirstCard();
        drawNewImage (ivPiocheCentrale, c.imageResource);
        bSeeDeck.setDisable(true);
    }

    /**
     * Affiche un pop up avec le détail de la particularité de la merveille
      * @param mouseEvent click simple de la souris sur la base de la merveille
     */
    public void displayWonderSpecialEffect(MouseEvent mouseEvent ){
        // on va afficher l'info sur la marveille
        //création d'un GridPane
        GridPane gridPane = new GridPane ();

        Image myImageTmp = null;

        ImageView iw =  ((ImageView)mouseEvent.getSource());

        if (iw.equals(this.ivAlexandrie1)){
            log.debug("On affiche le message d'Alexandrie");
            myImageTmp = new Image(getClass().getResourceAsStream(Wonder.Alexandrie.url));
        } else if (iw.equals(this.ivBabylon1)) {
            myImageTmp = new Image(getClass().getResourceAsStream(Wonder.Babylone.url));
        } else if (iw.equals(this.ivEphese1)) {
            myImageTmp = new Image(getClass().getResourceAsStream(Wonder.Ephese.url));
        } else if (iw.equals(this.ivGizeh1)) {
            myImageTmp = new Image(getClass().getResourceAsStream(Wonder.Gizeh.url));
        } else if (iw.equals(this.ivHalicarnasse1)) {
            myImageTmp = new Image(getClass().getResourceAsStream(Wonder.Halicarnasse.url));
        } else if (iw.equals(this.ivOlympie1)) {
            myImageTmp = new Image(getClass().getResourceAsStream(Wonder.Olympie.url));
        } else if (iw.equals(this.ivRhodes1)) {
            myImageTmp = new Image(getClass().getResourceAsStream(Wonder.Rhodes.url));
        }

        iw = new ImageView(myImageTmp);

        Stage newStage = new Stage();
        VBox comp = new VBox();
        comp.getChildren().add(iw);

        Scene stageScene = new Scene(comp, 200, 685);
        newStage.setScene(stageScene);
        newStage.show();
    }

    /**
     * on affiche la liste des joueurs dans le bandeau en haut
     * @param e click sur le bouton de sélection du nombre de joueur
     */
    public void cbUpdateListOfPLayers(Event e) {
        int nbOfPlayers = ((Integer)cbNumberOfPlayers.getValue()).intValue();

        playerName3.setVisible((nbOfPlayers>=3));
        lblPlayerName3.setVisible((nbOfPlayers>=3));
        playerName4.setVisible((nbOfPlayers>=4));
        lblPlayerName4.setVisible((nbOfPlayers>=4));
        playerName5.setVisible((nbOfPlayers>=5));
        lblPlayerName5.setVisible((nbOfPlayers>=5));
        playerName6.setVisible((nbOfPlayers>=6));
        lblPlayerName6.setVisible((nbOfPlayers>=6));
        playerName7.setVisible((nbOfPlayers>=7));
        lblPlayerName7.setVisible((nbOfPlayers>=7));
    }

    /**
     * ils sont tous présents dans le FXML, au début on les masque tous
     */
    private void hideProgressTokens(){
        bProgressTokenUrbanism.setVisible(false);
        bProgressTokenArtsAndCrafts.setVisible(false);
        bProgressTokenJewelry.setVisible(false);
        bProgressTokenScience.setVisible(false);
        bProgressTokenPropaganda.setVisible(false);
        bProgressTokenArchitecture.setVisible(false);
        bProgressTokenEconomy.setVisible(false);
        bProgressTokenIngeniery.setVisible(false);
        bProgressTokenTactic.setVisible(false);
        bProgressTokenDecoration.setVisible(false);
        bProgressTokenPolitic.setVisible(false);
        bProgressTokenStrategy.setVisible(false);
        bProgressTokenEducation.setVisible(false);
        bProgressTokenCulture.setVisible(false);
        bProgressCultureBis.setVisible(false);
    }

    /**
     * on les rend inactifs pendant le traitement de leur action
     */
    private void disableProgressTokensButtons(){
        bProgressToken1.setDisable(true);
        bProgressToken2.setDisable(true);
        bProgressToken3.setDisable(true);
        bProgressToken4.setDisable(true);
    }

    /**
     * met à jour les token sur l'écran
     * @param pt le token que l'on a et qui peuvent etre activé
     */
    private void displayAndActivateToken(ProgressToken pt){

        if (pt == ProgressToken.Architecture) {
            bProgressTokenUrbanism.setVisible(currentPlayer.isHasUrbanisme());
            bProgressTokenUrbanism.setDisable(!(currentPlayer.isHasUrbanisme()));
        }

        if (pt == ProgressToken.ArtsAndCrafts) {
            bProgressTokenArtsAndCrafts.setVisible(currentPlayer.isHasArtsAndCrafts());
            bProgressTokenArtsAndCrafts.setDisable(! (currentPlayer.isHasArtsAndCrafts()));
        }

        if (pt == ProgressToken.Jewelry) {
            bProgressTokenJewelry.setVisible(currentPlayer.isHasJewelry());
            bProgressTokenJewelry.setDisable(! (currentPlayer.isHasJewelry()));
        }

        if (pt == ProgressToken.Science) {
            bProgressTokenScience.setVisible(currentPlayer.isHasScience());
            bProgressTokenScience.setDisable(! (currentPlayer.isHasScience()));
        }

        if (pt == ProgressToken.Propaganda) {
            bProgressTokenPropaganda.setVisible(currentPlayer.isHasPropaganda());
            bProgressTokenPropaganda.setDisable(! (currentPlayer.isHasPropaganda()));
        }

        if (pt == ProgressToken.Architecture) {
            bProgressTokenArchitecture.setVisible(currentPlayer.isHasArchitecture());
            bProgressTokenArchitecture.setDisable(! (currentPlayer.isHasArchitecture()));
        }

        if (pt == ProgressToken.Economy) {
            bProgressTokenEconomy.setVisible(currentPlayer.isHasEconomy());
            bProgressTokenEconomy.setDisable(! (currentPlayer.isHasEconomy()));
        }

        if (pt == ProgressToken.Ingeniery) {
            bProgressTokenIngeniery.setVisible(currentPlayer.isHasIngeniery());
            bProgressTokenIngeniery.setDisable(! (currentPlayer.isHasIngeniery()));
        }

        if (pt == ProgressToken.Tactic) {
            bProgressTokenTactic.setVisible(currentPlayer.isHasTactic());
            bProgressTokenTactic.setDisable(! (currentPlayer.isHasTactic()));
        }

        if (pt == ProgressToken.Decoration) {
            bProgressTokenDecoration.setVisible(currentPlayer.isHasDecoration());
        }
        if (pt == ProgressToken.Politic) {
            bProgressTokenPolitic.setVisible(currentPlayer.isHasPolitic());
        }
        if (pt == ProgressToken.Strategy) {
            bProgressTokenStrategy.setVisible(currentPlayer.isHasStrategy());
        }
        if (pt == ProgressToken.Education) {
            bProgressTokenEducation.setVisible(currentPlayer.isHasEducation());
        }
        if (pt == ProgressToken.Culture) {
            bProgressTokenCulture.setVisible(currentPlayer.isHasCulture());
            bProgressCultureBis.setVisible(currentPlayer.isHasCultureBis());
        }
    }

    /**
     * traite la prise d'un token progres
     * @param event clique sur la souris
     */
    public void handlePiocheToken1(ActionEvent event){
        disableProgressTokensButtons();
        currentPlayer.addProgressToken(progressToken1);
        displayAndActivateToken(progressToken1);
        if (listOfToken.size()>0){
            progressToken1 = listOfToken.get(0);
            listOfToken.remove(progressToken1);
            drawNewImage(ivProgressToken1, progressToken1.imageResource);
        } else {
            ivProgressToken1.setVisible(false);
        }

        bFinDuTour.setDisable(false);
    }


    /**
     * traite la prise d'un token progres
     * @param event clique sur la souris
     */
    public void handlePiocheToken2(ActionEvent event){
        disableProgressTokensButtons();
        currentPlayer.addProgressToken(progressToken2);
        displayAndActivateToken(progressToken2);
        if (listOfToken.size()>0){
            progressToken2 = listOfToken.get(0);
            listOfToken.remove(progressToken2);
            drawNewImage(ivProgressToken2, progressToken2.imageResource);
        } else {
            ivProgressToken2.setVisible(false);
        }

        bFinDuTour.setDisable(false);
    }

    /**
     * traite la prise d'un token progres
     * @param event clique sur la souris
     */
    public void handlePiocheToken3(ActionEvent event){
        disableProgressTokensButtons();
        currentPlayer.addProgressToken(progressToken3);
        displayAndActivateToken(progressToken3);
        if (listOfToken.size()>0){
            progressToken3 = listOfToken.get(0);
            listOfToken.remove(progressToken3);
            drawNewImage(ivProgressToken3, progressToken3.imageResource);
        } else {
            ivProgressToken3.setVisible(false);
        }

        bFinDuTour.setDisable(false);
    }

    /**
     * traite la prise d'un token progres
     * @param event clique sur la souris
     */
    public void handlePiocheToken4(ActionEvent event){
        disableProgressTokensButtons();
        currentPlayer.addProgressToken(progressToken4);
        displayAndActivateToken(progressToken4);

        if (listOfToken.size()>0){
            progressToken4 = listOfToken.get(0);
            listOfToken.remove(progressToken4);
        }

        bFinDuTour.setDisable(false);

    }

    /**
     * Au démarage, affiche 4 tokens au hasard
     */
    private void initialisationProgressTokens(){
        listOfToken = new ArrayList<ProgressToken>();

        for (ProgressToken pt : ProgressTokens.TOKENS){
            listOfToken.add(pt);

        }

        // on prend 3 Token
        ProgressToken pt = listOfToken.get(0);
        drawNewImage(ivProgressToken1, pt.imageResource);
        listOfToken.remove(pt);
        progressToken1 = pt;


        pt = listOfToken.get(0);
        listOfToken.remove(pt);
        drawNewImage(ivProgressToken2, pt.imageResource);
        progressToken2 = pt;

        pt = listOfToken.get(0);
        listOfToken.remove(pt);
        drawNewImage(ivProgressToken3, pt.imageResource);
        progressToken3 = pt;

        // la pioche
        progressToken4 = listOfToken.get(0);

    }

    /**
     * affiche toutes les pioches
     * @param joueur le joueur dont on veut afficher les pioches
     */
    private void displayCardDeckJoueur(Joueur joueur){

        CardType c = joueur.getMyDeck().readFirstCard();

        if (c!=null){
            drawNewImage (ivPiocheGauche, c.imageResource);
            ivPiocheGauche.setVisible(true);
        } else {
            ivPiocheGauche.setVisible(false);
            ivPiocheCentrale.setDisable(true);
            currentPlayer.getIvMiniCard().setVisible(false);
        }

        c = joueur.getDeckJoueurDeDroite().readFirstCard();
        if (c!=null){
            drawNewImage (ivPiocheDroite, c.imageResource);
            ivPiocheDroite.setVisible(true);
            if (currentPlayer!=null) {
                currentPlayer.getJoueurSuivant().getIvMiniCard().setVisible(true);
            }
        } else {
            ivPiocheDroite.setVisible(false);
            ivPiocheDroite.setDisable(true);
            if (currentPlayer!=null) {
                currentPlayer.getJoueurSuivant().getIvMiniCard().setVisible(false);
            }
        }

        // doit rester carte cachée
        c = deckCentral.readFirstCard();
        if (c!=null){
            drawNewImage (ivPiocheCentrale, "images/cards/card-back/card-back-question.png");
        } else {
            ivPiocheCentrale.setVisible(false);
            ivPiocheCentrale.setDisable(true);
        }

        try {
            // on affiche le bandeau avec tous les joueurs
            drawNewImage(ivJoueur1, ((Joueur) listeDeJoueurs.get(0)).getMyDeck().readFirstCard().imageResource);
            if (((Joueur) listeDeJoueurs.get(0)).hasCat()) {
                drawNewImage(ivCardChatJoueur1, "images/tokens/token-cat.png");
            }
        } catch (Exception e) {
        }
        try {
            drawNewImage(ivJoueur2, ((Joueur) listeDeJoueurs.get(1)).getMyDeck().readFirstCard().imageResource);
            if (((Joueur) listeDeJoueurs.get(1)).hasCat()) {
                drawNewImage(ivCardChatJoueur2, "images/tokens/token-cat.png");
            }
        } catch (Exception e) {
        }
        try {
            if (listeDeJoueurs.size() > 2) {
                drawNewImage(ivJoueur3, ((Joueur) listeDeJoueurs.get(2)).getMyDeck().readFirstCard().imageResource);
                if (((Joueur) listeDeJoueurs.get(2)).hasCat()) {
                    drawNewImage(ivCardChatJoueur3, "images/tokens/token-cat.png");
                }
            }
        } catch (Exception e) {
        }
        try {
            if (listeDeJoueurs.size() > 3) {
                drawNewImage(ivJoueur4, ((Joueur) listeDeJoueurs.get(3)).getMyDeck().readFirstCard().imageResource);
                if (((Joueur) listeDeJoueurs.get(3)).hasCat()) {
                    drawNewImage(ivCardChatJoueur4, "images/tokens/token-cat.png");
                }
            }
        } catch (Exception e) {
        }
        try {
            if (listeDeJoueurs.size() > 4) {
                drawNewImage(ivJoueur5, ((Joueur) listeDeJoueurs.get(4)).getMyDeck().readFirstCard().imageResource);
                if (((Joueur) listeDeJoueurs.get(4)).hasCat()) {
                    drawNewImage(ivCardChatJoueur5, "images/tokens/token-cat.png");
                }
            }
        } catch (Exception e) {
        }
        try {
            if (listeDeJoueurs.size() > 5) {
                drawNewImage(ivJoueur6, ((Joueur) listeDeJoueurs.get(5)).getMyDeck().readFirstCard().imageResource);
                if (((Joueur) listeDeJoueurs.get(5)).hasCat()) {
                    drawNewImage(ivCardChatJoueur6, "images/tokens/token-cat.png");
                }
            }
        } catch (Exception e) {
        }
        try {
            if (listeDeJoueurs.size() > 6) {
                drawNewImage(ivJoueur7, ((Joueur) listeDeJoueurs.get(6)).getMyDeck().readFirstCard().imageResource);
                if (((Joueur) listeDeJoueurs.get(5)).hasCat()) {
                    drawNewImage(ivCardChatJoueur7, "images/tokens/token-cat.png");
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * affiche la merveille du joueur en cours de jeu
     * @param joueur
     */
    private void displayMaterialPlayer(Joueur joueur){

        HashMap <Material, Integer> currentPlayMaterial = joueur.getMaterial();
        lblQuantityWood.setText(Integer.toString(currentPlayMaterial.get(Material.Wood)));
        lblQuantityPaper.setText(Integer.toString(currentPlayMaterial.get(Material.Paper)));
        lblQuantityBrick.setText(Integer.toString(currentPlayMaterial.get(Material.Brick)));
        lblQuantityStone.setText(Integer.toString(currentPlayMaterial.get(Material.Stone)));
        lblQuantityGlass.setText(Integer.toString(currentPlayMaterial.get(Material.Glass)));
        lblQuantityGold.setText(Integer.toString(currentPlayMaterial.get(Material.Gold)));
        lblQuantityLaw.setText(Integer.toString(currentPlayer.getLaw()));
        lblQuantityArchitect.setText(Integer.toString(currentPlayer.getArchitect()));
        lblQuantityMechanic.setText(Integer.toString(currentPlayer.getMechanic()));
    }

    /**
     * affiche la merveille du joueur en cours de jeu
     * @param joueur
     */
    private void displayWonderPlayer(Joueur joueur){
        Wonder w = joueur.getWonder();

        wonderAlexandrie.setVisible(false);;
        wonderBabylon.setVisible(false);;
        wonderEphese.setVisible(false);;
        wonderGizeh.setVisible(false);;
        wonderHalicarnasse.setVisible(false);;
        wonderOlympie.setVisible(false);;
        wonderRhodes.setVisible(false);;

        if (w == Wonder.Alexandrie){
            wonderAlexandrie.setVisible(true);
        } else if (w == Wonder.Babylone){
            wonderBabylon.setVisible(true);
        } else if (w == Wonder.Ephese){
            wonderEphese.setVisible(true);
        } else if (w == Wonder.Gizeh){
            wonderGizeh.setVisible(true);
        } else if (w == Wonder.Halicarnasse){
            wonderHalicarnasse.setVisible(true);
        } else if (w == Wonder.Olympie){
            wonderOlympie.setVisible(true);
        } else if (w == Wonder.Rhodes){
            wonderRhodes.setVisible(true);;
        }
    }


    /**
         * le bouton qui lance le jeu
         */
    @FXML
    private Button bStartGame;

    @FXML
    private Button bProgressToken1;
    @FXML
    private Button bProgressToken2;
    @FXML
    private Button bProgressToken3;
    @FXML
    private Button bProgressToken4;
    /**
     * boutons qui controlent les actions du joueur en cours
     */
    @FXML
    private Button bSeeDeck;

    @FXML
    private Button bFinDuTour;

    @FXML
    private Button bPiocheGauche;
    @FXML
    private Button bPiocheDroite;
    @FXML
    private Button bPiocheCentrale;


    /**
     * le nom du joueur
     */
    @FXML
    private Pane initScreen;

    @FXML
    private Pane mainScreen;

    @FXML
    private Pane specialEffectHalicarnasse;

    @FXML
    private Pane specialEffectAlexandrie;

    /**
     * les merveilles
     */
    @FXML
    private Pane wonderAlexandrie;

    @FXML
    private Pane wonderBabylon;

    @FXML
    private Pane wonderEphese;

    @FXML
    private Pane wonderGizeh;

    @FXML
    private Pane wonderHalicarnasse;

    @FXML
    private Pane wonderOlympie;

    @FXML
    private Pane wonderRhodes;

    @FXML
    private GridPane gridEveryPlayers;

    /**
     * les paramètres de l'écran d'initialisation des joueurs
     */
    @FXML
    private ChoiceBox cbNumberOfPlayers;
    @FXML
    private TextField playerName1;
    @FXML
    private TextField playerName2;
    @FXML
    private TextField playerName3;
    @FXML
    private TextField playerName4;
    @FXML
    private TextField playerName5;
    @FXML
    private TextField playerName6;
    @FXML
    private TextField playerName7;

    @FXML
    private Label nomJoueur1;
    @FXML
    private Label nomJoueur2;
    @FXML
    private Label nomJoueur3;
    @FXML
    private Label nomJoueur4;
    @FXML
    private Label nomJoueur5;
    @FXML
    private Label nomJoueur6;
    @FXML
    private Label nomJoueur7;

    @FXML
    private ImageView ivCardChatJoueur1;
    @FXML
    private ImageView ivCardChatJoueur2;
    @FXML
    private ImageView ivCardChatJoueur3;
    @FXML
    private ImageView ivCardChatJoueur4;
    @FXML
    private ImageView ivCardChatJoueur5;
    @FXML
    private ImageView ivCardChatJoueur6;
    @FXML
    private ImageView ivCardChatJoueur7;
    @FXML
    private ImageView ivMerveilleJoueur1;
    @FXML
    private ImageView ivMerveilleJoueur2;
    @FXML
    private ImageView ivMerveilleJoueur3;
    @FXML
    private ImageView ivMerveilleJoueur4;
    @FXML
    private ImageView ivMerveilleJoueur5;
    @FXML
    private ImageView ivMerveilleJoueur6;
    @FXML
    private ImageView ivMerveilleJoueur7;

    @FXML
    private ImageView ivJoueur1;
    @FXML
    private ImageView ivJoueur2;
    @FXML
    private ImageView ivJoueur3;
    @FXML
    private ImageView ivJoueur4;
    @FXML
    private ImageView ivJoueur5;
    @FXML
    private ImageView ivJoueur6;
    @FXML
    private ImageView ivJoueur7;
    @FXML
    private Label nomDuJoueurEcran;

    @FXML
    private Label lblPlayerName1;
    @FXML
    private Label lblPlayerName2;
    @FXML
    private Label lblPlayerName3;
    @FXML
    private Label lblPlayerName4;
    @FXML
    private Label lblPlayerName5;
    @FXML
    private Label lblPlayerName6;
    @FXML
    private Label lblPlayerName7;

    /**
     * Les écrans des merveilles
     */
    @FXML
    private Image imgAlexandrie1;
    @FXML
    private Image imgAlexandrie2;
    @FXML
    private Image imgAlexandrie3;
    @FXML
    private Image imgAlexandrie4;
    @FXML
    private Image imgAlexandrie5;



    @FXML
    private Image imgEphese1;
    @FXML
    private Image imgEphese2;
    @FXML
    private Image imgEphese3;
    @FXML
    private Image imgEphese4;
    @FXML
    private Image imgEphese5;
    @FXML
    private Image imgGizeh1;
    @FXML
    private Image imgGizeh2;
    @FXML
    private Image imgGizeh3;
    @FXML
    private Image imgGizeh4;
    @FXML
    private Image imgHalicarnasse1;
    @FXML
    private Image imgHalicarnasse2;
    @FXML
    private Image imgHalicarnasse3;
    @FXML
    private Image imgHalicarnasse4;
    @FXML
    private Image imgHalicarnasse5;

    @FXML
    private Image imgOlympie1;
    @FXML
    private Image imgOlympie2;
    @FXML
    private Image imgOlympie3;
    @FXML
    private Image imgOlympie4;
    @FXML
    private Image imgOlympie5;

    @FXML
    private Image imgRhodes1;
    @FXML
    private Image imgRhodes2;
    @FXML
    private Image imgRhodes3;
    @FXML
    private Image imgRhodes4;
    @FXML
    private Image imgRhodes5;
    @FXML
    private ImageView ivAlexandrie1;
    @FXML
    private ImageView ivAlexandrie2;
    @FXML
    private ImageView ivAlexandrie3;
    @FXML
    private ImageView ivAlexandrie4;
    @FXML
    private ImageView ivAlexandrie5;

    @FXML
    private ImageView ivBabylon1;
    @FXML
    private ImageView ivBabylon2;

    @FXML
    private ImageView ivBabylon3;
    @FXML
    private ImageView ivBabylon4;
    @FXML
    private ImageView ivBabylon5;

    @FXML
    private ImageView ivEphese1;
    @FXML
    private ImageView ivEphese2;
    @FXML
    private ImageView ivEphese3;
    @FXML
    private ImageView ivEphese4;
    @FXML
    private ImageView ivEphese5;
    @FXML
    private ImageView ivOlympie1;
    @FXML
    private ImageView ivOlympie2;
    @FXML
    private ImageView ivOlympie3;
    @FXML
    private ImageView ivOlympie4;
    @FXML
    private ImageView ivOlympie5;
    @FXML
    private ImageView ivRhodes1;
    @FXML
    private ImageView ivRhodes2;
    @FXML
    private ImageView ivRhodes3;
    @FXML
    private ImageView ivRhodes4;
    @FXML
    private ImageView ivRhodes5;
    @FXML
    private ImageView ivHalicarnasse1;
    @FXML
    private ImageView ivHalicarnasse2;
    @FXML
    private ImageView ivHalicarnasse3;
    @FXML
    private ImageView ivHalicarnasse4;
    @FXML
    private ImageView ivHalicarnasse5;
    @FXML
    private ImageView ivGizeh1;
    @FXML
    private ImageView ivGizeh2;
    @FXML
    private ImageView ivGizeh3;
    @FXML
    private ImageView ivGizeh4;

    @FXML
    private ImageView ivGizeh5;

    /**
     * les pioches
     */
    @FXML
    private ImageView ivPiocheGauche;
    @FXML
    private ImageView ivPiocheDroite;
    @FXML
    private ImageView ivPiocheCentrale;

    /**
     * le Chat
     */
    @FXML
    private ImageView ivChat;

    /**
     * Les ressources du player en cours
     */
     @FXML
    private Label lblQuantityWood;
    @FXML
    private Label lblQuantityPaper;
    @FXML
    private Label lblQuantityBrick;
    @FXML
    private Label lblQuantityStone;
    @FXML
    private Label lblQuantityGlass;
    @FXML
    private Label lblQuantityGold;
    @FXML
    private Label lblQuantityLaw;
    @FXML
    private Label lblQuantityArchitect;
    @FXML
    private Label lblQuantityMechanic;

    /**
     * les Tokens Verts
     */
    @FXML
    private ImageView ivProgressToken1;  // face visible
    @FXML
    private ImageView ivProgressToken2;  // face visible
    @FXML
    private ImageView ivProgressToken3;  // face visible
    @FXML
    private ImageView ivProgressToken4;  // face cachée

    private ProgressToken progressToken1;
    private ProgressToken progressToken2;
    private ProgressToken progressToken3;
    private ProgressToken progressToken4;

    @FXML
    private Button bProgressTokenUrbanism;

    private CardType lastCardTaken;

    /**
     * traite l'utilisation du Token Urbainsme,
     * @param event l'action sur la souris
     */
    public void handleTokenUrbanism(Event event){
        // Lorsque vous prenez une carte grise 'bois' ou 'brique',
        // choisissez 1 carte supplémentaire parmi les 3 disponibles et posez-la devant vous
        if ((lastCardTaken!=null) && ((lastCardTaken==CardType.CardMaterialWood) || (lastCardTaken==CardType.CardMaterialBrick))){
            // une seule fois par tours
            bProgressTokenUrbanism.setDisable(true);

            // on a le droit à une pioche suplémentaire
            bPiocheCentrale.setDisable(deckCentral.size()==0);
            bPiocheGauche.setDisable(currentPlayer.getMyDeck().size()==0);
            bPiocheDroite.setDisable(currentPlayer.getDeckJoueurDeDroite().size()==0);
            lastCardTaken = null;
        }
    }

    @FXML
    private Button bProgressTokenArtsAndCrafts;

    /**
     * traite l'utilisation du Token ArtsAndCrafts,
     * @param event l'action sur la souris
     */
    public void handleTokenArtsAndCrafts(Event event){
        // Lorsque vous prenez une carte grise 'bois' ou 'brique',
        // choisissez 1 carte supplémentaire parmi les 3 disponibles et posez-la devant vous
        if ((lastCardTaken!=null) && ((lastCardTaken==CardType.CardMaterialWood) || (lastCardTaken==CardType.CardMaterialGlass))){
            // une seule fois par tours
            bProgressTokenArtsAndCrafts.setDisable(true);

            // on a le droit à une pioche suplémentaire
            bPiocheCentrale.setDisable(deckCentral.size()==0);
            bPiocheGauche.setDisable(currentPlayer.getMyDeck().size()==0);
            bPiocheDroite.setDisable(currentPlayer.getDeckJoueurDeDroite().size()==0);
            lastCardTaken = null;
        }
    }

    @FXML
    private Button bProgressTokenJewelry;

    /**
     * traite l'utilisation du Token Jewelry,
     * @param event action de cliquer
     */
    public void handleTokenJewelry(Event event){
        // grise 'pierre' ou une carte jaune,
        // choisissez 1 carte supplémentaire parmi les 3 disponibles et posez-la devant vous
        if ((lastCardTaken!=null) && ((lastCardTaken==CardType.CardMaterialStone) || (lastCardTaken==CardType.CardMaterialGold))){
            // une seule fois par tours
            bProgressTokenJewelry.setDisable(true);

            // on a le droit à une pioche suplémentaire
            bPiocheCentrale.setDisable(deckCentral.size()==0);
            bPiocheGauche.setDisable(currentPlayer.getMyDeck().size()==0);
            bPiocheDroite.setDisable(currentPlayer.getDeckJoueurDeDroite().size()==0);
            lastCardTaken = null;
        }
    }
    @FXML
    private Button bProgressTokenScience;

    /**
     * traite l'utilisation du Token Science,
     * @param event action de cliquer
     */
    public void handleTokenScience(Event event){
        // Lorsque vous prenez une carte verte,
        // choisissez 1 carte supplémentaire parmi les 3 disponibles et posez-la devant vous
        if ((lastCardTaken!=null) && ((lastCardTaken==CardType.CardScienceLaw) || (lastCardTaken==CardType.CardScienceArchitect)|| (lastCardTaken==CardType.CardScienceMechanic))){
            // une seule fois par tours
            bProgressTokenScience.setDisable(true);

            // on a le droit à une pioche suplémentaire
            bPiocheCentrale.setDisable(deckCentral.size()==0);
            bPiocheGauche.setDisable(currentPlayer.getMyDeck().size()==0);
            bPiocheDroite.setDisable(currentPlayer.getDeckJoueurDeDroite().size()==0);
            lastCardTaken = null;
        }
    }
    @FXML
    private Button bProgressTokenPropaganda;

    /**
     * traite l'utilisation du Token Propaganda,
     * @param event action de cliquer
     */
    public void handleTokenPropaganda(Event event){
        // Lorsque vous prenez une carte Rouge avec 1 ou 2 icônes 'corne',
        // choisissez 1 carte supplémentaire parmi les 3 disponibles et posez-la devant vous
        if ((lastCardTaken!=null) && ((lastCardTaken==CardType.CardWar_archer) || (lastCardTaken==CardType.CardWar_barbarian))){
            // une seule fois par tours
            bProgressTokenPropaganda.setDisable(true);

            // on a le droit à une pioche suplémentaire
            bPiocheCentrale.setDisable(deckCentral.size()==0);
            bPiocheGauche.setDisable(currentPlayer.getMyDeck().size()==0);
            bPiocheDroite.setDisable(currentPlayer.getDeckJoueurDeDroite().size()==0);
            lastCardTaken = null;
        }
    }
    @FXML
    private Button bProgressTokenArchitecture;

    /**
     * traite l'utilisation du Token Architecture,
     */
    public void handleTokenArchitecture(){
        // Lorsque vous construisez une étape,
        // choisissez 1 carte supplémentaire parmi les 3 disponibles et posez-la devant vous
        if (! currentPlayer.getWonderStructure().isWonderAchieved()){
            // une seule fois par tours
            bProgressTokenArchitecture.setDisable(true);

            // on a le droit à une pioche suplémentaire
            bPiocheCentrale.setDisable(deckCentral.size()==0);
            bPiocheGauche.setDisable(currentPlayer.getMyDeck().size()==0);
            bPiocheDroite.setDisable(currentPlayer.getDeckJoueurDeDroite().size()==0);
        }
    }
    @FXML
    private Button bProgressTokenEconomy;

    /**
     * action du token Economy
     * @param event clique sur la souris
     */
    public void handleTokenEconomy(Event event){
        int gold = currentPlayer.getMaterial().get(Material.Gold);

        // 1 carte jaune en votre possession vous offre 2 pièces au lieu d'une seule
        if (gold>0){
            // une seule fois par tours
            bProgressTokenEconomy.setDisable(true);

            // on a le droit à une pioche suplémentaire
            currentPlayer.getMaterial().replace(Material.Gold, gold+1);
        }
    }
    @FXML
    private Button bProgressTokenIngeniery;

    /**
     * action du token Ingeniery
     * @param event clique sur la souris
     */
    public void handleTokenIngeniery(Event event){
        bProgressTokenIngeniery.setDisable(true);

        currentPlayer.setMaterial(transmuteElements(currentPlayer.getMaterial()));
        Wonder w = currentPlayer.getWonder();

        if (currentPlayer.getWonderStructure().isConstructionPossible(currentPlayer.getMaterial())){
            if (w == Wonder.Alexandrie){  // test ok merveille et mini merveille
                updateAlexandrie();
            } else if (w == Wonder.Babylone){ // test ok merveille et mini merveille
                updateBabylon();
            } else if (w == Wonder.Ephese){ // test ok merveille et mini merveille
                updateEphese();
            } else if (w == Wonder.Gizeh){ // test ok merveille et mini merveille
                updateGizeh();
            } else if (w == Wonder.Halicarnasse){ // test ok merveille et mini merveille
                updateHalicarnasse();
            } else if (w == Wonder.Olympie){
                updateOlympie();
            }  else if (w == Wonder.Rhodes){ // test ok merveille et mini merveille
                updateRhodes();
            }
        } else {
            bProgressTokenIngeniery.setDisable(false);
        }
    }

    /**
     * tester uniquement sur une valeur
     * @param materials la liste courante de matériaux
     * @return la liste modifiée
     */
    private HashMap<Material, Integer> transmuteElements(HashMap<Material, Integer> materials){
        HashMap<Material, Integer> tmpMaterials = new HashMap<>();

        boolean isSuccess= false;

        // les cas possibles
        // case 1 : 2 identiques vers 2 différents (pas d'or) -> on en change 1 en or
        // case 2 : 2 differents vers 2 iddentiques (pas d'or) -> on en change 1 en or

        // case 3 : 2 identiques et un différent (pas d'or), on veut 3 identiques -> on change le différent en or
        // case 4 : 2 différents et un d'or et on veut 3 identiques  -> on transmute un en or
        // case 5 : 2 différents (quantité >2) on transmute 1 pour 3 identiques on en transmute un dont quantité >= 1 en or en
        // case 6 : 2 différents et un d'or  -> on change 1 en or -> on en transmute 1 en or

        // on commence par les plus gros
        int totalIdentique = 0;
        int totalDifferents = 0;
        int minimum = 100;
        int gold = 0;
        int value = 0;
        Material biggestMaterial = null;
        Material smallestMaterial = null;
        Material key = null;

        for (Iterator i = materials.keySet().iterator(); i.hasNext(); ) {
            key = (Material) i.next();
            value = Integer.valueOf(materials.get(key));

            tmpMaterials.put(key, value);

            if (value > 0) {
                if (!key.equals(Material.Gold)) {
                    totalDifferents++;
                    if (totalIdentique < value) {
                        biggestMaterial = key;
                        totalIdentique = value;
                    }

                    if (minimum > value) {
                        minimum = value;
                        smallestMaterial = key;
                    }
                } else {
                    gold = value;
                }
            }
        }

        // case 6
        if ((totalDifferents == 2)  && (gold>0)) {
            value = materials.get(biggestMaterial);
            tmpMaterials.replace(biggestMaterial, value - 1);
            tmpMaterials.replace(Material.Gold, gold + 1);
            isSuccess = true;
        }

        // case 5 : 2 différents (quantité >2)
        else if ((!isSuccess) && ((totalDifferents == 2) && (totalIdentique == 2))) {
            value = materials.get(smallestMaterial);
            tmpMaterials.replace(smallestMaterial, value - 1);
            tmpMaterials.replace(Material.Gold, gold + 1);
            isSuccess = true;
        }

        // caes 4 : 2 différents et un d'or et on veut 3 identiques  -> on transmute un en or
        else if ((!isSuccess) && ((totalDifferents == 2) && (totalIdentique == 1)) && (gold>0)) {
            value = materials.get(smallestMaterial);
            tmpMaterials.replace(smallestMaterial, value - 1);
            tmpMaterials.replace(Material.Gold, gold + 1);
            isSuccess = true;
        }


        // case 3 : 2 identiques et un différent (pas d'or), on veut 3 identiques -> on change le différent en or
        else if ((!isSuccess) && ((totalDifferents == 2) && (totalIdentique == 2))) {
            value = materials.get(smallestMaterial);
            tmpMaterials.replace(smallestMaterial, value - 1);
            tmpMaterials.replace(Material.Gold, gold + 1);
            isSuccess = true;
        }

        // 2 differents vers 2 identiques (pas d'or) -> on en change 1 en or
        else if ((!isSuccess) && ((totalDifferents == 2) && (totalIdentique == 1))) {
            value = materials.get(smallestMaterial);
            tmpMaterials.replace(smallestMaterial, value - 1);
            tmpMaterials.replace(Material.Gold, gold + 1);
            isSuccess = true;
        }


        // 2 identiques vers 2 différents (pas d'or) -> on en change 1 en or
        else if ((!isSuccess) && ((totalDifferents == 1) && (totalIdentique == 2))) {
            value = materials.get(smallestMaterial);
            tmpMaterials.replace(smallestMaterial, value - 1);
            tmpMaterials.replace(Material.Gold, gold + 1);
            isSuccess = true;
        }


        if (isSuccess && currentPlayer.getWonderStructure().isConstructionPossible(tmpMaterials)) {
            return tmpMaterials;
        } else {
            // on n'a pas trouvé
            return materials;
        }
    }

    @FXML
    private Button bProgressTokenTactic;

    /**
     * traitement des Token Tactic (special effect)
     * @param event clique sur le token
     */
    public void handleTokenTactic(Event event){
        currentPlayer.addBouclier(currentPlayer.getNbBouclier()+2);
    }

    @FXML
    private Button bProgressTokenDecoration;

    @FXML
    private Button bProgressTokenPolitic;

    @FXML
    private Button bProgressTokenStrategy;

    @FXML
    private Button bProgressTokenEducation;

    @FXML
    private Button bProgressTokenCulture;

    @FXML
    private Button bProgressCultureBis;


    private void drawNewImage (ImageView iv, String url) {
        try {
            double preferedWith = iv.getFitWidth();
            double preferedHeight = iv.getFitHeight();

            iv.setImage(new Image(getClass().getResourceAsStream(url)));
            iv.setFitWidth(preferedWith);
            iv.setFitHeight(preferedHeight);
        } catch (Exception e) {
            log.error("Impossible de dessiner l'image demandée " + url);
        }
    }

    private void displayChatJoueurs(){
        ivCardChatJoueur1.setVisible((listeDeJoueurs.get(0)).hasCat());
        ivCardChatJoueur2.setVisible((listeDeJoueurs.get(1)).hasCat());

        if (listeDeJoueurs.size() > 2) {
            ivCardChatJoueur3.setVisible((listeDeJoueurs.get(2)).hasCat());
        }

        if (listeDeJoueurs.size() > 3) {
            ivCardChatJoueur4.setVisible((listeDeJoueurs.get(3)).hasCat());
        }

        if (listeDeJoueurs.size() > 4) {
            ivCardChatJoueur5.setVisible((listeDeJoueurs.get(4)).hasCat());
        }

        if (listeDeJoueurs.size() > 5) {
            ivCardChatJoueur6.setVisible((listeDeJoueurs.get(5)).hasCat());
        }

        if (listeDeJoueurs.size() > 6) {
            ivCardChatJoueur7.setVisible((listeDeJoueurs.get(6)).hasCat());
        }
    }

    private void handlePrendreUneCarteParmi5GaucheOuDroite(){
        // on masque l'écran de jeu
        this.mainScreen.setVisible(false);
        this.mainScreen.setDisable(true);


        GridPane gp = (GridPane)specialEffectHalicarnasse.lookup("#specialEffectHalicarnasseGridPane");

        Button bSpecialHalicarnasseGauche = (Button)specialEffectHalicarnasse.lookup("#bSpecialHalicarnasseGauche");
        Button bSpecialHalicarnasseDroit = (Button)specialEffectHalicarnasse.lookup("#bSpecialHalicarnasseDroit");
        bSpecialHalicarnasseGauche.setDisable(true);
        bSpecialHalicarnasseDroit.setDisable(true);

        Pane paneTmp;
        ImageView iv;

        paneTmp = (Pane)getNodeFromGridPane(gp, 0, 0);
        iv = (ImageView)paneTmp.getChildren().get(0);

        CardType joueurCourant = currentPlayer.getMyDeck().readFirstCard();
        drawNewImage(iv, joueurCourant.imageResource);

        // les autres on met des points d'interrogation

        int nbOfCards = currentPlayer.getMyDeck().size();

        String url = getWonderUrl(currentPlayer.getWonder());

        // on met à jour les cartes qu'on affiche

        // on a les cartes des deux decks, le reste est en dos de carte

        paneTmp = (Pane)getNodeFromGridPane(gp, 0, 0);
        iv = (ImageView) paneTmp.getChildren().get(0);
        drawNewImage(iv, joueurCourant.imageResource);

        if (nbOfCards>1) {
            bSpecialHalicarnasseGauche.setDisable(false);
            paneTmp = (Pane) getNodeFromGridPane(gp, 2, 0);
            iv = (ImageView) paneTmp.getChildren().get(0);
            drawNewImage(iv, url);
        }

        if (nbOfCards>2) {
            paneTmp = (Pane) getNodeFromGridPane(gp, 4, 0);
            iv = (ImageView) paneTmp.getChildren().get(0);
            drawNewImage(iv, url);
        }

        if (nbOfCards>3) {
            paneTmp = (Pane) getNodeFromGridPane(gp, 6, 0);
            iv = (ImageView) paneTmp.getChildren().get(0);
            drawNewImage(iv, url);
        }

        if (nbOfCards>4) {
            paneTmp = (Pane) getNodeFromGridPane(gp, 8, 0);
            iv = (ImageView) paneTmp.getChildren().get(0);
            drawNewImage(iv, url);
        }

        CardType voisin = currentPlayer.getDeckJoueurDeDroite().readFirstCard();
        url = getWonderUrl(currentPlayer.getJoueurDeDroite().getWonder());
        nbOfCards = currentPlayer.getDeckJoueurDeDroite().size();

        paneTmp = (Pane)getNodeFromGridPane(gp, 0, 2);
        iv = (ImageView) paneTmp.getChildren().get(0);
        drawNewImage(iv, voisin.imageResource);

        if (nbOfCards>1) {
            bSpecialHalicarnasseDroit.setDisable(false);
            paneTmp = (Pane) getNodeFromGridPane(gp, 2, 2);
            iv = (ImageView) paneTmp.getChildren().get(0);
            drawNewImage(iv, url);
        }

        if (nbOfCards>2) {
            paneTmp = (Pane) getNodeFromGridPane(gp, 4, 2);
            iv = (ImageView) paneTmp.getChildren().get(0);
            drawNewImage(iv, url);
        }

        if (nbOfCards>3) {
            paneTmp = (Pane) getNodeFromGridPane(gp, 6, 2);
            iv = (ImageView) paneTmp.getChildren().get(0);
            drawNewImage(iv, url);
        }

        if (nbOfCards>4) {
            paneTmp = (Pane) getNodeFromGridPane(gp, 8, 2);
            iv = (ImageView) paneTmp.getChildren().get(0);
            drawNewImage(iv, url);
        }


        // on affiche et active celui d'Halicarnasse
        specialEffectHalicarnasse.setDisable(false);
        specialEffectHalicarnasse.setVisible(true);

    }


    private String getWonderUrl(Wonder w){
        String url = "";

        switch (w){
            case Alexandrie ->
                    url = "images/cards/card-back/card-back-alexandrie.png";

            case Babylone ->
                    url = "images/cards/card-back/card-back-babylon.png";

            case Ephese ->
                    url = "images/cards/card-back/card-back-ephese.png";

            case Gizeh ->
                    url = "images/cards/card-back/card-back-gizeh.png";

            case Halicarnasse ->
                    url = "images/cards/card-back/card-back-halicarnasse.png";

            case Olympie ->
                    url = "images/cards/card-back/card-back-olympie.png";

            case Rhodes ->
                    url = "images/cards/card-back/card-back-rhodes.png";
        }
        return url;
    }
    /**
     * on tire une carte du deck central
     */
    private void handlePrendrePremierCartePiocheCentrale(){
        handlePioche(deckCentral.takeOneCard());
    }

    /**
     * action spéciale d'Olympie on prend deux cartes
     */
    private void handlePrendreUneCarteGauchePuisDroite(){
        handlePioche(currentPlayer.getMyDeck().takeOneCard());
        handlePioche(currentPlayer.getDeckJoueurDeDroite().takeOneCard());
    }

    /**
     * preparation de l'écran traintant l'effet d'Alexandrie
     */
    private void handlePiocheAlexandrie(){
        // on masque l'écran de jeu
        mainScreen.setVisible(false);
        mainScreen.setDisable(true);
        specialEffectAlexandrie.setVisible(true);
        specialEffectAlexandrie.setDisable(false);

        GridPane gp = (GridPane)specialEffectAlexandrie.lookup("#specialEffectAlexandrieGridPane");


        Pane paneTmp;
        ImageView iv;
        Label label = null;

        int counteur = 0;

        for (Joueur joueur : listeDeJoueurs){
            paneTmp = (Pane)getNodeFromGridPane(gp, counteur, 0);
            iv = (ImageView)paneTmp.getChildren().get(0);
            drawNewImage(iv, joueur.getMyDeck().readFirstCard().imageResource);
            iv.setDisable(false);

            paneTmp = (Pane)getNodeFromGridPane(gp, counteur, 1);
            ((Label)paneTmp.getChildren().get(0)).setText(joueur.getNom());

            counteur += 2;
        }

        counteur = 2* listeDeJoueurs.size();
        for (int i=listeDeJoueurs.size(); i<7; i++){
            paneTmp = (Pane)getNodeFromGridPane(gp, counteur, 0);
            iv = (ImageView)paneTmp.getChildren().get(0);
            iv.setVisible(false);
            iv.setDisable(true);

            paneTmp = (Pane)getNodeFromGridPane(gp, counteur, 1);
            ((Label)paneTmp.getChildren().get(0)).setVisible(false);

            counteur += 2;
        }
    }

    private void handlePrendreJetonProgres(){
        this.bFinDuTour.setDisable(true);

        bProgressToken1.setDisable(!bProgressToken1.isVisible());
        bProgressToken2.setDisable(!bProgressToken2.isVisible());
        bProgressToken3.setDisable(!bProgressToken3.isVisible());
        bProgressToken4.setDisable(!bProgressToken4.isVisible());
    }

    private void handleFinDuJeu () {
        for (Joueur joueur : listeDeJoueurs){
            joueur.calculateFinalScore();
        }

        // utilise le fait que Joueur implemente compareTo
        Collections.sort(listeDeJoueurs);

        StringBuffer sb = new StringBuffer(200);
        sb.append(listeDeJoueurs.get(0));

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Le Jeu est terminé, le vainqueur est ");
        alert.setContentText(sb.toString());
        alert.showAndWait().ifPresent(rs -> {

            if (rs == ButtonType.OK) {
                // on ne fait plus rien
                System.exit(0);
            }
        });
    }


    private void handleWar(){
        // gérer les tokens
        int myShields = 0;
        int opponantShields = 0;
        nbTokenWar = 0;

        for (Joueur tmpJoueur : listeDeJoueurs){
            // on regarde le joueur avec son voisin suivant
            myShields = tmpJoueur.getNbBouclier();
            opponantShields = tmpJoueur.getJoueurSuivant().getNbBouclier();

            // traitement du cas : 2 joueurs
            if ((myShields>(2*opponantShields)) && (listeDeJoueurs.size()==2)) {
                tmpJoueur.addMilitaryPointsCentury(2);
            } else if (myShields>opponantShields) {
                tmpJoueur.addMilitaryPointsCentury(1);
            }
        }

        // on jette les cartes
        for (Joueur tmpJoueur : listeDeJoueurs){
            // on regarde le joueur avec son voisin suivant
            tmpJoueur.resetMilitaryPointsBarbarian();
            tmpJoueur.resetBouclier();
        }

        for (ImageView iv : listIvToken){
            drawNewImage(iv, "/images/tokens/token-conflict-peace.png");
        }
    }

    private ArrayList<ImageView> listIvToken;
    @FXML
    private ImageView ivToken1;

    @FXML
    private ImageView ivToken2;

    @FXML
    private ImageView ivToken3;

    @FXML
    private ImageView ivToken4;
    @FXML
    private ImageView ivToken5;
    @FXML
    private ImageView ivToken6;

    /**
     * mise à jour de la représentation visuelle de la merveille Alexandrie
     */
    private void updateAlexandrie(){
        currentPlayer.setMaterial(currentPlayer.getWonderStructure().buildOneFloor(currentPlayer.getMaterial()));
        int constructionLevel = currentPlayer.getWonderStructure().getStageConstructionActuel();
        ImageView iv = currentPlayer.getIvMiniMerveille();

        if (constructionLevel>0) {
            drawNewImage(ivAlexandrie1, WonderCardType.AlexandrieBaseFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.alexandrieSteps.get(1).url);
        }

        if (constructionLevel>1) {
            drawNewImage(ivAlexandrie2, WonderCardType.AlexandrieFirstFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.alexandrieSteps.get(2).url);
        }

        if (constructionLevel>2) {
            drawNewImage(ivAlexandrie3, WonderCardType.AlexandrieSecondFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.alexandrieSteps.get(3).url);
        }

        if (constructionLevel>3) {
            drawNewImage(ivAlexandrie4, WonderCardType.AlexandrieTroisiemeFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.alexandrieSteps.get(4).url);
        }

        if (constructionLevel>4) {
            drawNewImage(ivAlexandrie5, WonderCardType.AlexandrieDernierFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.alexandrieSteps.get(5).url);
        }
    }

    /**
     * met à jour l'affichage de l'évolution de la merveille jardins suspendus de Babylon. Test Ok
     */
    private void updateBabylon(){
        currentPlayer.setMaterial(currentPlayer.getWonderStructure().buildOneFloor(currentPlayer.getMaterial()));
        int constructionLevel = currentPlayer.getWonderStructure().getStageConstructionActuel();
        ImageView iv = currentPlayer.getIvMiniMerveille();

        if (constructionLevel>0) {
            drawNewImage(ivBabylon1, WonderCardType.BabylonBaseFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.babylonSteps.get(1).url);
        }

        if (constructionLevel>1) {
            drawNewImage(ivBabylon2, WonderCardType.BabylonFirstFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.babylonSteps.get(2).url);
        }

        if (constructionLevel>2) {
            drawNewImage(ivBabylon3, WonderCardType.BabylonSecondFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.babylonSteps.get(3).url);
        }

        if (constructionLevel>3) {
            if ( !((Babylon)currentPlayer.getWonderStructure()).isbPartieGaucheFaite()){
                // on veut construire la tour gauche
                drawNewImage(ivBabylon4, WonderCardType.BabylonTroisiemeFront.imageResource);
            } else if ( !((Babylon)currentPlayer.getWonderStructure()).isbPartieDroiteFaite()){
                drawNewImage(ivBabylon5, WonderCardType.BabylonQuatriemeFront.imageResource);
            } else if ((((Babylon)currentPlayer.getWonderStructure()).isbPartieGaucheFaite()) && ((Babylon)currentPlayer.getWonderStructure()).isbPartieDroiteFaite()){
                drawNewImage(ivBabylon4, WonderCardType.BabylonTroisiemeFront.imageResource);
                drawNewImage(ivBabylon5, WonderCardType.BabylonQuatriemeFront.imageResource);
            }

            if(((Babylon)currentPlayer.getWonderStructure()).isbPartieGaucheFaite() && ((Babylon)currentPlayer.getWonderStructure()).isbPartieDroiteFaite()){
                drawNewImage(iv, WonderConstructionSteps.babylonSteps.get(6).url);
            } else if (((Babylon)currentPlayer.getWonderStructure()).isbPartieGaucheFaite()){
                drawNewImage(iv, WonderConstructionSteps.babylonSteps.get(4).url);
            } else if (((Babylon)currentPlayer.getWonderStructure()).isbPartieDroiteFaite()){
                drawNewImage(iv, WonderConstructionSteps.babylonSteps.get(5).url);
            }
        }
    }

    /**
     * met à jour l'affichage de l'évolution de la merveille principale : Ephesese. Test Ok
     */
    private void updateEphese(){
        currentPlayer.setMaterial(currentPlayer.getWonderStructure().buildOneFloor(currentPlayer.getMaterial()));
        int constructionLevel = currentPlayer.getWonderStructure().getStageConstructionActuel();
        ImageView iv = currentPlayer.getIvMiniMerveille();

        Ephese ws = (Ephese)currentPlayer.getWonderStructure();

        if (constructionLevel>0) {
            drawNewImage(ivEphese1, WonderCardType.EpheseFirstFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.epheseSteps.get(1).url);
        }

        if (constructionLevel>1) {
            if (ws.isbColonneGaucheFait()){
                drawNewImage(ivEphese2, WonderCardType.EpheseSecondFront.imageResource);
                drawNewImage(iv, WonderConstructionSteps.epheseSteps.get(2).url);
            }
            if (ws.isbColonneMilieuFait()){
                drawNewImage(ivEphese3, WonderCardType.EpheseTroisiemeFront.imageResource);
                drawNewImage(iv, WonderConstructionSteps.epheseSteps.get(3).url);
            }
            if (ws.isbColonneDroitFait()){
                drawNewImage(ivEphese4, WonderCardType.EpheseQuatriemeFront.imageResource);
                drawNewImage(iv, WonderConstructionSteps.epheseSteps.get(4).url);
            }
        }

        if (constructionLevel>8) {
            drawNewImage(ivEphese5, WonderCardType.EpheseDernierFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.epheseSteps.get(9).url);
        }
        else {
            if (ws.isbColonneGaucheFait() && ws.isbColonneMilieuFait() && ws.isbColonneDroitFait() ) {
                drawNewImage(iv, WonderConstructionSteps.epheseSteps.get(8).url);
            } else if (ws.isbColonneGaucheFait() && ws.isbColonneMilieuFait()){
                drawNewImage(iv, WonderConstructionSteps.epheseSteps.get(5).url);
            } else if (ws.isbColonneGaucheFait() && ws.isbColonneDroitFait()){
                drawNewImage(iv, WonderConstructionSteps.epheseSteps.get(6).url);
            } else  if (ws.isbColonneMilieuFait() && ws.isbColonneDroitFait()){
                drawNewImage(iv, WonderConstructionSteps.epheseSteps.get(7).url);
            }
        }
    }

    /**
     * met à jour l'affichage de l'évolution de la merveille principale : Pyramide de Gizeh. Test Ok
     */
    private void updateGizeh(){
        currentPlayer.setMaterial(currentPlayer.getWonderStructure().buildOneFloor(currentPlayer.getMaterial()));
        int constructionLevel = currentPlayer.getWonderStructure().getStageConstructionActuel();
        ImageView iv = currentPlayer.getIvMiniMerveille();

        if (constructionLevel>0) {
            drawNewImage(ivGizeh1, WonderCardType.GizehBaseFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.gizehSteps.get(1).url);
        }

        if (constructionLevel>1) {
            drawNewImage(ivGizeh2, WonderCardType.GizehFirstFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.gizehSteps.get(2).url);
        }

        if (constructionLevel>2) {
            drawNewImage(ivGizeh3, WonderCardType.GizehSecondFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.gizehSteps.get(3).url);
        }

        if (constructionLevel>3) {
            drawNewImage(ivGizeh4, WonderCardType.GizehTroisiemeFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.gizehSteps.get(4).url);
        }


        if (constructionLevel>4) {
            drawNewImage(ivGizeh5, WonderCardType.GizehDernierFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.gizehSteps.get(5).url);
        }
    }

    /**
     * met à jour l'affichage de l'évolution de la merveille principale : Halicarnasse. Test Ok
     */
    private void updateHalicarnasse(){
        currentPlayer.setMaterial(currentPlayer.getWonderStructure().buildOneFloor(currentPlayer.getMaterial()));
        int constructionLevel = currentPlayer.getWonderStructure().getStageConstructionActuel();
        ImageView iv = currentPlayer.getIvMiniMerveille();

        if (constructionLevel>0) {
            drawNewImage(ivHalicarnasse1, WonderCardType.HalicarnasseBaseFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.halicarnasseSteps.get(1).url);
        }

        if (constructionLevel>1) {
            drawNewImage(ivHalicarnasse2, WonderCardType.HalicarnasseFirstFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.halicarnasseSteps.get(2).url);
        }

        if (constructionLevel>2) {
            drawNewImage(ivHalicarnasse3, WonderCardType.HalicarnasseSecondFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.halicarnasseSteps.get(3).url);
        }

        if (constructionLevel>3) {
            drawNewImage(ivHalicarnasse4, WonderCardType.HalicarnasseTroisièmeFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.halicarnasseSteps.get(4).url);
        }

        if (constructionLevel>4) {
            drawNewImage(ivHalicarnasse5, WonderCardType.HalicarnasseDernierFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.halicarnasseSteps.get(5).url);
        }

    }

    /**
     * met à jour l'affichage de l'évolution de la merveille principale : Olympie. Test Ok
     */
    private void updateOlympie(){
        currentPlayer.setMaterial(currentPlayer.getWonderStructure().buildOneFloor(currentPlayer.getMaterial()));
        int constructionLevel = currentPlayer.getWonderStructure().getStageConstructionActuel();
        ImageView iv = currentPlayer.getIvMiniMerveille();

        if (((Olympie)currentPlayer.getWonderStructure()).isbPartieGaucheFaite()) {
            drawNewImage(ivOlympie3, WonderCardType.OlympieFirst.imageResource);

        }

        if (((Olympie)currentPlayer.getWonderStructure()).isbPartieDroiteFaite()) {
            drawNewImage(ivOlympie2, WonderCardType.OlympieSecond.imageResource);
        }

        if (constructionLevel>0) {
            drawNewImage(ivOlympie1, WonderCardType.OlympieBase.imageResource);
            drawNewImage(iv, WonderConstructionSteps.olympieSteps.get(1).url);
        } else if (constructionLevel>01) {

            if ((((Olympie) currentPlayer.getWonderStructure()).isbPartieGaucheFaite()) && (((Olympie) currentPlayer.getWonderStructure()).isbPartieDroiteFaite())) {
                drawNewImage(ivOlympie2, WonderCardType.OlympieFirst.imageResource);
                drawNewImage(ivOlympie3, WonderCardType.OlympieSecond.imageResource);
                drawNewImage(iv, WonderConstructionSteps.olympieSteps.get(4).url);
            } else if (((Olympie) currentPlayer.getWonderStructure()).isbPartieGaucheFaite()) {
                drawNewImage(ivOlympie2, WonderCardType.OlympieFirst.imageResource);
                drawNewImage(iv, WonderConstructionSteps.olympieSteps.get(2).url);
            } else if (((Olympie) currentPlayer.getWonderStructure()).isbPartieDroiteFaite()) {
                drawNewImage(ivOlympie3, WonderCardType.OlympieSecond.imageResource);
                drawNewImage(iv, WonderConstructionSteps.olympieSteps.get(3).url);
            }
        }

        if (constructionLevel>4) {
            drawNewImage(ivOlympie4, WonderCardType.OlympieTroisieme.imageResource);
            drawNewImage(iv, WonderConstructionSteps.olympieSteps.get(5).url);
        }

        if (constructionLevel>5) {
            drawNewImage(ivOlympie5, WonderCardType.OlympieDernier.imageResource);
            drawNewImage(iv, WonderConstructionSteps.olympieSteps.get(6).url);
        }
    }

    /**affiche l'évolution de la merveille Colosse de Rhodes : test ok + mini rhodes
     *
     */
    private void updateRhodes(){
        currentPlayer.setMaterial(currentPlayer.getWonderStructure().buildOneFloor(currentPlayer.getMaterial()));
        int constructionLevel = currentPlayer.getWonderStructure().getStageConstructionActuel();
        ImageView iv = currentPlayer.getIvMiniMerveille();

        if (((Rhodes)currentPlayer.getWonderStructure()).isbPartieGaucheFaite()) {
            drawNewImage(ivRhodes1, WonderCardType.RhodesBaseFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.rhodesSteps.get(1).url);
        }

        if (((Rhodes)currentPlayer.getWonderStructure()).isbPartieDroiteFaite()) {
            drawNewImage(ivRhodes2, WonderCardType.RhodesFirstFront.imageResource);
        }

        if (constructionLevel<=3){
            if (((Rhodes)currentPlayer.getWonderStructure()).isbPartieGaucheFaite() && ((Rhodes)currentPlayer.getWonderStructure()).isbPartieDroiteFaite())    {
                drawNewImage(iv, WonderConstructionSteps.rhodesSteps.get(3).url);
            } else if (((Rhodes)currentPlayer.getWonderStructure()).isbPartieGaucheFaite()) {
                drawNewImage(iv, WonderConstructionSteps.rhodesSteps.get(1).url);
            } else if (((Rhodes)currentPlayer.getWonderStructure()).isbPartieDroiteFaite()) {
                drawNewImage(iv, WonderConstructionSteps.rhodesSteps.get(2).url);
            }
        }

        if (constructionLevel>3) {
            drawNewImage(ivRhodes3, WonderCardType.RhodesSecondFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.rhodesSteps.get(4).url);
        }

        if (constructionLevel>4) {
            drawNewImage(ivRhodes4, WonderCardType.RhodesTroisiemeFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.rhodesSteps.get(5).url);
        }

        if (constructionLevel>5) {
            drawNewImage(ivRhodes5, WonderCardType.RhodesDernierFront.imageResource);
            drawNewImage(iv, WonderConstructionSteps.rhodesSteps.get(6).url);
        }
    }
}


