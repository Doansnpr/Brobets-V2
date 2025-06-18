
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import view.Login.Session;


public class FormTambahPenyewaan extends javax.swing.JPanel {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    private BarangUpdateListener updateListener;
    boolean pelangganLama = false;
    String idPelangganLama = "";
    int poinSekarang = 0;
    
    private String idBarangTerpilih = "";
    private int hargaBarangTerpilih = 0;
    
    public FormTambahPenyewaan() {
        initComponents();
        
        Koneksi DB = new Koneksi();
        DB.config();
        con = DB.con;
        
        
        setupBarcodeScannerListener();
         
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
    
    private String generateID(String tableName, String idColumn, String prefix) {
        String newID = prefix + "001";
        try {
            String sql = "SELECT " + idColumn + " FROM " + tableName + " ORDER BY " + idColumn + " DESC LIMIT 1";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                String lastID = rs.getString(1);
                int num = Integer.parseInt(lastID.substring(prefix.length())) + 1;
                newID = prefix + String.format("%03d", num);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal generate ID: " + e.getMessage());
        }
        return newID;
    }
    
    public void setUpdateListener(BarangUpdateListener listener) {
        this.updateListener = listener;
    }
    
    private void load_tableBrg() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Qty");
        model.addColumn("Harga");
        model.addColumn("Subtotal");

        table_barang.setModel(model);
    }

    private void processBarcode() {
        String scannedKode = txt_barcode.getText().trim();
        if (scannedKode.isEmpty()) return;

        try {
            String sql = "SELECT * FROM barang WHERE barcode = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, scannedKode);
            rs = pst.executeQuery();

            if (rs.next()) {
                String idBarang = rs.getString("id_barang");
                String namaBarang = rs.getString("nama_barang");
                int harga = rs.getInt("harga_sewa");
                int stok = rs.getInt("stok");

                String qtyStr = JOptionPane.showInputDialog(null, "Masukkan jumlah untuk " + namaBarang + ":", "Jumlah Barang", JOptionPane.QUESTION_MESSAGE);
                if (qtyStr == null || qtyStr.trim().isEmpty()) return;

                int qty = Integer.parseInt(qtyStr.trim());

                if (qty <= 0) {
                    JOptionPane.showMessageDialog(null, "Jumlah harus lebih dari 0!");
                    return;
                }

                if (qty > stok) {
                    JOptionPane.showMessageDialog(null, "Stok tidak mencukupi! Tersedia: " + stok);
                    return;
                }

                DefaultTableModel model = (DefaultTableModel) table_barang.getModel();
                boolean barangSudahAda = false;

                for (int i = 0; i < model.getRowCount(); i++) {
                    String idTabel = model.getValueAt(i, 0).toString();
                    if (idTabel.equals(idBarang)) {
                        int qtyLama = Integer.parseInt(model.getValueAt(i, 2).toString());
                        int qtyBaru = qtyLama + qty;
                        double subTotalBaru = harga * qtyBaru;

                        model.setValueAt(qtyBaru, i, 2);
                        model.setValueAt(subTotalBaru, i, 4);
                        barangSudahAda = true;
                        break;
                    }
                }

                if (!barangSudahAda) {
                    double subTotal = qty * harga;
                    model.addRow(new Object[]{idBarang, namaBarang, qty, harga, subTotal});
                }

                hitungTotalHarga();
                txt_barcode.setText("");

            } else {
                JOptionPane.showMessageDialog(null, "Barang tidak ditemukan!");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Jumlah harus berupa angka!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
        }
    }

    private void setupBarcodeScannerListener() {
        txt_barcode.getDocument().addDocumentListener(new DocumentListener() {
            private Timer timer = new Timer(300, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    processBarcode();
                }
            });

            public void changedUpdate(DocumentEvent e) {
                restartTimer();
            }

            public void removeUpdate(DocumentEvent e) {
                restartTimer();
            }

            public void insertUpdate(DocumentEvent e) {
                restartTimer();
            }

            private void restartTimer() {
                timer.setRepeats(false);
                timer.restart();
            }
        });
    }
    
    private void CekDanHitungKembalian() {
        try {
            String bayarText = txt_bayar.getText().replace("Rp ", "").replace(",", "").replaceAll("[^\\d]", "");
            String totalText = txt_total.getText().replace("Rp ", "").replace(",", "").replaceAll("[^\\d]", "");

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
            String totalText = txt_total.getText().replace("Rp ", "").replace(",", "").replaceAll("[^\\d]", "");
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
    
    private boolean isTendaSudahDitambahkan(DefaultTableModel model) {
        for (int i = 0; i < model.getRowCount(); i++) {
            String namaBarang = model.getValueAt(i, 1).toString().toLowerCase();
            double harga = Double.parseDouble(model.getValueAt(i, 3).toString());
            if (namaBarang.contains("tenda") && harga == 0.0) {
                return true;
            }
        }
        return false;
    }

    private void hitungTotalHarga() {
        double total = 0.0;
        DefaultTableModel model = (DefaultTableModel) table_barang.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            double subtotal = Double.parseDouble(model.getValueAt(i, 4).toString());
            total += subtotal;
        }
        txt_total.setText("Rp " + String.format("%,.0f", total));
    }
    

    public void cekPelanggan() {
        String nama = nama_penyewa.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Isi nama pelanggan terlebih dahulu.");
            pelangganLama = false;
            return;
        }

        try {
            String query = "SELECT * FROM pelanggan WHERE nama_pelanggan = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nama);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String status = rs.getString("status");

                if ("nonaktif".equalsIgnoreCase(status)) {
                    JOptionPane.showMessageDialog(this, "Pelanggan ditemukan, namun statusnya NONAKTIF.\nAktifkan akun terlebih dahulu untuk melakukan sewa.");
                    pelangganLama = false;
                    return;
                }

                no_hp.setText(rs.getString("no_hp"));
                pelangganLama = true;
                idPelangganLama = rs.getString("id_pelanggan");
                poinSekarang = rs.getInt("poin");

                JOptionPane.showMessageDialog(this, "Pelanggan lama ditemukan dan aktif.\nPoin saat ini: " + poinSekarang);
            } else {
                pelangganLama = false;
                idPelangganLama = "";
                poinSekarang = 0;
                no_hp.setText("");
                JOptionPane.showMessageDialog(this, "Pelanggan baru ditemukan.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saat cek pelanggan: " + e.getMessage());
            pelangganLama = false;
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
    
    public String convertDateToSQLFormat(String inputDate) throws ParseException {
        SimpleDateFormat fromFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat toFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = fromFormat.parse(inputDate);
        return toFormat.format(date);
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
        nama_penyewa = new palette.JTextField_Rounded();
        jLabel9 = new javax.swing.JLabel();
        tgl_sewa = new palette.JTextField_Rounded();
        jLabel10 = new javax.swing.JLabel();
        tgl_kembali = new palette.JTextField_Rounded();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        no_hp = new palette.JTextField_Rounded();
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
        cek = new javax.swing.JButton();
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
        table_barang = new palette.JTable_Custom2();
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
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nama Penyewa");

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Tanggal Sewa");

        tgl_sewa.setText("");

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

        cek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cekActionPerformed(evt);
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
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_data_penyewaLayout.createSequentialGroup()
                                .addGroup(pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel9)
                                    .addComponent(nama_penyewa, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                                    .addComponent(tgl_sewa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_calender)
                                    .addComponent(cek, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                            .addComponent(no_hp, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGroup(pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_data_penyewaLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(33, 33, 33)
                        .addComponent(jPanelRounded1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_data_penyewaLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pn_data_penyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cek, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nama_penyewa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(no_hp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        btn_batal.setContentAreaFilled(false);

        btn_batal.setBorderPainted(false);
        btn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_hapus.png"))); // NOI18N
        btn_hapus.setBorder(null);
        btn_hapus.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_hapus_select.png"))); // NOI18N
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Barcode");

        jLabel44.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Nama Barang");

        jLabel45.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Jumlah");

        txt_jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_jumlahKeyTyped(evt);
            }
        });

        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        jPanelRounded5.setBackground(new java.awt.Color(46, 97, 97));
        jPanelRounded5.setRoundBottomLeft(10);
        jPanelRounded5.setRoundBottomRight(10);
        jPanelRounded5.setRoundTopLeft(10);
        jPanelRounded5.setRoundTopRight(10);

        table_barang.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(table_barang);

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

        txt_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bayarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_bayarKeyTyped(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Total Harga");

        txt_total.setEditable(false);

        jLabel48.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Kembalian");

        txt_kembalian.setEditable(false);

        btn_lanjut.setContentAreaFilled(false);

        btn_lanjut.setBorderPainted(false);
        btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_simpan.png"))); // NOI18N
        btn_simpan.setBorder(null);
        btn_simpan.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_simpan_select.png"))); // NOI18N
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        btn_batal.setContentAreaFilled(false);

        btn_batal.setBorderPainted(false);
        btn_kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_kembali.png"))); // NOI18N
        btn_kembali.setBorder(null);
        btn_kembali.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_kembali_select.png"))); // NOI18N
        btn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kembaliActionPerformed(evt);
            }
        });

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
                            .addComponent(jPanelRounded5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                                    .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                                        .addComponent(jLabel45)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(pn_barang_sewaLayout.createSequentialGroup()
                                        .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 15, Short.MAX_VALUE))))
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
                .addContainerGap(20, Short.MAX_VALUE))
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
        String namaPenyewa = nama_penyewa.getText().trim();
        String tglPinjam = tgl_sewa.getText().trim();
        String tglKembali = tgl_kembali.getText().trim();
        String noHp = no_hp.getText().trim();
        
        String jaminan = "";

        if (cek_sim.isSelected()) {
            jaminan += cek_sim.getText() + ", ";
        }
        if (cek_ktp.isSelected()) {
            jaminan += cek_ktp.getText() + ", ";
        }
        if (cek_ktm.isSelected()) {
            jaminan += cek_ktm.getText() + ", ";
        }
        if (cek_fckk.isSelected()) {
            jaminan += cek_fckk.getText() + ", ";
        }

        if (!jaminan.isEmpty()) {
            jaminan = jaminan.substring(0, jaminan.length() - 2);
        }

        if (namaPenyewa.isEmpty() || noHp.isEmpty() || jaminan.isEmpty() || tglPinjam.isEmpty() || tglKembali.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi semua data penyewa!");
            return;
        }
        
        

        pn_data_penyewa.setVisible(false);
        pn_barang_sewa.setVisible(true);
        load_tableBrg();
    }//GEN-LAST:event_btn_lanjutActionPerformed

    private void cekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cekActionPerformed
        // TODO add your handling code here:
        cekPelanggan();
    }//GEN-LAST:event_cekActionPerformed

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

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
            // TODO add your handling code here:
            int selectedRow = table_barang.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Silahkan pilih data yang ingin dihapus.",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        DefaultTableModel model = (DefaultTableModel) table_barang.getModel();

        int jumlah = Integer.parseInt(model.getValueAt(selectedRow, 2).toString());
        double harga = Integer.parseInt(model.getValueAt(selectedRow, 3).toString()); 

        if (jumlah > 1) {
            jumlah -= 1;
            double subtotal = jumlah * harga;

            model.setValueAt(jumlah, selectedRow, 2);    
            model.setValueAt(subtotal, selectedRow, 4); 
        } else {
            int konfirmasi = JOptionPane.showConfirmDialog(
                    this,
                    "Jumlah tinggal 1. Hapus barang ini dari daftar?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION
            );

            if (konfirmasi == JOptionPane.YES_OPTION) {
                model.removeRow(selectedRow);
            }
        }

        hitungTotalHarga();

    }//GEN-LAST:event_btn_hapusActionPerformed

    private void txt_jumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlahKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '\b') {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_txt_jumlahKeyTyped

    private void txt_bayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '\b') {
            evt.consume(); // ignore character kalau bukan angka
            Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_txt_bayarKeyTyped

    private void btn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kembaliActionPerformed
        // TODO add your handling code here:
        
        pn_barang_sewa.setVisible(false);
        pn_data_penyewa.setVisible(true);
        
    }//GEN-LAST:event_btn_kembaliActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        SwingUtilities.getWindowAncestor(this).dispose();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        // TODO add your handling code here:
        try {
            if (nama_penyewa.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Isi nama penyewa dulu dan cek pelanggan!");
                return;
            }

            String idPelanggan = pelangganLama ? idPelangganLama : generateID("pelanggan", "id_pelanggan", "PL");
            String idSewa = generateID("penyewaan", "id_sewa", "PN");

            String namaPenyewa = nama_penyewa.getText().trim();
            String tglPinjam = convertDateToSQLFormat(tgl_sewa.getText().trim());
            String tglKembali = convertDateToSQLFormat(tgl_kembali.getText().trim());

            String noHp = no_hp.getText().trim();

            String bayarStr = txt_bayar.getText()
                .replace("Rp", "")
                .replace(".", "")
                .replace(",", "") // << tambahkan ini
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

            String jaminan = "";
            if (cek_sim.isSelected()) {
                jaminan = "SIM";
            } else if (cek_ktp.isSelected()) {
                jaminan = "KTP";
            } else if (cek_ktm.isSelected()) {
                jaminan = "KTM";
            } else if (cek_fckk.isSelected()) {
                jaminan = "FC KK";
            }

            DefaultTableModel model = (DefaultTableModel) table_barang.getModel();
            double totalHarga = 0;
            boolean rewardDipakai = false;

            for (int i = 0; i < model.getRowCount(); i++) {
                int qty = Integer.parseInt(model.getValueAt(i, 2).toString());

                String hargaStr = model.getValueAt(i, 3).toString().replace("Rp", "").replace(".", "").replaceAll("\\s+", "");
                double harga = Double.parseDouble(hargaStr);

                totalHarga += qty * harga;

                String namaBarang = model.getValueAt(i, 1).toString().toLowerCase();
                if (namaBarang.contains("tenda") && harga == 0.0) {
                    rewardDipakai = true;
                }
            }

            StringBuilder keterangan = new StringBuilder();

            boolean poinBaruDitambahkan = false;

            if (!pelangganLama) {
                int poin = 0;
                if (totalHarga >= 50000) {
                    poin = 1;
                    poinBaruDitambahkan = true;
                }
                String statusReward = (poin >= 5) ? "tersedia" : "tidak tersedia";

                String sqlPelanggan = "INSERT INTO pelanggan (id_pelanggan, nama_pelanggan, no_hp, poin, status_reward, status) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement psPelanggan = con.prepareStatement(sqlPelanggan);
                psPelanggan.setString(1, idPelanggan);
                psPelanggan.setString(2, namaPenyewa);
                psPelanggan.setString(3, noHp);
                psPelanggan.setInt(4, poin);
                psPelanggan.setString(5, statusReward);
                psPelanggan.setString(6, "aktif");
                psPelanggan.executeUpdate();

                pelangganLama = true;
                idPelangganLama = idPelanggan;
                poinSekarang = poin;

                keterangan.append("Pelanggan baru ditambahkan. Poin awal: ").append(poin).append(".\n");
            }

            if (pelangganLama && !rewardDipakai && totalHarga >= 50000 && !poinBaruDitambahkan) {
                poinSekarang += 1;
                String statusReward = (poinSekarang >= 5) ? "tersedia" : "tidak tersedia";

                String updatePoin = "UPDATE pelanggan SET poin = ?, status_reward = ? WHERE id_pelanggan = ?";
                PreparedStatement psUpdatePoin = con.prepareStatement(updatePoin);
                psUpdatePoin.setInt(1, poinSekarang);
                psUpdatePoin.setString(2, statusReward);
                psUpdatePoin.setString(3, idPelanggan);
                psUpdatePoin.executeUpdate();

                keterangan.append("Poin ditambahkan. Poin sekarang: ").append(poinSekarang).append(".\n");
            }

            String sqlSewa = "INSERT INTO penyewaan (id_sewa, id_pelanggan, id_pengguna, tgl_sewa, tgl_rencana_kembali, jaminan, total_harga, bayar, kembalian, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psSewa = con.prepareStatement(sqlSewa);
            psSewa.setString(1, idSewa);
            psSewa.setString(2, idPelanggan);
            psSewa.setString(3, Session.getIdPengguna());
            psSewa.setString(4, tglPinjam);
            psSewa.setString(5, tglKembali);
            psSewa.setString(6, jaminan);
            psSewa.setDouble(7, totalHarga);
            psSewa.setInt(8, bayar);
            psSewa.setInt(9, kembalian);
            psSewa.setString(10, "Belum Kembali");

            psSewa.executeUpdate();

            for (int i = 0; i < table_barang.getRowCount(); i++) {
                String idBarang = table_barang.getValueAt(i, 0).toString();
                int qty = Integer.parseInt(table_barang.getValueAt(i, 2).toString());
                double harga = Double.parseDouble(table_barang.getValueAt(i, 3).toString());
                double subTotal = qty * harga;

                String idDetail = generateID("detail_sewa", "id_detail", "DTS");
                String sqlDetail = "INSERT INTO detail_sewa (id_detail, id_sewa, id_barang, qty, sub_total) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement psDetail = con.prepareStatement(sqlDetail);
                psDetail.setString(1, idDetail);
                psDetail.setString(2, idSewa);
                psDetail.setString(3, idBarang);
                psDetail.setInt(4, qty);
                psDetail.setDouble(5, subTotal);
                psDetail.executeUpdate();
            }

            StringBuilder pesanAkhir = new StringBuilder("Transaksi penyewaan berhasil disimpan.\n");
            pesanAkhir.append(keterangan);

            JOptionPane.showMessageDialog(this, pesanAkhir.toString());

            nama_penyewa.setText("");
            no_hp.setText("");
            txt_total.setText("Rp 0");
            txt_bayar.setText("");
            txt_kembalian.setText("");

            DefaultTableModel modelClear = (DefaultTableModel) table_barang.getModel();
            modelClear.setRowCount(0);

            poinSekarang = 0;
            pelangganLama = false;
            idPelangganLama = "";

            if (updateListener != null) {
            updateListener.onBarangUpdated();
            }

            SwingUtilities.getWindowAncestor(this).dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal simpan transaksi: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        // TODO add your handling code here:
         String keyword = search.getText().trim(); // hapus spasi depan/belakang

        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Silakan isi nama barang yg akan dicari!");
            return;
        }

        try {
            String sql = "SELECT * FROM barang WHERE nama_barang LIKE ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, "%" + keyword + "%");
            rs = pst.executeQuery();

            if (rs.next()) {
                txt_nama_brg.setText(rs.getString("nama_barang"));
                hargaBarangTerpilih = rs.getInt("harga_sewa");
                idBarangTerpilih = rs.getString("id_barang");
            } else {
                JOptionPane.showMessageDialog(null, "Barang tidak ditemukan!");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        try {
            String namaBarang = txt_nama_brg.getText().trim();
            String qtyStr = txt_jumlah.getText().trim();

            if (namaBarang.isEmpty() || qtyStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Isi nama barang dan qty!");
                return;
            }

            int qtyInt = Integer.parseInt(qtyStr);
            if (qtyInt <= 0) {
                JOptionPane.showMessageDialog(null, "Qty harus lebih dari nol.");
                return;
            }

            String sql = "SELECT id_barang, harga_sewa, stok FROM barang WHERE nama_barang = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, namaBarang);
            rs = pst.executeQuery();

            if (rs.next()) {
                String idBarang = rs.getString("id_barang");
                int harga = rs.getInt("harga_sewa");
                int stok = rs.getInt("stok");

                if (qtyInt > stok) {
                    JOptionPane.showMessageDialog(null, "Stok tidak mencukupi! Stok tersedia: " + stok);
                    return;
                }

                DefaultTableModel model = (DefaultTableModel) table_barang.getModel();
                boolean barangSudahAda = false;

                for (int i = 0; i < model.getRowCount(); i++) {
                    String idTabel = model.getValueAt(i, 0).toString();
                    if (idTabel.equals(idBarang)) {
                        int qtyLama = Integer.parseInt(model.getValueAt(i, 2).toString());
                        int qtyBaru = qtyLama + qtyInt;
                        int subTotalBaru = harga * qtyBaru;

                        model.setValueAt(qtyBaru, i, 2);
                        model.setValueAt(subTotalBaru, i, 4);
                        barangSudahAda = true;
                        break;
                    }
                }

                if (!barangSudahAda) {
                    int subTotal = qtyInt * harga;
                    model.addRow(new Object[]{idBarang, namaBarang, qtyInt, harga, subTotal});
                }

                if (pelangganLama) {
                    if (poinSekarang >= 5 && !isTendaSudahDitambahkan(model)) {
                        int jawab = JOptionPane.showConfirmDialog(null,
                                "Jumlah poin Anda: " + poinSekarang + "\n Mendapatkan 1x sewa tenda GRATIS!\nGunakan sekarang?",
                                "Reward Tersedia",
                                JOptionPane.YES_NO_OPTION);

                        if (jawab == JOptionPane.YES_OPTION) {
                            String sqlTenda = "SELECT id_barang, nama_barang FROM barang WHERE nama_barang LIKE '%tenda%'";
                            pst = con.prepareStatement(sqlTenda);
                            rs = pst.executeQuery();
                            if (rs.next()) {
                                String idTenda = rs.getString("id_barang");
                                String namaTenda = rs.getString("nama_barang");
                                model.addRow(new Object[]{idTenda, namaTenda, 1, 0, 0});
                                JOptionPane.showMessageDialog(null, "Tenda gratis berhasil ditambahkan!");
                            }
                        }
                    }
                }

                hitungTotalHarga();
                txt_nama_brg.setText("");
                txt_jumlah.setText("");
                search.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Barang tidak ditemukan!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_tambahActionPerformed


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
    private javax.swing.JButton cek;
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
    private palette.JTextField_Rounded nama_penyewa;
    private palette.JTextField_Rounded no_hp;
    private palette.JPanelRounded pn_barang_sewa;
    private palette.JPanelRounded pn_data_penyewa;
    private palette.JTextField_Rounded search;
    private palette.JTable_Custom2 table_barang;
    private palette.JTextField_Rounded tgl_kembali;
    private palette.JTextField_Rounded tgl_sewa;
    private palette.JTextField_Rounded txt_barcode;
    private palette.JTextField_Rounded txt_bayar;
    private palette.JTextField_Rounded txt_jumlah;
    private palette.JTextField_Rounded txt_kembalian;
    private palette.JTextField_Rounded txt_nama_brg;
    private palette.JTextField_Rounded txt_total;
    // End of variables declaration//GEN-END:variables
}
