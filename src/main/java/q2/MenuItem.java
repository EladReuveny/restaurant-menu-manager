/*
 * Represents a menu item in a restaurant.
 * @author Elad Reuveny
 */
package q2;

class MenuItem {
    /**
     * The description of the menu item.
     */
    private String description;

    /**
     * The course type of the menu item.
     */
    private String courseType;

    /**
     * The price of the menu item.
     */
    private double price;

    /**
     * The quantity of the menu item.
     */
    private int quantity;


    /**
     * Constructs a new MenuItem object.
     *
     * @param description the description of the menu item
     * @param courseType  the type of the course (appetizer, main course, dessert, etc.)
     * @param price       the price of the menu item
     * @param quantity    the quantity of the menu item
     */
    public MenuItem(String description, String courseType, double price, int quantity) {
        this.description = description;
        this.courseType = courseType;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Returns the description of the menu item.
     *
     * @return the description of the menu item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the type of the course (appetizer, main course, dessert, etc.) of the menu item.
     *
     * @return the type of the course
     */
    public String getCourseType() {
        return courseType;
    }

    /**
     * Returns the price of the menu item.
     *
     * @return the price of the menu item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the quantity of the menu item.
     *
     * @return the quantity of the menu item
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the menu item.
     *
     * @param quantity the new quantity of the menu item
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
