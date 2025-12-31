package com.daviipkp.lifedashboard.latest.controllers;

import com.daviipkp.lifedashboard.latest.dto.auth.AuthResponse;
import com.daviipkp.lifedashboard.latest.dto.auth.LoginCredentials;
import com.daviipkp.lifedashboard.latest.dto.auth.RegisterCredentials;
import com.daviipkp.lifedashboard.latest.instance.ResponseType;
import com.daviipkp.lifedashboard.latest.instance.ServerResponse;
import com.daviipkp.lifedashboard.latest.instance.UserAuthData;
import com.daviipkp.lifedashboard.latest.instance.enums.UserRole;
import com.daviipkp.lifedashboard.latest.repositories.UserRep;
import com.daviipkp.lifedashboard.latest.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRep userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginCredentials loginCredentials) {
        try {
            var userPass = new UsernamePasswordAuthenticationToken(loginCredentials.username(), loginCredentials.password());
            var auth = this.authenticationManager.authenticate(userPass);
            UserAuthData ud = (UserAuthData) auth.getPrincipal();

            var token = tokenService.generateToken(ud);
            return ResponseEntity.ok(new AuthResponse(true
                    , ""
                    , ud
                    , token));
        }catch (BadCredentialsException | UsernameNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ServerResponse("Incorrect user or password", 402, ResponseType.ERROR));
        }catch (DisabledException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ServerResponse("Account disabled. Contact support.", 403, ResponseType.ERROR));
        }catch (LockedException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ServerResponse("Account locked! Many login attempts.", 403, ResponseType.ERROR));
        }catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ServerResponse("There's already an account with this data.", 409, ResponseType.ERROR));
        }catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ServerResponse("Error. Contact support." , 500, ResponseType.ERROR));
        }

    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterCredentials registerCredentials) {
        try{
            if(this.userRepository.findByUsername(registerCredentials.username()).isPresent()) {
                throw new DataIntegrityViolationException("username");
            }else if(this.userRepository.findByEmail(registerCredentials.email()).isPresent()) {
                throw new DataIntegrityViolationException("e-mail");
            }
            String encryptedPassword = passwordEncoder.encode(registerCredentials.password());

            UserAuthData newUser = new UserAuthData(
                    registerCredentials.email()
                    , registerCredentials.username()
                    , encryptedPassword
                    , UserRole.ADMIN, false);

            userRepository.save(newUser);
            return ResponseEntity.ok().build();
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ServerResponse("There's already an account with this " + e.getMessage() , 409, ResponseType.ERROR));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ServerResponse("Error. Contact support." , 500, ResponseType.ERROR));
        }
    }
}
