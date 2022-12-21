package com.springboot.controller;

import com.springboot.FormatToJson.ToJsonData;
import com.springboot.model.Admin;
import com.springboot.service.AdminServices;
import com.springboot.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flotte/Admin")
@CrossOrigin(methods = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.OPTIONS})
public class AdminController {

    private AdminServices as;
    private TokenService serve;

    public AdminController(AdminServices as, TokenService serve) {
        super();
        this.as = as;
        this.serve = serve;
    }
    @GetMapping("/checkToken")
    public ResponseEntity<ToJsonData> isConnected(@PathVariable("id") int id,@PathVariable("token") String token){
        try {
                serve.checkTokens(token,id);
                return new ResponseEntity<>(new ToJsonData<String>("encore connecter"), HttpStatus.ACCEPTED);

        }catch (Exception e){
            return  new ResponseEntity<>(new ToJsonData<String>(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ToJsonData> login(@RequestBody  Admin admin){
            return as.login(admin);
    }
    @DeleteMapping("/log_out/{id}")
    public ResponseEntity<ToJsonData>LogOut(@PathVariable("id") int id){
        try {
            return as.Logout(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
