package project.akshay.recorderapptask;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    TabAdapter tabAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new RecorderFragment(),"Recorder");
        tabAdapter.addFragment(new ListFragment(), "List");

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
