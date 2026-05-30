import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

/**
 * Art Gallery Visitor Management System GUI
 * 
 * This class provides a graphical user interface for managing art gallery visitors,
 * including standard and elite visitors with their respective features.
 * 
 * @author Avijeet
 * @version 1.2.12.0
 */
public class ArtGalleryVisitorGUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JPanel inputPanel;
    private JPanel outputPanel;
    private JTextArea outputArea;

    // Text fields
    private JTextField visitorIdField;
    private JTextField fullNameField;
    private JTextField contactNumberField;
    private JTextField ticketCostField;
    private JTextField artworkNameField;
    private JTextField artworkPriceField;
    private JTextField cancellationReasonField;

    // Radio buttons for gender
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
    private JRadioButton otherRadio;

    // Date combo boxes
    private JComboBox<String> dayCombo;
    private JComboBox<String> monthCombo;
    private JComboBox<String> yearCombo;

    // Ticket type combo box
    private JComboBox<String> ticketTypeCombo;

    private ArtGalleryVisitor currentVisitor;

    public ArtGalleryVisitorGUI() {
        initComponents();
        createGUI();
    }

    private void initComponents() {
        // Text fields
        visitorIdField = new JTextField(10);
        fullNameField = new JTextField(20);
        contactNumberField = new JTextField(15);
        ticketCostField = new JTextField(10);
        ticketCostField.setEditable(false);
        artworkNameField = new JTextField(20);
        artworkPriceField = new JTextField(10);
        cancellationReasonField = new JTextField(20);

        // Radio buttons for gender
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        otherRadio = new JRadioButton("Other");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderGroup.add(otherRadio);

        // Date combo boxes
        dayCombo = new JComboBox<>();
        for (int i = 1; i <= 31; i++) dayCombo.addItem(String.valueOf(i));

        monthCombo = new JComboBox<>();
        String[] months = {"January", "February", "March", "April", "May", "June", 
                "July", "August", "September", "October", "November", "December"};
        for (String month : months) monthCombo.addItem(month);

        yearCombo = new JComboBox<>();
        for (int i = 2020; i <= 2025; i++) yearCombo.addItem(String.valueOf(i));

        // Ticket type combo box
        ticketTypeCombo = new JComboBox<>(new String[]{"Standard", "Elite"});
        ticketTypeCombo.addActionListener(e -> updateTicketCost());

        // Output area
        outputArea = new JTextArea(15, 80);
        outputArea.setEditable(false);
    }

    private void createGUI() {
        // Create main frame
        frame = new JFrame("Avijeet Art Gallery Visitor Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLayout(new BorderLayout());

        // Create main panel with light sky blue background
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(135, 206, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create header
        JLabel headerLabel = new JLabel("Avijeet Art Gallery Visitor", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(70, 130, 180));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Create content panel (70% height)
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        contentPanel.setBackground(new Color(135, 206, 250));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Create input panel (70% width)
        inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBackground(new Color(135, 206, 250));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.DARK_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

        // Add input fields
        addInputField("Visitor ID:", visitorIdField);
        addInputField("Full Name:", fullNameField);

        // Gender radio buttons panel
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        genderPanel.add(otherRadio);
        genderPanel.setBackground(new Color(135, 206, 250));
        inputPanel.add(new JLabel("Gender:"));
        inputPanel.add(genderPanel);

        addInputField("Contact Number:", contactNumberField);

        // Date panel
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.add(dayCombo);
        datePanel.add(monthCombo);
        datePanel.add(yearCombo);
        datePanel.setBackground(new Color(135, 206, 250));
        inputPanel.add(new JLabel("Registration Date:"));
        inputPanel.add(datePanel);

        inputPanel.add(new JLabel("Visitor Type:"));
        inputPanel.add(ticketTypeCombo);

        addInputField("Ticket Cost:", ticketCostField);
        addInputField("Artwork Name:", artworkNameField);
        addInputField("Artwork Price:", artworkPriceField);
        addInputField("Cancellation Reason:", cancellationReasonField);

        // Create output panel (30% width)
        outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBackground(new Color(135, 206, 250));
        outputPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.DARK_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        // Add panels to content panel with proper ratios
        JPanel leftWrapper = new JPanel(new BorderLayout());
        leftWrapper.add(inputPanel, BorderLayout.CENTER);
        leftWrapper.setPreferredSize(new Dimension((int)(frame.getWidth() * 0.7), 0));

        JPanel rightWrapper = new JPanel(new BorderLayout());
        rightWrapper.add(outputPanel, BorderLayout.CENTER);
        rightWrapper.setPreferredSize(new Dimension((int)(frame.getWidth() * 0.3), 0));

        contentPanel.add(leftWrapper);
        contentPanel.add(rightWrapper);

        // Create button panel (30% height)
        buttonPanel = createButtonPanel();

        // Add components to main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 3, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Actions"));
        panel.setBackground(new Color(135, 206, 250));

        addButton(panel, "Add Visitor", e -> addVisitor());
        addButton(panel, "Log Visit", e -> logVisit());
        addButton(panel, "Buy Product", e -> buyProduct());
        addButton(panel, "Assign Personal Art Advisor", e -> assignPersonalArtAdvisor());
        addButton(panel, "Check Upgrade", e -> checkUpgrade());
        addButton(panel, "Calculate Discount", e -> calculateDiscount());
        addButton(panel, "Calculate Reward Points", e -> calculateRewardPoints());
        addButton(panel, "Cancel Product", e -> cancelProduct());
        addButton(panel, "Generate Bill", e -> generateBill());
        addButton(panel, "Display Visitor Details", e -> displayVisitorDetails());
        addButton(panel, "Clear Fields", e -> clearFields());
        addButton(panel, "Save to File", e -> saveToFile());
        addButton(panel, "Read from File", e -> readFromFile());

        return panel;
    }

    private void addInputField(String label, JComponent field) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        inputPanel.add(jLabel);
        inputPanel.add(field);
    }

    private void addButton(JPanel panel, String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        button.setPreferredSize(new Dimension(150, 30));
        panel.add(button);
    }

    private void updateTicketCost() {
        String ticketType = (String) ticketTypeCombo.getSelectedItem();
        if ("Standard".equals(ticketType)) {
            ticketCostField.setText("1000");
        } else {
            ticketCostField.setText("2000");
        }
    }

    // Action methods
    private void addVisitor() {
        try {
            int visitorId = Integer.parseInt(visitorIdField.getText());
            String fullName = fullNameField.getText();
            String gender = maleRadio.isSelected() ? "Male" : 
                femaleRadio.isSelected() ? "Female" : "Other";
            String contactNumber = contactNumberField.getText();
            String visitorType = (String) ticketTypeCombo.getSelectedItem();
            double ticketCost = Double.parseDouble(ticketCostField.getText());

            String day = (String) dayCombo.getSelectedItem();
            String month = (String) monthCombo.getSelectedItem();
            String year = (String) yearCombo.getSelectedItem();
            String registrationDate = day + " " + month + " " + year;

            if (visitorType.equals("Standard")) {
                currentVisitor = new StandardVisitor(visitorId, fullName, gender, contactNumber, 
                    registrationDate, ticketCost, visitorType);
            } else {
                currentVisitor = new EliteVisitor(visitorId, fullName, gender, contactNumber, 
                    registrationDate, ticketCost, visitorType);
            }

            outputArea.append("Visitor created successfully!\n");
            outputArea.append("Registration Date: " + registrationDate + "\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter valid numeric values for Visitor ID and Ticket Cost", 
                "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logVisit() {
        if (currentVisitor != null) {
            currentVisitor.logVisit();
            outputArea.append("\nVisit logged successfully!\n");
            outputArea.append("Total visits: " + currentVisitor.getVisitCount() + "\n");

            if (currentVisitor instanceof StandardVisitor) {
                StandardVisitor sv = (StandardVisitor) currentVisitor;
                if (sv.checkDiscountUpgrade()) {
                    outputArea.append("Congratulations! You're now eligible for 15% discount.\n");
                }
            } else if (currentVisitor instanceof EliteVisitor) {
                EliteVisitor ev = (EliteVisitor) currentVisitor;
                if (ev.assignPersonalArtAdvisor()) {
                    outputArea.append("Personal Art Advisor assigned!\n");
                }
                if (ev.exclusiveEventAccess()) {
                    outputArea.append("You now have exclusive event access!\n");
                }
            }
        } else {
            outputArea.append("No visitor created yet. Please create a visitor first.\n");
        }
    }

    private void buyProduct() {
        if (currentVisitor != null) {
            String artworkName = artworkNameField.getText();
            try {
                double artworkPrice = Double.parseDouble(artworkPriceField.getText());

                String result = currentVisitor.buyProduct(artworkName, artworkPrice);
                outputArea.append("\n" + result + "\n");

                if (result.contains("successfully")) {
                    double discount = Math.round(currentVisitor.calculateDiscount());
                    double rewardPoints = Math.round(currentVisitor.calculateRewardPoints());

                    outputArea.append("Discount Applied: " + (int)discount + "\n");
                    outputArea.append("Final Price: " + (int)currentVisitor.getFinalPrice() + "\n");
                    outputArea.append("Reward Points Earned: " + (int)rewardPoints + "\n");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid artwork price", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            outputArea.append("No visitor created yet. Please create a visitor first.\n");
        }
    }

    private void assignPersonalArtAdvisor() {
        if (currentVisitor instanceof EliteVisitor) {
            EliteVisitor ev = (EliteVisitor) currentVisitor;
            if (ev.assignPersonalArtAdvisor()) {
                outputArea.append("\nPersonal Art Advisor assigned successfully!\n");
            } else {
                outputArea.append("\nNot enough reward points for Personal Art Advisor (need 5000+)\n");
            }
        } else {
            outputArea.append("\nOnly Elite visitors can have Personal Art Advisors\n");
        }
    }

    private void checkUpgrade() {
        if (currentVisitor instanceof StandardVisitor) {
            StandardVisitor sv = (StandardVisitor) currentVisitor;
            if (sv.checkDiscountUpgrade()) {
                outputArea.append("\nYou are eligible for discount upgrade (15%)\n");
            } else {
                outputArea.append("\nYou need " + (sv.getVisitLimit() - sv.getVisitCount()) + 
                    " more visits for discount upgrade\n");
            }
        } else {
            outputArea.append("\nUpgrade check only applies to Standard visitors\n");
        }
    }

    private void calculateDiscount() {
        if (currentVisitor != null) {
            double discount = Math.round(currentVisitor.calculateDiscount());
            outputArea.append("\nCalculated Discount: " + (int)discount + "\n");
            outputArea.append("Final Price: " + (int)currentVisitor.getFinalPrice() + "\n");
        } else {
            outputArea.append("\nNo visitor created yet. Please create a visitor first.\n");
        }
    }

    private void calculateRewardPoints() {
    if (currentVisitor != null) {
        // Calculate base reward points
        double rewardPoints = currentVisitor.calculateRewardPoints();
        
        // For refund case, we need to check if there's a cancellation
        if (currentVisitor.getCancelCount() > 0) {
            // Deduct points for cancelled items (finalPrice * 5)
            double pointsToDeduct = currentVisitor.getFinalPrice() * 5;
            rewardPoints -= pointsToDeduct;
            
            outputArea.append("\nReward Points After Deduction: " + (int)rewardPoints + "\n");
            outputArea.append("(Deducted " + (int)pointsToDeduct + " points for cancellation)\n");
        } else {
            // Normal reward points calculation
            rewardPoints = Math.round(rewardPoints);
            outputArea.append("\nTotal Reward Points: " + (int)rewardPoints + "\n");
        }
    } else {
        outputArea.append("\nNo visitor created yet. Please create a visitor first.\n");
    }
}

    private void cancelProduct() {
        if (currentVisitor != null) {
            String artworkName = artworkNameField.getText();
            String cancellationReason = cancellationReasonField.getText();

            if (cancellationReason.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a cancellation reason", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String result = currentVisitor.cancelProduct(artworkName, cancellationReason);
            outputArea.append("\n" + result + "\n");

            if (result.contains("Refund")) {
                outputArea.append("Remaining Reward Points: " + (int)currentVisitor.getRewardPoints() + "\n");
                outputArea.append("Cancellation Count: " + currentVisitor.getCancelCount() + "/" + 
                    currentVisitor.getCancelLimit() + "\n");
            }
        } else {
            outputArea.append("\nNo visitor created yet. Please create a visitor first.\n");
        }
    }

    private void generateBill() {
        if (currentVisitor != null) {
            outputArea.append("\n===== BILL GENERATION =====\n");
            outputArea.append("Visitor ID: " + currentVisitor.getVisitorId() + "\n");
            outputArea.append("Full Name: " + currentVisitor.getFullName() + "\n");
            outputArea.append("Gender: " + currentVisitor.getGender() + "\n");
            outputArea.append("Contact Number: " + currentVisitor.getContactNumber() + "\n");
            outputArea.append("Registration Date: " + currentVisitor.getRegistrationDate() + "\n");
            outputArea.append("Ticket Type: " + currentVisitor.getTicketType() + "\n");
            outputArea.append("Ticket Cost: " + (int)currentVisitor.getTicketCost() + "\n");
            outputArea.append("Visit Count: " + currentVisitor.getVisitCount() + "\n");
            outputArea.append("Reward Points: " + (int)currentVisitor.getRewardPoints() + "\n");
            
            if (currentVisitor instanceof StandardVisitor) {
                StandardVisitor sv = (StandardVisitor) currentVisitor;
                outputArea.append("Discount Percent: " + (int)(sv.getDiscountPercent() * 100) + "%\n");
            }
            
            outputArea.append("===== END OF BILL =====\n");
        } else {
            outputArea.append("\nNo visitor created yet. Please create a visitor first.\n");
        }
    }

    private void displayVisitorDetails() {
        if (currentVisitor != null) {
            outputArea.append("\n===== VISITOR DETAILS =====\n");
            outputArea.append("Visitor ID: " + currentVisitor.getVisitorId() + "\n");
            outputArea.append("Full Name: " + currentVisitor.getFullName() + "\n");
            outputArea.append("Gender: " + currentVisitor.getGender() + "\n");
            outputArea.append("Contact Number: " + currentVisitor.getContactNumber() + "\n");
            outputArea.append("Registration Date: " + currentVisitor.getRegistrationDate() + "\n");
            outputArea.append("Ticket Cost: " + (int)currentVisitor.getTicketCost() + "\n");
            outputArea.append("Ticket Type: " + currentVisitor.getTicketType() + "\n");
            outputArea.append("Visit Count: " + currentVisitor.getVisitCount() + "\n");
            outputArea.append("Reward Points: " + (int)currentVisitor.getRewardPoints() + "\n");
            outputArea.append("Is Active: " + currentVisitor.getIsActive() + "\n");

            if (currentVisitor instanceof StandardVisitor) {
                StandardVisitor sv = (StandardVisitor) currentVisitor;
                outputArea.append("Eligible for Discount Upgrade: " + sv.isEligibleForDiscountUpgrade() + "\n");
                outputArea.append("Discount Percent: " + (int)(sv.getDiscountPercent() * 100) + "%\n");
            } else if (currentVisitor instanceof EliteVisitor) {
                EliteVisitor ev = (EliteVisitor) currentVisitor;
                outputArea.append("Personal Art Advisor: " + ev.isAssignedPersonalArtAdvisor() + "\n");
                outputArea.append("Exclusive Event Access: " + ev.hasExclusiveEventAccess() + "\n");
            }
        } else {
            outputArea.append("\nNo visitor created yet. Please create a visitor first.\n");
        }
    }

    private void clearFields() {
        visitorIdField.setText("");
        fullNameField.setText("");
        contactNumberField.setText("");
        ticketCostField.setText("");
        artworkNameField.setText("");
        artworkPriceField.setText("");
        cancellationReasonField.setText("");
        maleRadio.setSelected(false);
        femaleRadio.setSelected(false);
        otherRadio.setSelected(false);
        dayCombo.setSelectedIndex(0);
        monthCombo.setSelectedIndex(0);
        yearCombo.setSelectedIndex(0);
        ticketTypeCombo.setSelectedIndex(0);
    }

    private void saveToFile() {
        // Implementation for saving to file would go here
        outputArea.append("\nSave to file functionality would be implemented here\n");
    }

    private void readFromFile() {
        // Implementation for reading from file would go here
        outputArea.append("\nRead from file functionality would be implemented here\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ArtGalleryVisitorGUI());
    }
}