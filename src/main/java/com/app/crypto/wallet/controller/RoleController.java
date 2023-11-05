package com.app.crypto.wallet.controller;

import com.app.crypto.wallet.domain.dto.InputDataRoleDto;
import com.app.crypto.wallet.domain.dto.ReadRoleDto;
import com.app.crypto.wallet.exceptions.RoleIsAlreadyRemoveException;
import com.app.crypto.wallet.exceptions.RoleIsAssignedException;
import com.app.crypto.wallet.exceptions.RoleNotFoundException;
import com.app.crypto.wallet.exceptions.UserNotFoundException;
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
    public ResponseEntity<ReadRoleDto> addRoleToUser(@RequestBody InputDataRoleDto inputDataRoleDto) throws UserNotFoundException, RoleNotFoundException, RoleIsAssignedException {
        return ResponseEntity.ok().body(dtoMapper.mapToReadRoleDto(roleService.addRoleToUser(dtoMapper.mapToRole(inputDataRoleDto))));
    }

    @PostMapping(value = "/remove", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadRoleDto> removeRoleFromUser(@RequestBody InputDataRoleDto inputDataRoleDto) throws UserNotFoundException, RoleNotFoundException, RoleIsAlreadyRemoveException {
        return ResponseEntity.ok().body(dtoMapper.mapToReadRoleDto(roleService.removeUserRoles(dtoMapper.mapToRole(inputDataRoleDto))));
    }
}
