package com.prettier.service.concretes;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.enums.RoleType;
import com.prettier.payload.mapper.RoleMapper;
import com.prettier.payload.request.concretes.RoleRequest;
import com.prettier.payload.response.concretes.RoleResponse;
import com.prettier.repository.RoleRepository;
import com.prettier.service.abstracts.RoleService;
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

        Role role =  roleRepository.findByNameEquals("ADMIN");
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
    public RoleResponse save(Language language, RoleRequest roleRequest) {

        log.debug("[{}][createRole] -> request: {}", this.getClass().getSimpleName(), roleRequest);

        //!!! User'e ait Role'ler getiriliyor mu?
//        Set<Role> roles = userService.getRolesById(roleRequest.getUserIds());

//        if (roles.isEmpty()) {
//            throw new RoleNotFoundException(language, FriendlyMessageCodes.ROLE_NOT_FOUND_EXCEPTION, "role request: " + roleRequest);
//        }

        Role role = roleMapper.toRole(roleRequest);

        Role roleResponse = roleRepository.save(role);
        log.debug("[{}][createRole] -> response: {}", this.getClass().getSimpleName(), roleResponse);
        return roleMapper.toResponse(roleResponse);


//        // Veritabanındaki tüm rolleri getir
//        Set<Role> dbRoles = new HashSet<>(roleRepository.findAll());
//
//
//        // Kullanıcı rollerini ve veritabanındaki rolleri karşılaştır
//        Set<Role> missingRoles = new HashSet<>(userRequest.getRoles());
//        missingRoles.removeAll(dbRoles); // Eksik olan rolleri belirle
//
//
//        // Eksik olan rolleri veritabanına ekle
//        for (Role missingRole : missingRoles) {
//            roleRepository.save(missingRole);
//        }
//
//        // Veritabanındaki tüm rolleri tekrar getir ve döndür
//        return new HashSet<>(roleRepository.findAll());


//        if (areRolesValid(userRequest.getRoles())) { //role varsa
//
//            throw new ConflictException(language, FriendlyMessageCodes.ROLE_ALREADY_EXISTS, "Role already exists");
//        }
//
//
//        return roleRepository.save(role);
    }


}
