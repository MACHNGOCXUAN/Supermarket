package dao.neo4j;

import lombok.NoArgsConstructor;
import model.DanhMucSanPham;
import org.neo4j.driver.*;
import org.neo4j.driver.summary.ResultSummary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class DanhMucSanPhamNeo4jDAO {
    private Driver driver;
    private SessionConfig sessionConfig;

    public DanhMucSanPhamNeo4jDAO(Driver driver, String databaseName) {
        this.driver = driver;
        this.sessionConfig = SessionConfig.builder().withDatabase(databaseName).build();
    }

    public DanhMucSanPham findByID(String maDanhMucSanPham) {
        String query = "MATCH(d: DanhMucSanPham {maDanhMucSanPham: $maDanhMucSanPham}) RETURN d";
        Map<String, Object> params = Map.of("maDanhMucSanPham", maDanhMucSanPham);

        try (var session = driver.session(sessionConfig)) {
            Result result = session.run(query, params);
            if (result.hasNext()) {
                var record = result.next();
                var node = record.get("d").asNode();
                String ten = node.get("tenDanhMucSanPham").asString();
                return new DanhMucSanPham(maDanhMucSanPham, ten);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean themDanhMucSanPham(DanhMucSanPham danhMuc) {
        String query = "CREATE (d: DanhMucSanPham) " +
                "SET d.maDanhMucSanPham = $maDanhMucSanPham, " +
                "d.tenDanhMucSanPham = $tenDanhMucSanPham";
        Map<String, Object> params = Map.of(
                "maDanhMucSanPham", danhMuc.getMaDanhMucSanPham(),
                "tenDanhMucSanPham", danhMuc.getTenDanhMucSanPham()
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

    public boolean updateDanhMucSanPham(String maDanhMucSanPham, DanhMucSanPham danhMuc) {
        String query = "MATCH (d: DanhMucSanPham {maDanhMucSanPham: $maDanhMucSanPham}) " +
                "SET d.tenDanhMucSanPham = $tenDanhMucSanPham";
        Map<String, Object> params = Map.of(
                "maDanhMucSanPham", maDanhMucSanPham,
                "tenDanhMucSanPham", danhMuc.getTenDanhMucSanPham()
        );

        try (var session = driver.session(sessionConfig)) {
            Result result = session.run(query, params);
            return result.consume().counters().propertiesSet() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaDanhMucSanPham(String maDanhMucSanPham) {
        String query = "MATCH (d: DanhMucSanPham {maDanhMucSanPham: $maDanhMucSanPham}) DETACH DELETE d";
        Map<String, Object> params = Map.of("maDanhMucSanPham", maDanhMucSanPham);

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

    public List<DanhMucSanPham> findAll() {
        List<DanhMucSanPham> list = new ArrayList<>();
        String query = "MATCH (d: DanhMucSanPham) RETURN d";

        try (var session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query);
                while (result.hasNext()) {
                    var record = result.next();
                    var node = record.get("d").asNode();
                    String ma = node.get("maDanhMucSanPham").asString();
                    String ten = node.get("tenDanhMucSanPham").asString();
                    list.add(new DanhMucSanPham(ma, ten));
                }
                return list;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
