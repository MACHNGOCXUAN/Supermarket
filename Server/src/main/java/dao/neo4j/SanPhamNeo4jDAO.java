package dao.neo4j;

import lombok.NoArgsConstructor;
import model.SanPham;
import org.neo4j.driver.*;
import org.neo4j.driver.summary.ResultSummary;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class SanPhamNeo4jDAO {
    private Driver driver;
    private SessionConfig sessionConfig;

    public SanPhamNeo4jDAO(Driver driver, String databaseName) {
        this.driver = driver;
        this.sessionConfig = SessionConfig.builder().withDatabase(databaseName).build();
    }

    public SanPham findByID(String maSanPham) {
        String query = "MATCH (sp:SanPham {maSanPham: $maSanPham}) RETURN sp";
        Map<String, Object> params = Map.of("maSanPham", maSanPham);

        try (var session = driver.session(sessionConfig)) {
            Result result = session.run(query, params);
            if (result.hasNext()) {
                var node = result.next().get("sp").asNode();
                return new SanPham(
                        maSanPham,
                        node.get("tenSanPham").asString(),
                        LocalDate.parse(node.get("hanSuDung").asString()),
                        node.get("giaBan").asDouble(),
                        node.get("thueVAT").asDouble(),
                        node.get("trangThai").asString(),
                        node.get("soLuongTon").asInt(),
                        LocalDate.parse(node.get("ngayNhap").asString()),
                        node.get("moTa").asString(null)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean themSanPham(SanPham sp) {
        String query = "CREATE (sp:SanPham) " +
                "SET sp.maSanPham = $maSanPham, " +
                "sp.tenSanPham = $tenSanPham, " +
                "sp.hanSuDung = $hanSuDung, " +
                "sp.giaBan = $giaBan, " +
                "sp.thueVAT = $thueVAT, " +
                "sp.trangThai = $trangThai, " +
                "sp.soLuongTon = $soLuongTon, " +
                "sp.ngayNhap = $ngayNhap, " +
                "sp.moTa = $moTa";
        Map<String, Object> params = Map.of(
                "maSanPham", sp.getMaSanPham(),
                "tenSanPham", sp.getTenSanPham(),
                "hanSuDung", sp.getHanSuDung().toString(),
                "giaBan", sp.getGiaBan(),
                "thueVAT", sp.getThueVAT(),
                "trangThai", sp.getTrangThai(),
                "soLuongTon", sp.getSoLuongTon(),
                "ngayNhap", sp.getNgayNhap().toString(),
                "moTa", sp.getMoTa()
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

    public boolean updateSanPham(String maSanPham, SanPham sp) {
        String query = "MATCH (sp:SanPham {maSanPham: $maSanPham}) " +
                "SET sp.tenSanPham = $tenSanPham, " +
                "sp.hanSuDung = $hanSuDung, " +
                "sp.giaBan = $giaBan, " +
                "sp.thueVAT = $thueVAT, " +
                "sp.trangThai = $trangThai, " +
                "sp.soLuongTon = $soLuongTon, " +
                "sp.ngayNhap = $ngayNhap, " +
                "sp.moTa = $moTa";
        Map<String, Object> params = Map.of(
                "maSanPham", maSanPham,
                "tenSanPham", sp.getTenSanPham(),
                "hanSuDung", sp.getHanSuDung().toString(),
                "giaBan", sp.getGiaBan(),
                "thueVAT", sp.getThueVAT(),
                "trangThai", sp.getTrangThai(),
                "soLuongTon", sp.getSoLuongTon(),
                "ngayNhap", sp.getNgayNhap().toString(),
                "moTa", sp.getMoTa()
        );

        try (var session = driver.session(sessionConfig)) {
            return session.run(query, params).consume().counters().propertiesSet() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaSanPham(String maSanPham) {
        String query = "MATCH (sp:SanPham {maSanPham: $maSanPham}) DETACH DELETE sp";
        Map<String, Object> params = Map.of("maSanPham", maSanPham);

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

    public List<SanPham> findAll() {
        List<SanPham> list = new ArrayList<>();
        String query = "MATCH (sp:SanPham) RETURN sp";

        try (var session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query);
                while (result.hasNext()) {
                    var node = result.next().get("sp").asNode();
                    list.add(new SanPham(
                            node.get("maSanPham").asString(),
                            node.get("tenSanPham").asString(),
                            LocalDate.parse(node.get("hanSuDung").asString()),
                            node.get("giaBan").asDouble(),
                            node.get("thueVAT").asDouble(),
                            node.get("trangThai").asString(),
                            node.get("soLuongTon").asInt(),
                            LocalDate.parse(node.get("ngayNhap").asString()),
                            node.get("moTa").asString(null)
                    ));
                }
                return list;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
