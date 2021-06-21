package Model;

/**
 * Classe amb tota la informacio del idioma
 */
public class Idioma {
    private String idiomaAngles;
    private String idiomaNatiu;

    /**
     * Constructor de la classe
     * @param idiomaAngles creem una variable per l'idioma en angles
     * @param idiomaNatiu creem una variable per l'idioma natiu.
     */
    public Idioma (String idiomaAngles, String idiomaNatiu) {
        this.idiomaAngles = idiomaAngles;
        this.idiomaNatiu = idiomaNatiu;
    }

    public Idioma () {}

    public void setIdiomaAngles(String idiomaAngles) {
        this.idiomaAngles = idiomaAngles;
    }

    public void setIdiomaNatiu(String idiomaNatiu) {
        this.idiomaNatiu = idiomaNatiu;
    }

    public String getIdiomaAngles() {
        return idiomaAngles;
    }

    public String getIdiomaNatiu() {
        return idiomaNatiu;
    }
}
