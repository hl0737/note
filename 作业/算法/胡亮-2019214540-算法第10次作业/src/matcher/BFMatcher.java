package matcher;

import tools.Tool;

import java.util.ArrayList;
import java.util.List;

public class BFMatcher extends Matcher {
    @Override
    public int[] matchInner(String txt, String pattern) {
        List<Integer> list = new ArrayList<>();
        int count = txt.length() - pattern.length() + 1;
        for (int i = 0; i < count; i++) {
            int j = 0;
            for (; j < pattern.length() && pattern.charAt(j) == txt.charAt(i + j); j++) ;
            if (j == pattern.length()) list.add(i);
        }
        int[] result = Tool.listToInt(list);
        return result.length == 0 ? new int[]{-1} : result;
    }
}