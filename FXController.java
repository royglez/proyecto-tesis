package application;

import java.awt.image.BufferedImage;

import facefinder.ImageSource;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class FXController {
	@FXML private ImageView imagePanel;
	
	ImageSource imageFromCam = new ImageSource();

	private void displayImageFromCamera(){
		
		//for(int i = 0; i < 100; i++){
			BufferedImage img = imageFromCam.getImage();
			Image image = SwingFXUtils.toFXImage(img, null); //Convert from BufferedImage to FX Image
			imagePanel.setImage(image);
		//}
	}
	
	@FXML protected void handleShowImageBtn(ActionEvent event) {
		displayImageFromCamera();
	}
	
}
