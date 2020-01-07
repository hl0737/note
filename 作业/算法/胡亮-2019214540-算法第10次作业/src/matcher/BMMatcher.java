package matcher;

import tools.Tool;

import java.util.ArrayList;
import java.util.List;

public class BMMatcher extends Matcher {
    @Override
    public int[] matchInner(String txt, String pattern) {
        int[] bmBc = bmBc(pattern);
        int[] bmGs = bmGs(pattern);
        List<Integer> list = new ArrayList<>();
        int j = 0;
        while (j <= txt.length() - pattern.length()) {
            int i;
            for (i = pattern.length() - 1; i >= 0 && pattern.charAt(i) == txt.charAt(i + j); i--) ;
            if (i < 0) {
                list.add(j);
                j += bmGs[0];
            } else {
                int bc;
                if (txt.charAt(i + j) > 127) {
                    bc = 0;
                } else {
                    bc = bmBc[txt.charAt(i + j)] - pattern.length() + i + 1;
                }
                int gs = bmGs[i];
                j += Math.max(bc, gs);
            }
        }
        int[] r = Tool.listToInt(list);
        return r;
    }

    private int[] bmBc(String pattern) {
        int ASCII_NUM = 128;
        int[] r = new int[ASCII_NUM];
        for (int i = 0; i < ASCII_NUM; i++) {
            r[i] = pattern.length();
        }
        for (int i = 0; i < pattern.length() - 1; i++) {
            r[pattern.charAt(i)] = pattern.length() - i - 1;
        }
        return r;
    }

    private int[] bmGs(String pattern) {
        int i, j;
        int[] suffix = suffix(pattern);
        int m = pattern.length();
        int[] bmGs = new int[pattern.length()];
        // 1
        for (i = 0; i < m; i++)
            bmGs[i] = m;
        // 2
        j = 0;
        for (i = m - 1; i >= 0; i--) {
            if (i + 1 == suffix[i]) {
                for (; j < m - 1 - i; j++) {
                    if (m == bmGs[j])
                        bmGs[j] = m - 1 - i;
                }
            }
        }
        // 3
        for (i = 0; i < m - 1; i++)
            bmGs[m - 1 - suffix[i]] = m - 1 - i;
        return bmGs;
    }

    private int[] suffix(String pattern) {
        int[] r = new int[pattern.length()];
        r[pattern.length() - 1] = pattern.length();
        int q;
        for (int i = pattern.length() - 2; i >= 0; i--) {
            q = i;
            while (q >= 0 && pattern.charAt(q) == pattern.charAt(pattern.length() - 1 - i + q)) {
                q--;
            }
            r[i] = i - q;
        }
        return r;
    }
}
