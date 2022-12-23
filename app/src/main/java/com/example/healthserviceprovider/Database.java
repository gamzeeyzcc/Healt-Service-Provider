package com.example.healthserviceprovider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Database {
    static Database instance;
    SQLiteDatabase db;
    Cursor c = null;
    private SQLiteOpenHelper openHelper;

    public Database(Context context) {
        this.openHelper = new DatabaseHelper(context);
    }

    public static Database getInstance(Context context) {
        if (instance == null) {
            instance = new Database(context);
        }
        return instance;
    }

    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    public void close() {
        if (db != null) {
            this.db.close();
            this.db.close();
        }
    }
    //Veri tabanından bir metin döndürür
    public String getText(String sql) {
        open();
        c = db.rawQuery(sql, null);
        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()) {
            String adress = c.getString(0);
            buffer.append("" + adress);
        }
        close();
        return buffer.toString();
    }
    //Bir sorgu çalıştırır
    public boolean RunQuery(String sql) {
        open();
        boolean res = true;
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            Log.d("Error:",e.getLocalizedMessage());
            res = false;
        } finally {
            close();
        }
        return res;
    }
    //Provider'ın kullanıcının favorilerinde olup olmadığını kontrol eder
    public boolean IsinFavorites(int uid,int pid){
        boolean res = false;
        open();
        c = db.rawQuery("SELECT * FROM Favorites WHERE userid="+uid+" AND providerid="+pid, null);
        if (c.moveToNext()) {
            res = true;
        }
        close();
        return res;
    }
    //Sorguya göre providerları liste olarak getirir
    public List<Provider> GetProviders(String sql) {
        open();
        List<Provider> modelList = new ArrayList<Provider>();
        c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            Provider model = new Provider();
            model.setId(c.getInt(0));
            model.setDiplomaNo(c.getString(1));
            model.setPassword(c.getString(2));
            model.setName(c.getString(3));
            model.setProfession(c.getString(4));
            model.setLat(c.getDouble(5));
            model.setLng(c.getDouble(6));
            modelList.add(model);
        }
        close();
        return modelList;
    }
    //Kullanıcı girişini sağlar
    public User UserLogin(String identity, String password) {
        open();
        User user = null;
        c = db.rawQuery("SELECT * FROM user WHERE identity='" + identity + "' AND password='" + password + "'", null);
        if (c.moveToNext()) {
            user = new User();
            user.setId(c.getInt(0));
            user.setIdentity(c.getString(1));
            user.setPassword(c.getString(2));
            user.setNamesurname(c.getString(3));
        }
        close();
        return user;
    }
    //Provider girişini sağlar
    public Provider ProviderLogin(String diplomano, String password) {
        open();
        Provider model = null;
        c = db.rawQuery("SELECT * FROM provider WHERE diplomano='" + diplomano + "' AND password='" + password + "'", null);
        if (c.moveToNext()) {
            model = new Provider();
            model.setId(c.getInt(0));
            model.setDiplomaNo(c.getString(1));
            model.setPassword(c.getString(2));
            model.setName(c.getString(3));
            model.setProfession(c.getString(4));
            model.setLat(c.getDouble(5));
            model.setLng(c.getDouble(6));
        }
        close();
        return model;
    }

    //ID'ye göre provider döndürür
    public Provider GetProvider(int providerId) {
        open();
        Provider model = null;
        c = db.rawQuery("SELECT * FROM provider WHERE id=" + providerId, null);
        if (c.moveToNext()) {
            model = new Provider();
            model.setId(c.getInt(0));
            model.setDiplomaNo(c.getString(1));
            model.setPassword(c.getString(2));
            model.setName(c.getString(3));
            model.setProfession(c.getString(4));
            model.setLat(c.getDouble(5));
            model.setLng(c.getDouble(6));
        }
        close();
        return model;
    }
    //ID'ye göre user döndürür
    public User GetUser(int userId) {
        open();
        User model = null;
        c = db.rawQuery("SELECT * FROM user WHERE id=" + userId, null);
        if (c.moveToNext()) {
            model = new User();
            model.setId(c.getInt(0));
            model.setIdentity(c.getString(1));
            model.setPassword(c.getString(2));
            model.setNamesurname(c.getString(3));
        }
        close();
        return model;
    }
    //Provider'a en az 1 mesaj gönderen kullanıcıları döndürür
    public ArrayList<User> GetUsersThatSendMessage(int providerId) {
        open();
        ArrayList<User> modelList = new ArrayList<User>();
        c = db.rawQuery("SELECT * FROM User WHERE id IN (SELECT DISTINCT userid FROM messages WHERE providerid=" + providerId + ")", null);
        while (c.moveToNext()) {
            User model = new User();
            model.setId(c.getInt(0));
            model.setIdentity(c.getString(1));
            model.setPassword(c.getString(2));
            model.setNamesurname(c.getString(3));
            modelList.add(model);
        }
        close();
        return modelList;
    }
    //Provider'ın randevularını döndürür
    public ArrayList<Appointment> GetAppointmentsOfProvider(int providerId) {
        open();
        ArrayList<Appointment> modelList = new ArrayList<Appointment>();
        c = db.rawQuery("SELECT DISTINCT ap.id,ap.providerid,ap.userid,ap.date,p.name,u.namesurname,CASE WHEN ap.date = date('now') THEN 0 WHEN ap.date < date('now') THEN 1 ELSE 2 END FROM appointment ap " +
                "INNER JOIN provider p ON p.id=ap.providerid INNER JOIN user u ON u.id=ap.userid WHERE ap.providerid=" + providerId + " ORDER BY ap.date", null);
        while (c.moveToNext()) {
            Appointment model = new Appointment();
            model.setId(c.getInt(0));
            model.setProviderid(c.getInt(1));
            model.setUserid(c.getInt(2));
            model.setDate(c.getString(3));
            model.setProviderName(c.getString(4));
            model.setPatientName(c.getString(5));
            modelList.add(model);
        }
        close();
        return modelList;
    }
    //Hasta'nın randevularını döndürür
    public ArrayList<Appointment> GetAppointmentsOfPatient(int userid) {
        open();
        ArrayList<Appointment> modelList = new ArrayList<Appointment>();
        c = db.rawQuery("SELECT DISTINCT ap.id,ap.providerid,ap.userid,ap.date,p.name,u.namesurname,CASE WHEN ap.date = date('now') THEN 0 WHEN ap.date < date('now') THEN 1 ELSE 2 END as status FROM appointment ap " +
                "INNER JOIN provider p ON p.id=ap.providerid INNER JOIN user u ON u.id=ap.userid WHERE ap.userid=" + userid + " AND status != 2  ORDER BY ap.date", null);
        while (c.moveToNext()) {
            Appointment model = new Appointment();
            model.setId(c.getInt(0));
            model.setProviderid(c.getInt(1));
            model.setUserid(c.getInt(2));
            model.setDate(c.getString(3));
            model.setProviderName(c.getString(4));
            model.setPatientName(c.getString(5));
            modelList.add(model);
        }
        close();
        return modelList;
    }
    //Bir hastanın, belli bir hastaneye olan randevularını döndürür
    public ArrayList<Appointment> GetAppointmentsOfPatientAndProvider(int userid,int providerid) {
        open();
        ArrayList<Appointment> modelList = new ArrayList<Appointment>();
        c = db.rawQuery("SELECT DISTINCT ap.id,ap.providerid,ap.userid,ap.date,p.name,u.namesurname,CASE WHEN ap.date = date('now') THEN 0 WHEN ap.date < date('now') THEN 1 ELSE 2 END FROM appointment ap " +
                "INNER JOIN provider p ON p.id=ap.providerid INNER JOIN user u ON u.id=ap.userid WHERE ap.userid=" + userid + " AND ap.providerid="+providerid+" ORDER BY ap.date", null);
        while (c.moveToNext()) {
            Appointment model = new Appointment();
            model.setId(c.getInt(0));
            model.setProviderid(c.getInt(1));
            model.setUserid(c.getInt(2));
            model.setDate(c.getString(3));
            model.setProviderName(c.getString(4));
            model.setPatientName(c.getString(5));
            model.setStatus(c.getInt(6));
            modelList.add(model);
        }
        close();
        return modelList;
    }
    //İki kişi arasındaki mesajları döndürür
    public ArrayList<UserMessage> GetMessagesBetween(int providerId, int userId) {
        open();
        ArrayList<UserMessage> modelList = new ArrayList<UserMessage>();
        c = db.rawQuery("SELECT messages.id,userid,providerid,message, CASE WHEN sender='Provider' THEN p.name ELSE u.namesurname END " +
                "FROM messages INNER JOIN user u ON u.id=messages.userid INNER JOIN provider p ON p.id=messages.providerid WHERE providerid=" + providerId + " AND userid=" + userId + " ORDER BY messages.id", null);
        while (c.moveToNext()) {
            UserMessage model = new UserMessage();
            model.setId(c.getInt(0));
            model.setUserId(c.getInt(1));
            model.setProviderId(c.getInt(2));
            model.setMessage(c.getString(3));
            model.setSender(c.getString(4));
            modelList.add(model);
        }
        close();
        return modelList;
    }
}
