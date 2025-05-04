package dao.neo4j;

import lombok.NoArgsConstructor;
import model.ChucVuNhanVien;
import model.GioiTinh;
import model.NhanVien;
import org.neo4j.driver.*;
import org.neo4j.driver.summary.ResultSummary;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class NhanVienNeo4jDAO {
    private Driver driver;
    private SessionConfig sessionConfig;

    public NhanVienNeo4jDAO(Driver driver, String databaseName) {
        this.driver = driver;
        this.sessionConfig = SessionConfig.builder().withDatabase(databaseName).build();
    }

    public NhanVien findByID(String maNhanVien) {
        String query = "MATCH (nv:NhanVien {maNhanVien: $maNhanVien}) RETURN nv";
        Map<String, Object> params = Map.of("maNhanVien", maNhanVien);

        try (var session = driver.session(sessionConfig)) {
            Result result = session.run(query, params);
            if (result.hasNext()) {
                var node = result.next().get("nv").asNode();
                return new NhanVien(
                        maNhanVien,
                        node.get("tenNhanVien").asString(),
                        LocalDate.parse(node.get("ngaySinh").asString()),
                        node.get("soDienThoai").asString(),
                        node.get("diaChi").asString(),
                        node.get("soDinhDanh").asString(),
                        node.get("gioiTinh").asString().equalsIgnoreCase("Nam") ? GioiTinh.NAM : node.get("gioiTinh").asString().equalsIgnoreCase("Nữ")? GioiTinh.NU : GioiTinh.KHAC,
                        node.get("chucVuNhanVien").asString().equalsIgnoreCase("Người quản lý") ? ChucVuNhanVien.NGUOIQUANLY : ChucVuNhanVien.NHANVIEN
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean themNhanVien(NhanVien nv) {
        String query = "CREATE (nv:NhanVien) " +
                "SET nv.maNhanVien = $maNhanVien, " +
                "nv.tenNhanVien = $tenNhanVien, " +
                "nv.ngaySinh = $ngaySinh, " +
                "nv.soDienThoai = $soDienThoai, " +
                "nv.diaChi = $diaChi, " +
                "nv.soDinhDanh = $soDinhDanh, " +
                "nv.gioiTinh = $gioiTinh, " +
                "nv.chucVuNhanVien = $chucVuNhanVien";
        Map<String, Object> params = Map.of(
                "maNhanVien", nv.getMaNhanVien(),
                "tenNhanVien", nv.getTenNhanVien(),
                "ngaySinh", nv.getNgaySinh().toString(),
                "soDienThoai", nv.getSoDienThoai(),
                "diaChi", nv.getDiaChi(),
                "soDinhDanh", nv.getSoDinhDanh(),
                "gioiTinh", nv.getGioiTinh().toString(),
                "chucVuNhanVien", nv.getChucVuNhanVien().toString()
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

    public boolean updateNhanVien(String maNhanVien, NhanVien nv) {
        String query = "MATCH (nv:NhanVien {maNhanVien: $maNhanVien}) " +
                "SET nv.tenNhanVien = $tenNhanVien, " +
                "nv.ngaySinh = $ngaySinh, " +
                "nv.soDienThoai = $soDienThoai, " +
                "nv.diaChi = $diaChi, " +
                "nv.soDinhDanh = $soDinhDanh, " +
                "nv.gioiTinh = $gioiTinh, " +
                "nv.chucVuNhanVien = $chucVuNhanVien";
        Map<String, Object> params = Map.of(
                "maNhanVien", maNhanVien,
                "tenNhanVien", nv.getTenNhanVien(),
                "ngaySinh", nv.getNgaySinh().toString(),
                "soDienThoai", nv.getSoDienThoai(),
                "diaChi", nv.getDiaChi(),
                "soDinhDanh", nv.getSoDinhDanh(),
                "gioiTinh", nv.getGioiTinh().toString(),
                "chucVuNhanVien", nv.getChucVuNhanVien().toString()
        );

        try (var session = driver.session(sessionConfig)) {
            return session.run(query, params).consume().counters().propertiesSet() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaNhanVien(String maNhanVien) {
        String query = "MATCH (nv:NhanVien {maNhanVien: $maNhanVien}) DETACH DELETE nv";
        Map<String, Object> params = Map.of("maNhanVien", maNhanVien);

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

    public List<NhanVien> findAll() {
        List<NhanVien> list = new ArrayList<>();
        String query = "MATCH (nv:NhanVien) RETURN nv";

        try (var session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query);
                while (result.hasNext()) {
                    var node = result.next().get("nv").asNode();
                    list.add(new NhanVien(
                            node.get("maNhanVien").asString(),
                            node.get("tenNhanVien").asString(),
                            LocalDate.parse(node.get("ngaySinh").asString()),
                            node.get("soDienThoai").asString(),
                            node.get("diaChi").asString(),
                            node.get("soDinhDanh").asString(),
                            node.get("gioiTinh").asString().equalsIgnoreCase("Nam") ? GioiTinh.NAM : node.get("gioiTinh").asString().equalsIgnoreCase("Nữ")? GioiTinh.NU : GioiTinh.KHAC,
                            node.get("chucVuNhanVien").asString().equalsIgnoreCase("Người quản lý") ? ChucVuNhanVien.NGUOIQUANLY : ChucVuNhanVien.NHANVIEN
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
