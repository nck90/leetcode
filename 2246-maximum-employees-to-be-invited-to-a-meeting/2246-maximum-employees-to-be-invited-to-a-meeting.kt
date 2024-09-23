class Solution {
    fun maximumInvitations(favorite: IntArray): Int {
        val indegrees = IntArray(favorite.size)

        for (node in indegrees.indices) {
            indegrees[favorite[node]] += 1
        }

        val indegreeQueue = LinkedList<Int>()
        val seenNodes = BooleanArray(indegrees.size)

        for (node in indegrees.indices) {
            if (indegrees[node] == 0) {
                indegreeQueue.offer(node)
                seenNodes[node] = true
            }
        }

        val maxInComingNodes = IntArray(favorite.size)

        while (indegreeQueue.isNotEmpty()) {
            val currentNode = indegreeQueue.poll() ?: continue
            val favNode = favorite[currentNode]

            maxInComingNodes[favNode] = maxOf(
                maxInComingNodes[favNode],
                1 + maxInComingNodes[currentNode]
            )

            if (seenNodes[favNode]) {
                continue
            }

            indegrees[favNode] -= 1

            if (indegrees[favNode] == 0) {
                indegreeQueue.offer(favNode)
                seenNodes[favNode] = true
            }
        }

        var result = 0
        var result2 = 0

        for (node in seenNodes.indices) {
            var currentNode = node
            var len = 0

            while (!seenNodes[currentNode]) {
                seenNodes[currentNode] = true
                currentNode = favorite[currentNode]
                len++
            }

            if (len == 2) {
                result2 += 2 + maxInComingNodes[favorite[node]] + maxInComingNodes[node]
            } else {
                result = maxOf(result, len)
            }
        }

        return maxOf(result, result2)
    }
}