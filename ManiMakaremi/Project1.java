//Mani Makaremi COMP 610
import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Project1 {

    public static void main(String[] args) throws FileNotFoundException {
        Map<Integer, List<String>> men = new HashMap<>();
        Map<Integer, List<String>> women = new HashMap<>();
        parsing(men,women);
        int maxMen = maxNumberForFirstSide(men,women,"max"); //if men first propose they get the max first choice
        System.out.println(maxMen);
        men.clear();
        women.clear();
        parsing(men,women);
        int maxWomen = maxNumberForFirstSide(women,men,"max"); //if women first propose they get the max first choice
        System.out.println(maxWomen);
        men.clear();
        women.clear();
        int total = totalNumberForFirstChoice(men,women);
        System.out.println(total);




    }

    private static void parsing(Map<Integer, List<String>> men,Map<Integer, List<String>> women) throws FileNotFoundException {
        File file = new File("input1.txt");
        Scanner sc =  new Scanner(file);
        String temp = sc.nextLine();
        //parsing the input file
        int numberOfMenOrWomen = Integer.parseInt(temp); // getting the first line number as int
        String[] temp2;
        for(int i=0;i<numberOfMenOrWomen;i++){
            temp2 = sc.nextLine().split("\\s+");
            List<String> list = new LinkedList<String>(Arrays.asList(temp2));
            men.put(i+1, list);
        }
        for(int i=0;i<numberOfMenOrWomen;i++){
            temp2 = sc.nextLine().split("\\s+");
            List<String> list2 = new LinkedList<String>(Arrays.asList(temp2));
            women.put(i+1,list2);
        }
    }

    private static int maxNumberForFirstSide(Map<Integer, List<String>> firstSet, Map<Integer, List<String>> secondSet , String result){
        //create array for to see who is free. By setting all to zero all of the persons are free
        int [] freeFirstSet = new int[firstSet.size()];
        int [] freeSecondSet = new int[secondSet.size()];
        Arrays.fill(freeFirstSet,0);
        Arrays.fill(freeSecondSet, 0);
        int propose;
        List<String> preferences = new ArrayList<String>();
        while (isDone(freeFirstSet)) {
            for (int i = 0; i < firstSet.size(); i++) {
                if(freeFirstSet[i] == 0) { //check if the person is free
                    preferences = firstSet.get(i + 1); //getting the preferences for each first set person
                    propose = Integer.parseInt(preferences.get(0)); //getting the first choice for the person
                    preferences.remove(0);
                    firstSet.put(i + 1, preferences);//delete the choice from the the person preferences
                    freeFirstSet[i] = propose; //make the person not free and make it the choice
                    if(freeSecondSet[propose-1] == 0) { // if no one proposed
                        freeSecondSet[propose - 1] = i + 1; // make the propose to second list
                    }
                    else{ //if someone else already proposed
                        preferences = secondSet.get(propose); //get the list of preference from second set
                        int betterChoice = camperChoices(preferences,freeSecondSet[propose-1],i+1); //find the better choice base on index
                        int temp = freeSecondSet[propose - 1]; // save previous choice
                        if (temp != betterChoice) { //if the priviest choice is not the better one
                            freeSecondSet[propose - 1] = betterChoice; //set it to new choice
                            freeFirstSet[temp-1] = 0; //remove the propose
                        }
                        else{
                            freeFirstSet[i] = 0; //make that person to not proposed again
                        }
                    }


                }

            }

        }
        int count = 0;

        for(List<String> listS : firstSet.values()){ //finding the number of first set that choose first choice
            if(listS.size() == freeFirstSet.length-1){
                count++;
            }
        }

        if (result.equals("max")) {
            return count;
        }
        else {
            int a = 0;
            for(List<String> listS : secondSet.values()){
                if(Integer.parseInt(listS.get(0)) == freeSecondSet[a]) { //if the the second set first element is in the free list
                    count++;
                }
                a++;
            }
            return count;
        }
    }

    private static int totalNumberForFirstChoice(Map<Integer, List<String>> men,Map<Integer, List<String>> women) throws FileNotFoundException {
        List<int[]> results = new ArrayList<int[]>();
        int max= 0;
        File file = new File("input1.txt");
        Scanner sc =  new Scanner(file);
        String temp = sc.nextLine();
        int [] arr = new int[(Integer.parseInt(temp)*2)];
        for(int i=0;i<arr.length;i++){
            arr[i]=i;
        }
        combination(arr,results); //find all the combination to have on one side

        for(int[] array:results){
            parsingForTotal(men,women,array); //parsing men and women base on the different combinations
            int tem3 = maxNumberForFirstSide(men,women,"total"); //run that specific combination to find total first choice
            if(max < tem3){   //find the max from all the combinations
                max = tem3;
            }
            women.clear();
            men.clear();

        }

        return max;

    }

    private static void parsingForTotal(Map<Integer, List<String>> men,Map<Integer, List<String>> women,int [] combination) throws FileNotFoundException {
        File file = new File("input1.txt");
        Scanner sc =  new Scanner(file);
        String temp = sc.nextLine();
        int key1 = 0;
        int key2 = 0;
        for(int i = 0;i<Integer.parseInt(temp)*2;i++){  //parsing it base on the array combination we send it and use it for men or women
            if(Arrays.binarySearch(combination,i)>-1){
                String[] temp2 = sc.nextLine().split("\\s+");
                List<String> list = new LinkedList<String>(Arrays.asList(temp2));
                men.put(key1+1, list);
                key1++;

            }
            else {
                String[] temp2 = sc.nextLine().split("\\s+");
                List<String> list = new LinkedList<String>(Arrays.asList(temp2));
                women.put(key2 + 1, list);
                key2++;
            }

        }
    }

    private static int camperChoices(List<String> list, int a, int b){
        int indexA = list.indexOf(String.valueOf(a));
        int indexB = list.indexOf(String.valueOf(b));
        if(indexB>indexA){
            return a;
        }
        return b;
    }


    //checking if we still have any person that is not mach
    private static boolean isDone(int [] freeList){
        for (int a : freeList){
            if(a == 0){
                return true;
            }
        }
        return false;
    }


    //make all the combinations return as list of arrays
    private static void combinationUtil(int arr[], int data[], int start,
                                int end, int index, int r,List<int[]> combinations)
    {
        int []temp = new int[r];

        // Current combination is ready to be printed, print it
        if (index == r)
        {
            for (int j=0; j<r; j++)
                temp[j] = data[j];
            combinations.add(temp);
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, r,combinations);
        }
    }

    private static void combination(int arr[],List<int[]> combinations)
    {
        int r = arr.length/2;
        int data[]=new int[r];
        int n = arr.length;
        combinationUtil(arr, data, 0, n-1, 0, r , combinations);
    }


}
