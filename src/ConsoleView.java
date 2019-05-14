
public class ConsoleView {

	private char[][] screen;
	private int width;
	private int height;

	private final static int WIDTH = 80;
	private final static int HEIGHT = 24;
	public ConsoleView(Model m){
		this(m, WIDTH, HEIGHT);
	}
	
	private Model model;
	private AnimationChar ani;
	
	public ConsoleView(Model m, int w, int h){
		model = m;
		width = w;
		height = h;
		screen = new char[width][height];
		ani = new AnimationChar(model, this);
		clear();
	}

	public void update(){
		clear();
		
		if(!Model.typeStart && !Model.gameClear && !Model.gameOver)
			instructionMenu();
		else if(Model.gameClear)
			gameClearMenu();
		else if(Model.gameOver)
			gameOverMenu();
		else
			inGameMenu();
		
		paint();
	}
	
	public void instructionMenu(){
		drawString("Time is Life", 5, 4);
		drawFramedString("Instruction Menu", '|', 5, 7, 3);
		drawString("RUN RUN ESCAPE FROM THE YOUKAI!", 5, 10);
		drawString("HOW TO PLAY : Type the letters inside the box accordingly", 5, 11);
		if(Model.clearOnce){
			drawString("   ***CONGRATULATIONS! YOU HAVE UNLOCKED SUPER HARD MODE***", 5, 13);
			drawString("Press 1 to select easy, 2 to select hard, 3 to select super hard", 5, 14);
		}
		else{
			drawString("   !!!CLEAR THE GAME ONCE TO UNLOCK SECRET MODE!!!", 5, 13);
			drawString("Press 1 to select easy, 2 to select hard", 5, 14);
		}
		drawString("|SELECTED MODE : " + model.getMode() + "|", 5, 15);
		drawString("Press g to start~", 5, 18);
		drawString("Press q to quit~ :(", 5, 20);
	}
	
	public void gameClearMenu(){
		drawFramedString("          !~You Win~!          ", '#', 24, 7, 5);
		drawString("Your time record: " + ConsoleController.getRecordTime() + "s", 29, 9);
		drawFramedString(" Press c to Continue ", '#', 29, 13, 3);
	}
	
	public void gameOverMenu(){
		drawFramedString(" ?!~Game Over~!? ", '#', 31, 8, 3);
		drawFramedString(" Press c to Continue ", '#', 29, 12, 3);
	}
	
	public void inGameMenu(){
		drawRect('*', 10, 4, 55, 5);
		drawString(model.getLetters(), 12, 6);
		
		drawFramedString("Timer: " + ConsoleController.getRecordTime(), '#', 67, 2, 3);
		
		for(int i = 0; i < WIDTH; i++){
			put('-', i, 21);
		}
		ani.drawMons();
		ani.drawPlayer();
	}
	
	public void clear() {
		// TODO 自動生成されたメソッド・スタブ
		for(int y=0; y < height; y++){
			for(int x=0; x < width; x++){
				screen[x][y] = ' ';
			}
		}
	}

	public void paint() {
		// TODO 自動生成されたメソッド・スタブ
		for(int y=0; y < height; y++){
			for(int x=0; x < width; x++){
				System.out.print(screen[x][y]);
			}
			System.out.println();
		}
	}

	public void put(char c, int x, int y){
		if(0 <= x && x < width && 0 <= y && y < height)
			screen[x][y] = c;
	}

	public void drawString(String s, int x, int y){
		for(int i=0; i < s.length(); i++)
			put(s.charAt(i), x+i, y);
	}

	public void drawRect(char c, int x, int y, int w, int h){
		for(int xx=x; xx < x + w; xx++)
			for(int yy=y; yy < y + h; yy++)
				if(xx == x || xx == x + w - 1 || yy == y || yy == y + h - 1)
					put(c, xx, yy);
	}
	public void drawFramedString(String s, char c, int x, int y, int space){
		drawString(s, x, y);
		drawRect(c, x - 1, y - 1, s.length() + 2, space);
	}
	/*
	public void drawFramedString(String s, char c, int x, int y){
		drawString(s, x, y);
		drawRect(c, x - 1, y - 1, s.length() + 2, 3);
	}
	*/
	
	/*
	public static void main(String[] args) throws InterruptedException {
		// TODO è‡ªå‹•ç”Ÿæˆ�ã�•ã‚Œã�Ÿãƒ¡ã‚½ãƒƒãƒ‰ãƒ»ã‚¹ã‚¿ãƒ–
		ConsoleView view = new ConsoleView(80, 24);
		for(int x=0; x < 80; x++){
			view.clear();
			//view.drawString("hello world", x, 10);
			//view.drawRect('*', x, 10, 10, 5);
			view.drawFramedString("PLEASE MARRY ME~!", '+', x, 2);
			view.paint();
			Thread.sleep(50);
		}
	}
	*/
}