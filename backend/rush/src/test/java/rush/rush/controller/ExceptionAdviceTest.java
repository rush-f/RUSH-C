package rush.rush.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import rush.rush.controller.article.FindArticleController;
import rush.rush.exception.AlreadyExistedEmailException;
import rush.rush.exception.AlreadySignedUpException;
import rush.rush.exception.NotAuthorizedOrExistException;
import rush.rush.exception.NotAuthorizedRedirectUriException;
import rush.rush.exception.NotExistsException;
import rush.rush.exception.NotIncludedMapException;
import rush.rush.exception.OAuth2AuthenticationProcessingException;
import rush.rush.exception.WrongGroupIdException;
import rush.rush.exception.WrongInputException;
import rush.rush.exception.WrongMapTypeException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ExceptionAdviceTest {

    @Mock
    private FindArticleController findArticleController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders
            .standaloneSetup(findArticleController)
            .setControllerAdvice(new ExceptionAdvice())
            .build();
    }

    @Test
    void handleAlreadyExistException() throws Exception {
        when(findArticleController.findMyArticles(any())).thenThrow(
            new AlreadyExistedEmailException("test"));

        mockMvc.perform(get("/articles/mine"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("errorMessage").hasJsonPath());
    }

    @Test
    void handleAlreadySignedUpException() throws Exception {
        when(findArticleController.findMyArticles(any())).thenThrow(
            new AlreadySignedUpException("test"));

        mockMvc.perform(get("/articles/mine"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("errorMessage").hasJsonPath());
    }

    @Test
    void handleNotAuthorizedOrExistException() throws Exception {
        when(findArticleController.findMyArticles(any())).thenThrow(
            new NotAuthorizedOrExistException("test"));

        mockMvc.perform(get("/articles/mine"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("errorMessage").hasJsonPath());
    }

    @Test
    void handleNotExistsException() throws Exception {
        when(findArticleController.findMyArticles(any())).thenThrow(
            new NotExistsException("test"));

        mockMvc.perform(get("/articles/mine"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("errorMessage").hasJsonPath());
    }

    @Test
    void handleNotIncludedMapException() throws Exception {
        when(findArticleController.findMyArticles(any())).thenThrow(
            new NotIncludedMapException("test"));

        mockMvc.perform(get("/articles/mine"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("errorMessage").hasJsonPath());
    }

    @Test
    void handleWrongGroupIdException() throws Exception {
        when(findArticleController.findMyArticles(any())).thenThrow(
            new WrongGroupIdException("test"));

        mockMvc.perform(get("/articles/mine"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("errorMessage").hasJsonPath());
    }

    @Test
    void handleWrongMapTypeException() throws Exception {
        when(findArticleController.findMyArticles(any())).thenThrow(
            new WrongMapTypeException("test"));

        mockMvc.perform(get("/articles/mine"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("errorMessage").hasJsonPath());
    }

    @Test
    void handleNotAuthorizedRedirectUriException() throws Exception {
        when(findArticleController.findMyArticles(any())).thenThrow(
            new NotAuthorizedRedirectUriException("test"));

        mockMvc.perform(get("/articles/mine"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("errorMessage").hasJsonPath());
    }

    @Test
    void handleOAuth2AuthenticationProcessingException() throws Exception {
        when(findArticleController.findMyArticles(any())).thenThrow(
            new OAuth2AuthenticationProcessingException("test"));

        mockMvc.perform(get("/articles/mine"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("errorMessage").hasJsonPath());
    }

    @Test
    void handleWrongInputException() throws Exception {
        when(findArticleController.findMyArticles(any())).thenThrow(
            new WrongInputException("test"));

        mockMvc.perform(get("/articles/mine"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("errorMessage").hasJsonPath());
    }
}