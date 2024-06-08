/*
 * The RestaurantController class is the controller for the restaurant GUI.
 * It handles the initialization of the menu, clearing the selection of menu items, and displaying the menu.
 * @author Elad Reuveny
 */
package q2;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class RestaurantController {
    /**
     * The name of the file containing the menu.
     */
    private final String FILE_NAME = "menu.txt";

    /**
     * An array of the different types of menu items
     */
    private final String[] ITEM_TYPES = {"First Course", "Main Course", "Last Course", "Drink"};

    /**
     * An array list containing all the menu items.
     */
    private final ArrayList<q2.MenuItem> menu = new ArrayList<q2.MenuItem>();

    /**
     * The VBox containing the menu items.
     */
    @FXML
    private VBox menuBox;

    /**
     * The button used to submit an order.
     */
    @FXML
    private Button orderButton;

    /**
     * An array list containing the currently selected menu items.
     */
    private ArrayList<q2.MenuItem> selectedItems;

    /**
     * Initializes the controller by initializing the menu, clearing the selection, and displaying the menu.
     */
    public void initialize() {
        initializeMenu();
        clearSelection();
        displayMenu();
    }

    /**
     * Initializes the menu by reading in menu items from a file and adding them to the menu ArrayList.
     * If the file is not found, prints an error message.
     */
    private void initializeMenu() {
        try {
            Scanner input = new Scanner(new File(FILE_NAME));
            while (input.hasNext()) {
                String description = input.nextLine();
                String courseType = input.nextLine();
                double price = Double.parseDouble(input.nextLine());
                q2.MenuItem item = new q2.MenuItem(description, courseType, price, 1);
                menu.add(item);
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    /**
     * Clears the selection by resetting all CheckBoxes to unselected and all ComboBoxes to their first item.
     * Also resets the selectedItems ArrayList.
     */
    private void clearSelection() {
        selectedItems = new ArrayList<q2.MenuItem>();

        for (Node node : menuBox.getChildren()) {
            if (node instanceof CheckBox) {
                ((CheckBox) node).setSelected(false);
            } else if (node instanceof ComboBox) {
                ((ComboBox) node).getSelectionModel().selectFirst();
            }
        }
    }

    /**
     * Displays the menu items in the UI by creating a CheckBox and ComboBox for each item in the menu.
     * When a CheckBox is selected, the corresponding MenuItem object is added to the selectedItems list and its quantity is updated
     * based on the value selected in the ComboBox. When a CheckBox is deselected, the corresponding MenuItem object is removed
     * from the selectedItems list and its quantity is reset to zero. When a ComboBox value is changed, the quantity of the
     * corresponding MenuItem object is updated.
     */
    @FXML
    private void displayMenu() {
        menuBox.setAlignment(Pos.TOP_RIGHT);
        orderButton.setAlignment(Pos.TOP_RIGHT);

        for (String itemType : ITEM_TYPES) {
            Label typeLabel = new Label(itemType + ":");
            menuBox.getChildren().add(typeLabel);

            for (q2.MenuItem item : menu) {
                if (item.getCourseType().equals(itemType)) {
                    CheckBox itemCheckBox = new CheckBox(item.getDescription() + " ($" + item.getPrice() + ")");
                    ComboBox<Integer> quantityComboBox = new ComboBox<Integer>
                            (FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
                    quantityComboBox.getSelectionModel().selectFirst();

                    // Setting on action function with lambda.
                    itemCheckBox.setOnAction((event) -> {
                        if (itemCheckBox.isSelected()) {
                            selectedItems.add(item);
                            item.setQuantity(quantityComboBox.getValue()); // Update quantity when item is selected
                        } else {
                            selectedItems.remove(item);
                            item.setQuantity(0); // Reset quantity when item is deselected
                        }
                    });

                    // Setting on action function with lambda.
                    quantityComboBox.setOnAction((event) -> {
                        if (itemCheckBox.isSelected()) {
                            item.setQuantity(quantityComboBox.getValue()); // Update quantity when ComboBox value is changed
                        }
                    });
                    menuBox.getChildren().addAll(itemCheckBox, quantityComboBox);
                }
            }
        }
    }

    /**
     * Event handler for the "Display Menu" button.
     * Displays a menu with check boxes and quantity ComboBoxes for each menu item
     * selected by the user.
     * Once the user selects their items and presses "Order", displays an alert
     * with the order details and prompts the user to enter their name and ID.
     * Creates a file with the user's name and ID and writes the order details to the file.
     * Clears the selected items once the order has been placed or cancelled.
     *
     * @param event The event object generated by pressing the "Display Menu" button.
     */
    @FXML
    void displayMenuPressed(ActionEvent event) {
        StringBuilder message = new StringBuilder();
        double sum = 0;
        message.append("    *Course type* \t *Description* \t *Price* \t *Quantity* \t *Price* \n");

        // Iterate through the selected items and append their details to the message.
        for (MenuItem item : selectedItems) {
            message.append("(").append(selectedItems.indexOf(item) + 1).append(") ")
                    .append(item.getCourseType()).append("\t   ")
                    .append(item.getDescription()).append("\t \t  ")
                    .append(item.getPrice()).append("\t \t  ")
                    .append(item.getQuantity()).append("\t \t  ")
                    .append(item.getPrice() * item.getQuantity()).append("\n");
            sum += item.getPrice() * item.getQuantity();
        }

        // Create a new alert to display the order details.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order information");
        alert.setHeaderText("Order Details: ");
        message.append("\nTotal price:\t\t\t\t\t\t\t\t\t\t**").append(sum).append("**");
        alert.setContentText(message.toString());

        // Add buttons to the alert.
        ButtonType confirmButton = new ButtonType("אישור ההזמנה");
        ButtonType updateButton = new ButtonType("עדכון ההזמנה");
        ButtonType cancelButton = new ButtonType("ביטול ההזמנה");
        ButtonType exitButton = new ButtonType("יציאה", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirmButton, updateButton, cancelButton, exitButton);

        // Show the alert and wait for the user to confirm.
        alert.showAndWait();

        // If the user clicks the "אישור ההזמנה" button.
        if (alert.getResult() == confirmButton) {
            // Create a new text field for the user to enter their name and ID
            TextField nameAndID = new TextField();
            nameAndID.setPromptText("Please enter your name and ID");
            alert.setTitle("Customer info");
            alert.setHeaderText("To finish the order, insert your details here: ");
            alert.getDialogPane().setContent(nameAndID);
            alert.getButtonTypes().setAll(new ButtonType("אישור", ButtonBar.ButtonData.APPLY));
            alert.showAndWait();

            // Write the order details to a file named, after the user has inserted his name and ID.
            try
            {
                FileWriter fileWriter = new FileWriter(nameAndID.getText() + ".txt");
                fileWriter.write(nameAndID.getText() + "'s order: \n\n" + message);
                fileWriter.close();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Order Confirmation");
                alert.setHeaderText("Order has been placed successfully!");
                alert.setContentText("Thank you for your order, " + nameAndID.getText() + ".\nYour order details have been saved to a file.");
                alert.getButtonTypes().setAll(new ButtonType("לסיום", ButtonBar.ButtonData.FINISH));
                alert.showAndWait();
                clearSelection();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        // Close the alert, and allows the customer to keep ordering from where he stopped.
        else if (alert.getResult() == updateButton)
            alert.close();

            // Clearing the selection of all items to their default values, and resetting the current order.
        else if (alert.getResult() == cancelButton)
            clearSelection(); //
    }
}
