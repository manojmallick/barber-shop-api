package com.ing.barber.shop.api.email;

import com.ing.barber.shop.api.appointment.model.Appointment;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {
  private static final String EMAIL_TEMPLATE_PATH = "templates/";
  private static final String EMAIL_TEMPLATE_PATH_EXTENSION = ".vm";

  private final JavaMailSender mailSender;
  private final VelocityEngine velocityEngine;

  @Value("${mail.from}")
  private String from;

  @Autowired
  public EmailService(JavaMailSender mailSender,
      VelocityEngine velocityEngine) {
    this.mailSender = mailSender;
    this.velocityEngine = velocityEngine;
  }

  public void sendConfirmationEmail(Appointment appointment){
    String customerEmail=appointment.getCustomer().getEmail();
    sendEmail(new String[]{customerEmail},EmailTemplate.CONFIRMATION_EMAIL,mapAppointmnetObject(appointment),"Conformation email");
  }

  public void sendReminderEmail(Appointment appointment,long minutes){
    String customerEmail=appointment.getCustomer().getEmail();
    Map<String,Object> model=mapAppointmnetObject(appointment);
    model.put("minutes",minutes);
    sendEmail(new String[]{customerEmail},EmailTemplate.REMINDER_EMAIL,model,"Reminder email");
  }

  private Map<String, Object> mapAppointmnetObject(Appointment appointment){
    Map<String,Object> model =new HashMap<>();
    model.put("id",appointment.getId());
    model.put("name",appointment.getCustomer().getName());
    model.put("email",appointment.getCustomer().getEmail());
    model.put("gender",appointment.getCustomer().getGender());
    model.put("mobile",appointment.getCustomer().getMobile());
    model.put("barberName",appointment.getBarber().getName());
    model.put("bookingDate",appointment.getBookingDate());
    model.put("bookingTime",appointment.getStartTime());
    return model;

  }

  private String getTemplateLocation(EmailTemplate emailTemplates) {

    return String.format(
        "%s%s%s",
        EMAIL_TEMPLATE_PATH,
        emailTemplates.name().toLowerCase(Locale.getDefault()),
        EMAIL_TEMPLATE_PATH_EXTENSION);
  }

  private String getTemplate(String templateLocation, Map<String, Object> model) {

    StringWriter result = new StringWriter();
    VelocityContext velocityContext = new VelocityContext(model);
    velocityEngine.mergeTemplate(templateLocation, "utf-8", velocityContext, result);
    return result.toString();
  }

  private void sendEmail(
      String[] to, EmailTemplate template, Map<String, Object> model, String subject) {

    String messageBody = getTemplate(getTemplateLocation(template), model);
    MimeMessagePreparator mimeMessagePreparator =
        mimeMessage -> {
          MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
          message.setTo(to);
          message.setFrom(from);
          message.setText(messageBody, true);
          message.setSubject(subject);
        };
    log.info("Sending mail from {} to {}, template {}", from, to, template);
    try{
    this.mailSender.send(mimeMessagePreparator);
    }catch (Exception e){
      log.error("Error occurred while sending email",e);
    }
  }
}
