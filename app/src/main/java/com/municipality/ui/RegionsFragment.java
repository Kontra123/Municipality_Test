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
import com.municipality.data.Region;
import com.municipality.network.NetworkManager;
import com.municipality.ui.adapter.ContriesAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


public class RegionsFragment extends Fragment implements NetworkManager.NetworkManagerListener, ContriesAdapter.ContriesAdapterListener{


    private RegionsFragmentListener regionsFragmentListener;
    private RecyclerView regionRecycleViews;

    public RegionsFragment() {
        // Required empty public constructor
    }

    public static RegionsFragment newInstance() {
        RegionsFragment fragment = new RegionsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.regions_layout, container, false);

        regionRecycleViews = (RecyclerView) view.findViewById(R.id.regions_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        regionRecycleViews.setLayoutManager(layoutManager);

        NetworkManager.getInstance().setNetworkManagerListener(this);
        NetworkManager.getInstance().getRegions();

        return view;
    }

    @Override
    public void onResponseFromServerReceived(List<? extends Object> regionListFromServer) {
        List<Region> regionList = (List<Region>)regionListFromServer;
        TreeSet<String> regionsTreeSet = new TreeSet<String>();

        // sort sort regions by are and won't add empty area
        if(regionList != null && !regionList.isEmpty()) {
            for (Region region : regionList) {
                if (!region.region.isEmpty()) {
                    regionsTreeSet.add(region.region);
                }
            }
        }

        RecyclerView.Adapter adapter = new ContriesAdapter(new ArrayList<String>(regionsTreeSet), this);
        regionRecycleViews.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RegionsFragmentListener) {
            regionsFragmentListener = (RegionsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement RegionsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        regionsFragmentListener = null;
    }

    @Override
    public void onItemClicked(String regionName) {
        if (regionsFragmentListener != null) {
            regionsFragmentListener.onFinishedRegionsScreen(regionName);
        }
    }

    public interface RegionsFragmentListener {
        // TODO: Update argument type and name
        void onFinishedRegionsScreen(String regionName);
    }
}
