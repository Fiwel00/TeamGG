package teamgg.ui.components.interactive.loadprofile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadProfileAL implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		
		LoadProfileSW loadProfileSW = new LoadProfileSW();
		loadProfileSW.execute();
		//only if there is an issue this code block should be uncommented
//		try {
//			loadProfileSW.get();
//		} catch (InterruptedException | ExecutionException e1) {
//			e1.printStackTrace();
//		}
		
		
	}
	
	
	
	
}
