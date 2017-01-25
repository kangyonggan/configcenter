package com.kangyonggan.app.configcenter.configcenter.biz.service.impl;

import com.kangyonggan.app.configcenter.configcenter.biz.service.TokenService;
import com.kangyonggan.app.configcenter.configcenter.biz.util.Digests;
import com.kangyonggan.app.configcenter.configcenter.biz.util.Encodes;
import com.kangyonggan.app.configcenter.configcenter.model.annotation.LogTime;
import com.kangyonggan.app.configcenter.configcenter.model.constants.AppConstants;
import com.kangyonggan.app.configcenter.configcenter.model.vo.Token;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author kangyonggan
 * @since 2017/1/19
 */
@Service
public class TokenServiceImpl extends BaseService<Token> implements TokenService {

    @Override
    @LogTime
    public String saveToken(String type, Long userId) {
        Token token = new Token();
        token.setType(type);
        token.setUserId(userId);

        String code = genTokenCode();
        token.setCode(code);
        token.setExpireTime(new Date(new Date().getTime() + 2 * 60 * 60 * 1000));// 2小时

        super.insertSelective(token);
        return code;
    }

    @Override
    @LogTime
    public Token findTokenByCode(String code) {
        Token token = new Token();
        token.setCode(code);

        return super.selectOne(token);
    }

    @Override
    @LogTime
    public void updateToken(Token token) {
        super.updateByPrimaryKeySelective(token);
    }

    private String genTokenCode() {
        byte[] hashKey = Digests.sha1(Digests.generateSalt(AppConstants.SALT_SIZE));
        return Encodes.encodeHex(hashKey);
    }
}
