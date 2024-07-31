import java.util.ArrayList;
import java.util.List;

interface Stock {
    void registerObserver(Observer observer);
    void deregisterObserver(Observer observer);
    void notifyObservers();
}

class StockMarket implements Stock {
    private List<Observer> observers = new ArrayList<>();
    private double price;

    public void setPrice(double price) {
        this.price = price;
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void deregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(price);
        }
    }
}

interface Observer {
    void update(double price);
}


class MobileApp implements Observer {
    @Override
    public void update(double price) {
        System.out.println("MobileApp: Stock price updated to $" + price);
    }
}

class WebApp implements Observer {
    @Override
    public void update(double price) {
        System.out.println("WebApp: Stock price updated to $" + price);
    }
}

public class ObserverPatternTest {
    public static void main(String[] args) {
        StockMarket stockMarket = new StockMarket();

        Observer mobileApp = new MobileApp();
        Observer webApp = new WebApp();

        stockMarket.registerObserver(mobileApp);
        stockMarket.registerObserver(webApp);

        System.out.println("Setting stock price to $100.00");
        stockMarket.setPrice(100.00);

        System.out.println("Setting stock price to $120.00");
        stockMarket.setPrice(120.00);
    }
}
