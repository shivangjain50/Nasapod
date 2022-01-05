package au.common.nasapod.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import au.common.nasapod.model.APODFavourite;
import au.common.nasapod.model.APODModel;

@Dao
public interface APODao {

    @Query("SELECT * FROM apod_table")
    LiveData<APODModel> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(APODModel... apod);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(APODFavourite... apod);

    @Query("SELECT * FROM apod_fav_table")
    LiveData<List<APODFavourite>> getAllFavorites();

    @Delete
    void deleteFavorite(APODFavourite... favourite);

    @Query("DELETE FROM apod_table")
    void deleteAll();
}
