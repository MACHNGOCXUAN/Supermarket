package dao;

import dao.impl.ChiTietHoaDonDAOImpl;
import dao.impl.SanPhamDAOImpl;
import model.SanPham;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SanPhamDAOTEST {

    private SanPhamDAO sanPhamDAO;
    private ChiTietHoaDonDAO chiTietHoaDonDAO;

    @BeforeEach
    public void setup() {
        sanPhamDAO = new SanPhamDAOImpl();
        chiTietHoaDonDAO = new ChiTietHoaDonDAOImpl();
    }

    @Test
    void getListByCategory(){
        String danhMucSPID = "DMSP22063";
        String[] listMaSanPhamExpected = {"SP00999", "SP83442", "SP95971"};
        int i = 0;
        for(SanPham sanPham : sanPhamDAO.getListByCategory(danhMucSPID)){
            assertEquals(listMaSanPhamExpected[i], sanPham.getMaSanPham());
            i++;
        }
    }

    @Test
    void getListByFitterFullSuccess(){
        String danhMucSPID = "DMSP04529_Kitkat";
        String month = "7";
        String year = "2024";
        String status = "Còn bán";

        String[] listMaSanPhamExpected = {"SP84339"};

        int i = 0;
        for(SanPham sanPham : sanPhamDAO.getListByFitter(danhMucSPID, month, year, status)){
            assertEquals(listMaSanPhamExpected[i], sanPham.getMaSanPham());
            i++;
        }

    }

    @Test
    void getListByFitterByDaySuccess(){
        String month = "9";
        String year = "2024";

        String[] listMaSanPhamExpected = {"SP14472",
                "SP26170",
                "SP27699",
                "SP30346",
                "SP32361",
                "SP63892",
                "SP71840",
                "SP83442",
                "SP90786"
        };
        int i = 0;
        for(SanPham sanPham : sanPhamDAO.getListByFitter("Tất cả", month, year, "Tất cả")){
            assertEquals(listMaSanPhamExpected[i], sanPham.getMaSanPham());
            i++;
        }


        int totalSanPham = 9;
        assertEquals(sanPhamDAO.getListByFitter("Tất cả", month, year, "Tất cả").size(), totalSanPham);

    }

    @Test
    void getListByFitterByStatusSellSuccess(){
        String status = "Còn bán";
        int totalSanPham = 49;
        assertEquals(sanPhamDAO.getListByFitter("Tất cả", "Tất cả", "Tất cả", status).size(), totalSanPham);
    }

    @Test
    void getListByFitterByStatusStopSellSuccess(){
        String status = "Ngưng bán";
        int totalSanPham = 36;
        assertEquals(sanPhamDAO.getListByFitter("Tất cả", "Tất cả", "Tất cả", status).size(), totalSanPham);
    }


    @Test
    void getDanhSachSanPhamSapHetHanTESTSuccess(){
        String[] listMaSanPhamExpected = {"SP73442", "SP78029", "SP94496", "SP96087"};
        int i = 0;
        for(SanPham sanPham : sanPhamDAO.getDanhSachSanPhamSapHetHan()){
            assertEquals(listMaSanPhamExpected[i], sanPham.getMaSanPham());
            i++;
        }
    }

    @Test
    void getLoiNhuanTheoNgayTESTSuccess(){
        LocalDate ngayBatDau = LocalDate.of(2025, 1, 1);
        LocalDate ngayKetThuc = LocalDate.of(2025, 4, 22);
        double tongTienNhapHang = sanPhamDAO.getTongTienNhapHangTheoNgay(ngayBatDau, ngayKetThuc);
        double doanhThuTheoNgay = chiTietHoaDonDAO.thongKeDoanhThuTheoNgay(ngayBatDau, ngayKetThuc)
                        .entrySet().stream()
                        .mapToDouble(entry -> entry.getValue())
                        .sum();

        Double[] listLoiNhuanExpected = {3968916.62, 334065.52, 2.91957663308E9, 1.692498976308E10, 2.572394993562E10};
        int i = 0;
        for(Map.Entry<LocalDate, Double> entry:  chiTietHoaDonDAO.thongKeDoanhThuTheoNgay(ngayBatDau, ngayKetThuc).entrySet()){
            double tongTienNhap = sanPhamDAO.getTongTienNhapHangTheoThangVaNam(entry.getKey().getMonthValue(), entry.getKey().getYear());
            double tongDoanhThu = entry.getValue();
            assertEquals(listLoiNhuanExpected[i], tongDoanhThu - tongTienNhap);
            i++;
        }

        double loiNhuanTheoNgayActual = doanhThuTheoNgay - tongTienNhapHang;
        double loiNhuanTheoNgayExpected = 4.5570838061924995E10;
        assertEquals(loiNhuanTheoNgayExpected, loiNhuanTheoNgayActual);
    }

    @Test
    void getLoiNhuanTheoNgayVaNamTESTSuccess(){
        int year = 2025;
        int month = 2;
        double loiNhuanActual = sanPhamDAO.getTongTienNhapHangTheoThangVaNam(month, 2025);
        double loiNhuanExpected = -3481251.995;

        double doanhThuActual = 0;

        for(Map.Entry<Integer, Double> entry: chiTietHoaDonDAO.thongKeDoanhThuTheoNam(year).entrySet()){
            if(entry.getKey() == month){
                doanhThuActual = entry.getValue();
            }
        }

        loiNhuanActual = doanhThuActual - loiNhuanActual;
        assertEquals(loiNhuanExpected, loiNhuanActual);
    }

    @Test
    void getLoiNhuanTheoNamTESTSuccess(){
        int year = 2025;
        double tongTienNhapNam = sanPhamDAO.getTongTienNhapHangTheoNam(year);
        double doanhThuTheoNam = chiTietHoaDonDAO.thongKeDoanhThuTheoNam(year)
                .entrySet().stream()
                .mapToDouble(entry -> entry.getValue())
                .sum();

        Double[] listLoiNhuanExpected = {4.26489396987E10, 2.92537961522E9};
        int i = 0;
        for(Map.Entry<Integer, Double> entry:  chiTietHoaDonDAO.thongKeDoanhThuTheoNam(year).entrySet()){
            double tongTienNhap = sanPhamDAO.getTongTienNhapHangTheoThangVaNam(entry.getKey(), year);
            double tongDoanhThu = entry.getValue();
            assertEquals(listLoiNhuanExpected[i], tongDoanhThu - tongTienNhap);
            i++;
        }

        double doanhThuTheoNamActual = doanhThuTheoNam - tongTienNhapNam;
        double doanhThuTheoNamExpected = 4.5570838061924995E10;
        assertEquals(doanhThuTheoNamExpected, doanhThuTheoNamActual);
    }

    @Test
    public void testSaveSanPhamSuccess() {
        String maSanPham = "SP85182";
        String tenSanPham = "Nước suối Lavie 500ml";
        LocalDate hanSuDung = LocalDate.of(2025, 12, 31);
        double giaBan = 7000;
        double thueVAT = 0.05; // 5%
        String trangThai = "còn bán";
        int soLuongTon = 100;
        LocalDate ngayNhap = LocalDate.of(2025, 4, 20);
        String moTa = "Chai nước tinh khiết Lavie dung tích 500ml.";

        SanPham sanPham = new SanPham(
                maSanPham,
                tenSanPham,
                hanSuDung,
                giaBan,
                thueVAT,
                trangThai,
                soLuongTon,
                ngayNhap,
                moTa
        );
        boolean result = sanPhamDAO.save(sanPham);
        assertTrue(result);
    }

    @Test
    public void testSaveSanPhamDuplicateID() {
        String maSanPham = "SP88976";
        String tenSanPham = "Nước suối Lavie 500ml";
        LocalDate hanSuDung = LocalDate.of(2025, 12, 31);
        double giaBan = 7000;
        double thueVAT = 0.05; // 5%
        String trangThai = "còn bán";
        int soLuongTon = 100;
        LocalDate ngayNhap = LocalDate.of(2025, 4, 20);
        String moTa = "Chai nước tinh khiết Lavie dung tích 500ml.";

        SanPham sanPham = new SanPham(
                maSanPham,
                tenSanPham,
                hanSuDung,
                giaBan,
                thueVAT,
                trangThai,
                soLuongTon,
                ngayNhap,
                moTa
        );

        boolean result = sanPhamDAO.save(sanPham);
        assertFalse(result);
    }

    @Test
    void thongKeSoLuongTheoTrangThai(){
        String[] labelExpected = {"Ngưng bán", "Còn bán"};

        Integer[] totalQuantityExpected = {36, 49};
        int i = 0;
        for(Map.Entry<String, Integer> entry :  sanPhamDAO.thongKeSoLuongTheoTrangThai().entrySet()){
            Assertions.assertEquals(entry.getKey(), labelExpected[i]);
            Assertions.assertEquals(entry.getValue(), totalQuantityExpected[i]);
            i++;
        }
    }

    @Test
    public void testFindOneSuccess() {
        SanPham sp = sanPhamDAO.findOne("SP88976");
        System.out.println(sp);
        assertNotNull(sp);
        assertEquals("Heavy Duty Plastic Table", sp.getTenSanPham());
    }

    @Test
    public void testFindOneFail() {
        SanPham sp = sanPhamDAO.findOne("SP69213");
        System.out.println(sp);
        assertNull(sp);
    }


    @Test
    public void testDeleteSanPhamSuccess() {
        boolean result = sanPhamDAO.delete("SP30492");
        assertTrue(result);
    }

    @Test
    public void testDeleteSanPhamFail() {
        boolean result = sanPhamDAO.delete("SP9999999");
        assertFalse(result);
    }

    @Test
    public void testGetList() {
        List<SanPham> ds = sanPhamDAO.getList();
        assertNotNull(ds);
        assertTrue(ds.size() > 0);
    }

    @AfterAll
    public void tearDown(){
        sanPhamDAO = null;
        chiTietHoaDonDAO = null;
    }

}

