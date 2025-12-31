package com.daviipkp.lifedashboard.latest.dto.auth;

import com.daviipkp.lifedashboard.latest.instance.UserAuthData;

public record AuthResponse(boolean success, String message, UserAuthData user, String token) {
}
