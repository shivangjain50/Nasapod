package au.common.nasapod.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import au.common.nasapod.BuildConfig;
import au.common.nasapod.IConstants;
import au.common.nasapod.model.APODFavourite;
import au.common.nasapod.model.APODModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APODRepository {

    private APODao apoDao;

    public APODRepository(Application application) {
        APODatabase db = APODatabase.getDatabase(application);
        apoDao = db.apodDao();
    }

    public LiveData<APODModel> getAPOD(String date) {
        getRetrofitBuilder().getAPOD(IConstants.API_KEY, date).enqueue(new Callback<APODModel>() {
            @Override
            public void onResponse(Call<APODModel> call, Response<APODModel> response) {
                insert(response.body());
            }

            @Override
            public void onFailure(Call<APODModel> call, Throwable t) {

            }
        });
        return apoDao.getAll();
    }

    private APODService getRetrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(APODService.class);
    }

    void insert(APODModel apodModel) {
        APODatabase.databaseWriteExecutor.execute(() -> {
            apoDao.deleteAll();
            apoDao.insert(apodModel);
        });
    }

    public void insertFavorite(APODFavourite apodModel) {
        APODatabase.databaseWriteExecutor.execute(() -> {
            apoDao.insert(apodModel);
        });
    }

    public LiveData<List<APODFavourite>> getAPDOFavorites() {
        return apoDao.getAllFavorites();
    }

    public void deleteFavourite(APODFavourite favourite) {
        APODatabase.databaseWriteExecutor.execute(() -> {
            apoDao.deleteFavorite(favourite);
        });
    }
}