package com.mountaineers.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.mountaineers.myapplication.adapters.GalleryAdapter;
import com.mountaineers.myapplication.databinding.ActivityGalleryBinding;


public class GalleryActivity extends AppCompatActivity {

    private TextView pageNum;

    // to store the image drawable resources
    private TypedArray imageArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGalleryBinding binding = ActivityGalleryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrieve the resourceArrayId from the intent passed by SampleExamActivity
        int resourceArrayId = Integer.parseInt(getIntent().getStringExtra("caller"));

        // Use obtainTypedArray() to get the image resource IDs from the resource array
        imageArray = getResources().obtainTypedArray(resourceArrayId);

        // Check if the resource array is valid and contains images
        if (imageArray == null || imageArray.length() == 0) {
            Toast.makeText(this, "There are no images to show", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Create the GalleryAdapter with the image array
        GalleryAdapter galleryAdapter = new GalleryAdapter(this, imageArray);

        //set the view pager adapter
        binding.viewPager.setAdapter(galleryAdapter);

        // Optional: Enable offscreen page limit to preload adjacent images (optional but recommended)
        binding.viewPager.setOffscreenPageLimit(1);


        // Set the initial page number
        pageNum = binding.pageNum;
        setCurrentPageNumber(0);

        // Listen to page changes and update the current page number accordingly
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setCurrentPageNumber(position);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentPageNumber(int position) {
        //get the currentPage and totalPage
        int currentPage = position + 1;
        int totalPage = imageArray.length();

        // Update the TextViews with the current page and total pages
        pageNum.setText((currentPage + "/" + totalPage));

    }
}