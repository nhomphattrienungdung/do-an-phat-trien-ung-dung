import java.awt.Button;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class Client extends JFrame implements ActionListener, MouseListener {
	private DatagramSocket client_DatagramSocket;
	private String client_sendData;
	private String client_receivedData;
	private String mail_current="";
	private JLabel user= new JLabel("");
	private InetAddress server_ip;
	private int status=1; //1: inbox  2: outbox  3: drafts  4: trash
	private final int server_port = 2222;
	private final int size_of_packet = 4096;
	
	private JPanel form_Login = new JPanel();
	private JPanel form_Register = new JPanel();
	private JPanel form_GetPass = new JPanel();
	private JPanel form_Main = new JPanel();
	
	private boolean kt_Register = true;
	private boolean kt_GetPass = true;
	private boolean kt_Main = true;
	
	private JTextArea listMail = new JTextArea(20,15);
	private JLabel listMail_Array[] = new JLabel[15];
	private String listMailCurrent_Array[];
	private String listMailView_Array[];
	private int listMailStatus_Array[];
	private JPanel content_Panel = new JPanel();
	
	private int num_inbox=0;
	private String username="";
	
	private int new_inbox=0;
	
	
	
	
	
	private JTextField user_JTextField = new JTextField(15);
	private JPasswordField pass_JTextField = new JPasswordField(15);
	private JPasswordField retypePass_JTextField = new JPasswordField(15);
	private JTextField userRegister_JTextField = new JTextField(15);
	private JTextField userGetPass_JTextField = new JTextField(15);
	private JPasswordField passRegister_JTextField = new JPasswordField(15);
	private JTextField resetPass_JTextField = new JTextField(15);
	private JTextField retype_resetPass_JTextField = new JTextField(15);
	
	private JTextField to_JTextField = new JTextField(75);
	private JTextField subject_JTextField = new JTextField(75);
	
	private JButton login_JButton = new JButton("Đăng nhập");
	private JButton logout_JButton = new JButton("Đăng xuất");
	private JButton compose_JButton = new JButton("Soạn thư");
	
	
	//private JButton logout_JButton = new JButton("Đăng xuất");
	private JButton send_JButton = new JButton("Gửi");
	private JButton send1_JButton = new JButton("Gửi");
	private JButton save_JButton = new JButton("Lưu");
	private JButton cancel_JButton = new JButton("Hủy");
	private JButton delete_JButton = new JButton("Xóa");
	private JButton reply_JButton = new JButton("Trả lời");
	private JButton forward_JButton = new JButton("Chuyển tiếp");
	
	
	
	/*
	private JButton delete_JButton = new JButton("Xóa");
	private JButton reply_JButton = new JButton("Trả lời");
	private JButton replyAll_JButton = new JButton("Trả lời tất cả");
	private JButton foward_JButton = new JButton("Chuyển tiếp");
	private JButton compose_JButton = new JButton("Soạn thư");
	private JButton cancel_JButton = new JButton("Hủy");
	private JButton save_JButton = new JButton("Lưu");
	*/
	private JButton resetPassForm_JButton = new JButton("Lấy lại mật khẩu");
	private JButton form_Register_JButton = new JButton("Tạo mới tài khoản");
	private JButton form_Login_JButton = new JButton("Quay lại Đăng nhập");
	private JButton form_Login1_JButton = new JButton("Quay lại Đăng nhập");
	
	private JButton resetPass_JButton = new JButton("Ok");
	private JButton register_JButton = new JButton("Đăng ký");
	private JButton changePass_JButton = new JButton("Đổi mật khẩu");
	
	private Label inbox_Label = new Label("   Hộp thư đến");
	private Label outbox_Label = new Label("   Thư đã gửi");
	private Label drafts_Label = new Label("   Thư nháp");
	private Label trash_Label = new Label("   Thùng rác");
	private Label contact_Label = new Label("   Liên hệ");
	
	
	
	private JPanel header = new JPanel();
	private JPanel left = new JPanel();
	private JPanel content = new JPanel();
	private JPanel top_of_content = new JPanel();
	private JPanel footer = new JPanel();
	
	private JPanel p0, p1, p2, p3, p4, p5, p6, p7, p8, p9;
	JPanel p10 = new JPanel();
	
	private Label send = new Label(), message_Label = new Label();
	private JPanel p = new JPanel();
	public Client() {
		super("Mail Client");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        setSize(1000,700);
        setResizable(false);
        this.setBackground(Color.WHITE);
        this.setLayout(new FlowLayout());
        p0 = new JPanel();
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        p4 = new JPanel();
        p5 = new JPanel();
        p6 = new JPanel();
        p7 = new JPanel();
        p8 = new JPanel();
        p9 = new JPanel();
        
        listMailCurrent_Array = new String[100];
        listMailView_Array = new String[100];
        listMailStatus_Array = new int[100];
        send_JButton.addActionListener(this);
        send1_JButton.addActionListener(this);
        delete_JButton.addActionListener(this);
		reply_JButton.addActionListener(this);
		forward_JButton.addActionListener(this);
		cancel_JButton.addActionListener(this);
		changePass_JButton.addActionListener(this);
        GUI_form_Login();
        this.add(form_Login);
        //addItem();
        setVisible(true);
        try {
        	client_DatagramSocket = new DatagramSocket();
        	server_ip = InetAddress.getByName("localhost");
        }
        catch (Exception e) {
        	e.printStackTrace();
        }

	}
    	

	public void GUI_form_Login() {
		JPanel p1, p2, p3, p4, p5, p6, p7, p8, p9;
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        p4 = new JPanel();
        p5 = new JPanel();
        p6 = new JPanel();
        p7 = new JPanel();
        p8 = new JPanel();
        p9 = new JPanel();
		
		p1.setLayout(new FlowLayout());
		p1.setBackground(Color.WHITE);
		p1.add(new JLabel("Chào mừng bạn đến với hệ thống TV Mail"));
		
		p2.setBackground(Color.WHITE);
		p2.setLayout(new FlowLayout(FlowLayout.CENTER));
		p2.add(new JLabel("ĐĂNG NHẬP HỆ THỐNG"));
		
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.setBackground(Color.WHITE);
		p3.add(new Label("Tên đăng nhập:      "));
		p3.add(user_JTextField);

		p4.setLayout(new FlowLayout(FlowLayout.LEFT));
		p4.setBackground(Color.WHITE);
		p4.add(new Label("Mật khẩu:                  "));
		p4.add(pass_JTextField);
		
		p5.setLayout(new FlowLayout(FlowLayout.LEFT));
		p5.setBackground(Color.WHITE);
		p5.add(new Label("                                   "));
		login_JButton.addActionListener(this);
		p5.add(login_JButton);
		
		p6.setLayout(new FlowLayout(FlowLayout.CENTER));
		p6.setBackground(Color.WHITE);
		message_Label.setForeground(Color.BLUE);
		message_Label.setText("Tên đăng nhập hoặc mật khẩu sai");
		message_Label.setVisible(false);
		p6.add(message_Label);
		
		p7.setLayout(new FlowLayout(FlowLayout.LEFT));
		p7.setBackground(Color.WHITE);
		p7.add(new Label("Quên mật khẩu?     "));
		resetPassForm_JButton.addActionListener(this);
		p7.add(resetPassForm_JButton);

		p8.setLayout(new FlowLayout(FlowLayout.LEFT));
		p8.setBackground(Color.WHITE);
		
		p9.setLayout(new FlowLayout(FlowLayout.LEFT));
		p9.setBackground(Color.WHITE);
		p9.add(new Label("Chưa có tài khoản?"));
		form_Register_JButton.addActionListener(this);
		p9.add(form_Register_JButton);
		
		form_Login.setLayout(new GridLayout(10,1));
		form_Login.removeAll();
		form_Login.add(p1);
		form_Login.add(p2);
		form_Login.add(p3);
		form_Login.add(p4);
		form_Login.add(p5);
		form_Login.add(p6); // rỗng
		form_Login.add(p7);
		form_Login.add(p8); // rỗng
		form_Login.add(p9);
	}
	
	public void GUI_form_Register() {
		JPanel p1, p2, p3, p4, p5, p6, p7, p8, p9, p10;
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        p4 = new JPanel();
        p5 = new JPanel();
        p6 = new JPanel();
        p7 = new JPanel();
        p8 = new JPanel();
        p9 = new JPanel();
        p10 = new JPanel();
		
		p1.setLayout(new FlowLayout());
		p1.setBackground(Color.WHITE);
		p1.add(new JLabel("Chào mừng bạn đến với hệ thống TV Mail"));

		p2.setBackground(Color.WHITE);
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		p2.add(new JLabel("                   ĐĂNG KÝ TÀI KHOẢN"));
		
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.setBackground(Color.WHITE);
		p3.add(new Label("Tên đăng nhập:      "));
		p3.add(userRegister_JTextField);
		Label userRule = new Label("Chỉ bao gồm chữ cái (a-z, A-Z), chữ số (0-9) và dấu gạch dưới (_). Độ dài ít nhất 6, nhiều nhất 20"); 
		p3.add(userRule);
		
		p4.setLayout(new FlowLayout(FlowLayout.LEFT));
		p4.setBackground(Color.WHITE);
		p4.add(new Label("Mật khẩu:                  "));
		p4.add(passRegister_JTextField);
		Label passRule = new Label("Độ dài ít nhất 6, nhiều nhất 20."); 
		p4.add(passRule);
		
		p5.setLayout(new FlowLayout(FlowLayout.LEFT));
		p5.setBackground(Color.WHITE);
		p5.add(new Label("Gõ lại mật khẩu:     "));
		p5.add(retypePass_JTextField);
		
		p6.setLayout(new FlowLayout(FlowLayout.LEFT));
		p6.setBackground(Color.WHITE);
		p6.add(new Label("Chuỗi bí mật:           "));
		p6.add(resetPass_JTextField);
		Label resetPassRule = new Label("Độ dài ít nhất 6, nhiều nhất 20. Dùng để xác nhận việc khôi phục mật khẩu."); 
		p6.add(resetPassRule);
		
		p7.setLayout(new FlowLayout(FlowLayout.LEFT));
		p7.setBackground(Color.WHITE);
		p7.add(new Label("Gõ lại chuỗi bí mật:"));
		p7.add(retype_resetPass_JTextField);
		
		p8.setLayout(new FlowLayout(FlowLayout.LEFT));
		p8.setBackground(Color.WHITE);
		p8.add(new Label("                                   "));
		register_JButton.addActionListener(this);
		p8.add(register_JButton);
		
		p9.setLayout(new FlowLayout(FlowLayout.LEFT));
		p9.setBackground(Color.WHITE);
		
		p10.setLayout(new FlowLayout(FlowLayout.LEFT));
		p10.setBackground(Color.WHITE);
		p10.add(new Label("Đã có tài khoản?    "));
		form_Login_JButton.addActionListener(this);
		p10.add(form_Login_JButton);
		
		form_Register.setLayout(new GridLayout(10,1));
		form_Register.add(p1);
		form_Register.add(p2);
		form_Register.add(p3);
		form_Register.add(p4);
		form_Register.add(p5);
		form_Register.add(p6); 
		form_Register.add(p7);
		form_Register.add(p8); 
		form_Register.add(p9); // rỗng
		form_Register.add(p10);
	}
	
	public void GUI_form_Main() {
		header.removeAll();
		left.removeAll();
		footer.removeAll();
		
		header.setBackground(Color.WHITE);
		left.setBackground(Color.WHITE);
		content.setBackground(Color.WHITE);
		footer.setBackground(Color.WHITE);
		
		JPanel p0, p1;
		p0 = new JPanel();
		p1 = new JPanel();
		p0.setBackground(Color.WHITE);
		p1.setBackground(Color.WHITE);
		JLabel tvmail = new JLabel("                  Hệ thống TV Mail                                                                                                                                                                    ");
		//tvmail.setFont(Font.getFont("Tahoma"));
		tvmail.setForeground(Color.RED);
		p0.add(tvmail);
		p0.add(new JLabel("Chào:  "));
		user.setForeground(Color.blue);
		p0.add(user);
		p0.add(new JLabel("   "));
		p0.add(changePass_JButton);
		logout_JButton.addActionListener(this);
		p0.add(logout_JButton);
		p1.add(new Label("___________________________________________________________________________________________________________________________________________"));
		header.setLayout(new GridLayout(2,1));
		//Border bd = new Border();
		//header.setBorder(bd);		
		header.add(p0);
		header.add(p1);
		
		//left.add(new JButton("Left"));
		left.setLayout(new GridLayout(14,1));
		inbox_Label.addMouseListener(this);
		outbox_Label.addMouseListener(this);
		drafts_Label.addMouseListener(this);
		trash_Label.addMouseListener(this);
		contact_Label.addMouseListener(this);
		compose_JButton.addActionListener(this);
		JPanel p4 = new JPanel();
		p4.setBackground(Color.WHITE);
		p4.add(compose_JButton);
		left.add(p4);
		left.add(inbox_Label);
		left.add(outbox_Label);
		left.add(drafts_Label);
		left.add(trash_Label);
		left.add(contact_Label);
		
		JPanel p2 = new JPanel(), p3 = new JPanel();
		p2.add(new Label("___________________________________________________________________________________________________________________________________________"));
		p2.setBackground(Color.WHITE);
		p3.setBackground(Color.WHITE);
		footer.setLayout(new GridLayout(2,1));
		p3.add(new JLabel("Phiên bản thử nghiệm.      Nhóm phát triển: "));
		p3.add(new Label("Huynh Duy Khanh"));
		footer.add(p2);
		footer.add(p3);		
		
		//JPanel p5 = new JPanel();
		top_of_content.setBackground(Color.WHITE);
		//top_of_content.add(new Label(" ok"));
		content.setLayout(new BorderLayout());
		listMail.setAutoscrolls(true);
		content.add(top_of_content, BorderLayout.NORTH);		
		content.add(content_Panel, BorderLayout.CENTER);
		content.add(new Label(""), BorderLayout.EAST);
		//content.add(listMail);

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		form_Main.setSize(1000, 700);
		form_Main.setBackground(Color.WHITE);
		form_Main.setLayout(layout);
		c.fill = GridBagConstraints.BOTH;
		
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = GridBagConstraints.REMAINDER; //end row
		layout.setConstraints(header,c);
		form_Main.add(header);
		
		c.weightx = 1;
		c.weighty = 15;
		c.gridwidth = 1;               
		layout.setConstraints(left,c);
		form_Main.add(left);
		
		c.weightx = 12;
		c.weighty = 15;                       
		c.gridwidth = GridBagConstraints.REMAINDER; //end row
		layout.setConstraints(content,c);
		form_Main.add(content);
		
		c.weightx = 1;
		c.weighty = 1; 
		c.gridwidth = GridBagConstraints.REMAINDER; //end row
		layout.setConstraints(footer,c);
		form_Main.add(footer);
	}
	
	public void GUI_form_GetPass() {
		JPanel p1, p2, p3, p4, p5, p6, p7;
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        p4 = new JPanel();
        p5 = new JPanel();
        p6 = new JPanel();
        p7 = new JPanel();
        
		p1.setLayout(new FlowLayout());
		p1.setBackground(Color.WHITE);
		p1.add(new JLabel("Chào mừng bạn đến với hệ thống TV Mail"));
		
		p2.setBackground(Color.WHITE);
		p2.setLayout(new FlowLayout(FlowLayout.CENTER));
		p2.add(new JLabel("KHÔI PHỤC MẬT KHẨU"));
		
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.setBackground(Color.WHITE);
		p3.add(new Label("Tên đăng nhập:      "));
		p3.add(userGetPass_JTextField);
		
		p4.setLayout(new FlowLayout(FlowLayout.LEFT));
		p4.setBackground(Color.WHITE);
		p4.add(new Label("Mã số bí mật:          "));
		p4.add(resetPass_JTextField);
		
		p5.setLayout(new FlowLayout(FlowLayout.LEFT));
		p5.setBackground(Color.WHITE);
		p5.add(new Label("                                   "));
		resetPass_JButton.addActionListener(this);
		p5.add(resetPass_JButton);
		
		p6.setLayout(new FlowLayout(FlowLayout.LEFT));
		p6.setBackground(Color.WHITE);
		
		p7.setLayout(new FlowLayout(FlowLayout.LEFT));
		p7.setBackground(Color.WHITE);
		p7.add(new Label("                                   "));
		form_Login1_JButton.addActionListener(this);
		p7.add(form_Login1_JButton);
		p7.setVisible(false);
		p7.setVisible(true);
		
		form_GetPass.setLayout(new GridLayout(10,1));
		form_GetPass.add(p1);
		form_GetPass.add(p2);
		form_GetPass.add(p3);
		form_GetPass.add(p4);
		form_GetPass.add(p5);
		form_GetPass.add(p6); // rỗng
		form_GetPass.add(p7);
	}
	
	@SuppressWarnings("deprecation")
	public boolean Login() {
		/* Quy ước gửi gói tin:
		 	Client --> Server
				0: register
				1: login 
				2: resetPass (getPass)
				3: changePass
				4: send
				5: reply
				6: forward
				7: read
				8: delete
				9: logout
			
			Phân biệt giữa các chuỗi truyền đi: ^$^
		*/
		client_sendData = "";
		client_sendData = "1" + user_JTextField.getText() + "^$^" + pass_JTextField.getText() + "1";
		//System.out.print(pass_JTextField.getPassword().);
		// Chuỗi truyền đi trong Login có dạng:
		//	1user^$^password1
		// Ví dụ:	1thanh^$^goalkeeper1
		sendData(client_sendData);
		client_receivedData = getData();
		if (client_receivedData.compareTo("1") == 0) return true;
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public boolean Register() {
		int len_user=    userRegister_JTextField.getText().length();
        int len_pass=    passRegister_JTextField.getText().length();
        client_sendData = "";
        client_sendData = "0"+ len_user + " "+userRegister_JTextField.getText() +len_pass+ " " + passRegister_JTextField.getText()+resetPass_JTextField.getText()+"0";
        System.out.printf(userRegister_JTextField.getText());
        System.out.printf(passRegister_JTextField.getText());
        System.out.printf(resetPass_JTextField.getText());
        sendData(client_sendData);
        client_receivedData = getData();
        System.out.printf(client_sendData);
        if (client_receivedData.compareTo("1") == 0) return true;		
		return false;
	}
	
	public boolean GetPass() {
		// Kiểm tra user đúng quy ước ký tự, độ dài trước khi gửi đến server
		// Chuỗi truyền đi có dạng:
		//	2^$^user^$^resetPass2
		
		client_sendData = "";
		client_sendData = "2" + userGetPass_JTextField.getText() + "^$^" + resetPass_JTextField.getText() + "2";
		 // Chuỗi truyền đi có dạng:
		//	2^$^user^$^resetPass2

		sendData(client_sendData);
		client_receivedData = getData();
		if (client_receivedData.compareTo("0") != 0) return true;

		

           
		
		return false;
	}
	
	public boolean ChangePass() {
		
		return false;
	}
	
	public boolean Send(String s) {
		String to = to_JTextField.getText();
		String subject = subject_JTextField.getText();
		String content = listMail.getText();
		client_sendData = s + username.length() + " " + username + to.length() + " " + to + subject.length() + " " + subject + content + s;
		sendData(client_sendData);
		client_receivedData = getData();
		if (client_receivedData.compareTo("1") ==0) return true;
		return false;
	}

	public boolean Reply() {
		String to = to_JTextField.getText();
		String subject = subject_JTextField.getText();
		String content = listMail.getText();
		client_sendData = "5" + username.length() + " " + username + to.length() + " " + to + subject.length() + " " + subject + content + "5";
		sendData(client_sendData);
		client_receivedData = getData();
		if (client_receivedData.compareTo("1") ==0) return true;
		return false;
	}
	
	public boolean Forward() {		
		return false;
	}
	
	public boolean Read() {		
		return false;
	}
	
	public boolean Delete() {
		String dir="";
		switch (status) {
			case 1:
				dir = "inbox";
				break;
			case 2:
				dir = "outbox";
				break;
			case 3:
				dir = "drafts";
				break;
			case 4:
				dir = "trash";
				break;
		}
		client_sendData= "8" + String.valueOf(username.length()) + " " + username + String.valueOf(dir.length()) + " " + dir + mail_current.substring(mail_current.length() - "_2011-04-29_21-39-37.txt".length(), mail_current.length()) + "8";
		sendData(client_sendData);
		client_receivedData = getData();
		if (client_receivedData.compareTo("1") == 0) return true;
		return false;
	}
	
	public boolean Logout() {		
		return false;
	}
	
	public boolean Display(String s) {

		//content.add(comp)
		//content_Panel.add(new JScrollPane(listMail), BorderLayout.CENTER);
		content_Panel.removeAll();
		content_Panel.setBackground(Color.WHITE);
		content_Panel.setLayout(new GridLayout(15,1));
		
		client_sendData = "7" + s + username;
		sendData(client_sendData);
		client_receivedData = getData();
		System.out.printf("\n\n\n" + client_receivedData + "\n\n\n");
		//client_receivedData = "3 abc6 abc ab4 ABCD";
		if (client_receivedData.compareTo("Không có thư") == 0) {
			content_Panel.add((new Label(client_receivedData)));
		}
		else {
			num_inbox=0;
			do {
				String temp="";
				int len=0;
				int i=0;
				while (i<client_receivedData.length()) {
					if (client_receivedData.charAt(i) == ' ') {
					
						temp = client_receivedData.substring(0,i);
						System.out.printf("Độ dài: " + temp + "\n");
						client_receivedData = client_receivedData.substring(i+1,client_receivedData.length());
					//System.out.printf(client_receivedData + "\n");
						len = Integer.parseInt(temp);
						break;
					}
					else i++;
				}
				listMailView_Array[num_inbox] = client_receivedData.substring(0,len);
				if (listMailView_Array[num_inbox].charAt(0) != '1' && listMailView_Array[num_inbox].charAt(0) != '0') listMailView_Array[num_inbox] = listMailView_Array[num_inbox].substring(1, listMailView_Array[num_inbox].length()) ;
				
				System.out.printf(" Nội dung: \'" + listMailView_Array[num_inbox].charAt(0) + "\'" + listMailView_Array[num_inbox] + "\n");
				int vt=0;
				//if (num_inbox == 0) vt=1;
				if (listMailView_Array[num_inbox].charAt(vt) == '1' /*|| listMailView_Array[num_inbox].charAt(1) == '1'*/) listMailStatus_Array[num_inbox] = 1;
				else listMailStatus_Array[num_inbox] = 0;
							
			
				if (len == client_receivedData.length()) {
					System.out.printf("\n\nOKKKKK");
					break;
				}
				client_receivedData = client_receivedData.substring(len,client_receivedData.length());
				System.out.printf("Còn lại: " + client_receivedData + "\n");
				num_inbox ++;
			
			
			} while (client_receivedData != "");
		
			//01684006260
			
			for (int i=0; i <= num_inbox; i++) {
				int vt=1;
				//if (i==0 && listMailStatus_Array[0]==1) vt = 2;
				listMail_Array[i] = new JLabel("");
				Font f1 = new Font("Courier New",Font.BOLD,14);
				Font f2 = new Font("Courier New",Font.PLAIN,14);
				
				if (listMailStatus_Array[i] == 1) {
					listMail_Array[i].setFont(f1);
				}
				else listMail_Array[i].setFont(f2);
				
				//listMail_Array[i].setFont(new Font("Times New Roman",Font.PLAIN,12));
				
				String temp = listMailView_Array[i].substring(vt, listMailView_Array[i].length() - "_2011-04-29_21-39-37.txt".length() + vt - 1);
				if (temp.length() > 80) {
					if (temp.charAt(80) == '\n' || temp.charAt(80) == '\r') temp = temp.substring(0, 79) + " ";
					else temp = temp.substring(0, 80);
					temp += "   " + listMailView_Array[i].substring(listMailView_Array[i].length() - "2011-04-29_21-39-37.txt".length(), listMailView_Array[i].length() - "_21-39-37.txt".length());
				}
				else {
					for (int j=-1; j<=80 - temp.length(); j++) temp += " "; 
					temp += "   " + listMailView_Array[i].substring(listMailView_Array[i].length() - "2011-04-29_21-39-37.txt".length(), listMailView_Array[i].length() - "_21-39-37.txt".length());
				}
				listMail_Array[i].setText(temp);
				listMail_Array[i].addMouseListener(this);
				//content_Panel.add(new Label("ok" + String.valueOf(i)));
				content_Panel.add(listMail_Array[i]);
			}	
		}

		content.setVisible(false);
		content.setVisible(true);
		return false;
	}

	public static void main(String args[]) {
		new Client();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == form_Register_JButton) {
			form_Login.setVisible(false);
			//this.setSize(880,400);
			if (kt_Register) {
				GUI_form_Register();
				this.add(form_Register);
				kt_Register = false;
			}
			else form_Register.setVisible(true);
		}
		else if (e.getSource() == form_Login_JButton) {
			form_Register.setVisible(false);
			//this.setSize(350,365);
			form_Login.setVisible(true);
		}
		else if (e.getSource() == logout_JButton) {
			form_Main.setVisible(false);
			//this.setSize(350,365);
			form_Login.setVisible(true);
		}
		else if (e.getSource() == resetPassForm_JButton) {
			form_Login.setVisible(false);
			//this.setSize(350,300);
			if (kt_GetPass) {
				GUI_form_GetPass();
				this.add(form_GetPass);
				kt_GetPass = false;
			}
			else form_GetPass.setVisible(true);
		}
		else if (e.getSource() == form_Login1_JButton) {
			form_GetPass.setVisible(false);
			//this.setSize(350,365);
			form_Login.setVisible(true);
		}
		else if (e.getSource() == login_JButton) {
			if (Login()) {
				Font f = new Font(this.getFont().toString(),Font.BOLD,this.getFont().getSize());
				Font f1 = new Font(this.getFont().toString(),this.getFont().getStyle(),this.getFont().getSize());
				
				inbox_Label.setFont(f);
				outbox_Label.setFont(f1);
						    		
	    		drafts_Label.setFont(f1);
	    		trash_Label.setFont(f1);
	    		contact_Label.setFont(f1);
				String user_temp = user_JTextField.getText(); 
				username = user_temp;
				if (user_temp.length() < 20) {
					for (int i=1; i<= 20 - user_temp.length(); i++) user_temp += " ";
				}
				user.setText(user_temp);
				message_Label.setVisible(false);
				form_Login.setVisible(false);
				//this.setSize(1000,700);
				if (kt_Main) {
					GUI_form_Main();
					this.add(form_Main);
					kt_Main = false;
				}
				else {
					header.setVisible(false);
					header.setVisible(true);
					form_Main.setVisible(true);
				}
				Display("1");
			}
			else {
				JOptionPane.showMessageDialog(this,"Tên đăng nhập hoặc mật khẩu sai.","Lỗi đăng nhập",JOptionPane.WARNING_MESSAGE);
				/*
				message_Label.setVisible(true);
				form_Login.setVisible(false);
				form_Login.setVisible(true);
				*/
			}
		}
		else if (e.getSource() == register_JButton) {
			if (Register()) {
				JOptionPane.showMessageDialog(this,"Bạn đã đăng kí thành công.","Thông báo",JOptionPane.PLAIN_MESSAGE);
				
			}
			else {
				JOptionPane.showMessageDialog(this,"Lỗi xảy ra trong quá trình đăng ký","Lỗi",JOptionPane.ERROR_MESSAGE);
				
			}
		}
		else if (e.getSource() == resetPass_JButton) {
			if (GetPass()==false) {
				
				JOptionPane.showMessageDialog(this,"Sai tên hoặc mã số bí mật","Lỗi",JOptionPane.ERROR_MESSAGE);
			}

			else {
				JOptionPane.showMessageDialog(this,client_receivedData,"Mật khẩu",JOptionPane.PLAIN_MESSAGE);
				
			}
		}
		else if (e.getSource() == compose_JButton) {
			top_of_content.removeAll();
			
			top_of_content.add(send_JButton);
			save_JButton.addActionListener(this);
			top_of_content.add(save_JButton);
			cancel_JButton.addActionListener(this);
			top_of_content.add(cancel_JButton);
			top_of_content.setVisible(false);
			top_of_content.setVisible(true);
			content_Panel.removeAll();
			JPanel p1 = new JPanel(), p2 = new JPanel(), p3 = new JPanel(), p4 = new JPanel();
			p1.setBackground(Color.WHITE);
			p1.setLayout(new FlowLayout(FlowLayout.LEFT));
			p1.add(new JLabel("Tới:        "));
			to_JTextField.setText("");
			p1.add(to_JTextField);
			
			p2.setBackground(Color.WHITE);
			p2.setLayout(new FlowLayout(FlowLayout.LEFT));
			p2.add(new JLabel("Tiêu đề: "));
			subject_JTextField.setText("");
			p2.add(subject_JTextField);
			
			p3.setBackground(Color.WHITE);
			p3.setLayout(new FlowLayout(FlowLayout.LEFT));
			p3.add(new JLabel("Nội dung: "));
			//p3.add(subject_JTextField);
			p4.setBackground(Color.WHITE);
			p4.setLayout(new GridLayout(3,1));
			
			p4.add(p1);
			p4.add(p2);
			p4.add(p3);
			content_Panel.setLayout(new BorderLayout());
			content_Panel.add(p4,BorderLayout.NORTH);
			listMail.setText("");
			content_Panel.add(new JScrollPane(listMail), BorderLayout.CENTER);
			
		}
		else if (e.getSource() == send_JButton || e.getSource() == send1_JButton) {
			String temp="4";
			if (e.getSource() == send1_JButton) temp="5";
			String to = to_JTextField.getText();
			if (to.length() < 1) {
				JOptionPane.showMessageDialog(this,"Chưa nhập tên người nhận thư","Lỗi",JOptionPane.ERROR_MESSAGE);
			}
			else {
				client_sendData = "s" + to + "s";
				sendData(client_sendData);
				client_receivedData = getData();
				if (client_receivedData.compareTo("1") != 0) {
					JOptionPane.showMessageDialog(this,"Tên người nhận thư không có trong hệ thống","Lỗi",JOptionPane.ERROR_MESSAGE);					
				}
				else if (Send(temp)) {
					JOptionPane.showMessageDialog(this,"Đã gửi thành công","Thông báo",JOptionPane.PLAIN_MESSAGE);
					Font f = new Font(this.getFont().toString(),Font.BOLD,this.getFont().getSize());
					Font f1 = new Font(this.getFont().toString(),this.getFont().getStyle(),this.getFont().getSize());
					
					inbox_Label.setFont(f1);
					outbox_Label.setFont(f);
							    		
		    		drafts_Label.setFont(f1);
		    		trash_Label.setFont(f1);
		    		contact_Label.setFont(f1);
		    		
		    		top_of_content.setVisible(false);
		    		//top_of_content.setVisible(true);
					Display("2");
				}
				else JOptionPane.showMessageDialog(this,"Có lỗi trong quá trình gửi. Hãy thử lại","Lỗi",JOptionPane.WARNING_MESSAGE);
				
			}
		}
		
		else if (e.getSource() == reply_JButton) {
			delete_JButton.setVisible(false);
			forward_JButton.setVisible(false);
			reply_JButton.setVisible(false);
			
			//send1_JButton.addActionListener(this);
			top_of_content.add(send1_JButton);
			top_of_content.add(cancel_JButton);
			//top_of_content.setVisible(false);
			//top_of_content.setVisible(true);
			content_Panel.removeAll();
			JPanel p1 = new JPanel(), p2 = new JPanel(), p3 = new JPanel(), p4 = new JPanel();
			p1.setBackground(Color.WHITE);
			p1.setLayout(new FlowLayout(FlowLayout.LEFT));
			p1.add(new JLabel("Tới:        "));
			int i=0;
			String temp="";
			do {
				if (mail_current.substring(i,i+6).compareTo("from: ") ==0) {
					temp = mail_current.substring(i+6, i+6+20);
					temp = temp.trim();
					break;
				}
				i++;
			} while (true);
			
			to_JTextField.setText(temp);
			p1.add(to_JTextField);
			
			
			p2.setBackground(Color.WHITE);
			p2.setLayout(new FlowLayout(FlowLayout.LEFT));
			p2.add(new JLabel("Tiêu đề: "));
			
			temp="";
			do {
				if (mail_current.substring(i,i+9).compareTo("subject: ") ==0) {
					temp = mail_current.substring(i+9, mail_current.length());
					int j=0;
					do {
						if (temp.substring(j,j+8).compareTo("content:") ==0) {
							temp = temp.substring(0,j);
							break;
						}
						j++;
					} while (true);
					//temp = temp.trim();
					break;
				}
				i++;
			} while (true);
			
			subject_JTextField.setText("Trả lời: " + temp);
			//p2.add(subject_JTextField);
			p2.add(new Label("Trả lời: " + temp));
			
			p3.setBackground(Color.WHITE);
			p3.setLayout(new FlowLayout(FlowLayout.LEFT));
			p3.add(new JLabel("Nội dung: "));
			//p3.add(subject_JTextField);
			p4.setBackground(Color.WHITE);
			p4.setLayout(new GridLayout(3,1));
			
			p4.add(p1);
			p4.add(p2);
			p4.add(p3);
			content_Panel.removeAll();
			content_Panel.setLayout(new BorderLayout());
			content_Panel.add(p4,BorderLayout.NORTH);
			listMail.setText("");
			content_Panel.add(new JScrollPane(listMail), BorderLayout.CENTER);
			content_Panel.setVisible(false);
			content_Panel.setVisible(true);
		}
		else if (e.getSource() == cancel_JButton) {
			Font f = new Font(this.getFont().toString(),Font.BOLD,this.getFont().getSize());
			Font f1 = new Font(this.getFont().toString(),this.getFont().getStyle(),this.getFont().getSize());
			
			inbox_Label.setFont(f);
			outbox_Label.setFont(f1);
					    		
    		drafts_Label.setFont(f1);
    		trash_Label.setFont(f1);
    		contact_Label.setFont(f1);
    		
    		top_of_content.setVisible(false);
    		//top_of_content.setVisible(true);
			Display("1");
		}
		else if (e.getSource() == delete_JButton) {
			if (Delete()) {
				JOptionPane.showMessageDialog(this,"Đã xóa thành công","Thông báo",JOptionPane.PLAIN_MESSAGE);
				Font f = new Font(this.getFont().toString(),Font.BOLD,this.getFont().getSize());
				Font f1 = new Font(this.getFont().toString(),this.getFont().getStyle(),this.getFont().getSize());
				if (status ==1) {
					inbox_Label.setFont(f);
					outbox_Label.setFont(f1);
				}	
				else {
					inbox_Label.setFont(f1);
					outbox_Label.setFont(f);
				}		    		
	    		drafts_Label.setFont(f1);
	    		trash_Label.setFont(f1);
	    		contact_Label.setFont(f1);
	    		
	    		top_of_content.setVisible(false);
	    		//top_of_content.setVisible(true);
				Display(String.valueOf(status));
			}
			else JOptionPane.showMessageDialog(this,"Có lỗi trong quá trình xóa. Hãy thử lại","Lỗi",JOptionPane.WARNING_MESSAGE);
			
		}
		else if (e.getSource() == forward_JButton || e.getSource() == changePass_JButton) {
			JOptionPane.showMessageDialog(this,"Chức năng này chưa có","Cảnh báo",JOptionPane.WARNING_MESSAGE);
		}
    }
	
	public void mouseClicked(MouseEvent e) {
		Font f = new Font(this.getFont().toString(),Font.BOLD,this.getFont().getSize());
		Font f1 = new Font(this.getFont().toString(),this.getFont().getStyle(),this.getFont().getSize());
		if (e.getSource() == inbox_Label) {  
			status = 1;
    		inbox_Label.setFont(f);
    		outbox_Label.setFont(f1);
    		drafts_Label.setFont(f1);
    		trash_Label.setFont(f1);
    		contact_Label.setFont(f1);
    		top_of_content.removeAll();
    		top_of_content.setVisible(false);
    		top_of_content.setVisible(true);
    		Display("1");
    	}    	
    	else if (e.getSource() == outbox_Label) {
    		status = 2;
    		inbox_Label.setFont(f1);
    		outbox_Label.setFont(f);
    		drafts_Label.setFont(f1);
    		trash_Label.setFont(f1);
    		contact_Label.setFont(f1);
    		top_of_content.removeAll();
    		top_of_content.setVisible(false);
    		top_of_content.setVisible(true);
    		Display("2");
    	}
    	else if (e.getSource() == drafts_Label) {
    		status = 3;
    		inbox_Label.setFont(f1);
    		outbox_Label.setFont(f1);
    		drafts_Label.setFont(f);
    		trash_Label.setFont(f1);
    		contact_Label.setFont(f1);
    		top_of_content.removeAll();
    		top_of_content.setVisible(false);
    		top_of_content.setVisible(true);
    		Display("3");
    	}
    	else if (e.getSource() == trash_Label) {
    		status = 4;
    		inbox_Label.setFont(f1);
    		outbox_Label.setFont(f1);
    		drafts_Label.setFont(f1);
    		trash_Label.setFont(f);
    		contact_Label.setFont(f1);
    		top_of_content.removeAll();
    		top_of_content.setVisible(false);
    		top_of_content.setVisible(true);
    		Display("4");
    	}
    	else if (e.getSource() == contact_Label) {
    		inbox_Label.setFont(f1);
    		outbox_Label.setFont(f1);
    		drafts_Label.setFont(f1);
    		trash_Label.setFont(f1);
    		contact_Label.setFont(f);
    		JOptionPane.showMessageDialog(this,"Chức năng này chưa có","Cảnh báo",JOptionPane.WARNING_MESSAGE);
    	}
		
		for (int i=0; i<=14; i++)
    		if (e.getSource() == listMail_Array[i]) {
    			mail_current = listMailView_Array[i];
    			if (listMailStatus_Array[i] == 1) {
    				client_sendData="";
    				client_sendData="u" + username + "$" + listMailView_Array[i].substring(listMailView_Array[i].length() - "_2011-04-29_21-39-37.txt".length(), listMailView_Array[i].length());
    				System.out.printf("\n In ra: " + client_sendData);
    				sendData(client_sendData);
    				System.out.printf("\n\nOKKKK" + getData());
    			}
    			
    			top_of_content.removeAll();
    			delete_JButton.setVisible(true);
    			reply_JButton.setVisible(true);
    			forward_JButton.setVisible(true);
    			top_of_content.add(delete_JButton);
    			//
    			if (status == 1) top_of_content.add(reply_JButton);
    			//
    			top_of_content.add(forward_JButton);
    			top_of_content.setVisible(false);
    			top_of_content.setVisible(true);
    			
    			content_Panel.removeAll();
    			System.out.printf(listMailView_Array[i]);
    			listMail.removeAll();
    			listMail.setAutoscrolls(true);
    			listMail.setText(listMailView_Array[i]);
    			//
    			content_Panel.setLayout(new BorderLayout());
    			content_Panel.add(listMail);
    			//content_Panel.add(new JScrollPane(listMail), BorderLayout.CENTER);
    			content_Panel.setVisible(false);
    			content_Panel.setVisible(true);
    			//listMail_Array[i].setCursor(Cursor.getPredefinedCursor(HAND_CURSOR)); 
    			break;
    		}
		
	}
    
    @SuppressWarnings("deprecation")
	public void mouseEntered(MouseEvent e) {
    	if (e.getSource() == inbox_Label) {
    		inbox_Label.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
    		inbox_Label.setBackground(Color.LIGHT_GRAY);
    	}    	
    	else if (e.getSource() == outbox_Label) {
    		outbox_Label.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
    		outbox_Label.setBackground(Color.LIGHT_GRAY);
    	}
    	else if (e.getSource() == drafts_Label) {
    		drafts_Label.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
    		drafts_Label.setBackground(Color.LIGHT_GRAY);
    	}
    	else if (e.getSource() == trash_Label) {
    		trash_Label.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
    		trash_Label.setBackground(Color.LIGHT_GRAY);
    	}
    	else if (e.getSource() == contact_Label) {
    		contact_Label.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));
    		contact_Label.setBackground(Color.LIGHT_GRAY);
    	}
    	
    	for (int i=0; i<=14; i++)
    		if (e.getSource() == listMail_Array[i]) {
    			listMail_Array[i].setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));    			
    		}
    		/*
    		Font f = new Font(this.getFont().toString(),Font.BOLD,this.getFont().getSize());
    		
    		inbox_Label.setText("Ok");
    		inbox_Label.setFont(f);
    		*/
    	
    	/*
    	Color c = Color.LIGHT_GRAY;
    	send.setForeground(c);
    	Font f = new Font(this.getFont().toString(),Font.BOLD,this.getFont().getSize());
    	//Font.
    	send.setFont(f);
    	*/
    }
    
    public void mouseExited(MouseEvent e) {
    	if (e.getSource() == inbox_Label) {    		
    		inbox_Label.setBackground(Color.WHITE);
    	}    	
    	else if (e.getSource() == outbox_Label) {
    		outbox_Label.setBackground(Color.WHITE);
    	}
    	else if (e.getSource() == drafts_Label) {
    		drafts_Label.setBackground(Color.WHITE);
    	}
    	else if (e.getSource() == trash_Label) {
    		trash_Label.setBackground(Color.WHITE);
    	}
    	else if (e.getSource() == contact_Label) {
    		contact_Label.setBackground(Color.WHITE);
    	}
    }
    
    public void mousePressed(MouseEvent e) {
    	
    }
    
    public void mouseReleased(MouseEvent e) {
    	
    }
	
	
	
	public String getData() {	   
		try {
				byte[] client_receivedByte = new byte[size_of_packet];				
				DatagramPacket client_receivedPacket = new DatagramPacket(client_receivedByte, client_receivedByte.length,server_ip,server_port);
				client_DatagramSocket.receive(client_receivedPacket);
				String s = new String (client_receivedPacket.getData()).trim();
				return s;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public void sendData(String s) {
		try {
			byte[] client_sendByte = new byte[size_of_packet];
			client_sendByte = s.getBytes();
			DatagramPacket client_sendPacket = new DatagramPacket(client_sendByte, client_sendByte.length,server_ip,server_port);
			client_DatagramSocket.send(client_sendPacket);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}