package jkl.iec.net.fx.gui;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class IECSocketStageController  {

//    @FXML private URL location;
	@FXML protected Pane rootPane;
	
	@FXML protected void handleOnKeyPressed(KeyEvent ke ) {
        System.out.println("Key Pressed: " + ke.getCode());
        if (ke.getCode() == KeyCode.ESCAPE)  {
        	closeStage();
        }
        if (ke.getCode() == KeyCode.ENTER)  {
            	saveStage();
            }
    }
	

	protected void closeStage() {
		Stage stage = (Stage) rootPane.getScene().getWindow();
		stage.close();	
	}

	
	void saveStage() {
		closeStage();
	}

	void loadStage() {
	}

    public void initialize() {
    }
}
