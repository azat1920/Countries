package randomapps.countries;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.*;
import android.widget.Button;
import android.widget.TextView;

import org.sqlite.ResourceFinder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends Activity  {

    Button btn1, btn2, btn3, btn4, btn5;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Init();

    }



    private void Init() {

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);

        OnClickListener oclBtn1 = new OnClickListener() {
            @Override
            public void onClick(View v) {

                    CheckContinents();
                    Intent intent = new Intent(MainActivity.this, ClassicMode.class);
                    startActivity(intent);


            }
        };

        OnClickListener oclBtn2 = new OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckContinents();
                Intent intent = new Intent(MainActivity.this, ManuallyMode.class);
                startActivity(intent);
            }
        };
        OnClickListener oclBtn3 = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Preferences.class);
                startActivity(intent);
            }
        };

        OnClickListener oclBtn4 = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClassicCountries.class);
                startActivity(intent);
            }
        };

        OnClickListener oclBtn5 = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManuallyCountries.class);
                startActivity(intent);
            }
        };

        btn1.setOnClickListener(oclBtn1);
        btn2.setOnClickListener(oclBtn2);
        btn3.setOnClickListener(oclBtn3);
        btn4.setOnClickListener(oclBtn4);
        btn5.setOnClickListener(oclBtn5);

        SetButtonTextPadding(btn1);

    }

    private boolean CheckContinents() {

        Set<String> s = pref.getStringSet("continents", null);
        if (s == null || s.size() == 0) {

            Set<String> continents = new TreeSet<>(Arrays.asList("1","2",
                    "3","4","5","6"));
            SharedPreferences.Editor editor = pref.edit();
            editor.putStringSet("continents", continents);
            editor.apply();


        }

        return true;
    }

    private void SetButtonTextPadding(Button btn){
        Display display = getWindowManager().getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        Log.d("Logs", "display height " + p.y);
        Log.d("Logs", "display wight " + p.x);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> set = sp.getStringSet("continents", null);
        SharedPreferences.Editor editor = sp.edit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
