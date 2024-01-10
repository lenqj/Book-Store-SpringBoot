package proiect.UserRole.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proiect.DTO.UserRoleDto;
import proiect.Mapper.RoleMapper;
import proiect.Model.UserRole;
import proiect.UserRole.Repository.UserRoleRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

        private final UserRoleRepository userRoleRepository;

        private final RoleMapper roleMapper;

        public UserRoleDto getRoleById(Integer id){
            return roleMapper.roleEntityToDto(Objects.requireNonNull(userRoleRepository.findById(id).orElse(null)));
        }

        public UserRoleDto findByRole(String role){
            return roleMapper.roleEntityToDto(userRoleRepository.findByName(role));
        }

        public List<UserRoleDto> getAllRoles(){
            return roleMapper.roleListEntityToDto(userRoleRepository.findAll());
        }

        public UserRoleDto createRole(UserRole role){
            return roleMapper.roleEntityToDto(userRoleRepository.save(role));
        }

        public UserRoleDto updateRole(UserRole role){
            return roleMapper.roleEntityToDto(userRoleRepository.save(role));
        }

        public void deleteRole(UserRole role){
            userRoleRepository.delete(role);
        }

    }
