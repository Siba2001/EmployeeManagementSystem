package employee;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterEmployee
 */
@WebServlet("/registerEmployee")
public class RegisterEmployee extends HttpServlet {
	Connection con;
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RegisterEmployee() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "c##siba1", "qwer");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    try {
	        String s1 = request.getParameter("fullname");
	        String s2 = request.getParameter("dob");
	        String s3 = request.getParameter("gender");
	        String s4 = request.getParameter("designation");
	        String s5 = request.getParameter("salary");
	        String s6 = request.getParameter("address");

	        PreparedStatement psmt = con.prepareStatement(
	            "INSERT INTO EMPLOYEE " +
	            "(FULL_NAME, DOB, GENDER, DESIGNATION, SALARY, ADDRESS) " +
	            "VALUES (?, ?, ?, ?, ?, ?)"
	        );

	        psmt.setString(1, s1);
	        psmt.setDate(2, java.sql.Date.valueOf(s2));
	        psmt.setString(3, s3);
	        psmt.setString(4, s4);
	        psmt.setDouble(5, Double.parseDouble(s5));
	        psmt.setString(6, s6);

	        psmt.executeUpdate();

	        
	        response.setContentType("text/html");
	        java.io.PrintWriter pw = response.getWriter();
	        pw.println("<script>");
	        pw.println("alert('Employee Added Successfully');");
	        pw.println("window.location='index.html';");
	        pw.println("</script>");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}