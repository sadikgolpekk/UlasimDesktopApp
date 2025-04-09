package model.passanger;

public class Engelli extends Yolcu {
    public Engelli() {
        super();
    }

    @Override
    public double indirimOrani() {
        return 1.0; // Engelliye %100 indirim
    }
}
