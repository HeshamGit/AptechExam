package com.mountaineers.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerActivity extends AppCompatActivity {
    //declare all the var
    SamplePagerAdapter samplePagerAdapter;
    ViewPager viewPager;
    TextView pageNum;
    int totalPage, currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        //All Images stored im integer array
        //csharp image and pdf
        Integer[] cSharpImg = {R.drawable.csharp1, R.drawable.csharp2, R.drawable.csharp3,
                R.drawable.csharp4, R.drawable.csharp5, R.drawable.csharp6, R.drawable.csharp7,
                R.drawable.csharp8, R.drawable.csharp9, R.drawable.csharp10, R.drawable.csharp11,
                R.drawable.csharp12, R.drawable.csharp13, R.drawable.csharp14, R.drawable.csharp15,
                R.drawable.csharp16, R.drawable.csharp17, R.drawable.csharp18, R.drawable.csharp19,
                R.drawable.csharp20, R.drawable.csharp21, R.drawable.csharp22, R.drawable.csharp23,
                R.drawable.csharp24, R.drawable.csharp25, R.drawable.csharp26, R.drawable.csharp27,
                R.drawable.csharp28, R.drawable.csharp29, R.drawable.csharp30};

        Integer[] cSharpPdf = {R.drawable.csharp_page_1, R.drawable.csharp_page_2, R.drawable.csharp_page_3,
                R.drawable.csharp_page_4, R.drawable.csharp_page_5, R.drawable.csharp_page_6
        };
        //seo pdf
        Integer[] SeoPdf = {R.drawable.seo_page_1, R.drawable.seo_page_2, R.drawable.seo_page_3,
                R.drawable.seo_page_4, R.drawable.seo_page_5, R.drawable.seo_page_6
        };
        //linux image and pdf
        Integer[] linuxImg = {
                R.drawable.red_hat_linux1, R.drawable.red_hat_linux2, R.drawable.red_hat_linux3,
                R.drawable.red_hat_linux4, R.drawable.red_hat_linux5, R.drawable.red_hat_linux6,
                R.drawable.red_hat_linux7, R.drawable.red_hat_linux8, R.drawable.red_hat_linux9,
                R.drawable.red_hat_linux10, R.drawable.red_hat_linux11, R.drawable.red_hat_linux12,
                R.drawable.red_hat_linux13, R.drawable.red_hat_linux14, R.drawable.red_hat_linux15,
                R.drawable.red_hat_linux16, R.drawable.red_hat_linux17, R.drawable.red_hat_linux18,
        };
        Integer[] linuxPdf = {R.drawable.linux_1, R.drawable.linux_2, R.drawable.linux_3,
                R.drawable.linux_4, R.drawable.linux_5
        };
        //java image
        Integer[] javaImg = {R.drawable.fun_of_java1, R.drawable.fun_of_java2, R.drawable.fun_of_java3, R.drawable.fun_of_java4,
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
        Integer[] apacheImg = {
                R.drawable.apache1, R.drawable.apache2
        };
        //android app dev image and pdf
        Integer[] androidAppImg = {
                R.drawable.android_app1, R.drawable.android_app2, R.drawable.android_app3
        };


        //getting the caller activity name
        String caller = getIntent().getExtras().getString("caller");

        String sample = getIntent().getExtras().getString(caller);

        //open the image view depend on the chosen activity and sample
        switch (sample) {
            case "sample5":
                samplePagerAdapter = new SamplePagerAdapter(androidAppImg);
                break;
            case "sample6":
                if(caller.equals("javaImg"))
                    samplePagerAdapter = new SamplePagerAdapter(javaImg);
                else
                    samplePagerAdapter = new SamplePagerAdapter(cSharpImg);
                break;
            case "sample7":
                samplePagerAdapter = new SamplePagerAdapter(cSharpPdf);
                break;
            case "sample2":
                samplePagerAdapter = new SamplePagerAdapter(SeoPdf);
                break;
            case "sample12":
                samplePagerAdapter = new SamplePagerAdapter(linuxImg);
                break;
            case "sample13":
                if(caller.equals("apacheImg"))
                    samplePagerAdapter = new SamplePagerAdapter(apacheImg);
                else
                    samplePagerAdapter = new SamplePagerAdapter(linuxPdf);
                break;

        }

        //casting
        viewPager = findViewById(R.id.view_pager);
        pageNum = findViewById(R.id.page_num);

        //set the view pager adapter
        viewPager.setAdapter(samplePagerAdapter);

        //get the totalPage and the currentPage
        totalPage = samplePagerAdapter.getCount();
        currentPage = viewPager.getCurrentItem() + 1;

        //to add the page num when the activity start
        if (savedInstanceState == null) {
            pageNum.setText((currentPage + "/" + totalPage));
        }

        //adding on change Listener to update the page number
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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