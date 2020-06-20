package a2;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import a2.FourInLine.ColumnNum;
import a2.Player.RedPlayer;
import com.sun.org.apache.xpath.internal.objects.XNull;
import com.sun.rowset.internal.Row;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import static java.util.stream.Collectors.*;

public class FourInLine {


    // Declare some constants

    static int NColumns = 7;
    static int NRows = 6;

    // A player is either the red player, or the blue player

    public static Player redPlayer = Player.redPlayer;
    public static Player bluePlayer = Player.bluePlayer;


    // A piece is either a red piece or a blue piece

    public static Piece redPiece = Piece.redPiece;
    public static Piece bluePiece = Piece.bluePiece;



	// A column is a list of Pieces.  The first element of the list represents the top of
    // the column, e.g.
    // row 6 --
    // row 5 --
    // row 4 -- RedPiece   <- first element of the list
    // row 3 -- RedPiece
    // row 2 -- BluePiece
    // row 1 -- RedPiece   <- last element in the list
    // The list for this column would be [redPiece, redPiece, bluePiece, redPiece]
    // Now, to add a piece to the TOP of a column, just create a new column
    // with that piece and append the rest of the old column to it

    // a Column is a list of pieces

    public static class Column extends ArrayList<Piece> {
        public Column() {
        }
        public Column(List<Piece> l) {
            this.addAll(l);
        }
    }

    // The GameState is a list of Columns

     public static class GameState extends ArrayList<Column> implements Cloneable {
		 public GameState() {
		 }

		 public GameState(List<List<Piece>> g) {
			 List<Column> c = g.stream().map(Column::new).collect(toList());
			 this.addAll(c);
		 }

		 @Override
		 public Object clone() {
			 GameState gameState = (GameState) super.clone();
			 for (int i = 0; i < this.size(); i++)
				 gameState.set(i, (Column) gameState.get(i).clone());
			 return gameState;
		 }
	 }

    // ColumnNums are 1-based, but list indices are 0-based.  indexOfColumn converts
    // a ColumnNum to a list index.

    public static class ColumnNum {
        int index;
        public ColumnNum(int index) {
            GameState s;
            this.index = index;
        }
        public int indexOfColumn() {
            return index - 1;
        }
        public String toString() {
            return "" + index;
        }
    }

    //
    //   Convert a column to a string of the form "rBrrBB", or "   rrB".  The string
    //   must have length 6.  If the column is not full, then the list should be
    //   prefixed with an appropriate number of spaces
    //

    //   Convert a column to a string of the form "rBrrBB", or "   rrB".  The string
    //   must have length 6.  If the column is not full, then the list should be
    //   prefixed with an appropriate number of spaces

    public static String showColumn(Column xs) {
		String s1 = "";
		HashSet set=new HashSet(xs);
		if (xs.size() == 0 ||(xs.get(0)==null&&set.size()==1)) {
			s1 = "      ";
		} else {
			for(int j = 0;j<6-xs.size();j++)
				s1+=" ";

			for(int i = 0; i <xs.size(); i++) {
				Piece p = xs.get(i);
				s1 += p.toString();
				}
			}
		return s1;
    }




    //
    //  Convert a GameState value to a string of the form:
    //  "    r        \n
    //   r   r   B   r\n
    //   B B r   B r B\n
    //   r B r r B r r\n
    //   r B B r B B r\n
    //   r B r r B r B"
    //   Useful functions:
    //     showColumn
    //       (which you already defined)
    //     and transposes a list of lists using streams,
    //       so List(List(1,2,3), List(4,5,6)) becomes List(List(1,4), List(2,5), List(3,6))

    public static String showGameState(GameState xs) {
    	int co=NColumns;
    	int ro=NRows;
		String[] s2=new String[co];
		//initial the array
		for(int i=0;i<s2.length;i++) {
			s2[i]=showColumn(xs.get(i));
		}

		String s3="";
		for(int i=0;i<ro;i++) {
			String temp="";
			for(int j=0;j<co;j++){
				if(j!=co-1)
					temp+=s2[j].charAt(i)+" ";
				else
					temp+=s2[j].charAt(i);
			}

			if(i!=ro-1)
				temp+="\n";
			s3+=temp;
		}
		return s3;
    }

    // Which pieces belong to which players?

    public static Piece pieceOf(Player player)  {
    	if(player.toString().equals("Red Player")){
    		return Piece.redPiece;
    	}else {
    		return Piece.bluePiece;
    	}
    }

    // Given a player, who is the opposing player?

    public static Player otherPlayer(Player player) {
    	if(player.toString().equals("Red Player")) {
    		return FourInLine.bluePlayer;
    	}else {
    		return FourInLine.redPlayer;
    	}
    }


    // Given a piece, what is the colour of the other player's pieces?

    public static Piece otherPiece(Piece piece) {
		if(piece.toString().equals("r")){
			return FourInLine.bluePiece;
		}else{
			return FourInLine.redPiece;
		}

    }


    // The initial GameState, all columns are empty.  Make sure to create the proper
    // number of columns

    public static GameState initGameState() {
		GameState gamestate=new GameState();
		for (int i = 0; i < NColumns; i++) {
			gamestate.add(new Column());
		}
    	return gamestate;
    }


    // Check if a column number is valid (i.e. in range)

    public static boolean isValidColumn(ColumnNum c) {
		int ColumnNumber=c.indexOfColumn();
		if(ColumnNumber>=0&&ColumnNumber<NColumns){return true;
		}else return false;
    }


    // Check if a column is full (a column can hold at most nRows of pieces)

    public static boolean isColumnFull(Column column) {
		String c=showColumn(column);
		if (c.contains(" ")){
			return false;
		}
		else return true;
    }


    // Return a list of all the columns which are not full (used by the AI)

    public static List<ColumnNum> allViableColumns(GameState game) {
		List<ColumnNum> empty = new ArrayList<ColumnNum>();
		int i=1;
		for (Column  c : game) {
			if(c.size()<6)
				empty.add(new ColumnNum(i));
			i++;
		}
		return empty;

    }


    // Check if the player is able to drop a piece into a column

    public static boolean canDropPiece(GameState game, ColumnNum columnN) {
    	List<ColumnNum> Columns = allViableColumns(game);
    	for (ColumnNum columnNum : Columns) {
			if(columnN.indexOfColumn()==columnNum.indexOfColumn()) {
				return true;
			}
		}
    	return false;
    }

    // Drop a piece into a numbered column, resulting in a new gamestate



	public static GameState dropPiece(GameState game, ColumnNum columnN, Piece piece) {
    	Column c = game.get(columnN.indexOfColumn());
    	if(c.size()<6) {
    		c.add(0,piece);
    	}
    	return game;
    }

    // Are there four pieces of the same colour in a column?

    static boolean fourInCol(Piece piece, Column col) {
		int count = 1;
    	if(col.size()!=0) {
			Piece temp = col.get(0);
			for (int i = 1; i <= col.size(); i++) {
				if (temp.toString() == col.get(i).toString() && temp.toString() == piece.toString()) {
					count++;
				} else {
					count = 0;
					temp = col.get(i);
				}
			}
		}
		if (count>=4) {
			return true;
		}else return false;
    }

    public static boolean fourInColumn(Piece piece, GameState game) {
		List<Integer> counts=new ArrayList<Integer>();
		for (int i=0;i< game.size();i++) {
			Column c=game.get(i);
			int count=1;
			if(c.size()!=0) {
				Piece temp = c.get(0);
				for (int j = 1; j < c.size(); j++) {
					if (temp.toString() == c.get(j).toString() && temp.toString() == piece.toString()) {
						count++;
						if (count == 4) break;
					} else {
						count = 1;
						temp = c.get(j);
					}

				}
				if (count >= 4) {
					counts.add(count);
				}
			}
		}
		if (counts.contains(4)) {
			return true;
		} else return false;
	}



    // transposes gameboard, assumes all columns are full
    static GameState transpose(GameState g) {
        return new GameState(IntStream.range(0, g.get(0).size())
                .mapToObj(i -> g.stream()
                        .map(l -> l.get(i))
                        .collect(toList()))
                .collect(toList()));
    }
    // A helper function that fills up a column with pieces of a certain colour.  It
    // is used to fill up the columns with pieces of the colour that
    // fourInRow/fourInDiagonal is not looking for.  This will make those functions
    // easier to define.

    static Column fillBlank(Piece piece, Column column) {
    	if(piece.toString().equals("r")){
			for(int i = column.size(); 6 > i;i++){
				column.add( 0,bluePiece);
			}
		}else {
			for(int i = column.size(); 6 > i;i++) {
				column.add(0, redPiece);
			}
		}
		return column;

    }

    // Are there four pieces of the same colour in a row?  Hint: use fillBlanks and
    // transpose to reduce the problem to fourInColumn


    public static boolean fourInRow(Piece piece, GameState game)  {

		GameState gameState= (GameState) game.clone();
		for (int i = 0;i<gameState.size();i++) {
			gameState.set(i,fillBlank(piece,gameState.get(i)));
		}
		gameState=transpose(gameState);
		return fourInColumn(piece, gameState);

	}


    // Another helper function for fourInDiagonal.  Remove n pieces from the top of
    // a full column and add blanks (of the colour we're not looking for) to the
    // bottom to make up the difference.  This makes fourDiagonal easier to define.

    static Column shift(int n, Piece piece, Column column) {
		if(column.size()!=6 || n==-1){
			return column;
		}
		else {
			Column col = new Column();
			//add the pieces since we are looking for
			for (int i = n; i < 6 - 1; i++) {
				col.add(column.get(i));
			}
			//make the top to bottom
			for (int i = 0; i < n; i++) {
				col.add(otherPiece(piece));
			}
			return col;
		}
    }

    // Are there four pieces of the same colour diagonally?  Hint: define a helper
    // function using structural recursion over the gamestate, and using shift and fourInRow.

    static boolean fourDiagonalHelper(GameState g, Piece piece){
       GameState gameState=(GameState) g.clone();
       for(int i=0;i<gameState.size();i++){
       	gameState.set(i,fillBlank(piece,gameState.get(i)));
	   }
       for(int j=0;j<gameState.size();j++){
       		int index=gameState.get(j).indexOf(piece);
		    gameState.set(j,shift(index,otherPiece(piece),gameState.get(j)));

	   }
       return fourInRow(piece,gameState);
    }

    public static boolean fourDiagonal(Piece piece, GameState game) {
		boolean judge=false;
		if(fourDiagonalHelper(game,piece))
			judge=true;
		else
			judge=false;
		return judge;
    }

    // Are there four pieces of the same colour in a line (in any direction)

    public static boolean fourInALine(Piece piece, GameState game)  {
    	boolean judge=false;
    	if(fourInColumn(piece, game)||fourInRow(piece, game)||fourDiagonal(piece, game))
    		judge=true;
    	else
    		judge=false;
		return judge;

    }

    // Who won the game.  Returns an Optional since it could be that no one has won the
    // game yet.

    public static Optional<Player> winner(GameState game) {
		Optional win =Optional.empty();

		if(fourInALine(redPiece,game))
			return win.of(redPlayer);
		if(fourInALine(bluePiece,game))
			return win.of(bluePlayer);
		return win;

    }

}
