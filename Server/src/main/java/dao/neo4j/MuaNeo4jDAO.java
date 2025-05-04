package dao.neo4j;

import org.neo4j.driver.*;

import java.util.Map;

public class MuaNeo4jDAO {
    private Driver driver;
    private SessionConfig sessionConfig;

    public MuaNeo4jDAO(Driver driver, String databaseName) {
        this.driver = driver;
        this.sessionConfig = SessionConfig.builder().withDatabase(databaseName).build();
    }

    public boolean themMua(String maKhachHang, String maHoaDon) {
        String query = "MATCH (kh:KhachHang {maKhachHang: $maKhachHang}), (hd:HoaDon {maHoaDon: $maHoaDon}) " +
                "MERGE (kh)-[:MUA]->(hd)";
        Map<String, Object> params = Map.of("maKhachHang", maKhachHang, "maHoaDon", maHoaDon);

        try (var session = driver.session(sessionConfig)) {
            return session.executeWrite(tx ->
                    tx.run(query, params).consume().counters().relationshipsCreated() > 0
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaMua(String maHoaDon) {
        String query = "MATCH (kh:KhachHang)-[r:MUA]->(hd:HoaDon {maHoaDon: $maHoaDon}) DELETE r";
        Map<String, Object> params = Map.of("maHoaDon", maHoaDon);

        try (var session = driver.session(sessionConfig)) {
            return session.executeWrite(tx ->
                    tx.run(query, params).consume().counters().relationshipsDeleted() > 0
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
