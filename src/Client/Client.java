/*
Author: Mostafa Vahidi
CS 3800.01 Computer Networks
9/22/18
 */
package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) throws IOException
    {
        System.out.println("Type in any message to send off to the server and get a response." +
                "  \n Type \"bye\" if you want to disconnect from the server.");
        Scanner sc = new Scanner(System.in);
        DatagramSocket ds = new DatagramSocket();
        InetAddress ip = InetAddress.getLocalHost();
        byte buf[] = null;
        //Setting the timeout for the socket to 3 seconds
        ds.setSoTimeout(3000);

        while (true)
        {
            String inp = sc.nextLine();
            buf = inp.getBytes();
            //Sending the Datagram Packet to the server with what the user wrote.
            DatagramPacket DpSend =
                    new DatagramPacket(buf, buf.length, ip, 1234);
            ds.send(DpSend);
            if (inp.equals("bye"))
                break;

            buf = new byte[65535];
            try {
                //Receiving the datagram packet from the server only if the response is less than 3 seconds.
                DatagramPacket DpReceive =
                        new DatagramPacket(buf, buf.length, ip, 1234);
                ds.receive(DpReceive);
                System.out.println("Server:-" + data(buf));
            } catch (SocketTimeoutException e) {
                System.out.println("Unfortunately the server did not respond within 3 seconds.");
                continue;
            }
        }
    }

    //Simple stringbuilder class for getting the byte array and turning into stringbuilder.
    public static StringBuilder data(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }

}
