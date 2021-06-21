package Model;

import java.util.ArrayList;

/**
 * Clase que emmagatzema una fase de la competicio
 */
public class Fase {
    private float pressupost;
    private String pais;
    private ArrayList<Batalla> batalles;

    /**
     * Constructor de la classe
     * @param pressupost Presupost que hi ha per a la organitzacio de la batalla
     * @param pais Pais on es celebra la fase
     * @param batalles Array de batalles que hi ha a la fase
     */
    public Fase (float pressupost, String pais, ArrayList<Batalla> batalles){
        this.pressupost = pressupost;
        this.pais = pais;
        this.batalles = batalles;
    }

    /**
     * Constructor sense parametres
     */
    public Fase () {
        batalles = new ArrayList<>();
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setPressupost(float pressupost) {
        this.pressupost = pressupost;
    }

    public float getPressupost() {
        return pressupost;
    }

    public String getPais() {
        return pais;
    }

    public ArrayList<Batalla> getBatalles() {
        return batalles;
    }
}
