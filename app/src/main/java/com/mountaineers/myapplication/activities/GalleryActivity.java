package com.mountaineers.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.mountaineers.myapplication.R;
import com.mountaineers.myapplication.adapters.GalleryAdapter;

import java.util.HashMap;
import java.util.Map;

public class GalleryActivity extends AppCompatActivity {

    private GalleryAdapter galleryAdapter;
    private TextView pageNum;
    Integer[] imageArray;

    //All Images stored in an integer array

    //semester one img
    private final Integer[] HTML5Img = {
            R.drawable.html1, R.drawable.html2
    };
    private final Integer[] elementaryCImg = {
            R.drawable.elementary_c1, R.drawable.elementary_c2, R.drawable.elementary_c3
    };
    private final Integer[] XMLImg = {
            R.drawable.xml1
    };
    private final Integer[] SQLServerImg = {
            R.drawable.sql_server1, R.drawable.sql_server2, R.drawable.sql_server3
    };

    //semester two img
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
    // oop in java image
    private final Integer[] oopJavaImg = {
            R.drawable.oop_in_java1, R.drawable.oop_in_java2, R.drawable.oop_in_java3,
            R.drawable.oop_in_java4, R.drawable.oop_in_java5, R.drawable.oop_in_java6,
            R.drawable.oop_in_java7, R.drawable.oop_in_java8, R.drawable.oop_in_java9,
            R.drawable.oop_in_java10, R.drawable.oop_in_java11, R.drawable.oop_in_java12,
            R.drawable.oop_in_java13, R.drawable.oop_in_java14, R.drawable.oop_in_java15,
            R.drawable.oop_in_java16, R.drawable.oop_in_java17, R.drawable.oop_in_java18,
            R.drawable.oop_in_java19, R.drawable.oop_in_java20, R.drawable.oop_in_java21,
            R.drawable.oop_in_java22
    };

    //semester three img
    private final Integer[] WebComponentPdf = {R.drawable.web_component_development1, R.drawable.web_component_development2, R.drawable.web_component_development3,
            R.drawable.web_component_development4, R.drawable.web_component_development5, R.drawable.web_component_development6
    };

    private final Integer[] FundJavaEnterprisePdf = {R.drawable.intro_to_web_interprise1, R.drawable.intro_to_web_interprise2, R.drawable.intro_to_web_interprise3,
            R.drawable.intro_to_web_interprise4, R.drawable.intro_to_web_interprise5, R.drawable.intro_to_web_interprise6
    };

    private final Integer[] DevAppJavaPdf = {R.drawable.java_web_frameworks1, R.drawable.java_web_frameworks2, R.drawable.java_web_frameworks3,
            R.drawable.java_web_frameworks4, R.drawable.java_web_frameworks5, R.drawable.java_web_frameworks6, R.drawable.java_web_frameworks7
    };

    private final Integer[] AgilePdf = {R.drawable.agile1, R.drawable.agile2, R.drawable.agile3,
            R.drawable.agile4, R.drawable.agile5
    };


    //semester four img
    private final Integer[] apacheImg = {
            R.drawable.apache1, R.drawable.apache2
    };
    //android app dev image and pdf
    private final Integer[] androidAppImg = {
            R.drawable.android_app1, R.drawable.android_app2, R.drawable.android_app3
    };


    // Define the map to associate caller names with image arrays
    private final Map<String, Integer[]> ImageMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // Populate the ImageMap
        // semester one
        ImageMap.put("elementaryCImg", elementaryCImg);
        ImageMap.put("HTML5Img", HTML5Img);
        ImageMap.put("SQLServerImg", SQLServerImg);
        ImageMap.put("XMLImg", XMLImg);

        // semester two
        ImageMap.put("cSharpImg", cSharpImg);
        ImageMap.put("cSharpPdf", cSharpPdf);
        ImageMap.put("SeoPdf", SeoPdf);
        ImageMap.put("linuxImg", linuxImg);
        ImageMap.put("linuxPdf", linuxPdf);
        ImageMap.put("javaImg", javaImg);
        ImageMap.put("oopJavaImg", oopJavaImg);

        // semester three
        ImageMap.put("AgilePdf", AgilePdf);
        ImageMap.put("DevAppJavaPdf", DevAppJavaPdf);
        ImageMap.put("FundJavaEnterprisePdf", FundJavaEnterprisePdf);
        ImageMap.put("WebComponentPdf", WebComponentPdf);

        // semester four
        ImageMap.put("apacheImg", apacheImg);
        ImageMap.put("androidAppImg", androidAppImg);


        // Getting the caller activity name
        String caller = getIntent().getStringExtra("caller");

        // Find the image array based on the caller name in the map
        imageArray = ImageMap.get(caller);

        // Create the adapter with the correct image array
        if (imageArray != null) {
            galleryAdapter = new GalleryAdapter(this, imageArray);
        } else {
            // Handle the case when the caller name is not found in the map
            // You can set a default image array or show an error message
            Toast.makeText(this, "there is no image to show", Toast.LENGTH_SHORT).show();
        }

        //casting
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        pageNum = findViewById(R.id.page_num);

        if (galleryAdapter != null) {
            //set the view pager adapter
            viewPager.setAdapter(galleryAdapter);

            // Set the initial page number
            setCurrentPageNumber(0);
        }

        // Optional: Enable offscreen page limit to preload adjacent images (optional but recommended)
        viewPager.setOffscreenPageLimit(1);

        // Listen to page changes and update the current page number accordingly
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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
        int totalPage = imageArray.length;
        // or
        // galleryAdapter.getItemCount();

        // Update the TextViews with the current page and total pages
        pageNum.setText((currentPage + "/" + totalPage));

    }
}