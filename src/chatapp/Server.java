// Name         : Akash Girish Limaye
// Student ID   : 1001551994

package chatapp;

import java.io.BufferedWriter;
import java.util.Date;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

//ClientSide Program for Multithreaded chat application [5]
public class Server extends javax.swing.JFrame {

    //array to store client information
    static ArrayList<ClientHandler> USER_LIST = new ArrayList<ClientHandler>();
    static int i = 0;
    //Creates new form Server
    public Server() {
    	initComponents();
    }
    @SuppressWarnings("unchecked")
    
    //initializing components on frame
    private void initComponents() {

    	jScrollPane1 = new javax.swing.JScrollPane();
    	jTextArea1 = new javax.swing.JTextArea();
    	jLabel1 = new javax.swing.JLabel();
    	jSeparator1 = new javax.swing.JSeparator();

    	setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    	jTextArea1.setEditable(false);
    	jTextArea1.setColumns(20);
    	jTextArea1.setRows(5);
    	jScrollPane1.setViewportView(jTextArea1);

    	jLabel1.setText("Server Console");

    	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    	getContentPane().setLayout(layout);
    	layout.setHorizontalGroup(
    		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    		.addGroup(layout.createSequentialGroup()
    			.addContainerGap()
    			.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    				.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
    				.addGroup(layout.createSequentialGroup()
    					.addComponent(jLabel1)
    					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
    					.addComponent(jSeparator1)))
    			.addContainerGap())
    		);
    	layout.setVerticalGroup(
    		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
    		.addGroup(layout.createSequentialGroup()
    			.addContainerGap()
    			.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
    				.addComponent(jLabel1)
    				.addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
    			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
    			.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
    			.addContainerGap())
    		);

    	pack();
    }
    
    
    
    public static void main(String args[]) {

    	try {
    		try {
            	for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            		if ("Nimbus".equals(info.getName())) {
            			javax.swing.UIManager.setLookAndFeel(info.getClassName());
            			break;
            		}
            	}
            } catch (ClassNotFoundException ex) {
            	java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
            	java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
            	java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            	java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            // Display the generated form 
            java.awt.EventQueue.invokeLater(new Runnable() {
            	public void run() {
            		new Server().setVisible(true);
            	}
            });
            //Code for reading db file and loading on the server screen [1]
            File file = new File("./data.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
            {
                System.out.println(sc.nextLine());
            }
            //opening server socket on port 2048 to accept connections
            ServerSocket SERV_SOCK = new ServerSocket(2048);
            //object of class socket to accept incoming connections
            Socket s;
            
            /*client request*/
            
            // running infinite loop for getting
            while (true)
            {
                // Accept the incoming request
            	s = SERV_SOCK.accept();
            	System.out.println("Connection established with "+s);

                // obtain input and output streams
            	DataInputStream dis = new DataInputStream(s.getInputStream());
            	DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                //Create a new handler object for handling this request.
            	jTextArea1.append("Initializing Handler Instance for Client "+i+"\n");
               	ClientHandler CL_Handler = new ClientHandler(s,"\n Client"+i, dis,dos,i);
                // Create a new Thread with the new handler object.
            	Thread handlerthread = new Thread(CL_Handler);
                // add this client to list of online clients
            	System.out.println("Adding Client "+i+" to Live User-List...");
            	USER_LIST.add(CL_Handler);
                // start the thread.
            	handlerthread.start();
                // for every new client i increments
            	i++;

            }
            
        } catch (IOException ex) {
        	Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    public static javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}

class ClientHandler implements Runnable 
{
	private String host;
	final DataInputStream dis;
	final DataOutputStream dos;
	Socket s;
	int i;
	boolean isloggedin;

	// constructor
	public ClientHandler(Socket s, String host, DataInputStream dis, DataOutputStream dos,int i) {
		this.dis = dis;
		this.dos = dos;
		this.host = host;
		this.s = s;
		this.i=i;
		this.isloggedin=true;
	}
        // get server time in http format [7]    
        String getServerTime() 
        {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
            "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return dateFormat.format(calendar.getTime());
        }
        
	@Override
	public void run() {

		String incomingMsg;
                //initializing date object for calculating message intervals
                //
                String SERV_DATETIME = getServerTime();
                Date date=new Date();
                //initializing file created boolean to false
		boolean isFileCreated = false;
		try
		{
			final String file="./data.txt";
			DateTime starttime=new DateTime();


			while (true) 
			{
				// receive the string
				incomingMsg = dis.readUTF();
				// http format for get and post [4]
                                String USR_AGENT="HTTPTool/1.1 \\n";
                                String SERV_HOST="127.0.0.1:2048";
                                String InitReqLine="POST ./chatapp/Server.html HTTP/1.1";
								String IncomingHost="Client "+i ;
                                String InitRespCode="HTTP/1.1 200 OK \r\n";
								String Cont_Type="text/html\r\n";
                // server Console for printing incoming message
				System.out.println(incomingMsg);
				Server.jTextArea1.append("\nMethod:\t"+InitReqLine+"\nFrom :\t"+IncomingHost+"\nDate/Time:\t"+SERV_DATETIME+"\nUser-Agent:\t"+USR_AGENT+"\nMessage:\t"+ incomingMsg+"\n"); 
				// handling logout on server and client
                                if(incomingMsg.toUpperCase().equals("LOGOUT")){
					Server.jTextArea1.append("Client " +i+ " , disconnected!");
					for (ClientHandler iterator : Server.USER_LIST)
					{
						dos.writeUTF("\n\nClient " +i+ " has closed the session");
					}
					this.isloggedin=false;
					//this.s.close();
					break;
				}
                                // break the incoming string into message and recipient part
				StringTokenizer st = new StringTokenizer(incomingMsg, "#");
				String OutboundMessage = st.nextToken();
				int length=OutboundMessage.length();
                                //calculate time-difference between two messages [6]
				DateTime currenttime=new DateTime();
				Seconds interval=Seconds.secondsBetween(currenttime,starttime);
				//send chat message to every client's chat console
                                for (ClientHandler iterator : Server.USER_LIST) 
				{
					iterator.dos.writeUTF("\n"+InitRespCode+"Date:\t"+date+"\n"+"Content_Type\t"+Cont_Type+"Content-Length\t"+length+"\nFrom:\tClient "+i+"\nMessage:\t"+OutboundMessage+"\nTime\t"+ interval+"\n");
					starttime=currenttime;
				}
				//code to append the chat history of server to database file [1]
                                String context=("Client "+i+":\n"+OutboundMessage+"\n\n");
				Files.write(Paths.get("./data.txt"), context.getBytes(), StandardOpenOption.APPEND);
                        }

                }catch (IOException e) 
                {

				e.printStackTrace();
		}
		try
		{
		// close i/o streams
        		this.dis.close();
			this.dos.close();
                }catch(IOException e)
                {
				e.printStackTrace();
		}
	}
}

/*
References :

1. “Different Ways of Reading a Text File in Java.” GeeksforGeeks, 27 May 2017, 
www.geeksforgeeks.org/different-ways-reading-text-file-java/.

2.Mahrsee, Rishabh. “Multi-Threaded Chat Application in Java | Set 2 (Client Side Programming).” GeeksforGeeks, 17 June 2017, 
www.geeksforgeeks.org/multi-threaded-chat-application-set-2/.

3. Kip. “How to Append Text to an Existing File in Java.” Stack Overflow, 3 June 2017, 20:20, 
stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java.

4. Marshall, James. “HTTP Made Really Easy.” HTTP Made Really Easy, 10 Dec. 2012, 
www.jmarshall.com/easy/http/#http1.1s3.

5. Mahrsee, Rishabh. “Multi-Threaded Chat Application in Java | Set 1 (Server Side Programming).” GeeksforGeeks, 17 June 2017, 
www.geeksforgeeks.org/multi-threaded-chat-application-set-1/.

6. Mkyong. “How to Calculate Date and Time Difference in Java.” How to Calculate Date and Time Difference in Java, 25 Jan. 2013, 
www.mkyong.com/java/how-to-calculate-date-time-difference-in-java/.

7. R, Hannes. “Getting Date in HTTP Format in Java.” Stack Overflow, 27 Dec. 2011, 08:13, 
stackoverflow.com/questions/7707555/getting-date-in-http-format-in-java.
*/