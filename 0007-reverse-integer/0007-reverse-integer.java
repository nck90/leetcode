class Solution {
    public int reverse(int x) {
        int result = 0;
        
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            
            if (result > 214748364 || (result == 214748364 && pop > 7)) return 0;
            if (result < -214748364 || (result == -214748364 && pop < -8)) return 0;

            
            result = result * 10 + pop;
        }
        
        return result;
    }
}