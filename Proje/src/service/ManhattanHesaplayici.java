package service;

/**
 * ManhattanHesaplayici, enlem ve boylam farklarını baz alarak Manhattan mesafesini hesaplar.
 */
public class ManhattanHesaplayici implements IMesafeHesaplayici {
    @Override
    public double hesapla(double lat1, double lon1, double lat2, double lon2) {
        // Enlem farkı (yaklaşık 110.574 km/°)
        double latDiff = Math.abs(lat1 - lat2) * 110.574;  
        // Boylam farkı (yaklaşık 111.320 km/°)
        double lonDiff = Math.abs(lon1 - lon2) * 111.320;
        return latDiff + lonDiff;
    }
}
