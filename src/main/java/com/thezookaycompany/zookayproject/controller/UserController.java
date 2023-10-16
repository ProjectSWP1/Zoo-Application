package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.LoginDto;
import com.thezookaycompany.zookayproject.model.dto.LoginResponse;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.thezookaycompany.zookayproject.model.dto.*;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Member;
import com.thezookaycompany.zookayproject.model.entity.Voucher;
import com.thezookaycompany.zookayproject.model.entity.ZooArea;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.repositories.MemberRepository;
import com.thezookaycompany.zookayproject.repositories.ZooAreaRepository;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.services.EmailService;
import com.thezookaycompany.zookayproject.services.MemberServices;
import com.thezookaycompany.zookayproject.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AccountRepository accountRepository;

    //Register for users, you should leave this json
    //    const requestData = {
    //        accountDto: {
    //            // AccountDto fields here
    //        },
    //        memberDto: {
    //            // MemberDto fields here
    //        }
    //    };
    @GetMapping(path = "/")
    public String userAccess() {
        return "User accessed";
    }

    // TODO: Clean code > chuyển toàn bộ account repository sang account services
    @PostMapping("/findUser")
    public Account getUser(@RequestBody AccountDto accountDto) {
        return accountRepository.findOneByEmail(accountDto.getEmail());
    }

    //For login user please write this json in ReactJS
    //        const loginDto = {
    //              email: email,
    //              password: password,
    //        };
    @PostMapping(path = "/login")
    public LoginResponse loginUser(@RequestBody LoginDto loginDto) {
        return accountService.loginAccount(loginDto);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RequestWrapper requestWrapper) {
        String response = accountService.addAccount(requestWrapper.getAccountDto(), requestWrapper.getMemberDto());
        if(response.contains("success")) {
            System.out.println("New account has been added");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/send-email")
    public String processSendMailWithToken(@RequestBody AccountDto accountDto){

        //send mail with token
        try {
            emailService.sendVertificationEmail(accountDto);
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "Please check your mail to get Verification link";
    }

    @PutMapping("/verify")
    public String verifyAccWithToken (@RequestParam String email, @RequestParam String otp){
        Account account = accountRepository.findAccountByEmail(email);
        if(account !=null){
            accountService.verifyAccount(account.getEmail(), otp);
        } else {
            throw new RuntimeException("Invalid OTP or OTP had expired");
        }
        return "Account verified successfully";
    }
    @Autowired
    private  MemberRepository memberRepository;
    @Autowired
    private  MemberServices memberServices;

    @GetMapping("/member/all")
    public List<Member> getAllMember(){

        return memberRepository.findAll();
    }
    @GetMapping("/member/{phoneNumber}")
    public Member findMemberByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {

        return memberRepository.findMemberByPhoneNumber(phoneNumber);
    }
    @PutMapping("/update/{phoneNumber}")
    public ResponseEntity<Member> updateMemberByPhoneNumber(
            @PathVariable String phoneNumber,
            @RequestBody Member updatedMember) {
        Member updated = memberServices.updateMemberByPhoneNumber(phoneNumber,updatedMember);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Autowired
    private ZooAreaRepository zooAreaRepository;

    @GetMapping("/zoo-area/id/{zooAreaId}")
    ZooArea findZooAreaByZooAreaID(@PathVariable("zooAreaId") String zooAreaId) {

        return memberServices.findZooAreaByZooAreaID(zooAreaId);

    }
    @GetMapping("/zoo-area/des/{description}")
    ZooArea findZooAreaByZooAreaDes(@PathVariable("description") String description) {

        return memberServices.findZooAreaByZooAreaDes(description);

    }

    @GetMapping("/zoo-area/all")
    public List <ZooArea> findAllZooArea(){
        return memberServices.findAllZooArea();
    }

    @GetMapping("/google")
    public Map<String, Object> getUserGoogleLogin(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return oAuth2User.getAttributes();
    }
    //PAYMENT---------------------------------------------------------------------------
    @PostMapping("/create-payment-intent")
    public PaymentResponse createPaymentIntent(@RequestBody OrdersDto ordersDto) throws StripeException {

        // create payment intent to confirm
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        // createPayment for product cost how much...
                        // object orderDto chứa total amount order
                        .setAmount((long) (ordersDto.getTotalOrder() * 1000L))
                        .putMetadata("TotalTickets", ordersDto.getDescription())
                        .setCurrency("vnd")
                        // In the latest version of the API, specifying the `automatic_payment_methods` parameter is optional because Stripe enables its functionality by default.
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        // Create a PaymentIntent with the order amount and currency
        PaymentIntent paymentIntent = PaymentIntent.create(params);

        return new PaymentResponse(paymentIntent.getId(),paymentIntent.getClientSecret());
    }
    //GET ALL VOUCHER
    @Autowired
    private VoucherService voucherService;
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @GetMapping("/get-all-voucher")
    public List <Voucher> getAllVoucher(){
        return voucherService.getAllVoucher();
    }

}
