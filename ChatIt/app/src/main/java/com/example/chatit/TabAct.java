package com.example.chatit;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.chatit.ui.main.SectionsPagerAdapter;
import com.example.chatit.databinding.ActivityTabBinding;

public class TabAct extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ActivityTabBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTabBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;

        ImageButton menuBtn = binding.menuTab;

        tabs.setSelectedTabIndicatorColor(Color.parseColor("#FF000000"));
        tabs.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabs.setTabTextColors(Color.parseColor("#FF000000"), Color.parseColor("#fff"));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        menuBtn.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(TabAct.this, view);
            popupMenu.setOnMenuItemClickListener(TabAct.this);
            popupMenu.inflate(R.menu.menufile);
            popupMenu.show();
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.newgroup:
                Toast.makeText(this, "New group", Toast.LENGTH_SHORT).show();
                return  true;

            case R.id.privacy:
                Toast.makeText(this, "Privacy", Toast.LENGTH_SHORT).show();
                break;
            case R.id.profile:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
        }
        return false;

    }
}