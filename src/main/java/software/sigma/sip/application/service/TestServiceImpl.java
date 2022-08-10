package software.sigma.sip.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.sip.domain.repository.TestRepository;
import software.sigma.sip.infrastructure.dto.TestDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    @Override
    public List<TestDto> findAll() {
        return testRepository.findAll()
                .stream()
                .map(TestDto::toTestDto)
                .toList();
    }
}
