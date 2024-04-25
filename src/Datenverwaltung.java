import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collection;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Datenverwaltung {

    private HashMap<String, Bank> banken;
    private HashMap<String, Girokonto> alleGirokonten;
    private HashMap<String, Kreditkarte> alleKreditkarten;
    private List<Person> personen;

    public Datenverwaltung(){
        this.banken = new HashMap<String,Bank>();
        this.personen = new ArrayList<>();
        this.alleGirokonten = new HashMap<String, Girokonto>();
        this.alleKreditkarten = new HashMap<String, Kreditkarte>();
    }

    public String bankAnlegen(String name) {
        String ausgabe="";
        int[] blz =erzeugeNeueBLZ();
        Bank bank = new Bank(name,blz);
        this.banken.put(name, bank);
        ausgabe = "Bank " + name + " wurde angelegt.\nBLZ: "+ Main.transformiereIntArrayZuString(blz);
        return ausgabe;
    }

    public String bankfilialeAnlegen(String bankname, String filialname) {
        Bank bank = this.banken.get(bankname);
        int filialID = bank.filialeAnlegen(filialname);

        return "Die Filiale "+filialname+" der Bank "+bankname+" ist angelegt.\nID: "+filialID;
    }

    public String bankautomatAnlegen(String bankname, String filialname){
        // AUFGABE: Diese Methode ist zu implementieren
    }

    public String bankautomatAnlegen(String bankname) {
        // AUFGABE: Diese Methode ist zu implementieren
    }

    public String personAnlegen(String name){
        Person person = new Person(name);
        int personenID = this.personen.size();
        this.personen.add(person);
        return "Neue Person mit dem Namen "+name+" ist angelegt.\nID: "+personenID;
    }

    public String kontoAnlegen(int id, String bankname) {
        Bank bank = this.banken.get(bankname);
        Person person =this.personen.get(id);

        int kontoID = bank.kundenkontoAnlegen(person);
        return "Konto angelegt.\nID: "+kontoID;
    }

    public String produktAnlegen(String bankname,int kontoId, String produkttyp){
        Bank bank = this.banken.get(bankname);
        String produktIDString="";
        String ausgabe="";
        if (produkttyp.equals("Kreditkarte")){
            int[] produktID = generiereNeueKreditkartennummer();
            produktIDString = Main.transformiereIntArrayZuString(produktID);
            Kreditkarte kreditkarte =bank.kreditAnlegen(kontoId,produktID);
            this.alleKreditkarten.put(produktIDString, kreditkarte);
            ausgabe = "Kreditkartenkonto angelegt.\nKreditkartennummer: ";
        }else if(produkttyp.equals("Girokonto")){
            int[] produktID = generiereNeueIban(bank.getBLZ());
            Girokonto girokonto = bank.giroAnlegen(kontoId,produktID);
            produktIDString = Main.transformiereIntArrayZuString(produktID);
            this.alleGirokonten.put(produktIDString, girokonto);
            ausgabe = "Girokonto angelegt.\nIBAN: ";
        }
        ausgabe += produktIDString;
        return ausgabe;
    }

    public String buchen(String produktNummer,int betrag) {
        // AUFGABE: Diese Methode ist zu implementieren
    }

    public String bankinfoAusgeben(String bankname) {
        Bank bank = this.banken.get(bankname);
        String ausgabe = "" + bank + "\n";
        ausgabe += "Liquidität: " + (BaFin.INSTANCE.liquiditaetPruefen(bank) ? "Bank ist liquide" : "Bank ist nicht liquide");
        return ausgabe;
    }

    public String personeninfoAusgeben(int id) {
        // AUFGABE: Diese Methode ist zu implementieren
    }

    public String allginfoAusgeben(){
        // AUFGABE: Diese Methode ist zu implementieren
    }

    private int[] erzeugeNeueBLZ(){
        int anzahlStellen = 3;
        String blzString = ""+ this.banken.size();
        int[] blz=null;
        while(blzString.length()<anzahlStellen)
            blzString = "0" + blzString;
        blz = Main.transformiereStringeingabeZuIntArray(blzString);
        return blz;
    }

    private int[] generiereNeueIban(int[] blz) {
        List<String> aktuelleGiros= generiereListeGirokontenStrings(blz);
        int[] neueGiro=null;
        neueGiro = generiereNeueZahlenfolge(aktuelleGiros,6);

        int[] neueIban = new int[9];
        for(int i=0; i<3;++i){
            neueIban[i] = blz[i];
        }
        for(int i=0; i<6;++i){
            neueIban[i+3] = neueGiro[i];
        }
        return neueIban;
    }

    private int[] generiereNeueKreditkartennummer(){
        int[] neueKreditkartennummer=null;
        neueKreditkartennummer = generiereNeueZahlenfolge(new LinkedList<>(this.alleKreditkarten.keySet()),16);
        neueKreditkartennummer[15] = Kreditkartenprüfsystem.berechnePrüfziffer(neueKreditkartennummer);
        return neueKreditkartennummer;
    }

    private int[] generiereNeueZahlenfolge(List<String> aktuelleZahlenfolgen,int anzahlStellen) {
        int[] neueZahlenfolge = new int[anzahlStellen];
        String neueZahlenfolgeString;
        while(true){
            for(int i=0; i<anzahlStellen; i++){
                int zufallszahl = ThreadLocalRandom.current().nextInt(9 + 1);
                neueZahlenfolge[i] = zufallszahl;
            }
            neueZahlenfolgeString = Main.transformiereIntArrayZuString(neueZahlenfolge);
            if( aktuelleZahlenfolgen.contains(neueZahlenfolgeString)==false ){
                return neueZahlenfolge;
            }
        }
    }

    private List<String> generiereListeGirokontenStrings(int[] blz){
        List<String> alleIbans = new LinkedList<>(this.alleGirokonten.keySet());
        ListIterator<String> ibanIterator = alleIbans.listIterator();
        List<String> girokontenDerBank = new LinkedList<String>();
        String ibanString;
        String blzString = Main.transformiereIntArrayZuString(blz);
        while(ibanIterator.hasNext()){
            ibanString = ibanIterator.next();
            if(blzString.equals(ibanString.substring(0, 3))){
                girokontenDerBank.add(ibanString.substring(3));
            }
        }
        return girokontenDerBank;
    }

}
