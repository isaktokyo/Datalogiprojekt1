import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Her henter vi datoerne fra databasen.
        String dbPath = "identifier.sqlite"; 
        // Hvor databasen ligger

        List<String> datoer = IndlaesData.getSunSignFromDB(dbPath);
        // Indhenter fødsdatoerne fra databasen og gemmer dem i en liste

        // Laver datoer om til stjernetegn
        List<String> signs = new ArrayList<>();
        // Tom liste som skal fyldes med stjernetegn

        for (String dato : datoer) { // Gennemgår datoerne en for en
            String sign = TilStjernetegn1.findStjernetegn(dato);
            // her findes hvilket stjernetegn datoen tilhører
            signs.add(sign); // Lægger stjernetegnet ind i listen
        }

        // Bruger Apriori til at regne på stjernetegnene
        double minSupport = 0.20;
        // Lige nu kræver vi at et stjernetegn fylder mindst 20%
        Apriori ap = new Apriori(minSupport); // laver en apriori med vores minsupport

        Map<String, Integer> counts = ap.findFrequentItems(signs); 
        // tæller hvor mange gange hvert stjernetegn optræder

        Map<String, Double> support = ap.computeSupport(counts, signs.size()); 
        // regner hvor stor en procentdel hvert stjernetegn fylder af alle

        Map<String, Double> filtered = ap.filterByMinSupport(support); 
        // filtrerer de stjernetegn som er store nok (lige nu 20% - minsupport)

        // Print resultater
        System.out.println("=== RÅ FREKVENS (count) ===");
        System.out.println(counts); // viser hvor mange gange hvert tegn har optrådt

        System.out.println("\n=== SUPPORT FOR ALLE ===");
        for (Map.Entry<String, Double> e : support.entrySet()) {
            // går igennem alle stjernetegn og deres supportværdi
            System.out.printf("%s : %.3f%n", e.getKey(), e.getValue());
        }

        System.out.println("\n=== OVER minSupport (" + minSupport + ") ===");
        for (Map.Entry<String, Double> e : filtered.entrySet()) {
            // går igennem stjernetegn som opfylder mindstekrav
            System.out.printf("%s : %.3f%n", e.getKey(), e.getValue());
            //%s udskriver en String (fisk eks), %.3f udksirver et floating point tal med 3 decimaler, %n ny linje
        }
    }
}
