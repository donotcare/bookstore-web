package ru.otus.bookstoreweb.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ru.otus.bookstoreweb.book.Book;

import java.io.Serializable;
import java.util.Collection;

public class CustomPermissionEvaluator implements org.springframework.security.access.PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object domainObject, Object permission) {
        if (domainObject instanceof Book) {
            return checkPrivileges(authentication, (Book) domainObject);
        } else {
            return true;
        }
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable domainObjectId, String domainObjectType, Object permission) {
        return true;
    }

    private boolean checkPrivileges(Authentication authentication, Book book) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (isAdmin(authorities)) {
            return true;
        } else if(book.getName().toLowerCase().contains("java")){
            return true;
        } else {
            return false;
        }
    }

    private boolean isAdmin(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).anyMatch(n -> n.equals("ROLE_ADMIN"));
    }
}
