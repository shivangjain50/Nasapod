package au.common.nasapod;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import java.util.Calendar;

import au.common.nasapod.model.APODFavourite;
import au.common.nasapod.model.APODModel;
import au.common.nasapod.repository.APODRepository;

public class MainActivity extends AppCompatActivity {

    private TextView title, date, description;
    private ImageView image, addToFavourite;
    private APODViewModel apodViewModel;
    private WebView videoView;
    private APODModel apodModel;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerControls();
        apodViewModel = new ViewModelProvider(this, new APODViewModelFactory(new APODRepository(getApplication()))).get(APODViewModel.class);
        setupData();
    }

    private void setupData() {
        fetchData(getDate());
        addToFavourite.setOnClickListener(v -> {
            APODFavourite favourite = getModelMapped();
            apodViewModel.insertFavourite(favourite);
            Toast.makeText(getApplication(), "Successfully added to favourites", Toast.LENGTH_LONG).show();
        });
    }

    private APODFavourite getModelMapped() {
        APODFavourite model = new APODFavourite();
        model.setDate(apodModel.getDate());
        model.setCopyright(apodModel.getCopyright());
        model.setExplanation(apodModel.getExplanation());
        model.setMediaType(apodModel.getMediaType());
        model.setServiceVersion(apodModel.getServiceVersion());
        model.setTitle(apodModel.getTitle());
        model.setUrl(apodModel.getUrl());
        return model;
    }

    void fetchData(String datee) {
        apodViewModel.getApod(datee).observe(this, apodModel -> {
            if (apodModel == null) return;
            this.apodModel = apodModel;
            title.setText(apodModel.getTitle());
            date.setText(apodModel.getDate());
            description.setText(apodModel.getExplanation());
            if (apodModel.getMediaType().equals(getString(R.string.Image))) {
                Picasso.get().load(apodModel.getUrl()).into(image);
                image.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.GONE);
            } else {
                openVideo(apodModel.getUrl());
                image.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
            }
            saveDate(apodModel.getDate());
        });
    }

    private void saveDate(String date) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Date", date);
        editor.apply();
    }

    private String getDate() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("Date", null);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void openVideo(String videoUrl) {
        videoView.loadUrl(videoUrl);
        videoView.setWebViewClient(new WebViewClient());
        videoView.getSettings().setJavaScriptEnabled(true);
    }

    private void registerControls() {
        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        description = findViewById(R.id.description);
        image = findViewById(R.id.image);
        videoView = findViewById(R.id.video);
        addToFavourite = findViewById(R.id.addtofavourite);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                DialogFragment fragment = new DatePickerFragment((view, year, month, day) -> {
                    fetchData(String.format("%d-%d-%d", year, month + 1, day));
                });
                fragment.show(getSupportFragmentManager(), "datePicker");
                return true;
            case R.id.favourite:
                startActivity(new Intent(this, FavouriteActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private OnDateSelectListener onDateSelectListener;

        public DatePickerFragment(OnDateSelectListener onDateSelectListener) {
            this.onDateSelectListener = onDateSelectListener;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            onDateSelectListener.onSelectedDate(view, year, month, day);
        }

        public interface OnDateSelectListener {
            void onSelectedDate(DatePicker view, int year, int month, int day);
        }
    }
}