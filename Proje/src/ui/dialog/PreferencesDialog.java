package ui.dialog;

import ui.UlasimArayuzu;
import javax.swing.*;
import java.awt.*;

public class PreferencesDialog extends JDialog {
    private JComboBox<String> themeComboBox;

    public PreferencesDialog(UlasimArayuzu owner) {
        super(owner, "Tercihler", true);
        setLayout(new BorderLayout());

        String[] themes = {"Light", "Dark", "Night", "Aqua", "Solarized"};
        JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
        panel.add(new JLabel("Tema:"));
        themeComboBox = new JComboBox<>(themes);
        panel.add(themeComboBox);
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("Tamam");
        JButton cancelButton = new JButton("İptal");
        okButton.addActionListener(e -> {
            applyPreferences(owner);
            setVisible(false);
        });
        cancelButton.addActionListener(e -> setVisible(false));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(owner);
    }

    private void applyPreferences(UlasimArayuzu owner) {
        String selectedTheme = (String) themeComboBox.getSelectedItem();
        switch (selectedTheme) {
            case "Dark":
                owner.getContentPane().setBackground(Color.DARK_GRAY);
                break;
            case "Night":
                owner.getContentPane().setBackground(new Color(10, 10, 50));
                break;
            case "Aqua":
                owner.getContentPane().setBackground(new Color(0, 200, 200));
                break;
            case "Solarized":
                owner.getContentPane().setBackground(new Color(238, 232, 213));
                break;
            default:
                owner.getContentPane().setBackground(null);
                break;
        }
        owner.repaint();
    }
}
