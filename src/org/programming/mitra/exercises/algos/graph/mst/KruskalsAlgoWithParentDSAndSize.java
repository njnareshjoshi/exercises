package org.programming.mitra.exercises.algos.graph.mst;

import java.util.Arrays;

public class KruskalsAlgoWithParentDSAndSize {
    public static void main(String[] args) {
        Graph graph = new Graph(6, 10);
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

        Graph MST = findMST(graph);
        Edge[] edges = MST.edges;

        System.out.println("Minimum spanning tree generated through KruskalsAlgo contains edges:");
        for (int i = 0; i < edges.length; i++) {
            System.out.println(edges[i]);
        }
    }


    private static Graph findMST(Graph graph) {
        Edge[] edges = graph.edges;
        Arrays.sort(edges);

        int[] parent = new int[graph.V];
        int[] size = new int[graph.V];
        for (int i = 0; i < graph.V; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        int counter = 0;
        Edge[] mstEdges = new Edge[graph.V - 1];
        for (int i = 0; i < graph.E; i++) {
            Edge edge = edges[i];

            int x = find(edge.from, parent);
            int y = find(edge.to, parent);
            if (x != y) {
                mstEdges[counter++] = edge;
                union(x, y, parent, size);
            }
        }

        return new Graph(graph.V, mstEdges);
    }

    private static void union(int x, int y, int[] parent, int[] size) {
        // Merging smaller sized tree into larger size tree
        // Summing up size of bigger tree by smaller tree
        // Marking size of smaller sized tree 0
        if (size[x] <= size[y]) {
            parent[x] = y;
            size[y] = size[y] + size[x];
            size[x] = 0;
        } else {
            parent[y] = x;
            size[x] = size[x] + size[y];
            size[y] = 0;
        }
    }

    private static int find(int x, int[] parent) {
        int temp = x;
        while (temp != parent[temp])
            temp = parent[temp];
        return temp;
    }

    private static class Graph {
        public int V, E;
        public Edge[] edges;

        public int size = 0;

        public Graph(int V, int E) {
            this.V = V;
            this.E = E;
            this.edges = new Edge[E];
        }

        public Graph(int V, Edge[] edges) {
            this.V = V;
            this.edges = edges;
            this.E = edges.length;
        }

        public void addEdge(int from, int to, int weight) {
            edges[size++] = new Edge(from, to, weight);
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
            return String.format("%d -> %d with weight=%d", from, to, weight);
        }
    }
}
