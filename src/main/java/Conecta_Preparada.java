import java.sql.*;
import java.util.MissingFormatArgumentException;

public class Conecta_Preparada {
    public static void main(String[] args) {

        try {
            Connection miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/Pruebas","root", "root");
            PreparedStatement miStatement = miConexion.prepareStatement("SELECT * FROM PRODUCTOS WHERE NOMBRE=?");

            miStatement.setString(1, "Cinta");

            ResultSet miResultset = miStatement.executeQuery();

            while(miResultset.next()){
                System.out.println(miResultset.getString("Nombre") +
                                    " " + miResultset.getString("Codigo") +
                                    " " + miResultset.getString("Precio") +
                                    " " + miResultset.getString("Descripcion"));
            }

            miStatement.setString(1, "pinza");

            miResultset = miStatement.executeQuery();

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
