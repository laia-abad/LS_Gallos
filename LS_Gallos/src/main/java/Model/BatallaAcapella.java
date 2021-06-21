package Model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase que guarda una batalla del tipus Acapella.
 */
public class BatallaAcapella extends Batalla {

    /**
     * Constructor de la clase.
     * @param tema Array de temes que tindrà la batalla.
     */
    public BatallaAcapella(ArrayList<Tema> tema) {
        //herencia de batalla, li pases lainformacio del constructor al seu pare
        super(tema, "Acapella");
    }

    public BatallaAcapella(){}
    /**
     * Calcula els punts per a una batalla del tipus acapella
     * @param estrofa Estrofa introduida pel rapero
     * @return puntuacio
     */
    public float calculaPunts (String estrofa) {
        int repeticions = calculaRimes(estrofa);
        //cast
        float puntuacio = (float) ((6 * Math.sqrt(repeticions) + 3) / 2);
        return puntuacio;
    }


}
