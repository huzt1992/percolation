/******************************************************************************

 PERCOLATION CONCEPT:

 Percolation. Given a composite systems comprised of randomly distributed insulating and metallic materials: what fraction of the materials need to be metallic so that the composite system is an electrical conductor? Given a porous landscape with water on the surface (or oil below), under what conditions will the water be able to drain through to the bottom (or the oil to gush through to the surface)? Scientists have defined an abstract process known as percolation to model such situations.

 The model. We model a percolation system using an n-by-n grid of sites. Each site is either open or blocked. A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites. We say the system percolates if there is a full site in the bottom row. In other words, a system percolates if we fill all open sites connected to the top row and that process fills some open site on the bottom row. (For the insulating/metallic materials example, the open sites correspond to metallic materials, so that a system that percolates has a metallic path from top to bottom, with full sites conducting. For the porous substance example, the open sites correspond to empty space through which water might flow, so that a system that percolates lets water fill open sites, flowing from top to bottom.)

 THIS PROGRAM:

 This program takes the grid size n to generate a n*n grid.
 Then, the user repeatedly open up individual site of grid.
 Finally, program will check whether the grid is percolated or not.
 ******************************************************************************/


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
