package org.programming.mitra.exercises.algos.graph.mst;

import java.util.Arrays;

// Using adjacency matrix
public class PrimsAlgorithm {
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

        int[] mst = findMST(graph);
        System.out.println(Arrays.toString(mst));
        for (int i = 1; i < mst.length; i++) {
            System.out.printf("Edge %d -> %d with weight=%d%n", mst[i], i, graph.weight(mst[i], i));
        }
    }

    public static int[] findMST(Graph graph) {
        int[] currentCosts = new int[graph.V];
        int[] MST = new int[graph.V];
        int[] inMST = new int[graph.V];

        for (int i = 0; i < graph.V; i++) {
            currentCosts[i] = Integer.MAX_VALUE;
            inMST[i] = 0;
        }

        currentCosts[0] = 0;
        MST[0] = -1;

        for (int i = 0; i < graph.V; i++) {
            int x = findMinCostCrossingVertex(currentCosts, inMST);
            inMST[x] = 1;
            for (int y = 0; y < graph.V; y++) {
                int weight = graph.weight(x, y);
                if (inMST[y] == 0 && weight != 0 && weight < currentCosts[y]) {
                    currentCosts[y] = weight;
                    MST[y] = x;
                }
            }
        }

        return MST;
    }

    private static int findMinCostCrossingVertex(int[] currentCosts, int[] inMST) {
        int minCost = Integer.MAX_VALUE;
        int minCostVertex = Integer.MAX_VALUE;
        for (int v = 0; v < currentCosts.length; v++) {
            if (inMST[v] == 0 && currentCosts[v] < minCost) {
                minCost = currentCosts[v];
                minCostVertex = v;
            }
        }
        return minCostVertex;
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
