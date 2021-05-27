package com.booking.api.repository;

import com.booking.api.entity.Booking;
import com.booking.api.entity.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking findFirstByRef(String ref);

    @Query("select b from Booking b where b.beginDate >= :beginDate and b.endDate <= :endDate and " +
            "b.status = :status")
    List<Booking> findAllBetweenBeginDateAndEndDate(@Param("beginDate") @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                            Date beginDate,
                                                    @Param("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                            Date endDate,
                                                    @Param("status") BookingStatus status);
}
