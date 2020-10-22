package maze;

public class MazeGraph {
    private final Node[][] graph;
    private int width, height;
    private int maxX, maxY;
    private int entrance, exit;
    private boolean isSolved = false;

    public MazeGraph(int height, int width) {
        setSize(height, width);
        graph = new Node[maxY][maxX];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                graph[i][j] = new Node(Utilities.getId(i, j));
            }
        }
    }

    public Node[][] getGraph() {
        return this.graph;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getEntrance() {
        return entrance;
    }

    public int getExit() {
        return exit;
    }

    public boolean getIsSolved() {
        return isSolved;
    }

    private void setSize(int height, int width) {
        this.height = height;
        this.width = width;

        this.maxY = (height - 1) / 2;
        this.maxX = (width - 1) / 2;
    }

    public void setEntrance(int entrance) {
        this.entrance = entrance;
    }

    public void setExit(int exit) {
        this.exit = exit;
    }

    public void setIsSolved() {
        isSolved = true;
    }

    public Node getNodeById(String id) {
        String[] pos = id.split("_");
        int y = Integer.parseInt(pos[0]);
        int x = Integer.parseInt(pos[1]);
        return (x >= width || y >= height) ? null : this.graph[y][x];
    }
}
