package nQueen;

import java.util.Arrays;

public class nQueens {
    public static void main(String[] args) {
        nQueens nq = new nQueens();
        nq.initialise();
    }

    private void initialise() {
        // setup board and fill with zero
        int[][] board = new int[4][4];
        for (int[] row : board)
            Arrays.fill(row, 0);

        // if solution found, print it
        if (findSol(board, 0)) {
            printSol(board);
        } else {
            System.out.println("No Solution Exists");
        }
    }

    private boolean findSol(int[][] board, int col) {
        if (col >= board.length)
            return true;

        for (int row = 0; row < board.length; row++) {
            if (placeQueenOrNot(board, row, col)) {
                board[row][col] = 1;
                if (findSol(board, col + 1))
                    return true;
                else {
                    board[row][col] = 0;
                }
            }
        }
        return false;
    }

    // check for queens in
    private boolean placeQueenOrNot(int[][] board, int row, int col) {
        // same col
        for (int i = 0; i < col; i++) {
            if (board[row][i] == 1) {
                return false;
            }
        }
        // top left diag
        for (int i = row, j = col; i > 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }
        // bottom left diag
        for (int i = row, j = col; j > 0 && i < board.length; i++, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }
        return true;
    }

    // print board
    private void printSol(int[][] board) {
        for (int[] row : board) {
            for (int col : row) {
                if (col == 0)
                    System.out.print(" X ");
                else
                    System.out.print(" Q ");
            }
            System.out.println();
        }
    }
}
