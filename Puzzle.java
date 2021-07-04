import java.io.*;
import java.util.*;

public class Puzzle {
    public static final int DIM = 9;
    public static final int SUBGRID_DIM = 3; 
    private int[][] values;
    private boolean[][] valIsFixed;
    private boolean[][][] subgridHasValue;
    private boolean[][] rowHasValue;
    private boolean[][] colHasValue; 
    public Puzzle() {
        this.values = new int[DIM][DIM];
        this.valIsFixed = new boolean[DIM][DIM];
        this.subgridHasValue = new boolean[SUBGRID_DIM][SUBGRID_DIM][DIM + 1]; 
        this.rowHasValue = new boolean[DIM][DIM + 1];
        this.colHasValue = new boolean[DIM][DIM + 1];
    }
    private boolean isSafe(int row, int col,int val) {
        if (rowHasValue[row][val] || colHasValue[col][val] || subgridHasValue[row/3][col/3][val] ){
            return false;
        } 
        return true;
    }
    private boolean solveRB(int n) {
        int row = n/9;
        int col = n%9;
        if(n == 81){
            return true;
        }
        else if(valIsFixed[row][col]){
            return solveRB(n + 1);
        }
       
        else{
            for (int val = 1; val <= 9; val++) {
                if (this.isSafe(row, col,val)) {
                    this.placeVal(val,row, col);
                    if (solveRB(n+ 1)) {
                        return true;
                    }
                    this.removeVal(val, row, col);
                }
            }
            
            return false;
        }

    }
    public boolean solve() { 
        boolean foundSol = this.solveRB(0);
        return foundSol;
    }
    public void placeVal(int val, int row, int col) {
        this.values[row][col] = val;
        this.subgridHasValue[row/SUBGRID_DIM][col/SUBGRID_DIM][val] = true;
        this.rowHasValue[row][val] = true;
        this.colHasValue[col][val] = true;
        
    }
    public void removeVal(int val, int row, int col) {
        this.values[row][col] = 0;
        this.subgridHasValue[row/SUBGRID_DIM][col/SUBGRID_DIM][val] = false;
        this.rowHasValue[row][val] = false;
        this.colHasValue[col][val] = false;
    }
    public void readFrom(Scanner input) {
        for (int r = 0; r < DIM; r++) {
            for (int c = 0; c < DIM; c++) {
                int val = input.nextInt();
                this.placeVal(val, r, c);
                if (val != 0) {
                    this.valIsFixed[r][c] = true;
                }
            }
            input.nextLine();
        }
    }
    public void display() {
        for (int r = 0; r < DIM; r++) {
            printRowSeparator();
            for (int c = 0; c < DIM; c++) {
                System.out.print("|");
                if (this.values[r][c] == 0) {
                    System.out.print("   ");
                } else {
                    System.out.print(" " + this.values[r][c] + " ");
                }
            }
            System.out.println("|");
        }
        printRowSeparator();
    }
    private static void printRowSeparator() {
        for (int i = 0; i < DIM; i++) {
            System.out.print("----");
        }
        System.out.println("-");
    }
}
