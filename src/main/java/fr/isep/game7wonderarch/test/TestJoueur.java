package fr.isep.game7wonderarch.test;

import fr.isep.game7wonderarch.domain.*;

import fr.isep.game7wonderarch.game.Deck;
import fr.isep.game7wonderarch.game.Joueur;

import fr.isep.game7wonderarch.wonders.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.LoggerConfig;


import java.util.*;

/**
 * classe de test des fonctions principales
 */
public class TestJoueur {

    /**
     * constructeur par defaut
     */
    public TestJoueur(){

    }
    private static final Logger log = LogManager.getLogger(TestJoueur.class);

    // to be used for main game
    private ArrayList<Joueur> listOfPPlayers = null;

    // to be used for main game
    private ArrayList<Wonder> listOfWonders = null;

    private Deck deckCentral = null;

    /**
     * renvoie une merveille à construire
     * @return une wonder au hasard
     */
    public Wonder getWonder(){
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
     * creation du deck central, partagé entre tous les joueurs
     * test ok
     */
    public void displayConflictTokens(){

        log.debug("Conflict Token pour deux joueurs");
        log.debug(ConflictTokens.deux_joueurs);


        log.debug("Conflict Token pour quatre joueurs");
        log.debug(ConflictTokens.quatre_joueurs);

    }

    /**
     * creation du deck central, partagé entre tous les joueurs
     * test ok
     */
    public void createDeckCentral(){
        Deck deckCentral = new Deck(null);
        log.debug("Creation du deck centre");
        log.debug(deckCentral);
    }

    /**
     * test la construciton d'Alexandrie
     */
    public void testConstructionAlexandrie(){

        Joueur joueur1 = null;

        joueur1 = new Joueur("Joueur de Test", 0, new Alexandrie(null, null, null, null, null), Wonder.Alexandrie, null, null, null);


        // on ajouter une ressource  : test ok
       WonderStructure ws = joueur1.getWonderStructure();

       joueur1.setWcs(WonderConstructionSteps.alexandrieSteps.get(0));
        log.debug("Joueur actuel \n " + joueur1);


        log.debug("Etape de la construction " + joueur1.getWcs().toString());

        HashMap <Material, Integer>  hm = joueur1.getMaterial();

        // on essaye Brick + Gold
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);
        // donc on peut, on consomme les ressources et on avance
        HashMap<Material,Integer> newMaterials = (((Alexandrie)joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        // le joueur apres sa construction
        log.debug("-----> la base est construite     ---- \n " + joueur1.toString());

        // Premier etage   ->    2 ressources identiques
        // on ajouter une Bricks puis une pierre puis une brick
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Stone, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Stone, 1);
        newMaterials = (((Alexandrie)joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        //
        log.debug("-----> Premier étage construit reste 1 brick et 0 pierre     ---- \n " + joueur1.toString());


        // deuxieme etage   ->    3 ressources differentes
        // on ajouter une Bricks puis une pierre puis une brick
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Stone, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Stone, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);
        newMaterials = (((Alexandrie)joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        // on peut construire on avance
        log.debug("-----> deuxieme étage construit, doit rester 1 brick, 1 pierre et 0 pour le reste     ---- \n " + joueur1.toString());

        // troisieme etage -> 3 ressources identiques, on essaye 2 Glass plus de l'or
        joueur1.ajouterUneQuantiteAUneRessource(Material.Glass, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Glass, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);
        newMaterials = (((Alexandrie)joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> troisieme etage construit, il doit rester 1 pierre et 1 brique     ---- \n " + joueur1.toString());


        // quatrieme etage -> 4 ressources différentes, on ajoute 1 Glass plus 1 brique ca ne doit pas marcher
        joueur1.ajouterUneQuantiteAUneRessource(Material.Glass, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        newMaterials = (((Alexandrie)joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> troisieme etage ne peut pas être construit, il doit rester 1 pierre et 2 briques et 1 verre     ---- \n " + joueur1.toString());
        joueur1.ajouterUneQuantiteAUneRessource(Material.Paper, 1);
        log.debug("-----> quatrieme etage construit, la merveille est achevée     ---- \n " + ((Alexandrie) joueur1.getWonderStructure()).isWonderAchieved());
    }

    /**
     * test toutes les options en enlevant les commentaires en fonction des besoins
     */
    public void testConstructionAlexandrieGold(){

        Joueur joueur1 = null;

        joueur1 = new Joueur("Joueur de Test", 0, new Alexandrie(null, null, null, null, null), Wonder.Alexandrie, null, null, null);

        // on ajouter une ressource  : test ok
        WonderStructure ws = joueur1.getWonderStructure();

        joueur1.setWcs(WonderConstructionSteps.alexandrieSteps.get(0));
        log.debug("Joueur actuel \n " + joueur1);

        log.debug("Etape de la construction " + joueur1.getWcs().toString());

        HashMap <Material, Integer>  hm = joueur1.getMaterial();

        // on essaye Brick + Paper
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Paper, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);
        // donc on peut, on consomme les ressources et on avance
        HashMap<Material,Integer> newMaterials = (((Alexandrie)joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        // le joueur apres sa construction
        log.debug("-----> la base est construite  il ne reste pas de ressource sauf 1 d'or  ---- \n " + joueur1.toString());

        // Premier etage   ->    2 ressources identiques
        // on ajouter une Bricks puis une pierre puis une brick
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        newMaterials = (((Alexandrie)joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        //
        log.debug("-----> Premier étage construit reste rien   ---- \n " + joueur1.toString());


        // deuxieme etage   ->    3 ressources differentes
        // on ajouter une Bricks puis une pierre puis une brick
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Stone, 1);
//        joueur1.ajouterUneQuantiteAUneRessource(Material.Stone, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Paper, 1);

   //     joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 3);  test 3 or ok

//          joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 2);  // test 2 or ok
//          joueur1.ajouterUneQuantiteAUneRessource(Material.Stone, 1);

 //       joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 4);  // test ok 4 en or mais 2 ressources distinctes doit rester 3 ors, 1 pierre
 //       joueur1.ajouterUneQuantiteAUneRessource(Material.Stone, 1);
 //       joueur1.ajouterUneQuantiteAUneRessource(Material.Stone, 1);
 //       joueur1.ajouterUneQuantiteAUneRessource(Material.Paper, 1);

        newMaterials = (((Alexandrie)joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        // on peut construire on avance
        log.debug("-----> deuxieme étage construit, doit rester  0 pour tout     ---- \n " + joueur1.toString());

        // troisieme etage -> 3 ressources identiques, on essaye 2 Glass plus de l'or
//        joueur1.ajouterUneQuantiteAUneRessource(Material.Glass, 1);
//        joueur1.ajouterUneQuantiteAUneRessource(Material.Glass, 1);
//        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);

          joueur1.ajouterUneQuantiteAUneRessource(Material.Glass, 3);  // test  3 identiques ok

            // test 1 ressrouces + 2 or // test ok
  //      joueur1.ajouterUneQuantiteAUneRessource(Material.Glass, 1);  // test  1 + 2 or
  //      joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 3);

        // test 2 ressrouces + 3 or  doit rester 2  // test ok
   //    joueur1.ajouterUneQuantiteAUneRessource(Material.Glass, 2);  // test  1 + 2 or
   //    joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 3);

        newMaterials = (((Alexandrie)joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> troisieme etage construit, il doit rester 0 en tout    ---- \n " + joueur1.toString());


        // quatrieme etage -> 4 ressources différentes, on ajoute 1 Glass plus 1 brique ca ne doit pas marcher
        joueur1.ajouterUneQuantiteAUneRessource(Material.Glass, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Paper, 1);
//        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 1);

        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 4);

        newMaterials = (((Alexandrie)joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> quatrieme etage construit, il doit rester 3 or en tout    ---- \n " + joueur1.toString());
        log.debug("-----> quatrieme etage construit, la merveille est achevée     ---- \n " + ((Alexandrie) joueur1.getWonderStructure()).isWonderAchieved());
    }

    /**
     * test la construciton de babylone
     */
    public void testConstructionBabylon(){

        Joueur joueur1 = null;

        joueur1 = new Joueur("Joueur de Test", 0, new Babylon(null, null, null, null, null), Wonder.Babylone, null, null, null);

        // on ajouter une ressource  : test ok
        WonderStructure ws = joueur1.getWonderStructure();

        joueur1.setWcs(WonderConstructionSteps.babylonSteps.get(0));

        // Etape 0 :
        // etape 1 : la base          : 2 ressources differentes  ok
        // etape 2 : premier etage    : 2 ressources identiques ok
        // etape 3 : deuxieme etage   : 2 ressources differentes ok
        // etage 4 : troisieme etage  : 3 ressources differentes ok
        // etage 5 : toit gauche      : 3 ressources identiques ok
        // etape 6 : toit droit       : 4 ressources différentes
        // etape 7 : toit gauche et droit, merveille achevée

        // test base ok : 2 ressources differentes : base
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 0);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);
        HashMap<Material,Integer> newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> base    ---- \n " + joueur1.toString());

        // test premier etage ok : 2 ressources identiques : etage 1
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 2);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 0);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 0);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> Etage 1    ---- \n " + joueur1.toString());

        // test deuxieme etage : 3 ressources differentes ok : etage 2
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> Etage 2    ---- \n " + joueur1.toString());


        // test troisieme etage ok : 4 ressources differentes ok côté Droit
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Paper, 2);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Stone, 1);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> Etage 3  cote droit  ---- \n " + joueur1.toString());

        log.debug("La merveille est contruite " + (joueur1.getWonderStructure().isWonderAchieved()));


        // test troisieme etage ok : 3 ressources identique ok côté gauche
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 0);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 0);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Paper, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> Etage 3  cote gauche  ---- \n " + joueur1.toString());

        log.debug("La merveille est contruite " + (joueur1.getWonderStructure().isWonderAchieved()));

    }
    /**
     * test pour la fin de partie
     */
    public void testFinDePartie(){


        List<Joueur> students = new ArrayList<>();
        students.add(new Joueur("Joueur de Test", 0, new Gizeh(null, null, null, null, null), Wonder.Gizeh, null, null, null));
        students.add(new Joueur("deux", 1, new Halicarnasse(null, null, null, null, null), Wonder.Halicarnasse, null, null, null));
        students.add(new Joueur("trois", 2, new Babylon(null, null, null, null, null), Wonder.Babylone, null, null, null));
        students.get(0).addLaurelPoints(5);
        students.get(1).addLaurelPoints(15);
        students.get(2).addLaurelPoints(20);


        int currentScore = 0 ;
        Joueur joueurTmp = students.get(0);
        for (Joueur joueur : students){
            joueur.calculateFinalScore();
            if (joueur.getScoreFinal()>currentScore){
                joueurTmp = joueur;
                currentScore = joueur.getScoreFinal();
            }

        }

        Collections.sort(students);


        System.out.println("students = " + joueurTmp);
    }

    /**
     * test construction Ephese fait ok
     */
    public void testConstructionEphese(){

        Joueur joueur1 = null;

        joueur1 = new Joueur("Joueur de Test", 0, new Ephese(null,
                                                                         null,
                                                                         null,
                                                                         null,
                                                                        null), Wonder.Ephese, null, null, null);

        // Etape 0 :
        // etape 1 : la base                         : 2 ressources differentes
        // etape 2 : colonne de gauche               : 2 ressources identiques
        // etape 3 : colonne du milieu               : 3 ressources differentes
        // etage 4 : colonne de droite               : 3 ressources identitique
        // etage 5 : colonnes de gauche et du milieu : besoin des ressources du pilier absent
        // etape 6 : colonnes de gauche et de droite : besoin des ressources du pilier absent
        // etape 7 : colonnes du milieu et de droite : besoin des ressources du pilier absent
        // etape 8 : les trois colonnes              : besoin des ressources du pilier absent
        // etape 9 : le toit                         : 4 ressources différentes :
        // test base ok : 2 ressources differentes
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 0);
        HashMap<Material,Integer> newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> base    ---- \n " + joueur1.toString());

        // colonne du milueu
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Paper, 1);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        // Etape 2 : colonne de gauche 2 ressources identiques
        // Etape 3 : colonne du milieu 3 ressources differentes
        // Etape 4 : colonne de Droite 3 ressources identiques
        log.debug("-----> on contruit la colonne    de Droite ---- \n " + joueur1.toString());

        // on fait celle de gauche
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 2);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 1);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        // Etape 2 : colonne de gauche 2 ressources identiques
        // Etape 3 : colonne du milieu 3 ressources differentes
        // Etape 4 : colonne de Droite 3 ressources identiques
        log.debug("-----> on contruit la colonne    de Droite et de gauche ---- \n " + joueur1.toString());

        // on fait la dernière colonne
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 3);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        // Etape 2 : colonne de gauche 2 ressources identiques
        // Etape 3 : colonne du milieu 3 ressources differentes
        // Etape 4 : colonne de Droite 3 ressources identiques
        log.debug("-----> on contruit la troisieme colonne   ---- \n " + joueur1.toString());

        // on fait le toit
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Glass, 3);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        // Etape 2 : colonne de gauche 2 ressources identiques
        // Etape 3 : colonne du milieu 3 ressources differentes
        // Etape 4 : colonne de Droite 3 ressources identiques
        log.debug("-----> on  finit la merveille  ---- \n " + joueur1.toString());


        /**
        // test premier etage ok : 2 ressources identiques ok
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 2);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 0);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 0);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> Etage 1    ---- \n " + joueur1.toString());

        // test deuxieme etage : 2 ressources differentes ok
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 0);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> Etage 2    ---- \n " + joueur1.toString());


        // test troisieme etage ok : 3 ressources differentes ok
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Paper, 0);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> Etage 3    ---- \n " + joueur1.toString());

        boolean testGauche = false;

        if (testGauche){
            // partie gauche en premier
            // 3 ressources identiques ok
            joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 0);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Paper, 0);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 2);
            newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
            joueur1.setMaterial(newMaterials);
            log.debug("-----> toit partie gauche    ---- \n " + joueur1.toString());

            log.debug("La merveille est finie doit etre faux => " + joueur1.getWonderStructure().isWonderAchieved());

            joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 1);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Paper, 1);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);
            newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
            joueur1.setMaterial(newMaterials);

            log.debug("La merveille est finie doit etre vrai => " + joueur1.getWonderStructure().isWonderAchieved());

        } else {
            // partie droite en premier
            // 4 ressources différentes
            joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 1);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Paper, 1);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);
            newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
            joueur1.setMaterial(newMaterials);
            log.debug("-----> toit partie droite    ---- \n " + joueur1.toString());

            log.debug("La merveille est finie doit etre faux => " + joueur1.getWonderStructure().isWonderAchieved());
            joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 0);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Paper, 0);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 2);
            newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
            joueur1.setMaterial(newMaterials);
            log.debug("La merveille est finie doit etre vrai => " + joueur1.getWonderStructure().isWonderAchieved());
            log.debug(joueur1);
        }
         */
    }

    /**
     * test construction Gizeh fait ok
     */
    public void testConstructionGizeh(){

        Joueur joueur1 = null;

        joueur1 = new Joueur("Joueur de Test", 0, new Gizeh(
                null,
                null,
                null,
                null, null), Wonder.Gizeh, null, null, null);

        // Etape 0 :
        // etape 1 : la base                         : 2 ressources identiques
        // etape 2 : premier etage                   : 3 ressources differentes
        // etape 3 : deuxième etage                  : 3 ressources identitiques
        // etage 4 : le somme de la pyramide         : 4 ressources differentes

        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 0);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  2);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 0);
        HashMap<Material,Integer> newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> base    ---- \n " + joueur1.toString());

        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> etage 1    ---- \n " + joueur1.toString());

        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  0);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 2);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> etage 2    ---- \n " + joueur1.toString());


        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 2);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> on  finit la merveille  ---- \n " + joueur1.toString());
        log.debug("Merveille achevée -> " + joueur1.getWonderStructure().isWonderAchieved());

    }

    /**
     * test construction Halicarnasse fait ok
     */
    public void testConstructionHalicarnasse(){

        Joueur joueur1 = null;

        joueur1 = new Joueur("Joueur de Test", 0, new Halicarnasse(
                null,
                null,
                null,
                null,
                null), Wonder.Halicarnasse, null, null, null);

        // Etape 0 :
        // etape 1 : la base                   : 2 ressources differentes  ok
        // etape 2 : premier etage             : 2 ressources identiques ok
        // etape 3 : deuxieme etage            : 3 ressources identiques ok
        // etape 3 : troisieme etage           : 3 ressources differentes ok
        // etape 4 : le somme de la pyramide   : 4 ressources différentes

        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 0);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  2);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);
        HashMap<Material,Integer> newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> base    ---- \n " + joueur1.toString());

        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  0);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> etage 1    ---- \n " + joueur1.toString());

        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  0);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> etage 2    ---- \n " + joueur1.toString());

        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> etage 3   ---- \n " + joueur1.toString());


        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Stone, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Paper, 1);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> on  finit la merveille  ---- \n " + joueur1.toString());
        log.debug("Merveille achevée -> " + joueur1.getWonderStructure().isWonderAchieved());

    }

    /**
     * test la creation d'un joueur
     */
    public void testCreationJoueur(){
        Joueur joueur1 = null;

        try {
            joueur1 = new Joueur("Joueur de Test", 0, new Alexandrie(null, null, null, null, null), getWonder(), null, null, null);

            // on ajouter une ressource  : test ok
            joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 10);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Glass, 30);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 40);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Paper, 60);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Stone, 20);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 50);

            log.debug("Joueur 1 apres la mise a jour des ressources" + joueur1);

            // ajout de plusieurs ressources  : test ok
            HashMap<Material,Integer> newRessources = new HashMap<>();
            newRessources.putIfAbsent(Material.Brick, Integer.valueOf(5));
            newRessources.putIfAbsent(Material.Glass, Integer.valueOf(50));
            newRessources.putIfAbsent(Material.Gold, Integer.valueOf(500));
            newRessources.putIfAbsent(Material.Paper, Integer.valueOf(5000));
            newRessources.putIfAbsent(Material.Stone, Integer.valueOf(50000));
            newRessources.putIfAbsent(Material.Wood, Integer.valueOf(500000));
            joueur1.ajouterPlusieursRessources(newRessources);

            // retourne la quantité de ressources : test ok
            log.debug("ressources du Joueur \n" + joueur1);

/**
            joueur1.ajouterUnePartieALaMerveille(WonderLevel.baseConstruite, true);
            joueur1.ajouterUnePartieALaMerveille(WonderLevel.constructionAchevee, true);
            joueur1.ajouterUnePartieALaMerveille(WonderLevel.pilierDroit, true);
            joueur1.ajouterUnePartieALaMerveille(WonderLevel.pilierDroit, true);
            joueur1.ajouterUnePartieALaMerveille(WonderLevel.pilierGauche, true);
            joueur1.ajouterUnePartieALaMerveille(WonderLevel.pilierGauche, true);
            joueur1.ajouterUnePartieALaMerveille(WonderLevel.constructionAchevee, true);
            joueur1.ajouterUnePartieALaMerveille(WonderLevel.pilierMilieu, true);
            joueur1.ajouterUnePartieALaMerveille(WonderLevel.constructionAchevee, true);
            joueur1.ajouterUnePartieALaMerveille(WonderLevel.pilierMilieu, true);
*/

     //       log.debug("Contenu du deck du joueur ");
     //       log.debug(joueur1.getMyDeck().playbleDeckToString());   // test ok

            WonderStructure ws = joueur1.getWonderStructure();

            HashMap <Material, Integer>  hm = joueur1.getMaterial();

            log.debug("On veut construire la base d'Alexandrie -> " + ws.isConstructionPossible(hm));

        }
        catch (IndexOutOfBoundsException e){
            log.error("Le joueur ne peut être créer car sa postion est en dehors du plateau ou il y a trop de joueurs.");
        }
    }

    /**
     * test construction Olympie fait ok
     */
    public void testConstructionOlympie(){

        Joueur joueur1 = null;

        joueur1 = new Joueur("Joueur de Test", 0, new Olympie(
                null,
                null,
                null,
                null,
                null), Wonder.Olympie, null, null, null);

        // TODO corriger avec les vraies valeurs
        // Etape 0 :
        // etape 1 : la base          : 2 ressources differentes  ok
        // etape 2 : pied gauche      : 2 ressources identiques ok
        // etape 3 : pied droit       : 2 ressources differentes ok
        // etage 4 : buste            : 3 ressources differentes ok
        // etage 5 : tête             : 4 ressources différentes

        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 0);
        HashMap<Material,Integer> newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("-----> base    ---- \n " + joueur1.toString());

        boolean bGaucheFirst = false;

        if (bGaucheFirst){
            joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 2);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  0);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 0);
            newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
            joueur1.setMaterial(newMaterials);
            log.debug("-----> pied gauche    ---- \n " + joueur1.toString());

            joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  1);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 0);
            newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
            joueur1.setMaterial(newMaterials);
            log.debug("-----> pied droit    ---- \n " + joueur1.toString());
        }  else {
            joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  1);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 0);
            newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
            joueur1.setMaterial(newMaterials);
            log.debug("-----> pied droit    ---- \n " + joueur1.toString());

            joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 2);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  0);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 0);
            newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
            joueur1.setMaterial(newMaterials);
            log.debug("-----> pied gauche    ---- \n " + joueur1.toString());
        }

        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("----->le buste    ---- \n " + joueur1.toString());


        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 2);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("----->la tête    ---- \n " + joueur1.toString());
        log.debug("Merveille achevée -> " + joueur1.getWonderStructure().isWonderAchieved());

    }


    /**
     * test construction Olympie fait ok
     */
    public void testConstructionRhodes(){

        Joueur joueur1 = null;

        joueur1 = new Joueur("Joueur de Test", 0, new Rhodes(
                null,
                null,
                null,
                null,
                null), Wonder.Olympie, null, null, null);

        // Etape 0 :
        // etape 1 : pied gauche      : 2 ressources identiques  ok
        // etape 2 : pied droit       : 2 ressources différentes ok
        // etape 3 : jambes           : 3 ressources differentes ok
        // etage 4 : buste            : 3 ressources identiques ok
        // etage 5 : tête             : 4 ressources différentes

        HashMap<Material,Integer> newMaterials =  null;

        boolean bGaucheFirst = false;

        if (bGaucheFirst){
            joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 2);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  0);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 0);
            newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
            joueur1.setMaterial(newMaterials);
            log.debug("-----> pied gauche    ---- \n " + joueur1.toString());

            joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  1);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 0);
            newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
            joueur1.setMaterial(newMaterials);
            log.debug("-----> pied droit    ---- \n " + joueur1.toString());
        }  else {
            joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  1);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 0);
            newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
            joueur1.setMaterial(newMaterials);
            log.debug("-----> pied droit    ---- \n " + joueur1.toString());

            joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  2);
            joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 0);
            newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
            joueur1.setMaterial(newMaterials);
            log.debug("-----> pied gauche    ---- \n " + joueur1.toString());
        }

        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 0);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 1);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("----->les jambes    ---- \n " + joueur1.toString());


        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 0);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 5);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("----->le buste    ---- \n " + joueur1.toString());

        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 0);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood,  1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 0);
        newMaterials =  ((joueur1.getWonderStructure()).buildOneFloor(joueur1.getMaterial()));
        joueur1.setMaterial(newMaterials);
        log.debug("----->la tête    ---- \n " + joueur1.toString());
        log.debug("Merveille achevée -> " + joueur1.getWonderStructure().isWonderAchieved());

    }


    /**
     * test toutes les combinaisons pour enlever des ressources
     */
    public void testRessourcesIdentiques(){
        Joueur joueur1 = null;

        joueur1 = new Joueur("Joueur de Test", 0, new Alexandrie(null, null, null, null, null), Wonder.Alexandrie, null, null, null);

        // on essaye Brick + Paper
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 2);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Paper, 2);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 5);
        // donc on peut, on consomme les ressources et on avance

        HashMap<Material,Integer> newMaterials = ((Alexandrie)joueur1.getWonderStructure()).removeRessourcesIdentical(joueur1.getMaterial(),3);
        joueur1.setMaterial(newMaterials);

        log.debug("Apres test 2 ressources identiques\n" + joueur1);
    }

    /**
     * test toutes les combinaisons pour enlever des ressources
     */
    public void removeRessourcesUnidentical(){
        Joueur joueur1 = null;

        joueur1 = new Joueur("Joueur de Test", 0, new Alexandrie(null, null, null, null, null), Wonder.Alexandrie, null, null, null);

        // on essaye Brick + Paper
        joueur1.ajouterUneQuantiteAUneRessource(Material.Brick, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Paper, 2);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Gold, 3);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Glass, 3);
        // donc on peut, on consomme les ressources et on avance

        HashMap<Material,Integer> newMaterials = ((Alexandrie)joueur1.getWonderStructure()).removeRessourcesUnidentical(joueur1.getMaterial(),3);
        joueur1.setMaterial(newMaterials);

        log.debug("removeRessourcesUnidentical\n" + joueur1);
    }

    /**
     * creer l'ensemble des joueurs et attache les decks  : test ok
     * @param playerNames un tableau contenant le nom des différents joueurs
     *
     */
    public void testCreateMultiplePlayers(ArrayList <String> playerNames){
        if (listOfPPlayers==null){
            listOfPPlayers = new ArrayList();

            Joueur joueur = null;
            Wonder w = null;
            WonderStructure ws = null;

            for (String playerName : playerNames ){

                w = getWonder();

                if (w == Wonder.Alexandrie){
                    ws = new Alexandrie(null, null, null, null, null);
                } else if (w == Wonder.Babylone){
                    ws = new Babylon(null, null, null, null, null);
                } else if (w == Wonder.Ephese){
                    ws = new Ephese(null, null, null, null, null);
                } else if (w == Wonder.Gizeh){
                    ws = new Gizeh(null, null, null, null, null);
                } else if (w == Wonder.Halicarnasse){
                    ws = new Halicarnasse(null, null, null, null, null);
                } else if (w == Wonder.Olympie){
                    ws = new Olympie(null, null, null, null, null);
                } else if (w == Wonder.Rhodes){
                    ws = new Rhodes(null, null, null, null, null);
                }

                joueur = new Joueur(playerName, listOfPPlayers.size(), ws, w, null, null, null);

                listOfPPlayers.add(joueur);

            }
        }

        // on connecte les decks des differents joueurs
        // comme on tourne dans le sens des aiguilles d'une montre, mon voisin de gauche est le suivant dans la liste des joueurs.
        // Si je suis le dernier de la liste, c'est le joueur en tête de liste
        Object [] joueurs = listOfPPlayers.toArray();
        Joueur j = null;

        for (int i=0; i<joueurs.length ; i++){

            if (i==(joueurs.length)-1){
               // car du bord
                ((Joueur) joueurs[0]).setDeckJoueurDeDroite(((Joueur) joueurs[i]).getMyDeck());
            } else {
                ((Joueur) joueurs[i + 1]).setDeckJoueurDeDroite(((Joueur) joueurs[i]).getMyDeck());
            }
        }
    }

    /**
     * test la prise d'une carte dans un deck en fonction du type de carte
     */
    public void takeOneCardPerType(){
        Joueur joueur1 = null;

        joueur1 = new Joueur("Joueur de Test", 0, new Alexandrie(null, null, null, null, null), Wonder.Alexandrie, null, null, null);

        joueur1.getMyDeck().takeOneCardPerType(CardType.CardScienceLaw);
    }

    /**
     * test la fonction de transformation d'un matériaux en un autre
     */
    public void testTransmuteMaterial(){
        Joueur joueur1 = null;

        joueur1 = new Joueur("Joueur de Test", 0, new Alexandrie(null, null, null, null, null), Wonder.Alexandrie, null, null, null);

        joueur1.ajouterUneQuantiteAUneRessource(Material.Glass, 1);
        joueur1.ajouterUneQuantiteAUneRessource(Material.Wood, 1);
        // donc on peut, on consomme les ressources et on avance

        HashMap<Material, Integer> newMaterial = transmuteElements(joueur1, joueur1.getMaterial());

        log.debug(newMaterial);
    }


    private HashMap<Material, Integer> transmuteElements(Joueur currentPlayer, HashMap<Material, Integer> materials) {

        HashMap<Material, Integer> tmpMaterials = new HashMap<>();

        boolean isSuccess = false;

        // les cas possibles  TODO
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

    /**
     * test la pioche dans les decks
     */
    public void testPiocheUneCarte(){
    // chaque joueur pioche une carte, et met à jour le deck du voisin (si version en réseau)

        if (deckCentral==null){
            deckCentral = new Deck(null); // le central
        }

        int quelDeck = 0;
        CardType ct = null;
        HashMap<Material,Integer> newMaterials =  null;

        for (Joueur joueur : listOfPPlayers){
            quelDeck =  (int)(Math.random() * 3);

            if (quelDeck==0){
                // prend la premiere carte du deck du joueur
                ct = joueur.getMyDeck().takeOneCard();

            } else if (quelDeck==1){
                // prend la premiere carte du deck du joueur de gauche
                ct = joueur.getDeckJoueurDeDroite().takeOneCard();
            } else {
                // deck central
                ct = deckCentral.takeOneCard();
            }

            // test si on peut construire la merveille : passage obligatoire
            if (ct.cardCategory == CardCategory.MaterialCard){
                // carte de ressources
                joueur.ajouterUneQuantiteAUneRessource(ct.material, 1);
                newMaterials =  ((joueur.getWonderStructure()).buildOneFloor(joueur.getMaterial()));
                joueur.setMaterial(newMaterials);
                log.debug("On a pris une carte ressources -----> \n " + joueur.toString());
            } else if (ct.cardCategory == CardCategory.PoliticCard){
                // carte de points de victoire, on regarde si on a le chat
                if (ct==CardType.CardPolitic_emperor){
                    joueur.addLaurelPoints(ct.laurelCount);
                } else if (ct==CardType.CardPolitic_cat){
                    // on libere le chat de tous les joueurs et on le positionne sur le joueur courant
                    for (Joueur tmpJoueur : listOfPPlayers){
                        tmpJoueur.loseCat();
                        }
                    }
                    joueur.addLaurelPoints(ct.laurelCount);
                    joueur.takeCat();
            }  else if (ct.cardCategory == CardCategory.ProgressCard){
                // carte de type science, droit ou constructeur
                joueur.ajouteruneCarteVerte(ct);
                if (joueur.testConditionCarteVerte()){
                    //on supprime les cartes en fonction de la version possible et on applique l'effet
                    // todo implémenter les jetons progrès
                }
            } else if (ct.cardCategory == CardCategory.WarCard){
                // les cartes de combats
                // on regarde si la carte à des

                // CardWar_barbarian  shield 1 corn 1
                // CardWar_centurion  shield 1 corn 0
                // CardWar_archer  shield 1 corn 2

            }
        }
    }

    /**
     * affiche les différents joueurs
     */
    public void displayPlayers(){
        for (Joueur joueur : listOfPPlayers ){
            log.debug(joueur);
        }
    }

    /**
     * fonction principale
     * @param args aucun
     */
    public static void main(String[] args){

        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);

        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        loggerConfig.setLevel(Level.DEBUG);
        ctx.updateLoggers();

        TestJoueur tj = new TestJoueur();

        // test de la creatoin d'un joueur
     //   tj.testCreationJoueur();

      //  tj.createDeckCentral();

      //  tj.displayConflictTokens();

     //   tj.testConstructionAlexandrie();

    //    tj.testConstructionAlexandrieGold();

   //      tj.testConstructionBabylon();

      //  tj.removeRessourcesUnidentical();

         tj.testConstructionEphese();

        // tj.testConstructionGizeh();

      //  tj.testConstructionHalicarnasse();


//        tj.testConstructionOlympie();

     //   tj.testConstructionRhodes();


        //tj.testTransmuteMaterial();

    //  //  tj.takeOneCardPerType();

  //      tj.testFinDePartie();

    }
}
