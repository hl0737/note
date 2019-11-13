public class InsertionSort implements Sort {
    @Override
    public long[] sort(long[] input) {
        return sortDelta(input, 0, 1);
    }

    public long[] sortDelta(long[] input, int which, int delta) {
        for (int i = which; i < input.length; i += delta) {
            int j = i - delta;
            for (; j >= 0; j -= delta) {
                if (input[i] < input[j]) continue;
                else break;
            }
            // 移动j+delta到i-delta之间的元素
            long tmp = input[i];
            for (int k = i - delta; k > j; k -= delta) {
                input[k + delta] = input[k];
            }
            input[j + delta] = tmp;
        }
        return input;
    }

    @Override
    public String name() {
        return "插入排序";
    }
}
