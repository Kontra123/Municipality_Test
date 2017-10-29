package com.municipality.network;



import com.municipality.data.CountriesByRegion;
import com.municipality.data.CountryDetails;
import com.municipality.data.Region;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by anupamchugh on 09/01/17.
 */

public interface APIInterface {

    @GET("/rest/v2/all/?fields=region")
    Call<List<Region>> getRegions();

    @GET("/rest/v2/region/{region}")
    Call<List<CountriesByRegion>> getCountriesByRegion(@Path("region") String region);

    @GET("/rest/v2/name/{country}")
    Call<List<CountryDetails>> getCountryDetails(@Path("country") String country);

}
