import java.io.IOException;

public class kt07JE1 {

    public static String transformiereIntArrayZuString(int[] ziffern){
        return transformiereIntArrayZuString(ziffern,0,ziffern.length);
    }
    public static String transformiereIntArrayZuString(int[] ziffern,int start, int ende){
        String ausgabe = "";
        for(int i =start; i<ende;i++){
            ausgabe += ziffern[i];
        }
        return ausgabe;
    }
    public static void main(String[] args) throws IOException {

        Datenverwaltung datenverwaltung = new Datenverwaltung();
        Benutzerinterface ui = new Benutzerinterface(datenverwaltung);
        ui.leseEingaben();
        ui.beendeKonsoleneingabe();

        /*
        Bank bank1 = new Bank("660908", "BBB");
        Person person1 = new Person("ingo", "werner");
        int kundennr1 = bank1.kundenkontoAnlegen(person1);
        String produktNummer = "523456";
        bank1.giroAnlegen(kundennr1, produktNummer);

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
        bank1.kreditAnlegen(kundennr1, produktNummer);
        produktNummer = Kreditkartenpruefsystem.createProduktNummer(16);
        bank1.kreditAnlegen(kundennr1, produktNummer);

        System.out.println();

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
*/
    }
}