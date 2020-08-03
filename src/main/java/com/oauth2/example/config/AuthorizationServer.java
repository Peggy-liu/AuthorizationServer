package com.oauth2.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService inMemoryUserDetailsManager;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			//client with password grant type
			.withClient("client1").secret("client1").scopes("read")
			.authorizedGrantTypes("password", "refresh_token")
		.and()
			//client with authorization grant type
			.withClient("client2")
			.secret("client2")
			.scopes("read")
			.authorizedGrantTypes("authorization_code", "refresh_token")
			.redirectUris("http//localhost:9090")
		.and()
			//client with client credential grant type
			.withClient("client3")
			.secret("client3")
			.scopes("read")
			.authorizedGrantTypes("client_credentials");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager);
		endpoints.userDetailsService(inMemoryUserDetailsManager);
	}

}
