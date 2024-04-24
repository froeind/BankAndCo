import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class Kreditkartenpruefsystem {
    private Kreditkartenpruefsystem() {
    }
    public static String createProduktNummer(int lengthProduktNummer) {
        Random random = new Random();
        String produktNummer;
        String tempProduktNummer;
        long countIt = 0;
        do {
            produktNummer = "";
            for ( int i = 1; i <= lengthProduktNummer; i++) {
                //for ( int i = 1; i <= lengthProduktNummer - 1; i++) {
                produktNummer += Integer.toString(random.nextInt(0, 10));
            }
            //tempProduktNummer = produktNummer + "0";
            //produktNummer += Integer.toString(berechnePruefziffer(tempProduktNummer));
            countIt++;
            //System.out.println(countIt + ": " + produktNummer);
        } while ( ! checkProduktNummer(produktNummer) );
        return produktNummer;
    }
    public static boolean checkProduktNummer(String produktNummer) {
        int pruefziffer = berechnePruefziffer(produktNummer);
        //System.out.println(pruefziffer);
        //System.out.println(Integer.valueOf(produktNummer.substring(produktNummer.length() - 1)));
        return ( pruefziffer == Integer.valueOf(produktNummer.substring(produktNummer.length() - 1)));
    }
    private static int[] handleProduktNummer(String produktNummer) {
        int[] produktNummerList = Arrays.stream(produktNummer.split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
        return produktNummerList;
    }
    public static int berechnePruefziffer(String produktNummer) {
        boolean logIt = false;
        // 1 Verdoppelung des Werts jeder zweiten Ziffer, beginnend mit der vorletzten Ziffer
        int[] produktNummerA = handleProduktNummer(produktNummer);
        if (logIt) { System.out.println(Arrays.toString(produktNummerA)); }
        for ( int i = 2; i < produktNummerA.length; i = i + 2 ) {
            produktNummerA[produktNummerA.length - i] *= 2;
        }
        if (logIt) { System.out.println(Arrays.toString(produktNummerA)); }
        // 2 Bildung der Quersummen aller Ergebnisse aus Schritt 1 und Addition dieser Quersummen
        long quersumme = Arrays.stream(produktNummerA)
                .mapToLong(i -> {
                    int sum = 0;
                    while (i > 0) {
                        sum += i % 10;
                        i /= 10;
                    }
                    return sum;
                })
                .sum();
        if (logIt) { System.out.println(quersumme); }
        // 3 Berechnung der Differenz zwischen dem Ergebnis aus Schritt 2 und der nächstkleineren durch 10 teilbaren Zahl
        int pruefziffer = (int) (quersumme % 10);
        if (logIt) { System.out.println(pruefziffer); }
        // 4 Berechnung der Differenz zwischen 10 und dem Ergebnis aus Schritt 3. Ergibt sich als Differenz 10, wird diese auf 0 gesetzt.
        pruefziffer = 10 - pruefziffer;
        return (pruefziffer == 10 ? 0 : pruefziffer);
    }
    public static boolean pruefeKreditkartennummer(String produktNummer) {
        return true;
    }
}