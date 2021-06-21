package Data;

import Model.Competicio;
import Model.Rapero;
import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collections;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;

/**
 * Clase per rescriure al JSON
 */
public class DataWriterJson {
    //static pots accedir desde qualsevol clase
    //final sense poder canviarla
    private static final String FILENAME_COMPETICIO = "src/main/resources/competicio.json";

    /**
     * Reescriu el JSON amb el nou array de raperos a competicio.
     *
     * @param competicio Informacio de la competicio.
     */
    public static void introduirRapero(Competicio competicio) throws IOException {
        //carrageum el fitxer
       // URL resource = ClassLoader.getSystemClassLoader().getResource(FILENAME_COMPETICIO);
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");

        StringBuilder json = new StringBuilder();
        //carrageum el fitxer
        //URL resource = ClassLoader.getSystemClassLoader().getResource(FILENAME_BATALLES);

        BufferedReader resource = new BufferedReader(new FileReader(FILENAME_COMPETICIO));

        String linea = "";
        while ( (linea = resource.readLine()) != null  ){
            json.append(linea);

            JsonWriter writer;


            try {
                //lleguim el json
                writer = new JsonWriter(new FileWriter(FILENAME_COMPETICIO));
                //writer = new Json.createWriter();


                writer.beginObject();
                //escrius competition
                writer.name("competition");
                writer.beginObject();
                writer.name("name");
                //escribim el nom del rapero
                writer.value(competicio.getNom());
                writer.name("startDate");
                writer.value(parser.format(competicio.getDataInicial()));
                writer.name("endDate");
                writer.value(parser.format(competicio.getDataFinal()));
                writer.name("phases");
                writer.beginArray();
                for (int i = 0; i < competicio.getFases().size(); i++) {
                    writer.beginObject();
                    writer.name("budget");
                    writer.value(competicio.getFases().get(i).getPressupost());
                    writer.name("country");
                    writer.value(competicio.getFases().get(i).getPais());
                    writer.endObject();
                }
                writer.endArray();
                writer.endObject();
                writer.name("countries");
                writer.beginArray();
                for (int i = 0; i < competicio.getPaisosAcceptats().size(); i++) {
                    writer.value(competicio.getPaisosAcceptats().get(i).getNomAngles());
                }
                writer.endArray();
                writer.name("rappers");
                writer.beginArray();
                for (int i = 0; i < competicio.getRaperos().size(); i++) {
                    writer.beginObject();
                    writer.name("realName");
                    writer.value(competicio.getRaperos().get(i).getNomComplert());
                    writer.name("stageName");
                    writer.value(competicio.getRaperos().get(i).getNomArtistic());
                    writer.name("birth");
                    writer.value(parser.format(competicio.getRaperos().get(i).getDataNaixement()));
                    writer.name("nationality");
                    writer.value(competicio.getRaperos().get(i).getPaisOrigen());
                    writer.name("level");
                    writer.value(competicio.getRaperos().get(i).getNivell());
                    writer.name("photo");
                    writer.value(competicio.getRaperos().get(i).getUrlImatge());
                    writer.endObject();
                }
                writer.endArray();
                writer.endObject();
                writer.close();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        resource.close();

    }

    /**
     * Al introduir el guanyador reordena l'array del JSON per tal de que el guanyador sigui el primer.
     *
     * @param guanyador Guanyador de la competicio.
     */
   public static void reordenarRaperos(Rapero guanyador) throws IOException {
        Competicio competicio = DataImport.loadCompeticio();
        for (int i = 0; i < competicio.getRaperos().size(); i++) {
            if (competicio.getRaperos().get(i).getNomArtistic().equals(guanyador.getNomArtistic())) {
                //intercambien el posicio dels rapers
                Collections.swap(competicio.getRaperos(), i, 0);
                break;
            }
        }
        //realitzem el canvi de posicions
        introduirRapero(competicio);
   }


}