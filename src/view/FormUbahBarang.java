
package view;

import event.BarangUpdateListener;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;


public class FormUbahBarang extends javax.swing.JPanel {

    
    private Map<String, String> kategoriMap = new HashMap<>();
    private Map<String, String> kategori1Map = new HashMap<>();
    
    
    
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    private String idBarangYangSedangDiedit;
    private BarangUpdateListener updateListener;
    private String selectedStatus = "Tersedia";
    
    public FormUbahBarang() {
        initComponents();
        
        Koneksi DB = new Koneksi();
        DB.config();
        con = DB.con;
        
        
        loadKategori();
        populateStatusComboBox();
        
        cmb_kategori_ubah.setRenderer(new DefaultListCellRenderer() {
        @Override
        public java.awt.Component getListCellRendererComponent(
                JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof Kategori) {
                Kategori kategori = (Kategori) value;
                setText(kategori.getNama()); 
            }

            return this;
        }
    });
        
        ((AbstractDocument) txt_harga1.getDocument()).setDocumentFilter(new FormatHarga());
        ((AbstractDocument) txt_beli1.getDocument()).setDocumentFilter(new FormatHarga());
        ((AbstractDocument) txt_stok1.getDocument()).setDocumentFilter(new filter());
        
        
    }
    
  
    
    @SuppressWarnings("unchecked")
    public void loadKategori() {
         cmb_kategori_ubah.removeAllItems();

        try {
            String sql = "SELECT * FROM katalog";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            DefaultComboBoxModel model = new DefaultComboBoxModel();
            cmb_kategori_ubah.setModel(model);

            while (rs.next()) {
                String id = rs.getString("id_katalog");
                String nama = rs.getString("nama_katalog");
                model.addElement(new Kategori(id, nama));
            }

            cmb_kategori_ubah.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(
                        JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Kategori) {
                        setText(((Kategori) value).getNama());
                    }
                    return this;
                }
            });

            rs.close();
            pst.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat kategori: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void populateStatusComboBox() {
        try {
            String query = "SELECT DISTINCT status FROM barang";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();

            Set<String> statusSet = new HashSet<>();
            cmb_status_ubah.removeAllItems();

            while (rs.next()) {
                String status = rs.getString("status");
                if (status != null && !status.trim().isEmpty() && statusSet.add(status.trim())) {
                    cmb_status_ubah.addItem(status.trim());
                }
            }

            // Tambahan manual jika belum ada
            String[] manualStatuses = {"Tersedia"};
            for (String manualStatus : manualStatuses) {
                if (statusSet.add(manualStatus)) {
                    cmb_status_ubah.addItem(manualStatus);
                }
            }

            rs.close();
            pst.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Kesalahan database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void setBarangUpdateListener(BarangUpdateListener listener) {
        this.updateListener = listener;
    }

    
    public void setDataBarang(String idBarang, String namaBarang, int hargaSewa, int hargaBeli, int stok, String status, String idKatalog) {
    this.idBarangYangSedangDiedit = idBarang;  // <--- Ini wajib agar btn_simpan tahu barang mana yang diedit

    txt_nama1.setText(namaBarang);
    txt_harga1.setText(String.valueOf(hargaSewa));
    txt_beli1.setText(String.valueOf(hargaBeli));
    txt_stok1.setText(String.valueOf(stok));
    cmb_status_ubah.setSelectedItem(status);

    // Pilih kategori berdasarkan ID
    for (int i = 0; i < cmb_kategori_ubah.getItemCount(); i++) {
        Object item = cmb_kategori_ubah.getItemAt(i);
        if (item instanceof Kategori kategori) {
            if (kategori.getId().equals(idKatalog)) {
                cmb_kategori_ubah.setSelectedIndex(i);
                break;
            }
        }
    }
}

    
    public String getIdBarangYangSedangDiedit() {
        return idBarangYangSedangDiedit;
    } 
    
  
    public void setIdBarangYangSedangDiedit(String id) {
        this.idBarangYangSedangDiedit = id;
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
        txt_nama1 = new palette.JTextField_Rounded();
        jLabel9 = new javax.swing.JLabel();
        txt_harga1 = new palette.JTextField_Rounded();
        jLabel10 = new javax.swing.JLabel();
        txt_beli1 = new palette.JTextField_Rounded();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_stok1 = new palette.JTextField_Rounded();
        jLabel13 = new javax.swing.JLabel();
        cmb_kategori_ubah = new javax.swing.JComboBox<>();
        cmb_status_ubah = new javax.swing.JComboBox<>();

        jTextField_Rounded1.setText("jTextField_Rounded1");

        pn_tambah.setBackground(new java.awt.Color(40, 70, 70));
        pn_tambah.setRoundBottomLeft(10);
        pn_tambah.setRoundBottomRight(10);
        pn_tambah.setRoundTopLeft(10);
        pn_tambah.setRoundTopRight(10);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_items.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Form Ubah");

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
                    .addComponent(txt_stok1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(txt_beli1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addComponent(txt_harga1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addComponent(jLabel8)
                    .addComponent(txt_nama1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addGroup(pn_tambahLayout.createSequentialGroup()
                        .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pn_tambahLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel2))
                    .addComponent(cmb_kategori_ubah, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_status_ubah, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(btn_batal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nama1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_harga1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_beli1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addGap(4, 4, 4)
                .addComponent(cmb_kategori_ubah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_stok1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_status_ubah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        String newNamaBarang = txt_nama1.getText().trim();
        String hargaStr = txt_harga1.getText().trim().replaceAll("[^\\d]", "");
        String beliStr = txt_beli1.getText().trim().replaceAll("[^\\d]", "");
        String stokStr = txt_stok1.getText().trim();
        String newStatus = cmb_status_ubah.getSelectedItem().toString();

        if (newNamaBarang.isEmpty() || hargaStr.isEmpty() || beliStr.isEmpty() || stokStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int newHargaSewa = Integer.parseInt(hargaStr);
            int newBeli = Integer.parseInt(beliStr);
            int newStok = Integer.parseInt(stokStr);

            Object selectedObj = cmb_kategori_ubah.getSelectedItem();
            if (selectedObj == null || !(selectedObj instanceof Kategori)) {
                JOptionPane.showMessageDialog(this, "Kategori tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Kategori kategori = (Kategori) selectedObj;
            String newKategoriId = kategori.getId();

            if (this.idBarangYangSedangDiedit == null) {
                JOptionPane.showMessageDialog(this, "Pilih data barang yang ingin diupdate!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String selectedIdBarang = this.idBarangYangSedangDiedit;

            String cekQuery = "SELECT * FROM barang WHERE nama_barang = ? AND id_barang != ?";
            pst = con.prepareStatement(cekQuery);
            pst.setString(1, newNamaBarang);
            pst.setString(2, selectedIdBarang);
            rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Nama barang sudah digunakan, silakan pilih yang lain", "Error", JOptionPane.ERROR_MESSAGE);
                rs.close();
                pst.close();
                return;
            }
            rs.close();
            pst.close();

            String updateQuery = "UPDATE barang SET nama_barang = ?, harga_sewa = ?, harga_beli = ?, id_katalog = ?, stok = ?, status = ? WHERE id_barang = ?";
            pst = con.prepareStatement(updateQuery);
            pst.setString(1, newNamaBarang);
            pst.setInt(2, newHargaSewa);
            pst.setInt(3, newBeli);
            pst.setString(4, newKategoriId);
            pst.setInt(5, newStok);
            pst.setString(6, newStatus);
            pst.setString(7, selectedIdBarang);

            int result = pst.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Data barang berhasil diperbarui.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                if (updateListener != null) {
                    updateListener.onBarangUpdated();
                }
                SwingUtilities.getWindowAncestor(this).dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal memperbarui data barang.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            pst.close();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga Sewa, Harga Beli, dan Stok harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Kesalahan database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        SwingUtilities.getWindowAncestor(this).dispose();
    }//GEN-LAST:event_btn_batalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JComboBox<String> cmb_kategori_ubah;
    private javax.swing.JComboBox<String> cmb_status_ubah;
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
    private palette.JTextField_Rounded txt_beli1;
    private palette.JTextField_Rounded txt_harga1;
    private palette.JTextField_Rounded txt_nama1;
    private palette.JTextField_Rounded txt_stok1;
    // End of variables declaration//GEN-END:variables
}
