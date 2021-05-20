package com.example.streetfeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.streetfeed.fragments.CartFragment;
import com.example.streetfeed.fragments.HomeFragment;
import com.example.streetfeed.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Customer_bottom_nav extends AppCompatActivity {

    BottomNavigationView bottom_nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_bottom_nav);
        //The below 3 lines code is for opening the home fragment by default...........

        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container,new HomeFragment());
        transaction.commit();

        bottom_nav=findViewById(R.id.bottom_nav);
        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                switch(item.getItemId())
                {
                    case R.id.home_menu:
                        transaction.add(R.id.fragment_container,new HomeFragment());
                        break;

                    case R.id.cart_menu:
                        transaction.add(R.id.fragment_container,new CartFragment());
                        break;
                    case R.id.profile_menu:
                        transaction.add(R.id.fragment_container,new ProfileFragment());
                        break;
                }
                transaction.commit();
                return true;
            }
        });
    }
}