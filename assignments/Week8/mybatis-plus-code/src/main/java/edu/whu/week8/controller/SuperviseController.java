package edu.whu.week8.controller;

import edu.whu.week8.aspect.SuperviseAspect;
import edu.whu.week8.aspect.SuperviseResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/supervise")
public class SuperviseController {

    final SuperviseAspect aspect;

    @Autowired
    public SuperviseController(SuperviseAspect aspect) {
        this.aspect = aspect;
    }

    @GetMapping("")
    public List<SuperviseResultDto> getSuperviseResult() {
        return aspect.getSuperviseResult();
    }
}
