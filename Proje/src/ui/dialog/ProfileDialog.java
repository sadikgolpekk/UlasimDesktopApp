package ui.dialog;

import ui.UlasimArayuzu;
import model.passanger.*;
import javax.swing.*;
import java.awt.*;

public class ProfileDialog extends JDialog {
    private JComboBox<String> profileComboBox;

    public ProfileDialog(UlasimArayuzu owner) {
        super(owner, "Profil Ayarları", true);
        setLayout(new BorderLayout());

        String[] profiles = {"Genel", "Öğrenci", "Yaşlı", "Öğretmen","Engelli"}; // ekleme buradan olabilir.
        JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
        panel.add(new JLabel("Profil:"));
        profileComboBox = new JComboBox<>(profiles);
        panel.add(profileComboBox);
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("Tamam");
        JButton cancelButton = new JButton("İptal");
        okButton.addActionListener(e -> {
            applyProfile(owner);
            setVisible(false);
        });
        cancelButton.addActionListener(e -> setVisible(false));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(owner);
    }

    private void applyProfile(UlasimArayuzu owner) {
        String selectedProfile = (String) profileComboBox.getSelectedItem();
        
        // 1) Artık tipi IYolcu olarak tanımlıyoruz
        IYolcu selectedYolcu;
        
        switch (selectedProfile) { // buraya ekleme yapılabilir
            case "Öğrenci":
                selectedYolcu = new Ogrenci();
                break;
            case "Yaşlı":
                selectedYolcu = new Yasli();
                break;
            case "Öğretmen":
                selectedYolcu = new Ogretmen();
                break;
            case "Engelli":
                selectedYolcu = new Engelli();
                break;
            default:
                selectedYolcu = new GenelYolcu();
        }
        
        // 2) UlasimArayuzu içindeki setSelectedYolcu da IYolcu parametresi almalıdır
        owner.setSelectedYolcu(selectedYolcu);

        JOptionPane.showMessageDialog(owner,
                "Profil " + selectedProfile + " olarak ayarlandı.",
                "Bilgi", JOptionPane.INFORMATION_MESSAGE);
    }
}
