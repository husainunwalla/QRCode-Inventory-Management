package com.example.enamul.qrcode;

public class inventory {

    public String name;
    public String price;

    public inventory(){

    }

    public inventory(String name, String price){
        this.name = name;
        this.price = price;
    }

    public  String toString(){
        return this.name + " @" + this.price + "Rs";
    }

}
