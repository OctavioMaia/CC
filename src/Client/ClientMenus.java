package Client;

import java.util.Scanner;

public class ClientMenus {
	
	private static Scanner input = new Scanner(System.in);
	
	protected static String menuInicio() {
		StringBuilder ap = new StringBuilder();
		ap.append("=========MENU=========\n");
		ap.append("0-Exit\n");
		ap.append("1-Register\n");
		ap.append("2-Login\n");
		ap.append("3-Logout\n");
		ap.append("4-Consult Music File");
		return ap.toString();
	}
	
	protected static int lerint() {
		Integer ret = 0;
		String inp = input.nextLine();
		try {
			ret = Integer.parseInt(inp);
		} catch (Exception e) {
			ret = lerint();
		}
		return ret;
	}

}
