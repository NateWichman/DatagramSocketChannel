import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

class udpserver{
    public static void main(String args[]){
	try{
	    Scanner scnr = new Scanner(System.in);
	    System.out.println("Enter a port number");
	    int portNumber = scnr.nextInt();
	    
	    DatagramChannel c = DatagramChannel.open();
	    Selector s = Selector.open();
	    c.configureBlocking(false);
	    c.register(s,SelectionKey.OP_READ);
	    c.bind(new InetSocketAddress(portNumber));
	    while(true){
		/*select checks the channels to see
		//if we can do the operations.
		//it returns the numkber or channels
		//that we can do that operation on */
		int n = s.select(5000);
		if(n == 0){
		    System.out.println("got a timeout");
		}else{
		    Iterator i = s.selectedKeys().iterator();
		    while(i.hasNext()){
			SelectionKey k = (SelectionKey)i.next();
			ByteBuffer buf = ByteBuffer.allocate(4096);
			SocketAddress clientaddr = c.receive(buf);
			String message = new String(buf.array());
			System.out.println("Received Message: " + message);
			i.remove();
			
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
