package tests;

import a2.FourInLine.*;
import a2.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static a2.FourInLine.*;
import static a2.GameTree.aiMove;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class GameTest {
    GameState board;
    String strRep;
    TestBoard testBoard;

    @BeforeEach
    public void setup() throws IOException {
        TestBoard t = new GameTest.TestBoard("testdata/aiTest.txt");
        this.testBoard = t;
        this.board = t.board;
    }


    @Test
    public void testShowGameState() {
        assertEquals(showGameState(board), testBoard.strRep);
    }

    @Test
    public void testPlayerName() {
        assertEquals(redPlayer.toString(), "Red Player");
    }

    @Test
    public void testConstructColumns() {
        Column c = new Column();
        assertEquals(c.size(), 0);
        c = new Column(Stream.of(redPiece).collect(toList()));
        assertEquals(c.size(), 1);
    }

    @Test
    public void testInitGame() {
        assertEquals(initGameState().size(), 7);
        assertEquals(initGameState().stream().allMatch( c -> c.size() == 0), true);
    }

    @Test
    public void testColSizeGT7() {
        Column c = new Column(Stream.of(redPiece, bluePiece, redPiece, redPiece, redPiece).collect(toList()));
        assertEquals(showColumn(c), " rBrrr");
        Column c1 = new Column(Stream.of(redPiece, bluePiece, redPiece, redPiece, redPiece, redPiece, redPiece).collect(toList()));

    }

    @Test
    public void testShowColumn() {
        Column c1 = new Column();
        Column c2 = new Column(Stream.of(redPiece, bluePiece, redPiece, redPiece).collect(toList()));
        assertEquals(showColumn(c1), "      ");
        assertEquals(showColumn(c2), "  rBrr");
        Column c3 = new Column(Stream.of(redPiece, bluePiece, redPiece, redPiece, redPiece, redPiece).collect(toList()));
        assertEquals(showColumn(c3), "rBrrrr");

    }

    @Test
    public void testshowGameState() {
        TestBoard tb = new TestBoard("testdata/redWinsRow.txt");
        assertEquals(showGameState(tb.board), tb.strRep);
    }

    @Test
    public void testPieceOf() {
        assertEquals(pieceOf(redPlayer), redPiece);
        assertEquals(pieceOf(bluePlayer), bluePiece);
    }
    @Test
    public void testOtherPlayer() {
        assertEquals(otherPlayer(redPlayer), bluePlayer);
        assertEquals(otherPlayer(bluePlayer), redPlayer);
    }
    @Test
    public void testOtherPiece() {
        assertEquals(otherPiece(redPiece), bluePiece);
        assertEquals(otherPiece(bluePiece), redPiece);
    }
    @Test
    public void testIsValidCol() {
        assert(!isValidColumn(new ColumnNum(0)));
        assert(!isValidColumn(new ColumnNum(8)));
        assert(isValidColumn(new ColumnNum(1)));
        assert(isValidColumn(new ColumnNum(7)));

    }

    @Test
    public void testIsColumnFull() {
        Column fullCol = new Column(Stream.of(redPiece, redPiece, bluePiece, bluePiece, redPiece, redPiece).collect(toList()));
        Column notFull = new Column();
        Column notFull2 = new Column(Stream.of(redPiece).collect(toList()));
        assert(isColumnFull(fullCol));
        assert(!isColumnFull(notFull));
        assert(!isColumnFull(notFull2));

    }
    @Test
    public void testAllViableCols() {
        GameState fullBoard = (new TestBoard("testdata/fullBoard.txt")).board;
        assertEquals(allViableColumns(fullBoard).isEmpty(), true);
        GameState notFullBoard = (new TestBoard("testdata/redWinsRow.txt")).board;
        System.err.println(allViableColumns(notFullBoard));
        assertEquals(allViableColumns(notFullBoard).size(), 6);
    }
    @Test
    public void testCanDropPiece() {
        GameState fullBoard = new TestBoard("testdata/fullBoard.txt").board; // all columns full in this board
        assertEquals(!canDropPiece(fullBoard, new ColumnNum(1)), true);
        GameState notFullBoard = new TestBoard("testdata/redWinsRow.txt").board; // this board has one column full
        assertEquals(!canDropPiece(notFullBoard, new ColumnNum(3)), true);
        assertEquals(canDropPiece(notFullBoard, new ColumnNum(1)), true);
    }
    @Test
    public void testDropPiece() {
        GameState beforeDrop = new TestBoard("testdata/redWinsRow.txt").board; // before drop piece
        GameState afterDrop = new TestBoard("testdata/dropPieceRed.txt").board; // after drop
        assertEquals(dropPiece(beforeDrop, new ColumnNum(2), redPiece), afterDrop);
    }

    @Test
    public void testFourInCol() {
        GameState fourInCol = new TestBoard("testdata/fourInCol.txt").board;
        GameState noFour = new TestBoard("testdata/noFourInLine.txt").board;
        assertEquals(fourInColumn(bluePiece, fourInCol), true);
        assertEquals(fourInColumn(bluePiece, noFour), false);
        assertEquals(fourInColumn(redPiece, fourInCol), false);

    }

    @Test
    public void testFourInRow() {
        GameState fourInRow = new TestBoard("testdata/fourInRow.txt").board;
        GameState noFour = new TestBoard("testdata/noFourInLine.txt").board;

        assertEquals(fourInRow(bluePiece, fourInRow), true);
        assertEquals(fourInRow(bluePiece, noFour), false);
        assertEquals(fourInRow(redPiece, fourInRow), false);

    }
    @Test
    public void testFourDiagonal() {
        GameState fourDiagonalBoard = new TestBoard("testdata/fourDiagonal.txt").board;
        GameState fd2 = new TestBoard("testdata/fourDiagonal2.txt").board;
        GameState fd3 = new TestBoard("testdata/fourDiagonal3.txt").board;

        GameState noFour = new TestBoard("testdata/noFourInLine.txt").board;
        assertEquals(fourDiagonal(bluePiece, fourDiagonalBoard), true);
        assertEquals(fourDiagonal(bluePiece, noFour), false);
        assertEquals(fourDiagonal(redPiece, fourDiagonalBoard), false);
        assertEquals(fourDiagonal(redPiece, fd2), true);
        assertEquals(fourDiagonal(bluePiece, fd3), true);
    }

    @Test
    public void testFourInALine() {
        GameState fd = new TestBoard("testdata/fourDiagonal.txt").board;
        GameState fr = new TestBoard("testdata/fourInRow.txt").board;
        GameState fc = new TestBoard("testdata/fourInCol.txt").board;
        assertEquals(fourInALine(bluePiece, fd), true);
        assertEquals(fourInALine(bluePiece, fr), true);
        assertEquals(fourInALine(bluePiece, fc), true);
    }
    @Test
    public void testWinner() {
        GameState fourDiag = new TestBoard("testdata/fourDiagonal.txt").board;
        assertEquals(winner(fourDiag), Optional.of(bluePlayer));
    }
    @Test
    public void testComputerPlayer() {
        GameState blueAboutToWin = new TestBoard("testdata/blueAboutToWin.txt").board;
        ColumnNum aiBlockCol = aiMove(4, redPlayer).apply(blueAboutToWin);
        GameState gameAfterAiMove = dropPiece(blueAboutToWin, aiBlockCol, pieceOf(redPlayer));
        GameState gameAfterHuman = dropPiece(gameAfterAiMove, new ColumnNum(2), pieceOf(bluePlayer));
        assertEquals(winner(gameAfterHuman), Optional.empty()); // computer should block
        ColumnNum aiWinCol = aiMove(4, bluePlayer).apply(blueAboutToWin);
        GameState gameafterAiWinMove = dropPiece(blueAboutToWin, aiWinCol, pieceOf(bluePlayer));
        assertEquals(winner(gameafterAiWinMove), Optional.of(bluePlayer)); // computer should win
    }



    class TestBoard {
        String strRep;
        GameState board;

        public TestBoard(String fileName)  {
            List<String> rows;
            try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
                rows = lines.collect(toList());
            } catch (Exception e) {
                throw new RuntimeException("error reading file: " + fileName);
            }
            strRep = String.join("\n", rows);
            List<List<Piece>> collect = IntStream.range(0, rows.get(0).length())
                    .mapToObj(i -> rows.stream()
                            .map(l -> toPiece(l.charAt(i)))
                            .filter(p -> p.isPresent())
                            .map(p -> p.get())
                            .collect(toList()))
                    .filter(l -> !l.isEmpty())
                    .collect(toList());
            board = new GameState();
            board.addAll(collect.stream()
                    .map(l -> new Column(l)).collect(toList()));
        }

        Optional<Piece> toPiece(Character c) {

            switch (c) {
                case 'r':
                    return Optional.of(redPiece);
                case 'B':
                    return Optional.of(bluePiece);
                default:
                    return Optional.empty();
            }
        }
    }
}
