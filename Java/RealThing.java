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
import java.io.*;
import java.beans.*;
import bvyy.util.*;
import java.util.*;
/**
 * A Real Thing is the base data object within the game. Any object in the world
 * must be represented by a single real thing object.
 *
 * @version 0.0.1  30 October 1998
 * @author (c) 1998 Brian Voon Yee Yap
 */

public class RealThing extends Thing {

  public RealThing() {
  } 

  private transient QuantifiedContainer contents;
  private transient PropertyChangeSupport propertyChangeListeners = new PropertyChangeSupport(this);
  private transient VetoableChangeSupport vetoableChangeListeners = new VetoableChangeSupport(this);
  private transient QuantifiedContainer mandatoryComponents;
  private transient QuantifiedContainer optionalComponents;
  private transient QuantifiedContainer attachedTo;
  private transient Vector generalEventListeners;
  private transient Hashtable capabilities;
  private transient Hashtable characteristics;
  private transient Category category;
  private transient Hashtable representations;
  private transient Thing location;

/**
 * Creates a new Thing from the components supplied. This routine assumes that
 * it is called from the appropriate category and hence all checking has been
 * performed.</P>
 *
 * If there are surplus components, these components remain where they were
 * prior to this action being performed.</P>
 *
 * @param myType the type of thing being created.
 * @param components a list of available components.
 * @param tools a list of available tools.
 * @param capabilities a list of available capabilities.
 * @param characteristics a list of available characteristics.
 * @param location the location where the thing is to be created.
 * @param loadDepth the depth down the containment and component trees that
 *                  data will be loaded. Each level in the hierarchy will
 *                  decrement this number by one.
 */
  public Thing(Category            myType,
               QuantifiedContainer components,
               Hashtable           tools,
               Hashtable           capabilities,
               Hashtable           characterisitcs,
               Thing               location,
               int                 loadDepth) {

  private Thing thisMandatoryComponent;
  private Thing thisOptionalComponent;
  private Thing thisComponent;

  this.Thing();
  this.setCategory(myType);

// Use the components.
  for (Enumeration e = myType.getMandatoryComponents.elements() ; e.hasMoreElements() ;) {
         thisMandatoryComponent=(Thing)e.nextElement();
         thisComponent=(Thing)components.get(thisMandatoryComponent.getName())
         if (thisMandatoryComponent.getQuantity() == thisComponent.getQuantity()){
           mandatoryComponents.put(thisComponent.getName(),thisComponent);
           thisComponent.setLocation(this);
         } else {
           // Buld the maximum number of mandatory components and add the rest
           // (up to the maximum) to the optional components.

// THERE IS A FLAW IN THE LOGIC CAUSED BY QUANTITY. THERE COULD BE 4 THE SAME BUT DIFFERENT OBJECTS OR ONE OBJECT WITH A QUANTITY OF 4!!!!!
             }
         }
       }
// Use the optional components.
// Add the effects of the tools.
// Add the effects of the capabilities.
// Add the effects of the characteristics.

  }
/**
 * This method is called to remove this thing from the game. All components
 * and contents are added to this things parent.
 *
 * @exception DestructionNotAllowedException If the validation of the
 *                                           destruction failed.
 */
  public synchronized void destroy() throws DestructionNotAllowedException {
//allow listeners to veto this command.
    try (vetoableChangeListeners.fireVetoableChange("destroy", getAllComponents(), getContents());)
    catch (PropertyVetoException pve) {
           throw new DestructionNotAllowedException(pve.getMessage());
           };
//put all components in the parent container.
//put all contents in the parent container.
//remove references to this object.
  }
/**
 * Creates a new Thing from the template category provided.
 *
 * @param template the categrory template to be used to create this thing.
 */
  public Thing(Category template) {
  }
/**
 * Sets the contents to the provided Quantified Container. The old contents
 * are lost.
 *
 * @param newContents the new quantified container.
 */
  public void setContents(QuantifiedContainer newContents) throws java.beans.PropertyVetoException {
    java.util.Vector  oldContents = contents;
    vetoableChangeListeners.fireVetoableChange("contents", oldContents, newContents);
    contents = newContents;
    propertyChangeListeners.firePropertyChange("contents", oldContents, newContents);
  }
/**
 * Returns the contents of this thing.
 *
 * @return the contents of this thing.
 */
  public QuantifiedContainer getContents() {
    return contents;
  }
/**
 * This is the call to use to add things into the content of this thing. If
 * the call is sucessful, the thing is added to this thing and removed from
 * the previous thing container.</P>
 *
 * @param newContent The thing to be moved into this container.
 * @exception ThingNotAllowedAsContentException This is thrown if the thing
 *                                              cannot be added. As a class it
 *                                              is generally subclassed to
 *                                              provide greater deatail as to
 *                                              the reason the thing could not
 *                                              be added.
 */
  public void addToContents(Thing newContent)
         throws ThingNotAllowedAsContentException {
  }
/**
 *
 */
  public synchronized void removePropertyChangeListener(PropertyChangeListener l) {
    propertyChangeListeners.removePropertyChangeListener(l);
  }
/**
 *
 */
  public synchronized void addPropertyChangeListener(PropertyChangeListener l) {
    propertyChangeListeners.addPropertyChangeListener(l);
  }
/**
 *
 */
  public synchronized void removeVetoableChangeListener(VetoableChangeListener l) {
    vetoableChangeListeners.removeVetoableChangeListener(l);
  }
/**
 *
 */
  public synchronized void addVetoableChangeListener(VetoableChangeListener l) {
    vetoableChangeListeners.addVetoableChangeListener(l);
  }
/**
 *
 */
  public void setMandatoryComponents(QuantifiedContainer newMandatoryComponents) throws java.beans.PropertyVetoException {
    QuantifiedContainer  oldMandatryComponents = mandatryComponents;
    vetoableChangeListeners.fireVetoableChange("mandatoryComponents", oldMandatryComponents, newMandatryComponents);
    propertyChangeListeners.firePropertyChange("mandatoryComponents", oldMandatryComponents, newMandatryComponents);
    mandatoryComponents = newMandatoryComponents;
  }
/**
 *
 */
  public QuantifiedContainer getMandatoryComponents() {
    return mandatoryComponents;
  }
/**
 *
 */
  public void setOptionalComponents(QuantifiedContainer newOptionalComponents) throws java.beans.PropertyVetoException {
    QuantifiedContainer  oldOptionalComponents = optionalComponents;
    vetoableChangeListeners.fireVetoableChange("optionalComponents", oldOptionalComponents, newOptionalComponents);
    optionalComponents = newOptionalComponents;
    propertyChangeListeners.firePropertyChange("optionalComponents", oldOptionalComponents, newOptionalComponents);
  }
/**
 *
 */
  public QuantifiedContainer getOptionalComponents() {
    return optionalComponents;
  }
/**
 * This returns a consolidated call to return all the components of this thing.
 *
 * @return a quantified container containing all the components.
 */
  public QuantifiedContainer getAllComponents() {
  }
/**
 * Returns true if the Thing is a component of this Thing.
 *
 * @return true if the Thing is a component of this Thing.
 */
  public boolean isAComponent(Thing theThing) {
  }
/**
 *
 */
  public void setAttachedTo(QuantifiedContainer newAttachedTo) throws java.beans.PropertyVetoException {
    QuantifiedContainer  oldAttachedTo = attachedTo;
    vetoableChangeListeners.fireVetoableChange("attachedTo", oldAttachedTo, newAttachedTo);
    attachedTo = newAttachedTo;
    propertyChangeListeners.firePropertyChange("attachedTo", oldAttachedTo, newAttachedTo);
  }
/**
 *
 */
  public QuantifiedContainer getAttachedTo() {
    return attachedTo;
  }
/**
 *
 */
  public synchronized void removeGeneralEventListener(GeneralEventListener l) {
    if (generalEventListeners != null && generalEventListeners.contains(l)) {
      Vector v = (Vector) generalEventListeners.clone();
      v.removeElement(l);
      generalEventListeners = v;
    }
  }
/**
 *
 */
  public synchronized void addGeneralEventListener(GeneralEventListener l) {
    Vector v = generalEventListeners == null ? new Vector(2) : (Vector) generalEventListeners.clone();
    if (!v.contains(l)) {
      v.addElement(l);
      generalEventListeners = v;
    }
  }
/**
 *
 */
  protected void fireReceiveEvent(GeneralEvent e) {
    if (generalEventListeners != null) {
      Vector listeners = generalEventListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++)
        ((GeneralEventListener) listeners.elementAt(i)).receiveEvent(e);
    }
  }
/**
 *
 */
  public void receiveEvent(GeneralEvent evt) {
  }
/**
 *
 */
  public void setCapabilities(Hashtable newCapabilities) throws java.beans.PropertyVetoException {
    Hashtable  oldCapabilities = capabilities;
    vetoableChangeListeners.fireVetoableChange("capabilities", oldCapabilities, newCapabilities);
    capabilities = newCapabilities;
    propertyChangeListeners.firePropertyChange("capabilities", oldCapabilities, newCapabilities);
  }
/**
 *
 */
  public Hashtable getCapabilities() {
    return capabilities;
  }
/**
 *
 */
  public void setCharacteristics(Hashtable newCharacteristics) throws java.beans.PropertyVetoException {
    Hashtable  oldCharacteristics = characteristics;
    vetoableChangeListeners.fireVetoableChange("characteristics", oldCharacteristics, newCharacteristics);
    characteristics = newCharacteristics;
    propertyChangeListeners.firePropertyChange("characteristics", oldCharacteristics, newCharacteristics);
  }
/**
 *
 */
  public Hashtable getCharacteristics() {
    return characteristics;
  }
/**
 *
 */
  public void setCategory(Category newCategory) throws java.beans.PropertyVetoException {
    Category  oldCategory = category;
    vetoableChangeListeners.fireVetoableChange("category", oldCategory, newCategory);
    category = newCategory;
    propertyChangeListeners.firePropertyChange("category", oldCategory, newCategory);
  }
/**
 *
 */
  public Category getCategory() {
    return category;
  }
/**
 *
 */
  public void setRepresentations(java.util.Hashtable newRepresentations) {
    java.util.Hashtable  oldRepresentations = representations;
    representations = newRepresentations;
    propertyChangeListeners.firePropertyChange("representations", oldRepresentations, newRepresentations);
  }
/**
 *
 */
  public java.util.Hashtable getRepresentations() {
    return representations;
  }
/**
 *
 */
  public void setLocation(bvyy.database.Thing newLocation) {
    location = newLocation;
  }
/**
 *
 */
  public bvyy.database.Thing getLocation() {
    return location;
  }
} 