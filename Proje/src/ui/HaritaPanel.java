package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import model.routing.Durak;
import model.routing.DurakBaglanti;
import service.VeriYukleyici;
import service.IMesafeHesaplayici;

/**
 * HaritaPanel, harita verilerinin görselleştirildiği, durakların ve bağlantılarının çizildiği bir paneldir.
 * Bu sınıf, JPanel’den kalıtım alır ve fare etkileşimleri (tıklama, sürükleme, yakınlaştırma) ile haritayı yönetir.
 */
public class HaritaPanel extends JPanel {
    private List<Durak> duraklar;
    private double zoomFactor = 1.0;
    private double panX = 0, panY = 0;
    private int lastMouseX, lastMouseY;
    private boolean dragging = false;

    // Seçim modu için
    private boolean selectingStart = false;
    private boolean selectingEnd = false;
    private PointSelectionListener pointSelectionListener;

    // Seçilen başlangıç ve bitiş noktalarını tutan alanlar
    private Point selectedStartPoint = null;
    private Point selectedEndPoint = null;

    // 1) Mesafe hesaplama stratejisi
    private IMesafeHesaplayici mesafeHesaplayici;

    public interface PointSelectionListener {
        void pointSelected(double lat, double lon, String type);
    }

    /**
     * HaritaPanel yapıcısı, verilen JSON dosyasından durak verilerini yükler
     * ve mesafe hesaplama stratejisini dışarıdan alır.
     *
     * @param jsonDosyaYolu JSON veri dosyasının yolu
     * @param hesaplayici   Mesafe hesaplama stratejisi (örneğin HaversineHesaplayici)
     */
    public HaritaPanel(String jsonDosyaYolu, IMesafeHesaplayici hesaplayici) {
        this.duraklar = VeriYukleyici.duraklariYukle(jsonDosyaYolu);
        this.mesafeHesaplayici = hesaplayici;
        setPreferredSize(new Dimension(800, 600));

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double zoomAmount = 0.1;
                double oldZoom = zoomFactor;
                int mouseX = e.getX();
                int mouseY = e.getY();
                if (e.getWheelRotation() < 0) {
                    zoomFactor += zoomAmount;
                } else {
                    zoomFactor -= zoomAmount;
                    if (zoomFactor < 0.5) zoomFactor = 0.5;
                }
                panX = (int) (mouseX - (mouseX - panX) * (zoomFactor / oldZoom));
                panY = (int) (mouseY - (mouseY - panY) * (zoomFactor / oldZoom));
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    lastMouseX = e.getX();
                    lastMouseY = e.getY();
                    dragging = true;
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectingStart || selectingEnd) {
                    double clickX = e.getX();
                    double clickY = e.getY();
                    double xTransformed = (clickX - (getWidth() / 2.0 + panX)) / zoomFactor;
                    double yTransformed = (clickY - (getHeight() / 2.0 + panY)) / zoomFactor;
                    double lon = xTransformed / 10000 + 29.9;
                    double lat = 40.9 - yTransformed / 10000;
                    
                    if (pointSelectionListener != null) {
                        if (selectingStart) {
                            pointSelectionListener.pointSelected(lat, lon, "start");
                            setSelectedStartPoint(lat, lon);
                        } else if (selectingEnd) {
                            pointSelectionListener.pointSelected(lat, lon, "end");
                            setSelectedEndPoint(lat, lon);
                        }
                    }
                    selectingStart = false;
                    selectingEnd = false;
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    panX += e.getX() - lastMouseX;
                    panY += e.getY() - lastMouseY;
                    lastMouseX = e.getX();
                    lastMouseY = e.getY();
                    repaint();
                }
            }
        });
    }

    public void updateDuraklar(List<Durak> newDuraklar) {
        this.duraklar = newDuraklar;
        repaint();
    }

    public void setPointSelectionListener(PointSelectionListener listener) {
        this.pointSelectionListener = listener;
    }

    public void setSelectingStart(boolean flag) {
        this.selectingStart = flag;
    }

    public void setSelectingEnd(boolean flag) {
        this.selectingEnd = flag;
    }

    public void setSelectedStartPoint(double lat, double lon) {
        int x = (int) ((lon - 29.9) * 10000);
        int y = (int) ((40.9 - lat) * 10000);
        selectedStartPoint = new Point(x, y);
        repaint();
    }

    public void setSelectedEndPoint(double lat, double lon) {
        int x = (int) ((lon - 29.9) * 10000);
        int y = (int) ((40.9 - lat) * 10000);
        selectedEndPoint = new Point(x, y);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate(getWidth() / 2.0 + panX, getHeight() / 2.0 + panY);
        g2d.scale(zoomFactor, zoomFactor);

        // Durakları çiz
        for (Durak durak : duraklar) {
            int x = (int) ((durak.getLon() - 29.9) * 10000);
            int y = (int) ((40.9 - durak.getLat()) * 10000);
            g2d.setColor(Color.BLUE);
            g2d.fillOval(x - 5, y - 5, 10, 10);
            g2d.setColor(Color.BLACK);
            g2d.drawString(durak.getName(), x + 8, y);
        }

        // Durak bağlantılarını çiz (kırmızı)
        g2d.setColor(Color.RED);
        for (Durak durak : duraklar) {
            int x1 = (int) ((durak.getLon() - 29.9) * 10000);
            int y1 = (int) ((40.9 - durak.getLat()) * 10000);
            if (durak.getNextStops() != null) {
                for (DurakBaglanti baglanti : durak.getNextStops()) {
                    Durak hedefDurak = getDurakById(baglanti.getStopId());
                    if (hedefDurak != null) {
                        int x2 = (int) ((hedefDurak.getLon() - 29.9) * 10000);
                        int y2 = (int) ((40.9 - hedefDurak.getLat()) * 10000);
                        g2d.drawLine(x1, y1, x2, y2);
                    }
                }
            }
        }

        // Seçilen noktaları çiz (kırmızı çarpı)
        g2d.setColor(Color.RED);
        if (selectedStartPoint != null) {
            drawCross(g2d, selectedStartPoint.x, selectedStartPoint.y);
        }
        if (selectedEndPoint != null) {
            drawCross(g2d, selectedEndPoint.x, selectedEndPoint.y);
        }
    }

    private void drawCross(Graphics2D g2d, int x, int y) {
        int size = 10;
        g2d.drawLine(x - size, y - size, x + size, y + size);
        g2d.drawLine(x - size, y + size, x + size, y - size);
    }

    public Durak getDurakById(String id) {
        for (Durak d : duraklar) {
            if (d.getId().equals(id)) {
                return d;
            }
        }
        return null;
    }

    /**
     * findNearestStop:
     * Mesafe hesaplaması için IMesafeHesaplayici kullanıyoruz (GeoUtil yerine).
     */
    public Durak findNearestStop(double lat, double lon) {
        Durak nearest = null;
        double minDistance = Double.MAX_VALUE;
        for (Durak d : duraklar) {
            double distance = mesafeHesaplayici.hesapla(lat, lon, d.getLat(), d.getLon());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = d;
            }
        }
        return nearest;
    }
}
