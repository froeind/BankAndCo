public class Kreditkarte extends Bankprodukt {
    public Kreditkarte(Bank bank, String produktNummer, int kontostand) {
        super(bank, produktNummer, kontostand);
    }
    public String toString() { return this.produktNummer; }
}