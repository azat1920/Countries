package randomapps.countries;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View.*;
import java.util.*;

public class ClassicCountries extends Activity{

    HashMap<Integer, Country> list = new HashMap<Integer, Country>();
    ArrayList<Integer> num = new ArrayList<Integer>();
    int maxNum = 10;
    int rightSum, wrongSum, count;
    Button btn1, btn2, btn3, btn4, btn6;
    TextView tv1, tv2, tv3, tv4;
    ProgressBar prb;
    SharedPreferences sp;

    MyDatabase db;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classic_countries);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        Initialize();
        StartGame();

    }

    protected void onDestroy() {
        if (cursor != null) cursor.close();
        if (db != null) db.close();
        super.onDestroy();

    }

    private HashMap<Integer, Country> MakeNewList() {

        int count = 192;
        int k = 0;
        int[] arr = GetContinents();

        //Записываем рандомные id стран в ArrayList, проверяя на повторяемость
        //и вхождение в список континентов
        while (k < maxNum) {
            boolean b;
            int a;

            do {
                b = false;
                a = (int) (Math.random() * count + 1);
                boolean t = db.isChecked(a, arr);
                if (t) {
                    for (Integer item : num) {

                        if (item == a) b = true;
                    }
                }else b = true;
            } while (b);
            k++;
            num.add(a);
        }

        HashMap<Integer, Country> list = new HashMap<Integer, Country>();

        for (int i = 0; i < maxNum; i++) {
            list.put(num.get(i), new Country(db, (int) num.get(i)));
        }


        return list;
    }



    private void Start(int i)  {
        Country country;
        country = list.get(num.get(i));

        DefaultShow();
        SetOnclick();
        tv1.setText(country.getCapital());
        btn1.setText(country.getCapital_1());
        btn2.setText(country.getCapital_2());
        btn3.setText(country.getCapital_3());
        btn4.setText(country.getCapital_4());
        tv2.setText(String.format("%d", rightSum));
        tv3.setText(String.format("%d", wrongSum));


    }

    private void DefaultShow(){
        btn1.setBackgroundColor(getResources().getColor(R.color.btn_bg));
        btn2.setBackgroundColor(getResources().getColor(R.color.btn_bg));
        btn3.setBackgroundColor(getResources().getColor(R.color.btn_bg));
        btn4.setBackgroundColor(getResources().getColor(R.color.btn_bg));
    }

    private void RemoveOnClick(){
        btn1.setOnClickListener(null);
        btn2.setOnClickListener(null);
        btn3.setOnClickListener(null);
        btn4.setOnClickListener(null);
    }

    private void SetOnclick(){

        OnClickListener oclbtn = new OnClickListener(){
            @Override
            public void onClick(View v) {

                btn6 = (Button) v;
                new ShowDialog().execute();

            }
        };

        btn1.setOnClickListener(oclbtn);
        btn2.setOnClickListener(oclbtn);
        btn3.setOnClickListener(oclbtn);
        btn4.setOnClickListener(oclbtn);
    }

    private void StartGame(){
        list = MakeNewList();
        Start(count);
    }

    private void Initialize(){

        rightSum = 0;
        wrongSum = 0;
        count = 0;
        maxNum = sp.getInt("seekBarPreference", 10);
        int k = GetMaxCount(GetContinents());
        if (maxNum > k) maxNum = k;

        btn1 = (Button) findViewById(R.id.cc_btn1);
        btn2 = (Button) findViewById(R.id.cc_btn2);
        btn3 = (Button) findViewById(R.id.cc_btn3);
        btn4 = (Button) findViewById(R.id.cc_btn4);
        tv1 = (TextView) findViewById(R.id.cc_tv1);
        tv2 = (TextView) findViewById(R.id.cc_tv2);
        tv3 = (TextView) findViewById(R.id.cc_tv3);
        tv4 = (TextView) findViewById(R.id.cc_tv4);
        prb = (ProgressBar) findViewById(R.id.cc_prb);
        prb.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.btn_bg), android.graphics.PorterDuff.Mode.SRC_IN);
        tv4.setText(String.format("%d", maxNum));
        prb.setProgress(0);
        prb.setMax(maxNum);
        db = new MyDatabase(this);

    }

    private int[] GetContinents(){
        Set<String> set = sp.getStringSet("continents", null);
        StringBuilder s =  new StringBuilder("");

        String[] strArr;


        if (set != null) {
            for (String str : set) s.append(str).append(" ");
        }

        String t = s.toString();
        strArr = t.split(" ");

        int[] arr = new int[strArr.length];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(strArr[i]);
        }

        return arr;
    }

    private int GetMaxCount(int[] arr){
        int sum = 0;
        for (int i : arr){
            switch (i){
                case 1: sum+=44;
                    break;
                case 2: sum+=47;
                    break;
                case 3: sum+=23;
                    break;
                case 4: sum+=12;
                    break;
                case 5: sum+=52;
                    break;
                case 6: sum+=14;
                    break;
                default:
                    break;
            }
        }


        return sum;
    }

    private String GetContinents(int[] arr){
        String s = "";

        for (int i = 0; i < arr.length; i++) {
            if (i < arr.length -1) s=("Region = " + arr[i] + " and ");
            else s = ("Region = " + arr[i]);
        }

        return s;
    }


    class ShowDialog extends AsyncTask<Void, Boolean, Void> {

        View v;

        @Override
        protected Void doInBackground(Void... params) {

            v = btn6;

            if (count < maxNum ) {
                String s = ((Button) v).getText().toString().toLowerCase();
                if (s.equals(list.get(num.get(count)).getCountry().toLowerCase())){
                    rightSum++;
                    publishProgress(true);
                    SystemClock.sleep(500);

                }else{
                    wrongSum++;
                    publishProgress(false);
                    SystemClock.sleep(700);

                }
                if (count < maxNum) count++;

            }

            return null;
        }


        protected void onProgressUpdate(Boolean... values){
            super.onProgressUpdate();
            Boolean b = values[0];

            prb.setProgress((count+1));
            if (b){
                btn6.setBackgroundColor(getResources().getColor(R.color.my_green));
                RemoveOnClick();
            }
            else{
                RemoveOnClick();
                String s1 = (String) btn1.getText();
                String s2 = (String) btn2.getText();
                String s3 = (String) btn3.getText();
                String s4 = (String) btn4.getText();

                if (s1.equals(list.get(num.get(count)).getCapital()))
                    btn1.setBackgroundColor(getResources().getColor(R.color.my_green));
                if (s2.equals(list.get(num.get(count)).getCapital()))
                    btn2.setBackgroundColor(getResources().getColor(R.color.my_green));
                if (s3.equals(list.get(num.get(count)).getCapital()))
                    btn3.setBackgroundColor(getResources().getColor(R.color.my_green));
                if (s4.equals(list.get(num.get(count)).getCapital()))
                    btn4.setBackgroundColor(getResources().getColor(R.color.my_green));

                btn6.setBackgroundColor(getResources().getColor(R.color.my_red));
            }


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (count < maxNum) Start(count);
            if (count == maxNum) {
                tv1.setVisibility(View.GONE);
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);
                btn3.setVisibility(View.GONE);
                btn4.setVisibility(View.GONE);
                tv2.setText(String.format("%d", rightSum));
                tv3.setText(String.format("%d", wrongSum));
                Button btn5 = (Button) findViewById(R.id.cc_btn5);
                btn5.setVisibility(View.VISIBLE);


                OnClickListener oclbtn_recreate  = new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //API level >=10
                        prb.setProgress(0);
                        recreate();
                    }
                };


                btn5.setOnClickListener(oclbtn_recreate);
            }
        }
    }





}





