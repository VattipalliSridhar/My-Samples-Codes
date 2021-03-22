package com.apps.myapplication.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apps.myapplication.SongListActivity;
import com.apps.myapplication.api.RetrofitApis;
import com.apps.myapplication.api.Utils;
import com.apps.myapplication.model.SongModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongViewModel extends AndroidViewModel {
    private MutableLiveData<List<SongModel.Song>> songMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> messageToShow = new MutableLiveData<>();
    RetrofitApis retrofitApis = RetrofitApis.Factory.create();
    Context mContext;

    public SongViewModel(@NonNull Application application) {
        super(application);
    }


    public void getSongList(Context songListActivity) {
        mContext=songListActivity;

        Call<SongModel> songModelCall =retrofitApis.getSongList();
        songModelCall.enqueue(new Callback<SongModel>() {
            @Override
            public void onResponse(Call<SongModel> call, Response<SongModel> response) {
                if (response.isSuccessful()) {

                    SongModel songModel = response.body();
                    songMutableLiveData.setValue(songModel.getSongs());

                }else{
                    String errorCode;
                    switch (response.code()) {
                        case 400:
                            errorCode = "404! not found";
                            messageToShow.setValue(errorCode);
                            break;
                        case 500:
                            errorCode = "500! server broken";
                            messageToShow.setValue(errorCode);
                            break;
                        default:
                            // errorCode = "Unknown error";
                            messageToShow.setValue(response.message());
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<SongModel> call, Throwable t) {
                if (!Utils.isNetworkAvailable(mContext)) {
                    messageToShow.setValue("Ooops! Network failure, please try again.");
                } else {
                    messageToShow.setValue("Something Wrong");
                }
            }
        });
    }



    public MutableLiveData<List<SongModel.Song>> getListMutableLiveData() {
        if (songMutableLiveData == null)
            songMutableLiveData = new MutableLiveData<>();
        return songMutableLiveData;
    }

    public MutableLiveData<String> getMessageToShow() {
        if (messageToShow == null)
            messageToShow.setValue("");
        return messageToShow;
    }
}
