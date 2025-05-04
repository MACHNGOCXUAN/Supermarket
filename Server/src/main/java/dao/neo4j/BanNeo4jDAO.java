package dao.neo4j;

import org.neo4j.driver.*;

import java.util.Map;

public class BanNeo4jDAO {
    private Driver driver;
    private SessionConfig sessionConfig;

    public BanNeo4jDAO(Driver driver, String databaseName) {
        this.driver = driver;
        this.sessionConfig = SessionConfig.builder().withDatabase(databaseName).build();
    }

    public boolean themBan(String maNhanVien, String maHoaDon) {
        String query = "MATCH (nv:NhanVien {maNhanVien: $maNhanVien}), (hd:HoaDon {maHoaDon: $maHoaDon}) " +
                "MERGE (nv)-[:BAN]->(hd)";
        Map<String, Object> params = Map.of("maNhanVien", maNhanVien, "maHoaDon", maHoaDon);

        try (var session = driver.session(sessionConfig)) {
            return session.executeWrite(tx ->
                    tx.run(query, params).consume().counters().relationshipsCreated() > 0
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaBan(String maHoaDon) {
        String query = "MATCH (nv:NhanVien)-[r:BAN]->(hd:HoaDon {maHoaDon: $maHoaDon}) DELETE r";
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
