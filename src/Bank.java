import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Bank {

    private String blz;
    private String name;
    private LinkedList<Bankfiliale> filialen;
    private LinkedList<Bankautomat> automaten;
    private ArrayList<Bankkunde> kunden;
    private Set<String> nummernkreis;

    public Bank(String blz, String name) {
        this.blz = blz;
        this.name = name;
        this.nummernkreis = new HashSet<String>();
    }

    public String getBlz() {
        return this.blz;
    }
    public String getName() {
        return this.name;
    }

    public boolean addNummernkreis(String bankProdukt, String produktNummer) {
        char kreis = bankProdukt.charAt(0);
        if (nummernkreis.contains(kreis + produktNummer)) {
            System.out.printf("Für die Nummer %s ist schon ein %s eingerichtet!%n", produktNummer, bankProdukt);
            return false;
        } else {
            nummernkreis.add(kreis + produktNummer);
            return true;
        }
    }

}

/*
+ filialeAnlegen(String): int
+ kundenkontoAnlegen(Person): int
+ automatAnlegen(): int
+ automatAnlegen(Bankfiliale): int
+ giroAnlegen(int, int[]): Girokonto
+ pruefeBankkundenID(int): boolean
+ kreditAnlegen(int, int[]): Kreditkarte
+ filialeSuchen(String): Bankfiliale
+ liquiditaetPruefen(): boolean
+ toString(): String

 */

