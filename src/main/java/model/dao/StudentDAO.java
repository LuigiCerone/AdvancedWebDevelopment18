package model.dao;

import model.dao.inter.StudentDAO_Interface;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentDAO implements StudentDAO_Interface{
    @Resource(name = "jdbc/UsersDB")
    private DataSource dataSource;

    public StudentDAO(){
    }

    @Override
    public boolean checkLogin(String email, String password) {
        String query = "SELECT * FROM student WHERE student.email = ? AND student.password = ?;";
        PreparedStatement preparedStatement;
        try (Connection conn = dataSource.getConnection()){
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.executeQuery();

            // TODO Controllare che c'è risultato.

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//
//    protected void doGet(HttpServletRequest request,
//                         HttpServletResponse response) throws ServletException, IOException {
//        PrintWriter writer = response.getWriter();
//        try {
//            Connection conn = dataSource.getConnection();
//
//            Statement statement = conn.createStatement();
//            String sql = "select username, email from users";
//            ResultSet rs = statement.executeQuery(sql);
//
//            int count = 1;
//
//            while (rs.next()) {
//                writer.println(String.format("User #%d: %-15s %s", count++,
//                        rs.getString("username"), rs.getString("email")));
//
//            }
//        } catch (SQLException ex) {
//            System.err.println(ex);
//        }
//    }

}
