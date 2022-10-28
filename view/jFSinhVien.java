/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.LopController;
import controller.PhieuMuonController;
import controller.SachController;
import controller.SinhVienController;
import controller.TheLoaiSachController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.PhieuMuon;
import model.Sach;
import model.SinhVien;
import model.TheLoaiSach;

/**
 *
 * @author OS
 */
public class jFSinhVien extends javax.swing.JFrame {

    ArrayList<SinhVien> listsv;
    ArrayList<Sach> lists;
    ArrayList<TheLoaiSach> listtls;
    ArrayList<PhieuMuon> listpm;
    Login login = new Login();
    String sdt = login.getSDT();

    /**
     * Creates new form SinhVien
     */
    public jFSinhVien() {
        initComponents();
        loadSinhVien();
        loadSach();
        loadPhieuMuon();
        lblWelcome.setText("Welcome: " + txtHoTen.getText());

    }

    public void loadSinhVien() {
        SinhVienController sinhVienController = new SinhVienController();
        SinhVien sinhVien = sinhVienController.LoadSvTheoSdt(sdt);
        txtMaSV.setText(sinhVien.getMaSv());
        LopController lopController = new LopController();
        txtLop.setText(lopController.layTenTheoMaLop(sinhVien.getMaLop()));
        txtHoTen.setText(sinhVien.getHoTen());
        Date date;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            dateNgaySinh.setDate(formatter.parse(sinhVien.getNgaySinh()));
        } catch (ParseException ex) {
            Logger.getLogger(jFSinhVien.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (sinhVien.getGioiTinh()) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }

        txtSDT.setText(sinhVien.getSoDienThoai());
        txtDiaChi.setText(sinhVien.getDiaChi());
        txtEmail.setText(sinhVien.getEmail());

    }

    public void loadSachVienTim() {
        SachController svc = new SachController();
        ArrayList<Sach> list = svc.searchSach(txtSearch.getText());
        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
        model.setRowCount(0);

        for (Sach sv : list) {

            Object[] row = new Object[]{
                sv.getMaSach(),
                sv.getTenSach(),
                sv.getMaTheLoai(),
                sv.getTacGia(),
                sv.getSoLuong(),
                sv.getNgay(),
                sv.getMaNXB(),
                sv.getNoiDung(),};
            model.addRow(row);
        }
    }

     public void loadPhieuMuon() {
        tblPhieuMuon.setModel(new DefaultTableModel(null, new String[]{"Mã Phiếu Mượn", "Mã Sinh Viên", "Mã Sách", "Số Lượng", "Ngày Mượn", "Ngày Hẹn Trả", "Đã Trả"}));
        PhieuMuonController controller = new PhieuMuonController();
        ArrayList<PhieuMuon> list = controller.listPhieuMuon();
        list = controller.layPMTheoMaSV(txtMaSV.getText());
        DefaultTableModel model = (DefaultTableModel) tblPhieuMuon.getModel();

        Object rowData[] = new Object[7];
        for (PhieuMuon item : list) {
            rowData[0] = item.getMaPhieuMuon();
            rowData[1] = item.getMaSinhVien();
            rowData[2] = item.getMaSach();
            rowData[3] = item.getSoLuong();

            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat reFormatter = new SimpleDateFormat("dd-MM-yyyy");
                Date dateMuon = formatter.parse(item.getNgayMuon());
                String dateMuon1 = reFormatter.format(dateMuon);
                rowData[4] = dateMuon1;

                Date dateTra = formatter.parse(item.getNgayHetHan());
                String dateTra1 = reFormatter.format(dateTra);
                rowData[5] = dateTra1;
            } catch (ParseException ex) {
                Logger.getLogger(Admin_PhieuMuon.class.getName()).log(Level.SEVERE, null, ex);
            }
            String traSach = "Chưa Trả";
            if (item.isDaTra() == true) {
                traSach = "Đã Trả";
            }
            rowData[6] = traSach;
            model.addRow(rowData);
        }

    }

    private void loadSach() {
        tblSach.setModel(new DefaultTableModel(null, new String[]{"Mã sách", "Tên sách", "TL sách", "Tác giả", "Số lượng", "NXB", "Ngày nhập", "ND"}));
        ArrayList<Sach> list = new SachController().danhSachTatCaSach();
        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
        Object rowData[] = new Object[9];
        TheLoaiSachController lsc = new TheLoaiSachController();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat reFormatter = new SimpleDateFormat("dd-MM-yyyy");

        for (Sach item : list) {
            rowData[0] = item.getMaSach();
            rowData[1] = item.getTenSach();
            rowData[2] = lsc.layTenTheoMa(item.getMaTheLoai());
            rowData[3] = item.getTacGia();
            rowData[4] = item.getSoLuong();
            rowData[5] = item.getMaNXB();
            try {
                rowData[6] = reFormatter.format(formatter.parse(item.getNgay()));
            } catch (ParseException ex) {
                Logger.getLogger(Admin_QlSinhVien.class.getName()).log(Level.SEVERE, null, ex);
            }

            rowData[7] = item.getNoiDung();
            model.addRow(rowData);
        }

    }

    public boolean valiformSV() {
        if (!(txtPass.getText()).matches("\\w{3,50}") && !(txtPass.getText().equals(""))) {
            txtPass.requestFocus();
            JOptionPane.showMessageDialog(this, "Mật khẩu ít nhất 3 kí tự");
            return false;
        } else if (txtDiaChi.getText().equals("")) {
            txtDiaChi.requestFocus();
            JOptionPane.showMessageDialog(this, "Chưa nhập Địa chỉ");
            return false;
        } else if (txtSDT.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập SĐT");
            txtSDT.requestFocus();
            return false;
        } else if (!(txtSDT.getText().matches("\\d{10,11}"))) {
            txtSDT.requestFocus();
            JOptionPane.showMessageDialog(this, "SĐT phải là số, 10 - 11 số");
            return false;
        } else if (txtEmail.getText().equals("")) {
            txtEmail.requestFocus();
            JOptionPane.showMessageDialog(this, "Chưa nhập Email");
            return false;
        } else if (!(txtEmail.getText().matches("\\w+@\\w+\\.\\w{1,3}"))) {
            txtEmail.requestFocus();
            JOptionPane.showMessageDialog(this, "Nhập Email đúng định dạng");
            return false;
        } else {
            return true;
        }
    }

    public void updatesv() {
        SinhVien sv = new SinhVien();
        sv.setMaSv(txtMaSV.getText());
        sv.setPassword(txtPass.getText());
        sv.setHoTen(txtHoTen.getText());
        Date date = dateNgaySinh.getDate();
        String df = new SimpleDateFormat("yyyy-MM-dd").format(date);
        sv.setNgaySinh(df);
        boolean gt;
        if (rdoNam.isSelected()) {
            gt = true;
        } else {
            gt = false;
        }
        sv.setGioiTinh(gt);
        sv.setDiaChi(txtDiaChi.getText());
        sv.setSoDienThoai(txtSDT.getText());
        sv.setEmail(txtEmail.getText());
        SinhVienController svc = new SinhVienController();
        if (txtPass.getText().equals("")) {
            if (svc.updateNoPass(sv) > 0) {
                JOptionPane.showMessageDialog(null, "cập nhật thành công");
//                clearFormInput();
            } else {
                JOptionPane.showMessageDialog(null, "cập nhật thất bại");
            }
        } else {
            if (svc.update(sv) > 0) {
                JOptionPane.showMessageDialog(null, "cập nhật thành công");
            } else {
                JOptionPane.showMessageDialog(null, "cập nhật thất bại");
            }
        }
    }

    public void loadSinhVienTimTheoTen() {
        SachController sachController = new SachController();
        ArrayList<Sach> listSach = sachController.searchSachTheoTen(txtSearch.getText());
        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
        model.setRowCount(0);

        for (Sach sach : listSach) {
            Object[] row = new Object[]{
                sach.getTenSach(),
                sach.getTacGia(),
                sach.getSoLuong(),
                sach.getNoiDung()
            };
            model.addRow(row);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        pnlForm = new javax.swing.JPanel();
        lblAvt = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();
        btnDangXuat = new javax.swing.JButton();
        pnl1 = new javax.swing.JPanel();
        tab = new javax.swing.JTabbedPane();
        tabSach = new javax.swing.JTabbedPane();
        pnl3 = new javax.swing.JPanel();
        lblTitleSach = new javax.swing.JLabel();
        pnl4 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        lblIconSearch = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnl5 = new javax.swing.JPanel();
        pnl7 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();
        tabPM = new javax.swing.JTabbedPane();
        pnl11 = new javax.swing.JPanel();
        lblTitlePM = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblPhieuMuon = new javax.swing.JTable();
        lblMaSach1 = new javax.swing.JLabel();
        lblSoLuong1 = new javax.swing.JLabel();
        tabSV = new javax.swing.JTabbedPane();
        pnl2 = new javax.swing.JPanel();
        lblTitleSV = new javax.swing.JLabel();
        lblMaSV = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        lblHoTen = new javax.swing.JLabel();
        lblNgaySinh = new javax.swing.JLabel();
        lblGioiTinh = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblDiaChi = new javax.swing.JLabel();
        lblSDT = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtPass = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtMaSV = new javax.swing.JTextField();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        btnCapNhat = new javax.swing.JButton();
        dateNgaySinh = new com.toedter.calendar.JDateChooser();
        lblPassword1 = new javax.swing.JLabel();
        lblHoTen1 = new javax.swing.JLabel();
        lblDiaChi1 = new javax.swing.JLabel();
        lblSDT1 = new javax.swing.JLabel();
        lblEmail1 = new javax.swing.JLabel();
        txtLop = new javax.swing.JTextField();
        lblMaSV1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnlForm.setBackground(new java.awt.Color(197, 197, 197));
        pnlForm.setMinimumSize(new java.awt.Dimension(1000, 600));

        lblAvt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/hinh4-removebg-preview.png"))); // NOI18N

        lblWelcome.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblWelcome.setText("Welcome: ");

        btnDangXuat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnDangXuat.setForeground(new java.awt.Color(255, 0, 0));
        btnDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Exit.png"))); // NOI18N
        btnDangXuat.setText("Đăng xuất");
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });

        pnl1.setBackground(new java.awt.Color(197, 197, 197));

        tab.setBackground(new java.awt.Color(197, 197, 197));
        tab.setForeground(new java.awt.Color(0, 51, 255));
        tab.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        tab.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tab.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        tabSach.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tabSach.setPreferredSize(new java.awt.Dimension(890, 420));

        pnl3.setBackground(new java.awt.Color(197, 197, 197));
        pnl3.setPreferredSize(new java.awt.Dimension(890, 420));

        lblTitleSach.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblTitleSach.setForeground(new java.awt.Color(255, 255, 0));
        lblTitleSach.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleSach.setText("SÁCH");

        pnl4.setBackground(new java.awt.Color(204, 204, 255));
        pnl4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm Kiếm theo Mã Sách, Tên Sách, Mã TL", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N
        pnl4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pnl4.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 20, 250, 30));
        pnl4.add(lblIconSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 19, -1, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Search_1.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        pnl4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, -1, -1));

        pnl5.setBackground(new java.awt.Color(197, 197, 197));
        pnl5.setLayout(new java.awt.CardLayout());

        pnl7.setBackground(new java.awt.Color(197, 197, 197));

        tblSach.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblSach.setRowHeight(30);
        jScrollPane6.setViewportView(tblSach);

        javax.swing.GroupLayout pnl7Layout = new javax.swing.GroupLayout(pnl7);
        pnl7.setLayout(pnl7Layout);
        pnl7Layout.setHorizontalGroup(
            pnl7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl7Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 742, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl7Layout.setVerticalGroup(
            pnl7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnl3Layout = new javax.swing.GroupLayout(pnl3);
        pnl3.setLayout(pnl3Layout);
        pnl3Layout.setHorizontalGroup(
            pnl3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitleSach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnl3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnl3Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(pnl4, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl3Layout.setVerticalGroup(
            pnl3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl3Layout.createSequentialGroup()
                .addComponent(lblTitleSach, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnl3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnl5, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(pnl3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl7, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(67, Short.MAX_VALUE))))
        );

        tabSach.addTab("Sách", pnl3);

        tab.addTab("          Sách           ", null, tabSach, "");

        tabPM.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tabPM.setPreferredSize(new java.awt.Dimension(890, 420));

        pnl11.setBackground(new java.awt.Color(197, 197, 197));

        lblTitlePM.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblTitlePM.setForeground(new java.awt.Color(255, 255, 0));
        lblTitlePM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitlePM.setText("PHIẾU MƯỢN SÁCH");

        tblPhieuMuon.setAutoCreateRowSorter(true);
        tblPhieuMuon.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblPhieuMuon.setRowHeight(30);
        jScrollPane4.setViewportView(tblPhieuMuon);

        lblMaSach1.setForeground(new java.awt.Color(255, 0, 0));

        lblSoLuong1.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout pnl11Layout = new javax.swing.GroupLayout(pnl11);
        pnl11.setLayout(pnl11Layout);
        pnl11Layout.setHorizontalGroup(
            pnl11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl11Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(lblMaSach1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnl11Layout.createSequentialGroup()
                .addGroup(pnl11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitlePM, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl11Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSoLuong1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnl11Layout.setVerticalGroup(
            pnl11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitlePM, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(lblMaSach1)
                .addGroup(pnl11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl11Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(lblSoLuong1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(261, 261, 261))
                    .addGroup(pnl11Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        tabPM.addTab("Lập phiếu Mượn", pnl11);

        tab.addTab("     Phiếu Mượn    ", null, tabPM, "");

        tabSV.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tabSV.setPreferredSize(new java.awt.Dimension(890, 420));

        pnl2.setBackground(new java.awt.Color(197, 197, 197));

        lblTitleSV.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblTitleSV.setForeground(new java.awt.Color(255, 255, 0));
        lblTitleSV.setText("THÔNG TIN SINH VIÊN");

        lblMaSV.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblMaSV.setText("Mã SV");

        lblPassword.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblPassword.setText("Password");

        lblHoTen.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblHoTen.setText("Họ tên");

        lblNgaySinh.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblNgaySinh.setText("Ngày sinh");

        lblGioiTinh.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblGioiTinh.setText("Giới tính");

        lblEmail.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblEmail.setText("Email");

        lblDiaChi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblDiaChi.setText("Địa chỉ");

        lblSDT.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblSDT.setText("SĐT");

        txtHoTen.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtEmail.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtPass.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtDiaChi.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtSDT.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });

        txtMaSV.setEditable(false);
        txtMaSV.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        buttonGroup1.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        rdoNu.setText("Nữ");

        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        dateNgaySinh.setDateFormatString("dd-MM-yyyy");

        lblPassword1.setForeground(new java.awt.Color(255, 0, 0));

        lblHoTen1.setForeground(new java.awt.Color(255, 0, 0));

        lblDiaChi1.setForeground(new java.awt.Color(255, 0, 0));

        lblSDT1.setForeground(new java.awt.Color(255, 0, 0));

        lblEmail1.setForeground(new java.awt.Color(255, 0, 0));

        txtLop.setEditable(false);
        txtLop.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lblMaSV1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblMaSV1.setText("Lớp");

        javax.swing.GroupLayout pnl2Layout = new javax.swing.GroupLayout(pnl2);
        pnl2.setLayout(pnl2Layout);
        pnl2Layout.setHorizontalGroup(
            pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitleSV, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(221, 221, 221))
            .addGroup(pnl2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl2Layout.createSequentialGroup()
                        .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl2Layout.createSequentialGroup()
                                .addComponent(lblMaSV)
                                .addGap(33, 33, 33)
                                .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl2Layout.createSequentialGroup()
                                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblHoTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHoTen1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnl2Layout.createSequentialGroup()
                                .addComponent(lblNgaySinh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dateNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                        .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl2Layout.createSequentialGroup()
                                .addComponent(lblEmail)
                                .addGap(26, 26, 26)
                                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblEmail1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtEmail)))
                            .addGroup(pnl2Layout.createSequentialGroup()
                                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDiaChi)
                                    .addComponent(lblSDT)
                                    .addComponent(lblMaSV1))
                                .addGap(18, 18, 18)
                                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtLop, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDiaChi1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSDT1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(146, 146, 146))
                    .addGroup(pnl2Layout.createSequentialGroup()
                        .addComponent(lblGioiTinh)
                        .addGap(18, 18, 18)
                        .addComponent(rdoNam)
                        .addGap(18, 18, 18)
                        .addComponent(rdoNu)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(pnl2Layout.createSequentialGroup()
                .addGap(338, 338, 338)
                .addComponent(btnCapNhat)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtDiaChi, txtEmail, txtHoTen, txtPass, txtSDT});

        pnl2Layout.setVerticalGroup(
            pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl2Layout.createSequentialGroup()
                .addComponent(lblTitleSV, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblMaSV1)
                        .addComponent(txtLop, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblMaSV)
                        .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPassword)
                        .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblDiaChi))
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDiaChi1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSDT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblHoTen)
                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSDT)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHoTen1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSDT1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl2Layout.createSequentialGroup()
                        .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblNgaySinh))
                            .addComponent(dateNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblGioiTinh)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu)))
                    .addGroup(pnl2Layout.createSequentialGroup()
                        .addGroup(pnl2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmail))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );

        tabSV.addTab("", pnl2);

        tab.addTab(" Cập nhật thông tin", null, tabSV, "");

        javax.swing.GroupLayout pnl1Layout = new javax.swing.GroupLayout(pnl1);
        pnl1.setLayout(pnl1Layout);
        pnl1Layout.setHorizontalGroup(
            pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, 955, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(106, Short.MAX_VALUE))
        );
        pnl1Layout.setVerticalGroup(
            pnl1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );

        tab.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout pnlFormLayout = new javax.swing.GroupLayout(pnlForm);
        pnlForm.setLayout(pnlFormLayout);
        pnlFormLayout.setHorizontalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addComponent(pnl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(lblAvt, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(lblWelcome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121))))
        );
        pnlFormLayout.setVerticalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblWelcome)
                            .addComponent(btnDangXuat))
                        .addGap(50, 50, 50))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblAvt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(pnl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        txtMaSV.setEditable(false);
        txtLop.setEditable(false);
        txtHoTen.setEditable(false);
        dateNgaySinh.setEnabled(false);
    }//GEN-LAST:event_formWindowOpened

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        int tk = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn cập nhật không");
        if (tk == JOptionPane.YES_OPTION) {
            if (valiformSV() == true) {
                updatesv();
            }
        } else {
            return;
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        // TODO add your handling code here:
        login.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        loadSinhVienTimTheoTen();
    }//GEN-LAST:event_jLabel1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(jFSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jFSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jFSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jFSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jFSinhVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnDangXuat;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private com.toedter.calendar.JDateChooser dateNgaySinh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblAvt;
    private javax.swing.JLabel lblDiaChi;
    private javax.swing.JLabel lblDiaChi1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEmail1;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblHoTen1;
    private javax.swing.JLabel lblIconSearch;
    private javax.swing.JLabel lblMaSV;
    private javax.swing.JLabel lblMaSV1;
    private javax.swing.JLabel lblMaSach1;
    private javax.swing.JLabel lblNgaySinh;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPassword1;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblSDT1;
    private javax.swing.JLabel lblSoLuong1;
    private javax.swing.JLabel lblTitlePM;
    private javax.swing.JLabel lblTitleSV;
    private javax.swing.JLabel lblTitleSach;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JPanel pnl1;
    private javax.swing.JPanel pnl11;
    private javax.swing.JPanel pnl2;
    private javax.swing.JPanel pnl3;
    private javax.swing.JPanel pnl4;
    private javax.swing.JPanel pnl5;
    private javax.swing.JPanel pnl7;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTabbedPane tabPM;
    private javax.swing.JTabbedPane tabSV;
    private javax.swing.JTabbedPane tabSach;
    private javax.swing.JTable tblPhieuMuon;
    private javax.swing.JTable tblSach;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtLop;
    private javax.swing.JTextField txtMaSV;
    private javax.swing.JTextField txtPass;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
