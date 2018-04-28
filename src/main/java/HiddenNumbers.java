import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Consumer;

import static java.lang.Integer.parseInt;

public class HiddenNumbers {

    private static final String INFILENAME = "testInput.txt";
    private static String OUTFILENAME = "testoutput.txt";
    public static BufferedReader reader = null;
    private static PrintWriter out;
    private static int AMOUNT_CASES = 0;
    private static int BASE = 0;
    HashMap<String, ArrayList> perputationPosibilities = new HashMap<String, ArrayList>();


    public static void main(String[] args) {
        try {
            File file = new File(INFILENAME);
            StringBuffer contents = new StringBuffer();

            out = new PrintWriter(OUTFILENAME);

            reader = new BufferedReader(new FileReader(file));
            obtainCaseAmount();
            for (int i = 0; i < AMOUNT_CASES; i++) {
                double hiddenNum = obtainHiddenNumber();
                printHiddenNumber(i, hiddenNum, out);
            }

            reader.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printHiddenNumber(int index,double hiddenNum, PrintWriter out) {
        DecimalFormat df = new DecimalFormat("##0");
        out.print("Case #" + index + ": ");
        out.print(df.format(hiddenNum));
        out.println();
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

    private static double  obtainHiddenNumber() {
        HashSet<String>  possibilitiesPermu = permutationPossibilities();
        double hiddenNum = calculateDecimalValue(possibilitiesPermu);
         return hiddenNum;
   }

    private static double calculateDecimalValue(HashSet<String> permuPossibilities) {
        Iterator posIterator = permuPossibilities.iterator();
        double decimal = 0;
        double maxValue = 0;
        double minValue = Double.MAX_VALUE;
        while (posIterator.hasNext()){
            String chainValue = (String)posIterator.next();
            decimal = decimalValue(chainValue);
            if(decimal > maxValue) maxValue = decimal;
            if(decimal < minValue) minValue = decimal;
        }
        return (maxValue-minValue);
    }

    private static double decimalValue(String chainValue) {
        int baseRedux = BASE-1;
        double decimalValue = 0;

        for (int i = 0; i <BASE; i++){
            decimalValue = decimalValue + Integer.parseInt(chainValue.substring(i,i+1)) * Math.pow(BASE,baseRedux);
            baseRedux--;
        }
        return decimalValue;
    }

    private static HashSet<String>  permutationPossibilities() {
        HashSet<String> options = new HashSet<>();
        try {
            String sCurrentLine = reader.readLine();
            StringBuilder valuesToPermutate = new StringBuilder();
            BASE = sCurrentLine.length();
            for (int i = 0; i < sCurrentLine.length(); i++) {
                valuesToPermutate.append(i);
            }
            options = generatePerm(valuesToPermutate.toString());
            removeInitializeZero(options);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return options;
    }


    private static void removeInitializeZero(Set<String> options) {
        Iterator<String> iterator = options.iterator();
        while (iterator.hasNext()) {
            String setElement = iterator.next();
            if (setElement.startsWith("0")) {
                iterator.remove();
            }
        }
    }

    public static HashSet<String> generatePerm(String input) {
        HashSet<String> set = new HashSet<String>();
        if (input == "")
            return set;

        Character a = input.charAt(0);

        if (input.length() > 1) {
            input = input.substring(1);

            Set<String> permSet = generatePerm(input);

            for (String x : permSet) {
                for (int i = 0; i <= x.length(); i++) {
                    set.add(x.substring(0, i) + a + x.substring(i));
                }
            }
        } else {
            set.add(a + "");
        }
        return set;
    }

 }