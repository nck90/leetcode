function recoverArray(nums: number[]): number[] {
    const n = nums.length / 2;
    nums.sort((u, v) => (u < v ? -1 : 1));
    const set: Set<number> = new Set(nums);

    const mark: number[] = new Array(n * 2).fill(0);
    
    for (let i = 1; i < n * 2; i++) {
        let k = nums[i] - nums[0];
        
        if (!set.has(nums[n * 2 - 1] - k)) {
            continue;
        }
        if (k === 0) {
            continue;
        }
        if (k % 2 !== 0) {
            continue;
        }
        

        for (let t = 0; t < n * 2; t++) {
            mark[t] = 0;
        }
        mark[0] = 1;
        mark[i] = 2;

        let pb = 0;
        while (pb < n * 2 && mark[pb] !== 0) {
            pb++;
        }
        mark[pb] = 1;

        for (let t = 1; t < n * 2; t++) {
            if (mark[t] !== 0) {
                continue;
            }
            if (nums[t] - nums[pb] === k) {
                mark[t] = 2;
                while (pb < n * 2 && mark[pb] !== 0) {
                    pb++;
                }
                mark[pb] = 1;
                continue;
            }
        }

        const valid = mark.findIndex((u) => u === 0) === -1;
        if (!valid) {
            continue;
        }
        
        const res = nums.filter((_, i) => mark[i] === 1).map((u) => u + Math.floor(k / 2));
        return res;
    }
    
    return [];
}