package maze;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Controller {
    MazeGraph mazeGraph = null;
    Scanner scanner;
    MazeGenerator mazeGenerator = new MazeGenerator();
    MazeRenderer mazeRenderer = new MazeRenderer();
    MazeRunner mazeRunner = new MazeRunner();
    boolean isRunning = true;

    private boolean isMazeLoaded() {
        return !Objects.isNull(mazeGraph);
    }

    public void start() {
        this.scanner = new Scanner(System.in);
        IOHandler ioHandler = new IOHandler(scanner);

        while (isRunning) {
            try {
                switch (getMainMenuCommand(scanner)) {
                    case 0:
                        isRunning = false;
                        System.out.println("Bye!");
                        scanner.close();
                        break;
                    case 1:
                        System.out.println("Enter the size of a new maze");
                        int size = Integer.parseInt(scanner.nextLine());

                        mazeGraph = mazeGenerator.createRandomGraph(size, size);
                        PrimEngine.run(mazeGraph);

                        mazeRenderer.render(mazeGraph, false);
                        break;
                    case 2:
                        mazeGraph = ioHandler.readMazeGraph();
                        break;
                    case 3:
                        ioHandler.writeGraph(mazeGraph);
                        break;
                    case 4:
                        mazeRenderer.render(mazeGraph, false);
                        break;
                    case 5:
                        mazeRunner.run(mazeGraph);
                        mazeRenderer.render(mazeGraph, true);
                        break;
                    default:
                        System.out.println("Error: Unexpected command.");
                }

            } catch (IOException e) {
                System.out.println("Writing Failed: " + e.getMessage());
            } catch (DecodeFailedException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Command Invalid. Please Try Again!");
            }

        }

    }

    private int getMainMenuCommand(Scanner scanner) {
        while (true) {
            System.out.println(
                    "\n=== Menu ===\n" +
                    "1. Generate a new maze\n2. Load a maze\n" +
                    (isMazeLoaded() ? "3. Save the maze\n4. Display the maze\n5. Find the escape.\n" : "") +
                    "0. Exit"
            );

            int commands = Integer.parseInt(scanner.nextLine());
            if (commands >= 0 && commands <= (isMazeLoaded() ? 5 : 2)) {
                return commands;
            }
        }
    }
}
