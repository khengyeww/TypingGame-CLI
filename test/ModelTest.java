import static org.junit.Assert.*;

import org.junit.Test;

public class ModelTest {
	
	// ????? ランダム文字列に対する確認テスト ?????
	// public String loaf = model.getLetters(); // そもそもランダム文字列が生成されていないから、null? 使えない
	private Model model;
	public String createdString;
	// private String loaf = "abc";
	// 初期化
	public ModelTest(){
		model = new Model();
		createdString = model.generateRandomChars();
	}
	
	@Test
	public void checkIfSameLetter() {
		
		assertTrue(createdString, true);
		System.out.println(createdString);
		
		char[] check = createdString.toCharArray();
		
		for(int i = 0; i < createdString.length(); i++){
			System.out.println(check[i]);
			assertEquals(check[i], createdString.charAt(i));
		}
	}

}
