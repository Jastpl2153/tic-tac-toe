package com.example.playAI;

public class State {
    private int position;
    private String[] state;

    public State(int position, String[] state) {
        this.position = position;
        this.state = state;
    }

    public int getPosition() {
        return position;
    }

    public String getStateIndex(int i) {
        return state[i];
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String[] getState() {
        return state;
    }

    public void setState(String[] state) {
        this.state = state;
    }
}
