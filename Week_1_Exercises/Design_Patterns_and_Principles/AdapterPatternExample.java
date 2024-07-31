interface PaymentProcessor {
    void processPayment(double amount);
}


class PayPalPaymentGateway {
    public void pay(double amount) {
        System.out.println("Processing payment of $" + amount + " through PayPal.");
    }
}

class StripePaymentGateway {
    public void charge(double amount) {
        System.out.println("Processing payment of $" + amount + " through Stripe.");
    }
}

class PayPalAdapter implements PaymentProcessor {
    private PayPalPaymentGateway payPal;

    public PayPalAdapter(PayPalPaymentGateway payPal) {
        this.payPal = payPal;
    }

    @Override
    public void processPayment(double amount) {
        payPal.pay(amount);
    }
}

class StripeAdapter implements PaymentProcessor {
    private StripePaymentGateway stripe;

    public StripeAdapter(StripePaymentGateway stripe) {
        this.stripe = stripe;
    }

    @Override
    public void processPayment(double amount) {
        stripe.charge(amount);
    }
}

public class AdapterPatternTest {
    public static void main(String[] args) {
        PayPalPaymentGateway payPal = new PayPalPaymentGateway();
        StripePaymentGateway stripe = new StripePaymentGateway();

        PaymentProcessor payPalProcessor = new PayPalAdapter(payPal);
        PaymentProcessor stripeProcessor = new StripeAdapter(stripe);

        payPalProcessor.processPayment(100.0);
        stripeProcessor.processPayment(150.0);
    }
}
