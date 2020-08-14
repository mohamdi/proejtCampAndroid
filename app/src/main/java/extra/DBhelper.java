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

    public Vaccination getVaccination(Long id){
        Vaccination vac = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM vaccination WHERE id = "+id+" ;", null);
        if(cur!=null){
            if(cur.moveToNext()){
                //Constructor id, date, lon, lat, nbrEnfant, camp, enfants, moughataa, user
                vac = new Vaccination(null,
                    cur.getString(cur.getColumnIndex("date_vaccination")),
                    cur.getDouble(cur.getColumnIndex("longiude")),
                    cur.getDouble(cur.getColumnIndex("latitude")),
                    cur.getString(cur.getColumnIndex("nombre")),
                    this.getCampagne(cur.getLong(cur.getColumnIndex("campagne"))),
                    null,
                    this.getMoughataa(cur.getLong(cur.getColumnIndex("moughataa"))),
                        new AppUser(cur.getLong(cur.getColumnIndex("user")))
                    );
                vac.setVaccin(cur.getString(cur.getColumnIndex("vaccin")));
            }
        }
        cur.close();
        db.close();
        return vac;
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
                        cur.getString(cur.getColumnIndex("nombre")),
                        this.getCampagne(cur.getLong(cur.getColumnIndex("campagne"))),
                        null,
                        this.getMoughataa(cur.getLong(cur.getColumnIndex("moughataa"))),
                        new AppUser(cur.getLong(cur.getColumnIndex("user")))
                );
                vac.setVaccin(cur.getString(cur.getColumnIndex("vaccin")));
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
            db.execSQL("INSERT INTO Vaccination (id, date_vaccination, longiude, latitude, nombre, campagne, moughataa, user, trancheAge, vaccin) VALUES ("+
                    vaccination.getId()+", '"+
                    vaccination.getDate_vaccination()+"', "+
                    vaccination.getLongiude()+", "+
                    vaccination.getLatitude()+", "+
                    vaccination.getNombre()+", "+
                    vaccination.getCampagne().getId()+", "+
                    vaccination.getMoughataa().getId()+", "+
                    vaccination.getUser().getId()+", '"+
                    vaccination.getTrancheAge()+"', '"+
                    vaccination.getVaccin()+"');");
            added = true;
            vaccination.getDate_vaccination();
        } catch (Exception e){
            Log.println(Log.ERROR,"[-] Error vaccination: ", e.getMessage());
        }
        db.close();
        return added;
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
                    "INSERT INTO Moughataa (id, moughataaname) VALUES ("+
                    moughataa.getId()+", "+
                    moughataa.getMoughataaname()+");"
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
                            campagne.getId()+", "+
                            campagne.getName()+", "+
                            campagne.getDate()+", "+
                            campagne.getStatut()+");"
            );
            added = true;
        } catch (Exception e){
            Log.d("[-] Error campagne: ", e.getMessage());
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

}
