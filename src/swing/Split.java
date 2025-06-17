package swing;

public class Split extends javax.swing.JPanel {

    public Split(String name) {
        initComponents();
        label.setText(name);
        setOpaque(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label = new javax.swing.JLabel();
        labelSplit1 = new swing.LabelSplit();
        labelSplit2 = new swing.LabelSplit();

        label.setForeground(new java.awt.Color(51, 167, 167));
        label.setText("Name");

        labelSplit1.setBackground(new java.awt.Color(51, 167, 167));

        labelSplit2.setBackground(new java.awt.Color(51, 167, 167));
        labelSplit2.setForeground(new java.awt.Color(51, 167, 167));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(labelSplit1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelSplit2, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
            .addComponent(labelSplit2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelSplit1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel label;
    private swing.LabelSplit labelSplit1;
    private swing.LabelSplit labelSplit2;
    // End of variables declaration//GEN-END:variables
}
