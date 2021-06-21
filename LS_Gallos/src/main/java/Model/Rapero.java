package Model;

import java.util.Date;

/**
 * Classe amb informacio dels raperos que participen a la competicio
 */
public class Rapero {
    private String nomComplert;
    private String nomArtistic;
    private Date dataNaixement;
    private int nivell;
    private String urlImatge;
    private String paisOrigen;
    private float puntuacio;

    /**
     * Constructor de la classe
     * @param nomComplert String amb el nom del rapero
     * @param nomArtistic String amb el nom artistic del rapero
     * @param dataNaixement Data de naixement del rapero
     * @param nivell int amb el nivell d'expertessa
     * @param urlImatge String amb l'url de la imatge de perfil del rapero
     * @param paisOrigen String amb el país d'origen del rapero
     * @param puntuacio int amb la puntuacio actual del rapero
     */
    public Rapero (String nomComplert, String nomArtistic, Date dataNaixement, int nivell, String urlImatge, String paisOrigen, float puntuacio) {
        this.nomComplert = nomComplert;
        this.nomArtistic = nomArtistic;
        this.dataNaixement = dataNaixement;
        this.nivell = nivell;
        this.urlImatge = urlImatge;
        this.paisOrigen = paisOrigen;
        this.puntuacio = puntuacio;
    }

    /**
     * Constructor de la classe
     */
    public Rapero () {
        this.puntuacio = 0;
    }

    /**
     * Suma els punts que ha aconseguit l'usuari ara amb els que ja tenia
     * @param punts puntuacio que ha aconseguit l'usuari
     */
    public void actualitzarPunts (float punts) {
        this.puntuacio += punts;
    }

    public void setDataNaixement(Date dataNaixement) {
        this.dataNaixement = dataNaixement;
    }

    public void setNivell(int nivell) {
        this.nivell = nivell;
    }

    public void setNomArtistic(String nomArtistic) {
        this.nomArtistic = nomArtistic;
    }

    public void setNomComplert(String nomComplert) {
        this.nomComplert = nomComplert;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
    }

    public void setUrlImatge(String urlImatge) {
        this.urlImatge = urlImatge;
    }

    public Date getDataNaixement() {
        return dataNaixement;
    }

    public int getNivell() {
        return nivell;
    }

    public float getPuntuacio() {
        return puntuacio;
    }

    public String getNomArtistic() {
        return nomArtistic;
    }

    public String getNomComplert() {
        return nomComplert;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public String getUrlImatge() {
        return urlImatge;
    }

}
