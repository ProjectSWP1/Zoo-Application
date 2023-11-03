package com.thezookaycompany.zookayproject.controller;

import com.stripe.exception.StripeException;
import com.thezookaycompany.zookayproject.model.dto.*;
import com.thezookaycompany.zookayproject.model.entity.*;
import com.thezookaycompany.zookayproject.repositories.ZooAreaRepository;
import com.thezookaycompany.zookayproject.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ZooNewsService zooNewsService;

    @Autowired
    private ZooAreaService zooAreaService;

    @Autowired
    private PaymentService paymentService;

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
    @PostMapping("/findUser")
    public Account getUser(@RequestBody AccountDto accountDto) {
        return accountService.getUserByEmail(accountDto.getEmail());
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
        if (response.contains("success")) {
            System.out.println("New account has been added");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/send-email")
   // @PreAuthorize("hasRole('Member')")
    public String processSendMailWithToken(@RequestBody AccountDto accountDto){

        //send mail with token

            Account account = accountService.getUserByEmail(accountDto.getEmail());
            if(account !=null){
                emailService.sendVertificationEmail(account);
            } else {
                return "Account's not found!";
            }
        return "Please check your mail to get Verification OTP";
    }

    @PutMapping("/verify")
    public String verifyAccWithToken (@RequestParam String email, @RequestParam String otp){
        String message;
        Account account = accountService.getUserByEmail(email);
        if(account !=null){
              message = accountService.verifyAccount(account.getEmail(), otp);
        } else {
            return "Invalid email - Couldn't find any account with email: "+email;
        }
        return message;
    }
    @Autowired
    private MemberServices memberServices;

    @GetMapping("/member/all")
    public List<Member> getAllMember(){

        return memberServices.getAllMember();
    }
    @GetMapping("/member/{phoneNumber}")
    public Member findMemberByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {

        return memberServices.findMemberByPhoneNumber(phoneNumber);
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

        return zooAreaService.findZooAreaByZooAreaID(zooAreaId);

    }
    @GetMapping("/zoo-area/des/{description}")
    ZooArea findZooAreaByZooAreaDes(@PathVariable("description") String description) {

        return zooAreaService.findZooAreaByZooAreaDes(description);

    }

    @GetMapping("/zoo-area/all")
    public List <ZooArea> findAllZooArea(){
        return zooAreaService.findAllZooArea();
    }

    //PAYMENT---------------------------------------------------------------------------
    @PostMapping("/create-payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody OrdersDto ordersDto) throws StripeException {
        String message= "";
        PaymentResponse paymentResponse = paymentService.createPaymentIntent(ordersDto);
        if (paymentResponse!= null){
            message =paymentService.confirmPayment(ordersDto,paymentResponse);
        } else {
            message = "OrderId has been paid";
        }
        return ResponseEntity.ok(message);
    }

    //-------------------------------------------------------


    //GET ALL VOUCHER
    @Autowired
    private VoucherService voucherService;
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @GetMapping("/get-all-voucher")
    public List <Voucher> getAllVoucher(){
        return voucherService.getAllVoucher();
    }
    @GetMapping("/getnews")
    public List<ZooNews> getAllNews() {
        return zooNewsService.getNews();
    }

    // Lấy tin tức mới nhất dựa vào Date created
    @GetMapping("/get-newest-news")
    public List<ZooNews> getNewestNews() {
        return zooNewsService.getNewestNews();
    }

    //----------------------GET TICKET-----------------------

    //Tìm Ticket dựa vào TicketID
    @GetMapping("/get-ticket/{ticketId}")
    Ticket findTicketByTicketID(@PathVariable("ticketId") String ticketId) {
        return ticketService.findTicketByTicketID(ticketId);
    }
    //Tìm tất cả ticket đang có

    @GetMapping("/get-ticket")
    public List<Ticket> getAllTickets() {
        return ticketService.findAllTicket();
    }

    //Hàm này lấy tất cả Ticket dựa vào description keyword
    @GetMapping("/get-ticket-desc/{keyword}")
    public List<Ticket> getTicketByDescription(@PathVariable String keyword) {
        return ticketService.getTicketByDescriptionKeyword(keyword);
    }
    //Hàm này lấy tất cả Ticket dựa vào Price theo thứ tự TĂNG DẦN//
    @GetMapping("/get-ticket/ascending")
    public List<Ticket> getTicketByTicketPriceAscending() {
        return ticketService.findAllByTicketPriceAsc();
    }

    //Hàm này lấy tất cả Ticket dựa vào Price theo thứ tự GIẢM DẦN//
    @GetMapping("/get-ticket/descending")
    public List<Ticket> getTicketByTicketPriceDescending() {
        return ticketService.findAllByTicketPriceDesc();
    }
}
