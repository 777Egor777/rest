package ru.job4j.auth.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.auth.AuthApplication;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest(classes = AuthApplication.class)
@AutoConfigureMockMvc
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository rep;

    @Test
    public void whenGetAllPersonsThenOkAndReturnAllPersons() throws Exception {
        mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk());
        verify(rep).findAll();
    }

    @Test
    public void whenGetPersonByIdThenOkAndReturnPerson() throws Exception {
        when(rep.findById(any())).thenReturn(Optional.of(new Person()));
        mockMvc.perform(get("/person/1000"))
                .andDo(print())
                .andExpect(status().isOk());
        ArgumentCaptor<Integer> arg = ArgumentCaptor.forClass(Integer.class);
        verify(rep).findById(arg.capture());
        assertThat(arg.getValue(), is(1000));
    }

    @Test
    public void whenPOSTNewPersonThenStatusCreatedAndSavePerson() throws Exception {
        when(rep.save(any())).thenReturn(new Person());
        mockMvc.perform(post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"egor\",\"password\":\"123\"}"))
                .andDo(print())
                .andExpect(status().isCreated());
        ArgumentCaptor<Person> arg = ArgumentCaptor.forClass(Person.class);
        verify(rep).save(arg.capture());
        assertThat(arg.getValue().getId(), is(0));
        assertThat(arg.getValue().getLogin(), is("egor"));
        assertThat(arg.getValue().getPassword(), is("123"));
    }

    @Test
    public void whenPUTPersonThenStatusOkAndUpdatePerson() throws Exception {
        when(rep.save(any())).thenReturn(new Person());
        mockMvc.perform(put("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"25\",\"login\":\"egor\",\"password\":\"123\"}"))
                .andDo(print())
                .andExpect(status().isOk());
        ArgumentCaptor<Person> arg = ArgumentCaptor.forClass(Person.class);
        verify(rep).save(arg.capture());
        assertThat(arg.getValue().getId(), is(25));
        assertThat(arg.getValue().getLogin(), is("egor"));
        assertThat(arg.getValue().getPassword(), is("123"));
    }

    @Test
    public void whenDELETEPersonThenStatusOkAndDeletePerson() throws Exception {
        mockMvc.perform(delete("/person/777"))
                .andDo(print())
                .andExpect(status().isOk());
        ArgumentCaptor<Person> arg = ArgumentCaptor.forClass(Person.class);
        verify(rep).delete(arg.capture());
        assertThat(arg.getValue().getId(), is(777));
    }
}