package ru.vinogradov.api.restApiForSocialPlatform.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vinogradov.api.restApiForSocialPlatform.entities.Role;
import ru.vinogradov.api.restApiForSocialPlatform.repositories.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public List<Role> findAllByName(String name) {
        return repository.findAllByName(name);
    }
}
