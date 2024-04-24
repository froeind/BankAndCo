import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Bank {

    private String blz;
    private String name;
    private ArrayList<Bankfiliale> filialen;
    private ArrayList<Bankautomat> automaten;
    private ArrayList<Bankkunde> kunden;
    private Set<String> nummernkreis;

    public Bank(String blz, String name) {
        this.blz = blz;
        this.name = name;
        this.filialen = new ArrayList<>();
        this.automaten = new ArrayList<>();
        this.kunden = new ArrayList<>();
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

    public Bankautomat automatAnlegen(String adresse, boolean fromExternal) {
        Bankautomat bankautomat = new Bankautomat(adresse);
        automaten.add(bankautomat);
        int automatId = bankautomat.getAutomatId();
        if (fromExternal) {
            System.out.printf("Automat %d in '%s' aufgestellt!\n", automatId, adresse);
        }
        this.addNummernkreis("Automat", String.format("%05d", automatId));
        return bankautomat;
    }
    public Bankautomat automatAnlegen(Bankfiliale filiale) {
        Bankautomat bankautomat = this.automatAnlegen(filiale.getAdresse(), false);
        filiale.bankautomatHinzufuegen(bankautomat);
        System.out.printf("Automat %d in Filiale '%s' als Automat %d aufgestellt!\n", bankautomat.getAutomatId(), filiale.getAdresse(), filiale.lastBankautomat());
        return bankautomat;
    }
    public Bankfiliale filialeAnlegen(String name, String adresse) {
        Bankfiliale filiale = new Bankfiliale(name, adresse, this);
        this.filialen.add(filiale);
        System.out.printf("Filiale '%s' eröffnet!\n", adresse);
        return filiale;
    }

}

/*
+ kundenkontoAnlegen(Person): int
+ giroAnlegen(int, int[]): Girokonto
+ pruefeBankkundenID(int): boolean
+ kreditAnlegen(int, int[]): Kreditkarte
+ filialeSuchen(String): Bankfiliale
+ liquiditaetPruefen(): boolean
+ toString(): String

 */

