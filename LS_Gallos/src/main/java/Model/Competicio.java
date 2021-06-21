package Model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Clase que emmagatzema totes les dades de la competicio
 */
public class Competicio {
    private String nom;
    private Date dataInicial;
    private Date dataFinal;
    private ArrayList<Rapero> raperos;
    private ArrayList<Fase> fases;
    private ArrayList<Pais> paisosAcceptats;

    /**
     * Constructor de clase
     * @param nom Nom de la competicio
     * @param dataInicial data d'inici de la competicio
     * @param dataFinal data on acaba la competicio
     * @param raperos array de raperos que participaran a la competicio
     * @param fases array de fases de la competicio
     * @param paisosAcceptats paisos acceptats a la competicio
     */
    public Competicio (String nom, Date dataInicial, Date dataFinal, ArrayList<Rapero> raperos, ArrayList<Fase> fases, ArrayList<Pais> paisosAcceptats) {
        this.nom = nom;
        this.dataFinal = dataFinal;
        this.dataInicial = dataInicial;
        this.raperos = raperos;
        this.fases = fases;
        this.paisosAcceptats = paisosAcceptats;
    }

    /**
     * Constructor sense parametres
     */
    public Competicio () {}

    //set gurada i get mostra
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public ArrayList<Fase> getFases() {
        return fases;
    }

    public void setRaperos(ArrayList<Rapero> raperos) {
        this.raperos = raperos;
    }

    public void setPaisosAcceptats(ArrayList<Pais> paisosAcceptats) {
        this.paisosAcceptats = paisosAcceptats;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setFases(ArrayList<Fase> fases) {
        this.fases = fases;
    }

    public ArrayList<Rapero> getRaperos() {
        return raperos;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Pais> getPaisosAcceptats() {
        return paisosAcceptats;
    }
}
