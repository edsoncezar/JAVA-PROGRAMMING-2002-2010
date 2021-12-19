package com.wrox.store.ejb.product;

import java.io.Serializable;

/**
 * A simple "value" object used to enscapsulate all of the information about
 * a product in one remote method call.
 *
 * @author		Simon Brown
 */
public class ProductDetails implements Serializable {

	/** the id of this product */
	public String id;

	/** the name of this product */
	public String name;

	/** the price of this product */
	public double price;

	/**
	 * Creates a new instance with the specified id.
	 *
	 * @param	id		the id of the product
	 */
	ProductDetails(String id) {
		this.id = id;
	}

	/**
	 * Gets the id of this product.
	 *
	 * @return	the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Gets the name of this product.
	 *
	 * @return	the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of this product.
	 *
	 * @param	newName		the new name of this product
	 */
	void setName(String newName) {
		this.name = newName;
	}

	/**
	 * Gets the price of this product.
	 *
	 * @return	the price as a double
	 */
	public double getPrice() {
		return this.price;
	}

	/**
	 * Sets the price of this product.
	 *
	 * @param	newPrice		the new price of this product
	 */
	void setPrice(double newPrice) {
		this.price = newPrice;
	}

}