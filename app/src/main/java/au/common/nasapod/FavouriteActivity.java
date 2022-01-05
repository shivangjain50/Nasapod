package au.common.nasapod;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import au.common.nasapod.repository.APODRepository;

public class FavouriteActivity extends AppCompatActivity {

    private APODViewModel apodViewModel;
    private RecyclerView favorite_list;
    private FavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        actionBarSetup();
        registerControls();
        apodViewModel = new ViewModelProvider(this, new APODViewModelFactory(new APODRepository(getApplication()))).get(APODViewModel.class);
        setUpData();
    }

    private void setUpData() {
        apodViewModel.getApodFavourites().observe(this, favourites -> {
            System.out.println(favourites);
            adapter = new FavoriteAdapter(favourites, favourite -> {
                apodViewModel.deleteFavourite(favourite);
            });
            favorite_list.setAdapter(adapter);
        });
        favorite_list.setLayoutManager(new LinearLayoutManager(this));

    }

    private void registerControls() {
        favorite_list = findViewById(R.id.favorite_list);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void actionBarSetup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar ab = getSupportActionBar();
            ab.setTitle("Favourites");
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}