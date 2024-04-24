import java.util.LinkedList;

public class Bankkunde {

    private Bank bank;
    private Person person;
    private Girokonto girokonto;
    private Kreditkarte kreditkarte;

    public Bankkunde(Bank bank, Person person) {
        this.bank = bank;
        this.person = person;
        this.girokonto = null;
        this.kreditkarte = null;
    }

    public void giroHinzufuegen(Girokonto girokonto) {
        this.girokonto = girokonto;
    }
    public void kreditHinzufuegen(Kreditkarte kreditkarte) {
        this.kreditkarte = kreditkarte;
    }

}

/*
+ getGirokonto(): Girokonto
+ toString(): String
 */