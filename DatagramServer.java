/***********************************************************************
     Udp Server. Asks for a port number to intiate receiving of data.
Listens on that port number for a Client to arrive. Receives a message
from that Client then sends the same message back. 

Some code provided by proffessor.

@author Nathan Cale Wichman
@Version September 2018
***********************************************************************/
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

class udpserver{
    public static void main(String args[]){
	try{
	    /** Setting up the scanner and receiving the port number from the user **/
	    Scanner scnr = new Scanner(System.in);
	    System.out.println("Enter a port number");
	    int portNumber = scnr.nextInt();
	    
	    /** Setting up the Datagram Socket **/
	    DatagramChannel c = DatagramChannel.open();
	    Selector s = Selector.open();
	    c.configureBlocking(false);
	    c.register(s,SelectionKey.OP_READ);
	    c.bind(new InetSocketAddress(portNumber));
		
	    /** Searching for a client **/
	    while(true){
		/** Checking for a time out at a specific interval **/
		int n = s.select(5000);
		if(n == 0){
		    System.out.println("got a timeout");
		}else{
		    Iterator i = s.selectedKeys().iterator();
		    while(i.hasNext()){
			/** Recieving and printing message **/
			SelectionKey k = (SelectionKey)i.next();
			ByteBuffer buf = ByteBuffer.allocate(4096);
			SocketAddress clientaddr = c.receive(buf);
			String message = new String(buf.array());
			System.out.println("Received Message: " + message);
			i.remove();
			
			/** Sending Message back to Client **/
			ByteBuffer buf2 = ByteBuffer.wrap(message.getBytes());
			c.send(buf2, clientaddr);
		    }
		}
	    }
	}catch(IOException e){
	    System.out.println("Error");
	}
    }
}
