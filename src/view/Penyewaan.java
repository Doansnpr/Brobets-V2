
package view;

import java.awt.Color;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Penyewaan extends javax.swing.JPanel {

   
    public Penyewaan() {
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
        btn_tambah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_detail = new javax.swing.JButton();
        btn_nota = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_Custom21 = new palette.JTable_Custom2();
        jLabel3 = new javax.swing.JLabel();
        search = new palette.JTextField_Rounded();

        setOpaque(false);

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Penyewaan");

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

        btn_hapus.setContentAreaFilled(false);

        btn_hapus.setBorderPainted(false);
        btn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_hapus.png"))); // NOI18N
        btn_hapus.setBorder(null);
        btn_hapus.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_hapus_select.png"))); // NOI18N

        btn_hapus.setContentAreaFilled(false);

        btn_hapus.setBorderPainted(false);
        btn_detail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_detail.png"))); // NOI18N
        btn_detail.setBorder(null);
        btn_detail.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_detail (1).png"))); // NOI18N

        btn_hapus.setContentAreaFilled(false);

        btn_hapus.setBorderPainted(false);
        btn_nota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_nota.png"))); // NOI18N
        btn_nota.setBorder(null);
        btn_nota.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_nota_select.png"))); // NOI18N

        jTable_Custom21.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Sewa", "Tanggal Sewa", "Tanggal Rencana Kembali", "Total Harga", "Bayar", "Kembalian", "Jaminan", "Status"
            }
        ));
        jScrollPane2.setViewportView(jTable_Custom21);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_penyewaan.png"))); // NOI18N

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_detail, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_nota, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(366, 366, 366)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_tambah)
                    .addComponent(btn_detail, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_nota, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        FormTambahPenyewaan panel = new FormTambahPenyewaan();

        JDialog dialog = new JDialog();
        dialog.setTitle("Data Barang");
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
        String keyword = search.getText();

        if (keyword.equals("Search") || keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Masukkan kata kunci pencarian.");
        }
    }//GEN-LAST:event_searchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_detail;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_nota;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private palette.JTable_Custom2 jTable_Custom21;
    private palette.JTextField_Rounded search;
    // End of variables declaration//GEN-END:variables
}
