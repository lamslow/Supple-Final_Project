package com.example.supple.model;

public class ProductsInCart {

    public String id;
    public String username;
    public String productname;
    public double price;
    public int quantity;
    public String image;

    public ProductsInCart() {
    }

    public  ProductsInCart(String id, String image, double price, String productname, int quantity, String username) {
        this.id = id;
        this.username = username;
        this.productname = productname;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }
}
