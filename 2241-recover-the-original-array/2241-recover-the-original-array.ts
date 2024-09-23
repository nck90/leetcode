function recoverArray(nums: number[]): number[] {
    nums.sort((a, b) => a - b);
    const n = nums.length / 2;
    
    for (let j = 1; j < nums.length; j++) {
        const possibleK = (nums[j] - nums[0]) / 2;
        
        if (possibleK <= 0 || (nums[j] - nums[0]) % 2 !== 0) {
            continue;
        }
        
        const countMap = new Map<number, number>();
        for (let num of nums) {
            countMap.set(num, (countMap.get(num) || 0) + 1);
        }
        
        const originalArray: number[] = [];
        let valid = true;
        
        for (let i = 0; i < nums.length; i++) {
            const x = nums[i];
            if (!countMap.get(x)) continue;
            
            const y = x + 2 * possibleK;
            if (!countMap.get(y)) {
                valid = false;
                break;
            }
            
            originalArray.push(x + possibleK);
            countMap.set(x, countMap.get(x)! - 1);
            countMap.set(y, countMap.get(y)! - 1);
        }
        
        if (valid) return originalArray;
    }
    
    return [];
}