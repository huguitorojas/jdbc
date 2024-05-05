import java.sql.*;

public class Conecta_Pruebas {
    public static void main(String[] args) {

        try {
            Connection miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/Pruebas","root", "root");
            Statement miStatement = miConexion.createStatement();

            ResultSet miResultset = miStatement.executeQuery( "SELECT * FROM PRODUCTOS");

            while(miResultset.next()){
                System.out.println(miResultset.getString("Nombre") +
                                    " " + miResultset.getString("Codigo") +
                                    " " + miResultset.getString("Precio") +
                                    " " + miResultset.getString("Descripcion"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
