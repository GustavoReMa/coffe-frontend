package com.coffe.front;

import Reporte.GenerarReportes;
import com.coffee.back.ConfigureProductDI;
import com.coffee.back.ConfigureUserDI;
import com.coffee.back.commons.enums.UserType;
import com.coffee.back.controller.impl.ProductCtrlImpl;
import com.coffee.back.controller.impl.UserCtrlImpl;
import com.coffee.back.controller.vo.ProductVO;
import com.coffee.back.controller.vo.UserVO;
import com.coffee.back.controller.vo.WorkerVO;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import static java.lang.String.valueOf;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/*La clase administrador contiene todas las funcionalidades del administrador como lo son;
alta producto, modificar producto, consulta producto, baja producto, alta usuario, baja 
usuario, modificar usuario, visualizar estadísticas, realizar reporte y cerrar sesión.
 */
public class Administrador extends javax.swing.JFrame {
//Declaración de algunas variables de las clases

    private JFileChooser fileChooser; //Objeto encargada de escoger la ruta de una imagen
    private String direcImagen = ""; //Variable que contendrá la ruta de la imagen
    private ProductVO producto; //Objeto que contendrá todos los atributos de un producto
    private List<ProductVO> productoList; //Objeto que contendrá a una lista de productos
    private WorkerVO trabajador; //Objeto que contendrá los atributos de un trabajador (usuario)
    private List<WorkerVO> workerList; //Lista de trabajadores
    private UserVO usuario; //Objeto que contendrá el nick del usuario y su contraseña
    private String categoria; //Variable que contendrá la categoría del producto en alta producto
    Toolkit t = Toolkit.getDefaultToolkit(); //Objeto que manejará propiedades de la pantalla
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Objeto que almacenará el tamaño de la pantalla
    JTable tabla = new JTable(); //Objeto que creará una tabla emergente
    GenerarReportes gr = new GenerarReportes(); //Objeto de la clase GenerarReportes 
    String dirImagenesGlobal[], nombreProdGlobal[];
    int contadorProdEliminar = 0, contadorProdBa = 0;
    int indiceProductoEliminar = 0, indiceProductoEliminar2 = 0, indiceProductoEliminar3 = 0,
            indiceProductoEliminar4 = 0, indiceProductoEliminar5 = 0, indiceProductoEliminar6 = 0;

    /*
    Éste método es un constructor que inicializa todas las variables utilizadas en la interfaz al crear una 
    instacia de ésta clase 
     */
    public Administrador() {
        initComponents(); //Método que inicizaliza todas las variables 
        realizarReporteVentas.setEnabled(false);
        bloquearCamposProd();
        bloquearCamposUsuario();
        bloqBtnEliminarUsu();
        inicializarProductoEliminar();
    }

    private void inicializarProductoEliminar() {
        productoList = new ArrayList<>(); //se crea un objeto de tipo ArrayList
        String nombreProdModBuscar = ""; //Se obtiene el nombre del producto a buscar
        /*Se declara un injector y se le declara el tipo de clase a la que queremos que haga referencia, en este caso
        a una configuración de producto */
        Injector injector = Guice.createInjector(new ConfigureProductDI());
        //Se declara el controlador
        ProductCtrlImpl productCtrl = injector.getInstance(ProductCtrlImpl.class);
        //Se guarda en el objeto productoList una lista de todos los productos que coinciden con el nombre
        productoList = productCtrl.buscarProducto(nombreProdModBuscar);
        Iterator<ProductVO> productoIterador = productoList.iterator();
        Iterator<ProductVO> productoIterador2 = productoList.iterator();
        //Ciclo para obtener el tamaño del vector
        int contador = 0;
        while (productoIterador.hasNext()) {
            ProductVO productoCiclo = productoIterador.next();
            contador++;
        }
        String dirImagen[] = new String[contador];
        String nombreProd[] = new String[contador];
        System.out.println("Tamaño de vector: " + contador);
        int contador2 = 0;
        /*Ciclo encargado de obtener los datos de los productos que coinciden con el nombre del que desea buscarse
        y los agrega al modelo de la tabla
         */
        while (productoIterador2.hasNext()) {
            ProductVO productoCiclo = productoIterador2.next();
            nombreProd[contador2] = productoCiclo.getProductName();
            dirImagen[contador2] = productoCiclo.getImage();
            System.out.println(dirImagen[contador2]);
            contador2++;
        }
        contadorProdEliminar = 1;
        nombreProdGlobal = nombreProd.clone();
        dirImagenesGlobal = dirImagen.clone();
        pintarImagenProducto(dirImagenesGlobal);
    }

    public void pintarImagenProducto(String[] direcciones) {
        int capacidad = direcciones.length / 6;
        int modulo = direcciones.length % 6;
        int indiceNumero = (contadorProdEliminar * 6) - 6;
        System.out.println(contadorProdEliminar);
        if (contadorProdEliminar <= 1) {
            atrasProdBA.setEnabled(false);
        } else {
            atrasProdBA.setEnabled(true);
        }
        if (contadorProdEliminar == capacidad) {
            siguienteProdBA.setEnabled(false);
        } else {
            siguienteProdBA.setEnabled(true);
        }
        if (direcciones.length > (contadorProdEliminar * 6)) {
            siguienteProdBA.setEnabled(true);
        } else {
            siguienteProdBA.setEnabled(false);
        }

        if (contadorProdEliminar > 0 && contadorProdEliminar <= capacidad) {
            pintarImagen(direcciones[indiceNumero], contEliminarProd1);
            setIndiceProductoEliminar(indiceNumero);
            pintarImagen(direcciones[++indiceNumero], contEliminarProd2);
            setIndiceProductoEliminar2(indiceNumero);
            pintarImagen(direcciones[++indiceNumero], contEliminarProd3);
            setIndiceProductoEliminar3(indiceNumero);
            pintarImagen(direcciones[++indiceNumero], contEliminarProd4);
            setIndiceProductoEliminar4(indiceNumero);
            pintarImagen(direcciones[++indiceNumero], contEliminarProd5);
            setIndiceProductoEliminar5(indiceNumero);
            pintarImagen(direcciones[++indiceNumero], contEliminarProd6);
            setIndiceProductoEliminar6(indiceNumero);
            opcionEliminarProd1.setEnabled(true);
            opcionEliminarProd2.setEnabled(true);
            opcionEliminarProd3.setEnabled(true);
            opcionEliminarProd4.setEnabled(true);
            opcionEliminarProd5.setEnabled(true);
            opcionEliminarProd6.setEnabled(true);
        } else {
            if (contadorProdEliminar > 0) {
                int indiceModulo = direcciones.length;
                if (modulo == 1) {
                    indiceModulo = indiceModulo - 1;
                    pintarImagen(direcciones[indiceModulo], contEliminarProd1);
                    setIndiceProductoEliminar(indiceModulo);
                    contEliminarProd2.setIcon(null);
                    contEliminarProd3.setIcon(null);
                    contEliminarProd4.setIcon(null);
                    contEliminarProd5.setIcon(null);
                    contEliminarProd6.setIcon(null);
                    opcionEliminarProd2.setEnabled(false);
                    opcionEliminarProd3.setEnabled(false);
                    opcionEliminarProd4.setEnabled(false);
                    opcionEliminarProd5.setEnabled(false);
                    opcionEliminarProd6.setEnabled(false);
                } else {
                    if (modulo == 2) {
                        indiceModulo = indiceModulo - 2;
                        pintarImagen(direcciones[indiceModulo], contEliminarProd1);
                        setIndiceProductoEliminar(indiceModulo);
                        pintarImagen(direcciones[++indiceModulo], contEliminarProd2);
                        setIndiceProductoEliminar2(indiceModulo);
                        contEliminarProd3.setIcon(null);
                        contEliminarProd4.setIcon(null);
                        contEliminarProd5.setIcon(null);
                        contEliminarProd6.setIcon(null);
                        opcionEliminarProd3.setEnabled(false);
                        opcionEliminarProd4.setEnabled(false);
                        opcionEliminarProd5.setEnabled(false);
                        opcionEliminarProd6.setEnabled(false);
                    } else {
                        if (modulo == 3) {
                            indiceModulo = indiceModulo - 3;
                            pintarImagen(direcciones[indiceModulo], contEliminarProd1);
                            setIndiceProductoEliminar(indiceModulo);
                            pintarImagen(direcciones[++indiceModulo], contEliminarProd2);
                            setIndiceProductoEliminar2(indiceModulo);
                            pintarImagen(direcciones[++indiceModulo], contEliminarProd3);
                            setIndiceProductoEliminar3(indiceModulo);
                            contEliminarProd4.setIcon(null);
                            contEliminarProd5.setIcon(null);
                            contEliminarProd6.setIcon(null);
                            opcionEliminarProd4.setEnabled(false);
                            opcionEliminarProd5.setEnabled(false);
                            opcionEliminarProd6.setEnabled(false);
                        } else {

                        }
                        if (modulo == 4) {
                            indiceModulo = indiceModulo - 4;
                            pintarImagen(direcciones[indiceModulo], contEliminarProd1);
                            setIndiceProductoEliminar(indiceModulo);
                            pintarImagen(direcciones[indiceModulo++], contEliminarProd2);
                            setIndiceProductoEliminar2(indiceModulo);
                            pintarImagen(direcciones[indiceModulo++], contEliminarProd3);
                            setIndiceProductoEliminar3(indiceModulo);
                            pintarImagen(direcciones[indiceModulo++], contEliminarProd4);
                            setIndiceProductoEliminar4(indiceModulo);
                            contEliminarProd5.setIcon(null);
                            contEliminarProd6.setIcon(null);
                            opcionEliminarProd5.setEnabled(false);
                            opcionEliminarProd6.setEnabled(false);
                        } else {

                        }
                        if (modulo == 5) {
                            indiceModulo = indiceModulo - 5;
                            pintarImagen(direcciones[indiceModulo], contEliminarProd1);
                            setIndiceProductoEliminar(indiceModulo);
                            pintarImagen(direcciones[indiceModulo++], contEliminarProd2);
                            setIndiceProductoEliminar2(indiceModulo);
                            pintarImagen(direcciones[indiceModulo++], contEliminarProd3);
                            setIndiceProductoEliminar3(indiceModulo);
                            pintarImagen(direcciones[indiceModulo++], contEliminarProd4);
                            setIndiceProductoEliminar4(indiceModulo);
                            pintarImagen(direcciones[indiceModulo++], contEliminarProd5);
                            setIndiceProductoEliminar5(indiceModulo);
                            contEliminarProd6.setIcon(null);
                            opcionEliminarProd6.setEnabled(false);
                        }
                    }
                }
            }
        }
    }

    public void setIndiceProductoEliminar(int indice) {
        indiceProductoEliminar = indice;
    }

    public int getIndiceProductoEliminar() {
        return indiceProductoEliminar;
    }

    public void setIndiceProductoEliminar2(int indice) {
        indiceProductoEliminar2 = indice;
    }

    public int getIndiceProductoEliminar2() {
        return indiceProductoEliminar2;
    }

    public void setIndiceProductoEliminar3(int indice) {
        indiceProductoEliminar3 = indice;
    }

    public int getIndiceProductoEliminar3() {
        return indiceProductoEliminar3;
    }

    public void setIndiceProductoEliminar4(int indice) {
        indiceProductoEliminar4 = indice;
    }

    public int getIndiceProductoEliminar4() {
        return indiceProductoEliminar4;
    }

    public void setIndiceProductoEliminar5(int indice) {
        indiceProductoEliminar5 = indice;
    }

    public int getIndiceProductoEliminar5() {
        return indiceProductoEliminar5;
    }

    public void setIndiceProductoEliminar6(int indice) {
        indiceProductoEliminar6 = indice;
    }

    public int getIndiceProductoEliminar6() {
        return indiceProductoEliminar6;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        ventasGeneral = new javax.swing.JPanel();
        panelPrincipal = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        realizarReporteVentas = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        email = new javax.swing.JButton();
        cerrarSesionVentas = new javax.swing.JButton();
        usuariosGeneral = new javax.swing.JTabbedPane();
        usuarioAltaGral = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        nombreUsuAL = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        apellidoUsuAL = new javax.swing.JTextField();
        telefonoUsuAL = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        direccionUsuAL = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        emailUsuAL = new javax.swing.JTextField();
        fotoUsuAL2 = new javax.swing.JLabel();
        fotoUsuAL = new javax.swing.JButton();
        guardarUsuAL = new javax.swing.JButton();
        cancelarUsuAL = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        nickUsuAL = new javax.swing.JTextField();
        passwordUsuAL = new javax.swing.JPasswordField();
        usuarioBajaGral = new javax.swing.JPanel();
        nombreUsuarioBaja = new javax.swing.JLabel();
        eliminarUsuarioBtn = new javax.swing.JButton();
        fotoUsuarioBaja = new javax.swing.JLabel();
        buscarUsuarioBaja = new javax.swing.JButton();
        apellidoUsuarioBaja = new javax.swing.JLabel();
        usuarioModificarGral = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        nombreUsuMOD = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        apellidoUsuMOD = new javax.swing.JTextField();
        telefonoUsuMOD = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        direccionUsuMOD = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        passwordUsuMOD = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        emailUsuMOD = new javax.swing.JTextField();
        fotoUsuMOD2 = new javax.swing.JLabel();
        guardarUsuMOD = new javax.swing.JButton();
        cancelarUsuMOD = new javax.swing.JButton();
        buscarUsuMOD = new javax.swing.JButton();
        fotoUsuMOD1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        nickUsuMOD = new javax.swing.JTextField();
        productoGeneral = new javax.swing.JTabbedPane();
        productoAltaGral = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        etiquetaNomProdAL = new javax.swing.JLabel();
        nombreProdAL = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        precioProdAL = new javax.swing.JTextField();
        imagenProdAL2 = new javax.swing.JLabel();
        guardarProdAL = new javax.swing.JButton();
        cancelarProdAL = new javax.swing.JButton();
        categoriaProdAL = new javax.swing.JComboBox<>();
        imagenProd1 = new javax.swing.JButton();
        cantidadProdAL = new javax.swing.JSpinner();
        productoModificarGral = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        nombreProdMOD = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        precioProdMOD = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        categoriaProdMOD = new javax.swing.JComboBox<>();
        imagenProdMOD2 = new javax.swing.JLabel();
        guardarProdMOD = new javax.swing.JButton();
        cancelarProdMOD = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        imagenProdMOD = new javax.swing.JButton();
        cantidadProdMOD = new javax.swing.JSpinner();
        buscarProdMOD = new javax.swing.JButton();
        productoConsultarGral = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        nombreProdCON = new javax.swing.JTextField();
        aceptarProdCON = new javax.swing.JButton();
        imagenProdCON = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCON = new javax.swing.JTable();
        productoBajaGral = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        nombreProdBA = new javax.swing.JTextField();
        buscarProdBA = new javax.swing.JButton();
        contEliminarProd1 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        contEliminarProd2 = new javax.swing.JLabel();
        contEliminarProd3 = new javax.swing.JLabel();
        contEliminarProd4 = new javax.swing.JLabel();
        contEliminarProd5 = new javax.swing.JLabel();
        contEliminarProd6 = new javax.swing.JLabel();
        opcionEliminarProd1 = new javax.swing.JButton();
        opcionEliminarProd2 = new javax.swing.JButton();
        opcionEliminarProd3 = new javax.swing.JButton();
        opcionEliminarProd4 = new javax.swing.JButton();
        opcionEliminarProd5 = new javax.swing.JButton();
        opcionEliminarProd6 = new javax.swing.JButton();
        siguienteProdBA = new javax.swing.JButton();
        atrasProdBA = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jTabbedPane1.setBackground(new java.awt.Color(0, 204, 204));
        jTabbedPane1.setForeground(new java.awt.Color(0, 51, 51));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setFont(new java.awt.Font("Franklin Gothic Heavy", 0, 18)); // NOI18N
        jTabbedPane1.setName(""); // NOI18N

        panelPrincipal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 153, 153))); // NOI18N
        panelPrincipal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("DE");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("A");

        realizarReporteVentas.setBackground(new java.awt.Color(6, 133, 135));
        realizarReporteVentas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        realizarReporteVentas.setText("REALIZAR REPORTE");
        realizarReporteVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                realizarReporteVentasActionPerformed(evt);
            }
        });

        jDateChooser1.setDateFormatString("yyyy/MM/dd");
        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        jDateChooser2.setDateFormatString("yyyy/MM/dd");
        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });

        email.setText("enviar email");
        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 435, Short.MAX_VALUE)
                        .addComponent(realizarReporteVentas))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(email)
                                .addGap(59, 59, 59)))
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(559, Short.MAX_VALUE))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(realizarReporteVentas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(119, 119, 119)
                        .addComponent(email)
                        .addGap(0, 130, Short.MAX_VALUE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        cerrarSesionVentas.setBackground(new java.awt.Color(6, 133, 135));
        cerrarSesionVentas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cerrarSesionVentas.setText("CERRAR SESIÓN");
        cerrarSesionVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarSesionVentasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ventasGeneralLayout = new javax.swing.GroupLayout(ventasGeneral);
        ventasGeneral.setLayout(ventasGeneralLayout);
        ventasGeneralLayout.setHorizontalGroup(
            ventasGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ventasGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ventasGeneralLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cerrarSesionVentas)
                .addGap(22, 22, 22))
        );
        ventasGeneralLayout.setVerticalGroup(
            ventasGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ventasGeneralLayout.createSequentialGroup()
                .addGap(0, 5, Short.MAX_VALUE)
                .addComponent(cerrarSesionVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelPrincipal.getAccessibleContext().setAccessibleName("");

        jTabbedPane1.addTab("VENTAS", ventasGeneral);

        usuariosGeneral.setForeground(new java.awt.Color(62, 87, 111));
        usuariosGeneral.setFont(new java.awt.Font("Franklin Gothic Heavy", 2, 14)); // NOI18N

        usuarioAltaGral.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ALTA USUARIO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 102, 102))); // NOI18N

        jLabel16.setBackground(new java.awt.Color(0, 102, 102));
        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 102, 102));
        jLabel16.setText("NOMBRE");

        nombreUsuAL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nombreUsuALKeyTyped(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 102, 102));
        jLabel19.setText("APELLIDO");

        apellidoUsuAL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                apellidoUsuALKeyTyped(evt);
            }
        });

        telefonoUsuAL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                telefonoUsuALKeyTyped(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 102, 102));
        jLabel20.setText("TELEFONO");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 102, 102));
        jLabel27.setText("DIRECCIÓN");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 102, 102));
        jLabel28.setText("CONTRASEÑA");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 102, 102));
        jLabel29.setText("EMAIL");

        fotoUsuAL2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 102, 102)));

        fotoUsuAL.setBackground(new java.awt.Color(6, 133, 135));
        fotoUsuAL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fotoUsuAL.setText("FOTO");
        fotoUsuAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fotoUsuALActionPerformed(evt);
            }
        });

        guardarUsuAL.setBackground(new java.awt.Color(6, 133, 135));
        guardarUsuAL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        guardarUsuAL.setText("GUARDAR");
        guardarUsuAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarUsuALActionPerformed(evt);
            }
        });

        cancelarUsuAL.setBackground(new java.awt.Color(6, 133, 135));
        cancelarUsuAL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cancelarUsuAL.setText("CANCELAR");
        cancelarUsuAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarUsuALActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 102, 102));
        jLabel30.setText("NICK");

        javax.swing.GroupLayout usuarioAltaGralLayout = new javax.swing.GroupLayout(usuarioAltaGral);
        usuarioAltaGral.setLayout(usuarioAltaGralLayout);
        usuarioAltaGralLayout.setHorizontalGroup(
            usuarioAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usuarioAltaGralLayout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(fotoUsuAL, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(fotoUsuAL2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(usuarioAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(guardarUsuAL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelarUsuAL, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
                .addGap(64, 64, 64))
            .addGroup(usuarioAltaGralLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(usuarioAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(usuarioAltaGralLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(18, 18, 18)
                        .addComponent(passwordUsuAL))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, usuarioAltaGralLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(direccionUsuAL, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, usuarioAltaGralLayout.createSequentialGroup()
                        .addGroup(usuarioAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel30))
                        .addGap(55, 55, 55)
                        .addGroup(usuarioAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nickUsuAL, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombreUsuAL, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(123, 123, 123)
                .addGroup(usuarioAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(usuarioAltaGralLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(22, 22, 22)
                        .addComponent(apellidoUsuAL, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(usuarioAltaGralLayout.createSequentialGroup()
                        .addGroup(usuarioAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addGroup(usuarioAltaGralLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel29)))
                        .addGap(18, 18, 18)
                        .addGroup(usuarioAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emailUsuAL, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(telefonoUsuAL, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        usuarioAltaGralLayout.setVerticalGroup(
            usuarioAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usuarioAltaGralLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(usuarioAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreUsuAL, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel19)
                    .addComponent(apellidoUsuAL, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(usuarioAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(usuarioAltaGralLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addGroup(usuarioAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(telefonoUsuAL, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE))
                    .addGroup(usuarioAltaGralLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(usuarioAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(nickUsuAL, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(usuarioAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(direccionUsuAL, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(59, 59, 59)))
                .addGroup(usuarioAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(emailUsuAL, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordUsuAL, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(usuarioAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usuarioAltaGralLayout.createSequentialGroup()
                        .addGroup(usuarioAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(usuarioAltaGralLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(guardarUsuAL, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57)
                                .addComponent(cancelarUsuAL, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(fotoUsuAL2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usuarioAltaGralLayout.createSequentialGroup()
                        .addComponent(fotoUsuAL)
                        .addGap(109, 109, 109))))
        );

        usuariosGeneral.addTab("ALTA", usuarioAltaGral);

        usuarioBajaGral.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BAJA USUARIO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 102, 102))); // NOI18N

        nombreUsuarioBaja.setBackground(new java.awt.Color(0, 102, 102));
        nombreUsuarioBaja.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        nombreUsuarioBaja.setForeground(new java.awt.Color(0, 102, 102));
        nombreUsuarioBaja.setText("NOMBRE");

        eliminarUsuarioBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/coffe/front/assets/cerrar.png"))); // NOI18N
        eliminarUsuarioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarUsuarioBtnActionPerformed(evt);
            }
        });

        fotoUsuarioBaja.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        buscarUsuarioBaja.setText("BUSCAR");
        buscarUsuarioBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarUsuarioBajaActionPerformed(evt);
            }
        });

        apellidoUsuarioBaja.setBackground(new java.awt.Color(0, 102, 102));
        apellidoUsuarioBaja.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        apellidoUsuarioBaja.setForeground(new java.awt.Color(0, 102, 102));
        apellidoUsuarioBaja.setText("APELLIDO");

        javax.swing.GroupLayout usuarioBajaGralLayout = new javax.swing.GroupLayout(usuarioBajaGral);
        usuarioBajaGral.setLayout(usuarioBajaGralLayout);
        usuarioBajaGralLayout.setHorizontalGroup(
            usuarioBajaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usuarioBajaGralLayout.createSequentialGroup()
                .addGroup(usuarioBajaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(usuarioBajaGralLayout.createSequentialGroup()
                        .addGap(269, 269, 269)
                        .addGroup(usuarioBajaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nombreUsuarioBaja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fotoUsuarioBaja, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                            .addComponent(apellidoUsuarioBaja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eliminarUsuarioBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(usuarioBajaGralLayout.createSequentialGroup()
                        .addGap(308, 308, 308)
                        .addComponent(buscarUsuarioBaja)))
                .addContainerGap(236, Short.MAX_VALUE))
        );
        usuarioBajaGralLayout.setVerticalGroup(
            usuarioBajaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usuarioBajaGralLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(buscarUsuarioBaja)
                .addGap(45, 45, 45)
                .addGroup(usuarioBajaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(eliminarUsuarioBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fotoUsuarioBaja, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombreUsuarioBaja)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(apellidoUsuarioBaja)
                .addContainerGap(275, Short.MAX_VALUE))
        );

        usuariosGeneral.addTab("BAJA", usuarioBajaGral);

        usuarioModificarGral.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MODIFICAR USUARIO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 102, 102))); // NOI18N

        jLabel9.setBackground(new java.awt.Color(0, 102, 102));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText("NOMBRE");

        nombreUsuMOD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nombreUsuMODKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 102));
        jLabel10.setText("APELLIDO");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 102, 102));
        jLabel11.setText("TELEFONO");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 102));
        jLabel12.setText("DIRECCIÓN");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 102, 102));
        jLabel13.setText("CONTRASEÑA");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 102, 102));
        jLabel14.setText("EMAIL");

        fotoUsuMOD2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 102, 102)));

        guardarUsuMOD.setBackground(new java.awt.Color(6, 133, 135));
        guardarUsuMOD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        guardarUsuMOD.setText("GUARDAR");
        guardarUsuMOD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarUsuMODActionPerformed(evt);
            }
        });

        cancelarUsuMOD.setBackground(new java.awt.Color(6, 133, 135));
        cancelarUsuMOD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cancelarUsuMOD.setText("CANCELAR");
        cancelarUsuMOD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarUsuMODActionPerformed(evt);
            }
        });

        buscarUsuMOD.setBackground(new java.awt.Color(6, 133, 135));
        buscarUsuMOD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buscarUsuMOD.setText("BUSCAR");
        buscarUsuMOD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarUsuMODActionPerformed(evt);
            }
        });

        fotoUsuMOD1.setBackground(new java.awt.Color(6, 133, 135));
        fotoUsuMOD1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fotoUsuMOD1.setText("FOTO");
        fotoUsuMOD1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fotoUsuMOD1ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 102, 102));
        jLabel15.setText("NICK");

        nickUsuMOD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nickUsuMODActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout usuarioModificarGralLayout = new javax.swing.GroupLayout(usuarioModificarGral);
        usuarioModificarGral.setLayout(usuarioModificarGralLayout);
        usuarioModificarGralLayout.setHorizontalGroup(
            usuarioModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usuarioModificarGralLayout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(fotoUsuMOD1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(fotoUsuMOD2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(usuarioModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(guardarUsuMOD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelarUsuMOD, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
                .addGap(64, 64, 64))
            .addGroup(usuarioModificarGralLayout.createSequentialGroup()
                .addGroup(usuarioModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(usuarioModificarGralLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(usuarioModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel15))
                        .addGap(55, 55, 55)
                        .addGroup(usuarioModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nickUsuMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombreUsuMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(usuarioModificarGralLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(usuarioModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(usuarioModificarGralLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(direccionUsuMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(usuarioModificarGralLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(passwordUsuMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscarUsuMOD)
                .addGap(28, 28, 28)
                .addGroup(usuarioModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(usuarioModificarGralLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(22, 22, 22)
                        .addComponent(apellidoUsuMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(usuarioModificarGralLayout.createSequentialGroup()
                        .addGroup(usuarioModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addGroup(usuarioModificarGralLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel14)))
                        .addGap(18, 18, 18)
                        .addGroup(usuarioModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emailUsuMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(telefonoUsuMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        usuarioModificarGralLayout.setVerticalGroup(
            usuarioModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usuarioModificarGralLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(usuarioModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreUsuMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(apellidoUsuMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarUsuMOD))
                .addGap(42, 42, 42)
                .addGroup(usuarioModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nickUsuMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(16, 16, 16)
                .addGroup(usuarioModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(telefonoUsuMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(usuarioModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(direccionUsuMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(usuarioModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(passwordUsuMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(emailUsuMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(usuarioModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usuarioModificarGralLayout.createSequentialGroup()
                        .addGroup(usuarioModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(usuarioModificarGralLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(guardarUsuMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57)
                                .addComponent(cancelarUsuMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(fotoUsuMOD2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, usuarioModificarGralLayout.createSequentialGroup()
                        .addComponent(fotoUsuMOD1)
                        .addGap(109, 109, 109))))
        );

        usuariosGeneral.addTab("MODIFICAR", usuarioModificarGral);

        jTabbedPane1.addTab("USUARIOS", usuariosGeneral);

        productoGeneral.setFont(new java.awt.Font("Franklin Gothic Heavy", 2, 14)); // NOI18N

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ALTA PRODUCTOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 102, 102))); // NOI18N
        jPanel8.setForeground(new java.awt.Color(0, 102, 102));

        etiquetaNomProdAL.setBackground(new java.awt.Color(0, 102, 102));
        etiquetaNomProdAL.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        etiquetaNomProdAL.setForeground(new java.awt.Color(0, 102, 102));
        etiquetaNomProdAL.setText("NOMBRE");

        jLabel23.setBackground(new java.awt.Color(0, 102, 102));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 102, 102));
        jLabel23.setText("CANTIDAD");

        jLabel26.setBackground(new java.awt.Color(0, 102, 102));
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 102, 102));
        jLabel26.setText("PRECIO");

        jLabel34.setBackground(new java.awt.Color(0, 102, 102));
        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 102, 102));
        jLabel34.setText("CATEGORIA");

        precioProdAL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                precioProdALKeyTyped(evt);
            }
        });

        imagenProdAL2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 102, 102)));

        guardarProdAL.setBackground(new java.awt.Color(6, 133, 135));
        guardarProdAL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        guardarProdAL.setText("GUARDAR");
        guardarProdAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarProdALActionPerformed(evt);
            }
        });

        cancelarProdAL.setBackground(new java.awt.Color(6, 133, 135));
        cancelarProdAL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cancelarProdAL.setText("CANCELAR");
        cancelarProdAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarProdALActionPerformed(evt);
            }
        });

        categoriaProdAL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        categoriaProdAL.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Escoje una categoría--", "COMIDA RAPIDA", "COMIDA", "BEBIDA", "SNACK", "BREAD" }));
        categoriaProdAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoriaProdALActionPerformed(evt);
            }
        });

        imagenProd1.setText("IMAGEN");
        imagenProd1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imagenProd1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(imagenProd1))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(imagenProdAL2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel34))
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(categoriaProdAL, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addComponent(precioProdAL, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addComponent(etiquetaNomProdAL)
                                .addGap(45, 45, 45)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nombreProdAL, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cantidadProdAL, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cancelarProdAL, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(guardarProdAL, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(99, 99, 99))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaNomProdAL)
                    .addComponent(nombreProdAL, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(83, 83, 83)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(cantidadProdAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(precioProdAL, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(86, 86, 86)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(categoriaProdAL, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(114, 114, 114))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(imagenProdAL2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                        .addComponent(guardarProdAL, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(cancelarProdAL, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(imagenProd1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout productoAltaGralLayout = new javax.swing.GroupLayout(productoAltaGral);
        productoAltaGral.setLayout(productoAltaGralLayout);
        productoAltaGralLayout.setHorizontalGroup(
            productoAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        productoAltaGralLayout.setVerticalGroup(
            productoAltaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        productoGeneral.addTab("ALTA", productoAltaGral);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MODIFICAR PRODUCTOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 102, 102))); // NOI18N

        jLabel37.setBackground(new java.awt.Color(0, 102, 102));
        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 102, 102));
        jLabel37.setText("NOMBRE");

        jLabel39.setBackground(new java.awt.Color(0, 102, 102));
        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 102, 102));
        jLabel39.setText("PRECIO");

        precioProdMOD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                precioProdMODKeyTyped(evt);
            }
        });

        jLabel40.setBackground(new java.awt.Color(0, 102, 102));
        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 102, 102));
        jLabel40.setText("CATEGORIA");

        categoriaProdMOD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        categoriaProdMOD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Escoje una categoría--", "COMIDA RAPIDA", "COMIDA", "BEBIDA", "SNACK", "BREAD" }));
        categoriaProdMOD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoriaProdMODActionPerformed(evt);
            }
        });

        imagenProdMOD2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 102, 102)));

        guardarProdMOD.setBackground(new java.awt.Color(6, 133, 135));
        guardarProdMOD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        guardarProdMOD.setText("GUARDAR");
        guardarProdMOD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarProdMODActionPerformed(evt);
            }
        });

        cancelarProdMOD.setBackground(new java.awt.Color(6, 133, 135));
        cancelarProdMOD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cancelarProdMOD.setText("CANCELAR");
        cancelarProdMOD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarProdMODActionPerformed(evt);
            }
        });

        jLabel41.setBackground(new java.awt.Color(0, 102, 102));
        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 102, 102));
        jLabel41.setText("CANTIDAD");

        imagenProdMOD.setText("IMAGEN");
        imagenProdMOD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imagenProdMODActionPerformed(evt);
            }
        });

        buscarProdMOD.setText("BUSCAR");
        buscarProdMOD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarProdMODActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelarProdMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(guardarProdMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(99, 99, 99))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addGap(45, 45, 45)
                                .addComponent(nombreProdMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel41)
                                    .addComponent(jLabel39))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(precioProdMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cantidadProdMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(imagenProdMOD)
                                .addGap(18, 18, 18)
                                .addComponent(imagenProdMOD2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(buscarProdMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addGap(26, 26, 26)
                        .addComponent(categoriaProdMOD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(nombreProdMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarProdMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(202, 202, 202)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(precioProdMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(categoriaProdMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(114, 114, 114))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(imagenProdMOD2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(imagenProdMOD)
                            .addComponent(jLabel41)
                            .addComponent(cantidadProdMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addComponent(guardarProdMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(cancelarProdMOD, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );

        javax.swing.GroupLayout productoModificarGralLayout = new javax.swing.GroupLayout(productoModificarGral);
        productoModificarGral.setLayout(productoModificarGralLayout);
        productoModificarGralLayout.setHorizontalGroup(
            productoModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        productoModificarGralLayout.setVerticalGroup(
            productoModificarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        productoGeneral.addTab("MODIFICAR", productoModificarGral);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " CONSULTAR PRODUCTOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 102, 102))); // NOI18N

        jLabel43.setBackground(new java.awt.Color(0, 102, 102));
        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 102, 102));
        jLabel43.setText("NOMBRE");

        aceptarProdCON.setBackground(new java.awt.Color(6, 133, 135));
        aceptarProdCON.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        aceptarProdCON.setText("ACEPTAR");
        aceptarProdCON.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarProdCONActionPerformed(evt);
            }
        });

        imagenProdCON.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 102, 102)));

        tablaCON.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "NOMBRE", "CANTIDAD", "PRECIO", "CATEGORIA"
            }
        ));
        tablaCON.setSelectionBackground(new java.awt.Color(0, 102, 102));
        tablaCON.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tablaCON);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(jLabel43)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nombreProdCON, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(69, 69, 69))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(aceptarProdCON)
                                .addGap(121, 121, 121)))
                        .addComponent(imagenProdCON, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(56, 56, 56))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel43)
                            .addComponent(nombreProdCON, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addComponent(aceptarProdCON))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(imagenProdCON, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(73, 73, 73)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(147, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout productoConsultarGralLayout = new javax.swing.GroupLayout(productoConsultarGral);
        productoConsultarGral.setLayout(productoConsultarGralLayout);
        productoConsultarGralLayout.setHorizontalGroup(
            productoConsultarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        productoConsultarGralLayout.setVerticalGroup(
            productoConsultarGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        productoGeneral.addTab("CONSULTAR", productoConsultarGral);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BAJA PRODUCTOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 102, 102))); // NOI18N

        jLabel45.setBackground(new java.awt.Color(0, 102, 102));
        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 102, 102));
        jLabel45.setText("NOMBRE");

        buscarProdBA.setBackground(new java.awt.Color(6, 133, 135));
        buscarProdBA.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buscarProdBA.setText("BUSCAR");
        buscarProdBA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarProdBAActionPerformed(evt);
            }
        });

        contEliminarProd1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 102, 102)));

        jLabel47.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 102, 102)));

        contEliminarProd2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 102, 102)));

        contEliminarProd3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 102, 102)));

        contEliminarProd4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 102, 102)));

        contEliminarProd5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 102, 102)));

        contEliminarProd6.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 102, 102)));

        opcionEliminarProd1.setText("X");
        opcionEliminarProd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionEliminarProd1ActionPerformed(evt);
            }
        });

        opcionEliminarProd2.setText("X");
        opcionEliminarProd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionEliminarProd2ActionPerformed(evt);
            }
        });

        opcionEliminarProd3.setText("X");
        opcionEliminarProd3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionEliminarProd3ActionPerformed(evt);
            }
        });

        opcionEliminarProd4.setText("X");
        opcionEliminarProd4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionEliminarProd4ActionPerformed(evt);
            }
        });

        opcionEliminarProd5.setText("X");
        opcionEliminarProd5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionEliminarProd5ActionPerformed(evt);
            }
        });

        opcionEliminarProd6.setText("X");
        opcionEliminarProd6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionEliminarProd6ActionPerformed(evt);
            }
        });

        siguienteProdBA.setText(">");
        siguienteProdBA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siguienteProdBAActionPerformed(evt);
            }
        });

        atrasProdBA.setText("<");
        atrasProdBA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atrasProdBAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(nombreProdBA, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                                        .addComponent(contEliminarProd1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(opcionEliminarProd1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                                        .addComponent(contEliminarProd4, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(opcionEliminarProd4)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(contEliminarProd5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(contEliminarProd2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(opcionEliminarProd2)
                                    .addComponent(opcionEliminarProd5))
                                .addGap(16, 16, 16))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(atrasProdBA)
                                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buscarProdBA, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(contEliminarProd3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opcionEliminarProd3))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(siguienteProdBA)
                                    .addComponent(contEliminarProd6, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opcionEliminarProd6)))))
                .addGap(44, 44, 44))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(87, 87, 87)
                                .addComponent(jLabel45))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addComponent(nombreProdBA, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(buscarProdBA)))
                        .addGap(98, 98, 98)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(contEliminarProd2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contEliminarProd3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contEliminarProd1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(opcionEliminarProd1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(opcionEliminarProd3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(opcionEliminarProd2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(68, 68, 68)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(opcionEliminarProd5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contEliminarProd6, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contEliminarProd5, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contEliminarProd4, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(opcionEliminarProd4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(opcionEliminarProd6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49))
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(siguienteProdBA, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(atrasProdBA, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(561, 561, 561)
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout productoBajaGralLayout = new javax.swing.GroupLayout(productoBajaGral);
        productoBajaGral.setLayout(productoBajaGralLayout);
        productoBajaGralLayout.setHorizontalGroup(
            productoBajaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        productoBajaGralLayout.setVerticalGroup(
            productoBajaGralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        productoGeneral.addTab("BAJA", productoBajaGral);

        jTabbedPane1.addTab("PRODUCTOS", productoGeneral);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 837, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("");

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
                .addGap(0, 2, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelarUsuMODActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarUsuMODActionPerformed
        bloquearCamposUsuario();
        nombreUsuMOD.setText("");
        apellidoUsuMOD.setText("");
        nickUsuMOD.setText("");
        direccionUsuMOD.setText("");
        telefonoUsuMOD.setText("");
        emailUsuMOD.setText("");
        passwordUsuMOD.setText("");
        fotoUsuMOD2.setIcon(null);
    }//GEN-LAST:event_cancelarUsuMODActionPerformed

    private void cancelarUsuALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarUsuALActionPerformed
        nombreUsuAL.setText("");
        apellidoUsuAL.setText("");
        nickUsuAL.setText("");
        direccionUsuAL.setText("");
        telefonoUsuAL.setText("");
        emailUsuAL.setText("");
        passwordUsuAL.setText("");
        fotoUsuAL2.setIcon(null);
    }//GEN-LAST:event_cancelarUsuALActionPerformed
    private void limpiarCamposUsuario(JTextField nombre, JTextField apellido, JTextField nick, JTextField dir) {

    }

    /*Método que se encarga de limpiar los datos en pantalla al momento que el usuario
    de click en el botón cancelar
     */
    private void cancelarProdALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarProdALActionPerformed
        //Limpiamos los campos de la pantalla de alta producto
        limpiarProducto(nombreProdAL, cantidadProdAL, precioProdAL, categoriaProdAL, imagenProdAL2);
    }//GEN-LAST:event_cancelarProdALActionPerformed
    /*Método que se encargará de leer los datos ingresados en la interfaz 
    y los mandará a los métodos correspondientes para manejar los datos     
     */
    private void guardarProdALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarProdALActionPerformed
        producto = new ProductVO();
        validarProducto(nombreProdAL, cantidadProdAL, precioProdAL, categoriaProdAL, imagenProdAL2);
        if (getValidarTerminadoProducto()) {
            String cantidadString = "";
            //Inicia obtención de datos ingresados en la interfaz 
            producto.setProductName(nombreProdAL.getText().toUpperCase());
            cantidadString = valueOf(cantidadProdAL.getValue().toString());
            Short cantidad = Short.parseShort(cantidadString);
            producto.setQuantity(cantidad);
            Double precio = Double.parseDouble(precioProdAL.getText());
            producto.setPriceTag(precio);
            producto.setCategoryName(getCategoria());
            System.out.println("categoria: " + getCategoria());
            producto.setImage(getDireccionImagen());
            //Finaliza obtención de datos 
            /*
        Se establece un injector y controlador para producto
        Injector: se encargará de la comunicación de la base de datos con java
        Controlador: se encargará de manejar y controlar los servicios establecidos para el administrador
             */
            Injector injector = Guice.createInjector(new ConfigureProductDI());
            ProductCtrlImpl productCtrl = injector.getInstance(ProductCtrlImpl.class);
            String productRecover = productCtrl.altaProducto(producto);
            JOptionPane.showMessageDialog(this, productRecover, "Estado", JOptionPane.INFORMATION_MESSAGE);
            //Limpiamos los campos de la pantalla de alta producto
            limpiarProducto(nombreProdAL, cantidadProdAL, precioProdAL, categoriaProdAL, imagenProdAL2);
            inicializarProductoEliminar();
        }
    }//GEN-LAST:event_guardarProdALActionPerformed
//Empiezan métodos para validar que los campos de los formularios estén escritos correctamente y tengan los valores esperados

    public void validarProducto(JTextField nombre, JSpinner cantidad, JTextField precio, JComboBox categoria, JLabel imagen) {
        String cantidadString = "";
        try {
            if (!nombre.getText().isEmpty()) {
                cantidadString = valueOf(cantidad.getValue().toString());
                int canti = Integer.parseInt(cantidadString);
                if (categoria.getSelectedIndex() != 0) {
                    if (categoria.getSelectedIndex() == 2) {
                        categoriaCantidad(cantidad);
                        if (!precio.getText().isEmpty()) {
                            if (imagen.getIcon() != null) {
                                setValidarProductoTerminado(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "Por favor, seleccione una imagen", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Ingrese un precio", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        if (!precio.getText().isEmpty()) {
                            if (canti > 0) {
                                if (imagen.getIcon() != null) {
                                    setValidarProductoTerminado(true);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una imagen", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a cero", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Ingrese un precio", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una categoría", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese nombre", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    boolean estadoDeValidacionProd = false;

    public void setValidarProductoTerminado(boolean aceptado) {
        estadoDeValidacionProd = true;
    }

    public boolean getValidarTerminadoProducto() {
        return estadoDeValidacionProd;
    }

    public void validarUsuario(JTextField nombre, JTextField apellido, JTextField nick, JTextField telefono, JTextField dir, JTextField email, JLabel foto) {
        try {
            if (!nombre.getText().isEmpty()) {
                if (!apellido.getText().isEmpty()) {
                    if (!nick.getText().isEmpty()) {
                        if (!telefono.getText().isEmpty()) {
                            if (!dir.getText().isEmpty()) {
                                if (!email.getText().isEmpty()) {
                                    if (foto.getIcon() != null) {
                                        setValidarTerminadoUsuAL(true);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Por favor, seleccione una imagen", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Ingrese email", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
                                }

                            } else {
                                JOptionPane.showMessageDialog(null, "Ingrese dirección", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Ingrese teléfono", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Ingrese nick", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "ingrese apellido", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese nombre", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    boolean estadoDeValidacionUsuAL = false;

    public void setValidarTerminadoUsuAL(boolean aceptado) {
        estadoDeValidacionUsuAL = true;
    }

    public boolean getValidarTerminadoUsu() {
        return estadoDeValidacionUsuAL;
    }
//Terminan métodos para validar que los campos de los formularios estén escritos correctamente y tengan los valores esperados
    private void imagenProd1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imagenProd1MouseClicked
        escogerImagen(imagenProdAL2);
    }//GEN-LAST:event_imagenProd1MouseClicked
    /* 
    Método encargado de saber qué categoría presiono el usuario del producto
     */
    private void categoriaProdALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoriaProdALActionPerformed
        JComboBox<String> combo = (JComboBox<String>) evt.getSource(); //Se declara una variable de tipo JComboBox
        switch (combo.getSelectedIndex()) { //Se obtiene el índice de la selección del comboBox
            case 1:
                categoria = "COMIDA RAPIDA";
                break;
            case 2:
                categoria = "COMIDA";
                categoriaCantidad(cantidadProdAL);
                break;
            case 3:
                categoria = "BEBIDA";
                break;
            case 4:
                categoria = "SNACK";
                break;
            case 5:
                categoria = "BREAD";
        }
    }//GEN-LAST:event_categoriaProdALActionPerformed
    private void categoriaCantidad(JSpinner cantidad) {
        cantidad.getModel().setValue(0);
    }
    /*
    Método encargado de buscar un producto, y verificar qué producto fue selecionado
    y en base a eso mostrar los datos en pantalla para su modificación
     */
    int idProductoGlobal = 0;
    private void buscarProdMODActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarProdMODActionPerformed
        productoList = new ArrayList<>(); //se crea un objeto de tipo ArrayList
        String nombreProdModBuscar = nombreProdMOD.getText().toUpperCase(); //Se obtiene el nombre del producto a buscar
        /*Se declara un injector y se le declara el tipo de clase a la que queremos que haga referencia, en este caso
        a una configuración de producto */
        Injector injector = Guice.createInjector(new ConfigureProductDI());
        //Se declara el controlador
        ProductCtrlImpl productCtrl = injector.getInstance(ProductCtrlImpl.class);
        //Se guarda en el objeto productoList una lista de todos los productos que coinciden con el nombre
        productoList = productCtrl.buscarProducto(nombreProdModBuscar);
        Iterator<ProductVO> productoIterador = productoList.iterator();
        //Se declaran variables de tipo vector para los valores de la tabla
        String data[][] = {};
        String cabeza[] = {"NOMBRE", "PRECIO"};
        DefaultTableModel tm = new DefaultTableModel(data, cabeza);
        JFrame vtn = new JFrame(); //Se crea un JFrame para el contenedor de la tabla de los productos a mostrar
        tabla.setModel(tm);
        tabla.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        vtn.add(scrollPane);
        vtn.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vtn.setVisible(true);
        vtn.setBounds(screenSize.width / 4, screenSize.height / 4, 400, 200);
        tabla.addMouseListener(new MouseAdapter() { //Se agrega un escuchador a la tabla
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    desbloquearCamposProd();
                    int indice = tableMousePressed(e); //La variable índice contendrá el número de la fila de la cual se le dio click a la tabla
                    vtn.dispose(); //Se cierra la tabla al momento de dar doble click sobre ella
                    //Empieza la obtención de los datos del producto que se busco
                    idProductoGlobal = productoList.get(indice).getId();
                    setIdProducto(idProductoGlobal);
                    nombreProdMOD.setText(productoList.get(indice).getProductName());
                    String valor = productoList.get(indice).getQuantity().toString();
                    int valorI = Integer.parseInt(valor);
                    cantidadProdMOD.getModel().setValue(valorI);
                    precioProdMOD.setText(productoList.get(indice).getPriceTag().toString());
                    String nombreCat = productoList.get(indice).getCategoryName();
                    int posicion = 0;
                    switch (nombreCat) {
                        case "COMIDA RAPIDA":
                            posicion = 1;
                            break;
                        case "COMIDA":
                            posicion = 2;
                            break;
                        case "BEBIDA":
                            posicion = 3;
                            break;
                        case "SNACK":
                            posicion = 4;
                            break;
                        case "BREAD":
                            posicion = 5;
                            break;
                    }
                    categoriaProdMOD.setSelectedIndex(posicion);
                    setDireccionImagen(productoList.get(indice).getImage());
                    ImageIcon imagen = new ImageIcon(productoList.get(indice).getImage());
                    ImageIcon icono = new ImageIcon(imagen.getImage().getScaledInstance(imagenProdMOD2.getWidth(), imagenProdMOD2.getHeight(), Image.SCALE_DEFAULT));
                    imagenProdMOD2.setIcon(icono);

                    //Finaliza la obtención de los datos 
                }
            }
        });
        /*Ciclo encargado de obtener los datos de los productos que coinciden con el nombre del que desea buscarse
        y los agrega al modelo de la tabla
         */
        while (productoIterador.hasNext()) {
            ProductVO productoCiclo = productoIterador.next();
            String nombreArray = productoCiclo.getProductName();
            String precioArray = productoCiclo.getPriceTag().toString();
            String datos[] = {nombreArray, precioArray};
            tm.addRow(datos);
        }
    }//GEN-LAST:event_buscarProdMODActionPerformed
    public void setIdProducto(int var) {
        this.idProductoGlobal = var;
    }

    public int getIdProducto() {
        return this.idProductoGlobal;
    }

    //Método utilizado para saber en qué fila se dio click a la tabla
    private int tableMousePressed(MouseEvent evt) {
        int row = 0;
        if (evt.getClickCount() == 2) {
            Point point = evt.getPoint(); //Obtiene los puntos de pantalla
            row = tabla.rowAtPoint(point); //Obtiene la coordenada en x de la tabla
        }
        return row;
    }

    //Método utilizado para saber en qué fila se dio click a la tabla
    private int tableMousePressed2(MouseEvent evt) {
        int row = 0;
        Point point = evt.getPoint(); //Obtiene los puntos de pantalla
        row = tablaCON.rowAtPoint(point); //Obtiene la coordenada en x de la tabla
        return row;
    }

    //Método encargado de obtener los datos de los ingresados por el usuario para la creación de un usuario
    private void guardarUsuALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarUsuALActionPerformed
        validarUsuario(nombreUsuAL, apellidoUsuAL, nickUsuAL, telefonoUsuAL, direccionUsuAL, emailUsuAL, fotoUsuAL2);
        if (getValidarTerminadoUsu()) {
            if (passwordUsuAL.getPassword().length > 0) {
                //Empieza la obtención de los datos ingresados en el formulario
                String passwordUsuario = "";
                trabajador = new WorkerVO();
                usuario = new UserVO();
                trabajador.setName(nombreUsuAL.getText().toUpperCase());
                usuario.setUserName(nickUsuAL.getText().toUpperCase());
                trabajador.setAddress(direccionUsuAL.getText().toUpperCase());
                passwordUsuario = String.copyValueOf(passwordUsuAL.getPassword());
                usuario.setPassword(passwordUsuario);
                usuario.setUserType(UserType.CAJERO);
                trabajador.setLastName(apellidoUsuAL.getText().toUpperCase());
                trabajador.setPhoneNumber(telefonoUsuAL.getText());
                trabajador.setEmail(emailUsuAL.getText());
                trabajador.setPhoto(getDireccionImagen());
                trabajador.setUserVO(usuario);
                //Finaliza la obtención de los datos 
                //Se establece un injector y controlador para usuario
                Injector injector = Guice.createInjector(new ConfigureUserDI());
                UserCtrlImpl userCtrl = injector.getInstance(UserCtrlImpl.class);
                String userRecover = userCtrl.altaUsuario(trabajador);
                JOptionPane.showMessageDialog(this, userRecover, "Estado", JOptionPane.INFORMATION_MESSAGE);
                //Limpiamos los campos de la pantalla de alta producto
                nombreUsuAL.setText("");
                nickUsuAL.setText("");
                direccionUsuAL.setText("");
                apellidoUsuAL.setText("");
                telefonoUsuAL.setText("");
                emailUsuAL.setText("");
                passwordUsuAL.setText("");
                fotoUsuAL2.setIcon(null);
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese contraseña", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_guardarUsuALActionPerformed

    private void fotoUsuALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fotoUsuALActionPerformed
        escogerImagen(fotoUsuAL2);
    }//GEN-LAST:event_fotoUsuALActionPerformed
    /*Método utilizado para la consulta de un producto el cual nos mostrará los productos que lleven similitud en 
    el nombre y lo podremos visualizar en una tabla
     */
    private void aceptarProdCONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarProdCONActionPerformed
        productoList = new ArrayList<>(); //Se declara un objeto de tipo ArrayList el cual contendrá los productos que coincidan con el que se quiere buscar
        productoList = null;
        String nombreProdCONBuscar = nombreProdCON.getText().toUpperCase(); //Se obtiene el nombre del producto a buscar
        //Se establece el injector para producto
        Injector injector = Guice.createInjector(new ConfigureProductDI());
        //Se establece el controlador para el producto a buscar
        ProductCtrlImpl productCtrl = injector.getInstance(ProductCtrlImpl.class);
        productoList = productCtrl.buscarProducto(nombreProdCONBuscar); //Obtiene la lista de los productos encontrados 
        Iterator<ProductVO> productoIterador = productoList.iterator();
        //Se declaran variables para las filas y columnas de la tabla que aprecerá con los productos que se desea buscar
        String data[][] = {};
        String cabeza[] = {"NOMBRE", "CANTIDAD", "PRECIO", "CATEGORÍA"};
        DefaultTableModel tm = new DefaultTableModel(data, cabeza);
        tablaCON.setModel(tm);
        String nombreArray, cantidadArray, precioArray, categoriaArray;
        // Ciclo que obtiene los atributos de los productos    
        while (productoIterador.hasNext()) {
            ProductVO productoCiclo = productoIterador.next();
            nombreArray = productoCiclo.getProductName();
            cantidadArray = productoCiclo.getQuantity().toString();
            precioArray = productoCiclo.getPriceTag().toString();
            categoriaArray = productoCiclo.getCategoryName();
            String datos[] = {nombreArray, cantidadArray, precioArray, categoriaArray};
            tm.addRow(datos);
        }
        //
        tablaCON.addMouseListener(new MouseAdapter() { //Se agrega un escuchador a la tabla
            @Override
            public void mousePressed(MouseEvent e) {
                int indice = tableMousePressed2(e); //La variable índice contendrá el número de la fila de la cual se le dio click a la tabla
                System.out.println("el indice de consulta " + indice);
                //Empieza la obtención de los datos del producto que se busco
                ImageIcon imagen = new ImageIcon(productoList.get(indice).getImage());
                ImageIcon icono = new ImageIcon(imagen.getImage().getScaledInstance(imagenProdCON.getWidth(), imagenProdCON.getHeight(), Image.SCALE_DEFAULT));
                imagenProdCON.setIcon(icono);
                //Finaliza la obtención de los datos 
            }
        });
    }//GEN-LAST:event_aceptarProdCONActionPerformed
    int id_usuario, tipoUsuarioNum;
    UserType usertype;
    private void buscarUsuMODActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarUsuMODActionPerformed
        workerList = new ArrayList<>(); //se crea un objeto de tipo ArrayList
        String nombreUsuModBuscar = nombreProdMOD.getText().toUpperCase(); //Se obtiene el nombre del producto a buscar
        /*Se declara un injector y se le declara el tipo de clase a la que queremos que haga referencia, en este caso
        a una configuración de producto */
        Injector injector = Guice.createInjector(new ConfigureUserDI());
        //Se declara el controlador
        UserCtrlImpl userCtrl = injector.getInstance(UserCtrlImpl.class);
        //Se guarda en el objeto productoList una lista de todos los productos que coinciden con el nombre
        workerList = userCtrl.buscarUsuarios();
        Iterator<WorkerVO> workerIterador = workerList.iterator();
        //Se declaran variables de tipo vector para los valores de la tabla
        String data[][] = {};
        String cabeza[] = {"NOMBRE", "APELLIDO"};
        DefaultTableModel tm = new DefaultTableModel(data, cabeza);
        JFrame vtn = new JFrame(); //Se crea un JFrame para el contenedor de la tabla de los productos a mostrar
        tabla.setModel(tm);
        tabla.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        vtn.add(scrollPane);
        vtn.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vtn.setVisible(true);
        vtn.setBounds(screenSize.width / 4, screenSize.height / 4, 400, 200);
        tabla.addMouseListener(new MouseAdapter() { //Se agrega un escuchador a la tabla
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int indice = tableMousePressed(e); //La variable índice contendrá el número de la fila de la cual se le dio click a la tabla
                    vtn.dispose(); //Se cierra la tabla al momento de dar doble click sobre ella
                    //Empieza la obtención de los datos del producto que se busco
                    id_usuario = workerList.get(indice).getId();
                    setIdUsuario(id_usuario);
                    desbloquearCamposUsuario();
                    nombreUsuMOD.setText(workerList.get(indice).getName());
                    apellidoUsuMOD.setText(workerList.get(indice).getLastName());
                    nickUsuMOD.setText(workerList.get(indice).getUserVO().getUserName());
                    telefonoUsuMOD.setText(workerList.get(indice).getPhoneNumber());
                    direccionUsuMOD.setText(workerList.get(indice).getAddress());
                    passwordUsuMOD.setText(workerList.get(indice).getUserVO().getPassword());
                    emailUsuMOD.setText(workerList.get(indice).getEmail());
                    pintarImagen(workerList.get(indice).getPhoto(), fotoUsuMOD2);
                    setDireccionImagen(workerList.get(indice).getPhoto());
                    if (workerList.get(indice).getUserVO().getUserType() == usertype.CAJERO) {
                        tipoUsuarioNum = 0;
                    } else {
                        tipoUsuarioNum = 1;
                    }
                    //Finaliza la obtención de los datos 
                }
            }
        });
        while (workerIterador.hasNext()) {
            WorkerVO workerCiclo = workerIterador.next();
            String nombreArray = workerCiclo.getName();
            String apellidoArray = workerCiclo.getLastName();
            String datos[] = {nombreArray, apellidoArray};
            tm.addRow(datos);
        }
    }//GEN-LAST:event_buscarUsuMODActionPerformed
    public void setIdUsuario(int id) {
        id_usuario = id;
    }

    public int getIdUsuario() {
        return id_usuario;
    }
    private void cerrarSesionVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarSesionVentasActionPerformed
        JFrame contenedorSes = new JFrame();
        contenedorSes.setBounds(screenSize.width / 4, screenSize.height / 4, 220, 100);
        contenedorSes.setVisible(true);
        contenedorSes.setResizable(false);
        JPanel contenedorPanel = new JPanel();
        JLabel cerrarSes = new JLabel("¿Estás seguro de salir?");
        JButton aceptoCerrarSesion = new JButton("ACEPTAR");
        JButton cancelarCerrarSesion = new JButton("CANCELAR");
        aceptoCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contenedorSes.dispose();
                dispose();
                Inicio inicioNew = new Inicio();
                inicioNew.setVisible(true);
            }
        });
        cancelarCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contenedorSes.dispose();
            }
        });
        contenedorPanel.add(cerrarSes);
        contenedorPanel.add(aceptoCerrarSesion);
        contenedorPanel.add(cancelarCerrarSesion);
        contenedorSes.add(contenedorPanel);
    }//GEN-LAST:event_cerrarSesionVentasActionPerformed

    private void guardarProdMODActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarProdMODActionPerformed
        producto = new ProductVO();
        validarProducto(nombreProdMOD, cantidadProdMOD, precioProdMOD, categoriaProdMOD, imagenProdMOD2);
        //Inicia obtención de datos ingresados en la interfaz
        if (getValidarTerminadoProducto()) {
            System.out.println("-------" + getIdProducto() + "------");
            producto.setId(getIdProducto());
            producto.setProductName(nombreProdMOD.getText().toUpperCase());
            String cantidadString = valueOf(cantidadProdMOD.getValue().toString());
            Short cantidad = Short.parseShort(cantidadString);
            producto.setQuantity(cantidad);
            Double precio = Double.parseDouble(precioProdMOD.getText());
            producto.setPriceTag(precio);
            producto.setCategoryName(getCategoria());
            System.out.println("siguiendo imagen: " + getDireccionImagen());
            producto.setImage(getDireccionImagen());
            //Finaliza obtención de datos 
            /*
        Se establece un injector y controlador para producto
        Injector: se encargará de la comunicación de la base de datos con java
        Controlador: se encargará de manejar y controlar los servicios establecidos para el administrador
             */
            Injector injector = Guice.createInjector(new ConfigureProductDI());
            ProductCtrlImpl productCtrl = injector.getInstance(ProductCtrlImpl.class);
            String productRecover = productCtrl.modificarProducto(producto);
            JOptionPane.showMessageDialog(this, productRecover, "--Estado--: ", JOptionPane.INFORMATION_MESSAGE);
            //Limpiamos los campos de la pantalla de modificar producto producto
            limpiarProducto(nombreProdMOD, cantidadProdMOD, precioProdMOD, categoriaProdMOD, imagenProdMOD2);
            bloquearCamposProd();
            inicializarProductoEliminar();
        }

    }//GEN-LAST:event_guardarProdMODActionPerformed
    public void limpiarProducto(JTextField nombre, JSpinner cantidad, JTextField precio, JComboBox categoria, JLabel imagen) {
        nombre.setText("");
        cantidad.getModel().setValue(0);
        precio.setText("");
        categoria.setSelectedIndex(0);
        imagen.setIcon(null);
    }
    private void categoriaProdMODActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoriaProdMODActionPerformed
        JComboBox<String> combo = (JComboBox<String>) evt.getSource(); //Se declara una variable de tipo JComboBox
        switch (combo.getSelectedIndex()) { //Se obtiene el índice de la selección del comboBox
            case 1:
                categoria = "COMIDA RAPIDA";
                break;
            case 2:
                categoria = "COMIDA";
                categoriaCantidad(cantidadProdMOD);
                break;
            case 3:
                categoria = "BEBIDA";
                break;
            case 4:
                categoria = "SNACK";
                break;
            case 5:
                categoria = "BREAD";
        }
    }//GEN-LAST:event_categoriaProdMODActionPerformed

    private void imagenProdMODActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imagenProdMODActionPerformed
        escogerImagen(imagenProdMOD2);
    }//GEN-LAST:event_imagenProdMODActionPerformed
    private void escogerImagen(JLabel etiquetaImagen) {
        fileChooser = new JFileChooser(); //Se declara una variable de tipo JFileChooser
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG", "jpg", "png"); //Se le especifica que tipo de archivo queremos que se nos muestre
        fileChooser.setFileFilter(filter); //Se le pasa como parametro el filtrado a la variable fileChooser 
        int regresaValor = fileChooser.showOpenDialog(null); //Variable encargada de verificar que la ventana para escoger la imagen se haya acompletado con éxito
        //Acción del fileChooser
        if (regresaValor == JFileChooser.APPROVE_OPTION) {
            //Crear propiedades para ser utilizadas por fileChooser
            File archivoElegido = fileChooser.getSelectedFile();
            //Obteniendo la dirección del archivo
            String direccion = archivoElegido.getPath();
            setDireccionImagen(direccion);
            //Se crea la imagen para la etiqueta
            ImageIcon imagen = new ImageIcon(direccion);
            ImageIcon icono = new ImageIcon(imagen.getImage().getScaledInstance(etiquetaImagen.getWidth(), etiquetaImagen.getHeight(), Image.SCALE_DEFAULT));
            etiquetaImagen.setIcon(icono);
        }
    }

    private void pintarImagen(String dir, JLabel jlblImagen) {
        ImageIcon imagen = new ImageIcon(dir);
        ImageIcon icono = new ImageIcon(imagen.getImage().getScaledInstance(jlblImagen.getWidth(), jlblImagen.getHeight(), Image.SCALE_DEFAULT));
        jlblImagen.setIcon(icono);
    }
    private void nickUsuMODActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nickUsuMODActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nickUsuMODActionPerformed

    private void cancelarProdMODActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarProdMODActionPerformed
        //Limpiamos los campos de la pantalla de alta producto
        limpiarProducto(nombreProdMOD, cantidadProdMOD, precioProdMOD, categoriaProdMOD, imagenProdMOD2);
        bloquearCamposProd();
    }//GEN-LAST:event_cancelarProdMODActionPerformed
    private void bloquearCamposProd() {
        cantidadProdMOD.setEnabled(false);
        precioProdMOD.setEnabled(false);
        imagenProdMOD.setEnabled(false);
        precioProdMOD.setEnabled(false);
        categoriaProdMOD.setEnabled(false);
    }

    private void desbloquearCamposProd() {
        cantidadProdMOD.setEnabled(true);
        precioProdMOD.setEnabled(true);
        imagenProdMOD.setEnabled(true);
        precioProdMOD.setEnabled(true);
        categoriaProdMOD.setEnabled(true);
    }
    private void precioProdALKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_precioProdALKeyTyped
        if (!Character.isDigit(evt.getKeyChar()) && evt.getKeyChar() != '.') {
            evt.consume();
        }
        if (evt.getKeyChar() == '.' && precioProdAL.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_precioProdALKeyTyped

    private void nombreUsuALKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreUsuALKeyTyped
        char tecla = evt.getKeyChar();
        if (!Character.isLetter(tecla) && tecla != KeyEvent.VK_SPACE && tecla != KeyEvent.VK_BACK_SPACE) {
            evt.consume();
        }
    }//GEN-LAST:event_nombreUsuALKeyTyped

    private void apellidoUsuALKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_apellidoUsuALKeyTyped
        char tecla = evt.getKeyChar();
        if (!Character.isLetter(tecla) && tecla != KeyEvent.VK_SPACE && tecla != KeyEvent.VK_BACK_SPACE) {
            evt.consume();
        }
    }//GEN-LAST:event_apellidoUsuALKeyTyped

    private void precioProdMODKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_precioProdMODKeyTyped
        if (!Character.isDigit(evt.getKeyChar()) && evt.getKeyChar() != '.') {
            evt.consume();
        }
        if (evt.getKeyChar() == '.' && precioProdMOD.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_precioProdMODKeyTyped

    private void telefonoUsuALKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefonoUsuALKeyTyped
        char tecla = evt.getKeyChar();
        if (telefonoUsuAL.getText().length() == 10) {
            evt.consume();
        }
        if (!Character.isDigit(tecla) && tecla != KeyEvent.VK_BACK_SPACE) {
            evt.consume();
        }
    }//GEN-LAST:event_telefonoUsuALKeyTyped

    private void nombreUsuMODKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreUsuMODKeyTyped
        char tecla = evt.getKeyChar();
        if (!Character.isLetter(tecla) && tecla != KeyEvent.VK_SPACE && tecla != KeyEvent.VK_BACK_SPACE) {
            evt.consume();
        }
    }//GEN-LAST:event_nombreUsuMODKeyTyped

    private void realizarReporteVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_realizarReporteVentasActionPerformed
        gr.reporteVentas(getFechaInicio(), getFechaFin());
    }//GEN-LAST:event_realizarReporteVentasActionPerformed

    SimpleDateFormat sdf;
    String fechaInicio = "";
    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        if (jDateChooser1.getDate() != null) {
            String formato = jDateChooser1.getDateFormatString();
            Date date = jDateChooser1.getDate();
            sdf = new SimpleDateFormat(formato);
            String fecha1 = String.valueOf(sdf.format(date));
            fechaInicio = fecha1;
            System.out.println("Fecha inicio: " + fechaInicio);
        } else {
            // Fechas(jDateChooser1);
        }
    }//GEN-LAST:event_jDateChooser1PropertyChange
    String fechaFin = "";
    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange
        if (jDateChooser1.getDate() != null) {
            realizarReporteVentas.setEnabled(true);
            Date datee = jDateChooser2.getDate();
            fechaFin = String.valueOf(sdf.format(datee));
            System.out.println("fecha fin: " + fechaFin);
        } else {
            //       Fechas(jDateChooser2);
        }
    }//GEN-LAST:event_jDateChooser2PropertyChange

    private void Fechas(JDateChooser jdc) {
        DateFormat df = DateFormat.getDateInstance();
        Date fechaActual = new Date();

        System.out.println("");

        jdc.setDate(fechaActual);
    }

    private String getFechaInicio() {
        return fechaInicio;
    }

    private String getFechaFin() {
        return fechaFin;
    }
    private void siguienteProdBAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siguienteProdBAActionPerformed
        contadorProdEliminar++;
        pintarImagenProducto(dirImagenesGlobal);
    }//GEN-LAST:event_siguienteProdBAActionPerformed

    private void atrasProdBAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atrasProdBAActionPerformed
        contadorProdEliminar--;
        pintarImagenProducto(dirImagenesGlobal);
    }//GEN-LAST:event_atrasProdBAActionPerformed

    private void buscarProdBAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarProdBAActionPerformed
        productoList = new ArrayList<>(); //se crea un objeto de tipo ArrayList
        String nombreProdBaBuscar = nombreProdBA.getText().toUpperCase(); //Se obtiene el nombre del producto a buscar
        /*Se declara un injector y se le declara el tipo de clase a la que queremos que haga referencia, en este caso
        a una configuración de producto */
        Injector injector = Guice.createInjector(new ConfigureProductDI());
        //Se declara el controlador
        ProductCtrlImpl productCtrl = injector.getInstance(ProductCtrlImpl.class);
        //Se guarda en el objeto productoList una lista de todos los productos que coinciden con el nombre
        productoList = productCtrl.buscarProducto(nombreProdBaBuscar);
        Iterator<ProductVO> productoIterador = productoList.iterator();
        //Se declaran variables de tipo vector para los valores de la tabla
        String data[][] = {};
        String cabeza[] = {"NOMBRE", "PRECIO"};
        DefaultTableModel tm = new DefaultTableModel(data, cabeza);
        JFrame vtn = new JFrame(); //Se crea un JFrame para el contenedor de la tabla de los productos a mostrar
        tabla.setModel(tm);
        tabla.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        vtn.add(scrollPane);
        vtn.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vtn.setVisible(true);
        vtn.setBounds(screenSize.width / 4, screenSize.height / 4, 400, 200);
        tabla.addMouseListener(new MouseAdapter() { //Se agrega un escuchador a la tabla
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int indice = tableMousePressed(e); //La variable índice contendrá el número de la fila de la cual se le dio click a la tabla
                    vtn.dispose(); //Se cierra la tabla al momento de dar doble click sobre ella
                    //Empieza la obtención de los datos del producto que se busco
                    // idProductoGlobal = productoList.get(indice).getId();
                    // setIdProducto(idProductoGlobal);
                    for (int i = 0; i < nombreProdGlobal.length; i++) {
                        if (nombreProdGlobal[i].equalsIgnoreCase(productoList.get(indice).getProductName())) {
                            setIndiceProductoEliminar(i);
                        }
                    }
                    nombreProdBA.setText(productoList.get(indice).getProductName());

                    ImageIcon imagen = new ImageIcon(productoList.get(indice).getImage());
                    ImageIcon icono = new ImageIcon(imagen.getImage().getScaledInstance(contEliminarProd1.getWidth(), contEliminarProd1.getHeight(), Image.SCALE_DEFAULT));
                    contEliminarProd1.setIcon(icono);
                    contEliminarProd2.setIcon(null);
                    contEliminarProd3.setIcon(null);
                    contEliminarProd4.setIcon(null);
                    contEliminarProd5.setIcon(null);
                    contEliminarProd6.setIcon(null);
                    opcionEliminarProd2.setEnabled(false);
                    opcionEliminarProd3.setEnabled(false);
                    opcionEliminarProd4.setEnabled(false);
                    opcionEliminarProd5.setEnabled(false);
                    opcionEliminarProd6.setEnabled(false);
                    //Finaliza la obtención de los datos 
                }
            }
        });
        /*Ciclo encargado de obtener los datos de los productos que coinciden con el nombre del que desea buscarse
        y los agrega al modelo de la tabla
         */
        while (productoIterador.hasNext()) {
            ProductVO productoCiclo = productoIterador.next();
            String nombreArray = productoCiclo.getProductName();
            String precioArray = productoCiclo.getPriceTag().toString();
            String datos[] = {nombreArray, precioArray};
            tm.addRow(datos);
        }
    }//GEN-LAST:event_buscarProdBAActionPerformed
    private void eliminarProd(int posicion) {
        String nombreEliminar = nombreProdGlobal[posicion];
        Injector injector = Guice.createInjector(new ConfigureProductDI());
        ProductCtrlImpl productCtrl = injector.getInstance(ProductCtrlImpl.class);
        String productRecover = productCtrl.bajaProducto(nombreEliminar);
        JOptionPane.showMessageDialog(this, productRecover, "Estado", JOptionPane.INFORMATION_MESSAGE);
    }
    private void eliminarUsuarioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarUsuarioBtnActionPerformed
        opcionEliminarUsuario();
        desBtnEliminarUsu();
        fotoUsuarioBaja.setIcon(null);
        nombreUsuarioBaja.setText("NOMBRE");
        apellidoUsuarioBaja.setText("APELLIDO");
    }//GEN-LAST:event_eliminarUsuarioBtnActionPerformed
    private void eliminarUsuario(String nombre) {
        Injector injector = Guice.createInjector(new ConfigureUserDI());
        UserCtrlImpl userCtrl = injector.getInstance(UserCtrlImpl.class);
        String userRecover = userCtrl.bajaUsuario(nombre);
        JOptionPane.showMessageDialog(this, userRecover, "Estado", JOptionPane.INFORMATION_MESSAGE);
    }
    private void opcionEliminarProd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionEliminarProd1ActionPerformed
        System.out.println(getIndiceProductoEliminar() + " : " + dirImagenesGlobal[getIndiceProductoEliminar()]
                + " nombre: " + nombreProdGlobal[getIndiceProductoEliminar()]);
        confirmacionEliminarProd(getIndiceProductoEliminar());
    }//GEN-LAST:event_opcionEliminarProd1ActionPerformed

    private void opcionEliminarProd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionEliminarProd2ActionPerformed
        System.out.println(getIndiceProductoEliminar2() + " : " + dirImagenesGlobal[getIndiceProductoEliminar2()]
                + " nombre: " + nombreProdGlobal[getIndiceProductoEliminar2()]);
        confirmacionEliminarProd(getIndiceProductoEliminar2());
    }//GEN-LAST:event_opcionEliminarProd2ActionPerformed

    private void opcionEliminarProd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionEliminarProd3ActionPerformed
        System.out.println(getIndiceProductoEliminar3() + " : " + dirImagenesGlobal[getIndiceProductoEliminar3()]
                + " nombre: " + nombreProdGlobal[getIndiceProductoEliminar3()]);
        confirmacionEliminarProd(getIndiceProductoEliminar3());
    }//GEN-LAST:event_opcionEliminarProd3ActionPerformed

    private void opcionEliminarProd4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionEliminarProd4ActionPerformed
        System.out.println(getIndiceProductoEliminar4() + " : " + dirImagenesGlobal[getIndiceProductoEliminar4()]
                + " nombre: " + nombreProdGlobal[getIndiceProductoEliminar4()]);
        confirmacionEliminarProd(getIndiceProductoEliminar4());
    }//GEN-LAST:event_opcionEliminarProd4ActionPerformed

    private void opcionEliminarProd5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionEliminarProd5ActionPerformed
        System.out.println(getIndiceProductoEliminar5() + " : " + dirImagenesGlobal[getIndiceProductoEliminar5()]
                + " nombre: " + nombreProdGlobal[getIndiceProductoEliminar5()]);
        confirmacionEliminarProd(getIndiceProductoEliminar5());
    }//GEN-LAST:event_opcionEliminarProd5ActionPerformed

    private void opcionEliminarProd6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionEliminarProd6ActionPerformed
        System.out.println(getIndiceProductoEliminar6() + " : " + dirImagenesGlobal[getIndiceProductoEliminar6()]
                + " nombre: " + nombreProdGlobal[getIndiceProductoEliminar6()]);
        confirmacionEliminarProd(getIndiceProductoEliminar6());
    }//GEN-LAST:event_opcionEliminarProd6ActionPerformed
    private void opcionEliminarUsuario() {
        JFrame contenedor = new JFrame();
        contenedor.setBounds(screenSize.width / 4, screenSize.height / 4, 280, 120);
        contenedor.setVisible(true);
        contenedor.setResizable(false);
        JPanel contenedorPanel = new JPanel();
        JLabel advertencia = new JLabel("¿Estás seguro que desea eliminarlo?");
        JButton acepto = new JButton("ACEPTAR");
        JButton cancelar = new JButton("CANCELAR");
        acepto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contenedor.dispose();
                eliminarUsuario(nickEliminarGlobal);
            }
        });
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contenedor.dispose();
            }
        });
        contenedorPanel.add(advertencia);
        contenedorPanel.add(acepto);
        contenedorPanel.add(cancelar);
        contenedor.add(contenedorPanel);
    }

    private void confirmacionEliminarProd(int indice) {
        JFrame contenedor = new JFrame();
        contenedor.setBounds(screenSize.width / 4, screenSize.height / 4, 280, 120);
        contenedor.setVisible(true);
        contenedor.setResizable(false);
        JPanel contenedorPanel = new JPanel();
        JLabel advertencia = new JLabel("¿Estás seguro que desea eliminarlo?");
        JButton acepto = new JButton("ACEPTAR");
        JButton cancelar = new JButton("CANCELAR");
        acepto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contenedor.dispose();
                eliminarProd(indice);
                inicializarProductoEliminar();
            }
        });
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contenedor.dispose();
            }
        });
        contenedorPanel.add(advertencia);
        contenedorPanel.add(acepto);
        contenedorPanel.add(cancelar);
        contenedor.add(contenedorPanel);
    }
    String nickEliminarGlobal;
    private void buscarUsuarioBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarUsuarioBajaActionPerformed
        workerList = new ArrayList<>(); //se crea un objeto de tipo ArrayList
        /*Se declara un injector y se le declara el tipo de clase a la que queremos que haga referencia, en este caso
        a una configuración de producto */
        Injector injector = Guice.createInjector(new ConfigureUserDI());
        //Se declara el controlador
        UserCtrlImpl userCtrl = injector.getInstance(UserCtrlImpl.class);
        //Se guarda en el objeto productoList una lista de todos los productos que coinciden con el nombre
        workerList = userCtrl.buscarUsuarios();
        Iterator<WorkerVO> workerIterador = workerList.iterator();
        //Se declaran variables de tipo vector para los valores de la tabla
        String data[][] = {};
        String cabeza[] = {"NOMBRE", "APELLIDO"};
        DefaultTableModel tm = new DefaultTableModel(data, cabeza);
        JFrame vtn = new JFrame(); //Se crea un JFrame para el contenedor de la tabla de los productos a mostrar
        tabla.setModel(tm);
        tabla.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        vtn.add(scrollPane);
        vtn.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vtn.setVisible(true);
        vtn.setBounds(screenSize.width / 4, screenSize.height / 4, 400, 200);
        tabla.addMouseListener(new MouseAdapter() { //Se agrega un escuchador a la tabla
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int indice = tableMousePressed(e); //La variable índice contendrá el número de la fila de la cual se le dio click a la tabla
                    vtn.dispose(); //Se cierra la tabla al momento de dar doble click sobre ella
                    //Empieza la obtención de los datos del producto que se busco
                    desBtnEliminarUsu();
                    nombreUsuarioBaja.setText(workerList.get(indice).getName());
                    apellidoUsuarioBaja.setText(workerList.get(indice).getLastName());
                    nickEliminarGlobal = workerList.get(indice).getUserVO().getUserName();
                    pintarImagen(workerList.get(indice).getPhoto(), fotoUsuarioBaja);
                    //Finaliza la obtención de los datos 
                }
            }
        });
        while (workerIterador.hasNext()) {
            WorkerVO workerCiclo = workerIterador.next();
            String nombreArray = workerCiclo.getName();
            String apellidoArray = workerCiclo.getLastName();
            String datos[] = {nombreArray, apellidoArray};
            tm.addRow(datos);
        }

    }//GEN-LAST:event_buscarUsuarioBajaActionPerformed
    private void bloqBtnEliminarUsu() {
        eliminarUsuarioBtn.setEnabled(false);
    }

    private void desBtnEliminarUsu() {
        eliminarUsuarioBtn.setEnabled(true);
    }
    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        try {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            Session sesion = Session.getDefaultInstance(props);
            String correoRemitente = "vermonsadecv@gmail.com";
            String passwordRemitente = "vermon123";
            String correoReceptor = "gustavosh0154@gmail.com";
            String asunto = "Mi primer correo en java";
            String mensaje = "Este es el contenido de mi primer video";
            MimeMessage message = new MimeMessage(sesion);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
            message.setSubject(asunto);
            message.setText(mensaje);
            Transport t = sesion.getTransport("smtp");
            t.connect(correoRemitente, passwordRemitente);
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            t.close();
            JOptionPane.showMessageDialog(null, "Correo electronico enviado");

            message.setFrom(new InternetAddress(correoRemitente));
        } catch (AddressException ex) {
            Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Administrador.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_emailActionPerformed

    private void fotoUsuMOD1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fotoUsuMOD1ActionPerformed
        escogerImagen(fotoUsuMOD2);
    }//GEN-LAST:event_fotoUsuMOD1ActionPerformed

    private void guardarUsuMODActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarUsuMODActionPerformed
        WorkerVO trabajador = new WorkerVO();
        UserVO usuario = new UserVO();
        validarUsuario(nombreUsuMOD, apellidoUsuMOD, nickUsuMOD, telefonoUsuMOD, direccionUsuMOD, emailUsuMOD, fotoUsuMOD2);
        if (getValidarTerminadoUsu()) {
            if (!passwordUsuMOD.getText().isEmpty()) {
                trabajador.setId(getIdUsuario());
                trabajador.setName(nombreUsuMOD.getText().toUpperCase());
                trabajador.setLastName(apellidoUsuMOD.getText().toUpperCase());
                usuario.setUserName(nickUsuMOD.getText().toUpperCase());
                usuario.setPassword(passwordUsuMOD.getText());
                if (tipoUsuarioNum == 0) {
                    usuario.setUserType(UserType.CAJERO);
                } else {
                    usuario.setUserType(UserType.ADMINISTRADOR);
                }
                trabajador.setUserVO(usuario);
                trabajador.setAddress(direccionUsuMOD.getText().toUpperCase());
                trabajador.setPhoneNumber(telefonoUsuMOD.getText());
                trabajador.setEmail(emailUsuMOD.getText());
                trabajador.setPhoto(getDireccionImagen());
                Injector injector = Guice.createInjector(new ConfigureUserDI());
                UserCtrlImpl userCtrl = injector.getInstance(UserCtrlImpl.class);
                String userRecover = userCtrl.modificarUsuario(trabajador);
                JOptionPane.showMessageDialog(this, userRecover, "Estado", JOptionPane.INFORMATION_MESSAGE);
                nombreUsuMOD.setText("");
                apellidoUsuMOD.setText("");
                nickUsuMOD.setText("");
                direccionUsuMOD.setText("");
                telefonoUsuMOD.setText("");
                emailUsuMOD.setText("");
                passwordUsuMOD.setText("");
                fotoUsuMOD2.setIcon(null);
                bloquearCamposUsuario();
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese contraseña", "Mensaje de error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_guardarUsuMODActionPerformed
    private void bloquearCamposUsuario() {
        nombreUsuMOD.setEnabled(false);
        apellidoUsuMOD.setEnabled(false);
        nickUsuMOD.setEnabled(false);
        telefonoUsuMOD.setEnabled(false);
        direccionUsuMOD.setEnabled(false);
        emailUsuMOD.setEnabled(false);
        passwordUsuMOD.setEnabled(false);
        fotoUsuMOD1.setEnabled(false);
    }

    private void desbloquearCamposUsuario() {
        nombreUsuMOD.setEnabled(true);
        apellidoUsuMOD.setEnabled(true);
        nickUsuMOD.setEnabled(true);
        telefonoUsuMOD.setEnabled(true);
        direccionUsuMOD.setEnabled(true);
        emailUsuMOD.setEnabled(true);
        passwordUsuMOD.setEnabled(true);
        fotoUsuMOD1.setEnabled(true);
    }

    private String getCategoria() {
        return categoria;
    }

    private void setDireccionImagen(String direccion) {
        direcImagen = direccion;
    }

    private String getDireccionImagen() {
        return direcImagen;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new Administrador().setVisible(true);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptarProdCON;
    private javax.swing.JTextField apellidoUsuAL;
    private javax.swing.JTextField apellidoUsuMOD;
    private javax.swing.JLabel apellidoUsuarioBaja;
    private javax.swing.JButton atrasProdBA;
    private javax.swing.JButton buscarProdBA;
    private javax.swing.JButton buscarProdMOD;
    private javax.swing.JButton buscarUsuMOD;
    private javax.swing.JButton buscarUsuarioBaja;
    private javax.swing.JButton cancelarProdAL;
    private javax.swing.JButton cancelarProdMOD;
    private javax.swing.JButton cancelarUsuAL;
    private javax.swing.JButton cancelarUsuMOD;
    private javax.swing.JSpinner cantidadProdAL;
    private javax.swing.JSpinner cantidadProdMOD;
    private javax.swing.JComboBox<String> categoriaProdAL;
    private javax.swing.JComboBox<String> categoriaProdMOD;
    private javax.swing.JButton cerrarSesionVentas;
    private javax.swing.JLabel contEliminarProd1;
    private javax.swing.JLabel contEliminarProd2;
    private javax.swing.JLabel contEliminarProd3;
    private javax.swing.JLabel contEliminarProd4;
    private javax.swing.JLabel contEliminarProd5;
    private javax.swing.JLabel contEliminarProd6;
    private javax.swing.JTextField direccionUsuAL;
    private javax.swing.JTextField direccionUsuMOD;
    private javax.swing.JButton eliminarUsuarioBtn;
    private javax.swing.JButton email;
    private javax.swing.JTextField emailUsuAL;
    private javax.swing.JTextField emailUsuMOD;
    private javax.swing.JLabel etiquetaNomProdAL;
    private javax.swing.JButton fotoUsuAL;
    private javax.swing.JLabel fotoUsuAL2;
    private javax.swing.JButton fotoUsuMOD1;
    private javax.swing.JLabel fotoUsuMOD2;
    private javax.swing.JLabel fotoUsuarioBaja;
    private javax.swing.JButton guardarProdAL;
    private javax.swing.JButton guardarProdMOD;
    private javax.swing.JButton guardarUsuAL;
    private javax.swing.JButton guardarUsuMOD;
    private javax.swing.JButton imagenProd1;
    private javax.swing.JLabel imagenProdAL2;
    private javax.swing.JLabel imagenProdCON;
    private javax.swing.JButton imagenProdMOD;
    private javax.swing.JLabel imagenProdMOD2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField nickUsuAL;
    private javax.swing.JTextField nickUsuMOD;
    private javax.swing.JTextField nombreProdAL;
    private javax.swing.JTextField nombreProdBA;
    private javax.swing.JTextField nombreProdCON;
    private javax.swing.JTextField nombreProdMOD;
    private javax.swing.JTextField nombreUsuAL;
    private javax.swing.JTextField nombreUsuMOD;
    private javax.swing.JLabel nombreUsuarioBaja;
    private javax.swing.JButton opcionEliminarProd1;
    private javax.swing.JButton opcionEliminarProd2;
    private javax.swing.JButton opcionEliminarProd3;
    private javax.swing.JButton opcionEliminarProd4;
    private javax.swing.JButton opcionEliminarProd5;
    private javax.swing.JButton opcionEliminarProd6;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPasswordField passwordUsuAL;
    private javax.swing.JTextField passwordUsuMOD;
    private javax.swing.JTextField precioProdAL;
    private javax.swing.JTextField precioProdMOD;
    private javax.swing.JPanel productoAltaGral;
    private javax.swing.JPanel productoBajaGral;
    private javax.swing.JPanel productoConsultarGral;
    private javax.swing.JTabbedPane productoGeneral;
    private javax.swing.JPanel productoModificarGral;
    private javax.swing.JButton realizarReporteVentas;
    private javax.swing.JButton siguienteProdBA;
    private javax.swing.JTable tablaCON;
    private javax.swing.JTextField telefonoUsuAL;
    private javax.swing.JTextField telefonoUsuMOD;
    private javax.swing.JPanel usuarioAltaGral;
    private javax.swing.JPanel usuarioBajaGral;
    private javax.swing.JPanel usuarioModificarGral;
    private javax.swing.JTabbedPane usuariosGeneral;
    private javax.swing.JPanel ventasGeneral;
    // End of variables declaration//GEN-END:variables

}
