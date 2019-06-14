package com.zadanie.roman_musiiovskyi.services.interfaces;

import com.zadanie.roman_musiiovskyi.models.Additive;
import com.zadanie.roman_musiiovskyi.services.CRUDService;
import com.zadanie.roman_musiiovskyi.util.dtos.AdditiveDTO;

public interface AdditiveService extends CRUDService<AdditiveDTO> {
    AdditiveDTO saveAndReturnDTO(Additive additive);
}
