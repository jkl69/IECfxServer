package jkl.iec.tc.application;

import java.util.Enumeration;
import java.util.Properties;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import jkl.iec.net.sockets.IECServer;
import jkl.iec.net.sockets.IECSocket;
import jkl.iec.net.sockets.IECSocket.IECSocketStatus;
import jkl.iec.tc.bean.type.IECMap;
import jkl.iec.tc.bean.type.IECTCItem;
import jkl.iec.tc.bean.utils.IECSimProperties;


public class IECServerStageController {

//    @FXML private URL location;

    @FXML private HBox controllPanel;
    @FXML private StackPane ItemPanel;
    @FXML private TextField ServerPortText;
    @FXML private TextArea LogArea;
    @FXML private TableView<IECSocket> ClientList;
    @FXML private TableView<VersionData> InfoTable;
    @FXML private Button   ServerStartButton;
    @FXML private Button   ServerStopButton;
    
    public static class VersionData {
	    private final SimpleStringProperty key;
	    private final SimpleStringProperty value;
	 
	    public VersionData(String k, String v) {
	        this.key = new SimpleStringProperty(k);
	        this.value = new SimpleStringProperty(v);
	    }
	 
	    public String getKey() {
	        return key.get();
	    }
	    public void setKey(String k) {
	        key.set(k);
	    }
	        
	    public String getValue() {
	        return value.get();
	    }
	    public void setValue(String v) {
	        value.set(v);
	    }
	}
	
    private final ObservableList<VersionData> versiondata =FXCollections.observableArrayList();
                
    @FXML void addButtonAction(ActionEvent event) {
    	System.out.println(event);
    	IECTCItem it=new IECTCItem(IECMap.getType(Server.ieccombobox.getValue()));
		it.data =new IECSimProperties(it);
		Server.iecPane.add(it);
    }

    @FXML void clearButtonAction(ActionEvent event) {
    	System.out.println(event);
    	Server.iecPane.itemlist.clear();
    }
    
    @FXML void startButtonAction(ActionEvent event) {
    	System.out.println(event);
    	Server.iecserver = new IECServer();
    	Server.iecserver.setIECServerListener(Server.listener);
		Server.iecserver.setLoghandler(Server.loghandler);
		Server.iecserver.IECPort = Integer.parseInt(ServerPortText.getText());
		
		ClientList.setItems(Server.iecserver.clients);

		Server.iecserver.start();
		ServerPortText.setDisable(true);
    	ServerStartButton.setDisable(true);
		ServerStopButton.setDisable(false);
		
    }
    
    @FXML void stopButtonAction(ActionEvent event) {
    	System.out.println(event);
    	if (Server.iecserver.isAlive()) {
    		Server.iecserver.interrupt();
    	}
		ServerPortText.setDisable(false);
		ServerStartButton.setDisable(false);
		ServerStopButton.setDisable(true);
    }

    @FXML void loadButtonAction(ActionEvent event) {
    	Server.files.load();
    }

    @FXML void saveButtonAction(ActionEvent event) {
    	Server.files.save();
    }

    private void initVersionData(Properties p) {
		Enumeration<?> it = p.propertyNames();
		String key;
		while (it.hasMoreElements()) {
			key = it.nextElement().toString();
			versiondata.add(new VersionData(key,p.getProperty(key)));
		}
    }
    
    public void initialize() {
//        assert pp1 == null : "fx:id=\"p1\" was not injected: check your FXML file 'IECServer.fxml'.";
    	System.out.println("STYLE"+ServerPortText.getStyle());
    	ServerPortText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0,	String arg1, String arg2) {
				try {
					Integer.parseInt(arg2);
					/*					ServerPortText.setStyle("-fx-background-color: red ,-fx-text-box-border ,white  ;" +
							"-fx-skin: \"com.sun.javafx.scene.control.skin.TextFieldSkin\";"+
//						    "-fx-background-color: -fx-shadow-highlight-color, -fx-text-box-border, -fx-control-inner-background;"+
						    "-fx-background-insets: 0, 1, 2;"+
						    "-fx-background-radius: 3, 2, 2;"+
						    "-fx-padding: 3 5 3 5;"+
						    "-fx-text-fill: -fx-text-inner-color;"+
						    "-fx-prompt-text-fill: derive(-fx-control-inner-background,-30%);"+
						    "-fx-cursor: text;");*/
					ServerPortText.setStyle("-fx-fx-background-color: -fx-focus-color, -fx-text-box-border, -fx-control-inner-background; "+
				    	"-fx-background-insets: -0.4, 1, 2;"+
				    	"-fx-background-radius: 3.4, 2, 2;"+
				    	"-fx-prompt-text-fill: transparent;");
					ServerStartButton.setDisable(false);
				} catch (Exception e) {
					ServerPortText.setStyle("-fx-background-color: -fx-focus-color, -fx-text-box-border, yellow ;"+  //yellow
							"-fx-background-insets: 0.4, 1, 2;"+
							"-fx-background-radius: 3, 2, 2;"+
							"-fx-padding: 3 5 3 5;"+
							"-fx-text-fill: red;");
//							"-fx-prompt-text-fill: derive(-fx-control-inner-background,-30%);"+
//							"-fx-cursor: text;"); 
					ServerStartButton.setDisable(true);
				}
			}
        });
		controllPanel.getChildren().add(0,Server.ieccombobox);
    	ItemPanel.getChildren().add(Server.iecPane);
    	InfoTable.setItems(versiondata);
    	initVersionData(Server.VersionProperties);
    	Server.loghandler.textArea = LogArea;

		}

}
