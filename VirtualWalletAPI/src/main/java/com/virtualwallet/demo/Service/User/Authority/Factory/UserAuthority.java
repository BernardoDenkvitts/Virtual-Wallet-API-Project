package com.virtualwallet.demo.Service.User.Authority.Factory;

import com.virtualwallet.demo.Model.User.Roles;
import com.virtualwallet.demo.Service.User.Authority.Strategies.AdminAuthorityStrategy;
import com.virtualwallet.demo.Service.User.Authority.Strategies.IAuthorityStrategy;
import com.virtualwallet.demo.Service.User.Authority.Strategies.BasicUserAuthorityStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Map;


public class UserAuthority
{
    private final IAuthorityStrategy strategy;

    public UserAuthority(Roles role)
    {
        Map<Roles, IAuthorityStrategy> strategiesMap = Map.of(
                Roles.ADMIN, new AdminAuthorityStrategy(),
                Roles.USER, new BasicUserAuthorityStrategy()
        );
        this.strategy = strategiesMap.get(role);
    }

    public List<SimpleGrantedAuthority> getAuthorities()
    {
        return this.strategy.grantAuthorities();
    }

}
