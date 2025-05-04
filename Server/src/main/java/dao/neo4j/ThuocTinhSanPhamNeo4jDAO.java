package dao.neo4j;

import lombok.NoArgsConstructor;
import model.ThuocTinhSanPham;
import org.neo4j.driver.*;
import org.neo4j.driver.summary.ResultSummary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class ThuocTinhSanPhamNeo4jDAO {
    private Driver driver;
    private SessionConfig sessionConfig;

    public ThuocTinhSanPhamNeo4jDAO(Driver driver, String databaseName) {
        this.driver = driver;
        this.sessionConfig = SessionConfig.builder().withDatabase(databaseName).build();
    }

    public ThuocTinhSanPham findByID(String maThuocTinhSanPham) {
        String query = "MATCH (tt:ThuocTinhSanPham {maThuocTinhSanPham: $maThuocTinhSanPham}) RETURN tt";
        Map<String, Object> params = Map.of("maThuocTinhSanPham", maThuocTinhSanPham);

        try (var session = driver.session(sessionConfig)) {
            Result result = session.run(query, params);
            if (result.hasNext()) {
                var node = result.next().get("tt").asNode();
                return new ThuocTinhSanPham(
                        maThuocTinhSanPham,
                        node.get("tenThuocTinh").asString(null),
                        node.get("giaTriThuocTinh").asString(null)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean themThuocTinhSanPham(ThuocTinhSanPham ttsp) {
        String query = "CREATE (tt:ThuocTinhSanPham) " +
                "SET tt.maThuocTinhSanPham = $maThuocTinhSanPham, " +
                "tt.tenThuocTinh = $tenThuocTinh, " +
                "tt.giaTriThuocTinh = $giaTriThuocTinh";
        Map<String, Object> params = Map.of(
                "maThuocTinhSanPham", ttsp.getMaThuocTinhSanPham(),
                "tenThuocTinh", ttsp.getTenThuocTinh(),
                "giaTriThuocTinh", ttsp.getGiaTriThuocTinh()
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

    public boolean updateThuocTinhSanPham(String maThuocTinhSanPham, ThuocTinhSanPham ttsp) {
        String query = "MATCH (tt:ThuocTinhSanPham {maThuocTinhSanPham: $maThuocTinhSanPham}) " +
                "SET tt.tenThuocTinh = $tenThuocTinh, " +
                "tt.giaTriThuocTinh = $giaTriThuocTinh";
        Map<String, Object> params = Map.of(
                "maThuocTinhSanPham", maThuocTinhSanPham,
                "tenThuocTinh", ttsp.getTenThuocTinh(),
                "giaTriThuocTinh", ttsp.getGiaTriThuocTinh()
        );

        try (var session = driver.session(sessionConfig)) {
            return session.run(query, params).consume().counters().propertiesSet() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaThuocTinhSanPham(String maThuocTinhSanPham) {
        String query = "MATCH (tt:ThuocTinhSanPham {maThuocTinhSanPham: $maThuocTinhSanPham}) DETACH DELETE tt";
        Map<String, Object> params = Map.of("maThuocTinhSanPham", maThuocTinhSanPham);

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

    public List<ThuocTinhSanPham> findAll() {
        List<ThuocTinhSanPham> list = new ArrayList<>();
        String query = "MATCH (tt:ThuocTinhSanPham) RETURN tt";

        try (var session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query);
                while (result.hasNext()) {
                    var node = result.next().get("tt").asNode();
                    list.add(new ThuocTinhSanPham(
                            node.get("maThuocTinhSanPham").asString(),
                            node.get("tenThuocTinh").asString(null),
                            node.get("giaTriThuocTinh").asString(null)
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
