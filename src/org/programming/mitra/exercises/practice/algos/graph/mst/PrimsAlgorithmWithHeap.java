package org.programming.mitra.exercises.practice.algos.graph.mst;

import java.util.PriorityQueue;

// Using adjacency matrix
public class PrimsAlgorithmWithHeap {
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

        Edge[] mstEdges = findMST(graph);
        for (int i = 0; i < mstEdges.length; i++) {
            System.out.println(mstEdges[i]);
        }

    }

    public static Edge[] findMST(Graph graph) {
        Edge[] MST = new Edge[graph.V - 1];
        int[] inMST = new int[graph.V];
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(graph.E);

        inMST[0] = 1;

        for (int v = 1; v < graph.V; v++) {
            int weight = graph.weight(0, v);
            if (weight != 0) {
                minHeap.add(new Edge(0, v, weight));
            }
        }

        int count = 0;
        while (count < graph.V - 1) {
            Edge edge = minHeap.remove();
            int x = edge.from;
            int y = edge.to;

            if (inMST[x] == 1 && inMST[y] == 0) {
                inMST[y] = 1;
                MST[count++] = edge;
                for (int z = 0; z < graph.V; z++) {
                    int weight = graph.weight(y, z);
                    if (inMST[z] == 0 && weight != 0) {
                        minHeap.add(new Edge(y, z, weight));
                    }
                }
            }
        }

        return MST;
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

    private static class Edge implements Comparable<Edge> {
        public int from, to, weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }

        @Override
        public String toString() {
            return String.format("Edge %d -> %d with weight=%d", from, to, weight);
        }
    }
}
