package com.rasanjana.westminster.shop;
import java.util.ArrayList;


public class ShoppingCart{
    private ArrayList<Product> productsCart;
    private ArrayList <Integer> quantity;
    public ShoppingCart(){
        this.productsCart = new ArrayList<>();
        this.quantity = new ArrayList<>();
    }
    //method to add products to the shopping cart
    public void addProduct(Product product) {
        int index = productsCart.indexOf(product); //get the index of the product in the shopping cart
        if (index != -1){  //if the product is already in the shopping cart
            quantity.set(index, quantity.get(index) + 1);//increment the quantity of the product by 1
        }else {
            productsCart.add(product); //add the product to the shopping cart
            quantity.add(1);//set the quantity of the product to 1
        }
    }
    //method to remove products from the shopping cart
    public void deleteProduct(Product product) {
        productsCart.remove(product);
    }
    public ArrayList<Product> getProductsCart(){
        return productsCart;
    }
    public  ArrayList<Integer> getQuantity(){
        return quantity;
    }


    private boolean hasEnoughProductsOfSameCategory(Product products, int amount) {
        int count = 0;
        for(int i=0; i<productsCart.size(); i++) {
            Product otherproduct = productsCart.get(i);
            if (otherproduct.getCategory().equals(products.getCategory())) { // if the category of the product is the same as the category of the other product
                count += quantity.get(i);
                if (count >=2){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isEligibleForDiscount() {
        for(int i=0; i<productsCart.size(); i++) {
            Product products = productsCart.get(i);
            int amount = quantity.get(i);
            if (hasEnoughProductsOfSameCategory(products, amount)) {
                return true;
            }
        }
        return false;
    }

}
