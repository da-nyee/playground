package validation.validator;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import validation.dto.InvalidResponse;
import validation.dto.ValidationRequest;

@Component
public class IdValidator implements SignUpValidator {

    private static final InvalidResponse INVALID_RESPONSE = new InvalidResponse("id", "유효하지 않은 id");

    @Override
    public List<InvalidResponse> validate(ValidationRequest request) {
        String id = request.getId();

        if (id == null || isBlank(id)) {
            return Collections.singletonList(INVALID_RESPONSE);
        }

        if (isLowercaseOrNumber(id)) {
            return Collections.emptyList();
        }
        return Collections.singletonList(INVALID_RESPONSE);
    }

    private boolean isBlank(String id) {
        return id.trim().isEmpty();
    }

    private boolean isLowercaseOrNumber(String id) {
        return id.matches("[a-z0-9]+");
    }

    @Override
    public boolean isSatisfiedBy(boolean isCompany) {
        return true;
    }
}
