/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.PhieuMuonController;
import controller.SachController;
import controller.SinhVienController;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.PhieuMuon;

/**
 *
 * @author OS
 */
public class Admin_PhieuMuon extends javax.swing.JInternalFrame {

    /**
     * Creates new form Admin_PhieuMuon
     */
    public Admin_PhieuMuon() {
        initComponents();
        loadPhieuMuon();
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        btnLuu.setEnabled(false);
        txtMaPhieuMuon.setEnabled(false);
        txtSoLuong.setEnabled(false);
        txtSoLuong.setText("1");
        setEditForm(false);
    }

    public void loadPhieuMuon() {
        tblBang.setModel(new DefaultTableModel(null, new String[]{"Mã Phiếu Mượn", "Mã Sinh Viên", "Mã Sách", "Số Lượng", "Ngày Mượn", "Ngày Hẹn Trả", "Đã Trả"}));
        PhieuMuonController controller = new PhieuMuonController();
        ArrayList<PhieuMuon> list = controller.listPhieuMuon();
        DefaultTableModel model = (DefaultTableModel) tblBang.getModel();

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

    public void setEditForm(Boolean a) {
        txtMaSinhVien.setEditable(a);
        txtMaSach.setEditable(a);
        txtNgayMuon.setEnabled(a);
        txtNgayTra.setEnabled(a);
        rdoChuaTra.setSelected(true);
    }

    public void clearFormInput() {
        txtMaPhieuMuon.setText("");
        txtMaSinhVien.setText("");
        txtMaSach.setText("");
        txtNgayMuon.setEnabled(false);
        txtNgayTra.setEnabled(false);
        rdoChuaTra.setSelected(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBang = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnSua = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnTaoMoi = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaPhieuMuon = new javax.swing.JTextField();
        txtMaSach = new javax.swing.JTextField();
        txtMaSinhVien = new javax.swing.JTextField();
        lblMaTheLoai1 = new javax.swing.JLabel();
        lblTenTheLoai1 = new javax.swing.JLabel();
        lblViTri1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        rdoDaTra = new javax.swing.JRadioButton();
        rdoChuaTra = new javax.swing.JRadioButton();
        txtNgayMuon = new com.toedter.calendar.JDateChooser();
        txtNgayTra = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnXoa = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        tblBang.setBackground(new java.awt.Color(51, 204, 255));
        tblBang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Phiếu Mượn", "Mã Sinh Viên", "Mã Sách", "Số Lượng", "Ngày Mượn", "Ngày Hẹn Trả", "Đã Trả"
            }
        ));
        tblBang.setGridColor(new java.awt.Color(0, 0, 0));
        tblBang.setRowHeight(30);
        tblBang.setSurrendersFocusOnKeystroke(true);
        tblBang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBang);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Phiếu Mượn");

        btnSua.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save as.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnLuu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save.png"))); // NOI18N
        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnTaoMoi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnTaoMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Create.png"))); // NOI18N
        btnTaoMoi.setText("Tạo Mới");
        btnTaoMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoMoiActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Mã Phiếu Mượn");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Mã Sinh Viên");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Mã sách");

        lblMaTheLoai1.setForeground(new java.awt.Color(255, 0, 0));

        lblTenTheLoai1.setForeground(new java.awt.Color(255, 0, 0));

        lblViTri1.setForeground(new java.awt.Color(255, 0, 0));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setText("Số Lượng");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setText("Ngày Mượn");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setText("Ngày Trả");

        buttonGroup1.add(rdoDaTra);
        rdoDaTra.setText("Đã Trả");

        buttonGroup1.add(rdoChuaTra);
        rdoChuaTra.setText("Chưa Trả");

        txtNgayMuon.setDateFormatString("dd-MM-yyyy");

        txtNgayTra.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(180, 180, 180)
                                        .addComponent(lblTenTheLoai1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblMaTheLoai1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(132, 132, 132)
                                        .addComponent(lblViTri1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtMaSinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(rdoChuaTra)
                                .addGap(53, 53, 53)
                                .addComponent(rdoDaTra, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(40, 40, 40))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(lblMaTheLoai1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaSinhVien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(lblTenTheLoai1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(71, 71, 71)
                                        .addComponent(lblViTri1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(46, 46, 46)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(46, 46, 46)
                                .addComponent(jLabel8))
                            .addComponent(txtNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoChuaTra)
                    .addComponent(rdoDaTra))
                .addGap(30, 30, 30))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm Mã TL, Tên TL hoặc Vị trí", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Search_1.png"))); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearch)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnXoa.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Delete_1.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnTaoMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(459, 459, 459)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnLuu, btnTaoMoi});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaoMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void suaPhieuMuon() {
        PhieuMuonController pmc = new PhieuMuonController();
        ArrayList<PhieuMuon> listPhieuMuon = pmc.listPhieuMuon();

//        PhieuMuon pmBefore = (PhieuMuon) listPhieuMuon.get(tblBang.getSelectedRow());
//        String soLuongBefore = pmBefore.getSoLuong();
        String maPM = txtMaPhieuMuon.getText();
        String maSv = txtMaSinhVien.getText();
        String maSach = txtMaSach.getText();
        String soLuong = txtSoLuong.getText();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat reFormatter = new SimpleDateFormat("dd-MM-yyyy");

        Date txtMuon = txtNgayMuon.getDate();
        Date txtTra = txtNgayTra.getDate();
        String dateMuon = formatter.format(txtMuon);
        String dateTra = formatter.format(txtTra);
        Boolean daTra = true;
        if (rdoChuaTra.isSelected()) {
            daTra = false;
        }

        PhieuMuon pm = new PhieuMuon(maPM, maSv, maSach, soLuong, dateMuon, dateTra, daTra);
        if (pmc.capNhatPM(pm) == 0) {
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại", "Thông Báo", JOptionPane.OK_OPTION);
            return;
        } else {
            JOptionPane.showMessageDialog(null, "Cập nhật Thành Công !", "Thông Báo", 1);
//            new SachController().bienDongSach(maPM);
            loadPhieuMuon();
            return;
        }

    }

    public void themMoiPM() {
        PhieuMuonController pmc = new PhieuMuonController();
        String maPM = null;
        String maSv = txtMaSinhVien.getText();
        String maSach = txtMaSach.getText();
        String soLuong = txtSoLuong.getText();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat reFormatter = new SimpleDateFormat("dd-MM-yyyy");

        Date txtMuon = txtNgayMuon.getDate();
        Date txtTra = txtNgayTra.getDate();
        String dateMuon = formatter.format(txtMuon);
        String dateTra = formatter.format(txtTra);
        Boolean daTra = true;
        if (rdoChuaTra.isSelected()) {
            daTra = false;
        }

        PhieuMuon pm = new PhieuMuon(maPM, maSv, maSach, soLuong, dateMuon, dateTra, daTra);
        if (!(pmc.themMoiPhieuMuon(pm))) {
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại", "Thông Báo", JOptionPane.OK_OPTION);
            return;
        } else {
            JOptionPane.showMessageDialog(null, "Cập nhật Thành Công !", "Thông Báo", 1);
//            new SachController().bienDongSach(maPM);
            loadPhieuMuon();
            return;
        }

    }

    public boolean valiform() {
        SinhVienController svc = new SinhVienController();
        SachController sc = new SachController();
        String maSv = txtMaSinhVien.getText();
        String maSach = txtMaSach.getText();
        PhieuMuonController pmc = new PhieuMuonController();

        if (txtMaSinhVien.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập Mã Sinh Viên");
            txtMaSinhVien.requestFocus();
            return false;
        } else if (txtMaSach.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập Mã Sách");
            txtMaSach.requestFocus();
            return false;
        } else if (svc.LoadSvTheoMaSv(maSv) == null) {
            JOptionPane.showMessageDialog(this, "Sinh Viên không tồn tại !");
            return false;
        } else if (sc.laySachTheoMa(maSach) == null) {
            JOptionPane.showMessageDialog(this, "Sách không tồn tại !");
            return false;
        } else {
        }

        int sachDaMuon = pmc.laySoSachDaMuonChuaTra(maSach);
        int sachDaNhap = Integer.parseInt(sc.laySachTheoMa(txtMaSach.getText()).getSoLuong());
        System.out.println("so Luong: " + sc.laySachTheoMa(txtMaSach.getText()).getSoLuong());

        if (sc.laySachTheoMa(maSach) == null) {
            JOptionPane.showMessageDialog(this, " Sách không tồn tại !");
            return false;
        } else if (sachDaNhap - sachDaMuon <= 0) {
            System.out.println("Muon: " + sachDaMuon);
            System.out.println("Nhap: " + sachDaNhap);

            JOptionPane.showMessageDialog(this, " Đã hết sách này trong kho !");
            return false;
        } else {
            System.out.println("vliform xong ");
            return true;
        }
    }

    ;
    
    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed

        if (txtNgayMuon.getDate() == null) {
            JOptionPane.showMessageDialog(null, "bạn chưa nhâp ngày mượn ", "Thông Báo", JOptionPane.OK_OPTION);
            return;
        }

        if (txtNgayTra.getDate() == null) {
            JOptionPane.showMessageDialog(null, "bạn chưa nhâp ngày trả ", "Thông Báo", JOptionPane.OK_OPTION);
            return;
        }

        if (!txtNgayTra.getDate().after(txtNgayMuon.getDate())) {
            JOptionPane.showMessageDialog(null, "Ngày trả phải trễ nơn ngày mượn !", "Thông Báo", JOptionPane.OK_OPTION);
            return;
        }

        if (btnTaoMoi.getText().equals("Hủy")) {
            if (valiform()) {
                suaPhieuMuon();
                clearFormInput();
                btnTaoMoi.setText("Tạo Mới");
            }
        } else {
            if (valiform()) {
                themMoiPM();
                clearFormInput();
            }
        }
        loadPhieuMuon();

    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnTaoMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoMoiActionPerformed
        if (btnTaoMoi.getText().equals("Hủy")) {
            btnTaoMoi.setText("Tạo Mới");
            btnSua.setEnabled(false);
            btnXoa.setEnabled(false);
            btnLuu.setEnabled(false);
            clearFormInput();
            tblBang.setSelectionMode(0);
            txtSoLuong.setText("1");
        } else {
            txtSoLuong.setText("1");
            setEditForm(true);
            clearFormInput();
            btnSua.setEnabled(false);
            btnXoa.setEnabled(false);
            btnLuu.setEnabled(true);
            txtNgayMuon.setEnabled(true);
            txtNgayTra.setEnabled(true);
            txtMaSinhVien.setFocusable(true);
        }
    }//GEN-LAST:event_btnTaoMoiActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        btnTaoMoi.setText("Hủy");
        setEditForm(true);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        btnLuu.setEnabled(true);
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int row = tblBang.getSelectedRow();
        String value = tblBang.getModel().getValueAt(row, 0).toString();
        PhieuMuonController controller = new PhieuMuonController();
        int result = JOptionPane.showConfirmDialog(null, "Ban co chac muon xoa?", "Dong Y", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            if (controller.xoaPhieuMuon(value) == 0) {
                JOptionPane.showMessageDialog(null, "Xoa That Bai", "Xoa Du Lieu", JOptionPane.OK_OPTION);
                return;
            } else {
                JOptionPane.showMessageDialog(null, "Xoa Thanh Cong", "Xoa Du Lieu", 1);
                clearFormInput();
                loadPhieuMuon();
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tblBangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangMouseClicked
        btnSua.setEnabled(true);
        btnXoa.setEnabled(true);
        btnLuu.setEnabled(false);
        setEditForm(false);
        PhieuMuonController pmc = new PhieuMuonController();
        ArrayList<PhieuMuon> danhSachPM = pmc.listPhieuMuon();
        int selectedRow = tblBang.getSelectedRow();
        PhieuMuon pm = danhSachPM.get(selectedRow);
        txtMaPhieuMuon.setText(pm.getMaPhieuMuon());
        txtMaSinhVien.setText(pm.getMaSinhVien());
        txtMaSach.setText(pm.getMaSach());
        txtSoLuong.setText(pm.getSoLuong());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String txtMuon = pm.getNgayMuon();
        String txtHetHan = pm.getNgayHetHan();
        try {
            Date dateMuon = formatter.parse(txtMuon);
            Date dateHetHan = formatter.parse(txtHetHan);
            txtNgayMuon.setDate(dateMuon);
            txtNgayTra.setDate(dateHetHan);
        } catch (ParseException ex) {
            Logger.getLogger(Admin_PhieuMuon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tblBangMouseClicked

    public void searchPM() {
        PhieuMuonController svc = new PhieuMuonController();
        ArrayList<PhieuMuon> list = svc.searchPhieuMuon(txtSearch.getText());
        tblBang.setModel(new DefaultTableModel(null, new String[]{"Mã Phiếu Mượn", "Mã Sinh Viên", "Mã Sách", "Số Lượng", "Ngày Mượn", "Ngày Hẹn Trả", "Đã Trả"}));
        DefaultTableModel model = (DefaultTableModel) tblBang.getModel();

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
    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        searchPM();
    }//GEN-LAST:event_jLabel6MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTaoMoi;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMaTheLoai1;
    private javax.swing.JLabel lblTenTheLoai1;
    private javax.swing.JLabel lblViTri1;
    private javax.swing.JRadioButton rdoChuaTra;
    private javax.swing.JRadioButton rdoDaTra;
    private javax.swing.JTable tblBang;
    private javax.swing.JTextField txtMaPhieuMuon;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtMaSinhVien;
    private com.toedter.calendar.JDateChooser txtNgayMuon;
    private com.toedter.calendar.JDateChooser txtNgayTra;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoLuong;
    // End of variables declaration//GEN-END:variables

}