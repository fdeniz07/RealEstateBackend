package com.prettier.service.concretes;

import com.prettier.entity.concretes.Advert;
import com.prettier.entity.concretes.AdvertTranslation;
import com.prettier.entity.concretes.AdvertType;
import com.prettier.entity.concretes.Category;
import com.prettier.entity.concretes.City;
import com.prettier.entity.concretes.Country;
import com.prettier.entity.concretes.District;
import com.prettier.entity.concretes.User;
import com.prettier.entity.enums.AdvertStatus;
import com.prettier.entity.enums.TextDirection;
import com.prettier.entity.enums.TranslationPublicationStatus;
import com.prettier.payloads.mapper.AdvertMapper;
import com.prettier.payloads.request.concretes.AdvertCreateRequest;
import com.prettier.repository.AdvertRepository;
import com.prettier.repository.AdvertTranslationRepository;
import com.prettier.repository.LanguageRepository;
import com.prettier.repository.AdvertTypeRepository;
import com.prettier.repository.CategoryRepository;
import com.prettier.repository.CityRepository;
import com.prettier.repository.CountryRepository;
import com.prettier.repository.DistrictRepository;
import com.prettier.service.abstracts.UserService;
import com.prettier.shared.exception.exceptions.adverts.AdvertAlreadyExistsException;
import com.prettier.shared.exception.exceptions.adverts.AdvertCreateForbiddenException;
import com.prettier.shared.exception.exceptions.adverts.AdvertInvalidContentLanguageException;
import com.prettier.shared.exception.exceptions.adverts.AdvertInvalidLocationException;
import com.prettier.shared.exception.exceptions.adverts.AdvertTypeNotFoundException;
import com.prettier.shared.i18n.registry.LanguageCapability;
import com.prettier.shared.i18n.registry.LanguageDescriptor;
import com.prettier.shared.i18n.registry.LanguageRegistryService;
import com.prettier.shared.i18n.registry.UnsupportedLanguageException;
import com.prettier.shared.exception.exceptions.categories.CategoryNotFoundException;
import com.prettier.shared.exception.exceptions.cities.CityNotFoundException;
import com.prettier.shared.utils.enums.Language;
import com.prettier.shared.utils.slug.SlugNormalizer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdvertManagerTest {

    private static final Language LANG = Language.EN;
    private static final String TITLE = "Luxury Villa Downtown";
    private static final String SLUG = "luxury-villa-downtown";
    private static final String OWNER_EMAIL = "owner@mail.com";

    @Mock private AdvertRepository advertRepository;
    @Mock private AdvertTypeRepository advertTypeRepository;
    @Mock private CountryRepository countryRepository;
    @Mock private CityRepository cityRepository;
    @Mock private DistrictRepository districtRepository;
    @Mock private CategoryRepository categoryRepository;
    @Mock private UserService userService;
    @Mock private AdvertMapper advertMapper;
    @Mock private SlugNormalizer slugNormalizer;
    @Mock private LanguageRegistryService languageRegistryService;
    @Mock private LanguageRepository languageRepository;
    @Mock private AdvertTranslationRepository advertTranslationRepository;

    private AdvertManager advertManager;
    private com.prettier.entity.concretes.Language germanLanguage;

    private AdvertType advertType;
    private Country country;
    private City city;
    private District district;
    private Category category;
    private User owner;
    private AdvertCreateRequest request;

    @BeforeEach
    void setUp() {
        advertManager =
                new AdvertManager(
                        advertRepository,
                        advertTypeRepository,
                        countryRepository,
                        cityRepository,
                        districtRepository,
                        categoryRepository,
                        userService,
                        advertMapper,
                        slugNormalizer,
                        languageRegistryService,
                        languageRepository,
                        advertTranslationRepository);

        germanLanguage =
                com.prettier.entity.concretes.Language.builder()
                        .code("de")
                        .name("German")
                        .nativeName("Deutsch")
                        .active(true)
                        .uiSupported(true)
                        .contentSupported(true)
                        .textDirection(TextDirection.LTR)
                        .sortOrder((short) 10)
                        .build();

        advertType = new AdvertType();
        advertType.setId(1L);
        advertType.setTitle("Sale");

        country = new Country();
        country.setId(10L);
        country.setCode("TR");

        city = new City();
        city.setId(20L);
        city.setCountry(country);

        district = new District();
        district.setId(30L);
        district.setCity(city);

        category = new Category();
        category.setId(40L);
        category.setTitle("Villa");
        category.setActive(true);

        owner = new User();
        owner.setId(42L);
        owner.setEmail(OWNER_EMAIL);

        request =
                AdvertCreateRequest.builder()
                        .contentLanguage("de")
                        .title(TITLE)
                        .desc("Sea view villa")
                        .price(1_250_000D)
                        .location("Downtown")
                        .advertTypeId(1L)
                        .countryId(10L)
                        .cityId(20L)
                        .districtId(30L)
                        .categoryId(40L)
                        .build();

        setSecurityContext(authority("CUSTOMER"));
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void add_validRequest_createsAdvertWithGeneratedSlugAndPendingStatus() {
        stubSuccessfulLookups();
        stubContentLanguage("de");
        when(slugNormalizer.normalize(TITLE, "de")).thenReturn(SLUG);
        when(advertRepository.existsBySlugIgnoreCase(SLUG)).thenReturn(false);
        when(advertTranslationRepository.existsByLanguageCodeAndSlugIgnoreCase("de", SLUG)).thenReturn(false);
        when(advertMapper.toAdvert(request)).thenReturn(new Advert());
        when(advertRepository.saveAndFlush(any(Advert.class)))
                .thenAnswer(
                        invocation -> {
                            Advert saved = invocation.getArgument(0);
                            saved.setId(100L);
                            return saved;
                        });
        when(advertTranslationRepository.save(any(AdvertTranslation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Advert created = advertManager.add(LANG, request);

        assertThat(created.getId()).isEqualTo(100L);
        assertThat(created.getSlug()).isEqualTo(SLUG);
        assertThat(created.getSourceLanguage()).isSameAs(germanLanguage);
        assertThat(created.getAdvertStatus()).isEqualTo(AdvertStatus.PENDING);
        assertThat(created.isActive()).isTrue();
        assertThat(created.getViewCount()).isZero();
        assertThat(created.isBuiltIn()).isFalse();
        assertThat(created.getAdvertType()).isSameAs(advertType);
        assertThat(created.getCountry()).isSameAs(country);
        assertThat(created.getCity()).isSameAs(city);
        assertThat(created.getDistrict()).isSameAs(district);
        assertThat(created.getCategory()).isSameAs(category);
        verify(slugNormalizer).normalize(TITLE, "de");
        verify(advertRepository).saveAndFlush(created);
        verify(advertTranslationRepository).save(any(AdvertTranslation.class));
    }

    @Test
    void add_assignsAuthenticatedUserAsOwner() {
        stubSuccessfulLookups();
        stubContentLanguage("de");
        when(slugNormalizer.normalize(TITLE, "de")).thenReturn(SLUG);
        when(advertRepository.existsBySlugIgnoreCase(SLUG)).thenReturn(false);
        when(advertTranslationRepository.existsByLanguageCodeAndSlugIgnoreCase("de", SLUG)).thenReturn(false);
        Advert mapped = new Advert();
        User injected = new User();
        injected.setId(999L);
        mapped.setUser(injected);
        when(advertMapper.toAdvert(request)).thenReturn(mapped);
        when(advertRepository.saveAndFlush(any(Advert.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(advertTranslationRepository.save(any(AdvertTranslation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Advert created = advertManager.add(LANG, request);

        assertThat(created.getUser()).isSameAs(owner);
        assertThat(created.getUser().getId()).isEqualTo(42L);
        verify(userService).getUserByEmail(LANG, OWNER_EMAIL);
    }

    @Test
    void add_adminAuthority_throwsForbiddenAndDoesNotPersist() {
        setSecurityContext(authority("ADMIN"));

        assertThatThrownBy(() -> advertManager.add(LANG, request))
                .isInstanceOf(AdvertCreateForbiddenException.class);

        verify(advertRepository, never()).saveAndFlush(any());
        verify(advertTypeRepository, never()).findById(any());
    }

    @Test
    void add_missingCustomerAuthority_throwsForbiddenAndDoesNotPersist() {
        setSecurityContext(List.of());

        assertThatThrownBy(() -> advertManager.add(LANG, request))
                .isInstanceOf(AdvertCreateForbiddenException.class);

        verify(advertRepository, never()).saveAndFlush(any());
    }

    @Test
    void add_inactiveCategory_throwsNotFoundAndDoesNotPersist() {
        category.setActive(false);
        stubReferenceLookups();

        assertThatThrownBy(() -> advertManager.add(LANG, request))
                .isInstanceOf(CategoryNotFoundException.class);

        verify(advertRepository, never()).saveAndFlush(any());
        verify(slugNormalizer, never()).normalize(any(), any());
    }

    @Test
    void add_deletedCategory_throwsNotFoundAndDoesNotPersist() {
        category.setDeleted(true);
        stubReferenceLookups();

        assertThatThrownBy(() -> advertManager.add(LANG, request))
                .isInstanceOf(CategoryNotFoundException.class);

        verify(advertRepository, never()).saveAndFlush(any());
    }

    @Test
    void add_deletedAdvertType_throwsNotFoundAndDoesNotPersist() {
        advertType.setDeleted(true);
        when(advertTypeRepository.findById(1L)).thenReturn(Optional.of(advertType));

        assertThatThrownBy(() -> advertManager.add(LANG, request))
                .isInstanceOf(AdvertTypeNotFoundException.class);

        verify(advertRepository, never()).saveAndFlush(any());
        verify(categoryRepository, never()).findById(any());
    }

    @Test
    void add_missingAdvertType_throwsNotFoundAndDoesNotPersist() {
        when(advertTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> advertManager.add(LANG, request))
                .isInstanceOf(AdvertTypeNotFoundException.class);

        verify(advertRepository, never()).saveAndFlush(any());
        verify(categoryRepository, never()).findById(any());
    }

    @Test
    void add_missingCategory_throwsNotFoundAndDoesNotPersist() {
        when(advertTypeRepository.findById(1L)).thenReturn(Optional.of(advertType));
        when(countryRepository.findById(10L)).thenReturn(Optional.of(country));
        when(cityRepository.findById(20L)).thenReturn(Optional.of(city));
        when(districtRepository.findById(30L)).thenReturn(Optional.of(district));
        when(categoryRepository.findById(40L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> advertManager.add(LANG, request))
                .isInstanceOf(CategoryNotFoundException.class);

        verify(advertRepository, never()).saveAndFlush(any());
        verify(slugNormalizer, never()).normalize(any(), any());
    }

    @Test
    void add_missingCity_throwsNotFoundAndDoesNotPersist() {
        when(advertTypeRepository.findById(1L)).thenReturn(Optional.of(advertType));
        when(countryRepository.findById(10L)).thenReturn(Optional.of(country));
        when(cityRepository.findById(20L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> advertManager.add(LANG, request))
                .isInstanceOf(CityNotFoundException.class);

        verify(advertRepository, never()).saveAndFlush(any());
        verify(slugNormalizer, never()).normalize(any(), any());
    }

    @Test
    void add_cityDoesNotBelongToCountry_throwsLocationMismatch() {
        Country otherCountry = new Country();
        otherCountry.setId(11L);
        city.setCountry(otherCountry);
        stubReferenceLookups();

        assertThatThrownBy(() -> advertManager.add(LANG, request))
                .isInstanceOf(AdvertInvalidLocationException.class);

        verify(advertRepository, never()).saveAndFlush(any());
    }

    @Test
    void add_districtDoesNotBelongToCity_throwsLocationMismatch() {
        City otherCity = new City();
        otherCity.setId(21L);
        otherCity.setCountry(country);
        district.setCity(otherCity);
        stubReferenceLookups();

        assertThatThrownBy(() -> advertManager.add(LANG, request))
                .isInstanceOf(AdvertInvalidLocationException.class);

        verify(advertRepository, never()).saveAndFlush(any());
    }

    @Test
    void add_duplicateSlug_throwsConflictAndDoesNotPersist() {
        stubSuccessfulLookups();
        stubContentLanguage("de");
        when(slugNormalizer.normalize(TITLE, "de")).thenReturn(SLUG);
        when(advertRepository.existsBySlugIgnoreCase(SLUG)).thenReturn(true);

        assertThatThrownBy(() -> advertManager.add(LANG, request))
                .isInstanceOf(AdvertAlreadyExistsException.class);

        verify(advertRepository, never()).saveAndFlush(any());
    }

    @Test
    void add_uniqueConstraintViolation_isMappedToConflict() {
        stubSuccessfulLookups();
        stubContentLanguage("de");
        when(slugNormalizer.normalize(TITLE, "de")).thenReturn(SLUG);
        when(advertRepository.existsBySlugIgnoreCase(SLUG)).thenReturn(false);
        when(advertTranslationRepository.existsByLanguageCodeAndSlugIgnoreCase("de", SLUG)).thenReturn(false);
        when(advertMapper.toAdvert(request)).thenReturn(new Advert());
        when(advertRepository.saveAndFlush(any(Advert.class)))
                .thenThrow(new DataIntegrityViolationException("ux_adverts_slug_lower"));

        assertThatThrownBy(() -> advertManager.add(LANG, request))
                .isInstanceOf(AdvertAlreadyExistsException.class);
    }

    @Test
    void add_turkishContentLanguage_usesTurkishSlugLanguageRegardlessOfPathLanguage() {
        stubSuccessfulLookups();
        com.prettier.entity.concretes.Language turkish =
                com.prettier.entity.concretes.Language.builder()
                        .code("tr")
                        .name("Turkish")
                        .nativeName("Turkce")
                        .active(true)
                        .uiSupported(true)
                        .contentSupported(true)
                        .textDirection(TextDirection.LTR)
                        .sortOrder((short) 20)
                        .build();
        request.setContentLanguage("tr");
        stubContentLanguage("tr", turkish);
        when(slugNormalizer.normalize(TITLE, "tr")).thenReturn("luxury-villa-downtown");
        when(advertRepository.existsBySlugIgnoreCase("luxury-villa-downtown")).thenReturn(false);
        when(advertTranslationRepository.existsByLanguageCodeAndSlugIgnoreCase("tr", "luxury-villa-downtown"))
                .thenReturn(false);
        when(advertMapper.toAdvert(request)).thenReturn(new Advert());
        when(advertRepository.saveAndFlush(any(Advert.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(advertTranslationRepository.save(any(AdvertTranslation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        advertManager.add(Language.EN, request);

        verify(slugNormalizer).normalize(TITLE, "tr");
    }

    @Test
    void add_unknownContentLanguage_throwsBadRequestAndDoesNotPersist() {
        stubSuccessfulLookups();
        when(languageRegistryService.requireActive(eq("xx-invalid"), eq(LanguageCapability.ADVERT_CONTENT)))
                .thenThrow(new UnsupportedLanguageException("Language is not registered: xx-invalid"));
        request.setContentLanguage("xx-invalid");

        assertThatThrownBy(() -> advertManager.add(LANG, request))
                .isInstanceOf(AdvertInvalidContentLanguageException.class);

        verify(advertRepository, never()).saveAndFlush(any());
        verify(advertTranslationRepository, never()).save(any());
    }

    @Test
    void add_translationSlugConflict_throwsConflictAndDoesNotPersist() {
        stubSuccessfulLookups();
        stubContentLanguage("de");
        when(slugNormalizer.normalize(TITLE, "de")).thenReturn(SLUG);
        when(advertRepository.existsBySlugIgnoreCase(SLUG)).thenReturn(false);
        when(advertTranslationRepository.existsByLanguageCodeAndSlugIgnoreCase("de", SLUG)).thenReturn(true);

        assertThatThrownBy(() -> advertManager.add(LANG, request))
                .isInstanceOf(AdvertAlreadyExistsException.class);

        verify(advertRepository, never()).saveAndFlush(any());
    }

    @Test
    void add_persistsDraftTranslationMatchingAdvertFields() {
        stubSuccessfulLookups();
        stubContentLanguage("de");
        when(slugNormalizer.normalize(TITLE, "de")).thenReturn(SLUG);
        when(advertRepository.existsBySlugIgnoreCase(SLUG)).thenReturn(false);
        when(advertTranslationRepository.existsByLanguageCodeAndSlugIgnoreCase("de", SLUG)).thenReturn(false);
        when(advertMapper.toAdvert(request)).thenReturn(new Advert());
        when(advertRepository.saveAndFlush(any(Advert.class)))
                .thenAnswer(
                        invocation -> {
                            Advert saved = invocation.getArgument(0);
                            saved.setId(100L);
                            saved.setTitle(TITLE);
                            saved.setDesc("Sea view villa");
                            saved.setSlug(SLUG);
                            return saved;
                        });
        when(advertTranslationRepository.save(any(AdvertTranslation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        advertManager.add(LANG, request);

        verify(advertTranslationRepository)
                .save(
                        org.mockito.ArgumentMatchers.argThat(
                                translation ->
                                        translation.getTranslationPublicationStatus()
                                                        == TranslationPublicationStatus.DRAFT
                                                && TITLE.equals(translation.getTitle())
                                                && SLUG.equals(translation.getSlug())
                                                && "Sea view villa".equals(translation.getDesc())));
    }

    private static GrantedAuthority authority(String name) {
        return new SimpleGrantedAuthority(name);
    }

    private static void setSecurityContext(List<GrantedAuthority> authorities) {
        UserDetails details =
                new org.springframework.security.core.userdetails.User(
                        OWNER_EMAIL, "secret", authorities);
        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(details, null, authorities));
    }

    private static void setSecurityContext(GrantedAuthority authority) {
        setSecurityContext(List.of(authority));
    }

    private void stubSuccessfulLookups() {
        stubReferenceLookups();
        when(userService.getUserByEmail(any(), eq(OWNER_EMAIL))).thenReturn(owner);
    }

    private void stubReferenceLookups() {
        when(advertTypeRepository.findById(1L)).thenReturn(Optional.of(advertType));
        when(countryRepository.findById(10L)).thenReturn(Optional.of(country));
        when(cityRepository.findById(20L)).thenReturn(Optional.of(city));
        when(districtRepository.findById(30L)).thenReturn(Optional.of(district));
        when(categoryRepository.findById(40L)).thenReturn(Optional.of(category));
    }

    private void stubContentLanguage(String code) {
        stubContentLanguage(code, germanLanguage);
    }

    private void stubContentLanguage(String code, com.prettier.entity.concretes.Language entity) {
        when(languageRegistryService.canonicalizeCode(code)).thenReturn(code);
        when(languageRegistryService.requireActive(eq(code), eq(LanguageCapability.ADVERT_CONTENT)))
                .thenReturn(
                        new LanguageDescriptor(
                                code,
                                null,
                                entity.getName(),
                                entity.getNativeName(),
                                entity.isActive(),
                                entity.isUiSupported(),
                                entity.isContentSupported(),
                                entity.getTextDirection(),
                                entity.getSortOrder()));
        when(languageRepository.findById(code)).thenReturn(Optional.of(entity));
    }
}