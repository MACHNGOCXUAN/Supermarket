select * from thuoctinhsanphams;


select * from taikhoans t join nhanviens nv
    on t.ma_nhan_vien = nv.ma_nhan_vien
         where nv.chuc_vu_nhan_vien = 'NHANVIEN';

select * from taikhoans t join nhanviens nv
on t.ma_nhan_vien = nv.ma_nhan_vien
where nv.chuc_vu_nhan_vien = 'NGUOIQUANLY';



select * from chitiethoadons;
select * from nhanviens where ma_nhan_vien= 'NV35652';
select * from sanphams;
select * from hoadons where ma_hoa_don = 'HD99324';
select * from khachhangs where ma_khach_hang = 'KH03129';
select * from khuyenmais where ma_san_pham = 'SP88976';
select * from danhmucsanphams;
select * from sanphams;

select * from sanphams where ma_san_pham = 'SP88976';

select * from khuyenmais;

UPDATE chitiethoadons
SET thanh_tien = so_luong * don_gia;
