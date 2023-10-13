package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.dto.AddRoleDto;
import com.app.crypto.wallet.domain.dto.CreateRoleDto;
import com.app.crypto.wallet.domain.dto.ReadRoleDto;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/roles")
@RestController
public class RoleController {
    private final RoleService roleService;
    private final DtoMapper dtoMapper;

    @GetMapping
    public List<ReadRoleDto> getAllRoles() {
        return dtoMapper.mapRoleListsToReadRoleDtoLists(roleService.getRoles());
    }

    @GetMapping(value = "/{roleId}")
    public ReadRoleDto getRole(@PathVariable Long roleId) {
        return dtoMapper.mapRoleToReadRoleDto(roleService.getRoleById(roleId));
    }

    @PostMapping(value = "/creates", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateRoleDto createRole(@RequestBody CreateRoleDto createRoleDto) {
        return dtoMapper.mapRoleToCreateRoleDto(roleService.createNewRole(dtoMapper.mapCreateRoleDtoToRole(createRoleDto)));
    }

    @PostMapping(value = "/adds", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AddRoleDto addRole(@RequestBody AddRoleDto addRoleDto) {
        return dtoMapper.mapRoleToAddRoleDto(roleService.addRoleToUser(dtoMapper.mapAddRoleDtoToRole(addRoleDto)));
    }


}
