package randomapps.countries;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class ManuallyMode extends Activity {

    SharedPreferences sp;
    TextView tv1, tv2, tv3, tv4;

    EditText et;
    Button btn1, btn2;
    int maxNum, count = 0, RightSum = 0, WrongSum = 0;
    ProgressBar prb;
    MyDatabase db;
    ArrayList<Integer> num = new ArrayList<Integer>();
    HashMap<Integer, Country> list = new HashMap<Integer, Country>();
    private static boolean checked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manuallymode_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        Initialize();
        list = MakeNewList();
        Start(count);
    }


    void Initialize() {
        RightSum = 0;
        WrongSum = 0;
        count = 0;
        maxNum = sp.getInt("seekBarPreference", 10);
        int k = GetMaxCount(GetContinents());
        if (maxNum > k) maxNum = k;


        tv1 = (TextView) findViewById(R.id.countryName);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView4);
        tv4 = (TextView) findViewById(R.id.textView6);

        et = (EditText) findViewById(R.id.editText);
        et.setText("");
        et.setFocusable(true);
        et.setClickable(true);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);

        prb = (ProgressBar) findViewById(R.id.progressBar);
        prb.setProgress(0);
        prb.setMax(maxNum);
        prb.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.btn_bg), android.graphics.PorterDuff.Mode.SRC_IN);

        tv4.setText(String.format("%d", maxNum));

        db = new MyDatabase(this);


    }

    private void SetOnclick() {

        View.OnClickListener oclbtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(et.getText().toString().equals(""))) {
                    new ShowDialog().execute();
                    checked = true;
                }

            }
        };

        View.OnClickListener oclbtn2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
                new ShowDialog().execute();
                checked = false;
            }
        };

        btn2.setOnClickListener(oclbtn);
        btn1.setOnClickListener(oclbtn2);
    }

    private void RemoveOnclick(){
        btn1.setOnClickListener(null);
        btn2.setOnClickListener(null);
    }

    private int[] GetContinents() {
        Set<String> set = sp.getStringSet("continents", null);
        StringBuilder s = new StringBuilder("");

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
                } else b = true;
            } while (b);
            k++;
            num.add(a);
        }

        HashMap<Integer, Country> list = new HashMap<Integer, Country>();

        for (int i = 0; i < maxNum; i++) {
            list.put(num.get(i), new Country(db.ReadRowDB((int) num.get(i))[0], db.ReadRowDB((int) num.get(i))[1]));
        }


        return list;
    }

    private void Start(int i) {
        Country country;
        country = list.get(num.get(i));

        SetOnclick();
        btn2.setBackgroundColor(getResources().getColor(R.color.btn_bg));
        tv1.setText(country.getCountry());
        tv2.setText(String.format("%d", RightSum));
        tv3.setText(String.format("%d", WrongSum));
        et.setInputType(1);
        et.setText("");

    }

    private String getEditTextString() {

        return et.getText().toString();
    }

    private void setEditTextInputType(int i) {
        et.setInputType(i);
    }


    private int GetMaxCount(int[] arr) {
        int sum = 0;
        for (int i : arr) {
            switch (i) {
                case 1:
                    sum += 44;
                    break;
                case 2:
                    sum += 47;
                    break;
                case 3:
                    sum += 23;
                    break;
                case 4:
                    sum += 12;
                    break;
                case 5:
                    sum += 52;
                    break;
                case 6:
                    sum += 14;
                    break;
                default:
                    break;
            }
        }


        return sum;
    }


    class ShowDialog extends AsyncTask<Void, Boolean, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            if (count < maxNum) {
                String s = getEditTextString().toLowerCase().trim();
                //setEditTextInputType(0);
                RemoveOnclick();

                if (s.equals(list.get(num.get(count)).getCapital().toLowerCase().trim())) {
                    RightSum++;
                    publishProgress(true);
                    SystemClock.sleep(700);

                } else {
                    WrongSum++;
                    publishProgress(false);
                    SystemClock.sleep(1000);

                }
                if (count < maxNum) count++;

            }

            return null;
        }


        protected void onProgressUpdate(Boolean... values) {
            super.onProgressUpdate();
            Boolean b = values[0];

            prb.setProgress((count + 1));
            if (checked) {

                if (b) {
                    btn2.setBackgroundColor(getResources().getColor(R.color.my_green));
                    RemoveOnclick();
                } else {
                    RemoveOnclick();
                    String s1 = et.getText().toString().toLowerCase().trim();

                    if (s1.equals(list.get(num.get(count)).getCapital().toLowerCase().trim()))
                        btn2.setBackgroundColor(getResources().getColor(R.color.my_green));
                    et.setText(list.get(num.get(count)).getCapital());
                    btn2.setBackgroundColor(getResources().getColor(R.color.my_red));
                }

            } else {
                et.setText(list.get(num.get(count)).getCapital());
            }


        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);

            if (count < maxNum) Start(count);
            if (count == maxNum) {

                View.OnClickListener oclbtn_recreate = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //API level >=10
                        prb.setProgress(0);
                        et.setText("");
                        recreate();
                    }
                };

                View.OnClickListener oclbtn_exit = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setEditTextInputType(0);
                        finish();
                    }
                };
                setEditTextInputType(0);
                et.setClickable(false);
                et.setFocusable(false);
                tv2.setText(String.format("%d", RightSum));
                tv3.setText(String.format("%d", WrongSum));
                btn1.setText(R.string.exit);
                btn2.setText(R.string.again);
                btn2.setBackgroundColor(getResources().getColor(R.color.btn_bg));
                btn1.setBackgroundColor(getResources().getColor(R.color.btn_bg));
                btn1.setOnClickListener(oclbtn_exit);
                btn2.setOnClickListener(oclbtn_recreate);
            }
        }
    }


    @Override
    protected void onDestroy() {
                et.setInputType(0);
                if (db != null) db.close();
        super.onDestroy();

    }
}
