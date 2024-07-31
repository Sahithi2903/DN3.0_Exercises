import java.util.HashMap;
class Product {
    private String productId;
    private String productName;
    private int quantity;
    private double price;

    public Product(String productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}

public class InventoryManagementSystem {
    private HashMap<String, Product> inventory;
    public InventoryManagementSystem() {
        this.inventory = new HashMap<>();
    }

    public void addProduct(Product product) {
        inventory.put(product.getProductId(), product);
    }

    public void updateProduct(String productId, int quantity, double price) {
        Product product = inventory.get(productId);
        if (product != null) {
            product.setQuantity(quantity);
            product.setPrice(price);
        } else {
            System.out.println("Product not found.");
        }
    }

    public void deleteProduct(String productId) {
        inventory.remove(productId);
    }

    public Product getProduct(String productId) {
        return inventory.get(productId);
    }

    public void displayProducts() {
        for (Product product : inventory.values()) {
            System.out.println(product);
        }
    }
    public static void main(String[] args) {
        InventoryManagementSystem ims = new InventoryManagementSystem();

        Product p1 = new Product("101", "Laptop", 10, 1000.0);
        Product p2 = new Product("102", "Smartphone", 20, 500.0);

        ims.addProduct(p1);
        ims.addProduct(p2);

        System.out.println("Initial inventory:");
        ims.displayProducts();

        ims.updateProduct("101", 15, 950.0);
        System.out.println("\nAfter updating Laptop:");
        ims.displayProducts();

        ims.deleteProduct("102");
        System.out.println("\nAfter deleting Smartphone:");
        ims.displayProducts();
    }
}
