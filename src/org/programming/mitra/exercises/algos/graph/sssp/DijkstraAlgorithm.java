package org.programming.mitra.exercises.algos.graph.sssp;

/**
 * Dijkstra's single source shortest path (sssp) algorithm to find single source shortest path in directed graph,
 * It works only on positive weight edges,
 * It might run exponentially if there is a negative weight edge or if there is a negative weight cycle.
 * It is a greedy algorithm and is somewhat similar to Prims MST algorithm.
 *
 * Time complexity = O(E.logV), In worst case E = V^2, Which wor case time complexity = O(V^2.logV)
 * Space complexity = O(E+V)
 *
 * @author Naresh Joshi
 */
public class DijkstraAlgorithm {

    public static void main(String[] args) {
        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 7);
        graph.addEdge(1, 2, 8);
        graph.addEdge(1, 3, 13);
        graph.addEdge(1, 4, 11);
        graph.addEdge(2, 3, 12);
        graph.addEdge(2, 4, 10);
        graph.addEdge(3, 4, 9);
        graph.addEdge(3, 5, 15);
        graph.addEdge(4, 5, 14);

        System.out.println("For below tree:");
        System.out.println(
                "               /0\\\n" +
                        "              /   \\ \n" +
                        "            4/     \\7\n" +
                        "            /   8   \\\n" +
                        "           1 ------- 2\n" +
                        "           | \\11  12/|\n" +
                        "         13|   \\  /  | 10\n" +
                        "           |   / \\   |\n" +
                        "           |  /   \\  |\n" +
                        "           | /     \\ |\n" +
                        "           3 ------- 4\n" +
                        "            \\   9   /\n" +
                        "           15\\     /14\n" +
                        "              \\   /\n" +
                        "               \\5/"
        );

        int source = 0;
        int[] dist = dijkstraShortestPath(graph, source);

        for (int i = 0; i < dist.length; i++) {
            System.out.println(String.format("Minimum distance of %d from source %d is %d", i, source, dist[i]));
        }
    }

    private static int[] dijkstraShortestPath(Graph graph, int source) {
        int[] dist = new int[graph.V];
        int[] parent = new int[graph.V];
        boolean[] inTree = new boolean[graph.V];

        for (int i = 0; i < graph.V; i++) {
            dist[i] = Integer.MAX_VALUE;
            parent[i] = -1;
            inTree[i] = false;
        }

        dist[source] = 0;

        for (int i = 0; i < graph.V - 1; i++) {

            int v = findMinDistance(dist, inTree);
            inTree[v] = true;

            for (int u = 0; u < graph.V; u++) {
                int w = graph.weight(v, u);
                if (!inTree[u] && w != 0 && (dist[v] + w < dist[u])) {
                    dist[u] = dist[v] + w;
                    parent[u] = v;
                }
            }
        }

        System.out.println("After running Dijkstra's algo, transformed tree below contains below edges: ");
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] != -1)
                System.out.println(String.format("Edge %d --> %d", parent[i], i));
        }

        return dist;
    }

    private static int findMinDistance(int[] dist, boolean[] inTree) {
        int minDistance = Integer.MAX_VALUE;
        int minDistanceVertex = 0;
        for (int v = 0; v < dist.length; v++) {
            if (!inTree[v] && dist[v] < minDistance) {
                minDistance = dist[v];
                minDistanceVertex = v;
            }
        }
        return minDistanceVertex;
    }

    private static class Graph {
        int V, E = 0;
        int[][] adjMatrix;

        public Graph(int V) {
            this.V = V;
            adjMatrix = new int[V][V];
        }

        public int weight(int from, int to) {
            return adjMatrix[from][to] != 0 ? adjMatrix[from][to] : adjMatrix[to][from];
        }

        public void addEdge(int from, int to, int weight) {
            E++;
            adjMatrix[from][to] = weight;
        }
    }
}
