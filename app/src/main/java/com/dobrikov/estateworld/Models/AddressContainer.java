package com.dobrikov.estateworld.Models;

public class AddressContainer {

    final String[] city = {"Ярославль", "Москва", "Санкт-Петербург", "Екатеринбург", "Новосибирск"};
    final String[] streets = {"Труфанова", "Батова", "Панина", "Волгоградская", "Союзная", "Ленина", "Чкалова", "Свердлова", "Саукова", "Ляпидевского", "Союзная", "Свободы", "Бабича"};
    final String[] districts = {"Брагино", "Заволжский", "Ленинский", "Фрунзенский", "Карачиха", "Суздальский"};
    final String[] titles = {"Скромная и уютная квартирка", "Масштабный проект родителей", "Продаю срочно", "Продам квартиру за ненадобностью", "Продается квартира в центре"
    , "Двадцать квадратных метров счастья", "Приятная студия на окраине города", "Квартира в аварийном доме", "Квартира с новым евроремонтом", "Квартира для семьи"};

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

    public String[] getTitles() {
        return titles;
    }
}
