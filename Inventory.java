import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Inventory extends JPanel {
        public JTextField textField;
        public JTextField quantField;
        protected ArrayList<String> items = new ArrayList<>();
        protected ArrayList<Integer> amounts = new ArrayList<>();
        public JButton button;
        public Connect connect = new Connect();

        Inventory() {
            GridBagLayout layout = new GridBagLayout();
            setLayout(layout);
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.insets = new Insets(5, 5, 5, 5);

            constraints.gridx = 0;
            constraints.gridy = 0;
            JButton viewList = new JButton();
            try {
                viewList.setIcon(new ImageIcon(ImageIO.read(new File("listbutton3.png"))));
            } catch (IOException e) {
                e.printStackTrace();
            }

            add(viewList, constraints);
            viewList.addActionListener(e -> {
                if(items.isEmpty() || amounts.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "List is empty");
                }
                else {
                    ShowList showList = new ShowList();
                    showList.listView(items, amounts);
                }
            });

            constraints.gridx = 1;
            constraints.gridy = 0;
            JLabel title = new JLabel("Inventory System");
            //title.setForeground(Color.BLUE);
            title.setFont(new Font("Arial", Font.BOLD, 24));
            add(title, constraints);

            constraints.gridx = 0;
            constraints.gridy = 1;
            JLabel label = new JLabel("Item number");
            label.setFont(new Font("Arial", Font.BOLD, 16));
            add(label, constraints);

            constraints.gridx = 1;
            constraints.gridy = 1;
            textField = new JTextField(20);
            add(textField, constraints);

            constraints.gridx = 0;
            constraints.gridy = 3;
            JLabel label1 = new JLabel("Quantity");
            label1.setFont(new Font("Arial", Font.BOLD, 16));
            add(label1, constraints);

            constraints.gridx = 1;
            constraints.gridy = 3;
            quantField = new JTextField(20);
            add(quantField, constraints);

            constraints.gridx = 1;
            constraints.gridy = 4;
            button = new JButton("Add to List");
            add(button, constraints);

            button.addActionListener(e -> {
                int inum = Integer.parseInt(quantField.getText());
                String text = textField.getText();
                addToCart(text, inum);
            });

            constraints.gridx = 1;
            constraints.gridy = 5;
            JButton confirmButton = new JButton("Confirm Order");
            add(confirmButton, constraints);
            confirmButton.addActionListener(e -> confirmPurchase());

        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            int w = getWidth();
            int h = getHeight();
            Color color1 = Color.LIGHT_GRAY;
            Color color2 = Color.DARK_GRAY;
            GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }

        public void clearList() {
            items.clear();
            amounts.clear();
            items.trimToSize();
            amounts.trimToSize();
            System.out.println(items);
            System.out.println(amounts);
        }

        //adds item to lists
        public void addToCart(String item, int quantity) {
            items.add(item);
            amounts.add(quantity);
        }
        public void confirmPurchase() {
            for (int i = 0; i < items.size(); i++) {
                String itemName = items.get(i);
                int conversion = Integer.parseInt(itemName);
                int itemQuantity = amounts.get(i);
                connect.transaction(conversion, itemQuantity, false);
            }
            clearList();
        }
        /**public String getTime() {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            return formatter.format(date);
        }**/

        public static void main(String[] args) {
            JFrame frame = new JFrame("Inventory");
            frame.setJMenuBar(new MenuDriver());
            frame.setSize(700, 500);
            frame.add(new Inventory());
            Image icon = Toolkit.getDefaultToolkit().getImage("Inventory.png");
            frame.setIconImage(icon);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
}
