package fr.isep.game7wonderarch.wonders;

import fr.isep.game7wonderarch.domain.Material;

import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Classe qui Définit la Merveille Babylon
 */
public class Babylon extends WonderStructure {

    private static final Logger log = LogManager.getLogger( Babylon.class );

    private WonderConstructionSteps wcs = WonderConstructionSteps.babylonSteps.get(0);

    /**
     * test si la partie gauche du toit est construite
     * @return vrai si elle est construite
     */
    public boolean isbPartieGaucheFaite() {
        return bPartieGaucheFaite;
    }

    /**
     * test si la partie droite du toit est construite
     * @return vrai si elle est construite
     */
    public boolean isbPartieDroiteFaite() {
        return bPartieDroiteFaite;
    }

    private boolean bPartieGaucheFaite = false;
    private boolean bPartieDroiteFaite = false;

    /**
     * merveille en 5 morceaux
     *
     * @param wonderPremier la base
     * @param wonderDeuxieme preier niveau
     * @param wonderTroisieme deuxième niveau
     * @param wonderQuatrieme troisieme niveau
     * @param wonderCinquieme sommet gauche

     */
    public Babylon(ImageView wonderPremier,
                   ImageView wonderDeuxieme,
                   ImageView wonderTroisieme,
                   ImageView wonderQuatrieme,
                   ImageView wonderCinquieme){

        setWonderPremier(wonderPremier);
        setWonderDeuxieme(wonderDeuxieme);
        setWonderTroisieme(wonderTroisieme);
        setWonderQuatrieme(wonderQuatrieme);
        setWonderCinquieme(wonderCinquieme);
    }


    /**
     * teste pour savoir si un etage peut etre construit
     * @param material la liste des materiaux disponibles avec leur quantité disponible
     * @return  vrai si un élément peut etre constuit
     */
    public boolean isConstructionPossible(HashMap<Material,Integer> material){
        if (isWonderAchieved()){
            return false;
        }

        int numberRessourcesDifferentes = 0;
        int numberRessourceIdentiques =0;
        int numberRessourceGold = 0;

        Material key;
        int value;

        for(Iterator i = material.keySet().iterator(); i.hasNext();){
            key=(Material)i.next();
            value= Integer.valueOf(material.get(key));

            if (key.equals(Material.Gold)  && (value>0)) {
                numberRessourceGold = value;
            } else if (value>0){
                numberRessourcesDifferentes++;

                if (numberRessourceIdentiques<value){
                    numberRessourceIdentiques = value;
                }
            }
        }

        numberRessourceIdentiques += numberRessourceGold;
        numberRessourcesDifferentes += numberRessourceGold;

        // Etape 0 :
        // etape 1 : la base          : 2 ressources differentes  ok
        // etape 2 : premier etage    : 2 ressources identiques ok
        // etape 3 : deuxieme etage   : 3 ressources differentes ok
        // etage 5 : toit gauche      : 3 ressources identiques ok
        // etape 6 : toit droit       : 4 ressources différentes
        //: etape 7 : toit gauche et droit, merveille achevée

        HashMap<Material,Integer> result = null;

        switch (stageConstructionActuel){
            case 0:   // Etape 0 -> Etape 1
                return (numberRessourcesDifferentes>=2);

            case 1:  // Etape 1 -> Etape 2
                return (numberRessourceIdentiques>=2);

            case 2:  // Etape 3 -> Etape 4
                return (numberRessourcesDifferentes>=3);

            case 3:  // Etape 4 -> Etape 5 ou Etape 6 (toit gauche) ou (toit droit) en fonction de ce qu'on peut faire

                if ((!bPartieGaucheFaite)  && hasRessourcesIdentical(material, 3)){
                // on veut construire la tour gauche
                    return (numberRessourceIdentiques>=3);
                } else {  // on doit faire la partie droite
                    return (numberRessourcesDifferentes>=4);
                }
            case 4: // 3 ressources identiques ok, puis ca dépend si toit droit a deja ete fait ou non pour la suite

                if ((!bPartieDroiteFaite)  && hasRessourcesUnidentical(material, 4)){
                    // on veut construire la tour gauche
                    return (numberRessourcesDifferentes>=4);
                } else {  // on doit faire la partie droite
                    return (numberRessourceIdentiques>=3);
                }
        }
        return false;
    }

    /**
     * construit un étage et on enleve les resssources de l'étage correspondant. on privilégie de garder l'or
     * @param material la liste des matériaux disponibles
     * @return la liste des matériaux restants
     */
    public HashMap<Material,Integer> buildOneFloor(HashMap<Material,Integer> material){

        if (! isConstructionPossible(material)){
            // impossible de construire
            return material;
        } else if (isWonderAchieved()){
            return material;
        }


        // Etape 0 :
        // etape 1 : la base          : 2 ressources differentes  ok
        // etape 2 : premier etage    : 2 ressources identiques ok
        // etage 4 : troisieme etage  : 3 ressources differentes ok
        // etage 5 : toit gauche      : 3 ressources identiques ok
        // etape 6 : toit droit       : 4 ressources différentes
        //: etape 7 : toit gauche et droit, merveille achevée

        HashMap<Material,Integer> result = new HashMap();

        switch (stageConstructionActuel){
            case 0:   // on construit la base
                result = removeRessourcesUnidentical(material, 2);
                addStructurePoints(3);
                stageConstructionActuel = 1;
                break;

            case 1:  // on construit le premier étage
                result = removeRessourcesIdentical(material, 2);
                addStructurePoints(0);   // pas de point à cette étape mais action --> pendre un jeton progres
                stageConstructionActuel = 2; // 2;
                break;

            case 2:  // on construit le deuxième étage
                result = removeRessourcesUnidentical(material, 3);
                addStructurePoints(5);
                stageConstructionActuel = 3;
                break;

            case 3:  // on construit la partie gauche du toit
                if ((!bPartieGaucheFaite) && hasRessourcesIdentical(material, 3)){
                    bPartieGaucheFaite =true;
                    addStructurePoints(5);
                    result = removeRessourcesIdentical(material, 3);

                    if (!bPartieDroiteFaite){
                        stageConstructionActuel = 4;  // on doit faire la partie droite et la partie gauche est faite
                    } else {
                        stageConstructionActuel = 6; // on a fini
                    }
                }
                else if ((!bPartieDroiteFaite ) && hasRessourcesUnidentical(material, 4)){
                    // on fait le partie droite
                    bPartieDroiteFaite =true;
                    addStructurePoints(7);
                    result = removeRessourcesUnidentical(material, 4);

                    if (!bPartieGaucheFaite){ // on doit faire la partie gauche
                        stageConstructionActuel = 3;
                    } else {
                        stageConstructionActuel = 6;  // on a fini
                    }
                } // sinon pas possible
                else {
                    stageConstructionActuel = 6;
                }
                break;

            case 4: // on constrtuit la partie droite
                if ((!bPartieDroiteFaite ) && hasRessourcesUnidentical(material, 4)){
                    bPartieDroiteFaite =true;
                    addStructurePoints(7);
                    result = removeRessourcesUnidentical(material, 4);

                    if (!bPartieGaucheFaite){ // on doit faire la partie gauche
                        stageConstructionActuel = 3;
                    } else {
                        stageConstructionActuel = 6;  // on a fini
                    }
                } else if ((!bPartieGaucheFaite) && hasRessourcesIdentical(material, 3)) {
                    bPartieGaucheFaite = true;
                    addStructurePoints(5);
                    result = removeRessourcesIdentical(material, 3);

                    if (!bPartieDroiteFaite) {
                        stageConstructionActuel = 4;  // on doit faire la partie droite et la partie gauche est faite
                    } else {
                        stageConstructionActuel = 6; // on a fini
                    }
                } // sinon pas possible
                else {
                    stageConstructionActuel = 6;
                }
                break;
        }
        return result;
    }

    /**
     * teste si la merveille est achevée
     * @return vrai si la merveilles est achevée
     */
    public boolean isWonderAchieved(){
        return (stageConstructionActuel == WonderConstructionSteps.babylonSteps.get(6).value);
    }

    /**
     * donne des informations sur les etapes nécessaires à la construcion du Phase d'Alexandie
     * @return une chaine détaillant les étapes
     */
    public final static String getConstructionInfo(){
        StringBuffer sb = new StringBuffer(200);
        sb.append("Babylon\n\tbase        \t2 ressources différentes\n");
        sb.append("Babylon\n\tetage 1     \t2 ressources identiques\n");
        sb.append("Babylon\n\tetage 2     \t3 ressources différentes\n");
        sb.append("Babylon\n\tToit Gauche \t3 ressources identiques\n");
        sb.append("Babylon\n\tToit Droit  \t4 ressources différentes\n");
        return sb.toString();
    }
}
