package validation.validator;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import validation.dto.InvalidResponse;
import validation.dto.ValidationRequest;

@Component
public class NameValidator implements SignUpValidator {

    private static final InvalidResponse INVALID_RESPONSE = new InvalidResponse("name", "유효하지 않은 name");

    @Override
    public List<InvalidResponse> validate(ValidationRequest request) {
        String name = request.getName();

        if (name == null || isBlank(name)) {
            return Collections.singletonList(INVALID_RESPONSE);
        }

        if (isKorean(name)) {
            return Collections.emptyList();
        }
        return Collections.singletonList(INVALID_RESPONSE);
    }

    private boolean isBlank(String name) {
        return name.trim().isEmpty();
    }

    private boolean isKorean(String id) {
        return id.matches("[가-힣]+");
    }

    @Override
    public boolean isCompany(boolean isCompany) {
        return isCompany;
    }
}
