package minesweeper;

import javafx.util.Pair;

import java.util.*;

public class MineSweeper {
    private int size;
    private int mineCount;
    private int[][] field;
    private int[][] countOfMines;
    private int[][] labels;
    boolean fieldsFilled = false;

    MineSweeper(int size, int mineCount) {
        this.size = size;
        this.mineCount = mineCount;
        field = new int[this.size][this.size];
        countOfMines = new int[this.size][this.size];
        labels = new int[this.size][this.size];

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                countOfMines[i][j] = -1;
            }
        }
    }

    void setMines (int exclX, int ecxlY) {
        Random random = new Random();

        int setMines = mineCount;
        while (setMines > 0) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            if (field[x][y] == 0 && !(x == exclX && y == ecxlY)) {
                field[x][y] = 1;
                setMines--;
            }
        }
    }


    void draw() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if (isMineHere(i,j)) {
                    System.out.print('X');
                }
                else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }
    }

    private boolean isMineHere(int x, int y) {
        return field[x][y] == 1;
    }

    private boolean isLabelHere(int x, int y) {
        return labels[x][y] == 1;
    }

    int calcMinesAround(int x, int y) {
        int cnt = 0;
        for(int i = Math.max(x-1,0); i <= Math.min(x+1,size-1); i++) {
            for(int j = Math.max(y-1,0); j <= Math.min(y+1,size-1); j++) {
                if (isMineHere(i, j)) { // (i != x) && (j != y)
                    cnt++;
                }
            }
        }
        if (!isMineHere(x, y)) {
            countOfMines[x][y] = cnt;
        }
        return cnt;
    }

    void drawWithCounts(boolean drawMinws) {
        System.out.print(" │"); for(int i = 0; i < size; i++) { System.out.print(i+1); } System.out.println("│");
        System.out.print("—│"); for(int i = 0; i < size; i++) { System.out.print("—"); } System.out.println("│");
        for(int i = 0; i < size; i++) {
            System.out.print((i+1)+"│");
            for(int j = 0; j < size; j++) {
                // int cnt = calcMinesAround(i, j);
                if (drawMinws &&  isMineHere(i, j)) {
                    System.out.print('X');
                } else if (countOfMines[i][j] == 0) {
                    System.out.print('/');
                } else if (countOfMines[i][j] > 0) {
                    System.out.print(countOfMines[i][j]);
                } else if (isLabelHere(i, j)) {
                    System.out.print('*');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println("│");
        }
        System.out.print("—│"); for(int i = 0; i < size; i++) { System.out.print("—"); } System.out.println("│");
    }


    void game() {
        Scanner scanner = new Scanner(System.in);
        drawWithCounts(false);

        boolean userWin = true;
        boolean gameOver = false;

        while (!gameOver) {
            System.out.print("Set / delete mines marks (x and y coordinates): ");
            int x = scanner.nextInt()-1;
            int y = scanner.nextInt()-1;
            String mode = scanner.next();

            switch (mode) {
                case "free":
                    // первое заполнеиние
                    if (!fieldsFilled) {
                        setMines(y, x);
                        fieldsFilled = true;
                    }

                    // открыти мину
                    if (isMineHere(y, x)) {
                        gameOver = true;
                        userWin  = false;
                    }

                    openMines(y, x);

                    break;
                case "mine":
                    updateMine(y, x);
                    break;
                default:
                    break;
            }

            drawWithCounts(!userWin);

            if (isAllMineaOpened()) {
                gameOver = true;
                userWin  = true;
                break;
            }
        }
        if (userWin) {
            System.out.println("Congratulations! You found all mines!");
        } else {
            System.out.println("You stepped on a mine and failed!");
        }
    }


    private void openMines(int x, int y) {
        if (isMineHere(x, y)) {
            return;
        } else if (countOfMines[x][y] == -1) {
            int cnt = calcMinesAround(x, y);
            countOfMines[x][y] = cnt;
            if (cnt == 0) {
                for(int i = Math.max(x-1,0); i <= Math.min(x+1,size-1); i++) {
                   for (int j = Math.max(y - 1, 0); j <= Math.min(y + 1, size - 1); j++) {
                       openMines(i, j);
                   }
                }
            }
        }
        return;
    }

    private boolean isAllMineaOpened() {

        int freeCell = 0;
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if (countOfMines[i][j] == -1) {
                    freeCell++;
                }
            }
        }
        boolean allMinesOpen = freeCell == mineCount;
        return allMinesOpen;
    }

    private boolean updateMine(int x, int y) {
        if (countOfMines[x][y] == -1) {
            labels[x][y] = 1-labels[x][y];
            return true;
        } else {
            return false;
        }
    }

}
