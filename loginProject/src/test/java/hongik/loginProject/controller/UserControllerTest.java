package hongik.loginProject.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import hongik.loginProject.entity.User;
import hongik.loginProject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testLoginSuccess() throws Exception {
        // Given
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password123");
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        // When & Then
        mockMvc.perform(post("/login")
                        .param("username", "testUser")
                        .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("username", "testUser"))
                .andExpect(view().name("loginSuccess"));
    }
}
