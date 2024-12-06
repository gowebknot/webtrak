package com.webknot.webtrak.util;

import com.webknot.webtrak.exception.BadRequestException;

public class Utils {

    public static void verifyPassword(String str, String match) {
        if (!str.equalsIgnoreCase(match)) {
            throw new BadRequestException("Not Authorized");
        }
    }

}
