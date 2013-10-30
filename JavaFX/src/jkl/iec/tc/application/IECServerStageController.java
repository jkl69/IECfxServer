package jkl.iec.tc.application;

import java.util.Enumeration;
import java.util.Properties;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
	
    @FXML private TableView<VersionData> InfoTable;

    private final ObservableList<VersionData> versiondata =FXCollections.observableArrayList();
                
    @FXML void addButtonAction(ActionEvent event) {
    	System.out.println(event);
    	IECTCItem it=new IECTCItem(IECMap.getType(Server.ieccombobox.getValue()));
		it.data =new IECSimProperties(it);
		Server.iecPane.add(it);
    }
    
    @FXML void startButtonAction(ActionEvent event) {
    	System.out.println(event);
    	Server.iecserver = new IECServer();
    	Server.iecserver.setIECServerListener(Server.listener);
		Server.iecserver.setLoghandler(Server.loghandler);
		ClientList.setItems(Server.iecserver.clients);
		TableColumn<IECSocket,String> tc;
		tc = (TableColumn<IECSocket, String>) ClientList.getColumns().get(0);
		tc.setCellValueFactory( new PropertyValueFactory<IECSocket,String>("Idx"));
		tc = (TableColumn<IECSocket, String>) ClientList.getColumns().get(1);
		tc.setCellValueFactory( new PropertyValueFactory<IECSocket,String>("RemoteAddress"));		
		TableColumn<IECSocket,IECSocketStatus> cc;
		cc = (TableColumn<IECSocket, IECSocketStatus>) ClientList.getColumns().get(2);
		cc.setCellValueFactory( new PropertyValueFactory<IECSocket,IECSocketStatus>("Iecstatus"));		
	
		Server.iecserver.start();
    }
    
    @FXML void stopButtonAction(ActionEvent event) {
    	System.out.println(event);
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
    	controllPanel.getChildren().add(0,Server.ieccombobox);
    	ItemPanel.getChildren().add(Server.iecPane);
    	InfoTable.setItems(versiondata);
    	initVersionData(Server.VersionProperties);
    	Server.loghandler.textArea = LogArea;
//        ClientList.setItems(Server.iecserver.clients);

		}

}
