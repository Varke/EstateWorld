package com.dobrikov.estateworld.Models;

import com.dobrikov.estateworld.R;

public class Apartment {

    /* Район "Брагино" включает такие улицы: Труфанова, Батова, Панина.
       Район "Ленинский" включает такие улицы: Ленина, Чкалова, Свердлова.
       Район "Заволжский" включает такие улицы: Саукова, Ляпидевского, Союзная.
     */

    private String street, district, city;
    private int countRooms, size, cost, level, typeHouse, imgid;

    public Apartment() {};

    public Apartment(String street, String district, String city, int countRooms, int size, int cost, int level, int typeHouse, int imgid) {
        this.street = street;
        this.district = district;
        this.city = city;
        this.countRooms = countRooms;
        this.size = size;
        this.cost = cost;
        this.level = level;
        this.typeHouse = typeHouse;
        this.imgid = imgid;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCountRooms() {
        return countRooms;
    }

    public void setCountRooms(int countRooms) {
        this.countRooms = countRooms;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTypeHouse() {
        return typeHouse;
    }

    public void setTypeHouse(int typeHouse) {
        this.typeHouse = typeHouse;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
}
