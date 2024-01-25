package com.prettier.service.concretes;

import com.prettier.entity.concretes.Role;
import com.prettier.payload.mapper.RoleMapper;
import com.prettier.payload.request.concretes.RoleRequest;
import com.prettier.payload.request.concretes.RoleUpdateRequest;
import com.prettier.payload.response.concretes.RoleResponse;
import com.prettier.repository.RoleRepository;
import com.prettier.service.abstracts.RoleService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.roles.RoleNotFoundException;
import com.prettier.shared.utils.enums.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleManager implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    //NOT: *********** Data Inilitalizer icin gerekli metotlar *************************

    @Override
    public Set<Role> findByRoleName(String roleName) {

        Role role = roleRepository.findByNameEquals("ADMIN");
        Set<Role> roleSet = new HashSet<>();

        return roleSet;
    }

    @Override
    public List<Role> getAllUserRole() {

        return roleRepository.findAll();
    }


    //NOT: *********** Role Manager standart metotlar *************************************

    @Override
    public List<Role> getRoles() {

        List<Role> roles = roleRepository.findAll();
        return roles;
    }

    @Override
    public RoleResponse getById(Language language, Long id) {
        return null;
    }

    @Override
    public RoleResponse save(Language language, RoleRequest roleRequest) {

        log.debug("[{}][createRole] -> request: {}", this.getClass().getSimpleName(), roleRequest);
        Role role = roleMapper.toRole(roleRequest);

        Role roleResponse = roleRepository.save(role);
        log.debug("[{}][createRole] -> response: {}", this.getClass().getSimpleName(), roleResponse);
        return roleMapper.toResponse(roleResponse);
    }

    @Override
    public RoleResponse add(Language language, RoleRequest roleRequest) {

        log.debug("[{}][newRole] -> request: {}", this.getClass().getSimpleName(), roleRequest);

        //Rol var mi kontrolü
        if(roleRepository.existsByNameEquals(roleRequest.getRoleName())){
            throw new RoleNotFoundException(language, FriendlyMessageCodes.ROLE_ALREADY_EXISTS, "Role already exists");
        }

        Role role = roleMapper.toRole(roleRequest);
        Role newRole = roleRepository.save(role);

        log.debug("[{}][newRole] -> response: {}", this.getClass().getSimpleName(), roleRequest);
        return roleMapper.toResponse(newRole);
    }

    @Override
    public RoleResponse update(Language language, RoleUpdateRequest roleUpdateRequest) {
        return null;
    }

    @Override
    public RoleResponse softdelete(Language language, Long id) {
        return null;
    }


}
