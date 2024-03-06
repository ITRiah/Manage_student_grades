package com.example.Bai1.jobscheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.Bai1.Service.EmailService;
import com.example.Bai1.entity.User;
import com.example.Bai1.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobSchedule {
	@Autowired
	UserRepo userRepo;

	@Autowired
	EmailService emailService;

	//@Scheduled(cron = "* * */6 * * *")
	public void hello() {
		LocalDate date = LocalDate.now();
		int day = date.getDayOfMonth();
		int month = date.getMonthValue();

		List<User> users = userRepo.searchByBirthDay(day, month);

		for (User user : users) {
			emailService.sendMailBirthday(user.getEmail(), user.getName());
		}
	}

	//@Scheduled(fixedDelay = 60000000)
	public void hello2() {
		System.out.println("hello");
	}
}
