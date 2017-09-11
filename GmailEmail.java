/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmailemail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author HuangVinh
 */
public class GmailEmail {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      final String username="packsisme7@gmail.com";
      final String password="0917233329";
      
       // Tạo đối tuong Properties và chỉ định thông tin host,port
        Properties props= new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.startTls.enable","true");
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        //Tạo đối tượng Session (phiên làm việc)
    Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(username, password);
            }
        });
    
    try{
        // Tao doi tuong Message
        Message message= new MimeMessage(session);
        message.setFrom(new InternetAddress("packsisme7@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("datvinh1311@gmail.com"));
        message.setSubject("Đây là Chủ Đề");
        message.setContent("Đây là nội dung","text/html;charset=utf-8");//"text/html;charset=utf-8" để ghi có dấu
        
        //Gửi mail
        Transport.send(message);
        System.out.println("Da Gui Thanh Cong!!!");
        
        
    }catch(MessagingException e){
        throw new RuntimeException(e);
    }
    }
    
}
