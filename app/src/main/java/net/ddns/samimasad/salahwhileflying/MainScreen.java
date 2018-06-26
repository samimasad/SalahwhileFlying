package net.ddns.samimasad.salahwhileflying;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;



public class MainScreen extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    RecyclerView.Adapter mAdapter;
    public void ClickCalc(View v)
    {

        //just calculate based on the values of the input the Salah time
        //check teh validiy of the inputs
        EditText lang = (EditText) findViewById(R.id.editText_Lang) ;
        EditText lat = (EditText) findViewById(R.id.editText_Lat) ;
        String lang_str = lang.getText().toString() ;
        String lat_str = lat.getText().toString() ;
        PrayTime pray  = new PrayTime();
        // Example of a call to a native method




        int offset[] ={60,60,60,60,60,60,60};
        Date now;




        if (!lang_str.isEmpty() && !lat_str.isEmpty() ) {
            double lang_value = Double.parseDouble(lang_str);
            double lat_value = Double.parseDouble(lat_str);
            pray.setCalcMethod(pray.MWL); //MWL method to calculate the praying itme
            pray.setAdjustHighLats(pray.AngleBased);
            //todo make those settings dynamic with location

            pray.setLat(lat_value);
            pray.setLng(lang_value);

            now = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);




            TimeZone timez = TimeZone.getDefault();

            double hoursDiff = (timez.getRawOffset() / 1000.0) / 3600;

            if (timez.inDaylightTime(now)) {
               pray.tune(offset);
            }

            ArrayList<String> salah_times = pray.getPrayerTimes(cal,lat_value,lang_value,hoursDiff);
            ArrayList<String> salah_names = pray.getTimeNames();
            String tempstring = salah_times.toString();
            tempstring = tempstring.replaceAll("[\\[\\],]","" ) ;
            //String timesofSalah[] = salah_times.toString().split(" ");
            String timesofSalah[] = tempstring.split(" ");


            tempstring = salah_names.toString();
            tempstring = tempstring.replaceAll("[\\[\\],5050]","" ) ;
            String namesofSalah[] = tempstring.split(" ") ;


            // specify an adapter (see also next example)
            RecyclerView list = (RecyclerView) findViewById(R.id.Salahlist);
            // use a linear layout manager
            RecyclerView.LayoutManager mLayoutManager;

            mLayoutManager = new LinearLayoutManager(this);
            list.setLayoutManager(mLayoutManager);

            mAdapter = new SalahlistAdapter(timesofSalah,namesofSalah);
            list.setAdapter(mAdapter);
            //tv.setText(Text[0]+Text[1]+Text[2]);




        }
        else
        {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Wrong Entry")
                    .setMessage("Please edit a valid location")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });




        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
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

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
