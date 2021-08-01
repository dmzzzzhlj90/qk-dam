/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qk.dam.resourceserver.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Joe Grandja
 * @since 0.0.1
 */
@RestController
public class MessagesController {

	@Autowired
	private JwtDecoder jwtDecoder;

	@GetMapping("/messages")
	public String[] getMessages() {
		return new String[] {"Message 1", "Message 2", "Message 3"};
	}

	@GetMapping("/jwtDecoder")
	public String ssss() {
		Jwt decode = jwtDecoder.decode("eyJraWQiOiJxay1kYW0tY2xpZW50IiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJhZG1pbiIsImF1ZCI6InFrLWRhbS1jbGllbnQiLCJhenAiOiJxay1kYW0tY2xpZW50IiwiaXNzIjoiaHR0cDpcL1wvYXV0aC1zZXJ2ZXI6OTkwMSIsImV4cCI6MTYyNzczODIxNCwiaWF0IjoxNjI3NzM2NDE0LCJub25jZSI6IkdxVzUyTnA1Qlh5cnlBa1Q2dWxYUmJrLWxrZHp0UzBSU3gxOUlVTnlaaW8iLCJqdGkiOiI5NmQ0YjE1My1hOGIyLTRlNjEtODZjYi1hOGRlYjA4ODQ2NDIifQ.c4O-126arm6V0dQDBuG-wp5RXrXm5uLG1PxvZXJMuYZv1aXY_SbagaWzFFZzkNtDNBxQg2EGGI9niKkNsCAZLKB9ssr3tRi9V0xspLK__EwUJVuDAj_I7y3U-Nn8jb4tI7oV7qSDK77ka8IUYYQrGewEyWtWb37SzrpUdWRK9qkL2YtPXRWZBpV46P4ABEDrdsbsenlCRSkvD1ZvqNeYsiiy-9c2m5dJQRrjxCOagOrfz-QUY1askLvxE97V3fdY_vIB2b4vgToS6Q-rLzLoXEAZF8xggYbmzXTs2YbSO_HQnixF23sNhnz3ftHV5ni0OvDhTHnMXY_fv2BkjRLyNw");

		System.out.println(decode);


		return "s";
	}

	@GetMapping("/oauth2/idToken")
	@ResponseBody
	public DefaultOidcUser idToken(
			Authentication authentication, @AuthenticationPrincipal OAuth2User oauth2User) {
		return (DefaultOidcUser) authentication.getPrincipal();
	}
}
