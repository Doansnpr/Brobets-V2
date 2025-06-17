
package view;

import form.*;


public class FormTambahKatalog extends javax.swing.JPanel {

    public FormTambahKatalog() {
        initComponents();
        
        
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
        txt_nama_katalog = new palette.JTextField_Rounded();
        jLabel8 = new javax.swing.JLabel();

        jTextField_Rounded1.setText("jTextField_Rounded1");

        pn_tambah.setBackground(new java.awt.Color(40, 70, 70));
        pn_tambah.setRoundBottomLeft(10);
        pn_tambah.setRoundBottomRight(10);
        pn_tambah.setRoundTopLeft(10);
        pn_tambah.setRoundTopRight(10);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_katalog.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Form Tambah");

        btn_simpan.setContentAreaFilled(false);

        btn_simpan.setBorderPainted(false);
        btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_simpan.png"))); // NOI18N
        btn_simpan.setBorder(null);
        btn_simpan.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_simpan_select.png"))); // NOI18N

        btn_batal.setContentAreaFilled(false);

        btn_batal.setBorderPainted(false);
        btn_batal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_batal.png"))); // NOI18N
        btn_batal.setBorder(null);
        btn_batal.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_batal_select.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nama Katalog");

        javax.swing.GroupLayout pn_tambahLayout = new javax.swing.GroupLayout(pn_tambah);
        pn_tambah.setLayout(pn_tambahLayout);
        pn_tambahLayout.setHorizontalGroup(
            pn_tambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_tambahLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(pn_tambahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                    .addComponent(txt_nama_katalog, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(txt_nama_katalog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
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
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private palette.JTextField_Rounded jTextField_Rounded1;
    private palette.JPanelRounded pn_tambah;
    private palette.JTextField_Rounded txt_nama_katalog;
    // End of variables declaration//GEN-END:variables
}
