package model.vehicle;

public abstract class Arac {
    // Varsayılan constructor
    public Arac() {
    }
    
    // Alt sınıfların override etmesi gereken ücret hesaplama metodu
    public abstract double hesaplaUcret(double mesafe);
}
