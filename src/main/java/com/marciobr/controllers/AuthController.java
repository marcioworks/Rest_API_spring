package com.marciobr.controllers;

import  static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marciobr.repositories.UserRepository;
import com.marciobr.security.AccountCredentialVO;
import com.marciobr.security.jwt.JwtTokenProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags = "AuthenticationEndPoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserRepository repository;

	@ApiOperation(value="authenticate one user and create a token")
	@PostMapping(value = "/signin", produces = { "application/json", "application/xml",
			"application/x-yaml" }, consumes = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<?> signin(@RequestBody AccountCredentialVO account) {
		var username = account.getUsername();
		var password = account.getPassword();

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		var user = repository.findByUsername(username);
		var token = "";

		if (username != null) {
			token = jwtTokenProvider.createToken(username, user.getRoles());
		} else {
			throw new UsernameNotFoundException("username " + username + " not found");
		}
		
		Map<Object,Object> model = new HashMap<>();
		model.put("username", username);
		model.put("token", token);
		return ok(model);
	}
}
