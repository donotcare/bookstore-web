package ru.otus.bookstoreweb.security;

import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.bookstoreweb.book.Book;

@Service
public class SecurityAclService {
    private final MutableAclService mutableAclService;

    public SecurityAclService(MutableAclService mutableAclService) {
        this.mutableAclService = mutableAclService;
    }

    @Transactional
    public void addPermissionToBook(Book book, Sid recipient, Permission permission) {
        ObjectIdentity oid = new ObjectIdentityImpl(book);
        MutableAcl acl;
        try {
            acl = (MutableAcl) mutableAclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = mutableAclService.createAcl(oid);
        }

        acl.insertAce(acl.getEntries().size(), permission, recipient, true);
        mutableAclService.updateAcl(acl);
    }
}
