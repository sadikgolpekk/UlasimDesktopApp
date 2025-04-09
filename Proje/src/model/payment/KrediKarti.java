package model.payment;

public class KrediKarti extends PaymentMethod {

    public KrediKarti() {
        super("Kredi Kartı");
    }

    @Override
    public double transformFare(double baseFare) {
        // Base fare'e 4 TL ekler: örneğin 10 TL -> 14 TL.
        return baseFare* 1.2;
    }
}
