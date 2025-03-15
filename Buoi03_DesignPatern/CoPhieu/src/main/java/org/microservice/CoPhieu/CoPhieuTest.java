package org.microservice.CoPhieu;

public class CoPhieuTest {
    public static void main(String[] args) {
        CoPhieu coPhieu = new CoPhieu(1, "VNX", 10000);

        Observer ndt1 = new NhaDauTu(1);
        Observer ndt2 = new NhaDauTu(2);
        Observer ndt3 = new NhaDauTu(3);
        Observer ndt4 = new NhaDauTu(4);

        coPhieu.add(ndt1);
        coPhieu.add(ndt2);
        coPhieu.add(ndt3);

        ndt1.setSubject(coPhieu);
        ndt2.setSubject(coPhieu);
        ndt3.setSubject(coPhieu);

        ndt1.update();

        coPhieu.changePrice(11000);
    }
}
