package tools;

import java.io.*;
import java.util.List;

public class Tool {
    public static int[] listToInt(List<Integer> list) {
        return list.stream().mapToInt(Integer::valueOf).toArray();
    }

    public static String readFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuffer s = new StringBuffer();
            String ss;
            while ((ss = reader.readLine()) != null) s.append(ss);
            reader.close();
            return s.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String readFile(InputStream stream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer s = new StringBuffer();
            String ss;
            while ((ss = reader.readLine()) != null) s.append(ss);
            reader.close();
            return s.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isArrayEqual(int[] matchBF, int[] matchKMP) {
        if (matchBF.length != matchKMP.length) return false;
        for (int i = 0; i < matchBF.length; i++) {
            if (matchBF[i] != matchKMP[i]) return false;
        }
        return true;
    }
}
