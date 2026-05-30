public class EliteVisitor extends ArtGalleryVisitor {
    private boolean assignedPersonalArtAdvisor;
    private boolean exclusiveEventAccess;

    public EliteVisitor(int visitorId, String fullName, String gender, String contactNumber,
                      String registrationDate, double ticketCost, String ticketType) {
        super(visitorId, fullName, gender, contactNumber, registrationDate, ticketCost, ticketType);
        this.assignedPersonalArtAdvisor = false;
        this.exclusiveEventAccess = false;
    }

    // Accessor methods
    public boolean isAssignedPersonalArtAdvisor() { return assignedPersonalArtAdvisor; }
    public boolean hasExclusiveEventAccess() { return exclusiveEventAccess; }

    public boolean assignPersonalArtAdvisor() {
        if (rewardPoints > 5000) {
            assignedPersonalArtAdvisor = true;
        }
        return assignedPersonalArtAdvisor;
    }

    public boolean exclusiveEventAccess() {
        if (assignedPersonalArtAdvisor) {
            exclusiveEventAccess = true;
        }
        return exclusiveEventAccess;
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
            discountAmount = artworkPrice * 0.40; // 40% discount for elite visitors
            finalPrice = artworkPrice - discountAmount;
            return discountAmount;
        }
        return 0.0;
    }

    @Override
    public double calculateRewardPoints() {
        if (isBought) {
            rewardPoints += finalPrice * 10; // 10 points per rupee for elite visitors
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

        // Process cancellation (5% fee for elite visitors)
        refundableAmount = artworkPrice * 0.95; // 95% refund
        rewardPoints -= finalPrice * 10;
        
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
            System.out.println("\n===== ELITE VISITOR BILL =====");
            System.out.println("Visitor ID: " + visitorId);
            System.out.println("Visitor Name: " + fullName);
            System.out.println("Artwork Name: " + artworkName);
            System.out.println("Artwork Price: " + artworkPrice);
            System.out.println("Discount Amount: " + discountAmount);
            System.out.println("Final Price: " + finalPrice);
            System.out.println("Reward Points Earned: " + (finalPrice * 10));
            System.out.println("Personal Art Advisor: " + (assignedPersonalArtAdvisor ? "Yes" : "No"));
            System.out.println("Exclusive Event Access: " + (exclusiveEventAccess ? "Yes" : "No"));
        } else {
            System.out.println("No purchase found to generate bill");
        }
    }

    private void terminateVisitor() {
        isActive = false;
        assignedPersonalArtAdvisor = false;
        exclusiveEventAccess = false;
        visitCount = 0;
        cancelCount = 0;
        rewardPoints = 0;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Personal Art Advisor: " + assignedPersonalArtAdvisor);
        System.out.println("Exclusive Event Access: " + exclusiveEventAccess);
    }
}