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

public class Big_Bang {
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
    public void tc_1_Assignment() {
        int result = service.saveTema("5", "ssvv", 8, 6);
        assertEquals(1, result);
    }

    @Test
    public void tc_2_InvalidStudent() {
        Iterable<Student> students = service.findAllStudents();
        int result = service.saveStudent("", "alex", 800);
        assertEquals(0, result);
    }

    @Test
    public void tc_3_InvalidGrade() {
        Iterable<Nota> students = service.findAllNote();
        int result = service.saveNota("2", "1", 6.3, 7, "ok");
        assertEquals(1, result);
    }

    @Test
    public void tc_4_BigBangIntegration() {
        int result_tema = service.saveTema("5", "ssvv", 8, 6);
        assertEquals(1, result_tema);
        Iterable<Student> students = service.findAllStudents();
        int result_student = service.saveStudent("", "alex", 800);
        assertEquals(0, result_student);
        Iterable<Nota> grades = service.findAllNote();
        int result = service.saveNota("2", "1", 6.3, 7, "ok");
        assertEquals(1, result);
    }
}
