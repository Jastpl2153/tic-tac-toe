package com.example.playAI;
import java.util.*;
import java.util.stream.IntStream;

public class Minimax {
    private final int step;
    private final String aiChoice;

    public Minimax(int step, String aiChoice) {
        this.step = step;
        this.aiChoice = aiChoice;
    }

    public int minimax(State state) {
        ArrayList<State> possibleMoves = generatePossibleMoves(state);
        ArrayList<Integer> movesList = new ArrayList<>();

        if (isFirstStepAI(state)){
            return -2;
        }

        for (State states : possibleMoves) {
            movesList.add(minValue(states));
        }

        if (getBestIndex(movesList) == -1) {
            return -1;
        } else {
            return possibleMoves.get(getBestIndex(movesList)).getPosition();
        }
    }

    private int getBestIndex(ArrayList<Integer> movesList) {
        return IntStream.range(0, movesList.size())
                .reduce((a, b) -> movesList.get(a) > movesList.get(b) ? a : b)
                .orElse(-1);
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

    private ArrayList<State> generatePossibleMoves(State state) {
        ArrayList<State> possibleMoves = new ArrayList<>();
        String player;
        if (step == 0) {
            player = calculatePlayerTurnOne(state, aiChoice);
        } else {
            player = calculatePlayerTurnTwo(state, aiChoice);
        }

        for (int i = 0; i < 9; i++) {
            if (!state.getStateIndex(i).equals("X") && !state.getStateIndex(i).equals("O")) {
                String[] newState = state.getState().clone();
                newState[i] = player;
                possibleMoves.add(new State(i, newState));
            }
        }
        return possibleMoves;
    }

    private String calculatePlayerTurnOne(State state, String aiChoice) {
        int xMoves = Collections.frequency(List.of(state.getState()), "X");
        int oMoves = Collections.frequency(List.of(state.getState()), "O");
        return (aiChoice.equals("X") && xMoves <= oMoves)
                || (aiChoice.equals("O") && oMoves <= xMoves)
                ? aiChoice : (aiChoice.equals("X") ? "O" : "X");
    }

    private String calculatePlayerTurnTwo(State state, String aiChoice) {
        int xMoves = Collections.frequency(List.of(state.getState()), "X");
        int oMoves = Collections.frequency(List.of(state.getState()), "O");
        return (aiChoice.equals("X") && xMoves < oMoves)
                || (aiChoice.equals("O") && oMoves < xMoves)
                ? aiChoice : (aiChoice.equals("X") ? "O" : "X");
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
            if (aiChoice.equals("X")) {
                // Если строка состоит только из символов "X", то ИИ победил
                if (line.equals("XXX")) {
                    return 1;
                    // Если строка состоит только из символов "O", то игрок победил
                } else if (line.equals("OOO")) {
                    return -1;
                }
            } else if (aiChoice.equals("O")) {
                // Если строка состоит только из символов "O", то ИИ победил
                if (line.equals("OOO")) {
                    return 1;
                    // Если строка состоит только из символов "X", то игрок победил
                } else if (line.equals("XXX")) {
                    return -1;
                }
            }
        }
        // Если ни одна из строк не победительская, то возвращаем 0
        return 0;
    }

    private boolean isFirstStepAI(State state){
        byte count = (byte) Arrays.stream(state.getState())
                .filter(String::isEmpty)
                .count();
        return count == 9 || count == 8;
    }
}
