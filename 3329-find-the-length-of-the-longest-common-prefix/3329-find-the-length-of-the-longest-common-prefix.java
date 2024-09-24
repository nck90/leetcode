class Solution {
    private class TrieNode {
        TrieNode[] children = new TrieNode[10];
        boolean hasArr1 = false;
        boolean hasArr2 = false;
    }

    public int longestCommonPrefix(int[] arr1, int[] arr2) {
        TrieNode root = new TrieNode();
        for (int num : arr1) {
            TrieNode node = root;
            int n = num;
            int mult = 1;
            while (n / mult >= 10) {
                mult *= 10;
            }
            while (mult > 0) {
                int digit = n / mult;
                n %= mult;
                if (node.children[digit] == null) {
                    node.children[digit] = new TrieNode();
                }
                node = node.children[digit];
                node.hasArr1 = true;
                mult /= 10;
            }
        }

        for (int num : arr2) {
            TrieNode node = root;
            int n = num;
            int mult = 1;
            while (n / mult >= 10) {
                mult *= 10;
            }
            while (mult > 0) {
                int digit = n / mult;
                n %= mult;
                if (node.children[digit] == null) {
                    node.children[digit] = new TrieNode();
                }
                node = node.children[digit];
                node.hasArr2 = true;
                mult /= 10;
            }
        }

        return dfs(root, 0);
    }

    private int dfs(TrieNode node, int depth) {
        if (node == null) return 0;
        int maxDepth = 0;
        if (node.hasArr1 && node.hasArr2) {
            maxDepth = depth;
        }
        for (TrieNode child : node.children) {
            if (child != null) {
                maxDepth = Math.max(maxDepth, dfs(child, depth + 1));
            }
        }
        return maxDepth;
    }
}