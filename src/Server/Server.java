/*
Author: Mostafa Vahidi
CS 3800.01 Computer Networks
9/22/18
 */
package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;

public class Server {
    public static void main(String[] args) throws IOException
    {
        DatagramSocket ds = new DatagramSocket(1234);
        byte[] receive = new byte[65535];
        DatagramPacket DpReceive = null;
        byte buf[] = null;
        //Setting the formatting for the timstamp from the server.
        final SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy.HH.mm.ss");

        while (true)
        {
            //Receiving the datagram packet from the client.
            DpReceive = new DatagramPacket(receive, receive.length);
            ds.receive(DpReceive);
            System.out.println("Client:-" + data(receive));

            //Adding the timestamp and sending off the datagram packet back to the client.
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String s = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(timestamp);
            String toReturn = data(receive) + " [" + s + "]";
            if (data(receive).toString().equals("bye"))
            {
                //Exiting if the user types in bye.
                System.out.println("Client sent bye.....EXITING");
                break;
            }


            buf = toReturn.getBytes();
            InetAddress ip = DpReceive.getAddress();
            int port = DpReceive.getPort();
            DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, port);
            ds.send(DpSend);

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
