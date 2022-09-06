package validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import validation.dto.InvalidResponse;
import validation.dto.ValidationRequest;
import validation.validator.IdValidator;
import validation.validator.NameValidator;
import validation.validator.PasswordValidator;
import validation.validator.SignUpValidator;

class SignUpValidationTest {

    private final SignUpValidator idValidator = new IdValidator();

    private final SignUpValidator passwordValidator = new PasswordValidator();

    private final SignUpValidator nameValidator = new NameValidator();

    private SignUpValidation signUpValidation;

    @BeforeEach
    void setUp() {
        signUpValidation = new SignUpValidation(List.of(
            idValidator,
            passwordValidator,
            nameValidator
        ));
    }

    @DisplayName("유효성 검증 성공")
    @Test
    void validate_success() {
        // given
        ValidationRequest request = new ValidationRequest("dani", "dani123", "다니", false);

        // when
        List<InvalidResponse> responses = signUpValidation.validate(request);

        // then
        assertThat(responses).isEmpty();
    }

    @DisplayName("유효성 검증 실패 - 유효하지 않은 id")
    @ParameterizedTest
    @ValueSource(strings = {"Dani", "da ni", "다니"})
    void validate_fail1(String id) {
        // given
        ValidationRequest request = new ValidationRequest(id, "dani123", null, true);

        List<InvalidResponse> expected = List.of(new InvalidResponse("id", "유효하지 않은 id"));

        // when
        List<InvalidResponse> responses = signUpValidation.validate(request);

        // then
        assertThat(responses.size()).isOne();
        assertThat(responses)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }

    @DisplayName("유효성 검증 실패 - 유효하지 않은 password")
    @ParameterizedTest
    @ValueSource(strings = {"dani123##", "da ni", "다니123"})
    void validate_fail2(String password) {
        // given
        ValidationRequest request = new ValidationRequest("dani", password, null, true);

        List<InvalidResponse> expected = List.of(new InvalidResponse("password", "유효하지 않은 password"));

        // when
        List<InvalidResponse> responses = signUpValidation.validate(request);

        // then
        assertThat(responses.size()).isOne();
        assertThat(responses)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }

    @DisplayName("유효성 검증 실패 - 유효하지 않은 name")
    @ParameterizedTest
    @ValueSource(strings = {"다니123", "dani", "", "  "})
    void validate_fail3(String name) {
        // given
        ValidationRequest request = new ValidationRequest("dani", "dani123", name, false);

        List<InvalidResponse> expected = List.of(new InvalidResponse("name", "유효하지 않은 name"));

        // when
        List<InvalidResponse> responses = signUpValidation.validate(request);

        // then
        assertThat(responses.size()).isOne();
        assertThat(responses)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }
}
