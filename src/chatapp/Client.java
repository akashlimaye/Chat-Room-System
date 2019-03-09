// Name         : Akash Girish Limaye
// Student ID   : 1001551994

package chatapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends javax.swing.JFrame{
    final static int SERV_PORT = 2048;
    static Socket s;
    //defining output streams
    DataInputStream ifc;
    DataOutputStream otc;
    public Client() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        // action to be performed when the window is close
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        // generate layout for client side chatroom
        jLabel1.setText("Chat Room");
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);
        jButton1.setText("Send");
        // action performed when the send button is pressed
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)))
                .addContainerGap())
            );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

        pack();
    }
    //method to send te client side message over the network
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if (evt.getSource().equals(jButton1)) {																					// condition is true when user clicks on exit button
         try {
				TransferText();																									// method to exit the server operation
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
    public static void main(String args[]) throws UnknownHostException, IOException {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
                
            }
        });
        // send message to server 
        InetAddress SERV_ADDR = InetAddress.getByName("localhost");
        s = new Socket(SERV_ADDR, SERV_PORT);
        String msg="";
        while(true) {
        DataInputStream dis = new DataInputStream(s.getInputStream());
        msg = dis.readUTF();
        jTextArea1.append(msg);
         
     }
 }
 // method to fetch string from textfield and send it over dataoutputstream to the server
 public void TransferText()  throws UnknownHostException, IOException {
  DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		String word = jTextField1.getText().trim();																					   // get the word to search entered by user
		dos.writeUTF(word);	
	        jTextField1.setText("");// read word from the client and write to server
       }

    // Variables declaration - do not modify//GEN-BEGIN:variables
       private javax.swing.JButton jButton1;
       private javax.swing.JLabel jLabel1;
       private javax.swing.JScrollPane jScrollPane1;
       private javax.swing.JSeparator jSeparator1;
       private static javax.swing.JTextArea jTextArea1;
       private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
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

