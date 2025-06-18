package palette;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class JComboBox_Rounded<E> extends JComboBox<E> {

    private final RoundedBorder border;

    public JComboBox_Rounded() {
        setOpaque(false);
        setFocusable(true);
        setBackground(new Color(0, 0, 0, 0));
        setForeground(Color.WHITE);
        border = new RoundedBorder(10);
        setBorder(border);
        setUI(new RoundedComboBoxUI());
    }

    private class RoundedComboBoxUI extends BasicComboBoxUI {

        @Override
        protected JButton createArrowButton() {
            JButton button = new JButton("▼");
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setForeground(Color.WHITE);
            button.setOpaque(false);
            return button;
        }

        @Override
        protected void installDefaults() {
            super.installDefaults();
            comboBox.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
                JLabel label = new JLabel(value == null ? "" : value.toString());
                label.setOpaque(true);
                label.setBackground(isSelected ? new Color(60, 100, 100) : new Color(40, 70, 70));
                label.setForeground(comboBox.getForeground());
                label.setFont(comboBox.getFont());
                return label;
            });
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            // HANYA gambar background custom, TANPA super.paint()
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(40, 70, 70));
            g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), border.getRound(), border.getRound());
            g2.dispose();
        }

        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            // Biarkan kosong, biar tidak timpa background
        }

        @Override
        public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(40, 70, 70));  // warna latar
            g2.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, border.getRound(), border.getRound());
            g2.dispose();

            @SuppressWarnings("unchecked")
            JComboBox<E> cb = (JComboBox<E>) comboBox;

            @SuppressWarnings("unchecked")
            JList<E> dummyList = (JList<E>) new JList<>();

            ListCellRenderer<? super E> renderer = cb.getRenderer();
            Component c = renderer.getListCellRendererComponent(
                dummyList,
                (E) cb.getSelectedItem(), // cast aman
                -1,
                false,
                false
            );

            c.setFont(cb.getFont());
            c.setForeground(cb.getForeground());
            c.setBackground(new Color(0, 0, 0, 0)); // transparan

            SwingUtilities.paintComponent(g, c, cb, bounds);
        }

    }

    private class RoundedBorder extends EmptyBorder {
        private Color color = new Color(0, 128, 128);
        private Color focusColor = Color.CYAN;
        private int round;

        public RoundedBorder(int round) {
            super(4, 8, 4, 8);
            this.round = round;
        }

        public int getRound() {
            return round;
        }

        public void setRound(int round) {
            this.round = round;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.isFocusOwner() ? focusColor : color);
            g2.drawRoundRect(x, y, width - 1, height - 1, round, round);
            g2.dispose();
        }
    }
}