package application;


import org.opencv.core.Mat;

import facefinder.ImageSource;
import facefinder.ObjectsFinder;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FXController extends Application {
	
	SimpleObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
	ObjectsFinder faceFinder = new ObjectsFinder(System.getProperty("user.dir") + "/resources/haarcascade_frontalface_alt.xml");
	ObjectsFinder eyeFinder = new ObjectsFinder(System.getProperty("user.dir") + "/resources/haarcascade_mcs_righteye.xml");
	
	@FXML private ImageView imagePanel;
	
	private AnimationTimer cameraImages  = new AnimationTimer() {
		@Override
		public void handle(long arg0) {
			Mat img = ImageSource.getInstance().getImage();
			if(img != null) {
				faceFinder.findObject(img);
				if(faceFinder.getDetectedObject()){
					eyeFinder.findObject(faceFinder.getROI());
					Image image = SwingFXUtils.toFXImage(ImageSource.matToBufferedImage(eyeFinder.getROI()), null); //Convert from BufferedImage to FX Image
					setImage(image);
				}
			}
		}
	};
	
	public final void setImage(Image img){
		imageProperty.set(img);
	}
	
	@FXML protected void handleShowImageBtn(ActionEvent event) {
		imagePanel.imageProperty().bind(imageProperty);
		this.cameraImages.start();
	}
	
	@FXML protected void handleCalibrateImageBtn(ActionEvent event) {
		System.out.println("Calibrate Image");
	}

	@Override
	public void start(Stage arg0) throws Exception {
		
	}
	
}
