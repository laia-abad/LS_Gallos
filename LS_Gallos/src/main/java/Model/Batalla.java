package Model;

import java.util.ArrayList;

/**
 * Clase que guarda una batalla, amb els seus temes i el tipus de batalla.
 */
public class Batalla {
    private ArrayList<Tema> tema;
    private String tipus;

    /**
     * Constructor de classe
     * @param tema Array de temes que te la batalla
     * @param tipus String del tipus de batalla
     */
    public Batalla(ArrayList<Tema> tema, String tipus) {
        this.tema = tema;
        this.tipus = tipus;
    }

    public Batalla(){
        tema = new ArrayList<>();
    }

    /**
     * Metode per calcular els punts segons un estrofa
     * @param estrofa Estrofa introduit pel rapero
     * @return puntuacio
     */
    public float calculaPunts (String estrofa) {
        return 0;
    }

    /**
     * Calcula quantes rimes hi ha en una estrofa
     * @param estrofa estrofa introduida pel usuari. Ha de tenir 4 versos, els 3 primers acabats en coma i el ultim en punt.
     * @return nombre de rimes a l'estrofa
     */
    protected static int calculaRimes(String estrofa) {
       boolean primer = true;
       String rima1 = "aaa"; //inicialitzem amb tres characters ja que la rima es de 2;
       int repeticions = 0;

       //declarem una eina per contruir una string
       StringBuilder vers1 = new StringBuilder(estrofa);

       //mirem cada vers i busquem rimes
       for (int j = 0; j < 4; j++) {
           int i;
           //al ultim vers hi ha un punt en comptes d'una coma
           if (j == 4 - 1) {
               i = vers1.indexOf(".");
           }
           else {
               i = vers1.indexOf(",");
           }
           //eliminem la coma/punt per a no llegir-la una altra vegada
           vers1.deleteCharAt(i);
           //fem un string amb la rima per a
           String rima2 = new StringBuilder().append(vers1.charAt(i - 2)).append(vers1.charAt(i - 1)).toString();
           if (rima1.equals(rima2)) {
               if (primer) {
                   repeticions++;
               }
               primer = false;
               repeticions++;
           }
           rima1 = rima2;
       }
       return repeticions;
   }

    public ArrayList<Tema> getTema() {
        return tema;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }
}
