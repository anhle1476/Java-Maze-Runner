package maze;

import java.util.Objects;

public class MazeRenderer {
    private final String WALL = "\u2588\u2588";
    private final String SPACE = "  ";
    private final String ESCAPE = "//";

    private void renderCell(String type) {
        System.out.print(type);
    }

    private void renderWalls(int times) {
        for (int i = 0; i < times; i++) {
            renderCell(WALL);
        }
    }

    private void renderRowWithNode(MazeGraph mazeGraph, boolean isRenderEscape, int maxX, int i, int j) {
        String thisId = Utilities.getId(i, j);
        String adjId = Utilities.getId(i, j + 1);
        if (isRenderEscape) {
            renderCell(mazeGraph.getNodeById(thisId).getIsOnWayOut() ? ESCAPE : SPACE);
        } else {
            renderCell(SPACE);
        }
        if (j + 1 >= maxX) return;
        renderCell(getCellType(mazeGraph, isRenderEscape, thisId, adjId));
    }

    private void renderRowWithEdge(MazeGraph mazeGraph, boolean isRenderEscape, int maxX, int i, int j) {
        String thisId = Utilities.getId(i, j);
        String adjId = Utilities.getId(i + 1, j);
        renderCell(getCellType(mazeGraph, isRenderEscape, thisId, adjId));
        if (j + 1 >= maxX) return;
        renderCell(WALL);
    }

    private String getCellType(MazeGraph mazeGraph, boolean isRenderEscape, String thisId, String adjId) {
        Node thisNode = mazeGraph.getNodeById(thisId);
        Integer edgeValue = thisNode.getEdgeValue(adjId);
        return Objects.isNull(edgeValue) ? WALL : isRenderEscape && edgeValue == 2 ? ESCAPE : SPACE;
    }

    private void renderWallWithGate(int width, int gate, boolean isRenderEscape) {
        int leftSideWidth = gate * 2 + 1;
        int rightSideWidth = width - leftSideWidth - 1;

        renderWalls(leftSideWidth);
        renderCell(isRenderEscape ? ESCAPE : SPACE);
        renderWalls(rightSideWidth);
        System.out.println();
    }

    public void render(MazeGraph mazeGraph, boolean isRenderEscape) {
        int width = mazeGraph.getWidth();
        int height = mazeGraph.getHeight();

        int maxY = mazeGraph.getMaxY();
        int maxX = mazeGraph.getMaxX();

        int rightWalls = width - (maxX * 2);
        int bottomWalls = height - (maxY * 2);

        renderWallWithGate(width, mazeGraph.getEntrance(), isRenderEscape);
        for (int i = 0; i < maxY; i++) {
            renderWalls(1);
            for (int j = 0; j < maxX; j++) {
                renderRowWithNode(mazeGraph, isRenderEscape, maxX, i, j);
            }
            renderWalls(rightWalls);
            System.out.println();

            if (i + 1 >= maxY) break;
            renderWalls(1);
            for (int j = 0; j < maxX; j++) {
                renderRowWithEdge(mazeGraph, isRenderEscape, maxX, i, j);
            }

            renderWalls(rightWalls);
            System.out.println();
        }

        for (int i = 0; i < bottomWalls; i++) {
            renderWallWithGate(width, mazeGraph.getExit(), isRenderEscape);
        }
    }


}
