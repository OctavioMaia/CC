package presentation;

public class Mainclient {

	public static void main(String[] args) {
		Menu t = new Menu(args[0],Integer.parseInt(args[1]));
		t.setVisible(true);
	}
	
}
