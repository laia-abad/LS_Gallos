package Model;

import java.util.ArrayList;

/**
 * Clase que guarda una batalla tipus escrita
 */
public class BatallaEscrita extends Batalla{
    private int segonsMax;

    /**
     * Constructor de la clase
     * @param tema Array de temes que conte la batalla
     * @param segonsMax Segons màxims dels participants per escriure les seves barres
     */
    public BatallaEscrita (ArrayList<Tema> tema, int segonsMax) {
        super(tema, "Escrita");
        this.segonsMax = segonsMax;
    }

    public BatallaEscrita(){}

    /**
     * Calcula els punts per a una batalla del tipus escrita
     * @param estrofa Estrofa introduida pel rapero
     * @return puntuacio
     */
    public float calculaPunts(String estrofa) {
        int repeticions = calculaRimes(estrofa);
        float puntuacio = ((16 + 2 + 128 + 64 + 256 + 4 + 32 + 512 + 1024 + repeticions) / (1024 + 128 + 4 + 64 + 16 + 256 + repeticions + 2 + 32 + 512)) + 3 * repeticions;
        return puntuacio;
    }
}
