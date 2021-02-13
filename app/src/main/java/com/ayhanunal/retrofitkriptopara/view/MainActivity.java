package com.ayhanunal.retrofitkriptopara.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ayhanunal.retrofitkriptopara.R;
import com.ayhanunal.retrofitkriptopara.adapter.RecyclerViewAdapter;
import com.ayhanunal.retrofitkriptopara.model.KriptoModel;
import com.ayhanunal.retrofitkriptopara.service.KriptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<KriptoModel> kriptoModels;
    private String BASE_URL = "https://api.nomics.com/v1/";
    Retrofit retrofit;

    RecyclerView recyclerView;

    RecyclerViewAdapter recyclerViewAdapter;

    CompositeDisposable compositeDisposable; //yapilan call ler belli bir süreden sonra hafızadan temizlenmesi gerekiyor.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://api.nomics.com/v1/prices?key=ee8ee2761466be14b3ff13014d7f08c3

        recyclerView = findViewById(R.id.recyclerView);


        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        loadData();

    }


    private void loadData(){

        KriptoAPI kriptoAPI = retrofit.create(KriptoAPI.class);

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(kriptoAPI.getData()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::handlerResponse));


        /*
        Call<List<KriptoModel>> call = kriptoAPI.getData();

        call.enqueue(new Callback<List<KriptoModel>>() {
            @Override
            public void onResponse(Call<List<KriptoModel>> call, Response<List<KriptoModel>> response) {
                //gelen cevap
                if(response.isSuccessful()){
                    List<KriptoModel> responseList = response.body();
                    kriptoModels = new ArrayList<>(responseList);

                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(kriptoModels);
                    recyclerView.setAdapter(recyclerViewAdapter);


                }
            }

            @Override
            public void onFailure(Call<List<KriptoModel>> call, Throwable t) {
                //hata olursa
                t.printStackTrace();

            }
        });

         */

    }

    private void handlerResponse(List<KriptoModel> kriptoModelList){

        kriptoModels = new ArrayList<>(kriptoModelList);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter = new RecyclerViewAdapter(kriptoModels);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}