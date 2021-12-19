package bingo.game;

import java.util.*;

import bingo.shared.*;

class DebugBag implements BagOfBalls {

    static final int ROW = 1;
    static final int COLUMN = 2;
    static final int DIAGONAL = 3;
    static final int REVERSEDIAGONAL = 4;

    static final int rowOrColNumber = 1;

    private Stack balls = new Stack();

    DebugBag() {
    }

    void setUp(Card c, int callThis) {
	if (callThis == ROW) {
	    for (int i = 0; i < Card.SIZE; i ++) 
	        balls.push(c.boardValues[i][rowOrColNumber]);
	} else if (callThis == COLUMN) {
	    for (int i = 0; i < Card.SIZE; i ++) 
	        balls.push(c.boardValues[rowOrColNumber][i]);
	} else if (callThis == DIAGONAL) {
	    for (int i = 0; i < Card.SIZE; i ++) 
	        balls.push(c.boardValues[i][i]);
	} else if (callThis == REVERSEDIAGONAL) {
	    balls.push(c.boardValues[0][4]);
	    balls.push(c.boardValues[1][3]);
	    // don't need to push the free space which is in (2,2)
	    balls.push(c.boardValues[3][1]);
	    balls.push(c.boardValues[4][0]);
	}
    }

    public BingoBall getNext() throws NoMoreBallsException {
	if (balls.size() > 0) {
	    return (BingoBall)balls.pop();
	} else {
	    throw new NoMoreBallsException();
	}
    }
}
