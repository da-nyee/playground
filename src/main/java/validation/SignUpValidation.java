package validation;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import validation.dto.InvalidResponse;
import validation.dto.ValidationRequest;
import validation.validator.SignUpValidator;

@Component
@RequiredArgsConstructor
public class SignUpValidation {

    private final List<SignUpValidator> validators;

    public List<InvalidResponse> validate(ValidationRequest request) {
        if (request == null) {
            return Collections.singletonList(new InvalidResponse("request", "유효하지 않은 request"));
        }

        return validators.stream()
            .filter(validator -> validator.isSatisfiedBy(request.isCompany()))
            .map(validator -> validator.validate(request))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }
}
