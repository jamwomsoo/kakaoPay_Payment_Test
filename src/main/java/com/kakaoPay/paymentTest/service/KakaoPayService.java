package com.kakaoPay.paymentTest.service;

import com.kakaoPay.paymentTest.domain.KakaoPayApprovalVO;
import com.kakaoPay.paymentTest.domain.KakaoPayReadyVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoPayService {

    private static final String HOST = "https://kapi.kakao.com";

    private KakaoPayReadyVO kakaoPayReadyVO;
    private KakaoPayApprovalVO kakaoPayApprovalVO;

    public String kakaoPayReady(){
        log.info("KakaoPayService> kakaoPayReady......................................... ");
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","KakaoAK " + "cbfe56d98ec364f4e7b331348437d0af");
        //headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid","TC0ONETIME");
        params.add("partner_order_id","1001");
        params.add("partner_user_id", "gorany");
        params.add("item_name", "갤럭시 S9");
        params.add("quantity", "1");
        params.add("total_amount", "2100");
        params.add("vat_amount","200");
        params.add("tax_free_amount", "100");
        params.add("approval_url", "http://localhost:3000/kakaoPaySuccess");
        params.add("cancel_url", "http://localhost:3000/kakaoPaySuccessFail");
        params.add("fail_url", "http://localhost:3000/kakaoPayCancel");

        log.info("파트너주문아이디:"+ params.get("partner_order_id")) ;

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String,String>>(params,headers);
        String url = "https://kapi.kakao.com/v1/payment/ready";
        try {
            kakaoPayReadyVO = restTemplate.postForObject(url, body, KakaoPayReadyVO.class);
            //kakaoPayReadyVO  = restTemplate.postForObject(url, body, KakaoPayReadyVO.class);
            log.info("결재준비 응답객체: " + kakaoPayReadyVO);
            return kakaoPayReadyVO.getNext_redirect_pc_url();
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return "hello";
    }

    public KakaoPayApprovalVO kakaoPayInfo(String pg_token) throws URISyntaxException {
        log.info("KakaoPayInfoVO..................................................");
        log.info(".......................");

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "cbfe56d98ec364f4e7b331348437d0af");
       // headers.add("Accept", MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE);
        headers.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReadyVO.getTid());
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", "gorany");
        params.add("pg_token", pg_token);
        params.add("total_amount", "2100");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        String url = "https://kapi.kakao.com/v1/payment/approve";
        try {
            kakaoPayApprovalVO = restTemplate.postForObject(url, body, KakaoPayApprovalVO.class);
            log.info("" + kakaoPayApprovalVO);
            return kakaoPayApprovalVO;
        }catch (RestClientException e){
            e.printStackTrace();
        }
        return null;
    }
}
