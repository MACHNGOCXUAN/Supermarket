package dao.neo4j;

import lombok.NoArgsConstructor;
import model.TaiKhoan;
import org.neo4j.driver.*;
import org.neo4j.driver.summary.ResultSummary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class TaiKhoanNeo4jDAO {
    private Driver driver;
    private SessionConfig sessionConfig;

    public TaiKhoanNeo4jDAO(Driver driver, String databaseName) {
        this.driver = driver;
        this.sessionConfig = SessionConfig.builder().withDatabase(databaseName).build();
    }

    public TaiKhoan findByID(String maTaiKhoan) {
        String query = "MATCH (tk:TaiKhoan {maTaiKhoan: $maTaiKhoan}) RETURN tk";
        Map<String, Object> params = Map.of("maTaiKhoan", maTaiKhoan);

        try (var session = driver.session(sessionConfig)) {
            Result result = session.run(query, params);
            if (result.hasNext()) {
                var node = result.next().get("tk").asNode();
                return new TaiKhoan(
                        maTaiKhoan,
                        node.get("tenDangNhap").asString(null),
                        node.get("matKhau").asString(null),
                        node.get("trangThai").asString(null)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean themTaiKhoan(TaiKhoan taiKhoan) {
        String query = "CREATE (tk:TaiKhoan) " +
                "SET tk.maTaiKhoan = $maTaiKhoan, " +
                "tk.tenDangNhap = $tenDangNhap, " +
                "tk.matKhau = $matKhau, " +
                "tk.trangThai = $trangThai";
        Map<String, Object> params = Map.of(
                "maTaiKhoan", taiKhoan.getMaTaiKhoan(),
                "tenDangNhap", taiKhoan.getTenDangNhap(),
                "matKhau", taiKhoan.getMatKhau(),
                "trangThai", taiKhoan.getTrangThai()
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

    public boolean updateTaiKhoan(String maTaiKhoan, TaiKhoan taiKhoan) {
        String query = "MATCH (tk:TaiKhoan {maTaiKhoan: $maTaiKhoan}) " +
                "SET tk.tenDangNhap = $tenDangNhap, " +
                "tk.matKhau = $matKhau, " +
                "tk.trangThai = $trangThai";
        Map<String, Object> params = Map.of(
                "maTaiKhoan", maTaiKhoan,
                "tenDangNhap", taiKhoan.getTenDangNhap(),
                "matKhau", taiKhoan.getMatKhau(),
                "trangThai", taiKhoan.getTrangThai()
        );

        try (var session = driver.session(sessionConfig)) {
            return session.run(query, params).consume().counters().propertiesSet() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaTaiKhoan(String maTaiKhoan) {
        String query = "MATCH (tk:TaiKhoan {maTaiKhoan: $maTaiKhoan}) DETACH DELETE tk";
        Map<String, Object> params = Map.of("maTaiKhoan", maTaiKhoan);

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

    public List<TaiKhoan> findAll() {
        List<TaiKhoan> list = new ArrayList<>();
        String query = "MATCH (tk:TaiKhoan) RETURN tk";

        try (var session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query);
                while (result.hasNext()) {
                    var node = result.next().get("tk").asNode();
                    list.add(new TaiKhoan(
                            node.get("maTaiKhoan").asString(),
                            node.get("tenDangNhap").asString(null),
                            node.get("matKhau").asString(null),
                            node.get("trangThai").asString(null)
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
