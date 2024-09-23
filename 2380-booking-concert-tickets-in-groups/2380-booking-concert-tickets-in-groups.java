class BookMyShow {
    private int n, m;
    private long[] seats;
    private long[] maxTree;
    private long[] sumTree;

    public BookMyShow(int n, int m) {
        this.n = n;
        this.m = m;
        this.seats = new long[n];
        this.maxTree = new long[4 * n];
        this.sumTree = new long[4 * n];
        for (int i = 0; i < n; i++) seats[i] = m;
        build(0, 0, n - 1);
    }

    private void build(int node, int start, int end) {
        if (start == end) {
            maxTree[node] = seats[start];
            sumTree[node] = seats[start];
        } else {
            int mid = (start + end) / 2;
            build(2 * node + 1, start, mid);
            build(2 * node + 2, mid + 1, end);
            maxTree[node] = Math.max(maxTree[2 * node + 1], maxTree[2 * node + 2]);
            sumTree[node] = sumTree[2 * node + 1] + sumTree[2 * node + 2];
        }
    }

    private void update(int node, int start, int end, int idx, int value) {
        if (start == end) {
            seats[start] -= value;
            maxTree[node] = seats[start];
            sumTree[node] = seats[start];
        } else {
            int mid = (start + end) / 2;
            if (idx <= mid) update(2 * node + 1, start, mid, idx, value);
            else update(2 * node + 2, mid + 1, end, idx, value);
            maxTree[node] = Math.max(maxTree[2 * node + 1], maxTree[2 * node + 2]);
            sumTree[node] = sumTree[2 * node + 1] + sumTree[2 * node + 2];
        }
    }

    private int queryMax(int node, int start, int end, int maxRow, int k) {
        if (start > maxRow || maxTree[node] < k) return -1;
        if (start == end) return start;
        int mid = (start + end) / 2;
        int left = queryMax(2 * node + 1, start, mid, maxRow, k);
        return left != -1 ? left : queryMax(2 * node + 2, mid + 1, end, maxRow, k);
    }

    private long querySum(int node, int start, int end, int l, int r) {
        if (start > r || end < l) return 0;
        if (start >= l && end <= r) return sumTree[node];
        int mid = (start + end) / 2;
        return querySum(2 * node + 1, start, mid, l, r) + querySum(2 * node + 2, mid + 1, end, l, r);
    }

    public int[] gather(int k, int maxRow) {
        int row = queryMax(0, 0, n - 1, maxRow, k);
        if (row == -1) return new int[]{};
        int startSeat = (int)(m - seats[row]);
        update(0, 0, n - 1, row, k);
        return new int[]{row, startSeat};
    }

    public boolean scatter(int k, int maxRow) {
        long availableSeats = querySum(0, 0, n - 1, 0, maxRow);
        if (availableSeats < k) return false;
        for (int r = 0; r <= maxRow && k > 0; r++) {
            if (seats[r] > 0) {
                int seatsToAllocate = (int)Math.min(k, seats[r]);
                update(0, 0, n - 1, r, seatsToAllocate);
                k -= seatsToAllocate;
            }
        }
        return true;
    }
}