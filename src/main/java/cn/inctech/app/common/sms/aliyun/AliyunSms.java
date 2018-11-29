package cn.inctech.app.common.sms.aliyun;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import cn.inctech.app.common.util.encr.AesUtil;

@Component
public class AliyunSms {

    public SendSmsResponse sendSms(Map<String, String> paramMap) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        //IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aes.unencr(access_key_id), aes.unencr(access_key_secret));
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(paramMap.get("PhoneNumbers"));
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(paramMap.get("SignName"));
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(paramMap.get("TemplateCode"));
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(paramMap.get("TemplateParam"));

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode(paramMap.get("extendCode"));

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId(paramMap.get("outId"));

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return sendSmsResponse;
    }
    
    static final String product = "Dysmsapi";
    static final String domain = "dysmsapi.aliyuncs.com";//产品域名,开发者无需替换
    // 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    
    @Resource AesUtil aes;
    @Value("${webapp.sms.access_key_id}") String access_key_id;
    @Value("${webapp.sms.access_key_secret}") String access_key_secret;
    final String accessKeyId = "LTAIBy05lplmc1wj";
    final String accessKeySecret = "DsraNFQM1J1QMIwgeFlBlI06FNqbzB";
}