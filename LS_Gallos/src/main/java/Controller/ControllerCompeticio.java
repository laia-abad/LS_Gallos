//carpeta on esta
package Controller;

//importem informacio dels arxius.
import Data.ApiPais;
import Data.DataWriterJson;
import Data.HtmlGenerator;
import Model.*;

import java.io.IOException;
import java.util.*;

/**
 * Clase on es realitzen totes les funcionalitats del programa despres del login/registre. Tot el relacionat amb la
 * competicio i les batalles.
 */

public class ControllerCompeticio {
    //els atributs tot privats per la encapsulacio.
    private ArrayList<Tema> temes; //array de temes
    private Competicio competicio; //tipus competicio on guradem les competicions
    private Scanner reader; //clase per fer scanf, creem una variable
    private Rapero user; //informacio que introdueix l'usuari

    /**
     * Constructor de la clase
     * @param temes Array de tots els temes que existeixen per les batalles.
     * @param competicio Conté tota la informació de la competició
     * @param user Conté la informació del usuari que ha fet login
     */

    public ControllerCompeticio(ArrayList<Tema> temes, Competicio competicio, Rapero user) {
        this.temes = temes;
        this.competicio = competicio;
        reader = new Scanner(System.in);
        this.user = user;
    }

    /**
     * Fa els emparellaments per a les batalles i les realitza.
     */
    private void realitzarBatallesOcultes(int numFase) {
        //matriu de 2X (numraperos/2)
        ArrayList<ArrayList<Rapero>> parelles = new ArrayList<>();

        //barrejem l'array de raperos
        Collections.shuffle(competicio.getRaperos());
        for (int i = 0; i < competicio.getRaperos().size(); i++) {
            if (competicio.getRaperos().get(i).getNomArtistic().equals(user.getNomArtistic())) {
                Collections.swap(competicio.getRaperos(), i, competicio.getRaperos().size() - 1);
            }
        }

        //Fem els emparellaments dels raperos per a poder fer les batalles
        for (int i = 0; i < competicio.getRaperos().size() - 2; i++) {
            ArrayList<Rapero> aux_parella = new ArrayList<>();
            aux_parella.add(competicio.getRaperos().get(i));
            i++;
            aux_parella.add(competicio.getRaperos().get(i));
            parelles.add(aux_parella);
        }

        //Realizem totes les batalles que no es mostraran per pantalla
        for (ArrayList<Rapero> parella : parelles) {
            batallaOculta(parella, numFase);
        }
    }

    /**
     * Realitza les batalles dels altres raperos i mostra a l'usuari el menu del lobby.
     */
    public void lobby() {
        //es true quan l'usuari hagi seleccionat l'opcio per a sortir
        boolean leave = false;

        //array per saber quin tipus de batalla, entre 3 opcions
        ArrayList<String> tipus = new ArrayList<>(Arrays.asList("Acapella", "A Sangre", "Escrita"));
        Collections.shuffle(tipus);

        //es true quan l'usuari hagi perdut
        boolean perdut = false;

        if (competicio.getRaperos().size() % 2 != 0) {
            Random r = new Random();
            //numero del rapero aleatori (que no es l'usuari)
            int result = competicio.getRaperos().size() - 1;
            while (result == competicio.getRaperos().size() - 1) {
                result = r.nextInt(competicio.getRaperos().size());
            }
            //treiem el rapero aleatori
            competicio.getRaperos().remove(result);
        }


        //conte l'array de raperos ordenat
        ArrayList<Rapero> ranking = competicio.getRaperos();
        int numFase = 1;
        realitzarBatallesOcultes(numFase - 1);
        int numBatalla = 1;
        //Sortim si l'usuari ha realitzat totes les fases, abandona o perd
        while (numFase <= competicio.getFases().size() && !leave && !perdut) {
            numBatalla = 1;
            while ((numBatalla < 3 && !leave) && !perdut) {
                //es true quan s'hagi realitzat una batalla
                boolean fet = false;
                Rapero rival;
                //comprovem que el rival del usuari no sigui ell mateix.
                if (competicio.getRaperos().get(competicio.getRaperos().size() - 2) != user) {
                    //agafem el rival
                    rival = competicio.getRaperos().get(competicio.getRaperos().size() - 2);
                } else {
                    //agafem un altre rival
                    rival = competicio.getRaperos().get(competicio.getRaperos().size() - 1);
                }

                //ordenem els usuaris al ranking
                ranking = competicio.getRaperos();
                //ordena segons la puntuacio
                ranking.sort(Comparator.comparing(Rapero::getPuntuacio));
                //li dona la volta de major a menor
                Collections.reverse(ranking);

                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------\n" +
                        "Phase: " + numFase + " / " + competicio.getFases().size() + " | Score: " + (int) user.getPuntuacio() + " | Battle " + numBatalla + " / 2: " + tipus.get(0) + " | Rival: " + rival.getNomArtistic() + "\n" +
                        "-------------------------------------------------------------------------------------------------------------------------------------------\n" +
                        "\n" +
                        "1. Start the battle\n" +
                        "2. Show ranking\n" +
                        "3. Create profile\n" +
                        "4. Leave competition\n" +
                        "\n" +
                        "Choose an option:\n");

                String opcio = reader.nextLine();
                switch (opcio) {
                    case "1":
                        //quan s'ha realitzat una batalla augmentem el nombre de batalles per la que anem
                        batallaPerPantalla(rival, user, tipus.get(0), numFase - 1);
                        numBatalla++;
                        //barrejem una els tipus per a que siguin aleatoris
                        Collections.shuffle(tipus);
                        //s'ha realitzat una batalla per tant, true
                        fet = true;
                        break;

                    case "2":
                        ranquing(ranking);
                        break;

                    case "3":
                        createProfile();
                        break;

                    case "4":
                        leave = true;
                        acabarBatallesOcultes(numFase - 1, numBatalla);
                        try {
                            DataWriterJson.reordenarRaperos(competicio.getRaperos().get(0));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    default:
                        System.out.println("The selected option doesn't exist.");

                }
                //ordenem el ranquing
                ranking = competicio.getRaperos();
                ranking.sort(Comparator.comparing(Rapero::getPuntuacio));
                Collections.reverse(ranking);
                //mira en quina fase esta per saber el numero de raperos i retorna si l'usuari perd o no
                perdut = checkFase(ranking, numFase, numBatalla);
                //si l'usuari ha fet una batalla, realizem les batalles dels altres usuaris.
                if (fet) {
                    realitzarBatallesOcultes(numFase - 1);
                }
            }
            //si l'usuari no ha perdut ni s'ha acabat la competicio, augmentem la fase i posem les puntuacions a 0.
            if (!perdut && numFase != 3) {
                for (int i = 0; i < competicio.getRaperos().size(); i++) {
                    competicio.getRaperos().get(i).setPuntuacio(0);
                }
            }
            numFase++;
        }
        numFase--;
        //menu que es mostra una vegada el usuari no pot realitzar mes batalles
        while (!leave) {
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
            if (perdut) {
                System.out.println("Phase: " + numFase + " / " + competicio.getFases().size() + " | Score: " + (int) user.getPuntuacio() + " | You've lost kid, I'm sure you'll do better next time... ");
            } else {
                System.out.println("Phase: " + numFase + " / " + competicio.getFases().size() + " | Score: " + (int) user.getPuntuacio() + " | You won! Well done. ");
            }
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------\n" +
                    "\n" +
                    "1. Start the battle\n" +
                    "2. Show ranking\n" +
                    "3. Create profile\n" +
                    "4. Leave competition\n" +
                    "\n" +
                    "Choose an option:\n");

            String opcio = reader.nextLine();
            switch (opcio) {
                case "1":
                    System.out.println("Competition ended. You cannot battle anyone else!\n");
                    break;

                case "2":
                    //ordenem els usuaris al ranking
                    ranking = competicio.getRaperos();
                    //ordena segons la puntuacio
                    ranking.sort(Comparator.comparing(Rapero::getPuntuacio));
                    //li dona la volta de major a menor
                    Collections.reverse(ranking);
                    ranquing(ranking);
                    break;

                case "3":
                    createProfile();
                    break;

                case "4":
                    leave = true;
                    acabarBatallesOcultes(numFase - 1, numBatalla);
                    try {
                        DataWriterJson.reordenarRaperos(ranking.get(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    reader.close();
                    break;

                default:
                    System.out.println("The selected option doesn't exist.");

            }
        }
    }

    //realitza les batalles falten despres de que l'usuari perdi
    private void acabarBatallesOcultes(int numFase, int numBatalla) {
        //Simulem la resta de batalles
        if (numFase < competicio.getFases().size() || numBatalla < 2) {
            while (numFase < competicio.getFases().size()) {
                numBatalla = 1;
                while (numBatalla <= 3) {
                    //ordenem el ranquing
                    competicio.getRaperos().sort(Comparator.comparing(Rapero::getPuntuacio));
                    Collections.reverse(competicio.getRaperos());
                    checkFase(competicio.getRaperos(), numFase, numBatalla);
                    //si l'usuari ha fet una batalla, realizem les batalles dels altres usuaris.
                    realitzarBatallesOcultes(numFase);
                    numBatalla++;
                }
                //si no s'ha acabat la competicio, augmentem la fase i posem les puntuacions a 0.
                if (numFase != 3) {
                    numFase++;
                    for (int i = 0; i < competicio.getRaperos().size(); i++) {
                        competicio.getRaperos().get(i).setPuntuacio(0);
                    }
                }
            }
        }
    }

    /**
     * Mira la fase actual i redueix el nom de raperos a la competicio segons la fase. Si l'usuari no esta a la llista
     * reduida, retorna un boolean indicant que ha perdut.
     * @param ranking Llista de raperos ordenada per punts.
     * @param numFase Numero de la fase actual.
     * @param numBatalla Numero de la batalla actual
     * @return true si l'usuari ha perdut, false si no.
     */
    private boolean checkFase(ArrayList<Rapero> ranking, int numFase, int numBatalla) {
        boolean perdut = true;
        //numBatalla es igual a 3 al acabar la ultima batalla de la fase
        if (competicio.getFases().size() == 2 && numBatalla == 3) {
            //numFase encara no ha augmentat quan cridem aquesta funcio, aixi que li sumem 1
            if (numFase + 1 == 2) {
                //ens quedem nomes amb els 2 millors raperos
                competicio.setRaperos(new ArrayList<>(ranking.subList(0, 2)));
            }
        //numBatalla es igual a 3 al acabar la ultima batalla de la fase
        } else if (competicio.getFases().size() == 3 && numBatalla == 3) {
            //numFase encara no ha augmentat quan cridem aquesta funcio, aixi que li sumem 1
            if (numFase + 1 == 2) {
                //ens quedem nomes amb la primera meitat dels millors raperos
                competicio.setRaperos(new ArrayList<>(ranking.subList(0, (ranking.size()/2))));
            //numFase encara no ha augmentat quan cridem aquesta funcio, aixi que li sumem 1
            } else if (numFase + 1 == 3) {
                //ens quedem nomes amb els 2 millors raperos
                competicio.setRaperos(new ArrayList<>(ranking.subList(0, 2)));
            }
        }

        //Mirem si l'usuari esta a l'array de raperos. Si no hi es, ha perdut.
        for (int i = 0; i < competicio.getRaperos().size(); i++) {
            if (competicio.getRaperos().get(i).getNomArtistic().equals(user.getNomArtistic())) {
                perdut = false;
                break;
            }
        }
        return perdut;
    }

    /**
     * Realitza les batalles que no es mostren per pantalla
     * @param raperos Array que conte la parella de raperos que s'esta enfrentant.
     */
    private void batallaOculta(ArrayList<Rapero> raperos, int numFase) {
        //barrejem els tipus per a que sigui random
        ArrayList<String> tipus = new ArrayList<>(Arrays.asList("Acapella", "A Sangre", "Escrita"));
        Collections.shuffle(tipus);
        //barrejem els raperos per a que sigui random
        Collections.shuffle(raperos);

        //Creem una batalla del tipus corresponent
        if (tipus.get(0).equals("Acapella")) {
            competicio.getFases().get(numFase).getBatalles().add(new BatallaAcapella());
        } else if (tipus.get(0).equals("A Sangre")) {
            competicio.getFases().get(numFase).getBatalles().add(new BatallaASangre());
        } else {
            competicio.getFases().get(numFase).getBatalles().add(new BatallaEscrita());
        }

        //2 batalles
        for (int n = 0; n < 2; n++) {
            //2 raperos
            for (int j = 0; j < 2; j++) {
                //barrejem els temes per a que sigui random
                Collections.shuffle(temes);
                int nivell = raperos.get(j).getNivell();
                //Busquem una rima que sigui del nivell del rapero
                for (int i = 0; i < temes.get(0).getRimes().size(); i++) {
                    if (nivell == temes.get(0).getRimes().get(i).getLevel()) {
                        Collections.shuffle(temes.get(0).getRimes().get(i).getVersos());
                        raperos.get(j).actualitzarPunts(competicio.getFases().get(numFase).getBatalles().get(competicio.getFases().get(numFase).getBatalles().size() - 1).calculaPunts(temes.get(0).getRimes().get(i).getVersos().get(0)));
                    }
                }
                competicio.getFases().get(numFase).getBatalles().get(competicio.getFases().get(numFase).getBatalles().size() - 1).getTema().add(temes.get(0));
            }
        }
    }

    /**
     * Realitza la batalla que es mostra per pantalla entre l'usuari i el seu rival.
     * @param rival Rapero amb qui s'enfrentara l'usuari
     * @param user Usuari que ha fet login
     * @param tipus Tipus de batalla
     */
    private void batallaPerPantalla(Rapero rival, Rapero user, String tipus, int numFase) {
        //decidim de manera aleatoria si l'usuari comença o no
        Random r = new Random();
        boolean first = r.nextBoolean();
        //Creem una batalla del tipus corresponent
        if (tipus.equals("Acapella")) {
            competicio.getFases().get(numFase).getBatalles().add(new BatallaAcapella());
        } else if (tipus.equals("A Sangre")) {
            competicio.getFases().get(numFase).getBatalles().add(new BatallaASangre());
        } else {
            competicio.getFases().get(numFase).getBatalles().add(new BatallaEscrita());
        }

        //2 batalles
        for (int j = 0; j < 2; j++) {
            //barrejem els temes per a que sigui random
            Collections.shuffle(temes);
            System.out.println("Topic: " + temes.get(0).getNom());
            if (j == 0) {
                System.out.println("A coin is tossed in the air and...");
            }

            if (first) {
                //mostra el vers del usuari i l que fa
                versUser(user, competicio.getFases().get(numFase).getBatalles().get(competicio.getFases().get(numFase).getBatalles().size() - 1));
                //mostra el vers del rival i l que fa
                versRival(rival, competicio.getFases().get(numFase).getBatalles().get(competicio.getFases().get(numFase).getBatalles().size() - 1));
            } else {
                versRival(rival, competicio.getFases().get(numFase).getBatalles().get(competicio.getFases().get(numFase).getBatalles().size() - 1));
                versUser(user, competicio.getFases().get(numFase).getBatalles().get(competicio.getFases().get(numFase).getBatalles().size() - 1));
            }
            competicio.getFases().get(numFase).getBatalles().get(competicio.getFases().get(numFase).getBatalles().size() - 1).getTema().add(temes.get(0));
        }
    }

    /**
     * Printa els versos del rival i calcula la seva puntuacio.
     * @param rival Rapero amb qui s'enfrentara l'usuari
     * @param batalla Batalla que esta passant
     */
    private void versRival(Rapero rival, Batalla batalla) {
        System.out.println(rival.getNomArtistic() + " your turn! Drop it!\n" +
                "\n" +
                rival.getNomArtistic() + ":\n");
        int nivell = rival.getNivell();
        boolean trobat = false;
        //array de totes les rimes del tema 0
        //Busquem una rima que sigui del nivell del rapero
        for (int i = 0; i < temes.get(0).getRimes().size(); i++) {
            if (nivell == temes.get(0).getRimes().get(i).getLevel()) {
                Collections.shuffle(temes.get(0).getRimes().get(i).getVersos());
                System.out.println(temes.get(0).getRimes().get(i).getVersos().get(0));
                rival.actualitzarPunts(batalla.calculaPunts(temes.get(0).getRimes().get(i).getVersos().get(0)));
                trobat = true;
                break;
            }
        }
        //si no trobem una rima del seu nivell, mostrem un missatge.
        if (!trobat) {
            System.out.println(rival.getNomArtistic() + " can't think of anything!");
        }
        System.out.println();
    }

    /**
     * Demana els versos a l'usuari i calcula la seva puntuacio. Els 3 primers versos introduis s'han d'acabar amb una coma,
     * i el ultim amb un punt. Hi ha 4 versos.
     * @param user Usuari que ha fet login
     * @param batalla Batalla que esta passant
     */
    private void versUser(Rapero user, Batalla batalla) {
        System.out.println("Your turn!\n" +
                "Enter your verse:");
        String estrofa = "";
        //cambiem el delimitador final del vers que es un \n
        Scanner punt = new Scanner(System.in).useDelimiter(".");
        //S'ha d'acabar cada vers amb una coma, i el ultim amb un punt. Hi ha 4 versos.
        for (int i = 0; i < 4; i++) {
            estrofa = estrofa.concat(punt.nextLine());
            if (i < 3) {
                //concatenem el "\n" als 3 primers versos, ja que el nextLine l'elimina
                estrofa = estrofa.concat("\n");
            } else {
                //concatenem el "." a l'ultim vers, ja que el nextLine l'elimina
                estrofa = estrofa.concat(".");
            }
        }
        //actualitzem els punts
        user.actualitzarPunts(batalla.calculaPunts(estrofa));
    }

    /**
     * Printa el ranquing ordenada de mes a menys puntuacio
     * @param ranking Llista ordenada dels raperos que participan a la batalla
     */
    private void ranquing(ArrayList<Rapero> ranking) {
        System.out.println("-------------------------------------\n" +
                "Pos. | Name | Score\n" +
                "------------------------------------\n\n");

        //printem els raperos 1 a 1
        for (int i = 0; i < ranking.size(); i++) {
            //si es tracta de l'usuari, printem una flecha indicant-ho.
            if (ranking.get(i).getNomArtistic().equals(user.getNomArtistic())) {
                System.out.println((i+1) + " " + ranking.get(i).getNomArtistic() + " - " + (int) ranking.get(i).getPuntuacio() + " <-- You");
            } else {
                System.out.println((i+1) + " " + ranking.get(i).getNomArtistic() + " - " + (int) ranking.get(i).getPuntuacio());
            }
        }
    }

    /**
     * Metode encara no creat
     */
    private void createProfile() {
        Rapero selected = new Rapero();
        boolean trobat = false;
        while (!trobat) {
            System.out.println("Enter the name of the rapper:");
            String name = reader.nextLine();
            for (int i = 0; i < competicio.getRaperos().size(); i++) {
                if (name.equals(competicio.getRaperos().get(i).getNomArtistic()) || name.equals(competicio.getRaperos().get(i).getNomComplert())) {
                    selected = competicio.getRaperos().get(i);
                    trobat = true;
                    break;
                }
            }
        }
        System.out.println("Getting information about their country of origin (" + selected.getPaisOrigen() + ")...");
        Pais pais = ApiPais.loadRestCountries(selected.getPaisOrigen().replace(" ", "%20"));

        System.out.println("Generating HTML file...");
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.generaHtml(selected, pais);

        System.out.println("Done! The profile will open in your default browser");
    }
}
