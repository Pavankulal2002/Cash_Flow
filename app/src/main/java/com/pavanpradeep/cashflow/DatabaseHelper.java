package com.pavanpradeep.cashflow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{
    //log
    private static String TAG = "DatabaseHelper";

    //Database name
    private static final String DATABASE_NAME = "Umbutho.db";

    //Table names
    private static final String TABLE_NAME_EXPENSE = "Expense";
    private static final String TABLE_NAME_INCOME = "Income";
    private static final String TABLE_NAME_CATEGORY = "Category";


    //Table EXPENSE
    private static final String COL_1_E = "ID";
    private static final String COL_2_E = "EXPENSE"; //int
    private static final String COL_3_E = "DESCRIPTION"; //string
    private static final String COL_4_E = "DATE"; //string
    private static final String COL_5_E = "CATEGORY"; //string

    //Table INCOME
    private static final String COL_1_I = "ID";
    private static final String COL_2_I = "INCOME"; //int
    private static final String COL_3_I = "MONTH"; //string

    //Table CATEGORY
    private static final String COL_1_C = "ID";
    private static final String COL_2_C = "DESCRIPTION"; //string
    private static final String COL_3_C = "BUDGET"; //int
    private static final String COL_4_C = "STATE"; // string   BOOLEAN (TRUE/FALSE).

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * Create 4 tables when first time open the apps.
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //creating required tables
        db.execSQL("create table " + TABLE_NAME_EXPENSE +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, EXPENSE INTEGER, DESCRIPTION TEXT, DATE TEXT, CATEGORY TEXT)");
        db.execSQL("create table " + TABLE_NAME_INCOME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, INCOME INTEGER, MONTH TEXT)");
        db.execSQL("create table " + TABLE_NAME_CATEGORY +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, DESCRIPTION TEXT, BUDGET INTEGER, STATE TEXT)");
        Log.d(TAG, "DONE CREATE 4 TABLE !");
    }

    /**
     * Upgrade tables.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_INCOME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_CATEGORY);
        //create new tables
        onCreate(db);
    }

    /**
     * Add row on table Expense.
     */
    public boolean insertDataExpense(String expense, String description, String date, String category)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_E, expense);
        contentValues.put(COL_3_E, description);
        contentValues.put(COL_4_E, date);
        contentValues.put(COL_5_E, category);
        long res = db.insert(TABLE_NAME_EXPENSE, null, contentValues);
        if(res == -1)
            return false;
        else
            return true;
    }

    /**
     * Add row on table Income.
     */
    public boolean insertDataIncome(String income, String month)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_I, income);
        contentValues.put(COL_3_I, month);
        long res = db.insert(TABLE_NAME_INCOME, null, contentValues);
        if(res == -1)
            return false;
        else
            return true;
    }

    /**
     * Add row on table Category.
     */
    public boolean insertDataCategory(String description, String budget, String state)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_C, description);
        contentValues.put(COL_3_C, budget);
        contentValues.put(COL_4_C, state);
        long res = db.insert(TABLE_NAME_CATEGORY, null, contentValues);
        if(res == -1)
            return false;
        else
            return true;
    }



    /**
     * Update row on table Category.
     */
    public boolean updateDataCategory(String id, String description, String budget, String state)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_C, description);
        contentValues.put(COL_3_C, budget);
        contentValues.put(COL_4_C, state);

        //updating rows
        db.update(TABLE_NAME_CATEGORY, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    /**
     * Update row on table Income.
     */
    public boolean updateMonthlyIncome(String id, String income, String month)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_I, income);
        contentValues.put(COL_3_I, month);

        //updating rows
        db.update(TABLE_NAME_INCOME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    /**
     * Query select ID, INCOME from Income where MONTH = "query(month)";
     */
    public Cursor getMonthlyIncome(String month)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select "+ COL_1_I +", " +COL_2_I+ " from "+ TABLE_NAME_INCOME +" where " + COL_3_I + " = " + " '" +month+ "'";
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    /**
     * Query select * from Expense where CATEGORY = query(cat) order by DATE;
     */
    public Cursor getCategoryDataFromExpense(String cat)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " +TABLE_NAME_EXPENSE+ " where " +COL_5_E+ " = "+" '"+cat+"'"+" order by "+COL_4_E+" desc";
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    /**
     * Query select DESCRIPTION, STATE, BUDGET from Category where DESCRIPTION = "query(cat)";
     */
    public Cursor getStateForCategory(String cat)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select "+ COL_2_C + ", " + COL_4_C + ", " + COL_3_C  +" from "+ TABLE_NAME_CATEGORY +" where " + COL_2_C + " = " + " '" +cat+ "'";
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    /**
     * Query select sum(EXPENSE) from Expense where DATE like 'query(date)' and CATEGORY = "query(cat)";
     */
    public Cursor calculatingTotalExpense(String date, String cat)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select sum(" +COL_2_E+ ") from " +TABLE_NAME_EXPENSE+ " where " +COL_4_E+ " like '" +date+"%' and " +COL_5_E+ " = " + "'" +cat+ "'";
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    /**
     * Query sum(EXPENSE) from Expense where DATE like 'query(date)';
     */
    public Cursor calculatingTotalExpenseForAllCategory(String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select sum(" +COL_2_E+ ") from " +TABLE_NAME_EXPENSE+ " where " +COL_4_E+ " like '" +date+"%'";
        Cursor res = db.rawQuery(query,null);
        return res;
    }

}
