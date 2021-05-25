package com.book.my.show.service.impl;

import com.book.my.show.entity.BookShowSeat;
import com.book.my.show.entity.Ticket;
import com.book.my.show.entity.User;
import com.book.my.show.exception.BookingNotPossibleException;
import com.book.my.show.exception.ErrorMapping;
import com.book.my.show.factory.PaymentFactory;
import com.book.my.show.repository.BookShowSeatRepository;
import com.book.my.show.repository.TicketRepository;
import com.book.my.show.repository.UserRepository;
import com.book.my.show.repository.specification.BookShowSeatSpecification;
import com.book.my.show.request.BookMovieTicket;
import com.book.my.show.request.BookMovieTicketRequest;
import com.book.my.show.request.TheatreInfo;
import com.book.my.show.request.UserInfo;
import com.book.my.show.response.BookMovieTicketResponse;
import com.book.my.show.service.IBookMovieTicketService;
import com.book.my.show.type.BookStatus;
import com.book.my.show.type.SeatStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class BookMovieTicketService implements IBookMovieTicketService {
    private final BookShowSeatRepository bookShowSeatRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public BookMovieTicketService(BookShowSeatRepository bookShowSeatRepository,
                                  UserRepository userRepository, TicketRepository ticketRepository) {
        this.bookShowSeatRepository = bookShowSeatRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    @Override
    public BookMovieTicketResponse bookMovieTicket(BookMovieTicketRequest bookMovieTicketRequest) throws Exception {
        BookMovieTicket bookMovieTicket = bookMovieTicketRequest.getBookMovieTicket();
        TheatreInfo theatreInfo = bookMovieTicket.getTheatreInfo();
        UserInfo userInfo = bookMovieTicket.getUserInfo();

        List<BookShowSeat> bookShowSeats =
                bookShowSeatRepository.findAll(createBookShowSeatSpecification(theatreInfo));

        if(!CollectionUtils.isEmpty(bookShowSeats)) {
            if(isUserRequestedSeatsAvailable(theatreInfo, bookShowSeats)) {
                blockMovieSeats(bookShowSeats);

                final double totalPayableAmount = calculateTotalPayableAmount(theatreInfo);

                if(processPaymentViaUserProvidedPaymentOption(bookMovieTicket, totalPayableAmount)) {
                    return new BookMovieTicketResponse()
                            .setBookStatus(BookStatus.BOOKED)
                            .setTicketId(createTicket(userInfo, bookShowSeats, totalPayableAmount).getTicketId());
                }
                log.info("Failed to process the payment due to some issues at payment service side/ due to insufficient balance for theatre : {}", theatreInfo.getTheatreName());
                throw new BookingNotPossibleException(HttpStatus.OK, ErrorMapping.BMS016);
            }
            log.info("Already filled some of the seats that user has requested for theatre : {}", theatreInfo.getTheatreName());
            throw new BookingNotPossibleException(HttpStatus.OK, ErrorMapping.BMS017);
        }
        log.info("No seat left as per the user's seat combination request for theatre : {}", theatreInfo.getTheatreName());
        throw new BookingNotPossibleException(HttpStatus.OK, ErrorMapping.BMS018);
    }

    private BookShowSeatSpecification createBookShowSeatSpecification(TheatreInfo theatreInfo) {
        return BookShowSeatSpecification.builder()
                .showDay(theatreInfo.getShowDay())
                .showTime(theatreInfo.getShowTime())
                .auditoriumName(theatreInfo.getAuditoriumName())
                .cityName(theatreInfo.getCityName())
                .movieName(theatreInfo.getMovieName())
                .theatreName(theatreInfo.getTheatreName())
                .seatInfoList(theatreInfo.getRequestedSeatList())
                .build();
    }

    private boolean isUserRequestedSeatsAvailable(TheatreInfo theatreInfo, List<BookShowSeat> bookShowSeats) {
        return bookShowSeats.size() != theatreInfo.getRequestedSeatList().size();
    }

    private void blockMovieSeats(List<BookShowSeat> bookShowSeats) {
        bookShowSeats.forEach((bookShowSeat) -> {
            bookShowSeat.setStatus(SeatStatus.BLOCKED);
        });
        bookShowSeatRepository.saveAll(bookShowSeats);
    }

    private double calculateTotalPayableAmount(TheatreInfo theatreInfo) {
        return theatreInfo.getRequestedSeatList().stream()
                .mapToDouble((seat) -> seat.getSeatType().getPrice()).sum();
    }

    private boolean processPaymentViaUserProvidedPaymentOption(BookMovieTicket bookMovieTicket, double totalPayableAmount) throws Exception {
        return PaymentFactory.PAYMENT_TYPE_CLASS_MAP.get(bookMovieTicket.getPaymentType())
                .newInstance().processPayment(totalPayableAmount);
    }

    private Ticket createTicket(UserInfo userInfo, List<BookShowSeat> bookShowSeats, double totalPayableAmount) {
        Ticket ticket = new Ticket()
                .setUser(createUserRepresentation(userInfo))
                .setBookShowSeats(changeTheSeatStatusFromBlockToBook(bookShowSeats))
                .setTicketAmount(totalPayableAmount)
                .setStatus(BookStatus.BOOKED)
                .setTicketId(UUID.randomUUID().toString().substring(0, 18));
        return ticketRepository.save(ticket);
    }

    private User createUserRepresentation(UserInfo userInfo) {
        User user = new User()
                .setName(userInfo.getName())
                .setEmail(userInfo.getEmail())
                .setMobileNumber(userInfo.getMobileNumber())
                .setUserId(UUID.randomUUID().toString().substring(0, 6));
        return userRepository.save(user);
    }

    private List<BookShowSeat> changeTheSeatStatusFromBlockToBook(List<BookShowSeat> bookShowSeats) {
        bookShowSeats.forEach((bookShowSeat) -> {
            bookShowSeat.setStatus(SeatStatus.BOOKED);
        });
        return bookShowSeats;
    }
}
