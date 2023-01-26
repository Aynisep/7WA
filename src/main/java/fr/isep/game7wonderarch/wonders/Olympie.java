package fr.isep.game7wonderarch.wonders;

import fr.isep.game7wonderarch.domain.Material;

import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Classe définissant la merveille Olympie
 */
public class Olympie extends WonderStructure {

    // pour loger
    private static final Logger log = LogManager.getLogger( Olympie.class );

    // la premiere etape de la merveille
    private WonderConstructionSteps wcs = WonderConstructionSteps.olympieSteps.get(0);

    /**
     * pour savoir si le pied droit est fait
     * @return vrai si le pied droit est dessiné
     */
    public boolean isbPartieGaucheFaite() {
        return bPartieGaucheFaite;
    }

    /**
     * pour savoir si le pied gauche est fait
     * @return vrai si le pied gauche est dessiné
     */
    public boolean isbPartieDroiteFaite() {
        return bPartieDroiteFaite;
    }

    private boolean bPartieGaucheFaite = false;
    private boolean bPartieDroiteFaite = false;

    /**
     * Constructeur de la merveille en 5 morceaux
     *
     * @param wonderPremier la base
     * @param wonderDeuxieme premier niveau
     * @param wonderTroisieme deuxième niveau
     * @param wonderQuatrieme troisieme niveau
     * @param wonderCinquieme sommet gauche
     */
    public Olympie(     ImageView wonderPremier,
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
     * test pour savoir si on peut construire un etage de plus
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
        // etape 2 : pied gauche      : 2 ressources identiques ok
        // etape 3 : pied droit       : 3 ressources differentes nok
        // etage 4 : buste            : 3 ressources identiques nok
        // etage 5 : tête             : 4 ressources différentes

        HashMap<Material,Integer> result = null;

        switch (stageConstructionActuel){
            case 0:   // Etape 0 -> Etape 1
                return (numberRessourcesDifferentes>=2);

            case 1:  // Etape 1 -> Etape 2 ou 1 vers 3
                return ((numberRessourceIdentiques>=2) || (numberRessourcesDifferentes>=3));

            case 2:  // Etape 2 -> Etape 3 ou vers 4
                return ((numberRessourceIdentiques>=3) || (numberRessourcesDifferentes>=3));

            case 3:  // Etape 3 -> Etape 2 ou vers 42
                return (numberRessourceIdentiques>=2);

            case 4:
                return (numberRessourceIdentiques>=3);

            case 5: // 3 ressources identiques ok, puis ca dépend si toit droit a deja ete fait ou non pour la suite
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

        // TODO corriger avec les vraies valeurs
        // Etape 0 :
        // etape 1 : la base          : 2 ressources differentes  ok
        // etape 2 : pied gauche      : 2 ressources identiques ok
        // etape 3 : pied droit       : 3 ressources differentes nok
        // etage 4 : buste            : 3 ressources identiques nok
        // etage 5 : tête             : 4 ressources différentes

        HashMap<Material,Integer> result = material;

        switch (stageConstructionActuel){
            case 0:   // Etape 0 -> Etape 1  -- on construit la base
                result = removeRessourcesUnidentical(material, 2);
                addStructurePoints(3);
                stageConstructionActuel = 1;
                break;

            case 1:  // Etape 1 -> Etape 2  -- on veut soit faire un pied gauche pour aller à l'étape 2 soit le droit pour aller à l'etage 3
                if ((!bPartieGaucheFaite) && hasRessourcesIdentical(material, 2)){
                    bPartieGaucheFaite = true;
                    addStructurePoints(2);
                    result = removeRessourcesIdentical(material, 2);
                    stageConstructionActuel = 2;
                } else if (hasRessourcesUnidentical(material, 3)) {  // on doit faire la partie droite
                    bPartieDroiteFaite =true;
                    addStructurePoints(5);
                    result = removeRessourcesUnidentical(material, 3);
                    stageConstructionActuel = 3;
                }
                break;

            case 2:  // Etape 2 -> Etape 3 -- on a le pied gauche, soit on fait le droit soit on passe au buste
                if ((!bPartieDroiteFaite)  && hasRessourcesUnidentical(material, 3)){
                    bPartieDroiteFaite = true;
                    addStructurePoints(5);
                    result = removeRessourcesUnidentical(material, 3);
                    stageConstructionActuel = 4;
                } else if (hasRessourcesIdentical(material, 3)) {  // on doit faire la partie droite
                    bPartieGaucheFaite =true;
                    addStructurePoints(2);
                result = removeRessourcesUnidentical(material, 3);
                stageConstructionActuel = 4;
            }
                break;

            case 3:  // Etape 3 -> Etape 4 -- on a le pied droit, soit on fait le gauche soit on passe au buste
                if (hasRessourcesIdentical(material, 2)){
                    bPartieGaucheFaite = true;
                    result = removeRessourcesIdentical(material, 2);
                    addStructurePoints(2);
                    stageConstructionActuel = 4;
                }
                break;

            case 4:
                result = removeRessourcesIdentical(material, 3);
                addStructurePoints(5);
                stageConstructionActuel = 5 ;
                break;

            case 5:
                result = removeRessourcesIdentical(material, 4);
                addStructurePoints(7);
                stageConstructionActuel = 6 ;
                break;
        }

        return result;
    }

    /**
     * teste si la merveille est achevée
     * @return vrai si la merveille est construite
     */
    public boolean isWonderAchieved(){
        return (stageConstructionActuel == WonderConstructionSteps.olympieSteps.get(6).value);
    }

    /**
     * information sur les materiaux nécessaires à la construction par etage
     * @return une chaine contenant les matiaux nécessaires
     */
    public final static String getConstructionInfo(){
        StringBuffer sb = new StringBuffer(200);
        sb.append("Olympie\n\tbase        \t2 ressources différentes\n");
        sb.append("Olympie\n\tpied gauche \t2 ressources identiques\n");
        sb.append("Olympie\n\tpied droit  \t2 ressources identiques\n");
        sb.append("Olympie\n\tbuste       \t3 ressources différentes\n");
        sb.append("Olympie\n\ttête        \t4 ressources différentes\n");
        return sb.toString();
    }
}
