import java.util.LinkedList;

public class Bankkunde {

    private Bank bank;
    private Person person;
    private String kundennummer;
    private Girokonto girokonto;
    private Kreditkarte kreditkarte;

    public Bankkunde(Bank bank, Person person, String kundennummer) {
        this.bank = bank;
        this.person = person;
        this.kundennummer = kundennummer;
        this.girokonto = null;
        this.kreditkarte = null;
    }

    public Girokonto getGirokonto() {
        return this.girokonto;
    }
    public Kreditkarte getKreditkarte() {
        return this.kreditkarte;
    }
    public String getKundennummer() {
        return this.kundennummer;
    }
    public Person getPerson() {
        return this.person;
    }

    public void giroHinzufuegen(Girokonto girokonto) {
        this.girokonto = girokonto;
    }
    public void kreditHinzufuegen(Kreditkarte kreditkarte) {
        this.kreditkarte = kreditkarte;
    }

    @Override
    public String toString(){
        return "" + this.person + " - " + this.bank;
    }
}