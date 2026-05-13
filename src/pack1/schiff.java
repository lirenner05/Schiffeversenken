package pack1;

public class schiff {
    private String name;
    private int laenge;
    private int treffer;

    public schiff(String name, int laenge) {
        this.name = name;
        this.laenge = laenge;
        this.treffer = 0;
    }

    // Wird aufgerufen, wenn ein Segment dieses Schiffs getroffen wird
    public void registriereTreffer() {
        if (treffer < laenge) {
            treffer++;
        }
    }

    // Prüft, ob das Schiff komplett zerstörte wurde
    public boolean istVersenkt() {
        return treffer >= laenge;
    }

    public String getName() {
        return name;
    }

    public int getLaenge() {
        return laenge;
    }

    public int getTreffer() {
        return treffer;
    }
}