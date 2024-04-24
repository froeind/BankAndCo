import java.util.ArrayList;
import java.util.Iterator;

public class Bankfiliale {

    private String name;
    private String adresse;
    private Bank bank;
    private ArrayList<Bankautomat> automaten;
    private ArrayList<Bankkunde> kunden;

    public Bankfiliale(String name, String adresse, Bank bank) {
        this.name = name;
        this.adresse = adresse;
        this.bank = bank;
        this.automaten = new ArrayList<>();
        this.kunden = new ArrayList<>();
    }

    public String getName(){
        return this.name;
    }
    public String getAdresse(){
        return this.adresse;
    }
    public Bank getBank(){
        return this.bank;
    }

    public void bankautomatHinzufuegen(Bankautomat bankautomat) {
        this.automaten.add(bankautomat);
    }
    public int lastBankautomat() {
        return this.automaten.size();
    }

    @Override
    public String toString(){
        String ausgabe = "Filialname: " + this.name;
        if( this.automaten.size() > 0){
            ausgabe += "\nAutomatennummern in der Filiale:\n";
            Iterator<Bankautomat> it = this.automaten.listIterator();
            while(it.hasNext()){
                ausgabe += it.next().toString() + " ";
            }
            ausgabe +=  "von insgesamt " + Integer.toString(Bankautomat.getAutomatIds()) + " aufgestellten\n";
        }
        return ausgabe;
    }

}