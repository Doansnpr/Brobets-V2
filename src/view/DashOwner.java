
package view;

import chart.ModelChart;
import java.awt.Color;

/**
 *
 * @author USER
 */
public class DashOwner extends javax.swing.JPanel {

   
    public DashOwner() {
        initComponents();
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

        cardBarang = new palette.JPanelRounded();
        lb_barang = new javax.swing.JLabel();
        lb_iconBarang = new javax.swing.JLabel();
        lb_jumlahBarang = new javax.swing.JLabel();
        cardPelanggan = new palette.JPanelRounded();
        lb_pelanggan = new javax.swing.JLabel();
        lb_iconPelanggan = new javax.swing.JLabel();
        lb_jumlahPelanggan = new javax.swing.JLabel();
        jPanelRounded1 = new palette.JPanelRounded();
        chart = new chart.CurveChart();

        setOpaque(false);

        cardBarang.setBackground(new java.awt.Color(29, 51, 51));
        cardBarang.setRoundBottomLeft(20);
        cardBarang.setRoundBottomRight(20);
        cardBarang.setRoundTopLeft(20);
        cardBarang.setRoundTopRight(20);

        lb_barang.setBackground(new java.awt.Color(255, 255, 255));
        lb_barang.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lb_barang.setForeground(new java.awt.Color(255, 255, 255));
        lb_barang.setText("PENGGUNA");

        lb_iconBarang.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lb_iconBarang.setForeground(new java.awt.Color(255, 255, 0));
        lb_iconBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-users-70.png"))); // NOI18N

        lb_jumlahBarang.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lb_jumlahBarang.setForeground(new java.awt.Color(255, 244, 232));
        lb_jumlahBarang.setText("5");

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

        cardPelanggan.setBackground(new java.awt.Color(29, 51, 51));
        cardPelanggan.setRoundBottomLeft(20);
        cardPelanggan.setRoundBottomRight(20);
        cardPelanggan.setRoundTopLeft(20);
        cardPelanggan.setRoundTopRight(20);

        lb_pelanggan.setBackground(new java.awt.Color(255, 255, 255));
        lb_pelanggan.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lb_pelanggan.setForeground(new java.awt.Color(255, 255, 255));
        lb_pelanggan.setText("LAPORAN");

        lb_iconPelanggan.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lb_iconPelanggan.setForeground(new java.awt.Color(255, 255, 255));
        lb_iconPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icons8-list-70.png"))); // NOI18N

        lb_jumlahPelanggan.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lb_jumlahPelanggan.setForeground(new java.awt.Color(255, 255, 255));
        lb_jumlahPelanggan.setText("4");

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
                .addGap(26, 26, 26)
                .addComponent(cardBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(cardPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(545, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(jPanelRounded1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(26, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cardPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(484, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(176, 176, 176)
                    .addComponent(jPanelRounded1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(176, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private palette.JPanelRounded cardBarang;
    private palette.JPanelRounded cardPelanggan;
    private chart.CurveChart chart;
    private palette.JPanelRounded jPanelRounded1;
    private javax.swing.JLabel lb_barang;
    private javax.swing.JLabel lb_iconBarang;
    private javax.swing.JLabel lb_iconPelanggan;
    private javax.swing.JLabel lb_jumlahBarang;
    private javax.swing.JLabel lb_jumlahPelanggan;
    private javax.swing.JLabel lb_pelanggan;
    // End of variables declaration//GEN-END:variables
}
