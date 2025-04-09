package service;

/**
 * IMesafeHesaplayici, farklı mesafe hesaplama algoritmalarını soyutlamak için kullanılan arayüzdür.
 * Örneğin: Haversine veya Manhattan mesafesi.
 */
public interface IMesafeHesaplayici {
    double hesapla(double lat1, double lon1, double lat2, double lon2);
}
