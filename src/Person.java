import java.util.LinkedList;

public class Person {

    private String vorname;
    private String nachname;
    private LinkedList<Bankprodukt> konten = new LinkedList<Bankprodukt>();

    public Person(String vorname, String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public String getName() {
        return this.vorname + " " + this.nachname;
    }

    public void kontoHinzufuegen(Bankprodukt bankprodukt){
        konten.add(bankprodukt);
    }

    @Override
    public String toString(){
        String ausgabe = "Name: " + this.vorname + " " + this.nachname;
        for (int i = 0; i < this.konten.size(); i++) {
            ausgabe = ausgabe + " - " + this.konten.get(i);
        }
        return ausgabe;
    }

}

