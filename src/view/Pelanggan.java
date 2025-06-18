
package view;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Pelanggan extends javax.swing.JPanel {

    JTable table;
    DefaultTableModel model; 
 
    public Pelanggan() {
        initComponents();
        loadDataTabel();
        
        search.setText("Search");
        search.setForeground(Color.white);

        search.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (search.getText().equals("Search")) {
                    search.setText("");
                    search.setForeground(Color.white);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (search.getText().isEmpty()) {
                    search.setText("Search");
                    search.setForeground(Color.white);
                }
            }
        });
        
        search.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                cariData();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                cariData();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                cariData();
            }
        });
    }

    private void loadDataTabel() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID Pelanggan");
    model.addColumn("Nama");
    model.addColumn("No HP");
    model.addColumn("Poin");
    model.addColumn("Status");
    model.addColumn("Status Reward");

    tbl_pelanggan.setModel(model);

    Koneksi koneksi = new Koneksi();
    koneksi.config();

    try {
        Connection conn = koneksi.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM pelanggan");

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("id_pelanggan"),
                rs.getString("nama_pelanggan"),
                rs.getString("no_hp"),
                rs.getInt("poin"),
                rs.getString("status"),
                rs.getString("status_reward")
            });
        }

        rs.close();
        st.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal mengambil data: " + e.getMessage());
    }
}
    
    private void hapusData() {
    int selectedRow = tbl_pelanggan.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dinonaktifkan!");
        return;
    }

    String idPelanggan = tbl_pelanggan.getValueAt(selectedRow, 0).toString();

    int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menonaktifkan pelanggan dengan ID: " + idPelanggan + "?",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION);

    if (konfirmasi == JOptionPane.YES_OPTION) {
        try {
            Koneksi koneksi = new Koneksi();
            koneksi.config();
            Connection conn = koneksi.getConnection();

            String sql = "UPDATE pelanggan SET status='nonaktif', poin=0 WHERE id_pelanggan=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idPelanggan);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Pelanggan dinonaktifkan!");
            loadDataTabel();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal: " + e.getMessage());
        }
    }
}
    
    private void aktifkanKembali() {
    int selectedRow = tbl_pelanggan.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih baris yang ingin diaktifkan kembali!");
        return;
    }

    String idPelanggan = tbl_pelanggan.getValueAt(selectedRow, 0).toString();

    try {
        Koneksi koneksi = new Koneksi();
        koneksi.config();
        Connection conn = koneksi.getConnection();

        String sql = "UPDATE pelanggan SET status='aktif' WHERE id_pelanggan=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, idPelanggan);
        ps.executeUpdate();

        JOptionPane.showMessageDialog(this, "Pelanggan diaktifkan kembali!");
        loadDataTabel();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal mengaktifkan kembali: " + e.getMessage());
    }
}
    
    private void cariData() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID Pelanggan");
    model.addColumn("Nama");
    model.addColumn("No HP");
    model.addColumn("Poin");
    model.addColumn("Status");
    model.addColumn("Status Reward");

    String keyword = search.getText().trim(); // ambil teks pencarian

    try {
        Koneksi koneksi = new Koneksi();
        koneksi.config();
        Connection conn = koneksi.getConnection();

        String query = "SELECT * FROM pelanggan WHERE id_pelanggan LIKE ? OR nama_pelanggan LIKE ? OR no_hp LIKE ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, "%" + keyword + "%");
        ps.setString(2, "%" + keyword + "%");
        ps.setString(3, "%" + keyword + "%");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("id_pelanggan"),
                rs.getString("nama_pelanggan"),
                rs.getString("no_hp"),
                rs.getInt("poin"),
                rs.getString("status"),
                rs.getString("status_reward")
            });
        }

        tbl_pelanggan.setModel(model);

        rs.close();
        ps.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage());
    }
}

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_pelanggan = new palette.JTable_Custom2();
        btn_aktif = new javax.swing.JButton();
        btn_nonaktif = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        search = new palette.JTextField_Rounded();

        setOpaque(false);

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Data Pelanggan");

        tbl_pelanggan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nama Pelanggan", "Nomer Telepon", "Poin", "Status"
            }
        ));
        jScrollPane1.setViewportView(tbl_pelanggan);

        btn_aktif.setContentAreaFilled(false);

        btn_aktif.setBorderPainted(false);
        btn_aktif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_aktif.png"))); // NOI18N
        btn_aktif.setBorder(null);
        btn_aktif.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_aktif_select.png"))); // NOI18N
        btn_aktif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_aktifActionPerformed(evt);
            }
        });

        btn_nonaktif.setContentAreaFilled(false);

        btn_nonaktif.setBorderPainted(false);
        btn_nonaktif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_nonaktif.png"))); // NOI18N
        btn_nonaktif.setBorder(null);
        btn_nonaktif.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_nonaktif_select.png"))); // NOI18N
        btn_nonaktif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nonaktifActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_pelanggan.png"))); // NOI18N

        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 984, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addGap(6, 6, 6))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_aktif, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_nonaktif, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_aktif)
                    .addComponent(btn_nonaktif))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        search.getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
        String keyword = search.getText();

        if (keyword.equals("Search") || keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Masukkan kata kunci pencarian.");
        }
    }//GEN-LAST:event_searchActionPerformed

    private void btn_nonaktifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nonaktifActionPerformed
        // TODO add your handling code here:
        hapusData();
    }//GEN-LAST:event_btn_nonaktifActionPerformed

    private void btn_aktifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_aktifActionPerformed
        // TODO add your handling code here:
        aktifkanKembali();
    }//GEN-LAST:event_btn_aktifActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_aktif;
    private javax.swing.JButton btn_nonaktif;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private palette.JTextField_Rounded search;
    private palette.JTable_Custom2 tbl_pelanggan;
    // End of variables declaration//GEN-END:variables
}
