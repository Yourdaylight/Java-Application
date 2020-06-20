// import FourInLine._
// import GameTree.aiMove
package a2;

import java.util.*;
import java.util.function.Function;

import a2.FourInLine.GameState;

import static a2.FourInLine.*;
import static a2.GameTree.aiMove;

public class Game  {

  public static void main(String[] args) {
    // start game loop
    startGame();
  }


  // A map (similar to a dictionary in Python) that maps
  // Players to the functions that get moves for those players.  This will
  // allow us to use the same code for human vs. human matches as for computer
  // vs. human and computer vs. computer.

    static class MoveGetterMap extends HashMap<Player, Function<GameState, ColumnNum>> {}

  // How many moves should the AI look ahead.  Higher numbers mean a smarter AI,
  // but it takes much longer to evaluate the game tree.

  static int aiDepth = 4;
  static ColumnNum lastMove;

  // UI routines

  static void startGame() {

    System.out.println("Welcome to four-in-line");

    Function<GameState, ColumnNum> redPlayer = getMoveGetter(FourInLine.redPlayer);
    Function<GameState, ColumnNum> bluePlayer = getMoveGetter(FourInLine.bluePlayer);

    MoveGetterMap moveGetter = new MoveGetterMap();
    moveGetter.put(FourInLine.redPlayer, redPlayer);
    moveGetter.put(FourInLine.bluePlayer, bluePlayer);
    drawBoard(initGameState());
    turn(moveGetter, FourInLine.redPlayer, initGameState());

  }

  // Execute a single turn

  static void turn(MoveGetterMap moveGetter, Player player, GameState game) {

    Optional<Player> win = winner(game);

    if (win.isPresent()) {
        drawBoard(game);
        System.out.printf("%s wins!%n", win.get().toString());
    } else if (allViableColumns(game).isEmpty()) {
        drawBoard(game);
        System.out.println("It's a draw!");
    } else {
    	GameState clone = cloneG(game);//电脑走会把这个地图变坏，只能这样
        ColumnNum c = (moveGetter.get(player)).apply(game);
        lastMove = c;
        GameState gameP = dropPiece(clone, c, pieceOf(player));
        drawBoard(gameP);
        turn(moveGetter, otherPlayer(player), gameP);
    }
  }
  static  GameState cloneG(GameState game) {
	  GameState newG=new GameState();
	  for (Column column : game) {
		  Column newC=new Column();
		  for (Piece p : column) {
			  newC.add(p);	
		  }
		  newG.add(newC);
	  }
	  return newG;
  }
  // gets a function that gets the next move for a particular player.
  // Depending on whether the player is human or computer, it will be
  // getHumanMove player, or getComputerMove player

  static  Function<GameState, ColumnNum> getMoveGetter(Player player) {

    System.out.printf("Is %s to be human or computer? ", player);

    Scanner scanner = new Scanner(System.in);
    String ln = scanner.nextLine().trim();

    if (ln.equals("computer")) {
      return aiMove(aiDepth, player);
    } else if (ln.equals("human")) {
      return getHumanMove(player);
    } else {
      System.out.println("Input must be either \"human\" or \"computer\"");
      return getMoveGetter(player);
    }
  }


  static ColumnNum getValidMove(GameState game) {
      ColumnNum c = getMove();
      if (!canDropPiece(game, c)) {
          System.out.printf("Column %s is full, try again.%n", c);
          return getValidMove(game);
      } else
          return c;
  }

  static ColumnNum getMove() {
        Scanner scanner = new Scanner(System.in);
        String ln = scanner.nextLine().trim();
        Optional<ColumnNum> c = getColumn(ln);
        if (c.isPresent()) {
            if (!isValidColumn(c.get()))  {
                System.out.println("No such column, try again.");
                System.out.println("Enter column number: ");
                return getMove();
            }
        } else {
            System.out.println("That wasn't a number. Enter column number: ");
            return getMove();
        }
        return c.get();
    }

  // Read a valid move

  static Function<GameState, ColumnNum> getHumanMove(Player player)  {
      return game -> {
          System.out.printf("%s's turn. Enter column number: ", player);
          return getValidMove(game);
      };
  }

  // Parse a column number from a string

  static Optional<ColumnNum> getColumn(String str) {
    String c = str.trim();
    try {
        return Optional.of(new ColumnNum(Integer.parseInt(c)));
    } catch (Exception e){
        return Optional.empty();
    }
  }

  // Draw the game board

  static void drawBoard(GameState gameState) {

    String[] strLines = showGameState(gameState).split("\n");

    List<Integer> c = Arrays.asList(6, 5, 4, 3, 2, 1);
    System.out.print(" ");
    for(int i = 1; i <= 7; i++) {
        if (lastMove != null && lastMove.index == i)
            System.out.printf("%s*", i); // highlight last played column
        else
            System.out.printf("%s ", i);
    }
    System.out.println("");

    for(Integer i: c) {
        System.out.printf("%s%s%n", i, strLines[6 - i]);
    }
  }
}

