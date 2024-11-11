
package proyecto;

import clases.ConexionMySQL;
import clases.Proyectos;

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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;


public class AppProyecto extends JFrame implements ActionListener {
    
    private JLabel lbl_titulo, lbl_1, lbl_2, lbl_3, lbl_4, lbl_5, lbl_6, lbl_7, lbl_8, lbl_filtro;
    private JTextField txt_codproy, txt_proy, txt_desc, txt_filtro;

    private JDateChooser dc_fini, dc_ffin;

    private JComboBox cbo_tipo, cbo_estado, cbo_usuario;

    private JButton btn_nuevo, btn_agregar, btn_editar, btn_borrar, btn_cerrar, btn_filtrar;

    private JTable tb_proyectos;
    private JScrollPane scr_proyectos;

    private final ConexionMySQL cn = new ConexionMySQL();

    private ArrayList<String[]> arr_tipo, arr_estado, arr_usuario;

    public AppProyecto() {
        super();

        IniciarFormulario();
        IniciarControles();
        //LimpiarDatos();
        MostrarDatos();
    }

    private void IniciarFormulario() {
        this.setTitle("TGH GESTIÓN DE PROYECTOS");
        this.setSize(430, 570);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void IniciarControles() {
        lbl_titulo = new JLabel();
        lbl_titulo.setText("GESTIÓN DE PROYECTOS");
        lbl_titulo.setFont(new Font("Calibri", Font.BOLD, 24));
        lbl_titulo.setForeground(new Color(0,255,0));
        lbl_titulo.setBounds(10, 15, 300, 25);

        lbl_1 = new JLabel();
        lbl_1.setText("Código Proyecto");
        lbl_1.setBounds(20, 50, 120, 25);

        txt_codproy = new JTextField();
        txt_codproy.setHorizontalAlignment(JTextField.CENTER);
        txt_codproy.setBounds(150, 50, 80, 25);

        lbl_2 = new JLabel();
        lbl_2.setText("Proyecto");
        lbl_2.setBounds(20, 80, 110, 25);

        txt_proy = new JTextField();
        txt_proy.setBounds(20, 110, 110, 25);

        lbl_3 = new JLabel();
        lbl_3.setText("Descripción");
        lbl_3.setBounds(150, 80, 110, 25);

        txt_desc = new JTextField();
        txt_desc.setBounds(150, 110, 110, 25);

        lbl_4 = new JLabel();
        lbl_4.setText("Fecha Inicio");
        lbl_4.setBounds(20, 170, 110, 25);

        dc_fini = new JDateChooser();
        dc_fini.setDateFormatString("dd/MM/yyyy");
        dc_fini.setBounds(20, 200, 110, 25);

        lbl_5 = new JLabel();
        lbl_5.setText("Fecha Fin");
        lbl_5.setBounds(20, 170, 110, 25);

        dc_ffin = new JDateChooser();
        dc_ffin.setDateFormatString("dd/MM/yyyy");
        dc_ffin.setBounds(20, 200, 110, 25);
        
        lbl_6 = new JLabel();
        lbl_6.setText("Tipo");
        lbl_6.setBounds(280, 170, 110, 25);

        cbo_tipo = new JComboBox();
        cbo_tipo.setBounds(280, 200, 110, 25);

        arr_tipo = this.ObtenerTipo();
        
        for (String[] tipo: arr_tipo) {
            cbo_tipo.addItem(tipo[1]);
        }

	lbl_7 = new JLabel();
        lbl_7.setText("Estado");
        lbl_7.setBounds(280, 170, 110, 25);

        cbo_estado = new JComboBox();
        cbo_estado.setBounds(280, 200, 110, 25);

        arr_estado = this.ObtenerEstado();
        
        for (String[] estado: arr_estado) {
            cbo_estado.addItem(estado[1]);
        }

	lbl_8 = new JLabel();
        lbl_8.setText("Responsable");
        lbl_8.setBounds(280, 170, 110, 25);

        cbo_usuario = new JComboBox();
        cbo_usuario.setBounds(280, 200, 110, 25);

        arr_tipo = this.ObtenerUsuario();
        
        for (String[] usuario: arr_usuario) {
            cbo_usuario.addItem(usuario[1]);
        }


        btn_nuevo = new JButton();
        btn_nuevo.setText("Nuevo");
        btn_nuevo.setBounds(10, 240, 90, 25);
        btn_nuevo.addActionListener(this);

        btn_agregar = new JButton();
        btn_agregar.setText("Agregar");
        btn_agregar.setBounds(110, 240, 90, 25);
        btn_agregar.addActionListener(this);

        btn_editar = new JButton();
        btn_editar.setText("Editar");
        btn_editar.setBounds(210, 240, 90, 25);
        btn_editar.addActionListener(this);

        btn_borrar = new JButton();
        btn_borrar.setText("Borrar");
        btn_borrar.setBounds(310, 240, 90, 25);
        btn_borrar.addActionListener(this);

        lbl_filtro = new JLabel("Buscar:");
        lbl_filtro.setBounds(20, 450, 60, 25);
        
        txt_filtro = new JTextField();
        txt_filtro.setBounds(80, 450, 200, 25);

        btn_filtrar = new JButton("Filtrar");
        btn_filtrar.setBounds(290, 450, 80, 24);
        btn_filtrar.addActionListener(this);
        
        btn_cerrar = new JButton();
        btn_cerrar.setText("Cerrar");
        btn_cerrar.setFont(new Font("Consolas", Font.BOLD, 14));
        btn_cerrar.setBackground(Color.RED);
        btn_cerrar.setForeground(Color.WHITE);
        btn_cerrar.setBounds(110, 485, 180, 30);
        btn_cerrar.addActionListener(this);
        

        tb_proyectos = new JTable();

        tb_proyectos.setRowHeight(20);
        tb_proyectos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        scr_proyectos = new JScrollPane(tb_proyectos);
        scr_proyectos.setBounds(10, 280, 390, 150);

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
        this.add(scr_proyectos);
        this.add(lbl_filtro);
        this.add(txt_filtro);
        this.add(btn_filtrar);

        ControladorTxt ctxt = new ControladorTxt();

        txt_codproy.addKeyListener(ctxt);
        txt_proy.addKeyListener(ctxt);
        txt_desc.addKeyListener(ctxt);
        txt_filtro.addKeyListener(ctxt);

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
            
            int nc = rs.getMetaData().getColumnCount();

            for (int i = 1; i <= nc; i++) {
                modelo.addColumn(rs.getMetaData().getColumnName(i));
            }

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
        
        arr_col.getColumn(0).setPreferredWidth(50);
        arr_col.getColumn(0).setCellRenderer(alinearCentro);
        
        arr_col.getColumn(1).setPreferredWidth(120);
    }

    private class ControladorTxt implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getSource() == txt_codproy && txt_codproy.getText().length() == 5) {
                e.consume();
            }  else if ((e.getSource() == txt_proy || e.getSource() == txt_desc) && 
                   ((JTextField) e.getSource()).getText().length() >= 25) {
            e.consume(); 
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
                    String proy = rs.getString("proyecto");
                    String des = rs.getString("descripcion"); 
                    String cod_tip = rs.getString("proyecto_codigo_tipo"); 
                    String cod_est = rs.getString("proyecto_codigo_estado"); 
                    String cod_usr = rs.getString("proyecto_codigo_responsable"); 
                    
                    
                    Date fini = rs.getDate("fecha_inicio");
                    Date ffin = rs.getDate("fecha_fin");

                    
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

               
            }
            rs.close();
            pstm.close();
            cnx.close();
            } catch (SQLException ex) {
                
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
