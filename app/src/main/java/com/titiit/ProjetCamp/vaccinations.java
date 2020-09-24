package com.titiit.ProjetCamp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import Synchronisation.Synchronisation;
import extra.Campagne;
import extra.DBhelper;
import extra.Moughataa;
import extra.Vaccin;
import extra.Vaccination;
import extra.VaccinationsAdapter;
import retrofit.CampagneService;
import retrofit.MoughataaService;
import retrofit.VaccinService;
import retrofit.VaccinationService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class vaccinations extends AppCompatActivity {

    RecyclerView vaccinationsRecycleView;
    VaccinationsAdapter vaccinsAdapter;
    List<Vaccination> vaccinsList;
    LayoutInflater inflater;
    DBhelper dBhelper;

    Button syncBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccinations);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            this.databaseHandler("projet_campagne_vaccination.db");
        } catch (IOException e) {
            e.printStackTrace();
        }

        findViewById(R.id.syncPanel).setVisibility(View.INVISIBLE);

        dBhelper = new DBhelper(this);
        dBhelper.openDataBase();

        vaccinsList = new ArrayList<Vaccination>();
        vaccinsList = dBhelper.getVaccinationList();

        if(vaccinsList.size()>0) {
            vaccinationsRecycleView = (RecyclerView) findViewById(R.id.vaccinationsListRecyclerView);
            vaccinsAdapter = new VaccinationsAdapter(vaccinsList, this);
            vaccinationsRecycleView.setHasFixedSize(true);
            vaccinationsRecycleView.setLayoutManager(new LinearLayoutManager(this));
            vaccinationsRecycleView.setAdapter(vaccinsAdapter);
        }else {
            new AlertDialog.Builder(vaccinations.this)
                    .setMessage("Rien a synchroniser maintenant")
                    .show();
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addVac = new Intent(getApplicationContext(), AddVaccination.class);
                addVac.putExtra("id", String.valueOf(getIntent().getStringExtra("id")));
                startActivity(addVac);
                finish();

            }
        });

        /////////
        syncBtn = findViewById(R.id.update);
        syncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.syncPanel).setVisibility(View.VISIBLE);
                Synchronisation sync = new Synchronisation(dBhelper);
                try{
                    sync.synchoniserMoughataas();
                    sync.synchoniserVaccins();
                    sync.synchroniserCampagnes();
                    sync.synchroniserVaccinations();
                    findViewById(R.id.syncPanel).setVisibility(View.GONE);
                    finish();
                    startActivity(getIntent());
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        ////////
    }

    private void databaseHandler(String dbname) throws IOException {
        if(!this.getDatabasePath(dbname).exists()){
            // Open your local db as the input stream
            InputStream myInput = this.getAssets().open(dbname);
            // Path to the just created empty db
            File outFileName = this.getDatabasePath(dbname);
            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
    }
}
