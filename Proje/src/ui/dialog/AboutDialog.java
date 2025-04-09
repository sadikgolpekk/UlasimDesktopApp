package ui.dialog;

import ui.UlasimArayuzu;
import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {
    public AboutDialog(JFrame owner) {
        super(owner, "Hakkında", true);
        setLayout(new BorderLayout());

        // Hakkında ve SOLID prensipleri açıklamalarını içeren HTML metni
        String aboutHTML = "<html>"
                + "<h2 style='text-align:center;'>Ulaşım Rota Planlama v1.0</h2>"
                + "<p style='text-align:justify;'>Bu uygulama, en uygun güzergahı hesaplayarak kullanıcıya sunar. "
                + "Proje, nesne yönelimli programlama (OOP) prensipleri, özellikle SOLID prensipleri (tek sorumluluk, "
                + "açık/kapalı, Liskov yerine geçme, arayüz ayrımı, bağımlılıkların tersine çevrilmesi) göz önünde bulundurularak geliştirilmiştir. "
                + "Bu sayede, sistem modüler, genişletilebilir ve bakım yapılması kolay bir yapıya sahiptir.</p>"
                + "<h3>SOLID Prensipleri</h3>"
                + "<ul>"
                + "<li><b>S - Single Responsibility Principle:</b> Her sınıfın yalnızca bir sorumluluğu olmalı; yani bir sınıfın değiştirilmesi yalnızca bir nedene bağlı olmalıdır.</li>"
                + "<li><b>O - Open/Closed Principle:</b> Sınıflar genişlemeye açık, ancak mevcut davranışları değiştirmeden yeni işlevler eklenebilmelidir.</li>"
                + "<li><b>L - Liskov Substitution Principle:</b> Türetilmiş sınıflar, taban sınıflarının yerine geçebilmeli ve beklenen davranışı sergilemelidir.</li>"
                + "<li><b>I - Interface Segregation Principle:</b> Bir sınıf, kullanmadığı metodları içeren geniş arayüzler yerine, daha küçük ve spesifik arayüzleri implement etmelidir.</li>"
                + "<li><b>D - Dependency Inversion Principle:</b> Yüksek seviyeli modüller, düşük seviyeli modüllere bağlı olmamalı; her ikisi de soyutlamalara bağlı olmalıdır.</li>"
                + "</ul>"
                + "</html>";

        // HTML içeriğini gösterecek JEditorPane oluşturulur
        JEditorPane editorPane = new JEditorPane("text/html", aboutHTML);
        editorPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(editorPane);
        add(scrollPane, BorderLayout.CENTER);

        // Tamam butonu oluşturulur, tıklandığında diyalog kapanır.
        JButton okButton = new JButton("Tamam");
        okButton.addActionListener(e -> setVisible(false));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(500, 400);
        setLocationRelativeTo(owner);
    }
}
