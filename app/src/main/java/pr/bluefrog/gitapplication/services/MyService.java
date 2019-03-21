package pr.bluefrog.gitapplication.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.Serializable;

import pr.bluefrog.gitapplication.R;
import pr.bluefrog.gitapplication.model.MediaPlayerModel;

public class MyService extends Service {
    MediaPlayer mediaPlayer;
    MediaPlayerModel model;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        mediaPlayer = MediaPlayer.create(this, R.raw.cheli);
        mediaPlayer.setLooping(false);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        if (mediaPlayer != null) {
            model = new MediaPlayerModel();
            model.setDuration(mediaPlayer.getDuration() + "");
            model.setTrackInfo(mediaPlayer.getTrackInfo().toString());
            model.setCurrentTime(mediaPlayer.getCurrentPosition() + "");
        }

        if (mediaPlayer.isPlaying()) {
            Toast.makeText(this, "Paused", Toast.LENGTH_SHORT).show();
            mediaPlayer.pause();
            Intent intent1 = new Intent("pr.bluefrog.MEDIAPLAYER");
            intent1.putExtra("playerobj", model);
            intent1.putExtra("status", "paused");
            sendBroadcast(intent1);
        } else {
            Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
            mediaPlayer.start();

            Intent intent1 = new Intent("pr.bluefrog.MEDIAPLAYER");
            intent1.putExtra("playerobj", model);
            intent1.putExtra("status", "start");
            sendBroadcast(intent1);

        }

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Toast.makeText(this, "Closed", Toast.LENGTH_SHORT).show();
        mediaPlayer.stop();
    }


}
