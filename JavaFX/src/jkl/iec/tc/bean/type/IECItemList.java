package jkl.iec.tc.bean.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;


class MyObservableList extends SimpleListProperty
{
    //constructor
    MyObservableList(){
        super(FXCollections.observableArrayList());
    }

}

public class IECItemList extends ArrayList<IECTCItem> implements ObservableList<IECTCItem> {
//public class IECItemList implements ObservableList<IECTCItem> {
	
//	List<String> list = new ArrayList<String>();
	private static final long serialVersionUID = -1049522014359818164L;

	@Override
	public void addListener(InvalidationListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(InvalidationListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addAll(IECTCItem... arg0) {
		// TODO Auto-generated method stub
	    System.out.println("ADD"+arg0.length);	
	    System.out.println("ADD"+arg0[0]+"size"+size());
	    
//		return false;
		return super.add(arg0[0]);
	}

	@Override
	public void addListener(ListChangeListener<? super IECTCItem> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeAll(IECTCItem... arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ListChangeListener<? super IECTCItem> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean retainAll(IECTCItem... arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setAll(IECTCItem... arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setAll(Collection<? extends IECTCItem> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
