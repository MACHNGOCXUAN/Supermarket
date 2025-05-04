import service.*;
import service.impl.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) throws NamingException, RemoteException, UnknownHostException {
        Context context = new InitialContext();
        KhachHangService khachHangService = new KhachHangServiceImpl();
        SanPhamService sanPhamService = new SanPhamServiceImpl();
        ThuocTinhSanPhamService thuocTinhSanPhamService = new ThuocTinhSanPhamServiceImpl();
        DanhMucSanPhamService danhMucSanPhamService = new DanhMucSanPhamServiceImpl();
        NhanVienService nhanVienService = new NhanVienServiceImpl();
        TaiKhoanService taiKhoanService = new TaiKhoanServiceImpl();

        KhuyenMaiService khuyenMaiService = new KhuyenMaiServiceImpl();
        HoaDonService hoaDonService = new HoaDonServiceImpl();
        ChiTietHoaDonService chiTietHoaDonService = new ChiTietHoaDonServiceImpl();

        String hostname = InetAddress.getLocalHost().getHostName();
        System.setProperty("sun.rmi.transport.tcp.readTimeout", "60000");
        System.setProperty("java.rmi.server.hostname", hostname);
        System.out.println(hostname);
//        System.setProperty("java.rmi.server.hostname", hostname);
        LocateRegistry.createRegistry(9090);





        context.bind("rmi://" + hostname + ":9090/khachHangService", khachHangService);
        context.bind("rmi://" + hostname + ":9090/sanPhamService", sanPhamService);
        context.bind("rmi://" + hostname + ":9090/thuocTinhSanPhamService", thuocTinhSanPhamService);
        context.bind("rmi://" + hostname + ":9090/danhMucSanPhamService", danhMucSanPhamService);
        context.bind("rmi://" + hostname + ":9090/nhanVienService", nhanVienService);
        context.bind("rmi://" + hostname + ":9090/taiKhoanService", taiKhoanService);
        context.bind("rmi://" + hostname + ":9090/hoaDonService", hoaDonService);
        context.bind("rmi://" + hostname + ":9090/chiTietHoaDonService", chiTietHoaDonService);
        context.bind("rmi://" + hostname + ":9090/khuyenMaiService", khuyenMaiService);

        System.out.println("Server is ready !!");
    }
}
