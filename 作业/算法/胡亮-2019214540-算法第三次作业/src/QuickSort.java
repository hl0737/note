public class QuickSort implements Sort {
    @Override
    public long[] sort(long[] input) {
        quickSort(input, 0, input.length - 1);
        return input;
    }

    private void quickSort(long[] in, int l, int r) {
        if (l >= r) return;
        int index = onceQuickSort(in, l, r);
        quickSort(in, l, index - 1);
        quickSort(in, index + 1, r);
    }

    private int onceQuickSort(long[] in, int l, int r) {
        int i = l, j = r;
        while (i < j) {
            for (; in[j] >= in[l] && j > i; j--) ;
            for (; in[i] <= in[l] && i < j; i++) ;
            if (i < j) {
                long t = in[i];
                in[i] = in[j];
                in[j] = t;
            }
        }
        long tmp = in[l];
        in[l] = in[i];
        in[i] = tmp;
        return i;
    }

    @Override
    public String name() {
        return "快速排序";
    }
}
