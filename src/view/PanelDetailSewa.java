/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Khafila Maulidiyah W
 */
public class PanelDetailSewa extends javax.swing.JPanel {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    private String idSewa;
    
    public PanelDetailSewa(String idSewa) {
        initComponents();
        Koneksi DB = new Koneksi();
        DB.config();
        con = DB.con;
        this.idSewa = idSewa;
        loadDetailSewa(); // method untuk isi data dari idSewa
    }

    
     private void loadDetailSewa() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No");
        model.addColumn("Nama Barang");
        model.addColumn("Harga Sewa");
        model.addColumn("Jumlah");
        model.addColumn("Subtotal");

        int no = 1;

        try {
            String sql = "SELECT b.nama_barang, ds.qty, b.harga_sewa, ds.sub_total " +
             "FROM detail_sewa ds " +
             "JOIN barang b ON ds.id_barang = b.id_barang " +
             "WHERE ds.id_sewa = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, idSewa);
            rs = pst.executeQuery();

            while (rs.next()) {
                String namaBarang = rs.getString("nama_barang");
                int jumlah = rs.getInt("harga_sewa");
                int harga = rs.getInt("qty");          
                int subtotal = rs.getInt("sub_total"); 

                model.addRow(new Object[]{
                    no++, namaBarang, jumlah, harga, subtotal
                });
            }


            table_detail.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load detail sewa: " + e.getMessage());
        }
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table_detail = new palette.JTable_Custom2();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(40, 70, 70));

        table_detail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(table_detail);

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Detail Sewa");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private palette.JTable_Custom2 table_detail;
    // End of variables declaration//GEN-END:variables
}
