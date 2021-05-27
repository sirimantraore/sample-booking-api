package com.booking.api.resources;

import com.booking.api.entity.Apartment;
import com.booking.api.entity.model.BookingStatus;
import com.booking.api.entity.model.City;
import com.booking.api.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cities")
public class CityResource {

    private static final int ONE_DAY = 1;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @GetMapping
    public List<City> getCities(@RequestParam("beginDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date beginDate,
                                @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
                                @RequestParam(value = "status", defaultValue = "VALIDATED") BookingStatus status) {
        endDate = endDate != null ? (updateDate(endDate)) : endDate;

        List<Apartment> apartments = apartmentRepository.findAllApartmentsAvailable(beginDate, endDate, status);
        return apartments.stream()
                .filter(distinctByKey(apartment -> apartment.getAddress().getPostalCode()))
                .map(apartment -> new City(apartment.getAddress().getPostalCode(), apartment.getAddress().getCity()))
                .collect(Collectors.toList());
    }


    @GetMapping("/apartments")
    public List<Apartment> searchAvailableApartments(@RequestParam("beginDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date beginDate,
                                                @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
                                                @RequestParam(value = "status", defaultValue = "VALIDATED") BookingStatus status) {
        if (endDate != null) {
            updateDate(endDate);
        }
        return apartmentRepository.findAllApartmentsAvailable(beginDate, endDate, status);
    }

    /**
     * Apartments by postal code
     */
    @GetMapping("/{postalCode}/apartments")
    public List<Apartment> searchAvailableApartmentByCity(@PathVariable @NotNull String postalCode,
                                                     @RequestParam("beginDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date beginDate,
                                                     @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
                                                     @RequestParam(value = "status", defaultValue = "VALIDATED") BookingStatus status) {
        if (endDate != null) {
            updateDate(endDate);
        }
        return apartmentRepository.findAllApartmentsByCityAvailable(beginDate, endDate, status, postalCode);
    }

    private Date updateDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, ONE_DAY);
        return calendar.getTime();
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
