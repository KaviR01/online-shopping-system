package com.rasanjana.westminster.shop;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    public static ArrayList<Product> products = new ArrayList<>();
    private static final int MAX_PRODUCTS = 50;

    public void ConsoleMenu(){
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("------------------------------------------------");
            System.out.println("********* Westminster Shopping Manager *********");
            System.out.println("------------------------------------------------");
            System.out.println("Please select an option:");
            System.out.println("""
                    1) Add Product
                    2) Delete Product
                    3) Print Product List
                    4) Save Products
                    5) Read Products
                    6) View GUI
                    7) Exit""");
            System.out.println("------------------------------------------------");
            System.out.println("------------------------------------------------");
            System.out.print("Enter your option: ");
            int choice = validateMenuChoice(scanner);

            switch (choice) {
                case 1 -> addProductDetails();
                case 2 -> deleteProduct();
                case 3 -> printProduct();
                case 4 -> saveProduct();
                case 5 -> readProduct();
                case 6 -> new Shop();
                case 7 -> {
                    System.out.println("******* Thank You!!! Have a nice day!!! *******");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    @Override
    public void addProductDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product ID: ");
        String productID = validateProductID(scanner);
        System.out.print("Enter product Name: ");
        String productName = validateProductName(scanner);
        System.out.print("Enter available Items: ");
        int availableItems = validateAvailableItems(scanner);
        System.out.print("Enter price: $ ");
        double price = validatePrice(scanner);
        System.out.println("Select the type of product to add: ");
        System.out.println("1)Electronics");
        System.out.println("2)Clothing");
        int choice;
        while(true){
            System.out.print("Enter your choice: ");
            try {
                choice = scanner.nextInt();
                if (choice == 1 || choice == 2) {
                    break; // Valid choice, exit the loop
                } else {
                    System.out.println("Invalid choice. Please enter 1 for Electronics or 2 for Clothing : ");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }


        Scanner scan = new Scanner(System.in);
        if (choice == 1) {
            System.out.print("Enter brand: ");
            String brand = validateBrand(scan);
            System.out.print("Enter warranty period: ");
            double warrantyPeriod = validateWarrantyPeriod(scan);

            if (products.size()<MAX_PRODUCTS){
                Electronics electronicsProduct = new Electronics(productID, productName, availableItems, price, brand, warrantyPeriod);
                products.add(electronicsProduct);
                System.out.println("Electronics product added successfully!");
            }else{
                System.out.println("Maximum number of products reached. Cannot add more than 50 products.");
            }

        } else {
            System.out.print("Enter size (XS,S,M,L,XL,2XL,3Xl): ");
            String size = validateSize(scan);
            System.out.print("Enter color: ");
            String color = validateColor(scan);
            if ((products.size()< MAX_PRODUCTS)){
                Clothing clothingProduct = new Clothing(productID, productName, availableItems, price, size, color);
                products.add(clothingProduct);
                System.out.println("Clothing product added successfully!");
            }else{
                System.out.println("Maximum number of products reached. Cannot add more than 50 products.");
            }

        }
    }

    @Override
    public void deleteProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product ID to delete: ");
        String productID = scanner.nextLine();
        if (productID.isEmpty()) {
            System.out.println("Product ID cannot be empty. Please enter a valid product ID.");
            return; // Exit the method
        }

        boolean productFound = false;
        int indexToRemove = -1;

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (product.getProductID().equals(productID)) {
                productFound = true;
                indexToRemove = i;
                break;
            }
        }

        if (productFound) {
            Product deletedProduct = products.remove(indexToRemove);
            System.out.println(deletedProduct.getClass().getSimpleName() + " product with ID " + productID + " deleted successfully.");
            System.out.println("Total products remaining: " + products.size());
        } else {
            System.out.println("Product not found.");
        }


    }

    @Override
    public void printProduct() {
        if (products.isEmpty()) {
            System.out.println("No products in the system.");
            return;
        }

        // Sort products alphabetically by product ID
        products.sort(Comparator.comparing(Product::getProductID));

        System.out.println("---------------------------------");
        System.out.println("---------- PRODUCT LIST ---------");
        for (Product product : products) {
            System.out.println("Product ID: " + product.getProductID());
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Product Type: " + product.getCategory()); // Display product type (electronics or clothing)
            System.out.println("Available Items: " + product.getAvailableItems());
            System.out.println("Price: $ " + product.getPrice());
            if(product instanceof Electronics){
                System.out.println("warranty: "+((Electronics) product).getWarrantyPeriod());
                System.out.println("Brand: "+((Electronics) product).getBrand());
            }
            if(product instanceof Clothing){
                System.out.println("Size: "+((Clothing) product).getSize());
                System.out.println("Color: "+((Clothing) product).getColor());
            }
            System.out.println("---------------------------------");
        }

    }

    @Override
    public void saveProduct() {
        try {
            FileWriter fileWriter = new FileWriter("products.txt");
            for (Product product : products) {
                fileWriter.write(
                        product.getProductID() + "," +
                        product.getProductName() + "," +
                        product.getAvailableItems() + "," +
                        product.getPrice() + "," +
                        product.getCategory()+","+
                        (product instanceof Electronics ? ((Electronics) product).getBrand() : ((Clothing) product).getSize()) + "," +
                        (product instanceof Electronics ? ((Electronics) product).getWarrantyPeriod() : ((Clothing) product).getColor()) + "\n");
            }
            fileWriter.close();
            System.out.println("Products saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }
    @Override
    public void readProduct() {
        try {
            File file = new File("products.txt");
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String[] productData = scanner.nextLine().split(",");

                    // Handle potential errors in file format
                    if (productData.length != 7) {
                        System.out.println("Invalid product data format in file: " + Arrays.toString(productData));
                        continue; // Skip to the next line if invalid data
                    }

                    // Parse product information and create the appropriate product object
                    try {
                        String productID = productData[0];
                        String productName = productData[1];
                        int availableItems = Integer.parseInt(productData[2]);
                        double price = Double.parseDouble(productData[3]);
                        String category = productData[4];

                        Product product;
                        if ("Electronics".equals(category)) {
                            String brand = productData[5];
                            double warrantyPeriod = Double.parseDouble(productData[6]);
                            product = new Electronics(productID, productName, availableItems, price, brand, warrantyPeriod);
                        } else if ("Clothing".equals(category)) {
                            String size = productData[5];
                            String color = productData[6];
                            product = new Clothing(productID, productName, availableItems, price, size, color);
                        } else {
                            System.out.println("Invalid product category: " + category);
                            continue; // Skip to the next line if the category is invalid
                        }

                        products.add(product);
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing data: " + e.getMessage());
                    }
                }
                scanner.close();
                System.out.println("Products loaded successfully!");
            } else {
                System.out.println("Product file not found.");
            }
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.ConsoleMenu();
    }


    // Validate Menu Choice input
    public int validateMenuChoice(Scanner scanner) {
        while (true) {
            try {
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= 7) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please try again.");
                scanner.next(); // Consume the invalid input
            }
        }

    }
    // Validate product ID input
    public String validateProductID(Scanner scanner) {
        while (true) {
            String productID = scanner.nextLine().trim();
            if (!productID.isEmpty() && productID.matches("[a-zA-Z0-9]{1,10}") && !isDuplicateProductID(productID)) {
                return productID;
            } else {
                if (isDuplicateProductID(productID)) {
                    System.out.print("Product ID already exists. Please enter a different ID: ");
                } else {
                    System.out.print("Invalid product ID format. Please enter a valid alphanumeric ID (up to 10 characters): ");
                }
            }
        }
    }

    public boolean isDuplicateProductID(String productID) {
        for (Product product : products) {
            if (product.getProductID().equals(productID)) {
                return true;
            }
        }
        return false;
    }

    // Validate product name input
    public String validateProductName(Scanner scanner) {
        while (true) {
            String productName = scanner.nextLine().trim();
            if (!productName.isEmpty() && productName.matches("[a-zA-Z\\s]+")) {
                return productName;
            } else {
                System.out.println("""
                        **Invalid format**
                        **Product name should be letters**
                        Please enter a valid Product name:\s""");
            }
        }
    }

    // Validate price input
    public double validatePrice(Scanner scanner) {
        while (true) {
            try {
                double price = Double.parseDouble(scanner.next());
                if (price > 0) {
                    return price;
                } else {
                    System.out.print("Invalid price. Please enter a positive value: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid numeric value for the price: ");
                scanner.next(); // Consume the invalid input
            }
        }
    }

    // Validate size input
    public String validateSize(Scanner scanner) {
        while (true) {
            String size = scanner.nextLine().trim().toUpperCase(); // Convert to uppercase for case-insensitive comparison
            if (size.matches("XS|S|M|L|XL|2XL|3XL")) {
                return size;
            } else {
                System.out.print("Invalid size. Please enter a valid size (XS, S, M, L, XL, 2XL, 3XL): ");
            }
        }
    }

    // Validate color input
    private String validateColor(Scanner scanner) {
        while (true) {
            String color = scanner.next().trim();
            if (!color.isEmpty() && color.matches("[a-zA-Z]+")) {
                return color;
            } else {
                System.out.print("Invalid color. Please enter a non-empty color containing only letters: ");
            }
        }
    }

    // Validate brand input
    public String validateBrand(Scanner scanner) {
        while (true) {
            String brand = scanner.nextLine().trim();
            if (!brand.isEmpty() && brand.matches("[a-zA-Z]+")) {
                return brand;
            } else {
                System.out.print("Invalid brand. Please enter a non-empty brand containing only letters: ");
            }
        }
    }

    // Validate warranty period input
    public double validateWarrantyPeriod(Scanner scanner) {
        while (true) {
            try {
                double warrantyPeriod = Double.parseDouble(scanner.next());
                if (warrantyPeriod >= 0) {
                    return warrantyPeriod;
                } else {
                    System.out.print("Invalid warranty period. Please enter a non-negative value: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    // Validate available items input
    private int validateAvailableItems(Scanner scanner) {
        while (true) {
            try {
                int availableItems = Integer.parseInt(scanner.nextLine());
                if (availableItems >= 0) {
                    return availableItems;
                } else {
                    System.out.print("Invalid input. Please enter a non-negative number for available items: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }

}
