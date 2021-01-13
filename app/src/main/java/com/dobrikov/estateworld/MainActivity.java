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
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.dobrikov.estateworld.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {

    Button btnsignin, btnsignup;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    String mailUser, numberUser;
    RelativeLayout root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnsignup = findViewById(R.id.button_signup);
        btnsignin = findViewById(R.id.button_signin);

        root = findViewById(R.id.root_element);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpWindow();
            }
        });
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInWindow();
            }
        });
    }

    private void showSignInWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Вход в существующий аккаунт");
        dialog.setMessage("Введите все данные для успешного входа");

        LayoutInflater inflater = LayoutInflater.from(this);
        View signin_window = inflater.inflate(R.layout.signin_window, null);
        dialog.setView(signin_window);

        final MaterialEditText email = signin_window.findViewById(R.id.mailField);
        final MaterialEditText password = signin_window.findViewById(R.id.passwordField);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Дальше", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(root, "Введите почту", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().length() < 5) {
                    Snackbar.make(root, "Пароль должен содержать от 6 символов", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                SQLiteDatabase usersDataBase = getBaseContext().openOrCreateDatabase("usersDataBase.db", MODE_PRIVATE, null);
                                usersDataBase.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT, number TEXT)");
                                Cursor query = usersDataBase.rawQuery("SELECT * FROM users;", null);
                                if(query.moveToFirst()){
                                    while(query.moveToNext()){
                                        if (query.getString(0).equals(email.getText().toString())) {
                                            numberUser = query.getString(1);
                                            mailUser = query.getString(0);
                                        }
                                    }
                                }
                                query.close();
                                usersDataBase.close();

                                Intent nextWindow = new Intent(MainActivity.this, SearchActivity.class);
                                nextWindow.putExtra("mailUser", mailUser);
                                nextWindow.putExtra("numberUser", numberUser);
                                startActivity(nextWindow);

                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root, "Ошибка авторизации. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.show();
    }

    private void showSignUpWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Регистрация");
        dialog.setMessage("Введите все данные для регистрации");

        LayoutInflater inflater = LayoutInflater.from(this);
        View signup_window = inflater.inflate(R.layout.signup_window, null);
        dialog.setView(signup_window);

        final MaterialEditText email = signup_window.findViewById(R.id.mailField);
        final MaterialEditText login = signup_window.findViewById(R.id.loginField);
        final MaterialEditText password = signup_window.findViewById(R.id.passwordField);
        final MaterialEditText number = signup_window.findViewById(R.id.numberField);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Дальше", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(root, "Введите почту", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(login.getText().toString())) {
                    Snackbar.make(root, "Введите имя", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(number.getText().toString())) {
                    Snackbar.make(root, "Введите номер телефона", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().length() < 5) {
                    Snackbar.make(root, "Пароль должен содержать от 6 символов", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // Registration
                auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                User user = new User();
                                user.setLogin(login.getText().toString());
                                user.setMail(email.getText().toString());
                                user.setPassword(password.getText().toString());
                                user.setNumber(number.getText().toString());

                                SQLiteDatabase usersDataBase = getBaseContext().openOrCreateDatabase("usersDataBase.db", MODE_PRIVATE, null);
                                usersDataBase.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT, number TEXT)");
                                usersDataBase.execSQL("INSERT INTO users VALUES ('" + email.getText().toString() +"', '"+ number.getText().toString() + "');");
                                usersDataBase.close();

                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Snackbar.make(root, "Успешная регистрация!", Snackbar.LENGTH_LONG).show();
                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root, "Ошибка регистрации. " + e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });

        dialog.show();
    }
}