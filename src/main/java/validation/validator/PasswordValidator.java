package validation.validator;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import validation.dto.InvalidResponse;
import validation.dto.ValidationRequest;

@Component
public class PasswordValidator implements SignUpValidator {

    private static final InvalidResponse INVALID_RESPONSE = new InvalidResponse("password", "유효하지 않은 password");

    @Override
    public List<InvalidResponse> validate(ValidationRequest request) {
        String password = request.getPassword();

        if (password == null || isBlank(password)) {
            return Collections.singletonList(INVALID_RESPONSE);
        }

        if (isEnglishOrNumber(password)) {
            return Collections.emptyList();
        }
        return Collections.singletonList(INVALID_RESPONSE);
    }

    private boolean isBlank(String password) {
        return password.trim().isEmpty();
    }

    private boolean isEnglishOrNumber(String password) {
        return password.matches("[a-zA-Z0-9]+");
    }

    @Override
    public boolean isCompany(boolean isCompany) {
        return isCompany;
    }
}
