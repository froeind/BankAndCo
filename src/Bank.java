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
    public int kundenkontoAnlegen(Person person) {
        int kundenlistennr = this.kunden.size() + 1;
        String kundennummer = String.format("%05d", kundenlistennr + 123_456L);
        Bankkunde bankkunde = new Bankkunde(this, person, kundennummer);
        this.kunden.add(bankkunde);
        return kundenlistennr;
    }
    public boolean giroAnlegen(int kundenlistennr, String produktNummer) {
        if (Bankprodukt.checkProduktNummer(produktNummer)) {
            if (this.addNummernkreis("Girokonto", produktNummer)) {
                // ein Kunde könnte mehrere Girokonten haben, das wird aber beim Bankkunden (noch) nicht unterstützt
                // aber ich fange es nicht ab, Girokonto würde also im Moment überschrieben
                Bankkunde bankkunde = this.kunden.get(kundenlistennr - 1);
                Girokonto girokonto = new Girokonto(this, bankkunde.getKundennummer(), produktNummer, 0);
                bankkunde.giroHinzufuegen(girokonto);
                return true;
            } else {
                System.out.printf("Die Girokontennummer %s ist schon vergeben!\n", produktNummer);
                return false;
            }
        } else {
            System.out.printf("Die Girokontennummer %s ist nicht valide!\n", produktNummer);
            return false;
        }
    }
    public boolean kreditAnlegen(int kundenlistennr, String produktNummer) {
        if (Bankprodukt.checkProduktNummer(produktNummer)) {
            if ( Kreditkartenpruefsystem.checkProduktNummer(produktNummer)) {
                if (this.addNummernkreis("Kreditkonto", produktNummer)) {
                    Bankkunde bankkunde = this.kunden.get(kundenlistennr - 1);
                    Kreditkarte kreditkarte = new Kreditkarte(this, bankkunde.getKundennummer(), produktNummer, 0);
                    bankkunde.kreditHinzufuegen(kreditkarte);
                    return true;
                } else {
                    System.out.printf("Die Kreditkartennummer %s ist schon vergeben!\n", produktNummer);
                    return false;
                }
            } else {
                System.out.printf("%s ist keine konsistente Kreditkartennummer!\n", produktNummer);
                return false;
            }
        } else {
            System.out.printf("Die Kreditkartennummer %s ist nicht valide!\n", produktNummer);
            return false;
        }
    }

    public boolean liquiditaetPruefen() {
        long bilanz = 0;
        for (Bankkunde kunde : kunden) {
            Girokonto girokonto = kunde.getGirokonto();
            if (girokonto != null) {
                bilanz += girokonto.getKontostand();
            }
            Kreditkarte kreditkarte = kunde.getKreditkarte();
            if (kreditkarte != null) {
                bilanz += kreditkarte.getKontostand();
            }
        }
        return (bilanz >= 0);
    }

}

/*
+ pruefeBankkundenID(int): boolean
+ filialeSuchen(String): Bankfiliale
+ toString(): String

 */

