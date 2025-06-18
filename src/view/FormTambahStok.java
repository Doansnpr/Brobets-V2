
package view;

import event.BarangUpdateListener;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;

public class FormTambahStok extends javax.swing.JPanel {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    private BarangUpdateListener updateListener;
    private Map<String, String> barangMap = new HashMap<>();
    private Map<String, String> pemasokMap = new HashMap<>();
    private Map<String, String> katalogMap = new HashMap<>();

    
    public FormTambahStok() {
        initComponents();
        
        Koneksi DB = new Koneksi();
        DB.config();
        con = DB.con;
        
        ((AbstractDocument) txt_jumlah.getDocument()).setDocumentFilter(new filter());
        ((AbstractDocument) txt_harga_beli.getDocument()).setDocumentFilter(new FormatHarga());


        loadBarangKeComboBox();
        loadKatalogToComboBox();
        loadPemasokToComboBox();
  
    }
    
    private void loadKatalogToComboBox() {
    cmb_katalog.removeAllItems();
    katalogMap.clear(); // hapus data sebelumnya

    try {
        Koneksi.config();
        Connection conn = Koneksi.getConnection();

        String sql = "SELECT id_katalog, nama_katalog FROM katalog";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String id = rs.getString("id_katalog");
            String nama = rs.getString("nama_katalog");

            katalogMap.put(nama, id); // simpan relasi nama -> id
            cmb_katalog.addItem(nama); // tampilkan nama ke comboBox
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat data katalog: " + e.getMessage());
    }
}

    private void loadBarangKeComboBox() {
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
    barangMap.clear();

    try {
        Koneksi.config();
        Connection conn = Koneksi.getConnection();
        String sql = "SELECT id_barang, nama_barang FROM barang";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String idBarang = rs.getString("id_barang");
            String namaBarang = rs.getString("nama_barang");
            barangMap.put(namaBarang, idBarang);
            model.addElement(namaBarang);
        }

        cmb_pilihbarang.setModel(model);
        cmb_pilihbarang.setSelectedIndex(-1); 

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat data barang: " + e.getMessage());
    }
}
    
    private void loadPemasokToComboBox() {
    cmb_idpemasok.removeAllItems();
    pemasokMap.clear(); // hapus data sebelumnya

    try {
        Koneksi.config();
        Connection conn = Koneksi.getConnection();

        String sql = "SELECT id_pemasok, nama_pemasok FROM pemasok";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String id = rs.getString("id_pemasok");
            String nama = rs.getString("nama_pemasok");

            pemasokMap.put(nama, id); // simpan relasi nama -> id
            cmb_idpemasok.addItem(nama); // tampilkan nama ke comboBox
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat data pemasok: " + e.getMessage());
    }
}
    
    public void setUpdateListener(BarangUpdateListener listener) {
        this.updateListener = listener;
    }
    
    private String generateID(String tableName, String idColumn, String prefix) {
        String newID = prefix + "001";
        try {
            String sql = "SELECT " + idColumn + " FROM " + tableName + 
                         " WHERE " + idColumn + " LIKE ? ORDER BY " + idColumn + " DESC LIMIT 1";
            pst = con.prepareStatement(sql);
            pst.setString(1, prefix + "%");
            rs = pst.executeQuery();
            if (rs.next()) {
                String lastID = rs.getString(1); // Misal: BMS006
                int num = Integer.parseInt(lastID.substring(prefix.length())); // 6
                num++; // 7
                newID = prefix + String.format("%03d", num); // BMS007
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal generate ID: " + e.getMessage());
        }
        return newID;
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField_Rounded1 = new palette.JTextField_Rounded();
        pn_tambah = new palette.JPanelRounded();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btn_simpan = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_harga_beli = new palette.JTextField_Rounded();
        jLabel10 = new javax.swing.JLabel();
        txt_jumlah = new palette.JTextField_Rounded();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cmb_katalog = new javax.swing.JComboBox<>();
        cmb_idpemasok = new javax.swing.JComboBox<>();
        cmb_pilihbarang = new javax.swing.JComboBox<>();

        jTextField_Rounded1.setText("jTextField_Rounded1");

        pn_tambah.setBackground(new java.awt.Color(40, 70, 70));
        pn_tambah.setRoundBottomLeft(10);
        pn_tambah.setRoundBottomRight(10);
        pn_tambah.setRoundTopLeft(10);
        pn_tambah.setRoundTopRight(10);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_stok.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Form Tambah Stok");

        btn_simpan.setContentAreaFilled(false);

        btn_simpan.setBorderPainted(false);
        btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_simpan.png"))); // NOI18N
        btn_simpan.setBorder(null);
        btn_simpan.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_simpan_select.png"))); // NOI18N
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        btn_batal.setContentAreaFilled(false);

        btn_batal.setBorderPainted(false);
        btn_batal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_batal.png"))); // NOI18N
        btn_batal.setBorder(null);
        btn_batal.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_batal_select.png"))); // NOI18N
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nama Barang");

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Harga Beli");

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Katalog");

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Jumlah");

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Pemasok");

        javax.swing.GroupLayout pn_tambahLayout = new javax.swing.GroupLayout(pn_tambah);
        pn_tambah.setLayout(pn_tambahLayout);
        pn_tambahLayout.setHorizontalGroup(
            pn_tambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_tambahLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(pn_tambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(txt_jumlah, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(txt_harga_beli, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addGroup(pn_tambahLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_tambahLayout.createSequentialGroup()
                        .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8)
                    .addComponent(cmb_katalog, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_idpemasok, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_pilihbarang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );
        pn_tambahLayout.setVerticalGroup(
            pn_tambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_tambahLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pn_tambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(pn_tambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_simpan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_batal, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(17, 17, 17)
                .addComponent(jLabel8)
                .addGap(4, 4, 4)
                .addComponent(cmb_pilihbarang, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addGap(4, 4, 4)
                .addComponent(cmb_katalog, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_harga_beli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_idpemasok, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addComponent(pn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        String selectedNamaBarang = (String) cmb_pilihbarang.getSelectedItem();
        if (selectedNamaBarang == null || selectedNamaBarang.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Pilih barang terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        

        String idBarang = barangMap.get(selectedNamaBarang);
        if (idBarang == null) {
            JOptionPane.showMessageDialog(this, "Barang tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String jumlahStr = txt_jumlah.getText().trim();
        String hargaStr = txt_harga_beli.getText().replaceAll("[^\\d]", ""); // Hapus "Rp" dan titik
        String namaKatalog = (String) cmb_katalog.getSelectedItem();
        String idKatalog = katalogMap.get(namaKatalog); 
        String namaPemasok = (String) cmb_idpemasok.getSelectedItem();
        String idPemasok = pemasokMap.get(namaPemasok);
        
        if (jumlahStr.isEmpty() || hargaStr.isEmpty() || idKatalog.isEmpty() || idPemasok == null || idPemasok.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int qty = Integer.parseInt(jumlahStr);
            int harga = Integer.parseInt(hargaStr);



            Koneksi.config();
            Connection conn = Koneksi.getConnection();
            String idStokMasuk = generateID("stok_masuk", "id_stok_masuk", "SM");


            // 1. INSERT ke stok_masuk
            String sqlInsert = "INSERT INTO stok_masuk (id_stok_masuk, id_barang, id_katalog, qty, harga, id_pemasok) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstInsert = conn.prepareStatement(sqlInsert);
            pstInsert.setString(1, idStokMasuk);
            pstInsert.setString(2, idBarang);
            pstInsert.setString(3, idKatalog);
            pstInsert.setInt(4, qty);
            pstInsert.setInt(5, harga);
            pstInsert.setString(6, idPemasok);
            pstInsert.executeUpdate();

            // 2. UPDATE stok barang
            String sqlUpdate = "UPDATE barang SET stok = stok + ? WHERE id_barang = ?";
            PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdate);
            pstUpdate.setInt(1, qty);
            pstUpdate.setString(2, idBarang);
            pstUpdate.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");

           if (updateListener != null) {
                updateListener.onBarangUpdated();
            }

            SwingUtilities.getWindowAncestor(this).dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah dan harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        SwingUtilities.getWindowAncestor(this).dispose();
    }//GEN-LAST:event_btn_batalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JComboBox<String> cmb_idpemasok;
    private javax.swing.JComboBox<String> cmb_katalog;
    private javax.swing.JComboBox<String> cmb_pilihbarang;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private palette.JTextField_Rounded jTextField_Rounded1;
    private palette.JPanelRounded pn_tambah;
    private palette.JTextField_Rounded txt_harga_beli;
    private palette.JTextField_Rounded txt_jumlah;
    // End of variables declaration//GEN-END:variables
}
