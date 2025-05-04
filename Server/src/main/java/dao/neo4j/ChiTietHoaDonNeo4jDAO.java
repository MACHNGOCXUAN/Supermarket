package dao.neo4j;

import model.ChiTietHoaDon;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.SessionConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChiTietHoaDonNeo4jDAO {
    private Driver driver;
    private SessionConfig sessionConfig;

    public ChiTietHoaDonNeo4jDAO(Driver driver, String databaseName) {
        this.driver = driver;
        this.sessionConfig = SessionConfig.builder().withDatabase(databaseName).build();
    }
    public boolean themChiTietHoaDon(ChiTietHoaDon ct) {
        String query = "MATCH (hd:HoaDon {maHoaDon: $maHoaDon}), (sp:SanPham {maSanPham: $maSanPham}) " +
                "MERGE (hd)-[ct:CHI_TIET_HOA_DON]->(sp) " +
                "SET ct.soLuong = $soLuong, " +
                "ct.donGia = $donGia, " +
                "ct.thanhTien = $thanhTien";

        Map<String, Object> params = Map.of(
                "maHoaDon", ct.getHoaDon().getMaHoaDon(),
                "maSanPham", ct.getSanPham().getMaSanPham(),
                "soLuong", ct.getSoLuong(),
                "donGia", ct.getDonGia(),
                "thanhTien", ct.getThanhTien()
        );

        try (var session = driver.session(sessionConfig)) {
            return session.executeWrite(tx ->
                    tx.run(query, params).consume().counters().relationshipsCreated() > 0
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean update(ChiTietHoaDon ct) {
        String query = "MATCH (hd:HoaDon {maHoaDon: $maHoaDon})-[ct:CHI_TIET_HOA_DON]->(sp:SanPham {maSanPham: $maSanPham}) " +
                "SET ct.soLuong = $soLuong, " +
                "ct.donGia = $donGia, " +
                "ct.thanhTien = $thanhTien";

        Map<String, Object> params = Map.of(
                "maHoaDon", ct.getHoaDon().getMaHoaDon(),
                "maSanPham", ct.getSanPham().getMaSanPham(),
                "soLuong", ct.getSoLuong(),
                "donGia", ct.getDonGia(),
                "thanhTien", ct.getThanhTien()
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
    public boolean deleteByHDID(String hdid){
        String query = "MATCH (hd:HoaDon {maHoaDon: $maHoaDon})-[ct:CHI_TIET_HOA_DON]->(sp:SanPham) DELETE ct";
        Map<String, Object> params = Map.of("maHoaDon", hdid);
        try (var session = driver.session(sessionConfig)) {
            return session.executeWrite(tx ->
                    tx.run(query, params).consume().counters().relationshipsDeleted() > 0
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<ChiTietHoaDon> getListByHDID(String maHoaDon) {
        String query = "MATCH (hd:HoaDon {maHoaDon: $maHoaDon})-[ct:CHI_TIET_HOA_DON]->(sp:SanPham) RETURN ct, sp";
        Map<String, Object> params = Map.of("maHoaDon", maHoaDon);
        List<ChiTietHoaDon> list = new ArrayList<>();
        HoaDonNeo4jDAO hoaDonNeo4jDAO = new HoaDonNeo4jDAO(driver, "nhom4");
        try (var session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                while (result.hasNext()) {
                    var record = result.next();
                    var rel = record.get("ct").asRelationship();
                    var sp = record.get("sp").asNode();

                    ChiTietHoaDon ct = new ChiTietHoaDon();
                    ct.setSoLuong(rel.get("soLuong").asInt());
                    ct.setDonGia(rel.get("donGia").asDouble());
                    ct.setThanhTien(rel.get("thanhTien").asDouble());
                    ct.setHoaDon(hoaDonNeo4jDAO.findById(maHoaDon));
                    ct.setSanPham(
                            new SanPhamNeo4jDAO(driver, "nhom4").findByID(sp.get("maSanPham").asString())
                    );


                    list.add(ct);
                }
                return list;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
