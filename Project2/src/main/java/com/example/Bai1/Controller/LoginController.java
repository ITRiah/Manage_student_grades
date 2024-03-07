package com.example.Bai1.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bai1.Service.JWTService;
import com.example.Bai1.dto.ResponseDTO;

@RestController
public class LoginController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JWTService jwtService;
	
	@PostMapping("/login")
	public ResponseDTO<String> login(@RequestParam("username") String username,
						@RequestParam("password") String password){
		
		//check tài khoản, mật khẩu
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		
		//login success, gen token
		return ResponseDTO.<String>builder()
					.status(200)
					.msg("ok")
					.data(jwtService.create(username))
					.build();
	}
	
	
	
}
