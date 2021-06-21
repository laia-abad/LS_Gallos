package Data;

import java.io.BufferedReader;
//import java.io.FileNotFoundException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Model.*;
//llibraria per poder lleguir json
import com.google.gson.stream.JsonReader;

/**
 * Classe encarregada d'importar les dades del JSON
 */
public class DataImport {
    //static pots accedir desde qualsevol clase
    //final sense poder canviarla

    private static final String FILENAME_BATALLES = "src/main/resources/batalles.json";
    private static final String FILENAME_COMPETICIO = "src/main/resources/competicio.json";

    /**
     * Constructor de la classe
     */
    public DataImport() {
    }

    /**
     * Carrega les dades del fitxer batalles.json, que conte un array de temes
     *
     * @return Array amb tots els temas
     */
    public static ArrayList<Tema> loadTemes() throws IOException {
        ArrayList<Tema> temes = new ArrayList<>();
        //carrageum el fitxer
        //URL resource = ClassLoader.getSystemClassLoader().getResource(FILENAME_BATALLES);
        try {
            BufferedReader resource = new BufferedReader(new FileReader(FILENAME_BATALLES));

            if ((resource.readLine()) == null) {
                System.out.println("Error! competicio.json no trobat.");
            } else {


                try {
                    JsonReader reader;
                    reader = new JsonReader(new FileReader(FILENAME_BATALLES));

                    reader.beginObject();
                    reader.nextName();
                    reader.beginArray();
                    while (reader.hasNext()) {
                        Tema aux = new Tema();
                        reader.beginObject();
                        reader.nextName();
                        aux.setNom(reader.nextString());
                        reader.nextName();
                        reader.beginArray();
                        reader.beginObject();
                        ArrayList<Rima> rimes = new ArrayList<>();
                        while (reader.hasNext()) {
                            Rima aux_rima = new Rima();
                            aux_rima.setLevel(Integer.parseInt(reader.nextName()));
                            reader.beginArray();
                            ArrayList<String> versos = new ArrayList<>();
                            while (reader.hasNext()) {
                                versos.add(reader.nextString());
                            }
                            reader.endArray();
                            aux_rima.setVersos(versos);
                            rimes.add(aux_rima);
                        }
                        aux.setRimes(rimes);
                        reader.endObject();
                        reader.endArray();
                        reader.endObject();
                        temes.add(aux);
                    }
                    reader.endArray();
                    reader.endObject();
                    reader.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }


                resource.close();

            }
            return temes;

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    /**
     * Carrega les dades de competicio.json que conte les dades de la competicio
     *
     * @return Tota la informacio de la competicio
     */
    public static Competicio loadCompeticio() throws IOException {
        Competicio competicio = new Competicio();
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");

        //lleguim el json
        // URL resource = ClassLoader.getSystemClassLoader().getResource(FILENAME_COMPETICIO);
        try {
            BufferedReader resource1 = new BufferedReader(new FileReader(FILENAME_COMPETICIO));


            JsonReader reader;

            try {
                reader = new JsonReader(new FileReader(FILENAME_COMPETICIO));
                //reader = new JsonReader(new FileReader(resource.getFile()));

                reader.beginObject();
                reader.nextName();
                reader.beginObject();
                reader.nextName();
                competicio.setNom(reader.nextString());
                reader.nextName();
                competicio.setDataInicial(parser.parse(reader.nextString()));
                reader.nextName();
                competicio.setDataFinal(parser.parse(reader.nextString()));
                reader.nextName();
                reader.beginArray();
                ArrayList<Fase> fases = new ArrayList<>();
                while (reader.hasNext()) {
                    reader.beginObject();
                    Fase aux_fase = new Fase();
                    reader.nextName();
                    aux_fase.setPressupost((float) reader.nextDouble());
                    reader.nextName();
                    aux_fase.setPais(reader.nextString());
                    reader.endObject();
                    fases.add(aux_fase);
                }
                competicio.setFases(fases);
                reader.endArray();
                reader.endObject();
                reader.nextName();
                reader.beginArray();
                ArrayList<Pais> paisos = new ArrayList<>();
                while (reader.hasNext()) {
                    Pais aux_pais = new Pais();
                    aux_pais.setNomAngles(reader.nextString());
                    paisos.add(aux_pais);
                }
                competicio.setPaisosAcceptats(paisos);
                reader.endArray();
                reader.nextName();
                reader.beginArray();
                ArrayList<Rapero> raperos = new ArrayList<>();
                while (reader.hasNext()) {
                    reader.beginObject();
                    Rapero aux_rapero = new Rapero();
                    reader.nextName();
                    aux_rapero.setNomComplert(reader.nextString());
                    reader.nextName();
                    aux_rapero.setNomArtistic(reader.nextString());
                    reader.nextName();
                    aux_rapero.setDataNaixement(parser.parse(reader.nextString()));
                    reader.nextName();
                    aux_rapero.setPaisOrigen(reader.nextString());
                    reader.nextName();
                    aux_rapero.setNivell(reader.nextInt());
                    reader.nextName();
                    aux_rapero.setUrlImatge(reader.nextString());
                    raperos.add(aux_rapero);
                    reader.endObject();

                }
                reader.endArray();
                reader.endObject();
                reader.close();

                competicio.setRaperos(raperos);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            resource1.close();
            return competicio;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
