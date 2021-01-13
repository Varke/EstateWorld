package com.dobrikov.estateworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dobrikov.estateworld.Models.AddressContainer;
import com.dobrikov.estateworld.Models.Apartment;
import com.dobrikov.estateworld.Models.BoxAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    ArrayList<Apartment> products = new ArrayList<Apartment>();
    BoxAdapter boxAdapter;
    String favoriteStr, userMail, favoriteApartments, loginUser, numberUser;
    RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        userMail = getIntent().getStringExtra("mailUser");
        numberUser = getIntent().getStringExtra("numberUser");
        loginUser = getIntent().getStringExtra("loginUser");
        favoriteApartments = getIntent().getStringExtra("favoriteApartments");
        root = findViewById(R.id.root_favoritesactivity);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_favorites);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_favorites:
                    case R.id.action_all_apartments:
                        Intent nextWindow = new Intent(FavoritesActivity.this, SearchActivity.class);
                        nextWindow.putExtra("mailUser", userMail);
                        nextWindow.putExtra("loginUser", loginUser);
                        nextWindow.putExtra("numberUser", numberUser);
                        nextWindow.putExtra("favoriteApartments", favoriteApartments);
                        startActivity(nextWindow);
                        finish();
                    case R.id.action_profile:
                }
                return true;
            }
        });

        fillData();
        boxAdapter = new BoxAdapter(this, products);

        // настраиваем список
        ListView lvMain = (ListView) findViewById(R.id.list_housess);
        lvMain.setAdapter(boxAdapter);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {

                AlertDialog.Builder dialog = new AlertDialog.Builder(FavoritesActivity.this);
                dialog.setTitle("Просмотр квартиры.");
                dialog.setMessage("Будьте внимательны! Никогда не отправляйте деньги предоплатой!");

                LayoutInflater inflater = LayoutInflater.from(FavoritesActivity.this);
                View information_about_apartment = inflater.inflate(R.layout.information_about_apartment_window, null);
                dialog.setView(information_about_apartment);

                ImageView imageApartment = (ImageView)information_about_apartment.findViewById(R.id.imageApartment);
                TextView titlePage = (TextView)information_about_apartment.findViewById(R.id.apartmentTitle);
                TextView apartmentDescription = (TextView)information_about_apartment.findViewById(R.id.apartmentDescription);
                TextView apartmentCountRoomsAndMeters = (TextView)information_about_apartment.findViewById(R.id.apartmentCountRoomsAndMeters);
                TextView descriptionHouse = (TextView)information_about_apartment.findViewById(R.id.descriptionHouse);
                TextView apartmentCost = (TextView)information_about_apartment.findViewById(R.id.apartmentCost);
                Button addToFavorite = (Button)information_about_apartment.findViewById(R.id.add_to_favorite_button);
                addToFavorite.setEnabled(false);

                Apartment apartment = (Apartment)lvMain.getAdapter().getItem(position);
                titlePage.setText(apartment.getTitle());
                imageApartment.setImageResource(apartment.getImageId(FavoritesActivity.this, apartment.getId()));
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


    }
    void fillData() {
        SQLiteDatabase usersDataBase = getBaseContext().openOrCreateDatabase("usersDataBase2.db", MODE_PRIVATE, null);
        Cursor queryU = usersDataBase.rawQuery("SELECT * FROM users;", null);
        while(queryU.moveToNext()) {
            if (queryU.getString(1).equals(userMail))
                favoriteStr = queryU.getString(3);
        }

        SQLiteDatabase apartmentsDataBase = getBaseContext().openOrCreateDatabase("apartmentsDataBaseFinal.db", MODE_PRIVATE, null);
        apartmentsDataBase.execSQL("CREATE TABLE IF NOT EXISTS apartments (street TEXT, district TEXT, city TEXT, title TEXT, ownerNumber INTEGER, countRooms INTEGER," +
                " size INTEGER, cost INTEGER, level INTEGER, typeHouse INTEGER, imgid INTEGER, id INTEGER)");
        Cursor query = apartmentsDataBase.rawQuery("SELECT * FROM apartments;", null);

        String[] trimStr = favoriteStr.split(" ");
        if(query.moveToFirst() && !favoriteStr.equals("")) {
        for (int i = 0; i < trimStr.length; i++)
        while(query.moveToNext()) {
            if (Integer.parseInt(trimStr[i]) == query.getInt(11)) {
                products.add(new Apartment(query.getString(0),
                        query.getString(1),
                        query.getString(2),
                        query.getString(3),
                        query.getString(4),
                        query.getInt(5),
                        query.getInt(6),
                        query.getInt(7),
                        query.getInt(8),
                        query.getInt(9),
                        query.getInt(10),
                        query.getInt(11)));
            }
        }

        } else {
            TextView emptyFavoriteText = (TextView)findViewById(R.id.empty_favorite);
            emptyFavoriteText.setText("В вашем списке избранного пока нет квартир.");
            emptyFavoriteText.setTextSize(20);
        }

        query.close();
        queryU.close();
        apartmentsDataBase.close();
        usersDataBase.close();
    }
}