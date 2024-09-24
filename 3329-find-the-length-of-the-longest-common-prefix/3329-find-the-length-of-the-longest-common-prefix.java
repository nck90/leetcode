class Solution {
    private class TrieNode {
        TrieNode[] children = new TrieNode[10];
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
                mult /= 10;
            }
        }
        int maxLength = 0;
        for (int num : arr2) {
            TrieNode node = root;
            int n = num;
            int mult = 1;
            while (n / mult >= 10) {
                mult *= 10;
            }
            int currentLength = 0;
            while (mult > 0) {
                int digit = n / mult;
                n %= mult;
                if (node.children[digit] == null) {
                    break;
                }
                node = node.children[digit];
                currentLength++;
                mult /= 10;
            }
            maxLength = Math.max(maxLength, currentLength);
        }
        return maxLength;
    }
}