class SegTree {
    long sum;
    int posMax;
    int l, r;
    SegTree left, right;

    public SegTree(int l, int r) {
        this.l = l;
        this.r = r;
    }
}

class BookMyShow {
    private long m;
    private long[] remain;
    private SegTree root;

    public BookMyShow(int n, int m) {
        this.m = m;
        this.remain = new long[n];
        for (int i = 0; i < n; i++) {
            remain[i] = m;
        }
        this.root = build(remain, 0, n - 1);
    }

    private SegTree build(long[] remain, int l, int r) {
        SegTree node = new SegTree(l, r);
        if (l == r) {
            node.sum = remain[l];
            node.posMax = l;
            return node;
        }
        int mid = (l + r) / 2;
        node.left = build(remain, l, mid);
        node.right = build(remain, mid + 1, r);
        fix(node, remain);
        return node;
    }

    private void fix(SegTree node, long[] remain) {
        node.sum = node.left.sum + node.right.sum;
        node.posMax = remain[node.left.posMax] >= remain[node.right.posMax] ? node.left.posMax : node.right.posMax;
    }

    private void update(SegTree node, long[] remain, int i) {
        if (node.l == node.r) {
            node.sum = remain[i];
            return;
        }
        int mid = (node.l + node.r) / 2;
        if (i <= mid) {
            update(node.left, remain, i);
        } else {
            update(node.right, remain, i);
        }
        fix(node, remain);
    }

    private int getGather(SegTree node, long[] remain, int l, int r, int k) {
        if (remain[node.posMax] < k) {
            return -1;
        }
        if (node.l == node.r) {
            return node.posMax;
        }
        int mid = (node.l + node.r) / 2;
        if (r <= mid) {
            return getGather(node.left, remain, l, r, k);
        }
        if (l >= mid + 1) {
            return getGather(node.right, remain, l, r, k);
        }
        int leftResult = getGather(node.left, remain, l, mid, k);
        if (leftResult != -1) {
            return leftResult;
        }
        return getGather(node.right, remain, mid + 1, r, k);
    }

    private long getScatter(SegTree node, int l, int r) {
        if (node.l == l && node.r == r) {
            return node.sum;
        }
        int mid = (node.l + node.r) / 2;
        if (r <= mid) {
            return getScatter(node.left, l, r);
        }
        if (l >= mid + 1) {
            return getScatter(node.right, l, r);
        }
        return getScatter(node.left, l, mid) + getScatter(node.right, mid + 1, r);
    }

    public int[] gather(int k, int maxRow) {
        int i = getGather(this.root, this.remain, 0, maxRow, k);
        if (i == -1) {
            return new int[]{};
        }
        int[] res = new int[]{i, (int) (this.m - this.remain[i])};
        this.remain[i] -= k;
        update(this.root, this.remain, i);
        return res;
    }

    public boolean scatter(int k, int maxRow) {
        long availableSeats = getScatter(this.root, 0, maxRow);
        if (availableSeats < k) {
            return false;
        }
        long remaining = k;
        for (int i = 0; i <= maxRow && remaining > 0; i++) {
            long allocatedSeats = Math.min(remaining, this.remain[i]);
            this.remain[i] -= allocatedSeats;
            remaining -= allocatedSeats;
            update(this.root, this.remain, i);
        }
        return true;
    }
}