package com.titiit.ProjetCamp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import Synchronisation.Synchronisation;
import extra.DBhelper;
import extra.Vaccination;
import extra.VaccinationsAdapter;

public class vaccinations extends AppCompatActivity {

    RecyclerView vaccinationsRecycleView;
    VaccinationsAdapter vaccinsAdapter;
    List<Vaccination> vaccinsList;
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
                    sync.synchoniserWilayas();
                    sync.synchoniserMoughataas();
                    sync.synchroniserCampagnes();
                    sync.synchoniserVaccins();
                    if(vaccinsList.size()>0){
                        sync.synchroniserVaccinations();
                    }
                    sync.synchroniserVaccinations();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                findViewById(R.id.syncPanel).setVisibility(View.GONE);
                finish();
                startActivity(getIntent());
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
