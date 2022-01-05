package au.common.nasapod;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import au.common.nasapod.repository.APODRepository;

public class APODViewModelFactory implements ViewModelProvider.Factory {

    private final APODRepository apodRepository;

    public APODViewModelFactory(APODRepository application) {
        this.apodRepository = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(APODViewModel.class)) {
            return (T) new APODViewModel(apodRepository);
        } else {
            try {
                throw new IllegalAccessException("Unknown class name");
            } catch (IllegalAccessException e) {
                return null;
            }
        }
    }
}
