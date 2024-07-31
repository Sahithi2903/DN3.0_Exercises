import java.util.Arrays;

class Order {
    private String orderId;
    private String customerName;
    private double totalPrice;

    public Order(String orderId, String customerName, double totalPrice) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

class BubbleSort {
    public static void bubbleSort(Order[] orders) {
        int n = orders.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (orders[j].getTotalPrice() > orders[j + 1].getTotalPrice()) {
                    Order temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                }
            }
        }
    }
}

class QuickSort {
    public static void quickSort(Order[] orders, int low, int high) {
        if (low < high) {
            int pi = partition(orders, low, high);

            quickSort(orders, low, pi - 1);
            quickSort(orders, pi + 1, high);
        }
    }

    private static int partition(Order[] orders, int low, int high) {
        double pivot = orders[high].getTotalPrice();
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (orders[j].getTotalPrice() <= pivot) {
                i++;
                Order temp = orders[i];
                orders[i] = orders[j];
                orders[j] = temp;
            }
        }

        Order temp = orders[i + 1];
        orders[i + 1] = orders[high];
        orders[high] = temp;

        return i + 1;
    }
}

public class EcommerceSort {
    public static void main(String[] args) {
        Order[] orders = {
            new Order("101", "Alice", 250.75),
            new Order("102", "Bob", 150.50),
            new Order("103", "Charlie", 320.40),
            new Order("104", "David", 100.00),
            new Order("105", "Eve", 200.30)
        };

        BubbleSort.bubbleSort(orders);
        System.out.println("Orders sorted by totalPrice using Bubble Sort:");
        for (Order order : orders) {
            System.out.println(order);
        }

        orders = new Order[]{
            new Order("101", "Alice", 250.75),
            new Order("102", "Bob", 150.50),
            new Order("103", "Charlie", 320.40),
            new Order("104", "David", 100.00),
            new Order("105", "Eve", 200.30)
        };

        QuickSort.quickSort(orders, 0, orders.length - 1);
        System.out.println("\nOrders sorted by totalPrice using Quick Sort:");
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
