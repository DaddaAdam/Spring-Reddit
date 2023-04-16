package com.emsi.springreddit.dto;

public record GenericResponse (int status, String message, String error, Object data){
}
