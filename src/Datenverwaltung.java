import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collection;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.HashMap;
import java.util.NoSuchElementException;
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

    public String personeninfoAusgeben(int id) throws NoSuchElementException {
        if (id < 0 || id > this.personen.size()) {
            throw new NoSuchElementException("Ungültige Personen-ID.");
        } else {
        Person person = this.personen.get(id);
        return "" + person;
        }
    }

    public String bankAnlegen(String name) {
        String ausgabe="";
        String blz = this.erzeugeNeueBLZ();
        Bank bank = new Bank(name,blz);
        this.banken.put(name, bank);
        ausgabe = "Bank " + name + " wurde angelegt.\nBLZ: " + blz;
        return ausgabe;
    }

    public String bankfilialeAnlegen(String bankname, String filialname) {
        Bank bank = this.banken.get(bankname);
        int filialID = bank.filialeAnlegen(filialname, "");
        return "Die Filiale "+filialname+" der Bank "+bankname+" ist angelegt.\nID: "+filialID;
    }

    public String bankautomatAnlegen(String bankname, String filialname){
        Bank bank = banken.get("bankname");
        Bankfiliale filiale = bank.getFiliale(filialname);
        Bankautomat bankautomat = bank.automatAnlegen(filiale);
        return bankautomat.getAdresse();
    }

    public String bankautomatAnlegen(String bankname) {
        Bank bank = banken.get("bankname");
        Bankautomat bankautomat = bank.automatAnlegen(bankname, true);
        return bankautomat.getAdresse();
    }

    public String personAnlegen(String vorname, String nachname){
        Person person = new Person(vorname, nachname);
        int personenID = this.personen.size();
        this.personen.add(person);
        return "Neue Person mit dem Namen " + vorname + " " + nachname + " ist angelegt.\nID: "+personenID;
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
        if (produkttyp.equals("k")) {
            String produktID = this.generiereNeueKreditkartennummer();
            Kreditkarte kreditkarte = bank.kreditAnlegen(kontoId,produktID);
            this.alleKreditkarten.put(produktIDString, kreditkarte);
            ausgabe = "Kreditkartenkonto angelegt.\nKreditkartennummer: ";
        } else if(produkttyp.equals("g")) {
            String produktID = this.generiereNeueIban(bank.getBlz());
            Girokonto girokonto = bank.giroAnlegen(kontoId,produktID);
            this.alleGirokonten.put(produktID, girokonto);
            ausgabe = "Girokonto angelegt.\nIBAN: ";
        }
        ausgabe += produktIDString;
        return ausgabe;
    }

    public long buchen(String produktNummer, int betrag) throws IllegalArgumentException, NoSuchElementException {
        if (produktNummer.length() == 9) {
            if (this.alleGirokonten.containsKey(produktNummer)) {
                this.alleGirokonten.get(produktNummer).buchen(betrag);
                return this.alleGirokonten.get(produktNummer).getKontostand();
            } else {
                throw new NoSuchElementException("Es gibt kein Girokonto mit dieser Nummer.");
            }
    } else if (produktNummer.length() == 16) {
            if (this.alleKreditkarten.containsKey(produktNummer)) {
                this.alleKreditkarten.get(produktNummer).buchen(betrag);
                return this.alleKreditkarten.get(produktNummer).getKontostand();
            } else {
                throw new NoSuchElementException("Es gibt keine Kreditkarte mit dieser Nummer.");
            }
        } else {
                   throw new IllegalArgumentException("Ungültige Nummer eingegeben: Kann weder Kreditkarten noch Girokonten zugeordnet werden.");
                }
    }

    public String bankinfoAusgeben(String bankname) {
        Bank bank = this.banken.get(bankname);
        String ausgabe = "" + bank + "\n";
        ausgabe += "Liquidität: " + (BaFin.INSTANCE.liquiditaetPruefen(bank) ? "Bank ist liquide" : "Bank ist nicht liquide");
        return ausgabe;
    }

    public String allginfoAusgeben(){
        String info = "";
        for (Bank bank : this.banken.values()) {
            info += bank.getName() + "\n";
            for (Bankkunde kunde : bank.getKunden()) {
                info += "   " + kunde.getKundennummer() + " " + kunde.getPerson().getName();
            }
        }
        return info;
    }

    private String erzeugeNeueBLZ(){
        int anzahlStellen = 3;
        String blz = "" + this.banken.size();
        while(blz.length()<anzahlStellen)
            blz = "0" + blz;
        return blz;
    }

    private String generiereNeueIban(String blz) {
        List<String> aktuelleGiros= this.generiereListeGirokontenStrings(blz);
        String neueGiro = this.generiereNeueZahlenfolge(aktuelleGiros,6);
        String neueIban = blz + neueGiro;
        return neueIban;
    }

    private String generiereNeueKreditkartennummer(){
        String neueKreditkartennummer = "";
        neueKreditkartennummer = this.generiereNeueZahlenfolge(new LinkedList<>(this.alleKreditkarten.keySet()),16);
        neueKreditkartennummer += Kreditkartenpruefsystem.berechnePruefziffer(neueKreditkartennummer);
        return neueKreditkartennummer;
    }

    private String generiereNeueZahlenfolge(List<String> aktuelleZahlenfolgen,int anzahlStellen) {
        int[] neueZahlenfolge = new int[anzahlStellen];
        String neueZahlenfolgeString;
        while(true){
            for(int i=0; i<anzahlStellen; i++){
                int zufallszahl = ThreadLocalRandom.current().nextInt(9 + 1);
                neueZahlenfolge[i] = zufallszahl;
            }
            neueZahlenfolgeString = kt07JE1.transformiereIntArrayZuString(neueZahlenfolge);
            if( aktuelleZahlenfolgen.contains(neueZahlenfolgeString)==false ){
                return neueZahlenfolgeString;
            }
        }
    }

    private List<String> generiereListeGirokontenStrings(String blz){
        List<String> alleIbans = new LinkedList<>(this.alleGirokonten.keySet());
        ListIterator<String> ibanIterator = alleIbans.listIterator();
        List<String> girokontenDerBank = new LinkedList<String>();
        String ibanString;
        while(ibanIterator.hasNext()){
            ibanString = ibanIterator.next();
            if(blz.equals(ibanString.substring(0, 3))){
                girokontenDerBank.add(ibanString.substring(3));
            }
        }
        return girokontenDerBank;
    }

}
