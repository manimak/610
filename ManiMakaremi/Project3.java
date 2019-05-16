import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Scanner;

public class Project3 {

    public static void main(String[] args) throws FileNotFoundException {

        //parsing the input file to two same size array list
        File file = new File("input3.txt");
        Scanner sc = new Scanner(file);

        List<Integer> firstList = new ArrayList<Integer>();
        List<Integer> secondList = new ArrayList<Integer>();
        int size = sc.nextInt()*2;
        int j = sc.nextInt();
        j = j+1;
        for(int i =0;i<size;i++) {
            if (i < size/2){
                firstList.add(sc.nextInt());
            }
            else{
                secondList.add(sc.nextInt());
            }
        }
        //exit if J is too large
        if(j>(firstList.size()+secondList.size())){
            System.out.println("J is too large!");
            System.exit(0);
        }

        if(firstList.size()>j) {
            firstList = new ArrayList<Integer>(firstList.subList(0, j));
        }
        if(secondList.size()>j) {
            secondList = new ArrayList<Integer>(secondList.subList(0, j));
        }

        //start if the algorithm

        //while loop till one of the lists has one element
        while ((firstList.size()+secondList.size())>3 && firstList.size()>=1 && secondList.size()>=1){
            int firstMid = firstList.get((firstList.size()/2));
            int secondMid = secondList.get((secondList.size()/2));
            if(j<=((firstList.size()+secondList.size())/2)){
                if(firstMid<=secondMid){
                    secondList = new ArrayList<Integer>(secondList.subList(0,(secondList.size()/2)));
                }
                else {
                    firstList = new ArrayList<Integer>(firstList.subList(0,(firstList.size()/2)));
                }


            }
            else {
                if(firstMid<=secondMid){
                    j = j - firstList.size()/2;
                    firstList =  new ArrayList<Integer>(firstList.subList((firstList.size()/2),firstList.size()));

                }
                else {
                    j = j - secondList.size()/2;
                    secondList = new ArrayList<Integer>(secondList.subList((secondList.size()/2),secondList.size()));
                }
            }
            if( firstList.size()<=1 || secondList.size()<=1){

                break;
            }

//            for (Integer a: firstList
//            ) {
//                System.out.println(a);
//            }
//            for (Integer a: secondList
//            ) {
//                System.out.println(a);
//            }

//            System.out.println("**************");
        }
//        for(int i = 0;i<firstList.size();i++){
//
//            System.out.println(firstList.get(i));
//        }
//        for(int i = 0;i<secondList.size();i++){
//
//            System.out.println(secondList.get(i));
//        }
//        System.out.println("<"+j+">");
//        System.out.println("**************");

        int [] twoNumber = new int[]{-999999999,999999999};
        int oneNumber = 0;

        if(firstList.size() != 1) {
            if(firstList.size()>1) {
                twoNumber[0] = firstList.get(j - 2);
            }
            if(j-1<firstList.size()) {
                twoNumber[1] = firstList.get(j - 1);
            }
            oneNumber = secondList.get(0);
        }
        else {
            if(secondList.size()>1) {
                twoNumber[0] = secondList.get(j - 2);
            }
            if(j-1<secondList.size()) {
                twoNumber[1] = secondList.get(j - 1);
            }
            oneNumber = firstList.get(0);
        }
//        System.out.println(Arrays.toString(twoNumber));
//        System.out.println(oneNumber);
//        System.out.println("**************");

        if(oneNumber<twoNumber[0]){
            System.out.println(twoNumber[0]);
        }else {
            if(oneNumber<twoNumber[1]){
                System.out.println(oneNumber);
            }else {
                System.out.println(twoNumber[1]);
            }
        }

    }

}
