package proiect.DTO;

import lombok.Builder;
import lombok.Data;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
public record UserDto(
        String username,
        List<UserRoleDto> roles,
        String firstName,
        String lastName,
        String emailAddress,
        String address,
        String mobile,
        LocalDate birthdate) {
    public String getPrettyRoles(){
        StringBuilder stringBuilder = new StringBuilder(" ");
        roles.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}
