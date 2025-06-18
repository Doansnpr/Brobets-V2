
package view;

import event.BarangUpdateListener;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;


public class FormTambahBarang extends javax.swing.JPanel {
    
    private Map<String, String> kategoriMap = new HashMap<>();
    private Map<String, String> kategori1Map = new HashMap<>();

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    private BarangUpdateListener updateListener;
    
    private String selectedStatus = "Tersedia";
    
    public FormTambahBarang() {
        initComponents();
        
        Koneksi DB = new Koneksi();
        DB.config();
        con = DB.con;
       
        
        loadKategoriToComboBox();
        cmb_kategori_tambah.setSelectedItem(null);
       
        
        ((AbstractDocument) txt_harga.getDocument()).setDocumentFilter(new FormatHarga());
        ((AbstractDocument) txt_beli.getDocument()).setDocumentFilter(new FormatHarga());
        ((AbstractDocument) txt_stok.getDocument()).setDocumentFilter(new filter());
        
        Barang.EnumComboBoxLoader.loadEnumFromDatabase(con, cmb_status_tambah);
        cmb_status_tambah.setSelectedItem(null);
        
       
        
    }
    
    public void setUpdateListener(BarangUpdateListener listener) {
        this.updateListener = listener;
    }

    private void loadKategoriToComboBox() {
        cmb_kategori_tambah.removeAllItems();
        kategoriMap.clear();

        try {
            Koneksi.config();
            Connection conn = Koneksi.getConnection();

            String sql = "SELECT id_katalog, nama_katalog FROM katalog";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id_katalog");
                String nama = rs.getString("nama_katalog");

                kategoriMap.put(nama, id); // simpan relasi nama -> id
                cmb_kategori_tambah.addItem(nama); // tampilkan nama ke comboBox
                
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data katalog: " + e.getMessage());
        }
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
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btn_simpan = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txt_nama = new palette.JTextField_Rounded();
        jLabel9 = new javax.swing.JLabel();
        txt_harga = new palette.JTextField_Rounded();
        jLabel10 = new javax.swing.JLabel();
        txt_beli = new palette.JTextField_Rounded();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_stok = new palette.JTextField_Rounded();
        jLabel13 = new javax.swing.JLabel();
        cmb_kategori_tambah = new javax.swing.JComboBox<>();
        cmb_status_tambah = new javax.swing.JComboBox<>();

        jTextField_Rounded1.setText("jTextField_Rounded1");

        pn_tambah.setBackground(new java.awt.Color(40, 70, 70));
        pn_tambah.setRoundBottomLeft(10);
        pn_tambah.setRoundBottomRight(10);
        pn_tambah.setRoundTopLeft(10);
        pn_tambah.setRoundTopRight(10);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_items.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Form Tambah");

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
        jLabel9.setText("Harga Sewa");

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Harga Beli");

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Katalog");

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Stok");

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Status");

        javax.swing.GroupLayout pn_tambahLayout = new javax.swing.GroupLayout(pn_tambah);
        pn_tambah.setLayout(pn_tambahLayout);
        pn_tambahLayout.setHorizontalGroup(
            pn_tambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_tambahLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pn_tambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12)
                    .addComponent(txt_stok, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(txt_beli, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addComponent(txt_harga, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addComponent(jLabel8)
                    .addComponent(txt_nama, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addGroup(pn_tambahLayout.createSequentialGroup()
                        .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pn_tambahLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel2))
                    .addComponent(cmb_kategori_tambah, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_status_tambah, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        pn_tambahLayout.setVerticalGroup(
            pn_tambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_tambahLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pn_tambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_tambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_simpan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_batal, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_beli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addGap(4, 4, 4)
                .addComponent(cmb_kategori_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_stok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_status_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 2, Short.MAX_VALUE)
                .addComponent(pn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        // TODO add your handling code here:

        String nama_barang, kategori, status, save;
        int harga_sewa, stok, harga_beli;

        String id_barang = generateID("barang", "id_barang", "BRG");
        nama_barang = txt_nama.getText();
        txt_nama.setForeground(Color.BLACK);
        String hargaStr = txt_harga.getText();
        String beliStr = txt_beli.getText();
        kategori = cmb_kategori_tambah.getSelectedItem().toString().toUpperCase();
        String stokStr = txt_stok.getText();
        status = cmb_status_tambah.getSelectedItem().toString().toUpperCase();

        if (nama_barang.isEmpty() || nama_barang.equals("Masukkan Nama Barang")
            || hargaStr.isEmpty() || hargaStr.equals("Masukkan Harga Sewa")
            || beliStr.isEmpty() || beliStr.equals("Masukkan Harga Beli")
            || cmb_kategori_tambah.getSelectedItem() == null || cmb_kategori_tambah.getSelectedItem().toString().isEmpty()
            || stokStr.isEmpty() || stokStr.equals("Masukkan Stok")
            || cmb_status_tambah.getSelectedItem() == null || cmb_status_tambah.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Cek apakah nama barang sudah ada di DB
            String cekNamaSQL = "SELECT COUNT(*) FROM barang WHERE nama_barang = ?";
            pst = con.prepareStatement(cekNamaSQL);
            pst.setString(1, nama_barang);
            ResultSet rs = pst.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "Nama barang sudah ada, silakan ganti nama lain!", "Error", JOptionPane.ERROR_MESSAGE);
                pst.close();
                return;
            }
            pst.close();

            harga_sewa = Integer.parseInt(hargaStr.replaceAll("[^0-9]", ""));
            harga_beli = Integer.parseInt(beliStr.replaceAll("[^0-9]", ""));
            stok = Integer.parseInt(stokStr);

            if (harga_sewa <= 0 || harga_beli <= 0 || stok <= 0) {
                JOptionPane.showMessageDialog(this, "Harga, Beli, dan Stok harus lebih dari 0!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nama_katalog = cmb_kategori_tambah.getSelectedItem().toString();
            String id_katalog = "";

            String sqlKat = "SELECT id_katalog FROM katalog WHERE nama_katalog = ?";
            pst = con.prepareStatement(sqlKat);
            pst.setString(1, nama_katalog);
            rs = pst.executeQuery();

            if (rs.next()) {
                id_katalog = rs.getString("id_katalog");
            } else {
                JOptionPane.showMessageDialog(this, "Kategori tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            pst.close();
             save = "INSERT INTO barang (id_barang, nama_barang, harga_sewa, harga_beli, id_katalog, stok, status) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            pst = con.prepareStatement(save);
            pst.setString(1, id_barang);
            pst.setString(2, nama_barang);
            pst.setInt(3, harga_sewa);
            pst.setInt(4, harga_beli);
            pst.setString(5, id_katalog);
            pst.setInt(6, stok);
            pst.setString(7, status);


            int result = pst.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Barang berhasil ditambahkan", "Sukses", JOptionPane.INFORMATION_MESSAGE);

                if (updateListener != null) {
                    updateListener.onBarangUpdated();
                }

                SwingUtilities.getWindowAncestor(this).dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan barang!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            pst.close();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga Sewa, Harga Beli, dan Stok harus berupa angka valid!", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        SwingUtilities.getWindowAncestor(this).dispose();
    }//GEN-LAST:event_btn_batalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JComboBox<String> cmb_kategori_tambah;
    private javax.swing.JComboBox<String> cmb_status_tambah;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private palette.JTextField_Rounded jTextField_Rounded1;
    private palette.JPanelRounded pn_tambah;
    private palette.JTextField_Rounded txt_beli;
    private palette.JTextField_Rounded txt_harga;
    private palette.JTextField_Rounded txt_nama;
    private palette.JTextField_Rounded txt_stok;
    // End of variables declaration//GEN-END:variables
}
