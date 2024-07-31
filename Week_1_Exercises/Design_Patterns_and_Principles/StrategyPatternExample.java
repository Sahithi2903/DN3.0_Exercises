interface PaymentStrategy {
    void pay(double amount);
}


class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cardHolderName;

    public CreditCardPayment(String cardNumber, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Processing credit card payment of $" + amount +
                           " for card number: " + cardNumber +
                           " (Card Holder: " + cardHolderName + ")");
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Processing PayPal payment of $" + amount +
                           " for email: " + email);
    }
}

class PaymentContext {
    private PaymentStrategy paymentStrategy;

    public PaymentContext(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void executePayment(double amount) {
        paymentStrategy.pay(amount);
    }
}

public class StrategyPatternTest {
    public static void main(String[] args) {
        PaymentStrategy creditCardPayment = new CreditCardPayment("1234-5678-9876-5432", "John Doe");
        
        PaymentStrategy payPalPayment = new PayPalPayment("john.doe@example.com");

        PaymentContext paymentContext = new PaymentContext(creditCardPayment);
        System.out.println("Using Credit Card Payment Strategy:");
        paymentContext.executePayment(250.00);

        paymentContext = new PaymentContext(payPalPayment);
        System.out.println("Using PayPal Payment Strategy:");
        paymentContext.executePayment(150.00);
    }
}
