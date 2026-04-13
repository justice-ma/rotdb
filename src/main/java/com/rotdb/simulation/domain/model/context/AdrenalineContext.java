package com.rotdb.simulation.domain.model.context;

public class AdrenalineContext {
    private double adrenaline, maximumBound = 100, minimumBound = 0;
    private String message;

    public double getAdrenaline() {
        return adrenaline;
    }

    public void setAdrenaline(double adrenaline) {
        this.adrenaline = adrenaline;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getMaximumBound() {
        return maximumBound;
    }

    public void setMaximumBound(double maximumBound) {
        this.maximumBound = maximumBound;
    }

    public double getMinimumBound() {
        return minimumBound;
    }

    public void setMinimumBound(double minimumBound) {
        this.minimumBound = minimumBound;
    }

    public void addAdrenaline(double adrenaline) {
        setAdrenaline(this.getAdrenaline() + adrenaline);
    }
}
