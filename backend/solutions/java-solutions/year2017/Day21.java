package year2017;

import java.util.ArrayList;
import java.util.List;

public class Day21 {
    record Grid(List<List<Boolean>> grid) {}
    record Rule(List<Grid> inputs, Grid output, int size) {}

    public static void main(String[] args) {
        //Part one
        List<String> input = Utils.readLines("21.txt");
        List<Rule> rules = input.stream().map(Day21::getRule).toList();
        Grid grid = getGrid(".#./..#/###");

        for (int i = 0; i < 5; i++) {
            if (grid.grid.size() % 2 == 0) {
                List<List<Grid>> squares = split(grid, 2);
                squares = squares.stream().map(line -> line.stream().map(split -> getOutput(split, rules)).toList()).toList();
                grid = concat(squares);
            } else if (grid.grid.size() % 3 == 0) {
                List<List<Grid>> squares = split(grid, 3);
                squares = squares.stream().map(line -> line.stream().map(split -> getOutput(split, rules)).toList()).toList();
                grid = concat(squares);
            } else {
                grid = getOutput(grid, rules);
            }
        }

        System.out.println(grid.grid.stream()
                .mapToLong(girdLine -> girdLine.stream()
                        .filter(pixel -> pixel)
                        .count()).sum());


        //Part two
        grid = getGrid(".#./..#/###");

        for (int i = 0; i < 18; i++) {
            if (grid.grid.size() % 2 == 0) {
                List<List<Grid>> squares = split(grid, 2);
                squares = squares.stream().map(line -> line.stream().map(split -> getOutput(split, rules)).toList()).toList();
                grid = concat(squares);
            } else if (grid.grid.size() % 3 == 0) {
                List<List<Grid>> squares = split(grid, 3);
                squares = squares.stream().map(line -> line.stream().map(split -> getOutput(split, rules)).toList()).toList();
                grid = concat(squares);
            } else {
                grid = getOutput(grid, rules);
            }
        }

        System.out.println(grid.grid.stream()
                .mapToLong(girdLine -> girdLine.stream()
                        .filter(pixel -> pixel)
                        .count()).sum());
    }

    private static Rule getRule(String rule) {
        String[] splitRule = rule.split(" => ");

        Grid inputGrid = getGrid(splitRule[0]);
        Grid outputGrid = getGrid(splitRule[1]);

        return new Rule(getVariations(inputGrid), outputGrid, inputGrid.grid.size());

    }

    private static Grid getGrid(String gridString) {
        String[] split = gridString.split("/");
        List<List<Boolean>> grid = new ArrayList<>();

        for (String line : split) {
            List<Boolean> gridLine = new ArrayList<>();
            for (char pixel : line.toCharArray()) {
                gridLine.add(pixel == '#');
            }
            grid.add(gridLine);
        }

        return new Grid(grid);
    }

    private static List<Grid> getVariations(Grid grid) {
        List<Grid> variations = new ArrayList<>();
        variations.add(grid);

        //rotations
        for (int i = 0; i < 3; i++) {
            variations.add(rotate(variations.get(i)));
        }

        //flipped
        variations.add(flip(grid));
        for (int i = 4; i < 7; i++) {
            variations.add(rotate(variations.get(i)));
        }

        return variations;
    }

    private static Grid rotate(Grid grid) {
        List<List<Boolean>> rotated = new ArrayList<>();
        int size = grid.grid.size();

        //0,0 0,1 0,2
        //1,0 1,1 1,2
        //2,0 2,1 2,2

        // ==>

        //2,0 1,0 0,0
        //2,1 1,1 0,1
        //2,2 1,2 0,2

        for (int i = 0; i < size; i++) {
            List<Boolean> row = new ArrayList<>();

            for (int j = size - 1; j >= 0; j--) {
                row.add(grid.grid.get(j).get(i));
            }

            rotated.add(row);
        }
        return new Grid(rotated);
    }

    private static Grid flip(Grid grid) {
        List<List<Boolean>> flipped = new ArrayList<>();

        //0,0 0,1 0,2
        //1,0 1,1 1,2
        //2,0 2,1 2,2

        // ==>

        //2,0 2,1 2,2
        //1,0 1,1 1,2
        //0,0 0,1 0,2

        for (int i = grid.grid.size() - 1; i >= 0; i--) {
            List<Boolean> newRow = new ArrayList<>(grid.grid.get(i));
            flipped.add(newRow);
        }

        return new Grid(flipped);
    }

    private static List<List<Grid>> split(Grid grid, int n) {
        //n = 2
        //0,0 0,1 0,2 0,3 0,4 0,5
        //1,0 1,1 1,2 1,3 1,4 1,5
        //2,0 2,1 2,2 2,3 2,4 2,5
        //3,0 3,1 3,2 3,3 3,4 3,5
        //4,0 4,1 4,2 4,3 4,4 4,5
        //5,0 5,1 5,2 5,3 5,4 5,5

        // =>

        //0,0 0,1   //0,2 0,3   //0,4 0,5
        //1,0 1,1   //1,2 1,3   //1,4 1,5

        //2,0 2,1   //2,2 2,3   //2,4 2,5
        //3,0 3,1   //3,2 3,3   //3,4 3,5

        //4,0 4,1   //4,2 4,3   //4,4 4,5
        //5,0 5,1   //5,2 5,3   //5,4 5,5

        List<List<Grid>> squares = new ArrayList<>();
        for (int i = 0; i < grid.grid.size() ; i += n) {

            List<Grid> squaresLine = new ArrayList<>();
            for (int j = 0; j < grid.grid.size(); j += n) {

                List<List<Boolean>> square = new ArrayList<>();
                for (int k = 0; k < n; k++) {
                    square.add(grid.grid.get(i+k).subList(j, j+n));
                }

                squaresLine.add(new Grid(square));
            }

            squares.add(squaresLine);
        }

        return squares;
    }

    private static Grid concat(List<List<Grid>> squares) {
        int m = squares.size();
        int n = squares.get(0).get(0).grid.size();

        //m = 3
        //n = 2
        //0,0 0,1   //0,2 0,3   //0,4 0,5
        //1,0 1,1   //1,2 1,3   //1,4 1,5

        //2,0 2,1   //2,2 2,3   //2,4 2,5
        //3,0 3,1   //3,2 3,3   //3,4 3,5

        //4,0 4,1   //4,2 4,3   //4,4 4,5
        //5,0 5,1   //5,2 5,3   //5,4 5,5

        // =>

        //0,0 0,1 0,2 0,3 0,4 0,5
        //1,0 1,1 1,2 1,3 1,4 1,5
        //2,0 2,1 2,2 2,3 2,4 2,5
        //3,0 3,1 3,2 3,3 3,4 3,5
        //4,0 4,1 4,2 4,3 4,4 4,5
        //5,0 5,1 5,2 5,3 5,4 5,5

        List<List<Boolean>> grid = new ArrayList<>();
        for (int i = 0; i < m; i++) {

            for (int k = 0; k < n; k++) {
                List<Boolean> gridLine = new ArrayList<>();
                for (int j = 0; j < m; j++) {
                    gridLine.addAll(squares.get(i).get(j).grid.get(k));
                }
                grid.add(gridLine);
            }

        }

        return new Grid(grid);
    }

    private static Grid getOutput(Grid input, List<Rule> rules) {
        for (Rule rule : rules) {
            if (rule.size != input.grid.size()) {
                continue;
            }

            if (rule.inputs.stream().anyMatch(input::equals)) {
                return rule.output;
            }
        }

        throw new RuntimeException("No matching rule found");
    }
}
