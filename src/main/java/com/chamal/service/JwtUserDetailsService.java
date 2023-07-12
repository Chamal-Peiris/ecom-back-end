package com.chamal.service;

import com.chamal.constant.UserRole;
import com.chamal.model.User;
import com.chamal.dto.UserDto;
import com.chamal.repository.UserRepository;
import com.chamal.service.exception.DuplicateRecordException;
import com.chamal.service.exception.NotFoundException;
import com.chamal.service.util.EntityDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private EntityDtoConverter converter;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
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


	public User getLoggedUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Object principal = authentication.getPrincipal();
		User userDao = null;
		if (principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) principal;
			String username = userDetails.getUsername();
			userDao = userRepository.findByUsername(username);
		}
		if(userDao==null) throw new NotFoundException("No logged in user found");
		return userDao;
	}
	private List<GrantedAuthority> getAuthorities(Set<UserRole> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (UserRole role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.name()));
		}
		return authorities;
	}
	public User save(UserDto user) {

		if(userRepository.findByUsername(user.getUsername())!=null){
			throw new DuplicateRecordException("Username already exists");
		}

		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setRole(new HashSet<>(user.getUserRole()));
		return userRepository.save(newUser);
	}

	public UserDto getUser(Long userId){

		Optional<User> userDao = userRepository.findById(userId);

		if(!userDao.isPresent()) throw new NotFoundException("No User Found for the given id");
		return converter.getUserDto(userDao.get());
	}

	public List<UserDto> getUsers(){
		List<User> userList = userRepository.findAll();

		if(userList.isEmpty()) throw new NotFoundException("No Users found in the database");

		return userList.stream().map(userDao -> converter.getUserDto(userDao)).collect(Collectors.toList());

	}
}