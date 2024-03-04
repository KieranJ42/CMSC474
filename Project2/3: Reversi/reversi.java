import java.util.*;

public class reversi {
    static long START_TIME = System.nanoTime();
    static long TIMEOUT = 2_500_000_000L;
    static int DEPTH = 12;
    static boolean PRINT_BOARD = System.getenv("DEBUG_OTHELLO_PRINT_BOARD") != null;

    public static void main(String[] args) {
        // Parse input, construct board
        int[][] arr = new int[][]{
                {0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < Board.NUM_ROWS; i++) {
            for (int j = 0; j < Board.cols_in_row(i + 1); j++) {
                arr[i][j] = scanner.nextInt();
            }
        }

        Board board = new Board(arr);

        // Calculate optimal move
        Cell move = board.find_best_move();

        if (move != null) {
            if (PRINT_BOARD) {
                System.out.println(board.play_move(true, move));
            } else {
                // Print optimal move
                System.out.println(move);
            }
        } else {
            System.exit(1);
        }
    }
}

enum Direction {
    Above,
    Below,
    Left,
    Right,
    TopLeft,
    TopRight,
    BottomLeft,
    BottomRight;

    /**
     * @return opposite direction
     */
    public Direction opposite() {
        switch (this) {
            case Above:
                return Below;
            case Below:
                return Above;
            case Left:
                return Right;
            case Right:
                return Left;
            case TopLeft:
                return BottomRight;
            case TopRight:
                return BottomLeft;
            case BottomLeft:
                return TopRight;
            case BottomRight:
                return TopLeft;
        }
        throw new RuntimeException();
    }
}

final class Board implements Cloneable {
    static final int EMPTY = 0;
    static final int SELF = 1;
    static final int OPPONENT = 2;

    static final int NUM_ROWS = 10;
    private final int[][] cells;

    Board(int[][] cells) {
        this.cells = cells;
    }

    static Iterator<Cell> cells() {
        return new CellsIterator();
    }

    static int cols_in_row(int row) {
        if (row >= 1 && row <= NUM_ROWS) {
            return row * 2;
        }
        throw new IndexOutOfBoundsException("row must be between 1 and 10 (got " + row + ")");
    }

    public int get_contents(Cell cell) {
        return cells[cell.row() - 1][cell.col() - 1];
    }

    public void set_cell_contents(Cell cell, int val) {
        cells[cell.row() - 1][cell.col() - 1] = val;
    }

    Cell find_best_move() {
        return minimax(reversi.DEPTH, true, Integer.MIN_VALUE, Integer.MAX_VALUE, true, false).move();
    }

    //  return: array of size 3. arr[0]: minimax value, arr[1]: row move to play, arr[2]: col move to play
    MinimaxResult minimax(int depth, boolean player_is_self, int alpha, int beta, boolean needmove, boolean previous_turn_skipped) {
        int minmax;
        ArrayList<Cell> possibleMoves = new ArrayList<>();
        Cell[] valid_moves = find_valid_moves(player_is_self);

        // Don't recurse as deeply if there are many moves to check
        int new_depth = depth - 1 - valid_moves.length / 4;

        if (valid_moves.length == 0) {
            if (previous_turn_skipped) {
                // The game has ended! check for a winner.
                int score = 0;
                for (int[] row : this.cells) {
                    for (int cell : row) {
                        if (cell == SELF) {
                            score++;
                        } else if (cell == OPPONENT) {
                            score--;
                        }
                    }
                }

                if (score > 0) {
                    return new MinimaxResult(null, Integer.MAX_VALUE);
                } else if (score < 0) {
                    return new MinimaxResult(null, Integer.MIN_VALUE);
                } else {
                    return new MinimaxResult(null, 0);
                }
            } else {
                return this.minimax(depth, !player_is_self, alpha, beta, needmove, true);
            }
        } else if (depth <= 0 || System.nanoTime() - reversi.START_TIME >= reversi.TIMEOUT) {
            return new MinimaxResult(null, eval_board());
        } else if (player_is_self) {
            int max = Integer.MIN_VALUE;
            for (Cell cell : valid_moves) {
                Board board_after_move = this.play_move(true, cell);

                int eval = board_after_move.minimax(new_depth, false, alpha, beta, false, false).score();

                if (eval >= max) {
                    if (needmove && eval > max) {
                        possibleMoves.clear();
                    }
                    max = eval;
                    if (needmove) {
                        possibleMoves.add(cell);
                    }
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            minmax = max;
        } else {
            int min = Integer.MAX_VALUE;
            for (Cell cell : valid_moves) {
                Board board_after_move = this.play_move(false, cell);

                int eval = board_after_move.minimax(new_depth, true, alpha, beta, false, false).score();

                if (eval <= min) {
                    if (needmove && eval < min) {
                        possibleMoves.clear();
                    }
                    min = eval;
                    if (needmove) {
                        possibleMoves.add(cell);
                    }
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            minmax = min;
        }

        if (needmove) {
            return new MinimaxResult(possibleMoves.get((int) (System.currentTimeMillis() % possibleMoves.size())), minmax);
        } else {
            return new MinimaxResult(null, minmax);
        }
    }

    int eval_board() {
        int score = 0;
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                int weight = 1;
                if (row == 0 || (row == 9 && (col == 0 || col == 19))) { //corner
                    weight = 20;
                } else if ((row >= 3 && row <= 7) && (col >= row - 1 && col <= row + 2)) { //middle
                    weight = 7;
                } else if (col == 1 || col == row * 2 || (row == 8 && (col != 0 && col != 17))) { //High risk squares
                    weight = -5;
                } else if (col == 0 || col == (row * 2 + 1) || row == 9) { //edges
                    weight = 9;
                }
                score += ((cells[row][col] == 2) ? -1 : cells[row][col]) * weight; // MULTIPLY WITH -1 FOR OPPONENT, 1 FOR SELF, O OTHERWISE
            }
        }
        return score;
    }

    /**
     * @param from cell, assumed to contain one of player stones,
     *             that serves to anchor the move
     *             (other end of the "sandwich")
     * @param dir  direction to look for move in
     * @return valid move, or null if not found
     */
    Cell find_move_in_direction(Cell from, Direction dir, boolean player_is_self) {
        int respondent;
        if (player_is_self) {
            respondent = Board.OPPONENT;
        } else {
            respondent = Board.SELF;
        }

        Cell adjacent = from.get_adjacent(dir);

        if (adjacent != null && get_contents(adjacent) == respondent) {
            do {
                adjacent = adjacent.get_adjacent(dir);
            } while (adjacent != null && get_contents(adjacent) == respondent);

            if (adjacent != null && get_contents(adjacent) == Board.EMPTY) {
                return adjacent;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    Cell[] find_valid_moves(boolean player_is_self) {
        int player;
        if (player_is_self) {
            player = Board.SELF;
        } else {
            player = Board.OPPONENT;
        }

        HashSet<Cell> moves = new HashSet<>();
        for (Iterator<Cell> it = cells(); it.hasNext(); ) {
            Cell cell = it.next();
            if (get_contents(cell) == player) {
                for (Direction d : Direction.values()) {
                    Cell move = find_move_in_direction(cell, d, player_is_self);
                    if (move != null) {
                        moves.add(move);
                    }
                }
            }

        }
        return moves.toArray(new Cell[]{});
    }

    /**
     * @param player_is_self true if self player is placing the stone
     * @param cell           the cell played in (assumed valid move)
     * @return new board after played
     */
    Board play_move(boolean player_is_self, Cell cell) {
        int player;
        int respondent;
        if (player_is_self) {
            player = Board.SELF;
            respondent = Board.OPPONENT;
        } else {
            player = Board.OPPONENT;
            respondent = Board.SELF;
        }

        Board new_board = this.clone();
        new_board.set_cell_contents(cell, player);
        for (Direction dir : Direction.values()) {
            Cell adjacent = cell.get_adjacent(dir);

            if (adjacent != null && get_contents(adjacent) == respondent) {
                do {
                    adjacent = adjacent.get_adjacent(dir);
                } while (adjacent != null && get_contents(adjacent) == respondent);

                if (adjacent != null && get_contents(adjacent) == player) {
                    Direction opposite = dir.opposite();
                    // We have a sandwich, go back and flip
                    adjacent = adjacent.get_adjacent(opposite);
                    while (get_contents(adjacent) == respondent) {
                        new_board.set_cell_contents(adjacent, player);
                        adjacent = adjacent.get_adjacent(opposite);
                    }
                }
            }
        }
        return new_board;
    }

    @Override
    public Board clone() {
        int[][] new_arr = new int[NUM_ROWS][];
        for (int r = 0; r < NUM_ROWS; r++) {
            new_arr[r] = this.cells[r].clone();
        }
        return new Board(new_arr);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Board that = (Board) obj;
        return Arrays.deepEquals(this.cells, that.cells);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(cells);
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(310);
        for (int row = 0; row < cells.length; row++) {
            for (int i = 0; i < (2 * (10 - row)); i++) {
                buf.append(' ');
            }
            for (int col = 0; col < cells[row].length - 1; col++) {
                buf.append(cells[row][col]);
                buf.append(' ');
            }
            buf.append(cells[row][cells[row].length - 1]);
            buf.append('\n');
        }
        return buf.toString();
    }

    static final class CellsIterator implements Iterator<Cell> {
        int row = 1;
        int col = 1;

        @Override
        public boolean hasNext() {
            return row < NUM_ROWS || (row == NUM_ROWS && col < cols_in_row(NUM_ROWS));
        }

        @Override
        public Cell next() {
            if (hasNext()) {
                Cell ret = new Cell(row, col);
                if (col < cols_in_row(row)) {
                    col += 1;
                } else {
                    row += 1;
                    col = 1;
                }
                return ret;
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    final static class MinimaxResult {
        private final Cell move;
        private final int score;

        MinimaxResult(Cell move, int score) {
            this.move = move;
            this.score = score;
        }

        public Cell move() {
            return move;
        }

        public int score() {
            return score;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            MinimaxResult that = (MinimaxResult) obj;
            return Objects.equals(this.move, that.move) &&
                    this.score == that.score;
        }

        @Override
        public int hashCode() {
            return Objects.hash(move, score);
        }

        @Override
        public String toString() {
            return "MinmaxResult[" +
                    "move=" + move + ", " +
                    "score=" + score + ']';
        }
    }
}

final class Cell implements Cloneable {
    // 1-indexed
    private final int row;
    // 1-indexed
    private final int col;

    /**
     * @param row 1-indexed
     * @param col 1-indexed
     */
    Cell(int row, int col) {
        if (col < 1 || Board.cols_in_row(row) < col) {
            throw new IndexOutOfBoundsException("invalid row or col (got " + col + " " + row + ")");
        }
        this.row = row;
        this.col = col;
    }

    /**
     * @return 1-indexed
     */
    public int row() {
        return row;
    }

    /**
     * @return 1-indexed
     */
    public int col() {
        return col;
    }

    /**
     * Get the `Cell` adjacent to this one, in the specified direction.
     *
     * @param direction The direction to look in
     * @return The adjacent cell, or `null` if no such cell
     */
    public Cell get_adjacent(Direction direction) {
        try {
            switch (direction) {
                case Above:
                    return new Cell(row() - 1, col() - 1);
                case Below:
                    return new Cell(row() + 1, col() + 1);
                case Left:
                    return new Cell(row(), col() - 1);
                case Right:
                    return new Cell(row(), col() + 1);
                case TopLeft:
                    return new Cell(row() - 1, col() - 2);
                case TopRight:
                    return new Cell(row() - 1, col());
                case BottomLeft:
                    return new Cell(row() + 1, col());
                case BottomRight:
                    return new Cell(row() + 1, col() + 2);
            }
            throw new RuntimeException();
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public Cell clone() {
        return new Cell(row, col);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Cell that = (Cell) obj;
        return this.row == that.row && this.col == that.col;
    }

    @Override
    public int hashCode() {
        return ((col - 1) << 4) + (row - 1);
    }

    @Override
    public String toString() {
        return row() + " " + col();
    }
}