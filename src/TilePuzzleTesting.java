import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TilePuzzleTesting {

    public static void main(String[] args) {
        testTiles("tiles-testcases-ul.txt", new IterativeDeepeningAStarSearch());
    }

    static void testTiles(String filename, Search searcher) {
        double totalTime = 0.0;
        int numTests = 0;
        int previousPathLength = -1;
        List<String> inputLines = readFile(filename);
        for (String inputLine : inputLines) {
            String[] fields = inputLine.split(" ");
            int correctPathLength = Integer.parseInt(fields[0]);
            String[] startStateArrayStrings = fields[2].split(",");


            long startStateBits = Long.parseUnsignedLong(fields[3].replaceFirst("0x", ""), 16);
            int correctManhattanDistanceHeuristic = Integer.parseInt(fields[4]);
            String correctSolution = fields[5];

//            State startState = new FifteenPuzzleState(startStateArray);   // use a constructor that expects an array of 16 ints
            State startState = new FifteenPuzzleState(startStateBits);  // use a constructor that expects a single 64-bit long

            long startTime = System.nanoTime();
            Solution solution = searcher.search(startState);
            long endTime = System.nanoTime();
            double duration = (endTime-startTime) / 1e6;

            if (solution == null) {
                System.out.println("ERROR: No solution found for " + startState);
            } else {
                if (solution.getPathLength() != correctPathLength) {
                    System.out.println("ERROR: wrong path length for " + startState + " should be " + correctPathLength + " but got " + solution.getPathLength());
                }
            }

          // Check if your manhattan distance heuristic matches the correct values
          if (startState.heuristic() != correctManhattanDistanceHeuristic) {
              System.out.println("ERROR: wrong heuristic for " + startState + " should be " + correctManhattanDistanceHeuristic + " but got " + startState.heuristic());
          }

          // Check that your custom heuristic is never pessimistic
          if (startState.heuristic() > correctPathLength) {
              System.out.println("ERROR: pessimistic heuristic for " + startState + " heuristic " + startState.heuristic() + " should be less than " + correctPathLength);
          }

            if (previousPathLength != -1 && previousPathLength != correctPathLength) {
                displayAverageTime(searcher, previousPathLength, totalTime, numTests);
                totalTime = 0.0;
                numTests = 0;
            }

            totalTime += duration;
            numTests += 1;
            previousPathLength = correctPathLength;
        }
        displayAverageTime(searcher, previousPathLength, totalTime, numTests);
    }

    static List<String> readFile(String filename) {
        try {
            Path filepath = FileSystems.getDefault().getPath(filename);
            return Files.readAllLines(filepath);
        } catch (IOException ioexception) {
            throw new RuntimeException("Ack!  We had a problem reading the file: " + ioexception);
        }
    }

    static void displayAverageTime(Search searcher, int pathLength, double totalTime, int numTests) {
        double avgTime = totalTime / numTests;
        System.out.printf("Average time to solve puzzles using %s with path length %2d is %8.1f ms based on %2d test cases\n",
                searcher.toString(), pathLength, avgTime, numTests);
    }
}