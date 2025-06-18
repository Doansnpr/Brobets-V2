
package view;

import event.EventMenuSwitch;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.MainPegawai;


public class BarangRusak extends javax.swing.JPanel {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    private EventMenuSwitch switchEvent;
    private MainPegawai main;
 
    public BarangRusak() {
        initComponents();
        this.switchEvent = switchEvent;
        this.main = main;
        Koneksi DB = new Koneksi();
        DB.config();
        con = DB.con;
        
        tampilkanDataBarangRusak();
        
        
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
                cariRusak();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                cariRusak();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                cariRusak();
            }
        });
    }
    
        
    private void tampilkanDataBarangRusak() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Bermasalah");
        model.addColumn("ID Detail Pengembalian");
        model.addColumn("ID Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Stok");
        model.addColumn("Harga Beli");
        model.addColumn("Status");
        model.addColumn("Sumber Rusak");

        try {
            con = Koneksi.getConnection();
            String sql = "SELECT bb.id_brg_bermasalah, bb.id_detail_kembali, bb.id_barang, "
                       + "b.nama_barang, bb.stok, bb.harga_beli, bb.status, bb.sumber_rusak "
                       + "FROM barang_bermasalah bb "
                       + "JOIN barang b ON bb.id_barang = b.id_barang";

            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_brg_bermasalah"),
                    rs.getString("id_detail_kembali"),
                    rs.getString("id_barang"),
                    rs.getString("nama_barang"),
                    rs.getInt("stok"),
                    rs.getInt("harga_beli"),
                    rs.getString("status"),
                    rs.getString("sumber_rusak")
                });
            }

            tbl_rusak.setModel(model);

            tbl_rusak.removeColumn(tbl_rusak.getColumnModel().getColumn(0));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan data barang rusak: " + e.getMessage());
        }
    }
    
    private void cariRusak() {
    String keyword = search.getText().trim();

    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID Bermasalah");
    model.addColumn("ID Detail");
    model.addColumn("ID Barang");
    model.addColumn("Nama Barang");
    model.addColumn("Stok");
    model.addColumn("Harga Beli");
    model.addColumn("Status");
    model.addColumn("Sumber Rusak");

    try {
        con = Koneksi.getConnection();
        String sql = "SELECT bb.id_brg_bermasalah, bb.id_detail_kembali, bb.id_barang, "
                   + "b.nama_barang, bb.stok, bb.harga_beli, bb.status, bb.sumber_rusak "
                   + "FROM barang_bermasalah bb "
                   + "JOIN barang b ON bb.id_barang = b.id_barang "
                   + "WHERE b.nama_barang LIKE ? OR bb.status LIKE ?";

        pst = con.prepareStatement(sql);
        pst.setString(1, "%" + keyword + "%");
        pst.setString(2, "%" + keyword + "%");
        rs = pst.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("id_brg_bermasalah"),
                rs.getString("id_detail_kembali"),
                rs.getString("id_barang"),
                rs.getString("nama_barang"),
                rs.getInt("stok"),
                rs.getInt("harga_beli"),
                rs.getString("status"),
                rs.getString("sumber_rusak")
            });
        }

        tbl_rusak.setModel(model);
        tbl_rusak.removeColumn(tbl_rusak.getColumnModel().getColumn(0));
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal mencari data barang rusak: " + e.getMessage());
    }
}

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_rusak = new palette.JTable_Custom2();
        btn_periksa = new javax.swing.JButton();
        btn_perbaikan = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        search = new palette.JTextField_Rounded();
        btn_back = new javax.swing.JButton();

        setOpaque(false);

        jPanel1.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Data Barang Rusak");

        tbl_rusak.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nama Barang", "Harga Sewa", "Kategori", "Stok", "Status", "Harga Beli"
            }
        ));
        jScrollPane1.setViewportView(tbl_rusak);

        btn_periksa.setContentAreaFilled(false);

        btn_periksa.setBorderPainted(false);
        btn_periksa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_periksa.png"))); // NOI18N
        btn_periksa.setBorder(null);
        btn_periksa.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_periksa_select.png"))); // NOI18N
        btn_periksa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_periksaActionPerformed(evt);
            }
        });

        btn_perbaikan.setContentAreaFilled(false);

        btn_perbaikan.setBorderPainted(false);
        btn_perbaikan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_perbaiki.png"))); // NOI18N
        btn_perbaikan.setBorder(null);
        btn_perbaikan.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_perbaiki_select.png"))); // NOI18N
        btn_perbaikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_perbaikanActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_items.png"))); // NOI18N

        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        btn_perbaikan.setContentAreaFilled(false);

        btn_perbaikan.setBorderPainted(false);
        btn_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_kembali.png"))); // NOI18N
        btn_back.setBorder(null);
        btn_back.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_kembali_select.png"))); // NOI18N
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(btn_periksa, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_perbaikan, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(468, 468, 468)
                            .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel2))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 984, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn_periksa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_perbaikan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_back, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1018, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 631, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_periksaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_periksaActionPerformed
        // TODO add your handling code here:
       int[] selectedRows = tbl_rusak.getSelectedRows();

        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(null, "Pilih barang yang akan diperiksa!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) tbl_rusak.getModel();

        for (int row : selectedRows) {
            String statusBarang = model.getValueAt(row, 6).toString(); // Kolom ke-6 = status
            if (!statusBarang.equalsIgnoreCase("Menunggu Pemeriksaan")) {
            JOptionPane.showMessageDialog(null, "Hanya barang dengan status 'Menunggu Pemeriksaan' yang bisa diperiksa!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        }

        String[] options = {"Rusak", "Maintenance"};
        String newStatus = (String) JOptionPane.showInputDialog(
            null,
            "Pilih status baru untuk barang yang dipilih:",
            "Proses Pemeriksaan",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (newStatus != null) {
            try {
                con.setAutoCommit(false);

                String sqlUpdate = "UPDATE barang_bermasalah SET status = ? WHERE id_brg_bermasalah = ?";
                PreparedStatement pstUpdate = con.prepareStatement(sqlUpdate);

                for (int row : selectedRows) {
                    String idMasalah = model.getValueAt(row, 0).toString();


                    pstUpdate.setString(1, newStatus);
                    pstUpdate.setString(2, idMasalah);
                    pstUpdate.addBatch();
                }

                pstUpdate.executeBatch();
                con.commit();

                JOptionPane.showMessageDialog(null, "Status barang berhasil diupdate ke '" + newStatus + "'");

                tampilkanDataBarangRusak();

            } catch (SQLException ex) {
                try {
                    con.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Gagal update status: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btn_periksaActionPerformed

    private void btn_perbaikanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_perbaikanActionPerformed
        // TODO add your handling code here:
        int row = tbl_rusak.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data barang rusak terlebih dahulu.");
            return;
        }

        DefaultTableModel rusakModel = (DefaultTableModel) tbl_rusak.getModel();
        DefaultTableModel barangModel = (DefaultTableModel) main.getBarangPanel().getTblBarang().getModel();

        try {
            String idRusak = rusakModel.getValueAt(row, 0).toString();
            String idBarang = rusakModel.getValueAt(row, 2).toString();
            String nama = rusakModel.getValueAt(row, 3).toString();
            int stokRusak = Integer.parseInt(rusakModel.getValueAt(row, 4).toString());
            int hargaBeli = Integer.parseInt(rusakModel.getValueAt(row, 5).toString());
            String status = rusakModel.getValueAt(row, 6).toString();

            if (!status.equalsIgnoreCase("Maintenance")) {
                JOptionPane.showMessageDialog(this, "Hanya barang dengan status 'Maintenance' yang bisa diperbaiki.");
                return;
            }

            String input = JOptionPane.showInputDialog(this, "Jumlah barang diperbaiki:");
            if (input == null) return;

            int jumlah = Integer.parseInt(input);
            if (jumlah <= 0 || jumlah > stokRusak) {
                JOptionPane.showMessageDialog(this, "Jumlah tidak valid.");
                return;
            }

            if (JOptionPane.showConfirmDialog(this, "Yakin perbaiki " + jumlah + " barang?", "Konfirmasi", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
                return;

            con = Koneksi.getConnection();
            boolean barangDitemukan = false;

            // Update stok di tbl_barang
            for (int i = 0; i < barangModel.getRowCount(); i++) {
                if (barangModel.getValueAt(i, 0).toString().equals(idBarang)) {
                    int stokLama = Integer.parseInt(barangModel.getValueAt(i, 4).toString());
                    int stokBaru = stokLama + jumlah;
                    barangModel.setValueAt(stokBaru, i, 4);

                    PreparedStatement pst = con.prepareStatement("UPDATE barang SET stok = ? WHERE id_barang = ?");
                    pst.setInt(1, stokBaru);
                    pst.setString(2, idBarang);
                    pst.executeUpdate();
                    pst.close();

                    barangDitemukan = true;
                    break;
                }
            }

            // Jika barang belum ada di tabel
            if (!barangDitemukan) {
                PreparedStatement pst = con.prepareStatement("INSERT INTO barang VALUES (?, ?, 0, ?, '', ?, 'Tersedia')");
                pst.setString(1, idBarang);
                pst.setString(2, nama);
                pst.setInt(3, hargaBeli);
                pst.setInt(4, jumlah);
                pst.executeUpdate();
                pst.close();

                barangModel.addRow(new Object[]{idBarang, nama, 0, hargaBeli, jumlah, "Tersedia"});
            }

            // Update atau hapus dari tbl_rusak
            int sisa = stokRusak - jumlah;
            if (sisa > 0) {
                rusakModel.setValueAt(sisa, row, 4);
                PreparedStatement pst = con.prepareStatement("UPDATE barang_bermasalah SET stok = ? WHERE id_brg_bermasalah = ?");
                pst.setInt(1, sisa);
                pst.setString(2, idRusak);
                pst.executeUpdate();
                pst.close();
            } else {
                PreparedStatement pst = con.prepareStatement("DELETE FROM barang_bermasalah WHERE id_brg_bermasalah = ?");
                pst.setString(1, idRusak);
                pst.executeUpdate();
                pst.close();
                rusakModel.removeRow(row);
            }

            JOptionPane.showMessageDialog(this, "Perbaikan berhasil dan stok diperbarui.");
            main.getBarangPanel().load_table();
            tampilkanDataBarangRusak();

            main.setPanel(main.getBarangPanel());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_perbaikanActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
        String keyword = search.getText();

        if (keyword.equals("Search") || keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Masukkan kata kunci pencarian.");
        }
    }//GEN-LAST:event_searchActionPerformed

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        // TODO add your handling code here:
//        main.setPanel(main.getBarangPanel());
    }//GEN-LAST:event_btn_backActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_perbaikan;
    private javax.swing.JButton btn_periksa;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private palette.JTextField_Rounded search;
    private palette.JTable_Custom2 tbl_rusak;
    // End of variables declaration//GEN-END:variables
}
