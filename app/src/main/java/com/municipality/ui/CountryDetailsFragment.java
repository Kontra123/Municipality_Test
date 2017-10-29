package com.municipality.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.municipality.R;
import com.municipality.data.CountryDetails;
import com.municipality.network.NetworkManager;
import com.municipality.ui.adapter.ContriesAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AmirG on 10/28/2017.
 */
public class CountryDetailsFragment  extends Fragment implements NetworkManager.NetworkManagerListener {

    private static final String COUNTRY_NAME_EXTRA = "country_name_extra";

    private RecyclerView regionRecycleViews;
    private String countryName;

    public CountryDetailsFragment() {
        // Required empty public constructor
    }

    public static CountryDetailsFragment newInstance(String countryName) {
        CountryDetailsFragment fragment = new CountryDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(COUNTRY_NAME_EXTRA,countryName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            countryName = bundle.getString(COUNTRY_NAME_EXTRA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.regions_layout, container, false);

        regionRecycleViews = (RecyclerView) view.findViewById(R.id.regions_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        regionRecycleViews.setLayoutManager(layoutManager);

        NetworkManager.getInstance().setNetworkManagerListener(this);
        NetworkManager.getInstance().getContryDetails(countryName);

        return view;
    }

    @Override
    public void onResponseFromServerReceived(List<?> countryDetailsListFromServer) {

        List<CountryDetails> countryDetailsList = (List<CountryDetails>)countryDetailsListFromServer;

        StringBuffer countryDetailsStringBuffer = new StringBuffer();
        List<String> countryDetails = new ArrayList<String>();
        for (CountryDetails countryDetailsItem : countryDetailsList) {
            countryDetailsStringBuffer.append("Currency name: " + countryDetailsItem.currencies.get(0).name + "\n\n");
            countryDetailsStringBuffer.append("Capital: " + countryDetailsItem.capital + "\n\n");
            countryDetailsStringBuffer.append("Borders: " + countryDetailsItem.borders);
            countryDetails.add(countryDetailsStringBuffer.toString());
        }

        RecyclerView.Adapter adapter = new ContriesAdapter(countryDetails, null);
        regionRecycleViews.setAdapter(adapter);
    }

}
