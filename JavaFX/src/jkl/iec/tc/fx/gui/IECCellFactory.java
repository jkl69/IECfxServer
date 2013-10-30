package jkl.iec.tc.fx.gui;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import jkl.iec.tc.bean.type.IECTCItem;
import jkl.iec.tc.bean.type.IECTCObject;
import jkl.iec.tc.fx.gui.IECCellFactory.IECvalueProperty;
   

class IECVcell extends TableCell<IECTCItem, IECTCObject> {
//class IECVcell extends TableCell<IECTCItem, Double> {
		
	IECvalueProperty iecval = null;
/*	
	IECVcell(){
		this.iecval = (IECvalueProperty) getTableColumn().getUserData();
	}
*/	
	private IECTCObject oldiob;
	private InvalidationListener listener =null;
	 
    public void updateItem(final IECTCObject item, boolean empty) {     
        super.updateItem(item, empty);
        iecval = (IECvalueProperty) getTableColumn().getUserData();
//        System.out.println("ColumnObject:"+iecval);
//        textProperty().unbind();
        if(oldiob != null) {
           oldiob.ValProperty().removeListener(listener);
           oldiob = null;
          }
        
        if (! empty) {
        	listener = new InvalidationListener() {
                public void invalidated(Observable o) {
 //                   System.out.println("Invalid Object:"+o);
                	if (iecval == IECvalueProperty.value){
                    	setText(item.getValueasString());
                    }
                    if (iecval == IECvalueProperty.qu){
                    	setText(getItem().getQUasString());
//                    	setText(String.valueOf(getItem().getQU()));
                    }                	
//                    setText(String.valueOf(item.getVal()));
                }
              };

//    		setText(getItem().getValueasString());
//         	textProperty().bind(item.ValProperty().asString());

//            item.ValProperty().addListener(listener);  
            if (iecval == IECvalueProperty.value){
                item.ValProperty().addListener(listener);  
                setText(getItem().getValueasString());
            }
            if (iecval == IECvalueProperty.qu){
                item.QUProperty().addListener(listener);  
//            	setText(String.valueOf(getItem().getQU()));
            	setText(getItem().getQUasString());
            }
      	}else {
            setText(null);
      	}
        setGraphic(null);
    }
}

public class IECCellFactory implements Callback<TableColumn<IECTCItem, IECTCObject>, TableCell<IECTCItem, IECTCObject>>{

	public enum IECvalueProperty {value,qu,time,sim}

	@Override
	public TableCell<IECTCItem, IECTCObject> call(final TableColumn<IECTCItem, IECTCObject> col) {
		final TableCell<IECTCItem, IECTCObject> cell = new IECVcell();
		
		cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            if (event.getClickCount() > 0) {
//            	System.out.println(col.getUserData());
            	IECvalueProperty iecval = (IECvalueProperty) cell.getTableColumn().getUserData();
            	IECTCObject o = (IECTCObject) cell.itemProperty().getValue();
			    TableCell<IECTCItem, IECTCObject> c = (TableCell<IECTCItem, IECTCObject>) event.getSource();
                System.out.println("Cell text: " + c.getText());
//                if (o != null) {
                if ((o != null )&&(o.item.isM_typ())) {
                    IECstage iecDialog = new IECstage(o,iecval);
                    iecDialog.show();               	
                }

            }
        }
      });
		return cell;
	}
}
