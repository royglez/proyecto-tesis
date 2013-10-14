/**
 * 
 */
package facefinder;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import java.awt.image.BufferedImage;

/**
 * @author Rodrigo González
 *
 * Singleton Class
 *
 */

public final class ImageSource {
	
	private static ImageSource instance = null;
	private final static VideoCapture camera = new VideoCapture();
	
	private ImageSource(){}
	
	/**
	 * Double Checked Locking
	 * If a threat wants to access at the same time another threat is using this method
	 * the second threat will have to wait until the first one finishes using it.
	 * @return
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
	
	//Read an image form the camera and return it as a BufferedImage
	public BufferedImage getImage(){
		Mat image = new Mat();
		
		camera.read(image);
		return matToBufferedImage(image);
	}
	
	//Convert from Mat to BufferedImage
    private static BufferedImage matToBufferedImage (final Mat matrix) {
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
