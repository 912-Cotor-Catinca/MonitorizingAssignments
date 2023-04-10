package org.example;

import org.example.domain.Nota;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.NotaXMLRepository;
import org.example.repository.StudentXMLRepository;
import org.example.repository.TemaXMLRepository;
import org.example.service.Service;
import org.example.validation.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AssignmentTest {
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    private Validator<Student> studentValidator = new StudentValidator();
    private Validator<Tema> temaValidator = new TemaValidator();
    private Validator<Nota> notaValidator = new NotaValidator();
    private StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
    private TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
    private NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

    private Service service = new Service(fileRepository1, fileRepository2, fileRepository3);

    @Test
    public void addGoodAssignment() {
        int result = service.saveTema("4", "ssvv", 8, 6);
        assertEquals(0, result);
    }

    @Test
    public void addBadIdAssignment() {
        int result = service.saveTema("", "ssvv", 8, 6);
        assertEquals(1, result);
    }

    @Test
    public void addBadDeadlineAssignment() {
        int result = service.saveTema("4", "ssvv", 0, 6);
        assertEquals(1, result);
    }

    @Test
    public void addBadStartlineAssignment() {
        int result = service.saveTema("4", "ssvv", 8, 0);
        assertEquals(1, result);
    }

    @Test
    public void addBadDescriptionAssignment() {
        int result = service.saveTema("4", "", 8, 5);
        assertEquals(1, result);
    }

    @Test
    public void addRepoAssignment() {
        Tema tema = new Tema("4", "ssvv", 5, 4);
        try {
            fileRepository2.save(tema);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
}
