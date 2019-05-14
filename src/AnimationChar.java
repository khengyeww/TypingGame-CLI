
public class AnimationChar {

	private Model model;
	private ConsoleView copy;
	private final float DISTANCE = 7;
	private float mons_move = 0;
	private float player_move = 0;
	private int jumpMons = 0;
	
	public AnimationChar(Model m, ConsoleView view){
		model = m;
		copy = view;
	}
	
	public void Reset() {
		mons_move = 0;
		player_move = 0;
	}
	
	public int get_MonsX(){
		return (int)mons_move;
	}
	
	public int get_PlayerX(){
		return (int)player_move;
	}
	
	public boolean Mons_touched_Player(){
		float Mons_touchedPlay = DISTANCE + player_move - mons_move;
		
		if(Mons_touchedPlay <= 0)
			return true;
		else
			return false;
	}
	
	public boolean player_outOfScreen(){
		if(model.update_PlayerX() > 66)
			return true;
		else
			return false;
	}
	
	public void drawMons(){
		if(ConsoleController.gameTimer % 2 != 0)
			jumpMons = -1;
		else
			jumpMons = 0;
		
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 7; j++){
				if(i == 0 || j == 0 || i == 9 || j == 6)
					copy.put('*', 0 + i + model.update_MonsX(), 15 + j + jumpMons);
				if(j == 1){
					if(i > 3 && i < 6)
						copy.put('-', 0 + i + model.update_MonsX(), 15 + j + jumpMons);
					if(i == 3)
						copy.put('\\', 0 + i + model.update_MonsX(), 15 + j + jumpMons);
					if(i == 6)
						copy.put('/', 0 + i + model.update_MonsX(), 15 + j + jumpMons);
				}
				if((i == 2 || i == 3 || i == 6 || i == 7) && j == 2)
					copy.put('#', 0 + i + model.update_MonsX(), 15 + j + jumpMons);
				if((i >= 2 && i <= 7) && j == 4)
					copy.put('-', 0 + i + model.update_MonsX(), 15 + j + jumpMons);
				if(j == 5){
					if(i == 3 || i == 6)
						copy.put('|', 0 + i + model.update_MonsX(), 15 + j + jumpMons);
					if(i == 2)
						copy.put('\\', 0 + i + model.update_MonsX(), 15 + j + jumpMons);
					if(i == 7)
						copy.put('/', 0 + i + model.update_MonsX(), 15 + j + jumpMons);
				}
			}
		}
	}
	
	public void monsUpdate(){
		if(model.getMode().equals("EASY")){
			if(ConsoleController.gameTimer < 8) {
				mons_move += 0.1;
			}
			else if(ConsoleController.gameTimer < 20) {
				mons_move += 0.25;
			}
			else {
				mons_move += 0.35;
			}
		}
		else if(model.getMode().equals("HARD")){
			if(ConsoleController.gameTimer < 5) {
				mons_move += 0.2;
			}
			else if(ConsoleController.gameTimer < 15) {
				mons_move += 0.25;
			}
			else {
				mons_move += 0.3;
			}
		}
		else{
			if(ConsoleController.gameTimer < 5) {
				mons_move += 0.2;
			}
			else if(ConsoleController.gameTimer < 15) {
				mons_move += 0.35;
			}
			else {
				mons_move += 0.4;
			}
		}
	}
	
	public void drawPlayer(){
		// Head
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(i == 1 && j ==1)
					copy.put(' ', 15 + i + model.update_PlayerX(), 15 + j);
				else
					copy.put('#', 15 + i + model.update_PlayerX(), 15 + j);
			}
		}
		// Body
		copy.put('|', 16 + model.update_PlayerX(), 18);
		copy.put('|', 16 + model.update_PlayerX(), 19);
		
		// Left Leg
		copy.put('/', 15 + model.update_PlayerX(), 20);
		copy.put('/', 14 + model.update_PlayerX(), 21);
		
		// Right Leg
		copy.put('\\', 17 + model.update_PlayerX(), 20);
		copy.put('\\', 18 + model.update_PlayerX(), 21);
		
		if(ConsoleController.gameTimer % 2 != 0){
			// Left Arm
			copy.put('/', 15 + model.update_PlayerX(), 18);
			copy.put('\\', 15 + model.update_PlayerX(), 19);

			// Right Arm
			copy.put('\\', 17 + model.update_PlayerX(), 18);
			copy.put('/', 18 + model.update_PlayerX(), 18);
		}
		else{
			// Both Arm
			copy.put('\\', 17 + model.update_PlayerX(), 19);
		}
	}
	
	public void playerUpdate(){
		if(model.getContCorrect() == 0)
			player_move += 0;
		else if(model.getContCorrect() < 3)
			player_move += 0.1;
		else if(model.getContCorrect() < 15)
			player_move += 0.25;
		else if(model.getContCorrect() < 30)
			player_move += 0.375;
		else if(model.getContCorrect() < 40)
			player_move += 0.45;
		else
			player_move += 0.8;
	}
}
