package yetanothertest;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Thread;
import com.google.api.services.gmail.model.ListThreadsResponse;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Properties;
import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

// ...

public class MailTest {
	
  // ...
	 // Check https://developers.google.com/gmail/api/auth/scopes for all available scopes
	  private static final String SCOPE = "https://www.googleapis.com/auth/gmail.modify";
	  private static final String APP_NAME = "Gmail API Quickstart";
	  // Email address of the user, or "me" can be used to represent the currently authorized user.
	  private static final String USER = "me";
	  // Path to the client_secret.json file downloaded from the Developer Console
	  private static final String CLIENT_SECRET_PATH = "C:\\Users\\Panagiotis\\Downloads\\client_secret_1088880005749-cch5boqdg5cik2kt4niv8v70u3cjcur6.apps.googleusercontent.com.json";

	  private static GoogleClientSecrets clientSecrets;
	  static Gmail service;
	  public static SendMail send=new SendMail();
	  public static void main (String [] args) {
		  
	    HttpTransport httpTransport = new NetHttpTransport();
	    JsonFactory jsonFactory = new JacksonFactory();

	    try {
			clientSecrets = GoogleClientSecrets.load(jsonFactory,  new FileReader(CLIENT_SECRET_PATH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // Allow user to authorize via url.
	    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	        httpTransport, jsonFactory, clientSecrets, Arrays.asList(SCOPE))
	        .setAccessType("online")
	        .setApprovalPrompt("auto").build();

	    String url = flow.newAuthorizationUrl().setRedirectUri(GoogleOAuthConstants.OOB_REDIRECT_URI)
	        .build();
	    System.out.println("Please open the following URL in your browser then type"
	                       + " the authorization code:\n" + url);

	    // Read code entered by user.
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    String code;
		try {
			code = br.readLine();
			GoogleTokenResponse response;
			response = flow.newTokenRequest(code)
				    .setRedirectUri(GoogleOAuthConstants.OOB_REDIRECT_URI).execute();
			 // Generate Credential using retrieved code.
			 GoogleCredential credential = new GoogleCredential()
		        .setFromTokenResponse(response);
			  // Create a new authorized Gmail API client
		      service = new Gmail.Builder(httpTransport, jsonFactory, credential)
		        .setApplicationName(APP_NAME).build();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	   
	  
		
	   

	  

	    // Retrieve a page of Threads; max of 100 by default.
	  //  ListMessagesResponse messagesResponse = service.users().messages().list(USER).execute();
	//   List<Message> messages = messagesResponse.getMessages();

	    //Print ID of each Thread.
	   // for (Message message : messages) {
	     // System.out.println("Message ID: " + message.getPayload());
	   // }
	  //  for(int i=0;i<5;i++){
	   // MimeMessage email=createEmail("ece7761@upnet.gr","me","hello","hello");
	    //sendMessage(service,"me",email);
	    //}
	     
	     
	    MailGui gui=new MailGui();
	 }
  /**
   * Send an email from the user's mailbox to its recipient.
   *
   * @param service Authorized Gmail API instance.
   * @param userId User's email address. The special value "me"
   * can be used to indicate the authenticated user.
   * @param email Email to be sent.
   * @throws MessagingException
   * @throws IOException
   */
  public static void sendMessage(Gmail service, String userId, MimeMessage email)
      throws MessagingException, IOException {
    Message message = createMessageWithEmail(email);
    message = service.users().messages().send(userId, message).execute();

    System.out.println("Message id: " + message.getId());
    System.out.println(message.toPrettyString());
  }

  /**
   * Create a Message from an email
   *
   * @param email Email to be set to raw of message
   * @return Message containing base64url encoded email.
   * @throws IOException
   * @throws MessagingException
   */
  public static Message createMessageWithEmail(MimeMessage email)
      throws MessagingException, IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    email.writeTo(bytes);
    String encodedEmail = Base64.encodeBase64URLSafeString(bytes.toByteArray());
    Message message = new Message();
    message.setRaw(encodedEmail);
    return message;
  }

  /**
   * Create a MimeMessage using the parameters provided.
   *
   * @param to Email address of the receiver.
   * @param from Email address of the sender, the mailbox account.
   * @param subject Subject of the email.
   * @param bodyText Body text of the email.
   * @return MimeMessage to be used to send email.
   * @throws MessagingException
   */
  public static MimeMessage createEmail(String to, String from, String subject,
      String bodyText) throws MessagingException {
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);

    MimeMessage email = new MimeMessage(session);
    InternetAddress tAddress = new InternetAddress(to);
    InternetAddress fAddress = new InternetAddress(from);

    email.setFrom(new InternetAddress(from));
    email.addRecipient(javax.mail.Message.RecipientType.TO,
                       new InternetAddress(to));
    email.setSubject(subject);
    email.setText(bodyText);
    return email;
  }

 

  // ...

}