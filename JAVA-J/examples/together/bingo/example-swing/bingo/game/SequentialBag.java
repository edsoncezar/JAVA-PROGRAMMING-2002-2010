package bingo.game;

import java.util.*;

import bingo.shared.*;

class SequentialBag implements BagOfBalls {

    private Stack balls = new Stack();

    SequentialBag () {
	    // generate all of the balls
	for (int j = BingoBall.MAX; j >= BingoBall.MIN; j--)
	    balls.push(new BingoBall(j));
    }

    // PENDING: the compiler wanted this public...why?
    public BingoBall getNext() throws NoMoreBallsException {
	if (balls.size() > 0) {
	    return (BingoBall)balls.pop();
	} else {
	    throw new NoMoreBallsException();
	}
    }
}
