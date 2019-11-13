public class RadixSort implements Sort {

    @Override
    public long[] sort(long[] input) {
        //找出最大位数
        int maxD = 0;
        for (long l : input) {
            int d = 0;
            if (l == 0) continue;
            else {
                do {
                    d++;
                    l /= 10;
                } while (l != 0);
            }
            if (d > maxD) maxD = d;
        }
        //按位数进行排序
        for (int i = 0; i < maxD; i++) {
            //每一位进行计量排序
            int[] digitCount = new int[10];
            for (long l : input) {
                int digit = getDigit(l, i);
                digitCount[digit]++;
            }
            long[] tmp = new long[input.length];
            // 每一位的下标排到第几个了
            int[] digitIndex = new int[10];
            // 累加digitCount
            for (int j = 1; j < digitCount.length; j++) {
                digitCount[j] += digitCount[j - 1];
            }
            for (long l : input) {
                int digit = getDigit(l, i);
                int i1 = digit == 0 ? 0 : digitCount[digit - 1];
                int offset = digitIndex[digit];
                tmp[i1 + offset] = l;
                digitIndex[digit]++;
            }
            System.arraycopy(tmp, 0, input, 0, tmp.length);
        }
        return input;
    }

    private int getDigit(long l, int minD) {
        for (int i = 0; i < minD; i++) {
            l /= 10;
        }
        return (int) (l % 10);
    }

    @Override
    public String name() {
        return "基数排序";
    }
}
