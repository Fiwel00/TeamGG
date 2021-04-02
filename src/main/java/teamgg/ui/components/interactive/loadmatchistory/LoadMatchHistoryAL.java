package teamgg.ui.components.interactive.loadmatchistory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

public class LoadMatchHistoryAL implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		LoadMatchHistorySW loadMatchHitorySW = new LoadMatchHistorySW();
		loadMatchHitorySW.execute();
		//only if there is an issue this code block should be uncommented
//			try {
//				loadMatchHitorySW.get();
//			} catch (InterruptedException | ExecutionException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
	}

}
