import javazoom.jl.player.Player;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sound.sampled.*;
import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class EmailSend {
    private final int BUFFER_SIZE = 128000;
    private File soundFile;
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceLine;
    public Connect connect = new Connect();
    public JFrame frame = new JFrame("Email");
    public JPanel panel;
    public void emailFrame() {
        frame.setLayout(null);
        //panel.setLayout();
        JLabel label = new JLabel("Recipient ");
        label.setBounds(50,90,90,50);
        frame.add(label);

        JTextField reciepentAddress = new JTextField(20);
        reciepentAddress.setBounds(130, 100, 140,25);
        frame.add(reciepentAddress);

        JButton button = new JButton("Send");
        button.setBounds(140,160,110,30);
        frame.add(button);

        button.addActionListener(e-> {
            String x = connect.displayInventory();
            sendMail(reciepentAddress.getText() , x);
        });

        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);
    }
    public void sendMail(String recipient, String table) {
        // Recipient's email ID needs to be mentioned.
        String to = recipient;

        // Sender's email ID needs to be mentioned
        String from = "inventorymanagementproject1@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("inventorymanagementproject1@gmail.com", "Testing1");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Inventory Table");

            // Now set the actual message
            message.setText(table);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");

            frame.dispose();
            playSound("1312.mp3");

            JOptionPane.showMessageDialog(null, "Message sent successfully");

            
        } catch (MessagingException mex) {
            JOptionPane.showMessageDialog(null, mex);
            //mex.printStackTrace();
        }
    }
    public void playSound(String filename){
        try {
            BufferedInputStream buffer = new BufferedInputStream(
                    new FileInputStream(filename));
            Player player = new Player(buffer);
            player.play();
        }
        catch (Exception e) {

            System.out.println(e);
        }
    }

}
