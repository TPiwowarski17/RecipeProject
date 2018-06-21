package com.piwowarski.controllers;


import com.piwowarski.DTO.RecipeDto;
import com.piwowarski.models.Recipe;
import com.piwowarski.repositories.RecipeRepository;
import com.piwowarski.services.ImageService;
import com.piwowarski.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ImageControllerTest {
    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    ImageController imageController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        imageController = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    public void getImageForm() throws Exception {
        RecipeDto dto = new RecipeDto();
        dto.setId(1L);

        when(recipeService.findDtoById(anyLong())).thenReturn(dto);

        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findDtoById(anyLong());
    }


    @Test
    public void handleImagePost() throws Exception {

        Long id = 1L;
        MockMultipartFile multipartFile =
                new MockMultipartFile("imageFile", "testing.txt", "text/plain", "Ala ma kota".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(id);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        imageService.saveImageFile(id, multipartFile);

        verify(recipeRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImg().length);
    }

    @Test
    public void renderImageFromDB() throws Exception {

        RecipeDto dto = new RecipeDto();
        dto.setId(1L);

        String s = "Ala ma kota";
        Byte[] bytes = new Byte[s.getBytes().length];

        int i = 0;

        for (byte b : s.getBytes()) {
            bytes[i++] = b;
        }

        dto.setImg(bytes);
        when(recipeService.findDtoById(anyLong())).thenReturn(dto);

        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        byte[] responseBytes = response.getContentAsByteArray();

        assertEquals(s.getBytes().length, responseBytes.length);
    }

}
