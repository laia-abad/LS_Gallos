package Data;

import Model.Pais;
import Model.Rapero;
import edu.salleurl.profile.Profile;
import edu.salleurl.profile.ProfileFactory;
import edu.salleurl.profile.Profileable;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class HtmlGenerator {

    public HtmlGenerator (){};

    public void generaHtml(Rapero rapero, Pais pais) {
        Profile profile = ProfileFactory.createProfile("HTML/" + toCamelCase(rapero.getNomArtistic()) + ".html");
        Profileable profileable = new Profileable() {
            @Override
            public String getName() {
                return rapero.getNomComplert();
            }

            @Override
            public String getNickname() {
                return rapero.getNomArtistic();
            }

            @Override
            public String getBirthdate() {
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                return dateFormat.format(rapero.getDataNaixement());
            }

            @Override
            public String getPictureUrl() {
                return rapero.getUrlImatge();
            }
        };
        profile.setProfileable(profileable);
        profile.addLanguage(pais.getIdiomesToString());
        profile.setCountry(pais.getNomAngles());
        profile.setFlagUrl(pais.getUrlBandera());
        try {
            profile.writeAndOpen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String toCamelCase(String str) {
        String [] parts = str.split(" ");
        String strFinal = parts[0].toLowerCase();
        for (int i = 1; i < parts.length; i++) {
            strFinal = strFinal + capitalize(parts[i]);
        }
        return strFinal;
    }

    public static String capitalize(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
