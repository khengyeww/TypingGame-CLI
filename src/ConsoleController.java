import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.Timer;

public class ConsoleController implements ActionListener {

	private final static int DELAY = 125;
	private Model model;
	private Timer timer;
	private Timer ingameTimer;
	public static int gameTimer = 0;
	public static int recordPlayerTimer = 0;
	
	public ConsoleController(Model m){
		model = m;
		timer = new Timer(DELAY, this);
		ingameTimer = new Timer(1000, action);
	}
	
	ActionListener action = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent acvt) {
			if(Model.typeStart){
				gameTimer++;
				recordPlayerTimer = gameTimer;
			}
			else {
				gameTimer = 0;
				ingameTimer.restart();
			}
		}
	};
	
	public void run() throws IOException{
		timer.start();
		ingameTimer.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		while((line = reader.readLine()) != null){
			if(line.length() != 0)
				model.process(line);
		}
	}
	
	public void actionPerformed(ActionEvent e){
		model.process("TIME_ELAPSED");
	}
	
	public static int getRecordTime(){
		return recordPlayerTimer;
	}
}
