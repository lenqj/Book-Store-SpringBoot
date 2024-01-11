package proiect.User.Service;

import proiect.DTO.UserRoleDto;
import proiect.Model.User.UserRole;

import java.util.List;
import java.util.Objects;

public interface UserRoleService  {
    UserRoleDto getRoleById(Integer id);
    UserRoleDto findByRole(String role);

    List<UserRoleDto> getAllRoles();

    UserRoleDto createRole(UserRole role);

    UserRoleDto updateRole(UserRole role);
    void deleteRole(UserRole role);
    UserRole findByName(String role);
}
