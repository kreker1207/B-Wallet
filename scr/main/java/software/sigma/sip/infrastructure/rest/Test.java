package software.sigma.sip.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.sip.application.service.TestService;
import software.sigma.sip.infrastructure.dto.TestDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class Test {

    private final TestService testService;

    @GetMapping
    public List<TestDto> findAll() {
        return testService.findAll();
    }
}
