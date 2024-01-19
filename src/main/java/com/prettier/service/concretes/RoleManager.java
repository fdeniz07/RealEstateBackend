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

    //Runner tarafi icin gerekli method
    @Override
    public List<Role> getAllUserRole() {
        return roleRepository.findAll();
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


//    @Override
//    public List<Role> getRolesByUser(Long userId) {
//        return roleRepository.findAllByUserId(userId);
//    }
//
//    @Override
//    public Role getRoleByType(Role role) {
//
//        return roleRepository.findByRoleTypeEquals(role)
//                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + role));
//
//    }


    public Set<Role> synchronizeRoles(Set<Role> userRoles) {
        // Veritabanındaki tüm rolleri getir
        Set<Role> dbRoles = new HashSet<>(roleRepository.findAll());

        // Kullanıcı rollerini ve veritabanındaki rolleri karşılaştır
        Set<Role> missingRoles = new HashSet<>(userRoles);
        missingRoles.removeAll(dbRoles); // Eksik olan rolleri belirle

        // Eksik olan rolleri veritabanına ekle
        for (Role missingRole : missingRoles) {
            roleRepository.save(missingRole);
        }

        // Veritabanındaki tüm rolleri tekrar getir ve döndür
        return new HashSet<>(roleRepository.findAll());
    }


    // Uygulama başlatıldığında çalışacak metot

    //Runner tarafi icin gerekli method
    public Role builtInSave(Language language, RoleType roleType) {

//        if (roleRepository.existsByRoleEquals(roleType)) { //role varsa
//
//            throw new ConflictException(language, FriendlyMessageCodes.ROLE_ALREADY_EXISTS, "Role already exists");
//        }

        Role role = Role.builder().name(roleType.name()).build();
        return roleRepository.save(role);
    }


    @Override
    public Set<Role> getRoleByIds(Long[] roleIds) {

        return roleRepository.findByIdsEquals(roleIds);
    }


//    @PostConstruct
//    public void init() {
//        // Enum değerlerini döngü ile gezerek her birini veritabanına kaydet
//        for (RoleType roleType : RoleType.values()) {
//            // Veritabanında bu rol adıyla kayıtlı bir rol yoksa eklenir
//            if (!roleRepository.existsByRole_NameEquals(roleType.name())) {
//                Role role = new Role();
//                role.setRoleName(roleType);
//                // Diğer rol özelliklerini de belirleyebilirsiniz
//                roleRepository.save(role);
//            }
//        }
//    }

}
