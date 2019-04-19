package pr.bluefrog.gitapplication.bottomnavigationview;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import pr.bluefrog.gitapplication.R;
import pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.Grosery;
import pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.MyDatabase;
import pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.Shops;

import static pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.MyDatabase.MIGRATION_2_3;
import static pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.MyDatabase.MIGRATION_3_4;
import static pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.MyDatabase.MIGRATION_4_5;
import static pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.MyDatabase.MIGRATION_5_6;

public class ItemAddUpdateActivity extends AppCompatActivity {

    EditText et_shop_name, et_shop_item;
    MyDatabase database;
    List<Grosery> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_add_update);
        findViews();
    }

    private void findViews() {
        et_shop_name = (EditText) findViewById(R.id.et_shop_name);
        et_shop_item = (EditText) findViewById(R.id.et_shop_item);

        database = Room.databaseBuilder(this, MyDatabase.class, "productsdb")
                .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6)
                .fallbackToDestructiveMigration()
                .build();
    }

    public void onClick_add(View view) {

        Grosery grosery = new Grosery();
        grosery.setGroseryname(et_shop_name.getText().toString().trim());
        grosery.setGroseryItems(et_shop_item.getText().toString().trim());
        list.add(grosery);

        database.groseryDao().insertAll(list);
        setSuccess(grosery);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setFailure();
    }

    private void setSuccess(Grosery shops) {
        Intent intent = new Intent();
        intent.putExtra("model", shops);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setFailure() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
