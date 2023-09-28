package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.utils.RandomTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Controller
public class ForgotPasswordController {

    @Autowired
    private AccountService accountService;


    @GetMapping("/forgotPwd")
    public String forgotPwdForm (Model model){

        model.addAttribute("formTitle","Forgot Password");

        // return view name
        return "forgotPwdForm";
    }

    @PostMapping("/forgotPwd")
    public String phaseForgotPwdForm(@RequestBody String email){
        // giống jsp frontend sẽ tạo 1 form chứa input type name=email để đưa email user nhập vào xử lý
        // String email = request.getParameter("email");
        // tạo 1 token mới để gửi cho user
        String token = RandomTokenGenerator.generateRandomString(20);

        try {
            // validate account and set new resetPWdToken token
            accountService.updateResetPwdToken(token, email);

        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Email: "+email);
        System.out.println("Token: "+ token);

        return "forgotPwdForm";
    }
//    @GetMapping("/getAll")
//    public void getAllAccount (){
//       List<Account> list =  accountService.getAllAccount();
//
//       for (Account c : list){
//           System.out.println(c.toString());
//       }
//    }
}
