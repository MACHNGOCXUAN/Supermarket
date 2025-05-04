package dao.neo4j;

import lombok.NoArgsConstructor;
import model.KhuyenMai;
import org.neo4j.driver.*;
import org.neo4j.driver.summary.ResultSummary;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class KhuyenMaiNeo4jDAO {
    private Driver driver;
    private SessionConfig sessionConfig;

    public KhuyenMaiNeo4jDAO(Driver driver, String databaseName) {
        this.driver = driver;
        this.sessionConfig = SessionConfig.builder().withDatabase(databaseName).build();
    }

    public KhuyenMai findByID(String maKhuyenMai) {
        String query = "MATCH (km:KhuyenMai {maKhuyenMai: $maKhuyenMai}) RETURN km";
        Map<String, Object> params = Map.of("maKhuyenMai", maKhuyenMai);

        try (var session = driver.session(sessionConfig)) {
            Result result = session.run(query, params);
            if (result.hasNext()) {
                var record = result.next();
                var node = record.get("km").asNode();
                return new KhuyenMai(
                        maKhuyenMai,
                        node.get("tenKhuyenMai").asString(null),
                        node.get("tienGiam").asDouble(),
                        LocalDate.parse(node.get("ngayBatDau").asString()),
                        LocalDate.parse(node.get("ngayKetThuc").asString())
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean themKhuyenMai(KhuyenMai khuyenMai) {
        String query = "CREATE (km:KhuyenMai) " +
                "SET km.maKhuyenMai = $maKhuyenMai, " +
                "km.tenKhuyenMai = $tenKhuyenMai, " +
                "km.tienGiam = $tienGiam, " +
                "km.ngayBatDau = $ngayBatDau, " +
                "km.ngayKetThuc = $ngayKetThuc";
        Map<String, Object> params = Map.of(
                "maKhuyenMai", khuyenMai.getMaKhuyenMai(),
                "tenKhuyenMai", khuyenMai.getTenKhuyenMai(),
                "tienGiam", khuyenMai.getTienGiam(),
                "ngayBatDau", khuyenMai.getNgayBatDau().toString(),
                "ngayKetThuc", khuyenMai.getNgayKetThuc().toString()
        );

        try (var session = driver.session(sessionConfig)) {
            return session.executeWrite(tx -> {
                ResultSummary summary = tx.run(query, params).consume();
                return summary.counters().nodesCreated() > 0;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateKhuyenMai(String maKhuyenMai, KhuyenMai khuyenMai) {
        String query = "MATCH (km:KhuyenMai {maKhuyenMai: $maKhuyenMai}) " +
                "SET km.tenKhuyenMai = $tenKhuyenMai, " +
                "km.tienGiam = $tienGiam, " +
                "km.ngayBatDau = $ngayBatDau, " +
                "km.ngayKetThuc = $ngayKetThuc";
        Map<String, Object> params = Map.of(
                "maKhuyenMai", maKhuyenMai,
                "tenKhuyenMai", khuyenMai.getTenKhuyenMai(),
                "tienGiam", khuyenMai.getTienGiam(),
                "ngayBatDau", khuyenMai.getNgayBatDau().toString(),
                "ngayKetThuc", khuyenMai.getNgayKetThuc().toString()
        );

        try (var session = driver.session(sessionConfig)) {
            Result result = session.run(query, params);
            return result.consume().counters().propertiesSet() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaKhuyenMai(String maKhuyenMai) {
        String query = "MATCH (km:KhuyenMai {maKhuyenMai: $maKhuyenMai}) DETACH DELETE km";
        Map<String, Object> params = Map.of("maKhuyenMai", maKhuyenMai);

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

    public List<KhuyenMai> findAll() {
        List<KhuyenMai> list = new ArrayList<>();
        String query = "MATCH (km:KhuyenMai) RETURN km";

        try (var session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query);
                while (result.hasNext()) {
                    var record = result.next();
                    var node = record.get("km").asNode();
                    KhuyenMai km = new KhuyenMai(
                            node.get("maKhuyenMai").asString(),
                            node.get("tenKhuyenMai").asString(null),
                            node.get("tienGiam").asDouble(),
                            LocalDate.parse(node.get("ngayBatDau").asString()),
                            LocalDate.parse(node.get("ngayKetThuc").asString())
                    );
                    list.add(km);
                }
                return list;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
