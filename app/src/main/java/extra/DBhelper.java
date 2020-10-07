package extra;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBhelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "projet_campagne_vaccination.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    static Context context;

    public DBhelper(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    public List<Vaccination> getVaccinationList(){
        List<Vaccination> vaccinationList = new ArrayList<Vaccination>();
        Vaccination vac = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM vaccination ;", null);
        if(cur!=null){
            while(cur.moveToNext()){
                vac = new Vaccination(cur.getLong(cur.getColumnIndex("id")),
                        cur.getString(cur.getColumnIndex("date_vaccination")),
                        cur.getDouble(cur.getColumnIndex("longiude")),
                        cur.getDouble(cur.getColumnIndex("latitude")),
                        cur.getInt(cur.getColumnIndex("nombre_enfant")),
                        cur.getString(cur.getColumnIndex("tranche_age")),
                        this.getCampagne(cur.getLong(cur.getColumnIndex("campagne"))),
                        this.getMoughataa(cur.getLong(cur.getColumnIndex("moughataa"))),
                        new AppUser(cur.getLong(cur.getColumnIndex("user"))),
                        this.getVaccin(cur.getLong(cur.getColumnIndex("vaccin")))
                );
                vaccinationList.add(vac);
            }
        }
        cur.close();
        db.close();
        return vaccinationList;
    }

    public boolean addVaccination(Vaccination vaccination){
        boolean added = false;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("INSERT INTO Vaccination (id, date_vaccination, longiude, latitude, nombre_enfant, campagne, moughataa, user, tranche_age, vaccin) VALUES ("+
                    vaccination.getId()+", '"+
                    vaccination.getDate_vaccination()+"', "+
                    vaccination.getLongiude()+", "+
                    vaccination.getLatitude()+", "+
                    vaccination.getNombre_enfant()+", "+
                    vaccination.getCampagne().getId()+", "+
                    vaccination.getMoughataa().getId()+", "+
                    vaccination.getUser().getId()+", '"+
                    vaccination.getTranche_age()+"', '"+
                    vaccination.getVaccin().getId()+"');");
            added = true;
            vaccination.getDate_vaccination();
        } catch (Exception e){
            Log.println(Log.ERROR,"[-] Error vaccination: ", e.getMessage());
        }
        db.close();
        return added;
    }

    public boolean removeVaccination(Vaccination vaccination){
        boolean deleted = false;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM vaccination WHERE id = "+vaccination.getId());
            deleted = true;
        } catch (Exception e){
            Log.println(Log.ERROR,"[-] Error vaccination: ", e.getMessage());
        }
        db.close();
        return deleted;
    }

    public Moughataa getMoughataa(Long id){
        Moughataa moughataa = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curs = db.rawQuery("SELECT * FROM Moughataa WHERE id = "+id+" ;", null);
        if(curs!=null){
            if(curs.moveToNext()){
                moughataa = new Moughataa(curs.getLong(curs.getColumnIndex("id")), curs.getString(curs.getColumnIndex("moughataaname")));
            }
        }
        curs.close();
        db.close();
        return moughataa;
    }

    public Moughataa getMoughataByNameAndWilaya(String name, Long idWilaya){
        Moughataa moughataa = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curs = db.rawQuery("SELECT * FROM Moughataa WHERE moughataaname = '"+name+"' AND wilaya_id =  "+idWilaya+";", null);
        if(curs!=null){
            if(curs.moveToNext()){
                moughataa = new Moughataa(curs.getLong(curs.getColumnIndex("id")), curs.getString(curs.getColumnIndex("moughataaname")));
            }
        }
        curs.close();
        db.close();
        return moughataa;
    }

    public List<Moughataa> getMoughtaasByWilaya(Long idWilaya){
        List<Moughataa> moughataaList = new ArrayList<Moughataa>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curs = db.rawQuery("SELECT * FROM Moughataa WHERE wilaya_id = "+idWilaya+";", null);
        if(curs!=null){
            Moughataa moughataa = new Moughataa();
            while(curs.moveToNext()){
                moughataaList.add(new Moughataa(curs.getLong(curs.getColumnIndex("id")), curs.getString(curs.getColumnIndex("moughataaname"))));
            }
        }
        curs.close();
        db.close();
        return moughataaList;
    }

    public List<Moughataa> getMoughataaList(){
        List<Moughataa> moughataaList = new ArrayList<Moughataa>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curs = db.rawQuery("SELECT * FROM Moughataa ;", null);
        if(curs!=null){
            Moughataa moughataa = new Moughataa();
            while(curs.moveToNext()){
                moughataaList.add(new Moughataa(curs.getLong(curs.getColumnIndex("id")), curs.getString(curs.getColumnIndex("moughataaname"))));
            }
        }
        curs.close();
        db.close();
        return moughataaList;
    }

    public boolean addMoughataa(Moughataa moughataa){
        boolean added = false;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(
                    "INSERT INTO Moughataa (id, moughataaname, wilaya_id) VALUES ("+
                    moughataa.getId()+", '"+
                    moughataa.getMoughataaname()+"'," +
                    moughataa.getWilaya().getId()+");"
            );
            added = true;
        } catch (Exception e){
            Log.d("[-] Error moughataa: ", e.getMessage());
        }
        db.close();
        return added;
    }

    public Wilaya getWilaya(Long id){
        Wilaya wilaya = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curs = db.rawQuery("SELECT * FROM wilaya WHERE id = "+id+" ;", null);
        if(curs!=null){
            if(curs.moveToNext()){
                wilaya = new Wilaya(curs.getLong(curs.getColumnIndex("id")), curs.getString(curs.getColumnIndex("name")));
            }
        }
        curs.close();
        db.close();
        return wilaya;
    }

    public Wilaya getWilayaByName(String name){
        Wilaya wilaya = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curs = db.rawQuery("SELECT * FROM wilaya WHERE name = '"+name+"' ;", null);
        if(curs!=null){
            if(curs.moveToNext()){
                wilaya = new Wilaya(curs.getLong(curs.getColumnIndex("id")), curs.getString(curs.getColumnIndex("name")));
            }
        }
        curs.close();
        db.close();
        return wilaya;
    }

    public List<Wilaya> getWilayaList(){
        List<Wilaya> wilayaList = new ArrayList<Wilaya>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curs = db.rawQuery("SELECT * FROM wilaya ;", null);
        if(curs!=null){
            Moughataa moughataa = new Moughataa();
            while(curs.moveToNext()){
                wilayaList.add(new Wilaya(curs.getLong(curs.getColumnIndex("id")), curs.getString(curs.getColumnIndex("name"))));
            }
        }
        curs.close();
        db.close();
        return wilayaList;
    }

    public boolean addWilaya(Wilaya wilaya){
        boolean added = false;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(
                    "INSERT INTO wilaya (id, name) VALUES ("+
                            wilaya.getId()+", '"+
                            wilaya.getName()+"');"
            );
            added = true;
        } catch (Exception e){
            Log.d("[-] Error moughataa: ", e.getMessage());
        }
        db.close();
        return added;
    }

    public Campagne getCampagne(Long id){
        Campagne campagne = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curs = db.rawQuery("SELECT * FROM Campagne WHERE id = "+id+" ;", null);
        if(curs!=null){
            if(curs.moveToNext()){
                campagne = new Campagne(curs.getLong(curs.getColumnIndex("id")),
                                        curs.getString(curs.getColumnIndex("name")),
                                        curs.getString(curs.getColumnIndex("date")),
                                        curs.getString(curs.getColumnIndex("statut"))
                                        );
            }
        }
        curs.close();
        db.close();
        return campagne;
    }

    public List<Campagne> getCampagneList(){
        List<Campagne> campagneList = new ArrayList<Campagne>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curs = db.rawQuery("SELECT * FROM Campagne", null);
        if(curs!=null){
            Campagne campagne = new Campagne();
            while(curs.moveToNext()){
                campagneList.add(new Campagne(curs.getLong(curs.getColumnIndex("id")), curs.getString(curs.getColumnIndex("name")), curs.getString(curs.getColumnIndex("date")), curs.getString(curs.getColumnIndex("statut"))));
            }
        }
        curs.close();
        db.close();
        return campagneList;
    }

    public boolean addCampagne(Campagne campagne){
        boolean added = false;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(
                    "INSERT INTO Campagne (id, name, date, statut) VALUES ("+
                            campagne.getId()+", '"+
                            campagne.getName()+"', '"+
                            campagne.getDate()+"', '"+
                            campagne.getStatut()+"');"
            );
            added = true;
        } catch (Exception e){
            Log.d("[-] Error campagne: ", e.getMessage());
        }
        db.close();
        return added;
    }

    public Vaccin getVaccin(Long id){
        Vaccin vaccin = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curs = db.rawQuery("SELECT * FROM Vaccin WHERE id = "+id+" ;", null);
        if(curs!=null){
            if(curs.moveToNext()){
                vaccin = new Vaccin(curs.getLong(curs.getColumnIndex("id")),
                        curs.getString(curs.getColumnIndex("nom_vaccin")),
                        this.getCampagne(curs.getLong(curs.getColumnIndex("campagne")))
                );
            }
        }
        curs.close();
        db.close();
        return vaccin;
    }

    public List<Vaccin> getVaccinsList(){
        List<Vaccin> vaccinList = new ArrayList<Vaccin>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curs = db.rawQuery("SELECT * FROM Vaccin", null);
        if(curs!=null){
            Vaccin vaccin = new Vaccin();
            while(curs.moveToNext()){
                vaccinList.add(new Vaccin(curs.getLong(curs.getColumnIndex("id")), curs.getString(curs.getColumnIndex("nom_vaccin")), this.getCampagne(curs.getLong(curs.getColumnIndex("campagne")))));
            }
        }
        curs.close();
        db.close();
        return vaccinList;
    }

    public boolean addVaccin(Vaccin vaccin){
        boolean added = false;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(
                    "INSERT INTO vaccin (id, nom_vaccin, campagne) VALUES ("+
                            vaccin.getId()+", '"+
                            vaccin.getNom_vaccin()+"', "+
                            vaccin.getCampagne().getId()+");"
            );
            added = true;
        } catch (Exception e){
            Log.d("[-] Error vaccin: ", e.getMessage());
        }
        db.close();
        return added;
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    public void CopyDataBaseFromAsset() throws IOException {
        //////////

        //////////
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        // Path to the just created empty db
        String outFileName = getDatabasePath();

        // if the path doesn't exist first, create it
        File f = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();
        else{

        }

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
    private static String getDatabasePath() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    ///// Synchroniser les wilayas
    public void synWilayas(List<Wilaya> newWilayas){
        List<Wilaya> wilayas = this.getWilayaList();
        if(wilayas.size()==0){
            for(Wilaya wil : newWilayas){
                this.addWilaya(wil);
            }
            return;
        }
        if(wilayas.size()==newWilayas.size()){
            return;
        }else{
            if(newWilayas.size()>wilayas.size()){
                int i = 0;
                for(i=0;i<newWilayas.size();i++){
                    boolean match = false;
                    int j=0;
                    for(j=0;j<wilayas.size();j++){
                        if(newWilayas.get(i).getName().equals(wilayas.get(j).getName())){
                            match = true;
                            break;
                        }
                        if(!match){
                            this.addWilaya(new Wilaya(null, newWilayas.get(i).getName()));
                        }
                    }
                }
            }
        }
    }

    ////Synchroniser les moughataas
    public void synMoughtaas(List<Moughataa> newMoughataas){
        List<Moughataa> mgts = this.getMoughataaList();
        if(mgts.size()==0){
            for(Moughataa mgt : newMoughataas){
                this.addMoughataa(mgt);
            }
            return;
        }
        if(mgts.size()==newMoughataas.size()){
            return;
        }else{
            if(newMoughataas.size()>mgts.size()){
                int i = 0;
                for(i=0;i<newMoughataas.size();i++){
                    boolean match = false;
                    int j=0;
                    for(j=0;j<mgts.size();j++){
                        if(newMoughataas.get(i).getMoughataaname().equals(mgts.get(j).getMoughataaname())){
                            match = true;
                            break;
                        }
                        if(!match){
                            this.addMoughataa(new Moughataa(null, newMoughataas.get(i).getMoughataaname()));
                        }
                    }
                }
            }
        }
    }

    ////Synchroniser les vaccins
    public void synVaccins(List<Vaccin> newVaccins){
        List<Vaccin> vaccins = this.getVaccinsList();
        if(vaccins.size()==0){
            for(Vaccin vac : newVaccins){
                this.addVaccin(vac);
            }
            return;
        }
        if(vaccins.size()==newVaccins.size()){
            return;
        }else{
            if(newVaccins.size()>vaccins.size()){
                int i = 0;
                for(i=0;i<newVaccins.size();i++){
                    boolean match = false;
                    int j=0;
                    for(j=0;j<vaccins.size();j++){
                        if(newVaccins.get(i).getNom_vaccin().equals(vaccins.get(j).getNom_vaccin())){
                            match = true;
                            break;
                        }
                        if(!match){
                            this.addVaccin(new Vaccin(null, newVaccins.get(i).getNom_vaccin(), newVaccins.get(i).getCampagne()));
                        }
                    }
                }
            }
        }
    }

    ////Synchroniser les campagnes
    public void synCampagnes(List<Campagne> newCampagnes) {
        List<Campagne> campagnes = this.getCampagneList();
        if(campagnes.size()==0){
            for(Campagne camp : newCampagnes){
                this.addCampagne(camp);
            }
            return;
        }
        if(campagnes.size()==newCampagnes.size()){
            return;
        }else{
            if(newCampagnes.size()>campagnes.size()){
                int i = 0;
                for(i=0;i<newCampagnes.size();i++){
                    boolean match = false;
                    int j=0;
                    for(j=0;j<campagnes.size();j++){
                        if(newCampagnes.get(i).getName().equals(campagnes.get(j).getName())){
                            match = true;
                            break;
                        }
                        if(!match){
                            this.addCampagne(new Campagne(null, newCampagnes.get(i).getName(), newCampagnes.get(i).getDate(), newCampagnes.get(i).getStatut()));
                        }
                    }
                }
            }
        }
    }
}
