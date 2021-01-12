package com.dobrikov.estateworld.Models;

public class AddressContainer {

    final String[] city = {"Ярославль", "Москва", "Санкт-Петербург", "Екатеринбург", "Новосибирск"};
    final String[] streets = {"Труфанова", "Батова", "Панина", "Волгоградская", "Союзная", "Ленина", "Чкалова", "Свердлова", "Саукова", "Ляпидевского", "Союзная"};
    final String[] districts = {"Брагино", "Заволжский", "Ленинский"};

    public AddressContainer() {

    }

    public String[] getCity() {
        return city;
    }

    public String[] getStreets() {
        return streets;
    }

    public String[] getDistricts() {
        return districts;
    }
}
