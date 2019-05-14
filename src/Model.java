import java.io.IOException;
import java.util.Random;

public class Model {
	private ConsoleView view;
	private ConsoleController controller;
	private AnimationChar ani;
	private final static Random random = new Random();
	
	private String dispLetters; // 表示する文字列
	private char[] charArray;
	private int numCount = 0; // 文字列の中の何番目の文字かを指す
	private int continuousCorrect = 0;
	public static boolean typeStart = false;
	public static boolean gameClear = false;
	public static boolean gameOver = false;
	public static boolean clearOnce = false;
	public boolean hardMode = false;
	public boolean superHardMode = false;
	
	// ランダムな文字列の生成
	public String generateRandomChars() {
		String choosefrChars = "";
		String ez = "abcdefghijklmnopqrstuvwxyz1234567890";
		String hard = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		int length = 0;
		
		if(!hardMode && !superHardMode){
			choosefrChars = ez;
			length = 51;
		}
		else if(hardMode && !superHardMode){
			choosefrChars = hard;
			length = 46;
		}
		else{
			choosefrChars = hard;
			length = 51;
		}
        
       StringBuilder word = new StringBuilder();
       for (int i = 0; i < length; i++) {
           word.append(choosefrChars.charAt(random.nextInt(choosefrChars.length())));
           }
       
       return word.toString();
    }
	
	public Model(){
		view = new ConsoleView(this, 80, 24);
		controller = new ConsoleController(this);
		ani = new AnimationChar(this, view);
	}
	
	public synchronized void process(String event){
		if(!typeStart && !gameClear && !gameOver) {
			if(event.equals("TIME_ELAPSED")){
				;
			}
			else if(event.equals("1")) {
		 		hardMode = false;
		 		superHardMode = false;
			}
			else if(event.equals("2")){
		 		hardMode = true;
		 		superHardMode = false;
			}
			else if(event.equals("3") && clearOnce)
		 		superHardMode = true;
			else if(event.equals("g")){
				beforeStart_Reset();
				ani.Reset();
				dispLetters = generateRandomChars();
				charArray = dispLetters.toCharArray();
	 			typeStart = true;
			}
			else if(event.equals("q"))
		 		System.exit(0);
		}
		else if(gameClear || gameOver){
			if(event.equals("TIME_ELAPSED")){
				;
			}
			else if(event.equals("c")){
		 		gameClear = false;
		 		gameOver = false;
			}
		}
	 	else if(typeStart){
			if(event.equals("TIME_ELAPSED")){
				// アニメーション
				ani.monsUpdate();
				ani.playerUpdate();
				
				if(ani.Mons_touched_Player()){
	 				loseCond();
	 			}
	 			
	 			if(ani.player_outOfScreen()){
	 				winCond();
	 			}
			}
			else{
				// ユーザの入力処理
				if(numCount < dispLetters.length()){
					char userInput = event.charAt(0);

					if(userInput == charArray[numCount]){
						charArray[numCount] = ' ';
						dispLetters = String.valueOf(charArray);

						numCount++;
						continuousCorrect++;
						
						if(numCount == dispLetters.length())
							winCond();
					}
					else {
						continuousCorrect = 0;
					}
				}
			}
		}
		
		view.update();
	}
	
	public void run() throws InterruptedException, IOException{
		controller.run();
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO 自動生成されたメソッド・スタブ
		Model m = new Model();
		m.run();
	}
	
	public String getLetters(){
		return dispLetters;
	}
	
	public String getMode(){
		if(!hardMode && !superHardMode)
			return "EASY";
		else if(hardMode && !superHardMode)
			return "HARD";
		else
			return "SUPER HARD";
	}

	public void beforeStart_Reset(){
		ConsoleController.recordPlayerTimer = 0;
		numCount = 0;
		continuousCorrect = 0;
	}
	
	public void winCond(){
		typeStart = false;
		clearOnce = true;
		gameClear = true;
	}
	
	public void loseCond(){
		typeStart = false;
		gameOver = true;
	}
	
	public int getContCorrect(){
		return continuousCorrect;
	}
	
	public int update_MonsX(){
		return ani.get_MonsX();
	}
	
	public int update_PlayerX(){
		return ani.get_PlayerX();
	}
}