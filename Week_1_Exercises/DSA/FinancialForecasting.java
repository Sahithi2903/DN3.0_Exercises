import java.util.HashMap;

public class FinancialForecasting {

    public static double calculateFutureValue(double currentValue, double growthRate, int years) {
        if (years == 0) {
            return currentValue;
        }
        return (1 + growthRate) * calculateFutureValue(currentValue, growthRate, years - 1);
    }

    public static double calculateFutureValueMemoized(double currentValue, double growthRate, int years, HashMap<Integer, Double> memo) {
        if (years == 0) {
            return currentValue;
        }
        if (memo.containsKey(years)) {
            return memo.get(years);
        }
        double futureValue = (1 + growthRate) * calculateFutureValueMemoized(currentValue, growthRate, years - 1, memo);
        memo.put(years, futureValue);
        return futureValue;
    }

    public static void main(String[] args) {
        double currentValue = 1000.0; // Initial value
        double growthRate = 0.05; // 5% growth rate
        int years = 10; // Number of years for prediction

        double futureValueRecursive = calculateFutureValue(currentValue, growthRate, years);
        System.out.println("Future Value (Recursive): " + futureValueRecursive);

        HashMap<Integer, Double> memo = new HashMap<>();
        double futureValueMemoized = calculateFutureValueMemoized(currentValue, growthRate, years, memo);
        System.out.println("Future Value (Memoized): " + futureValueMemoized);
    }
}
