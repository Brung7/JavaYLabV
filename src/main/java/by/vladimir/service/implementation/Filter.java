package by.vladimir.service.implementation;

import by.vladimir.entity.Frequency;
import by.vladimir.entity.Role;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Filter {

    public Optional<Role> findRole(String role){
        return Optional.ofNullable(role).flatMap(r->{
            for(Role a :Role.values()){
                if (a.name().equals(role)){
                    return Optional.of(a);
                }
            }
            return Optional.empty();
        });
    }

    public Optional<Frequency> findFrequency(String frequency){
        return Optional.ofNullable(frequency).flatMap(r->{
            for(Frequency a :Frequency.values()){
                if (a.name().equals(frequency)){
                    return Optional.of(a);
                }
            }
            return Optional.empty();
        });
    }
}
