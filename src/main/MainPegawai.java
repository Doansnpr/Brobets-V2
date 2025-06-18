
package main;

import event.EventMenuSwitch;
import event.MenuEvent;
import java.awt.Color;
import java.awt.Component;
import view.Barang;
import view.DashPegawai;
import view.Katalog;
import view.Laporan;
import view.Login;
import view.Login.Session;
import view.Pelanggan;
import view.Pemasok;
import view.Pengembalian;
import view.Pengguna;
import view.Penyewaan;
import view.StokMasuk;

public class MainPegawai extends javax.swing.JFrame {
    
    String nama = Session.getNamaPengguna();
    String role = Session.getRole();
    
    private DashPegawai dashPanel;
    private Barang barangPanel;
    private Katalog katalogPanel;
    private Pelanggan pelangganPanel;
    private Pemasok pemasokPanel;
    private StokMasuk stokMasukPanel;
    private Penyewaan penyewaanPanel;
    private Pengembalian pengembalianPanel;
    private Pengguna penggunaPanel;
    private Laporan laporanPanel;
    
    public MainPegawai(String nama, String role) {
    initComponents();
    getContentPane().setBackground(new Color(63, 109, 217));

    EventMenuSwitch switchEvent = new EventMenuSwitch() {
        @Override
        public void switchPanel(Component component) {
            showMenu(component);
        }
    };

    dashPanel = new DashPegawai();
    barangPanel = new Barang(switchEvent);
    katalogPanel = new Katalog();
    pelangganPanel = new Pelanggan();
    pemasokPanel = new Pemasok();
    stokMasukPanel = new StokMasuk();
    penyewaanPanel = new Penyewaan();
    pengembalianPanel = new Pengembalian();
    penggunaPanel = new Pengguna();
    laporanPanel = new Laporan();

    // Buat variabel event dulu
    MenuEvent event = new MenuEvent() {
        @Override
        public void menuSelected(int index) {
            if (role.equalsIgnoreCase("pegawai")) {
                switch (index) {
                    case 0 -> showMenu(dashPanel);
                    case 1 -> showMenu(barangPanel);
                    case 2 -> showMenu(katalogPanel);
                    case 3 -> showMenu(pelangganPanel);
                    case 4 -> showMenu(pemasokPanel);
                    case 5 -> showMenu(stokMasukPanel);
                    case 6 -> showMenu(penyewaanPanel);
                    case 7 -> showMenu(pengembalianPanel);
                }
            } else if (role.equalsIgnoreCase("owner")) {
                switch (index) {
                    case 8 -> showMenu(penggunaPanel);
                    case 9 -> showMenu(laporanPanel);
                }
            }

            if (index == 99) {
                dispose(); // logout
                new Login().setVisible(true);
            }
        }
    };

    // Urutan yang benar:
    menu.setUserLevel(role);  // <-- Set role dulu
    menu.initMenu(event);     // <-- lalu inisialisasi menu berdasarkan role

    // Pilih menu awal tergantung role
    if (role.equalsIgnoreCase("owner")) {
        menu.setSelected(8);  // buka menu Pengguna
    } else {
        menu.setSelected(0);  // buka Dashboard
    }
}


 
    public Barang getBarangPanel() {
        return barangPanel;
    }

    public void setPanel(Component c) {
        getContentPane().removeAll();
        getContentPane().add(c);
        getContentPane().revalidate();
        getContentPane().repaint();
    }
    
    private void showMenu(Component com) {
        content.removeAll();
        content.add(com);
        content.revalidate();
        content.repaint();
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        menu = new component.Menu();
        content = new palette.JPanelRounded();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(29, 51, 51));

        content.setBackground(new java.awt.Color(40, 70, 70));
        content.setRoundBottomLeft(20);
        content.setRoundBottomRight(20);
        content.setRoundTopLeft(20);
        content.setRoundTopRight(20);
        content.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(content, javax.swing.GroupLayout.PREFERRED_SIZE, 1018, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addComponent(content, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                 new MainPegawai(Session.getNamaPengguna(), Session.getRole()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private palette.JPanelRounded content;
    private javax.swing.JPanel jPanel1;
    private component.Menu menu;
    // End of variables declaration//GEN-END:variables
}
