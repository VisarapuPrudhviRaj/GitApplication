package pr.bluefrog.gitapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {
    public Receiver() {
    }

    CountValueListner listner;

    public Receiver(CountValueListner listner) {
        this.listner = listner;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                long value = millisUntilFinished / 1000;
                listner.onCount(value);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {


            }

        }.start();

        Toast.makeText(context, "Device Storage low", Toast.LENGTH_LONG).show();
    }

    public interface CountValueListner {
        public void onCount(long value);
    }
}
