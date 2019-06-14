package com.zadanie.roman_musiiovskyi.services.serviceJPA;

import com.zadanie.roman_musiiovskyi.controllers.RoleRestController;
import com.zadanie.roman_musiiovskyi.models.security.Role;
import com.zadanie.roman_musiiovskyi.repository.RoleRepoJPA;
import com.zadanie.roman_musiiovskyi.services.interfaces.RoleService;
import com.zadanie.roman_musiiovskyi.services.exeptions.ResourceNotFoundException;
import com.zadanie.roman_musiiovskyi.util.dtos.RoleDTO;
import com.zadanie.roman_musiiovskyi.util.mappers.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceJPA implements RoleService {

    private  RoleRepoJPA roleRepoJPA;
    private  RoleMapper roleMapper;

    @Autowired
    public RoleServiceJPA(RoleRepoJPA roleRepoJPA, RoleMapper roleMapper) {
        this.roleRepoJPA = roleRepoJPA;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDTO> listAll() {
        return roleRepoJPA
                .findAll()
                .stream()
                .map(role -> {
                    RoleDTO roleDTO= roleMapper.roleToRoleDTO(role);
                    return roleDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO getById(Long id) {
        return roleRepoJPA.findById(id)
                .map(roleMapper::roleToRoleDTO)
                .map(roleDTO -> {
                    roleDTO.setUrl(getRoleUrl(id));
                    return roleDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public RoleDTO createNew(RoleDTO object) {

        return saveAndReturnDTO(roleMapper.roleDTOToRole(object));
    }

    @Override
    public RoleDTO putByDTO(Long id, RoleDTO object) {

        Role role = roleMapper.roleDTOToRole(object);
        role.setId(id);

        return saveAndReturnDTO(role);
    }

    @Override
    public RoleDTO patchByDTO(Long id, RoleDTO object) {
        return roleRepoJPA.findById(id).map(role -> {
            if (role.getRole() != null){
                role.setRole(role.getRole());
            }
            RoleDTO returnDTO = roleMapper.roleToRoleDTO(roleRepoJPA.save(role));
            return  returnDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        roleRepoJPA.deleteById(id);
    }

    @Override
    public RoleDTO saveAndReturnDTO(Role role) {
        Role savedRole = roleRepoJPA.save(role);

        RoleDTO returnDTO = roleMapper.roleToRoleDTO(savedRole);
        return returnDTO;
    }

    private String getRoleUrl(Long id) {
        return RoleRestController.BASE_URL + "/" + id;
    }

}
