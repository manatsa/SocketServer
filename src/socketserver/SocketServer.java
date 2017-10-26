/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver;

import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author mana
 */
public class SocketServer extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        TextArea logs=new TextArea();
        logs.setFont(new Font("verdana",12));
        logs.setWrapText(true);
        logs.setPrefColumnCount(40);
        logs.setPrefRowCount(10);
            
        Button btn = new Button();
        btn.setText("Start Server");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
               Runnable run=()-> {
                   
                       startServer(logs);
                   
               };
               Thread t=new Thread(run);
               t.setDaemon(true);
               t.start();
               
               
               
            }
        });
        
        
       
        Label dummy=new Label(".");
        dummy.setMinHeight(40);
        
        Label heading=new Label("Socket Server");
        heading.setFont(new Font("times",20));
        heading.setAlignment(Pos.CENTER);
        heading.setPadding(new Insets(20,10,20,10));
        
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        
        root.getChildren().addAll(heading,logs,dummy,btn); 
        
        Scene scene = new Scene(root, 800, 450);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    ServerSocket server;
    public void startServer(TextArea log)
    {
        try {
            server=new ServerSocket(2010);
            log.appendText("\n\nServer Socket started!");
            while(true)
            {
                log.appendText("\n\nWaiting for new Connection....");
                Socket socket=server.accept();
                log.appendText("\n\nReceived connection from :"+ socket.getInetAddress().getHostAddress()+"\t >>>>>"+ socket.getInetAddress().getHostName());
                SocketThread sock=new SocketThread(socket,log);
                Thread t=new Thread(sock);
                            t.setDaemon(true);
                            t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
