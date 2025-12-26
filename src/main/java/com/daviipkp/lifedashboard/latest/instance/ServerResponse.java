package com.daviipkp.lifedashboard.latest.instance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServerResponse {

    private String message;
    private int status;
    private ResponseType type;
}
