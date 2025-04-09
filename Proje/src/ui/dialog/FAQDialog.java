package ui.dialog;

import ui.UlasimArayuzu;
import javax.swing.*;
import java.awt.*;

public class FAQDialog extends JDialog {
    public FAQDialog(UlasimArayuzu owner) {
        super(owner, "SSS", true);
        setLayout(new BorderLayout());
        
        String faqHTML = "<html><body style='font-family:Arial; font-size:12px;'>"
                + "<h2>SSS</h2>"
                
                // Soru 1
                + "<h3>Soru 1: Yeni bir ulaşım aracı eklemek istersek ne yapmalıyız?</h3>"
                + "<p>Mevcut sistemimiz, Open/Closed prensibi göz önünde bulundurularak tasarlanmıştır. "
                + "Yeni bir ulaşım aracı (örneğin, elektrikli scooter) eklemek için, temel 'Arac' soyut sınıfından türeyen yeni bir sınıf oluşturmanız yeterlidir. "
                + "Bu şekilde, mevcut kodda herhangi bir değişiklik yapmadan sistemi genişletebilirsiniz.</p>"
                
                // Soru 2
                + "<h3>Soru 2: Otonom taksi ve benzeri yeni ulaşım araçlarının projeye eklenmesi için ne tür değişiklikler gerekir?</h3>"
                + "<p>Otonom taksi gibi yeni araçların eklenmesi, 'Arac' hiyerarşisine yeni alt sınıfların eklenmesiyle mümkündür. "
                + "Polimorfizm sayesinde, mevcut rota hesaplama, ödeme ve diğer iş mantığı kodları hiçbir değişikliğe uğramadan yeni araçları destekleyecektir.</p>"
                
                // Soru 3
                + "<h3>Soru 3: Daha önce yazılmış fonksiyonlardan hangilerinde değişiklik yapılmalıdır?</h3>"
                + "<p>İyi tasarlanmış bir sistemde, mevcut fonksiyonlar Open/Closed prensibine uygun yazılmıştır. "
                + "Yeni gereksinimler ortaya çıktığında, yeni alt sınıflar veya stratejiler ekleyerek sistemi genişletmek, mevcut kodları değiştirmemek en sağlıklı yaklaşımdır.</p>"
                
                // Soru 4
                + "<h3>Soru 4: Nesne hiyerarşisi nasıl tanımlanmalı ki yeni araçların eklenmesi daha kolay olsun?</h3>"
                + "<p>İdeal olarak, 'Arac' soyut sınıfı altında, taksi, otobüs, tramvay gibi farklı ulaşım araçlarına ait sınıflar oluşturulmalıdır. "
                + "Her alt sınıf, kendi ücret hesaplama metodunu (hesaplaUcret) override ederek, sistemin polimorfik yapısından faydalanır. "
                + "Bu sayede, yeni bir araç eklenmesi durumunda mevcut kodda herhangi bir if-else kontrolüne ihtiyaç duyulmaz.</p>"
                
                // Soru 5
                + "<h3>Soru 5: 65 yaş ve üzeri bireyler için ücretsiz seyahat hakkının 20 seyahat ile sınırlandırılması durumunda ne yapılmalıdır?</h3>"
                + "<p>Bu tür bir değişiklik, Yolcu hiyerarşisinde ele alınmalıdır. Örneğin, 'Yaşlı' yolcu sınıfına bir sayaç ekleyip, 20 seyahatten sonra indirim oranını 0 yapabilirsiniz. "
                + "Bu değişiklik, yalnızca ilgili sınıfta (örneğin 'Yaşlı') yapılır; böylece diğer sınıflara dokunulmaz ve Open/Closed prensibi korunur.</p>"
                
                + "</body></html>";
        
        JEditorPane editorPane = new JEditorPane("text/html", faqHTML);
        editorPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(editorPane);
        add(scrollPane, BorderLayout.CENTER);
        
        JButton okButton = new JButton("Tamam");
        okButton.addActionListener(e -> setVisible(false));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setSize(600, 400);
        setLocationRelativeTo(owner);
    }
}
