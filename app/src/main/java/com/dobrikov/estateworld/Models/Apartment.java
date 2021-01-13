package com.dobrikov.estateworld.Models;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

import com.dobrikov.estateworld.R;

import static java.lang.String.valueOf;

public class Apartment {

    /* Район "Брагино" включает такие улицы: Труфанова, Батова, Панина.
       Район "Ленинский" включает такие улицы: Ленина, Чкалова, Свердлова.
       Район "Заволжский" включает такие улицы: Саукова, Ляпидевского, Союзная.

       typeHouse: 0 - дом панельный, 1 - дом кирпичный.
     */

    private String street, district, city, title, ownerEmail;
    private int countRooms, size, cost, level, typeHouse, imgid;
    private int id;

    public Apartment() {    }

    public Apartment(String street, String district, String city, String title, String ownerEmail, int countRooms, int size, int cost, int level, int typeHouse, int imgid, int id) {
        this.street = street;
        this.district = district;
        this.city = city;
        this.title = title;
        this.ownerEmail = ownerEmail;
        this.countRooms = countRooms;
        this.size = size;
        this.cost = cost;
        this.level = level;
        this.typeHouse = typeHouse;
        this.imgid = imgid;
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        String nameOfFile = "R.drawable.house" + imgid;
        return R.drawable.house0;
    }

    public static int getImageId(Context context, int idimage) {
        return context.getResources().getIdentifier("drawable/house" + valueOf(idimage), null, context.getPackageName());
    }

    public String getRoomsString() {
        if (countRooms == 1) {
            return "Одна комната";
        }
        if (countRooms > 1 && countRooms < 5)
            return valueOf(countRooms) + " комнаты";
        else return valueOf(countRooms) + " комнат";
    }

    public String getHouseTypeString() {
        if (typeHouse == 1)
            return "Кирпичный дом.";
            else return "Панельный дом";
    }
}
