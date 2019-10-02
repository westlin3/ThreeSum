package csc482;

import java.util.Arrays;

public class ThreeSum {
    // Do not instantiate.
    private ThreeSum() { }

    /**
     * Prints to standard output the (i, j, k) with {@code i < j < k}
     * such that {@code a[i] + a[j] + a[k] == 0}.
     *
     * @param a the array of integers
     */
    public static void printAll(int[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                for (int k = j+1; k < n; k++) {
                    if (a[i] + a[j] + a[k] == 0) {
                        System.out.println(a[i] + " " + a[j] + " " + a[k]);
                    }
                }
            }
        }
    }

    /**
     * Returns the number of triples (i, j, k) with {@code i < j < k}
     * such that {@code a[i] + a[j] + a[k] == 0}.
     *
     * @param  a the array of integers
     * @return the number of triples (i, j, k) with {@code i < j < k}
     *         such that {@code a[i] + a[j] + a[k] == 0}
     */
    //pulled this from the online of the book I'm using
    public static int slow3(long[] a) {
        int n = a.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                for (int k = j+1; k < n; k++) {
                    //System.out.println(a[k]);
                    if (a[i] + a[j] + a[k] == 0) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // simple binary search for fast search
    public static boolean binarySearch(long [] arr,int splitting, int length, long searchValue) {
        if (length >= splitting) {
            int mid = splitting + (length - splitting) / 2;

            if (arr[mid] == searchValue)
                return true;

            if (arr[mid] > searchValue)
                return binarySearch(arr, splitting, mid-1, searchValue);

            return binarySearch(arr, mid+1, length, searchValue);
        }
        return false;
    }

    // does the same as the slow3 except it sorts it first then does a binary search for
    // the last value rather than iterating over the whole list
    public static int fast3(long[] list) {
        int n = list.length;
        int count = 0;
        // n*log(n) speed a modified merge sort according to docs.
        Arrays.sort(list);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                boolean finder = binarySearch(list,j+1, n-1, -(list[i]+list[j]));
                if (finder == true)
                    count ++;
            }
        }
        return count;
    }

    // Okay just two iteration the last one comes from both iteration.
    public static int fastest3(long[] list) {
        int n = list.length;
        int count = 0;
        Arrays.sort(list);
        for (int i = 0; i < n-2; i++) {
            int backIterator = i+1;
            int frontIterator = n-1;
            while (backIterator < frontIterator) {
                if (list[i] + list[backIterator] + list[frontIterator] == 0) {
                    count ++;
                }
                if (list[i] + list[backIterator] + list[frontIterator] < 0)
                    backIterator++;
                else
                    frontIterator--;

            }
        }
        return count;
    }


    /**
     * Reads in a sequence of integers from a file, specified as a command-line argument;
     * counts the number of triples sum to exactly zero; prints out the time to perform
     * the computation.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args)  {
        int sizeOfList = 10000;
        int trials = 1;
        long totalTime = 0;
        long[] myList = {-10, -9, -7, -5, -1, 1, 3, 5, 6, 7, 8, 11};
        long[] myList2 = {-2,-1,0,1,2};
        for (int i =0; i<trials; i++) {
            long[] testList = createRandomIntegerList(sizeOfList);

            ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch();
            TrialStopwatch.start();
            int count = fastest3(myList2);
            //  system.out.println("elapsed time = " + timer.elapsedTime());
            System.out.println(count);
            long trialTime = TrialStopwatch.elapsedTime();
            totalTime += trialTime;
        }
        double nanoToSeconds = Math.pow(10, -9);
        System.out.println(totalTime / trials* nanoToSeconds);
    }

    public static long[] createRandomIntegerList(int size){
        long MAXVALUE = 4100000;
        long MINVALUE = -4100000;
        long[] newList = new long[size];
        for(int j=0;j<size;j++){
            newList[j] = (long) (MAXVALUE - Math.random() * (MAXVALUE - MINVALUE));
        }
        return newList;
    }

}
