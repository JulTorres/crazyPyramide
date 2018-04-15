package fr.wildcodeschool;

import java.util.*;

public class CrazyPyramide {

    private int taille;
    private int nbLignes;
    private int ligneCounter = 0;
    private int nbEspaceInitial;
    private int nbEspaceCourant;
    private int nbEtoilesCourant;


    /**
     * CONSTRUCTOR
     */
    public CrazyPyramide(int taille) {
        this.taille = taille;
        nbLignes = ( taille * (taille + 5) ) / 2;
        setNbEspaceInitial();
    }

    /**
     * SET INITIAL NUMBER OF SPACES
     */
    private void setNbEspaceInitial() {
        nbEspaceInitial = 0;
        nbEspaceInitial += nbLignes - 1;
        nbEspaceInitial += (taille - 1) * 2;
        int modificateurVariable = 0;
        for (int i = 1; i <= taille; i++) {
            if (i % 2 == 0 && i > 2) {
                nbEspaceInitial += ++modificateurVariable;
            } else nbEspaceInitial += modificateurVariable;
        }
    }


    /**
     * RESPONSIBLE FOR BUILDING THE LEVELS
     * @return List<String> The whole pyramid, a string for each line
     */
    private List<String> buildPyramide() {
        nbEspaceCourant = nbEspaceInitial;
        nbEtoilesCourant = 1;
        int modificateurGlobal = 2;
        List<String> lignesPyramide = new ArrayList<String>();

        for (int i = 1; i <= taille; i++) {
            // modify number of spaces and stars according to the level
            if(i > 1 ) {
                if(i % 2 == 0 && i > 2) {
                    modificateurGlobal += 1;
                }
                nbEspaceCourant -= modificateurGlobal;
                nbEtoilesCourant += 2 * modificateurGlobal;
            }
            // call buildNiveau each level
            lignesPyramide.addAll(buildNiveau(i));
        }
        return lignesPyramide;
    }

    /**
     * RESPONSIBLE FOR BUILDING THE LINES
     * @param numNiveau
     * @return String[] : tableau des lignes du niveau
     */
    private List<String> buildNiveau(int numNiveau) {

        int nbLignesNiveau = numNiveau + 2;
        List<String> lignesNiveau = new ArrayList<String>();


        // call buildLigne for each line of the level
        for (int i = 0; i < nbLignesNiveau; i++) {
            lignesNiveau.add(buildLigne(nbLignes, ++ligneCounter)); // usage de ligneCounter ??
        }

        // call buildPorte if this is the last level
        if(numNiveau == this.taille) {
            lignesNiveau = buildPorte(lignesNiveau);
        }

        return lignesNiveau;
    }

    /**
     *  Génère une ligne de la pyramide.
     * @param nbLigne : Le nombre de lignes de la pyramide.
     * @param numLigne : Le numéro de la ligne à construire.
     * @return String : Les caractères de la ligne.
     */
    private String buildLigne(int nbLigne, int numLigne){

        StringBuilder stringBuilder = new StringBuilder();
        String ligne;

        //calcul le nb d'espaces de la ligne
        int nbEspaces = nbEspaceCourant--;

        //calcule le nombre d'étoiles de la ligne
        int nbEtoile = nbEtoilesCourant;
        nbEtoilesCourant += 2;

        // ajoute les espaces
        for(int i = nbEspaces; i > 0; i-- ) {
            stringBuilder.append(' ');
        }

        // ajoute le /
        stringBuilder.append('/');

        // ajoute les étoiles
        for(int i = nbEtoile; i > 0; i--) {
            stringBuilder.append('*');
        }

        //ajoute le \
        stringBuilder.append('\\');

        ligne = stringBuilder.toString();
        return ligne;
    }

    /**
     * Imprime toutes les lignes de la pyramide.
     */
    private void drawPyramide() {
        List<String> lines = this.buildPyramide();
        for (String line: lines ) {
            System.out.println(line);
        }
    }

    // reçoit les lignes du dernier niveau et les décore pour ajouter la porte
    private List<String> buildPorte(List<String> niveau) {
        int nbLignes = niveau.size();
        int centre = (int) Math.ceil(niveau.get(nbLignes - 1).length() / 2);

        for(int i = 0; i < taille; i++) {
            String ligneModifiee = buildLignePorte(niveau.get(nbLignes-1 - i), centre);
            niveau.set(nbLignes-1 - i, ligneModifiee);
        }


        if(taille > 1) {
            int indicePoignee = (niveau.size() - 1) - (int) Math.floor((taille - 1) / 2);
            String ligneModifiee = buildPoignee(niveau.get(indicePoignee), centre);
            niveau.set(indicePoignee, ligneModifiee);
        }
        return niveau;
    }

    // transforme les * en | pour construire la porte
    private String buildLignePorte(String ligne, int centre) {
        int nbChars = ligne.length();
        char[] ligneChars = ligne.toCharArray();

        int modificateur = 0;
        int cible = 0;
        for(int i = 1; i <= taille; i++) {
            if(i % 2 == 0) {
                modificateur += 1;
                cible = centre + modificateur;
            } else {
                cible = centre - modificateur;
            }
            ligneChars[cible] = '|';
        }

        String nouvelleLigne = new String(ligneChars);//retransforme en String
        return nouvelleLigne;
    }

    private String buildPoignee(String ligne, int centre) {
        char[] ligneChars = ligne.toCharArray();

        ligneChars[centre + 1] = '$';

        String nouvelleLigne = new String(ligneChars);//retransforme en String
        return nouvelleLigne;
    }

    public static void main(String... args) {
        // crée une pyramide dont la taille est demandée en argument par l'utilisateur
/*
        CrazyPyramide pyramide = new CrazyPyramide(Integer.parseInt(args[0]));
*/
        // test avec taille fixe
        CrazyPyramide pyramide = new CrazyPyramide(1);

        pyramide.drawPyramide();
    }

}
