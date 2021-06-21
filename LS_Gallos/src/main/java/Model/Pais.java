package Model;

import java.util.ArrayList;
/**
 * Classe amb la informacio dels paisos que estan permitits a la batalla
 */
public class Pais {
    private String nomAngles;
    private int nomHabitants;
    private String regio;
    private String urlBandera;
    private ArrayList<Idioma> idiomes;

    /**
     * Constructor de la classe
     * @param nomAngles String amb el nom en angles
     * @param nomHabitants int amb el nombre d'habitants
     * @param regio String amb la regio
     * @param urlBandera String amb l'url de la bandera
     * @param idiomes Array d'idiomes que es parlen al pais
     */
    public Pais (String nomAngles, int nomHabitants, String regio, String urlBandera, ArrayList<Idioma> idiomes) {
        this.nomAngles = nomAngles;
        this.nomHabitants = nomHabitants;
        this.regio = regio;
        this.urlBandera = urlBandera;
        this.idiomes = idiomes;
    }

    /**
     * Constructor sense parametres.
     */
    public Pais () {
        idiomes = new ArrayList<>();
    }

    public void setNomAngles(String nomAngles) {
        this.nomAngles = nomAngles;
    }

    public String getNomAngles() {
        return nomAngles;
    }

    public void setNomHabitants(int nomHabitants) {
        this.nomHabitants = nomHabitants;
    }

    public void setUrlBandera(String urlBandera) {
        this.urlBandera = urlBandera;
    }

    public ArrayList<Idioma> getIdiomes() {
        return idiomes;
    }

    public String getIdiomesToString() {
        String str = "";
        for (int i = 0; idiomes.size() > i; i++) {
            str = str + idiomes.get(i).getIdiomaAngles() + "<br>";
        }
        return str;
    }

    public void setRegio(String regio) {
        this.regio = regio;
    }

    public String getUrlBandera() {
        return urlBandera;
    }
}
