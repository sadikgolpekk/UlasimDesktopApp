package model.passanger;

public abstract class Yolcu implements IYolcu {
    public Yolcu() {

    }
    
    // indirimOrani() metodu abstract olduğu için
    // alt sınıflar tarafından override edilecektir.
    // public abstract double indirimOrani();
}
