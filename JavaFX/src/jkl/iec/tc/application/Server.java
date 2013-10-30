package jkl.iec.tc.application;

import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jkl.iec.net.sockets.IECServer;
import jkl.iec.tc.fx.gui.IECComboBox;
import jkl.iec.tc.fx.gui.IECListTablePane;
import jkl.iec.tc.fx.gui.IECstage;

public class Server extends Application{
	
	public final static Logger log = Logger.getLogger(Server.class .getName()); 
	
//	public static final String VersionFile = "jkl/iec/tc/application/images/version.ser"; 
	public static final String VersionFile = "images/version.ser"; 
	public static Properties VersionProperties;
	
	static final IECServerTraceHandler loghandler =new IECServerTraceHandler();
	static final IECListTablePane iecPane = new IECListTablePane();
	static final IECComboBox ieccombobox = new IECComboBox();
	static final IECTCEventListener listener = new IECTCEventListener();
	static final IECServerFileWorker files= new IECServerFileWorker();

	static Stage stage =null;
	static IECServer iecserver ;	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	public void init(){
		VersionProperties = files.getVersionProperties();
		iecPane.start();
		iecPane.itemlist.setIECTCStreamListener(listener);
    	log.addHandler(loghandler);
		IECServer.log.setLevel(Level.INFO);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent p = null;
		
		URL url = IECstage.class.getResource("/jkl/iec/tc/fxml/IECServer.fxml");
    	log.info("URL: "+ url );
    	p = (Parent) loader.load(url.openStream());
    	primaryStage.setScene(new Scene(p));
//        IECServerStageController ic = loader.getController();
//        ic.init();
		stage = primaryStage;
	
		primaryStage.getIcons().add(new Image("/jkl/iec/tc/application/images/IEC.PNG"));
		primaryStage.setTitle("  IEC-TestServer");
		log.info("Server start");
		primaryStage.show();	
	}
	
	public void stop(){
		iecPane.stop();
		if (iecserver != null) {
			iecserver.interrupt();
		}
	}
}
