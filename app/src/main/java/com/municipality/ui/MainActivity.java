package com.municipality.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.municipality.R;


public class MainActivity extends AppCompatActivity implements RegionsFragment.RegionsFragmentListener, CountriesByRegionFragment.CountriesByRegionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RegionsFragment regionsFragment = RegionsFragment.newInstance();
        openFragment(regionsFragment);

    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.main_layout, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    @Override
    public void onFinishedRegionsScreen(String regionName) {
        CountriesByRegionFragment regionsFragment = CountriesByRegionFragment.newInstance(regionName);
        openFragment(regionsFragment);
    }

    @Override
    public void onFinishedCountriesByRegionScreen(String countryName) {
        CountryDetailsFragment countryDetailsFragment = CountryDetailsFragment.newInstance(countryName);
        openFragment(countryDetailsFragment);
    }

    @Override
    public void onBackPressed() {

        FragmentManager fm = getFragmentManager();

        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        }
        else {
            finish();
        }

    }

}
