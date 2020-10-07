package com.titiit.ProjetCamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import extra.AppUser;
import extra.Campagne;
import retrofit.AuthService;
import retrofit.CampagneService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import session.Session;

public class MainActivity extends AppCompatActivity {

    EditText login;
    EditText pass;
    Button btnLogin;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnLogin = findViewById(R.id.btnLogin);
        this.login = findViewById(R.id.login);
        this.pass = findViewById(R.id.pass);

        session = new Session(getApplicationContext());

        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.6:8080/agent/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                AuthService authService = retrofit.create(AuthService.class);

                Call<AppUser> call = authService.authenticateUser(login.getText().toString(), pass.getText().toString());

                call.enqueue(new Callback<AppUser>() {
                    @Override
                    public void onResponse(Call<AppUser> call, Response<AppUser> response) {
                        if(response.isSuccessful()){
                            if(response.body().getId()!=null){
                                session.setId(response.body().getId());
                                Intent fab = new Intent(getApplicationContext(), vaccinations.class);
                                fab.putExtra("id", String.valueOf(session.getId()));
                                startActivity(fab);
                            }else{
                                Toast.makeText(MainActivity.this, "Login/Mot de passe est incorrect", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Probleme d'authentification", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AppUser> call, Throwable t) {
                        Log.e("ERROR AUTH", t.getMessage());
                    }
                });

            }
        });
    }
}
