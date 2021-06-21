package Controller;

import Data.DataWriterJson;
import Model.Competicio;
import Model.Rapero;
import Model.Tema;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Classe que controla la informacio que el usuari introdueix. Mostra un menu inicial de registre usuari o login depenen
 * de les dates de la competicio.
 */
public class ControllerInici {

    private Competicio competicio;
    private ArrayList<Tema> temes;

    /**
     * Serveix per utilitzar la informacio que tenim guardada de les competicions, raperos i temes.
     *
     * @param competicio Competicio actual
     * @param temes      Array de temes que es podran utilitzar a la competicio
     */
    public ControllerInici(Competicio competicio, ArrayList<Tema> temes) {
        this.competicio = competicio;
        this.temes = temes;
    }

    /**
     * Mostrem per pantalla la informacio de la competicio i executem la condicioData
     * que serveix per  registrar un nou usuari o realitzar un login depenent de
     * les dates de la competicio.
     */
    public void menu() {
        if (competicio != null && temes != null) {

            boolean sortir = false;

            System.out.println("Welcome to competition: " + competicio.getNom() + "\n");

            //mostrem la data inicial de la competicio
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = dateFormat.format(competicio.getDataInicial());
            System.out.println("Starts on " + strDate);

            //mostrem la data final de la competicio
            String strDateFinal = dateFormat.format(competicio.getDataFinal());
            System.out.println("Ends on " + strDateFinal);

            //mostrem el numero de fases de la competicio
            System.out.println("Phases: " + competicio.getFases().size());

            //mostrem el numero de participants
            System.out.println("Currently: " + competicio.getRaperos().size() + " participants");

            System.out.println("\n--------------------------------------------------\n");

            //repetim la funcio a no ser que l'usuari presioni la opcio 2.
            while (!sortir) {
                sortir = condicionData(strDate, strDateFinal);
            }
        }
    }

    /**
     * Demana i guarda la informacio de un rapero nou,
     * i en cas d'algun error torna a demanar la informacio.
     *
     * @throws ParseException indica que hi ha hagut un error fent el parsejant la data
     */
    private void raperoRegistre() throws ParseException, IOException {
        Scanner scan = new Scanner(System.in);
        boolean sortirName = false;
        boolean sortirCountry = false;
        boolean sortirBirth = false;
        Rapero registre = new Rapero();

        boolean res;

        System.out.println("\n--------------------------------------------------\n");
        System.out.println("Please, enter your personal information:");
        System.out.println("- Full name:");
        //Guardem la informacio del nom complert
        registre.setNomComplert(scan.nextLine());

        //bucle per tornar a demanar el nom artistic.
        while (!sortirName) {
            boolean error = false;
            System.out.println("- Artistic name:");
            //Guardem la informacio del nom artistic
            String name = scan.nextLine();

            //fem un bucle per revisar cada nom artistic que tenim guardat
            for (int i = 0; i < competicio.getRaperos().size(); i++) {

                //si un dels noms que tenim guardats coincideix amb el introduit mostrem un missatge d'error
                if (competicio.getRaperos().get(i).getNomArtistic().equals(name)) {
                    System.out.println("\nThis name is already in use\n");
                    //acabem el bucle
                    i = competicio.getRaperos().size();
                    error = true;
                }
            }
            //si no hi a cap error guardem la informacio i sortim del bucle que torna a demanar el nom artistic.
            if (!error) {
                sortirName = true;
                registre.setNomArtistic(name);
            }
        }

        //bucle per tornar a demanar la data de naixement.
        while (!sortirBirth) {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy ");
            LocalDateTime now = LocalDateTime.now();
            //Guardem la data d'avui
            String nowToday = dtf.format(now);

            System.out.println("- Birth date (dd/MM/YYYY):");
            //Guardem la data del naixement
            String dateString = scan.nextLine();

            //creem variables en format de data
            Date dateStringStart;
            Date dateNowToday;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            //passem la informacio de string a data
            dateStringStart = sdf.parse(dateString);
            dateNowToday = sdf.parse(nowToday);

            //retorna un boolea de si la data es valida o no
            res = validarFecha(dateString);

            //si la data existeix i es anterior al dia d'avui
            if (res && (dateNowToday.after(dateStringStart))) {
                //sortim del bucle
                sortirBirth = true;
                registre.setDataNaixement(sdf.parse(dateString));

            } else {
                //mostrem missatge d'error
                System.out.println("\nThis date is not correct\n");
            }

        }

        //bucle per tornar a demanar del country
        while (!sortirCountry) {
            System.out.println("- Country:");
            //Guardem el country
            String country = scan.nextLine();

            //fem un bucle per revisar cada country que tenim guardat
            for (int i = 0; i < competicio.getRaperos().size(); i++) {
                //si el country introduit es igual que algun que tenim guardat
                if (competicio.getRaperos().get(i).getPaisOrigen().equals(country)) {
                    //sortim del bucle
                    sortirCountry = true;
                    break;
                }
            }
            //si el country coincideix el guardem
            if (sortirCountry) {
                registre.setPaisOrigen(country);
            } else {
                //mostrem missatge d'error
                System.out.println("\nThis country does not exist\n");
            }
        }

        System.out.println("- Level:");
        //guardem el nivell
        registre.setNivell(scan.nextInt());

        //ens saltem el /n
        scan.nextLine();

        System.out.println("- Photo URL:");
        //guardem el url de la imatge
        registre.setUrlImatge(scan.nextLine());


        competicio.getRaperos().add(registre);
        try {
            DataWriterJson.introduirRapero(competicio);
            System.out.println("Registration completed!\n"
                    + "--------------------------------------------------\n");
        } catch (Exception e){
            System.out.println("There was an error in the registration");
        }



    }

    /**
     * Mira si la data introduida existeix o no.
     *
     * @param dateString data que ha introduit l'usuari com a data de naixement.
     * @return true si la data existeix, false si no.
     */
    private static boolean validarFecha(String dateString) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            formatoFecha.setLenient(false);
            formatoFecha.parse(dateString);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Compara el nomArtistic introduit per l'usuari amb els que tenim ja registrats,
     * en cas que sigui correcte guardem tota la informacio d'aquell rapero,
     * sino mostrem un error i tornem a demanar el nomArtistic.
     * @return usuari que fa el login
     */
    private Rapero raperoRegistreConegut() {
        boolean sortirName = false;
        Scanner scan = new Scanner(System.in);
        Rapero user = new Rapero();
        System.out.println("\n--------------------------------------------------\n");

        //bucle per tornar a demanar el nom artistic
        while (!sortirName) {
            System.out.println("Enter your artistic name:");
            //guardem el nom artistic
            String name = scan.nextLine();

            //bucle per revisar tots els noms artistics que tenim guardats
            for (int i = 0; i < competicio.getRaperos().size(); i++) {

                //si el nom artistic introduit coincideix amb el que esta guardat
                if (competicio.getRaperos().get(i).getNomArtistic().equals(name)) {
                    //sortim del bucle
                    sortirName = true;
                    //guardem tota la informacio del rapero
                    user = competicio.getRaperos().get(i);

                }
            }
            if (!sortirName) {
                //missatge d'error
                System.out.println("\nYo' bro, there's no " + name + " in ma' list.\n");
            }
        }

        return user;
    }

    /**
     * Depenent de les dades de la competicio mostrem un menu de registre rapero o de login.
     *
     * @param strDate      data inicial de la competicio.
     * @param strDateFinal data final de la competicio.
     * @return true si s'acaba el programa.
     */
    private boolean condicionData(String strDate, String strDateFinal) {
        Scanner scan = new Scanner(System.in);
        boolean sortir = false;

        //guardem la data actual
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy ");
        LocalDateTime now = LocalDateTime.now();
        String nowToday = dtf.format(now);

        //declarem les variables en format date
        Date dateCompStart;
        Date dateCompFinal;
        Date dateToday;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            //traspasem les dates de string a date
            dateCompStart = sdf.parse(strDate);
            dateCompFinal = sdf.parse(strDateFinal);
            dateToday = sdf.parse(nowToday);

            //si la compaticio no ha començat
            if ((dateCompStart.getTime() > dateToday.getTime())) {
                System.out.println("Competition hasn't started yet. Do you want to:");
                String num;

                System.out.println("\n1. Register\n" + "2. Leave\n");
                System.out.println("Choose an option:");
                num = scan.nextLine();

                switch (num) {
                    case "1":
                        //fem la funcio de registrar el rapero
                        raperoRegistre();
                        break;
                    case "2":
                        //sortir del programa
                        System.out.println("Good Bye!");
                        sortir = true;
                        break;

                    default:
                        //missatge d'error
                        System.out.println("The selected option doesn't exist.");
                }

            } else {
                //la competicio ha començat i encara no ha acabat
                if (dateCompStart.getTime() <= dateToday.getTime() && (dateCompFinal.getTime() > dateToday.getTime())) {
                    System.out.println("Competition started. Do you want to:");
                    String num;
                    System.out.println("\n1. Login\n" + "2. Leave\n");
                    System.out.println("Choose an option:");
                    num = scan.nextLine();


                    switch (num) {
                        case "1":
                            //fem la funcio de un rapero que ja coneixem
                            ControllerCompeticio cc = new ControllerCompeticio(temes, competicio, raperoRegistreConegut());
                            //anem al lobby
                            cc.lobby();
                            sortir = true;
                            break;
                        case "2":
                            //sortir del programa
                            System.out.println("Good Bye!");
                            sortir = true;
                            break;

                        default:
                            //missatge d'error
                            System.out.println("The selected option doesn't exist.");

                    }

                }
                //si ja ha acabat la competicio
                if ((dateCompFinal.getTime() < dateToday.getTime()) || (dateCompFinal.getTime() == dateToday.getTime())) {
                    //mostrem al guanyador
                    System.out.println("Competition ended. " + competicio.getRaperos().get(0).getNomArtistic() + " is the winner. Do you want to:");
                    String num;
                    System.out.println("\n1. Leave");
                    System.out.println("Choose an option:");
                    num = scan.nextLine();

                    if ("1".equals(num)) {
                        System.out.println("Good Bye!");
                        sortir = true;
                    } else {
                        System.out.println("The selected option doesn't exist.");
                    }
                }
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return sortir;

    }

}
