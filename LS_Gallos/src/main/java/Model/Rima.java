package Model;

import java.util.ArrayList;

/**
 * Classe que emmagatzema una rima d'un tema.
 */
public class Rima {
    private int level;
    private ArrayList<String> versos;

    /**
     * Constructor de la classe
     * @param level nivell per a poder utilitzar la rima
     * @param versos Array de versos
     */

    public Rima (int level, ArrayList<String> versos) {
        this.level = level;
        this.versos = versos;
    }

    /**
     * Constructor sense parametres.
     */
    public Rima () {}

    public void setLevel(int level) {
        this.level = level;
    }

    public void setVersos(ArrayList<String> versos) {
        this.versos = versos;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<String> getVersos() {
        return versos;
    }
}
