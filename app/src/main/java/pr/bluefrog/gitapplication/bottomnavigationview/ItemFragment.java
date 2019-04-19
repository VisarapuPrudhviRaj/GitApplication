package pr.bluefrog.gitapplication.bottomnavigationview;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pr.bluefrog.gitapplication.R;
import pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.Grosery;
import pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.MyDatabase;
import pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.Products;
import pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.Shops;

import static pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.MyDatabase.MIGRATION_2_3;
import static pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.MyDatabase.MIGRATION_3_4;
import static pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.MyDatabase.MIGRATION_4_5;
import static pr.bluefrog.gitapplication.bottomnavigationview.roomdatabase.MyDatabase.MIGRATION_5_6;

public class ItemFragment extends Fragment {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    MyDatabase database;
    List<Grosery> list = new ArrayList<>();
    LinearLayout ll_item;
    private int REQUEST_CODE = 101;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_layout, container, false);
        findView(view);

        /*  loadData();*/

        return view;
    }

    private void findView(View view) {
        fab = view.findViewById(R.id.fab);
        ll_item = (LinearLayout) view.findViewById(R.id.ll_item);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity().getApplicationContext(), ItemAddUpdateActivity.class), REQUEST_CODE);
            }
        });

        loadData();
    }


    private void loadData() {
        database = Room.databaseBuilder(getActivity().getApplicationContext(),
                MyDatabase.class, "productsdb")
                .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5,MIGRATION_5_6)
                .fallbackToDestructiveMigration()
                .build();
        list = database.groseryDao().getAll();

        for (int i = 0; i < list.size(); i++) {
            Grosery grosery = new Grosery();
            grosery.setGroseryname(list.get(i).getGroseryname());
            grosery.setGroseryItems(list.get(i).getGroseryItems());
            addStrip(grosery);
        }


        Log.d("List", "loadData: " + list);
    }

    @Override
    public void onResume() {
        super.onResume();
//        loadData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                if (data != null) {
                    addStrip((Grosery) data.getExtras().getSerializable("model"));
                }

            }
        }
    }

    private void addStrip(Grosery model) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_item_layout, ll_item, false);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_desg = (TextView) view.findViewById(R.id.tv_desg);
        ImageView img_edit = (ImageView) view.findViewById(R.id.img_edit);
        ImageView img_del = (ImageView) view.findViewById(R.id.img_del);

        tv_name.setText(model.getGroseryname());
        tv_desg.setText(model.getGroseryItems());

        ll_item.addView(view);


    }
}
