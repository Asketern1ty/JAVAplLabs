import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

enum OwnerType {
    PUPIL, STUDENT, REGULAR
}

abstract class TransitCard implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String id;
    protected OwnerType ownerType;

    public TransitCard(OwnerType ownerType) {
        this.id = UUID.randomUUID().toString();
        this.ownerType = ownerType;
    }

    public String getId() { return id; }
    public OwnerType getOwnerType() { return ownerType; }

    public abstract boolean processPass();
    public abstract String getCardInfo();
}

class TripCard extends TransitCard {
    private int remainingTrips;

    public TripCard(OwnerType ownerType, int trips) {
        super(ownerType);
        if (trips != 5 && trips != 10) {
            throw new IllegalArgumentException("Card can only be for 5 or 10 trips.");
        }
        this.remainingTrips = trips;
    }

    @Override
    public boolean processPass() {
        if (remainingTrips > 0) {
            remainingTrips--;
            return true;
        }
        return false;
    }

    @Override
    public String getCardInfo() {
        return String.format("TripCard [%s] - Remaining trips: %d", ownerType, remainingTrips);
    }
}

class TermCard extends TransitCard {
    private LocalDate expiryDate;

    public TermCard(OwnerType ownerType, int daysValid) {
        super(ownerType);
        this.expiryDate = LocalDate.now().plusDays(daysValid);
    }

    @Override
    public boolean processPass() {
        return !LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public String getCardInfo() {
        return String.format("TermCard [%s] - Valid until: %s", ownerType, expiryDate);
    }
}

class AccumulativeCard extends TransitCard {
    private double balance;
    private static final double FARE_COST = 8.0;

    public AccumulativeCard() {
        super(OwnerType.REGULAR);
        this.balance = 0.0;
    }

    public void topUp(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Card " + id.substring(0, 5) + "... topped up by " + amount);
        }
    }

    @Override
    public boolean processPass() {
        if (balance >= FARE_COST) {
            balance -= FARE_COST;
            return true;
        }
        return false;
    }

    @Override
    public String getCardInfo() {
        return String.format("AccumulativeCard [%s] - Balance: %.2f", ownerType, balance);
    }
}

class CardRegistry implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, TransitCard> issuedCards = new HashMap<>();

    public TransitCard issueTripCard(OwnerType type, int trips) {
        TransitCard card = new TripCard(type, trips);
        issuedCards.put(card.getId(), card);
        return card;
    }

    public TransitCard issueTermCard(OwnerType type, int days) {
        TransitCard card = new TermCard(type, days);
        issuedCards.put(card.getId(), card);
        return card;
    }

    public AccumulativeCard issueAccumulativeCard() {
        AccumulativeCard card = new AccumulativeCard();
        issuedCards.put(card.getId(), card);
        return card;
    }

    public TransitCard getCard(String id) {
        return issuedCards.get(id);
    }
    
    public Map<String, TransitCard> getIssuedCards() {
        return issuedCards;
    }
}

class Turnstile {
    private CardRegistry registry;
    private int totalPasses = 0;
    private int totalDenials = 0;
    private Map<OwnerType, Integer> passesByType = new HashMap<>();
    private Map<OwnerType, Integer> denialsByType = new HashMap<>();

    public Turnstile(CardRegistry registry) {
        this.registry = registry;
        for (OwnerType type : OwnerType.values()) {
            passesByType.put(type, 0);
            denialsByType.put(type, 0);
        }
    }

    public void setRegistry(CardRegistry registry) {
        this.registry = registry;
    }

    public boolean readCard(String cardId) {
        TransitCard card = registry.getCard(cardId);
        if (card == null) {
            System.out.println("Denied: Card not found (invalid).");
            totalDenials++;
            return false;
        }

        OwnerType type = card.getOwnerType();
        if (card.processPass()) {
            System.out.println("Access granted: " + card.getCardInfo());
            totalPasses++;
            passesByType.put(type, passesByType.get(type) + 1);
            return true;
        } else {
            System.out.println("Denied: " + card.getCardInfo() + " (Limit exhausted or insufficient funds)");
            totalDenials++;
            denialsByType.put(type, denialsByType.get(type) + 1);
            return false;
        }
    }

    public void printStatistics() {
        System.out.println("\n=== TURNSTILE STATISTICS ===");
        System.out.println("1) Summary data:");
        System.out.println("   Access granted: " + totalPasses);
        System.out.println("   Access denied: " + totalDenials);
        System.out.println("2) Data by card type (Success / Denial):");
        for (OwnerType type : OwnerType.values()) {
            System.out.printf("   - %-10s : %d / %d\n", type, passesByType.get(type), denialsByType.get(type));
        }
        System.out.println("============================\n");
    }
}

// Виділення роботи з файлами в окремий клас (Вимога 4)
class FileManager {
    public static void saveRegistry(CardRegistry registry, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(registry);
            System.out.println("Card database successfully saved to file.");
        }
    }

    public static CardRegistry loadRegistry(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            CardRegistry registry = (CardRegistry) ois.readObject();
            System.out.println("Card database successfully loaded from file.");
            return registry;
        }
    }
}

public class Mtask {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CardRegistry registry = new CardRegistry();
        Turnstile turnstile = new Turnstile(registry);

        TransitCard studentTripCard = registry.issueTripCard(OwnerType.STUDENT, 5);
        TransitCard pupilTermCard = registry.issueTermCard(OwnerType.PUPIL, 30);
        AccumulativeCard regularAccCard = registry.issueAccumulativeCard();
        regularAccCard.topUp(10.0);

        System.out.println("--- Passenger flow simulation ---");
        turnstile.readCard(studentTripCard.getId());
        turnstile.readCard(pupilTermCard.getId());
        turnstile.readCard(regularAccCard.getId());

        // Демонстрація збереження даних у файл
        System.out.print("\nEnter the path and file name to save data (e.g., registry.dat): ");
        String filePath = scanner.nextLine();

        try {
            // Збереження об'єктів
            FileManager.saveRegistry(registry, filePath);
            
            // Читання об'єктів у новий реєстр для перевірки
            CardRegistry loadedRegistry = FileManager.loadRegistry(filePath);
            turnstile.setRegistry(loadedRegistry);
            
            System.out.println("\n--- Simulation after loading from file ---");
            turnstile.readCard(regularAccCard.getId()); 
            turnstile.readCard("fake-uuid-12345");
            turnstile.printStatistics();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error working with file: " + e.getMessage());
        }
    }
}