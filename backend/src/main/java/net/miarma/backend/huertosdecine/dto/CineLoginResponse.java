package net.miarma.backend.huertosdecine.dto;

import net.miarma.backlib.dto.CredentialDto;
import net.miarma.backlib.dto.UserDto;

public record CineLoginResponse(String token, UserDto user, CredentialDto account, UserMetadataDto.Response metadata) {
}
