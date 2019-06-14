package com.zadanie.roman_musiiovskyi.services.serviceJPA;

import com.zadanie.roman_musiiovskyi.controllers.AdditiveRestController;
import com.zadanie.roman_musiiovskyi.models.Additive;
import com.zadanie.roman_musiiovskyi.repository.AdditiveRepoJPA;
import com.zadanie.roman_musiiovskyi.services.interfaces.AdditiveService;
import com.zadanie.roman_musiiovskyi.services.exeptions.ResourceNotFoundException;
import com.zadanie.roman_musiiovskyi.util.dtos.AdditiveDTO;
import com.zadanie.roman_musiiovskyi.util.mappers.AdditiveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdditiveServiceJPA implements AdditiveService {

    private AdditiveRepoJPA additiveRepoJPA;
    private AdditiveMapper additiveMapper;

    @Autowired
    public AdditiveServiceJPA(AdditiveRepoJPA additiveRepoJPA, AdditiveMapper additiveMapper) {
        this.additiveRepoJPA = additiveRepoJPA;
        this.additiveMapper = additiveMapper;
    }

    @Override
    public List<AdditiveDTO> listAll() {
        return additiveRepoJPA
                .findAll()
                .stream()
                .map(additive -> {
                    AdditiveDTO additiveDTO= additiveMapper.additiveToAdditiveDTO(additive);
                    additiveDTO.setUrl(getAdditiveUrl(additive.getId()));
                    return additiveDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public AdditiveDTO getById(Long id) {
        return additiveRepoJPA.findById(id)
                .map(additiveMapper::additiveToAdditiveDTO)
                .map(lessonDTO -> {
                    lessonDTO.setUrl(getAdditiveUrl(id));
                    return lessonDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public AdditiveDTO saveAndReturnDTO(Additive additive) {
        Additive savedAdditive = additiveRepoJPA.save(additive);

        AdditiveDTO returnDto = additiveMapper.additiveToAdditiveDTO(savedAdditive);
        returnDto.setUrl(getAdditiveUrl(savedAdditive.getId()));

        return returnDto;
    }

    @Override
    public AdditiveDTO createNew(AdditiveDTO additiveDTO) {
        return saveAndReturnDTO(additiveMapper.additiveDTOToAdditive(additiveDTO));
    }

    @Override
    public AdditiveDTO putByDTO(Long id, AdditiveDTO object) {
        Additive lesson = additiveMapper.additiveDTOToAdditive(object);
        lesson.setId(id);

        return saveAndReturnDTO(lesson);
    }

    @Override
    public AdditiveDTO patchByDTO(Long id, AdditiveDTO object) {
        return additiveRepoJPA.findById(id).map(additive -> {
            if (object.getName() != null){
                additive.setName(object.getName());
            }

            AdditiveDTO returnDto = additiveMapper.additiveToAdditiveDTO(additiveRepoJPA.save(additive));
            return returnDto;

        }).orElseThrow(ResourceNotFoundException::new);
    }


    @Override
    public void deleteById(Long id) {
        additiveRepoJPA.deleteById(id);
    }

    private String getAdditiveUrl(Long id) {
        return AdditiveRestController.BASE_URL + "/" + id;
    }

}
