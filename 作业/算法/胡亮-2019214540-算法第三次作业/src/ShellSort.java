public class ShellSort implements Sort {
    @Override
    public long[] sort(long[] input) {
        InsertionSort sort = new InsertionSort();
        int delta = input.length >> 1;
        for (; delta > 0; delta = delta >> 1) {
            for (int i = 0; i < delta; i++) {
                sort.sortDelta(input, i, delta);
            }
        }
        return input;
    }

    @Override
    public String name() {
        return "希尔排序";
    }
}
