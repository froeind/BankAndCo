public class Kreditkarte extends Bankprodukt {
    public Kreditkarte(Bank bank, String produktNummer, int kontostand) {
        super(bank, produktNummer, kontostand);
    }

    public boolean pruefungKreditNum(){
        return Kreditkartenpruefsystem.pruefeKreditkartennummer(super.getProduktNummer());
    }

    @Override
    public String toString(){
        String ausgabe = "Kreditkartennummer: " + super.getProduktNummer();
        ausgabe += " - Saldo: " + super.getKontostand();
        return ausgabe;
    }

}