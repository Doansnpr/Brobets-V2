
package view;


import event.BarangUpdateListener;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.SwingUtilities;

public class FormTambahPengguna extends javax.swing.JPanel {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    private BarangUpdateListener updateListener;
    
    public FormTambahPengguna() {
        initComponents();
        
        txt_password.setEchoChar((char) 0); 
        
        Koneksi DB = new Koneksi();
        DB.config();
        con = DB.con;
        
        
  
    }
    
    public void setUpdateListener(BarangUpdateListener listener) {
        this.updateListener = listener;
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

    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField_Rounded1 = new palette.JTextField_Rounded();
        pn_tambah = new palette.JPanelRounded();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btn_simpan = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        txt_nama = new palette.JTextField_Rounded();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_telp = new palette.JTextField_Rounded();
        jLabel10 = new javax.swing.JLabel();
        txt_email = new palette.JTextField_Rounded();
        jLabel11 = new javax.swing.JLabel();
        txt_alamat = new palette.JTextField_Rounded();
        jLabel12 = new javax.swing.JLabel();
        txt_username = new palette.JTextField_Rounded();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        rbAdmin = new javax.swing.JRadioButton();
        rbPegawai = new javax.swing.JRadioButton();
        btn_password = new javax.swing.JCheckBox();
        txt_password = new javax.swing.JPasswordField();

        jTextField_Rounded1.setText("jTextField_Rounded1");

        pn_tambah.setBackground(new java.awt.Color(40, 70, 70));
        pn_tambah.setRoundBottomLeft(10);
        pn_tambah.setRoundBottomRight(10);
        pn_tambah.setRoundTopLeft(10);
        pn_tambah.setRoundTopRight(10);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_pemasok.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Form Tambah");

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
        jLabel8.setText("Nama ");

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("No. Telepon");

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Email");

        txt_email.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_emailKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Alamat");

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Username");

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Password");

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Role");

        rbAdmin.setForeground(new java.awt.Color(255, 244, 232));
        rbAdmin.setText("Owner");
        rbAdmin.setBorder(null);
        rbAdmin.setContentAreaFilled(false);
        rbAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbAdminActionPerformed(evt);
            }
        });

        rbPegawai.setForeground(new java.awt.Color(255, 244, 232));
        rbPegawai.setText("Pegawai");
        rbPegawai.setBorder(null);
        rbPegawai.setContentAreaFilled(false);

        btn_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_passwordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_tambahLayout = new javax.swing.GroupLayout(pn_tambah);
        pn_tambah.setLayout(pn_tambahLayout);
        pn_tambahLayout.setHorizontalGroup(
            pn_tambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_tambahLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(pn_tambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_username, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addComponent(txt_alamat, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addComponent(txt_email, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addComponent(txt_telp, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addComponent(txt_nama, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_tambahLayout.createSequentialGroup()
                        .addComponent(txt_password)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_password)
                        .addGap(13, 13, 13))
                    .addGroup(pn_tambahLayout.createSequentialGroup()
                        .addComponent(rbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel14)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
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
                    .addComponent(jLabel13))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_telp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_tambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_password, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_password, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_tambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
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
        String nama, email, no_hp, alamat, username, password, role = null, save;
    
    String idUser = generateIdOtomatis();
    nama = txt_nama.getText();
    email = txt_email.getText();
    no_hp = txt_telp.getText();
    alamat = txt_alamat.getText();
    username = txt_username.getText();
    password = new String(txt_password.getPassword());
    if (rbAdmin.isSelected()) {
    role = "admin";
    } else if (rbPegawai.isSelected()) {
    role = "pegawai";
    }
        
    if (
        nama.isEmpty() || nama.equals("Masukkan Nama Lengkap") ||
        email.isEmpty() || email.equals("Masukkan Email") ||
        no_hp.isEmpty() || no_hp.equals("Masukkan Nomor Telepon") ||
        alamat.isEmpty() || alamat.equals("Masukkan Alamat") ||
        username.isEmpty() || username.equals("Masukkan Username") ||
        password.isEmpty() || password.equals("Masukkan Password")
    ) {
        JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    save = "INSERT INTO pengguna (id_pengguna, nama_lengkap, email, alamat, no_hp, nama_pengguna, password, role) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    try {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/brobets", "root", "");
        String CekUsername = "SELECT * FROM pengguna WHERE nama_pengguna = ?"; // cek usesrname
        PreparedStatement cekuser = con.prepareStatement(CekUsername);
        cekuser.setString(1, username);
        ResultSet rs = cekuser.executeQuery();

        if (rs.next()) {
            JOptionPane.showMessageDialog(this, "Username sudah digunakan, silakan pilih yang lain", "Error", JOptionPane.ERROR_MESSAGE);
            return; // berhrnti nyimpen
        }
        PreparedStatement simpan = con.prepareStatement(save);
        simpan.setString(1, idUser);
        simpan.setString(2, nama);
        simpan.setString(3, email);
        simpan.setString(4, alamat);
        simpan.setString(5, no_hp);
        simpan.setString(6, username);
        simpan.setString(7, password);
        simpan.setString(8, role);
   
        int result = simpan.executeUpdate();
        
        if (result > 0 ) {
            JOptionPane.showMessageDialog(this, "Pengguna berhasil ditambahkan", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            if (updateListener != null) {
            updateListener.onBarangUpdated();
        }

        SwingUtilities.getWindowAncestor(this).dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan pengguna!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            con.close();
        
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Database Error" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
                                        

    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        SwingUtilities.getWindowAncestor(this).dispose();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void txt_emailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_emailKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '\b') {
            evt.consume(); // mencegah karakter non-angka diketik
        }
    
    }//GEN-LAST:event_txt_emailKeyTyped

    private void rbAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAdminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbAdminActionPerformed

    private void btn_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_passwordActionPerformed
        if (btn_password.isSelected()) {
            txt_password.setEchoChar((char) 0); // Menampilkan password
        } else {
            txt_password.setEchoChar('•'); // Menyembunyikan password
        }
    }//GEN-LAST:event_btn_passwordActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JCheckBox btn_password;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private palette.JTextField_Rounded jTextField_Rounded1;
    private palette.JPanelRounded pn_tambah;
    private javax.swing.JRadioButton rbAdmin;
    private javax.swing.JRadioButton rbPegawai;
    private palette.JTextField_Rounded txt_alamat;
    private palette.JTextField_Rounded txt_email;
    private palette.JTextField_Rounded txt_nama;
    private javax.swing.JPasswordField txt_password;
    private palette.JTextField_Rounded txt_telp;
    private palette.JTextField_Rounded txt_username;
    // End of variables declaration//GEN-END:variables
}
