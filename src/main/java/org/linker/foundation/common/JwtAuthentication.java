package org.linker.foundation.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.Verification;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.linker.foundation.authority.AuthoritySession;
import org.linker.foundation.utils.DateUtils;
import org.linker.foundation.utils.DesAlgorithm;

import java.util.Date;
import java.util.List;

public class JwtAuthentication {

    private final String secret;

    public static JwtAuthentication create(String secret) {
        return new JwtAuthentication(secret);
    }

    private final JWTCreator.Builder jwtBuilder;
    private final Verification verification;
    private final DesAlgorithm algorithm;

    private JwtAuthentication(String secret) {
        try {
            this.secret = secret;
            this.jwtBuilder = JWT.create()
                    .withAudience("ruochuchina.com")
                    .withClaim(PublicClaims.ISSUER, "http://ruochuchina.com");

            this.verification = JWT.require(Algorithm.HMAC256(secret))
                    .withAudience("ruochuchina.com")
                    .withClaim(PublicClaims.ISSUER, "http://ruochuchina.com")
                    .acceptLeeway(1L);

            this.algorithm = DesAlgorithm.create(secret);
        } catch (Exception e) {
            throw new RuntimeException("initial jwt authentication error......", e);
        }
    }

    private String joiner(Object... objects) {
        return Joiner.on(":").join(objects);
    }

    public String calPubKey(String userId, String roleId) {
        long now = DateUtils.time();
        String msg = now % 2 == 0 ? joiner(userId, now, roleId) : joiner(roleId, now, userId);
        return algorithm.encrypt(msg);
    }

    public AuthoritySession verifyPubKey(String pubKey) {
        String msg = algorithm.decrypt(pubKey);
        List<String> msgList = Splitter.on(":").splitToList(msg);
        if (msgList.size() == 3) {
            boolean even = 0 == (Long.parseLong(msgList.get(1)) % 2);
            AuthoritySession session = new AuthoritySession();
            session.userId = even ? msgList.get(0) : msgList.get(2);
            session.roleId = even ? msgList.get(2) : msgList.get(0);
            return session;
        }
        throw new RuntimeException("verify pubKey error");
    }

    public String calSignature(String userId, String roleId, long expireTime) {
        try {
            Date iat = DateUtils.now();
            Date exp = DateUtils.ofDate(iat.getTime() + expireTime);
            return jwtBuilder.withClaim(PublicClaims.SUBJECT, joiner(userId, roleId))
                    .withClaim(PublicClaims.ISSUED_AT, iat)
                    .withClaim(PublicClaims.EXPIRES_AT, exp)
                    .withClaim(PublicClaims.NOT_BEFORE, iat)
                    .sign(Algorithm.HMAC256(secret));
        } catch (Exception e) {
            throw new RuntimeException("calculate signature error.....", e);
        }
    }

    public AuthoritySession verifySignature(String pubKey, String signature) {
        AuthoritySession session = verifyPubKey(pubKey);
        if (null != session) {
            try {
                JWTVerifier verifier = verification
                        .withClaim(PublicClaims.SUBJECT, joiner(session.userId, session.roleId))
                        .build();
                verifier.verify(signature);
            } catch (Exception e) {
                throw new RuntimeException("verify signature error", e);
            }
        }
        return session;
    }

}