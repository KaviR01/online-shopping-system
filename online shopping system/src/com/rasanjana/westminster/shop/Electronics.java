package com.rasanjana.westminster.shop;


public class Electronics extends Product {
    private String brand;
    private double warrantyPeriod;

    public Electronics(String productID, String productName, int availableItems, double price, String brand, double warrantyPeriod){
        super(productID, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    //Getters and Setters
    public String getBrand(){
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public double getWarrantyPeriod(){
        return warrantyPeriod;
    }
    public void setWarrantyPeriod(double warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String getInfo() {
        return this.getBrand() + ", " + this.getWarrantyPeriod();
    }
    @Override
    public String getProduct() {
        return this.getProductID() + ",\n" + this.getProductName() + ",\n" +this.getInfo();
    }
    @Override
    public String getCategory() {
        return "Electronics";
    }
}
