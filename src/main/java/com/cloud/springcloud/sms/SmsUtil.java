package com.cloud.springcloud.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Author: lixu
 * @Dcscription:
 * @Date: Created in 15:26 2018/1/29
 * @Modified By:
 */
public class SmsUtil
{
    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIUorksQ0fDUJU";
    static final String accessKeySecret = "42L3Iu0IPEdQs1it1TykYovXZ5jBOT";

    /**
     * 短信发送主入口
     * @param mobile
     * @param signname
     * @param sms_tempno
     * @param templateparam
     * @param yzmlx
     * @return
     * @throws ClientException
     */
    public static SendSmsResponse sendSms(String mobile, String signname, String sms_tempno, String templateparam, int yzmlx) throws ClientException
    {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(mobile);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signname);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(sms_tempno);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//        request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
        request.setTemplateParam(templateparam);
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId(yzmlx+"");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return sendSmsResponse;
    }

    /**
     * 短信模板01
     * @param mobile
     * @param param
     * @return
     * @throws ClientException
     */
    public static SendSmsResponse sendMsg(String mobile, String param,int yzmlx) throws ClientException
    {
        String sms_tempno = "";
        if (yzmlx==1)
            sms_tempno = "SMS_123820178";
        if (yzmlx==2)
            sms_tempno = "SMS_123820182";
        if (yzmlx==3)
            sms_tempno = "SMS_123820177";
        if (yzmlx==4)
            sms_tempno = "SMS_123820176";
        if (yzmlx==5)
            sms_tempno = "SMS_123820176";
        if (yzmlx==6)
            sms_tempno = "SMS_123820176";
        if (yzmlx==7)
            sms_tempno = "SMS_123820176";
        if (yzmlx==8)
            sms_tempno = "SMS_123820176";
        if (yzmlx ==9)
            sms_tempno = "SMS_123798949";
        if (yzmlx == 10)
            sms_tempno = "SMS_142951627";
        return sendSms(mobile, "琳琅商城", sms_tempno, param, yzmlx);
    }

    /**
     * 短信明细查询
     * @param bizId
     * @return
     * @throws ClientException
     */
    public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("15000000000");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }

    public static void main(String[] args) throws ClientException, InterruptedException {

        //发短信
        SendSmsResponse response = sendMsg("15810172080","{\"code\":\"1101\"}",2);
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());

        Thread.sleep(3000L);

        //查明细
        if(response.getCode() != null && response.getCode().equals("OK"))
        {
            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
            System.out.println("短信明细查询接口返回数据----------------");
            System.out.println("Code=" + querySendDetailsResponse.getCode());
            System.out.println("Message=" + querySendDetailsResponse.getMessage());
            int i = 0;
            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
            {
                System.out.println("SmsSendDetailDTO["+i+"]:");
                System.out.println("Content=" + smsSendDetailDTO.getContent());
                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
            }
            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
        }
    }

    /**
     * 生成6位随机数,并且拼接字符串
     * @return
     */
    public static String getRandomCount() {
        String str = "";
        Random ran = new Random();
        for (int i = 0; i < 6; i++) {
            str += ran.nextInt(9);
        }
        return str;
    }
}
