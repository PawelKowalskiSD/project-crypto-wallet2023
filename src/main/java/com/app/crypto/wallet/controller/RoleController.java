package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.dto.AddRoleDto;
import com.app.crypto.wallet.domain.dto.ReadRoleDto;
import com.app.crypto.wallet.domain.dto.RemoveRoleDto;
import com.app.crypto.wallet.exceptions.RoleNotFoundException;
import com.app.crypto.wallet.mapper.DtoMapper;
import com.app.crypto.wallet.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/roles")
@RestController
public class RoleController {
    private final RoleService roleService;
    private final DtoMapper dtoMapper;

    @GetMapping
    public ResponseEntity<List<ReadRoleDto>> getAllRoles() {
        return ResponseEntity.ok().body(dtoMapper.mapToReadRoleDtoList(roleService.getRoles()));
    }

    @GetMapping(value = "/{roleId}")
    public ResponseEntity<ReadRoleDto> getRole(@PathVariable Long roleId) throws RoleNotFoundException {
        return ResponseEntity.ok().body(dtoMapper.mapToReadRoleDto(roleService.getRoleById(roleId)));
    }

    @PostMapping(value = "/adds", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReadRoleDto>> addRoleToUser(@RequestBody AddRoleDto addRoleDto) {
        return ResponseEntity.ok().body(dtoMapper.mapToReadRoleDtoList(roleService.addRoleToUser(dtoMapper.mapToRole(addRoleDto))));
    }

    @PostMapping(value = "/remove", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReadRoleDto>> removeRoleFromUser(@RequestBody RemoveRoleDto removeRoleDto) {
        return ResponseEntity.ok().body(dtoMapper.mapToReadRoleDtoList(roleService.removeUserRoles(dtoMapper.mapToRole(removeRoleDto))));
    }
}
