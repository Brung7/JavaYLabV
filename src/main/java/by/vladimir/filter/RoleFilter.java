package by.vladimir.filter;

import org.springframework.stereotype.Component;

@Component
public class RoleFilter {

    public boolean isAdmin(String role){
        return "ADMIN".equals(role);
    }
}