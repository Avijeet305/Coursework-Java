public class StandardVisitor extends ArtGalleryVisitor {
    private boolean isEligibleForDiscountUpgrade;
    private final int visitLimit;
    private float discountPercent;

    public StandardVisitor(int visitorId, String fullName, String gender, String contactNumber,
                         String registrationDate, double ticketCost, String ticketType) {
        super(visitorId, fullName, gender, contactNumber, registrationDate, ticketCost, ticketType);
        this.visitLimit = 5;
        this.discountPercent = 0.10f;
        this.isEligibleForDiscountUpgrade = false;
    }

    // Accessor methods
    public boolean isEligibleForDiscountUpgrade() { return isEligibleForDiscountUpgrade; }
    public int getVisitLimit() { return visitLimit; }
    public float getDiscountPercent() { return discountPercent; }

    public boolean checkDiscountUpgrade() {
        if (visitCount >= visitLimit) {
            isEligibleForDiscountUpgrade = true;
            discountPercent = 0.15f;
        }
        return isEligibleForDiscountUpgrade;
    }

    @Override
    public String buyProduct(String artworkName, double artworkPrice) {
        if (!isActive) {
            return "Please log in before making a purchase";
        }

        if (!isBought || this.artworkName == null || !this.artworkName.equals(artworkName)) {
            this.artworkName = artworkName;
            this.artworkPrice = artworkPrice;
            isBought = true;
            buyCount++;
            return "Artwork '" + artworkName + "' purchased successfully";
        } else {
            return "You have already purchased this artwork";
        }
    }

    @Override
    public double calculateDiscount() {
        if (isBought) {
            checkDiscountUpgrade();
            discountAmount = artworkPrice * discountPercent;
            finalPrice = artworkPrice - discountAmount;
            return discountAmount;
        }
        return 0.0;
    }

    @Override
    public double calculateRewardPoints() {
        if (isBought) {
            rewardPoints += finalPrice * 5;
            return rewardPoints;
        }
        return rewardPoints;
    }

    @Override
    public String cancelProduct(String artworkName, String cancellationReason) {
        if (cancelCount >= cancelLimit) {
            terminateVisitor();
            return "Cancellation limit reached. Account terminated.";
        }

        if (buyCount == 0) {
            return "No product to cancel";
        }

        if (!this.artworkName.equals(artworkName)) {
            return "Artwork name doesn't match";
        }

        // Process cancellation
        refundableAmount = artworkPrice * 0.90; // 90% refund (10% fee)
        rewardPoints -= finalPrice * 5;
        
        // Reset purchase details
        this.artworkName = null;
        this.artworkPrice = 0.0;
        isBought = false;
        buyCount--;
        cancelCount++;
        this.cancellationReason = cancellationReason;

        return "Product cancelled. Refund: " + refundableAmount;
    }

    @Override
    public void generateBill() {
        if (isBought) {
            System.out.println("\n===== STANDARD VISITOR BILL =====");
            System.out.println("Visitor ID: " + visitorId);
            System.out.println("Visitor Name: " + fullName);
            System.out.println("Artwork Name: " + artworkName);
            System.out.println("Artwork Price: " + artworkPrice);
            System.out.println("Discount Amount: " + discountAmount);
            System.out.println("Final Price: " + finalPrice);
            System.out.println("Reward Points Earned: " + (finalPrice * 5));
        } else {
            System.out.println("No purchase found to generate bill");
        }
    }

    private void terminateVisitor() {
        isActive = false;
        isEligibleForDiscountUpgrade = false;
        visitCount = 0;
        cancelCount = 0;
        rewardPoints = 0;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Eligible for Discount Upgrade: " + isEligibleForDiscountUpgrade);
        System.out.println("Visit Limit: " + visitLimit);
        System.out.println("Discount Percent: " + (discountPercent * 100) + "%");
    }
}