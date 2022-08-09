package software.sigma.sip.application.service;

import software.sigma.sip.infrastructure.dto.TestDto;

import java.util.List;

public interface TestService {
    List<TestDto> findAll();
}
