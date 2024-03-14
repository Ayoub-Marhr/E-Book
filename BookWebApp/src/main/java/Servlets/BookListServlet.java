package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/BookList")
public class BookListServlet extends HttpServlet{
	private static final String Query= "SELECT Id,BookName,BookEdition,BookPrice FROM bookreferences ";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    //get print writer
	    PrintWriter pw = resp.getWriter();
	    //set content Type
	    resp.setContentType("text/html");
	    
	    pw.println("<!DOCTYPE html>");
	    pw.println("<html>");
	    pw.println("<head>");
	    pw.println("<title>Book List</title>");
	    pw.println("<style>");
	    pw.println("table {");
	    pw.println("    font-family: Arial, sans-serif;");
	    pw.println("    border-collapse: collapse;");
	    pw.println("    width: 100%;");
	    pw.println("}");
	    pw.println("th, td {");
	    pw.println("    border: 1px solid #dddddd;");
	    pw.println("    text-align: left;");
	    pw.println("    padding: 8px;");
	    pw.println("}");
	    pw.println("tr:nth-child(even) {");
	    pw.println("    background-color: #f2f2f2;");
	    pw.println("}");
	    pw.println("</style>");
	    pw.println("</head>");
	    pw.println("<body>");
	    
	    // Load JDBC driver
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        pw.println("<h2>JDBC Driver not found.</h2>");
	        return;
	    }

	    // Generate the connection
	    try (Connection con = DriverManager.getConnection("jdbc:mysql:///book", "root", "")) {
	        PreparedStatement ps = con.prepareStatement(Query);
	        ResultSet rs = ps.executeQuery();
	        pw.println("<table>");
	        pw.println("<tr>");
	        pw.println("<th>Book Id</th>");
	        pw.println("<th>Book Name</th>");
	        pw.println("<th>Book Edition</th>");
	        pw.println("<th>Book Price</th>");
	        pw.println("</tr>");
	        while(rs.next()) {
	            pw.println("<tr>");
	            pw.println("<td>"+rs.getInt(1)+"</td>");
	            pw.println("<td>"+rs.getString(2)+ "</td>");
	            pw.println("<td>"+rs.getString(3)+"</td>");
	            pw.println("<td>"+rs.getFloat(4)+"</td>");
	            pw.println("</tr>");
	        };
	        pw.println("</table>");

	    } catch (SQLException se) {
	        se.printStackTrace();
	        pw.println("<h1>" + se.getMessage() + "</h1>");
	    } catch (Exception e) {
	        e.printStackTrace();
	        pw.println("<h1>" + e.getMessage() + "</h1>");
	    }
	    pw.println("<br>");
	    pw.println("<a href='Home.html'>Home</a>");
	    pw.println("</body>");
	    pw.println("</html>");
	}

	

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}

}
