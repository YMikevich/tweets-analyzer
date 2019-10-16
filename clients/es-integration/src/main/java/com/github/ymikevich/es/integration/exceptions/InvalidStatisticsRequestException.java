package com.github.ymikevich.es.integration.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class InvalidStatisticsRequestException extends RuntimeException {

    private String username;
    private String fromDate;

    public InvalidStatisticsRequestException(final String username, final String fromDate) {
        this.username = username;
        this.fromDate = fromDate;
    }

    @Override
    public String getMessage() {
        return "User with username @" + username + " might" + " not exist or there are no tweets since "
                + fromDate + " date";
    }
}
