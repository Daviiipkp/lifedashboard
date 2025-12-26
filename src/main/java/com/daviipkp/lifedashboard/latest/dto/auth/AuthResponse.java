package com.daviipkp.lifedashboard.latest.dto.auth;

import com.daviipkp.lifedashboard.latest.instance.UserData;

public record AuthResponse(boolean success, String message, UserData user, String token) {
}
