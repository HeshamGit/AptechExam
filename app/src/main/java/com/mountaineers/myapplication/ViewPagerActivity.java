package com.mountaineers.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.HashMap;
import java.util.Map;

public class ViewPagerActivity extends AppCompatActivity {
    //declare all the var
    private SamplePagerAdapter samplePagerAdapter;
    private TextView pageNum;
    private int totalPage, currentPage = 1;

    //All Images stored im integer array
    //csharp image and pdf
    private final Integer[] cSharpImg = {R.drawable.csharp1, R.drawable.csharp2, R.drawable.csharp3,
            R.drawable.csharp4, R.drawable.csharp5, R.drawable.csharp6, R.drawable.csharp7,
            R.drawable.csharp8, R.drawable.csharp9, R.drawable.csharp10, R.drawable.csharp11,
            R.drawable.csharp12, R.drawable.csharp13, R.drawable.csharp14, R.drawable.csharp15,
            R.drawable.csharp16, R.drawable.csharp17, R.drawable.csharp18, R.drawable.csharp19,
            R.drawable.csharp20, R.drawable.csharp21, R.drawable.csharp22, R.drawable.csharp23,
            R.drawable.csharp24, R.drawable.csharp25, R.drawable.csharp26, R.drawable.csharp27,
            R.drawable.csharp28, R.drawable.csharp29, R.drawable.csharp30};

    private final Integer[] cSharpPdf = {R.drawable.csharp_page_1, R.drawable.csharp_page_2, R.drawable.csharp_page_3,
            R.drawable.csharp_page_4, R.drawable.csharp_page_5, R.drawable.csharp_page_6
    };
    //seo pdf
    private final Integer[] SeoPdf = {R.drawable.seo_page_1, R.drawable.seo_page_2, R.drawable.seo_page_3,
            R.drawable.seo_page_4, R.drawable.seo_page_5, R.drawable.seo_page_6
    };
    //linux image and pdf
    private final Integer[] linuxImg = {
            R.drawable.red_hat_linux1, R.drawable.red_hat_linux2, R.drawable.red_hat_linux3,
            R.drawable.red_hat_linux4, R.drawable.red_hat_linux5, R.drawable.red_hat_linux6,
            R.drawable.red_hat_linux7, R.drawable.red_hat_linux8, R.drawable.red_hat_linux9,
            R.drawable.red_hat_linux10, R.drawable.red_hat_linux11, R.drawable.red_hat_linux12,
            R.drawable.red_hat_linux13, R.drawable.red_hat_linux14, R.drawable.red_hat_linux15,
            R.drawable.red_hat_linux16, R.drawable.red_hat_linux17, R.drawable.red_hat_linux18,
    };
    private final Integer[] linuxPdf = {R.drawable.linux_1, R.drawable.linux_2, R.drawable.linux_3,
            R.drawable.linux_4, R.drawable.linux_5
    };
    //java image
    private final Integer[] javaImg = {R.drawable.fun_of_java1, R.drawable.fun_of_java2, R.drawable.fun_of_java3, R.drawable.fun_of_java4,
            R.drawable.learn_java_by_eg1, R.drawable.learn_java_by_eg2, R.drawable.learn_java_by_eg3,
            R.drawable.learn_java_by_eg5, R.drawable.learn_java_by_eg7, R.drawable.learn_java_by_eg8,
            R.drawable.learn_java_by_eg9, R.drawable.learn_java_by_eg10, R.drawable.learn_java_by_eg13,
            R.drawable.learn_java_by_eg14, R.drawable.learn_java_by_eg15, R.drawable.learn_java_by_eg18,
            R.drawable.learn_java_by_eg19, R.drawable.learn_java_by_eg20, R.drawable.learn_java_by_eg21,
            R.drawable.learn_java_by_eg22, R.drawable.learn_java_by_eg23, R.drawable.learn_java_by_eg24,
            R.drawable.learn_java_by_eg25, R.drawable.learn_java_by_eg26, R.drawable.learn_java_by_eg27,
            R.drawable.learn_java_by_eg28
    };

    //semester four img
    //linux image and pdf
    private final Integer[] apacheImg = {
            R.drawable.apache1, R.drawable.apache2
    };
    //android app dev image and pdf
    private final Integer[] androidAppImg = {
            R.drawable.android_app1, R.drawable.android_app2, R.drawable.android_app3
    };

    // Define the map to associate caller names with image arrays
    private final Map<String, Integer[]> ImageMap = new HashMap<>();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        // Populate the ImageMap
        ImageMap.put("cSharpImg", cSharpImg);
        ImageMap.put("cSharpPdf", cSharpPdf);
        ImageMap.put("SeoPdf", SeoPdf);
        ImageMap.put("linuxImg", linuxImg);
        ImageMap.put("linuxPdf", linuxPdf);
        ImageMap.put("javaImg", javaImg);
        ImageMap.put("apacheImg", apacheImg);
        ImageMap.put("androidAppImg", androidAppImg);


        // Getting the caller activity name
        String caller = getIntent().getStringExtra("caller");

        // Find the image array based on the caller name in the map
        Integer[] imageArray = ImageMap.get(caller);

        // Create the adapter with the correct image array
        if (imageArray != null) {
            samplePagerAdapter = new SamplePagerAdapter(imageArray);
        } else {
            // Handle the case when the caller name is not found in the map
            // You can set a default image array or show an error message
            Toast.makeText(this, "there is no image to show", Toast.LENGTH_SHORT).show();
        }

        //casting
        ViewPager imageViewPager = findViewById(R.id.view_pager);
        pageNum = findViewById(R.id.page_num);

        if (samplePagerAdapter != null) {
            //set the view pager adapter
            imageViewPager.setAdapter(samplePagerAdapter);

            //get the totalPage
            totalPage = samplePagerAdapter.getCount();

            //to display the totalPage with current in screen
            pageNum.setText((currentPage + "/" + totalPage));
        }

        //to add the page num when the activity start
//        if (savedInstanceState == null) {
//            pageNum.setText((currentPage + "/" + totalPage));
//        }

        //adding on change Listener to update the page number
        imageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //get the currentPage
                currentPage = position + 1;
                //change the text
                pageNum.setText((currentPage + "/" + totalPage));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

}