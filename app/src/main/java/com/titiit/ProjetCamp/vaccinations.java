package com.titiit.ProjetCamp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import extra.DBhelper;
import extra.Moughataa;
import extra.Vaccination;
import extra.VaccinationsAdapter;

public class vaccinations extends AppCompatActivity {

    RecyclerView vaccinationsRecycleView;
    VaccinationsAdapter vaccinsAdapter;
    List<Vaccination> vaccinsList;
    LayoutInflater inflater;
    DBhelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccinations);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dBhelper = new DBhelper(this);
        dBhelper.openDataBase();

        vaccinsList = new ArrayList<Vaccination>();
        vaccinsList = dBhelper.getVaccinationList();
        dBhelper.close();

        vaccinationsRecycleView = (RecyclerView) findViewById(R.id.vaccinationsListRecyclerView);
        vaccinsAdapter = new VaccinationsAdapter(vaccinsList, this);

        vaccinationsRecycleView.setHasFixedSize(true);
        vaccinationsRecycleView.setLayoutManager(new LinearLayoutManager(this));
        vaccinationsRecycleView.setAdapter(vaccinsAdapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addVac = new Intent(getApplicationContext(), AddVaccination.class);
                startActivity(addVac);
            }
        });
    }

}
