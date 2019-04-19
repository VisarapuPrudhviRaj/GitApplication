package pr.bluefrog.gitapplication.bottomnavigationview;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import pr.bluefrog.gitapplication.R;

public class BottomNavActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    TextView tv_toolbar;
    BottomNavigationView bottomVIew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        tv_toolbar = findViewById(R.id.tv_toolbar);

        bottomVIew = findViewById(R.id.bottomVIew);
        bottomVIew.setOnNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment;

        switch (item.getItemId()) {
            case R.id.navigation_shop:
                fragment = new ShopFrament();
                loadFragment(fragment);
                tv_toolbar.setText("Shops");
                return true;
            case R.id.navigation_basket:
                fragment = new BasketFragment();
                loadFragment(fragment);
                tv_toolbar.setText("Baskets");
                return true;
            case R.id.navigation_gift:
                fragment = new GittFragment();
                loadFragment(fragment);
                tv_toolbar.setText("Gifts");
                return true;
            case R.id.navigation_items:
                fragment = new ItemFragment();
                loadFragment(fragment);
                tv_toolbar.setText("Cart");
                return true;

            case R.id.navigation_settings:
                fragment = new SettingsFragment();
                loadFragment(fragment);
                tv_toolbar.setText("Settings");
                return true;
        }

        return false;
    }

    private void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment).addToBackStack(null).commit();
    }
}
