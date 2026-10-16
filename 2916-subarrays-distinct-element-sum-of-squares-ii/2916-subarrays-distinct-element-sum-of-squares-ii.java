
class Solution {
    static final int MOD = 1_000_000_007;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        long ans = 0;

        long[] sum = new long[4 * n];
        long[] sq = new long[4 * n];
        long[] lazy = new long[4 * n];

        int[] last = new int[100001];
        java.util.Arrays.fill(last, -1);

        for (int i = 0; i < n; i++) {
            int left = last[nums[i]] + 1;
            update(1, 0, n - 1, left, i, 1, sum, sq, lazy);
            ans = (ans + sq[1]) % MOD;
            last[nums[i]] = i;
        }

        return (int) ans;
    }

    private void update(int node, int start, int end,
                        int l, int r, long val,
                        long[] sum, long[] sq, long[] lazy) {
        if (l > end || r < start) return;

        if (l <= start && end <= r) {
            sq[node] = (sq[node] + 2 * val * sum[node]
                    + val * val * (end - start + 1)) % MOD;
            sum[node] = (sum[node] + val * (end - start + 1)) % MOD;
            lazy[node] = (lazy[node] + val) % MOD;
            return;
        }

        int mid = start + (end - start) / 2;
        push(node, start, mid, end, sum, sq, lazy);

        update(node * 2, start, mid, l, r, val, sum, sq, lazy);
        update(node * 2 + 1, mid + 1, end, l, r, val, sum, sq, lazy);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
        sq[node] = (sq[node * 2] + sq[node * 2 + 1]) % MOD;
    }

    private void push(int node, int start, int mid, int end,
                      long[] sum, long[] sq, long[] lazy) {
        long val = lazy[node];
        if (val == 0) return;

        apply(node * 2, start, mid, val, sum, sq, lazy);
        apply(node * 2 + 1, mid + 1, end, val, sum, sq, lazy);
        lazy[node] = 0;
    }

    private void apply(int node, int start, int end, long val,
                       long[] sum, long[] sq, long[] lazy) {
        sq[node] = (sq[node] + 2 * val * sum[node]
                + val * val * (end - start + 1)) % MOD;
        sum[node] = (sum[node] + val * (end - start + 1)) % MOD;
        lazy[node] = (lazy[node] + val) % MOD;
    }
}
