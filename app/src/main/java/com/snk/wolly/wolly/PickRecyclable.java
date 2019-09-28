package com.snk.wolly.wolly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import de.hdodenhof.circleimageview.CircleImageView;

public class PickRecyclable extends AppCompatActivity {
    public static final String GOOGLE_ACCOUNT = "google_account";
    Toolbar toolbar;
    CircleImageView ivRecycleLogo,ivBottle, ivPaper, ivCap,ivTetrapak, ivGlassBottle, ivBaterias, ivBarrioMain, ivProfileMain;
    FrameLayout flBarrioProgressMain, flProfileProgressMain;
    RelativeLayout rvProfileMain, rvBarrioMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_recyclable);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ivRecycleLogo = (CircleImageView) findViewById(R.id.ivRecycleLogo);
        ivBottle= (CircleImageView) findViewById(R.id.ivBottle);
        ivPaper = (CircleImageView) findViewById(R.id.ivPaper);
        ivCap = (CircleImageView) findViewById(R.id.ivCap);
        ivTetrapak = (CircleImageView) findViewById(R.id.ivTetrapak);
        ivGlassBottle = (CircleImageView) findViewById(R.id.ivGlassBottle);
        ivBaterias = (CircleImageView) findViewById(R.id.ivBaterias);
        ivBarrioMain = (CircleImageView) findViewById(R.id.ivBarrioMain);
        ivProfileMain = (CircleImageView) findViewById(R.id.ivProfileMain);

        flBarrioProgressMain = findViewById(R.id.flBarrioProgressMain);
        flProfileProgressMain = findViewById(R.id.flProfileProgressMain);

        rvBarrioMain = findViewById(R.id.rvBarrioMain);
        rvProfileMain = findViewById(R.id.rvProfileMain);

        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels;



        setMargins(flBarrioProgressMain, 0,0,0,0);


    }
    private void setDataOnView() {
        GoogleSignInAccount googleSignInAccount = getIntent().getParcelableExtra(GOOGLE_ACCOUNT);
        /*Picasso.get().load(googleSignInAccount.getPhotoUrl()).centerInside().fit().into(profileImage);
        profileName.setText(googleSignInAccount.getDisplayName());
        profileEmail.setText(googleSignInAccount.getEmail());*/
    }
    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}
