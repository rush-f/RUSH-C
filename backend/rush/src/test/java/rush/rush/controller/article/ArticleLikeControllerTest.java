package rush.rush.controller.article;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import rush.rush.controller.ExceptionAdvice;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ArticleLikeControllerTest {

    @Autowired
    private ArticleLikeController articleLikeController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders
            .standaloneSetup(articleLikeController)
            .setControllerAdvice(new ExceptionAdvice())
            .build();
    }

    @Test
    @DisplayName("좋아요 변경 - 존재하지 않는 게시글")
    void changeMyLike_IfNotExistArticleId_ThrowException() throws Exception{
        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("hasiliked", "true");

        mockMvc.perform(post("/articles/public/1/like")
            .params(info))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("errorMessage").hasJsonPath());
    }

    @Test
    @DisplayName("좋아요 확인 - 잘못된 맵타입")
    void checkMyLike_IfWrongMapType_ThrowException() throws Exception{
        mockMvc.perform(get("/articles/public22/1/like"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("errorMessage").hasJsonPath());
    }
}