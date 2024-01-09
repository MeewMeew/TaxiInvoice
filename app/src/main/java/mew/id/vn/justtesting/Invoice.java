package mew.id.vn.justtesting;

import java.io.Serializable;

public class Invoice implements Serializable {
    private long id;
    private String carNumber;
    private double distance, price, discount;

    public Invoice() {
    }

    public Invoice(String carNumber, double distance, double price, double discount) {
        this.carNumber = carNumber;
        this.distance = distance;
        this.price = price;
        this.discount = discount;
    }

    public Invoice(long id, String carNumber, double distance, double price, double discount) {
        this.id = id;
        this.carNumber = carNumber;
        this.distance = distance;
        this.price = price;
        this.discount = discount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalPrice() {
        return this.price * this.distance * ((100 - this.discount) / 100);
    }
}
