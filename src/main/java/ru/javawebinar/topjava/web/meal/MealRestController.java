package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";

    @Autowired
    ConversionService conversionService;

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Meal create(@RequestBody Meal meal) {
        return super.create(meal);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAll() {
        return super.getAll();
    }

    // With default Spring Conversion Service
//    @Override
//    @GetMapping(value = "/between", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<MealTo> getBetween(@RequestParam("startDate") @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
//                                   @RequestParam("startTime") @DateTimeFormat(iso = ISO.TIME) LocalTime startTime,
//                                   @RequestParam("endDate") @DateTimeFormat(iso = ISO.DATE) LocalDate endDate,
//                                   @RequestParam("endTime") @DateTimeFormat(iso = ISO.TIME) LocalTime endTime) {
//        return super.getBetween(startDate, startTime, endDate, endTime);
//    }

    // With ustom SPI converters
    @GetMapping(value = "/between", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getBetween(@RequestParam("startDate") String startDateParam,
                                   @RequestParam("startTime") String startTimeParam,
                                   @RequestParam("endDate") String endDateParam,
                                   @RequestParam("endTime") String endTimeParam) {
        LocalDate startDate = conversionService.convert(startDateParam, LocalDate.class);
        LocalTime startTime = conversionService.convert(startTimeParam, LocalTime.class);
        LocalDate endDate = conversionService.convert(endDateParam, LocalDate.class);
        LocalTime endTime = conversionService.convert(endTimeParam, LocalTime.class);
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}