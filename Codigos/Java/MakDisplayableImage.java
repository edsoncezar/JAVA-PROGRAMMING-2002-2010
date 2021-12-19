package beans;

import java.io.Serializable;

/**
 * MakDisplayableImage is the base class for a set of classes
 * that represent an image file and its related information.
 *
 * Key fields are supported if this object will be stored in a database.
 *
 * Thumbnail and medium size images are supported.
 * Relative or absolute pathnames are maintained
 * to the file location of each image.
 *
 * @author      Michael Kellstrand
 * @version     1.0
 */
public class MakDisplayableImage implements Serializable
{

	// The key for this object if inserted into a database
	private int image_id;

	// A text caption for this image
	private String image_caption;

	// The thumbnail image
	// The relative path to this image file
	private String tn_image_path;

	// The pixel dimensions of this image
	private int tn_image_x;
	private int tn_image_y;

	// The key for the thumbnail image, if inserted into a database
	private int tn_image_id;

	// The medium size image
	// The relative path to this image file
	private String med_image_path;

	// The pixel dimensions of this image
	private int med_image_x;
	private int med_image_y;

	// The key for the medium image, if inserted into a database
	private int med_image_id;


	/**
     * Initializes a newly created MakDisplayableImage object
     * by resetting all fields.
     *
	 */
	public MakDisplayableImage()
	{
		image_id = -1;
		image_caption = "";
		tn_image_path = "";
		tn_image_x = 0;
		tn_image_y = 0;
		med_image_path = "";
		tn_image_id = -1;
		med_image_id = -1;
	}

	/**
     * Returns the database table id for the image data.
     *
	 * @return	the image id
	 */
	public int getImageId()
	{
		return (image_id);
	}

	/**
     * Sets the database table id for the image data.
     *
     * @param  id   the image id
     */
	public void setImageId(int id) {
		image_id = id;
	}

	/**
     * Returns the database table id for the thumbnail image.
     *
	 * @return	the thumbnail image id
	 */
	public int getTnImageId()
	{
		return (tn_image_id);
	}

	/**
     * Sets the database table id for the thumbnail image.
     *
     * @param  id   the thumbnail image id
     */
	public void setTnImageId(int id)
	{
		tn_image_id = id;
	}

	/**
     * Returns the database table id for the medium image.
     *
	 * @return	the medium image id
	 */
	public int getMedImageId()
	{
		return (med_image_id);
	}

	/**
     * Sets the database table id for the medium image.
     *
     * @param  id   the medium image id
     */
	public void setMedImageId(int id)
	{
		med_image_id = id;
	}

	/**
     * Returns the caption associated with this image.
     *
	 * @return	the image caption
	 */
	public String getCaption()
	{
		return (image_caption);
	}

	/**
     * Sets the caption associated with this image.
     *
     * @param  caption  the caption for this image
     */
	public void setCaption(String caption)
	{
		if (caption != null) {
			image_caption = caption;
		}
	}

	/**
     * Returns the pathname to the thumbnail image file.
     * This can be a relative or absolute path
     * depending on how it is to be used.
     *
	 * @return	the image pathname
	 */
	public String getTnPath()
	{
		return (tn_image_path);
	}

	/**
     * Sets the pathname to the thumbnail image file.
     * This can be a relative or absolute path
     * depending on how it is to be used.
     *
     * @param  path  the image pathname
     */
	public void setTnPath(String path)
	{
		tn_image_path = path;
	}

	/**
     * Returns the pathname to the medium size image file.
     * This can be a relative or absolute path
     * depending on how it is to be used.
     *
	 * @return	the image pathname
	 */
	public String getMedPath()
	{
		return (med_image_path);
	}

	/**
     * Sets the pathname to the medium size image file.
     * This can be a relative or absolute path
     * depending on how it is to be used.
     *
     * @param  path  the image pathname
     */
	public void setMedPath(String path)
	{
		med_image_path = path;
	}

	/**
     * Returns the pixel width of the thumbnail image file.
     * This image width and height should be provided with
     * any image sent to the browser to help format the page.
     *
	 * @return	the thumbnail image pixel width
	 */
	public int getTnImageX()
	{
		return (tn_image_x);
	}

	/**
     * Sets the pixel width of the thumbnail image file.
     * This image width and height should be provided with
     * any image sent to the browser to help format the page.
     *
     * @param  x   the thumbnail image pixel width
     */
	public void setTnImageX(int x)
	{
		tn_image_x = Math.max(0,x);
	}

	/**
     * Returns the pixel height of the thumbnail image file.
     * This image width and height should be provided with
     * any image sent to the browser to help format the page.
     *
	 * @return	the thumbnail image pixel height
	 */
	public int getTnImageY()
	{
		return (tn_image_y);
	}

	/**
     * Sets the pixel height of the thumbnail image file.
     * This image width and height should be provided with
     * any image sent to the browser to help format the page.
     *
     * @param  x   the thumbnail image pixel height
     */
	public void setTnImageY(int y)
	{
		tn_image_y = Math.max(0,y);
	}


	/**
     * Returns the pixel width of the medium image file.
     * This image width and height should be provided with
     * any image sent to the browser to help format the page.
     *
	 * @return	the medium image pixel width
	 */
	public int getMedImageX()
	{
		return (med_image_x);
	}

	/**
     * Sets the pixel width of the medium image file.
     * This image width and height should be provided with
     * any image sent to the browser to help format the page.
     *
     * @param  x   the medium image pixel width
     */
	public void setMedImageX(int x)
	{
		med_image_x = Math.max(0,x);
	}

	/**
     * Returns the pixel height of the medium image file.
     * This image width and height should be provided with
     * any image sent to the browser to help format the page.
     *
	 * @return	the medium image pixel height
	 */
	public int getMedImageY()
	{
		return (med_image_y);
	}

	/**
     * Sets the pixel height of the medium image file.
     * This image width and height should be provided with
     * any image sent to the browser to help format the page.
     *
     * @param  x   the medium image pixel height
     */
	public void setMedImageY(int y)
	{
		med_image_y = Math.max(0,y);
	}


}

