package fr.isep.game7wonderarch.wonders;

import fr.isep.game7wonderarch.domain.Material;

import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;

/**
 new WonderConstructionSteps("Etape 0", 0, "ephese/etape0.png", false), // chantier
 new WonderConstructionSteps("Etape 1", 1, "ephese/etape1.png", false), // base
 new WonderConstructionSteps("Etape 2", 2, "ephese/etape2.png", true), // colonne de gauche 1
 new WonderConstructionSteps("Etape 3", 3, "ephese/etape3.png", true), // colonne du milieu
 new WonderConstructionSteps("Etape 4", 4, "ephese/etape4.png", true), // colonne de droite
 new WonderConstructionSteps("Etape 5", 5, "ephese/etape5.png", true), // colonnes gauche et milieu
 new WonderConstructionSteps("Etape 6", 6, "ephese/etape6.png", true), // colonnes gauche et droite
 new WonderConstructionSteps("Etape 7", 7, "ephese/etape7.png", true), // colonnes du milieu et de droite
 new WonderConstructionSteps("Etape 8", 8, "ephese/etape8.png", true), // les trois colonnes
 new WonderConstructionSteps("Etape 9", 9, "ephese/etape9.png", false), // finie
 */

public class Ephese extends WonderStructure {

    private static final Logger log = LogManager.getLogger( Babylon.class );

    private WonderConstructionSteps wcs = WonderConstructionSteps.epheseSteps.get(0);  // rien de construit

    private boolean bColonneGaucheFait = false;

    /**
     * test si la colonne de gauche est faite
     * @return vrai si la colonne de gauche est construite
     */
    public boolean isbColonneGaucheFait() {
        return bColonneGaucheFait;
    }

    /**
     * test si la colonne de droite est faite
     * @return vrai si la colonne de droite est construite
     */
    public boolean isbColonneDroitFait() {
        return bColonneDroitFait;
    }

    /**
     * test si la colonne du milieu est faite
     * @return vrai si la colonne du milieu est construite
     */
    public boolean isbColonneMilieuFait() {
        return bColonneMilieuFait;
    }

    private boolean bColonneDroitFait = false;
    private boolean bColonneMilieuFait = false;


    /**
     * merveille en 5 morceaux
     * @param wonderPremier la base
     * @param wonderDeuxieme preier niveau
     * @param wonderTroisieme deuxième niveau
     * @param wonderQuatrieme troisieme niveau
     * @param wonderCinquieme sommet gauche
     */
    public Ephese( ImageView wonderPremier,
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
     *
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
        // etape 1 : la base                         : 2 ressources differentes
        // etape 2 : colonne de gauche               : 2 ressources identiques
        // etape 3 : colonne du milieu               : 3 ressources differentes
        // etage 4 : colonne de droite               : 3 ressources identitique
        // etage 5 : colonnes de gauche et du milieu : besoin des ressources du pilier absent
        // etape 6 : colonnes de gauche et de droite : besoin des ressources du pilier absent
        // etape 7 : colonnes du milieu et de droite : besoin des ressources du pilier absent
        // etape 8 : les trois colonnes              : besoin des ressources du pilier absent
        // etape 9 : le toit                         : 4 ressources différentes :

        switch (stageConstructionActuel){
            case 0:   // Etape 0 -> Etape 1 : on construit le base
                return (numberRessourcesDifferentes>=2);

            case 1:   // on construit une colonne
                return ((numberRessourceIdentiques>=2) || (numberRessourcesDifferentes>=3));

            case 2:   // la colonne de gauche existe : on construit une deuxime colonne
                return ((numberRessourceIdentiques>=3) || (numberRessourcesDifferentes>=3));

            case 3:   // la colonne du milieu existe : on construit une deuxieme colonne
                return ((numberRessourceIdentiques>=2) || (numberRessourcesDifferentes>=3));

            case 4:   // la colonne de droite existe : on construit une deuxieme colonne
              return ((numberRessourceIdentiques>=2) || (numberRessourcesDifferentes>=3));

            case 5: // de gauche et milieu vers 8
                return (numberRessourceIdentiques>=3);

            case 6: // de gauche et droite vers 8
                return (numberRessourcesDifferentes>=3);

            case 7: // de milieu et de droite vers 8
                return (numberRessourceIdentiques>=2);

            case 8: // de milieu et de droite vers 8
                return (numberRessourcesDifferentes>=4);
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

        HashMap<Material,Integer> result = null;

        switch (stageConstructionActuel){
            case 0:   // Etape 0 -> Etape 1 : on construit le base
                result = removeRessourcesUnidentical(material, 2);
                addStructurePoints(3);
                stageConstructionActuel = 1;
                break;

            case 1:   // on construit une colonne
                // soit celle de gauche
                if ((!bColonneGaucheFait) && hasRessourcesIdentical(material, 2)){
                    result = removeRessourcesIdentical(material, 2);
                    bColonneGaucheFait = true;
                    addStructurePoints(3);
                    stageConstructionActuel = 2;
                } else if ((!bColonneMilieuFait) && hasRessourcesUnidentical(material, 3)){ // soit celle du milieu : 3 ressources differentes
                    result = removeRessourcesUnidentical(material, 3);
                    bColonneMilieuFait = true;
                    addStructurePoints(5);
                    stageConstructionActuel = 3;
                } else if (hasRessourcesIdentical(material, 3)) { // dernière option, on fait la colonne de Droite
                    result = removeRessourcesIdentical(material, 3);
                    bColonneDroitFait = true;
                    addStructurePoints(4);
                    stageConstructionActuel = 4;
                }
                break;

            case 2:   // la colonne de gauche existe : on construit une deuxime colonne
                // soit celle de gauche
                if ((!bColonneMilieuFait) && hasRessourcesIdentical(material, 3)){ // soit celle du milieu : 3 ressources differentes
                    result = removeRessourcesIdentical(material, 3);
                    bColonneMilieuFait = true;
                    addStructurePoints(5);
                    stageConstructionActuel = 5;
                } else if (hasRessourcesUnidentical(material, 3)) { // dernière option, on fait la colonne de Droite
                    result = removeRessourcesUnidentical(material, 3);
                    bColonneDroitFait = true;
                    addStructurePoints(4);
                    stageConstructionActuel = 6;
                }
                break;

            case 3:   // la colonne du milieu existe : on construit une deuxieme colonne
                // soit celle de gauche
                if ((!bColonneGaucheFait) && hasRessourcesIdentical(material, 2)){ // soit celle de gauche : 2 ressources identiques
                    result = removeRessourcesIdentical(material, 2);
                    bColonneGaucheFait = true;
                    addStructurePoints(3);
                    stageConstructionActuel = 5;
                } else if (hasRessourcesIdentical(material, 3)) { // on fait la colonne de droite :  3 ressources différentes
                    result = removeRessourcesIdentical(material, 3);
                    bColonneDroitFait = true;
                    addStructurePoints(4);
                    stageConstructionActuel = 7;
                }
                break;

            case 4:   // la colonne de droite existe : on construit une deuxieme colonne
                // soit celle de gauche
                if ((!bColonneGaucheFait) && hasRessourcesIdentical(material, 2)){ // soit celle de gauche : 2 ressources identiques
                    result = removeRessourcesIdentical(material, 2);
                    bColonneGaucheFait = true;
                    addStructurePoints(3);
                    stageConstructionActuel = 6;
                } else if (hasRessourcesUnidentical(material, 3)) { // on fait la colonne de droite :  3 ressources différentes
                    result = removeRessourcesUnidentical(material, 3);
                    bColonneMilieuFait = true;
                    addStructurePoints(5);
                    stageConstructionActuel = 7;
                }
                break;

            case 5: // de gauche et milieu vers 8
                if ((!bColonneDroitFait) && (hasRessourcesIdentical(material, 3))) { // on fait la colonne de droite :  3 ressources différentes
                    result = removeRessourcesIdentical(material, 3);
                    bColonneDroitFait = true;
                    addStructurePoints(4);
                    stageConstructionActuel = 8;
                }
                break;

            case 6: // de gauche et droite vers 8
                if ((!bColonneMilieuFait) && (hasRessourcesUnidentical(material, 3))) { // on fait la colonne de droite :  3 ressources différentes
                    result = removeRessourcesUnidentical(material, 3);
                    bColonneMilieuFait = true;
                    addStructurePoints(5);
                    stageConstructionActuel = 8;
                }
                break;

            case 7: // de milieu et de droite vers 8
                if ((!bColonneGaucheFait) && (hasRessourcesIdentical(material, 2))) { // on fait la colonne de droite :  3 ressources différentes
                    result = removeRessourcesIdentical(material, 2);
                    bColonneGaucheFait = true;
                    addStructurePoints(3);
                    stageConstructionActuel = 8;
                }
                break;

            case 8: // de milieu et de droite vers 8
                if ((bColonneGaucheFait && bColonneMilieuFait && bColonneDroitFait) && (hasRessourcesUnidentical(material, 4))) { // on fait la colonne de droite :  3 ressources différentes
                    result = removeRessourcesUnidentical(material, 4);
                    addStructurePoints(7);
                    stageConstructionActuel = 9;
                }
                break;
        }

        return result;
    }

    /**
     *
     * @return vrai si la merveilles est achevée
     */
    public boolean isWonderAchieved(){
        return (stageConstructionActuel == WonderConstructionSteps.epheseSteps.get(9).value);
    }
}

