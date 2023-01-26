package fr.isep.game7wonderarch.wonders;

import fr.isep.game7wonderarch.domain.Material;

import javafx.scene.image.ImageView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;

/**
 * classe abstraite contenant la définition d'une merveille
 */
public abstract class WonderStructure {

    /**
     * constructeur par defaut
     */
    public WonderStructure(){

    }

    /**
     * pour loger les messages
     */
    private static final Logger log = LogManager.getLogger( WonderStructure.class );

    /**
     * niveau de construction atteint
     * @return le niveeau actuel
     */
    public int getStageConstructionActuel() {
        return stageConstructionActuel;
    }

    private int structurePoints = 0;

    /**
     * ajoute des points de victoire liés à la merveille
     * @param structurePoints le nombre de point à ajouter en fonction de l'étage
     */
    public void addStructurePoints (int structurePoints){
        this.structurePoints += structurePoints;
    }

    /**
     * les pionts de Victoire liées à cette merveille
     * @return les points de victoire gagnés pas les étages construits
     */
    public int getStructurePoints(){
        return structurePoints;
    }

    /**
     * fixe l'étage actuel
     * @param stageConstructionActuel l'étage que l'on vient de construire
     */
    public void setStageConstructionActuel(int stageConstructionActuel) {
        this.stageConstructionActuel = stageConstructionActuel;
    }

    /**
     * ne peut etre utilisé directement dans la classe, lié à la merveille
     */
    protected int stageConstructionActuel = 0;

    /**
     * les fonctions suivantes sont protected et doivent mettre à null si l'element n'eixste pas
     * @return
     */

    private ImageView wonderPremier;

    private ImageView wonderDeuxieme;

    /**
     * retourne l'Image view pointant sur la premiere partie de la merveille
     * @return l'Image view pointant sur la premiere partie de la merveille
     */
    public ImageView getWonderPremier() {
        return wonderPremier;
    }

    /**
     * sauvagarde l'Image View de la premiere partie de la merveille
     * @param wonderPremier l'Image View sur lequel on devra agir pour mettre à jour les imagees
     */
    public void setWonderPremier(ImageView wonderPremier) {
        this.wonderPremier = wonderPremier;
    }

    /**
     * retourne l'Image view pointant sur la Deuxieme partie de la merveille
     * @return l'Image view pointant sur la Deuxieme partie de la merveille
     */
    public ImageView getWonderDeuxieme() {
        return wonderDeuxieme;
    }

    /**
     * sauvagarde l'Image View de la deuxieme partie de la merveille
     * @param wonderDeuxieme l'Image View sur lequel on devra agir pour mettre à jour les imagees
     */
    public void setWonderDeuxieme(ImageView wonderDeuxieme) {
        this.wonderDeuxieme = wonderDeuxieme;
    }

    /**
     * retourne l'Image view pointant sur la Troisieme partie de la merveille
     * @return l'Image view pointant sur la Troisieme partie de la merveille
     */
    public ImageView getWonderTroisieme() {
        return wonderTroisieme;
    }

    /**
     * sauvagarde l'Image View de la troiseme partie de la merveille
     * @param wonderTroisieme l'Image View sur lequel on devra agir pour mettre à jour les imagees
     */
    public void setWonderTroisieme(ImageView wonderTroisieme) {
        this.wonderTroisieme = wonderTroisieme;
    }

    /**
     * retourne l'Image view pointant sur la Quatrieme partie de la merveille
     * @return l'Image view pointant sur la Quatrieme partie de la merveille
     */
    public ImageView getWonderQuatrieme() {
        return wonderQuatrieme;
    }

    /**
     * sauvagarde l'Image View de la quatrieme partie de la merveille
     * @param wonderQuatrieme l'Image View sur lequel on devra agir pour mettre à jour les imagees
     */
    public void setWonderQuatrieme(ImageView wonderQuatrieme) {
        this.wonderQuatrieme = wonderQuatrieme;
    }

    /**
     * retourne l'Image view pointant sur la Cinquieme partie de la merveille
     * @return l'Image view pointant sur la Cinquieme partie de la merveille
     */
    public ImageView getWonderCinquieme() {
        return wonderCinquieme;
    }

    /**
     * sauvagarde l'Image View de la cinquieme partie de la merveille
     * @param wonderCinquieme l'Image View sur lequel on devra agir pour mettre à jour les imagees
     */
    public void setWonderCinquieme(ImageView wonderCinquieme) {
        this.wonderCinquieme = wonderCinquieme;
    }

    private ImageView wonderTroisieme;

    private ImageView wonderQuatrieme;

    private ImageView wonderCinquieme;

    /**
     * permet de savoir si la merveille est achevée
     * @return vrai si elle est achevéé
     */
    public abstract boolean isWonderAchieved();

    /**
     * permet de savoir si on peut construire un étage de plus
     * @param material la quantité de ressources dont on dispose
     * @return doit être overwrité
     */
    public abstract boolean isConstructionPossible(HashMap<Material,Integer> material);

    /**
     * permet d'enlever les ressoruces pour construire un étage. Recherche la meilleure ressource et si besoin complète avec de l'or
     * @param material liste des ressrouces disponibles
     * @param quantity la quantité à trouver d'une ressource éventuellement accompagnée d'or
     * @return la liste des ressources moins celles qu'il fallait enlever
     */
    public HashMap<Material,Integer> removeRessourcesIdentical(HashMap<Material,Integer> material, int quantity){

        HashMap<Material,Integer> newMaterials = new HashMap<>();

        Material key=null;
        int value=0;
        int maxValue=0;
        Material tmpMaterial=null;
        boolean found = false;

        for(Iterator i = material.keySet().iterator(); i.hasNext();) {
            key = (Material) i.next();
            value = Integer.valueOf(material.get(key));

            if ((!found) && (value >= quantity) && !key.equals(Material.Gold)) {
                newMaterials.put(key, material.get(key) - quantity);
                found = true;
            } else if ((maxValue < value) && !key.equals(Material.Gold)){
                tmpMaterial = key;
                maxValue = value;
                newMaterials.put(key, material.get(key));
            } else {
                newMaterials.put(key, material.get(key));
            }
        }

        if(!found) {
            if (maxValue>0) {
                newMaterials.put(tmpMaterial, newMaterials.get(tmpMaterial)-maxValue);
            }
            newMaterials.replace(Material.Gold, newMaterials.get(Material.Gold)-(quantity-maxValue));
        }

        return newMaterials;
    }

    /**
     * construit un étage
     * @param material la lkiste des matériaux disponibles
     * @return la liste des matériaux restants
     */
    public abstract HashMap<Material,Integer> buildOneFloor(HashMap<Material,Integer> material);

    /**
     * permet d'enlever les ressoruces pour construire un étage. Recherche la meilleure ressource et si besoin complète avec de l'or
     * @param material liste des ressrouces disponibles
     * @param quantity la quantité à trouver d'une ressource éventuellement accompagnée d'or
     * @return vrai s'il a trouve la quantité necessaire
     */
    public boolean hasRessourcesIdentical(HashMap<Material,Integer> material, int quantity){
        Material key=null;
        int value=0;
        int maxValue=0;
        Material tmpMaterial=null;
        boolean found = false;
        int goldQuantity=0;

        for(Iterator i = material.keySet().iterator(); i.hasNext();) {
            key = (Material) i.next();
            value = Integer.valueOf(material.get(key));

            if ((!found) && (value >= quantity) && !key.equals(Material.Gold)) {
                found = true;
            } else if ((maxValue < value) && !key.equals(Material.Gold)){
                tmpMaterial = key;
                maxValue = value;
            }  else if (key.equals(Material.Gold)){
                goldQuantity = value;
            }

            if (found){
                break;
            }
        }

        if(!found) {
            return ((maxValue+goldQuantity)>=quantity);
        } else {
            return true;
        }
    }

    /**
     * permet d'enlever les ressoruces pour construire un étage. Recherche la meilleure combinzison de ressources et si besoin complète avec de l'or
     * @param material liste des ressrouces disponibles
     * @param quantity la quantité à trouver des ressources éventuellement accompagnée d'or
     * @return la liste des ressources restantes après avoir enlever quantity de ressources différentes
     */
    public HashMap<Material,Integer> removeRessourcesUnidentical(HashMap<Material,Integer> material, int quantity){

        HashMap<Material,Integer> newMaterials = new HashMap<>();

        Material key=null;
        int value=0;
        int counteur  = 0;

        for(Iterator i = material.keySet().iterator(); i.hasNext();) {
            key = (Material) i.next();
            value = Integer.valueOf(material.get(key));

            if ((value>0) && (counteur <= quantity) && !key.equals(Material.Gold)) {
                newMaterials.put(key, material.get(key) - 1);
                counteur++;
            }
            else {
                newMaterials.put(key, material.get(key));
            }
        }
        if(counteur <= quantity) {
            newMaterials.replace(Material.Gold, newMaterials.get(Material.Gold)-(quantity-counteur));
        }
        return newMaterials;
    }

    /**
     * permet de savoir si on a assez de ressources différentes
     * @param material liste des ressrouces disponibles
     * @param quantity la quantité à trouver de ressources différentes éventuellement accompagnée d'or
     * @return vrai s'il a trouve la quantité necessaire
     */
    public boolean hasRessourcesUnidentical(HashMap<Material,Integer> material, int quantity){
        if ((material==null) || (material.size()==0)){
            log.info("Probleme à l'initialisation du joueur");
            return false;
        }

        HashMap<Material,Integer> newMaterials = new HashMap<>();

        Material key=null;
        int value=0;
        int goldQuantity=0;

        for(Iterator i = material.keySet().iterator(); i.hasNext();) {
            key = (Material) i.next();
            value = Integer.valueOf(material.get(key));

            if (key.equals(Material.Gold)){
                goldQuantity = value;
            } else if (value>0){
                newMaterials.put(key, value);
            }
        }

        return ((newMaterials.size()+goldQuantity)>=quantity);
    }

}
