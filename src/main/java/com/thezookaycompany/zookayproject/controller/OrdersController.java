package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.exception.PaymentNotSuccessfulException;
import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.OrdersDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.services.OrdersService;
import com.thezookaycompany.zookayproject.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/order")
public class OrdersController {
    private final String SUCCESS_RESPONSE = "success";

    @Autowired
    private TokenService tokenService;
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private AccountService accountService;


//    DÙNG CÁI NÀY
    @GetMapping("/all")
    public List<OrdersDto> getAllOrdersDetail() {
        return ordersService.getAllOrdersDetail();
    }
    //    DÙNG CÁI NÀY
    @GetMapping("/{orderID}")
    public Optional<OrdersDto> getOrderDetailsById(@PathVariable Integer orderID) {
        return ordersService.getOrderDetailsById(orderID);
    }
//MẤY CÁI DƯỚI LỖI
    @GetMapping("/get-order/{orderID}")
    Orders findOrdersByOrderID(@PathVariable("orderID") Integer orderID) {
        return ordersService.findOrdersByOrderID(orderID);
    }
    @GetMapping("/get-order")
    public List<Orders> finAllOrders (){
        return ordersService.findAllOrders();
    }

    @GetMapping("/get-order-desc/{keyword}")
    public List<Orders> getOrdersByDescription(@PathVariable String keyword) {
        return ordersService.findOrdersByDescriptionContainingKeyword(keyword);
    }
    @GetMapping("/get-order/ascending")
    public List<Orders> getOrdersByOrderIDAscending() {
        return ordersService.findAllByOrderIDAsc();
    }

    @GetMapping("/get-order/descending")
    public List<Orders> getOrdersByOrderIDDescending() {
        return ordersService.findAllByOrderIDDesc();
    }
    @GetMapping("/{orderID}/orderDetails")
    public List<Map<String, Object>> listOrderDetailsTicket(@PathVariable Integer orderID) throws PaymentNotSuccessfulException {
        return ordersService.listOrderDetailsTicket(orderID);
    }
    // ham tra ve list order find by email  desc sau đó chọn ra cái đầu tiên
    //note: make sure to /create-order successfully
    @GetMapping("/find-order-by-email/{email}")
        public Orders findLatestOrderByEmail (@PathVariable("email") String email){
        List<Orders> list = ordersService.findOrdersByEmail(email);
        Orders orders = new Orders();
        if (list !=null && list.size()>0){
            orders =list.get(0);
        }
        return orders;
    }
    @GetMapping("/find-orders-by-email/{email}")
    public List<Orders> findAllOrdersByEmail (@PathVariable("email") String email){
        List<Orders> list = ordersService.findOrdersByEmail(email);
        if (list !=null && list.size()>0){
            return list;
        }
        else {
            return null;
        }
    }

    //EXAMPLE CREATE ORDER // DÙNG CÁI NÀY
    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody OrdersDto ordersDto,@RequestHeader("Authorization") String bearerJwt) {
        String response ="";
        if(bearerJwt != null && !bearerJwt.isEmpty()){
            String jwt =bearerJwt.replace("Bearer ","");
            Map<String, Object> data = tokenService.decodeJwt(jwt);
            String userEmail="";

            for(Map.Entry<String, Object> entry : data.entrySet()){
                if(entry.getKey().equals("email")){
                    userEmail = (String) entry.getValue();
                    break;
                }
            }
            Account account = accountService.getUserByEmail(userEmail);
            if(account != null){
                 response = ordersService.createMemberOrders(ordersDto,account);
            }
        }
        // ko co jwt la GuEsT
        else {
            response =ordersService.createGuestOrders(ordersDto);
        }
        if(response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }
}
