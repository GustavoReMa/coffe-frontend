package com.coffe.front;

import com.coffee.back.ConfigureUserDI;
import com.coffee.back.commons.enums.UserType;
import com.coffee.back.controller.impl.UserCtrlImpl;
import com.coffee.back.controller.vo.UserVO;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javax.swing.JOptionPane;

/*Clase que cuenta con la funcionalidad de aceptar datos y mandarlos a los controladores
para verificar los datos en la base de datos y si existen abrirles la interfaz correspondiente
 */
public class Inicio extends javax.swing.JFrame {

    public Inicio() {
        initComponents();
        //  setIconImage(new ImageIcon(getClass().getResource("/vermon/Logo.png")).getImage());
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nickUsuIni = new javax.swing.JTextField();
        passUsuIni = new javax.swing.JPasswordField();
        ingresarUsuIni = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 153, 153));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("INICIAR SESIÓN");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffe/front/assets/usuario.PNG"))); // NOI18N
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        nickUsuIni.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "NICK", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 0, 12), new java.awt.Color(0, 153, 153))); // NOI18N
        nickUsuIni.setDisabledTextColor(new java.awt.Color(255, 255, 51));

        passUsuIni.setToolTipText("");
        passUsuIni.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CONTRASEÑA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 0, 12), new java.awt.Color(0, 153, 153))); // NOI18N
        passUsuIni.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        passUsuIni.setDropMode(javax.swing.DropMode.INSERT);
        passUsuIni.setName(""); // NOI18N

        ingresarUsuIni.setBackground(new java.awt.Color(0, 102, 102));
        ingresarUsuIni.setForeground(new java.awt.Color(255, 255, 255));
        ingresarUsuIni.setText("INGRESAR");
        ingresarUsuIni.setBorder(null);
        ingresarUsuIni.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ingresarUsuIniMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passUsuIni, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nickUsuIni, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ingresarUsuIni, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jLabel1)))
                .addContainerGap(54, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(160, 160, 160))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nickUsuIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passUsuIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ingresarUsuIni, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        nickUsuIni.getAccessibleContext().setAccessibleName("NICK");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ingresarUsuIniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ingresarUsuIniMouseClicked
        String userName = null;
        String passwordUser = null;
        try {
            userName = nickUsuIni.getText(); // se recupera el nombre de usuario ingresado
            passwordUser = String.copyValueOf(passUsuIni.getPassword());    // se recupera la contraseña del usuario
            UserVO userVO = new UserVO();   // se instancia el modelo de un usuario
            // se configuran los campos del modelo
            userVO.setUserName(userName);
            userVO.setPassword(passwordUser);
            userVO.setUserType(UserType.UKNOWN);

            Injector injector = Guice.createInjector(new ConfigureUserDI());
            UserCtrlImpl userCtrl = injector.getInstance(UserCtrlImpl.class);
            UserVO userRecover = userCtrl.iniciarSesion(userVO); //  responsibilizamos al controlador de ejecutar la tarea iniciarSesión
            switch (userRecover.getUserType()) {
                case ADMINISTRADOR:
                    this.dispose();
                    Administrador admin = new Administrador();
                    break;
                case CAJERO:
                    this.dispose();
                    Cajero cajero = new Cajero(userRecover);
                    break;
                case UKNOWN:
                    errorLogin("Datos incorrectos o usuario no existe");
                    break;
            }

        } catch (NullPointerException e) {
            if (userName == null || passwordUser == null) {
                errorLogin("Verifique campos vacios ");
            }
        }
    }//GEN-LAST:event_ingresarUsuIniMouseClicked

    private void errorLogin(String message) {
        JOptionPane.showMessageDialog(this, message, "Alerta", JOptionPane.WARNING_MESSAGE);
    }

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
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Inicio().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ingresarUsuIni;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField nickUsuIni;
    private javax.swing.JPasswordField passUsuIni;
    // End of variables declaration//GEN-END:variables
}
