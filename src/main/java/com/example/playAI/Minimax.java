package com.example.playAI;
import java.util.*;
import java.util.stream.IntStream;

public class Minimax {
    public int minimax(State state) {
        ArrayList<State> possibleMoves = generatePossibleMoves(state);
        ArrayList<Integer> movesList = new ArrayList<>();

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
        OptionalInt result = IntStream.range(0, movesList.size())
                .reduce((a, b) -> movesList.get(a) > movesList.get(b) ? a : b);
        if (result.isPresent()) {
            return result.getAsInt();
        } else {
            return -1;
        }
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

    private ArrayList<State> generatePossibleMoves(State state) {
        ArrayList<State> possibleMoves = new ArrayList<>();
        String player = calculatePlayerTurn(state);

        for (int i = 0; i < 9; i++) {
            if (!state.getStateIndex(i).equals("X") && !state.getStateIndex(i).equals("O")) {
                String[] newState = state.getState().clone();
                newState[i] = player;
                possibleMoves.add(new State(i, newState));
            }
        }
        return possibleMoves;
    }

    private String calculatePlayerTurn(State state) {
        int xMoves = Collections.frequency(List.of(state.getState()), "X");
        int oMoves = Collections.frequency(List.of(state.getState()), "O");
        return (xMoves <= oMoves) ? "X" : "O";
    }

    private int checkWinningState(State state) {
        for (int i = 0; i < 8; i++) {
            String line = checkState(state, i);
            switch (line) {
                case "XXX" -> {
                    return 1;
                }
                case "OOO" -> {
                    return -1;
                }
                default -> {
                }
            }
        }
        return 0;
    }
}
