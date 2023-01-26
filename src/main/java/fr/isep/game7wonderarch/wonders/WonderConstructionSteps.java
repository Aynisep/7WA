package fr.isep.game7wonderarch.wonders;

import java.util.Arrays;
import java.util.List;

/**
 * classe représentant les etapes de la construction d'une merveille
 */
public class WonderConstructionSteps {

    /**
     * valeur de l'étape de construction
     */
    public final int value;

    /**
     * nom de l'étape
     */
    public final String name;

    /**
     * image de l'étape
     */
    public final String url;

    /**
     * vrai si l'étape à un effet special
     */
    public final boolean actionMerveille;

    /**
     * Constructeur de la classe
     * @param name nom de l'étape
     * @param value valeur de l'étape
     * @param url chemin vers l'image
     * @param actionMerveille l'effet special
     */
    public WonderConstructionSteps(String name, int value, String url, boolean actionMerveille) {
        this.value = value;
        this.name = name;
        this.url = "/images/wonders/" + url;
        this.actionMerveille = actionMerveille;
    }

    /**
     * representation sous la forme d'une chaine de la merveille
     * @return un chaine représentant la merveille
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        sb.append("Merveille etapa = ").append(name).append(" value=").append(value).append(" url=").append(url);
        return sb.toString();
    }

    /**
     * les étapes de la construction du phare d'Alexandrie
     */
    public static final List<WonderConstructionSteps> alexandrieSteps = Arrays.asList(new WonderConstructionSteps[]{
            // material
            new WonderConstructionSteps("Etape 0", 0, "alexandrie/etape0.png", false), // chantier
            new WonderConstructionSteps("Etape 1", 1, "alexandrie/etape1.png", false), // base
            new WonderConstructionSteps("Etape 2", 2, "alexandrie/etape2.png", true),  // etage 1 : l'action est disponible
            new WonderConstructionSteps("Etape 3", 3, "alexandrie/etape3.png", false), // etage 2
            new WonderConstructionSteps("Etape 4", 4, "alexandrie/etape4.png", true),  // etage 3 : l'action est disponible
            new WonderConstructionSteps("Etape 5", 5, "alexandrie/etape5.png", false), // finie
    });


    /**
     * les étapes de la construction des jardins suspendus de Babylon
     */
    public static final List<WonderConstructionSteps> babylonSteps = Arrays.asList(new WonderConstructionSteps[]{
            // material
            new WonderConstructionSteps("Etape 0", 0, "babylon/etape0.png", false), // chantier
            new WonderConstructionSteps("Etape 1", 1, "babylon/etape1.png", false), // base
            new WonderConstructionSteps("Etape 2", 2, "babylon/etape2.png", true),  // etage 1
            new WonderConstructionSteps("Etape 3", 3, "babylon/etape3.png", false), // etage 2
            new WonderConstructionSteps("Etape 4", 4, "babylon/etape4.png", true),  // partie gaudhe du toit sans la droite
            new WonderConstructionSteps("Etape 5", 5, "babylon/etape5.png", false), // partie droite du toit sans la gauche
            new WonderConstructionSteps("Etape 6", 6, "babylon/etape6.png", false), // finie
    });

    /**
     * les etapes pour construire Ephese
     */
    public static final List<WonderConstructionSteps> epheseSteps = Arrays.asList(new WonderConstructionSteps[]{
            // material
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
    });

    /**
     * les etapes pour construire Gizeh
     */
    public static final List<WonderConstructionSteps> gizehSteps = Arrays.asList(new WonderConstructionSteps[]{
            // material
            new WonderConstructionSteps("Etape 0", 0, "gizeh/etape0.png", false), // chantier
            new WonderConstructionSteps("Etape 1", 1, "gizeh/etape1.png", false), // base
            new WonderConstructionSteps("Etape 2", 2, "gizeh/etape2.png", false), // etage 1
            new WonderConstructionSteps("Etape 3", 3, "gizeh/etape3.png", false), // etage 2
            new WonderConstructionSteps("Etape 4", 4, "gizeh/etape4.png", false), // etage 3
            new WonderConstructionSteps("Etape 5", 5, "gizeh/etape5.png", false), // finie

    });

    /**
     * les etapes pour construire Halicarnasse
     */
    public static final List<WonderConstructionSteps> halicarnasseSteps = Arrays.asList(new WonderConstructionSteps[]{
            // material
            new WonderConstructionSteps("Etape 0", 0, "halicarnasse/etape0.png", false), // chantier
            new WonderConstructionSteps("Etape 1", 1, "halicarnasse/etape1.png", false), // base
            new WonderConstructionSteps("Etape 2", 2, "halicarnasse/etape2.png", true), // etage 1
            new WonderConstructionSteps("Etape 3", 3, "halicarnasse/etape3.png", true), // etage 2
            new WonderConstructionSteps("Etape 4", 4, "halicarnasse/etape4.png", true), // etage 3
            new WonderConstructionSteps("Etape 5", 5, "halicarnasse/etape5.png", false), // finie

    });

    /**
     * les etapes pour construire Olympie
     */
    public static final List<WonderConstructionSteps> olympieSteps = Arrays.asList(new WonderConstructionSteps[]{
            // material
            new WonderConstructionSteps("Etape 0", 0, "olympie/etape0.png", false), // chantier
            new WonderConstructionSteps("Etape 1", 1, "olympie/etape1.png", false), // base
            new WonderConstructionSteps("Etape 2", 2, "olympie/etape2.png", true),  // pied gauche
            new WonderConstructionSteps("Etape 3", 3, "olympie/etape3.png", false), // pied droit
            new WonderConstructionSteps("Etape 4", 4, "olympie/etape4.png", true),  //  les pieds : c'est vrai si pied gauche n'est pas deja la, faire un ou pour test
            new WonderConstructionSteps("Etape 5", 5, "olympie/etape5.png", true),  // buste
            new WonderConstructionSteps("Etape 6", 6, "olympie/etape6.png", false), // finie

    });

    /**
     * les etapes pour construire Rhodes
     */
    public static final List<WonderConstructionSteps> rhodesSteps = Arrays.asList(new WonderConstructionSteps[]{
            // material
            new WonderConstructionSteps("Etape 0", 0, "rhodes/etape0.png", false), // chantier
            new WonderConstructionSteps("Etape 1", 1, "rhodes/etape1.png", true), // base gauche
            new WonderConstructionSteps("Etape 2", 2, "rhodes/etape2.png", false),  // base droite
            new WonderConstructionSteps("Etape 3", 3, "rhodes/etape3.png", true), // les deux bases : tester si droite en premier
            new WonderConstructionSteps("Etape 4", 4, "rhodes/etape4.png", false),  // les jambes
            new WonderConstructionSteps("Etape 5", 5, "rhodes/etape5.png", true),  // le buste
            new WonderConstructionSteps("Etape 6", 6, "rhodes/etape6.png", false), // finie

    });
}