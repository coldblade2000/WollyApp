package com.snk.wolly.wolly;

import androidx.annotation.Nullable;

public class Recyclable {

    private int image;
    private int type;
    private boolean isWeighable;
    private double scorePerUnit;
    private double scorePerKg;
    public static final int BOTTLE = 0;
    public static final int PAPER = 1;
    public static final int CAP = 2;
    public static final int TETRAPAK= 3;
    public static final int GLASSBOTTLE = 4;
    public static final int BATTERY = 5;
    private String[] names = {"Plastic Bottle" ,"Paper", "Plastic Cap", "TetraPak", "Glass Bottle", "Battery"};



    public Recyclable(int type, int image, double scorePerUnit, @Nullable double scorePerKg){
        this.type = type;
        this.image=image;
        this.isWeighable = true;
        this.scorePerUnit = scorePerUnit;
        this.scorePerKg = scorePerKg;
    }
    public Recyclable(int type, int image, double scorePerUnit){
        this.type = type;
        this.image=image;
        this.isWeighable = false;
        this.scorePerUnit = scorePerUnit;
        this.scorePerKg = -1.0;
    }

    public String getName(){
        return names[type];
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isWeighable() {
        return isWeighable;
    }

    public void setWeighable(boolean weighable) {
        isWeighable = weighable;
    }

    public double getScorePerUnit() {
        return scorePerUnit;
    }

    public void setScorePerUnit(double scorePerUnit) {
        this.scorePerUnit = scorePerUnit;
    }

    public double getScorePerKg() {
        return scorePerKg;
    }

    public void setScorePerKg(double scorePerKg) {
        this.scorePerKg = scorePerKg;
    }
}
