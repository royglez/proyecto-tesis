package application;

import java.awt.image.BufferedImage;

import facefinder.ImageSource;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FXController extends Application {
	
	SimpleObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
	
	@FXML private ImageView imagePanel;
	ImageSource imageFromCam = new ImageSource();
	
	private AnimationTimer cameraImages  = new AnimationTimer() {
		@Override
		public void handle(long arg0) {
			// TODO Auto-generated method stub
			BufferedImage img = imageFromCam.getImage();
			Image image = SwingFXUtils.toFXImage(img, null); //Convert from BufferedImage to FX Image
			setImage(image);
			System.out.println("Hola");
		}
	};
	
	public final void setImage(Image img){
		imageProperty.set(img);
	}
	
	public final Image getImage(){
		return imageProperty.get();
	}

	private void displayImageFromCamera(){		
		this.cameraImages.start();
	}
	
	@FXML protected void handleShowImageBtn(ActionEvent event) {
		imagePanel.imageProperty().bind(imageProperty);
		displayImageFromCamera();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		Runtime.getRuntime().addShutdownHook(new Thread()
	    {
	        @Override
	        public void run()
	        {
	        	System.out.println("Saliendo");
	            cameraImages.stop();
	            imageFromCam.destroyCamera();
	        }
	    });
	}
	
}
