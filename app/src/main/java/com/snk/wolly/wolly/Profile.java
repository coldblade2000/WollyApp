package com.snk.wolly.wolly;

public class Profile {
    private int puntos;
    private String name;
    private double unsetKG;

    public Profile(int puntos, String name){
        this.puntos = puntos;
        this.name = name;
        this.unsetKG = 0;
    }
    public Profile(){}

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnsetKG() {
        return unsetKG;
    }

    public boolean addPoints(Double quantity, Recyclable recyclable ,boolean isWeighted){
        if (isWeighted){
            puntos += quantity*recyclable.getScorePerKg();
        }else{
            puntos += quantity*recyclable.getScorePerUnit();
        }
        return true;
    }

    public double categorizeUnsetKG(Recyclable recyclable){
        double addedPoints = unsetKG*recyclable.getScorePerKg();
        unsetKG = 0;
        return (puntos + addedPoints);
    }

    public void setUnsetKG(double unsetKG) {
        this.unsetKG = unsetKG;
    }
}
