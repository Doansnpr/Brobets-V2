package palette;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class JTable_Custom2 extends JTable {

    private int selectedRow = -1;
    private int hoveredRow = -1;

    public JTable_Custom2() {
        setShowHorizontalLines(true);
        setGridColor(new Color(230, 230, 230)); 
        setRowHeight(40); 
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setDefaultRenderer(null); 
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                TablezHeader header = new TablezHeader(value != null ? value.toString() : "");

                if (column == 10) {
                    header.setHorizontalAlignment(JLabel.CENTER);
                }

                return header;
            }
        });

        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(noFocusBorder);
                if (isSelected) {
                    com.setForeground(new Color(15, 89, 140));
                    com.setBackground(new Color(214, 234, 248));
                } else if (row == hoveredRow) {
                    com.setBackground(new Color(48,85,85));
                    com.setForeground(Color.WHITE);
                } else {
                    if (row % 2 == 0) {
                        com.setBackground(Color.WHITE);
                    } else {
                        com.setBackground(new Color(245, 245, 245));
                    }
                    com.setForeground(new Color(102, 102, 102));
                }

                return com;
            }
        });

        
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        @Override
        public void mouseMoved(MouseEvent e) {
            int row = rowAtPoint(e.getPoint());
            if (row != hoveredRow) {
                hoveredRow = row;
                repaint();
            }
        }
    }); 
        
    addMouseListener(new MouseAdapter() {
        @Override
        public void mouseExited(MouseEvent e) {
            hoveredRow = -1;
            repaint();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int row = getSelectedRow();
            if (row == selectedRow) {
                clearSelection();
                selectedRow = -1;
            } else {
                selectedRow = row;
            }
        }
    });
  
    
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = getSelectedRow();
                if (row == selectedRow) {
                    clearSelection();
                    selectedRow = -1;
                } else {
                    selectedRow = row;
                }
            }
        });
    }

    public void addRow(Object[] row) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(row);
    }

    private class TablezHeader extends JLabel {

        public TablezHeader(String text) {
            super(text);
            setOpaque(true);
            setBackground(new Color(48,85,85));
            setForeground(new Color(255, 255, 255));
            setFont(UIManager.getFont("TableHeader.font"));
            setHorizontalAlignment(CENTER);
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(29,51,51)),
                new EmptyBorder(8, 8, 8, 8)
            ));

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(90, 0, 0));
            g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
        }
    }

}
