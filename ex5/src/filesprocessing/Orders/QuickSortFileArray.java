package filesprocessing.Orders;

import filesprocessing.DirectoryProcessor;

import java.io.File;

// i took inspiration from :
//https://www.geeksforgeeks.org/quick-sort/

/**
 * class to preform quickSort
 */
class QuickSortFileArray {
    private static int fileLength;
    private static File fileNames[];

    private static DirectoryProcessor.orders myOrder;
    private static boolean isReverse;

    /**
     * main method to sort array of files with the quicksort implementation style
     *
     * @param files array to preform sorting with
     * @param order how to order them
     */
    static void sort(File files[], DirectoryProcessor.orders order, boolean isReversed) {
        if (files == null || files.length == 0) {
            return;
        }
        myOrder = order;
        isReverse = isReversed;
        fileLength = files.length;
        fileNames = files;
        // sort entire array
        quickSort(0, fileLength - 1);
    }

    /**
     * recursive quicksort method
     * chooses pivot
     *
     * @param lowerIndex  left bound
     * @param higherIndex right bound
     */
    private static void quickSort(int lowerIndex, int higherIndex) {
        int leftPointer, rightPointer;
        //Random rand = new Random();
        //int chosenPivotPoint = rand.nextInt((higherIndex - lowerIndex) + 1) + lowerIndex;
        int chosenPivotPoint = higherIndex;

        for (leftPointer = lowerIndex, rightPointer = higherIndex; leftPointer <= rightPointer; ) {
            while (OrderTaggingLogic(fileNames[rightPointer], fileNames[chosenPivotPoint]) > 0)
                rightPointer--;
            while (OrderTaggingLogic(fileNames[leftPointer], fileNames[chosenPivotPoint]) < 0)
                leftPointer++;

            if (leftPointer <= rightPointer) {
                swapItems(leftPointer, rightPointer);
                leftPointer++;
                rightPointer--;
            }
        }

        if (lowerIndex < rightPointer) {
            quickSort(lowerIndex, rightPointer);
        }
        if (leftPointer < higherIndex) {
            quickSort(leftPointer, higherIndex);
        }
    }

    /**
     * swaps two files in the array by index
     *
     * @param i index 1
     * @param j index 2
     */
    private static void swapItems(int i, int j) {
        File temp = fileNames[i];
        fileNames[i] = fileNames[j];
        fileNames[j] = temp;
    }

    /**
     * gets numerical value from comparing tw0 files
     *
     * @param fileA file 1
     * @param fileB file 2
     * @return numerical comparision of file 1 and file 2
     */
    private static int OrderTaggingLogic(File fileA, File fileB) {
        if (isReverse) {
            File temp = fileA;
            fileA = fileB;
            fileB = temp;
        }

        String fileA_Name = fileA.getName();
        String fileB_Name = fileB.getName();

        int absoluteNameCompare = fileA.getAbsolutePath().compareTo(fileB.getAbsolutePath());
        int primaryCompare = 0;

        switch (myOrder) {
            case ABS:
                return absoluteNameCompare;
            case SIZE:
                primaryCompare = (int) (fileA.length() - fileB.length());
                break;
            case TYPE:
                primaryCompare = getType(fileA_Name).compareTo(getType(fileB_Name));
                break;
        }

        if (primaryCompare != 0)
            return primaryCompare;
        return absoluteNameCompare;


    }

    /**
     * gets type of file
     *
     * @param file file
     * @return the type of the file
     */
    private static String getType(String file) {
        int i = file.lastIndexOf('.');
        if (i > 0)
            return file.substring(i + 1);
        if (i == -1)
            return file;
        //i==0 --> as explained in the pdf, that means the type in empty
        return "";
    }
}
