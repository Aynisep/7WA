package fr.isep.game7wonderarch.wonders;

import fr.isep.game7wonderarch.domain.Material;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Classe qui Définit la merveille Colosse de Rhodes
 */
public class Rhodes extends WonderStructure {

    /**
     * permet de tracer
     */
    private static final Logger log = LogManager.getLogger( Rhodes.class );

    /**
     * les étapes de la construction
     */
    private WonderConstructionSteps wcs = WonderConstructionSteps.rhodesSteps.get(0);

    /**
     * le pied droit est-il construit (à gauche sur le dessin)
     * @return vrai si le pied droit est construit
     */
    public boolean isbPartieGaucheFaite() {
        return bPartieGaucheFaite;
    }

    /**
     * le pied gauche est-il construit (à doite sur le dessin)
     * @return vrai si le pied gauche est construit
     */
    public boolean isbPartieDroiteFaite() {
        return bPartieDroiteFaite;
    }

    /**
     * vrai si le pied droit est construit
     */
    private boolean bPartieGaucheFaite = false;

    /**
     * vrai si le pied gauche est construir
     */
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
    public Rhodes( ImageView wonderPremier,
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
     * vérifie si on peut construire un étage de plus
     * @param material la liste des materiaux disponibles avec leur quantité disponible
     * @return  vrai si un étage peut etre constuit
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
        // etape 1 : pied gauche      : 2 ressources identiques  ok
        // etape 2 : pied droit       : 2 ressources différentes ok
        // etape 3 : jambes           : 3 ressources differentes ok
        // etage 4 : buste            : 3 ressources identiques ok
        // etage 5 : tête             : 4 ressources différentes

        HashMap<Material,Integer> result = null;

        switch (stageConstructionActuel){
            case 0:   // Etape 0 -> Etape 1 ou etape 2
                return ((numberRessourceIdentiques>=2) || (numberRessourcesDifferentes>=2));

            case 1:  // Etape 1 -> Etape 2 vers 3
                return (numberRessourcesDifferentes>=2);

            case 2:  // Etape 2 -> Etape 1 ou vers 3
                return ((numberRessourceIdentiques>=2) || (numberRessourcesDifferentes>=3));

            case 3:  // Etape 3 -> Etape 4
                return (numberRessourcesDifferentes>=3);

            case 4:
                return (numberRessourceIdentiques>=3);

            case 5:
                return (numberRessourcesDifferentes>=4);
        }
        return false;
    }

    /**
     * construit un étage et on enleve les resssources de l'étage correspondant. on privilégie de garder l'or
     * @param material la lkiste des matériaux disponibles
     * @return les matériaux restants après la construction
     */
    public HashMap<Material,Integer> buildOneFloor(HashMap<Material,Integer> material){

        if (! isConstructionPossible(material)){
            // impossible de construire
            return material;
        } else if (isWonderAchieved()){
            return material;
        }

        // Etape 0 :
        // etape 1 : pied gauche      : 2 ressources identiques  ok
        // etape 2 : pied droit       : 2 ressources différentes ok
        // etape 3 : jambes           : 3 ressources differentes ok
        // etage 4 : buste            : 3 ressources identiques ok
        // etage 5 : tête             : 4 ressources différentes

        HashMap<Material,Integer> result = material;

        switch (stageConstructionActuel){
            case 0:   //Etape 0 -> Etape 1 ou etape 2
                if ((!bPartieGaucheFaite)  && hasRessourcesIdentical(material, 2)){
                    bPartieGaucheFaite = true;
                    addStructurePoints(4);
                    result = removeRessourcesIdentical(material, 2);
                    stageConstructionActuel = 1;
                } else if (hasRessourcesUnidentical(material, 2)) {  // on doit faire la partie droite
                    bPartieDroiteFaite =true;
                    addStructurePoints(4);
                    result = removeRessourcesUnidentical(material, 2);
                    stageConstructionActuel = 2;
                }
                break;

            case 1:  // Etape 1 = parie gauche faite -> Etape 2  ou vers 3
                bPartieDroiteFaite = true;
                addStructurePoints(4);
                result = removeRessourcesUnidentical(material, 2);
                stageConstructionActuel = 3;
                break;

            case 2:  // Etape 2 = partie droite faite -> Etape 1 ou vers 3 -- on a le pied gauche, soit on fait le droit soit on passe au buste
                bPartieGaucheFaite = true;
                addStructurePoints(4);
                result = removeRessourcesIdentical(material, 2);
                stageConstructionActuel = 3;
                break;

            case 3:  // jambes
                result = removeRessourcesUnidentical(material, 3);
                addStructurePoints(5);
                stageConstructionActuel = 4 ;
                break;

            case 4: // buste
                result = removeRessourcesIdentical(material, 3);
                addStructurePoints(6);
                stageConstructionActuel = 5 ;
                break;

            case 5:  // la tete
                result = removeRessourcesUnidentical(material, 4);
                addStructurePoints(7);
                stageConstructionActuel = 6 ;
                break;
        }

        return result;
    }

    /**
     * permet de savoir si la merveille est achevée
     * @return vrai si la construction est finie
     */
    public boolean isWonderAchieved(){
        return (stageConstructionActuel == WonderConstructionSteps.rhodesSteps.get(6).value);
    }

    /**
     * information sur les étapes de la construction
     * @return une chaine pour afficher les infos
     */
    public final static String getConstructionInfo(){
        StringBuffer sb = new StringBuffer(200);
        sb.append("Rhodes\n\tpied gauche \t2 ressources identiques\n");
        sb.append("Rhodes\n\tpied droit  \t2 ressources différentes\n");
        sb.append("Rhodes\n\tjambes      \t3 ressources différentes\n");
        sb.append("Rhodes\n\tbuste       \t3 ressources identiques\n");
        sb.append("Rhodes\n\ttête        \t4 ressources différentes\n");
        return sb.toString();
    }

}

