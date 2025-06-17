
package view;


import java.awt.Color;
import javax.swing.JOptionPane;


public class FormTambahPenyewaan extends javax.swing.JPanel {

    public FormTambahPenyewaan() {
        initComponents();
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
        
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.raven.datechooser.DateChooser();
        dateChooser2 = new com.raven.datechooser.DateChooser();
        pn_data_penyewa = new palette.JPanelRounded();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btn_lanjut = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txt_nama = new palette.JTextField_Rounded();
        jLabel9 = new javax.swing.JLabel();
        tgl_sewa = new palette.JTextField_Rounded();
        jLabel10 = new javax.swing.JLabel();
        tgl_kembali = new palette.JTextField_Rounded();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_telp = new palette.JTextField_Rounded();
        jPanelRounded1 = new palette.JPanelRounded();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cek_sim = new javax.swing.JRadioButton();
        cek_ktp = new javax.swing.JRadioButton();
        cek_ktm = new javax.swing.JRadioButton();
        cek_fckk = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btn_calender = new javax.swing.JButton();
        btn_calender2 = new javax.swing.JButton();
        pn_barang_sewa = new palette.JPanelRounded();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        txt_barcode = new palette.JTextField_Rounded();
        jLabel44 = new javax.swing.JLabel();
        txt_nama_brg = new palette.JTextField_Rounded();
        jLabel45 = new javax.swing.JLabel();
        txt_jumlah = new palette.JTextField_Rounded();
        search = new palette.JTextField_Rounded();
        btn_search = new javax.swing.JButton();
        jPanelRounded5 = new palette.JPanelRounded();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Custom21 = new palette.JTable_Custom2();
        jLabel46 = new javax.swing.JLabel();
        txt_bayar = new palette.JTextField_Rounded();
        jLabel47 = new javax.swing.JLabel();
        txt_total = new palette.JTextField_Rounded();
        jLabel48 = new javax.swing.JLabel();
        txt_kembalian = new palette.JTextField_Rounded();
        btn_simpan = new javax.swing.JButton();
        btn_kembali = new javax.swing.JButton();

        dateChooser1.setForeground(new java.awt.Color(0, 102, 102));
        dateChooser1.setTextRefernce(tgl_sewa);

        dateChooser2.setForeground(new java.awt.Color(0, 102, 102));
        dateChooser2.setTextRefernce(tgl_kembali);

        setLayout(new java.awt.CardLayout());

        pn_data_penyewa.setBackground(new java.awt.Color(40, 70, 70));
        pn_data_penyewa.setRoundBottomLeft(10);
        pn_data_penyewa.setRoundBottomRight(10);
        pn_data_penyewa.setRoundTopLeft(10);
        pn_data_penyewa.setRoundTopRight(10);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_penyewaan.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Data Penyewa");

        btn_lanjut.setContentAreaFilled(false);

        btn_lanjut.setBorderPainted(false);
        btn_lanjut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_lanjut.png"))); // NOI18N
        btn_lanjut.setBorder(null);
        btn_lanjut.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_lanjut_select.png"))); // NOI18N
        btn_lanjut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lanjutActionPerformed(evt);
            }
        });

        btn_batal.setContentAreaFilled(false);

        btn_batal.setBorderPainted(false);
        btn_batal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_batal.png"))); // NOI18N
        btn_batal.setBorder(null);
        btn_batal.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_batal_select.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nama Penyewa");

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Tanggal Sewa");

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Tanggal Rencana Kembali");

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("No. Telepon");

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Jaminan");

        jPanelRounded1.setBackground(new java.awt.Color(57, 121, 121));
        jPanelRounded1.setRoundBottomLeft(10);
        jPanelRounded1.setRoundBottomRight(10);
        jPanelRounded1.setRoundTopLeft(10);
        jPanelRounded1.setRoundTopRight(10);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/syarat dan ketentuan fix.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Syarat dan Ketentuan Berlaku :");

        javax.swing.GroupLayout jPanelRounded1Layout = new javax.swing.GroupLayout(jPanelRounded1);
        jPanelRounded1.setLayout(jPanelRounded1Layout);
        jPanelRounded1Layout.setHorizontalGroup(
            jPanelRounded1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRounded1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanelRounded1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        jPanelRounded1Layout.setVerticalGroup(
            jPanelRounded1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRounded1Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        cek_sim.setText("SIM");
        cek_sim.setBorder(null);

        cek_ktp.setText("KTP");
        cek_ktp.setBorder(null);

        cek_ktm.setText("KTM");
        cek_ktm.setBorder(null);

        cek_fckk.setText("FC KK");
        cek_fckk.setBorder(null);

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("SIM");

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("KTP");

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("KTM");

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("FC. KK");

        btn_calender.setContentAreaFilled(false);

        btn_calender.setBorderPainted(false);
        btn_calender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Button Calender.png"))); // NOI18N
        btn_calender.setBorder(null);
        btn_calender.setContentAreaFilled(false);
        btn_calender.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Button Calender Select.png"))); // NOI18N
        btn_calender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_calenderActionPerformed(evt);
            }
        });

        btn_calender.setContentAreaFilled(false);

        btn_calender.setBorderPainted(false);
        btn_calender2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Button Calender.png"))); // NOI18N
        btn_calender2.setBorder(null);
        btn_calender2.setContentAreaFilled(false);
        btn_calender2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Button Calender Select.png"))); // NOI18N
        btn_calender2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_calender2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_data_penyewaLayout = new javax.swing.GroupLayout(pn_data_penyewa);
        pn_data_penyewa.setLayout(pn_data_penyewaLayout);
        pn_data_penyewaLayout.setHorizontalGroup(
            pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_data_penyewaLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_data_penyewaLayout.createSequentialGroup()
                        .addGroup(pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(pn_data_penyewaLayout.createSequentialGroup()
                                .addGroup(pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addGroup(pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_nama, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                                        .addComponent(tgl_sewa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_calender))
                            .addComponent(jLabel10)
                            .addGroup(pn_data_penyewaLayout.createSequentialGroup()
                                .addComponent(tgl_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_calender2))
                            .addGroup(pn_data_penyewaLayout.createSequentialGroup()
                                .addComponent(cek_sim, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cek_ktp, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cek_ktm, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cek_fckk, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jLabel13))
                            .addComponent(jLabel12)
                            .addComponent(txt_telp, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pn_data_penyewaLayout.createSequentialGroup()
                                .addComponent(btn_lanjut, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel11))
                        .addGap(17, 17, 17))
                    .addGroup(pn_data_penyewaLayout.createSequentialGroup()
                        .addGroup(pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pn_data_penyewaLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanelRounded1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        pn_data_penyewaLayout.setVerticalGroup(
            pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_data_penyewaLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(33, 33, 33)
                .addGroup(pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelRounded1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pn_data_penyewaLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tgl_sewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_calender))
                        .addGap(7, 7, 7)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tgl_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_calender2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addGap(0, 0, 0)
                        .addComponent(txt_telp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cek_ktp, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cek_ktm, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cek_sim, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cek_fckk, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_batal)
                            .addComponent(btn_lanjut))))
                .addGap(16, 16, 16))
        );

        add(pn_data_penyewa, "card2");

        pn_barang_sewa.setBackground(new java.awt.Color(40, 70, 70));
        pn_barang_sewa.setRoundBottomLeft(10);
        pn_barang_sewa.setRoundBottomRight(10);
        pn_barang_sewa.setRoundTopLeft(10);
        pn_barang_sewa.setRoundTopRight(10);

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_penyewaan.png"))); // NOI18N

        jLabel42.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Barang Sewa");

        btn_lanjut.setContentAreaFilled(false);

        btn_lanjut.setBorderPainted(false);
        btn_tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_tambah.png"))); // NOI18N
        btn_tambah.setBorder(null);
        btn_tambah.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_tambah_select.png"))); // NOI18N

        btn_batal.setContentAreaFilled(false);

        btn_batal.setBorderPainted(false);
        btn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_hapus.png"))); // NOI18N
        btn_hapus.setBorder(null);
        btn_hapus.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_hapus_select.png"))); // NOI18N

        jLabel43.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Barcode");

        jLabel44.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Nama Barang");

        jLabel45.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Jumlah");

        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        jPanelRounded5.setBackground(new java.awt.Color(46, 97, 97));
        jPanelRounded5.setRoundBottomLeft(10);
        jPanelRounded5.setRoundBottomRight(10);
        jPanelRounded5.setRoundTopLeft(10);
        jPanelRounded5.setRoundTopRight(10);

        jTable_Custom21.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable_Custom21);

        javax.swing.GroupLayout jPanelRounded5Layout = new javax.swing.GroupLayout(jPanelRounded5);
        jPanelRounded5.setLayout(jPanelRounded5Layout);
        jPanelRounded5Layout.setHorizontalGroup(
            jPanelRounded5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRounded5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanelRounded5Layout.setVerticalGroup(
            jPanelRounded5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRounded5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel46.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Bayar");

        jLabel47.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Total Harga");

        jLabel48.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Kembalian");

        btn_lanjut.setContentAreaFilled(false);

        btn_lanjut.setBorderPainted(false);
        btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_simpan.png"))); // NOI18N
        btn_simpan.setBorder(null);
        btn_simpan.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_simpan_select.png"))); // NOI18N

        btn_batal.setContentAreaFilled(false);

        btn_batal.setBorderPainted(false);
        btn_kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_kembali.png"))); // NOI18N
        btn_kembali.setBorder(null);
        btn_kembali.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_kembali_select.png"))); // NOI18N

        javax.swing.GroupLayout pn_barang_sewaLayout = new javax.swing.GroupLayout(pn_barang_sewa);
        pn_barang_sewa.setLayout(pn_barang_sewaLayout);
        pn_barang_sewaLayout.setHorizontalGroup(
            pn_barang_sewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pn_barang_sewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                        .addGroup(pn_barang_sewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel42)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                        .addGroup(pn_barang_sewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                                .addGroup(pn_barang_sewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel46)
                                    .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                                        .addComponent(txt_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pn_barang_sewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel47)
                                            .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pn_barang_sewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel48)
                                    .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                                        .addComponent(txt_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                                .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pn_barang_sewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel43)
                                    .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                                        .addComponent(txt_barcode, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pn_barang_sewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel44)
                                            .addComponent(txt_nama_brg, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pn_barang_sewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel45)
                                    .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                                        .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jPanelRounded5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pn_barang_sewaLayout.setVerticalGroup(
            pn_barang_sewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pn_barang_sewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_barang_sewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_barcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addGap(38, 38, 38))
                    .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_barang_sewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_tambah)
                            .addComponent(btn_hapus)
                            .addGroup(pn_barang_sewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_nama_brg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelRounded5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_barang_sewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                        .addComponent(jLabel46)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addGap(38, 38, 38))
                    .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                        .addComponent(jLabel48)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_barang_sewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_simpan)
                            .addComponent(btn_kembali)
                            .addGroup(pn_barang_sewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        add(pn_barang_sewa, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btn_calenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_calenderActionPerformed
        // TODO add your handling code here:
       dateChooser1.showPopup();
    }//GEN-LAST:event_btn_calenderActionPerformed

    private void btn_calender2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_calender2ActionPerformed
        // TODO add your handling code here:
        dateChooser2.showPopup();
    }//GEN-LAST:event_btn_calender2ActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
        String keyword = search.getText();

        if (keyword.equals("Search") || keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Masukkan kata kunci pencarian.");
        }
    }//GEN-LAST:event_searchActionPerformed

    private void btn_lanjutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lanjutActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btn_lanjutActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_calender;
    private javax.swing.JButton btn_calender2;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_kembali;
    private javax.swing.JButton btn_lanjut;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JRadioButton cek_fckk;
    private javax.swing.JRadioButton cek_ktm;
    private javax.swing.JRadioButton cek_ktp;
    private javax.swing.JRadioButton cek_sim;
    private com.raven.datechooser.DateChooser dateChooser1;
    private com.raven.datechooser.DateChooser dateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private palette.JPanelRounded jPanelRounded1;
    private palette.JPanelRounded jPanelRounded5;
    private javax.swing.JScrollPane jScrollPane1;
    private palette.JTable_Custom2 jTable_Custom21;
    private palette.JPanelRounded pn_barang_sewa;
    private palette.JPanelRounded pn_data_penyewa;
    private palette.JTextField_Rounded search;
    private palette.JTextField_Rounded tgl_kembali;
    private palette.JTextField_Rounded tgl_sewa;
    private palette.JTextField_Rounded txt_barcode;
    private palette.JTextField_Rounded txt_bayar;
    private palette.JTextField_Rounded txt_jumlah;
    private palette.JTextField_Rounded txt_kembalian;
    private palette.JTextField_Rounded txt_nama;
    private palette.JTextField_Rounded txt_nama_brg;
    private palette.JTextField_Rounded txt_telp;
    private palette.JTextField_Rounded txt_total;
    // End of variables declaration//GEN-END:variables
}
