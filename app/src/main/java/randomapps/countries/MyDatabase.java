package randomapps.countries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import java.util.ArrayList;


public class MyDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;

    private static final String Country = "Country";
    private static final String Capital = "Capital";
    private static final String Id = "_id";
    private static final String SqlTables = "Countries";
    private static final String Region = "Region";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public String getCountry(int i)
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {Country};

        qb.setTables(SqlTables);
        Cursor c = qb.query(db, sqlSelect, Id + " = " + i, null, null, null, null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex(Country));
    }

    public String getCapital(int i)
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {Capital};
        qb.setTables(SqlTables);

        Cursor c = qb.query(db, sqlSelect, Id + " = " + i, null, null, null, null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex(Capital));
    }

    public String[] ReadRowDB(int i) {

        String[] res = new String[2];

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {Country, Capital};

        qb.setTables(SqlTables);

        Cursor c = qb.query(db, sqlSelect, Id + " = " + i, null, null, null, null);
        c.moveToFirst();

        res[0] = c.getString(c.getColumnIndex(Country));
        res[1] = c.getString(c.getColumnIndex(Capital));

        return res;


    }

    public String ReadRowCapitalDB(int i) {
        String res = "";

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {Capital};

        qb.setTables(SqlTables);
        Cursor c = qb.query(db, sqlSelect, Id + " = " + i, null, null, null, null);
        c.moveToFirst();

        res = c.getString(c.getColumnIndex(Capital));
        c.close();

        return res;
    }

    public String ReadRowCountryDB(int i) {
        String res = "";

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {Country};

        qb.setTables(SqlTables);
        Cursor c = qb.query(db, sqlSelect, Id + " = " + i, null, null, null, null);
        c.moveToFirst();

        res = c.getString(c.getColumnIndex(Country));
        c.close();

        return res;
    }



    public String[] makeCountry(String Capital, int i) {
        String[] Capitals = new String[4];

        ArrayList<Integer> num = new ArrayList<Integer>();
        int count = 192;
        int k = 0;
        int maxNum = 3;
        num.add(i);

        //Записываем рандомные id стран в ArrayList, проверяя на повторяемость
        while (k < maxNum) {
            boolean b;
            int a;
            do {
                b = false;
                a = (int) (Math.random() * count + 1);
                for (Integer item : num) {
                    if (item.equals(a)) b = true;
                }
            } while (b);
            k++;
            num.add(a);
        }

        Capitals[0] = Capital;

        for (int j = 1; j < 4; j++) {
            Capitals[j] = this.ReadRowCapitalDB(num.get(j));
        }


        for (int u = 0; u < 2; u++) {
            for (int t = 3; t > 0; t--) {
                int j = (int) (Math.random()*(t+1));
                String str = Capitals[j];
                Capitals[j] = Capitals[t];
                Capitals[t] = str;
            }
        }

        return Capitals;

    }


    //----------------------

    public String[] makeCapital(String Capital, int i) {
        String[] Countries = new String[4];

        ArrayList<Integer> num = new ArrayList<Integer>();
        int count = 192;
        int k = 0;
        int maxNum = 3;
        num.add(i);

        //Записываем рандомные id стран в ArrayList, проверяя на повторяемость
        while (k < maxNum) {
            boolean b;
            int a;
            do {
                b = false;
                a = (int) (Math.random() * count + 1);
                for (Integer item : num) {
                    if (item.equals(a)) b = true;
                }
            } while (b);
            k++;
            num.add(a);
        }

        Countries[0] = Capital;

        for (int j = 1; j < 4; j++) {
            Countries[j] = this.ReadRowCountryDB(num.get(j));
        }


        for (int u = 0; u < 2; u++) {
            for (int t = 3; t > 0; t--) {
                int j = (int) (Math.random()*(t+1));
                String str = Countries[j];
                Countries[j] = Countries[t];
                Countries[t] = str;
            }
        }

        return Countries;

    }

    //----------------------




    public boolean isChecked(int i, int[] arr){
        boolean b = false;

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {Region};

        qb.setTables(SqlTables);
        Cursor c = qb.query(db, sqlSelect, Id + " = " + i, null, null, null, null);
        c.moveToFirst();

        int t = c.getInt(c.getColumnIndex(Region));
        for (int k : arr){
            if (k == t) {
                b = true;
            }
    }

        c.close();

        return b;
    }
}