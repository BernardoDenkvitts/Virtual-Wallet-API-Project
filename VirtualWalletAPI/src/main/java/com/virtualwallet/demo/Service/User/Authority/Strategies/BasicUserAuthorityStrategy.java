package com.virtualwallet.demo.Service.User.Authority.Strategies;

import com.virtualwallet.demo.Model.User.Roles;
import com.virtualwallet.demo.Service.User.Authority.Permissions;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class BasicUserAuthorityStrategy implements IAuthorityStrategy
{

    public BasicUserAuthorityStrategy() {}

    @Override
    public List<SimpleGrantedAuthority> grantAuthorities()
    {
        List<SimpleGrantedAuthority> basicUserAuthority = new ArrayList<>();
        basicUserAuthority.add(new SimpleGrantedAuthority(Permissions.READ.name()));
        basicUserAuthority.add(new SimpleGrantedAuthority(Permissions.CREATE.name()));

        basicUserAuthority.add(new SimpleGrantedAuthority("ROLE_" + Roles.USER.name()));
        return basicUserAuthority;
    }
}
