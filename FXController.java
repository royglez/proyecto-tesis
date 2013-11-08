package application;


import org.opencv.core.Mat;
import org.opencv.core.Rect;

import facefinder.ImageSource;
import facefinder.ObjectsFinder;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FXController extends Application {
	
	SimpleObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
	ObjectsFinder faceFinder = new ObjectsFinder(System.getProperty("user.dir") + "/resources/haarcascade_frontalface_alt.xml");
	ObjectsFinder eyeFinder = new ObjectsFinder(System.getProperty("user.dir") + "/resources/haarcascade_lefteye_2splits.xml");
	
	@FXML private ImageView imagePanel;
	@FXML private Label leftEyeLbl;
	@FXML private Label rightEyeLbl;
	
	
	private AnimationTimer cameraImages  = new AnimationTimer() {
		@Override
		public void handle(long arg0) {
			Mat img = ImageSource.getInstance().getImage();
			if(img != null) {
				faceFinder.findObject(img);
				if(faceFinder.getDetectedObject()){
					Mat leftFace = new Mat(faceFinder.getROI(), new Rect(0,0,faceFinder.getROI().width()/2,faceFinder.getROI().height()/2));
					Mat rightFace = new Mat(faceFinder.getROI(), new Rect(faceFinder.getROI().width()/2,0,faceFinder.getROI().width()/2,faceFinder.getROI().height()/2));
					
					eyeFinder.findObject(leftFace);
					
					if(eyeFinder.getDetectedObject() == true){
						leftEyeLbl.textProperty().set("Open");
						leftEyeLbl.setTextFill(Color.web("#33FF33"));
					}else{
						leftEyeLbl.textProperty().set("Closed");
						leftEyeLbl.setTextFill(Color.web("#990000"));
					}
					
					eyeFinder.findObject(rightFace);
					
					if(eyeFinder.getDetectedObject() == true){
						rightEyeLbl.textProperty().set("Open");
						rightEyeLbl.setTextFill(Color.web("#33FF33"));
					}else{
						rightEyeLbl.textProperty().set("Closed");
						rightEyeLbl.setTextFill(Color.web("#990000"));
					}
					
					Image image = SwingFXUtils.toFXImage(ImageSource.matToBufferedImage(img), null); //Convert from BufferedImage to FX Image
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
		this.faceFinder.setScaleFactor(1.9);
		this.faceFinder.setMinNeighbors(3);
		this.faceFinder.setMinSizeFactor(30);
		this.faceFinder.setMaxSizeFactor(1.8);
		
		this.eyeFinder.setScaleFactor(3);
		this.eyeFinder.setMinNeighbors(2);
		this.eyeFinder.setMinSizeFactor(80);
		this.eyeFinder.setMaxSizeFactor(2);
		
		this.cameraImages.start();
	}
	
	@FXML protected void handleCalibrateImageBtn(ActionEvent event) {
		System.out.println("Calibrate Image");
	}

	@Override
	public void start(Stage arg0) throws Exception {
		
	}
	
}
