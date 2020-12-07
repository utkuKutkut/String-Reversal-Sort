import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//this version works as it describes in mail. You can type 'java StringReversalV1.java < P2-data-11.in > P2-data-01.sol' to make it work
//this version works as it describes in mail. You can type 'java StringReversalV1.java < P2-data-11.in > P2-data-01.sol' to make it work
//this version works as it describes in mail. You can type 'java StringReversalV1.java < P2-data-11.in > P2-data-01.sol' to make it work
//this version works as it describes in mail. You can type 'java StringReversalV1.java < P2-data-11.in > P2-data-01.sol' to make it work
//this version works as it describes in mail. You can type 'java StringReversalV1.java < P2-data-11.in > P2-data-01.sol' to make it work

public class StringReversalV1 {
    //Let's define our MAX value to check if sorting is possible or not!
    static final BigInteger inf = new BigInteger("100000000000000000000000000000000000000000000000000000");

    //Pass list parameter with full of strings
    String stringReversal(List<String> strings) {
        int n = strings.size();
        //for the sake of preventing OVERFLOWS, I used BIGINTEGER for all my operations.!!!
        //Double was not enough with 64 bits (2^64 ...)

        BigInteger[] cost = new BigInteger[n];
        BigInteger exponent = new BigInteger("2");
        int count=0;
        //assign exponential of 2 to each value in cost array! (so we can convert to binary at the end)
        for (int i =n-1; i>=0; i--) {
            cost[i] = exponent.pow(count);
            count++;
        }
        //create DP array with nx2 !
        BigInteger[][] dp = new BigInteger[n][2];
        int i;
        //put max value to all DP cells first!
        for (i = 0; i < n; i++) {
            dp[i][0] = inf;
            dp[i][1] = inf;
        }
        //first value should be 0
        dp[0][0] = BigInteger.valueOf(0);
        dp[0][1] = cost[0];

        //Using simple for loop only to check multiple conditions
        for (i = 1; i < n; i++) {
            String s1 = strings.get(i - 1);
            String s2 = strings.get(i);
            StringBuilder sb = new StringBuilder(s1);
            //save reverse version of string 1
            String s1r = sb.reverse().toString();
            StringBuilder sb1 = new StringBuilder(s2);
            //save reverse version of string 2 which we will compare with string 1;
            String s2r = sb1.reverse().toString();// reverse s2

            //if already sorted ...
            if (s1.compareTo(s2) <= 0) {
                BigInteger small = dp[i][0].min(dp[i - 1][0]);
                dp[i][0] = small;
            }
            //if reversed version is sorted
            if (s1r.compareTo(s2) <= 0) {
                BigInteger small = dp[i][0].min(dp[i - 1][1]);
                dp[i][0] = small;

            }
            //if reverse version of s1 and s2 is sorted
            if (s1r.compareTo(s2r) <= 0) {
                BigInteger small = dp[i][1].min(cost[i].add(dp[i - 1][1]));
                dp[i][1] = small;
            }
            //if will be sorted when we reverse s2
            if (s1.compareTo(s2r) <= 0) {
                BigInteger small = dp[i][1].min(cost[i].add(dp[i - 1][0]));
                dp[i][1] = small;
            }
        }
        BigInteger totalCost =  dp[n - 1][0].min(dp[n - 1][1]);
        if(totalCost.compareTo(inf)==1 || totalCost.compareTo(inf)==0){
            totalCost=BigInteger.valueOf(-1);
            return "-1";
        }
        //let's convert our cost to binary and then add necesarry leading zeros...
        String answer=totalCost.toString(2);
        int leadZeros=n-answer.length();
        for(int a=0;a<leadZeros;a++){
            answer="0"+answer;
        }
        return answer;
    }



    //in the main function, we simply read the file path from console and iterate over all files in the folder.
    public static void main(String[] args) {
        List<String> arr = new ArrayList<>();

        //reads input file from the console
        Scanner sc = new Scanner(System.in);
        StringReversalV1 obj = new StringReversalV1();


        //the part for standart input version
            /*
            File file = new File(filePath);

           // File[] listOfFiles = folder.listFiles();
            StringReversal obj = new StringReversal();

            //for (File file : listOfFiles) {
                Scanner sc = null;
                try {
                    sc = new Scanner(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            */
        //

        String input;
        StringBuffer sb = new StringBuffer();

        while (sc.hasNextLine()) {
            input = sc.nextLine();
            sb.append(input + " ");
        }
        String[] string = sb.toString().split(" ");
        int count = Integer.parseInt(string[0]);
        int num = 1;
        //let's iterate over all the the test cases in the folder
        for (int i = 0; i < count; i++) {
            int counter = Integer.parseInt(string[num]);
            for (int j = 0; j < counter; j++) {
                num++;
                arr.add(string[num]);
            }

            String n = obj.stringReversal(arr);
            if (n.equalsIgnoreCase("-1")) System.out.println("IMPOSSIBLE");
            else System.out.println(n);
            //clean parameter
            arr = new ArrayList<>();
            num++;
        }

    }

}
