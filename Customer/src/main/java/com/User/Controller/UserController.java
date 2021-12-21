package com.User.Controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.User.Model.Coupon;
import com.User.Model.JwtRequest;
import com.User.Model.JwtResponse;
import com.User.Model.User;
import com.User.Repository.UserRepository;
import com.User.Utility.JWTUtility;



@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    RestTemplate restTemplate;
    
	@Autowired
	private JWTUtility jwtUtility;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@GetMapping("/")
	public String home() {
		return "Welocome Admin";
	}
	@PostMapping("/authenticate")
	public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest ) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							jwtRequest.getUsername(),
							jwtRequest.getPassword()
							)
					);
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid Credentials",e);
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		final String token = jwtUtility.generateToken(userDetails);
		return new JwtResponse(token);
	} 
    
    
    


    @GetMapping("/list")// requesting for the list of users.
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    
    @PostMapping("/addUser") // Adding new user
    public String addUser(@RequestBody User user){
        userRepository.save(user);
        return "user Added Succesfully";
    }
    
    @DeleteMapping("/delete/{userID}") // deleting user with specific id
    public String deleteUser(@PathVariable String userID) {
        userRepository.deleteById(userID);
        return "User With Id = "+ userID +" Deleted Successfully";
    }
   
    @PutMapping("/update/{userID}")// updating details of existing user  
    public String updateUser(@RequestBody User user, @PathVariable String userID){
        userRepository.save(user);
        return "user with ID ="+userID+"is Updated Succesfully";
    }
    
    //---------------coupon controller-------------------------------
    @GetMapping(value = "/listCoupon")
    public List<Coupon> getAllCoupons(){
        return Arrays.asList(restTemplate.getForObject("http://coupon-service/coupons/list",Coupon[].class));
    }

}
