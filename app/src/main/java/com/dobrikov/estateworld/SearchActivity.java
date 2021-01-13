
package com.dobrikov.estateworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dobrikov.estateworld.Models.AddressContainer;
import com.dobrikov.estateworld.Models.Apartment;
import com.dobrikov.estateworld.Models.BoxAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class SearchActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    ArrayList<Apartment> products = new ArrayList<Apartment>();
    BoxAdapter boxAdapter;
    AddressContainer addressContainer = new AddressContainer();
    String textToTextViewFilter = "Фильтры поиска: ";
    RelativeLayout root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // создаем адаптер
        fillData();
        boxAdapter = new BoxAdapter(this, products);

        // настраиваем список
        ListView lvMain = (ListView) findViewById(R.id.list_houses);
        lvMain.setAdapter(boxAdapter);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {

                AlertDialog.Builder dialog = new AlertDialog.Builder(SearchActivity.this);
                dialog.setTitle("Просмотр квартиры.");
                dialog.setMessage("Будьте внимательны! Никогда не отправляйте деньги предоплатой!");

                LayoutInflater inflater = LayoutInflater.from(SearchActivity.this);
                View information_about_apartment = inflater.inflate(R.layout.information_about_apartment_window, null);
                dialog.setView(information_about_apartment);

                ImageView imageApartment = (ImageView)information_about_apartment.findViewById(R.id.imageApartment);
                TextView titlePage = (TextView)information_about_apartment.findViewById(R.id.apartmentTitle);
                TextView apartmentDescription = (TextView)information_about_apartment.findViewById(R.id.apartmentDescription);
                TextView apartmentCountRoomsAndMeters = (TextView)information_about_apartment.findViewById(R.id.apartmentCountRoomsAndMeters);
                TextView descriptionHouse = (TextView)information_about_apartment.findViewById(R.id.descriptionHouse);
                TextView apartmentCost = (TextView)information_about_apartment.findViewById(R.id.apartmentCost);

                Apartment apartment = (Apartment)lvMain.getAdapter().getItem(position);
                titlePage.setText(apartment.getTitle());
                imageApartment.setImageResource(apartment.getImageId(SearchActivity.this, apartment.getId()));
                apartmentDescription.setText("г." + apartment.getCity() + ", р-н." + apartment.getDistrict() + ", ул." + apartment.getStreet());
                apartmentCountRoomsAndMeters.setText(apartment.getRoomsString() + ", " + apartment.getSize() + "кв.м.");
                descriptionHouse.setText(apartment.getLevel() + " этаж. " + apartment.getHouseTypeString());
                apartmentCost.setText(apartment.getCost() + "₽");

                dialog.setPositiveButton("Связаться с продавцом", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    Snackbar.make(root, "Телефон продавца: " + apartment.getOwnerEmail(), Snackbar.LENGTH_LONG).show();
                }
            }).setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    dialogInterface.dismiss();
                }
            });

                dialog.show();
            }
        });

    // String street, String district, String city, String title, String ownerEmail, int countRooms, int size, int cost, int level, int typeHouse, int imgid, int id
    // генерируем данные для адаптера


        root = findViewById(R.id.root_searchactivity);
        Button btnFind = findViewById(R.id.findButton);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showFindHousesWindow();
            }
        });

    }
    void fillData() {
        products.add(new Apartment("Труфанова", "Брагино", "Ярославль", "Скромная квартира в общежитии.", "testaccout@gmail.ru"
                , 1, 31, 1100000, 5, 1, 0, 0));
        products.add(new Apartment("Панина", "Брагино", "Ярославль", "Скромная квартира в общежитии.", "testaccout@gmail.ru"
                , 1, 31, 1100000, 5, 0, 0, 1));
        products.add(new Apartment("Союзная", "Ленинский", "Ярославль", "Скромная квартира в общежитии.", "testaccout@gmail.ru"
                , 1, 31, 1100000, 5, 1, 0, 2));
        products.add(new Apartment("Труфанова", "Брагино", "Москва", "Скромная квартира в общежитии.", "testaccout@gmail.ru"
                , 1, 31, 1100000, 5, 0, 0, 1));
    }
    private void showFindHousesWindow() {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Давайте найдём вам квартиру!");


            LayoutInflater inflater = LayoutInflater.from(this);
            View find_houses_window = inflater.inflate(R.layout.find_houses, null);
            dialog.setView(find_houses_window);
            RangeSeekBar _roomSeekBar = (RangeSeekBar)find_houses_window.findViewById(R.id.roomSeekbar);
            RangeSeekBar _meterSeekBar = (RangeSeekBar)find_houses_window.findViewById(R.id.meterSeekbar);
            RangeSeekBar _levelSeekBar = (RangeSeekBar)find_houses_window.findViewById(R.id.levelSeekbar);
            RangeSeekBar _costSeekBar = (RangeSeekBar)find_houses_window.findViewById(R.id.costSeekbar);
            CheckBox _brick = (CheckBox) find_houses_window.findViewById(R.id.brickCheckBox);
            CheckBox _panel = (CheckBox) find_houses_window.findViewById(R.id.panelCheckBox);
            TextView textFromStreetField = find_houses_window.findViewById(R.id.streetField);

            dialog.setPositiveButton("Найти квартиру", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    ArrayList<Apartment> test = applyFilters(textFromStreetField.getText().toString(),
                                 _roomSeekBar.getSelectedMinValue().intValue(),
                                 _roomSeekBar.getSelectedMaxValue().intValue(),
                                 _meterSeekBar.getSelectedMinValue().intValue(),
                                 _meterSeekBar.getSelectedMaxValue().intValue(),
                                 _levelSeekBar.getSelectedMinValue().intValue(),
                                 _levelSeekBar.getSelectedMaxValue().intValue(),
                                 _costSeekBar.getSelectedMinValue().intValue(),
                                 _costSeekBar.getSelectedMaxValue().intValue(),
                                 _brick.isChecked(),
                                 _panel.isChecked(), find_houses_window);
                    Snackbar.make(root, "Фильтры применены.", Snackbar.LENGTH_SHORT).show();
                    ListView lvMain = (ListView) findViewById(R.id.list_houses);
                    TextView tv = (TextView)findViewById(R.id.filters_text);
                    tv.setText(textToTextViewFilter);
                    tv.setTextSize(15);

                    lvMain.setAdapter(new BoxAdapter(SearchActivity.this, test));

                }
            });
            dialog.show();
    }


    private ArrayList<Apartment> applyFilters(String address, int countMinRooms, int countMaxRooms
            , int countMinSize, int countMaxSize, int minLevel, int maxLevel, int minCost, int maxCost, boolean brick, boolean panel, View find_houses_window) {


        RangeSeekBar _roomSeekBar = (RangeSeekBar)find_houses_window.findViewById(R.id.roomSeekbar);
        RangeSeekBar _meterSeekBar = (RangeSeekBar)find_houses_window.findViewById(R.id.meterSeekbar);
        RangeSeekBar _levelSeekBar = (RangeSeekBar)find_houses_window.findViewById(R.id.levelSeekbar);
        RangeSeekBar _costSeekBar = (RangeSeekBar)find_houses_window.findViewById(R.id.costSeekbar);
        CheckBox _brick = (CheckBox) find_houses_window.findViewById(R.id.brickCheckBox);
        CheckBox _panel = (CheckBox) find_houses_window.findViewById(R.id.panelCheckBox);
        TextView textFromStreetField = find_houses_window.findViewById(R.id.streetField);






        boolean street = false, district = false, city = false;
        String streetStr ="", districtStr = "", cityStr = "";
        int _needType = 2;
        if (brick && !panel)
            _needType = 1;
        if (panel && !brick)
            _needType = 0;
        if (brick && panel)
            _needType = 2;



        String addressArray[] = address.split(", ");



        for (int i = 0; i < addressArray.length; i++)
            for (int j = 0; j < addressContainer.getStreets().length; j++)
                if (addressArray[i].equalsIgnoreCase(addressContainer.getStreets()[j])) {
                    street = true;
                    streetStr = addressArray[i];
                }
        for (int i = 0; i < addressArray.length; i++)
            for (int j = 0; j < addressContainer.getDistricts().length; j++)
                if (addressArray[i].equalsIgnoreCase(addressContainer.getDistricts()[j])) {
                    district = true;
                    districtStr = addressArray[i];
                }
        for (int i = 0; i < addressArray.length; i++)
            for (int j = 0; j < addressContainer.getCity().length; j++)
                if (addressArray[i].equalsIgnoreCase(addressContainer.getCity()[j])) {
                    city = true;
                    cityStr = addressArray[i];
                }
        ArrayList<Apartment> productsWithFilter = new ArrayList<Apartment>();
                for (int i = 0; i < products.size(); i++)
                {
                    if (products.get(i).getCountRooms() >= countMinRooms &&
                        products.get(i).getCountRooms() <= countMaxRooms &&
                        products.get(i).getSize() >= countMinSize &&
                        products.get(i).getSize() <= countMaxSize &&
                        products.get(i).getLevel() >= minLevel &&
                        products.get(i).getLevel() <= maxLevel &&
                        products.get(i).getCost() >= minCost &&
                        products.get(i).getCost() <= maxCost &&
                            (products.get(i).getTypeHouse() == _needType && _needType != 2 || _needType == 2 ))
                    {
                        productsWithFilter.add(products.get(i));
                    }

                }
                ArrayList<Integer> tempint = new ArrayList<Integer>();
                for (int i = productsWithFilter.size()-1; i >= 0; i--) {
                    if (streetStr.equals("")){
                        if (districtStr.equals("")) {
                            if (cityStr.equals("")) {
                                return productsWithFilter;
                            }
                            else if (!(productsWithFilter.get(i).getCity().equalsIgnoreCase(cityStr)))
                                productsWithFilter.remove(i);
                        }
                        else if (!(productsWithFilter.get(i).getDistrict().equalsIgnoreCase(districtStr))||(city == true && productsWithFilter.get(i).getCity().equalsIgnoreCase(cityStr) == false))
                            productsWithFilter.remove(i);
                    }
                    else if (!(productsWithFilter.get(i).getStreet().equalsIgnoreCase(streetStr))||(district == true && productsWithFilter.get(i).getDistrict().equalsIgnoreCase(districtStr) == false)||(city == true && productsWithFilter.get(i).getCity().equalsIgnoreCase(cityStr) == false))
                        productsWithFilter.remove(i);
                }

        if (city)
            textToTextViewFilter = textToTextViewFilter.concat("г." + cityStr.toUpperCase() +", ");
        if (district)
            textToTextViewFilter = textToTextViewFilter.concat("р-н." + districtStr.toUpperCase() +", ");
        if (street)
            textToTextViewFilter = textToTextViewFilter.concat("ул." + streetStr.toUpperCase() +", ");
        textToTextViewFilter = textToTextViewFilter.concat("КОМНАТЫ [" + valueOf(countMinRooms) + " — " + valueOf(countMaxRooms) + "], ");
        textToTextViewFilter = textToTextViewFilter.concat("МЕТРАЖ [" + valueOf(countMinSize) + " — " + valueOf(countMaxSize) + "кв.м.], ");
        textToTextViewFilter = textToTextViewFilter.concat("ЭТАЖ [" + valueOf(minLevel) + " — " + valueOf(maxLevel) + "], ");
        if (_costSeekBar.getAbsoluteMinValue().intValue() != minCost || _costSeekBar.getAbsoluteMaxValue().intValue() != maxCost)
            textToTextViewFilter = textToTextViewFilter.concat("ЦЕНА [" + valueOf(minCost) + " — " + valueOf(maxCost) + "], ");
        else textToTextViewFilter = textToTextViewFilter.concat("ЦЕНА [ЛЮБАЯ], ");
        if (_needType == 0)
            textToTextViewFilter = textToTextViewFilter.concat("ДОМ [ПАНЕЛЬНЫЙ].");
        if (_needType == 1)
            textToTextViewFilter = textToTextViewFilter.concat("ДОМ [КИРПИЧНЫЙ].");
        if (_needType == 2)
            textToTextViewFilter = textToTextViewFilter.concat("ДОМ [ПАНЕЛЬНЫЙ, КИРПИЧНЫЙ].");
        textToTextViewFilter = textToTextViewFilter.concat("\nВАРИАНТОВ НАЙДЕНО: " + productsWithFilter.size());
                return productsWithFilter;
    }
}