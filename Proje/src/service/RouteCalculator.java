package service;

import ui.HaritaPanel;
import model.routing.Durak;
import model.routing.DurakBaglanti;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * RouteCalculator:
 * DFS ile rota bulma,
 * Edge ve RouteOption veri yapılarını barındıran
 * iş mantığı sınıfı.
 */
public class RouteCalculator {

    // DFS için sabit derinlik sınırı
    private static final int DFS_MAX_DEPTH = 20;

    private List<Durak> duraklar;
    private HaritaPanel haritaPanel;

    public RouteCalculator(List<Durak> duraklar, HaritaPanel haritaPanel) {
        this.duraklar = duraklar;
        this.haritaPanel = haritaPanel;
    }

    /**
     * computeAllRoutes:
     * baslangıc -> bitiş arasındaki tüm rota seçeneklerini döndürür.
     */
    public List<RouteOption> computeAllRoutes(Durak baslangic, Durak bitis) {
        List<RouteOption> routeOptions = new ArrayList<>();
        RouteOption currentRoute = new RouteOption();
        List<Durak> path = new ArrayList<>();
        path.add(baslangic);

        // DFS_MAX_DEPTH sabitini kullanıyoruz.
        findRoutes(baslangic, bitis, path, currentRoute, routeOptions, DFS_MAX_DEPTH);
        removeDuplicateRoutes(routeOptions);

        return routeOptions;
    }

    private void findRoutes(Durak current, Durak destination,
                            List<Durak> path,
                            RouteOption currentRoute,
                            List<RouteOption> routes,
                            int maxDepth) {
        if (areStopsEquivalent(current, destination)) {
            RouteOption copy = new RouteOption(currentRoute);
            routes.add(copy);
            return;
        }
        if (path.size() > maxDepth) return;

        for (Edge edge : getNeighbors(current)) {
            if (!isStopInPath(edge.to, path)) {
                currentRoute.edges.add(edge);
                currentRoute.totalDistance += edge.distance;
                currentRoute.totalFare += edge.fare;
                currentRoute.totalTime += edge.time;
                path.add(edge.to);

                findRoutes(edge.to, destination, path, currentRoute, routes, maxDepth);

                // Backtracking
                path.remove(path.size() - 1);
                currentRoute.edges.remove(currentRoute.edges.size() - 1);
                currentRoute.totalDistance -= edge.distance;
                currentRoute.totalFare -= edge.fare;
                currentRoute.totalTime -= edge.time;
            }
        }
    }

    private List<Edge> getNeighbors(Durak current) {
        List<Edge> neighbors = new ArrayList<>();
        // İleri yön
        if (current.getNextStops() != null) {
            for (DurakBaglanti baglanti : current.getNextStops()) {
                Durak next = haritaPanel.getDurakById(baglanti.getStopId());
                if (next != null) {
                    neighbors.add(new Edge(current, next,
                            baglanti.getMesafe(),
                            baglanti.getUcret(),
                            baglanti.getSure(),
                            false));
                }
            }
        }
        // Ters yön
        for (Durak other : duraklar) {
            if (other.getNextStops() != null) {
                for (DurakBaglanti baglanti : other.getNextStops()) {
                    if (baglanti.getStopId().equals(current.getId())) {
                        neighbors.add(new Edge(current, other,
                                baglanti.getMesafe(),
                                baglanti.getUcret(),
                                baglanti.getSure(),
                                false));
                    }
                }
            }
        }
        // Transfer
        if (current.getTransfer() != null) {
            Durak next = haritaPanel.getDurakById(current.getTransfer().getTransferStopId());
            if (next != null) {
                neighbors.add(new Edge(current, next,
                        0,
                        current.getTransfer().getTransferUcret(),
                        current.getTransfer().getTransferSure(),
                        true));
            }
        }
        // Diğer durakların transferi
        for (Durak other : duraklar) {
            if (other.getTransfer() != null && other.getTransfer().getTransferStopId().equals(current.getId())) {
                neighbors.add(new Edge(current, other,
                        0,
                        other.getTransfer().getTransferUcret(),
                        other.getTransfer().getTransferSure(),
                        true));
            }
        }
        return neighbors;
    }

    private void removeDuplicateRoutes(List<RouteOption> routeOptions) {
        Set<String> routeSignatures = new HashSet<>();
        Iterator<RouteOption> it = routeOptions.iterator();
        while (it.hasNext()) {
            RouteOption ro = it.next();
            String signature = buildRouteSignature(ro);
            if (routeSignatures.contains(signature)) {
                it.remove();
            } else {
                routeSignatures.add(signature);
            }
        }
    }

    public String buildRouteSignature(RouteOption ro) {
        StringBuilder sb = new StringBuilder();
        for (Edge e : ro.edges) {
            sb.append(e.from.getId()).append("->");
        }
        if (!ro.edges.isEmpty()) {
            sb.append(ro.edges.get(ro.edges.size() - 1).to.getId());
        }
        return sb.toString();
    }

    // Yardımcı metotlar
    private boolean areStopsEquivalent(Durak d1, Durak d2) {
        double tol = 0.002;
        return (Math.abs(d1.getLat() - d2.getLat()) < tol &&
                Math.abs(d1.getLon() - d2.getLon()) < tol);
    }

    private boolean isStopInPath(Durak stop, List<Durak> path) {
        for (Durak p : path) {
            if (areStopsEquivalent(stop, p)) return true;
        }
        return false;
    }

    // ----- Veri Yapıları -----
    public static class Edge {
        public Durak from;
        public Durak to;
        public double distance;
        public double fare;
        public double time;
        public boolean isTransfer;

        public Edge(Durak from, Durak to,
                    double distance, double fare, double time,
                    boolean isTransfer) {
            this.from = from;
            this.to = to;
            this.distance = distance;
            this.fare = fare;
            this.time = time;
            this.isTransfer = isTransfer;
        }
    }

    public static class RouteOption {
        public List<Edge> edges = new ArrayList<>();
        public double totalDistance = 0;
        public double totalFare = 0;
        public double totalTime = 0;

        public RouteOption() {}

        // Copy constructor
        public RouteOption(RouteOption other) {
            this.edges.addAll(other.edges);
            this.totalDistance = other.totalDistance;
            this.totalFare = other.totalFare;
            this.totalTime = other.totalTime;
        }
    }
}
