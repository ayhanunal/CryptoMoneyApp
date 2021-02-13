package com.ayhanunal.retrofitkriptopara.service;

import com.ayhanunal.retrofitkriptopara.model.KriptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface KriptoAPI {

    //get, post, update, delete

    //https://api.nomics.com/v1/prices?key=ee8ee2761466be14b3ff13014d7f08c3

    @GET("prices?key=ee8ee2761466be14b3ff13014d7f08c3")
    Observable<List<KriptoModel>> getData();

    //Call<List<KriptoModel>> getData();

}
