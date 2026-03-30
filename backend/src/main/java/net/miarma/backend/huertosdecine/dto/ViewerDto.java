package net.miarma.backend.huertosdecine.dto;

import net.miarma.backlib.dto.CredentialDto;
import net.miarma.backlib.dto.UserDto;

public record ViewerDto(UserDto user, CredentialDto account, UserMetadataDto.Response metadata) {
}
