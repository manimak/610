//Mani Makaremi COMP 610
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

class MergeSort {
    int answre = 0;

    protected MergeSort(){

    }

    //using the merge sort algorithm to sort the numbers and get the CountingInversions
    //this method call it self recursively to split the array to one elements and then merge them bu calling the merge method
    protected void mergeSort(int[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = (int)Math.ceil((double)n / 2);
        int[] l = new int[mid];
        int[] r = new int[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);
        merge(a, l, r, mid, n - mid);
    }

    //this method gets the spllited  arrays and merge them
    //we find the answer by adding the numbers we skip every time we pick a number form the right side
    protected void merge(
            int[] a, int[] l, int[] r, int left, int right) {
        int leftArrayLength = l.length; //saving the left length to find how many numbers we skip if the number switch
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i] <= r[j]) {
                leftArrayLength--;
                a[k++] = l[i++];
            }
            else {
                answre = answre + leftArrayLength;
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }

    protected int getAnswre(){
        return answre;
    }
}

public class Project2 {


    public static void main(String[] args) throws FileNotFoundException {

        //getting the data from the text file and change it to int array
        File file = new File("input2.txt");
        Scanner sc =  new Scanner(file);
        String tempString = sc.nextLine();
        while (sc.hasNextLine()){
            tempString = tempString + " "+ sc.nextLine();
        }
        String [] temp = tempString.trim().split("\\W+");
        int [] numbers = new int[temp.length];
        for(int i = 0;i<temp.length;i++){
            numbers[i] = Integer.parseInt(temp[i]);
        }
        MergeSort a = new MergeSort();
        a.mergeSort(numbers , numbers.length);
//        System.out.println(Arrays.toString(numbers));
        System.out.println(a.getAnswre());

//        int[] ar1 = new int[1000];
//        for(int i = 0; i <  ar1.length; i++) {
//            System.out.print(" ");
//            System.out.print((int)(Math.random() * 1000));
//        }





    }

}
