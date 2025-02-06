package com.prettier.service.concretes;

import com.prettier.entity.concretes.Advert;
import com.prettier.payloads.mapper.AdvertMapper;
import com.prettier.payloads.request.concretes.AdvertRequest;
import com.prettier.payloads.request.concretes.AdvertUpdateRequest;
import com.prettier.payloads.response.concretes.AdvertResponse;
import com.prettier.repository.AdvertRepository;
import com.prettier.service.abstracts.AdvertService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.adverts.AdvertAlreadyDeletedException;
import com.prettier.shared.exception.exceptions.adverts.AdvertNotCreatedException;
import com.prettier.shared.exception.exceptions.adverts.AdvertNotFoundException;
import com.prettier.shared.utils.enums.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    private final AdvertMapper advertMapper;


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
     * Yeni bir ilan ekler.
     *
     * @param language Kullanılacak dil
     * @param advertRequest Eklenecek ilan bilgileri
     * @return Eklenen ilan
     * @throws AdvertNotCreatedException İlan oluşturulamadığında
     */
    @Override
    public Advert add(Language language, AdvertRequest advertRequest) {

        log.debug("[{}][createdAdvert] -> request: {}", this.getClass().getSimpleName(), advertRequest);
        try {
            Advert advert = advertMapper.toAdvert(advertRequest);
            Advert savedAdvert = advertRepository.save(advert);
            log.debug("[{}][createAdvert] -> response: {}", this.getClass().getSimpleName(), savedAdvert);
            return savedAdvert;
        } catch (Exception exception) {
            throw new AdvertNotCreatedException(language, FriendlyMessageCodes.ADVERT_NOT_CREATED_EXCEPTION, "advert request: " + advertRequest.toString());
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
}
