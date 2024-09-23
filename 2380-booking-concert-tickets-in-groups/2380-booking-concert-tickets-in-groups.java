class BookMyShow {
    private IntervalTree tree;

    public BookMyShow(int n, int m) {
        tree = new IntervalTree(n - 1, m);
    }

    public int[] gather(int k, int maxRow) {
        return tree.BookGather(k, maxRow);
    }

    public boolean scatter(int k, int maxRow) {
        return tree.BookScatter(k, maxRow);
    }
}


class IntervalTreeNode {
    private int start;
    private int end;
    private int mid;
    private int capacity;
    private int max;
    private long total;
    private IntervalTreeNode left;
    private IntervalTreeNode right;

    public IntervalTreeNode(int start, int end, int capacity) {
        this.start = start;
        this.end = end;
        this.mid = (start + end) / 2;
        this.capacity = capacity;
        this.max = capacity;
        this.total = (end - start + 1) * (long)capacity;

        if (start != end) {
            this.left = new IntervalTreeNode(start, mid, capacity);
            this.right = new IntervalTreeNode(mid + 1, end, capacity);
        }
    }

    public int[] BookGather(int number, int maxRow) {
        if (start != end) {
            int[] res = new int[0];

            if (this.left.max >= number) {
                res = this.left.BookGather(number, maxRow);
            } else if (maxRow >= mid + 1 && this.right.max >= number) {
                res = this.right.BookGather(number, maxRow);
            }

            if (res.length > 0) {
                this.total = left.total + right.total;
                this.max = Math.max(this.left.max, this.right.max);
            }

            return res;
        } else {
            if (this.total >= number) {
                int[] booking = new int[]{start, capacity - max};
                this.max -= number;
                this.total -= number;
                return booking;
            } else {
                return new int[0];
            }
        }
    }

    public long CheckScatter(int maxRow) {
        if (start == end || maxRow == end) {
            return total;
        } else {
            if (maxRow <= mid) {
                return left.CheckScatter(maxRow);
            }
            return left.total + right.CheckScatter(maxRow);
        }
    }

    public void BookScatter(long number) {
        if (start != end) {
            if (this.left.total < number) {
                this.right.BookScatter(number - this.left.total);
            }
            this.left.BookScatter(Math.min(this.left.total, number));
            this.max = Math.max(left.max, right.max);
            this.total = left.total + right.total;
        } else {
            this.total -= number;
            this.max -= number;
        }
    }
}

class IntervalTree {
    private IntervalTreeNode root;

    public IntervalTree(int end, int capacity) {
        root = new IntervalTreeNode(0, end, capacity);
    }

    public int[] BookGather(int number, int maxRow) {
        return root.BookGather(number, maxRow);
    }

    public boolean BookScatter(int number, int maxRow) {
        if (root.CheckScatter(maxRow) >= number) {
            root.BookScatter(number);
            return true;
        }
        return false;
    }
}