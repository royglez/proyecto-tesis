/**
 * @author Rodrigo Gonzalez
 */

package facefinder;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.objdetect.CascadeClassifier;

public class ObjectsFinder {
	/**
	 * This class finds an specific object in a given image.
	 * The class must be feed with a cascade classifier (XML) file.
	 *  
	 * @class ObjectsFinder
	 * @param {String} Cascade classifier file location.
	 * @constructor
	 */
	public ObjectsFinder(String cascade){
		if(cascade != null && !cascade.isEmpty()){
			cascadeClassificator = new CascadeClassifier(cascade);
		}else{
			System.out.println("The cascade file must not be empty. Please Verify.");
		}
	}
	
	/**
	 * Returns true if it was possible to detect an object in the last iteration.
	 * 
	 * @property detectedObject
	 * @type Boolean
	 */
	private boolean detectedObject = false;
	
	/**
	 * Region of Interest. An extraction of the image where the last object was located.
	 * 
	 * @property roi
	 * @type Mat Object
	 */
	private Mat roi = new Mat();
	
	/**
	 * Cascade classifier object.
	 * 
	 * @property cascadeClassificator
	 * @type CascadeClassifier Object
	 */
	private CascadeClassifier cascadeClassificator =  null;
	
	/**
	 * Array containing the detected objects.
	 * 
	 * @property objectsDetected
	 * @type MatOfRect Object
	 */
	private MatOfRect objectsDetected = new MatOfRect();
	
	/**
	 * Method that returns the region of interest where the last object was found.
	 * 
	 * @return {Object} Mat image.
	 */
	public Mat getROI(){
		return this.roi;
	}
	
	/**
	 * Returns a boolean value that indicates if it was possible to identify an object in the last iteration.
	 * 
	 * @return {Boolean}.
	 */
	public boolean getDetectedObject(){
		return this.detectedObject;
	}
	
	/**
	 * Sets or changes the cascade classifier file.
	 * 
	 * @param {String} Cascade classifier file location.
	 */
	public void setCascadeClassifier(String cascade){
		if(!cascade.isEmpty() && cascade != null){
			if(cascadeClassificator != null){
				cascadeClassificator.load(cascade);
			}else{
				cascadeClassificator = new CascadeClassifier(cascade);
			}
		}else{
			System.out.println("The cascade file must not be empty. Please Verify.");
		}
	}
	
	/**
	 * Method that finds all the objects in an image, based on the cascade classifier file.
	 * 
	 * @param {Object} Expects a Mat image.
	 * @return {Object} Returns an image with the located objects marked with a square.
	 */
	public Mat findObject(Mat image) {
		if(!cascadeClassificator.empty() && cascadeClassificator != null){
			Size minImgSize = image.size();
			minImgSize.width /= 20;
			minImgSize.height /= 20;
			cascadeClassificator.detectMultiScale(image, objectsDetected, 1.7, 2, 1, minImgSize, image.size());
			
			if(objectsDetected.toArray().length > 0){
				int X = objectsDetected.toArray()[0].x;
				int Y = objectsDetected.toArray()[0].y;
				int Width = objectsDetected.toArray()[0].width;
				int Height = objectsDetected.toArray()[0].height;
				
				roi = new Mat(image,new Rect(X,Y,Width,Height));
				Core.rectangle(image, new Point(X, Y), new Point(X + Width,Y + Height), new Scalar(0, 255, 0));
				this.detectedObject = true;
			}else{
				roi = image;
				this.detectedObject = false;
			}
			return image;
		}else{
			System.out.println("The cascade file must not be empty. Please Verify.");
		}
		return null;
	}
}
