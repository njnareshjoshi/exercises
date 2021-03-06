package org.programming.mitra.exercises.algos.graph.mst;

import java.util.Arrays;

public class KruskalsAlgoWithNameDS {
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

        Graph MST = findMST(graph);
        Edge[] edges = MST.edges;

        System.out.println("Number of edge for minimum spanning is=" + edges.length);
        for (int i = 0; i < edges.length; i++) {
            System.out.println(edges[i]);
        }
    }


    private static Graph findMST(Graph graph) {
        Edge[] edges = graph.edges;
        Arrays.sort(edges);

        int[] name = new int[graph.V];
        for (int i = 0; i < name.length; i++) {
            name[i] = i;
        }

        int counter = 0;
        Edge[] mstEdges = new Edge[graph.V - 1];
        for (int i = 0; i < graph.E; i++) {
            Edge edge = edges[i];

            int x = find(edge.from, name);
            int y = find(edge.to, name);
            if (x != y) {
                mstEdges[counter++] = edge;
                union(x, y, name);
            }
        }

        return new Graph(graph.V, mstEdges);
    }

    private static void union(int x, int y, int[] name) {
        if (x < y) {
            for (int i = 0; i < name.length; i++) {
                if (name[i] == y)
                    name[i] = x;
            }
        } else {
            for (int i = 0; i < name.length; i++) {
                if (name[i] == x)
                    name[i] = y;
            }
        }
    }

    private static int find(int x, int[] name) {
        return name[x];
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
            return String.format("Edge %d -> %d with weight=%d", from, to, weight);
        }
    }
}
