package rs.ac.uns.ftn.kts.students.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.kts.students.authmodel.AuthenticationRequest;
import rs.ac.uns.ftn.kts.students.authmodel.AuthenticationResponse;
import rs.ac.uns.ftn.kts.students.service.UserService;
import rs.ac.uns.ftn.kts.students.util.JwtUtil;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @RequestMapping(value ="/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );

        }catch (BadCredentialsException e) {
            throw new Exception ("netacni podaci username/password", e);
        }
        final UserDetails userDetails = userService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        String uloga = "";
        for (GrantedAuthority authority : userDetails.getAuthorities())
            uloga = authority.getAuthority();

        return ResponseEntity.ok(new AuthenticationResponse(jwt,uloga));
    }

}
