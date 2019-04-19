package pr.bluefrog.gitapplication.bottomnavigationview;

import android.content.Intent;
import android.net.LinkAddress;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import pr.bluefrog.gitapplication.R;
import pr.bluefrog.gitapplication.bottomnavigationview.retrofit.SampleRetrofitActivity;

public class SettingsFragment extends Fragment {

    Button btnPersistent, btnModal,btnRetrofit;

    LinearLayout modal_layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_layout, container, false);
        btnPersistent = view.findViewById(R.id.btnPersistent);
        btnModal = view.findViewById(R.id.btnModal);
        btnRetrofit = view.findViewById(R.id.btnRetrofit);
        modal_layout = view.findViewById(R.id.modal_layout);

        btnPersistent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BillingFragment fragment = new BillingFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                v = getLayoutInflater().inflate(R.layout.modal_bottom_sheet_layout, null);
                BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
                dialog.setContentView(v);
                ModalBottomSheetFragment fragment = new ModalBottomSheetFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, fragment).addToBackStack(null).commit();
                dialog.show();
                modal_layout.setVisibility(View.VISIBLE);



            }
        });

        btnRetrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SampleRetrofitActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

}
