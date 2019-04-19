package pr.bluefrog.gitapplication.bottomnavigationview;

import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import pr.bluefrog.gitapplication.R;

public class BillingFragment extends Fragment {

    LinearLayout bottom_sheet_layout;
    Button btnPayOrder;
    BottomSheetBehavior bottomSheetBehavior;

    SQLiteStatement statement;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.billing_fragment_layout, container, false);
        bottom_sheet_layout = view.findViewById(R.id.bottom_sheet_layout);
        btnPayOrder = view.findViewById(R.id.btnPayOrder);
        bottomSheetProces();

        return view;
    }

    private void bottomSheetProces() {


        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet_layout);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        bottom_sheet_layout.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        bottom_sheet_layout.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        btnPayOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    bottom_sheet_layout.setVisibility(View.VISIBLE);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    bottom_sheet_layout.setVisibility(View.GONE);
                }
            }
        });

    }

}
