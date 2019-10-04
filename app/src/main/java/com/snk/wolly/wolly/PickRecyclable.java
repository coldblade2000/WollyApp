package com.snk.wolly.wolly;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.provider.ContactsContract;
import android.transition.Explode;
import android.transition.Fade;
import android.util.DisplayMetrics;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class PickRecyclable extends AppCompatActivity implements PickRecyclableClickListener {
    public static final String GOOGLE_ACCOUNT = "google_account";
    Toolbar toolbar;
    CircleImageView ivBottle, ivPaper, ivCap,ivTetrapak, ivGlassBottle, ivBaterias, ivBarrioMain, ivProfileMain;
    FrameLayout flBarrioProgressMain, flProfileProgressMain;
    RelativeLayout rvProfileMain, rvBarrioMain;
    Profile defaultProfile;
    Barrio defaultBarrio;
    TextView tvProfileLevel, tvBarrioLevel, tvBarrioNameMain, tvProfileNameMain;
    float dpWidth;
    RecyclerView rvCards;
    ImageView ivRecycleLogo;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setSharedElementExitTransition(new Explode());

        setContentView(R.layout.activity_pick_recyclable);
//        Random rand = new Random();
        flProfileProgressMain = findViewById(R.id.flProfileProgressMain);
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        dpWidth = displayMetrics.widthPixels;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = database.getReference("User");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int oldPoints = 0;
                if(defaultProfile!=null) {
                    oldPoints = defaultProfile.getPuntos();
                }
                boolean levelIncreased = false;

                defaultProfile = dataSnapshot.getValue(Profile.class);
                if (defaultProfile != null) {

                    if((oldPoints/1000) < (defaultProfile.getPuntos()/1000)) levelIncreased = true;
                    tvProfileNameMain.setText(defaultProfile.getName());
                    setMargins(flProfileProgressMain,defaultProfile.getPuntos() ,levelIncreased);
                    tvProfileLevel.setText("LVL " + (defaultProfile.getPuntos()/1000));
                }else{

                    defaultProfile = new Profile(0, "Pepito");
                    userRef.setValue(defaultProfile);
                    tvProfileNameMain.setText(defaultProfile.getName());
                    setMargins(flProfileProgressMain, 0,false);

                    tvProfileLevel.setText("LVL " + (defaultProfile.getPuntos()/1000));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                /*Toast.makeText(PickRecyclable.this, "onCancelledFirebase", Toast.LENGTH_SHORT).show();
                defaultProfile = new Profile(0, "Pepito");
                userRef.setValue(defaultProfile);
                tvProfileNameMain.setText(defaultProfile.getName());
                setMargins(flProfileProgressMain, 0,0,(int)((1000 - defaultProfile.getPuntos()%1000) *dpWidth/1000),0);

                tvProfileLevel.setText("LVL " + (defaultProfile.getPuntos()/1000));*/
            }
        });

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Pick a recyclable");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(toolbar);

        ivRecycleLogo = findViewById(R.id.ivRecycleLogo);
        Glide.with(this)
                .load(R.mipmap.recycle)
                .into(ivRecycleLogo);

        Recyclable bottle = new Recyclable(Recyclable.BOTTLE, R.mipmap.bottle, 5.0);
        Recyclable paper = new Recyclable( Recyclable.PAPER, R.mipmap.paper,  1.0,  200);
        Recyclable cap = new Recyclable(Recyclable.CAP, R.mipmap.cap, 1.1, 220);
        Recyclable tetrapak = new Recyclable(Recyclable.TETRAPAK, R.mipmap.tetra, 5.0);
        Recyclable glassBottle = new Recyclable(Recyclable.GLASSBOTTLE, R.mipmap.glass, 9.0);
        Recyclable battery = new Recyclable(Recyclable.BATTERY, R.mipmap.battery, 6.0);

        rvCards = findViewById(R.id.rvCards);
        rvCards.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this, 1);
        rvCards.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        Recyclable[] mDataset = {bottle, paper, cap, tetrapak, glassBottle, battery};
        mAdapter = new RecycleAdapter(getApplicationContext(), mDataset, (PickRecyclableClickListener) this);
        rvCards.setAdapter(mAdapter);


        ivBarrioMain = (CircleImageView) findViewById(R.id.ivBarrioMain);
        ivProfileMain = (CircleImageView) findViewById(R.id.ivProfileMain);

//        Glide.with(this)
//                .load(R.mipmap.bottle)
//                .into(ivBottle);
//        Glide.with(this)
//                .load(R.mipmap.paper)
//                .into(ivPaper);
//        Glide.with(this)
//                .load(R.mipmap.cap)
//                .into(ivCap);
//        Glide.with(this)
//                .load(R.mipmap.tetra)
//                .into(ivTetrapak);
//        Glide.with(this)
//                .load(R.mipmap.glass)
//                .into(ivGlassBottle);
//        Glide.with(this)
//                .load(R.mipmap.battery)
//                .into(ivBaterias);



//        flBarrioProgressMain = findViewById(R.id.flBarrioProgressMain);


//        rvBarrioMain = findViewById(R.id.rvBarrioMain);
        rvProfileMain = findViewById(R.id.rvProfileMain);

        tvProfileLevel = findViewById(R.id.tvProfileLevel);
//        tvBarrioLevel = findViewById(R.id.tvBarrioLevel);
        tvProfileNameMain = findViewById(R.id.tvProfileNameMain);
//        tvBarrioNameMain = findViewById(R.id.tvBarrioNameMain);

        /*tvProfileNameMain.setText(defaultProfile.getName());
//        tvBarrioNameMain.setText(defaultBarrio.getName());
        tvProfileLevel.setText("LVL " + (defaultProfile.getPuntos()/1000));*/
//        tvBarrioLevel.setText("LVL " + (defaultBarrio.getPuntos()/1000));


        /*ivBottle.setOnClickListener(clickListener);
        ivPaper.setOnClickListener(clickListener);
        ivCap.setOnClickListener(clickListener);
        ivTetrapak.setOnClickListener(clickListener);
        ivGlassBottle.setOnClickListener(clickListener);
        ivBaterias.setOnClickListener(clickListener);*/


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
    private void setMargins (final View view, int points, boolean didLevelIncrease) {
        /*if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }*///1000 - defaultProfile.getPuntos()%1000) *dpWidth/1000)
        final int newMargin = (int)((1000 - points%1000) *dpWidth/1000);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
        int oldMarginGlobal = lp.rightMargin;
        if(didLevelIncrease){
            ValueAnimator animator = ValueAnimator.ofInt(oldMarginGlobal, 0);
            animator.setDuration(1000);
            animator.start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    RelativeLayout.LayoutParams lp2= (RelativeLayout.LayoutParams) view.getLayoutParams();
                    lp2.rightMargin = (int)animation.getAnimatedValue();
                    view.setLayoutParams(lp2);
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ValueAnimator animator = ValueAnimator.ofInt((int)dpWidth, newMargin);
                    animator.setDuration(1000);
                    animator.start();
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            RelativeLayout.LayoutParams lp2= (RelativeLayout.LayoutParams) view.getLayoutParams();
                            lp2.rightMargin = (int)animation.getAnimatedValue();
                            view.setLayoutParams(lp2);
                        }
                    });
                }
            });

            /*Animation b = new Animation(){
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    lp.rightMargin = oldMarginGlobal - (int)(oldMarginGlobal / interpolatedTime);
                    //lp.setMargins(0, 0, right, bottom);
                    view.setLayoutParams(lp);
                }

            };
            b.setDuration(1000);
            view.startAnimation(b);*/
            oldMarginGlobal = 0;
        }else {

            ValueAnimator animator = ValueAnimator.ofInt(oldMarginGlobal, newMargin);
            animator.setDuration(1000);
            animator.start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    lp2.rightMargin = (int) animation.getAnimatedValue();
                    view.setLayoutParams(lp2);
                }
            });
        }
        /*Animation a = new Animation(){
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
                int oldMargin = lp.rightMargin;
                lp.rightMargin = (int)(newMargin / interpolatedTime);
                //lp.setMargins(0, 0, right, bottom);
                view.setLayoutParams(lp);
            }

        };
        a.setDuration(1000);
        view.startAnimation(a);*/
    }


    @Override
    public void onItemClick(int position, ImageView imageView, TextView textView, Recyclable recyclable) {
        Intent intent = new Intent(this, AddRecycle.class);
        intent.putExtra("position", position);
        intent.putExtra("name", recyclable.getName());
        intent.putExtra("image", recyclable.getImage());
        intent.putExtra("scoreKG", recyclable.getScorePerKg());
        intent.putExtra("scoreUnit", recyclable.getScorePerUnit());
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this,
                        Pair.create((View)imageView, "recycLogo"),
                        Pair.create((View)textView, "recycName") );

        startActivityForResult(intent, 1, options.toBundle());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == 1 && resultCode == 1){
                //TODO add resuult logic
            }
        }catch (Exception e){
            Log.e("onAcitivityResult", e.getMessage());
        }
    }
}
