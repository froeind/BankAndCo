import java.util.regex.Pattern;

public abstract class Bankprodukt {

    private Bank bank;
    String produktNummer;
    private int kontostand;

    public Bankprodukt(Bank bank, String produktNummer, int kontostand) {
        this.bank = bank;
        this.produktNummer = produktNummer;
        this.kontostand = kontostand;
    }

    public String getProduktNummer() {
        return this.produktNummer;
    }

    public int getKontostand() {
        return this.kontostand;
    }

    public static boolean checkProduktNummer(String produktNummer) {
        return Pattern.matches("[0-9]+", produktNummer);
    }

    public void buchen(int betrag) {
        this.kontostand += betrag;
    }

}