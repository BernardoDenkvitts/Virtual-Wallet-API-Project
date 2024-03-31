package com.virtualwallet.demo.Service.User.Authority.Strategies;

import com.virtualwallet.demo.Model.User.Roles;
import com.virtualwallet.demo.Service.User.Authority.Permissions;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class AdminAuthorityStrategy implements IAuthorityStrategy
{
    public AdminAuthorityStrategy() {}

    @Override
    public List<SimpleGrantedAuthority> grantAuthorities()
    {
        List<SimpleGrantedAuthority> adminAuthority = new ArrayList<>();
        for(Permissions perm : Permissions.values())
        {
            adminAuthority.add(new SimpleGrantedAuthority(perm.name()));
        }

        adminAuthority.add(new SimpleGrantedAuthority("ROLE_" + Roles.ADMIN.name()));
        return adminAuthority;
    }
}
