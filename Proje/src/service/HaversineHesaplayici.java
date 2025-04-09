package service;

/**
 * HaversineHesaplayici, Haversine formülünü kullanarak iki nokta arasındaki mesafeyi hesaplar.
 */
public class HaversineHesaplayici implements IMesafeHesaplayici {
    @Override
    public double hesapla(double lat1, double lon1, double lat2, double lon2) {
        final int RADIUS = 6371; // Dünya yarıçapı (km)
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                 + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                 * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RADIUS * c;
    }
}
