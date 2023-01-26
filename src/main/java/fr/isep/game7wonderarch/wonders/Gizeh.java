package fr.isep.game7wonderarch.wonders;

import fr.isep.game7wonderarch.domain.Material;

import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Classe qui Définit la Merveille Gizeh
 */
public class Gizeh extends WonderStructure {

    private static final Logger log = LogManager.getLogger( Gizeh.class );

    private WonderConstructionSteps wcs = WonderConstructionSteps.gizehSteps.get(0);

    /**
     * merveille en 5 morceaux
     *
     * @param wonderPremier la base
     * @param wonderDeuxieme preier niveau
     * @param wonderTroisieme deuxième niveau
     * @param wonderQuatrieme troisieme niveau
     * @param wonderCinquieme quatrieme niveau
     */
    public Gizeh(ImageView wonderPremier,
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
        // etape 1 : la base                   : 2 ressources identiques  ok
        // etape 2 : premier etage             : 3 ressources differentes ok
        // etape 3 : deuxieme etage            : 3 ressources identiques ok
        // etape 4 : le somme de la pyramide   : 4 ressources différentes

        switch (stageConstructionActuel){
            case 0:   // Etape 0 -> Etape 1
                return (numberRessourcesDifferentes>=2);

            case 1:  // Etape 1 -> Etape 2
                return (numberRessourceIdentiques>=2);

            case 2:  // Etape 2 -> Etape 3
                return (numberRessourcesDifferentes>=3);

            case 3:  // Etape 3 -> Etape 4
                return (numberRessourceIdentiques>=3);

            case 4:  // Etape 3 -> Etape 4
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

        // Etape 0 :
        // etape 1 : la base                   : 2 ressources identiques  ok
        // etape 2 : premier etage             : 3 ressources differentes ok
        // etape 3 : deuxieme etage            : 3 ressources identiques ok
        // etape 4 : le somme de la pyramide   : 4 ressources différentes

        HashMap<Material,Integer> result = material;

        switch (stageConstructionActuel) {
            case 0:   // Etape 0 -> Etape 1
                result = removeRessourcesUnidentical(material, 2);
                addStructurePoints(4);
                stageConstructionActuel = 1;
                break;

            case 1:  // Etape 1 -> Etape 2
                result = removeRessourcesIdentical(material, 2);
                addStructurePoints(5);
                stageConstructionActuel = 2;
                break;

            case 2:  // Etape 2 -> Etape 3
                result = removeRessourcesUnidentical(material, 3);
                addStructurePoints(6);
                stageConstructionActuel = 3;
                break;

            case 3:  // Etape 3 -> Etape 4
                result = removeRessourcesIdentical(material, 3);
                addStructurePoints(7);
                stageConstructionActuel = 4;
                break;

            case 4:  // Etape 4 -> fin merveille
                result = removeRessourcesUnidentical(material, 4);
                addStructurePoints(8);
                stageConstructionActuel = 5;
                break;
        }
        return result;
    }

    /**
     *
     * @return vrai si la merveilles est achevée
     */
    public boolean isWonderAchieved(){
        return (stageConstructionActuel == WonderConstructionSteps.babylonSteps.get(4).value);
    }

    /**
     * donne des informations sur les etapes nécessaires à la construcion du Phase d'Alexandie
     * @return une chaine détaillant les étapes
     */
    public final static String getConstructionInfo(){
        StringBuffer sb = new StringBuffer(200);
        sb.append("Babylon\n\tbase        \t2 ressources identiques\n");
        sb.append("Babylon\n\tetage 1     \t3 ressources differentes\n");
        sb.append("Babylon\n\tetage 2     \t3 ressources identitiques\n");
        sb.append("Babylon\n\tetage 3     \t4 ressources differentes\n");
        return sb.toString();
    }
}

