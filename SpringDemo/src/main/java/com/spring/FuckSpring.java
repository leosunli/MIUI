package com.spring;

/**
 * Created by mi on 16-9-22.
 */
public class FuckSpring {
    private String fuck;

    public String getFuck() {
        return fuck;
    }

    public void setFuck(String fuck) {
        this.fuck = fuck;
    }

    public void show() {
        System.out.println("--message--" + getFuck());
    }
}
