import java.io.IOException;
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
        this.codewoerterErstePosition.add("beenden/x");

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

    public String analysiereEingabe(String eingabe) throws IOException {
        List<String> eingabeZerlegt = new LinkedList<>(Arrays.asList(eingabe.split(" ")));
        String ausgabe = "";
        for (int i = 0; i < eingabeZerlegt.size(); i++) {
            if (ausgabe == "") {
                switch (eingabeZerlegt.get(i)) {
                    case "beenden", "x":
                        ausgabe = "beenden";
                        break;
                    case "neu":
                        eingabeZerlegt.remove(i);
                        ausgabe = this.analysiereEingabeNeu(eingabeZerlegt);
                        break;
                    case "buchen":
                        eingabeZerlegt.remove(i);
                        ausgabe = this.analysiereEingabeBuchen(eingabeZerlegt);
                        break;
                    case "ausgabe":
                        eingabeZerlegt.remove(i);
                        ausgabe = this.analysiereEingabeAusgabe(eingabeZerlegt);
                        break;
                }
            }
        }
        return ausgabe;
    }
    private String analysiereEingabeNeu(List<String> teileingabe) {
        String ausgabe="";
        for (int i = 0; i < teileingabe.size(); i++) {
            if (ausgabe == "") {
                switch(teileingabe.get(i)){
                    case "bank":
                        teileingabe.remove(i);
                        ausgabe = this.datenverwaltung.bankAnlegen(teileingabe.get(0));
                        break;
                    case "filiale":
                        teileingabe.remove(i);
                        ausgabe = this.datenverwaltung.bankfilialeAnlegen(teileingabe.get(0), teileingabe.get(1));
                        break;
                    case "automat":
                        teileingabe.remove(i);
                        if ( teileingabe.size() == 1 ){
                            ausgabe = this.datenverwaltung.bankautomatAnlegen(teileingabe.get(0));
                        }else if(teileingabe.size() == 2){
                            ausgabe = this.datenverwaltung.bankautomatAnlegen(teileingabe.get(0),teileingabe.get(1));
                        }
                        break;
                    case "person":
                        teileingabe.remove(i);
                        ausgabe = this.datenverwaltung.personAnlegen(teileingabe.get(0),teileingabe.get(1));
                        break;
                    case "konto":
                        teileingabe.remove(i);
                        int personenID = Integer.parseInt(teileingabe.get(0));
                        ausgabe = this.datenverwaltung.kontoAnlegen(personenID, teileingabe.get(1));
                        break;
                    case "produkt":
                        teileingabe.remove(i);
                        int kontoID = Integer.parseInt(teileingabe.get(1));
                        ausgabe = this.datenverwaltung.produktAnlegen(teileingabe.get(0), kontoID, teileingabe.get(2));
                        break;
                }
            }
        }
        return ausgabe;
    }
    private String analysiereEingabeAusgabe(List<String> teileingabe) throws IOException {
        String ausgabe="";
        for (int i = 0; i < teileingabe.size(); i++) {
            if (ausgabe == "") {
                switch(teileingabe.get(i)){
                    case "uebersicht":
                        ausgabe = this.datenverwaltung.allginfoAusgeben();
                        break;
                    case "bank":
                        teileingabe.remove(i);
                        ausgabe = this.datenverwaltung.bankinfoAusgeben(teileingabe.get(0));
                        break;
                    case "person":
                        teileingabe.remove(i);
                        try {
                            int personenID = Integer.parseInt(teileingabe.get(0));
                            ausgabe = this.datenverwaltung.personeninfoAusgeben(personenID);
                        } catch (NumberFormatException e) {
                            //e.printStackTrace();
                            throw new IOException("Des is keine Zahl!");
                        }
                }
            }
        }
        return ausgabe;
    }


    private String analysiereEingabeBuchen(List<String> teileingabe) throws IOException {
        int betrag;
        try {
        betrag = Integer.parseInt(teileingabe.get(1));
        return "" + this.datenverwaltung.buchen(teileingabe.get(0),betrag);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            throw new IOException("Des is keine Zahl!");
        }
    }

    public void leseEingaben() {
        String eingabe;
        String ausgabe;
        while( true ){
            this.showActions();
            System.out.print(">");
            eingabe = this.scanner.nextLine();
            try {
            ausgabe = analysiereEingabe(eingabe);
            if (ausgabe.equals("beenden")) {
                System.out.println("Das Programm wird nun beendet");
                break;
            } else {
                System.out.println(ausgabe);
            }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void showActions() {
        String out1 = Arrays.toString(codewoerterErstePosition.toArray());
        String out2 = "\n" + Arrays.toString(codewoerterNeu.toArray());
        out2 = out2.replaceAll("filiale,","filiale bankname filialname,");
        out2 = out2.replaceAll("automat,","automat <bankname/bankname filialname>,");
        out2 = out2.replaceAll("person,","person vorname nachname,");
        out2 = out2.replaceAll("konto,","konto id bankname,");
        out2 = out2.replaceAll("produkt","produkt bankname kontoId <k/g>");
        String out3 = "\n" + Arrays.toString(codewoerterAusgabe.toArray());
        out3 = out3.replaceAll("person,","person id,");
        out1 += out2 + out3;
        out1 = out1.replaceAll("bank,","bank name,");
        out1 = out1.replaceAll("\\[|\\]", "");
        System.out.println(out1);
    }

    public void beendeKonsoleneingabe(){
        if(this.scanner != null ){
            this.scanner.close();
        }
    }
    public void initialisiereKonsoleeingabeNeu(){
        this.scanner = new Scanner(System.in);
    }

    /*@Override
    protected void finalize() throws Throwable{
        beendeKonsoleneingabe();
    }*/
}