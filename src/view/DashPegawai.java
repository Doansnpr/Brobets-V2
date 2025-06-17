
package view;

import chart.ModelChart;
import java.awt.Color;


public class DashPegawai extends javax.swing.JPanel {

    /**
     * Creates new form DashPeegawai
     */
    public DashPegawai() {
        initComponents();
        setOpaque(false);
        init();
    }

    private void init() {
        chart.addLegend("Pendapataan", new Color(12, 84, 175), new Color(0, 108, 247));
        chart.addLegend("Pengeluaran", new Color(54, 4, 143), new Color(104, 49, 200));
        chart.addLegend("Keuntungan", new Color(5, 125, 0), new Color(95, 209, 69));
        chart.addLegend("Kerugian", new Color(186, 37, 37), new Color(241, 100, 120));
        chart.addData(new ModelChart("January", new double[]{500, 200, 80, 89}));
        chart.addData(new ModelChart("February", new double[]{1000, 750, 90, 150}));
        chart.addData(new ModelChart("March", new double[]{200, 350, 460, 900}));
        chart.addData(new ModelChart("April", new double[]{480, 150, 750, 700}));
        chart.addData(new ModelChart("May", new double[]{350, 540, 300, 150}));
        chart.addData(new ModelChart("June", new double[]{190, 280, 81, 200}));
        chart.start();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardKatalog3 = new palette.JPanelRounded();
        lb_katalog3 = new javax.swing.JLabel();
        lb_iconKatalog3 = new javax.swing.JLabel();
        lb_jumlahKatalog3 = new javax.swing.JLabel();
        cardPengembalian = new palette.JPanelRounded();
        lb_kembali = new javax.swing.JLabel();
        lb_iconKembali = new javax.swing.JLabel();
        lb_jumlahKembali = new javax.swing.JLabel();
        cardBarang = new palette.JPanelRounded();
        lb_barang = new javax.swing.JLabel();
        lb_iconBarang = new javax.swing.JLabel();
        lb_jumlahBarang = new javax.swing.JLabel();
        cardPemasok = new palette.JPanelRounded();
        lb_pemasok = new javax.swing.JLabel();
        lb_iconPemasok = new javax.swing.JLabel();
        lb_jumlahPemasok = new javax.swing.JLabel();
        cardStokMasuk = new palette.JPanelRounded();
        lb_stokmasuk = new javax.swing.JLabel();
        lb_iconStokmasuk = new javax.swing.JLabel();
        lb_jumlahStokmasuk = new javax.swing.JLabel();
        cardPelanggan = new palette.JPanelRounded();
        lb_pelanggan = new javax.swing.JLabel();
        lb_iconPelanggan = new javax.swing.JLabel();
        lb_jumlahPelanggan = new javax.swing.JLabel();
        cardPenyewaan = new palette.JPanelRounded();
        lb_sewa = new javax.swing.JLabel();
        lb_iconSewa = new javax.swing.JLabel();
        lb_jumlahSewa = new javax.swing.JLabel();
        jPanelRounded1 = new palette.JPanelRounded();
        chart = new chart.CurveChart();

        setOpaque(false);

        cardKatalog3.setBackground(new java.awt.Color(29, 51, 51));
        cardKatalog3.setRoundBottomLeft(20);
        cardKatalog3.setRoundBottomRight(20);
        cardKatalog3.setRoundTopLeft(20);
        cardKatalog3.setRoundTopRight(20);

        lb_katalog3.setBackground(new java.awt.Color(255, 255, 255));
        lb_katalog3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lb_katalog3.setForeground(new java.awt.Color(255, 255, 255));
        lb_katalog3.setText("KATALOG");

        lb_iconKatalog3.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lb_iconKatalog3.setForeground(new java.awt.Color(255, 255, 255));
        lb_iconKatalog3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-list-70.png"))); // NOI18N

        lb_jumlahKatalog3.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lb_jumlahKatalog3.setForeground(new java.awt.Color(255, 255, 255));
        lb_jumlahKatalog3.setText("7");

        javax.swing.GroupLayout cardKatalog3Layout = new javax.swing.GroupLayout(cardKatalog3);
        cardKatalog3.setLayout(cardKatalog3Layout);
        cardKatalog3Layout.setHorizontalGroup(
            cardKatalog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardKatalog3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(cardKatalog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_katalog3)
                    .addComponent(lb_jumlahKatalog3))
                .addGap(44, 44, 44)
                .addComponent(lb_iconKatalog3)
                .addGap(20, 20, 20))
        );
        cardKatalog3Layout.setVerticalGroup(
            cardKatalog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardKatalog3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(cardKatalog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_iconKatalog3)
                    .addGroup(cardKatalog3Layout.createSequentialGroup()
                        .addComponent(lb_katalog3)
                        .addGap(20, 20, 20)
                        .addComponent(lb_jumlahKatalog3)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        cardPengembalian.setBackground(new java.awt.Color(29, 51, 51));
        cardPengembalian.setRoundBottomLeft(20);
        cardPengembalian.setRoundBottomRight(20);
        cardPengembalian.setRoundTopLeft(20);
        cardPengembalian.setRoundTopRight(20);

        lb_kembali.setBackground(new java.awt.Color(255, 255, 255));
        lb_kembali.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lb_kembali.setForeground(new java.awt.Color(255, 255, 255));
        lb_kembali.setText("PENGEMBALIAN");

        lb_iconKembali.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lb_iconKembali.setForeground(new java.awt.Color(255, 255, 0));
        lb_iconKembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-return-baggage-70.png"))); // NOI18N

        lb_jumlahKembali.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lb_jumlahKembali.setForeground(new java.awt.Color(255, 244, 232));
        lb_jumlahKembali.setText("100");

        javax.swing.GroupLayout cardPengembalianLayout = new javax.swing.GroupLayout(cardPengembalian);
        cardPengembalian.setLayout(cardPengembalianLayout);
        cardPengembalianLayout.setHorizontalGroup(
            cardPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardPengembalianLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(cardPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_kembali)
                    .addComponent(lb_jumlahKembali))
                .addGap(44, 44, 44)
                .addComponent(lb_iconKembali)
                .addGap(20, 20, 20))
        );
        cardPengembalianLayout.setVerticalGroup(
            cardPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardPengembalianLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(cardPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_iconKembali)
                    .addGroup(cardPengembalianLayout.createSequentialGroup()
                        .addComponent(lb_kembali)
                        .addGap(20, 20, 20)
                        .addComponent(lb_jumlahKembali)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        cardBarang.setBackground(new java.awt.Color(29, 51, 51));
        cardBarang.setRoundBottomLeft(20);
        cardBarang.setRoundBottomRight(20);
        cardBarang.setRoundTopLeft(20);
        cardBarang.setRoundTopRight(20);

        lb_barang.setBackground(new java.awt.Color(255, 255, 255));
        lb_barang.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lb_barang.setForeground(new java.awt.Color(255, 255, 255));
        lb_barang.setText("BARANG");

        lb_iconBarang.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lb_iconBarang.setForeground(new java.awt.Color(255, 255, 0));
        lb_iconBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-nft-collection-70.png"))); // NOI18N

        lb_jumlahBarang.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lb_jumlahBarang.setForeground(new java.awt.Color(255, 244, 232));
        lb_jumlahBarang.setText("25");

        javax.swing.GroupLayout cardBarangLayout = new javax.swing.GroupLayout(cardBarang);
        cardBarang.setLayout(cardBarangLayout);
        cardBarangLayout.setHorizontalGroup(
            cardBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardBarangLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(cardBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_barang)
                    .addComponent(lb_jumlahBarang))
                .addGap(44, 44, 44)
                .addComponent(lb_iconBarang)
                .addGap(20, 20, 20))
        );
        cardBarangLayout.setVerticalGroup(
            cardBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardBarangLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(cardBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_iconBarang)
                    .addGroup(cardBarangLayout.createSequentialGroup()
                        .addComponent(lb_barang)
                        .addGap(20, 20, 20)
                        .addComponent(lb_jumlahBarang)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        cardPemasok.setBackground(new java.awt.Color(29, 51, 51));
        cardPemasok.setRoundBottomLeft(20);
        cardPemasok.setRoundBottomRight(20);
        cardPemasok.setRoundTopLeft(20);
        cardPemasok.setRoundTopRight(20);

        lb_pemasok.setBackground(new java.awt.Color(255, 255, 255));
        lb_pemasok.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lb_pemasok.setForeground(new java.awt.Color(255, 255, 255));
        lb_pemasok.setText("PEMASOK");

        lb_iconPemasok.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lb_iconPemasok.setForeground(new java.awt.Color(255, 255, 255));
        lb_iconPemasok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-supplier-70.png"))); // NOI18N

        lb_jumlahPemasok.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lb_jumlahPemasok.setForeground(new java.awt.Color(255, 255, 255));
        lb_jumlahPemasok.setText("8");

        javax.swing.GroupLayout cardPemasokLayout = new javax.swing.GroupLayout(cardPemasok);
        cardPemasok.setLayout(cardPemasokLayout);
        cardPemasokLayout.setHorizontalGroup(
            cardPemasokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardPemasokLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(cardPemasokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_pemasok)
                    .addComponent(lb_jumlahPemasok))
                .addGap(44, 44, 44)
                .addComponent(lb_iconPemasok)
                .addGap(20, 20, 20))
        );
        cardPemasokLayout.setVerticalGroup(
            cardPemasokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardPemasokLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(cardPemasokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_iconPemasok)
                    .addGroup(cardPemasokLayout.createSequentialGroup()
                        .addComponent(lb_pemasok)
                        .addGap(20, 20, 20)
                        .addComponent(lb_jumlahPemasok)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        cardStokMasuk.setBackground(new java.awt.Color(29, 51, 51));
        cardStokMasuk.setRoundBottomLeft(20);
        cardStokMasuk.setRoundBottomRight(20);
        cardStokMasuk.setRoundTopLeft(20);
        cardStokMasuk.setRoundTopRight(20);

        lb_stokmasuk.setBackground(new java.awt.Color(255, 255, 255));
        lb_stokmasuk.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lb_stokmasuk.setForeground(new java.awt.Color(255, 255, 255));
        lb_stokmasuk.setText("STOK MASUK");

        lb_iconStokmasuk.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lb_iconStokmasuk.setForeground(new java.awt.Color(255, 255, 0));
        lb_iconStokmasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-trolley-70.png"))); // NOI18N

        lb_jumlahStokmasuk.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lb_jumlahStokmasuk.setForeground(new java.awt.Color(255, 244, 232));
        lb_jumlahStokmasuk.setText("13");

        javax.swing.GroupLayout cardStokMasukLayout = new javax.swing.GroupLayout(cardStokMasuk);
        cardStokMasuk.setLayout(cardStokMasukLayout);
        cardStokMasukLayout.setHorizontalGroup(
            cardStokMasukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardStokMasukLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(cardStokMasukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_stokmasuk)
                    .addComponent(lb_jumlahStokmasuk))
                .addGap(44, 44, 44)
                .addComponent(lb_iconStokmasuk)
                .addGap(20, 20, 20))
        );
        cardStokMasukLayout.setVerticalGroup(
            cardStokMasukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardStokMasukLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(cardStokMasukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_iconStokmasuk)
                    .addGroup(cardStokMasukLayout.createSequentialGroup()
                        .addComponent(lb_stokmasuk)
                        .addGap(20, 20, 20)
                        .addComponent(lb_jumlahStokmasuk)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        cardPelanggan.setBackground(new java.awt.Color(29, 51, 51));
        cardPelanggan.setRoundBottomLeft(20);
        cardPelanggan.setRoundBottomRight(20);
        cardPelanggan.setRoundTopLeft(20);
        cardPelanggan.setRoundTopRight(20);

        lb_pelanggan.setBackground(new java.awt.Color(255, 255, 255));
        lb_pelanggan.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lb_pelanggan.setForeground(new java.awt.Color(255, 255, 255));
        lb_pelanggan.setText("PELANGGAN");

        lb_iconPelanggan.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lb_iconPelanggan.setForeground(new java.awt.Color(255, 255, 255));
        lb_iconPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-users-70.png"))); // NOI18N

        lb_jumlahPelanggan.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lb_jumlahPelanggan.setForeground(new java.awt.Color(255, 255, 255));
        lb_jumlahPelanggan.setText("30");

        javax.swing.GroupLayout cardPelangganLayout = new javax.swing.GroupLayout(cardPelanggan);
        cardPelanggan.setLayout(cardPelangganLayout);
        cardPelangganLayout.setHorizontalGroup(
            cardPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardPelangganLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(cardPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_pelanggan)
                    .addComponent(lb_jumlahPelanggan))
                .addGap(44, 44, 44)
                .addComponent(lb_iconPelanggan)
                .addGap(20, 20, 20))
        );
        cardPelangganLayout.setVerticalGroup(
            cardPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardPelangganLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(cardPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_iconPelanggan)
                    .addGroup(cardPelangganLayout.createSequentialGroup()
                        .addComponent(lb_pelanggan)
                        .addGap(20, 20, 20)
                        .addComponent(lb_jumlahPelanggan)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        cardPenyewaan.setBackground(new java.awt.Color(29, 51, 51));
        cardPenyewaan.setRoundBottomLeft(20);
        cardPenyewaan.setRoundBottomRight(20);
        cardPenyewaan.setRoundTopLeft(20);
        cardPenyewaan.setRoundTopRight(20);

        lb_sewa.setBackground(new java.awt.Color(255, 255, 255));
        lb_sewa.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lb_sewa.setForeground(new java.awt.Color(255, 255, 255));
        lb_sewa.setText("PENYEWAAN");

        lb_iconSewa.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lb_iconSewa.setForeground(new java.awt.Color(255, 255, 0));
        lb_iconSewa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-nft-share-70.png"))); // NOI18N

        lb_jumlahSewa.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lb_jumlahSewa.setForeground(new java.awt.Color(255, 244, 232));
        lb_jumlahSewa.setText("120");

        javax.swing.GroupLayout cardPenyewaanLayout = new javax.swing.GroupLayout(cardPenyewaan);
        cardPenyewaan.setLayout(cardPenyewaanLayout);
        cardPenyewaanLayout.setHorizontalGroup(
            cardPenyewaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardPenyewaanLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(cardPenyewaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_sewa)
                    .addComponent(lb_jumlahSewa))
                .addGap(44, 44, 44)
                .addComponent(lb_iconSewa)
                .addGap(20, 20, 20))
        );
        cardPenyewaanLayout.setVerticalGroup(
            cardPenyewaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardPenyewaanLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(cardPenyewaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_iconSewa)
                    .addGroup(cardPenyewaanLayout.createSequentialGroup()
                        .addComponent(lb_sewa)
                        .addGap(20, 20, 20)
                        .addComponent(lb_jumlahSewa)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanelRounded1.setBackground(new java.awt.Color(29, 51, 51));
        jPanelRounded1.setRoundBottomLeft(20);
        jPanelRounded1.setRoundBottomRight(20);
        jPanelRounded1.setRoundTopLeft(20);
        jPanelRounded1.setRoundTopRight(20);

        javax.swing.GroupLayout jPanelRounded1Layout = new javax.swing.GroupLayout(jPanelRounded1);
        jPanelRounded1.setLayout(jPanelRounded1Layout);
        jPanelRounded1Layout.setHorizontalGroup(
            jPanelRounded1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRounded1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 970, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelRounded1Layout.setVerticalGroup(
            jPanelRounded1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelRounded1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelRounded1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(cardBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(35, 35, 35)
                            .addComponent(cardPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(35, 35, 35)
                            .addComponent(cardKatalog3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(35, 35, 35)
                            .addComponent(cardPemasok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(111, 111, 111)
                            .addComponent(cardPenyewaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(35, 35, 35)
                            .addComponent(cardPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(35, 35, 35)
                            .addComponent(cardStokMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cardPemasok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardKatalog3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cardPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardPenyewaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardStokMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jPanelRounded1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private palette.JPanelRounded cardBarang;
    private palette.JPanelRounded cardKatalog3;
    private palette.JPanelRounded cardPelanggan;
    private palette.JPanelRounded cardPemasok;
    private palette.JPanelRounded cardPengembalian;
    private palette.JPanelRounded cardPenyewaan;
    private palette.JPanelRounded cardStokMasuk;
    private chart.CurveChart chart;
    private palette.JPanelRounded jPanelRounded1;
    private javax.swing.JLabel lb_barang;
    private javax.swing.JLabel lb_iconBarang;
    private javax.swing.JLabel lb_iconKatalog3;
    private javax.swing.JLabel lb_iconKembali;
    private javax.swing.JLabel lb_iconPelanggan;
    private javax.swing.JLabel lb_iconPemasok;
    private javax.swing.JLabel lb_iconSewa;
    private javax.swing.JLabel lb_iconStokmasuk;
    private javax.swing.JLabel lb_jumlahBarang;
    private javax.swing.JLabel lb_jumlahKatalog3;
    private javax.swing.JLabel lb_jumlahKembali;
    private javax.swing.JLabel lb_jumlahPelanggan;
    private javax.swing.JLabel lb_jumlahPemasok;
    private javax.swing.JLabel lb_jumlahSewa;
    private javax.swing.JLabel lb_jumlahStokmasuk;
    private javax.swing.JLabel lb_katalog3;
    private javax.swing.JLabel lb_kembali;
    private javax.swing.JLabel lb_pelanggan;
    private javax.swing.JLabel lb_pemasok;
    private javax.swing.JLabel lb_sewa;
    private javax.swing.JLabel lb_stokmasuk;
    // End of variables declaration//GEN-END:variables
}
