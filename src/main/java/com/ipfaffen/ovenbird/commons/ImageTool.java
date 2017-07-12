package com.ipfaffen.ovenbird.commons;

import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.media.jai.InterpolationBicubic;
import javax.media.jai.InterpolationBicubic2;
import javax.media.jai.InterpolationBilinear;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import com.sun.media.jai.codec.ByteArraySeekableStream;
import com.sun.media.jai.codec.FileSeekableStream;

/**
 * A java version of thumbnail generator.
 * 
 * @author sverdianto
 * @see http://www.grails.org/plugin/image-tools
 * @version 1.0.4
 */
public class ImageTool {

	@SuppressWarnings("rawtypes")
	static Map masks = new HashMap();
	@SuppressWarnings("rawtypes")
	static Map alphas = new HashMap();

	private RenderedOp original = null;
	private RenderedOp image = null;
	private RenderedOp result = null;
	private RenderedOp alpha = null;
	private RenderedOp mask = null;

	/**
	 * Removes the accelaration lib exception
	 */
	static {
		System.setProperty("com.sun.media.jai.disableMediaLib", "true");
	}

	/**
	 * Should a thumbnail be created only if it will be smaller in size than the current image?
	 */
	boolean decreaseOnly = true;

	/**
	 * Returns the height for the currently loaded image
	 * 
	 * @return height of the currently loaded image
	 */
	public int getHeight() {
		return image.getHeight();
	}

	/**
	 * Returns the width for the currently loaded image
	 * 
	 * @return width of the currently loaded image
	 */
	public int getWidth() {
		return image.getWidth();
	}

	/**
	 * Saves a snapshot of the currently loaded image
	 * 
	 */
	public void saveOriginal() {
		original = (RenderedOp) image.createSnapshot();
	}

	/**
	 * Restores a snapshot onto the original image.
	 * 
	 */
	public void restoreOriginal() {
		image = (RenderedOp) original.createSnapshot();
	}

	/**
	 * Loads an image from a file.
	 * 
	 * @param file path to the file from which the image should be loaded
	 * @throws IOException
	 */
	public void load(String file) throws IOException {
		FileSeekableStream fss = new FileSeekableStream(file);
		image = JAI.create("stream", fss);
	}

	/**
	 * Loads a mask from a file and saves it on the cache, indexed by the file name
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void loadMask(String file) throws IOException {
		mask = (RenderedOp) ImageTool.masks.get(file);
		if(mask == null) {
			FileSeekableStream fss = new FileSeekableStream(file);
			mask = JAI.create("stream", fss);
			masks.put(file, mask);
		}
	}

	/**
	 * Loads an alpha mask from a file and saves it on the cache
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void loadAlpha(String file) throws IOException {
		alpha = (RenderedOp) ImageTool.alphas.get(file);
		if(alpha == null) {
			FileSeekableStream fss = new FileSeekableStream(file);
			alpha = JAI.create("stream", fss);
			alphas.put(file, alpha);
		}
	}

	/**
	 * Overwrites the current image with the latest result image obtained.
	 */
	public void swapSource() {
		image = result;
		result = null;
	}

	/**
	 * Loads an image from a byte array.
	 * 
	 * @param bytes array to be used for image initialization
	 * @throws IOException
	 */
	public void load(byte[] bytes) throws IOException {
		ByteArraySeekableStream byteStream = new ByteArraySeekableStream(bytes);
		image = JAI.create("stream", byteStream);
	}

	/**
	 * Writes the resulting image to a file.
	 * 
	 * @param file full path where the image should be saved
	 * @param type file type for the image
	 * @see <a *="" href="http://java.sun.com/products/java-media/jai/iio.html">Possible JAI encodings</a>
	 */
	public void writeResult(String file, String type) throws IOException {
		FileOutputStream os = new FileOutputStream(file);
		JAI.create("encode", result, os, type, null);
		os.close();
	}

	/**
	 * Returns the resulting image as a byte array.
	 * 
	 * @param type file type for the image
	 * @see <a *="" href="http://java.sun.com/products/java-media/jai/iio.html">Possible JAI encodings</a>
	 */
	public byte[] getBytes(String type) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		JAI.create("encode", image, bos, type, null);
		return bos.toByteArray();
	}

	/**
	 * @return
	 */
	public BufferedImage getBufferedImage() {
		return image.getAsBufferedImage();
	}

	/**
	 * Creates a thumbnail of a maximum length and stores it in the result image
	 * 
	 * @param edgeLength Maximum length
	 */
	public void thumbnail(float edgeLength) {
		if(getHeight() < edgeLength && getWidth() < edgeLength && decreaseOnly) {
			result = image;
		}
		else {
			boolean tall = (getHeight() > getWidth());
			float modifier = edgeLength / (float) (tall ? getHeight() : getWidth());
			ParameterBlock params = new ParameterBlock();
			params.addSource(image);
			params.add(modifier);// x scale factor
			params.add(modifier);// y scale factor
			params.add(0.0F);// x translate
			params.add(0.0F);// y translate
			params.add(new InterpolationNearest());// interpolation method
			result = JAI.create("scale", params);
		}
	}

	/**
	 * This method creates a thumbnail of the maxWidth and maxHeight it takes as a parameter
	 * 
	 * Example : Calling the method thumnailSpecial(640, 480, 1, 1) will never produce images larger than 640 on the
	 * width, and never larger than 480 on the height and use InterpolationBilinear(8) and scale
	 * 
	 * @param maxWidth The maximum width the thumbnail is allowed to have
	 * 
	 * @param maxHeigth The maximum height the thumbnail is allowed to have
	 * 
	 * @param interPolationType Is for you to choose what interpolation you wish to use 1 : InterpolationBilinear(8) //
	 * Produces good image quality with smaller image size(byte) then the other two 2 : InterpolationBicubic(8) //
	 * Supposed to produce better than above, but also larger size(byte) 3 : InterpolationBicubic2(8) // Supposed to
	 * produce the best of the three, but also largest size(byte)
	 * 
	 * @param renderingType Too choose the rendering type 1: Uses scale // Better on larger thumbnails 2: Uses
	 * SubsampleAverage // Produces clearer images when it comes to really small thumbnail e.g 80x60
	 */
	public void thumbnailSpecial(float maxWidth, float maxHeight, int interPolationType, int renderingType) {
		if(getHeight() <= maxHeight && getWidth() <= maxWidth) {
			/**
			 * Don't change, keep it as it is, even though one might loose out on the compression included below (not
			 * sure)
			 */
			result = image;
		}
		else {
			boolean tall = (getHeight() * (maxWidth / maxHeight) > getWidth());
			float modifier = maxWidth / (float) (tall ? (getHeight() * (maxWidth / maxHeight)) : getWidth());
			ParameterBlock params = new ParameterBlock();
			params.addSource(image);

			/**
			 * Had to do this because of that the different rendering options require either float or double
			 */
			switch(renderingType) {
				case 1:
					params.add(modifier);// x scale factor
					params.add(modifier);// y scale factor
					break;
				case 2:
					params.add((double) modifier);// x scale factor
					params.add((double) modifier);// y scale factor
					break;
				default:
					params.add(modifier);// x scale factor
					params.add(modifier);// y scale factor
					break;
			}

			params.add(0.0F);// x translate
			params.add(0.0F);// y translate
			switch(interPolationType) {
				case 1:
					params.add(new InterpolationBilinear(8));
					break; // Produces good image quality with smaller image
							// size(byte) then the other two
				case 2:
					params.add(new InterpolationBicubic(8));
					break; // Supposed to produce better than above, but also
							// larger
							// size(byte)
				case 3:
					params.add(new InterpolationBicubic2(8));
					break; // Supposed to produce the best of the two, but also
							// largest size(byte)
				default:
					params.add(new InterpolationBilinear(8));
					break;
			}

			switch(renderingType) {
				case 1:
					result = JAI.create("scale", params);
					break;
				case 2:
					result = JAI.create("SubsampleAverage", params);
					break;
				default:
					result = JAI.create("scale", params);
					break;
			}
		}
	}

	public void setImageToNull() {
		image = null;
	}

	/**
	 * Crops the image and stores the result
	 * 
	 * @param edgeX Horizontal crop. The image will be cropped edgeX/2 on both sides.
	 * @param edgeY Vertical crop. The image will be cropped edgeY/2 on top and bottom.
	 */
	public void crop(float edgeX, float edgeY) {
		ParameterBlock params = new ParameterBlock();
		params.addSource(image);
		params.add((float) (edgeX / 2));// x origin
		params.add((float) (edgeY / 2));// y origin
		params.add((float) (getWidth() - edgeX));// width
		params.add((float) (getHeight() - edgeY));// height
		result = JAI.create("crop", params);
	}

	/**
	 * Crops the image to a square, centered, and stores it in the result image
	 * 
	 */
	public void square() {
		float border = getWidth() - getHeight();
		float cropX, cropY;
		if(border > 0) {
			cropX = border;
			cropY = 0;
		}
		else {
			cropX = 0;
			cropY = -border;
		}
		crop(cropX, cropY);
	}

	/**
	 * Applies the currently loaded mask and alpha to the image
	 */
	public void applyMask() {
		ParameterBlock params = new ParameterBlock();
		params.addSource(mask);
		params.addSource(image);
		params.add(alpha);
		params.add(null);
		params.add(new Boolean(false));
		result = JAI.create("composite", params, null);
	}
}