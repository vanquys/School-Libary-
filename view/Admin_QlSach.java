/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.PhieuMuonController;
import controller.SachController;
import controller.TheLoaiSachController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Sach;
import model.TheLoaiSach;

/**
 *
 * @author OS
 */
public class Admin_QlSach extends javax.swing.JInternalFrame {

    /**
     * Creates new form Admin_QlSach
     */
    public Admin_QlSach() {
        initComponents();
        loadSach();
        loadComboBox();
        txtMaSach.setEnabled(false);
        btnXoa.setEnabled(false);
        btnSua.setEnabled(false);
        setEditForm(false);
    }

    public void loadComboBox() {
        TheLoaiSachController theLoaiSachController = new TheLoaiSachController();
        ArrayList<TheLoaiSach> ListTheLoai = theLoaiSachController.ListTheLoai();
        for (TheLoaiSach theLoaiSach : ListTheLoai) {
            String tenTheLoai = theLoaiSach.getTenTheLoai();
            cbbTheLoai.addItem(tenTheLoai);
        }
    }

    public void insertSach() {

        Sach sv = new Sach();
        sv.setMaSach(txtMaSach.getText());
        sv.setTenSach(txtTenSach.getText());
        sv.setMaNXB(txtNXB.getText());
        TheLoaiSachController sachController = new TheLoaiSachController();
        sv.setMaTheLoai(sachController.layMaTheoTen(cbbTheLoai.getSelectedItem().toString()));
        sv.setTacGia(txtTacGia.getText());
        sv.setSoLuong((String) txtSoLuong.getText());
        Date date = txtbirthday.getDate();
        String df = new SimpleDateFormat("yyyy-MM-dd").format(date);
        sv.setNgay(df);
        sv.setNoiDung(txtNoiDung.getText());

        SachController svc = new SachController();
        if (svc.insert(sv) != 0) {
            JOptionPane.showMessageDialog(null, "Thêm Sách thành công");
        } else {
            JOptionPane.showMessageDialog(null, "Thêm Thất Bại !");
        }
    }

    public void updateSach() {
        Sach s = new Sach();
        s.setMaSach(txtMaSach.getText());
        s.setTenSach(txtTenSach.getText());
        s.setMaNXB(txtNXB.getText());
        TheLoaiSachController sc = new TheLoaiSachController();
        String tLoai = sc.layMaTheoTen((String) cbbTheLoai.getSelectedItem());
        s.setMaTheLoai(tLoai);
        s.setTacGia(txtTacGia.getText());
        
        
        PhieuMuonController pmc = new PhieuMuonController();
        int sachDaMuon = pmc.laySoSachDaMuonChuaTra(txtMaSach.getText());
        int inputSL = Integer.parseInt(txtSoLuong.getText());
        
        s.setSoLuong(String.valueOf(inputSL+sachDaMuon));
        s.setNoiDung(txtNoiDung.getText());
        Date date = txtbirthday.getDate();
        String df = new SimpleDateFormat("yyyy-MM-dd").format(date);
        s.setNgay(df);
        SachController sachc = new SachController();
        if (sachc.update(s) > 0) {
            JOptionPane.showMessageDialog(null, "cập nhật thành công");
            clearFormInput();
            tblQuanLySach.setSelectionMode(0);
        } else {
            JOptionPane.showMessageDialog(null, "cập nhật thất bại");
        }

    }

    private void loadSach() {
        tblQuanLySach.setModel(new DefaultTableModel(null, new String[]{"Mã sách", "Tên sách", "TL sách", "Tác giả", "SL Còn Lại", "NXB", "Ngày nhập", "ND"}));
        ArrayList<Sach> list = new SachController().danhSachTatCaSach();
        DefaultTableModel model = (DefaultTableModel) tblQuanLySach.getModel();
        Object rowData[] = new Object[9];
        TheLoaiSachController lsc = new TheLoaiSachController();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat reFormatter = new SimpleDateFormat("dd-MM-yyyy");
        PhieuMuonController pmc = new PhieuMuonController();
        SachController sc = new SachController();
        for (Sach item : list) {
            rowData[0] = item.getMaSach();
            rowData[1] = item.getTenSach();
            rowData[2] = lsc.layTenTheoMa(item.getMaTheLoai());
            rowData[3] = item.getTacGia();
            int sachDaMuon = pmc.laySoSachDaMuonChuaTra(item.getMaSach());
            int sachDaNhap = Integer.parseInt(sc.laySachTheoMa(item.getMaSach()).getSoLuong());

            rowData[4] = String.valueOf(sachDaNhap - sachDaMuon);
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

    public void deleteSach() {
        int index = tblQuanLySach.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 sách trong bảng để xóa", "Thông Báo", 1);
            return;
        }
        SachController sachController = new SachController();

        int tk = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không");
        if (tk == JOptionPane.YES_OPTION) {
            if (sachController.delete(txtMaSach.getText()) != 0) {
                JOptionPane.showMessageDialog(this, "Xóa Sách thành công", "Thông Báo", 1);

            } else {
                JOptionPane.showMessageDialog(this, "Lỗi hệ thống", "Thông Báo", 0);
            }

        } else {
            return;

        }
    }

    public void loadSachTimKiem() {
        SachController svc = new SachController();
        ArrayList<Sach> list = svc.searchSach(txtSearch.getText());
        DefaultTableModel model = (DefaultTableModel) tblQuanLySach.getModel();
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

    public boolean valiform() {
        
        if (txtTenSach.getText().equals("")) {
            txtTenSach.requestFocus();
            JOptionPane.showMessageDialog(this, "Chưa nhập Tên Sách");
            return false;
        } else if (!(txtTenSach.getText().matches("\\D*"))) {
            txtTenSach.requestFocus();
            JOptionPane.showMessageDialog(this, "Tên sách phải là chữ");
            return false;
        } else if (!(txtTacGia.getText().matches("\\D*"))) {
            txtTacGia.requestFocus();
            JOptionPane.showMessageDialog(this, "Tên tác giả phải là chữ");
            return false;
        } else if (txtNoiDung.getText().equals("")) {
            txtNoiDung.requestFocus();
            JOptionPane.showMessageDialog(this, "Chưa nhập nội dung");
            return false;
        } else if (txtSoLuong.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập số lượng");
            txtSoLuong.requestFocus();
            return false;
        } else {
            return true;
        }

    }

    public void clearFormInput() {
        txtMaSach.setText(null);
        txtTenSach.setText(null);
        txtNXB.setText(null);
        cbbTheLoai.setSelectedIndex(0);
        txtTacGia.setText(null);
        txtSoLuong.setText("1");
        txtNoiDung.setText(null);
        setStatus(true);
    }

    public void setEditForm(Boolean b) {
        txtTenSach.setEditable(b);
        txtNXB.setEditable(b);
        txtbirthday.setEnabled(b);
        cbbTheLoai.setEnabled(b);
        txtTacGia.setEnabled(b);
        txtSoLuong.setEnabled(b);
        txtNoiDung.setEditable(b);
    }

    public void setStatus(boolean insertable) {
        btnLuu.setEnabled(insertable);
        btnSua.setEnabled(!insertable);
        btnXoa.setEnabled(!insertable);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        pnlThongTinSach = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMaSach = new javax.swing.JTextField();
        txtTenSach = new javax.swing.JTextField();
        txtNoiDung = new javax.swing.JTextField();
        txtNXB = new javax.swing.JTextField();
        txtTacGia = new javax.swing.JTextField();
        cbbTheLoai = new javax.swing.JComboBox<>();
        txtSoLuong = new javax.swing.JTextField();
        txtbirthday = new com.toedter.calendar.JDateChooser();
        lblTitle = new javax.swing.JLabel();
        pnlSearchSach = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        ScrollPaneQLSach = new javax.swing.JScrollPane();
        tblQuanLySach = new javax.swing.JTable();
        btnXoa = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnTaoMoi = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setTitle("Quản Lý Sách");
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setMaximumSize(new java.awt.Dimension(1200, 750));
        setMinimumSize(new java.awt.Dimension(1200, 750));
        setName(""); // NOI18N

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        pnlThongTinSach.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel1.setText("Mã Sách :");

        jLabel2.setText("Tên Sách :");

        jLabel3.setText("Thể Loại :");

        jLabel4.setText("NXB :");

        jLabel5.setText("Tác Giả :");

        jLabel6.setText("Số lượng :");

        jLabel7.setText("Ngày Nhập :");

        jLabel8.setText("Nội Dung :");

        txtNoiDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoiDungActionPerformed(evt);
            }
        });

        txtbirthday.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout pnlThongTinSachLayout = new javax.swing.GroupLayout(pnlThongTinSach);
        pnlThongTinSach.setLayout(pnlThongTinSachLayout);
        pnlThongTinSachLayout.setHorizontalGroup(
            pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtMaSach))
                    .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenSach))
                    .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(cbbTheLoai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtbirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtNoiDung))
                    .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtSoLuong))
                    .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtTacGia))
                    .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtNXB)))
                .addContainerGap())
        );

        pnlThongTinSachLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8});

        pnlThongTinSachLayout.setVerticalGroup(
            pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbbTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(txtbirthday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(pnlThongTinSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinSachLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtNoiDung, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlThongTinSachLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtMaSach, txtTenSach});

        lblTitle.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Quản Lý Sách");

        pnlSearchSach.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm Kiếm Theo Mã hoặc Tên"));

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Search_1.png"))); // NOI18N
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlSearchSachLayout = new javax.swing.GroupLayout(pnlSearchSach);
        pnlSearchSach.setLayout(pnlSearchSachLayout);
        pnlSearchSachLayout.setHorizontalGroup(
            pnlSearchSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSearchSachLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addGap(82, 82, 82))
        );
        pnlSearchSachLayout.setVerticalGroup(
            pnlSearchSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSearchSachLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlSearchSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tblQuanLySach.setBackground(new java.awt.Color(51, 204, 255));
        tblQuanLySach.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblQuanLySach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sách", "Tên Sách", "Mã Thể Loại", "Tác Giả", "Số Lượng", "Mã Nhà Xuất Bản", "Ngày xuất bản", "Nội Dung"
            }
        ));
        tblQuanLySach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQuanLySachMouseClicked(evt);
            }
        });
        ScrollPaneQLSach.setViewportView(tblQuanLySach);

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Delete_1.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save.png"))); // NOI18N
        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnTaoMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Create.png"))); // NOI18N
        btnTaoMoi.setText("Tạo Mới");
        btnTaoMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoMoiActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save as.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(450, 450, 450)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(401, 401, 401))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(pnlSearchSach, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(ScrollPaneQLSach, javax.swing.GroupLayout.PREFERRED_SIZE, 739, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnTaoMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlThongTinSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblTitle)
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnlThongTinSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(pnlSearchSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(ScrollPaneQLSach, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaoMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNoiDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoiDungActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoiDungActionPerformed

    private void btnTaoMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoMoiActionPerformed
        if (btnTaoMoi.getText().equals("Hủy")) {
            btnTaoMoi.setText("Tạo Mới");
        }
        clearFormInput();
        tblQuanLySach.setSelectionMode(0);
        setEditForm(true);
    }//GEN-LAST:event_btnTaoMoiActionPerformed

    public void displayFormInput() {
        SachController sachController = new SachController();
        ArrayList<Sach> danhSachTatCaSach = sachController.danhSachTatCaSach();
        int selectedRow = tblQuanLySach.getSelectedRow();
        Sach sach = danhSachTatCaSach.get(selectedRow);
        txtMaSach.setText(sach.getMaSach());
        txtTenSach.setText(sach.getTenSach());
        txtNXB.setText(sach.getMaNXB());
        TheLoaiSachController tlSachController = new TheLoaiSachController();
        String tenTl = tlSachController.layTenTheoMa(sach.getMaTheLoai());
        cbbTheLoai.setSelectedItem(tenTl);
        txtTacGia.setText(sach.getTacGia());

        PhieuMuonController pmc = new PhieuMuonController();
        int sachDaMuon = pmc.laySoSachDaMuonChuaTra(txtMaSach.getText());
        int sachDaNhap = Integer.parseInt(sach.getSoLuong());
        txtSoLuong.setText(String.valueOf(sachDaNhap - sachDaMuon));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String txtNgay = sach.getNgay();
        try {
            Date dateSinh = formatter.parse(txtNgay);
            txtbirthday.setDate(dateSinh);
        } catch (ParseException ex) {
            Logger.getLogger(Admin_PhieuMuon.class.getName()).log(Level.SEVERE, null, ex);
        }

        txtNoiDung.setText(sach.getNoiDung());

    }

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
       
        if (txtbirthday.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn ngày nhập ", "Thông Báo", JOptionPane.OK_OPTION);
            return;
        }
        if (btnTaoMoi.getText().equals("Tạo Mới")) {
            // TAO MOI
            if (valiform() == true) {

                insertSach();
            }
        } else {
            if (valiform() == true) {
                updateSach();
                btnTaoMoi.setText("Tạo Mới");
            }
        }
        loadSach();
    }//GEN-LAST:event_btnLuuActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed


    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        deleteSach();
        clearFormInput();
        loadSach();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        btnTaoMoi.setText("Hủy");
        btnXoa.setEnabled(false);
        btnLuu.setEnabled(true);
        btnSua.setEnabled(false);
        setEditForm(true);
    }//GEN-LAST:event_btnSuaActionPerformed

    private void tblQuanLySachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQuanLySachMouseClicked
        btnXoa.setEnabled(true);
        btnSua.setEnabled(true);
        displayFormInput();
    }//GEN-LAST:event_tblQuanLySachMouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        loadSachTimKiem();
    }//GEN-LAST:event_jLabel11MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPaneQLSach;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTaoMoi;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbbTheLoai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlSearchSach;
    private javax.swing.JPanel pnlThongTinSach;
    private javax.swing.JTable tblQuanLySach;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtNXB;
    private javax.swing.JTextField txtNoiDung;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTacGia;
    private javax.swing.JTextField txtTenSach;
    private com.toedter.calendar.JDateChooser txtbirthday;
    // End of variables declaration//GEN-END:variables
}
