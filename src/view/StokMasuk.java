
package view;

import java.awt.Color;
import javax.swing.JDialog;
import javax.swing.JOptionPane;


public class StokMasuk extends javax.swing.JPanel {

    public StokMasuk() {
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

        btn_tambah = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Custom21 = new palette.JTable_Custom2();
        search = new palette.JTextField_Rounded();
        jLabel3 = new javax.swing.JLabel();

        setOpaque(false);

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

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Stok Masuk");

        jTable_Custom21.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Kategori", "Quantity", "Harga Beli"
            }
        ));
        jScrollPane1.setViewportView(jTable_Custom21);

        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_stok.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jLabel2)
                        .addContainerGap(862, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 987, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(15, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(100, 100, 100)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(15, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        FormTambahStok panel = new FormTambahStok();

        JDialog dialog = new JDialog();
        dialog.setTitle("Data Stok Masuk");
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
    private javax.swing.JButton btn_tambah;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private palette.JTable_Custom2 jTable_Custom21;
    private palette.JTextField_Rounded search;
    // End of variables declaration//GEN-END:variables
}
