import java.util.Arrays;
import java.util.Comparator;

class Product {
    private String productId;
    private String productName;
    private String category;

    public Product(String productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}

class LinearSearch {
    public static Product linearSearch(Product[] products, String productName) {
        for (Product product : products) {
            if (product.getProductName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }
}

class BinarySearch {
    public static Product binarySearch(Product[] products, String productName) {
        Arrays.sort(products, Comparator.comparing(Product::getProductName));

        int left = 0;
        int right = products.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int result = productName.compareToIgnoreCase(products[mid].getProductName());

            if (result == 0) {
                return products[mid];
            }
            if (result > 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }
}

public class EcommerceSearch {
    public static void main(String[] args) {
        Product[] products = {
            new Product("101", "Laptop", "Electronics"),
            new Product("102", "Smartphone", "Electronics"),
            new Product("103", "Tablet", "Electronics"),
            new Product("104", "Headphones", "Accessories"),
            new Product("105", "Charger", "Accessories")
        };

        String searchTerm = "Tablet";

        Product resultLinear = LinearSearch.linearSearch(products, searchTerm);
        if (resultLinear != null) {
            System.out.println("Linear Search Result: " + resultLinear);
        } else {
            System.out.println("Product not found using Linear Search.");
        }

        Product resultBinary = BinarySearch.binarySearch(products, searchTerm);
        if (resultBinary != null) {
            System.out.println("Binary Search Result: " + resultBinary);
        } else {
            System.out.println("Product not found using Binary Search.");
        }
    }
}
