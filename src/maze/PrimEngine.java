package maze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PrimEngine {
    private static final HashSet<String> visited = new HashSet<>();

    public static void run(MazeGraph graph) {
        visited.clear();
        visited.add(Utilities.getId(0, 0));

        int numberOfNodes = graph.getMaxX() * graph.getMaxY();

        while (visited.size() < numberOfNodes) {
            String[] edgeIds = getMinEdge(graph);
            String fromId = edgeIds[0];
            String toId = edgeIds[1];

            if (fromId.equals("")) break;
            graph.getNodeById(fromId).changeEdgeValue(toId, 100);
            graph.getNodeById(toId).changeEdgeValue(fromId, 100);

            visited.add(toId);
        }
        removeUnusedEdges(graph);
    }

    private static String[] getMinEdge(MazeGraph graph) {
        String fromId = "";
        String toId = "";
        int value = 101;
        for (String id : visited) {
            Node thisNode = graph.getNodeById(id);
            Set<String> thisAdjNodes = thisNode.getAdjNodeSet();

            for (String adjNodeId : thisAdjNodes) {
                if (visited.contains(adjNodeId)) continue;
                int thisValue = thisNode.getEdgeValue(adjNodeId);
                if (thisValue < value) {
                    value = thisValue;
                    fromId = id;
                    toId = adjNodeId;
                }
            }
        }
        return new String[]{fromId, toId};
    }

    private static void removeUnusedEdges(MazeGraph graph) {
        ArrayList<String> removeKeys = new ArrayList<>();
        for (Node[] row : graph.getGraph()) {
            for (Node node : row) {
                removeKeys.clear();

                for (String adjNodeId : node.getAdjNodeSet()) {
                    if (node.getEdgeValue(adjNodeId) == 100) {
                        node.changeEdgeValue(adjNodeId, 1);
                    } else {
                        removeKeys.add(adjNodeId);
                    }
                }

                removeKeys.forEach(node::removeEdge);
            }
        }
    }
}
