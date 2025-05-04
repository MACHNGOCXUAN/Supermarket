package dao.neo4j;

import model.ThuocTinhSanPham;
import org.neo4j.driver.*;
import org.neo4j.driver.summary.ResultSummary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ThuocTinhSanPhamThuocSanPhamNeo4jDAO {
    private final Driver driver;
    private final SessionConfig sessionConfig;

    public ThuocTinhSanPhamThuocSanPhamNeo4jDAO(Driver driver, String databaseName) {
        this.driver = driver;
        this.sessionConfig = SessionConfig.builder().withDatabase(databaseName).build();
    }

    public List<ThuocTinhSanPham> getListByProductId(String productId) {
        String query = "MATCH (sp:SanPham {maSanPham: $productId})-[:CO_THUOC_TINH]->(tt:ThuocTinhSanPham) RETURN tt";
        Map<String, Object> params = Map.of("productId", productId);
        List<ThuocTinhSanPham> list = new ArrayList<>();

        try (var session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                while (result.hasNext()) {
                    var node = result.next().get("tt").asNode();
                    ThuocTinhSanPham ttsp = new ThuocTinhSanPham(
                            node.get("maThuocTinhSanPham").asString(),
                            node.get("tenThuocTinh").asString(null),
                            node.get("giaTriThuocTinh").asString(null)
                    );
                    list.add(ttsp);
                }
                return list;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean themQuanHeThuocTinhSanPham(String maSanPham, String maThuocTinh) {
        String query = "MATCH (sp:SanPham {maSanPham: $maSanPham}), (tt:ThuocTinhSanPham {maThuocTinhSanPham: $maThuocTinh}) " +
                "MERGE (sp)-[:CO_THUOC_TINH]->(tt)";
        Map<String, Object> params = Map.of(
                "maSanPham", maSanPham,
                "maThuocTinh", maThuocTinh
        );

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

    public boolean xoaQuanHeThuocTinhSanPham(String maSanPham, String maThuocTinh) {
        String query = "MATCH (sp:SanPham {maSanPham: $maSanPham})-[r:CO_THUOC_TINH]->(tt:ThuocTinhSanPham {maThuocTinhSanPham: $maThuocTinh}) " +
                "DELETE r";
        Map<String, Object> params = Map.of(
                "maSanPham", maSanPham,
                "maThuocTinh", maThuocTinh
        );

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
