package Synchronisation;

import android.util.Log;
import java.util.List;

import extra.Campagne;
import extra.DBhelper;
import extra.Moughataa;
import extra.Vaccin;
import extra.Vaccination;
import retrofit.CampagneService;
import retrofit.MoughataaService;
import retrofit.VaccinService;
import retrofit.VaccinationService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Synchronisation {

    String URL = "http://192.168.1.112:8080/agent/";

    public DBhelper dBhelper;

    public Synchronisation(DBhelper dBhelper){
        this.dBhelper = dBhelper;
    }

    public void synchoniserMoughataas(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoughataaService moughataaService = retrofit.create(MoughataaService.class);
        Call<List<Moughataa>> call = moughataaService.getMoughataas();
        call.enqueue(new Callback<List<Moughataa>>() {
            @Override
            public void onResponse(Call<List<Moughataa>> call, Response<List<Moughataa>> response) {
                if(response.isSuccessful()){
                    List<Moughataa> list = response.body();
                    dBhelper.synMoughtaas(list);
                }else{
                    Log.i("REPONSE ERR MOUGHATAAS", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Moughataa>> call, Throwable t) {
                Log.e("ERROR MOUGHATAAS ", t.getMessage());
            }
        });
    }

    public void synchoniserVaccins(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        VaccinService vaccinService = retrofit.create(VaccinService.class);

        Call<List<Vaccin>> call = vaccinService.getVaccins();

        call.enqueue(new Callback<List<Vaccin>>() {
            @Override
            public void onResponse(Call<List<Vaccin>> call, Response<List<Vaccin>> response) {
                if(response.isSuccessful()){
                    List<Vaccin> list = response.body();
                    dBhelper.synVaccins(list);
                }else{
                    Log.i("REPONSE ERR Vaccins", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Vaccin>> call, Throwable t) {
                Log.e("ERROR VACCINS ", t.getMessage());
            }
        });
    }

    public void synchroniserCampagnes(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CampagneService campagneService = retrofit.create(CampagneService.class);

        Call<List<Campagne>> call = campagneService.getCampagnes();

        call.enqueue(new Callback<List<Campagne>>() {
            @Override
            public void onResponse(Call<List<Campagne>> call, Response<List<Campagne>> response) {
                if(response.isSuccessful()){
                    List<Campagne> list = response.body();
                    dBhelper.synCampagnes(list);
                }else{
                    Log.i("REPONSE ERR Campagnes", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Campagne>> call, Throwable t) {
                Log.e("ERROR CAMPAGNES", t.getMessage());
            }
        });
    }

    public void synchroniserVaccinations(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        VaccinationService vaccinationService = retrofit.create(VaccinationService.class);

        for(final Vaccination vaccination : dBhelper.getVaccinationList()){
            Call<Vaccination> call = vaccinationService.addVaccination(vaccination);

            call.enqueue(new Callback<Vaccination>() {
                @Override
                public void onResponse(Call<Vaccination> call, Response<Vaccination> response) {
                    if(response.isSuccessful()){
                        if(!dBhelper.removeVaccination(vaccination))
                            Log.e("ERROR DELETING", response.message());
                    }else{
                        Log.i("RES ERR Vaccinations", response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<Vaccination> call, Throwable t) {
                    Log.e("ERROR VACCINATIONS", t.getMessage());
                }
            });
        }
    }

}
