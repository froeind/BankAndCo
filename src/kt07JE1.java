

public class kt07JE1 {

    public static void main(String[] args) {

        Bank bank1 = new Bank("660908", "BBB");
        Person person1 = new Person("ingo", "werner");
        Bankkunde bankkunde1 = new Bankkunde(bank1, person1);
        Girokonto girokonto1;
        String produktNummer = "523456";
        if (Bankprodukt.checkProduktNummer(produktNummer)) {
            if (bank1.addNummernkreis("Girokonto", produktNummer)) {
                girokonto1 = new Girokonto(bank1, produktNummer, 10000);
                bankkunde1.giroHinzufuegen(girokonto1);
            }
        }
        produktNummer = "523456";
        if (Bankprodukt.checkProduktNummer(produktNummer)) {
            bank1.addNummernkreis("Girokonto", produktNummer);
        }
        produktNummer = "X523456";
        if (Bankprodukt.checkProduktNummer(produktNummer)) {
            bank1.addNummernkreis("Girokonto", produktNummer);
        } else {
            System.out.printf("%s ist keine gültige Nummer!\n", produktNummer);
        }
        produktNummer = "6536698745212507";
        if ( Kreditkartenpruefsystem.checkProduktNummer(produktNummer)) {
            if (Bankprodukt.checkProduktNummer(produktNummer)) {
                if (bank1.addNummernkreis("Kreditkonto", produktNummer)) {
                    Kreditkarte kreditkarte1 = new Kreditkarte(bank1, produktNummer, 0);
                    bankkunde1.kreditHinzufuegen(kreditkarte1);
                }
            }
        } else {
            System.out.printf("%s ist keine gültige Kreditkartennummer!\n", produktNummer);
        }
        produktNummer = Kreditkartenpruefsystem.createProduktNummer(16);
        if (bank1.addNummernkreis("Kreditkonto", produktNummer)) {
            Kreditkarte kreditkarte1 = new Kreditkarte(bank1, produktNummer, 0);
            bankkunde1.kreditHinzufuegen(kreditkarte1);
            System.out.printf("%s ist eine gültige Kreditkartennummer!\n", produktNummer);
        }

        Bankautomat bankautomat1 = bank1.automatAnlegen("An der schönen blauen Donau 1", true);
        Bankfiliale filiale1 = bank1.filialeAnlegen("die Erste", "Am Grauen Rhein 13");
        Bankautomat bankautomat2 = bank1.automatAnlegen(filiale1);

        System.out.println("Bankautomat 1 ist in Filiale: " + bankautomat1.istInFiliale());
        System.out.println("Bankautomat 2 ist in Filiale: " + bankautomat2.istInFiliale());
        System.out.println(filiale1.toString());

        Bankfiliale filiale2 = bank1.filialeAnlegen("die Zweite", "Oberwaldstraße 12");
        Bankautomat bankautomat3 = bank1.automatAnlegen(filiale2);
        Bankautomat bankautomat4 = bank1.automatAnlegen(filiale2);
        Bankautomat bankautomat5 = bank1.automatAnlegen(filiale2);
        System.out.println(filiale2.toString());

    }
}