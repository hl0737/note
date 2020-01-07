package matcher;

import tools.Tool;

import java.util.ArrayList;
import java.util.List;

public class KMPMatcher extends Matcher {
    @Override
    public int[] matchInner(String txt, String pattern) {
        List<Integer> result = new ArrayList<>();
        int[] next = calNext(pattern);
        int len1 = txt.length();
        int len2 = pattern.length();
        char[] t = txt.toCharArray();
        char[] p = pattern.toCharArray();
        for (int i = 0, j = 0; i < len1; i++) {
            while (j > 0 && t[i] != p[j]) {
                j = next[j - 1];
            }
            if (t[i] == p[j])
                j++;
            if (j == len2) {
                result.add(i - j + 1);
                j = 0;
            }
        }
        int[] r = Tool.listToInt(result);
        return r;
    }

    private int[] calNext(String pattern) {
        char[] s = pattern.toCharArray();
        int next[] = new int[pattern.length()];
        int len = pattern.length();
        next[0] = 0;
        for (int i = 1, k = 0; i < len; i++) {
            while (k > 0 && s[k] != s[i])
                k = next[k - 1];
            if (s[k] == s[i])
                k++;
            next[i] = k;
        }
        return next;
    }
}
