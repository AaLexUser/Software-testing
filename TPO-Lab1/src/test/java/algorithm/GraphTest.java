package algorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static algorithm.Graph.depthFirstSearch;
import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    private Graph<String> graph;

    @BeforeEach
    void setUp() {
        graph = new Graph<>();
    }

    private Scanner getScanner(String path) throws FileNotFoundException {
        return new Scanner(new BufferedInputStream(new FileInputStream(path)));
    }

    @ParameterizedTest(name = "in={0}, out={1}")
    @CsvFileSource(
            files = "src/test/resources/paths.csv",
            numLinesToSkip = 1
    )
    void loadGraphGetCorrectTraversal(String inPath, String outPath)
            throws FileNotFoundException {

        final var in = getScanner(inPath);
        final var out = getScanner(outPath);

        final List<String> expected = new ArrayList<>();

        final var numOfEdges = in.nextInt();
        for (var i = 0; i < numOfEdges; i++) {
            graph.addEdge(in.next(), in.next());
        }
        final var numOfVertex = out.nextInt();
        for (var i = 0; i < numOfVertex; i++) {
            expected.add(out.next());
        }
        assertEquals(expected,  depthFirstSearch(graph, in.next()));
    }

    @ParameterizedTest(name = "vertex={0}")
    @ValueSource(strings = {
            "aaaa",
            "bbb",
            "ITMO",
            "hohohoho"
    })
    void AddVertexThatExistsThrowingIllegalArgumentException(String vertex){
        assertThrows(IllegalArgumentException.class, () -> {
            graph.addVertex(vertex);
            graph.addVertex(vertex);
        });
    }

    @ParameterizedTest(name = "startVertex={0}")
    @ValueSource(strings = {
            "aaaa",
            "bbb",
            "ITMO",
            "hohohoho"
    })
    void dfsWithStartVertexThatDontExistThrowingIllegalArgumentException(String startVertex)
           {
        assertThrows(IllegalArgumentException.class, () -> depthFirstSearch(graph, startVertex));
    }
}