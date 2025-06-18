
package view;

import event.BarangUpdateListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

public class FormTambahPengembalian extends javax.swing.JPanel {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    private BarangUpdateListener updateListener;
    private String idPengembalianYangSedangDiedit;
    
     private final List<Integer> originalQuantities = new ArrayList<>();
    private final List<String> listIdKembali = new ArrayList<>();

    
    public FormTambahPengembalian() {
        initComponents();
        
        Koneksi DB = new Koneksi();
        DB.config();
        con = DB.con;
        
        total_denda.setText("Rp 0");
        denda_kerusakan.setText("Rp 0");
    }

    public void setUpdateListener(BarangUpdateListener listener) {
        this.updateListener = listener;
    }
    
    public void setDataRetur(String idSewa, String namaPelanggan, String tglKembali, String status, String denda, String jaminan) {
        this.idPengembalianYangSedangDiedit = idSewa; // Jika kamu ingin simpan ID transaksi untuk keperluan update/simpan

        ID_transaksi.setText(idSewa);
        txt_nama_penyewa.setText(namaPelanggan);
        tgl_kembali.setText(tglKembali);
        txt_status.setText(status);
        denda_terlambat.setText(denda);
        txt_jaminan.setText(jaminan);
    }


    public void loadBarangKembali(String idSewa) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
               return column == 4;
            }
        };

        model.addColumn("ID Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Jumlah Disewa");
        model.addColumn("Jumlah Kembali");
        model.addColumn("Kondisi");

        originalQuantities.clear();

        try {
            Koneksi.config();
            con = Koneksi.getConnection();

            String sql = "SELECT ds.id_barang, b.nama_barang, ds.qty "
                       + "FROM detail_sewa ds "
                       + "JOIN barang b ON ds.id_barang = b.id_barang "
                       + "WHERE ds.id_sewa = ?";

            pst = con.prepareStatement(sql);
            pst.setString(1, idSewa);
            rs = pst.executeQuery();

            while (rs.next()) {
                String idBarang = rs.getString("id_barang");
                String namaBarang = rs.getString("nama_barang");
                int qty = rs.getInt("qty");

                for (int i = 0; i < qty; i++) {
                    model.addRow(new Object[]{idBarang, namaBarang, 1, 1, "Baik"});
                    originalQuantities.add(1);
                }
            }

            table_barang_kembali.setModel(model);

            String[] kondisiEnum = {"Baik", "Rusak", "Hilang"};
            JComboBox<String> comboKondisi = new JComboBox<>(kondisiEnum);
            table_barang_kembali.getColumnModel().getColumn(4)
                    .setCellEditor(new DefaultCellEditor(comboKondisi));

            model.addTableModelListener(e -> {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int col = e.getColumn();

                    if (col == 4) {
                        String kondisi = (String) model.getValueAt(row, 4);

                        if ("Hilang".equals(kondisi)) {
                            model.setValueAt(1, row, 3);
                        } else {
                            model.setValueAt(originalQuantities.get(row), row, 3);
                        }
                    }

                    try {
                        int dendaKeterlambatan = Integer.parseInt(denda_terlambat.getText());
                        hitungDendaBarangKembali(dendaKeterlambatan);
                    } catch (NumberFormatException ex) {
                        total_denda.setText("0");
                    }
                }
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat barang: " + e.getMessage());
        }
    }
    
    public int parseRupiah(String rpText) {
        try {
            String clean = rpText.replace("Rp", "")
                    .trim()
                    .split(",")[0]
                    .replace(".", "");
            return Integer.parseInt(clean);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public void tampilkanPreviewStrukPengembalian(String idKembali) {
    try {
        // Query utama untuk ambil data pengembalian & pelanggan
        String sql = "SELECT p.id_kembali, p.id_sewa, p.tgl_kembali, p.status, p.denda_keterlambatan, p.total_denda, " +
                     "p.bayar, p.kembalian, pg.nama_lengkap, pg.no_hp, s.tgl_sewa, s.jaminan " +
                     "FROM pengembalian p " +
                     "JOIN penyewaan s ON p.id_sewa = s.id_sewa " +
                     "JOIN pengguna pg ON s.id_pengguna = pg.id_pengguna " +
                     "WHERE p.id_kembali = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, idKembali);
        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            JOptionPane.showMessageDialog(null, "Data pengembalian tidak ditemukan.");
            return;
        }

        // Ambil data dari query
        String idSewa = rs.getString("id_sewa");
        String tglPinjam = rs.getString("tgl_sewa");
        String tglKembali = rs.getString("tgl_kembali");
        String status = rs.getString("status");
        String jaminan = rs.getString("jaminan");
        String nama = rs.getString("nama_lengkap");
        String noHp = rs.getString("no_hp");
        int bayar = rs.getInt("bayar");
        int kembalian = rs.getInt("kembalian");

        // Info pelanggan dan transaksi
        StringBuilder isiStruk = new StringBuilder();
        isiStruk.append("===========================================\n");
        isiStruk.append("ID Pengembalian : ").append(idKembali).append("\n");
        isiStruk.append("ID Penyewaan    : ").append(idSewa).append("\n");
        isiStruk.append("Nama            : ").append(nama).append("\n");
        isiStruk.append("No HP           : ").append(noHp).append("\n");
        isiStruk.append("Tgl Pinjam      : ").append(tglPinjam).append("\n");
        isiStruk.append("Tgl Kembali     : ").append(tglKembali).append("\n");
        isiStruk.append("Status          : ").append(status).append("\n");
        isiStruk.append("Jaminan         : ").append(jaminan).append("\n");
        isiStruk.append("-------------------------------------------\n");
        isiStruk.append("BARANG KEMBALI:\n");
        isiStruk.append(String.format("%-13s %3s %-6s %10s\n", "Nama", "Qty", "Kondisi", "Denda"));
        isiStruk.append("===========================================\n");

        // Ambil detail barang
        String sqlDetail = "SELECT b.nama_barang, dp.kondisi, SUM(dp.jumlah) AS jumlah, SUM(dp.denda_barang) AS denda " +
                           "FROM detail_pengembalian dp " +
                           "JOIN barang b ON dp.id_barang = b.id_barang " +
                           "WHERE dp.id_kembali = ? " +
                           "GROUP BY b.nama_barang, dp.kondisi";
        PreparedStatement psDetail = con.prepareStatement(sqlDetail);
        psDetail.setString(1, idKembali);
        ResultSet rsDetail = psDetail.executeQuery();

        int totalDenda = 0;
        while (rsDetail.next()) {
            String namaBarang = rsDetail.getString("nama_barang");
            int qty = rsDetail.getInt("jumlah");
            String kondisi = rsDetail.getString("kondisi");
            int denda = rsDetail.getInt("denda");
            totalDenda += denda;

            String namaPendek = namaBarang.length() > 13 ? namaBarang.substring(0, 13) : namaBarang;
            isiStruk.append(String.format("%-13s %3d %-6s %10s\n",
                namaPendek, qty,
                kondisi.length() > 6 ? kondisi.substring(0, 6) : kondisi,
                String.format("Rp %,d", denda)
            ));
        }

        isiStruk.append("-------------------------------------------\n");
        isiStruk.append(String.format("%-15s : Rp %,d\n", "Total Denda", totalDenda));
        isiStruk.append(String.format("%-15s : Rp %,d\n", "Bayar", bayar));
        isiStruk.append(String.format("%-15s : Rp %,d\n", "Kembalian", kembalian));
        isiStruk.append("Kasir           : ").append(Login.Session.getUsername()).append("\n");
        isiStruk.append("===========================================\n");

        // Ucapan
        String ucapan = "TERIMA KASIH TELAH MENGEMBALIKAN!";

        // Tampilkan preview
        tampilkanPreviewStruk(isiStruk.toString(), ucapan);

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal menampilkan preview nota pengembalian: " + e.getMessage());
    }
}

    private void cetakStruk(String isiStruk, String ucapan) {
        try {
            BufferedImage logo = ImageIO.read(getClass().getResource("/assets/logo (2).png"));
            String[] headerLines = {
                "Jl. Gajah Mada Gg. Buntu No. 2",
                "(Barat Bank Danamon) Jember-Jawa Timur",
                "WA Only (No Call/SMS) 0821 3191 2829",
                "IG : brobet_jbr | Kode Pos. 68131",
                ""
            };

            PrinterJob job = PrinterJob.getPrinterJob();
            PageFormat pf = job.defaultPage();
            Paper paper = pf.getPaper();

            double width = 72 * 2.83;
            double height = 297 * 2.83;
            double margin = 5;

            paper.setSize(width, height);
            paper.setImageableArea(margin, margin, width - 2 * margin, height - 2 * margin);
            pf.setPaper(paper);
            pf.setOrientation(PageFormat.PORTRAIT);

            Printable printable = (graphics, pageFormat, pageIndex) -> {
                if (pageIndex > 0) return Printable.NO_SUCH_PAGE;

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));
                FontMetrics fm = g2d.getFontMetrics();

                int y = 0;
                int pageWidth = (int) pageFormat.getImageableWidth();

                // Logo
                int logoWidth = 80, logoHeight = 80;
                int logoX = (pageWidth - logoWidth) / 2;
                g2d.drawImage(logo, logoX, y, logoWidth, logoHeight, null);
                y += logoHeight + 5;

                // Header
                for (String line : headerLines) {
                    int lineWidth = fm.stringWidth(line);
                    g2d.drawString(line, (pageWidth - lineWidth) / 2, y);
                    y += fm.getHeight();
                }

                // Isi Struk
                for (String line : isiStruk.split("\n")) {
                    y = drawWrappedLine(g2d, line, 0, y, pageWidth, fm);
                }

                // Ucapan (centered)
                y += fm.getHeight();
                int ucapanWidth = fm.stringWidth(ucapan);
                g2d.drawString(ucapan, (pageWidth - ucapanWidth) / 2, y);

                return Printable.PAGE_EXISTS;
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

    public void tampilkanPreviewStruk(String isiStruk, String ucapan) {
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

                Font font = new Font("Courier New", Font.PLAIN, 10);
                g2d.setFont(font);
                g2d.setColor(Color.BLACK);
                FontMetrics fm = g2d.getFontMetrics();

                int y = 130;
                int lineSpacing = 13;
                int panelWidth = getWidth();

                // Header
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

                y += 5; // jarak sebelum isi struk

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
            cetakStruk(isiStruk, ucapan);
        });
        tombolPanel.add(btnCetak);
        previewFrame.add(tombolPanel, BorderLayout.SOUTH);

        previewFrame.setVisible(true);
    }
    
    private void CekDanHitungKembalian() {
        try {
            String bayarText = txt_bayar.getText().replace("Rp ", "").replace(",", "").replaceAll("[^\\d]", "");
            String totalText = total_denda.getText().replace("Rp ", "").replace(",", "").replaceAll("[^\\d]", "");

            if (bayarText.isEmpty() || totalText.isEmpty() || bayarText.equals("0")) {
                txt_kembalian.setText("Rp 0");
                return;
            }

            int bayar = Integer.parseInt(bayarText);
            int totalHarga = Integer.parseInt(totalText);

            if (bayar < totalHarga) {
                txt_kembalian.setText("Rp 0");
                return;
            }

            Kembalian();
        } catch (NumberFormatException e) {
            txt_kembalian.setText("Rp 0");
        }
    }

    private void Kembalian() {
        try {
            String totalText = total_denda.getText().replace("Rp ", "").replace(",", "").replaceAll("[^\\d]", "");
            String bayarText = txt_bayar.getText().replace("Rp ", "").replace(",", "").replaceAll("[^\\d]", "");

            if (totalText.isEmpty() || bayarText.isEmpty()) {
                txt_kembalian.setText("Rp 0");
                return;
            }

            int totalHarga = Integer.parseInt(totalText);
            int bayar = Integer.parseInt(bayarText);

            int kembalian = bayar - totalHarga;

            if (kembalian < 0) {
                JOptionPane.showMessageDialog(null, "Jumlah bayar kurang!");
                txt_kembalian.setText("Rp 0");
                return;
            }

            txt_kembalian.setText("Rp " + String.format("%,d", kembalian));
        } catch (NumberFormatException e) {
            txt_kembalian.setText("Rp 0");
            JOptionPane.showMessageDialog(null, "Tolong masukkan angka yang valid.");
        }
    }

    public void hitungDendaBarangKembali(int dendaKeterlambatan) {
        int totalDendaKerusakan = 0;
        DefaultTableModel model = (DefaultTableModel) table_barang_kembali.getModel();

        try {
            for (int i = 0; i < model.getRowCount(); i++) {
                String idBarang = model.getValueAt(i, 0).toString();
                String kondisi = model.getValueAt(i, 4).toString();

                String sql = "SELECT harga_beli FROM barang WHERE id_barang = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, idBarang);
                rs = pst.executeQuery();

                if (rs.next()) {
                    int hargaBeli = rs.getInt("harga_beli");

                    if (kondisi.equalsIgnoreCase("Hilang")) {
                        totalDendaKerusakan += hargaBeli;
                    } else if (kondisi.equalsIgnoreCase("Rusak")) {
                        int jumlahKembali = Integer.parseInt(model.getValueAt(i, 3).toString());
                        totalDendaKerusakan += hargaBeli * jumlahKembali;
                    }
                }

                rs.close();
                pst.close();
            }

            int totalDenda = dendaKeterlambatan + totalDendaKerusakan;
            total_denda.setText("Rp " + String.format("%,.0f", (double) totalDenda));
            denda_kerusakan.setText("Rp " + String.format("%,.0f", (double) totalDendaKerusakan));

        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Gagal menghitung denda: " + e.getMessage());
        }
    }

    
    private String generateID(String tableName, String idColumn, String prefix) {
    String newID = prefix + "001";
    try {
        String sql = "SELECT MAX(" + idColumn + ") FROM " + tableName;
        pst = con.prepareStatement(sql);
        rs = pst.executeQuery();
        if (rs.next() && rs.getString(1) != null) {
            String lastID = rs.getString(1); // contoh: DTP015
            int num = Integer.parseInt(lastID.substring(prefix.length())) + 1;
            newID = prefix + String.format("%03d", num);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal generate ID: " + e.getMessage());
    }
    return newID;
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_retur = new palette.JPanelRounded();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btn_lanjut = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        ID_transaksi = new palette.JTextField_Rounded();
        txt_nama_penyewa = new palette.JTextField_Rounded();
        tgl_kembali = new palette.JTextField_Rounded();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txt_status = new palette.JTextField_Rounded();
        jLabel20 = new javax.swing.JLabel();
        denda_terlambat = new palette.JTextField_Rounded();
        jLabel21 = new javax.swing.JLabel();
        txt_jaminan = new palette.JTextField_Rounded();
        jLabel22 = new javax.swing.JLabel();
        pn_barang = new palette.JPanelRounded();
        denda_kerusakan = new palette.JTextField_Rounded();
        jLabel47 = new javax.swing.JLabel();
        total_denda = new palette.JTextField_Rounded();
        jLabel41 = new javax.swing.JLabel();
        btn_kembali = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanelRounded5 = new palette.JPanelRounded();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_barang_kembali = new palette.JTable_Custom2();
        txt_kembalian = new palette.JTextField_Rounded();
        jLabel49 = new javax.swing.JLabel();
        txt_bayar = new palette.JTextField_Rounded();
        jLabel48 = new javax.swing.JLabel();
        btn_simpan1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(40, 70, 70));
        setLayout(new java.awt.CardLayout());

        pn_retur.setBackground(new java.awt.Color(40, 70, 70));
        pn_retur.setRoundBottomLeft(10);
        pn_retur.setRoundBottomRight(10);
        pn_retur.setRoundTopLeft(10);
        pn_retur.setRoundTopRight(10);

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Form Retur");

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_pemasok.png"))); // NOI18N

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
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        ID_transaksi.setEditable(false);

        txt_nama_penyewa.setEditable(false);

        tgl_kembali.setEditable(false);

        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Status");

        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Nama Penyewa");

        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("ID Transaksi");

        jLabel19.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));

        txt_status.setEditable(false);

        jLabel20.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Denda Keterlambatan");

        denda_terlambat.setEditable(false);

        jLabel21.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Jaminan");

        txt_jaminan.setEditable(false);

        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Tanggal Pengembalian");

        javax.swing.GroupLayout pn_returLayout = new javax.swing.GroupLayout(pn_retur);
        pn_retur.setLayout(pn_returLayout);
        pn_returLayout.setHorizontalGroup(
            pn_returLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_returLayout.createSequentialGroup()
                .addGap(206, 206, 206)
                .addGroup(pn_returLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel16)
                    .addComponent(jLabel21)
                    .addComponent(txt_jaminan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20)
                    .addComponent(denda_terlambat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pn_returLayout.createSequentialGroup()
                        .addComponent(btn_lanjut, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_returLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17)
                    .addComponent(txt_nama_penyewa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tgl_kembali, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel22)
                    .addComponent(ID_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(207, Short.MAX_VALUE))
            .addGroup(pn_returLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pn_returLayout.createSequentialGroup()
                    .addGap(190, 190, 190)
                    .addComponent(jLabel19)
                    .addContainerGap(597, Short.MAX_VALUE)))
        );
        pn_returLayout.setVerticalGroup(
            pn_returLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_returLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pn_returLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(pn_returLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_lanjut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_batal, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ID_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nama_penyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tgl_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(denda_terlambat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_jaminan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(pn_returLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pn_returLayout.createSequentialGroup()
                    .addGap(297, 297, 297)
                    .addComponent(jLabel19)
                    .addContainerGap(194, Short.MAX_VALUE)))
        );

        add(pn_retur, "card2");

        pn_barang.setBackground(new java.awt.Color(40, 70, 70));
        pn_barang.setOpaque(true);
        pn_barang.setRoundBottomLeft(10);
        pn_barang.setRoundBottomRight(10);
        pn_barang.setRoundTopLeft(10);
        pn_barang.setRoundTopRight(10);

        denda_kerusakan.setEditable(false);

        jLabel47.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Denda Kerusakan");

        total_denda.setEditable(false);

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon_penyewaan.png"))); // NOI18N

        btn_kembali.setContentAreaFilled(false);

        btn_kembali.setBorderPainted(false);
        btn_kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_kembali.png"))); // NOI18N
        btn_kembali.setBorder(null);
        btn_kembali.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_kembali_select.png"))); // NOI18N
        btn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kembaliActionPerformed(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Bayar");

        jLabel42.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Barang Yang Di Sewa");

        jPanelRounded5.setBackground(new java.awt.Color(46, 97, 97));
        jPanelRounded5.setRoundBottomLeft(10);
        jPanelRounded5.setRoundBottomRight(10);
        jPanelRounded5.setRoundTopLeft(10);
        jPanelRounded5.setRoundTopRight(10);

        table_barang_kembali.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(table_barang_kembali);

        javax.swing.GroupLayout jPanelRounded5Layout = new javax.swing.GroupLayout(jPanelRounded5);
        jPanelRounded5.setLayout(jPanelRounded5Layout);
        jPanelRounded5Layout.setHorizontalGroup(
            jPanelRounded5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRounded5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelRounded5Layout.setVerticalGroup(
            jPanelRounded5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRounded5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                .addContainerGap())
        );

        txt_kembalian.setEditable(false);

        jLabel49.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Kembalian");

        txt_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bayarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_bayarKeyTyped(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Total Denda");

        btn_simpan1.setContentAreaFilled(false);

        btn_simpan1.setBorderPainted(false);
        btn_simpan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_simpan.png"))); // NOI18N
        btn_simpan1.setBorder(null);
        btn_simpan1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_simpan_select.png"))); // NOI18N
        btn_simpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpan1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_barangLayout = new javax.swing.GroupLayout(pn_barang);
        pn_barang.setLayout(pn_barangLayout);
        pn_barangLayout.setHorizontalGroup(
            pn_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_barangLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pn_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_barangLayout.createSequentialGroup()
                        .addGroup(pn_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pn_barangLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel42)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pn_barangLayout.createSequentialGroup()
                        .addGroup(pn_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_barangLayout.createSequentialGroup()
                                .addComponent(denda_kerusakan, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(total_denda, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(btn_simpan1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pn_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanelRounded5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pn_barangLayout.createSequentialGroup()
                                    .addGroup(pn_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel46)
                                        .addComponent(jLabel47))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(pn_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel48)
                                        .addComponent(txt_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel49))
                                    .addGap(350, 350, 350))))
                        .addGap(0, 49, Short.MAX_VALUE))))
        );
        pn_barangLayout.setVerticalGroup(
            pn_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_barangLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pn_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelRounded5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(jLabel49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(jLabel48))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_simpan1)
                    .addGroup(pn_barangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(denda_kerusakan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(total_denda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_kembali))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        add(pn_barang, "card3");
    }// </editor-fold>//GEN-END:initComponents

    private void btn_lanjutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lanjutActionPerformed
        // TODO add your handling code here:
        pn_retur.setVisible(false);
        pn_barang.setVisible(true);
        String idSewa = ID_transaksi.getText(); // atau ambil dari form sebelumnya
        loadBarangKembali(idSewa);
    }//GEN-LAST:event_btn_lanjutActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        SwingUtilities.getWindowAncestor(this).dispose();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void btn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kembaliActionPerformed
        // TODO add your handling code here:

                pn_barang.setVisible(false);
                pn_retur.setVisible(true);
    }//GEN-LAST:event_btn_kembaliActionPerformed

    private void txt_bayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyReleased
        // TODO add your handling code here:
        String input = txt_bayar.getText().replace("Rp ", "").replace(",", "").replaceAll("[^\\d]", "");

        try {
            if (!input.isEmpty()) {
                int angka = Integer.parseInt(input);
                txt_bayar.setText("Rp " + String.format("%,d", angka));
            } else {
                txt_bayar.setText("Rp 0");
            }
        } catch (NumberFormatException e) {
            txt_bayar.setText("Rp 0");
        }

        CekDanHitungKembalian();
    }//GEN-LAST:event_txt_bayarKeyReleased

    private void txt_bayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '\b') {
            evt.consume(); // ignore character kalau bukan angka
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_txt_bayarKeyTyped

    private void btn_simpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpan1ActionPerformed
        String idPengembalian = generateID("pengembalian", "id_kembali", "PM");
        String idSewa = ID_transaksi.getText();
        String tanggalKembali = tgl_kembali.getText();
        String status = txt_status.getText();
        int dendaKeterlambatan = parseRupiah(denda_terlambat.getText());
        int totalDenda = parseRupiah(total_denda.getText());
        String bayarStr = txt_bayar.getText()
                .replace("Rp", "")
                .replace(".", "")
                .replace(",", "")
                .replaceAll("\\s+", "")
                .trim();

            int bayar = Integer.parseInt(bayarStr);

        String kembalianStr = txt_kembalian.getText()
            .replace("Rp", "")
            .replace(".", "")
            .replace(",", "") // << tambahkan ini
            .replaceAll("\\s+", "")
            .trim();
        int kembalian = Integer.parseInt(kembalianStr);

        try {
            String sqlPengembalian = "INSERT INTO pengembalian (id_kembali, id_sewa, tgl_kembali, status, denda_keterlambatan, total_denda, bayar, kembalian) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(sqlPengembalian);
            pst.setString(1, idPengembalian);
            pst.setString(2, idSewa);
            pst.setString(3, tanggalKembali);
            pst.setString(4, status);
            pst.setInt(5, dendaKeterlambatan);
            pst.setInt(6, totalDenda);
            pst.setInt(7, bayar);
            pst.setInt(8, kembalian);
            pst.executeUpdate();

            for (int i = 0; i < table_barang_kembali.getRowCount(); i++) {
                String idBarang = table_barang_kembali.getValueAt(i, 0).toString();
                int jumlahKembali = Integer.parseInt(table_barang_kembali.getValueAt(i, 3).toString());
                String kondisi = table_barang_kembali.getValueAt(i, 4).toString();

                int dendaBarang = 0;

                if (kondisi.equalsIgnoreCase("Rusak") || kondisi.equalsIgnoreCase("Hilang")) {
                    String queryHarga = "SELECT harga_beli FROM barang WHERE id_barang = ?";
                    PreparedStatement pstHarga = con.prepareStatement(queryHarga);
                    pstHarga.setString(1, idBarang);
                    ResultSet rsHarga = pstHarga.executeQuery();

                    if (rsHarga.next()) {
                        int hargaBeli = rsHarga.getInt("harga_beli");
                        dendaBarang = hargaBeli * jumlahKembali;
                    }

                    rsHarga.close();
                    pstHarga.close();
                }

                String idDetail = generateID("detail_pengembalian", "id_detail_kembali", "DTP");
                String sqlDetail = "INSERT INTO detail_pengembalian (id_detail_kembali, id_kembali, id_barang, jumlah, kondisi, denda_barang) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pstDetail = con.prepareStatement(sqlDetail);
                pstDetail.setString(1, idDetail);
                pstDetail.setString(2, idPengembalian);
                pstDetail.setString(3, idBarang);
                pstDetail.setInt(4, jumlahKembali);
                pstDetail.setString(5, kondisi);
                pstDetail.setInt(6, dendaBarang);
                pstDetail.executeUpdate();

                // Jika Rusak atau Hilang, simpan ke barang_bermasalah
                if (kondisi.equalsIgnoreCase("Rusak") || kondisi.equalsIgnoreCase("Hilang")) {
                    String statusMasalah = kondisi.equalsIgnoreCase("Rusak") ? "Menunggu Pemeriksaan" : "Hilang";

                    String queryBarang = "SELECT nama_barang, harga_beli FROM barang WHERE id_barang = ?";
                    PreparedStatement pstBarang = con.prepareStatement(queryBarang);
                    pstBarang.setString(1, idBarang);
                    ResultSet rsBarang = pstBarang.executeQuery();

                    if (rsBarang.next()) {
                        String namaBarang = rsBarang.getString("nama_barang");
                        int hargaBeli = rsBarang.getInt("harga_beli");

                        String sumberRusak = "Penyewa";

                        // Cek apakah sudah ada berdasarkan id_barang, status, sumber_rusak, id_detail_kembali
                        String cekExist = "SELECT stok FROM barang_bermasalah WHERE id_barang = ? AND status = ? AND id_detail_kembali = ? AND sumber_rusak = ?";
                        PreparedStatement pstCek = con.prepareStatement(cekExist);
                        pstCek.setString(1, idBarang);
                        pstCek.setString(2, statusMasalah);
                        pstCek.setString(3, idDetail);
                        pstCek.setString(4, sumberRusak);
                        ResultSet rsCek = pstCek.executeQuery();

                        if (rsCek.next()) {
                            // Update stok
                            String updateBermasalah = "UPDATE barang_bermasalah SET stok = stok + ? WHERE id_barang = ? AND status = ? AND id_detail_kembali = ? AND sumber_rusak = ?";
                            PreparedStatement pstUpdate = con.prepareStatement(updateBermasalah);
                            pstUpdate.setInt(1, jumlahKembali);
                            pstUpdate.setString(2, idBarang);
                            pstUpdate.setString(3, statusMasalah);
                            pstUpdate.setString(4, idDetail);
                            pstUpdate.setString(5, sumberRusak);
                            pstUpdate.executeUpdate();
                        } else {
                            // Insert baru
                            String idBermasalah = generateID("barang_bermasalah", "id_brg_bermasalah", "BMS");
                            String insertBermasalah = "INSERT INTO barang_bermasalah (id_brg_bermasalah, id_detail_kembali, id_barang, stok, harga_beli, status, sumber_rusak) VALUES (?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement pstInsert = con.prepareStatement(insertBermasalah);
                            pstInsert.setString(1, idBermasalah);
                            pstInsert.setString(2, idDetail);
                            pstInsert.setString(3, idBarang);
                            pstInsert.setInt(4, jumlahKembali);
                            pstInsert.setInt(5, hargaBeli);
                            pstInsert.setString(6, statusMasalah);
                            pstInsert.setString(7, sumberRusak);
                            pstInsert.executeUpdate();
                        }

                        rsCek.close();
                        pstCek.close();
                    }

                    rsBarang.close();
                    pstBarang.close();
                }
            }

            String sqlUpdate = "UPDATE penyewaan SET status = 'Sudah Kembali' WHERE id_sewa = ?";
            PreparedStatement pstUpdate = con.prepareStatement(sqlUpdate);
            pstUpdate.setString(1, idSewa);
            pstUpdate.executeUpdate();

            int pilihan = JOptionPane.showConfirmDialog(
                null,
                "Pengembalian berhasil disimpan!\nTotal Denda: Rp " + totalDenda
                + "\n\nApakah Anda ingin mencetak nota pengembalian sekarang?",
                "Pengembalian Berhasil",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE
            );

            if (pilihan == JOptionPane.YES_OPTION) {
                tampilkanPreviewStrukPengembalian(idPengembalian);
            }

            if (updateListener != null) {
                updateListener.onBarangUpdated();
            }

            SwingUtilities.getWindowAncestor(this).dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Gagal menyimpan pengembalian:\n" + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_simpan1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private palette.JTextField_Rounded ID_transaksi;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_kembali;
    private javax.swing.JButton btn_lanjut;
    private javax.swing.JButton btn_simpan1;
    private palette.JTextField_Rounded denda_kerusakan;
    private palette.JTextField_Rounded denda_terlambat;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private palette.JPanelRounded jPanelRounded5;
    private javax.swing.JScrollPane jScrollPane1;
    private palette.JPanelRounded pn_barang;
    private palette.JPanelRounded pn_retur;
    private palette.JTable_Custom2 table_barang_kembali;
    private palette.JTextField_Rounded tgl_kembali;
    private palette.JTextField_Rounded total_denda;
    private palette.JTextField_Rounded txt_bayar;
    private palette.JTextField_Rounded txt_jaminan;
    private palette.JTextField_Rounded txt_kembalian;
    private palette.JTextField_Rounded txt_nama_penyewa;
    private palette.JTextField_Rounded txt_status;
    // End of variables declaration//GEN-END:variables
}
