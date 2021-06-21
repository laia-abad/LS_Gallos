package Data;

import Model.Idioma;
import Model.Pais;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiPais {

    public static Pais loadRestCountries(String nomPais) {
        Pais pais = new Pais();

        String apiurl = "https://restcountries.eu/rest/v2/name/" + nomPais;

        URL url;
        try {
            url = new URL(apiurl);
            JsonReader reader;

            try {
                reader = new JsonReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

                reader.beginArray();
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    switch (name) {
                        case "name":
                            pais.setNomAngles(reader.nextString());
                            break;
                        case "population":
                            pais.setNomHabitants(reader.nextInt());
                            break;
                        case "region":
                            pais.setRegio(reader.nextString());
                            break;
                        case "languages":
                            reader.beginArray();
                            while (reader.hasNext()) {
                                Idioma auxIdioma = new Idioma();
                                reader.beginObject();
                                while (reader.hasNext()) {
                                    String label = reader.nextName();
                                    switch (label) {
                                        case "name":
                                            auxIdioma.setIdiomaAngles(reader.nextString());
                                            break;
                                        case "nativeName":
                                            auxIdioma.setIdiomaNatiu(reader.nextString());
                                            break;
                                        default:
                                            reader.skipValue();
                                    }
                                }
                                reader.endObject();
                                pais.getIdiomes().add(auxIdioma);
                            }
                            reader.endArray();
                            break;
                        case "flag":
                            pais.setUrlBandera(reader.nextString());
                            break;
                        default:
                            reader.skipValue();
                    }
                }

                reader.endObject();
                reader.endArray();
                reader.close();
                return pais;

            } catch (IOException e) {
                System.out.println("Error, codi de parada no vàlid!\n");
                return null; //retornem true perque hi ha hagut un error
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
