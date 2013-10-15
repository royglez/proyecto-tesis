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
	
	private CascadeClassifier cascadeClassificator =  null;
	private MatOfRect objectsDetected = new MatOfRect();

	public ObjectsFinder(String cascade){
		if(cascade != null && !cascade.isEmpty()){
			cascadeClassificator = new CascadeClassifier(cascade);
		}else{
			System.out.println("The cascade file must not be empty. Please Verify.");
		}
	}
	
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
	
	public Mat findObject(Mat image) {
		if(!cascadeClassificator.empty() && cascadeClassificator != null){
			Size minImgSize = image.size();
			minImgSize.width /= 20;
			minImgSize.height /= 20;
			cascadeClassificator.detectMultiScale(image, objectsDetected, 1.7, 2, 1, minImgSize, image.size());
			
			if(objectsDetected.toArray().length > 0){
				for(Rect rect : objectsDetected.toArray()) {
					Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
				}
			}
			return image;
		}else{
			System.out.println("The cascade file must not be empty. Please Verify.");
		}
		return null;
	}
}
