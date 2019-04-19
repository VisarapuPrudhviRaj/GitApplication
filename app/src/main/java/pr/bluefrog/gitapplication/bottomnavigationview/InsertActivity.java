package pr.bluefrog.gitapplication.bottomnavigationview;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pr.bluefrog.gitapplication.R;
import pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.MyDatabase;
import pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.Products;

import static pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.MyDatabase.MIGRATION_2_3;
import static pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.MyDatabase.MIGRATION_3_4;
import static pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.MyDatabase.MIGRATION_4_5;
import static pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.MyDatabase.MIGRATION_5_6;

public class InsertActivity extends AppCompatActivity {

    EditText et_username, et_desg;
    MyDatabase database;
    List<Products> list = new ArrayList<>();
    Products products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        findViews();

        if (getIntent() != null && getIntent().getExtras() != null) {
            products = (Products) getIntent().getSerializableExtra("model");
            setData(products);
        }


    }

    private void setData(Products products) {
        if (products != null) {
            et_username.setText(products.getName());
            et_desg.setText(products.getDesgination());
            final int id = products.getUid();
            ((Button) findViewById(R.id.btn_submit)).setText("Update");
            ((Button) findViewById(R.id.btn_submit)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String name = et_username.getText().toString().trim();
                    String desg = et_desg.getText().toString().trim();

                    database.productDao().update(name, desg, id);
                    Toast.makeText(InsertActivity.this, "Recorded Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

        }
    }

    private void findViews() {

        et_username = (EditText) findViewById(R.id.et_username);
        et_desg = (EditText) findViewById(R.id.et_desg);

        database = Room.databaseBuilder(InsertActivity.this, MyDatabase.class, "productsdb")
                .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6)
                .fallbackToDestructiveMigration()
                .build();

    }

    public void onClick_Insert(View view) {

        String name = et_username.getText().toString().trim();
        String desg = et_desg.getText().toString().trim();

        Products products = new Products();
        products.setName(name);
        products.setDesgination(desg);
        list.add(products);

        database.productDao().insertAll(list);

        Toast.makeText(this, "Record Inserted", Toast.LENGTH_SHORT).show();

        et_username.setText("");
        et_desg.setText("");

        finish();


    }


}
