package org.programming.mitra.exercises.algos.graph.dag;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

/**
 * TopologicalSort And LongestPathCalculation
 * 1. Generate a DAG with random vertices, edges and  weights
 * 2. Perform Topological Sort on DAG
 * 3. Calculate longest path for weighted and non-weighted DAG
 *
 * @author Naresh Joshi
 */
public class TopologicalSortAndLongestPath {
    public static void main(String[] args) {

        // Test 1
        // 1. Generate a DAG with random vertices, edges and  weights
        DAG_UsingAdjacencyMatrix randomDagObj = new DAG_UsingAdjacencyMatrix(5);
        randomDagObj.generateRandomDAG(5);
        randomDagObj.printDag();

        // 2. Perform Topological Sort on DAG
        randomDagObj.topologicalSort();
        randomDagObj.topologicalSortRecursive();

        // 3. Calculate longest path for weighted and non-weighted DAG
        randomDagObj.findLongestPath();


        // Test 2
        DAG_UsingAdjacencyMatrix randomDagObj2 = new DAG_UsingAdjacencyMatrix(100);
        randomDagObj2.generateRandomDAG(90);
        randomDagObj2.printDag();

        randomDagObj2.topologicalSort();
        randomDagObj2.topologicalSortRecursive();

        randomDagObj2.findLongestPath();

        // Test 3 - Without random DAG
        /*
          dagObj represents below graph
          0, 1, 0, 1, 0, 0
          0, 0, 1, 1, 0, 0
          0, 0, 0, 1, 1, 1
          0, 0, 0, 0, 1, 1
          0, 0, 0, 0, 1, 0
          0, 0, 0, 0, 0, 0
         */
        DAG_UsingAdjacencyMatrix dagObj = new DAG_UsingAdjacencyMatrix(6);
        dagObj.addEdge(0, 1);
        dagObj.addEdge(0, 3);
        dagObj.addEdge(1, 2);
        dagObj.addEdge(1, 3);
        dagObj.addEdge(2, 3);
        dagObj.addEdge(2, 4);
        dagObj.addEdge(2, 5);
        dagObj.addEdge(3, 4);
        dagObj.addEdge(3, 5);
        dagObj.addEdge(5, 4);

        dagObj.printDag();
        dagObj.topologicalSort(); // It should like [0, 1, 2, 3, 5, 4]
        dagObj.topologicalSortRecursive(); // It should like [0, 1, 2, 3, 5, 4]
        dagObj.findLongestPath(); // Equal to 5

        // Test 4 - Without random DAG with weights on edges
        /*
          weightedDagObj represents below graph
          0, 2, 0, 1, 0, 0
          0, 0, 2, 1, 0, 0
          0, 0, 0, 1, 1, 5
          0, 0, 0, 0, 1, 1
          0, 0, 0, 0, 0, 1
          0, 0, 0, 0, 0, 0
         */
        DAG_UsingAdjacencyMatrix weightedDagObj = new DAG_UsingAdjacencyMatrix(6);
        weightedDagObj.addEdge(0, 1, 2);
        weightedDagObj.addEdge(0, 3, 1);
        weightedDagObj.addEdge(1, 2, 2);
        weightedDagObj.addEdge(1, 3, 1);
        weightedDagObj.addEdge(2, 3, 1);
        weightedDagObj.addEdge(2, 4, 1);
        weightedDagObj.addEdge(2, 5, 5);
        weightedDagObj.addEdge(3, 4, 1);
        weightedDagObj.addEdge(3, 5, 1);
        weightedDagObj.addEdge(4, 5, 1);

        weightedDagObj.printDag();
        weightedDagObj.findLongestPath();
    }
}

class DAG_UsingAdjacencyMatrix {

    private final int[][] adjMatrix;

    // Will be used to construct adjacency matrix of inputted verticesSize.
    public DAG_UsingAdjacencyMatrix(int verticesSize) {
        System.out.println();
        System.out.println("Generating a DAG with " + verticesSize + " vertices.");
        adjMatrix = new int[verticesSize][verticesSize];
    }

    public void generateRandomDAG(int edgeSize) {
        int verticesSize = adjMatrix.length;

        // Creating edges with a random vertex and random weight
        for (int i = 0; i < edgeSize; i++) {
            int randomFromVertex = ThreadLocalRandom.current().nextInt(0, verticesSize);
            int randomToVertex = ThreadLocalRandom.current().nextInt(randomFromVertex, verticesSize);
            int randomWeight = ThreadLocalRandom.current().nextInt(0, 10);
            addEdge(randomFromVertex, randomToVertex, randomWeight);
        }
    }

    public void printDag() {
        // Printing randomly generated adjacency matrix.
        System.out.println("Generated Adjacency matrix");
        for (int[] matrix : adjMatrix) {
            System.out.println(Arrays.toString(matrix));
        }
    }

    // Method will add an edge to DAG for inputted vertices with default weight of 1.
    public void addEdge(int vertex1, int vertex2) {
        addEdge(vertex1, vertex2, 1);
    }

    // Method will add an edge to DAG for input vertices with inputted weight.
    public void addEdge(int vertex1, int vertex2, int weight) {
        // Validations to check if we are not creating cycles in our DAG
        if (vertex1 == vertex2) {
            System.out.println(String.format("Can not add edge %s->%s because it will form a cycle.", vertex1, vertex2));
        } else if (adjMatrix[vertex2][vertex1] != 0) {
            System.out.println(String.format("Can not add edge %s->%s because and opposite edge %s->%s already exists.", vertex1, vertex2, vertex2, vertex1));
        } else {
            adjMatrix[vertex1][vertex2] = weight;
        }
    }

    // Method will return a copy of adjacency matrix.
    public int[][] getAdjMatrix() {
        return adjMatrix.clone();
    }

    // Method will topologically sort our dag and return vertices in a sorted array.
    public int[] topologicalSort() {
        int length = adjMatrix.length;
        int[] result = new int[length];
        int[] visited = new int[length];
        int[] inDegree = new int[length];
        Queue<Integer> queue = new LinkedList<>();

        // Calculating in_degree of all vertices available within the DAG.
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                // If there is an edge from vertex i to vertex j then increasing in_degree of vertex j.
                if (adjMatrix[i][j] != 0) {
                    inDegree[j] = inDegree[j] + 1;
                }
            }
        }

        // Adding all vertices with in_degree == 0 to queue because these vertices are our starting point for topological order.
        // Also adding the nodes which we have visited to a visited array so we do not visit it again.
        for (int i = 0; i < length; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
                visited[i] = 1;
            }
        }

        int i = 0;
        // Looping through the while it is not empty and doing below mentioned steps:
        while (!queue.isEmpty()) {
            // 1. Removing a vertex from queue and adding it to result array.
            int vertex = queue.remove();
            result[i++] = vertex;

            // 2. Iterating through the adjacency matrix and picking vertices which
            for (int j = 0; j < length; j++) {
                // 2.1. Have an edge from vertex to it and,
                // 2.2. Which are not in the visited array.
                if (adjMatrix[vertex][j] != 0 && visited[j] == 0) {

                    // 3. Decreasing in_degree of those vertices
                    inDegree[j] = inDegree[j] - 1;

                    // 4. If decreased in_degree of the any of these vertices is 0 then putting those into queue and visited array,
                    //    Because now they are the next nodes which need to be picked for topological ordering.
                    if (inDegree[j] == 0) {
                        queue.add(j);
                        visited[j] = 1;
                    }
                }
            }
        }

        // Printing the final topological ordering and returning it.
        System.out.println("Topological ordering " + Arrays.toString(result));
        return result;
    }

    // Method will recursively topological sort our dag and return vertices in a sorted array.
    public int[] topologicalSortRecursive() {
        int length = adjMatrix.length;
        int[] visited = new int[length];
        Stack<Integer> stack = new Stack<>();
        // Iterating through all the vertices staring with 0.
        for (int i = 0; i < length; i++) {
            if (visited[i] == 0) {
                // Calling supporting recursive method for all unvisited vertices.
                topologicalSortRecursive(adjMatrix, length, i, visited, stack);
            }
        }

        int count = 0;
        int[] result = new int[length];
        // After all recursive call completion, stack will be storing the topological ordering in reverse order.
        // So popping from stack and putting it our result array.
        while (!stack.isEmpty()) {
            result[count++] = stack.pop();
        }

        // Printing the final topological ordering and returning it.
        System.out.println("Recursively calculated topological ordering " + Arrays.toString(result));
        return result;
    }

    private void topologicalSortRecursive(int[][] dag, int length, int currentVertex, int[] visited, Stack<Integer> stack) {
        // Marking currentVertex as visited.
        visited[currentVertex] = 1;
        for (int j = 0; j < length; j++) {
            if (dag[currentVertex][j] != 0 && visited[j] == 0) {
                // If there is an edge from currentVertex to vertex j and vertex j is not visited yet then calculating its topological ordering recursively.
                topologicalSortRecursive(dag, length, j, visited, stack);
            }
        }

        // After all recursive calls to generate topological ordering of currentVertex finishes, pushing currentVertex into stack
        stack.push(currentVertex);
    }

    // Method to find longest path for the dag
    public int findLongestPath() {
        int length = adjMatrix.length;
        int[] inDegree = new int[length];
        int[] path = new int[length];

        // Calculating in_degree of all vertices available within the DAG.
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                // If there is an edge from vertex i to vertex j then increasing in_degree of vertex j.
                // dag[i][j] holds the weight for edge i --> j.
                if (adjMatrix[i][j] != 0) {
                    inDegree[j] = inDegree[j] + 1;
                }
            }
        }

        int longestPath = 0;
        // Iterating through all the vertices staring with 0.
        for (int i = 0; i < length; i++) {
            if (inDegree[i] == 0) {
                // Calling supporting recursive method for all unvisited vertices, supporting will store longest path for vertex i at path[i]
                findLongestPath(adjMatrix, length, i, path);

                // If longest path for vertex i at path[i] is greater then longestPath then putting path[i] into longestPath.
                longestPath = Math.max(longestPath, path[i]);
            }
        }

        System.out.println("Longest path " + longestPath);

        return longestPath;
    }

    private void findLongestPath(int[][] dag, int length, int currentVertex, int[] path) {
        for (int j = 0; j < length; j++) {
            // If there is an edge from currentVertex to vertex j and vertex j with some weight.
            if (dag[currentVertex][j] != 0) {
                // Then calculating longest path for j its topological ordering recursively.
                findLongestPath(dag, length, j, path);
                // Updating longest path for currentVertex stored at path[currentVertex] if it is less then (weight of currentVertex --> j + longest path for j)
                path[currentVertex] = Math.max(path[currentVertex], dag[currentVertex][j] + path[j]);
            }
        }
    }

}