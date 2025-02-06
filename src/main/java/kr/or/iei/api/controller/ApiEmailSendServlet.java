package kr.or.iei.api.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ApiEmailSendServlet
 */
@WebServlet("/api/emailSend")
public class ApiEmailSendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApiEmailSendServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String receiver=request.getParameter("receiver");
		String emailTitle=request.getParameter("emailTitle");
		String emailContent=request.getParameter("emailContent");
		
		Properties prop=new Properties();
		prop.put("mail.smtp.host","smtp.naver.com");
		prop.put("mail.smtp.port",465);
		prop.put("mail.smtp.auth","true");
		prop.put("mail.smtp.ssl.enable",true);
		prop.put("mail.smtp.ssl.trust","smtp.naver.com");
		
		Session session=Session.getDefaultInstance(prop,new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("pkj9089@naver.com","asaasasa7810");
				
			}
		});
		MimeMessage msg=new MimeMessage(session);
		try {
			msg.setSentDate(new Date());
			msg.setFrom(new InternetAddress("pkj9089@naver.com","kh정보교육원"));
			/*//수신자가 1명일 때
			InternetAddress to=new InternetAddress(receiver);
			msg.setRecipient(Message.RecipientType.TO, to);
			*/
			//수신자가 여러명일 때
			InternetAddress[] receiveArr=new InternetAddress[2];
			receiveArr[0]=new InternetAddress("pkj9089@naver.com");
			receiveArr[1]=new InternetAddress("pkj78100@naver.com");
			msg.setRecipients(Message.RecipientType.TO, receiveArr);
			 
			msg.setSubject(emailTitle);
			msg.setContent(emailContent,"text/html; charset=utf-8");
			
			Transport.send(msg);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
