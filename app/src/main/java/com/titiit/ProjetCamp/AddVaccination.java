package com.titiit.ProjetCamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import extra.AppUser;
import extra.Campagne;
import extra.DBhelper;
import extra.Moughataa;
import extra.Vaccin;
import extra.Vaccination;
import retrofit.MoughataaService;

import static android.widget.Toast.makeText;

public class AddVaccination extends AppCompatActivity {

    ///////////
    MoughataaService moughataaService;
    ///////////

    Spinner vaccinsList;
    Spinner moghataasList;
    EditText nbreEnfant;
    Spinner trancheAge;
    Button submit;
    Spinner campagne;
    DBhelper dBhelper;

    //Localisation
    LocationManager locationManager;
    LocationListener locationListener;
    double lon, lat;
    Vaccination vaccination;
    String dateFormatted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaccination);

        /////////
        //getMoughataasList();
        ////////

        nbreEnfant = findViewById(R.id.nbrEnfants);
        trancheAge = findViewById(R.id.trancheAge);
        vaccinsList = findViewById(R.id.vaccins);
        moghataasList = findViewById(R.id.moughataas);
        submit = findViewById(R.id.btnSubmit);
        campagne = findViewById(R.id.campagne);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        dBhelper = new DBhelper(this);
        dBhelper.openDataBase();

        //String[] agesList = {"0-5", "6-10"};
        List<String> agesList = new ArrayList<String>();
        agesList.add("0-5");
        agesList.add("0-10");
        agesList.add("0-15");
        ArrayAdapter<String> agesAd = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, agesList);
        trancheAge.setAdapter(agesAd);

        List<String> mgts = new ArrayList<String>();
        final List<Moughataa> moughataas = dBhelper.getMoughataaList();
        for(int i=0; i<moughataas.size(); i++){
            mgts.add(moughataas.get(i).getMoughataaname());
        }
        ArrayAdapter<String> mgtsList= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mgts);
        moghataasList.setAdapter(mgtsList);

        List<String> vaccs = new ArrayList<String>();
        final List<Vaccin> vaccins = dBhelper.getVaccinsList();
        for(int i=0; i<vaccins.size(); i++){
            vaccs.add(vaccins.get(i).getNom_vaccin());
        }
        ArrayAdapter<String> vaccsList = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, vaccs);
        vaccinsList.setAdapter(vaccsList);

        List<String> camps = new ArrayList<String>();
        final List<Campagne> campagnes = dBhelper.getCampagneList();
        for(int i=0; i<campagnes.size(); i++){
            camps.add(campagnes.get(i).getName());
        }
        ArrayAdapter<String> campsAdptr = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, camps);
        campagne.setAdapter(campsAdptr);

        dBhelper.close();

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        this.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long selectedMoughataa = 0;
                long selectedCampagne = 0;
                long selectedVaccin = 0;
                for(int i=0; i<campagnes.size(); i++){
                    if(campagnes.get(i).getName().equals(campagne.getSelectedItem().toString())){
                        selectedCampagne = campagnes.get(i).getId();
                        break;
                    }
                }
                for(int i=0; i<moughataas.size(); i++){
                    if(moughataas.get(i).getMoughataaname().equals(moghataasList.getSelectedItem().toString())){
                        selectedMoughataa = moughataas.get(i).getId();
                        break;
                    }
                }
                for(int i=0; i<vaccins.size(); i++){
                    if(vaccins.get(i).getNom_vaccin().equals(vaccinsList.getSelectedItem().toString())){
                        selectedVaccin = vaccins.get(i).getId();
                        break;
                    }
                }


                Date date = Calendar.getInstance().getTime();

                nbreEnfant.setEnabled(false);
                trancheAge.setEnabled(false);
                vaccinsList.setEnabled(false);
                moghataasList.setEnabled(false);
                submit.setEnabled(false);
                campagne.setEnabled(false);

                dateFormatted = date.getDate()+"/"+date.getMonth()+"/"+(date.getYear()+1900);

                vaccination = new Vaccination(null,
                        dateFormatted,
                        lon,
                        lat,
                        Integer.parseInt(nbreEnfant.getText().toString()),
                        trancheAge.getSelectedItem().toString(),
                        dBhelper.getCampagne(selectedCampagne),
                        dBhelper.getMoughataa(selectedMoughataa),
                        new AppUser(Long.parseLong(getIntent().getExtras().getString("id"))),
                        dBhelper.getVaccin(selectedVaccin)
                );
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

                findLocalisation();
            }
        });

    }    // end of onCreate
    ///////////////////////////////////////////////////////////// Localisation start /////////////////////////////////////////////////////////////
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }


    void configure_button() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }
        locationManager.requestSingleUpdate("gps", locationListener, null);
    }
    public void findLocalisation(){
        locationListener = new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }

            @Override
            public void onLocationChanged(Location location) {
                lon = location.getLatitude();
                lat = location.getLongitude();
                vaccination.setLongiude(lon);
                vaccination.setLatitude(lat);

                addVaccination(vaccination);
                nbreEnfant.setEnabled(true);
                trancheAge.setEnabled(true);
                vaccinsList.setEnabled(true);
                moghataasList.setEnabled(true);
                submit.setEnabled(true);
                campagne.setEnabled(true);

                findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                makeText(getApplicationContext(), "Date: "+dateFormatted+" \n"+
                        "("+vaccination.getLongiude()+","+vaccination.getLatitude()+")", Toast.LENGTH_LONG).show();
            }
        };
        configure_button();
    }

    public void addVaccination(Vaccination vac){
        dBhelper.addVaccination(vac);
    }
        ///////////////////////////////////////////////////////////// Localisation end /////////////////////////////////////////////////////////////

}
