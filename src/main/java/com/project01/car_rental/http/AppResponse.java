package com.project01.car_rental.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;

public class AppResponse {
    private static HashMap<String,Object> response;

    public static AppResponse success() {
        response = new HashMap<>();
        response.put("status", "Success");
        response.put("code", HttpStatus.OK.value());

        return new AppResponse();
    }

    public static AppResponse error() {
        response = new HashMap<>();
        response.put("status", "Error");
        response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new AppResponse();
    }

    public AppResponse withCode(HttpStatus code) {
        response.put("code", code.value());
        return this;
    }

    public AppResponse withMessage(String message) {
        response.put("message", message);
        return this;
    }

    public AppResponse withData(Object data) {
        response.put("data", data);
        return this;
    }

    public AppResponse withDataAsArray(Object data) {
        ArrayList<Object> list = new ArrayList<>();
        list.add(data);
        return withData(list);
    }

    public ResponseEntity<Object> build() {
        int code = (Integer) response.get("code");
        return new ResponseEntity<>(response, HttpStatus.valueOf(code));
    }
}
