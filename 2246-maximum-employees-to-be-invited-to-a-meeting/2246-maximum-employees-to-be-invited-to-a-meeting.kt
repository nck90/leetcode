class Solution {
    fun maximumInvitations(favorite: IntArray): Int {
        return maxOf(findMaxCycle(favorite), handleMutualPairs(favorite))
    }

    private fun findMaxCycle(favorites: IntArray): Int {
        val n = favorites.size
        val visited = BooleanArray(n)
        var maxCycleSize = 0

        for (startNode in 0 until n) {
            if (visited[startNode]) continue
            val cycleIndex = mutableMapOf<Int, Int>()
            var currentNode = startNode
            var step = 0

            while (!visited[currentNode]) {
                visited[currentNode] = true
                cycleIndex[currentNode] = step++
                currentNode = favorites[currentNode]
            }

            if (cycleIndex.contains(currentNode)) {
                val cycleStart = cycleIndex[currentNode]!!
                val cycleLength = step - cycleStart
                maxCycleSize = maxOf(maxCycleSize, cycleLength)
            }
        }

        return maxCycleSize
    }

    private fun handleMutualPairs(favorites: IntArray): Int {
        val n = favorites.size
        val inDegree = IntArray(n)
        val distance = IntArray(n)
        val visited = BooleanArray(n)

        for (i in favorites.indices) {
            inDegree[favorites[i]]++
        }

        val queue = ArrayDeque<Int>()
        for (i in 0 until n) {
            if (inDegree[i] == 0) {
                queue.add(i)
            }
        }

        while (queue.isNotEmpty()) {
            val currentNode = queue.removeFirst()
            val nextNode = favorites[currentNode]
            distance[nextNode] = maxOf(distance[nextNode], distance[currentNode] + 1)
            if (--inDegree[nextNode] == 0) {
                queue.add(nextNode)
            }
        }

        var totalPairChainLength = 0

        for (i in 0 until n) {
            if (i == favorites[favorites[i]] && i < favorites[i]) {
                totalPairChainLength += distance[i] + distance[favorites[i]] + 2
            }
        }

        return totalPairChainLength
    }
}