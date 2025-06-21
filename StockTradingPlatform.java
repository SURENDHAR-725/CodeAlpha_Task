import java.util.*;

class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    void updatePrice(double newPrice) {
        this.price = newPrice;
    }
}

class Transaction {
    String stockSymbol;
    int quantity;
    double price;
    String type; // "BUY" or "SELL"
    Date date;

    Transaction(String stockSymbol, int quantity, double price, String type) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return type + " " + quantity + " shares of " + stockSymbol + " @ " + price + " on " + date;
    }
}

class User {
    String name;
    double balance;
    HashMap<String, Integer> portfolio;
    ArrayList<Transaction> transactions;

    User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.portfolio = new HashMap<>();
        this.transactions = new ArrayList<>();
    }

    void buyStock(Stock stock, int quantity) {
        double cost = stock.price * quantity;
        if (cost > balance) {
            System.out.println("❌ Not enough balance to buy.");
            return;
        }
        balance -= cost;
        portfolio.put(stock.symbol, portfolio.getOrDefault(stock.symbol, 0) + quantity);
        transactions.add(new Transaction(stock.symbol, quantity, stock.price, "BUY"));
        System.out.println("✅ Bought " + quantity + " shares of " + stock.symbol);
    }

    void sellStock(Stock stock, int quantity) {
        int owned = portfolio.getOrDefault(stock.symbol, 0);
        if (quantity > owned) {
            System.out.println("❌ Not enough shares to sell.");
            return;
        }
        balance += stock.price * quantity;
        portfolio.put(stock.symbol, owned - quantity);
        transactions.add(new Transaction(stock.symbol, quantity, stock.price, "SELL"));
        System.out.println("✅ Sold " + quantity + " shares of " + stock.symbol);
    }

    void showPortfolio(Map<String, Stock> market) {
        System.out.println("\n📊 Portfolio of " + name);
        System.out.println("Balance: $" + balance);
        for (String symbol : portfolio.keySet()) {
            int qty = portfolio.get(symbol);
            double value = market.get(symbol).price * qty;
            System.out.println(symbol + ": " + qty + " shares (Value: $" + value + ")");
        }
    }

    void showTransactions() {
        System.out.println("\n📁 Transaction History:");
        for (Transaction tx : transactions) {
            System.out.println(tx);
        }
    }
}

public class StockTradingPlatform {
    static HashMap<String, Stock> market = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeMarket();
        User user = new User("Alice", 10000.0);

        while (true) {
            System.out.println("\n=== Stock Trading Platform ===");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transactions");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> viewMarket();
                case 2 -> buyStock(user);
                case 3 -> sellStock(user);
                case 4 -> user.showPortfolio(market);
                case 5 -> user.showTransactions();
                case 6 -> {
                    System.out.println("Exiting... ✅");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void initializeMarket() {
        market.put("AAPL", new Stock("AAPL", 180.25));
        market.put("GOOGL", new Stock("GOOGL", 2950.60));
        market.put("AMZN", new Stock("AMZN", 3500.00));
        market.put("TSLA", new Stock("TSLA", 900.10));
    }

    static void viewMarket() {
        System.out.println("\n📈 Market Stocks:");
        for (Stock stock : market.values()) {
            System.out.println(stock.symbol + ": $" + stock.price);
        }
    }

    static void buyStock(User user) {
        System.out.print("Enter stock symbol to buy: ");
        String symbol = scanner.next().toUpperCase();
        if (!market.containsKey(symbol)) {
            System.out.println("❌ Stock not found.");
            return;
        }
        System.out.print("Enter quantity to buy: ");
        int qty = scanner.nextInt();
        user.buyStock(market.get(symbol), qty);
    }

    static void sellStock(User user) {
        System.out.print("Enter stock symbol to sell: ");
        String symbol = scanner.next().toUpperCase();
        if (!market.containsKey(symbol)) {
            System.out.println("❌ Stock not found.");
            return;
        }
        System.out.print("Enter quantity to sell: ");
        int qty = scanner.nextInt();
        user.sellStock(market.get(symbol), qty);
    }
}
