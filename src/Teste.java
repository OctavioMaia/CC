import java.net.Inet4Address;
import java.net.UnknownHostException;

public class Teste {
	
	public static void main(String[] argv){
		byte b = (byte) Integer.parseInt("11111",2);
		System.out.print(Byte.toString(b)+"\n");
		
		
		try {
			System.out.println(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
