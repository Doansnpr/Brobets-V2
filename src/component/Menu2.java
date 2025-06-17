
package component;

import event.MenuEvent;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import swing.MenuButton;
import swing.Split;

public class Menu2 extends javax.swing.JPanel {
    
    private MenuButton selectedMenu;
    private MenuButton unSelectedMenu;
    private Animator animator;
    private MenuEvent event;

    public Menu2() {
        initComponents();
        setOpaque(false);
        scroll.setViewportBorder(null);
        scroll.setBorder(null);
        scroll.getViewport().setOpaque(false);
        panelMenu.setLayout(new MigLayout("wrap, fillx, inset 0", "[fill]"));
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                selectedMenu.setAnimate(fraction);
                if (unSelectedMenu != null) {
                    unSelectedMenu.setAnimate(1f - fraction);
                }
            }
        };
        animator = new Animator(300, target);
        animator.setAcceleration(.5f);
        animator.setDeceleration(.5f);
        animator.setResolution(0);
    }
    
    public void initMenu(MenuEvent event) {
        this.event = event;
        addMenu("icon_dash", "Dashboard", 0);
        split("Master");;
        addMenu("icon_pelanggan", "Pengguna", 1);
        addMenu("icon_katalog", "Laporan", 2);
        space();
        addMenu("icon_logout", "Logout", 3);
    }

    private void addMenu(String icon, String text, int index) {
        MenuButton menu = new MenuButton(index);
        setFont(menu.getFont().deriveFont(Font.PLAIN, 15));
        menu.setIcon(new ImageIcon(getClass().getResource("/assets/" + icon + ".png")));
        menu.setText("  " + text);
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!animator.isRunning()) {
                    if (menu != selectedMenu) {
                        unSelectedMenu = selectedMenu;
                        selectedMenu = menu;
                        animator.start();
                    }
                }
                if (event != null) {
                event.menuSelected(menu.getIndex());
            }
            }
        });
        panelMenu.add(menu);
    }
    
    private void space() {
        panelMenu.add(new JLabel(), "push");
    }
    
    private void split(String name) {
        panelMenu.add(new Split(name), "h 30");
    }
    
    public void setSelected(int index) {
        for (Component com : panelMenu.getComponents()) {
            MenuButton menu = (MenuButton) com;
            if (menu.getIndex() == index) {
                if (menu != selectedMenu) {
                    unSelectedMenu = selectedMenu;
                    selectedMenu = menu;
                    animator.start();
                    event.menuSelected(index);
                }
                break;
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        image = new swing.ImageAvatar();
        scroll = new javax.swing.JScrollPane();
        panelMenu = new javax.swing.JPanel();

        image.setBorderSize(3);
        image.setBorderSpace(2);
        image.setGradientColor1(new java.awt.Color(0, 0, 255));
        image.setGradientColor2(new java.awt.Color(255, 0, 0));
        image.setImage(new javax.swing.ImageIcon(getClass().getResource("/assets/Logo Fix.png"))); // NOI18N

        panelMenu.setOpaque(false);

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 228, Short.MAX_VALUE)
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 397, Short.MAX_VALUE)
        );

        scroll.setViewportView(panelMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(image, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scroll)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(scroll))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.ImageAvatar image;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JScrollPane scroll;
    // End of variables declaration//GEN-END:variables
}
