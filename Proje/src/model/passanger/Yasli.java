package model.passanger;

public class Yasli extends Yolcu {
    public Yasli() {
        super();
    }

    @Override
    public double indirimOrani() {
        return 1.0; // Yaşlılara tam indirim (örnek)
    }
}
