package com.prettier.service.concretes;

import com.prettier.entity.concretes.City;
import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.payload.mapper.RoleMapper;
import com.prettier.payload.request.concretes.RoleRequest;
import com.prettier.payload.response.concretes.CityResponse;
import com.prettier.payload.response.concretes.RoleResponse;
import com.prettier.repository.RoleRepository;
import com.prettier.service.abstracts.RoleService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.cities.CityAlreadyExistsException;
import com.prettier.shared.exception.exceptions.cities.CityNotFoundException;
import com.prettier.shared.exception.exceptions.roles.RoleAlreadyDeletedException;
import com.prettier.shared.exception.exceptions.roles.RoleAlreadyExistsException;
import com.prettier.shared.exception.exceptions.roles.RoleNotFoundException;
import com.prettier.shared.utils.enums.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleManager implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    //NOT: *********** Data Inilitalizer icin gerekli metotlar *************************

    @Override
    public Set<Role> getByRoleName(String roleName) {

        Role role = roleRepository.findByNameEquals(roleName);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        return roleSet;
    }

    //Not: getByUser() ********************************************************************
    @Override
    public Set<Role> getByUser(User user) {

        Set<Long> roleIds = user.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toSet());

        Set<Role> roles = new HashSet<>();
        roles.addAll(roleRepository.findAllById(roleIds));

        return roles;
    }

    //Not: getRoleList ***************************************************************
    @Override
    public List<Role> getRoleList() {

        return roleRepository.findAll();
    }


    //NOT: *********** Role Manager standart metotlar *************************************

    @Override
    public RoleResponse save(Language language, RoleRequest roleRequest) {

        log.debug("[{}][createRole] -> request: {}", this.getClass().getSimpleName(), roleRequest);
        Role role = roleMapper.toRole(roleRequest);

        Role roleResponse = roleRepository.save(role);
        log.debug("[{}][createRole] -> response: {}", this.getClass().getSimpleName(), roleResponse);
        return roleMapper.toResponse(roleResponse);
    }


    //Not: getAll() *********************************************************************************************************************************
    @Override
    public Page<RoleResponse> getRoles(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getRoles]", this.getClass().getSimpleName());
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            PageRequest.of(page, size, Sort.by(sort).descending());
        }

        List<RoleResponse> roles = getRoleList()
                .stream()
                .map(roleMapper::toResponse)
                .toList();

        if (roles.isEmpty()) {
            throw new RoleNotFoundException(language, FriendlyMessageCodes.ROLE_NOT_FOUND_EXCEPTION, "Roles not found");
        }
        log.debug("[{}][getRoles] -> response: {}", this.getClass().getSimpleName(), roles);
        return roleRepository.findAll(pageable).map(roleMapper::toResponse);
    }


    //Not: getById() ****************************************************************************************************
    @Override
    public RoleResponse getById(Language language, Long id) {

        log.debug("[{}][getById] -> request roleId: {}", this.getClass().getSimpleName(), id);

        Role role = roleRepository.findById(id).orElseThrow(
                () -> new RoleNotFoundException(language, FriendlyMessageCodes.ROLE_NOT_FOUND_EXCEPTION, "Role not found for role id: " + id));

        log.debug("[{}][getById] -> response: {}", this.getClass().getSimpleName(), role);
        return roleMapper.toResponse(role);
    }


    //Not: addRole() ******************************************************************************************************

    @Override
    public RoleResponse add(Language language, RoleRequest roleRequest) {

        log.debug("[{}][newRole] -> request: {}", this.getClass().getSimpleName(), roleRequest);

        //Rol var mi kontrolü
        if (roleRepository.existsByNameEquals(roleRequest.getRoleName())) {
            throw new RoleAlreadyExistsException(language, FriendlyMessageCodes.ROLE_ALREADY_EXISTS, "Role already exists");
        }

        Role role = roleMapper.toRole(roleRequest);
        Role newRole = roleRepository.save(role);

        log.debug("[{}][newRole] -> response: {}", this.getClass().getSimpleName(), roleRequest);
        return roleMapper.toResponse(newRole);
    }

    //Not: updateRole() ******************************************************************************************************
    @Override
    public RoleResponse update(Language language, RoleRequest roleRequest) {

        log.debug("[{}][newRole] -> request: {}", this.getClass().getSimpleName(), roleRequest);

        List<Role> roles = roleRepository.findAll();

        //Rol var mi kontrolü
        if (roleRepository.existsByNameEquals(roleRequest.getRoleName())) {
            throw new RoleAlreadyExistsException(language, FriendlyMessageCodes.ROLE_ALREADY_EXISTS, "Role already exists");
        }

        Role role = roleMapper.toRole(roleRequest);
        Role updatedRole = roleRepository.saveAndFlush(role);

        log.debug("[{}][newRole] -> response: {}", this.getClass().getSimpleName(), roleRequest);
        return roleMapper.toResponse(updatedRole);
    }

    //Not: delete() ************************************************************************************************************
    @Override
    public RoleResponse softdelete(Language language, Long id) {

        log.debug("[{}][newRole] -> request: {}", this.getClass().getSimpleName(), id);

        Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(language, FriendlyMessageCodes.ROLE_NOT_FOUND_EXCEPTION, "Role not found."));

        try {
            role = getRole(language, id);
            role.setDeleted(true);

            RoleResponse response = roleMapper.toResponse(roleRepository.save(role));
            log.debug("[{}][newRole] -> response: {}", this.getClass().getSimpleName(), response);
            return response;
        } catch (RoleNotFoundException exception) {
            throw new RoleAlreadyDeletedException(language, FriendlyMessageCodes.ROLE_ALREADY_DELETED, "Role already deleted role id: " + id);
        }
    }

    //Not: Other *********************************************************************************************************************************

    //!!! Ilgili Id, Role tablosunda var mi kontrolü
    public Role getRole(Language language, Long id) {

        log.debug("[{}][getRole] -> request roleId: {}", this.getClass().getSimpleName(), id);
        Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(language, FriendlyMessageCodes.ROLE_NOT_FOUND_EXCEPTION, "Role not found for role id: " + id));

        log.debug("[{}][getRole] -> response: {}", this.getClass().getSimpleName(), role);
        return role;
    }

    //!!! Ilgili CityName, City tablosunda var mi kontrolü
    public boolean existsByRoleName(Language language, String roleName) {

        log.debug("[{}][existsByRoleName] -> request roleName: {}", this.getClass().getSimpleName(), roleName);
        if (roleRepository.existsByName(roleName)) {
            throw new CityAlreadyExistsException(language, FriendlyMessageCodes.ROLE_ALREADY_EXISTS, "This Role already exists for role name: " + roleName);
        }

        log.debug("[{}][existsByRoleName] -> response: {}", this.getClass().getSimpleName(), roleName);
        return false;
    }

}
