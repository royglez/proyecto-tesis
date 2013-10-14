package application;

import java.awt.image.BufferedImage;

import facefinder.ImageSource;
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
	
	@FXML private ImageView imagePanel;
	
	private AnimationTimer cameraImages  = new AnimationTimer() {
		@Override
		public void handle(long arg0) {
			BufferedImage img = ImageSource.getInstance().getImage();
			Image image = SwingFXUtils.toFXImage(img, null); //Convert from BufferedImage to FX Image
			setImage(image);
		}
	};
	
	public final void setImage(Image img){
		imageProperty.set(img);
	}
	
	@FXML protected void handleShowImageBtn(ActionEvent event) {
		imagePanel.imageProperty().bind(imageProperty);
		this.cameraImages.start();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		
	}
	
}
