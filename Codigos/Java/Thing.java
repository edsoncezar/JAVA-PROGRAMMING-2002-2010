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
import bvyy.util.*;
import java.io.*;
import java.util.*;
/**
 * A Thing is the base data object within the game. It is provided to allow
 * limited loads for database access. When a Thnig is loaded, no children are
 * loaded. Similarly, a Thing has a Database ID. This allows it to be retreived
 * by the database functions.
 *
 * @version 0.0.2 6 December 1998
 * @author (c) 1998 Brian Voon Yee Yap
 */

public class Thing implements Serializable {

  private dbID    datastoreID;
  private String  name;
  private Long    quantity;

  private boolean loaded = false;

  // The difference between these db components and the components in the sub
  // classes are that they represent the db ID of the object rather than the
  // objects. It is these objects that are stored rather than the transient
  // objects in the sub classes.

  private QuantifiedContainer dbContents;
  private QuantifiedContainer dbMandatoryComponents;
  private QuantifiedContainer dbOptionalComponents;
  private QuantifiedContainer dbAttachedTo;
  private Hashtable           dbCapabilities;
  private Hashtable           dbCharacteristics;
  private Category            dbCategory;
  private Hashtable           dbRepresentations;
  private Thing               dbLocation;
  private Thing               dbContainer;
/**
 * reads this class from the spceified input stream.
 *
 * @param ois the input stream for reading.
 * @exception ClassNotFoundException if the class cannot be found.
 * @exception IOException if some other IO excpetion occurs.
 */
  void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }
/**
 * Saves this object to the specified output stream.
 *
 * @param oos the output stream for saving.
 * @exception IOException if the save fails.
 */
  void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }
/**
 * Returns the datastoreID for this object.
 *
 * @return the datastoreID for this object.
 */
  public dbID getDatastoreID() {
    return datastoreID;
  }
/**
 * Sets the data store id to the new ID.
 *
 * @param newDatastoreID the new ID.
 */
  public void setDatastoreID(dbID newDatastoreID) {
    datastoreID = newDatastoreID;
  }
/**
 * Changes the name for this thing. Note that the name is a propoer noun.
 *
 * @param newName the new name for this thing.
 */
  public void setName(String newName) {
    name = newName;
  }
/**
 * Returns the name of this thing.
 *
 * @return the name of this thing.
 */
  public String getName() {
    return name;
  }
/**
 * Sets how many real-world objects are represented by this one object.
 *
 * @param newQuantity the new number of things
 */
  public void setQuantity(Long newQuantity) {
    quantity = newQuantity;
  }
/**
 * Returns the number of things represented by this object.
 *
 * @return the number of things represented by this object.
 */
  public Long getQuantity() {
    //vetoable??
    return quantity;
  }
/**
 * Returns the fully loaded object. Needs a factory class??? Does not extend
 * well to the sub-classes.
 *
 * @return the fully loaded object.
 */
  public RealThing load() {
    RealThing tempThing;
    //load the object...
    return tempThing;
  }
/**
 * Returns the fully unloaded object.
 *
 * @return the fully unloaded object.
 */
  public Thing unload() {
    return this;
  }
/**
 * Call this method to get a thing to disassemble itself.
 */
  public void disassemble() {
  //check for attachments
  //start grabbing db-lock.
  //check tools for completeness
  //check capabilities for completeness
  //check characteristics for completeness
  //wait for lock confirmation
  //call container to complete process
  dbContainer.disassemble(this);
  }
/**
 * Called by a contained thing to actually perform the disassemble funciton.
 *
 * @param theThing the thing that is being disassembled.
 */
  public void disassemble(Thing theThing) {
  //grab database lock
  //get things components
  //release thing
  //release database lock
  }
}
