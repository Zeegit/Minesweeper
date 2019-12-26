package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many mines do you want on the field? ");
        int mineCount = scanner.nextInt();
        //System.out.println();


        int size = 9;

        MineSweeper minemweeper = new MineSweeper(size, mineCount);
        // minemweeper.setMines();
        //minemweeper.draw();
        //System.out.println();
        // minemweeper.drawWithCounts();
        minemweeper.game();



    }
}
