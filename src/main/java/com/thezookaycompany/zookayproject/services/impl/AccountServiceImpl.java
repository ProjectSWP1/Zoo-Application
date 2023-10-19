package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.LoginDto;
import com.thezookaycompany.zookayproject.model.dto.LoginResponse;
import com.thezookaycompany.zookayproject.model.dto.MemberDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Employees;
import com.thezookaycompany.zookayproject.model.entity.Role;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.repositories.EmployeesRepository;
import com.thezookaycompany.zookayproject.repositories.MemberRepository;
import com.thezookaycompany.zookayproject.repositories.RoleRepository;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.services.EmployeeService;
import com.thezookaycompany.zookayproject.services.MemberServices;
import com.thezookaycompany.zookayproject.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MemberServices memberServices;



    @Override
    public String addAccount(AccountDto accountDto, MemberDto memberDto) {
        if(accountDto.getEmail().isEmpty() || accountDto.getEmail() == null) {
            return "Email field is empty";
        }
        memberDto.setPhoneNumber(accountDto.getPhoneNumber());

        if(accountDto.getPhoneNumber().isEmpty() || accountDto.getPhoneNumber() == null || !isValidPhoneNumber(accountDto.getPhoneNumber())) {
            return "Invalid phone number, please try again";
        }

        if(memberDto.getDob() == null || memberDto.getDob().isEmpty()) {
            return "You cannot leave empty date of birth field";
        }

        Account temp = accountRepository.findAccountByEmail(accountDto.getEmail());
        if (temp != null) {
            return "This account has already existed";
        }

        // Parse lại Email thành Username
        accountDto.setUsername(accountDto.getEmail().trim().split("@")[0]);
        String encodedPassword = passwordEncoder.encode(accountDto.getPassword());
        Role userRole = roleRepository.findByRoleName("Member").get();

        // Add member trước rồi mới add account
        memberServices.addMember(accountDto, memberDto);
        Account acc = new Account(
                accountDto.getUsername(),
                encodedPassword,
                accountDto.getEmail(),
                memberRepository.findMemberByPhoneNumber(memberDto.getPhoneNumber()),
                userRole,
                false
        );
        accountRepository.save(acc);
        return "You have registered successfully. To verify your email, check your gmail box";
    }

    @Override
    public String deactivateAccount(String email) {
        Account acc = accountRepository.findById(email).orElse(null);
        if(acc != null) {
            acc.setActive(false);
            accountRepository.save(acc);
            return "The account " + email + " has been successfully deactivated";
        }
        return "Not found account with email " + email;
    }

    @Override
    public String removeAccount(String email) {
        if(email == null || !email.contains("@")) {
            return "Failed to check this email or invalid input";
        }
        Account acc = accountRepository.findById(email).orElse(null);
        if(acc != null) {
            // Tìm thằng Employee và xóa trước rồi xóa Account vì ràng buộc
            Employees employees = employeesRepository.findEmployeesByEmail(acc);
            if(employees == null) {
                return "Cannot remove account because some constraints in Employee";
            }
            employeesRepository.delete(employees);
            accountRepository.deleteById(acc.getEmail());
            return "Deleted account " + email + " successfully";
        }
        return "Failed to find account with email " + email;
    }

    @Override
    public Account getUserByEmail(String email) {
        return accountRepository.findOneByEmail(email);
    }

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> getAllInactiveAccount() {
        return accountRepository.findAccountsByActiveIsFalse();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "0[0-9]{9}";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }

    // Hàm assign role tới account (chỉ khi đã có thằng Employee active = 1)
    @Override
    public boolean assignRoleToAccount(AccountDto accountDto, String role_id) {
        if(!roleRepository.existsById(role_id)) {
            return false;
        }
        if(accountRepository.existsById(accountDto.getEmail())) {
            Account acc = accountRepository.findById(accountDto.getEmail()).get();
            // Nếu employee ko có -> nghĩa chưa thêm employee trước khi assign role account này
            if(!role_id.contains("MB")) {
                if(!employeesRepository.existsEmployeesByEmailAndActiveIsTrue(acc)) {
                    return false;
                }
            }
            acc.setRole(roleRepository.findById(role_id).get());
            accountRepository.save(acc);
            return true;
        }
        return false;
    }

    @Override
    public LoginResponse loginAccount(LoginDto loginDto) {
        String username = loginDto.getEmail();
        if(username.contains("@")) {
            username = loginDto.getEmail().trim().split("@")[0];
        }
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, loginDto.getPassword())
            );
            Account acc = accountRepository.findByUsername(username).get();
            boolean active = acc.isActive();
            if (!active){
                return new LoginResponse(null, "");
            }
            String token = tokenService.generateJwt(auth);

            return new LoginResponse(acc, token);


        } catch(AuthenticationException e) {
            return new LoginResponse(null, "");
        }
    }

    @Override
    public void updateResetPwdToken(String token, String email) throws AccountNotFoundException {

            // check account exist ?
            Account account = accountRepository.findAccountByEmail(email);

            // nếu tồn tại thì set account new Token
            if (account !=null){
                account.setResetPwdToken(token);
                accountRepository.save(account);
            } else {
                throw new AccountNotFoundException("Could not find any customer with email "+email);
            }
    }

    @Override
    public Account getAccByPwdToken(String resetPwdToken) {
        return accountRepository.findByResetPwdToken(resetPwdToken);
    }

    @Override
    public void updatePassword(Account account, String newPassword) {
        //encode and save new password
        String encodePwd = passwordEncoder.encode(newPassword);
        account.setPassword(encodePwd);

        //xóa token cũ ngăn người dùng sử dụng token để tự đổi mk
        account.setResetPwdToken(null);

        accountRepository.save(account);
    }

    @Override
    public void updateVerifyToken(String token, String email) throws AccountNotFoundException {

        // check account exist ?
        Account account = accountRepository.findAccountByEmail(email);

        // nếu tồn tại thì set verify Token
        if (account !=null){
            account.setVerificationToken(token);
            account.setOtpGeneratedTime(LocalDateTime.now());
            accountRepository.save(account);
        } else {
            throw new AccountNotFoundException("Could not find any customer with email "+email);
        }
    }

    @Override
    public void verifyAccount(String email, String otp) {

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Could not find any customer with email "+email));
        //Period.between(account.getOtpGeneratedTime(),)
        // check otp trùng hoặc otp expired (2')
        if(account.getVerificationToken().equals(otp) || Duration.between(account.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds()< (2 *60)){
            account.setActive(true);
            accountRepository.save(account);
        }
    }
}
