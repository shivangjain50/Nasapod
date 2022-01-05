package au.common.nasapod;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import au.common.nasapod.model.APODFavourite;
import au.common.nasapod.model.APODModel;
import au.common.nasapod.repository.APODRepository;

public class APODViewModel extends ViewModel {
    private APODRepository apodRepository;

    public APODViewModel(APODRepository apodRepository) {
        this.apodRepository = apodRepository;
    }

    public LiveData<APODModel> getApod(String date) {
        return apodRepository.getAPOD(date);
    }

    public void insertFavourite(APODFavourite model) {
        apodRepository.insertFavorite(model);
    }

    public LiveData<List<APODFavourite>> getApodFavourites() {
        return apodRepository.getAPDOFavorites();
    }

    public void deleteFavourite(APODFavourite favourite) {
        apodRepository.deleteFavourite(favourite);
    }
}
