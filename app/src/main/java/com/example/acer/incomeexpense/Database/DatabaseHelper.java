package com.example.acer.incomeexpense.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.acer.incomeexpense.Common;
import com.example.acer.incomeexpense.Fragments.IncomeFragment;
import com.example.acer.incomeexpense.Fragments.LoanFragment;
import com.example.acer.incomeexpense.Fragments.RecordFragment;
import com.example.acer.incomeexpense.MainActivity;
import com.example.acer.incomeexpense.models.Income_model;
import com.example.acer.incomeexpense.models.Loan_model;
import com.example.acer.incomeexpense.models.Records_model;
import com.example.acer.incomeexpense.models.User_model;

import java.util.ArrayList;
import java.util.List;

import static android.provider.Telephony.TextBasedSmsColumns.PERSON;
import static com.example.acer.incomeexpense.Fragments.RecordFragment.date;

public class DatabaseHelper extends SQLiteOpenHelper {

    public SharedPreferences preferences;
    public String phoneNumber, totalexpense, selectedDate;


    public static final String USER_TABLE_NAME = "User";
    public static final String USER_COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PASSWORD = "password";
    public static final String USERINFO_WALLET = "wallet";
    public static final String USERINFO_EXPENSELIMIT = "user_limit";

    public static final String USER_TABLE = "CREATE TABLE " + USER_TABLE_NAME
            + "("
            + USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_EMAIL + " TEXT, " + COLUMN_NAME + " TEXT, "
            + COLUMN_PHONE + " TEXT, " + COLUMN_PASSWORD + " TEXT, " +USERINFO_WALLET + " TEXT, " +USERINFO_EXPENSELIMIT + " TEXT " + ");";


    public static final String TABLE_NAME = "Record";
    public static final String RECORD_ID = "record_id";
    public static final String COLUMN_ID = "id";
    public static final String NAME = "itemname";
    public static final String LOCATION = "location";
    public static final String DATE = "date";
    public static final String MONTH = "month";
    public static final String YEAR = "year";
    public static final String USERNUMBER = "usernum";
    public static final String PRICE = "rupee";
    public static final String ONLYDATE = "onlydate";
    public static final String ONLTMONTH = "onlymonth";
    public static final String TABLE = "CREATE TABLE " + TABLE_NAME
            + "("
            + RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ID + " TEXT, " + NAME + " TEXT, "
            + LOCATION + " TEXT, " + DATE + " TEXT, " + MONTH + " TEXT, " + YEAR + " TEXT, " + USERNUMBER + " TEXT, " + ONLYDATE + " TEXT, " + ONLTMONTH + " TEXT, " + PRICE + " INTEGER " + ");";

    public static final String LOAN_TABLE = "Loan";
    public static final String LOAN_ID = "loan_id";
    public static final String LOAN_COLUMN_ID = "id";
    public static final String PERSON_NAME = "personame";
    public static final String LOAN_DATE = "date";
    public static final String LOAN_MONTH = "month";
    public static final String LOAN_YEAR = "year";
    public static final String L_USERNUMBER = "usernum";
    public static final String LOAN_GIVEN = "loan_rupee";
    public static final String LOAN_DUEDATE = "due_date";
    public static final String GIVEN_TYPE = "giventype";
    public static final String LOAN_ONLYDATE = "loan_onlydate";


    public static final String TABLE_LOAN = "CREATE TABLE " + LOAN_TABLE
            + "("
            + LOAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + LOAN_COLUMN_ID + " TEXT, " + PERSON_NAME + " TEXT, "
            + LOAN_DATE + " TEXT, " + LOAN_MONTH + " TEXT, " + LOAN_YEAR + " TEXT, " + L_USERNUMBER + " TEXT, " + LOAN_DUEDATE + " TEXT, " + GIVEN_TYPE + " TEXT,  " + LOAN_ONLYDATE + " TEXT,  " + LOAN_GIVEN + " INTEGER " + ");";


    public static final String TABLE_INCOME = "Income";
    public static final String INCOME_ID = "income_id";
    public static final String INCOME_COLUMN_ID = "id";
    public static final String INCOME_NAME = "incomesource";
    public static final String INCOME_DATE = "date";
    public static final String INCOME_MONTH = "month";
    public static final String INCOME_YEAR = "year";
    public static final String INCOME_USERNUMBER = "in_usernum";
    public static final String INCOME_PRICE = "income_rupee";
    public static final String INCOME_ONLYDATE = "onlydate";
    public static final String INCOME_ONLTMONTH = "onlymonth";
    public static final String INCOME_TABLE = "CREATE TABLE " + TABLE_INCOME
            + "("
            + INCOME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + INCOME_COLUMN_ID + " TEXT, " + INCOME_NAME + " TEXT, "
            + INCOME_DATE + " TEXT, " + INCOME_MONTH + " TEXT, " + INCOME_YEAR + " TEXT, " + INCOME_USERNUMBER + " TEXT, " + INCOME_ONLYDATE + " TEXT, " + INCOME_ONLTMONTH + " TEXT, " + INCOME_PRICE + " INTEGER " + ");";


//    public static final String TABLE_USERINFO = "userinfo";
//    public static final String USERINFO_ID = "userinfo_id";
//    public static final String USERINFO_COLUMN_ID = "id";
//    public static final String USERINFO_WALLET = "user_wallet";
//    public static final String USERINFO_EXPENSELIMIT = "user_limit";
//
//    public static final String USERINFO_TABLE = "CREATE TABLE " + TABLE_USERINFO
//            + "("
//            + USERINFO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + USERINFO_COLUMN_ID + " TEXT, " + USERINFO_WALLET + " TEXT, "
//            + USERINFO_EXPENSELIMIT + " TEXT " + ");";



    Context context;

    private static final int DatabaseVersion = 1;
    private static final String DatabaseName = "IncomeExpense.db";


    public DatabaseHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        this.context = context;
        preferences = this.context.getSharedPreferences(Common.sharedPreferences, Context.MODE_PRIVATE);
        phoneNumber = preferences.getString("Number", null);
        totalexpense = preferences.getString("Total", null);
        selectedDate = preferences.getString("Date", null);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(USER_TABLE);
        db.execSQL(TABLE);
        db.execSQL(TABLE_LOAN);
        db.execSQL(INCOME_TABLE);
     //   db.execSQL(USERINFO_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + LOAN_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_INCOME);
      //  db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERINFO);


    }

    public long insertUserData(String email, String name, String password, String number) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("email", email);

        values.put("name", name);

        values.put("phone", number);

        values.put("password", password);

        values.put("wallet", "0");

        values.put("user_limit", "0");


        //insert row
        long id = db.insert(USER_TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public List<User_model> getalldata() {
        String query;

        query = String.format("SELECT * FROM User WHERE phone='%s'", phoneNumber);


        List<User_model> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            do {
                User_model model = new User_model();
                model.setId(cursor.getInt(cursor.getColumnIndex(USER_COLUMN_ID)));
                model.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                model.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                model.setNumber(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
                model.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
                model.setWallet(cursor.getString(cursor.getColumnIndex(USERINFO_WALLET)));
                model.setLimit(cursor.getString(cursor.getColumnIndex(USERINFO_EXPENSELIMIT)));

                models.add(model);


            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }

    public boolean getData(String number, String password) {
        // get readable database as we are not inserting anything

        SQLiteDatabase db = this.getReadableDatabase();
        String Query = String.format("SELECT * FROM User WHERE phone='%s' AND password='%s'", number, password);

        Cursor cursor = db.rawQuery(Query, null);


        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            return false;
        }

    }

    public long insertRecordData(String item, String rupee, String time, String month, String year, String odate, String omonth, String usernum, String location) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("itemname", item);
        values.put(USERNUMBER, usernum);

        values.put("rupee", rupee);
        values.put(LOCATION, location);
        values.put(DATE, time);
        values.put(ONLYDATE, odate);
        values.put(ONLTMONTH, omonth);

        values.put(MONTH, month);
        values.put(YEAR, year);


        //insert row
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public List<Records_model> getallrecords() {

        String query = String.format("SELECT  * FROM Record Where usernum='%s' ", RecordFragment.usernum);


        List<Records_model> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            do {

                Records_model model = new Records_model();


                model.setId(cursor.getString(cursor.getColumnIndex(RECORD_ID)));
                model.setItem(cursor.getString(cursor.getColumnIndex(NAME)));
                model.setRupee(cursor.getString(cursor.getColumnIndex(PRICE)));
                model.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                model.setOnlydate(cursor.getString(cursor.getColumnIndex(ONLYDATE)));
                model.setOnlymonth(cursor.getString(cursor.getColumnIndex(ONLTMONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(YEAR)));
                model.setUsernumber(cursor.getString(cursor.getColumnIndex(USERNUMBER)));
                model.setAddress(cursor.getString(cursor.getColumnIndex(LOCATION)));


                models.add(model);


            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }

    public Records_model getalldatarec(long id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM Record Where usernum='%s' ", phoneNumber);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();

        Records_model model = new Records_model();

        cursor.getString(cursor.getColumnIndex(RECORD_ID));
        cursor.getString(cursor.getColumnIndex(NAME));
        cursor.getString(cursor.getColumnIndex(PRICE));
        cursor.getString(cursor.getColumnIndex(DATE));
        cursor.getString(cursor.getColumnIndex(USERNUMBER));
        cursor.getString(cursor.getColumnIndex(LOCATION));

        cursor.close();
        return model;

    }

    public double totalsum() {
        double grandprice = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("Select SUM (rupee) From Record Where usernum='%s'", RecordFragment.usernum);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {

            cursor.moveToFirst();
            grandprice = cursor.getDouble(0);

        }
        cursor.close();
        return grandprice;
    }

    public ArrayList<Records_model> byDate(String date) {

        String query = String.format("Select * From Record Where date='%s' AND usernum='%s'", date, RecordFragment.usernum);


        ArrayList<Records_model> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            do {

                Records_model model = new Records_model();

                model.setId(cursor.getString(cursor.getColumnIndex(RECORD_ID)));
                model.setItem(cursor.getString(cursor.getColumnIndex(NAME)));
                model.setRupee(cursor.getString(cursor.getColumnIndex(PRICE)));
                model.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                model.setOnlydate(cursor.getString(cursor.getColumnIndex(ONLYDATE)));
                model.setOnlymonth(cursor.getString(cursor.getColumnIndex(ONLTMONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(YEAR)));
                model.setUsernumber(cursor.getString(cursor.getColumnIndex(USERNUMBER)));
                model.setAddress(cursor.getString(cursor.getColumnIndex(LOCATION)));


                models.add(model);


            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }

    //bymonth
    public ArrayList<Records_model> byMonth(String month) {

        String query = String.format("Select * From Record Where month='%s' AND usernum='%s'", month, RecordFragment.usernum);


        ArrayList<Records_model> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            do {

                Records_model model = new Records_model();
                model.setId(cursor.getString(cursor.getColumnIndex(RECORD_ID)));
                model.setItem(cursor.getString(cursor.getColumnIndex(NAME)));
                model.setRupee(cursor.getString(cursor.getColumnIndex(PRICE)));
                model.setMonth(cursor.getString(cursor.getColumnIndex(MONTH)));
                model.setOnlydate(cursor.getString(cursor.getColumnIndex(ONLYDATE)));
                model.setOnlymonth(cursor.getString(cursor.getColumnIndex(ONLTMONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(YEAR)));
                model.setUsernumber(cursor.getString(cursor.getColumnIndex(USERNUMBER)));
                model.setAddress(cursor.getString(cursor.getColumnIndex(LOCATION)));

                models.add(model);


            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }

    //byYear

    public ArrayList<Records_model> byYear(String year) {

        String query = String.format("Select * From Record Where year='%s' AND usernum='%s'", year, RecordFragment.usernum);


        ArrayList<Records_model> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            do {

                Records_model model = new Records_model();
                model.setId(cursor.getString(cursor.getColumnIndex(RECORD_ID)));
                model.setItem(cursor.getString(cursor.getColumnIndex(NAME)));
                model.setRupee(cursor.getString(cursor.getColumnIndex(PRICE)));
                model.setYear(cursor.getString(cursor.getColumnIndex(YEAR)));
                model.setOnlydate(cursor.getString(cursor.getColumnIndex(ONLYDATE)));
                model.setOnlymonth(cursor.getString(cursor.getColumnIndex(ONLTMONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(YEAR)));
                model.setUsernumber(cursor.getString(cursor.getColumnIndex(USERNUMBER)));
                model.setAddress(cursor.getString(cursor.getColumnIndex(LOCATION)));


                models.add(model);


            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }

    public double totalsumofDate(String date) {
        double grandprice = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("Select SUM (rupee) From Record Where date='%s' AND usernum='%s'", date, RecordFragment.usernum);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {

            cursor.moveToFirst();
            grandprice = cursor.getDouble(0);

        }
        cursor.close();
        return grandprice;
    }

    public double totalsumofMonthh(String month) {
        double grandprice = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("Select SUM (rupee) From Record Where month='%s' AND usernum='%s'", month, RecordFragment.usernum);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {

            cursor.moveToFirst();
            grandprice = cursor.getDouble(0);

        }
        cursor.close();
        return grandprice;
    }

    public double totalsumofYear(String year) {
        double grandprice = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("Select SUM (rupee) From Record Where year='%s' AND usernum='%s'", year, RecordFragment.usernum);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {

            cursor.moveToFirst();
            grandprice = cursor.getDouble(0);

        }
        cursor.close();
        return grandprice;
    }

    public void removeRecord(String recordId) {

        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete()

        String Query = String.format("DELETE FROM Record WHERE record_id='%s'", recordId);
        db.execSQL(Query);

    }

    public List<Records_model> getRecentrecords(String date) {

        String query = String.format("SELECT  * FROM Record Where usernum='%s' AND date='%s'", RecordFragment.usernum, date);

        List<Records_model> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            do {

                Records_model model = new Records_model();
                model.setId(cursor.getString(cursor.getColumnIndex(RECORD_ID)));
                model.setItem(cursor.getString(cursor.getColumnIndex(NAME)));
                model.setRupee(cursor.getString(cursor.getColumnIndex(PRICE)));
                model.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                model.setOnlydate(cursor.getString(cursor.getColumnIndex(ONLYDATE)));
                model.setOnlymonth(cursor.getString(cursor.getColumnIndex(ONLTMONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(YEAR)));
                model.setUsernumber(cursor.getString(cursor.getColumnIndex(USERNUMBER)));
                model.setAddress(cursor.getString(cursor.getColumnIndex(LOCATION)));


                models.add(model);


            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }

    public double recenttotalsum(String date) {
        double grandprice = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("Select SUM (rupee) From Record Where usernum='%s' AND date='%s'", RecordFragment.usernum, date);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {

            cursor.moveToFirst();
            grandprice = cursor.getDouble(0);

        }
        cursor.close();
        return grandprice;
    }

    public long insertLoanData(String personname, String givenrupee, String ltime, String lmonth, String lyear, String usernum, String duedate, String type, String date) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PERSON_NAME, personname);
        values.put(USERNUMBER, usernum);
        values.put(LOAN_GIVEN, givenrupee);
        values.put(LOAN_ONLYDATE, ltime);
        values.put(LOAN_MONTH, lmonth);
        values.put(LOAN_YEAR, lyear);
        values.put(LOAN_DUEDATE, duedate);
        values.put(GIVEN_TYPE, type);
        values.put(LOAN_DATE, date);


        long id = db.insert(LOAN_TABLE, null, values);
        db.close();
        return id;
    }

    public List<Loan_model> getallloanrecords() {

        String query = String.format("SELECT  * FROM Loan Where usernum='%s' ", RecordFragment.usernum);


        List<Loan_model> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            do {

                Loan_model model = new Loan_model();

                model.setId(cursor.getString(cursor.getColumnIndex(LOAN_ID)));
                model.setPersonName(cursor.getString(cursor.getColumnIndex(PERSON_NAME)));
                model.setLoan(cursor.getString(cursor.getColumnIndex(LOAN_GIVEN)));
                model.setDate(cursor.getString(cursor.getColumnIndex(LOAN_DATE)));
                model.setMonth(cursor.getString(cursor.getColumnIndex(LOAN_MONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(LOAN_YEAR)));
                model.setUsernumber(cursor.getString(cursor.getColumnIndex(L_USERNUMBER)));
                model.setReturn_date(cursor.getString(cursor.getColumnIndex(LOAN_DUEDATE)));
                model.setLoan_type(cursor.getString(cursor.getColumnIndex(GIVEN_TYPE)));
                model.setOnlydate(cursor.getString(cursor.getColumnIndex(LOAN_ONLYDATE)));


                models.add(model);


            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }

    public Loan_model getallloan(long id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM Loan Where usernum='%s' ", phoneNumber);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();

        Loan_model model = new Loan_model();

        cursor.getString(cursor.getColumnIndex(LOAN_ID));
        cursor.getString(cursor.getColumnIndex(PERSON_NAME));
        cursor.getString(cursor.getColumnIndex(LOAN_GIVEN));
        cursor.getString(cursor.getColumnIndex(LOAN_DATE));
        cursor.getString(cursor.getColumnIndex(USERNUMBER));
        cursor.getString(cursor.getColumnIndex(LOAN_DUEDATE));
        cursor.getString(cursor.getColumnIndex(GIVEN_TYPE));
        cursor.getString(cursor.getColumnIndex(LOAN_ONLYDATE));


        cursor.close();
        return model;

    }

    public void removeLoan(String loanid) {

        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete()

        String Query = String.format("DELETE FROM Loan WHERE loan_id='%s'", loanid);
        db.execSQL(Query);

    }

    public long insertIncomeData(String source, String cash, String time, String month, String year, String odate, String omonth, String usernum) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(INCOME_NAME, source);
        values.put(INCOME_USERNUMBER, usernum);

        values.put(INCOME_PRICE, cash);
        values.put(INCOME_DATE, time);
        values.put(INCOME_ONLYDATE, odate);
        values.put(INCOME_ONLTMONTH, omonth);

        values.put(INCOME_MONTH, month);
        values.put(INCOME_YEAR, year);

        //insert row
        long id = db.insert(TABLE_INCOME, null, values);
        db.close();
        return id;
    }

    public List<Income_model> getallincome() {

        String query = String.format("SELECT  * FROM Income Where in_usernum='%s'", IncomeFragment.usernum);


        List<Income_model> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            do {

                Income_model model = new Income_model();

                model.setId(cursor.getString(cursor.getColumnIndex(INCOME_ID)));
                model.setSource(cursor.getString(cursor.getColumnIndex(INCOME_NAME)));
                model.setRupee(cursor.getString(cursor.getColumnIndex(INCOME_PRICE)));
                model.setDate(cursor.getString(cursor.getColumnIndex(INCOME_DATE)));
                model.setOnlydate(cursor.getString(cursor.getColumnIndex(INCOME_ONLYDATE)));
                model.setOnlymonth(cursor.getString(cursor.getColumnIndex(INCOME_ONLTMONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(INCOME_YEAR)));
                model.setUsernumber(cursor.getString(cursor.getColumnIndex(INCOME_USERNUMBER)));


                models.add(model);


            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }


    public double incometotalsum() {
        double grandprice = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("Select SUM (income_rupee) From Income Where in_usernum='%s'", IncomeFragment.usernum);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {

            cursor.moveToFirst();
            grandprice = cursor.getDouble(0);

        }
        cursor.close();
        return grandprice;


    }
    public void removeIncome(String incomeid) {

        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete()

        String Query = String.format("DELETE FROM Income WHERE income_id='%s'", incomeid);
        db.execSQL(Query);

    }

    //bymonth
    public ArrayList<Income_model> byIncomeMonth(String month) {

        String query = String.format("Select * From Income Where month='%s' AND in_usernum='%s'", month, IncomeFragment.usernum);


        ArrayList<Income_model> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            do {

                Income_model model = new Income_model();

                model.setId(cursor.getString(cursor.getColumnIndex(INCOME_ID)));
                model.setSource(cursor.getString(cursor.getColumnIndex(INCOME_NAME)));
                model.setRupee(cursor.getString(cursor.getColumnIndex(INCOME_PRICE)));
                model.setMonth(cursor.getString(cursor.getColumnIndex(INCOME_MONTH)));
                model.setOnlydate(cursor.getString(cursor.getColumnIndex(INCOME_ONLYDATE)));
                model.setOnlymonth(cursor.getString(cursor.getColumnIndex(INCOME_ONLTMONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(INCOME_YEAR)));
                model.setUsernumber(cursor.getString(cursor.getColumnIndex(INCOME_USERNUMBER)));

                models.add(model);


            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }

    public double totalsumofIncomeMonthh(String month) {
        double grandprice = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("Select SUM (income_rupee) From Income Where month='%s' AND in_usernum='%s'", month, IncomeFragment.usernum);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {

            cursor.moveToFirst();
            grandprice = cursor.getDouble(0);

        }
        cursor.close();
        return grandprice;
    }


    public List<Loan_model> getreturnDate(String today_date){

        String query = String.format("SELECT * FROM Loan WHERE usernum='%s' and due_date='%s'", MainActivity.usernum, today_date);

        List<Loan_model> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Log.e("Cursor_count", cursor.getCount()+"");
        Log.e("todaysdate","date"+today_date);

        if (cursor.moveToFirst())
            do {

                Loan_model model = new Loan_model();
                model.setId(cursor.getString(cursor.getColumnIndex(LOAN_ID)));
                model.setPersonName(cursor.getString(cursor.getColumnIndex(PERSON_NAME)));
                model.setDate(cursor.getString(cursor.getColumnIndex(LOAN_DATE)));
                model.setMonth(cursor.getString(cursor.getColumnIndex(LOAN_MONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(LOAN_YEAR)));
                model.setUsernumber(cursor.getString(cursor.getColumnIndex(L_USERNUMBER)));
                model.setLoan(String.valueOf(cursor.getInt(cursor.getColumnIndex(LOAN_GIVEN))));
                model.setReturn_date(cursor.getString(cursor.getColumnIndex(LOAN_DUEDATE)));
                model.setLoan_type(cursor.getString(cursor.getColumnIndex(GIVEN_TYPE)));
                model.setOnlydate(cursor.getString(cursor.getColumnIndex(LOAN_ONLYDATE)));

                models.add(model);

            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }
    public List<Loan_model> getReturnLoanrecords(String date) {

        String query = String.format("SELECT * FROM Loan WHERE usernum='%s' AND date='%s'", MainActivity.usernum, date);


        List<Loan_model> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Log.e("insertedDate",date);
        if (cursor.moveToFirst())
            do {

                Loan_model model = new Loan_model();
                model.setId(cursor.getString(cursor.getColumnIndex(LOAN_ID)));
                model.setPersonName(cursor.getString(cursor.getColumnIndex(PERSON_NAME)));
                model.setDate(cursor.getString(cursor.getColumnIndex(LOAN_DATE)));
                model.setMonth(cursor.getString(cursor.getColumnIndex(LOAN_MONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(LOAN_YEAR)));
                model.setUsernumber(cursor.getString(cursor.getColumnIndex(L_USERNUMBER)));
                model.setLoan(String.valueOf(cursor.getInt(cursor.getColumnIndex(LOAN_GIVEN))));
                model.setReturn_date(cursor.getString(cursor.getColumnIndex(LOAN_DUEDATE)));
                model.setLoan_type(cursor.getString(cursor.getColumnIndex(GIVEN_TYPE)));
                model.setOnlydate(cursor.getString(cursor.getColumnIndex(LOAN_ONLYDATE)));
                models.add(model);


            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }



    public void updateWallet(String newAmount){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("UPDATE User SET wallet='%s' WHERE phone='%s'",newAmount, phoneNumber);
        db.execSQL(query);


    }

    public User_model getUserWallet(String userNum){

        SQLiteDatabase db = this.getReadableDatabase();

        String query = String.format("Select wallet, user_limit from User where phone='%s'",userNum);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null)
            cursor.moveToFirst();
        // prepare note object
        User_model user = new User_model(
                cursor.getString(cursor.getColumnIndex(USERINFO_WALLET)),
              cursor.getString(cursor.getColumnIndex(USERINFO_EXPENSELIMIT)));
        // close the db connection
        cursor.close();
        return user;
    }
    public void updateLimit(String newAmount){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("UPDATE User SET user_limit='%s' WHERE phone='%s'",newAmount, phoneNumber);
        db.execSQL(query);


    }

}

