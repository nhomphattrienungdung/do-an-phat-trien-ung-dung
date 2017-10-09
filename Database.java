import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {
	private Connection con;
	private Statement stn;
	private ResultSet rs;
	
	public void connect() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:TVMail_db");
			stn=con.createStatement();			
		}
        catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		new Database();
	
	}
	
	// Kiểm tra tài khoản đã có trong hệ thống hay chưa
	public boolean isRightUser(String user) {
		try {
			this.connect();
			rs = stn.executeQuery("select * from user_tb where user='" + user + "'");
			boolean b = rs.next();
			con.close();
			return b;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Kiểm tra mật khẩu có đúng hay không
	public boolean isRightPass(String user, String pass) {
		try {
			this.connect();
			rs = stn.executeQuery("select pass from user_tb where user='" + user + "'");
			String rs_pass = new String();
			while(rs.next()) rs_pass = rs.getString(1);
			con.close();
			//System.out.println(rs_pass);
			if (rs_pass.compareTo(pass) == 0) return true;
			else return false;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Kiểm tra chuỗi để khôi phục mật khẩu có đúng hay không
	public boolean isRight_resetPass(String user, String resetPass) {
		try {
			this.connect();
			rs = stn.executeQuery("select resetPass from user_tb where user='" + user + "'");
			String rs_resetPass = new String();
			while(rs.next()) rs_resetPass = rs.getString(1);
			con.close();
			if (rs_resetPass.compareTo(resetPass) == 0) return true;
			else return false;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Lấy mật khẩu
	public String getPass(String user) {
		try {
			this.connect();
			rs = stn.executeQuery("select pass from user_tb where user='" + user + "'");
			String rs_pass = new String();
			while(rs.next()) rs_pass = rs.getString(1);
			con.close();
			return rs_pass;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	// Tạo mới một tài khoản
	public boolean create_User(String user, String pass, String resetPass) {
		try {
			this.connect();
			stn.executeUpdate("insert into user_tb values('" + user + "','" + pass + "','" + resetPass + "')");
			con.close();
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Cập nhật mật khẩu mới
	public void update_Pass(String user, String pass) {
		try {
			this.connect();
			stn.executeUpdate("update user_tb set pass='" + pass + "' where user='" + user + "'");
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Cập nhật chuỗi khôi phục mật khẩu mới
	public void update_resetPass(String user, String resetPass) {
		try {
			this.connect();
			stn.executeUpdate("update user_tb set resetPass='" + resetPass + "' where user='" + user + "'");
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
