package jkl.iec.tc.bean.utils;


import java.util.Date;
import java.util.logging.Logger;

import jkl.iec.tc.bean.type.IECMap;
import jkl.iec.tc.bean.type.IECItemList;
import jkl.iec.tc.bean.type.IECTCItem;
import jkl.iec.tc.bean.utils.IECSimProperties.SimtimeProperty;

public class IECSimulatorThread implements Runnable {

	public final static Logger log = Logger.getLogger(IECSimulatorThread.class .getName()); 
	
	IECSimProperties simprop=null;
	IECItemList ieclist=null;
	
    public void run() {
		log.finest("Simulator cycel Started ");
				for (int it=0;it<ieclist.size();it++) {
				   	IECTCItem Item = (IECTCItem)ieclist.get(it);
				   	simprop = (IECSimProperties) Item.data;
//		   		    System.out.println("item.simprop: "+simprop);
		   		    // on type change set back to defaults.
				   	if (simprop != null) {
			   		    if (simprop.old_type !=((IECTCItem) ieclist.get(it)).getIectyp()) {
				    		simprop.setDefProps();
				    		System.out.println("Timer_Str = "+simprop.getTimerString());	
					    	}
				    	}
					    	// if simulation enabled execute SimItem(()
					    if (Item.getFlag1()) {
					    	simItem(Item);
					    	log.finer("TimerStr = "+simprop.getTimerString());	
				    		}
				    }
	}
	    
	private void sim_M_Item(IECTCItem item) {
	    if (simprop.NextSimTime <= new Date().getTime()) {
    		System.out.print("SIMUL: "+ item.getName()+"  INC:"+ simprop.getValinc()+" " );
    		if (!item.getIOB(0).setVal(item.getIOB(0).getVal()+simprop.getValinc())) {
	    		System.out.print("New Item val > MAX_VAL: ");	
	    		simprop.setValinc(-1 * simprop.getValinc());
//		    		System.out.print("New INC: "+simprop.getValinc() + " ");	
    			item.getIOB(0).setVal(item.getIOB(0).getVal()+simprop.getValinc());
    		}
    		System.out.print("New Item val: "+item.getIOB(0).getVal() + " ");	
    		calcNextSimTime();
    		}
    	}

    private void simItem(IECTCItem item) {
    	if (IECMap.IEC_M_Type.contains(item.getIectyp())) {
      			sim_M_Item(item);
      		}
   	    	if (IECMap.IEC_C_Type.contains(item.getIectyp())) {
      			sim_C_Item(item);
      		}
      }
   	    
    private void sim_C_Item(IECTCItem item) {
//   		    	System.out.println("time"+item.iob(0).getTime()+"  time_RX"+item.iob(0).Time_RX);	
   	   	if (item.getIOB(0).getTime() != item.getIOB(0).Time_RX) {   //
   	    	String tr = "Simul  reaction trigger "+item.getName()+" Search item Type:"+simprop.itemproperties.backType+
   	    			"  asdu:"+simprop.itemproperties.backASDU+" IOB:"+simprop.itemproperties.backIOB;
	    	IECTCItem simitem =ieclist.getIECItem(simprop.itemproperties.backType,simprop.itemproperties.backASDU,simprop.itemproperties.backIOB);
   	    	if (simitem != null) {
   		    	tr = tr+" --> OK";
   	    		if (simprop.getValinc()==0) {
   	    			simitem.getIOB(0).setVal(item.getIOB(0).getVal());	
   	    		} 
   	    		if ((simprop.getValinc()!=0)& (!simitem.getIOB(0).setVal(simitem.getIOB(0).getVal()+simprop.getValinc()))) {
   	    			simprop.setValinc(-1 * simprop.getValinc());
   			    	simitem.getIOB(0).setVal(simitem.getIOB(0).getVal()+simprop.getValinc());
   			    	}
   			    }
   			    System.out.println(tr);
   			    item.getIOB(0).Time_RX = item.getIOB(0).getTime();
   	    	}
   	    }

    @SuppressWarnings("deprecation")
	private void calcNextSimTime() {
    	Date now =new Date();
    	System.out.println("calcNextSimTime:"+simprop.getTimerString()+" inc:"+simprop.timeproperties.TimeInc);
    	if (simprop.timeproperties.timetyp == SimtimeProperty.fixTime) {
       		System.out.println("Once a min at sec: "+ simprop.timeproperties.TimeInc *-1);
   			now.setMinutes(now.getMinutes()+1);
   			now.setSeconds(simprop.timeproperties.TimeInc*-1);
       		simprop.NextSimTime = now.getTime();
   			return;
   		}
   		if (simprop.timeproperties.timetyp == SimtimeProperty.cycleTime) {
       		System.out.println("New Time sec Inc: "+ simprop.timeproperties.TimeInc*1000);
   			simprop.NextSimTime = now.getTime() + simprop.timeproperties.TimeInc*1000;
   			return;
    		}
   		int r= (int)((Math.random()*(simprop.timeproperties.TimeInc2-simprop.timeproperties.TimeInc))+simprop.timeproperties.TimeInc)*1000;
   		System.out.println("New Timer Random Inc: "+ r);
   		simprop.NextSimTime = now.getTime() + r;
	    }

	public IECSimulatorThread(IECItemList l) {
		this.ieclist = l; 
	 }
	
}
