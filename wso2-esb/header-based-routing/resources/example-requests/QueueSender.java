import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class QueueSender {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");

        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("JMSIn");

        MessageProducer producer = session.createProducer(queue);

        TextMessage msg = session.createTextMessage();
        msg.setText(
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "       <soapenv:Body>" +
                "           <m0:CheckPriceRequest xmlns:m0=\"http://services.samples\">" +
                "               <m0:Code>WSO2</m0:Code>" +
                "           </m0:CheckPriceRequest>" +
                "       </soapenv:Body>\n" +
                "</soapenv:Envelope>");

        msg.setStringProperty("ClientId", "App1");
        producer.send(msg);
        session.close();
        connection.close();
    }
}
