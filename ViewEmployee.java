package employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/viewemp")
public class ViewEmployee extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Connection con;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:ORCL",
                "c##siba1",
                "qwer"
            );
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        pw.println("<!DOCTYPE html>");
        pw.println("<html lang='en'>");
        pw.println("<head>");
        pw.println("<meta charset='UTF-8'>");
        pw.println("<title>View Employee | Siba Sai EMS</title>");
        pw.println("<link rel='stylesheet' href='styleview.css'>");
        pw.println("</head>");
        pw.println("<body>");

        /* ================= TOP BAR ================= */
        pw.println("<div class='top-bar'>");
        pw.println("<div class='logo'>Siba Sai EMS</div>");
        pw.println("<div class='navbar'>");
        pw.println("<a href='index.html'>Registration</a>");
        pw.println("<a href='viewemp'>View Emp</a>");
        pw.println("<a href='about.html'>About</a>");
        pw.println("<a href='login.html'>Login</a>");
        pw.println("</div>");
        pw.println("</div>");

        /* ================= CONTENT ================= */
        pw.println("<div class='container'>");
        pw.println("<h2>Employee Details</h2>");

        pw.println("<table>");
        pw.println("<tr>");
        pw.println("<th>ID</th>");
        pw.println("<th>Full Name</th>");
        pw.println("<th>DOB</th>");
        pw.println("<th>Gender</th>");
        pw.println("<th>Designation</th>");
        pw.println("<th>Salary</th>");
        pw.println("<th>Address</th>");
        pw.println("</tr>");

        try (
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT * FROM EMPLOYEE ORDER BY EMP_ID"
            )
        ) {
            int serialNo = 1;
            while (rs.next()) {
                pw.println("<tr>");
                pw.println("<td>" + serialNo++ + "</td>");
                pw.println("<td>" + rs.getString("FULL_NAME") + "</td>");
                pw.println("<td>" + rs.getDate("DOB") + "</td>");
                pw.println("<td>" + rs.getString("GENDER") + "</td>");
                pw.println("<td>" + rs.getString("DESIGNATION") + "</td>");
                pw.println("<td>" + rs.getDouble("SALARY") + "</td>");
                pw.println("<td>" + rs.getString("ADDRESS") + "</td>");
                pw.println("</tr>");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }

        pw.println("</table>");
        pw.println("</div>");

        pw.println("</body>");
        pw.println("</html>");
    }
}
