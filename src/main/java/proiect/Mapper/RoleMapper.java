package proiect.Mapper;

import org.springframework.stereotype.Component;
import proiect.DTO.UserRoleDto;
import proiect.Model.User.UserRole;

import java.util.List;


@Component
public class RoleMapper {

    public UserRoleDto roleEntityToDto(UserRole role){
        return UserRoleDto.builder()
                .role(role.getRole())
                .build();
    }

    public List<UserRoleDto> roleListEntityToDto(List<UserRole> roles){
        return roles.stream()
                .map(this::roleEntityToDto)
                .toList();
    }

    public UserRole roleDtoToEntity(UserRoleDto roleDto){
        return UserRole.builder()
                .role(roleDto.role())
                .build();
    }

    public List<UserRole> roleListDtoToEntity(List<UserRoleDto> roleDtos){
        return roleDtos.stream()
                .map(this::roleDtoToEntity)
                .toList();
    }
}
