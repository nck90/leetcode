class Solution {
    fun maximumInvitations(favorite: IntArray): Int {
        return maxOf(findMaxCycle(favorite), topologicalSort(favorite))
    }

    private fun findMaxCycle(favorites: IntArray): Int {
        val n = favorites.size
        val visited = BooleanArray(n)
        var maxCycleSize = 0

        for (startNode in 0 until n) {
            if (visited[startNode]) continue
            val cycle = mutableListOf<Int>()
            var currentNode = startNode

            while (!visited[currentNode]) {
                cycle.add(currentNode)
                visited[currentNode] = true
                currentNode = favorites[currentNode]
            }

            for (k in cycle.indices) {
                if (cycle[k] == currentNode) {
                    maxCycleSize = maxOf(maxCycleSize, cycle.size - k)
                    break
                }
            }
        }
        return maxCycleSize
    }

    private fun topologicalSort(favorites: IntArray): Int {
        val n = favorites.size
        val inDegree = IntArray(n)
        val distance = IntArray(n) { 1 }

        for (favorite in favorites) {
            inDegree[favorite]++
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

        var answer = 0
        for (i in 0 until n) {
            if (i == favorites[favorites[i]]) {
                answer += distance[i]
            }
        }
        return answer
    }
}