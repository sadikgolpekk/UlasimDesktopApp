package ui.dialog;

import ui.UlasimArayuzu;
import model.payment.*;
import javax.swing.*;
import java.awt.*;

public class PaymentDialog extends JDialog {
    private JComboBox<String> methodComboBox;

    public PaymentDialog(UlasimArayuzu owner) {
        super(owner, "Ödeme Ayarları", true);
        // BorderLayout ile düzenleyerek üstte ödeme yöntemi paneli, altta buton paneli oluşturuyoruz.
        setLayout(new BorderLayout(5, 5));

        // Üst panel: Ödeme yöntemi seçimi
        JPanel methodPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        methodPanel.add(new JLabel("Ödeme Yöntemi:"));
        methodComboBox = new JComboBox<>(new String[] {"Nakit", "Kredi Kartı", "KentKart"});
        methodPanel.add(methodComboBox);
        add(methodPanel, BorderLayout.CENTER);

        // Alt panel: Butonlar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JButton okButton = new JButton("Tamam");
        okButton.addActionListener(e -> {
            applyPayment(owner);
            setVisible(false);
        });
        JButton cancelButton = new JButton("İptal");
        cancelButton.addActionListener(e -> setVisible(false));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(owner);
    }

    private void applyPayment(UlasimArayuzu owner) {
        String selectedMethod = (String) methodComboBox.getSelectedItem();
        PaymentMethod pm;
        switch (selectedMethod) {
            case "Nakit":
                pm = new Nakit();
                break;
            case "Kredi Kartı":
                pm = new KrediKarti();
                break;
            case "KentKart":
                pm = new KentKart();
                break;
            default:
                pm = null;
        }

        if (pm != null) {
            owner.setCurrentPaymentMethod(pm);
            JOptionPane.showMessageDialog(owner,
                "Ödeme Yöntemi: " + pm.getMethodName(),
                "Bilgi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
