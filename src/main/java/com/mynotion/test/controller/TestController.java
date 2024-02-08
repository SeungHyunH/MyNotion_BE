package com.mynotion.test.controller;

import com.mynotion.common.SuccessResponse;
import com.mynotion.exception.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @PostMapping
    public ResponseEntity<Object> test(@RequestBody Map<String, Object> requestData) {
        // 맵으로 받은 데이터에서 "data" 키의 값을 추출
        String data = (String) requestData.get("data");
        logger.info("received : {}", data);

        // 요청 데이터가 "성공"인지 확인하여 처리
        if (!"성공".equals(data)) {
            ErrorResponse errorResponse = new ErrorResponse("T-01", "입력된 값이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        }

        // 문자열이 일치하는 경우 성공 응답을 반환
        SuccessResponse<String> successResponse = new SuccessResponse<>("S-01", "Test successful!", "성공적으로 테스트되었습니다.");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(successResponse);
    }
}
