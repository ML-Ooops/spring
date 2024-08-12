package org.example.mlooops.controller;

import org.example.mlooops.dto.JoinDTO;
import org.example.mlooops.service.JoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {
    private final JoinService joinService;
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public ResponseEntity<ResponseData> joinProcess(@RequestBody JoinDTO joinDTO){
        int result=joinService.JoinProcess(joinDTO);
        ResponseData data;
        switch (result){
            case 101 :
                data = new ResponseData("Overlap username");
                return new ResponseEntity<>(data, HttpStatus.CONFLICT); // 409 Conflict
            case 102 :data = new ResponseData("Overlap eamil");
                return new ResponseEntity<>(data, HttpStatus.CONFLICT); // 409 Conflict
            case 200 :data = new ResponseData("Complete join");
                return new ResponseEntity<>(data, HttpStatus.OK); // 200 OK
            default: data = new ResponseData("Occure other fail ");
                return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }

    }


    class ResponseData {
        private String result;

        public ResponseData(String result){
            this.result=result;
        }

        // Getters and Setters
        public String getOutput() {
            return result;
        }

        public void setOutput(String result) {
            this.result=result;
        }
    }
}
