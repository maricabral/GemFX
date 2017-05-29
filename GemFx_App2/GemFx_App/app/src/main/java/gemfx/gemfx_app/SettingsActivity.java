package gemfx.gemfx_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Mariana on 10/16/2016.
 */
public class SettingsActivity extends AppCompatActivity {

    public final String PREFS = "MyData";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        load();

        Toolbar mytoolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(mytoolbar);

        getSupportActionBar().setTitle("  Settings");
        getSupportActionBar().setIcon(R.drawable.ic_action_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView d_level = (TextView) findViewById(R.id.tv_delay_level);
        final TextView d_time = (TextView) findViewById(R.id.tv_delay_time);
        final TextView c_depth = (TextView) findViewById(R.id.tv_cho_depth);
        final TextView c_rate = (TextView) findViewById(R.id.tv_chorus_rate);
        final TextView di_level = (TextView) findViewById(R.id.tv_dis_level);
        final TextView low = (TextView) findViewById(R.id.tv_low);
        final TextView mid = (TextView) findViewById(R.id.tv_mid);
        final TextView high = (TextView) findViewById(R.id.tv_high);
        final TextView wah = (TextView) findViewById(R.id.tv_wah);

        SeekBar s_level = (SeekBar) findViewById(R.id.seek_delay_level);
        SeekBar s_time = (SeekBar) findViewById(R.id.seek_delay_time);
        SeekBar s_depth = (SeekBar) findViewById(R.id.seek_chorus_depth);
        SeekBar s_rate = (SeekBar) findViewById(R.id.seek_chorus_rate);
        SeekBar sdi_level = (SeekBar) findViewById(R.id.seek_dis_level);
        SeekBar slow = (SeekBar) findViewById(R.id.seek_low);
        SeekBar smid = (SeekBar) findViewById(R.id.seek_mid);
        SeekBar shigh = (SeekBar) findViewById(R.id.seek_high);
        SeekBar swah = (SeekBar) findViewById(R.id.seek_wah);

        s_level.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int myprogress = progress / 10;
                d_level.setText("Level = " + myprogress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        s_time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int myprogress = progress / 10;
                d_time.setText("Duration = " + myprogress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        s_depth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int myprogress = progress / 10;
                c_depth.setText("Depth = " + myprogress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        s_rate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int myprogress = progress / 10;
                c_rate.setText("Rate = " + myprogress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sdi_level.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int myprogress = progress / 10;
                di_level.setText("Level = " + myprogress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        slow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int myprogress = progress / 10;
                low.setText("Low = " + myprogress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        smid.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int myprogress = progress / 10;
                mid.setText("Mid = " + myprogress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        shigh.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int myprogress = progress / 10;
                high.setText("High = " + myprogress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        swah.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int myprogress = progress / 10;
                wah.setText("WahWah Level = " + myprogress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void load() {
        SharedPreferences sharedpreferences = getSharedPreferences(PREFS, 0);
        int dl = sharedpreferences.getInt("delay_level", 0);
        int dt =sharedpreferences.getInt("delay_time", 0);
        int cd =sharedpreferences.getInt("chorus_depth", 0);
        int cr =sharedpreferences.getInt("chorus_rate", 0);
        int dil =sharedpreferences.getInt("distortion_level", 0);
        int l =sharedpreferences.getInt("low", 0);
        int m =sharedpreferences.getInt("mid", 0);
        int h =sharedpreferences.getInt("high", 0);
        int w =sharedpreferences.getInt("wah", 0);

        SeekBar s_level = (SeekBar) findViewById(R.id.seek_delay_level);
        SeekBar s_time = (SeekBar) findViewById(R.id.seek_delay_time);
        SeekBar s_depth = (SeekBar) findViewById(R.id.seek_chorus_depth);
        SeekBar s_rate = (SeekBar) findViewById(R.id.seek_chorus_rate);
        SeekBar sdi_level = (SeekBar) findViewById(R.id.seek_dis_level);
        SeekBar slow = (SeekBar) findViewById(R.id.seek_low);
        SeekBar smid = (SeekBar) findViewById(R.id.seek_mid);
        SeekBar shigh = (SeekBar) findViewById(R.id.seek_high);
        SeekBar swah = (SeekBar) findViewById(R.id.seek_wah);

        final TextView d_level = (TextView) findViewById(R.id.tv_delay_level);
        final TextView d_time = (TextView) findViewById(R.id.tv_delay_time);
        final TextView c_depth = (TextView) findViewById(R.id.tv_cho_depth);
        final TextView c_rate = (TextView) findViewById(R.id.tv_chorus_rate);
        final TextView di_level = (TextView) findViewById(R.id.tv_dis_level);
        final TextView low = (TextView) findViewById(R.id.tv_low);
        final TextView mid = (TextView) findViewById(R.id.tv_mid);
        final TextView high = (TextView) findViewById(R.id.tv_high);
        final TextView wah = (TextView) findViewById(R.id.tv_wah);

        s_level.setProgress(dl);
        d_level.setText("Level = " + dl/10);

        s_time.setProgress(dt);
        d_time.setText("Duration = " + dt/10);

        s_depth.setProgress(cd);
        c_depth.setText("Depth = " + cd/10);

        s_rate.setProgress(cr);
        c_rate.setText("Rate = " + cr/10);

        sdi_level.setProgress(dil);
        di_level.setText("Level = " + dil/10);

        slow.setProgress(l);
        low.setText("Low = " + l/10);

        smid.setProgress(m);
        mid.setText("Mid = " + m/10);

        shigh.setProgress(h);
        high.setText("High = " + h/10);

        swah.setProgress(w);
        wah.setText("WahWah Level = " + w/10);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            savedata();

            Intent intent = NavUtils.getParentActivityIntent(this).putExtra("delay", 5);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void savedata()
    {
        SeekBar s_level = (SeekBar) findViewById(R.id.seek_delay_level);
        SeekBar s_time = (SeekBar) findViewById(R.id.seek_delay_time);
        SeekBar s_depth = (SeekBar) findViewById(R.id.seek_chorus_depth);
        SeekBar s_rate = (SeekBar) findViewById(R.id.seek_chorus_rate);
        SeekBar sdi_level = (SeekBar) findViewById(R.id.seek_dis_level);
        SeekBar slow = (SeekBar) findViewById(R.id.seek_low);
        SeekBar smid = (SeekBar) findViewById(R.id.seek_mid);
        SeekBar shigh = (SeekBar) findViewById(R.id.seek_high);
        SeekBar swah = (SeekBar) findViewById(R.id.seek_wah);

        SharedPreferences sharedpreferences = getSharedPreferences(PREFS, 0);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("delay_level", s_level.getProgress());
        editor.putInt("delay_time", s_time.getProgress());
        editor.putInt("chorus_depth", s_depth.getProgress());
        editor.putInt("chorus_rate", s_rate.getProgress());
        editor.putInt("distortion_level", sdi_level.getProgress());
        editor.putInt("low", slow.getProgress());
        editor.putInt("mid", smid.getProgress());
        editor.putInt("high", shigh.getProgress());
        editor.putInt("wah", swah.getProgress());

        editor.commit();

        //os ints que vc tem que enviar sao s_level.getProgess() / 10 (pq ele vai de 0 a 100 e a gente só usa de 0 a 10)
        // entao sao s_time.getProfess() /10;
        //s_depth.getProgress() / 10;
        //s_rate.getProgress() / 10;
        //... e assim pro diante

    }


}



