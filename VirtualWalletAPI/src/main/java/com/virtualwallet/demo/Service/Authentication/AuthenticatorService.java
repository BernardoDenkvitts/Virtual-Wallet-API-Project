package com.virtualwallet.demo.Service.Authentication;

import com.virtualwallet.demo.DTO.Authentication.LoginRequestDTO;
import com.virtualwallet.demo.DTO.Authentication.LoginResponseDTO;
import com.virtualwallet.demo.DTO.Authentication.NewAccountRequestDTO;
import com.virtualwallet.demo.Exception.AuthenticationException;
import com.virtualwallet.demo.Exception.BadRequestException;
import com.virtualwallet.demo.Model.User.User;
import com.virtualwallet.demo.Repository.UserRepository;
import com.virtualwallet.demo.Service.Authentication.Token.IToken;
import com.virtualwallet.demo.Service.CryptoWallet.IWallet;
import com.virtualwallet.demo.Mappers.Authentication.IAuthenticatorMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthenticatorService implements IAuthenticator
{
    private final UserRepository userRepository;
    private final IWallet wallet;
    private final IAuthenticatorMapper authenticationMapper;
    private final AuthenticationManager authenticationManager;
    private final IToken tokenService;
    private static final String EMAIL_ALREADY_IN_USE = "Email Already In Use";
    private static final String EMAIL_NOT_FOUND = "Email Not Found";

    public AuthenticatorService(
        UserRepository userRepository,
        IWallet wallet,
        IAuthenticatorMapper authenticationMapper,
        AuthenticationManager authenticationManager,
        IToken tokenService
    )
    {
        this.userRepository = userRepository;
        this.wallet = wallet;
        this.authenticationMapper = authenticationMapper;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional
    public String register(NewAccountRequestDTO newAccountRequestDTO)
    {
        if(userRepository.findByEmail(newAccountRequestDTO.getEmail()) != null)
        {
            throw new BadRequestException(EMAIL_ALREADY_IN_USE);
        }

        User newUser = authenticationMapper.newAccountRequestDtoToUser(newAccountRequestDTO);
        newUser.setPswd(new BCryptPasswordEncoder().encode(newUser.getPassword()));
        newUser.setCryptosAddress(wallet.createWallet());

        userRepository.save(newUser);
        return newUser.getId();
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO)
    {
        if (userRepository.findByEmail(loginRequestDTO.email()) == null)
        {
            throw new AuthenticationException(EMAIL_NOT_FOUND);
        }

        UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(
                loginRequestDTO.email(), loginRequestDTO.pswd()
        );

        Authentication auth = authenticationManager.authenticate(userAuth);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        return new LoginResponseDTO(token);
    }

}
