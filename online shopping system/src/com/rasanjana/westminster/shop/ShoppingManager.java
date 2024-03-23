package com.rasanjana.westminster.shop;

import com.rasanjana.westminster.shop.Product;

 interface ShoppingManager {

    void addProductDetails(); //add products
    void deleteProduct(); //delete products

    void printProduct(); //print list of products
    void saveProduct(); //save to file
    void readProduct(); //load from file
}
