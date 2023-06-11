package com.chamal.service;

import com.chamal.constant.UserRole;
import com.chamal.model.UserDao;
import com.chamal.dto.UserDto;
import com.chamal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDao user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		List<GrantedAuthority> authorities = getAuthorities(user.getRole());

		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				authorities
		);
	}
	private List<GrantedAuthority> getAuthorities(Set<UserRole> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (UserRole role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.name()));
		}
		return authorities;
	}
	public UserDao save(UserDto user) {
		UserDao newUser = new UserDao();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setRole(new HashSet<>(user.getUserRole()));
		return userDao.save(newUser);
	}
}