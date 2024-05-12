package com.food.Sec_web.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @JsonProperty("access_token")
    private String accessToken;
}
