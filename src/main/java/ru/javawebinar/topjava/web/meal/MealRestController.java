package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealTo> getAll(){
        log.info("getAll meal");
        return service.getAll(SecurityUtil.authUserId(), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal get(int id){
        log.info("get meal {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal){
        log.info("create meal {}", meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int id){
        log.info("delete meal {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id){
        log.info("update meal {} with id={}", meal, id);
        ValidationUtil.assureIdConsistent(meal, id);
        service.update(meal, id);
    }
}