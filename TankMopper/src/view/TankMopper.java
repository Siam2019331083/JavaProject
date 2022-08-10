package view;
import javax.swing.SwingUtilities;

import model.TankMopperModel;


public class TankMopper implements Runnable {
	
	public void rt()
	{
		SwingUtilities.invokeLater(new TankMopper());
	}
 
	public static void main(String[] args) {
		LFrame f= new LFrame();
		f.makeframe();


	}

	@Override
	public void run() {
		new TankMopperFrame(new TankMopperModel());
	}

}