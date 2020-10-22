package maze;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class MazeRunner {
    LinkedList<String> runStack = new LinkedList<>();
    HashSet<String> visited = new HashSet<>();
    String[] result;
    String entranceId, exitId;

    public void run(MazeGraph mazeGraph) {
        if (mazeGraph.getIsSolved()) return;

        resetAttributes();

        entranceId = "0_" + mazeGraph.getEntrance();
        exitId =  (mazeGraph.getMaxY() - 1) + "_" + mazeGraph.getExit();

        System.out.println("entrance: " + entranceId + " - exit: " + exitId);
        searchNode(mazeGraph, entranceId);

        markWayOut(mazeGraph);
    }

    private void searchNode(MazeGraph mazeGraph, String id) {
        if (visited.contains(id)) return ;

        runStack.addLast(id);
        visited.add(id);

        if (id.equals(exitId)) {
            result = runStack.toArray(new String[0]);
            return;
        }

        Node thisNode = mazeGraph.getNodeById(id);
        Set<String> adjNodes = thisNode.getAdjNodeSet();

        for (String adjNodeId : adjNodes) {
            searchNode(mazeGraph, adjNodeId);
        }

        runStack.pollLast();
    }

    private void markWayOut(MazeGraph mazeGraph) {
        mazeGraph.setIsSolved();
        System.out.println("result: " + Arrays.toString(result));
        int last = result.length - 1;
        for (int i = 0; i <= last; i++) {
            Node thisNode = mazeGraph.getNodeById(result[i]);
            thisNode.setIsOnWayOut();

            if (i == last) break;
            thisNode.changeEdgeValue(result[i + 1], 2);
            mazeGraph.getNodeById(result[i + 1]).changeEdgeValue(result[i], 2);
        }
    }

    private void resetAttributes() {
        result = null;
        runStack.clear();
        visited.clear();
    }
}
