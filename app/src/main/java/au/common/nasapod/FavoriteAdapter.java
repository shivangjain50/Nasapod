package au.common.nasapod;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import au.common.nasapod.model.APODFavourite;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavViewHolder> {

    private final List<APODFavourite> favourites;
    private final OnFavouriteSelectListener onFavouriteSelectListener;

    public FavoriteAdapter(List<APODFavourite> favourites, OnFavouriteSelectListener onFavouriteSelectListener) {
        this.favourites = favourites;
        this.onFavouriteSelectListener = onFavouriteSelectListener;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        APODFavourite favourite = favourites.get(position);
        holder.title.setText(favourite.getTitle());
        holder.delete.setOnClickListener(v -> {
            onFavouriteSelectListener.onFavouriteSelect(favourite);
        });
    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }

    public static class FavViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView delete;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.fav_title);
            delete = itemView.findViewById(R.id.fav_delete);
        }
    }

    public interface OnFavouriteSelectListener {
        void onFavouriteSelect(APODFavourite favourite);
    }
}
