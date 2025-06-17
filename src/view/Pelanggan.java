
package view;

import java.awt.Color;
import javax.swing.JOptionPane;

public class Pelanggan extends javax.swing.JPanel {

 
    public Pelanggan() {
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
        btn_aktif = new javax.swing.JButton();
        btn_nonaktif = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        search = new palette.JTextField_Rounded();

        setOpaque(false);

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Data Pelanggan");

        jTable_Custom21.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nama Pelanggan", "Nomer Telepon", "Poin", "Status"
            }
        ));
        jScrollPane1.setViewportView(jTable_Custom21);

        btn_aktif.setContentAreaFilled(false);

        btn_aktif.setBorderPainted(false);
        btn_aktif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_aktif.png"))); // NOI18N
        btn_aktif.setBorder(null);
        btn_aktif.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_aktif_select.png"))); // NOI18N

        btn_nonaktif.setContentAreaFilled(false);

        btn_nonaktif.setBorderPainted(false);
        btn_nonaktif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_nonaktif.png"))); // NOI18N
        btn_nonaktif.setBorder(null);
        btn_nonaktif.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_nonaktif_select.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_pelanggan.png"))); // NOI18N

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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 984, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addGap(6, 6, 6))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_aktif, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_nonaktif, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_aktif)
                    .addComponent(btn_nonaktif))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        search.getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
        String keyword = search.getText();

        if (keyword.equals("Search") || keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Masukkan kata kunci pencarian.");
        }
    }//GEN-LAST:event_searchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_aktif;
    private javax.swing.JButton btn_nonaktif;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private palette.JTable_Custom2 jTable_Custom21;
    private palette.JTextField_Rounded search;
    // End of variables declaration//GEN-END:variables
}
