package com.zooms.dean.auth.provider;

import com.zooms.dean.auth.exceptions.SendLimitException;

public interface SendMessageLimit {

    void filter(String phone) throws SendLimitException;

}
