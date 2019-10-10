package com.snk.wolly.wolly;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.transition.Explode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class PickRecyclable extends AppCompatActivity implements PickRecyclableClickListener{
    public static final int REQUEST_CODE = 1;
    public static final int UNIT_SUCCESS_RESULT = 1;
    public static final int KG_SUCCESS_RESULT= 2;
    Toolbar toolbar;
    CircleImageView ivProfileMain;
    FrameLayout flProfileProgressMain;
    RelativeLayout rvProfileMain;
    Profile defaultProfile;

    TextView tvProfileLevel, tvProfileNameMain;
    float dpWidth;
    RecyclerView rvCards;
    ImageView ivRecycleLogo;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Recyclable[] mDataset;

    //   curl --data "weightdelta=5.2" https://us-central1-wolly-1569687603282.cloudfunctions.net/receivekgdelta

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
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) flProfileProgressMain.getLayoutParams();
        lp.rightMargin = (int) dpWidth;
        flProfileProgressMain.setLayoutParams(lp);

        Recyclable bottle = new Recyclable(Recyclable.BOTTLE, R.mipmap.bottle, 5.0, 250);
        Recyclable paper = new Recyclable( Recyclable.PAPER, R.mipmap.paper,  1.0,  200);
        Recyclable cap = new Recyclable(Recyclable.CAP, R.mipmap.cap, 1.1, 220);
        Recyclable tetrapak = new Recyclable(Recyclable.TETRAPAK, R.mipmap.tetra, 5.0, 250);
        Recyclable glassBottle = new Recyclable(Recyclable.GLASSBOTTLE, R.mipmap.glass, 9.0, 27);
        Recyclable battery = new Recyclable(Recyclable.BATTERY, R.mipmap.battery, 6.0, 260);

        mDataset = new Recyclable[]{bottle, paper, cap, tetrapak, glassBottle, battery};



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = database.getReference("User");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                int oldPoints = 0;
                if(defaultProfile!=null) {
                    oldPoints = defaultProfile.getPuntos();

                    boolean levelIncreased = false;

                    defaultProfile = dataSnapshot.getValue(Profile.class);
                    SharedPrefs.setInt(getApplicationContext(), "puntos", defaultProfile.getPuntos());
                    SharedPrefs.setString(getApplicationContext(), "name", defaultProfile.getName());

                    if((oldPoints/1000) < (defaultProfile.getPuntos()/1000)) levelIncreased = true;

                    tvProfileNameMain.setText(defaultProfile.getName());
                    setMargins(flProfileProgressMain,defaultProfile.getPuntos() ,levelIncreased);
                    tvProfileLevel.setText("NVL " + (defaultProfile.getPuntos()/1000));

                    if(defaultProfile.getUnsetKG() != 0.0 ){
                        AlertDialog.Builder builder = new AlertDialog.Builder(PickRecyclable.this);
                        String title = String.format("Tienes %skg de reciclables sin categorizar!", defaultProfile.getUnsetKG());
                        builder.setTitle(title);
                        builder.setCancelable(false);
                        CharSequence[] recyclables = new CharSequence[mDataset.length];
                        for (int i=0; i<mDataset.length; i++){
                            recyclables[i] = mDataset[i].getName();
                        }
                        builder.setItems(recyclables, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                double oldPoints = defaultProfile.getPuntos();
                                double addedPoints = defaultProfile.categorizeUnsetKG(mDataset[which]);
                                Profile newProfile = new Profile((int)(addedPoints), defaultProfile.getName());
                                userRef.setValue(newProfile);
                                Toast.makeText(getApplicationContext(), "Ganaste +"+(addedPoints-oldPoints)+" puntos!", Toast.LENGTH_LONG).show();
                            }
                        });
                        builder.create();
                        builder.show();
                    }
                }else{

                    defaultProfile = new Profile(SharedPrefs.getInt(getApplicationContext(), "puntos"), SharedPrefs.getString(getApplicationContext(), "name"));
                    Log.d("we", ""+ defaultProfile.getPuntos() + "   "+defaultProfile.getName());
                    //userRef.setValue(defaultProfile);
                    //Log.d("Else", "CREATED DEFAULT PROFILE");
                    tvProfileNameMain.setText(defaultProfile.getName());
                    setMargins(flProfileProgressMain, defaultProfile.getPuntos(),false);

                    tvProfileLevel.setText("NVL " + (defaultProfile.getPuntos()/1000));
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("Escoge un reciclable:");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(toolbar);

        ivRecycleLogo = findViewById(R.id.ivRecycleLogo);
        Glide.with(this)
                .load(R.mipmap.logo)
                .into(ivRecycleLogo);



        rvCards = findViewById(R.id.rvCards);
        rvCards.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this, 1);
        rvCards.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecycleAdapter(getApplicationContext(), mDataset, (PickRecyclableClickListener) this);
        rvCards.setAdapter(mAdapter);

        ivProfileMain = (CircleImageView) findViewById(R.id.ivProfileMain);
        Glide.with(this)
                .load(R.mipmap.profile)
                .fitCenter()
                .into(ivProfileMain);
        rvProfileMain = findViewById(R.id.rvProfileMain);
        rvProfileMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(PickRecyclable.this);
                final EditText edittext = new EditText(PickRecyclable.this);
                alert.setTitle("Cambiar nombre de perfil:");
                edittext.setHint("Pepito");
                edittext.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                alert.setView(edittext);
                alert.setPositiveButton("Cambiar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(edittext.getText() != null && !edittext.getText().toString().isEmpty()){
                            DatabaseReference childName = userRef.child("name");
                            childName.setValue(edittext.getText().toString());
                            dialog.dismiss();
                        }else{
                            Toast.makeText(getApplicationContext(), "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });


        tvProfileLevel = findViewById(R.id.tvProfileLevel);
        tvProfileNameMain = findViewById(R.id.tvProfileNameMain);

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(defaultProfile != null){
            SharedPrefs.setInt(getApplicationContext(), "puntos", defaultProfile.getPuntos());
            SharedPrefs.setString(getApplicationContext(), "name", defaultProfile.getName());
        }else{
            SharedPrefs.setInt(getApplicationContext(), "puntos", 0);
            SharedPrefs.setString(getApplicationContext(), "name", "NULL");
        }
    }

    private void setMargins (final View view, int points, boolean didLevelIncrease) {

        final int newMargin = (int)((1000 - points%1000) *dpWidth/1000);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
        int oldMarginGlobal = lp.rightMargin;
        if(didLevelIncrease){
            final ValueAnimator animator = ValueAnimator.ofInt(oldMarginGlobal, 0);
            animator.setDuration(1000);
            animator.start();
            final ArgbEvaluator evaluator = new ArgbEvaluator();

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    RelativeLayout.LayoutParams lp2= (RelativeLayout.LayoutParams) view.getLayoutParams();
                    lp2.rightMargin = (int)animation.getAnimatedValue();
                    view.setLayoutParams(lp2);

                    view.setBackgroundColor((int)evaluator.evaluate(animation.getAnimatedFraction(), ContextCompat.getColor(getApplicationContext(), R.color.colorAccent),
                            ContextCompat.getColor(getApplicationContext(), R.color.colorGolden)));
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ValueAnimator animator2 = ValueAnimator.ofInt((int)dpWidth, newMargin);
                    animator2.setDuration(1000);
                    animator2.start();
                    animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            RelativeLayout.LayoutParams lp2= (RelativeLayout.LayoutParams) view.getLayoutParams();
                            lp2.rightMargin = (int)animation.getAnimatedValue();
                            view.setLayoutParams(lp2);
                            view.setBackgroundColor((int)evaluator.evaluate(animation.getAnimatedFraction(), ContextCompat.getColor(getApplicationContext(), R.color.colorGolden),
                                    ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
                        }
                    });
                }
            });

        }else {

            ValueAnimator animator = ValueAnimator.ofInt(oldMarginGlobal, newMargin);
            animator.setDuration(2000);
            animator.start();
            final ArgbEvaluator evaluator = new ArgbEvaluator();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    lp2.rightMargin = (int) animation.getAnimatedValue();
                    view.setLayoutParams(lp2);
                    float frac = 1F - Math.abs((2*animation.getAnimatedFraction())-1F);
                    view.setBackgroundColor((int)evaluator.evaluate(frac, ContextCompat.getColor(getApplicationContext(), R.color.colorAccent),
                            ContextCompat.getColor(getApplicationContext(), R.color.colorGolden)));
                }
            });
        }
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

        startActivityForResult(intent, REQUEST_CODE, options.toBundle());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == REQUEST_CODE){
                assert data != null;
                String name = data.getStringExtra("name");
                double puntos = 0;
                switch (resultCode){
                    case UNIT_SUCCESS_RESULT:
                        double scoreUnit = data.getDoubleExtra("scoreUnit", 0.0);
                        double unitCount = data.getDoubleExtra("unitCount", 0.0);
                         puntos = scoreUnit*unitCount;

                        Toast.makeText(getApplicationContext(), "Ganaste +"+puntos+" puntos!", Toast.LENGTH_SHORT).show();
                        break;
                    case KG_SUCCESS_RESULT:
                        double scoreKg = data.getDoubleExtra("scoreKg", 0.0);
                        double kgCount = data.getDoubleExtra("kgCount", 0.0);
                        puntos = scoreKg*kgCount;
                        Toast.makeText(getApplicationContext(), "Ganaste +"+puntos+" puntos!", Toast.LENGTH_SHORT).show();

                        break;
                }
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference userRef = database.getReference("User");
                final double finalPuntos = puntos;
                userRef.addListenerForSingleValueEvent(new ValueEventListener(){

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Profile tempProfile = dataSnapshot.getValue(Profile.class);
                        DatabaseReference puntosRef = userRef.child("puntos");
                        assert tempProfile != null;
                        puntosRef.setValue(tempProfile.getPuntos()+ finalPuntos);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        }catch (Exception e){
            Log.e("onAcitivityResult", e.getMessage());
        }
    }
}
