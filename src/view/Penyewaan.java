
package view;

import java.awt.Color;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Paper;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Penyewaan extends javax.swing.JPanel {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
   
   
    public Penyewaan() {
        initComponents();
        
        Koneksi DB = new Koneksi();
        DB.config();
        con = DB.con;
        

        load_table();
        
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
        
        search.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                cariPenyewaan();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                cariPenyewaan();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                cariPenyewaan();
            }
        });
    }
    
    private void cariPenyewaan() {
    String keyword = search.getText().trim();

    DefaultTableModel model = (DefaultTableModel) table_sewa.getModel();
    model.setRowCount(0); // Hanya hapus baris, jangan tambah kolom lagi!

    try {
        String sql = "SELECT penyewaan.*, pengguna.nama_pengguna, pelanggan.nama_pelanggan FROM penyewaan JOIN pelanggan " 
                + "ON penyewaan.id_pelanggan = pelanggan.id_pelanggan JOIN pengguna ON penyewaan.id_pengguna = pengguna.id_pengguna WHERE pelanggan.nama_pelanggan LIKE ?";
        pst = con.prepareStatement(sql);
        pst.setString(1, "%" + keyword + "%");
        rs = pst.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("id_sewa"),
                rs.getString("nama_pelanggan"),
                rs.getString("nama_pengguna"),
                rs.getString("tgl_sewa"),
                rs.getString("tgl_rencana_kembali"),
                rs.getString("total_harga"),
                rs.getString("bayar"),
                rs.getString("kembalian"),
                rs.getString("jaminan"),
                rs.getString("status")
            });
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage());
    }
}
    
    private void load_table() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Sewa");
        model.addColumn("Nama Penyewa");
        model.addColumn("Nama Pegawai");
        model.addColumn("Tanggal Sewa");
        model.addColumn("Tanggal Rencana Kembali");
        model.addColumn("Total Harga");
        model.addColumn("Bayar");
        model.addColumn("Kembalian");
        model.addColumn("Jaminan");
        model.addColumn("Status");

        try {
            String sql = "SELECT penyewaan.*, pengguna.nama_pengguna, pelanggan.nama_pelanggan FROM penyewaan JOIN pelanggan "
                    + "ON penyewaan.id_pelanggan = pelanggan.id_pelanggan JOIN pengguna ON penyewaan.id_pengguna = pengguna.id_pengguna ORDER BY penyewaan.id_sewa DESC;";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_sewa"),
                    rs.getString("nama_pelanggan"),
                    rs.getString("nama_pengguna"),
                    rs.getString("tgl_sewa"),
                    rs.getString("tgl_rencana_kembali"),
                    rs.getString("total_harga"),
                    rs.getString("bayar"),
                    rs.getString("kembalian"),
                    rs.getString("jaminan"),
                    rs.getString("status"),});
            }

            table_sewa.setModel(model);

            table_sewa.getColumnModel().getColumn(0).setPreferredWidth(80);
            table_sewa.getColumnModel().getColumn(1).setPreferredWidth(120);
            table_sewa.getColumnModel().getColumn(2).setPreferredWidth(120);
            table_sewa.getColumnModel().getColumn(3).setPreferredWidth(100);
            table_sewa.getColumnModel().getColumn(4).setPreferredWidth(170);
            table_sewa.getColumnModel().getColumn(5).setPreferredWidth(100);
            table_sewa.getColumnModel().getColumn(6).setPreferredWidth(80);
            table_sewa.getColumnModel().getColumn(7).setPreferredWidth(90);
            table_sewa.getColumnModel().getColumn(8).setPreferredWidth(90);
            table_sewa.getColumnModel().getColumn(9).setPreferredWidth(100);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private void hapusData() {
        int selectedRow = table_sewa.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Silakan pilih data yang ingin dihapus terlebih dahulu.",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin menghapus data ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            String idSewa = table_sewa.getValueAt(selectedRow, 0).toString();

            try {
                con = Koneksi.getConnection();

                String sqlDetail = "DELETE FROM detail_sewa WHERE id_sewa = ?";
                PreparedStatement psDetail = con.prepareStatement(sqlDetail);
                psDetail.setString(1, idSewa);
                psDetail.executeUpdate();

                String sqlSewa = "DELETE FROM penyewaan WHERE id_sewa = ?";
                PreparedStatement psSewa = con.prepareStatement(sqlSewa);
                psSewa.setString(1, idSewa);
                psSewa.executeUpdate();

                DefaultTableModel model = (DefaultTableModel) table_sewa.getModel();
                model.removeRow(selectedRow);

                JOptionPane.showMessageDialog(
                        this,
                        "Data berhasil dihapus.",
                        "Informasi",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Gagal menghapus data: " + ex.getMessage(),
                        "Kesalahan",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    private void tampilkanPreviewStruk(String isiStruk, String ucapan) {
        JFrame previewFrame = new JFrame("Preview Nota");
        previewFrame.setSize(300, 500);
        previewFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                try {
                    BufferedImage logo = ImageIO.read(getClass().getResource("/assets/logo (2).png"));
                    g2d.drawImage(logo, 90, 10, 100, 100, null);
                } catch (IOException e) {
                    g2d.drawString("Logo tidak ditemukan", 10, 20);
                }

                // Font Calibri tetap
                Font font = new Font("Courier New", Font.PLAIN, 10);
                g2d.setFont(font);
                g2d.setColor(Color.BLACK);
                FontMetrics fm = g2d.getFontMetrics();

                int y = 130;
                int lineSpacing = 13; // Konsisten jarak antar baris
                int panelWidth = getWidth();

                // Header toko
                String[] headerLines = {
                    "Jl. Gajah Mada Gg. Buntu No. 2",
                    "(Barat Bank Danamon)Jember-Jawa Timur",
                    "WA Only (No Call/SMS) 0821 3191 2829",
                    "IG : brobet_jbr | Kode Pos. 68131"
                };

                for (String line : headerLines) {
                    int textWidth = fm.stringWidth(line);
                    int x = (panelWidth - textWidth) / 2;
                    g2d.drawString(line, x, y);
                    y += lineSpacing;
                }

                y += 5; // sedikit jarak sebelum isi struk

                // Isi struk dan ucapan
                for (String line : isiStruk.split("\n")) {
                    if (line.trim().equalsIgnoreCase(ucapan.trim())) {
                        int textWidth = fm.stringWidth(line);
                        int x = (panelWidth - textWidth) / 2;
                        g2d.drawString(line, x, y);
                    } else {
                        g2d.drawString(line, 10, y);
                    }
                    y += lineSpacing;
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(280, 550);
            }
        };

        JScrollPane scrollPane = new JScrollPane(panel);
        previewFrame.add(scrollPane, BorderLayout.CENTER);

        JPanel tombolPanel = new JPanel();
        JButton btnCetak = new JButton("Cetak");
        btnCetak.addActionListener(e -> {
            previewFrame.dispose();
            cetakStruk(isiStruk, ucapan); // langsung cetak
        });
        tombolPanel.add(btnCetak);
        previewFrame.add(tombolPanel, BorderLayout.SOUTH);

        previewFrame.setVisible(true);
    }

    private void cetakStruk(String isiStruk, String ucapan) {
        try {
            BufferedImage logo = ImageIO.read(getClass().getResource("/assets/logo (2).png"));
            String[] headerLines = {
                "Jl. Gajah Mada Gg. Buntu No. 2",
                "(Barat Bank Danamon)Jember",
                "Telp. WA 082131912829",
                "IG : brobet_jbr"
            };

            PrinterJob job = PrinterJob.getPrinterJob();
            PageFormat pf = job.defaultPage();
            Paper paper = pf.getPaper();

            // Set ukuran kertas thermal 80mm = 72mm x 297mm (tinggi bebas)
            double width = 72 * 2.83;  // 1mm = 2.83 poin → 203dpi
            double height = 297 * 2.83; // tinggi kertas default A4
            double margin = 5; // kecilin margin

            paper.setSize(width, height);
            paper.setImageableArea(margin, margin, width - 2 * margin, height - 2 * margin);
            pf.setPaper(paper);
            pf.setOrientation(PageFormat.PORTRAIT);

            Printable printable = new Printable() {
                @Override
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                    if (pageIndex > 0) {
                        return NO_SUCH_PAGE;
                    }

                    Graphics2D g2d = (Graphics2D) graphics;
                    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                    Font font = new Font("Courier New", Font.PLAIN, 9);
                    g2d.setFont(font);
                    FontMetrics fm = g2d.getFontMetrics();
                    int lineSpacing = fm.getHeight() + 2;  // spasi antar baris

                    int y = 0;
                    int pageWidth = (int) pageFormat.getImageableWidth();

                    // Logo (centered)
                    int logoWidth = 80, logoHeight = 80;
                    int logoX = (pageWidth - logoWidth) / 2;
                    g2d.drawImage(logo, logoX, y, logoWidth, logoHeight, null);
                    y += logoHeight + 5;

                    // Header (centered)
                    for (String line : headerLines) {
                        int lineWidth = fm.stringWidth(line);
                        int x = (pageWidth - lineWidth) / 2;
                        g2d.drawString(line, x, y);
                        y += lineSpacing;
                    }

                    for (String line : isiStruk.split("\n")) {
                        if (line.contains(":")) {
                            String[] parts = line.split(":", 2);
                            String label = parts[0].trim();
                            String nilai = parts[1].trim();

                            int xLabel = 0;
                            int xTitik = 100;
                            int xValue = 110;

                            g2d.drawString(label, xLabel, y);
                            g2d.drawString(":", xTitik, y);
                            g2d.drawString(nilai, xValue, y);
                            y += lineSpacing;
                        } else {
                            // buat baris lain yg panjang, pakai wrap
                            y = drawWrappedLine(g2d, line, 0, y, pageWidth, fm);
                        }

                    }

                    // Ucapan (centered)
                    y += 5;  // jarak manual kecil
                    int ucapanWidth = fm.stringWidth(ucapan);
                    int xUcapan = (pageWidth - ucapanWidth) / 2;
                    g2d.drawString(ucapan, xUcapan, y);

                    return PAGE_EXISTS;
                }

                // Fungsi bantu untuk potong baris panjang
                private int drawWrappedLine(Graphics2D g2d, String text, int x, int y, int maxWidth, FontMetrics fm) {
                    String[] words = text.split(" ");
                    StringBuilder lineBuilder = new StringBuilder();

                    for (String word : words) {
                        if (fm.stringWidth(lineBuilder + word + " ") > maxWidth) {
                            g2d.drawString(lineBuilder.toString(), x, y);
                            y += fm.getHeight();
                            lineBuilder = new StringBuilder();
                        }
                        lineBuilder.append(word).append(" ");
                    }
                    if (!lineBuilder.toString().isEmpty()) {
                        g2d.drawString(lineBuilder.toString(), x, y);
                        y += fm.getHeight();
                    }

                    return y;
                }
            };

            job.setPrintable(printable, pf);

            if (job.printDialog()) {
                job.print();
                JOptionPane.showMessageDialog(null, "Nota berhasil dicetak!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mencetak: " + e.getMessage());
        }
    }
    
    public void onShow() {
        load_table(); // atau fungsi refresh data
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
        table_sewa = new palette.JTable_Custom2();
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
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        btn_detail.setContentAreaFilled(false);

        btn_detail.setBorderPainted(false);
        btn_detail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_detail.png"))); // NOI18N
        btn_detail.setBorder(null);
        btn_detail.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_detail (1).png"))); // NOI18N
        btn_detail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_detailActionPerformed(evt);
            }
        });

        btn_nota.setContentAreaFilled(false);

        btn_nota.setBorderPainted(false);
        btn_nota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_nota.png"))); // NOI18N
        btn_nota.setBorder(null);
        btn_nota.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_nota_select.png"))); // NOI18N
        btn_nota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_notaActionPerformed(evt);
            }
        });

        table_sewa.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(table_sewa);

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
        panel.setUpdateListener(() -> {
            load_table();
        });


        JDialog dialog = new JDialog();
        dialog.setTitle("Data penyewaan");
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

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
         hapusData();
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_detailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_detailActionPerformed
        // TODO add your handling code here:
//        
         int selectedRow = table_sewa.getSelectedRow();
        if (selectedRow != -1) {
            String idSewa = table_sewa.getValueAt(selectedRow, 0).toString();

            PanelDetailSewa panel = new PanelDetailSewa(idSewa);

            JDialog dialog = new JDialog();
            dialog.setTitle("Detail Sewa");
            dialog.setModal(true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.getContentPane().add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data penyewaan terlebih dahulu.");
        }

    }//GEN-LAST:event_btn_detailActionPerformed

    private void btn_notaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_notaActionPerformed
        // TODO add your handling code here:
         int selectedRow = table_sewa.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data penyewaan yang ingin dicetak.");
            return;
        }

        try {
            String idSewa = table_sewa.getValueAt(selectedRow, 0).toString();

            // Ambil data utama penyewaan dan pelanggan
            String sqlSewa = "SELECT p.tgl_sewa, p.tgl_rencana_kembali, pl.nama_pelanggan, pl.no_hp, p.jaminan "
                    + "FROM penyewaan p JOIN pelanggan pl ON p.id_pelanggan = pl.id_pelanggan "
                    + "WHERE p.id_sewa = ?";
            PreparedStatement psSewa = con.prepareStatement(sqlSewa);
            psSewa.setString(1, idSewa);
            ResultSet rsSewa = psSewa.executeQuery();

            if (!rsSewa.next()) {
                JOptionPane.showMessageDialog(this, "Data penyewaan tidak ditemukan.");
                return;
            }

            String tglPinjam = rsSewa.getString("tgl_sewa");
            String tglKembali = rsSewa.getString("tgl_rencana_kembali");
            String nama = rsSewa.getString("nama_pelanggan");
            String noHp = rsSewa.getString("no_hp");
            String jaminan = rsSewa.getString("jaminan");

            // Ambil detail barang sewa
            String sqlDetail = "SELECT b.nama_barang, ds.qty, b.harga_sewa "
                    + "FROM detail_sewa ds JOIN barang b ON ds.id_barang = b.id_barang "
                    + "WHERE ds.id_sewa = ?";
            PreparedStatement psDetail = con.prepareStatement(sqlDetail);
            psDetail.setString(1, idSewa);
            ResultSet rsDetail = psDetail.executeQuery();

            int total = 0;
            StringBuilder detailBarang = new StringBuilder();
            detailBarang.append(String.format("%-15s %6s %12s\n", "Nama", "Jumlah", "Subtotal"));
            detailBarang.append("-------------------------------------------\n");

            while (rsDetail.next()) {
                String namaBarang = rsDetail.getString("nama_barang");
                int harga = rsDetail.getInt("harga_sewa");
                int qty = rsDetail.getInt("qty");
                int subTotal = qty * harga;
                total += subTotal;
                int sub = subTotal;

                String namaPendek = namaBarang.length() > 15 ? namaBarang.substring(0, 15) : namaBarang;
                detailBarang.append(String.format("%-13s %7d %12s\n", namaPendek, qty, String.format("Rp%,d", sub)));
            }

            // Ambil data bayar dan kembalian
            String sqlBayar = "SELECT bayar, kembalian FROM penyewaan WHERE id_sewa = ?";
            PreparedStatement psBayar = con.prepareStatement(sqlBayar);
            psBayar.setString(1, idSewa);
            ResultSet rsBayar = psBayar.executeQuery();

            String bayar = "Rp0", kembalian = "Rp0";
            if (rsBayar.next()) {
                bayar = String.format("Rp%,d", rsBayar.getInt("bayar"));
                kembalian = String.format("Rp%,d", rsBayar.getInt("kembalian"));
            }

            StringBuilder infoPelanggan = new StringBuilder();
            infoPelanggan.append("===========================================\n");
            infoPelanggan.append(String.format("%-13s : %s\n", "ID Penyewaan", idSewa));
            infoPelanggan.append(String.format("%-13s : %s\n", "Nama", nama));
            infoPelanggan.append(String.format("%-13s : %s\n", "No HP", noHp));
            infoPelanggan.append(String.format("%-13s : %s\n", "Tgl Pinjam", tglPinjam));
            infoPelanggan.append(String.format("%-13s : %s\n", "Tgl Kembali", tglKembali));
            infoPelanggan.append(String.format("%-13s : %s\n", "Jaminan", jaminan));

            infoPelanggan.append("-------------------------------------------\n");

            StringBuilder isiStruk = new StringBuilder();
            isiStruk.append("BARANG SEWA:\n");
            isiStruk.append(detailBarang);
            isiStruk.append("-------------------------------------------\n");
            isiStruk.append(String.format("%-13s : Rp%,d\n", "Total", total));
            isiStruk.append(String.format("%-13s : %s\n", "Bayar", bayar));
            isiStruk.append(String.format("%-13s : %s\n", "Kembalian", kembalian));
            isiStruk.append(String.format("%-13s : %s\n", "Kasir", Login.Session.getUsername()));
            isiStruk.append("===========================================\n\n");

            String ucapan = "TERIMAKASIH SUDAH MENYEWA!";
            StringBuilder previewStruk = new StringBuilder();
            previewStruk.append(infoPelanggan);
            previewStruk.append(isiStruk);

            // Tampilkan preview
            tampilkanPreviewStruk(previewStruk.toString(), ucapan);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menampilkan nota: " + e.getMessage());
        }
    }//GEN-LAST:event_btn_notaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_detail;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_nota;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private palette.JTextField_Rounded search;
    private palette.JTable_Custom2 table_sewa;
    // End of variables declaration//GEN-END:variables
}
