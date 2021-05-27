package com.booking.api.resources;

import com.booking.api.entity.Booking;
import com.booking.api.entity.model.BookingStatus;
import com.booking.api.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingResource {

    @Autowired
    private BookingRepository repository;

    private static final int ONE_DAY = 1;

    @PostMapping
    public List<Booking> create(@RequestBody List<Booking> bookings) {
        return repository.saveAll(bookings);
    }

    @PutMapping
    public Booking update(@RequestBody Booking booking) {
        return repository.save(booking);
    }

    @GetMapping("/{reference}")
    public Booking getBookingByRef(@PathVariable @NotNull String reference) {
        return repository.findFirstByRef(reference);
    }

    @GetMapping
    public List<Booking> findBookingsAvailable() {
        return repository.findAll();
    }

    @GetMapping("/search")
    public List<Booking> searchByDate(@RequestParam("beginDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date beginDate,
                                      @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
                                      @RequestParam(value = "status", defaultValue = "VALIDATED") BookingStatus status) {
        if (endDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDate);
            calendar.add(Calendar.DATE, ONE_DAY);
            endDate = calendar.getTime();
        }
        return repository.findAllBetweenBeginDateAndEndDate(beginDate, endDate, status);
    }
}
