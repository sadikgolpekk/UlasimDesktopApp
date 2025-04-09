package model.passanger;

public class Ogretmen extends Yolcu {
    public Ogretmen() {
        super();
    }

    @Override
    public double indirimOrani() {
        return 0.3; // Örneğin, %30 indirim
    }
}
