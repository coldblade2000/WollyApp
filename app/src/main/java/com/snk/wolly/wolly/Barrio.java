package com.snk.wolly.wolly;

public class Barrio {
    private int puntos;
    private String name;

    public Barrio(int puntos, String name){
        this.puntos = puntos;
        this.name = name;
    }

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
}
