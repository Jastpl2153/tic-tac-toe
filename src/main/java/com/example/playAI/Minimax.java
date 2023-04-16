package com.example.playAI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Minimax {
    private final int firstStep;
    private final String choice;

    public Minimax(int firstStep, String choice) {
        this.firstStep = firstStep;
        this.choice = choice;
    }

    public int minimax(State state) {
        if (isFirstStepAI(state)){
            return -2;
        }

        ArrayList<State> possibleMoves = generatePossibleMoves(state);
        ArrayList<Integer> movesList = getMinValues(possibleMoves);

        if (getBestIndex(movesList) == -1) {
            return -1;
        } else {
            return possibleMoves.get(getBestIndex(movesList)).getPosition();
        }
    }

    private boolean isFirstStepAI(State state){
        byte count = (byte) Arrays.stream(state.getState())
                .filter(String::isEmpty)
                .count();
        return count == 9 || count == 8;
    }

    private ArrayList<State> generatePossibleMoves(State state) {
        ArrayList<State> possibleMoves = new ArrayList<>();
        String player = getPlayer(state);

        for (int i = 0; i < 9; i++) {
            if (isValid(state, i)) {
                String[] newState = state.getState().clone();
                newState[i] = player;
                possibleMoves.add(new State(i, newState));
            }
        }
        return possibleMoves;
    }

    private String getPlayer(State state){
        if (firstStep == 0) {
            return calculatePlayerTurnOne(state, choice);
        } else {
            return calculatePlayerTurnTwo(state, choice);
        }
    }

    private boolean isValid(State state, int index) {
        return !state.getStateIndex(index).equals("X") && !state.getStateIndex(index).equals("O");
    }

    private String calculatePlayerTurnOne(State state, String aiChoice) {
        int xMoves = Collections.frequency(List.of(state.getState()), "X");
        int oMoves = Collections.frequency(List.of(state.getState()), "O");
        return (aiChoice.equals("X") && xMoves <= oMoves)
                || (aiChoice.equals("O") && oMoves <= xMoves)
                ? aiChoice
                : (aiChoice.equals("X") ? "O" : "X");
    }

    private String calculatePlayerTurnTwo(State state, String choice) {
        int xMoves = Collections.frequency(List.of(state.getState()), "X");
        int oMoves = Collections.frequency(List.of(state.getState()), "O");
        return (choice.equals("X") && xMoves < oMoves)
                || (choice.equals("O") && oMoves < xMoves)
                ? choice
                : (choice.equals("X") ? "O" : "X");
    }

    private ArrayList<Integer> getMinValues(ArrayList<State> possibleMoves) {
        ArrayList<Integer> movesList = new ArrayList<>();
        for (State state : possibleMoves) {
            movesList.add(minValue(state));
        }
        return movesList;
    }

    private int minValue(State state) {
        if (isGameOver(state)) {
            return checkWinningState(state);
        }
        return generatePossibleMoves(state).stream()
                .mapToInt(this::maxValue)
                .min()
                .orElseThrow();
    }

    private int maxValue(State state) {
        if (isGameOver(state)) {
            return checkWinningState(state);
        }
        return generatePossibleMoves(state).stream()
                .mapToInt(this::minValue)
                .max()
                .orElse((int) -Double.POSITIVE_INFINITY);
    }

    private boolean isGameOver(State state) {
        return hasWinner(state) || getFilledCells(state);
    }

    private boolean hasWinner(State state) {
        for (int i = 0; i < 9; i++) {
            String line = checkState(state, i);
            if (line.equals("XXX") || line.equals("OOO")) {
                return true;
            }
        }
        return false;
    }

    private boolean getFilledCells(State state) {
        return Arrays.stream(state.getState()).noneMatch(s -> s.equals(""));
    }

    private String checkState(State state, int a) {
        return switch (a) {
            case 0 -> state.getStateIndex(0) + state.getStateIndex(1) + state.getStateIndex(2);
            case 1 -> state.getStateIndex(3) + state.getStateIndex(4) + state.getStateIndex(5);
            case 2 -> state.getStateIndex(6) + state.getStateIndex(7) + state.getStateIndex(8);
            case 3 -> state.getStateIndex(0) + state.getStateIndex(3) + state.getStateIndex(6);
            case 4 -> state.getStateIndex(1) + state.getStateIndex(4) + state.getStateIndex(7);
            case 5 -> state.getStateIndex(2) + state.getStateIndex(5) + state.getStateIndex(8);
            case 6 -> state.getStateIndex(0) + state.getStateIndex(4) + state.getStateIndex(8);
            case 7 -> state.getStateIndex(2) + state.getStateIndex(4) + state.getStateIndex(6);
            default -> "";
        };
    }

    private int checkWinningState(State state) {
        for (int i = 0; i < 8; i++) {
            String line = checkState(state, i);
            if (line.equals("XXX")) {
                return choice.equals("X") ? 1 : -1;
            } else if (line.equals("OOO")) {
                return choice.equals("O") ? 1 : -1;
            }
        }
        return 0;
    }

    private int getBestIndex(ArrayList<Integer> movesList) {
        return IntStream.range(0, movesList.size())
                .reduce((a, b) -> movesList.get(a) > movesList.get(b) ? a : b)
                .orElse(-1);
    }
}
