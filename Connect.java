import javax.swing.*;
import java.sql.*;

public class Connect {
    private Connection connect() {
        String url = "jdbc:sqlite:InventoryDatabase.sqlite";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void checkInventory(int sku) {
        String query = "SELECT * FROM Inventory where SKU=?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // set the value
            pstmt.setInt(1, sku);
            //
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("its in there");
            } else {
                System.out.println("Value not in Table");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // and Quantity = ?
    }

    public String displayInventory() {
        String message = null;
        String sql = "SELECT SKU, Quantity FROM Inventory";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                message = "SKU" + "\t" + "Quantity" + "\n" + rs.getInt("Sku") + "\t" +
                        rs.getInt("Quantity");
                //System.out.println(rs.getInt("SKU") +  "\t" +
                //        rs.getInt("Quantity"));
            }
        } catch (SQLException e) {
            message = e.getMessage();
        }
        return message;
    }

    public void transaction(int sku, int quant, boolean addition) {
        String sql = "SELECT SKU, Quantity FROM Inventory";
        try (Connection conn = this.connect();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                    int SKU = rs.getInt("Sku");
                    int Quantity = rs.getInt("Quantity");
                    if (sku == SKU) {
                        if(!addition) {
                            if (Quantity < quant) {
                                String outMessage = "Item amount exceeds level of Inventory" + SKU + "is" + quant;
                                JOptionPane.showMessageDialog(null, outMessage);
                            } else {
                                int q = Quantity - quant;
                                conn.close();
                                finalizeAmount(sku, q);
                            }
                        } else {
                            if(quant > 0) {
                                conn.close();
                                int amount = quant + Quantity;
                                finalizeAmount(sku, amount);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Item not in Inventory");
                    }
                }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void finalizeAmount(int SKU, int quantity) {
        System.out.println("Success");
        String sql = "UPDATE Inventory SET Quantity = ?  WHERE SKU = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, SKU);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addRow(int sku, int quant) {
        String sql = "INSERT INTO warehouses(name,capacity) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sku);
            pstmt.setInt(2, quant);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
