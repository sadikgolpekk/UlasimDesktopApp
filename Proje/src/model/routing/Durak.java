package model.routing;

import java.util.List;

public class Durak {
    private String id;
    private String name;
    private double lat;
    private double lon;
    private List<DurakBaglanti> nextStops;
    private DurakTransfer transfer;

    // **Ana Yapıcı (Tüm Değerlerle)**
    public Durak(String id, String name, double lat, double lon, List<DurakBaglanti> nextStops, DurakTransfer transfer) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.nextStops = nextStops;
        this.transfer = transfer;
    }

    // **Yeni: Eksik Parametreler İçin Alternatif Yapıcı**
    public Durak(String id, String name, double lat, double lon) {
        this(id, name, lat, lon, null, null);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getLat() { return lat; }
    public double getLon() { return lon; }
    public List<DurakBaglanti> getNextStops() { return nextStops; }
    public DurakTransfer getTransfer() { return transfer; }
}
