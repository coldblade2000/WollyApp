package com.snk.wolly.wolly;

import android.app.ActivityOptions;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.transition.Fade;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.bumptech.glide.Glide;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class PickRecyclable extends AppCompatActivity {
    public static final String GOOGLE_ACCOUNT = "google_account";
    Toolbar toolbar;
    CircleImageView ivRecycleLogo,ivBottle, ivPaper, ivCap,ivTetrapak, ivGlassBottle, ivBaterias, ivBarrioMain, ivProfileMain;
    FrameLayout flBarrioProgressMain, flProfileProgressMain;
    RelativeLayout rvProfileMain, rvBarrioMain;
    Profile defaultProfile;
    Barrio defaultBarrio;
    TextView tvProfileLevel, tvBarrioLevel, tvBarrioNameMain, tvProfileNameMain;
    float dpWidth;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String id = "ivBaterias";
            switch (v.getId()){
                case R.id.ivBaterias:
                    id = "ivBaterias";
                    ivBaterias.setTransitionName("recyclable");
                    break;
                case R.id.ivBottle:
                    id = "ivBottle";
                    ivBottle.setTransitionName("recyclable");
                    break;
                case R.id.ivPaper:
                    id = "ivPaper";
                    ivPaper.setTransitionName("recyclable");
                    break;
                case R.id.ivCap:
                    id = "ivCap";
                    ivCap.setTransitionName("recyclable");
                    break;
                case R.id.ivTetrapak:
                    id = "ivTetrapak";
                    ivTetrapak.setTransitionName("recyclable");
                    break;
                case R.id.ivGlassBottle:
                    id = "ivGlassBottle";
                    ivGlassBottle.setTransitionName("recyclable");
                    break;
            }

            Intent intent = new Intent(PickRecyclable.this, AddRecycle.class);
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            intent.putExtras(bundle);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(PickRecyclable.this, v, "recyclable").toBundle());

        }
    };

    private void clickAdd(String id, View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setSharedElementExitTransition(new Fade());

        setContentView(R.layout.activity_pick_recyclable);
        Random rand = new Random();
        defaultBarrio = new Barrio(rand.nextInt(1000*100), "Barrio Santa Paula");
        defaultProfile = new Profile((rand.nextInt(1000*100)), "Jose Manuel Florez");

        // Apply activity transition


        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ivRecycleLogo = (CircleImageView) findViewById(R.id.ivRecycleLogo);

        ivBottle= (CircleImageView) findViewById(R.id.ivBottle);
        ivPaper = (CircleImageView) findViewById(R.id.ivPaper);
        ivCap = (CircleImageView) findViewById(R.id.ivCap);
        ivTetrapak = (CircleImageView) findViewById(R.id.ivTetrapak);
        ivGlassBottle = (CircleImageView) findViewById(R.id.ivGlassBottle);
        ivBaterias = (CircleImageView) findViewById(R.id.ivBaterias);

        int color = Color.parseColor("#4CAF50");
        ivBottle.setColorFilter(color);
        ivPaper.setColorFilter(color);
        ivCap.setColorFilter(color);
        ivTetrapak.setColorFilter(color);
        ivGlassBottle.setColorFilter(color);
        ivBaterias.setColorFilter(color);

        ivBarrioMain = (CircleImageView) findViewById(R.id.ivBarrioMain);
        ivProfileMain = (CircleImageView) findViewById(R.id.ivProfileMain);

        Glide.with(this)
                .load(R.mipmap.bottle)
                .into(ivBottle);
        Glide.with(this)
                .load(R.mipmap.paper)
                .into(ivPaper);
        Glide.with(this)
                .load(R.mipmap.cap)
                .into(ivCap);
        Glide.with(this)
                .load(R.mipmap.tetra)
                .into(ivTetrapak);
        Glide.with(this)
                .load(R.mipmap.glass)
                .into(ivGlassBottle);
        Glide.with(this)
                .load(R.mipmap.battery)
                .into(ivBaterias);



        flBarrioProgressMain = findViewById(R.id.flBarrioProgressMain);
        flProfileProgressMain = findViewById(R.id.flProfileProgressMain);

        rvBarrioMain = findViewById(R.id.rvBarrioMain);
        rvProfileMain = findViewById(R.id.rvProfileMain);

        tvProfileLevel = findViewById(R.id.tvProfileLevel);
        tvBarrioLevel = findViewById(R.id.tvBarrioLevel);
        tvProfileNameMain = findViewById(R.id.tvProfileNameMain);
        tvBarrioNameMain = findViewById(R.id.tvBarrioNameMain);

        tvProfileNameMain.setText(defaultProfile.getName());
        tvBarrioNameMain.setText(defaultBarrio.getName());
        tvProfileLevel.setText("LVL " + (defaultProfile.getPuntos()/1000));
        tvBarrioLevel.setText("LVL " + (defaultBarrio.getPuntos()/1000));

        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        dpWidth = displayMetrics.widthPixels;
        setMargins(flBarrioProgressMain, 0,0,(int)(defaultBarrio.getPuntos()%1000 *dpWidth/1000),0);
        setMargins(flProfileProgressMain, 0,0,(int)(defaultProfile.getPuntos()%1000 *dpWidth/1000),0);

        ivBottle.setOnClickListener(clickListener);
        ivPaper.setOnClickListener(clickListener);
        ivCap.setOnClickListener(clickListener);
        ivTetrapak.setOnClickListener(clickListener);
        ivGlassBottle.setOnClickListener(clickListener);
        ivBaterias.setOnClickListener(clickListener);


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setDataOnView() {
        //GoogleSignInAccount googleSignInAccount = getIntent().getParcelableExtra(GOOGLE_ACCOUNT);
        /*Picasso.get().load(googleSignInAccount.getPhotoUrl()).centerInside().fit().into(profileImage);
        profileName.setText(googleSignInAccount.getDisplayName());
        profileEmail.setText(googleSignInAccount.getEmail());*/
    }
    private void setMargins (View view, int left, int top, int right, int bottom) {
        /*if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }*/
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
        lp.setMargins(left, top, right, bottom);
        view.setLayoutParams(lp);
    }
}
