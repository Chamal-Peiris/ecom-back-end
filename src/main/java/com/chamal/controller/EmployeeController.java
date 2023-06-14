package com.chamal.controller;
import com.chamal.constant.UserRole;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public String getEmployees() throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

       if(! authentication.getAuthorities().stream().anyMatch(authority -> authority.equals(new SimpleGrantedAuthority("ADMIN")))){
           throw new Exception("Admin Role not found");
       }
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails){


            UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();

            WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
            String ipAddress = details.getRemoteAddress();

            return "Logged in as: "+ username+"With ip address: "+ ipAddress;
        }else {
            return principal. toString();
        }
    }
}