package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.dto.AddRoleDto;
import com.app.crypto.wallet.domain.dto.CreateRoleDto;
import com.app.crypto.wallet.domain.dto.ReadRoleDto;
import com.app.crypto.wallet.domain.dto.RemoveRoleDto;
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
        return dtoMapper.mapToReadRoleDtoList(roleService.getRoles());
    }

    @GetMapping(value = "/{roleId}")
    public ReadRoleDto getRole(@PathVariable Long roleId) {
        return dtoMapper.mapToReadRoleDto(roleService.getRoleById(roleId));
    }

    @PostMapping(value = "/creates", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateRoleDto createRole(@RequestBody CreateRoleDto createRoleDto) {
        return dtoMapper.mapToCreateRoleDto(roleService.createNewRole(dtoMapper.mapToRole(createRoleDto)));
    }

    @PostMapping(value = "/adds", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AddRoleDto addRole(@RequestBody AddRoleDto addRoleDto) {
        return dtoMapper.mapToAddRoleDto(roleService.addRoleToUser(dtoMapper.mapToRole(addRoleDto)));
    }

    @PostMapping(value = "/remove", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RemoveRoleDto removeRole(@RequestBody RemoveRoleDto removeRoleDto) {
        return dtoMapper.mapToRemoveRoleDto(roleService.removeUserRoles(dtoMapper.mapToRole(removeRoleDto)));
    }
}
