package model.vehicle;

public class Otobus extends Arac {

    public Otobus() {
        super();
    }

    @Override
    public double hesaplaUcret(double mesafe) {
        // Otobüs için hesaplama yapılmıyor, veriler JSON’dan alınır.
        return 0.0;
    }
}
