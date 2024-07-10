package com.example.User.Service;

import com.example.User.Entity.UserEntity;
import com.example.User.Reponsitory.AuthenticationRepositories;
import com.example.User.Request.AuthenticationRequest;
import com.example.User.Request.logoutRequest;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.Data;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class AuthenticationImpl implements AuthenticationService {
    @Autowired
    AuthenticationRepositories authenticationRepositories;

    @Override
    public String authenticate(AuthenticationRequest request) {
        Optional<UserEntity> userOptional = authenticationRepositories.findByUsername(request.getUsername());
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                try {
                    var token = generateToken(user);
                    return token;
                } catch (JOSEException e) {
                    e.printStackTrace();
                    return "Đã xảy ra lỗi khi tạo token!";
                }
            } else {
                return "UserName hoặc Password không đúng!";
            }
        } else {
            return "UserName hoặc Password không đúng!";
        }
    }

    @NonFinal
    protected static final String Singer_key = "sCj1CBV+VSn6qlZMQxQ0eSEpFsey7Zqp2gaVmLR/3LgOc0UTappt5pCypZt/PWsa";

    String generateToken(UserEntity user) throws JOSEException {
        //tao header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        //tao payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("yyy.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope",buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        jwsObject.sign(new MACSigner(Singer_key.getBytes()));
        return jwsObject.serialize();
    }
    private String buildScope(UserEntity user) {
        StringJoiner scope = new StringJoiner(" ");
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            String[] rolesArray = user.getRoles().split(",");
            for (String role : rolesArray) {
                scope.add(role.trim());
            }
        }
        return scope.toString();
    }
    private SignedJWT verify(String token) throws ParseException, JOSEException {
        JWSVerifier verifier = new MACVerifier(Singer_key);
        SignedJWT jwt = SignedJWT.parse(token);
        Date expirationTime = jwt.getJWTClaimsSet().getExpirationTime();
        var verify = jwt.verify(verifier);
        if(!verify && expirationTime.after(new Date()))
            throw new RuntimeException("");
        return jwt;
    }
    public void logout(logoutRequest request){

    }


}
