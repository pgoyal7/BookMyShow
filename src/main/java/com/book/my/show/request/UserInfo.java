package com.book.my.show.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@NoArgsConstructor
public class UserInfo {
    //validation
    private String name;
    private String email;
    private String mobileNumber;
}