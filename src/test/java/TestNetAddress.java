import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestNetAddress {

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		
		InetAddress net = InetAddress.getLocalHost();
		
		net.getHostAddress();
		net.getHostName();
		
		System.out.println("getHostAddress:"+net.getHostAddress());
		System.out.println("getHostName:"+net.getHostName());

	}

}
