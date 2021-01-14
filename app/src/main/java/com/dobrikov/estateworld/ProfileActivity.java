package com.dobrikov.estateworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.rengwuxian.materialedittext.MaterialEditText;

public class ProfileActivity extends AppCompatActivity {

    String userMail, numberUser, loginUser, favoriteApartments;
    RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userMail = getIntent().getStringExtra("mailUser");
        numberUser = getIntent().getStringExtra("numberUser");
        loginUser = getIntent().getStringExtra("loginUser");
        favoriteApartments = getIntent().getStringExtra("favoriteApartments");
        root = findViewById(R.id.root_profile_activity);

        TextView loginLabel = (TextView)findViewById(R.id.loginLabel);
        loginLabel.setText(loginUser);
        TextView mailLabel = (TextView)findViewById(R.id.mailLabel);
        mailLabel.setText(userMail);
        TextView numberLabel = (TextView)findViewById(R.id.numberLabel);
        numberLabel.setText(numberUser);


        Button sellApartmentBtn = (Button)findViewById(R.id.sellApartmentButton);
        sellApartmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellApartmentShow();
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_favorite:
                        Intent profileWindow = new Intent(ProfileActivity.this, FavoritesActivity.class);
                        profileWindow.putExtra("mailUser", userMail);
                        profileWindow.putExtra("loginUser", loginUser);
                        profileWindow.putExtra("numberUser", numberUser);
                        profileWindow.putExtra("favoriteApartments", favoriteApartments);
                        startActivity(profileWindow);
                        finish();
                    case R.id.action_all_apartments:
                        Intent nextWindow = new Intent(ProfileActivity.this, SearchActivity.class);
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
    }

    private void sellApartmentShow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Оформление заявки на продажу квартиры.");
        dialog.setMessage("К сожалению временно нельзя прикреплять изображения к объявлению.");

        LayoutInflater inflater = LayoutInflater.from(this);
        View sell_apartment_window = inflater.inflate(R.layout.sell_apartment_window, null);
        dialog.setView(sell_apartment_window);

        final MaterialEditText titleField = sell_apartment_window.findViewById(R.id.titleField);
        final MaterialEditText streetField = sell_apartment_window.findViewById(R.id.streetField);
        final MaterialEditText districtField = sell_apartment_window.findViewById(R.id.districtField);
        final MaterialEditText cityField = sell_apartment_window.findViewById(R.id.cityField);
        final MaterialEditText roomsField = sell_apartment_window.findViewById(R.id.roomsField);
        final MaterialEditText sizeField = sell_apartment_window.findViewById(R.id.sizeField);
        final MaterialEditText levelField = sell_apartment_window.findViewById(R.id.levelField);
        final MaterialEditText costField = sell_apartment_window.findViewById(R.id.costField);
        final MaterialEditText numberField = sell_apartment_window.findViewById(R.id.numberField);
        numberField.setText(numberUser);
        CheckBox brickCheckBoxS = sell_apartment_window.findViewById(R.id.brickCheckBoxS);
        CheckBox panelCheckBoxS = sell_apartment_window.findViewById(R.id.panelCheckBoxS);
        Button enterSell = sell_apartment_window.findViewById(R.id.entersell);



        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        enterSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int typehouse = 0;
                if (TextUtils.isEmpty(titleField.getText().toString())) {
                    Snackbar.make(root, "Введите заголовок объявления", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(streetField.getText().toString())) {
                    Snackbar.make(root, "Введите улицу", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(districtField.getText().toString())) {
                    Snackbar.make(root, "Введите район", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(cityField.getText().toString())) {
                    Snackbar.make(root, "Введите город", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(roomsField.getText().toString())) {
                    Snackbar.make(root, "Введите количество комнат", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(sizeField.getText().toString())) {
                    Snackbar.make(root, "Введите площадь помещения", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(levelField.getText().toString())) {
                    Snackbar.make(root, "Введите этаж", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(costField.getText().toString())) {
                    Snackbar.make(root, "Введите стоимость квартиры", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(numberField.getText().toString())) {
                    Snackbar.make(root, "Введите номер телефона для связи", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (panelCheckBoxS.isChecked() && brickCheckBoxS.isChecked() || !panelCheckBoxS.isChecked() && !brickCheckBoxS.isChecked()) {
                    Snackbar.make(root, "Выберите тип дома", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (panelCheckBoxS.isChecked())
                    typehouse = 0;
                else typehouse = 1;
                int countApartments = 0;
                SQLiteDatabase apartmentsDataBase = getBaseContext().openOrCreateDatabase("apartmentsDataBaseRelease.db", MODE_PRIVATE, null);
                apartmentsDataBase.execSQL("CREATE TABLE IF NOT EXISTS apartments (street TEXT, district TEXT, city TEXT, title TEXT, ownerNumber INTEGER, countRooms INTEGER," +
                        " size INTEGER, cost INTEGER, level INTEGER, typeHouse INTEGER, imgid INTEGER, id INTEGER)");
                Cursor query = apartmentsDataBase.rawQuery("SELECT * FROM apartments;", null);

                if(query.moveToFirst()) {
                    while (query.moveToNext())
                        countApartments++;
                }
                countApartments = countApartments - 1;
                query.close();
                apartmentsDataBase.execSQL("INSERT INTO apartments VALUES ('" + streetField.getText().toString() +"', '"+ districtField.getText().toString() + "', '" + cityField.getText().toString() + "', '" + titleField.getText().toString() + "', '"
                        + numberField.getText().toString() + "', '" + Integer.parseInt(roomsField.getText().toString()) + "', '"
                        + Integer.parseInt(sizeField.getText().toString()) + "', '" + Integer.parseInt(costField.getText().toString()) + "', '" + Integer.parseInt(levelField.getText().toString()) + "', '" + typehouse + "', '" + 16 +"', '" + countApartments + "');");
                apartmentsDataBase.close();
                Snackbar.make(root, "Квартира выставлена на продажу", Snackbar.LENGTH_LONG).show();
            }
        });


        dialog.show();
    }

}