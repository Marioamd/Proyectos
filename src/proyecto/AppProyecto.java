
package proyecto;

import clases.ConexionMySQL;
import clases.Proyecto;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;


public class AppProyecto extends JFrame implements ActionListener {
    
    private JLabel lbl_titulo, lbl_1, lbl_2, lbl_3, lbl_4, lbl_5, lbl_6, lbl_7, lbl_8, lbl_filtro;
    private JTextField txt_codproy, txt_proy, txt_filtro;
    
    private JTextArea txt_desc; 

    private JDateChooser dc_fini, dc_ffin;

    private JComboBox cbo_tipo, cbo_estado, cbo_usuario;

    private JButton btn_nuevo, btn_agregar, btn_editar, btn_borrar, btn_cerrar, btn_filtrar, btn_mostrar_registro;

    private JTable tb_proyectos;
    private JScrollPane scr_proyectos;

    private final ConexionMySQL cn = new ConexionMySQL();

    private ArrayList<String[]> arr_tipo, arr_estado, arr_usuario;

    public AppProyecto() {
        super();

        IniciarFormulario();
        IniciarControles();
        LimpiarDatos();
        MostrarDatos();
    }

    private void IniciarFormulario() {
        this.setTitle("TGH GESTIÓN DE PROYECTOS");
        this.setSize(605, 680);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

private void IniciarControles() {
    lbl_titulo = new JLabel("GESTIÓN DE PROYECTOS");
    lbl_titulo.setFont(new Font("Tahoma", Font.BOLD, 22));
    lbl_titulo.setForeground(new Color(0, 191, 168));
    lbl_titulo.setBounds(20, 20, 300, 25);

    lbl_1 = new JLabel("Código Proyecto:");
    lbl_1.setBounds(20, 60, 120, 25);

    txt_codproy = new JTextField();
    txt_codproy.setHorizontalAlignment(JTextField.CENTER);
    txt_codproy.setBounds(130, 60, 120, 25);

    lbl_2 = new JLabel("Proyecto:");
    lbl_2.setBounds(320, 60, 95, 25);

    txt_proy = new JTextField();
    txt_proy.setBounds(390, 60, 180, 25);
    

    lbl_3 = new JLabel("Descripción del proyecto:");
    lbl_3.setBounds(20, 95, 220, 25);

    txt_desc = new JTextArea();
    txt_desc.setBounds(20, 120, 260, 50);
    txt_desc.setLineWrap(true); 
    txt_desc.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); 

    lbl_4 = new JLabel("Fecha de inicio:");
    lbl_4.setBounds(320, 115, 110, 25);

    dc_fini = new JDateChooser();
    dc_fini.setDateFormatString("dd/MM/yyyy");
    dc_fini.setBounds(320, 140, 110, 25);

    lbl_5 = new JLabel("Fecha de fin:");
    lbl_5.setBounds(460, 115, 110, 25);

    dc_ffin = new JDateChooser();
    dc_ffin.setDateFormatString("dd/MM/yyyy");
    dc_ffin.setBounds(460, 140, 110, 25);

    lbl_6 = new JLabel("Tipo de proyecto:");
    lbl_6.setBounds(20, 190, 110, 25);

    cbo_tipo = new JComboBox();
    cbo_tipo.setBounds(130, 190, 150, 25);
    arr_tipo = this.ObtenerTipo();
    for (String[] tipo : arr_tipo) {
        cbo_tipo.addItem(tipo[1]);
    }

    lbl_7 = new JLabel("Estado:");
    lbl_7.setBounds(320, 190, 110, 25);

    cbo_estado = new JComboBox();
    cbo_estado.setBounds(400, 190, 150, 25);
    arr_estado = this.ObtenerEstado();
    for (String[] estado : arr_estado) {
        cbo_estado.addItem(estado[1]);
    }

    lbl_8 = new JLabel("Responsable:");
    lbl_8.setBounds(20, 235, 140, 25);

    cbo_usuario = new JComboBox();
    cbo_usuario.setBounds(130, 235, 150, 25);
    arr_usuario = this.ObtenerUsuario();
    for (String[] usuario : arr_usuario) {
        cbo_usuario.addItem(usuario[1]);
    }

    btn_nuevo = new JButton("Nuevo");
    btn_nuevo.setFont(new Font("Tahoma", Font.BOLD, 12));
    btn_nuevo.setBackground(new Color(93, 54, 196 ));
    btn_nuevo.setForeground(Color.WHITE);
    btn_nuevo.setBounds(42, 285, 100, 30);
    btn_nuevo.addActionListener(this);

    btn_agregar = new JButton("Agregar");
    btn_agregar.setFont(new Font("Tahoma", Font.BOLD, 12));
    btn_agregar.setBackground(new Color(54, 196, 108 ));
    btn_agregar.setForeground(Color.WHITE);
    btn_agregar.setBounds(177, 285, 100, 30);
    btn_agregar.addActionListener(this);

    btn_editar = new JButton("Editar");
    btn_editar.setFont(new Font("Tahoma", Font.BOLD, 12));
    btn_editar.setBackground(new Color(196, 164, 54));
    btn_editar.setForeground(Color.WHITE);
    btn_editar.setBounds(312, 285, 100, 30);
    btn_editar.addActionListener(this);

    btn_borrar = new JButton("Borrar");
    btn_borrar.setFont(new Font("Tahoma", Font.BOLD, 12));
    btn_borrar.setBackground(new Color(196, 54, 54  ));
    btn_borrar.setForeground(Color.WHITE);
    btn_borrar.setBounds(447, 285, 100, 30);
    btn_borrar.addActionListener(this);

    btn_cerrar = new JButton("Cerrar");
    btn_cerrar.setFont(new Font("Tahoma", Font.BOLD, 14));
    btn_cerrar.setBackground(new Color(0, 191, 168));
    btn_cerrar.setForeground(Color.WHITE);
    btn_cerrar.setBounds(450, 590, 120, 30);
    btn_cerrar.addActionListener(this);

    lbl_filtro = new JLabel("Buscar:");
    lbl_filtro.setBounds(42, 335, 60, 25);

    txt_filtro = new JTextField();
    txt_filtro.setBounds(112, 335, 240, 25);

    btn_filtrar = new JButton("Filtrar");
    btn_filtrar.setBounds(370, 335, 80, 25);
    btn_filtrar.addActionListener(e -> {
    String proyecto = txt_filtro.getText().trim();
    if (!proyecto.isEmpty()) {
        Filtrar(proyecto);
    } else {
        JOptionPane.showMessageDialog(
            null,
            "Debe ingresar un valor para filtrar.",
            "TGH TECHNOLOGY SOLUTION",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
});


    btn_mostrar_registro = new JButton("Limpiar");
    btn_mostrar_registro.setBounds(468, 335, 80, 25);
    btn_mostrar_registro.addActionListener(e -> {
        MostrarDatos();          
        txt_filtro.setText("");  
    });
    this.add(btn_mostrar_registro);


    tb_proyectos = new JTable();
    tb_proyectos.setRowHeight(20);
    tb_proyectos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

    scr_proyectos = new JScrollPane(tb_proyectos);
    scr_proyectos.setBounds(10, 375, 570, 200);



        this.add(lbl_titulo);
        this.add(lbl_1);
        this.add(txt_codproy);
        this.add(lbl_2);
        this.add(txt_proy);
        this.add(lbl_3);
        this.add(txt_desc);
        this.add(lbl_4);
        this.add(dc_fini);
        this.add(lbl_5);
        this.add(dc_ffin);
        this.add(lbl_6);
        this.add(cbo_tipo);
        this.add(lbl_7);
        this.add(cbo_estado);
        this.add(lbl_8);
        this.add(cbo_usuario);
        this.add(btn_nuevo);
        this.add(btn_agregar);
        this.add(btn_editar);
        this.add(btn_borrar);
        this.add(btn_cerrar);
        this.add(lbl_filtro);
        this.add(txt_filtro);
        this.add(btn_filtrar);
        this.add(btn_mostrar_registro);
        this.add(scr_proyectos);


        ControladorTxt ctxt = new ControladorTxt();

        txt_codproy.addKeyListener(ctxt);
        txt_proy.addKeyListener(ctxt);
        txt_desc.addKeyListener(ctxt);

        ControladorClick click = new ControladorClick();

        tb_proyectos.addMouseListener(click);
    }

    private ArrayList<String[]> ObtenerTipo() {
        ArrayList<String[]> arr_lista = new ArrayList<>();

        String cad_sql = "call sp_listar_tipos();";
        
        Connection cnx;

        try {
            cnx = cn.Conectar();

            java.sql.PreparedStatement pstm;
            pstm = cnx.prepareStatement(cad_sql);

            try (ResultSet rs = pstm.executeQuery()) {

                arr_lista.add(new String[]{"", "Seleccione tipo"});

                while (rs.next()) {
                    String codigo = rs.getString("codigo_tipo");
                    String tipo = rs.getString("tipo");
                    
                    arr_lista.add(new String[]{codigo, tipo});
                }
            }

            pstm.close();
            cnx.close();
        } catch (SQLException ex) {
        }
        
        return arr_lista;
    }

	private ArrayList<String[]> ObtenerEstado() {
        ArrayList<String[]> arr_lista = new ArrayList<>();

        String cad_sql = "call sp_listar_estados();";
        
        Connection cnx;

        try {
            cnx = cn.Conectar();

            java.sql.PreparedStatement pstm;
            pstm = cnx.prepareStatement(cad_sql);

            try (ResultSet rs = pstm.executeQuery()) {

                arr_lista.add(new String[]{"", "Seleccione estado"});

                while (rs.next()) {
                    String codigo = rs.getString("codigo_estado");
                    String estado = rs.getString("estado");
                    
                    arr_lista.add(new String[]{codigo, estado});
                }
            }

            pstm.close();
            cnx.close();
        } catch (SQLException ex) {
        }
        
        return arr_lista;
    }

	private ArrayList<String[]> ObtenerUsuario() {
        ArrayList<String[]> arr_lista = new ArrayList<>();

        String cad_sql = "call sp_listar_usuarios();";
        
        Connection cnx;

        try {
            cnx = cn.Conectar();

            java.sql.PreparedStatement pstm;
            pstm = cnx.prepareStatement(cad_sql);

            try (ResultSet rs = pstm.executeQuery()) {

                arr_lista.add(new String[]{"", "Seleccione usuario"});

                while (rs.next()) {
                    String codigo = rs.getString("codigo_usuario");
                    String usuario = rs.getString("usuario");
                    
                    arr_lista.add(new String[]{codigo, usuario});
                }
            }

            pstm.close();
            cnx.close();
        } catch (SQLException ex) {
        }
        
        return arr_lista;
    }

    private void MostrarDatos() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

      
        modelo.setRowCount(0);

        Connection cnx = null;
        Statement stm = null;
        ResultSet rs = null;

        try {
            cnx = cn.Conectar();
            stm = cnx.createStatement();
            
            rs = stm.executeQuery("call sp_listar_proyectos();");
            //rs = stm.executeQuery("{call sp_listar_proyectos()}"); //  para SQL Server
            
            int nc = rs.getMetaData().getColumnCount();

            modelo.addColumn("Código");
            modelo.addColumn("Proyecto");
            modelo.addColumn("Descripción");
            modelo.addColumn("Fecha Inicio");
            modelo.addColumn("Fecha Fin");
            modelo.addColumn("Responsable");


            while (rs.next()) {
                Object[] arr_filas = new Object[nc];

                for (int i = 0; i < nc; i++) {
                    arr_filas[i] = rs.getObject(i + 1);
                }

                modelo.addRow(arr_filas);
            }

        } catch (SQLException e1) {

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (cnx != null) {
                    cnx.close();
                }
            } catch (SQLException e2) {

            }
        }

        tb_proyectos.setModel(modelo);
        tb_proyectos.setRowHeight(22);
        
        DefaultTableCellRenderer alinearCentro = new DefaultTableCellRenderer();
        
        alinearCentro.setHorizontalAlignment(SwingConstants.CENTER);
        
        TableColumnModel arr_col = tb_proyectos.getColumnModel();
        
        if (arr_col.getColumnCount() > 0) {
        arr_col.getColumn(0).setPreferredWidth(50);
        arr_col.getColumn(0).setCellRenderer(alinearCentro);
        
        if (arr_col.getColumnCount() > 1) {
            arr_col.getColumn(1).setPreferredWidth(120);
            
        if (arr_col.getColumnCount() > 2) {
        arr_col.getColumn(2).setPreferredWidth(120);
        }
        }
    } else {
        System.out.println("No se han agregado columnas al modelo de tabla.");
    }
    }

    private class ControladorTxt implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

        if (e.getSource() == txt_codproy && txt_codproy.getText().length() == 5) {
            e.consume();
        }  

        else if (e.getSource() == txt_proy) {
            if (txt_proy.getText().length() >= 30) {
                e.consume();
            }
        } 
        else if (e.getSource() == txt_desc) {
            if (txt_desc.getText().length() >= 80) {
                e.consume();
            }
        }
    }



        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private class ControladorClick extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            int registro = tb_proyectos.getSelectedRow();
            System.out.println("Fila seleccionada: " + registro);  

            if (registro != -1) {  
                txt_codproy.setEditable(false);

                String codproy = (String) tb_proyectos.getValueAt(registro, 0);
                txt_codproy.setText(codproy);

                String cad_sql = "call sp_buscar_proyecto(?);";
                Connection cnx;

                try {
                    cnx = cn.Conectar();

                    java.sql.PreparedStatement pstm;
                    pstm = cnx.prepareStatement(cad_sql);
                    pstm.setString(1, codproy);

                    ResultSet rs = pstm.executeQuery();

                    if (rs.next()) {
                        System.out.println("Datos encontrados para el código de proyecto: " + codproy);

                        String proy = rs.getString("proyecto");
                        String des = rs.getString("descripcion"); 
                        Date fini = rs.getDate("fecha_inicio");
                        Date ffin = rs.getDate("fecha_fin");
                        String cod_tip = rs.getString("proyecto_codigo_tipo"); 
                        String cod_est = rs.getString("proyecto_codigo_estado"); 
                        String cod_usr = rs.getString("proyecto_codigo_responsable");

                        txt_proy.setText(proy);
                        txt_desc.setText(des);
                        dc_fini.setDate(fini);
                        dc_ffin.setDate(ffin);

                        for (int i = 0; i < cbo_tipo.getItemCount(); i++) {
                            if (arr_tipo.get(i)[0].equals(cod_tip)) {
                                cbo_tipo.setSelectedIndex(i);
                                break;
                            }
                        } 

                        for (int i = 0; i < cbo_estado.getItemCount(); i++) {
                            if (arr_estado.get(i)[0].equals(cod_est)) {
                                cbo_estado.setSelectedIndex(i);
                                break;
                            }
                        } 

                        for (int i = 0; i < cbo_usuario.getItemCount(); i++) {
                            if (arr_usuario.get(i)[0].equals(cod_usr)) {
                                cbo_usuario.setSelectedIndex(i);
                                break;
                            }
                        }
                    } else {
                        System.out.println("No se encontraron datos para el código de proyecto: " + codproy);
                    }

                    rs.close();
                    pstm.close();
                    cnx.close();
                } catch (SQLException ex) {
                    ex.printStackTrace(); 
                }
            }
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btn_cerrar) {
        dispose();
        return;
    }
        if (e.getSource() == btn_nuevo) {
            LimpiarDatos();
        } else if (e.getSource() == btn_mostrar_registro) {
            MostrarDatos();
        } else {
            if (e.getSource() != btn_borrar) {
                if (txt_codproy.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingrese código de proyecto.", "TGH TECHNOLOGY SOLUTION", JOptionPane.WARNING_MESSAGE);
                    txt_codproy.requestFocus();
                    return;
                }
                if (txt_proy.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingrese nombre del proyecto.", "TGH TECHNOLOGY SOLUTION", JOptionPane.WARNING_MESSAGE);
                    txt_proy.requestFocus();
                    return;
                }
                if (txt_desc.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingrese descripción del proyecto.", "TGH TECHNOLOGY SOLUTION", JOptionPane.WARNING_MESSAGE);
                    txt_desc.requestFocus();
                    return;
                }
                if (dc_fini.getDate() == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione la fecha de inicio del proyecto.", "TGH TECHNOLOGY SOLUTION", JOptionPane.WARNING_MESSAGE);
                    dc_fini.requestFocus();
                    return;
                }

                Date fechaInicio = dc_fini.getDate();
                Date fechaActual = new Date();

                if (fechaInicio.after(fechaActual)) {
                    JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser posterior a la fecha actual.", "TGH TECHNOLOGY SOLUTION", JOptionPane.WARNING_MESSAGE);
                    dc_fini.requestFocus();
                    return;
                }

                if (dc_ffin.getDate() == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione la fecha de cierre del proyecto.", "TGH TECHNOLOGY SOLUTION", JOptionPane.WARNING_MESSAGE);
                    dc_ffin.requestFocus();
                    return;
                }

                Date fechaFin = dc_ffin.getDate();

                if (fechaFin.before(fechaInicio)) {
                    JOptionPane.showMessageDialog(null, "La fecha de cierre no puede ser anterior a la fecha de inicio.", "TGH TECHNOLOGY SOLUTION", JOptionPane.WARNING_MESSAGE);
                    dc_ffin.requestFocus();
                    return;
                }

                if (cbo_tipo.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Seleccione un tipo de proyecto.", "TGH TECHNOLOGY SOLUTION", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (cbo_estado.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Seleccione un estado para el proyecto.", "TGH TECHNOLOGY SOLUTION", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (cbo_usuario.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Seleccione un responsable de proyecto.", "TGH TECHNOLOGY SOLUTION", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
        


        Proyecto proyecto = new Proyecto();
        proyecto.setCodigo_proyecto(txt_codproy.getText());
        proyecto.setProyecto(txt_proy.getText());
        proyecto.setDescripcion(txt_desc.getText());

        
        Date fini_1 = dc_fini.getDate();
        long fini_2 = fini_1.getTime();
        java.sql.Date fini_3 = new java.sql.Date(fini_2);

        proyecto.setFecha_inicio(fini_3);

        Date ffin_1 = dc_ffin.getDate();
        long ffin_2 = ffin_1.getTime();
        java.sql.Date ffin_3 = new java.sql.Date(ffin_2);

        proyecto.setFecha_fin(ffin_3);


        String ct = "", nt = cbo_tipo.getSelectedItem().toString();

        for (String[] tipo: arr_tipo) {
            if (nt.equals(tipo[1])) {
                ct = tipo[0];
                break;
            }
        }

        proyecto.setProyecto_codigo_tipo(ct);


        String ce = "", ne = cbo_estado.getSelectedItem().toString();

        for (String[] estado: arr_estado) {
            if (ne.equals(estado[1])) {
                ce = estado[0];
                break;
            }
        }

        proyecto.setProyecto_codigo_estado(ce);


        String cu = "", nu = cbo_usuario.getSelectedItem().toString();

        for (String[] usuario: arr_usuario) {
            if (nu.equals(usuario[1])) {
                cu = usuario[0];
                break;
            }
        }

        proyecto.setProyecto_codigo_responsable(cu);

        Connection cnx = null;
        java.sql.PreparedStatement pstm = null;

        try {
            cnx = cn.Conectar();
            String cad_sql;

            if (e.getSource() == btn_agregar) {
                cad_sql = "call sp_registrar_proyecto(?,?,?,?,?,?,?,?);";
                 //cad_sql = "{call sp_registrar_proyecto(?,?,?,?,?,?,?,?)}"; // SQL
                pstm = cnx.prepareStatement(cad_sql);
                pstm.setString(1, proyecto.getCodigo_proyecto());
                pstm.setString(2, proyecto.getProyecto());
                pstm.setString(3, proyecto.getDescripcion());
                pstm.setDate(4, (java.sql.Date) proyecto.getFecha_inicio());
                pstm.setDate(5, (java.sql.Date) proyecto.getFecha_fin());
                pstm.setString(6, proyecto.getProyecto_codigo_tipo());
                pstm.setString(7, proyecto.getProyecto_codigo_estado());
                pstm.setString(8, proyecto.getProyecto_codigo_responsable());
                pstm.executeUpdate();
                JOptionPane.showMessageDialog(null, "Proyecto registrado con éxito.");

            } else if (e.getSource() == btn_editar) {
                
                cad_sql = "call sp_editar_proyecto(?,?,?,?,?,?,?,?);";
                pstm = cnx.prepareStatement(cad_sql);
                pstm.setString(1, proyecto.getCodigo_proyecto());
                pstm.setString(2, proyecto.getProyecto());
                pstm.setString(3, proyecto.getDescripcion());
                pstm.setDate(4, (java.sql.Date) proyecto.getFecha_inicio());
                pstm.setDate(5, (java.sql.Date) proyecto.getFecha_fin());
                pstm.setString(6, proyecto.getProyecto_codigo_tipo());
                pstm.setString(7, proyecto.getProyecto_codigo_estado());
                pstm.setString(8, proyecto.getProyecto_codigo_responsable());
                pstm.executeUpdate();
                JOptionPane.showMessageDialog(null, "Proyecto actualizado con éxito.");


            } else if (e.getSource() == btn_borrar) {
                int opc = JOptionPane.showConfirmDialog(null,
                        "¿Seguro de borrar el registro?",
                        "TGH TECHNOLOGY SOLUTION", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (opc == JOptionPane.YES_OPTION) {
                    cad_sql = "call sp_borrar_proyecto(?);";
                    pstm = cnx.prepareStatement(cad_sql);
                    pstm.setString(1, proyecto.getCodigo_proyecto());
                    pstm.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Proyecto eliminado.");
                }

            }else if (e.getSource() == btn_filtrar){
                cad_sql =  "call sp_filtrar_proyecto(?);";
                pstm = cnx.prepareStatement(cad_sql);
                pstm.setString(1, proyecto.getProyecto());
            }

            MostrarDatos();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (pstm != null) pstm.close();
                if (cnx != null) cnx.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }

        LimpiarDatos();
    }
}

    private void LimpiarDatos() {
    txt_codproy.setEditable(true);

    tb_proyectos.clearSelection();

    txt_codproy.setText("");
    txt_proy.setText("");
    txt_desc.setText("");

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Date fechaInicio;
    try {
        fechaInicio = sdf.parse("2000-01-01");
        dc_fini.setDate(fechaInicio);
    } catch (ParseException ex) {
        Logger.getLogger(AppProyecto.class.getName()).log(Level.SEVERE, "Error de fecha de inicio", ex);
    }

    Date fechaFin;
    try {
        fechaFin = sdf.parse("2000-12-31");
        dc_ffin.setDate(fechaFin);
    } catch (ParseException ex) {
        Logger.getLogger(AppProyecto.class.getName()).log(Level.SEVERE, "Error de fecha de fin", ex);
    }

    if (!arr_tipo.isEmpty()) {
        cbo_tipo.setSelectedIndex(0);
    }

    if (!arr_estado.isEmpty()) {
        cbo_estado.setSelectedIndex(0);
    }

    if (!arr_usuario.isEmpty()) {
        cbo_usuario.setSelectedIndex(0);
    }


    txt_codproy.requestFocus();

    }
    
    private void Filtrar(String proyecto) {
    DefaultTableModel modelo = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    };

    modelo.setRowCount(0);

    Connection cnx = null;
    java.sql.PreparedStatement pstm = null;
    ResultSet rs = null;

    try {
        cnx = cn.Conectar();
        
        String cad_sql = "call sp_filtrar_proyecto(?)";
        //String cad_sql = "{call sp_filtrar_proyecto(?)}"; // Para SQL server
        
        pstm = cnx.prepareStatement(cad_sql);
        pstm.setString(1, proyecto + "%");  

        rs = pstm.executeQuery();

        int nc = rs.getMetaData().getColumnCount();

            modelo.addColumn("Código");
            modelo.addColumn("Proyecto");
            modelo.addColumn("Descripción");
            modelo.addColumn("Fecha Inicio");
            modelo.addColumn("Fecha Fin");
            modelo.addColumn("Tipo");
            modelo.addColumn("Estado");
            modelo.addColumn("Responsable");

        while (rs.next()) {
            Object[] arr_filas = new Object[nc];

            for (int i = 0; i < nc; i++) {
                arr_filas[i] = rs.getObject(i + 1);
            }

            modelo.addRow(arr_filas);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al filtrar los datos.");
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstm != null) pstm.close();
            if (cnx != null) cnx.close();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }

    tb_proyectos.setModel(modelo);
    tb_proyectos.setRowHeight(22);

    DefaultTableCellRenderer alinearCentro = new DefaultTableCellRenderer();
    alinearCentro.setHorizontalAlignment(SwingConstants.CENTER);

    TableColumnModel arr_col = tb_proyectos.getColumnModel();
    arr_col.getColumn(0).setPreferredWidth(50);
    arr_col.getColumn(0).setCellRenderer(alinearCentro);

    arr_col.getColumn(1).setPreferredWidth(120);
}
    
    public static void main(String[] args) {
        AppProyecto frm = new AppProyecto();
        frm.setVisible(true);
    }        

}
