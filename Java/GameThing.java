package bvyy.database;
// Copyright(C)1998 Brian Yap

// This program is free software; you can redistribute it and/or modify it
// under the terms of the GNU General Public License as published by the
// Free Software Foundation; either version 2 of the License, or (at your
// option) any later version.

// This program is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
// more details.

// You should have received a copy of the GNU General Public License along with
// this program; if not, write to the Free Software Foundation, Inc., 675 Mass
// Ave, Cambridge, MA 02139, USA.

/**
 * A Game Thing is a data base object used to represent a game. It impliments
 * the game specific commands.</P>
 *
 * @version 0.0.1  30 October 1998
 * @author (c) 1998 Brian Voon Yee Yap
 */

public static class GameThing extends RealThing {

  private        CategoryIndex categoryIndex = new CategoryIndex();
  private static GameThing gameThing         = new GameThing();

/**
 * This uses the staic variable gameThing to ensure that the gameThing is a
 * singleton class.
 */
  public GameThing() {
    if (gameThing == null) {
    }
  }
/**
 *
 */
  public bvyy.database.CategoryIndex getCategoryIndex() {
    return categoryIndex;
  }
/**
 *
 */
  public void setCategoryIndex(bvyy.database.CategoryIndex newCategoryIndex) {
    categoryIndex = newCategoryIndex;
  }
}