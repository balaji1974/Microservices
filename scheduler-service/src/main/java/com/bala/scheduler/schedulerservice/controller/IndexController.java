package com.bala.scheduler.schedulerservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bala.scheduler.schedulerservice.entity.SchedulerJobInfo;
import com.bala.scheduler.schedulerservice.service.SchedulerJobService;


@Controller
public class IndexController {

	@Autowired
	private SchedulerJobService scheduleJobService;
	
	@GetMapping("/index")
	public String index(Model model){
		List<SchedulerJobInfo> jobList = scheduleJobService.getAllJobList();
		System.out.println("The total jobs that will be run is : "+ jobList.size());	
		model.addAttribute("jobs", jobList);
		return "index";
	}
	
}
