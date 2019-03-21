package pr.bluefrog.gitapplication;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

import pr.bluefrog.gitapplication.model.MediaPlayerModel;
import pr.bluefrog.gitapplication.services.MyService;

public class ServiceActivtiy extends AppCompatActivity {

    MyService myService;
    BroadcastReceiver broadcastReceiver;
    MediaPlayerModel model;
    String status;
    SeekBar seekbar;
    private int oneTimeOnly = 0;
    Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_activtiy);
        myService = new MyService();
        findViews();

    }

    private void findViews() {

        seekbar = (SeekBar) findViewById(R.id.seekbar);
        seekbar.setClickable(false);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.getExtras() != null) {
                    model = (MediaPlayerModel) intent.getSerializableExtra("playerobj");
                    status = intent.getStringExtra("status");
                    setData(model, status);
                }

            }
        };
    }

    String mins, running;

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {

            ((TextView) findViewById(R.id.tv_running_time)).setText(running);
            seekbar.setProgress(Integer.parseInt(model.getDuration()));
            myHandler.postDelayed(this, 100);
        }
    };


    private void setData(final MediaPlayerModel model, String status) {


        mins = String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(Long.valueOf(model.getDuration())),
                TimeUnit.MILLISECONDS.toSeconds(Long.valueOf(model.getDuration())) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(Long.valueOf(model.getDuration()))));

        running = String.format("%d : %d ", TimeUnit.MILLISECONDS.toMinutes(Long.valueOf(model.getCurrentTime())),
                TimeUnit.MILLISECONDS.toSeconds(Long.valueOf(model.getCurrentTime())) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(Long.valueOf(model.getCurrentTime()))));


        if (oneTimeOnly == 0) {
            seekbar.setMax(Integer.parseInt(model.getDuration()));
            oneTimeOnly = 1;
        }


        if (status.trim().equals("start")) {

            ((TextView) findViewById(R.id.tv_play_name)).setText(String.valueOf(model.getTrackInfo()));
            ((TextView) findViewById(R.id.tv_play_name)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.tv_play_duration)).setText(String.valueOf(mins));
            ((TextView) findViewById(R.id.tv_running_time)).setText(String.valueOf(running));
//            songTimer(Long.valueOf(model.getDuration()));
            ((ImageView) findViewById(R.id.img_start)).setImageResource(android.R.drawable.ic_media_pause);
            seekbar.setProgress(Integer.parseInt(model.getCurrentTime()));
            myHandler.postDelayed(UpdateSongTime, 100);

        } else {
            ((TextView) findViewById(R.id.tv_play_name)).setText(String.valueOf(model.getTrackInfo()));
            ((TextView) findViewById(R.id.tv_play_name)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.tv_play_duration)).setText(String.valueOf(mins));
            ((TextView) findViewById(R.id.tv_running_time)).setText(String.valueOf(running));
           /* seekbar.setProgress(Integer.parseInt(model.getDuration()));
            myHandler.postDelayed(UpdateSongTime, 100);*/
            ((ImageView) findViewById(R.id.img_start)).setImageResource(android.R.drawable.ic_media_play);
        }


    }

    private void songTimer(Long aLong) {
        new CountDownTimer(aLong, 1000) {

            public void onTick(long millisUntilFinished) {

                if (status.equals("pause")) {

                } else {
                    running = String.format("%d : %d ", TimeUnit.MILLISECONDS.toMinutes(Long.valueOf(millisUntilFinished)),
                            TimeUnit.MILLISECONDS.toSeconds(Long.valueOf(millisUntilFinished)) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(Long.valueOf(millisUntilFinished))));

                    ((TextView) findViewById(R.id.tv_running_time)).setText(running);
                }


                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                ((TextView) findViewById(R.id.tv_running_time)).setText("00:00");
            }

        }.start();
    }

    public void onClick_StartService(View view) {
        startService(new Intent(this, myService.getClass()));
    }

    public void onClick_StopService(View view) {
        stopService(new Intent(this, myService.getClass()));
        finish();
    }

    public void onClick_PreviousService(View view) {
        startService(new Intent(this, myService.getClass()));
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("pr.bluefrog.MEDIAPLAYER");
        registerReceiver(broadcastReceiver, intentFilter);
        super.onStart();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(broadcastReceiver);
        super.onPause();
    }


}
