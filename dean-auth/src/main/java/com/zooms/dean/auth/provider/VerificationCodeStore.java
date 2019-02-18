package com.zooms.dean.auth.provider;

import java.util.Set;

public interface VerificationCodeStore {

    void storeVerificationCode(String phone, String code);

    Set<String> findVerificationCode(String phone);

}
