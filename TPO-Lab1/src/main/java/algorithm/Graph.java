package algorithm;

import java.util.*;

public class Graph<T> {
    private final Map<T, List<T>> adjacencyList = new HashMap<>();

    public boolean containsVertex(T vertex) {
        return adjacencyList.containsKey(vertex);
    }

    public void addVertex(T vertex) {
        if(!containsVertex(vertex)) {
            adjacencyList.put(vertex, new LinkedList<>());
        }
        else {
            throw new IllegalArgumentException("Vertex already exists");
        }
    }

    public void addEdge(T source, T destination) {
        if(!containsVertex(source)) {
            addVertex(source);
        }
        if(!containsVertex(destination)) {
            addVertex(destination);
        }
        adjacencyList.get(source).add(destination);
        adjacencyList.get(destination).add(source);
    }

    public static <T> void depthFirstTraversal(Graph<T> graph, T start, List<T> visited) {
        visited.add(start);
        for(T neighbor : graph.adjacencyList.get(start)) {
            if(!visited.contains(neighbor)) {
                depthFirstTraversal(graph, neighbor, visited);
            }
        }
    }

    public static <T> List<T> depthFirstSearch(Graph<T> graph, T start) {
        List<T> visited = new ArrayList<>();
        if (!graph.containsVertex(start)) {
            throw new IllegalArgumentException("Vertex does not exist");
        }
        depthFirstTraversal(graph, start, visited);
        return visited;
    }

}
