package gemfx.gemfx_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.TimeUnit;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v7.widget.MenuPopupWindow;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {

    private Button gem_bt;
    private Button del_bt;
    private Button dis_bt;
    private Button cho_bt;
    private SeekBar myseek;

    public String PREFS = "MyData";

    //Fila de requests.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String host = "http://192.168.0.102:5000/gemfx/"; //mudar o ip aqui!

        final SharedPreferences sharedpreferences = getSharedPreferences(PREFS, 0);
        final int dl = sharedpreferences.getInt("delay_level", 0)/10;
        final int dt =sharedpreferences.getInt("delay_time", 0)/10;
        final int cd =sharedpreferences.getInt("chorus_depth", 0)/10;
        final int cr =sharedpreferences.getInt("chorus_rate", 0)/10;
        final int dil =sharedpreferences.getInt("distortion_level", 0)/10;
        final int l =sharedpreferences.getInt("low", 0)/10;
        final int m =sharedpreferences.getInt("mid", 0)/10;
        final int h =sharedpreferences.getInt("high", 0)/10;
        final int w =sharedpreferences.getInt("wah", 0)/10;


        Toolbar mytoolbar = (Toolbar)findViewById(R.id.mytoolbar);
        setSupportActionBar(mytoolbar);

        getSupportActionBar().setTitle("  GemFX");
        getSupportActionBar().setIcon(R.drawable.ic_action_name);

        gem_bt = ((Button)findViewById(R.id.but_Gem));
        gem_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int l =sharedpreferences.getInt("low", 0)/10;
                int m =sharedpreferences.getInt("mid", 0)/10;
                int h =sharedpreferences.getInt("high", 0)/10;
                int w =sharedpreferences.getInt("wah", 0)/10;

                if (gem_bt.getText().equals("GemEffect ON")) {
                    gem_bt.setTextColor(Color.GREEN);

                    //envia settings
                    String url_low = host + "settings/gemeffect/low/" + Integer.toString(l);
                    String url_mid = host + "settings/gemeffect/mid/" + Integer.toString(m);
                    String url_high = host + "settings/gemeffect/high/" + Integer.toString(h);
                    String url_wah = host + "settings/gemeffect/wahwah/" + Integer.toString(w);
                    sendRequest(url_low, queue);
                    sendRequest(url_mid, queue);
                    sendRequest(url_high, queue);
                    sendRequest(url_wah, queue);

                    //espera 1s
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {

                    }

                    //liga o efeito
                    String url = host + "effects/gemeffect/on";
                    sendRequest(url, queue);
                }
                    else {
                    gem_bt.setTextColor(Color.WHITE);

                    //desliga o efeito
                    String url = host + "effects/gemeffect/off";
                    sendRequest(url, queue);
                }

            }
        });

        dis_bt = ((Button)findViewById(R.id.but_Distortion));
        dis_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int dil = sharedpreferences.getInt("distortion_level", 0) / 10;

                if (dis_bt.getText().equals("Distortion ON")) {
                    dis_bt.setTextColor(Color.GREEN);

                    //envia o novo level
                    String url_level = host + "settings/distortion/level/" + Integer.toString(dil);
                    sendRequest(url_level, queue);

                    //espera 1s
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {

                    }

                    //liga o efeito
                    String url = host + "effects/distortion/on";
                    sendRequest(url, queue);
                }

                else {
                    dis_bt.setTextColor(Color.WHITE);

                    //desliga o efeito
                    String url = host + "effects/distortion/off";
                    sendRequest(url, queue);
                }
            }
        });

        del_bt = ((Button)findViewById(R.id.but_Delay));
        del_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int dl = sharedpreferences.getInt("delay_level", 0)/10;
                int dt = sharedpreferences.getInt("delay_time", 0)/10;

                Integer.toString(dl);

                if (del_bt.getText().equals("Delay ON")) {
                    del_bt.setTextColor(Color.GREEN);

                    //envia os settings
                    String url_level = host + "settings/delay/level/" + Integer.toString(dl);
                    String url_time = host + "settings/delay/duration/" + Integer.toString(dt);
                    sendRequest(url_level, queue);
                    sendRequest(url_time, queue);

                    //espera 1s
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {

                    }

                    //liga o efeito
                    String url = host + "effects/delay/on";
                    sendRequest(url, queue);
                }
                else {
                    del_bt.setTextColor(Color.WHITE);

                    //desliga o efeito
                    String url = host + "effects/delay/off";
                    sendRequest(url, queue);
                }
            }
        });

        cho_bt = ((Button)findViewById(R.id.but_Chorus));
        cho_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 int cd =sharedpreferences.getInt("chorus_depth", 0)/10;
                 int cr =sharedpreferences.getInt("chorus_rate", 0)/10;

                if (cho_bt.getText().equals("Chorus ON")) {
                    cho_bt.setTextColor(Color.GREEN);

                    //envia os settings
                    String url_depth = host + "settings/chorus/depth/" + Integer.toString(cd);
                    String url_rate = host + "settings/chorus/rate/" + Integer.toString(cr);
                    sendRequest(url_depth, queue);
                    sendRequest(url_rate, queue);

                    //liga o efeito
                    String url = host + "effects/chorus/on";
                    sendRequest(url, queue);
                }

                else {
                    cho_bt.setTextColor(Color.WHITE);

                    //desliga o efeito
                    String url = host + "effects/chorus/off";
                    sendRequest(url, queue);
                }
            }
        });

        myseek = (SeekBar)findViewById(R.id.seekBar);
        final TextView value = (TextView)findViewById(R.id.text_value);
        myseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

               int myprogress = progress / 10;
                value.setText("" + myprogress);

                //envia a amplitude
                String url = host + "amplitude/" + Integer.toString(myprogress);

                //não foi usado o método send request aqui pq sao muitos ao mesmo tempo, buga o toast.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //exibe a resposta no toast
                                //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //caso falhe
                        //Toast.makeText(getApplicationContext(), "Falha de Comunicação! :(", Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });
    }


   @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.menu_1)
        {
                Intent i = new Intent();
                i.setClass(this, SettingsActivity.class);
                startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendRequest(String url, RequestQueue queue)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //exibe a resposta no toast
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //caso falhe
                //Toast.makeText(getApplicationContext(), "Falha de Comunicação! :(", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }


}
