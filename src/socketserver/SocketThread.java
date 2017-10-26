/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextArea;

/**
 *
 * @author mana
 */
public class SocketThread implements Runnable {
    Socket socket;
    DataOutputStream dout;
    DataInputStream din;
    TextArea log;
    public SocketThread(Socket socket,TextArea l) throws IOException {
        this.socket=socket;
        dout=new DataOutputStream(socket.getOutputStream());
        din=new DataInputStream(socket.getInputStream());
        this.log=l;
    }

    private byte[] bigIntToByteArray( final long i ) {
    BigInteger bigInt = BigInteger.valueOf(i);      
    return bigInt.toByteArray();
}
    
    @Override
    public void run() {
        try {
            //long data=din.readLong();
            byte[] b=new byte[din.available()];
            din.read(b, 0, din.available());
            String data=new String(b);
            
            //String data=din.readUTF();
            log.appendText("\n\nRECEIVED :"+data);
            
            dout.write(b, 0, b.length);
        } catch (IOException ex) {
            Logger.getLogger(SocketThread.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if(!socket.isClosed()){
                    din.close();
                    dout.close();
                    socket.close();
                }
            } catch (Exception e) {
                
            }
            
        }
    }
    
}
