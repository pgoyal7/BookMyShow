package com.book.my.show.request;

import com.book.my.show.type.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@NoArgsConstructor
public class BookMovieTicket {//TODO CHECK ON EACH FIEDL INCLUDING ENUMCLASS PAYMENT TYPE ELSE BAD REQUEST
    private UserInfo userInfo;
    private TheatreInfo theatreInfo;
    private PaymentType paymentType;
}
