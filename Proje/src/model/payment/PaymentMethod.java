package model.payment;

public abstract class PaymentMethod {
    protected String methodName;

    public PaymentMethod(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    /**
     * Base fare üzerinde ödeme yöntemine özgü ayarlamaları (örneğin indirim veya komisyon)
     * uygular. Örneğin, KentKart %20 indirim uygularken, KrediKartı %20 komisyon ekleyebilir.
     */
    public abstract double transformFare(double baseFare);
}
