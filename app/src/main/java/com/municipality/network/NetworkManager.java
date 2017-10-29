package com.municipality.network;


import com.municipality.data.CountriesByRegion;
import com.municipality.data.CountryDetails;
import com.municipality.data.Region;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AmirG on 10/27/2017.
 */
public class NetworkManager {

    private APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);;
    private NetworkManagerListener networkManagerListener;

    public static synchronized NetworkManager getInstance()
    {
        return SingeltonHolder.NETWORK_MANAGER;
    }

    private static class SingeltonHolder
    {
        private static final NetworkManager NETWORK_MANAGER = new NetworkManager();
    }

    public interface NetworkManagerListener {
        public void onResponseFromServerReceived(List<?> list);
    }

    public void setNetworkManagerListener(NetworkManagerListener networkManagerListener) {
        this.networkManagerListener = networkManagerListener;
    }

    public void getRegions() {

        Call<List<Region>> call = apiInterface.getRegions();
        call.enqueue(new Callback<List<Region>>() {
            @Override
            public void onResponse(Call<List<Region>> call, Response<List<Region>> response) {
                List<?> regionsList = response.body();

                handleResponseFromServer(regionsList);
            }

            @Override
            public void onFailure(Call<List<Region>> call, Throwable t) {

                call.cancel();

                handleResponseFromServer(null);
            }
        });
    }

    public void getContriesByRegion(String region) {

        Call<List<CountriesByRegion>> call = apiInterface.getCountriesByRegion(region);
        call.enqueue(new Callback<List<CountriesByRegion>>() {
            @Override
            public void onResponse(Call<List<CountriesByRegion>> call, Response<List<CountriesByRegion>> response) {

                List<?> countriesByRegion = response.body();

                handleResponseFromServer(countriesByRegion);
            }

            @Override
            public void onFailure(Call<List<CountriesByRegion>> call, Throwable t) {

                call.cancel();

                handleResponseFromServer(null);
            }
        });
    }

    public void getContryDetails(String countryName) {

        Call<List<CountryDetails>> call = apiInterface.getCountryDetails(countryName);
        call.enqueue(new Callback<List<CountryDetails>>() {
            @Override
            public void onResponse(Call<List<CountryDetails>> call, Response<List<CountryDetails>> response) {

                List<?> countryDetails = response.body();

                handleResponseFromServer(countryDetails);
            }

            @Override
            public void onFailure(Call<List<CountryDetails>> call, Throwable t) {

                call.cancel();

                handleResponseFromServer(null);
            }
        });
    }

    private void handleResponseFromServer(List<?> countriesByRegion) {
        networkManagerListener.onResponseFromServerReceived(countriesByRegion);
    }

}
