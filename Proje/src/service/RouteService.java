package service;

import model.passanger.IYolcu;
import model.payment.PaymentMethod;
import model.routing.Durak;
import model.vehicle.Arac;
import model.vehicle.Taksi;
import ui.HaritaPanel;

import java.util.List;

public class RouteService {

    /**
     * Tüm rota seçeneklerini hesaplayıp, HTML formatında string döndürür.
     * RouteCalculator, DFS_MAX_DEPTH sabitini içeride kullanır.
     */
    public static String computeRoutesHTML(
            Durak baslangicDurak, Durak bitisDurak,
            double startLat, double startLon,
            double endLat, double endLon,
            IMesafeHesaplayici mesafeHesaplayici,
            PaymentMethod currentPaymentMethod,
            IYolcu selectedYolcu,
            List<Durak> duraklar,
            HaritaPanel haritaPanel
    ) {
        // 1) Taksi hesaplaması
        Arac taksi = new Taksi();
        double startTaxiDistance = mesafeHesaplayici.hesapla(
                startLat, startLon, baslangicDurak.getLat(), baslangicDurak.getLon());
        double endTaxiDistance = mesafeHesaplayici.hesapla(
                endLat, endLon, bitisDurak.getLat(), bitisDurak.getLon());

        double startTaxiFare = (startTaxiDistance > 3)
                ? taksi.hesaplaUcret(startTaxiDistance) : 0.0;
        double endTaxiFare = (endTaxiDistance > 3)
                ? taksi.hesaplaUcret(endTaxiDistance) : 0.0;

        double startTaxiTime = (startTaxiDistance > 3)
                ? ((startTaxiDistance / 40.0) * 60.0) : 0.0;
        double endTaxiTime = (endTaxiDistance > 3)
                ? ((endTaxiDistance / 40.0) * 60.0) : 0.0;

        // 2) RouteCalculator'a başvur
        RouteCalculator calculator = new RouteCalculator(duraklar, haritaPanel);
        List<RouteCalculator.RouteOption> routeOptions = calculator.computeAllRoutes(baslangicDurak, bitisDurak);

        // HTML formatlı çıktı için StringBuilder
        StringBuilder sb = new StringBuilder("<html><body style='font-family: Arial; font-size: 12px;'>");

        if (routeOptions.isEmpty()) {
            sb.append("<p style='color:red;'><b>Ulaşım için geçerli rota bulunamadı.</b></p>");
        } else {
            int optionCount = 1;
            for (RouteCalculator.RouteOption option : routeOptions) {
                sb.append("<h3>Rota Seçeneği ").append(optionCount++).append(":</h3>");

                // Başlangıç taksi
                if (startTaxiDistance > 3) {
                    sb.append(String.format("<p><b>Başlangıç Taksi:</b> %.2f km, %.2f TL, %.2f dk</p>",
                            startTaxiDistance, startTaxiFare, startTaxiTime));
                }

                // Toplu taşıma edge'leri
                for (RouteCalculator.Edge edge : option.edges) {
                    String transferStr = edge.isTransfer ? " (Transfer)" : "";
                    sb.append(String.format("<p>%s -> %s%s: %.2f km, %.2f TL, %.2f dk</p>",
                            edge.from.getName(), edge.to.getName(), transferStr,
                            edge.distance, edge.fare, edge.time));
                }

                // Bitiş taksi
                if (endTaxiDistance > 3) {
                    sb.append(String.format("<p><b>Bitiş Taksi:</b> %.2f km, %.2f TL, %.2f dk</p>",
                            endTaxiDistance, endTaxiFare, endTaxiTime));
                }

                double totalDistance = startTaxiDistance + option.totalDistance + endTaxiDistance;
                double totalTime = startTaxiTime + option.totalTime + endTaxiTime;
                double publicTransportFare = option.totalFare;
                double transformedFare = (currentPaymentMethod != null)
                        ? currentPaymentMethod.transformFare(publicTransportFare)
                        : publicTransportFare;
                double finalPublicFare = transformedFare * (1 - selectedYolcu.indirimOrani());
                double totalFare = startTaxiFare + finalPublicFare + endTaxiFare;

                sb.append(String.format("<p style='color:blue;'><b>Toplam:</b> %.2f km, %.2f TL, %.2f dk</p><hr/>",
                        totalDistance, totalFare, totalTime));
            }

            // 3) En optimize rotaları bulma
            RouteCalculator.RouteOption bestTimeRoute = null;
            RouteCalculator.RouteOption bestFareRoute = null;
            RouteCalculator.RouteOption bestDistanceRoute = null;

            double minTime = Double.MAX_VALUE, minFare = Double.MAX_VALUE, minDistance = Double.MAX_VALUE;
            for (RouteCalculator.RouteOption option : routeOptions) {
                double routeDistance = startTaxiDistance + option.totalDistance + endTaxiDistance;
                double routeTime = startTaxiTime + option.totalTime + endTaxiTime;
                double routeFare = startTaxiFare
                        + ((currentPaymentMethod != null 
                            ? currentPaymentMethod.transformFare(option.totalFare) 
                            : option.totalFare) * (1 - selectedYolcu.indirimOrani()))
                        + endTaxiFare;

                if (routeTime < minTime) {
                    minTime = routeTime;
                    bestTimeRoute = option;
                }
                if (routeFare < minFare) {
                    minFare = routeFare;
                    bestFareRoute = option;
                }
                if (routeDistance < minDistance) {
                    minDistance = routeDistance;
                    bestDistanceRoute = option;
                }
            }

            sb.append("<h3>----- <b>EN OPTIMIZE ROTALAR</b> -----</h3>");
            if (bestTimeRoute != null) {
                sb.append(String.format("<p><b>En Kısa Süreli Yol:</b> %.2f dk</p>", minTime));
                sb.append("<p>(").append(calculator.buildRouteSignature(bestTimeRoute)).append(")</p>");
            }
            if (bestFareRoute != null) {
                sb.append(String.format("<p><b>En Ucuz Yol:</b> %.2f TL</p>", minFare));
                sb.append("<p>(").append(calculator.buildRouteSignature(bestFareRoute)).append(")</p>");
            }
            if (bestDistanceRoute != null) {
                sb.append(String.format("<p><b>En Kısa Mesafeli Yol:</b> %.2f km</p>", minDistance));
                sb.append("<p>(").append(calculator.buildRouteSignature(bestDistanceRoute)).append(")</p>");
            }
        }

        sb.append("</body></html>");
        String htmlOutput = sb.toString();

        // Ses çalma: Eğer ödeme yöntemi "Kredi Kartı" veya "KentKart" ise ses dosyasını çal.
        if (currentPaymentMethod != null) {
            String methodName = currentPaymentMethod.getMethodName();
            if (methodName.equalsIgnoreCase("Kredi Kartı") || methodName.equalsIgnoreCase("KentKart")) {
                // "payment_sound.wav" dosyasını çal. Dosya yolunu projeye göre ayarlayın.
                SoundPlayer.playSound("data/AkbilSesi.wav");
            }
            if (methodName.equalsIgnoreCase("Nakit")) {
                // "payment_sound.wav" dosyasını çal. Dosya yolunu projeye göre ayarlayın.
                SoundPlayer.playSound("data/NakitSes.wav");
            }
        }

        return htmlOutput;
    }
}
