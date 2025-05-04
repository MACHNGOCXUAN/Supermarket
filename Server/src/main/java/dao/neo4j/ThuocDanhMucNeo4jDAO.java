package dao.neo4j;

import org.neo4j.driver.Driver;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.summary.ResultSummary;

import java.util.Map;

public class ThuocDanhMucNeo4jDAO {
    private Driver driver;
    private SessionConfig sessionConfig;
    public ThuocDanhMucNeo4jDAO(Driver driver, String databaseName) {
        this.driver = driver;
        this.sessionConfig = SessionConfig.builder().withDatabase(databaseName).build();
    }
    public  boolean themThuocDanhMuc(String maSP, String maDanhMuc) {
       String query = "MATCH (sp:SanPham {maSanPham: $maSanPham}), (d:DanhMucSanPham {maDanhMucSanPham: $maDanhMuc})\n" +
               "MERGE (sp)-[:THUOC_DANH_MUC]->(d)";
         Map<String, Object> params = Map.of("maSanPham", maSP, "maDanhMuc", maDanhMuc);
            try(var session = driver.session(sessionConfig)){
                return session.executeWrite(tx -> {
                    ResultSummary result = tx.run(query, params).consume();
                    return result.counters().relationshipsCreated()>0;
                });
            }catch (Exception e){
                e.printStackTrace();
            }
            return false;
    }
    public  boolean xoaThuocDanhMuc(String maSP, String maDanhMuc){
        String query = "MATCH (sp:SanPham {maSanPham: $maSanPham})-[r:THUOC_DANH_MUC]->(d:DanhMucSanPham {maDanhMucSanPham: $maDanhMuc})\n" +
                "DELETE r";
        Map<String, Object> params = Map.of("maSanPham", maSP, "maDanhMuc", maDanhMuc);
        try(var session = driver.session(sessionConfig)){
            return session.executeWrite(tx -> {
                ResultSummary result = tx.run(query, params).consume();
                return result.counters().relationshipsDeleted()>0;
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
