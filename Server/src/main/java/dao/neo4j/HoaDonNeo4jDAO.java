package dao.neo4j;

import model.HoaDon;
import org.neo4j.driver.*;
import org.neo4j.driver.summary.ResultSummary;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HoaDonNeo4jDAO {
    private Driver driver;
    private SessionConfig sessionConfig;

    public HoaDonNeo4jDAO(Driver driver, String databasename) {
        this.driver = driver;
        this.sessionConfig = SessionConfig.builder().withDatabase(databasename).build();
    }

    public boolean themHoaDon(HoaDon hoaDon) {
        String query = "CREATE (hd:HoaDon) " +
                "SET hd.maHoaDon = $maHoaDon, " +
                "hd.ngayLapHoaDon = date($ngayLapHoaDon), " +
                "hd.diemTichLuySuDung = $diemTichLuySuDung, " +
                "hd.tongTien = $tongTien, " +
                "hd.ghiChu = $ghiChu";
        Map<String, Object> params = Map.of(
                "maHoaDon", hoaDon.getMaHoaDon(),
                "ngayLapHoaDon", hoaDon.getNgayLapHoaDon().toString(),
                "diemTichLuySuDung", hoaDon.getDiemTichLuySuDung(),
                "tongTien", hoaDon.getTongTien(),
                "ghiChu", hoaDon.getGhiChu()
        );

        try (var session = driver.session(sessionConfig)) {
            return session.executeWrite(tx -> {
                ResultSummary result = tx.run(query, params).consume();
                return result.counters().nodesCreated() > 0;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaHoaDon(String maHoaDon) {
        String query = "MATCH (hd:HoaDon {maHoaDon: $maHoaDon}) DETACH DELETE hd";
        Map<String, Object> params = Map.of("maHoaDon", maHoaDon);

        try (var session = driver.session(sessionConfig)) {
            return session.executeWrite(tx -> {
                ResultSummary summary = tx.run(query, params).consume();
                return summary.counters().nodesDeleted() > 0;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public HoaDon findById(String maHoaDon) {
        String query = "MATCH (hd:HoaDon {maHoaDon: $maHoaDon}) RETURN hd";
        Map<String, Object> params = Map.of("maHoaDon", maHoaDon);

        try (var session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                if (result.hasNext()) {
                    var node = result.next().get("hd").asNode();
                    return new HoaDon(
                            maHoaDon,
                            LocalDate.parse(node.get("ngayLapHoaDon").asString()),
                            node.get("diemTichLuySuDung").asInt(),
                            node.get("tongTien").asDouble(),
                            node.get("ghiChu").asString(null)
                    );
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HoaDon> findAll() {
        String query = "MATCH (hd:HoaDon) RETURN hd";
        List<HoaDon> list = new ArrayList<>();

        try (var session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query);
                while (result.hasNext()) {
                    var node = result.next().get("hd").asNode();
                    HoaDon hoaDon = new HoaDon(
                            node.get("maHoaDon").asString(),
                           node.get("ngayLapHoaDon").asLocalDate(),
                            node.get("diemTichLuySuDung").asInt(),
                            node.get("tongTien").asDouble(),
                            node.get("ghiChu").asString(null)
                    );
                    list.add(hoaDon);
                }
                return list;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateHoaDon(String maHoaDon, HoaDon hoaDon) {
        String query = "MATCH (hd:HoaDon {maHoaDon: $maHoaDon}) " +
                "SET hd.ngayLapHoaDon = date($ngayLapHoaDon), " +
                "hd.diemTichLuySuDung = $diemTichLuySuDung, " +
                "hd.tongTien = $tongTien, " +
                "hd.ghiChu = $ghiChu";
        Map<String, Object> params = Map.of(
                "maHoaDon", maHoaDon,
                "ngayLapHoaDon", hoaDon.getNgayLapHoaDon().toString(),
                "diemTichLuySuDung", hoaDon.getDiemTichLuySuDung(),
                "tongTien", hoaDon.getTongTien(),
                "ghiChu", hoaDon.getGhiChu()
        );

        try (var session = driver.session(sessionConfig)) {
            return session.executeWrite(tx ->
                    tx.run(query, params).consume().counters().propertiesSet() > 0
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
