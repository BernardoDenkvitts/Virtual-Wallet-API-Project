package com.virtualwallet.demo.Service.User.Authority.Strategies;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public interface IAuthorityStrategy
{
    List<SimpleGrantedAuthority> grantAuthorities();
}
