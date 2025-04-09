package model.payment;

public class Nakit extends PaymentMethod {

    public Nakit() {
        super("Nakit");
    }

    @Override
    public double transformFare(double baseFare) {
        // Nakit ödeme yöntemi ücrete etki etmez.
        return baseFare;
    }
}
