package com.titiit.ProjetCamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText login;
    EditText pass;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnLogin = findViewById(R.id.btnLogin);
        this.login = findViewById(R.id.login);
        this.pass = findViewById(R.id.pass);

        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent fab = new Intent(getApplicationContext(), vaccinations.class);
                startActivity(fab);
                //startActivity(new Intent(getApplicationContext(), Vaccinsations.class));
            }
        });
    }
}
