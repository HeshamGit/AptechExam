package com.mountaineers.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private final Integer[] images;
    private final LayoutInflater inflater;

    public GalleryAdapter(Context context, Integer[] images) {
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_gallery_image, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
//        int imageResId = images.get(position);
        int imageResId = images[position];

        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache the original image without compression
                .override(Target.SIZE_ORIGINAL); // Load the original image size

        Glide.with(holder.itemView)
                .load(imageResId)
                .apply(requestOptions)
                .preload(); // Preload the image for smoother navigation

        Glide.with(holder.itemView)
                .load(imageResId)
                .apply(requestOptions)
                .into(holder.photoView);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    static class GalleryViewHolder extends RecyclerView.ViewHolder {
        PhotoView photoView;

        GalleryViewHolder(View itemView) {
            super(itemView);
            photoView = itemView.findViewById(R.id.photoView);
        }
    }
}
