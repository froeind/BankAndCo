
public class Girokonto extends Bankprodukt {

    public Girokonto(Bank bank, String kundennummer, String produktNummer, int kontostand) {
        super(bank, kundennummer, produktNummer, kontostand);
    }

    @Override
    public String toString(){
        String ausgabe = "IBAN: " + super.getProduktNummer();
        ausgabe = ausgabe + " - Saldo: " + super.getKontostand();
        return ausgabe;
    }

}