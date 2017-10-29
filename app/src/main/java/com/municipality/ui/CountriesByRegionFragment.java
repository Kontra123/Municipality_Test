package com.municipality.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.municipality.R;
import com.municipality.data.CountriesByRegion;
import com.municipality.network.NetworkManager;
import com.municipality.ui.adapter.ContriesAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import utils.MyComperator;

/**
 * Created by AmirG on 10/28/2017.
 */
public class CountriesByRegionFragment extends Fragment implements NetworkManager.NetworkManagerListener, ContriesAdapter.ContriesAdapterListener {

    private static final String REGION_NAME_EXTRA = "region_name_extra";

    private CountriesByRegionListener countriesByRegionListener;
    private RecyclerView regionRecycleViews;
    private String regionName;

    public CountriesByRegionFragment() {
        // Required empty public constructor
    }

    public static CountriesByRegionFragment newInstance(String regionName) {
        CountriesByRegionFragment fragment = new CountriesByRegionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(REGION_NAME_EXTRA,regionName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            regionName = bundle.getString(REGION_NAME_EXTRA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.regions_layout, container, false);

        regionRecycleViews = (RecyclerView) view.findViewById(R.id.regions_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        regionRecycleViews.setLayoutManager(layoutManager);

        NetworkManager.getInstance().setNetworkManagerListener(this);
        NetworkManager.getInstance().getContriesByRegion(regionName);

        return view;
    }

    @Override
    public void onResponseFromServerReceived(List<?> countriesByRegionListFromServer) {

        List<CountriesByRegion> countriesByRegionList = (List<CountriesByRegion>)countriesByRegionListFromServer;
        countriesByRegionList = new MyComperator().doSortByArea(countriesByRegionList);

        List<String> countriesNames = new ArrayList<String>();
        for (CountriesByRegion countryByRegionItem : countriesByRegionList) {
            countriesNames.add(countryByRegionItem.name);
        }

        RecyclerView.Adapter adapter = new ContriesAdapter(countriesNames, this);
        regionRecycleViews.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CountriesByRegionListener) {
            countriesByRegionListener = (CountriesByRegionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CountriesByRegionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        countriesByRegionListener = null;
    }

    @Override
    public void onItemClicked(String name) {
        if (countriesByRegionListener != null) {
            countriesByRegionListener.onFinishedCountriesByRegionScreen(name);
        }
    }

    public interface CountriesByRegionListener {
        // TODO: Update argument type and name
        void onFinishedCountriesByRegionScreen(String regionName);
    }
}
