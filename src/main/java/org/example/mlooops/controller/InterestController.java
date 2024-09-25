package org.example.mlooops.controller;

import org.example.mlooops.DTOtoFastAPI.Response.UserUpdateDTO_Res;
import org.example.mlooops.dto.InitialInterestDTO;
import org.example.mlooops.service.InterestService;
import org.example.mlooops.service.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class InterestController {
    private InterestService initilizerInterestService;

    public InterestController(InterestService initilizerInterestService) {
        this.initilizerInterestService = initilizerInterestService;
    }

    @PostMapping("/interest/init")
    public ResponseEntity<ResponseData> init(@RequestBody InitialInterestDTO interestDTO) {
        System.out.println("input data");
        int result=initilizerInterestService.InitializationServie(interestDTO);
        ResponseData data;
        switch (result){
            case 103 :
                data = new ResponseData("Overlap");
                return new ResponseEntity<>(data, HttpStatus.CONFLICT); // 409 Conflict
            case 200 :data = new ResponseData("Complete initialization interest");
                return new ResponseEntity<>(data, HttpStatus.OK); // 200 OK
            default: data = new ResponseData("Occure other fail ");
                return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

}
