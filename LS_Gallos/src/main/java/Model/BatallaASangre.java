package Model;

import java.util.ArrayList;

/**
 * Clase que guarda una batalla tipus a sangre
 */
public class BatallaASangre extends Batalla {
    private String nomProductor;

    /**
     * Constructor de la clase
     * @param nomProductor Nom del productor de les bases
     * @param tema Array de temes que conte la batalla
     */
    public BatallaASangre (String nomProductor, ArrayList<Tema> tema) {
        super(tema, "A Sangre");
        this.nomProductor = nomProductor;
    }
    public BatallaASangre(){}
    /**
     * Calcula els punts per a una batalla del tipus a sangre
     * @param estrofa Estrofa introduida pel rapero
     * @return puntuacio
     */
    public float calculaPunts (String estrofa) {
        int repeticions = calculaRimes(estrofa);
        float puntuacio = (float) ((Math.PI * repeticions * repeticions) / 4);
        return puntuacio;
    }
}
