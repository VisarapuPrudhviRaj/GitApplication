package pr.bluefrog.gitapplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Receiver.CountValueListner {

    Receiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        receiver = new Receiver(this);


    }

    public void onClick_Broadcast(View view) {

        Intent intent = new Intent("pr.bluefrog.gitapplication.CUSTOM_INTENT");
        intent.putExtra("LOW", "Low Memory");
        sendBroadcast(intent);
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("pr.bluefrog.gitapplication.CUSTOM_INTENT");
        registerReceiver(receiver, intentFilter);
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onCount(long value) {
        ((TextView) findViewById(R.id.textView)).setText(String.valueOf(value));
        if (value == 0) {
            ((TextView) findViewById(R.id.textView)).setText("DONE");
        }
    }

    public void onClick_Service(View view) {
        startActivity(new Intent(this, ServiceActivtiy.class));
    }
}
