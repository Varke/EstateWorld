package com.dobrikov.estateworld;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dobrikov.estateworld.Models.Apartment;
import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.List;

import static java.lang.String.valueOf;

public class SearchActivity extends AppCompatActivity {


    RelativeLayout root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        root = findViewById(R.id.root_searchactivity);
        Button btnFind = findViewById(R.id.findButton);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showFindHousesWindow();
            }
        });

    }

    private void showFindHousesWindow() {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Давайте найдём вам квартиру!");


            LayoutInflater inflater = LayoutInflater.from(this);
            View find_houses_window = inflater.inflate(R.layout.find_houses, null);
            dialog.setView(find_houses_window);
            RangeSeekBar rsb = (RangeSeekBar)find_houses_window.findViewById(R.id.roomSeekbar);

            dialog.setPositiveButton("Найти квартиру", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Snackbar.make(root, "ВЫ ВВЕЛИ ОТ " + rsb.getSelectedMinValue().toString() + " DO " + rsb.getSelectedMaxValue().toString(), Snackbar.LENGTH_SHORT).show();
                }
            });
            dialog.show();
    }
}