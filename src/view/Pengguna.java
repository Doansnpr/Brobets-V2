
package view;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import javax.swing.JDialog;

public class Pengguna extends javax.swing.JPanel {

    
    public Pengguna() {
        initComponents();
       
//        label_username.setText(Login.Session.getUsername());

        try {
            loadDataFromDatabase();
        } catch (Exception e) {
        }
        
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
                cariDataPengguna();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                cariDataPengguna();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                cariDataPengguna();
            }
        });
    }
    
    String url = "jdbc:mysql://localhost:3306/brobets";
    String user = "root";
    String DBpassword = "";
    
    
    private void loadDataFromDatabase() throws java.sql.SQLException {
        String query = "SELECT * FROM pengguna WHERE status = 'Aktif'";
        try (Connection con = DriverManager.getConnection(url, user, DBpassword);
             java.sql.Statement stmt = con.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(query)) {
             DefaultTableModel model = (DefaultTableModel) tb_user.getModel();
             model.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                rs.getString("id_pengguna"),
                rs.getString("nama_lengkap"),
                rs.getString("email"),
                rs.getString("alamat"),
                rs.getString("no_hp"),
                rs.getString("nama_pengguna"),
                rs.getString("role"),
                rs.getString("password"),
                };
                model.addRow(row);
            }
        }    
  }
    
    public String generateIdOtomatis() {
        String idBaru = "UR001";
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/brobets", "root", "");
            // 2. Ambil ID terakhir dari database
            String sql = "SELECT id_pengguna FROM pengguna ORDER BY id_pengguna DESC LIMIT 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // 3. Generate ID baru
            if (rs.next()) {
                String lastId = rs.getString("id_pengguna"); // contoh: UR007
                int angka = Integer.parseInt(lastId.substring(2)); // ambil "007" jadi int 7
                angka++; // naik satu
                idBaru = String.format("UR%03d", angka); // jadi UR008
            }

            // 4. Tutup koneksi
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal generate ID: " + e.getMessage());
        }

        return idBaru;
    }
    
    private void cariDataPengguna() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID");
    model.addColumn("Nama");
    model.addColumn("Email");
    model.addColumn("Alamat");
    model.addColumn("No HP");
    model.addColumn("Username");
    model.addColumn("Role");
    model.addColumn("Password");

    String keyword = search.getText().trim(); // teks pencarian

    String sql = "SELECT * FROM pengguna WHERE status = 'Aktif' AND ("
            + "id_pengguna LIKE ? OR "
            + "nama_lengkap LIKE ? OR "
            + "email LIKE ? OR "
            + "alamat LIKE ? OR "
            + "no_hp LIKE ? OR "
            + "nama_pengguna LIKE ? OR "
            + "role LIKE ? OR "
            + "password LIKE ?)";

    try {
        Connection conn = Koneksi.getConnection(); // Pastikan method getConnection() sudah tersedia
        PreparedStatement pst = conn.prepareStatement(sql);
        for (int i = 1; i <= 8; i++) {
            pst.setString(i, "%" + keyword + "%");
        }

        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("id_pengguna"),
                rs.getString("nama_lengkap"),
                rs.getString("email"),
                rs.getString("alamat"),
                rs.getString("no_hp"),
                rs.getString("nama_pengguna"),
                rs.getString("role"),
                rs.getString("password")
            });
        }

        tb_user.setModel(model);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage());
    }
}

    public void onShow() {
        try {
            loadDataFromDatabase();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }// atau fungsi refresh data
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_user = new palette.JTable_Custom2();
        search = new palette.JTextField_Rounded();

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_pengguna.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Data Pengguna");

        btn_tambah.setContentAreaFilled(false);

        btn_tambah.setBorderPainted(false);
        btn_tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_tambah.png"))); // NOI18N
        btn_tambah.setBorder(null);
        btn_tambah.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_tambah_select.png"))); // NOI18N
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        btn_ubah.setContentAreaFilled(false);

        btn_ubah.setBorderPainted(false);
        btn_ubah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_edit.png"))); // NOI18N
        btn_ubah.setBorder(null);
        btn_ubah.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_edit_select.png"))); // NOI18N
        btn_ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubahActionPerformed(evt);
            }
        });

        btn_hapus.setContentAreaFilled(false);

        btn_hapus.setBorderPainted(false);
        btn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_hapus.png"))); // NOI18N
        btn_hapus.setBorder(null);
        btn_hapus.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_hapus_select.png"))); // NOI18N
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        tb_user.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID Katalog", "Nama Katalog"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tb_user);

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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 978, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btn_ubah, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_ubah, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        FormTambahPengguna panel = new FormTambahPengguna();
        panel.setUpdateListener(() -> {
            try {
                loadDataFromDatabase();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal memuat data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JDialog dialog = new JDialog();
        dialog.setTitle("Data Barang");
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
       int selectedRow = tb_user.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ambil semua data dari tabel
        DefaultTableModel model = (DefaultTableModel) tb_user.getModel();
        String idPengguna     = String.valueOf(model.getValueAt(selectedRow, 0));
        String nama       = String.valueOf(model.getValueAt(selectedRow, 1));
        String email      = String.valueOf(model.getValueAt(selectedRow, 2));
        String alamat     = String.valueOf(model.getValueAt(selectedRow, 3));
        String noHP       = String.valueOf(model.getValueAt(selectedRow, 4));
        String username   = String.valueOf(model.getValueAt(selectedRow, 5));
        String password   = String.valueOf(model.getValueAt(selectedRow, 7));  // indeks ke-6 adalah role (tidak digunakan)

        // Buat panel ubah
        FormUbahPengguna panel = new FormUbahPengguna();
        panel.setDataPengguna(idPengguna, nama, email, alamat, noHP, username, password);

        // Listener untuk reload tabel setelah update
        panel.setUpdateListener(() -> {
            try {
                loadDataFromDatabase();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal memuat ulang data: " + ex.getMessage());
            }
        });

        // Tampilkan JDialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Ubah Data Pengguna");
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }//GEN-LAST:event_btn_ubahActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        int PilihBaris = tb_user.getSelectedRow();
    if (PilihBaris == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Mendapatkan ID dari kolom pertama
    DefaultTableModel model = (DefaultTableModel) tb_user.getModel();
    String idUser = (String) model.getValueAt(PilihBaris, 0);

    // Konfirmasi penghapusan
    int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi", javax.swing.JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    // Hapus data dari database
    String delete = "UPDATE pengguna SET status = 'Nonaktif' WHERE id_pengguna = ?";

    try (Connection con = DriverManager.getConnection(url, user, DBpassword);
         PreparedStatement hapus = con.prepareStatement(delete)) {

        hapus.setString(1, idUser);
        int rowsAffected = hapus.executeUpdate();

        if (rowsAffected > 0) {
            // Hapus baris dari tabel
            model.removeRow(PilihBaris);
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus.", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menghapus data: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
        String keyword = search.getText();

        if (keyword.equals("Search") || keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Masukkan kata kunci pencarian.");
        }
    }//GEN-LAST:event_searchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private palette.JTextField_Rounded search;
    private palette.JTable_Custom2 tb_user;
    // End of variables declaration//GEN-END:variables
}
