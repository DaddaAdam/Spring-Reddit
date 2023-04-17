package com.emsi.springreddit.dto.response;

public record AuthenticationResponse(int status, String message, String token, String error) {
}
