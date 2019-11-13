public class MergeSort implements Sort {

    @Override
    public long[] sort(long[] input) {
        sortInner(input, 0, input.length - 1);
        return input;
    }

    private void sortInner(long[] input, int l, int r) {
        if (l == r) return;
        int mid = l + ((r - l) >> 1);
        sortInner(input, l, mid);
        sortInner(input, mid + 1, r);
        merge(input, l, mid, r);
    }

    private void merge(long[] input, int l, int mid, int r) {
        long[] tmp = new long[r - l + 1];
        int i = l;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= r) {
            tmp[k++] = input[i] < input[j] ? input[i++] : input[j++];
        }
        while (i <= mid) {
            tmp[k++] = input[i++];
        }
        while (j <= r) {
            tmp[k++] = input[j++];
        }
        System.arraycopy(tmp, 0, input, l, tmp.length);
    }

    @Override
    public String name() {
        return "归并排序";
    }
}
