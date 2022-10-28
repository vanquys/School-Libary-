/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.LopController;
import controller.SinhVienController;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.SinhVien;
import util.BaoMat;

/**
 *
 * @author OS
 */
public class Admin_QlSinhVien extends javax.swing.JInternalFrame {

    /**
     * Creates new form Admin_QlSinhVien
     */
    public Admin_QlSinhVien() {
        initComponents();
        loadSinhVien();
        loadComboBoxQlSinhVien();
        setStatus(true);
        txtMaSinhVien.setEnabled(false);

    }

    private void loadComboBoxQlSinhVien() {
        try {
            String sql = "Select * from lop";

            Connection conn = util.ConnectDB.connectSQLServer();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            LopController lopController = new LopController();
            while (rs.next()) {
                comboBoxQlSinhVien.addItem(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Admin_QlSinhVien.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loadSinhVien() {
        tblBang.setModel(new DefaultTableModel(null, new String[]{"Mã SV", "Mật Khẩu", "Lớp", "Họ Tên", "Ngày Sinh", "Giới Tính", "Địa Chỉ", "SDT", "Email"}));
        ArrayList<SinhVien> list = new SinhVienController().danhSachTatCaSinhVien();
        DefaultTableModel model = (DefaultTableModel) tblBang.getModel();
        Object rowData[] = new Object[9];
        LopController lopController = new LopController();
        for (SinhVien item : list) {
            rowData[0] = item.getMaSv();
            rowData[1] = item.getPassword();
            rowData[2] = lopController.layTenTheoMaLop(item.getMaLop());

            rowData[3] = item.getHoTen();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat reFormatter = new SimpleDateFormat("dd-MM-yyyy");
            try {
                rowData[4] = reFormatter.format(formatter.parse(item.getNgaySinh()));
            } catch (ParseException ex) {
                Logger.getLogger(Admin_QlSinhVien.class.getName()).log(Level.SEVERE, null, ex);
            }

            String gt = "Nữ";
            if (item.getGioiTinh() == true) {
                gt = "nam";
            }
            rowData[5] = gt;
            rowData[6] = item.getDiaChi();
            rowData[7] = item.getSoDienThoai();
            rowData[8] = item.getEmail();
            model.addRow(rowData);
        }

    }

    public void insertSinhVien() {

        SinhVien sv = new SinhVien();
        sv.setMaSv(txtMaSinhVien.getText());

        BaoMat baoMat = new BaoMat();
        try {
            sv.setPassword(baoMat.encryptPass(txtMatKhau.getText()));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Admin_QlSinhVien.class.getName()).log(Level.SEVERE, null, ex);
        }

        sv.setHoTen(txtHoVaTen.getText());

        LopController lopController = new LopController();
        sv.setMaLop(lopController.layMaLopTheoTen(comboBoxQlSinhVien.getSelectedItem().toString()));

        boolean gt;
        if (RadNam.isSelected()) {
            gt = true;
        } else {
            gt = false;
        }
        sv.setGioiTinh(gt);
        sv.setDiaChi(txtDiaChi.getText());
        sv.setSoDienThoai(txtSĐT.getText());
        sv.setEmail(txtEmail.getText());
        Date date = txtbirthday.getDate();
        String df = new SimpleDateFormat("yyyy-MM-dd").format(date);
        sv.setNgaySinh(df);
        SinhVienController svc = new SinhVienController();
        if (svc.insert(sv) != 0) {
            JOptionPane.showMessageDialog(null, "Thêm Sinh Viên thành công");
        } else {
            JOptionPane.showMessageDialog(null, "Thêm Sinh Viên Thất Bại");
        }
    }

    public void updateSinhVien() {
        SinhVien sv = new SinhVien();
        sv.setMaSv(txtMaSinhVien.getText());
        sv.setPassword(txtMatKhau.getText());
        sv.setHoTen(txtHoVaTen.getText());
        boolean gt;
        if (RadNam.isSelected()) {
            gt = true;
        } else {
            gt = false;
        }
        sv.setGioiTinh(gt);
        sv.setDiaChi(txtDiaChi.getText());
        sv.setSoDienThoai(txtSĐT.getText());

        LopController lopController = new LopController();
        String maLop = lopController.layMaLopTheoTen((String) comboBoxQlSinhVien.getSelectedItem());

        sv.setMaLop(maLop);

        sv.setEmail(txtEmail.getText());

        Date date = txtbirthday.getDate();
        String df = new SimpleDateFormat("yyyy-MM-dd").format(date);
        sv.setNgaySinh(df);
        SinhVienController svc = new SinhVienController();

        if (txtMatKhau.getText().equals("")) {
            if (svc.updateNoPass(sv) > 0) {
                JOptionPane.showMessageDialog(null, "cập nhật thành công");
                clearFormInput();
                tblBang.setSelectionMode(0);
            } else {
                JOptionPane.showMessageDialog(null, "cập nhật thất bại");
            }
        } else {
            if (svc.update(sv) > 0) {
                JOptionPane.showMessageDialog(null, "cập nhật thành công");
                clearFormInput();
                tblBang.setSelectionMode(0);
            } else {
                JOptionPane.showMessageDialog(null, "cập nhật thất bại");
            }
        }

    }

    public void deleteSinhVien() {
        int index = tblBang.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 Sinh Viên trong bảng để xóa", "Thông Báo", 1);
            return;
        }
        SinhVienController svc = new SinhVienController();

        int tk = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không");
        if (tk == JOptionPane.YES_OPTION) {
            if (svc.delete(txtMaSinhVien.getText()) != 0) {
                JOptionPane.showMessageDialog(this, "Xóa Sinh Viên thành công", "Thông Báo", 1);

            } else {
                JOptionPane.showMessageDialog(this, "Lỗi hệ thống", "Thông Báo", 0);
            }

        } else {
            return;

        }
    }

    public void loadSinhVienTimTheoTen() {
        SinhVienController svc = new SinhVienController();
        ArrayList<SinhVien> listsv = svc.SearchSvTheoTen(txtSearch.getText());
        DefaultTableModel model = (DefaultTableModel) tblBang.getModel();
        model.setRowCount(0);
        LopController lopController = new LopController();
        ArrayList<SinhVien> listsvTheoMa = svc.SearchSvTheoMa(txtSearch.getText());
        for (SinhVien sinhVien : listsvTheoMa) {
            listsv.add(sinhVien);
        }
        for (SinhVien sv : listsv) {
            String tenLop = lopController.layTenTheoMaLop(sv.getMaLop());
            Object[] row = new Object[]{
                sv.getMaSv(),
                sv.getPassword(),
                tenLop,
                sv.getHoTen(),
                sv.getNgaySinh(),
                sv.getGioiTinh(),
                sv.getDiaChi(),
                sv.getSoDienThoai(),
                sv.getEmail()
            };
            model.addRow(row);
        }
    }

    public void clearFormInput() {
        txtMaSinhVien.setText(null);
        txtMatKhau.setText(null);
        txtHoVaTen.setText(null);
        RadNam.setEnabled(true);
        txtDiaChi.setText(null);
        txtSĐT.setText(null);
        txtEmail.setText(null);
        buttonGroup1.clearSelection();
        comboBoxQlSinhVien.setSelectedIndex(0);
        setStatus(true);
    }

    public void setEditForm(Boolean b) {
        txtMatKhau.setEditable(b);
        txtHoVaTen.setEditable(b);
        txtbirthday.setEnabled(b);
        RadNam.setEnabled(b);
        RadNu.setEnabled(b);
        txtDiaChi.setEditable(b);
        txtSĐT.setEditable(b);
        txtEmail.setEditable(b);
        comboBoxQlSinhVien.setEnabled(b);
    }

    public void displayFormInput() {
        SinhVienController sinhVienController = new SinhVienController();
        ArrayList<SinhVien> danhSachTatCaSinhVien = sinhVienController.danhSachTatCaSinhVien();
        int selectedRow = tblBang.getSelectedRow();
        SinhVien sv = danhSachTatCaSinhVien.get(selectedRow);
        txtMaSinhVien.setText(sv.getMaSv());
        txtMatKhau.setText("");
        LopController lopController = new LopController();
        comboBoxQlSinhVien.setSelectedItem(lopController.layTenTheoMaLop(sv.getMaLop()));
        txtHoVaTen.setText(sv.getHoTen());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String txtNgaySinh = sv.getNgaySinh();
        try {
            Date dateSinh = formatter.parse(txtNgaySinh);
            txtbirthday.setDate(dateSinh);
        } catch (ParseException ex) {
            Logger.getLogger(Admin_PhieuMuon.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (sv.getGioiTinh() == true) {
            RadNam.setSelected(true);
        }
        if (sv.getGioiTinh() == false) {
            RadNu.setSelected(true);
        }
        txtDiaChi.setText(sv.getDiaChi());
        txtSĐT.setText(sv.getSoDienThoai());
        txtEmail.setText(sv.getEmail());
    }

    public void setStatus(boolean insertable) {
        btnLuu.setEnabled(insertable);
        btnSua.setEnabled(!insertable);
        btnXoa.setEnabled(!insertable);
    }

    public boolean valiform() {

        if (!(txtMatKhau.getText()).matches("\\w{3,50}")) {
            if (!(txtMatKhau.getText().equals(""))) {
                txtMatKhau.requestFocus();
                JOptionPane.showMessageDialog(this, "Mật khẩu ít nhất 3 kí tự");
                return false;
            }
        } else if (txtHoVaTen.getText().equals("")) {
            txtHoVaTen.requestFocus();
            JOptionPane.showMessageDialog(this, "Chưa nhập Họ Tên");
            return false;
        } else if (!(txtHoVaTen.getText().matches("\\D*"))) {
            txtHoVaTen.requestFocus();
            JOptionPane.showMessageDialog(this, "Họ Tên phải là chữ");
            return false;
        } else if (buttonGroup1.isSelected(null)) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn Giới Tính");
            return false;
        } else if (txtDiaChi.getText().equals("")) {
            txtDiaChi.requestFocus();
            JOptionPane.showMessageDialog(this, "Chưa nhập Địa chỉ");
            return false;
        } else if (txtSĐT.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập SĐT");
            txtSĐT.requestFocus();
            return false;
        } else if (!(txtSĐT.getText().matches("\\d{10,11}"))) {
            txtSĐT.requestFocus();
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
        }

        // tính ngày sinh
        DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, 24);
        Date date1 = null;
        Date date2 = null;
        try {
            String endDate = simpleDateFormat.format(currentDate);
            date2 = simpleDateFormat.parse(endDate);
            long getDiff = date2.getTime() - txtbirthday.getDate().getTime();
            long getDaysDiff = TimeUnit.MILLISECONDS.toDays(getDiff);
            if (getDaysDiff < 6574) {
                JOptionPane.showMessageDialog(this, "Sinh viên phải từ 18 tuổi trở lên ! ");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBang = new javax.swing.JTable();
        pnlThongTin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMaSinhVien = new javax.swing.JTextField();
        txtHoVaTen = new javax.swing.JTextField();
        txtMatKhau = new javax.swing.JTextField();
        RadNam = new javax.swing.JRadioButton();
        RadNu = new javax.swing.JRadioButton();
        txtbirthday = new com.toedter.calendar.JDateChooser();
        txtDiaChi = new javax.swing.JTextField();
        txtSĐT = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        comboBoxQlSinhVien = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        btnLuu = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnTaoMoi = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        search = new javax.swing.JLabel();

        setBackground(new java.awt.Color(28, 56, 86));
        setTitle("Quản Lý Sinh Viên");
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setMinimumSize(new java.awt.Dimension(1200, 650));
        setNormalBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setPreferredSize(new java.awt.Dimension(1154, 754));

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setMinimumSize(new java.awt.Dimension(1200, 650));
        jPanel2.setPreferredSize(new java.awt.Dimension(1154, 754));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(452, 402));

        tblBang.setBackground(new java.awt.Color(51, 204, 255));
        tblBang.setModel(new javax.swing.table.DefaultTableModel(
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
        tblBang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBang);

        pnlThongTin.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        pnlThongTin.setMinimumSize(new java.awt.Dimension(0, 0));
        pnlThongTin.setPreferredSize(new java.awt.Dimension(715, 262));

        jLabel1.setText("Mã SV");

        jLabel2.setText("Mật Khẩu");

        jLabel3.setText("Họ Và Tên");

        jLabel4.setText("Ngày Sinh ");

        jLabel5.setText("Lớp");

        jLabel6.setText("Giới Tính ");

        jLabel7.setText("Địa Chỉ");

        jLabel8.setText("SĐT");

        jLabel9.setText("Email");

        buttonGroup1.add(RadNam);
        RadNam.setText("Nam");

        buttonGroup1.add(RadNu);
        RadNu.setText("Nữ");

        txtbirthday.setDateFormatString("dd-MM-yyyy");

        comboBoxQlSinhVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxQlSinhVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinLayout = new javax.swing.GroupLayout(pnlThongTin);
        pnlThongTin.setLayout(pnlThongTinLayout);
        pnlThongTinLayout.setHorizontalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(txtMaSinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(txtHoVaTen, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(txtbirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(txtSĐT, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboBoxQlSinhVien, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addComponent(RadNam, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(RadNu, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        pnlThongTinLayout.setVerticalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtMaSinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtHoVaTen, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtbirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RadNam)
                    .addComponent(RadNu))
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(comboBoxQlSinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtSĐT, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("QUẢN LÝ SINH VIÊN");

        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save.png"))); // NOI18N
        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save as.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Delete_1.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnTaoMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Create.png"))); // NOI18N
        btnTaoMoi.setText("Tạo Mới");
        btnTaoMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoMoiActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm Kiếm Theo Mã hoặc Tên"));

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Search_1.png"))); // NOI18N
        search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(search)
                .addGap(82, 82, 82))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSearch))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(342, 342, 342)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(159, 159, 159)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnTaoMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(99, 99, 99)
                                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pnlThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(81, 81, 81))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel10)
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlThongTin, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE))
                .addGap(5, 100, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaoMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        btnTaoMoi.setText("Hủy");
        txtMatKhau.requestFocus(true);
        btnXoa.setEnabled(false);
        btnLuu.setEnabled(true);
        btnSua.setEnabled(false);
        setEditForm(true);
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnTaoMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoMoiActionPerformed
        // TODO add your handling code here:
      
        if (btnTaoMoi.getText().equals("Hủy")) {
            btnTaoMoi.setText("Tạo Mới");
        }
        clearFormInput();
        tblBang.setSelectionMode(0);
        setEditForm(true);

    }//GEN-LAST:event_btnTaoMoiActionPerformed

    private void tblBangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangMouseClicked
        displayFormInput();
        setStatus(false);
        setEditForm(false);

    }//GEN-LAST:event_tblBangMouseClicked

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        if (txtbirthday.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn ngày sinh ", "Thông Báo", JOptionPane.OK_OPTION);
            return;
        }

        if (btnTaoMoi.getText().equals("Tạo Mới")) {
            // TAO MOI
            if (valiform() == true) {
                insertSinhVien();
            }
        } else {
            if (valiform() == true) {
                updateSinhVien();
                btnTaoMoi.setText("Tạo Mới");
            }
        }
        loadSinhVien();
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        deleteSinhVien();
        clearFormInput();
        loadSinhVien();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed

    }//GEN-LAST:event_txtSearchActionPerformed

    private void searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchMouseClicked
        loadSinhVienTimTheoTen();
    }//GEN-LAST:event_searchMouseClicked

    private void comboBoxQlSinhVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxQlSinhVienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxQlSinhVienActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton RadNam;
    private javax.swing.JRadioButton RadNu;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTaoMoi;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> comboBoxQlSinhVien;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlThongTin;
    private javax.swing.JLabel search;
    private javax.swing.JTable tblBang;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoVaTen;
    private javax.swing.JTextField txtMaSinhVien;
    private javax.swing.JTextField txtMatKhau;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSĐT;
    private com.toedter.calendar.JDateChooser txtbirthday;
    // End of variables declaration//GEN-END:variables
}
