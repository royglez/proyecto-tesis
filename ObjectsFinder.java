package facefinder;

import org.opencv.core.MatOfRect;
import org.opencv.objdetect.CascadeClassifier;

public class ObjectsFinder {
	
	private CascadeClassifier cascadeClassificator =  null;
	private MatOfRect objects = new MatOfRect();

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
	
	public void findObject() {
		
	}
}
