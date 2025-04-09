package model.passanger;

public class GenelYolcu extends Yolcu {
    public GenelYolcu() {
        super();
    }

    @Override
    public double indirimOrani() {
        return 0.0; // Genel yolcuda indirim yok
    }
}
