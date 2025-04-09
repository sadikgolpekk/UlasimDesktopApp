package model.payment;

public class KentKart extends PaymentMethod {

    public KentKart() {
        super("KentKart");
    }

    @Override
    public double transformFare(double baseFare) {
        // Base fare'den %20 indirim uygular: örneğin 10 TL -> 8 TL.
        return baseFare * 0.8;
    }
}
