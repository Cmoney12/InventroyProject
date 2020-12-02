import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MenuDriver extends JMenuBar {
    public Connect connect = new Connect();
    public JMenuItem text;
    public MenuDriver() {
        JMenu file = new JMenu("File");
        //export = new JMenuItem("Export");
        text = new JMenuItem("Save as text");
        file.add(text);
        //export.add(text);
        add(file);
        text.addActionListener(e -> SaveMe());
        JMenu options = new JMenu("Options");
        JMenuItem invCheck = new JMenuItem("Check Inventory");

        JMenuItem addProduct = new JMenuItem("Add Product");
        addProduct.addActionListener(e -> {
            AddProduct addProduct1 = new AddProduct();
            addProduct1.AddProductFrame();
        });
        options.add(invCheck);
        options.add(addProduct);
        add(options);
        invCheck.addActionListener(e -> {
            String x = connect.displayInventory();
            DisplayTable table = new DisplayTable();
            table.SecondFrame(x);

        });
        JMenuItem email = new JMenuItem("Email");
        options.add(email);
        email.addActionListener(e -> {
            EmailSend emailSend = new EmailSend();
            emailSend.emailFrame();
        });
        add(options, BorderLayout.NORTH);
    }
    public void SaveMe(){
        Connect connection = new Connect();
        final JFileChooser SaveAs = new JFileChooser();
        SaveAs.setApproveButtonText("Save");
        int actionDialog = SaveAs.showOpenDialog(text);
        if (actionDialog != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File fileName = new File(SaveAs.getSelectedFile() + ".txt");
        BufferedWriter outFile = null;
        try {
            //outFile = new BufferedWriter(new FileWriter(fileName));
            String x = connection.displayInventory();

            //Write to File
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(x);
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (outFile != null) {
                try {
                    outFile.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
