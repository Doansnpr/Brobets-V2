
package view;

import event.BarangUpdateListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import view.Koneksi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.awt.print.*;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import view.FormTambahPengembalian;


public class Pengembalian extends javax.swing.JPanel {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    private final List<Integer> originalQuantities = new ArrayList<>();
    private final List<String> listIdKembali = new ArrayList<>();
    
    public Pengembalian() {
        initComponents();
        
        tampilDataPenyewaan();
        
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
    
public void tampilDataPenyewaan() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Sewa");
        model.addColumn("Nama Penyewa");
        model.addColumn("Tgl Sewa");
        model.addColumn("Tgl Rencana Kembali");
        model.addColumn("Jaminan");

        try {
            String sql = "SELECT p.id_sewa, pl.nama_pelanggan, p.tgl_sewa, p.tgl_rencana_kembali, p.jaminan "
                    + "FROM penyewaan p "
                    + "JOIN pelanggan pl ON p.id_pelanggan = pl.id_pelanggan "
                    + "WHERE p.Status != 'Sudah Kembali'";

            Koneksi.config();
            con = Koneksi.getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_sewa"),
                    rs.getString("nama_pelanggan"),
                    rs.getDate("tgl_sewa"),
                    rs.getDate("tgl_rencana_kembali"),
                    rs.getString("jaminan")
                });
            }

            table_kembali.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menampilkan data: " + e.getMessage());
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_kembali = new palette.JTable_Custom2();
        btn_retur = new javax.swing.JButton();
        btn_riwayat = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        search = new palette.JTextField_Rounded();

        setOpaque(false);

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Pengembalian");

        table_kembali.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Sewa", "Tanggal Kembali", "Status", "Denda Keterlambatan", "Total Denda", "Bayar", "Kembalian"
            }
        ));
        jScrollPane1.setViewportView(table_kembali);

        btn_retur.setContentAreaFilled(false);

        btn_retur.setBorderPainted(false);
        btn_retur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_retur.png"))); // NOI18N
        btn_retur.setBorder(null);
        btn_retur.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_retur_select.png"))); // NOI18N
        btn_retur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_returActionPerformed(evt);
            }
        });

        btn_riwayat.setContentAreaFilled(false);

        btn_riwayat.setBorderPainted(false);
        btn_riwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_riwayat.png"))); // NOI18N
        btn_riwayat.setBorder(null);
        btn_riwayat.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_riwayat_select.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_pengembalian.png"))); // NOI18N

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
                .addGap(20, 20, 20)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(btn_retur, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_riwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 984, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_riwayat)
                            .addComponent(btn_retur, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
        String keyword = search.getText();

        if (keyword.equals("Search") || keyword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Masukkan kata kunci pencarian.");
        }
    }//GEN-LAST:event_searchActionPerformed

    private void btn_returActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_returActionPerformed
        // TODO add your handling code here:
        int selectedRow = table_kembali.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Harap pilih data yang ingin diretur!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idSewa = table_kembali.getValueAt(selectedRow, 0).toString();
        String namaPelanggan = table_kembali.getValueAt(selectedRow, 1).toString();
        String tglRencanaKembali = table_kembali.getValueAt(selectedRow, 3).toString();
        String jaminan = table_kembali.getValueAt(selectedRow, 4).toString();

        LocalDate tglHariIni = LocalDate.now();
        LocalDate tglRencana = LocalDate.parse(tglRencanaKembali);

        long selisihHari = ChronoUnit.DAYS.between(tglRencana, tglHariIni);
        String status;
        int denda;

        if (selisihHari > 0) {
            status = "Terlambat";
            denda = (int) selisihHari * 10000;
        } else {
            status = "Tepat Waktu";
            denda = 0;
        }

        // Buat dan tampilkan dialog
        FormTambahPengembalian panel = new FormTambahPengembalian();

        // Kirim data ke panel (pastikan panel punya setter)
        panel.setDataRetur(idSewa, namaPelanggan, tglHariIni.toString(), status, String.valueOf(denda), jaminan);
        panel.setUpdateListener(() -> {
            tampilDataPenyewaan();
        });
       
        JDialog dialog = new JDialog();
        dialog.setTitle("Form Retur Penyewaan");
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }//GEN-LAST:event_btn_returActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_retur;
    private javax.swing.JButton btn_riwayat;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private palette.JTextField_Rounded search;
    private palette.JTable_Custom2 table_kembali;
    // End of variables declaration//GEN-END:variables
}
