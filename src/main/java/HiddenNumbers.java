import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

public class HiddenNumbers {

    private static final String INFILENAME = "inputChallenge.txt";
    private static String OUTFILENAME = "outputChallenge.txt";
    public static BufferedReader reader = null;
    private static PrintWriter out;
    private static int AMOUNT_CASES = 0;
    HashMap<String, ArrayList> perputationPosibilities = new HashMap<String, ArrayList>();


    public static void main(String[] args) {
        try {
            File file = new File(INFILENAME);
            StringBuffer contents = new StringBuffer();

            reader = new BufferedReader(new FileReader(file));
            retrieveInput();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void retrieveInput() {
        File file = new File(INFILENAME);
        StringBuilder contents = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            obtainCaseAmount();

            for (int i = 0; i < AMOUNT_CASES; i++) {
                permutationPosibilities();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void permutationPosibilities() {
        try {
            String sCurrentLine = reader.readLine();
            Integer[] values = new Integer[sCurrentLine.length()];

            for (int i = 0; i < sCurrentLine.length(); i++) {
                values[i] = i;
            }
            Set<Integer[]> options = getPermutationsRecursive(values);
            removeInitializeZero(options);

            //perputationPosibilities.add();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void removeInitializeZero(Set<Integer[]> options) {
        Iterator<Integer[]> iterator = options.iterator();
        while(iterator.hasNext()) {
            Integer[] setElement = iterator.next();
            if(setElement.toString().charAt(0) == 0) {
                iterator.remove();
            }
        }
    }


    private static void obtainCaseAmount() {
        try {
            String contentLine = reader.readLine();
            if (contentLine != null) {
                AMOUNT_CASES = parseInt(contentLine);
            }
        } catch (IOException e) {
            System.out.println("Error reading number of Cases.");
        }

    }

    public static Set<Integer[]> getPermutationsRecursive(Integer[] num) {
        if (num == null)
            return null;

        Set<Integer[]> perms = new HashSet<>();

        //base case
        if (num.length == 0) {
            perms.add(new Integer[0]);
            return perms;
        }

        //shave off first int then get sub perms on remaining ints.
        //...then insert the first into each position of each sub perm..recurse
        int first = num[0];
        Integer[] remainder = Arrays.copyOfRange(num, 1, num.length);
        Set<Integer[]> subPerms = getPermutationsRecursive(remainder);
        for (Integer[] subPerm : subPerms) {
            for (int i = 0; i <= subPerm.length; ++i) { // '<='   IMPORTANT !!!
                Integer[] newPerm = Arrays.copyOf(subPerm, subPerm.length + 1);
                for (int j = newPerm.length - 1; j > i; --j)
                    newPerm[j] = newPerm[j - 1];
                newPerm[i] = first;
                perms.add(newPerm);
            }
        }

        return perms;
    }

}