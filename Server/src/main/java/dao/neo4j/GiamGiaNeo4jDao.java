package dao.neo4j;

import org.neo4j.driver.Driver;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.summary.ResultSummary;

import java.util.Map;

public class GiamGiaNeo4jDao {
    private Driver driver;
    private SessionConfig sessionConfig;

    public GiamGiaNeo4jDao(Driver driver, String databaseName) {
        this.driver = driver;
        this.sessionConfig = SessionConfig.builder().withDatabase(databaseName).build();
    }

    public boolean themGiamGia(String maGiamGia, String maSanPham) {
        String query = "MATCH (km:KhuyenMai {maKhuyenMai: $maGiamGia}), (sp:SanPham {maSanPham: $maSanPham}) " +
                "MERGE (km)-[:GIAM_GIA]->(sp)";
        Map<String, Object> params = Map.of("maGiamGia", maGiamGia, "maSanPham", maSanPham);

        try (var session = driver.session(sessionConfig)) {
            return session.executeWrite(tx -> {
                ResultSummary summary = tx.run(query, params).consume();
                return summary.counters().relationshipsCreated() > 0;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaGiamGia(String maGiamGia) {
        String query = "MATCH (km:KhuyenMai {maKhuyenMai: $maGiamGia})-[r:GIAM_GIA]->(sp:SanPham ) " +
                "DELETE r";
        Map<String, Object> params = Map.of("maGiamGia", maGiamGia );

        try (var session = driver.session(sessionConfig)) {
            return session.executeWrite(tx -> {
                ResultSummary summary = tx.run(query, params).consume();
                return summary.counters().relationshipsDeleted() > 0;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
