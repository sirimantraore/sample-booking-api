package com.booking.api.repository;

import com.booking.api.entity.Apartment;
import com.booking.api.entity.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    Apartment findByRef(String ref);

    @Query("select a from Apartment a where a.ref not in (select b.apartment.ref from Booking b " +
            "where b.beginDate >= :beginDate and b.endDate <= :endDate and b.status =:status)")
    List<Apartment> findAllApartmentsAvailable(@Param("beginDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date beginDate,
                                               @Param("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
                                               @Param("status") BookingStatus status);

    @Query("select a from Apartment a where a.address.postalCode = :postalCode and a.ref not in " +
            "(select b.apartment.ref from Booking b where b.beginDate >= :beginDate and b.endDate <= :endDate " +
            "and b.status = :status)")
    List<Apartment> findAllApartmentsByCityAvailable(@Param("beginDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date beginDate,
                                               @Param("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
                                               @Param("status") BookingStatus status,
                                               @Param("postalCode") String postalCode);
}
