package nQueen;

import java.util.Arrays;

public class nQueens {
    public static void main(String[] args) {
        nQueens nq = new nQueens();
        nq.initialise(4);
    }

    private void initialise(int n) {
        int[][] board = new int[n][n];

        for (int[] row : board)
            Arrays.fill(row, 0);

        if (!findSol(board, 0)) {
            System.out.println("No Solution Exists");
        } else {
            printSol(board);
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

    private boolean placeQueenOrNot(int[][] board, int row, int col) {
        // same col
        for (int i = 0; i < col; i++) {
            if (board[row][i] == 1) {
                return false;
            }
        }
        // top left diagonal
        for (int i = row, j = col; i > 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }
        // bottom left diagonal
        for (int i = row, j = col; j > 0 && i < board.length; i++, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }
        return true;
    }

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
