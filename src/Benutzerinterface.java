import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Benutzerinterface {

    private Scanner scanner;
    private List<String> codewoerterErstePosition;
    private List<String> codewoerterNeu;
    private List<String> codewoerterAusgabe;
    private Datenverwaltung datenverwaltung;

    public Benutzerinterface(Datenverwaltung datenverwaltung){
        this.scanner = new Scanner(System.in);

        this.codewoerterErstePosition = new ArrayList<>();
        this.codewoerterErstePosition.add("neu");
        this.codewoerterErstePosition.add("buchen");
        this.codewoerterErstePosition.add("ausgabe");
        this.codewoerterErstePosition.add("beenden");

        this.codewoerterAusgabe = new ArrayList<>();
        this.codewoerterAusgabe.add("bank");
        this.codewoerterAusgabe.add("person");
        this.codewoerterAusgabe.add("uebersicht");

        this.codewoerterNeu = new ArrayList<>(6);
        this.codewoerterNeu.add("bank");
        this.codewoerterNeu.add("filiale");
        this.codewoerterNeu.add("automat");
        this.codewoerterNeu.add("person");
        this.codewoerterNeu.add("konto");
        this.codewoerterNeu.add("produkt");

        this.datenverwaltung = datenverwaltung;
    }

    public String analysiereEingabe(String eingabe) {
        List<String> eingabeZerlegt = new LinkedList<>(Arrays.asList(eingabe.split(" ")));
        String ausgabe = "";
        switch (eingabeZerlegt.get(0)){
            case "beenden":
                ausgabe = "beenden";
                break;
            case "neu":
                eingabeZerlegt.remove(0);
                ausgabe = this.analysiereEingabeNeu(eingabeZerlegt);
                break;
            case "buchen":
                eingabeZerlegt.remove(0);
                ausgabe = this.analysiereEingabeBuchen(eingabeZerlegt);
                break;
            case "ausgabe":
                eingabeZerlegt.remove(0);
                ausgabe = this.analysiereEingabeAusgabe(eingabeZerlegt);
                break;
        }
        return ausgabe;
    }
    private String analysiereEingabeNeu(List<String> teileingabe) {
        String ausgabe="";
        switch(teileingabe.get(0)){
            case "bank":
                ausgabe = this.datenverwaltung.bankAnlegen(teileingabe.get(1));
                break;
            case "filiale":
                ausgabe = this.datenverwaltung.bankfilialeAnlegen(teileingabe.get(1), teileingabe.get(2));
                break;
            case "automat":
                if(teileingabe.size()==2){
                    ausgabe = this.datenverwaltung.bankautomatAnlegen(teileingabe.get(1));
                }else if(teileingabe.size()==3){
                    ausgabe = this.datenverwaltung.bankautomatAnlegen(teileingabe.get(1),teileingabe.get(2));
                }
                break;
            case "person":
                ausgabe = this.datenverwaltung.personAnlegen(teileingabe.get(1));
                break;
            case "konto":
                int personenID = Integer.parseInt(teileingabe.get(1));
                ausgabe = this.datenverwaltung.kontoAnlegen(personenID, teileingabe.get(2));
                break;
            case "produkt":
                int kontoID = Integer.parseInt(teileingabe.get(2));
                ausgabe = this.datenverwaltung.produktAnlegen(teileingabe.get(1), kontoID, teileingabe.get(3));
                break;
        }
        return ausgabe;
    }
    private String analysiereEingabeAusgabe(List<String> teileingabe) {
        String ausgabe="";
        switch(teileingabe.get(0)){
            case "uebersicht":
                ausgabe = this.datenverwaltung.allginfoAusgeben();
                break;
            case "bank":
                ausgabe = this.datenverwaltung.bankinfoAusgeben(teileingabe.get(1));
                break;
            case "person":
                int personenID = Integer.parseInt(teileingabe.get(1));
                ausgabe = this.datenverwaltung.personeninfoAusgeben(personenID);
        }
        return ausgabe;
    }

    private String analysiereEingabeBuchen(List<String> teileingabe) {
        // NOTIZ: Diese Methode wird später implementiert.
        // Hier sollen Konsoleneingaben der folgenden Form verarbeitet werden:
        //    buchen >produktnummer< >betrag<
        // Dabei ist buchen ein Codewort, das genau so in die Konsole eingegeben
        // werden muss, >produktnummer< ist ein Platzhalter, an dessen Stelle
        // entweder eine 9-stellige IBAN oder eine 16-stellige Kreditkartennummer
        // eingegeben werden.
        // Der Parameter 'teileingabe' dieser Methode ist eine Liste
        // der einzelnen Eingabe als String, getrennt an Leerzeichen.
        // Dabei wurde 'buchen' bereits entfernt.
        return "";
    }

    public void leseEingaben(){
        String eingabe;
        String ausgabe;
        while( true ){
            System.out.print(">");
            eingabe = this.scanner.nextLine();
            ausgabe = analysiereEingabe(eingabe);
            if(ausgabe.equals("beenden")){
                System.out.println("Das Programm wird nun beendet");
                break;
            }else{
                System.out.println(ausgabe);
            }
        }
    }

    public void beendeKonsoleneingabe(){
        if(this.scanner != null ){
            this.scanner.close();
        }
    }
    public void initialisiereKonsoleeingabeNeu(){
        this.scanner = new Scanner(System.in);
    }
/*
    protected void finalize() throws Throwable{
        beendeKonsoleneingabe();
    }
 */
}