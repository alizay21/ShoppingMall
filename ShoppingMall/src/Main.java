import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Item {
    String name;
    int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }
}

class Shop {
    String name;
    Item[] items;

    public Shop(String name, Item[] items) {
        this.name = name;
        this.items = items;
    }
}

class Mall {
    private static final String actualUsername = "user123";
    private static final String actualPassword = "abc123";

    private static final int MAX_SHOPS = 4;
    private static final int MAX_ITEMS = 3;

    private static final Shop[] shops = {
            new Shop("clothes", new Item[]{new Item("baby clothes", 2000), new Item("lady clothes", 3000), new Item("gent clothes", 2500)}),
            new Shop("mobiles", new Item[]{new Item("Nokia", 10000), new Item("Samsung", 35000), new Item("iPhone", 70000)}),
            new Shop("jewelry", new Item[]{new Item("diamond", 100000), new Item("gold", 50000), new Item("silver", 30000)}),
            new Shop("shoes", new Item[]{new Item("school shoes", 1600), new Item("ladies shoes", 2000), new Item("sports shoes", 4000)})
    };

    private String username;
    private String password;

    private int bill = 0;
    private int totalBoughtItems = 0;
    private String[] boughtItems = new String[50];

    private Scanner scanner = new Scanner(System.in);

    private boolean isValidLogin(String name, String password) {
        return name.equals(actualUsername) && password.equals(actualPassword);
    }

    private boolean validateUser() {
        System.out.print("Enter username: ");
        username = scanner.next();
        System.out.print("Enter password: ");
        password = scanner.next();
        return isValidLogin(username, password);
    }

    private void displayBill() {
        System.out.println("\nThank you for shopping");
        System.out.println("Your current total bill is: " + bill + "\n");
    }

    private void displayBoughtItems() {
        System.out.println("\nList of items are:");
        if (totalBoughtItems == 0) {
            System.out.println("No items bought\n");
        } else {
            for (int i = 0; i < totalBoughtItems; i++) {
                System.out.println((i + 1) + ". " + boughtItems[i]);
            }
        }
        System.out.println();
    }

    private void saveShoppingHistory() {
        try {
            FileWriter writer = new FileWriter("ShoppingHistory.txt", true);
            writer.write("### List of bought items ###:\n");
            if (totalBoughtItems == 0) {
                writer.write("No items bought\n");
            } else {
                for (int i = 0; i < totalBoughtItems; i++) {
                    writer.write((i + 1) + ". " + boughtItems[i] + "\n");
                }
            }
            writer.write("\nTotal Bill is: " + bill + "\n\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearShoppingHistory() {
        try {
            FileWriter writer = new FileWriter("ShoppingHistory.txt");
            writer.close();
            System.out.println("Shopping history cleared\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayShoppingHistory() {
        System.out.println("Shopping History: \n");
        try {
            Scanner fileScanner = new Scanner(new java.io.File("ShoppingHistory.txt"));
            while (fileScanner.hasNextLine()) {
                System.out.println(fileScanner.nextLine());
            }
            fileScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shopAtMall() {
        while (!validateUser()) {
        }

        System.out.println("\n\t\t_*WELCOME TO SHOPPING MALL*_\n");

        boolean isShopping = true;

        while (isShopping) {
            System.out.println("Press 1 to buy an item\nPress 2 for current total bill\nPress 3 to display the list of current bought items\nPress 4 to display shopping history\nPress 5 to clear shopping history\nPress 6 to exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    buyItem();
                    break;
                case 2:
                    displayBill();
                    break;
                case 3:
                    displayBoughtItems();
                    break;
                case 4:
                    displayShoppingHistory();
                    break;
                case 5:
                    clearShoppingHistory();
                    break;
                case 6:
                    isShopping = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.\n");
            }
        }
        if (totalBoughtItems > 0) {
            saveShoppingHistory();
        }
        System.out.println("\nThank you for shopping.\n");
    }

    private void buyItem() {
        for (int s = 0; s < MAX_SHOPS; s++) {
            System.out.println("\t Press " + s + " for " + shops[s].name);
        }
        int choiceShop = scanner.nextInt();
        if (choiceShop < 0 || choiceShop >= MAX_SHOPS) {
            System.out.println("Invalid shop choice. Try again.\n");
            return;
        }

        for (int i = 0; i < MAX_ITEMS; i++) {
            System.out.println("\t\t Press " + i + " for " + shops[choiceShop].items[i].name);
        }
        int choiceItem = scanner.nextInt();
        if (choiceItem < 0 || choiceItem >= MAX_ITEMS) {
            System.out.println("Invalid item choice. Try again.\n");
            return;
        }

        System.out.println("\t\t\t Price of *" + shops[choiceShop].items[choiceItem].name + "* is " + shops[choiceShop].items[choiceItem].price);
        System.out.println("\t\t\t If you want to buy this item, press Y. Press any other key to cancel");
        char confirmation = scanner.next().charAt(0);
        if (confirmation == 'Y' || confirmation == 'y') {
            bill += shops[choiceShop].items[choiceItem].price;
            boughtItems[totalBoughtItems] = shops[choiceShop].items[choiceItem].name;
            totalBoughtItems++;
            displayBill();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Mall mall = new Mall();
        mall.shopAtMall();
    }
}