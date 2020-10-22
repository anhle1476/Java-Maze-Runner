package maze;

import java.util.Random;

public class MazeGenerator {
    private final Random random = new Random();

    public MazeGraph createRandomGraph(int height, int width) {
        MazeGraph mazeGraph = new MazeGraph(height, width);

        Node[][] graph = mazeGraph.getGraph();
        int maxX = mazeGraph.getMaxX();
        int maxY = mazeGraph.getMaxY();

        mazeGraph.setEntrance(random.nextInt(maxX));
        mazeGraph.setExit(random.nextInt(maxX));

        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                generateRandomEdgeValue(graph, maxX, maxY, i, j);
            }
        }

        return mazeGraph;
    }

    void generateRandomEdgeValue(Node[][] graph, int maxX, int maxY, int i, int j) {
        if (i + 1 < maxY) {
            int value = random.nextInt(100);
            addEdgesPair(graph, i, j, i + 1, j, value);
        }
        if (j + 1 < maxX) {
            int value = random.nextInt(100);
            addEdgesPair(graph, i, j, i, j + 1, value);
        }
    }

    private void addEdgesPair(Node[][] graph, int thisI, int thisJ, int adjI, int adjJ, int value) {
        String thisNodeId = Utilities.getId(thisI, thisJ);
        String adjNodeId = Utilities.getId(adjI, adjJ);

        graph[thisI][thisJ].addEdge(adjNodeId, value);
        graph[adjI][adjJ].addEdge(thisNodeId, value);
    }
}
