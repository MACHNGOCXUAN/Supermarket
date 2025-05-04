package dao;

import dao.impl.ChiTietHoaDonDAOImpl;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ChiTietHoaDonDAOTEST{
    private ChiTietHoaDonDAO chiTietHoaDonDAO;

    @BeforeAll
    void setUp(){
        chiTietHoaDonDAO = new ChiTietHoaDonDAOImpl();
    }

    @Test
    void doanhThuTheoNgayGanNhatTESTSuccess(){
        int limit = 7;
        List<LocalDate> localDatesExpected = List.of(
            LocalDate.of(2025, 4, 20),
            LocalDate.of(2025, 4, 19),
            LocalDate.of(2025, 4, 18)
        );

        Double[] totalAmountExpected = {4718916.62, 1084065.52, 2.92032663308E9};
        int i = 0;
        for(Map.Entry<LocalDate, Double> entry :  chiTietHoaDonDAO.doanhThuTheoNgayGanNhat(limit).entrySet()){
            Assertions.assertEquals(entry.getKey(), localDatesExpected.get(i));
            Assertions.assertEquals(entry.getValue(), totalAmountExpected[i]);
            i++;
        }

        double totalActual = chiTietHoaDonDAO.doanhThuTheoNgayGanNhat(limit)
                .entrySet().stream().mapToDouble(entry -> entry.getValue()).sum();
        double totalExpected = Arrays.stream(totalAmountExpected).mapToDouble(total -> total).sum();
        Assertions.assertEquals(totalExpected, totalActual);
    }

    @Test
    void thongKeDoanhThuTheoNamTESTSuccess(){
        int year = 2024;
        Integer[] MonthExpected = {1, 2, 3, 4, 8, 11};

        Double[] totalAmountExpected = {
                6.18765282699E9,
                6217954.03,
                1.825787373314E10,
                2.952129012194E10,
                2.3334973529E9,
                1.692403811178E10
        };
        int i = 0;
        for(Map.Entry<Integer, Double> entry :  chiTietHoaDonDAO.thongKeDoanhThuTheoNam(year).entrySet()){
            Assertions.assertEquals(entry.getKey(), MonthExpected[i]);
            Assertions.assertEquals(entry.getValue(), totalAmountExpected[i]);
            i++;
        }

        double totalActual = chiTietHoaDonDAO.thongKeDoanhThuTheoNam(year)
                .entrySet().stream().mapToDouble(entry -> entry.getValue()).sum();
        double totalExpected = Arrays.stream(totalAmountExpected).mapToDouble(total -> total).sum();
        Assertions.assertEquals(totalExpected, totalActual);
    }

    @Test
    void thongKeDoanhThuTheoNgayTESTSuccess(){
        LocalDate ngayBatDau = LocalDate.of(2025, 1, 1);
        LocalDate ngayKetThuc = LocalDate.of(2025, 4, 22);

        List<LocalDate> localDatesExpected = List.of(
                LocalDate.of(2025, 4, 20),
                LocalDate.of(2025, 4, 19),
                LocalDate.of(2025, 4, 18),
                LocalDate.of(2025, 1, 25),
                LocalDate.of(2025, 1, 9)
        );

        Double[] totalAmountExpected = {4718916.62, 1084065.52, 2.92032663308E9, 1.692498976308E10, 2.572394993562E10};



        int i = 0;
        Assertions.assertEquals(1, 1);
        for(Map.Entry<LocalDate, Double> entry :  chiTietHoaDonDAO.thongKeDoanhThuTheoNgay(ngayBatDau, ngayKetThuc).entrySet()){
            Assertions.assertEquals(localDatesExpected.get(i), entry.getKey());
            Assertions.assertEquals(totalAmountExpected[i], entry.getValue());
            i++;
        }
//        chiTietHoaDonDAO.thongKeDoanhThuTheoNgay(ngayBatDau, ngayKetThuc).entrySet().forEach(entry -> {
//            System.out.println(entry.getKey());
//            System.out.println(entry.getValue());
//        });

        double totalActual = chiTietHoaDonDAO.thongKeDoanhThuTheoNgay(ngayBatDau, ngayKetThuc)
                .entrySet().stream().mapToDouble(entry -> entry.getValue()).sum();
        double totalExpected = Arrays.stream(totalAmountExpected).mapToDouble(total -> total).sum();
        Assertions.assertEquals(totalExpected, totalActual);
    }

    @Test
    void getSoLuongDonTheoNamTESTSuccess(){
        long soLuongExpected = 709;
        int year = 2025;
        Assertions.assertEquals(soLuongExpected, chiTietHoaDonDAO.getSoLuongDonTheoNam(year));
    }

    @Test
    void getSoLuongDonTheoNgayTESTSuccess(){
        long soLuongExpected = 709;
        LocalDate ngayBatDau = LocalDate.of(2025, 1, 1);
        LocalDate ngayKetThuc = LocalDate.of(2025, 4, 22);
        Assertions.assertEquals(soLuongExpected, chiTietHoaDonDAO.getSoLuongDonTheoNgay(ngayBatDau, ngayKetThuc));
    }


    @Test
    void getTongKhachHangTheoNamTESTSuccess(){
        long tongKhachExpected = 18;
        int year = 2025;
        Assertions.assertEquals(tongKhachExpected, chiTietHoaDonDAO.getTongKhachHangTheoNam(year));
    }

    @Test
    void getTongKhachHangTheoNgayTESTSuccess(){
        long tongKhachExpected = 18;
        LocalDate ngayBatDau = LocalDate.of(2025, 1, 1);
        LocalDate ngayKetThuc = LocalDate.of(2025, 4, 22);
        Assertions.assertEquals(tongKhachExpected, chiTietHoaDonDAO.getTongKhachHangTheoNgay(ngayBatDau, ngayKetThuc));
    }

    @AfterAll
    void tearDown(){
        chiTietHoaDonDAO = null;
    }

}
