package validation.validator;

import java.util.List;
import validation.dto.InvalidResponse;
import validation.dto.ValidationRequest;

public interface SignUpValidator {

    List<InvalidResponse> validate(ValidationRequest request);

    boolean isSatisfiedBy(boolean isCompany);
}
