package com.springboot.controller;

import com.springboot.FormatToJson.ToJsonData;
import com.springboot.model.Kilometrage;
import com.springboot.repository.kilometrageRepository;
import com.springboot.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/flotte/kilometrage")
@CrossOrigin(methods = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.OPTIONS,RequestMethod.POST,RequestMethod.PUT})
public class kilometrageController {

    private  kilometrageRepository kilometrageRepository;
    private TokenService serve;

    public kilometrageController(kilometrageRepository kilometrageRepository, TokenService serve) {
        super();
        this.kilometrageRepository = kilometrageRepository;
        this.serve = serve;
    }

    @GetMapping
    public ResponseEntity<ToJsonData> getAllKilometrage() {
        ToJsonData<List<Kilometrage>> kill = new ToJsonData<>(kilometrageRepository.findAll());
        return new ResponseEntity<>(kill, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<ToJsonData> createKilometrage(@RequestBody Kilometrage kilometrage) {
        try {
            kilometrageRepository.save(kilometrage);
            ToJsonData<Kilometrage> kill = new ToJsonData<>(kilometrage);
            return new ResponseEntity<>(kill, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<ToJsonData>(new ToJsonData<String>(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ToJsonData> updateKilometrage(@PathVariable int id, @RequestBody Kilometrage kilometrage) {
        try {
            Kilometrage kilometrages = kilometrageRepository.findById(id).orElseThrow(() -> new AssertionError("kilometrage Id not found !!!"));
            kilometrages.setDate(kilometrage.getDate());
            kilometrages.setDebut(kilometrage.getDebut());
            kilometrages.setFin(kilometrage.getFin());
            kilometrageRepository.save(kilometrages);
            ToJsonData<Kilometrage> kill = new ToJsonData<>(kilometrage);
            return ResponseEntity.ok(kill);
        }catch (Exception e){
            return new ResponseEntity<ToJsonData>(new ToJsonData<String>(e.getMessage()),HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<ToJsonData> getKilomById(@PathVariable int id,@RequestParam("id") int idu,@RequestParam("token") String token) {
        System.out.println("number one");
        try {
            serve.checkTokens(token,idu);
            List<Kilometrage> list = kilometrageRepository.findAllByIdavion(id);
            for (Kilometrage t:list) {
                System.out.println(t.getFin()+" "+t.getDebut()+" date "+t.getDate());
            }
            ToJsonData<List<Kilometrage>> kill = new ToJsonData<>(list);
            return ResponseEntity.ok(kill);
        }catch (Exception e){
            return new ResponseEntity<ToJsonData>(new ToJsonData<String>(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ToJsonData>  DeleteKilometrage(@PathVariable int id) {
        try {
            Kilometrage kilometrage = kilometrageRepository.findById(id).orElseThrow(() -> new AssertionError("kilometrage Id not found !!!"));
            kilometrageRepository.deleteById(id);
            ToJsonData<String> message = new ToJsonData<>("deleting object successfully");
            return new ResponseEntity<>(message,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<ToJsonData>(new ToJsonData<String>(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }
}
