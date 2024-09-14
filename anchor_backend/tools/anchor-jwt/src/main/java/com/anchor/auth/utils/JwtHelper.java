package com.anchor.auth.utils;

import com.anchor.context.BaseContextConstants;
import com.anchor.exception.BizException;
import com.anchor.exception.code.ExceptionCode;
import com.anchor.utils.DateUtils;
import com.anchor.utils.StrHelper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;


import java.time.LocalDateTime;
import java.util.List;


@Slf4j
public class JwtHelper {
    private static final RsaKeyHelper RSA_KEY_HELPER = new RsaKeyHelper();

    /**
     * 生成用户token
     */
    public static Token generatorUserToken(JwtUserInfo jwtUserInfo,String keyPath,int expire) throws BizException {
        JwtBuilder jwtBuilder = Jwts.builder().setAudience(String.valueOf(jwtUserInfo.getId())).claim(BaseContextConstants.JWT_KEY_NAME, jwtUserInfo.getUserName()).claim(BaseContextConstants.JWT_KEY_ROLE, jwtUserInfo.getPermissions());
        return generatotToke(jwtBuilder, keyPath, expire);
    }

    /**
     * 生成token
     * @param jwtBuilder
     * @param priKeyPath
     * @param expire
     * @return
     * @throws BizException
     */
    protected static Token generatotToke (JwtBuilder jwtBuilder, String priKeyPath, int expire) throws BizException {
        try {
            String compactJwt = jwtBuilder.setExpiration(DateUtils.localDateTime2Date(LocalDateTime.now().plusDays(expire))).signWith(SignatureAlgorithm.RS256, RSA_KEY_HELPER.getPrivateKey(priKeyPath)).compact();
            return new Token(compactJwt,expire);
        }catch (Exception e){
            log.error("error:{},message:{}", ExceptionCode.JWT_GEN_TOKEN_FAIL.getCode(),e.getMessage());
            throw new BizException(ExceptionCode.JWT_GEN_TOKEN_FAIL.getCode(),ExceptionCode.JWT_GEN_TOKEN_FAIL.getMsg());
        }
    }

    public static JwtUserInfo getJwtFromToken(String token, String pubKeyPath) throws BizException {
        Jws<Claims> claimsJws = parserToken(token, pubKeyPath);
        Claims body = claimsJws.getBody();
        String strUserId = body.getSubject();
        String userName = StrHelper.getObjectValue(body.get(BaseContextConstants.JWT_KEY_NAME));
        List<String> permissions =(List) body.get(BaseContextConstants.JWT_KEY_ROLE);
//        String password = StrHelper.getObjectValue(body.get(BaseContextConstants.JWT_KEY_PASSWORD));
//        Long userId = NumberHelper.longValueOf0(strUserId);
        JwtUserInfo jwtUserInfo = new JwtUserInfo();
//        jwtUserInfo.setPassword(password);
        jwtUserInfo.setUserName(userName);
        jwtUserInfo.setPermissions(permissions);

        return jwtUserInfo;
    }

    /**
     * 公钥解析token
     */
    private static Jws<Claims> parserToken(String token, String pubKeyPath) throws BizException {
        try {
            return Jwts.parser().setSigningKey(RSA_KEY_HELPER.getPublicKey(pubKeyPath)).parseClaimsJws(token);
        }catch (ExpiredJwtException e){
            //过期
            throw new BizException(ExceptionCode.JWT_TOKEN_EXPIRED.getCode(),ExceptionCode.JWT_TOKEN_EXPIRED.getMsg());
        }catch (SignatureException ex){
            //签名错误
            throw new BizException(ExceptionCode.JWT_SIGNATURE.getCode(),ExceptionCode.JWT_SIGNATURE.getMsg());
        }
        catch (IllegalArgumentException ex){
            //token为空
            throw new BizException(ExceptionCode.JWT_ILLEGAL_ARGUMENT.getCode(),ExceptionCode.JWT_ILLEGAL_ARGUMENT.getMsg());
        }
        catch (Exception ex){
            //其他错误
            throw new BizException(ExceptionCode.JWT_PARSER_TOKEN_FAIL.getCode(),ExceptionCode.JWT_PARSER_TOKEN_FAIL.getMsg());
        }
    }
}
