package dao.neo4j;

import org.neo4j.driver.Driver;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.summary.ResultSummary;

import java.util.Map;

public class TaiKhoanThuocNhanVienNeo4jDAO {
    private Driver driver;
    private SessionConfig sessionConfig;

    public TaiKhoanThuocNhanVienNeo4jDAO(Driver driver, String databaseName) {
        this.driver = driver;
        this.sessionConfig = SessionConfig.builder().withDatabase(databaseName).build();
    }

    public boolean themThuocNhanVien(String maTaiKhoan, String maNhanVien){
        String query = "MATCH (nv: NhanVien{maNhanVien : $maNhanVien}) MATCH (tk: TaiKhoan{maTaiKhoan : $maTaiKhoan}) MERGE (tk)-[:THUOC]->(nv)";
        Map<String, Object> params = Map.of("maTaiKhoan", maTaiKhoan, "maNhanVien", maNhanVien);
        try(var session = driver.session(sessionConfig)){
//            return session.writeTransaction(tx -> tx.run(query, params).consume().counters().relationshipsCreated()>0);
            return session.executeWrite(tx->{
                ResultSummary resultSummary = tx.run(query,params).consume();
                return  resultSummary.counters().relationshipsCreated()>0;
            });

        }catch (Exception e){

            e.printStackTrace();
        }
        return false;
    }
    public boolean xoaThuocNhanVien(String maTaiKhoan) {
        String query = "MATCH (tk: TaiKhoan {maTaiKhoan: $maTaiKhoan})-[r:THUOC]->(nv: NhanVien ) " +
                "DELETE r";
        Map<String, Object> params = Map.of("maTaiKhoan", maTaiKhoan);
        try (var session = driver.session(sessionConfig)) {
            return session.executeWrite(tx -> {
                ResultSummary resultSummary = tx.run(query, params).consume();
                return resultSummary.counters().relationshipsDeleted() > 0;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
