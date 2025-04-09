package ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.passanger.*;
import model.payment.*;
import model.routing.Durak;
import service.VeriYukleyici;
import service.IMesafeHesaplayici;
import service.ManhattanHesaplayici;
import service.HaversineHesaplayici;   // Varsayılan olarak Haversine
import service.RouteService;
import ui.dialog.*;

/**
 * UlasimArayuzu sınıfı, ana kullanıcı arayüzünü (GUI) oluşturur.
 */
public class UlasimArayuzu extends JFrame {
    private Durak baslangicDurak, bitisDurak;
    private List<Durak> duraklar;

    private double startLat, startLon, endLat, endLon;

    // Değişiklik 1: JTextArea yerine JEditorPane
    private JEditorPane rotaBilgiPane;
    private JButton baslangicButton, bitisButton, hesaplaButton;
    private JLabel baslangicLabel, bitisLabel;
    private HaritaPanel haritaPanel;

    // çok biçimlilik örneği 
    private IYolcu selectedYolcu = new GenelYolcu(); // varsayılan yolcu

    private PaymentMethod currentPaymentMethod = null;

    // Mesafe hesaplamasını hangi stratejiyle yapacağımızı tutan field:
    private IMesafeHesaplayici mesafeHesaplayici;

    
    public UlasimArayuzu() { // varsayilan  constructor
        this(new HaversineHesaplayici()); // alternatifi ManhattanHesaplayici
    }
    
    // Constructor: Dışarıdan hangi mesafe algoritmasını kullanacağımızı alıyoruz
    public UlasimArayuzu(IMesafeHesaplayici hesaplayici) {
        setTitle("İzmit Ulaşım Rota Planlama");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Mesafe hesaplayıcı nesnesini saklıyoruz
        this.mesafeHesaplayici = hesaplayici;

       // MENÜ ÇUBUĞU
        JMenuBar menuBar = new JMenuBar();
            
        // 1) Dosya Menüsü
        JMenu fileMenu = new JMenu("Dosya");
        JMenuItem exitItem = new JMenuItem("Çıkış");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
            
        // 2) Ayarlar Menüsü
        JMenu settingsMenu = new JMenu("Ayarlar");
            
        // a) Tercihler
        JMenuItem preferencesItem = new JMenuItem("Tercihler");
        preferencesItem.addActionListener(e -> {
            PreferencesDialog dialog = new PreferencesDialog(UlasimArayuzu.this);
            dialog.setVisible(true);
        });
        settingsMenu.add(preferencesItem);
        
        // b) Profil Ayarları
        JMenuItem profileItem = new JMenuItem("Profil Ayarları");
        profileItem.addActionListener(e -> {
            ProfileDialog pd = new ProfileDialog(UlasimArayuzu.this);
            pd.setVisible(true);
        });
        settingsMenu.add(profileItem);
        
        // c) Ödeme Ayarları
        JMenuItem paymentItem = new JMenuItem("Ödeme Ayarları");
        paymentItem.addActionListener(e -> {
            PaymentDialog payDlg = new PaymentDialog(UlasimArayuzu.this);
            payDlg.setVisible(true);
        });
        settingsMenu.add(paymentItem);
        
        menuBar.add(settingsMenu);
        
        // 3) Yardım Menüsü
        JMenu helpMenu = new JMenu("Yardım");
        
        // a) SSS
        JMenuItem faqItem = new JMenuItem("SSS");
        faqItem.addActionListener(e -> {
            FAQDialog faq = new FAQDialog(UlasimArayuzu.this);
            faq.setVisible(true);
        });
        helpMenu.add(faqItem);
        
        // b) Hakkında
        JMenuItem aboutItem = new JMenuItem("Hakkında");
        aboutItem.addActionListener(e -> {
            AboutDialog aboutDialog = new AboutDialog(UlasimArayuzu.this);
            aboutDialog.setVisible(true);
        });
        helpMenu.add(aboutItem);
        
        menuBar.add(helpMenu);
        
        // Menü çubuğunu pencereye ekle
        setJMenuBar(menuBar);


        // VERİ YÜKLEME
        duraklar = VeriYukleyici.duraklariYukle("data/veriseti.json");

        // HARİTA PANELİ
        haritaPanel = new HaritaPanel("data/veriseti.json",this.mesafeHesaplayici); // istedigimiz parametre

        haritaPanel.setOpaque(false);
        add(haritaPanel, BorderLayout.CENTER);

        // SAĞ PANEL (BİLGİ PANELİ)
        JPanel bilgiPanel = new JPanel();
        bilgiPanel.setLayout(new BoxLayout(bilgiPanel, BoxLayout.Y_AXIS));
        bilgiPanel.setBackground(new Color(230, 240, 255, 200));
        bilgiPanel.setPreferredSize(new Dimension(600, getHeight()));

        JLabel bilgiLabel = new JLabel("Rota Bilgileri");
        bilgiLabel.setFont(new Font("Arial", Font.BOLD, 30));
        bilgiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bilgiLabel.setHorizontalAlignment(SwingConstants.CENTER);

        baslangicButton = new JButton("Başlangıç Noktasını Seç");
        baslangicLabel  = new JLabel("Başlangıç: Henüz Seçilmedi");
        bitisButton     = new JButton("Bitiş Noktasını Seç");
        bitisLabel      = new JLabel("Bitiş: Henüz Seçilmedi");
        hesaplaButton   = new JButton("Rota Hesapla");

        // Değişiklik 2: Rota bilgisini gösterecek alanda text/html ayarlama
        rotaBilgiPane = new JEditorPane("text/html", "");
        rotaBilgiPane.setEditable(false);
       JScrollPane scrollPane = new JScrollPane(rotaBilgiPane);
        scrollPane.setPreferredSize(new Dimension(580, 300));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        bilgiPanel.add(Box.createVerticalStrut(10));
        bilgiPanel.add(bilgiLabel);
        bilgiPanel.add(Box.createVerticalStrut(10));
        bilgiPanel.add(baslangicButton);
        bilgiPanel.add(baslangicLabel);
        bilgiPanel.add(Box.createVerticalStrut(10));
        bilgiPanel.add(bitisButton);
        bilgiPanel.add(bitisLabel);
        bilgiPanel.add(Box.createVerticalStrut(10));
        bilgiPanel.add(hesaplaButton);
        bilgiPanel.add(Box.createVerticalStrut(10));
        bilgiPanel.add(scrollPane);
        bilgiPanel.add(Box.createVerticalStrut(10));
        add(bilgiPanel, BorderLayout.EAST);



        baslangicButton.setToolTipText("Harita üzerinde başlangıç noktasını seçmek için tıklayın.");
        bitisButton.setToolTipText("Harita üzerinde bitiş noktasını seçmek için tıklayın.");
        hesaplaButton.setToolTipText("Seçilen noktalara göre en uygun rotayı hesaplar.");


        JLabel statusBar = new JLabel("Hazır");
        statusBar.setFont(new Font("Arial", Font.ITALIC, 12));
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        add(statusBar, BorderLayout.SOUTH);

        // HARİTA PANELİ SEÇİM LİSTENER
        haritaPanel.setPointSelectionListener((lat, lon, type) -> {
            if ("start".equals(type)) {
                startLat = lat;
                startLon = lon;
                Durak nearest = haritaPanel.findNearestStop(lat, lon);
                if (nearest == null) {
                    JOptionPane.showMessageDialog(UlasimArayuzu.this,
                        "Bu bölgede durak bulunamadı!",
                        "Uyarı", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                baslangicDurak = nearest;
                baslangicLabel.setText("Başlangıç: " + nearest.getName());
                
                // Mesafe hesaplama (Artık GeoUtil yok, IMesafeHesaplayici var!)
                double mesafe = mesafeHesaplayici.hesapla(
                        lat, lon, nearest.getLat(), nearest.getLon());
                
                if (mesafe > 3) {
                    JOptionPane.showMessageDialog(UlasimArayuzu.this,
                        "Başlangıç noktanız durağa 3 km'den uzak. Taksi kullanılacak!");
                }
                haritaPanel.setSelectedStartPoint(lat, lon);
            } else if ("end".equals(type)) {
                endLat = lat;
                endLon = lon;
                Durak nearest = haritaPanel.findNearestStop(lat, lon);
                if (nearest == null) {
                    JOptionPane.showMessageDialog(UlasimArayuzu.this,
                        "Bu bölgede durak bulunamadı!",
                        "Uyarı", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                bitisDurak = nearest;
                bitisLabel.setText("Bitiş: " + nearest.getName());
                
                // Mesafe hesaplama
                double mesafe = mesafeHesaplayici.hesapla(
                        lat, lon, nearest.getLat(), nearest.getLon());
                
                if (mesafe > 3) {
                    JOptionPane.showMessageDialog(UlasimArayuzu.this,
                        "Bitiş noktanız durağa 3 km'den uzak. Taksi kullanılacak!");
                }
                haritaPanel.setSelectedEndPoint(lat, lon);
            }
        });

        // BUTON İŞLEMLERİ
        baslangicButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(UlasimArayuzu.this,
                    "Lütfen haritada başlangıç noktasını seçmek için tıklayınız.");
            haritaPanel.setSelectingStart(true);
        });

        bitisButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(UlasimArayuzu.this,
                    "Lütfen haritada bitiş noktasını seçmek için tıklayınız.");
            haritaPanel.setSelectingEnd(true);
        });

        hesaplaButton.addActionListener(e -> {
            if (baslangicDurak == null || bitisDurak == null) {
                JOptionPane.showMessageDialog(UlasimArayuzu.this,
                        "Lütfen hem başlangıç hem de bitiş noktasını seçiniz.");
            } else {
                statusBar.setText("Rota hesaplanıyor...");
                computeRoutes();
                statusBar.setText("Hesaplama tamamlandı.");
            }
        });
    }

    private void computeRoutes() {
    if (baslangicDurak == null || bitisDurak == null) return;

    // Servis sınıfını çağırarak HTML çıktısını alıyoruz
    String htmlOutput = RouteService.computeRoutesHTML(
            baslangicDurak, bitisDurak,
            startLat, startLon, endLat, endLon,
            mesafeHesaplayici, currentPaymentMethod, selectedYolcu,
            duraklar, haritaPanel
    );

    // JEditorPane'e HTML çıktısını setliyoruz
    rotaBilgiPane.setText(htmlOutput);
}

    // Diyalogların erişimi için setter metotlar
    public void setSelectedYolcu(IYolcu yolcu) {
        this.selectedYolcu = yolcu;
    }
    public void setCurrentPaymentMethod(PaymentMethod pm) {
        this.currentPaymentMethod = pm;
    }

    // main metodunda hangi hesaplayıcıyı kullanmak istediğimizi belirtiyoruz.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Varsayılan olarak HaversineHesaplayici kullanarak başlatabilirsiniz:
            UlasimArayuzu ui = new UlasimArayuzu(); 
            ui.setVisible(true);
        });
    }
}
