package service;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import model.routing.Durak;
import model.routing.DurakBaglanti;
import model.routing.DurakTransfer;

public class VeriYukleyici {
    public static List<Durak> duraklariYukle(String dosyaYolu) {
        List<Durak> durakListesi = new ArrayList<>();
        try {
            // JSON Dosyasını tamamen oku
            String icerik = new String(Files.readAllBytes(Paths.get(dosyaYolu)));
            JSONObject json = new JSONObject(icerik);
            JSONArray duraklar = json.getJSONArray("duraklar");

            System.out.println("\nJSON'dan yüklenen duraklar:");

            for (int i = 0; i < duraklar.length(); i++) {
                JSONObject durakJson = duraklar.getJSONObject(i);
                String name = durakJson.getString("name");
                String id = durakJson.getString("id");
                double lat = durakJson.getDouble("lat");
                double lon = durakJson.getDouble("lon");
                List<DurakBaglanti> baglantilar = new ArrayList<>();
                DurakTransfer transfer = null;

                if (durakJson.has("nextStops")) {
                    JSONArray nextStops = durakJson.getJSONArray("nextStops");
                    for (int j = 0; j < nextStops.length(); j++) {
                        JSONObject baglantiJson = nextStops.getJSONObject(j);
                        baglantilar.add(new DurakBaglanti(
                            baglantiJson.getString("stopId"),
                            baglantiJson.getDouble("mesafe"),
                            baglantiJson.getInt("sure"),
                            baglantiJson.getDouble("ucret")
                        ));
                    }
                }

                if (durakJson.has("transfer") && !durakJson.isNull("transfer")) {
                    try {
                        JSONObject transferJson = durakJson.getJSONObject("transfer");
                        transfer = new DurakTransfer(
                            transferJson.getString("transferStopId"),
                            transferJson.getInt("transferSure"),
                            transferJson.getDouble("transferUcret")
                        );
                    } catch (Exception e) {
                        System.out.println("Uyarı: " + name + " durağında geçersiz transfer verisi var.");
                    }
                }

                Durak durak = new Durak(id, name, lat, lon, baglantilar, transfer);
                durakListesi.add(durak);

                System.out.println("- " + name + " (" + id + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return durakListesi;
    }
}
