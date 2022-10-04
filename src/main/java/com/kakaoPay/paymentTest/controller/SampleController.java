package com.kakaoPay.paymentTest.controller;

import com.kakaoPay.paymentTest.domain.KakaoPayReadyVO;
import com.kakaoPay.paymentTest.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SampleController {

    private final KakaoPayService kakaopay;

    @RequestMapping(value = "/home")
    public String home(){
        return "index";
    }


    @GetMapping("/kakaoPay")
    public String kakaoPayGet(){
        return "kakaoPay";
    }

    @PostMapping("/kakaoPay")
    public String kakaoPay(){

        log.info("kakaoPay post.....................................");
        //KakaoPayReadyVO kakaoPayReadyVO = kakaopay.kakaoPayReady();
        String url = kakaopay.kakaoPayReady();
        log.info(".........................결재고유 번호 : ", url);
       // log.info(".........................결재고유 번호 : ", .getTid());
        return url;
    }

    @GetMapping("/kakaoPaySuccess")
    public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model) throws URISyntaxException {
        log.info("kakaoPaySuccess get......................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);

       // model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token));
        return "Success";
    }

    @GetMapping("/kakaoPaySuccessFail")
    public void kakaoPaySuccessFail(@RequestParam("pg_token") String pg_token, Model model) throws URISyntaxException {
        log.info("kakaoPaySuccess get......................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);

        model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token));
    }
    @GetMapping("/kakaoPayCancel")
    public void kakaoPayCancel(@RequestParam("pg_token") String pg_token, Model model) throws URISyntaxException {
        log.info("kakaoPaySuccess get......................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);

        model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token));
    }

}
