import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModificaBBDD {
    public static void main(String[] args) {

        try {
            Connection miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/Pruebas", "root", "root");
            Statement miStatement = miConexion.createStatement();

            ResultSet miResultset = miStatement.executeQuery("SELECT * FROM PRODUCTOS");

            String instruccionSql = "UPDATE PRODUCTOS SET PRECIO= 18 WHERE CODIGO = 15";

            miStatement.executeUpdate(instruccionSql);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
