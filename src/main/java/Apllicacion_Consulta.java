import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Apllicacion_Consulta {
    public static void main(String[] args) {
        MarcoAplicaion miMarco = new MarcoAplicaion();
        miMarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        miMarco.setVisible(true);

    }
}

class MarcoAplicaion extends JFrame {
    public MarcoAplicaion() {
        setTitle("Consulta BBDD");
        setBounds(500, 300, 400, 400);
        setLayout(new BorderLayout());
        JPanel menus = new JPanel();
        menus.setLayout(new FlowLayout());
        secciones = new JComboBox();
        secciones.setEditable(false);
        secciones.addItem("Todos");

        paises = new JComboBox<>();
        paises.setEditable(false);
        paises.addItem("Todos");

        resultado = new JTextArea(4, 50);
        resultado.setEditable(false);
        add(resultado);

        menus.add(secciones);
        menus.add(paises);

        add(menus, BorderLayout.NORTH);
        add(resultado, BorderLayout.CENTER);

        JButton botonConsulta = new JButton("Consulta");

        botonConsulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutaConsulta();
            }
        });
        add(botonConsulta, BorderLayout.SOUTH);

        try {

            miConexion = DriverManager.getConnection("jdbc:mysql://localhost/pruebas", "root", "root");
            Statement sentencia = miConexion.createStatement();
            String consulta = "SELECT DISTINCTROW seccion FROM PRODUCTOS";

            ResultSet rs = sentencia.executeQuery(consulta);

            while (rs.next()){
                secciones.addItem(rs.getString("Seccion"));
            }
            rs.close();

            consulta="SELECT DISTINCTROW ORIGEN FROM PRODUCTOS";
            rs=sentencia.executeQuery(consulta);

            while (rs.next()){
                paises.addItem(rs.getString("Origen"));
            }
            rs.close();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void ejecutaConsulta(){
        ResultSet rs = null;
        String seccion = (String)secciones.getSelectedItem();
        String pais = (String)paises.getSelectedItem();
        try {

            resultado.setText("");
            if(secciones.getSelectedItem()!="Todos" && paises.getSelectedItem()=="Todos") {
                enviaConsultaSeccion = miConexion.prepareStatement(consutaSeccion);
                enviaConsultaSeccion.setString(1, seccion);
                rs = enviaConsultaSeccion.executeQuery();
            } else if (secciones.getSelectedItem()=="Todos" && paises.getSelectedItem()!="Todos") {
                enviaConsultaPais= miConexion.prepareStatement(consutaPais);
                enviaConsultaPais.setString(1, pais);
                rs = enviaConsultaPais.executeQuery();
            } else if (secciones.getSelectedItem()!="Todos" && paises.getSelectedItem()!="Todos") {
                enviaConsultaTodos=miConexion.prepareStatement(consutaTodos);
                enviaConsultaTodos.setString(1, seccion);
                enviaConsultaTodos.setString(2, pais);
                rs = enviaConsultaTodos.executeQuery();

            }

            while (rs.next()){
                resultado.append(rs.getString(1));
                resultado.append(", ");
                resultado.append(rs.getString(2));
                resultado.append(", ");
                resultado.append(rs.getString(3));
                resultado.append(", ");
                resultado.append(rs.getString(4));
                resultado.append(", ");
                resultado.append(rs.getString(6));
                resultado.append("\n");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection miConexion;

    private PreparedStatement enviaConsultaSeccion;
    private PreparedStatement enviaConsultaPais;
    private PreparedStatement enviaConsultaTodos;

    private final String consutaSeccion = "SELECT * FROM PRODUCTOS WHERE SECCION = ?";
    private final String consutaPais = "SELECT * FROM PRODUCTOS WHERE ORIGEN = ?";
    private final String consutaTodos = "SELECT * FROM PRODUCTOS WHERE SECCION = ? AND ORIGEN =?" ;

    private JComboBox secciones;
    private JComboBox paises;
    private JTextArea resultado;
}

