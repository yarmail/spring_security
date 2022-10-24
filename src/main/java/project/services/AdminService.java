package project.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * 87 Аннотация @PreAuthorize
 */
@Service
public class AdminService {

    //Метод должен выполнятся только ROLE_ADMIN
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void doAdminStuff() {
        System.out.println("Only admin here");
    }
}