package pr.bluefrog.gitapplication.bottomnavigationview;

import android.app.AlertDialog;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class GittFragment extends Fragment {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    MyDatabase database;
    GiftAdapter adapter;
    List<Products> list = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gift_layout, container, false);
        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), InsertActivity.class));
            }
        });

        loadData();

        return view;
    }


    private void loadData() {
        database = Room.databaseBuilder(getActivity().getApplicationContext(),
                MyDatabase.class, "productsdb")
                .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6)
                .fallbackToDestructiveMigration()
                .build();

        list = database.productDao().getAll();

        Log.d("List", "loadData: " + list);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GiftAdapter(getActivity().getApplicationContext(), list);

        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.MyViewHolder> {

        Context context;
        List<Products> list;

        public GiftAdapter(Context context, List<Products> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public GiftAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.gift_adapter_layout, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final GiftAdapter.MyViewHolder holder, int position) {

            final Products model = list.get(position);

            holder.tv_name.setText(model.getName());
            holder.tv_desg.setText(model.getDesgination());

            holder.img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, InsertActivity.class);
                    intent.putExtra("model", model);
                    startActivity(intent);

                }
            });

            holder.img_del.setOnClickListener(


                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alerDialog(model, holder);
                        }
                    });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_name, tv_desg;
            ImageView img_edit, img_del;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv_name = itemView.findViewById(R.id.tv_name);
                tv_desg = itemView.findViewById(R.id.tv_desg);
                img_edit = itemView.findViewById(R.id.img_edit);
                img_del = itemView.findViewById(R.id.img_del);
            }
        }
    }


    private void alerDialog(final Products model, final GiftAdapter.MyViewHolder holder) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete this?");
        alertDialog.create();
        // Setting Icon to Dialog
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke YES event
                database.productDao().delete(model.getUid());
                list.remove(holder.getAdapterPosition());
                adapter.notifyDataSetChanged();
            }
        });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
}
