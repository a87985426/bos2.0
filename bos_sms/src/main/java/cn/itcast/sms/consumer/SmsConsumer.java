package cn.itcast.sms.consumer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Service;
@Service
public class SmsConsumer implements MessageListener {

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		MapMessage mapMessage = (MapMessage) message;
		// String sendSmsByHTTP = SmsUtils.sendSmsByHTTP(model.getTelephone(),
				// "尊敬的用户您好，本次获取的验证码为："
				// + randomCode + ",服务电话：4006184000");
		String sendSmsByHTTP = "000/xxx";
		if (sendSmsByHTTP.startsWith("000")) {
			try {
				System.out.println("发送短信成功,手机号为:"+mapMessage.getString("telephone")+"内容为:"+mapMessage.getString("content"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("发送失败,信息码为:" + sendSmsByHTTP);
		}
	}
	
}
