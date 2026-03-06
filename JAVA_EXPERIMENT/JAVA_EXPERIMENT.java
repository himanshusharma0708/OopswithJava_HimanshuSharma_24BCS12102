import java.util.HashMap;

// --- 1. MODEL CLASSES ---
class Student {
    String uid, name;
    int fineAmount, currentBorrowCount;

    public Student(String uid, String name, int fine, int count) {
        this.uid = uid; this.name = name;
        this.fineAmount = fine; this.currentBorrowCount = count;
    }
}

class Asset {
    String assetId, assetName;
    boolean available;
    int securityLevel;

    public Asset(String id, String name, boolean avail, int level) {
        this.assetId = id; this.assetName = name;
        this.available = avail; this.securityLevel = level;
    }
}

class CheckoutRequest {
    String uid, assetId;
    int hoursRequested;

    public CheckoutRequest(String uid, String assetId, int hrs) {
        this.uid = uid; this.assetId = assetId;
        this.hoursRequested = hrs;
    }
}

// --- 2. UTILITY & LOGGING ---
class ValidationUtil {
    public static void validateUid(String uid) {
        if (uid == null || uid.length() < 8 || uid.length() > 12 || uid.contains(" "))
            throw new IllegalArgumentException("Invalid UID: 8-12 chars, no spaces.");
    }

    public static void validateAssetId(String id) {
        if (id == null || !id.startsWith("LAB-") || !id.substring(4).matches("\\d+"))
            throw new IllegalArgumentException("Invalid AssetID: Must be LAB-###.");
    }

    public static void validateHours(int hrs) {
        if (hrs < 1 || hrs > 6)
            throw new IllegalArgumentException("Invalid Hours: Must be 1-6.");
    }
}

class AuditLogger {
    public static void log(String msg) { System.out.println("[AUDIT] " + msg); }
    public static void logError(Exception e) { System.err.println("[AUDIT-ERROR] " + e.getMessage()); }
}

// --- 3. STORE & SERVICE ---
class AssetStore {
    private HashMap<String, Asset> assets = new HashMap<>();
    public void addAsset(Asset a) { assets.put(a.assetId, a); }
    public Asset findAsset(String assetId) {
        Asset a = assets.get(assetId);
        if (a == null) throw new NullPointerException("Asset not found: " + assetId);
        return a;
    }
    public void markBorrowed(Asset a) {
        if (!a.available) throw new IllegalStateException("Asset already borrowed.");
        a.available = false;
    }
}

class CheckoutService {
    private AssetStore store;
    private HashMap<String, Student> students;

    public CheckoutService(AssetStore store, HashMap<String, Student> students) {
        this.store = store;
        this.students = students;
    }

    public String checkout(CheckoutRequest req) throws IllegalArgumentException, 
           IllegalStateException, SecurityException, NullPointerException {
        
        ValidationUtil.validateUid(req.uid);
        ValidationUtil.validateAssetId(req.assetId);
        ValidationUtil.validateHours(req.hoursRequested);

        Student s = students.get(req.uid);
        if (s == null) throw new NullPointerException("Student not found.");
        Asset a = store.findAsset(req.assetId);

        if (s.fineAmount > 0) throw new IllegalStateException("Fine exists.");
        if (s.currentBorrowCount >= 2) throw new IllegalStateException("Limit reached.");
        if (!a.available) throw new IllegalStateException("Unavailable.");
        if (a.securityLevel == 3 && !s.uid.startsWith("KRG")) 
            throw new SecurityException("Security Level 3 requires KRG prefix.");

        int finalHours = req.hoursRequested;
        if (finalHours == 6) System.out.println("Note: Max duration selected.");
        if (a.assetName.contains("Cable") && finalHours > 3) {
            finalHours = 3;
            System.out.println("Policy applied: Cable hours reduced to 3.");
        }

        store.markBorrowed(a);
        s.currentBorrowCount++;
        return "TXN-20260306-" + a.assetId + "-" + s.uid;
    }
}

// --- 4. THE MAIN ENTRY POINT ---
public class Main {
    public static void main(String[] args) {
        AssetStore store = new AssetStore();
        store.addAsset(new Asset("LAB-101", "HDMI Cable", true, 1));
        store.addAsset(new Asset("LAB-505", "Restricted Drive", true, 3));
        store.addAsset(new Asset("LAB-999", "Unavailable Tool", false, 1));

        HashMap<String, Student> students = new HashMap<>();
        students.put("KRG20281", new Student("KRG20281", "Alice", 0, 0));
        students.put("STU99999", new Student("STU99999", "Bob", 50, 0)); 
        students.put("STU12345", new Student("STU12345", "Charlie", 0, 0));

        CheckoutService service = new CheckoutService(store, students);

        CheckoutRequest[] requests = {
            new CheckoutRequest("KRG20281", "LAB-101", 6), // Success
            new CheckoutRequest("STU12345", "BAD-ID", 4),   // Invalid ID
            new CheckoutRequest("STU99999", "LAB-505", 2)  // Fine Failure
        };

        for (CheckoutRequest req : requests) {
            try {
                System.out.println("\n--- REQUEST: " + req.assetId + " ---");
                System.out.println("Result: " + service.checkout(req));
            } catch (Exception e) {
                System.out.println("Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            } finally {
                AuditLogger.log("Finished UID=" + req.uid + ", asset=" + req.assetId);
            }
        }
    }
}