import java.util.Scanner;

public class Percolation {

    private int matrixSize;
    private int matrixSide;
    private int openSiteCount;
    private boolean[][] openSiteGrid;
    private FindAndConnect fullGrid;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("illegal");
        }
        matrixSide = n;
        matrixSize = n * n;
        openSiteGrid = new boolean[n + 1][n + 1];
        // when initialized, boolean array is null
        fullGrid = new FindAndConnect(n * n + 2);
        openSiteCount = 0;
    }

    // // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int openLocation = this.convertToGrid(row, col);
        openSiteGrid[row][col] = true;

        openSiteCount++;
        // if it openned in the top row
        if (openLocation <= matrixSide) {
            fullGrid.union(0, openLocation);
        }

        // if it opeened in the bot row
        if (openLocation > (matrixSize - matrixSide)) {
            fullGrid.union(matrixSize + 1, openLocation);
        }


        if (0 < row - 1 && isOpen(row - 1, col)) {
            fullGrid.union(openLocation - matrixSide, openLocation);
        }


        if (row + 1 <= matrixSide && isOpen(row + 1, col)) {
            fullGrid.union(openLocation + matrixSide, openLocation);
        }


        if (col + 1 <= matrixSide && isOpen(row, col + 1)) {
            fullGrid.union(openLocation + 1, openLocation);
        }


        if (col - 1 > 0 && isOpen(row, col - 1)) {
            fullGrid.union(openLocation - 1, openLocation);
        }

    }


    private int convertToGrid(int row, int col) {
        return (row - 1) * this.matrixSide + col;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return openSiteGrid[row][col];
    }


    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int targetSite = convertToGrid(row, col);
        return fullGrid.find(targetSite) == fullGrid.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSiteCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return fullGrid.find(matrixSize + 1) == fullGrid.find(0);
    }

    // test client (optional)
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);

        System.out.println("Please input 'n' in command line to create n*n grid");
        int n = reader.nextInt();

        Percolation per = new Percolation(n);
        System.out.println("You have successfully created" + n + "*" + n + "grid!");

        while (true) {
            System.out.println("Please open up a site by input 2 numbers(representing row and col); Or you can exit by input '0'");
            int x = reader.nextInt();
            if (x == 0) {
                break;
            }
            int y = reader.nextInt();

            per.open(x, y);
            System.out.println("You have openned the site:" + x + "," + y);
        }

        System.out.println("Does the grid percolate?" + per.percolates());
    }
}
