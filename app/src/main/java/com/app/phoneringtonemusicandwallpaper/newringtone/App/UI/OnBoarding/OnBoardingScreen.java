package com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.OnBoarding;

import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.editor;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.prevStarted;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.sharedpreferences;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.DashboardScreen;
import com.app.phoneringtonemusicandwallpaper.newringtone.R;

public class OnBoardingScreen extends AppCompatActivity {


    public ImageView iv_auto_image_slider, dot_1, dot_2, dot_3;
    public TextView stepperTitle, stepperDescription, start_txt;
    public RelativeLayout back_btn, btn_next, btn_continue;
    public int sliderCounter;
    Boolean slider_activity;
    int pagePosition = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_on_boarding_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        findViewByID();

        sliderCounter = R.drawable.slider1;



        sharedpreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        iv_auto_image_slider.setImageResource(R.drawable.slider1);

        slider_activity = getIntent().getBooleanExtra("Slider", false);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pagePosition == 0) {
                    pagePosition = 1;
                    stepperTitle.setText("Enhance Your Device \n With Premium Content");
                    stepperDescription.setText("Express Yourself with our \n Exclusive Collection");
                    start_txt.setText("Continue");

                    back_btn.setVisibility(View.GONE);
                    btn_next.setVisibility(View.GONE);

                    dot_1.setBackground(ContextCompat.getDrawable(OnBoardingScreen.this, R.drawable.wight_btn_bg));
                    dot_2.setBackground(ContextCompat.getDrawable(OnBoardingScreen.this, R.drawable.gray_btn_bg));
                    dot_3.setBackground(ContextCompat.getDrawable(OnBoardingScreen.this, R.drawable.gray_btn_bg));
                } else if (pagePosition == 1) {
                    pagePosition = 2;
                    stepperTitle.setText("Get Ready for a \n Customized Experience");
                    stepperDescription.setText("Elevate Your Phone's Style with \n Unique Wallpapers");
                    start_txt.setText("Continue");

                    btn_next.setVisibility(View.GONE);
                    back_btn.setVisibility(View.VISIBLE);

                    dot_1.setBackground(ContextCompat.getDrawable(OnBoardingScreen.this, R.drawable.gray_btn_bg));
                    dot_2.setBackground(ContextCompat.getDrawable(OnBoardingScreen.this, R.drawable.wight_btn_bg));
                    dot_3.setBackground(ContextCompat.getDrawable(OnBoardingScreen.this, R.drawable.gray_btn_bg));
                } else if (pagePosition == 2) {
                    pagePosition = 3;
                    stepperTitle.setText("Unlock the power \n of Personalization");
                    stepperDescription.setText("Tailor-Made Ringtones and \n Wallpapers Await You");
                    start_txt.setText("Start Now");

                    btn_next.setVisibility(View.VISIBLE);
                    back_btn.setVisibility(View.VISIBLE);

                    dot_1.setBackground(ContextCompat.getDrawable(OnBoardingScreen.this, R.drawable.gray_btn_bg));
                    dot_2.setBackground(ContextCompat.getDrawable(OnBoardingScreen.this, R.drawable.gray_btn_bg));
                    dot_3.setBackground(ContextCompat.getDrawable(OnBoardingScreen.this, R.drawable.wight_btn_bg));
                } else if (pagePosition == 3) {
                    Intent intent = new Intent(OnBoardingScreen.this, DashboardScreen.class);
                    startActivity(intent);

                    editor = sharedpreferences.edit();
                    editor.putBoolean(prevStarted, true);
                    editor.apply();
                }
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pagePosition == 2) {
                    pagePosition = 1;
                    stepperTitle.setText("Enhance Your Device \n With Premium Content");
                    stepperDescription.setText("Express Yourself with our \n Exclusive Collection");
                    start_txt.setText("Continue");

                    back_btn.setVisibility(View.GONE);
                    btn_next.setVisibility(View.GONE);

                    dot_1.setBackground(ContextCompat.getDrawable(OnBoardingScreen.this, R.drawable.wight_btn_bg));
                    dot_2.setBackground(ContextCompat.getDrawable(OnBoardingScreen.this, R.drawable.gray_btn_bg));
                    dot_3.setBackground(ContextCompat.getDrawable(OnBoardingScreen.this, R.drawable.gray_btn_bg));
                } else if (pagePosition == 3) {
                    pagePosition = 2;
                    stepperTitle.setText("Get Ready for a \n Customized Experience");
                    stepperDescription.setText("Elevate Your Phone's Style with \n Unique Wallpapers");
                    start_txt.setText("Continue");

                    btn_next.setVisibility(View.GONE);
                    back_btn.setVisibility(View.VISIBLE);

                    dot_1.setBackground(ContextCompat.getDrawable(OnBoardingScreen.this, R.drawable.gray_btn_bg));
                    dot_2.setBackground(ContextCompat.getDrawable(OnBoardingScreen.this, R.drawable.wight_btn_bg));
                    dot_3.setBackground(ContextCompat.getDrawable(OnBoardingScreen.this, R.drawable.gray_btn_bg));
                }
            }
        });

    }

    private void findViewByID() {
        slider_activity = getIntent().getBooleanExtra("Slider", false);
        iv_auto_image_slider = (ImageView) findViewById(R.id.iv_auto_image_slider);
        back_btn = (RelativeLayout) findViewById(R.id.back_btn);
        btn_next = (RelativeLayout) findViewById(R.id.btn_next);
        btn_continue = (RelativeLayout) findViewById(R.id.btn_continue);
        stepperTitle = (TextView) findViewById(R.id.stepperTitle);
        start_txt = (TextView) findViewById(R.id.start_btn);
        stepperDescription = (TextView) findViewById(R.id.stepperDescription);
        dot_1 = (ImageView) findViewById(R.id.dot_1);
        dot_2 = (ImageView) findViewById(R.id.dot_2);
        dot_3 = (ImageView) findViewById(R.id.dot_3);
    }
}