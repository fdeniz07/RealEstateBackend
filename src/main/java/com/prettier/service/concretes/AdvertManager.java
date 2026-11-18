package com.prettier.service.concretes;

import com.prettier.entity.concretes.Advert;
import com.prettier.entity.concretes.AdvertType;
import com.prettier.entity.concretes.Category;
import com.prettier.entity.concretes.City;
import com.prettier.entity.concretes.Country;
import com.prettier.entity.concretes.District;
import com.prettier.entity.concretes.User;
import com.prettier.entity.enums.AdvertStatus;
import com.prettier.payloads.mapper.AdvertMapper;
import com.prettier.payloads.request.concretes.AdvertCreateRequest;
import com.prettier.payloads.request.concretes.AdvertUpdateRequest;
import com.prettier.payloads.response.concretes.AdvertResponse;
import com.prettier.repository.AdvertRepository;
import com.prettier.repository.AdvertTypeRepository;
import com.prettier.repository.CategoryRepository;
import com.prettier.repository.CityRepository;
import com.prettier.repository.CountryRepository;
import com.prettier.repository.DistrictRepository;
import com.prettier.service.abstracts.AdvertService;
import com.prettier.service.abstracts.UserService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.adverts.AdvertAlreadyDeletedException;
import com.prettier.shared.exception.exceptions.adverts.AdvertAlreadyExistsException;
import com.prettier.shared.exception.exceptions.adverts.AdvertCreateForbiddenException;
import com.prettier.shared.exception.exceptions.adverts.AdvertInvalidLocationException;
import com.prettier.shared.exception.exceptions.adverts.AdvertNotFoundException;
import com.prettier.shared.exception.exceptions.adverts.AdvertTypeNotFoundException;
import com.prettier.shared.exception.exceptions.categories.CategoryNotFoundException;
import com.prettier.shared.exception.exceptions.cities.CityNotFoundException;
import com.prettier.shared.exception.exceptions.countries.CountryNotFoundException;
import com.prettier.shared.exception.exceptions.districts.DistrictNotFoundException;
import com.prettier.shared.exception.exceptions.users.UserNotFoundException;
import com.prettier.shared.utils.enums.Language;
import com.prettier.shared.utils.slug.SlugNormalizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * İlan işlemlerini yöneten servis sınıfı.
 * Bu sınıf, ilanların listelenmesi, eklenmesi, güncellenmesi ve silinmesi gibi
 * temel CRUD operasyonlarını gerçekleştirir.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdvertManager implements AdvertService {

    private final AdvertRepository advertRepository;
    private final AdvertTypeRepository advertTypeRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final AdvertMapper advertMapper;
    private final SlugNormalizer slugNormalizer;


    /**
     * Tüm ilanları sayfalı şekilde getirir.
     *
     * @param language Kullanılacak dil
     * @param page Sayfa numarası
     * @param size Sayfa başına kayıt sayısı
     * @param sort Sıralama yapılacak alan
     * @param type Sıralama tipi (asc/desc)
     * @return İlanların listesini içeren sayfa
     */
    @Override
    public Page<AdvertResponse> getAll(Language language, int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return advertRepository.findAll(pageable).map(advertMapper::toResponse);
    }

    /**
     * Aktif ilanları sayfalı şekilde getirir.
     *
     * @param language Kullanılacak dil
     * @param page Sayfa numarası
     * @param size Sayfa başına kayıt sayısı
     * @param sort Sıralama yapılacak alan
     * @param type Sıralama tipi (asc/desc)
     * @return Aktif ilanların listesini içeren sayfa
     */
    @Override
    public Page<AdvertResponse> getListWithActive(Language language, int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return advertRepository.findAllActive(pageable).map(advertMapper::toResponse);
    }

    /**
     * ID'ye göre tüm durumda olan ilanı getirir.
     *
     * @param language Kullanılacak dil
     * @param id İlanın ID'si
     * @return İlan detayları
     */
    @Override
    public AdvertResponse getByIdAllType(Language language, Long id) {
        return null;
    }

    /**
     * ID'ye göre aktif ilanı getirir.
     *
     * @param language Kullanılacak dil
     * @param id İlanın ID'si
     * @return Aktif ilan detayları
     * @throws AdvertNotFoundException İlan bulunamadığında
     */
    @Override
    public AdvertResponse getByIdActive(Language language, Long id) {
        log.debug("[{}][getAdvert] -> request advertId: {}", this.getClass().getSimpleName(), id);
        Advert advert = advertRepository.getByIdAndIsActiveFalse(id);
        if (Objects.isNull(advert)) {
            throw new AdvertNotFoundException(language, FriendlyMessageCodes.ADVERT_NOT_FOUND_EXCEPTION, "Advert not found for advert id: " + id);
        }
        log.debug("[{}][getAdvert] -> response: {}", this.getClass().getSimpleName(), advert);
        return advertMapper.toResponse(advert);
    }


    /**
     * Yeni bir ilan ekler. Sahip, slug ve durum istemciden alınmaz.
     *
     * @param language Kullanılacak dil
     * @param advertCreateRequest Eklenecek ilan bilgileri
     * @return Eklenen ilan
     */
    @Override
    @Transactional
    public Advert add(Language language, AdvertCreateRequest advertCreateRequest) {

        log.debug("[{}][createdAdvert] -> request: {}", this.getClass().getSimpleName(), advertCreateRequest);

        assertCustomerAuthority(language);

        AdvertType advertType = advertTypeRepository.findById(advertCreateRequest.getAdvertTypeId())
                .orElseThrow(() -> new AdvertTypeNotFoundException(
                        language,
                        FriendlyMessageCodes.ADVERT_TYPE_NOT_FOUND_EXCEPTION,
                        "Advert type not found for id: " + advertCreateRequest.getAdvertTypeId()));
        if (advertType.isDeleted()) {
            throw new AdvertTypeNotFoundException(
                    language,
                    FriendlyMessageCodes.ADVERT_TYPE_NOT_FOUND_EXCEPTION,
                    "Advert type not found for id: " + advertCreateRequest.getAdvertTypeId());
        }
        Country country = countryRepository.findById(advertCreateRequest.getCountryId())
                .orElseThrow(() -> new CountryNotFoundException(
                        language,
                        FriendlyMessageCodes.COUNTRY_NOT_FOUND_EXCEPTION,
                        "Country not found for id: " + advertCreateRequest.getCountryId()));
        City city = cityRepository.findById(advertCreateRequest.getCityId())
                .orElseThrow(() -> new CityNotFoundException(
                        language,
                        FriendlyMessageCodes.CITY_NOT_FOUND_EXCEPTION,
                        "City not found for id: " + advertCreateRequest.getCityId()));
        District district = districtRepository.findById(advertCreateRequest.getDistrictId())
                .orElseThrow(() -> new DistrictNotFoundException(
                        language,
                        FriendlyMessageCodes.DISTRICT_NOT_FOUND_EXCEPTION,
                        "District not found for id: " + advertCreateRequest.getDistrictId()));
        Category category = categoryRepository.findById(advertCreateRequest.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(
                        language,
                        FriendlyMessageCodes.CATEGORY_NOT_FOUND_EXCEPTION,
                        "Category not found for id: " + advertCreateRequest.getCategoryId()));
        if (!category.isActive() || category.isDeleted()) {
            throw new CategoryNotFoundException(
                    language,
                    FriendlyMessageCodes.CATEGORY_NOT_FOUND_EXCEPTION,
                    "Category not found for id: " + advertCreateRequest.getCategoryId());
        }

        assertLocationHierarchy(language, country, city, district);

        User owner = resolveAuthenticatedOwner(language);
        String slug = slugNormalizer.normalize(advertCreateRequest.getTitle(), toSlugLanguage(language));
        if (advertRepository.existsBySlugIgnoreCase(slug)) {
            throw new AdvertAlreadyExistsException(
                    language,
                    FriendlyMessageCodes.ADVERT_ALREADY_EXISTS,
                    "Advert slug already exists: " + slug);
        }

        Advert advert = advertMapper.toAdvert(advertCreateRequest);
        advert.setAdvertType(advertType);
        advert.setCountry(country);
        advert.setCity(city);
        advert.setDistrict(district);
        advert.setCategory(category);
        advert.setUser(owner);
        advert.setSlug(slug);
        advert.setAdvertStatus(AdvertStatus.PENDING);
        advert.setActive(true);
        advert.setViewCount(0);
        advert.setBuiltIn(false);

        try {
            Advert savedAdvert = advertRepository.saveAndFlush(advert);
            log.debug("[{}][createAdvert] -> response: {}", this.getClass().getSimpleName(), savedAdvert);
            return savedAdvert;
        } catch (DataIntegrityViolationException exception) {
            throw new AdvertAlreadyExistsException(
                    language,
                    FriendlyMessageCodes.ADVERT_ALREADY_EXISTS,
                    "Advert slug unique constraint violated: " + slug);
        }
    }


    /**
     * Var olan bir ilanı günceller.
     *
     * @param language Kullanılacak dil
     * @param advertUpdateRequest Güncellenecek ilan bilgileri
     * @param id Güncellenecek ilanın ID'si
     * @return Güncellenmiş ilan
     * @throws AdvertNotFoundException İlan bulunamadığında
     */
    @Override
    public Advert update(Language language, AdvertUpdateRequest advertUpdateRequest, Long id) {
        log.debug("[{}][updateAdvert] -> request: {} {}", this.getClass().getSimpleName(), id, advertUpdateRequest);

        //Advert Var mi kontrolü
        Advert existingAdvert = getAdvert(language, id);

       Advert updatedAdvert=  advertMapper.toUpdatedAdvert(advertUpdateRequest, id,existingAdvert);
       advertRepository.save(updatedAdvert);
        log.debug("[{}][updateAdvert] -> response: {}", this.getClass().getSimpleName(), updatedAdvert);
        return updatedAdvert;
    }


    /**
     * İlanı pasif duruma getirir (soft delete).
     *
     * @param language Kullanılacak dil
     * @param id Silinecek ilanın ID'si
     * @return Pasif duruma getirilmiş ilan
     * @throws AdvertAlreadyDeletedException İlan zaten silinmiş olduğunda
     */
    @Override
    public Advert delete(Language language, Long id) {
        log.debug("[{}][deleteAdvert] -> request advertId: {}", this.getClass().getSimpleName(), id);
        Advert advert;

        advert = advertRepository.findById(id).orElseThrow(() -> new AdvertAlreadyDeletedException(language, FriendlyMessageCodes.ADVERT_ALREADY_DELETED, "Advert already deleted advert id: " + id));
        advert.setActive(false);
        return advertRepository.save(advert);
    }

    // Ilgili Id, Advert tablosunda var mi kontrolü
    /**
     * ID'ye göre ilanı getirir. Bulunamazsa hata fırlatır.
     *
     * @param language Kullanılacak dil
     * @param advertId İlanın ID'si
     * @return Bulunan ilan
     * @throws AdvertNotFoundException İlan bulunamadığında
     */
    public Advert getAdvert(Language language, Long advertId) {

        log.debug("[{}][getAdvert] -> request advertId: {}", this.getClass().getSimpleName(), advertId);
        Advert advert = advertRepository.findById(advertId).orElseThrow(() -> new AdvertNotFoundException(language, FriendlyMessageCodes.ADVERT_NOT_FOUND_EXCEPTION, "Advert not found for advert id: " + advertId));

        log.debug("[{}][getAdvert] -> response: {}", this.getClass().getSimpleName(), advert);
        return advert;
    }

    private void assertCustomerAuthority(Language language) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getPrincipal() == null
                || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new AdvertCreateForbiddenException(
                    language,
                    FriendlyMessageCodes.ADVERT_CREATE_FORBIDDEN,
                    "Authenticated customer is required to create an advert");
        }
        boolean hasCustomerRole =
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .anyMatch("CUSTOMER"::equals);
        if (!hasCustomerRole) {
            throw new AdvertCreateForbiddenException(
                    language,
                    FriendlyMessageCodes.ADVERT_CREATE_FORBIDDEN,
                    "Only users with CUSTOMER authority may create adverts");
        }
    }

    private void assertLocationHierarchy(Language language, Country country, City city, District district) {
        if (city.getCountry() == null || !Objects.equals(city.getCountry().getId(), country.getId())) {
            throw new AdvertInvalidLocationException(
                    language,
                    FriendlyMessageCodes.ADVERT_LOCATION_MISMATCH,
                    "City id " + city.getId() + " does not belong to country id " + country.getId());
        }
        if (district.getCity() == null || !Objects.equals(district.getCity().getId(), city.getId())) {
            throw new AdvertInvalidLocationException(
                    language,
                    FriendlyMessageCodes.ADVERT_LOCATION_MISMATCH,
                    "District id " + district.getId() + " does not belong to city id " + city.getId());
        }
    }

    private User resolveAuthenticatedOwner(Language language) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userService.getUserByEmail(language, userDetails.getUsername());
        }
        throw new UserNotFoundException(
                language,
                FriendlyMessageCodes.USER_NOT_FOUND_EXCEPTION,
                "Authenticated user is required to create an advert");
    }

    /**
     * {@link SlugNormalizer} accepts {@code tr} and {@code de}. API locale {@code EN} uses {@code de}
     * so ASCII titles still normalize; Turkish mappings apply only for {@link Language#TR}.
     */
    private String toSlugLanguage(Language language) {
        if (language == Language.TR) {
            return "tr";
        }
        return "de";
    }
}
