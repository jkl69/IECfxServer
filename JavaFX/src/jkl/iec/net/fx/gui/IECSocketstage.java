package jkl.iec.net.fx.gui;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jkl.iec.tc.bean.type.IECTCObject;
import jkl.iec.tc.fx.gui.IECObjectCellFactory.IECvalueProperty;

public class IECSocketstage extends Stage {
	IECTCObject iob;
	IECvalueProperty iecp;
    
	Parent root = null;
    final FXMLLoader loader = new FXMLLoader();
    final String fxml = "/jkl/iec/net/fxml/IECSocketDlg.fxml";
    
	public IECSocketstage() {
//        scene.getStylesheets().add(urlstyle);
        URL url = IECSocketstage.class.getResource(fxml);
        
        try {
//  		root = (Parent) loader.load(IECstage.class.getResource(fxml).openStream());
 			root = (Parent) loader.load(url.openStream());
  		} catch (IOException e) {
  			e.printStackTrace();
  		}
		
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.UNDECORATED); 
        setResizable(false);
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/jkl/iec/tc/fxml/IECscene.css");
        setScene(scene);
	}

}
