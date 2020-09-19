package jm233333.dsv.visualized;

import java.util.ArrayList;
import java.util.Arrays;

import jm233333.util.Pair;

public class VDSOptParser {

    public static Pair<String, ArrayList<Integer>> parse(VDS vds, String str) {
        // clip prefix/suffix spaces
        int l = 0, r = str.length() - 1;
        while (l <= r && str.charAt(l) == ' ') l ++;
        while (l <= r && str.charAt(r) == ' ') r --;
        if (l > r) {
            System.out.println("  Error in VDSOptParser : A string that only contains white spaces.");
            return null;
        }
        str = str.substring(l, r + 1);
        // get operation arguments
        String[] optArgs = str.split("\\s+");
        // get method name
        String methodName = optArgs[0];
        // get method parameters
        Class<?>[] methodParameters = new Class<?>[optArgs.length - 1];
        Arrays.fill(methodParameters, int.class);
        // check method existence
        try {
            vds.getClass().getMethod(methodName, methodParameters);
        } catch (NoSuchMethodException e) {
            System.out.printf("  Error in VDSOptParser : Cannot find method named %s with %d parameters.\n", methodName, methodParameters.length);
            return null;
        }
        // check method arguments
        ArrayList<Integer> arguments = new ArrayList<>();
        for (int i = 1; i < optArgs.length; i ++) {
            try {
                int argument = Integer.parseInt(optArgs[i]);
                arguments.add(argument);
            } catch (NumberFormatException e) {
                System.out.printf("  Error in VDSOptParser : Illegal %d-th argument %s.\n", i, optArgs[i]);
                return null;
            }
        }
        // return
        return new Pair<>(methodName, arguments);
    }

    private VDSOptParser() {}
}
