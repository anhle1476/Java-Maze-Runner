package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class IOHandler {
    private final Scanner cmdScanner;

    public IOHandler(Scanner cmdScanner) {
        this.cmdScanner = cmdScanner;
    }

    private void encodeNode(StringBuilder buffer, Node node) {
        for (String adjNode : node.getAdjNodeSet()) {
            buffer.append(adjNode).append("-");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        buffer.append("\n");
    }

    private void decodeNode(String data, Node node) throws DecodeFailedException {
        String[] adjNodes = data.split("-");
        if (adjNodes.length == 0) {
            throw new DecodeFailedException("Node Format Invalid: ", data);
        }
        for (String adjNode : adjNodes) {
            node.addEdge(adjNode, 1);
        }
    }

    private int[] getMetaData(Scanner scanner) throws DecodeFailedException {
        String line = scanner.nextLine();
        String[] arr = line.split("-");
        if (arr.length != 2) {
            throw new DecodeFailedException("Meta Data Format Invalid: ", line);
        }
        return new int[]{Integer.parseInt(arr[0]), Integer.parseInt(arr[1])};
    }

    public void writeGraph(MazeGraph mazeGraph) throws IOException {
        System.out.println("write");
        System.out.println("[Writing] Enter File Path:");
        File file = new File(cmdScanner.nextLine());

        try (FileWriter writer = new FileWriter(file)) {
            StringBuilder buffer = new StringBuilder();

            buffer
                    .append(mazeGraph.getHeight())
                    .append("-")
                    .append(mazeGraph.getWidth())
                    .append("\n")
                    .append(mazeGraph.getEntrance())
                    .append("-")
                    .append(mazeGraph.getExit())
                    .append("\n");

            for (Node[] row : mazeGraph.getGraph()) {
                for (Node node : row) {
                    encodeNode(buffer, node);
                }
            }

            writer.write(buffer.toString());
        }
    }

    public MazeGraph readMazeGraph() throws DecodeFailedException {
        System.out.println("read");
        System.out.println("[Reading] Enter File Path:");
        String filePath = cmdScanner.nextLine();
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(file)) {
             int[] size = getMetaData(scanner);
            MazeGraph mazeGraph = new MazeGraph(size[0], size[1]);

            int[] gates = getMetaData(scanner);
            mazeGraph.setEntrance(gates[0]);
            mazeGraph.setExit(gates[1]);

            int maxX = mazeGraph.getMaxX();
            int maxY = mazeGraph.getMaxY();
            Node[][] graph = mazeGraph.getGraph();

            for (int i = 0; i < maxY; i++) {
                for (int j = 0; j < maxX; j++) {
                    decodeNode(scanner.nextLine(), graph[i][j]);
                }
            }

            return mazeGraph;
        } catch (FileNotFoundException e) {
            throw new DecodeFailedException("File not found: ", filePath, e);
        } catch (NullPointerException e) {
            throw new DecodeFailedException("Invalid format: Wrong number of lines. ", e.getMessage());
        }
    }
}
