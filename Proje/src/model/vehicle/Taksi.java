package model.vehicle;

public class Taksi extends Arac {
    private static final double ACIKIS_UCRETI = 10.0;
    private static final double KM_BASI_UCRET = 4.0;

    public Taksi() {
        super();
    }

    @Override
    public double hesaplaUcret(double mesafe) {
        // Taksi ücreti = açılış + (mesafe * km başı ücret)
        return ACIKIS_UCRETI + (mesafe * KM_BASI_UCRET);
    }
}
