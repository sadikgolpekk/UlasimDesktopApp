package model.passanger;

public class Ogrenci extends Yolcu {
    public Ogrenci() {
        super();
    }

    @Override
    public double indirimOrani() {
        return 0.5; // Öğrenciye %50 indirim
    }
}
