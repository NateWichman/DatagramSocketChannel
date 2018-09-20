import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

class udpclient{
    public static void main(String args[]){
	try{
	    Scanner scnr = new Scanner(System.in);
	    System.out.println("Enter a port number: ");
	    int portNumber = scnr.nextInt();
	    System.out.println("Enter an IP address: ");
	    scnr.nextLine();
	    String ipAddress = scnr.nextLine();
	    System.out.println("IpReceived: " + ipAddress);
	    
	    //Datagram socket. Even though it doesnt have the word socket in it
	    //Remember a DatagramChannel is a Datagram socket
	    DatagramChannel sc = DatagramChannel.open();
	    Console cons = System.console();
	    String m = cons.readLine("Enter your message: ");
	    ByteBuffer buf = ByteBuffer.wrap(m.getBytes());
	    sc.send(buf, new InetSocketAddress((ipAddress),portNumber));

	    ByteBuffer buf2 = ByteBuffer.allocate(5000);
	    sc.receive(buf2);
	    buf2.flip();
	    byte[] a = new byte[buf2.remaining()];
	    buf2.get(a);
	    String message = new String(buf2.array());
	    //  String message = new String(buf2.array());
	    System.out.println("Reveived Message from Server: " + message);
	    sc.close();

	}catch(IOException e){
	    System.out.println("Error happened\n");
	}
    }
}
