import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShowList {
    public JFrame jFrame = new JFrame("List");
    public StringBuilder string;
    public TextArea area;
    public String x;
    public String y;
    public void listView(ArrayList<String> product, ArrayList<Integer> quantity) {
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setLayout(null);
        string = new StringBuilder();
        string.append("SKU " + "\t" + "Quantity" + "\n");
        for(int i = 0; i < product.size(); i++) {
            x = product.get(i);
            string.append(x);
            string.append("\t");
            y = quantity.get(i).toString();
            string.append(y);
            string.append("\n");
        }
        area = new TextArea(string.toString());
        JButton button  = new JButton("Clear");
        button.setBounds(100,200,70,25);
        jFrame.add(button);
        button.addActionListener(e -> clearWindow());
        area.setBounds(0,0,300, 100);
        jFrame.add(area);
        area.setEditable(false);
        jFrame.setSize(300,300);
        jFrame.setVisible(true);
        product.clear();
        quantity.clear();
    }
    public void clearWindow() {
        Inventory inventory = new Inventory();
        inventory.clearList();
        x = "";
        y = "";
        string.setLength(0);
        area.setText(null);
    }
}
