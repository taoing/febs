package com.taoing.security.code.sms;

import com.taoing.security.code.ValidateCode;
import com.taoing.security.code.ValidateCodeGenerator;
import com.taoing.security.properties.FebsSecurityProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private FebsSecurityProperties securityProperties;

    @Override
    public ValidateCode createCode() {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
    }
}
