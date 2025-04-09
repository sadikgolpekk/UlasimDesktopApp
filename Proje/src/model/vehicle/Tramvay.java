package model.vehicle;

public class Tramvay extends Arac {

    public Tramvay() {
        super();
    }

    @Override
    public double hesaplaUcret(double mesafe) {
        // Tramvay için hesaplama yapılmıyor, veriler JSON’dan alınır.
        return 0.0;
    }
}
