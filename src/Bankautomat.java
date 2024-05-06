import java.util.Iterator;

public class Bankautomat {

    private static int automatIds = 0;
    private int automatId = 0;
    private String adresse;
    private Bankfiliale filiale;

    public Bankautomat(String adresse) {
        Bankautomat.automatIds++;
        this.automatId = Bankautomat.automatIds;
        this.adresse = adresse;
        this.filiale = null;
    }
    public Bankautomat(String adresse, Bankfiliale filiale) {
        Bankautomat.automatIds++;
        this.automatId = Bankautomat.automatIds;
        this.adresse = adresse;
        this.filiale = filiale;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public static int getAutomatIds() {
        return automatIds;
    }
    public int getAutomatId() {
        return automatId;
    }

    public boolean istInFiliale() {
        return (this.filiale != null);
    }

    @Override
    public String toString() {
        return Integer.toString(this.automatId);
    }
}