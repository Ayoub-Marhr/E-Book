package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
	private static final String Query= "INSERT INTO bookreferences(BookName,BookEdition,BookPrice)VALUES(?,?,?)";
	@Override
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    //get print writer
	    PrintWriter pw = resp.getWriter();
	    //set content Type
	    resp.setContentType("text/html");
	    //get the book info 
	    String bookName = req.getParameter("bookName");
	    String bookEdition = req.getParameter("bookEdition");
	    String bookPriceStr = req.getParameter("bookPrice");

	    // Check if any of the parameters are null
	    if (bookName == null || bookEdition == null || bookPriceStr == null) {
	        pw.println("<h2>One or more parameters are missing.</h2>");
	        return;
	    }

	    try {
	        // Parse bookPrice
	        Float bookPrice = Float.parseFloat(bookPriceStr);

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
	            ps.setString(1, bookName);
	            ps.setString(2, bookEdition);
	            ps.setFloat(3, bookPrice);
	            int count = ps.executeUpdate();
	            if (count == 1) {
	                pw.println("<h2>Record is inserted successfully</h2>");
	            } else {
	                pw.println("<h2>Record is not inserted</h2>");
	            }
	        } catch (SQLException se) {
	            se.printStackTrace();
	            pw.println("<h1>" + se.getMessage() + "</h2>");
	        } catch (Exception e) {
	            e.printStackTrace();
	            pw.println("<h1>" + e.getMessage() + "</h2>");
	        }

	    } catch (NumberFormatException e) {
	        pw.println("<h2>Invalid value for bookPrice</h2>");
	    }
	    pw.println("<a href='Home.html'>Home</a>");
	    pw.println("<br>");
	    pw.println("<a href='BookList'>Book List</a>");
	}

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
}