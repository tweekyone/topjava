package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.meal.AbstractMealController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping
    public String doGet(HttpServletRequest request) {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete" -> {
                super.delete(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("meals", super.getAll());
                return "meals";
            }
            case "create", "update" -> {
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        super.get(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("meal", meal);
                return "mealForm";
            }
            case "filter" -> {
                LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
                LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
                LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
                LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
                request.setAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
                return "meals";
            }
            default -> {
                request.setAttribute("meals", super.getAll());
                return "meals";
            }
        }
    }

    @PostMapping
    public String doPost(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.hasLength(request.getParameter("id"))) {
            super.update(meal, Integer.parseInt(request.getParameter("id")));
        } else {
            super.create(meal);
        }
        request.setAttribute("meals", super.getAll());
        return "meals";
    }
}
