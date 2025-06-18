
package view;

import event.EventMenuSwitch;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JTable;


public class Barang extends javax.swing.JPanel {
    
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    private EventMenuSwitch switchEvent;
    private String idBarang, namaBarang;
    private BufferedImage barcodeImage;
    private JLabel lblBarcode;
    private String idBarangYangSedangDiedit;
    public static Map<String, String> kategoriMap = new HashMap<>();         
    public static Map<String, String> kategoriMapReverse = new HashMap<>();
    
    private String selectedStatus = "Tersedia";
    
    public Barang(EventMenuSwitch switchEvent) {
        this.switchEvent = switchEvent;
        initComponents();
        
        Koneksi DB = new Koneksi();
        DB.config();
        con = DB.con;
        
        loadKategoriToMap();    
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
                cariBarang();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                cariBarang();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                cariBarang();
            }
        });
         
        

    }

    
    public void load_table() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Harga Sewa");
        model.addColumn("Katalog");
        model.addColumn("Stok");
        model.addColumn("Status");
        model.addColumn("Harga Beli");
        model.addColumn("Barcode");

        // Inisialisasi ulang map-nya agar data baru
        kategoriMap.clear();

        try {
            Connection con = Koneksi.getConnection();

            // ISI kategoriMap SEKARANG
            String sqlKategori = "SELECT id_katalog, nama_katalog FROM katalog";
            PreparedStatement pstKategori = con.prepareStatement(sqlKategori);
            ResultSet rsKategori = pstKategori.executeQuery();

            while (rsKategori.next()) {
                String id = rsKategori.getString("id_katalog");
                String nama = rsKategori.getString("nama_katalog");
                kategoriMap.put(nama, id); // ← simpan relasi nama → id
            }

            // ISI TABEL BARANG
            String sqlBarang = "SELECT b.id_barang, b.nama_barang, b.harga_sewa, k.nama_katalog, b.stok, b.status, b.harga_beli, b.barcode " +
                   "FROM barang b JOIN katalog k ON b.id_katalog = k.id_katalog " +
                   "ORDER BY b.id_barang ASC";

            PreparedStatement pstBarang = con.prepareStatement(sqlBarang);
            ResultSet rsBarang = pstBarang.executeQuery();

            while (rsBarang.next()) {
                model.addRow(new Object[]{
                    rsBarang.getString(1),
                    rsBarang.getString(2),
                    rsBarang.getInt(3),
                    rsBarang.getString(4), // nama kategori
                    rsBarang.getInt(5),
                    rsBarang.getString(6),
                    rsBarang.getInt(7),
                    rsBarang.getString(8)
                });
            }

            tbl_barang.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading table: " + e.getMessage());
        }
    }
       
    private void loadKategoriToMap() {
        kategoriMap.clear();
        try {
            Connection con = Koneksi.getConnection();
            String sql = "SELECT id_katalog, nama_katalog FROM katalog";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                kategoriMap.put(rs.getString("nama_katalog"), rs.getString("id_katalog"));
            }

            rs.close();
            pst.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public JTable getTblBarang() {
        return tbl_barang;
    }

    private void tambahbrgrusak() {
        int selectedRow = tbl_barang.getSelectedRow();

        if (selectedRow < 0) {
            if (switchEvent != null) {
                switchEvent.switchPanel(new BarangRusak());
            }
            return;
        }

        String idBarang = tbl_barang.getValueAt(selectedRow, 0).toString();
        int stok = Integer.parseInt(tbl_barang.getValueAt(selectedRow, 4).toString());
        int hargaBeli = Integer.parseInt(tbl_barang.getValueAt(selectedRow, 6).toString());

        if (stok <= 0) {
            JOptionPane.showMessageDialog(this, "Stok barang habis.");
            return;
        }

        String[] opsi = {"Rusak", "Hilang", "Maintenance"};
        String status = (String) JOptionPane.showInputDialog(this, "Pilih kondisi:", "Status Rusak",
                JOptionPane.QUESTION_MESSAGE, null, opsi, opsi[0]);
        if (status == null) {
            return;
        }

        String inputJumlah = JOptionPane.showInputDialog(this, "Jumlah rusak:");
        if (inputJumlah == null || inputJumlah.trim().isEmpty()) {
            return;
        }

        int jumlah;
        try {
            jumlah = Integer.parseInt(inputJumlah);
            if (jumlah <= 0 || jumlah > stok) {
                JOptionPane.showMessageDialog(this, "Jumlah tidak valid.");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Input harus berupa angka.");
            return;
        }

        try {
            con = Koneksi.getConnection();

            // Cek apakah barang rusak dengan status itu sudah ada
            String cek = "SELECT id_brg_bermasalah, stok FROM barang_bermasalah WHERE id_barang = ? AND status = ?";
            PreparedStatement pstCek = con.prepareStatement(cek);
            pstCek.setString(1, idBarang);
            pstCek.setString(2, status);
            ResultSet rs = pstCek.executeQuery();

            if (rs.next()) {
                // Jika ada, update stok
                int stokLama = rs.getInt("stok");
                String idLama = rs.getString("id_brg_bermasalah");
                String update = "UPDATE barang_bermasalah SET stok = ? WHERE id_brg_bermasalah = ?";
                PreparedStatement pstUp = con.prepareStatement(update);
                pstUp.setInt(1, stokLama + jumlah);
                pstUp.setString(2, idLama);
                pstUp.executeUpdate();
            } else {
                // Jika belum ada, insert data baru
                String idBaru = generateID("barang_bermasalah", "id_brg_bermasalah", "BMS");
                String insert = "INSERT INTO barang_bermasalah "
                        + "(id_brg_bermasalah, id_barang, stok, harga_beli, status, sumber_rusak, id_detail_kembali) "
                        + "VALUES (?, ?, ?, ?, ?, ?, NULL)";
                PreparedStatement pstIn = con.prepareStatement(insert);
                pstIn.setString(1, idBaru);
                pstIn.setString(2, idBarang);
                pstIn.setInt(3, jumlah);
                pstIn.setInt(4, hargaBeli);
                pstIn.setString(5, status);
                pstIn.setString(6, "Internal");
                pstIn.executeUpdate();
            }

            // Kurangi stok di tabel barang
            String updateBarang = "UPDATE barang SET stok = stok - ? WHERE id_barang = ?";
            PreparedStatement pstBarang = con.prepareStatement(updateBarang);
            pstBarang.setInt(1, jumlah);
            pstBarang.setString(2, idBarang);
            pstBarang.executeUpdate();

            JOptionPane.showMessageDialog(this, "Barang rusak berhasil dicatat.");
            load_table();
            if (switchEvent != null) {
                switchEvent.switchPanel(new BarangRusak());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public class EnumComboBoxLoader {

        public static void loadEnumFromDatabase(Connection con, JComboBox<String> comboBox) {
            String query = "SHOW COLUMNS FROM barang LIKE 'status'";

            try (PreparedStatement pst = con.prepareStatement(query); ResultSet rs = pst.executeQuery()) {

                if (rs.next()) {
                    String type = rs.getString("Type");

                    String enumValues = type.substring(type.indexOf("(") + 1, type.indexOf(")"));
                    String[] values = enumValues.replace("'", "").split(",");

                    comboBox.removeAllItems();
                    for (String value : values) {
                        String formatted = value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
                        comboBox.addItem(formatted);
                    }
                } else {
                    System.out.println("Kolom 'status' tidak ditemukan.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Gagal memuat enum: " + e.getMessage());
            }
        }
    }
    
    private String generateID(String tableName, String idColumn, String prefix) {
        String newID = prefix + "001";
        try {
            String sql = "SELECT " + idColumn + " FROM " + tableName + 
                         " WHERE " + idColumn + " LIKE ? ORDER BY " + idColumn + " DESC LIMIT 1";
            pst = con.prepareStatement(sql);
            pst.setString(1, prefix + "%");
            rs = pst.executeQuery();
            if (rs.next()) {
                String lastID = rs.getString(1); // Misal: BMS006
                int num = Integer.parseInt(lastID.substring(prefix.length())); // 6
                num++; // 7
                newID = prefix + String.format("%03d", num); // BMS007
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal generate ID: " + e.getMessage());
        }
        return newID;
    }
    
    private void cariBarang() {
    String keyword = search.getText().trim();

    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID Barang");
    model.addColumn("Nama Barang");
    model.addColumn("Harga Sewa");
    model.addColumn("Katalog");
    model.addColumn("Stok");
    model.addColumn("Status");
    model.addColumn("Harga Beli");
    model.addColumn("Barcode");

    try {
        String sql = "SELECT b.id_barang, b.nama_barang, b.harga_sewa, k.nama_katalog, b.stok, b.status, b.harga_beli, b.barcode " +
                     "FROM barang b JOIN katalog k ON b.id_katalog = k.id_katalog " +
                     "WHERE b.nama_barang LIKE ? ORDER BY b.id_barang ASC";
        pst = con.prepareStatement(sql);
        pst.setString(1, "%" + keyword + "%");
        rs = pst.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("id_barang"),
                rs.getString("nama_barang"),
                rs.getInt("harga_sewa"),
                rs.getString("nama_katalog"),   // katalog
                rs.getInt("stok"),
                rs.getString("status"),
                rs.getInt("harga_beli"),
                rs.getString("barcode")
            });
        }

        tbl_barang.setModel(model);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage());
    }
}

    public class DialogBarcodeGenerator extends JDialog {
        
    private String idBarang, namaBarang;
    private BufferedImage barcodeImage;
    private JLabel lblBarcode;

    public DialogBarcodeGenerator(String idBarang, String namaBarang) {
        this.idBarang = idBarang;
        this.namaBarang = namaBarang;

        setTitle("Generate Barcode");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        lblBarcode = new JLabel("", SwingConstants.CENTER);
        add(lblBarcode, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnSimpan = new JButton("Simpan ke Tabel");
        JButton btnCetak = new JButton("Cetak");
        JButton btnCancel = new JButton("Cancel");
        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnCetak);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        String barcodeText = idBarang + "-" + namaBarang;
        generateBarcode(barcodeText);


        btnSimpan.addActionListener(e -> simpanBarcodeKeDB(idBarang));
        btnCetak.addActionListener(e -> cetakBarcode());
        btnCancel.addActionListener(e -> dispose());
    }

    private void generateBarcode(String text) {
        try {
            int width = 400;
            int height = 150;

            BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, width, height);
            barcodeImage = MatrixToImageWriter.toBufferedImage(matrix);

            lblBarcode.setIcon(new ImageIcon(barcodeImage));
            lblBarcode.setPreferredSize(new Dimension(width, height));
            lblBarcode.revalidate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal generate barcode: " + e.getMessage());
        }
    }

    private void simpanBarcodeKeDB(String idBarang) {
    try {
        String barcodeText = idBarang + "-" + namaBarang; // barcode = ID + Nama
        Connection conn = Koneksi.getConnection(); // koneksi DB
        String sql = "UPDATE barang SET barcode = ? WHERE id_barang = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, barcodeText); // nilai barcode disimpan
        ps.setString(2, idBarang);    // kondisi WHERE tetap pakai ID
        ps.executeUpdate();

        JOptionPane.showMessageDialog(this, "Barcode disimpan ke database.");
        load_table(); // refresh tabel di main window, jika perlu
        dispose();    // ✅ tutup JDialog setelah simpan sukses
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error simpan: " + e.getMessage());
    }
}

    private void cetakBarcode() {
        try {
            File outputfile = new File("barcode_" + idBarang + ".png");
            ImageIO.write(barcodeImage, "png", outputfile);
            Desktop.getDesktop().open(outputfile); // buka file
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal cetak barcode: " + e.getMessage());
        }
    }
    }

    public void onShow() {
        load_table(); // atau fungsi refresh data
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_barang = new palette.JTable_Custom2();
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

        tbl_barang.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_barang);

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
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        btn_barcode.setContentAreaFilled(false);
        btn_barcode.setBorderPainted(false);
        btn_barcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_barcode.png"))); // NOI18N
        btn_barcode.setBorder(null);
        btn_barcode.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/btn_barcode_select.png"))); // NOI18N
        btn_barcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_barcodeActionPerformed(evt);
            }
        });

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
                .addGap(16, 16, 16))
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
        panel.setUpdateListener(() -> {
            load_table();
        });
        
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
        int selectedRow = tbl_barang.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diedit!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ambil semua data dari tabel
        DefaultTableModel model = (DefaultTableModel) tbl_barang.getModel();
        String idBarang = String.valueOf(model.getValueAt(selectedRow, 0));
        String namaBarang = String.valueOf(model.getValueAt(selectedRow, 1));
        int hargaSewa = Integer.parseInt(String.valueOf(model.getValueAt(selectedRow, 2)));
        String namaKategori = String.valueOf(model.getValueAt(selectedRow, 3));
        int stok = Integer.parseInt(String.valueOf(model.getValueAt(selectedRow, 4)));
        String status = String.valueOf(model.getValueAt(selectedRow, 5));
        int hargaBeli = Integer.parseInt(String.valueOf(model.getValueAt(selectedRow, 6)));

        // Ambil id_katalog dari nama kategori
        String idKatalog = kategoriMap.get(namaKategori);
        if (idKatalog == null) {
            JOptionPane.showMessageDialog(this, "Kategori \"" + namaKategori + "\" tidak ditemukan di kategoriMap!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Buka form ubah barang
        FormUbahBarang panel = new FormUbahBarang();
        panel.setDataBarang(idBarang, namaBarang, hargaSewa, hargaBeli, stok, status, idKatalog); // <-- kirim data

        // Listener untuk reload tabel setelah update
        panel.setBarangUpdateListener(() -> {
            load_table();
        });

        // Tampilkan form di JDialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Ubah Data Barang");
        dialog.setModal(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void btn_rusakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rusakActionPerformed
        // TODO add your handling code here:
        tambahbrgrusak();
        
        if (switchEvent != null) {
            switchEvent.switchPanel(new BarangRusak());
        }
    }//GEN-LAST:event_btn_rusakActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        int selectedRow = tbl_barang.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tbl_barang.getModel();
        String idBarang = (String) model.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            String sql = "DELETE FROM barang WHERE id_barang = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, idBarang);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            pst.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Barang sedang disewa dan tidak dapat dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_barcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_barcodeActionPerformed
        // TODO add your handling code here:
        int selectedRow = tbl_barang.getSelectedRow();
        if (selectedRow != -1) {
            String idBarang = tbl_barang.getValueAt(selectedRow, 0).toString();
            String namaBarang = tbl_barang.getValueAt(selectedRow, 1).toString();
            new DialogBarcodeGenerator(idBarang, namaBarang).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Pilih data barang terlebih dahulu!");
        }
    }//GEN-LAST:event_btn_barcodeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_barcode;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_rusak;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private palette.JTextField_Rounded search;
    private palette.JTable_Custom2 tbl_barang;
    // End of variables declaration//GEN-END:variables
}
