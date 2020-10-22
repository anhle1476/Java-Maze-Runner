package maze;

import java.util.HashMap;
import java.util.Set;

public class Node {
    private HashMap<String, Integer> node = new HashMap<>();
    private String id;
    private boolean isOnWayOut = false;

    public Node(String id) {
        this.id = id;
    }

    public void addEdge(String adjNode, int value) {
        this.node.put(adjNode, value);
    }

    public Integer getEdgeValue(String adjNode) {
        return this.node.get(adjNode);
    }

    public Set<String> getAdjNodeSet() {
        return this.node.keySet();
    }

    public boolean getIsOnWayOut() { return isOnWayOut; }

    public void setIsOnWayOut() { isOnWayOut = true; }

    public void changeEdgeValue(String adjNode, int newValue) {
        this.node.replace(adjNode, newValue);
    }

    public void removeEdge(String adjNode) {
        node.remove(adjNode);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(id + ":");
        for (String edge : getAdjNodeSet()) {
            result.append(String.format(" {%s:%d}", edge, node.get(edge)));
        }
        return result.toString();
    }
}
