package lav.wru1;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Ashwin on 3/13/2018.
 */

public class FirstPage extends AppCompatActivity {
    TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstpage);

        tab = (TabLayout) findViewById(R.id.simpleTabLayout);
        final TabLayout.Tab mapg = tab.newTab();
        mapg.setText("map");
        tab.addTab(mapg);
        tab.setSelectedTabIndicatorColor(getResources().getColor(R.color.MildGrey));
        tab.setSelectedTabIndicatorHeight((int) 8d);
       // tab.setTabTextColors(ColorStateList.valueOf(getResources().getColor(R.color.White)));


        final TabLayout.Tab pic = tab.newTab();
        pic.setText("pics");
        tab.addTab(pic);


        tab.getTabAt(R.layout.mapgol);
        mapg.select();


        Fragment fragment = new MapFrag();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simpleFrameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new MapFrag();
                        break;

                    case 1:
                        fragment = new PicFrag();
                        break;
                }

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.e("RE", "reee");
            }
        });
    }
}
