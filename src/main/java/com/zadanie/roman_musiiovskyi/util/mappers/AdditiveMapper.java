package com.zadanie.roman_musiiovskyi.util.mappers;

import com.zadanie.roman_musiiovskyi.models.Additive;
import com.zadanie.roman_musiiovskyi.util.dtos.AdditiveDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdditiveMapper {

    AdditiveMapper INSTANCE = Mappers.getMapper(AdditiveMapper.class);
    AdditiveDTO additiveToAdditiveDTO(Additive additive);
    Additive additiveDTOToAdditive(AdditiveDTO additiveDTO);
}
