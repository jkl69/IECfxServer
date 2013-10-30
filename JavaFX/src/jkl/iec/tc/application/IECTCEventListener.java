package jkl.iec.tc.application;

import java.awt.event.ActionEvent;

import javafx.application.Platform;

import jkl.iec.net.sockets.IECSocket;
import jkl.iec.net.sockets.IIECnetActionListener;
import jkl.iec.net.sockets.IECServer.IECServerAction;
import jkl.iec.tc.bean.type.IECMap.IECTyp;
import jkl.iec.tc.bean.type.IECTCItem;
import jkl.iec.tc.bean.type.IIECTCStreamListener;

public class IECTCEventListener implements IIECTCStreamListener,IIECnetActionListener {

	private boolean awnserUnsupported = true;

	public void doStream (IECTCItem i) {
		 byte[] buf = i.getStream();
		 doStream(buf, buf.length);
	}
	
	@Override
	public void doStream(byte[] b, int c) {
		System.out.println("IECTCStreamListener:onStreamRX");
		if (Server.iecserver != null) {
			Server.iecserver.send(b, c);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(IECServerAction.IECServerClientConnect.toString())) {
//			Server.Clientpanel.addClient(iecSock);
			}		
	}

	class TTk implements Runnable {
		IECTCItem litem;
		IECTCItem item;
		TTk (IECTCItem li ,IECTCItem i) {
			this.litem = li;
			this.item = i;
		}
		@Override
		public void run() {
			litem.getObject0().setVal(item.getObject0().getVal());		
			litem.setCOT(6);		
//			litem.setCOT(item.getCOT());		
		}
		
	};
	
	@Override
	public void onReceive(IECSocket sender, byte[] b, int len) {
		System.out.println("IECTCStreamListener:onReceive");
		IECTCItem i = new IECTCItem(b, len);
		i.setName("DUMMY");
		System.out.println("IECTCStreamListener:i.getIectyp()"+i.getIectyp());
		if (i.getIectyp()==IECTyp.IEC_NULL_TYPE) {
   	 	  System.out.println("received unsupported Type!: ");
		  // setCOT(0x45);
		  if (awnserUnsupported ) {
			  b[2] = 0x45;
			  doStream(b, len);
		  }
   		return;
   	 }
		if (i.getCOT()==6) {
			IECTCItem listitem = Server.iecPane.itemlist.getIECItem(i.getIectyp(),i.getASDU(),i.getObject0().getaddr());
	 		if (listitem != null) {
				System.out.println("ITEM IN LIST --> actcon");

				Platform.runLater(new TTk(listitem,i) );
	            
//				i.setCOT(0x07);
//				doStream(i);				
			} else {  //Item not in List
				System.out.println("ITEM NOT IN LIST --> nactcon");
				i.setCOT(0x47);
				doStream(i);
		    }
   		return;
   	 	}
		 System.out.println("COT NOT 6 nothing to do! "+i.getCOT());
	}

}
