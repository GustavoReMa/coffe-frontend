package com.coffe.front;

import com.coffee.back.ConfigureProductDI;
import com.coffee.back.ConfigureSaleDI;
import com.coffee.back.ConfigureUserDI;
import com.coffee.back.commons.enums.UserType;
import com.coffee.back.controller.ProductCtrl;
import com.coffee.back.controller.SaleCtrl;
import com.coffee.back.controller.UserCtrl;
import com.coffee.back.controller.impl.ProductCtrlImpl;
import com.coffee.back.controller.impl.SaleCtrlImpl;
import com.coffee.back.controller.impl.UserCtrlImpl;
import com.coffee.back.controller.vo.ProductVO;
import com.coffee.back.controller.vo.SaleVO;
import com.coffee.back.controller.vo.SaleVO.SaleProduct;
import com.coffee.back.controller.vo.UserVO;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Bienvenido
 */
public class Cajero extends javax.swing.JFrame {

    private final UserVO user;
    private final UserCtrl userCtrl;
    private final SaleCtrl saleCtrl;
    private final ProductCtrl productCtrl;
    private boolean areThereProductsOnSale = false; // Guarda el estado de la venta.
    private boolean isRowSelectedProductFound = false;
    private boolean changedRowSelected = false;
    private int countStockRows = 0;
    private double totalSale = 0.0;
    private int saleID;
    private int lastIndexChanged = -1;

    public Cajero(UserVO user) {
        initComponents();
        super.setVisible(true);
        this.user = user;
        Injector injector = Guice.createInjector(new ConfigureUserDI());
        Injector injectorSale = Guice.createInjector(new ConfigureSaleDI());
        Injector injectorProduct = Guice.createInjector(new ConfigureProductDI());
        this.userCtrl = injector.getInstance(UserCtrlImpl.class);
        this.saleCtrl = injectorSale.getInstance(SaleCtrlImpl.class);
        this.productCtrl = injectorProduct.getInstance(ProductCtrlImpl.class);
        initVariables();
    }

    private void initVariables() {
        this.jLabel7.setText(user.getUserName());
        DefaultTableModel stockDefault = ((DefaultTableModel) stockTable.getModel());
        stockDefault.addTableModelListener((rowAffected) -> {
            int lastRow = rowAffected.getLastRow();
            if (rowAffected.getType() == TableModelEvent.INSERT && stockDefault.getValueAt(lastRow, 6) != null) {
                int quantity = (int) stockDefault.getValueAt(lastRow, 5);
                double price = (double) stockDefault.getValueAt(lastRow, 6);
                totalSale += quantity * price;
                stockDefault.setValueAt(quantity * price, rowAffected.getLastRow(), 7);
                totalLabel.setText("$" + totalSale);
            }
        });
        saleID = saleCtrl.getSaleId();
        saleID++;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        stockTable = new javax.swing.JTable(){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        jLabel9 = new javax.swing.JLabel();
        productToFindTextField = new javax.swing.JTextField();
        productToFindButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        productsTable = new javax.swing.JTable(){
            public boolean isCellEditable(int row, int column){
                return column ==2;
            }
        };
        addToStockButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        purchaseButton = new javax.swing.JButton();
        totalLabel = new javax.swing.JLabel();
        devolutionLabel = new javax.swing.JLabel();
        cashTextField = new javax.swing.JTextField();
        cerrarSesionCajero = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        stockTable.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        stockTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo de barras", "#", "ID", "PRODUCTO", "CATEGORIA", "CANTIDAD", "PRECIO", "NETO"
            }
        ));
        stockTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        stockTable.setAutoscrolls(false);
        stockTable.setSelectionBackground(new java.awt.Color(0, 153, 153));
        jScrollPane1.setViewportView(stockTable);

        jLabel9.setBackground(new java.awt.Color(0, 102, 102));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText("NOMBRE");

        productToFindButton.setBackground(new java.awt.Color(6, 133, 135));
        productToFindButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        productToFindButton.setText("BUSCAR PRODUCTO");
        productToFindButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productToFindButtonActionPerformed(evt);
            }
        });

        productsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NOMBRE", "CANTIDAD", "CATEGORIA", "PRECIO", "EXISTENCIA"
            }
        ));
        productsTable.setMaximumSize(new java.awt.Dimension(2147483647, 70));
        jScrollPane2.setViewportView(productsTable);

        addToStockButton.setBackground(new java.awt.Color(6, 133, 135));
        addToStockButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        addToStockButton.setText("AGREGAR A STOCK");
        addToStockButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToStockButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("CAJA");

        jLabel2.setText("CAJERO");

        jButton7.setBackground(new java.awt.Color(6, 133, 135));
        jButton7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton7.setText("CORTE DE CAJA");

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("TOTAL");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("EFECTIVO");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("CAMBIO");

        purchaseButton.setBackground(new java.awt.Color(62, 87, 111));
        purchaseButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        purchaseButton.setText("REALIZAR VENTA");
        purchaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purchaseButtonActionPerformed(evt);
            }
        });

        totalLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        totalLabel.setText("$0.0");

        devolutionLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        devolutionLabel.setText("$0.0");

        cashTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cashTextFieldKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalLabel)
                    .addComponent(devolutionLabel)
                    .addComponent(cashTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(purchaseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(totalLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cashTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(devolutionLabel))
                .addGap(56, 56, 56)
                .addComponent(purchaseButton))
        );

        cerrarSesionCajero.setText("CERRAR SESIÓN");
        cerrarSesionCajero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarSesionCajeroActionPerformed(evt);
            }
        });

        jLabel6.setText("#01");

        jLabel7.setText("JUAN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cerrarSesionCajero, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(productToFindTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(213, 213, 213))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(139, 139, 139)
                                .addComponent(addToStockButton)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)))))
                .addGap(26, 26, 26))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(productToFindButton)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addGap(106, 106, 106))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cerrarSesionCajero, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productToFindTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productToFindButton)
                    .addComponent(jLabel9))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addToStockButton)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cerrarSesionCajeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarSesionCajeroActionPerformed
        // Para cerrar sesion se debe de verificar que el usuario actual
        // sea de tipo administrador de lo contrario no puede cerrar sesion.
        // al cerrar sesion el administrador deberá introducir su contraseña para poder
        // validar y llevar acabo la funcionalidad
        // revisar si no hay una venta actual.
        if (areThereProductsOnSale) {
            JOptionPane.showMessageDialog(this, "Usted necesita finalizar la venta", "Venta", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFrame contenedorSes = new JFrame();
        JLabel cerrarSes = new JLabel("¿Estás seguro de salir?");
        JLabel userNameLabel = new JLabel("Nick");
        JTextField userNameTextField = new JTextField();
        JLabel passwordLabel = new JLabel("Contraseña:");
        JPasswordField passwordField = new JPasswordField();
        JButton aceptoCerrarSesion = new JButton("ACEPTAR");
        JButton cancelarCerrarSesion = new JButton("CANCELAR");

        contenedorSes.setBounds(1000 / 4, 1000 / 4, 250, 150);
        contenedorSes.setVisible(true);
        contenedorSes.setResizable(true);
        contenedorSes.setLayout(null);

        aceptoCerrarSesion.addActionListener((ActionEvent e) -> {
            if (String.copyValueOf(passwordField.getPassword()).equals("") || userNameTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(contenedorSes, "Los campos no pueden ser vacios", "Cerrar Sesión", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String password = String.valueOf(passwordField.getPassword());

            user.setPassword(password);
            user.setUserName(userNameTextField.getText());
            user.setUserType(UserType.ADMINISTRADOR); // por default un administrador es el unico que puede cerrar sesion por
            // reglas de negocio

            boolean isAdministrator = userCtrl.cerrarSesion(user);
            if (isAdministrator) {
                contenedorSes.dispose();
                Inicio inicioNew = new Inicio();
                inicioNew.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(contenedorSes, "Usted no tiene los privilegios para cerrar sesión", "Cerrar Sesión", JOptionPane.WARNING_MESSAGE);
            }

        });
        cancelarCerrarSesion.addActionListener((ActionEvent e) -> {
            contenedorSes.dispose();
        });

        passwordField.setSize(20, 30);

        cerrarSes.setBounds(40, 5, 250, 25);
        userNameLabel.setBounds(20, 40, 150, 25);
        userNameTextField.setBounds(130, 40, 110, 25);
        passwordLabel.setBounds(20, 70, 150, 25);
        passwordField.setBounds(130, 70, 110, 25);
        aceptoCerrarSesion.setBounds(20, 100, 100, 25);
        cancelarCerrarSesion.setBounds(130, 100, 110, 25);

        contenedorSes.add(cerrarSes);
        contenedorSes.add(userNameLabel);
        contenedorSes.add(userNameTextField);
        contenedorSes.add(passwordLabel);
        contenedorSes.add(passwordField);
        contenedorSes.add(aceptoCerrarSesion);
        contenedorSes.add(cancelarCerrarSesion);
    }//GEN-LAST:event_cerrarSesionCajeroActionPerformed

    private int getSaleId() {
        return saleID;
    }

    private void setSaleId() {
        saleID++;
    }
    private void purchaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purchaseButtonActionPerformed
        if (areThereProductsOnSale && !cashTextField.getText().equals("")) {
            SaleVO sale = new SaleVO();
            sale.setCashierNickName(user.getUserName());
            sale.setSaleId(getSaleId());
            sale.setTotalSale(totalSale);
            sale.setDateSale("2018-04-22");
            List<SaleVO.SaleProduct> saleProducts = new ArrayList<>();
            DefaultTableModel defaultStock = (DefaultTableModel) stockTable.getModel();
            while (countStockRows > 0 && defaultStock.getValueAt(0, 2) != null) {
                SaleVO.SaleProduct saleProduct = sale.new SaleProduct();

                int id = (Integer) defaultStock.getValueAt(0, 2);
                String productName = (String) defaultStock.getValueAt(0, 3);
                int quantity = (Integer) defaultStock.getValueAt(0, 5);
                double neto = (Double) defaultStock.getValueAt(0, 7);

                saleProduct.setProductId(id);
                saleProduct.setProductName(productName);
                saleProduct.setQuantityProduct(quantity);
                saleProduct.setNeto(neto);
                saleProducts.add(saleProduct);
                defaultStock.removeRow(0);
                countStockRows--;
                System.out.println("LLenado completo....");
            }
            System.out.println("Saliedo....");
            System.out.println("Fuera....");
            sale.setSaleProduct(saleProducts);
            saleCtrl.realizarVenta(sale);
            totalSale = 0;
            areThereProductsOnSale = false;
            resetFinalSale();
            setSaleId();
            defaultStock.setRowCount(19);
        }
    }//GEN-LAST:event_purchaseButtonActionPerformed

    private void resetFinalSale() {
        cashTextField.setText("");
        totalLabel.setText("$0.0");
        devolutionLabel.setText("$0.0");
    }

    private void productToFindButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productToFindButtonActionPerformed
        if (!productToFindTextField.getText().equals("") && productToFindTextField.getText().matches("[a-zA-Z]+")) {
            List<ProductVO> productsMatches = this.productCtrl.buscarProducto(productToFindTextField.getText().toUpperCase());
            if (!productsMatches.isEmpty()) {
                DefaultTableModel tableModel = (DefaultTableModel) productsTable.getModel();
                tableModel.addTableModelListener((e) -> {
                    // verificar que el producto haya cambio a un valor mayor a 0
                    //productsTable.getValueAt(e.getLastRow(),e.getColumn());
                    if (e.getType() == TableModelEvent.UPDATE) {
                        // fue actualizado revisamos que la cantidad establecida sea
                        // mayor a 0 para un producto
                        String quantity = (String) productsTable.getValueAt(e.getLastRow(), 2);
                        short quantityProduct = (short) productsTable.getValueAt(productsTable.getSelectedRow(), 5);
                        short quantityShort = Short.parseShort(quantity);
                        if (quantityShort > 0 && quantityShort <= quantityProduct) {
                            changedRowSelected = true;
                            lastIndexChanged = e.getLastRow();
                        }
                        //no es permitido un producto con menor cantidad
                    }
                });
                productsTable.getSelectionModel().addListSelectionListener((e) -> {
                    if (productsTable.getSelectedRow() > -1) {
                        isRowSelectedProductFound = true;
                        if(productsTable.getSelectedRow() == lastIndexChanged)
                            changedRowSelected = true;
                        else 
                            changedRowSelected = false;
                    }
                });
                DefaultTableModel defaults = ((DefaultTableModel) productsTable.getModel());
                while (defaults.getRowCount() > 0) {
                    defaults.removeRow(0);
                }
                for (int row = 0; row < productsMatches.size(); row++) {
                    Vector vector = new Vector();
                    vector.add(productsMatches.get(row).getId());
                    vector.add(productsMatches.get(row).getProductName());
                    vector.add("0");
                    vector.add(productsMatches.get(row).getCategoryName());
                    vector.add(productsMatches.get(row).getPriceTag());
                    vector.add(productsMatches.get(row).getQuantity());
                    defaults.addRow(vector);
                }
            } // else no se ha encontrado algun producto con las caracteristicas
        }
        //else entrada incorrecta
    }//GEN-LAST:event_productToFindButtonActionPerformed

    private void resetTextBox() {
        productToFindTextField.setText("");
    }

    private void addToStockButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addToStockButtonActionPerformed
        if (changedRowSelected && isRowSelectedProductFound) {
            DefaultTableModel stockModel = ((DefaultTableModel) stockTable.getModel());
            stockModel.isCellEditable(2, 3);
            while (stockModel.getRowCount() > countStockRows) {
                stockModel.removeRow(countStockRows);
            }
            DefaultTableModel productsFoundModel = ((DefaultTableModel) productsTable.getModel());
            try {
                String quantitySaleProduct = (String) productsFoundModel.getValueAt(productsTable.getSelectedRow(), 2);
                int value = Integer.parseInt(quantitySaleProduct);
                Vector vector = new Vector();
                vector.add("");
                vector.add("");
                vector.add(productsFoundModel.getValueAt(productsTable.getSelectedRow(), 0));
                vector.add(productsFoundModel.getValueAt(productsTable.getSelectedRow(), 1));
                vector.add(productsFoundModel.getValueAt(productsTable.getSelectedRow(), 3));
                vector.add(value);
                vector.add(productsFoundModel.getValueAt(productsTable.getSelectedRow(), 4));
                vector.add(0.0d);

                stockModel.addRow(vector);
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
            countStockRows++;
            areThereProductsOnSale = true;
            while (productsFoundModel.getRowCount() > 0) {
                productsFoundModel.removeRow(0);
            }
            productsFoundModel.setRowCount(4);
            isRowSelectedProductFound = false;
            changedRowSelected = false;
            resetTextBox();
        } else {
            // Actually no row is selected
        }
    }//GEN-LAST:event_addToStockButtonActionPerformed

    private void cashTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cashTextFieldKeyReleased
        try {
            String moneyPaid = ((JTextField) evt.getComponent()).getText();
            double money = Double.parseDouble(moneyPaid);
            double total = Double.parseDouble(totalLabel.getText().substring(1, totalLabel.getText().length() - 1));
            String totalString = String.valueOf(money - total);
            devolutionLabel.setText(totalString);
        } catch (Exception ex) {
            // It's not a number
        }
    }//GEN-LAST:event_cashTextFieldKeyReleased

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // Invocacion de prueba de ajuste
        java.awt.EventQueue.invokeLater(() -> {
            UserVO userVO = new UserVO();
            userVO.setPassword("pedro12");
            userVO.setUserName("PED@EMP");
            userVO.setUserType(UserType.CAJERO);
            Cajero cajero = new Cajero(userVO);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addToStockButton;
    private javax.swing.JTextField cashTextField;
    private javax.swing.JButton cerrarSesionCajero;
    private javax.swing.JLabel devolutionLabel;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton productToFindButton;
    private javax.swing.JTextField productToFindTextField;
    private javax.swing.JTable productsTable;
    private javax.swing.JButton purchaseButton;
    private javax.swing.JTable stockTable;
    private javax.swing.JLabel totalLabel;
    // End of variables declaration//GEN-END:variables
}
