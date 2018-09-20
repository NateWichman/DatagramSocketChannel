/************************************************************************************
     udp client. Prompts user for port number, IP address, and a message to send. Then 
Sends that message to the specified Server. Then attempts to receive a message back 
from the Server, on which it will print that message out.

Some code provided by proffessor.

@author Nathan Cale Wichman
@Version September 2018
************************************************************************************/

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

class udpclient{
    public static void main(String args[]){
	try{
	    /** Scanner for inputting data **/
	    Scanner scnr = new Scanner(System.in);
		
	    /** Receiving port number from scanner **/
	    System.out.println("Enter a port number: ");
	    int portNumber = scnr.nextInt();
		
            /** Receiving IP address from Server **/
	    System.out.println("Enter an IP address: ");
	    scnr.nextLine(); //Skipping new line charachter
	    String ipAddress = scnr.nextLine();
	    System.out.println("IpReceived: " + ipAddress);
	    
	    /** Datagram Socket setup **/
	    DatagramChannel sc = DatagramChannel.open();
	    Console cons = System.console();
		
	    /** Getting a message to send to the Server from the scanner **/
	    String m = cons.readLine("Enter your message: ");
		
	    /** Sending that message to the Server at the specified ip address and portnumber **/
	    ByteBuffer buf = ByteBuffer.wrap(m.getBytes());
	    sc.send(buf, new InetSocketAddress((ipAddress),portNumber));

	    /** Receiving message **/
	    ByteBuffer buf2 = ByteBuffer.allocate(5000);
	    sc.receive(buf2);
	    buf2.flip();
	    byte[] a = new byte[buf2.remaining()];
	    buf2.get(a);
	    String message = new String(buf2.array());

	    System.out.println("Reveived Message from Server: " + message);
		
	    /** Closing Socket Channel **/
	    sc.close();

	}catch(IOException e){
	    System.out.println("Error happened\n");
	}
    }
}
