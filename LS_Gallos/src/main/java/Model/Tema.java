package Model;

import java.util.ArrayList;

/**
 * Classe que emmagatzema un tema d'una batalla
 */

public class Tema {
    private String nom;
    private ArrayList<Rima> rimes;

    /**
     * Constructor de la classe
     * @param nom Nom del tema
     * @param rimes Array de rimes que te el tema
     */

    public Tema (String nom, ArrayList<Rima> rimes) {
        this.nom = nom;
        this.rimes = rimes;
    }

    /**
     * Constructor sense parametres.
     */
    public Tema() {}

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setRimes(ArrayList<Rima> rimes) {
        this.rimes = rimes;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Rima> getRimes() {
        return rimes;
    }
}
