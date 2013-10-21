/**
 * @author Rodrigo Gonzalez
 */

package facefinder;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import java.awt.image.BufferedImage;


public final class ImageSource {
	/**
	 * Singleton class that provides the images from the camera.
	 * 
	 * @class ImageSource
	 * @static
	 */
	private ImageSource(){}
	
	/**
	 * Singleton instance for the class.
	 * 
	 * @property instance
	 * @type ImageSource Object
	 * @static
	 */
	private static ImageSource instance = null;
	
	/**
	 * Video camera object.
	 * 
	 * @property camera
	 * @type VideoCapture Object
	 * @final
	 * @static
	 */
	private final static VideoCapture camera = new VideoCapture();
	
	/**
	 * Image obtained from the camera.
	 * 
	 * @property image
	 * @type Mat Object
	 */
	private Mat image = new Mat();
	
	/**
	 * Retrieves an image taken by the camera.
	 * 
	 * @method getImage
	 * @return {Object} Returns an image in Mat (OpenCV image) format if possible, otherwise returns null.
	 */
	public Mat getImage() {
		camera.read(image);
		if(!image.empty()){
			return this.image;
		}else{
			return null;
		}
	}

	/**
	 * Retrieves the objects instance of the singleton class.
	 * NOTE: If a threat wants to access at the same time another threat is using this method
	 * the second threat will have to wait until the first one finishes using it.
	 * 
	 * @method getInstance
	 * @return {Object} Returns the ImageSource class instance.
	 */
	public static ImageSource getInstance() {
		if(instance == null) {
			synchronized (ImageSource.class){
				if(instance == null) {
					camera.open(-1);
					instance = new ImageSource();
				}
			}
		}
		return instance;
	}
	
	public void destroyCamera(){
		camera.release();
	}
	
	/**
	 * Convert from Mat to BufferedImage format.
	 * 
	 * @mathod matToBufferedImage
	 * @param {Object} Expects a Mat image
	 * @return {Object} Returns a BufferedImage format image
	 */
    public static synchronized BufferedImage matToBufferedImage (final Mat matrix) {
        int cols = matrix.cols();
        int rows = matrix.rows();
        int elemSize = (int)matrix.elemSize();
        byte[] data = new byte[cols * rows * elemSize];
        int type;

        matrix.get(0, 0, data);

        switch (matrix.channels()) {
            case 1:
                type = BufferedImage.TYPE_BYTE_GRAY;
                break;

            case 3: 
                type = BufferedImage.TYPE_3BYTE_BGR;

                // bgr to rgb
                byte b;
                for(int i=0; i<data.length; i=i+3) {
                    b = data[i];
                    data[i] = data[i+2];
                    data[i+2] = b;
                }
                break;

            default:
                return null;
        }

        BufferedImage bImage = new BufferedImage(cols, rows, type);
        bImage.getRaster().setDataElements(0, 0, cols, rows, data);

        return bImage;
    }
	
}
