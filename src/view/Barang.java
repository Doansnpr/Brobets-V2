
package view;

import event.EventMenuSwitch;
import form.FormTambahBarang;
import form.FormUbahBarang;
import java.awt.Color;
import javax.swing.JDialog;
import javax.swing.JOptionPane;


public class Barang extends javax.swing.JPanel {

   private EventMenuSwitch switchEvent;
   
    public Barang(EventMenuSwitch switchEvent) {
        this.switchEvent = switchEvent;
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

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Custom21 = new palette.JTable_Custom2();
        btn_tambah = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_barcode = new javax.swing.JButton();
        btn_rusak = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        search = new palette.JTextField_Rounded();

        setOpaque(false);

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Data Barang");

        jTable_Custom21.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable_Custom21);

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

        btn_barcode.setContentAreaFilled(false);
        btn_barcode.setBorderPainted(false);
        btn_barcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_barcode.png"))); // NOI18N
        btn_barcode.setBorder(null);
        btn_barcode.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_barcode_select.png"))); // NOI18N

        btn_rusak.setContentAreaFilled(false);
        btn_rusak.setBorderPainted(false);
        btn_rusak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_rusak.png"))); // NOI18N
        btn_rusak.setBorder(null);
        btn_rusak.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_rusak_select.png"))); // NOI18N
        btn_rusak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rusakActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_items.png"))); // NOI18N

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_ubah, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_barcode, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_rusak, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(246, 246, 246)
                            .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel2))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 984, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn_tambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_hapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_barcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_rusak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_ubah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
        String keyword = search.getText();

        if (keyword.equals("Search") || keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Masukkan kata kunci pencarian.");
        }
    }//GEN-LAST:event_searchActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        FormTambahBarang panel = new FormTambahBarang();

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
        // TODO add your handling code here:
        FormUbahBarang panel = new FormUbahBarang();

        JDialog dialog = new JDialog();
        dialog.setTitle("Data Barang");
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void btn_rusakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rusakActionPerformed
        // TODO add your handling code here:
        if (switchEvent != null) {
            switchEvent.switchPanel(new BarangRusak());
        }
    }//GEN-LAST:event_btn_rusakActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_barcode;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_rusak;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private palette.JTable_Custom2 jTable_Custom21;
    private palette.JTextField_Rounded search;
    // End of variables declaration//GEN-END:variables
}
