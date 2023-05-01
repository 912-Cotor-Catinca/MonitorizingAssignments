package org.example;

import org.example.domain.Nota;
import org.example.domain.Pair;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.NotaXMLRepository;
import org.example.repository.StudentXMLRepository;
import org.example.repository.TemaXMLRepository;
import org.example.service.Service;
import org.example.validation.NotaValidator;
import org.example.validation.StudentValidator;
import org.example.validation.TemaValidator;
import org.example.validation.Validator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class IntegrationTest {

    private Validator<Student> studentValidator = new StudentValidator();
    private Validator<Tema> temaValidator = new TemaValidator();
    private Validator<Nota> notaValidator = new NotaValidator();
    @Mock
    private StudentXMLRepository studentXMLRepositoryMock = new StudentXMLRepository(studentValidator, "studenti.xml");
    @Mock
    private TemaXMLRepository temaXMLRepositoryMock = new TemaXMLRepository(temaValidator, "teme.xml");

    private NotaXMLRepository notaXMLRepository = new NotaXMLRepository(notaValidator, "note.xml");

    private Service service;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service  = new Service(studentXMLRepositoryMock, temaXMLRepositoryMock, notaXMLRepository);
    }

    @Test
    public void test_add_student() {
        Student expected = new Student("15", "alex", 800);
        when(studentXMLRepositoryMock.save(new Student("15", "alex", 800))).thenReturn(expected);
        int result = service.saveStudent("15", "alex", 800);
        assertEquals(result, 1);
    }

    @Test
    public void test_add_student_assignment() {
        Student expected = new Student("15", "alex", 800);
        when(studentXMLRepositoryMock.save(new Student("15", "alex", 800))).thenReturn(expected);
        int result = service.saveStudent("15", "alex", 800);
        assertEquals(result, 1);

        Tema tema = new Tema("13", "ssvv", 8, 7);
        when(temaXMLRepositoryMock.save(tema)).thenReturn(new Tema("13", "ssvv", 8, 7));
        int result1 = service.saveTema("13", "ssvv", 8, 7);
        assertEquals(result1, 1);
    }

    @Test
    public void test_add_all() {
        Student expected = new Student("15", "alex", 800);
        studentXMLRepositoryMock.save(new Student("15", "alex", 800));

        verify(studentXMLRepositoryMock).save(new Student("15", "alex", 800));
        when(studentXMLRepositoryMock.save(new Student("15", "alex", 800))).thenReturn(expected);
        int result = service.saveStudent("15", "alex", 800);
        assertEquals(result, 1);

        Tema tema = new Tema("13", "ssvv", 8, 7);
        doReturn(new Tema("13", "ssvv", 8, 7)).when(temaXMLRepositoryMock).save(tema);
        when(temaXMLRepositoryMock.save(tema)).thenReturn(new Tema("13", "ssvv", 8, 7));
        int result1 = service.saveTema("13", "ssvv", 8, 7);
        assertEquals(result1, 1);

        Iterable<Nota> notas = notaXMLRepository.findAll();
        Nota first = notas.iterator().next();
        assertEquals(first.getID(), new Pair<>("5", "4"));
        assertEquals(first.getSaptamanaPredare(), 10);

    }
}
