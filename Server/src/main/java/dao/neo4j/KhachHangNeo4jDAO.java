package dao.neo4j;

import lombok.NoArgsConstructor;
import model.GioiTinh;
import model.KhachHang;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.summary.ResultSummary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class KhachHangNeo4jDAO {
    private Driver driver;
    private SessionConfig sessionConfig;
    public  KhachHangNeo4jDAO(Driver driver, String databaseName){
        this.driver = driver;
        this.sessionConfig = SessionConfig.builder().withDatabase(databaseName).build();
    }
    public  KhachHang findByID(String maKhachHang){
        String query ="MATCH(kh:KhachHang{maKhachHang:$maKhachHang})\n" +
                "RETURN kh";
        KhachHang kh = null;
        Map<String, Object> params = Map.of("maKhachHang", maKhachHang);
        try(var session = driver.session(sessionConfig)){
            Result result = session.run(query, params);
          while(result.hasNext()){
              var record = result.next();
              var node = record.get("kh").asNode();
              String tenKhachHang = node.get("tenKhachHang").asString();
                String soDienThoai = node.get("soDienThoai").asString();
                String gioiTinh = node.get("gioiTinh").asString();
                int diemTichLuy = node.get("diemTichLuy").asInt();
                kh = new KhachHang(maKhachHang, tenKhachHang, soDienThoai, gioiTinh.equalsIgnoreCase("Nam")?GioiTinh.NAM:gioiTinh.equalsIgnoreCase("Nữ")?GioiTinh.NU:GioiTinh.KHAC, diemTichLuy);
          }
          return kh;
        }catch (Exception e){
            e.printStackTrace();
        }
        return kh;
    }

    public  boolean updateKhachHang(String maKhachHang, KhachHang khachHang){
            String query = "MATCH (kh: KhachHang{maKhachHang:$maKhachHang})\n" +
                    "SET kh.tenKhachHang = $tenKhachHang,\n" +
                    "kh.soDienThoai = $soDienThoai,\n" +
                    "kh.gioiTinh = $gioiTinh,\n" +
                    "kh.diemTichLuy = $diemTichLuy";
            Map<String, Object> params = Map.of("maKhachHang", maKhachHang, "tenKhachHang", khachHang.getTenKhachHang(), "soDienThoai", khachHang.getSoDienThoai(), "gioiTinh", khachHang.getGioiTinh().toString(), "diemTichLuy", khachHang.getDiemTichLuy());
            try(var session = driver.session(sessionConfig)){
                    Result result = session.run(query, params);
                    return  result.consume().counters().propertiesSet()>0;
            }catch (Exception e){
                e.printStackTrace();


            }
        return false;
    }
    public List<KhachHang> findAll(){
        List<KhachHang> list = new ArrayList<>()  ;
        String query = "MATCH (kh:KhachHang) RETURN kh";
        try (var session = driver.session(sessionConfig)){
            return  session.executeRead(tx->{
                Result result = tx.run(query);
                while(result.hasNext()){
                    var record = result.next();
                    var node = record.get("kh").asNode();
                    String maKhachHang = node.get("maKhachHang").asString();
                    String tenKhachHang = node.get("tenKhachHang").asString();
                    String soDienThoai = node.get("soDienThoai").asString();
                    String gioiTinh = node.get("gioiTinh").asString();
                    int diemTichLuy = node.get("diemTichLuy").asInt();
                    KhachHang khachHang = new KhachHang(maKhachHang, tenKhachHang, soDienThoai,gioiTinh.equalsIgnoreCase("Nam")? GioiTinh.NAM:gioiTinh.equalsIgnoreCase("Nữ")?GioiTinh.NU:GioiTinh.KHAC, diemTichLuy);
                    list.add(khachHang);
                }
                return list;
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

        public  boolean xoaKhachHang(String maKhachHang) {
            String query = "MATCH (kh:KhachHang{maKhachHang:$maKhachHang})\n" +
                    "DETACH DELETE kh";
            Map<String, Object> params = Map.of("maKhachHang", maKhachHang);
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

    public boolean themKhachHang( KhachHang khachHang){
        String query = "CREATE (kh: KhachHang)\n" +
                "SET kh.maKhachHang = $maKhachHang,\n" +
                "kh.tenKhachHang = $tenKhachHang,\n" +
                "kh.soDienThoai = $soDienThoai,\n" +
                "kh.gioiTinh = $gioiTinh,\n" +
                "kh.diemTichLuy = $diemTichLuy";
        Map<String, Object> params = Map.of("maKhachHang", khachHang.getMaKhachHang(), "tenKhachHang", khachHang.getTenKhachHang(), "soDienThoai", khachHang.getSoDienThoai(), "gioiTinh", khachHang.getGioiTinh().toString(), "diemTichLuy", khachHang.getDiemTichLuy());
        try (var session = driver.session(sessionConfig)){
            return session.executeWrite(tx->{
                ResultSummary summary = tx.run(query,params).consume();
                return summary.counters().nodesCreated()>0;
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return false
                ;
    }
}
