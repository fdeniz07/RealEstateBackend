package com.prettier.service.concretes;

import com.prettier.entity.concretes.City;
import com.prettier.entity.concretes.District;
import com.prettier.payloads.mapper.CityMapper;
import com.prettier.payloads.mapper.DistrictMapper;
import com.prettier.payloads.request.concretes.DistrictRequest;
import com.prettier.payloads.request.concretes.DistrictUpdateRequest;
import com.prettier.payloads.response.concretes.DistrictResponse;
import com.prettier.repository.DistrictRepository;
import com.prettier.service.abstracts.CityService;
import com.prettier.service.abstracts.DistrictService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.districts.DistrictAlreadyDeletedException;
import com.prettier.shared.exception.exceptions.districts.DistrictAlreadyExistsException;
import com.prettier.shared.exception.exceptions.districts.DistrictNotCreatedException;
import com.prettier.shared.exception.exceptions.districts.DistrictNotFoundException;
import com.prettier.shared.utils.enums.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service implementation class that manages District-related operations.
 * This class handles CRUD operations and pagination for Districts.
 * Implements the DistrictService interface to provide district management functionality.
 * Includes interaction with City service for maintaining district-city relationships.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DistrictManager implements DistrictService {

    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;

    private final CityService cityService;
    private final CityMapper cityMapper;

    //NOT: *********** Data Inilitalizer icin gerekli metotlar *************************

    /**
     * Retrieves all districts from the database.
     * Used primarily for data initialization purposes.
     *
     * @return List of all District entities in the database
     */
    @Override
    public List<District> getAllDistricts() {

        return districtRepository.findAll();
    }


    //NOT: *********** District Manager standart metotlar *************************************

    //Not: getAll() *********************************************************************************************************************************
    /**
     * Retrieves a paginated list of all districts.
     *
     * @param language The language for error messages and localization
     * @param page The page number for pagination
     * @param size The number of items per page
     * @param sort The field to sort by
     * @param type The sort direction ("asc" or "desc")
     * @return A Page of DistrictResponse containing all districts
     * @throws DistrictNotFoundException if no districts are found
     */
    @Override
    public Page<DistrictResponse> getDistricts(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getDistricts]", this.getClass().getSimpleName());
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            PageRequest.of(page, size, Sort.by(sort).descending());
        }

        List<DistrictResponse> districts = districtRepository.findAll()
                .stream()
                .map(districtMapper::toResponse)
                .collect(Collectors.toList());

        if (districts.isEmpty()) {
            throw new DistrictNotFoundException(language, FriendlyMessageCodes.DISTRICT_NOT_FOUND_EXCEPTION, "Districts not found");
        }
        log.debug("[{}][getDistricts] -> response: {}", this.getClass().getSimpleName(), districts);
        return districtRepository.findAll(pageable).map(districtMapper::toResponse);
    }


    //Not: getById() *********************************************************************************************************************************
    /**
     * Retrieves a specific district by its ID.
     *
     * @param language The language for error messages and localization
     * @param id The ID of the district to retrieve
     * @return DistrictResponse containing the district details
     * @throws DistrictNotFoundException if the district is not found
     */
    @Override
    public DistrictResponse getByDistrictId(Language language, Long id) {

        log.debug("[{}][getDistrict] -> request districtId: {}", this.getClass().getSimpleName(), id);

        District district = districtRepository.findById(id).orElseThrow(() -> new DistrictNotFoundException(language, FriendlyMessageCodes.DISTRICT_NOT_FOUND_EXCEPTION, "District not found for district id: " + id));

        log.debug("[{}][getDistrict] -> response: {}", this.getClass().getSimpleName(), district);
        return districtMapper.toResponse(district);
    }

    //Not: add() ****************************************************************************************************************************************
    /**
     * Creates a new district.
     *
     * @param language The language for error messages and localization
     * @param districtRequest The district creation request containing district details
     * @return DistrictResponse containing the created district details
     * @throws DistrictNotCreatedException if the district cannot be created
     * @throws DistrictAlreadyExistsException if a district with the same name already exists
     */
    @Override
    public DistrictResponse add(Language language, DistrictRequest districtRequest) {

        log.debug("[{}][createDistrict] -> request: {}", this.getClass().getSimpleName(), districtRequest);
        //Requestten gelen CityId var mi diye kontrol ediyoruz, varsa bilgileri atiyoruz
        City existingCity = cityService.getByCityId(language, districtRequest.getCityId());

        // District adi veritabaninda mevcut mu kontrolü
        boolean existsByDistrictName = existsByDistrictName(language, districtRequest.getName());

        //District db de mevcutsa hata firlat, yoksa kaydet
        if (existsByDistrictName) {
            throw new DistrictNotCreatedException(language, FriendlyMessageCodes.DISTRICT_NOT_CREATED_EXCEPTION, "district request: " + districtRequest.toString());
        } else {

            //Mapping isleminden sonra daha önce buldugumuz city bilgisini set edip, kaydediyoruz
            District newDistrict = districtMapper.toDistrict(districtRequest);
            newDistrict.setCity(existingCity);

            District response = districtRepository.save(newDistrict);
            log.debug("[{}][createDistrict] -> response: {}", this.getClass().getSimpleName(), response);
            return districtMapper.toResponse(response);
        }
    }

    //Not: update() *********************************************************************************************************************************
    /**
     * Updates an existing district.
     *
     * @param language The language for error messages and localization
     * @param districtUpdateRequest The district update request containing updated details
     * @param id The ID of the district to update
     * @return DistrictResponse containing the updated district details
     * @throws DistrictNotFoundException if the district is not found
     */
    @Override
    public DistrictResponse update(Language language, DistrictUpdateRequest districtUpdateRequest, Long id) {

        log.debug("[{}][updateDistrict] -> request: {} {}", this.getClass().getSimpleName(), id, districtUpdateRequest);

        //Requestten gelen CityId var mi diye kontrol ediyoruz, varsa bilgileri atiyoruz
        City existingCity = cityService.getByCityId(language, districtUpdateRequest.getCityId());

        //District gercekte db de var mi kontrolü
        District existingDistrict = getDistrict(language, id);

        //Mapping islemini yap, sonrasinda bulunan city bilgilerini set et
        District updatedDistrict = districtMapper.toUpdatedDistrict(districtUpdateRequest, existingDistrict);
        updatedDistrict.setCity(existingCity);

        // Veritabanına güncellenmiş District'yi kaydet
        District response = districtRepository.save(updatedDistrict);

        log.debug("[{}][updateDistrict] -> response: {}", this.getClass().getSimpleName(), response);
        return districtMapper.toResponse(response);
    }

    //Not: delete() *********************************************************************************************************************************
    /**
     * Performs a soft delete on a district by marking it as deleted.
     *
     * @param language The language for error messages and localization
     * @param id The ID of the district to delete
     * @return DistrictResponse containing the deleted district details
     * @throws DistrictNotFoundException if the district is not found
     * @throws DistrictAlreadyDeletedException if the district is already deleted
     */
    @Override
    public DistrictResponse softDelete(Language language, Long id) {

        //District Var mi kontrolü
        log.debug("[{}][deleteDistrict] -> request districtId: {}", this.getClass().getSimpleName(), id);
        District district = districtRepository.findById(id).orElseThrow(() -> new DistrictNotFoundException(language, FriendlyMessageCodes.DISTRICT_NOT_FOUND_EXCEPTION, "District not found for district id: " + id));

        //District var ama isDeleted=true mu kontrolü
        try {
            district = getDistrict(language, id);
            district.setDeleted(true);
            DistrictResponse districtResponse = districtMapper.toResponse(districtRepository.save(district));
            log.debug("[{}][deleteDistrict] -> response: {}", this.getClass().getSimpleName(), districtResponse);
            return districtResponse;
        } catch (DistrictNotFoundException districtNotFoundException) {
            throw new DistrictAlreadyDeletedException(language, FriendlyMessageCodes.DISTRICT_ALREADY_DELETED, "District already deleted district id: " + id);
        }
    }

    //Not: Other *********************************************************************************************************************************

    /**
     * Helper method to retrieve a district entity by ID.
     *
     * @param language The language for error messages and localization
     * @param districtId The ID of the district to retrieve
     * @return District entity
     * @throws DistrictNotFoundException if the district is not found
     */
    //!!! Ilgili Id, District tablosunda var mi kontrolü
    public District getDistrict(Language language, Long districtId) {

        log.debug("[{}][getDistrict] -> request districtId: {}", this.getClass().getSimpleName(), districtId);
        District district = districtRepository.findById(districtId).orElseThrow(() -> new DistrictNotFoundException(language, FriendlyMessageCodes.DISTRICT_NOT_FOUND_EXCEPTION, "District not found for district id: " + districtId));

        log.debug("[{}][getDistrict] -> response: {}", this.getClass().getSimpleName(), district);
        return district;
    }


    /**
     * Helper method to check if a district with the given name exists.
     *
     * @param language The language for error messages and localization
     * @param districtName The name to check
     * @return boolean indicating whether the district exists
     * @throws DistrictAlreadyExistsException if a district with the given name exists
     */
    //!!! Ilgili DistrictName, District tablosunda var mi kontrolü
    public boolean existsByDistrictName(Language language, String districtName) {

        log.debug("[{}][getDistrict] -> request districtName: {}", this.getClass().getSimpleName(), districtName);
        if (districtRepository.existsByName(districtName)) {
            throw new DistrictAlreadyExistsException(language, FriendlyMessageCodes.DISTRICT_ALREADY_EXISTS, "This District already exists for district name: " + districtName);
        }

        log.debug("[{}][getDistrict] -> response: {}", this.getClass().getSimpleName(), districtName);
        return false;
    }
}