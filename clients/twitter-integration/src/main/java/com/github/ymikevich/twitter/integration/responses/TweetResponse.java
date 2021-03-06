package com.github.ymikevich.twitter.integration.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * The type Tweet response.
 */
@Getter
@Setter
@Builder
public class TweetResponse {
    private Long id;
    private LocalDateTime createdAt;
    private String text;
    private String source;
    private int favoriteCount;
    private int retweetCount;
    private TwitterUser user;
}
